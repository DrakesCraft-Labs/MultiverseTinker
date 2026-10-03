#!/usr/bin/env bash
# Boots a real Paper server with the plugin jar and fails if MultiverseTinker does not come up cleanly.
#
# usage: paper-smoke-test.sh <minecraft-version> <plugin-jar>
#
# The server is throwaway: a flat world in a temp folder, offline mode, no players. Once it is up the
# console runs the plugin's own self-checks (/mvtink verify resolves every item id, /mvtink reload
# re-reads the whole configuration), then the server is stopped and its log is scanned for anything
# the plugin threw. The log is copied to $SMOKE_LOG_DIR (default: ./smoke-logs) for the job artifacts.

set -euo pipefail

VERSION="${1:?minecraft version required}"
PLUGIN_JAR="$(realpath "${2:?plugin jar required}")"
LOG_DIR="$(realpath -m "${SMOKE_LOG_DIR:-smoke-logs}")"
BOOT_TIMEOUT="${SMOKE_BOOT_TIMEOUT:-420}"
UA="MultiverseTinker-CI (https://github.com/${GITHUB_REPOSITORY:-DrakesCraft-Labs/MultiverseTinker})"

WORK="$(mktemp -d)"
LOG="$WORK/server.log"
mkdir -p "$LOG_DIR" "$WORK/plugins"
trap 'cp "$LOG" "$LOG_DIR/paper-$VERSION.log" 2>/dev/null || true' EXIT

echo "::group::Download Paper $VERSION"
BUILD_JSON="$(curl -fsSL -H "User-Agent: $UA" "https://fill.papermc.io/v3/projects/paper/versions/$VERSION/builds/latest")"
URL="$(jq -r '.downloads["server:default"].url' <<<"$BUILD_JSON")"
SHA="$(jq -r '.downloads["server:default"].checksums.sha256' <<<"$BUILD_JSON")"
echo "Paper $VERSION build $(jq -r '.id' <<<"$BUILD_JSON") ($(jq -r '.channel' <<<"$BUILD_JSON"))"
curl -fsSL -H "User-Agent: $UA" -o "$WORK/paper.jar" "$URL"
echo "$SHA  $WORK/paper.jar" | sha256sum -c -
echo "::endgroup::"

cp "$PLUGIN_JAR" "$WORK/plugins/"
# A Minecraft server refuses to start until its EULA is accepted (https://aka.ms/MinecraftEULA).
echo "eula=true" > "$WORK/eula.txt"
cat > "$WORK/server.properties" <<'EOF'
online-mode=false
level-type=minecraft\:flat
generate-structures=false
spawn-protection=0
view-distance=2
simulation-distance=2
max-players=1
server-port=25565
enable-rcon=false
enable-query=false
EOF

cd "$WORK"
mkfifo console
# Hold the fifo open on fd 3 so the server never sees EOF on stdin before "stop" is sent.
exec 3<>console
java -Xms1G -Xmx2G -jar paper.jar --nogui <console >"$LOG" 2>&1 &
SERVER_PID=$!

send() { echo "$1" >&3; }

wait_for() {
  local pattern="$1" timeout="$2"
  for _ in $(seq 1 "$timeout"); do
    if grep -qE "$pattern" "$LOG"; then return 0; fi
    if ! kill -0 "$SERVER_PID" 2>/dev/null; then
      echo "::error::Paper $VERSION exited before printing: $pattern"
      cat "$LOG"
      exit 1
    fi
    sleep 1
  done
  echo "::error::Timed out after ${timeout}s waiting for: $pattern"
  cat "$LOG"
  kill "$SERVER_PID" 2>/dev/null || true
  exit 1
}

echo "::group::Boot Paper $VERSION"
wait_for 'Done \([0-9.]+s\)!' "$BOOT_TIMEOUT"
echo "::endgroup::"

send "mvtink verify"
wait_for 'Every item id resolves correctly|ids failed to resolve' 60
send "mvtink reload"
wait_for 'reloaded successfully' 60
send "stop"
for _ in $(seq 1 120); do
  kill -0 "$SERVER_PID" 2>/dev/null || break
  sleep 1
done
if kill -0 "$SERVER_PID" 2>/dev/null; then
  echo "::warning::Paper did not stop within 120s; killing it."
  kill -9 "$SERVER_PID" || true
fi
exec 3>&-

echo "::group::Server log"
cat "$LOG"
echo "::endgroup::"

FAILED=0
check_present() {
  if ! grep -qE "$1" "$LOG"; then
    echo "::error::Paper $VERSION: missing from the log: $2"
    FAILED=1
  fi
}
check_absent() {
  if grep -nE "$1" "$LOG"; then
    echo "::error::Paper $VERSION: $2"
    FAILED=1
  fi
}

check_present 'MultiverseTinker successfully enabled' 'the plugin never finished enabling'
check_present 'Every item id resolves correctly' '/mvtink verify did not resolve every item id'
check_present 'configuration, items and loot tables reloaded successfully' '/mvtink reload did not complete'
check_absent "Could not load '?plugins/MultiverseTinker" 'Paper refused to load the jar'
check_absent 'Error occurred while (enabling|disabling) MultiverseTinker' 'the plugin threw while enabling or disabling'
check_absent 'ids failed to resolve' '/mvtink verify found unresolved item ids'
check_absent '(NoSuchMethodError|NoSuchFieldError|NoClassDefFoundError|AbstractMethodError|IncompatibleClassChangeError)' 'a linkage error: the jar uses an API this server does not have'
check_absent 'at com\.chagui68\.' 'an exception was thrown from plugin code'

if [ "$FAILED" -ne 0 ]; then
  exit 1
fi
echo "MultiverseTinker enabled, verified, reloaded and disabled cleanly on Paper $VERSION."
