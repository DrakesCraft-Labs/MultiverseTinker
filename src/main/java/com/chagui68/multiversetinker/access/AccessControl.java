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
 *
 * <p>The administration is the one thing a server can hand out in slices rather than whole: each
 * {@link AdminCommand} has a node of its own, and the umbrella {@link Surface#ADMIN_COMMANDS} still
 * grants every one of them. See {@link #allows(CommandSender, AdminCommand)}.</p>
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
         * is meant to have, and everything else stays behind a node. This is the <b>umbrella</b> —
         * holding it grants every {@link AdminCommand}, which is what a server that has always handed
         * out {@code multiversetinker.admin} keeps getting. A server that wants to hand out a single
         * slice grants that subcommand's node instead. See {@link #isConfigurable()}.</p>
         */
        ADMIN_COMMANDS("admin-commands", AdminCommand.UMBRELLA, Mode.PERMISSION,
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

    /**
     * The administrative {@code /mvtink} subcommands, each with a node of its own.
     *
     * <p>The administration used to be all or nothing: either a player could give items, forge
     * equipment, count the registry and reload the plugin, or none of it. A server that wants an event
     * host who may only hand out items, or a builder who may only assemble equipment, can now grant one
     * of these nodes without handing over the rest.</p>
     *
     * <p>The umbrella is {@link #UMBRELLA} — {@link Surface#ADMIN_COMMANDS}, which still grants every
     * one of them, so an existing server that has always granted {@code multiversetinker.admin} is
     * unaffected. The umbrella is honoured in {@link #allows(CommandSender, AdminCommand)} rather than
     * left to the permission backend's child inheritance, so the answer is the same whatever plugin is
     * deciding (or none at all).</p>
     */
    public enum AdminCommand {

        /** {@code /mvtink craft}: assemble a weapon, tool or armor piece straight into a hand. */
        CRAFT("craft"),

        /** {@code /mvtink give}: hand out any registered item, forging a crucible pair id on the way. */
        GIVE("give"),

        /** {@code /mvtink forge}: build, check or open the multiblock Forge. */
        FORGE("forge"),

        /** {@code /mvtink verify}: count the registry and prove every id resolves. */
        VERIFY("verify"),

        /** {@code /mvtink reload}: re-read the configuration, items and loot tables. */
        RELOAD("reload");

        /**
         * The node that grants every subcommand, and the one servers have always handed out.
         *
         * <p>A compile-time constant on purpose: it is the same literal {@link Surface#ADMIN_COMMANDS}
         * carries, so the two can never drift apart.</p>
         */
        public static final String UMBRELLA = "multiversetinker.admin";

        private final String node;

        AdminCommand(@Nonnull String node) {
            this.node = node;
        }

        /** How the subcommand is typed after {@code /mvtink}, and the last segment of its node. */
        @Nonnull
        public String label() {
            return node;
        }

        /** Node that grants this subcommand on its own. */
        @Nonnull
        public String permission() {
            return UMBRELLA + "." + node;
        }

        /** The subcommand this name names, or {@code null} when it is not an administrative one. */
        @Nullable
        public static AdminCommand of(@Nullable String sub) {
            if (sub == null) return null;
            String value = sub.trim().toLowerCase(Locale.ROOT);
            for (AdminCommand command : values()) {
                if (command.node.equals(value)) return command;
            }
            return null;
        }

        /** Every node, in declaration order, for the documentation and the tests. */
        @Nonnull
        public static List<String> permissions() {
            List<String> out = new ArrayList<>();
            for (AdminCommand command : values()) out.add(command.permission());
            return out;
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

    /** One-line report of the administrative nodes, for the log and the documentation. */
    @Nonnull
    public static String adminNodeSummary() {
        List<String> nodes = new ArrayList<>();
        for (AdminCommand command : AdminCommand.values()) {
            nodes.add(command.label() + " (" + command.permission() + ")");
        }
        return String.join(" · ", nodes);
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
     * and the administrative subcommands stay behind their nodes no matter what the file says.</p>
     */
    public static boolean allows(@Nonnull CommandSender sender, @Nonnull Surface surface) {
        return switch (mode(surface)) {
            case PUBLIC -> true;
            case OP -> sender.isOp();
            case PERMISSION -> sender.hasPermission(surface.permission());
        };
    }

    /**
     * Whether this sender may run one administrative subcommand.
     *
     * <p>Both the subcommand's own node and the umbrella are accepted, which is what lets a server
     * keep granting {@code multiversetinker.admin} exactly as before while another narrows a player down
     * to, say, {@code multiversetinker.admin.give}. Answering here rather than relying on the permission
     * backend's child inheritance keeps the behaviour identical under LuckPerms, under a plain
     * {@code permissions.yml} and under no permissions plugin at all.</p>
     */
    public static boolean allows(@Nonnull CommandSender sender, @Nonnull AdminCommand command) {
        return sender.hasPermission(command.permission()) || sender.hasPermission(AdminCommand.UMBRELLA);
    }

    /**
     * Whether the sender holds any part of the administration at all.
     *
     * <p>Used to decide what to print where the answer is not about one subcommand: whether to show the
     * administrative help, and whether a refusal should still point a stranger at the public codex.</p>
     */
    public static boolean allowsAnyAdmin(@Nonnull CommandSender sender) {
        if (allows(sender, Surface.ADMIN_COMMANDS)) return true;
        for (AdminCommand command : AdminCommand.values()) {
            if (sender.hasPermission(command.permission())) return true;
        }
        return false;
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
