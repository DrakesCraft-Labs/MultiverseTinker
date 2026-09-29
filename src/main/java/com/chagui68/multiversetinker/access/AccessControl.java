package com.chagui68.multiversetinker.access;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * Decides who may use each part of the plugin, straight from {@code config.yml}.
 *
 * <p>A server that runs no permissions plugin still has to decide who may forge, browse the codex or
 * brush stone, so every surface carries a {@link Mode}: {@code public} leaves it open to everyone,
 * {@code op} restricts it to operators, and {@code permission} hands the decision to the node
 * declared in {@code plugin.yml} (so LuckPerms, PermissionsEx or the vanilla {@code permissions.yml}
 * can narrow it further). The defaults reproduce the historical behaviour of the plugin exactly, so
 * an untouched config changes nothing.</p>
 */
public final class AccessControl {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /** Every part of the plugin a server owner can rule on. */
    public enum Surface {

        /** Opening the Alloy Codex, from {@code /mvtink codex} or the book button in the crucible. */
        CODEX("codex", "multiversetinker.codex", Mode.PUBLIC,
                "<red>You do not have permission to open the Alloy Codex.</red>"),

        /** The multiblock Forge, its GUI, the Alloy Crucible and the casting cauldron. */
        FORGE("forge", "multiversetinker.forge", Mode.PUBLIC,
                "<red>You do not have permission to use the Forge and the Alloy Crucible.</red>"),

        /** Brushing a valid geological block for minerals. */
        ARCHAEOLOGY("archaeology", "multiversetinker.archaeology", Mode.PUBLIC,
                "<red>You do not have permission to perform geological archaeology.</red>"),

        /**
         * The administrative {@code /mvtink} subcommands, and aiming the codex at another player.
         *
         * <p>The one surface a server cannot open: {@code /mvtink codex} is the only command a player
         * is meant to have, and everything else stays behind the node. See {@link #isConfigurable()}.</p>
         */
        ADMIN_COMMANDS("admin-commands", "multiversetinker.admin", Mode.PERMISSION,
                "<red>You do not have permission to execute this command.</red>");

        private final String key;
        private final String permission;
        private final Mode defaultMode;
        private final String defaultMessage;

        Surface(@Nonnull String key, @Nonnull String permission, @Nonnull Mode defaultMode,
                @Nonnull String defaultMessage) {
            this.key = key;
            this.permission = permission;
            this.defaultMode = defaultMode;
            this.defaultMessage = defaultMessage;
        }

        /** Path of the mode in {@code config.yml}. */
        @Nonnull
        public String configKey() {
            return "access." + key;
        }

        /** Path of the refusal message in {@code config.yml}. */
        @Nonnull
        public String messageKey() {
            return "messages.access-denied." + key;
        }

        /** Node that decides this surface while it is configured as {@link Mode#PERMISSION}. */
        @Nonnull
        public String permission() {
            return permission;
        }

        @Nonnull
        public Mode defaultMode() {
            return defaultMode;
        }

        @Nonnull
        public String defaultMessage() {
            return defaultMessage;
        }

        /** How this surface is named in logs and documentation. */
        @Nonnull
        public String getKey() {
            return key;
        }

        /**
         * Whether {@code config.yml} may rule on this surface.
         *
         * <p>Every surface is configurable except {@link #ADMIN_COMMANDS}, which is always
         * {@link Mode#PERMISSION}: the codex is the only command players are meant to have, so the way
         * to hand a player the rest is a permissions plugin granting {@code multiversetinker.admin},
         * never a config value. A config that still carries the old key is ignored, not obeyed.</p>
         */
        public boolean isConfigurable() {
            return this != ADMIN_COMMANDS;
        }
    }

    /** Who a surface answers to. */
    public enum Mode {

        /** Every player, without ever asking a permissions plugin. */
        PUBLIC,

        /** The permission node declared in {@code plugin.yml}. */
        PERMISSION,

        /** Server operators only, for nodes a permissions plugin should stay out of. */
        OP;

        /**
         * Reads a mode from config, accepting the synonyms a server owner is likely to write and
         * falling back (never throwing) on anything unrecognised, so a typo cannot lock a server out
         * of its own forge.
         */
        @Nonnull
        public static Mode parse(@Nullable String raw, @Nonnull Mode fallback) {
            if (raw == null) return fallback;

            String value = raw.trim().toUpperCase(Locale.ROOT);
            for (Mode mode : values()) {
                if (mode.name().equals(value)) return mode;
            }
            return switch (value) {
                case "EVERYONE", "ALL", "TRUE", "YES" -> PUBLIC;
                case "OPS", "OPERATOR", "OPERATORS", "ADMIN", "ADMINS" -> OP;
                case "PERM", "PERMS", "NODE" -> PERMISSION;
                default -> fallback;
            };
        }

        /** How the mode is spelled in {@code config.yml}. */
        @Nonnull
        public String configName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    /** How much an advisory matters. */
    public enum Severity {

        /** The rule could hand players something only an administrator should have. */
        DANGEROUS,

        /** The rule cannot be satisfied on this server, so the surface is dead. */
        UNUSABLE
    }

    /**
     * A rule worth saying out loud when the access settings are applied.
     *
     * <p>A server can close a door and forget it, so a rule that promises players the administrative
     * commands, or that nobody on this server can satisfy, is reported on startup and on
     * {@code /mvtink reload} instead of being left for a player to stumble into.</p>
     *
     * @param severity  how much it matters
     * @param configKey the {@code config.yml} key responsible, so the report points at the line to edit
     * @param message   what is wrong and what to do about it
     */
    public record Advisory(@Nonnull Severity severity, @Nonnull String configKey, @Nonnull String message) {
    }

    private static final EnumMap<Surface, Mode> MODES = new EnumMap<>(Surface.class);
    private static final EnumMap<Surface, String> MESSAGES = new EnumMap<>(Surface.class);

    static {
        reset();
    }

    private AccessControl() {
    }

    /**
     * Applies the {@code access} and {@code messages.access-denied} sections of {@code config.yml}.
     * Called on enable and again by {@code /mvtink reload}, so a server never has to restart.
     *
     * @return the config keys that were ignored because their surface is not configurable, so the
     *         caller can say so in the log instead of leaving a server wondering why nothing changed
     */
    @Nonnull
    public static List<String> configure(@Nullable ConfigurationSection config) {
        reset();
        if (config == null) return List.of();

        List<String> ignored = new ArrayList<>();
        for (Surface surface : Surface.values()) {
            if (surface.isConfigurable()) {
                MODES.put(surface, Mode.parse(config.getString(surface.configKey()), surface.defaultMode()));
            } else if (config.isSet(surface.configKey())) {
                ignored.add(surface.configKey());
            }
            MESSAGES.put(surface, config.getString(surface.messageKey(), surface.defaultMessage()));
        }
        return ignored;
    }

    /**
     * The rules worth a warning: one that would hand players the administrative commands, and one that
     * nobody on this server can satisfy.
     *
     * <p>Call after {@link #configure(ConfigurationSection)}, whose modes this reads. A server with no
     * operator at all can never reach an {@link Mode#OP} surface, and a leftover
     * {@code access.admin-commands: public} is exactly the value that used to hand every player the item
     * giver and the instant forger — neither is left for a player to discover, both are logged.</p>
     *
     * @param config             the section the modes were read from, so a stale key can be spotted
     * @param serverHasOperators whether any operator exists to satisfy an {@link Mode#OP} surface
     */
    @Nonnull
    public static List<Advisory> advisories(@Nullable ConfigurationSection config, boolean serverHasOperators) {
        List<Advisory> advisories = new ArrayList<>();

        // A key from before the administrative commands stopped being configurable. Left at any other
        // value it does nothing at all; left at "public" it is the dangerous one, so that is said plainly.
        if (config != null) {
            for (Surface surface : Surface.values()) {
                if (surface.isConfigurable() || !config.isSet(surface.configKey())) continue;
                if (Mode.parse(config.getString(surface.configKey()), surface.defaultMode()) != Mode.PUBLIC) continue;

                advisories.add(new Advisory(Severity.DANGEROUS, surface.configKey(),
                        "config.yml still asks for " + surface.configKey() + ": public, which would give every"
                                + " player the administrative /mvtink subcommands. It is ignored: they always require "
                                + surface.permission() + " (operators by default, or whoever a permissions plugin"
                                + " grants it to). Delete the key to silence this."));
            }
        }

        if (serverHasOperators) return advisories;

        // With no operator anywhere, an op-only surface is closed to everyone in practice.
        for (Surface surface : Surface.values()) {
            if (mode(surface) != Mode.OP) continue;

            advisories.add(new Advisory(Severity.UNUSABLE, surface.configKey(),
                    surface.configKey() + " is \"op\" and this server has no operators, so nobody can use "
                            + describe(surface) + ". Give someone the operator flag, or set the mode to public."));
        }
        return advisories;
    }

    /** How a surface is named in a warning, so the message says what a server has just closed. */
    @Nonnull
    private static String describe(@Nonnull Surface surface) {
        return switch (surface) {
            case CODEX -> "the Alloy Codex, the only /mvtink command players have";
            case FORGE -> "the Forge, the Alloy Crucible and the casting cauldron";
            case ARCHAEOLOGY -> "the brush";
            case ADMIN_COMMANDS -> "the administrative /mvtink subcommands";
        };
    }

    /** Restores the shipped defaults, used before any config is read and by the tests. */
    public static void reset() {
        for (Surface surface : Surface.values()) {
            MODES.put(surface, surface.defaultMode());
            MESSAGES.put(surface, surface.defaultMessage());
        }
    }

    @Nonnull
    public static Mode mode(@Nonnull Surface surface) {
        return MODES.getOrDefault(surface, surface.defaultMode());
    }

    /** True while the surface is open to every player, whatever their permissions. */
    public static boolean isPublic(@Nonnull Surface surface) {
        return mode(surface) == Mode.PUBLIC;
    }

    /**
     * Whether this sender may use the surface under the configured mode. Console is always allowed:
     * a mode is about players, and the server console already has full access to plugin.yml.
     *
     * <p>An administrative surface always answers to its node, because {@link Surface#isConfigurable()}
     * keeps a config from ever changing its mode: {@code /mvtink codex} is the one command players get,
     * and {@code craft}, {@code give}, {@code verify} and {@code reload} stay behind
     * {@code multiversetinker.admin} no matter what the file says.</p>
     */
    public static boolean allows(@Nonnull CommandSender sender, @Nonnull Surface surface) {
        return switch (mode(surface)) {
            case PUBLIC -> true;
            case OP -> sender.isOp();
            case PERMISSION -> sender.hasPermission(surface.permission());
        };
    }

    /** The refusal a denied player sees, in their own server's wording. */
    @Nonnull
    public static Component denial(@Nonnull Surface surface) {
        return MINI_MESSAGE.deserialize(MESSAGES.getOrDefault(surface, surface.defaultMessage()));
    }

    /** One-line report of every surface, for the log and {@code /mvtink verify}. */
    @Nonnull
    public static String summary() {
        StringJoiner joiner = new StringJoiner(" · ");
        for (Surface surface : Surface.values()) {
            joiner.add(surface.getKey() + ": " + mode(surface).configName());
        }
        return joiner.toString();
    }
}
