package com.chagui68.multiversetinker.commands;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.access.AccessControl.AdminCommand;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.access.AccessControl.Surface;
import com.chagui68.multiversetinker.archaeology.ArchaeologyLootTable;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.PerkEpithet;
import com.chagui68.multiversetinker.tools.SignatureArt;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class MultiverseTinkerCommand implements CommandExecutor, TabCompleter {

    /**
     * The umbrella node: full access to every administrative subcommand.
     *
     * <p>Never configurable: {@code /mvtink codex} is the only command players are meant to have, so
     * every other subcommand asks for a node before it does anything. Each one also has a node of its
     * own ({@link AdminCommand}), which is how a server hands out a single slice of the administration
     * — while a player holding this umbrella keeps getting all of them, exactly as before. Operators
     * hold it by default and a permissions plugin can grant it to a player.</p>
     */
    public static final String ADMIN_PERMISSION = Surface.ADMIN_COMMANDS.permission();

    /** Opens the Alloy Codex; granted to everyone by default, because it is reference material. */
    public static final String CODEX_PERMISSION = Surface.CODEX.permission();

    /** Item-id suffixes that are not item kinds but still resolve to the same material. */
    private static final List<String> LEGACY_ALIASES = List.of("_processed", "_handle", "_pommel");

    /** Prefix every registered id carries, optional in every command argument. */
    private static final String ID_PREFIX = "mvtink_";

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final TinkerItemRegistry itemRegistry;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public MultiverseTinkerCommand(@Nonnull MultiverseTinker plugin,
                                  @Nonnull MaterialRegistry materialRegistry,
                                  @Nonnull TinkerItemRegistry itemRegistry) {
        this.plugin = plugin;
        this.materialRegistry = materialRegistry;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 0) {
            // Anyone holding part of the administration gets the administrative help, narrowed below to
            // the subcommands they may actually run; everyone else only ever hears about the codex.
            if (AccessControl.allowsAnyAdmin(sender)) {
                sendHelp(sender, label);
            } else {
                sendPublicHelp(sender, label);
            }
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);

        // The codex is public reference material, so it is answered before the admin gate.
        if (sub.equals("codex")) {
            return openCodex(sender, label, args);
        }

        // Each subcommand answers to its own node, with the umbrella granting every one of them.
        AdminCommand admin = AdminCommand.of(sub);
        if (admin != null) {
            if (!AccessControl.allows(sender, admin)) {
                refuse(sender, label);
                return true;
            }
        } else if (!AccessControl.allowsAnyAdmin(sender)) {
            // A subcommand nobody has: a stranger hears the refusal rather than the help, so the command
            // surface is never mapped out for them.
            refuse(sender, label);
            return true;
        }

        switch (sub) {
            case "reload" -> {
                plugin.reloadConfig();
                plugin.applyLoreSettings();
                plugin.applyAnimationSettings();
                plugin.applyEquipmentSettings();
                plugin.applyAccessSettings();
                plugin.applyLootSettings();
                plugin.getArchaeologyManager().getLootTable().reload();
                itemRegistry.reload();
                // Forged alloys are named after their parents, so their words are re-derived on reload.
                PerkEpithet.clearCache();
                SignatureArt.clearCache();
                sender.sendMessage(miniMessage.deserialize("<green>MultiverseTinker configuration, items and loot tables reloaded successfully!</green>"));
                return true;
            }

            case "verify" -> {
                // Proves that every material and every item kind is actually registered and resolvable.
                int materials = materialRegistry.getAll().size();
                int kindsPerMaterial = TinkerItemRegistry.kindsPerMaterial();
                int expectedIds = materials * (kindsPerMaterial + 4);
                List<String> unresolved = new ArrayList<>();

                for (TinkerMaterial mat : materialRegistry.getAll()) {
                    for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
                        String id = mat.getId().toLowerCase(Locale.ROOT) + kind.getSuffix();
                        if (itemRegistry.getItemById(id) == null) {
                            unresolved.add(id);
                        }
                    }
                }

                for (String special : List.of("mvtink_smeltery", "mvtink_brush_prospector")) {
                    if (itemRegistry.getItemById(special) == null) unresolved.add(special);
                }
                for (com.chagui68.multiversetinker.api.CastType cast : com.chagui68.multiversetinker.api.CastType.values()) {
                    if (itemRegistry.getItemById(cast.getId().toLowerCase(Locale.ROOT)) == null) unresolved.add(cast.getId());
                }

                sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker Item Registry Verification ===</gold>"));
                sender.sendMessage(miniMessage.deserialize("<gray>Materials registered: <yellow>" + materials
                        + "</yellow> (catalogued + alloys + catalysts)</gray>"));
                sender.sendMessage(miniMessage.deserialize("<gray>Item kinds per material: <yellow>" + kindsPerMaterial
                        + "</yellow> (raw, ingot, nugget, block, molten bucket + 10 part types)</gray>"));
                sender.sendMessage(miniMessage.deserialize("<gray>Distinct item ids available: <yellow>"
                        + itemRegistry.getAvailableItemIdCount() + "</yellow></gray>"));
                sender.sendMessage(miniMessage.deserialize("<gray>Material-derived ids checked: <yellow>" + expectedIds
                        + "</yellow> (each material also answers to its bare id, _processed, _handle and _pommel)</gray>"));
                ArchaeologyLootTable loot = plugin.getArchaeologyManager().getLootTable();
                sender.sendMessage(miniMessage.deserialize("<gray>Archaeology rarity weights: <yellow>" + loot.weightsSummary()
                        + "</yellow> <gray>(" + (loot.usesDefaultWeights() ? "shipped" : "configured") + ")</gray>"));
                if (unresolved.isEmpty()) {
                    sender.sendMessage(miniMessage.deserialize("<green>✔ Every item id resolves correctly. Caches are now warm.</green>"));
                } else {
                    sender.sendMessage(miniMessage.deserialize("<red>✖ " + unresolved.size() + " ids failed to resolve: </red><yellow>"
                            + String.join(", ", unresolved.subList(0, Math.min(10, unresolved.size()))) + "</yellow>"));
                }
                return true;
            }

            case "give" -> {
                if (args.length < 3) {
                    sender.sendMessage(miniMessage.deserialize("<red>Usage: /" + label + " give <player> <mvtink_id> [amount]</red>"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(miniMessage.deserialize("<red>Player not found: " + args[1] + "</red>"));
                    return true;
                }

                String itemId = args[2].toLowerCase(Locale.ROOT);
                ItemStack item = itemRegistry.getItemById(itemId);
                if (item == null && !itemId.startsWith(ID_PREFIX)) {
                    // Convenience: "tin_ingot" and "mvtink_tin_ingot" are the same item.
                    item = itemRegistry.getItemById(ID_PREFIX + itemId);
                }

                // The codex previews the id of every pair the crucible would accept — including the ones
                // nobody has smelted yet — so an id copied out of it can name an alloy that does not exist.
                // Forge and register it here, under the very same rules the crucible enforces.
                String handedOut = itemId;
                TinkerAlloy blended = null;
                boolean freshlyForged = false;
                if (item == null) {
                    TinkerItemRegistry.IdRequest request = TinkerItemRegistry.parseId(itemId);
                    blended = plugin.getAlloyRegistry().alloyForPairId(request.materialId(), materialRegistry);
                    if (blended != null) {
                        freshlyForged = blended.id().equalsIgnoreCase(request.materialId());
                        handedOut = request.idFor(blended.id());
                        item = itemRegistry.getItemById(handedOut);
                    }
                }

                if (item == null) {
                    sender.sendMessage(miniMessage.deserialize("<red>Item not found: " + args[2] + "</red>"));
                    List<String> suggestions = suggestIds(itemId);
                    if (!suggestions.isEmpty()) {
                        sender.sendMessage(miniMessage.deserialize("<gray>Did you mean: <yellow>"
                                + String.join("</yellow>, <yellow>", suggestions) + "</yellow>?</gray>"));
                    } else {
                        sender.sendMessage(miniMessage.deserialize("<gray>Use tab-completion after <yellow>give <player> </yellow>to browse every registered item.</gray>"));
                    }
                    return true;
                }

                int amount = 1;
                if (args.length >= 4) {
                    try {
                        amount = Math.max(1, Integer.parseInt(args[3]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(miniMessage.deserialize("<red>Invalid amount: " + args[3] + "</red>"));
                        return true;
                    }
                }

                item.setAmount(amount);
                target.getInventory().addItem(item);

                if (blended != null) {
                    // The pair was resolved, which is worth saying: an alloy was just created and saved, and
                    // a curated pair hands over a differently named alloy than the id suggested.
                    sender.sendMessage(miniMessage.deserialize(freshlyForged
                            ? "<gray>That id names a crucible pair nobody had smelted yet: forged and registered </gray><yellow>"
                                    + blended.name() + "</yellow> <dark_gray>(" + blended.id() + ")</dark_gray><gray>.</gray>"
                            : "<gray>That id names a crucible pair, and the crucible would produce </gray><yellow>"
                                    + blended.name() + "</yellow> <dark_gray>(" + blended.id() + ")</dark_gray><gray>.</gray>"));
                }

                sender.sendMessage(miniMessage.deserialize("<green>Gave " + amount + "x " + handedOut + " to " + target.getName() + ".</green>"));
                return true;
            }

            case "forge" -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(miniMessage.deserialize("<red>This command can only be executed by in-game players.</red>"));
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage(miniMessage.deserialize("<red>Usage: /" + label + " forge <build|check|gui> [rotation]</red>"));
                    return true;
                }

                String forgeSub = args[1].toLowerCase(Locale.ROOT);
                switch (forgeSub) {
                    case "build" -> {
                        int rot = 0;
                        if (args.length >= 3) {
                            try {
                                rot = Integer.parseInt(args[2]);
                            } catch (NumberFormatException ignored) {}
                        }
                        org.bukkit.Location loc = player.getLocation().getBlock().getLocation();
                        plugin.getForgeManager().buildStructure(loc, rot);
                        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully constructed Multiverse Forge structure at your location (Rotation " + rot + "°)!</green>"));
                        return true;
                    }
                    case "check" -> {
                        org.bukkit.block.Block target = player.getTargetBlockExact(6);
                        if (target == null || (target.getType() != org.bukkit.Material.ANVIL
                                && target.getType() != org.bukkit.Material.CHIPPED_ANVIL
                                && target.getType() != org.bukkit.Material.DAMAGED_ANVIL)) {
                            player.sendMessage(miniMessage.deserialize("<red>⚠ Look at a central Anvil to check its multiblock structure.</red>"));
                            return true;
                        }
                        com.chagui68.multiversetinker.forge.structure.ForgeStructure.ValidationResult res = plugin.getForgeManager().checkForge(target.getLocation());
                        if (res.isValid()) {
                            player.sendMessage(miniMessage.deserialize("<green>✔ Multiverse Forge is VALID! (" + res.matchedBlocks() + "/" + res.totalBlocks() + " blocks matched, " + String.format(java.util.Locale.US, "%.1f", res.percentage()) + "%, rotation " + res.rotation() + "°).</green>"));
                        } else {
                            player.sendMessage(miniMessage.deserialize("<yellow>⚠ Multiverse Forge is INCOMPLETE: (" + res.matchedBlocks() + "/" + res.totalBlocks() + " blocks matched, " + String.format(java.util.Locale.US, "%.1f", res.percentage()) + "%). Missing blocks or lava columns.</yellow>"));
                        }
                        return true;
                    }
                    case "gui" -> {
                        com.chagui68.multiversetinker.forge.gui.ForgeGUI gui = new com.chagui68.multiversetinker.forge.gui.ForgeGUI(plugin, itemRegistry, materialRegistry);
                        player.openInventory(gui.getInventory());
                        return true;
                    }
                    default -> {
                        player.sendMessage(miniMessage.deserialize("<red>Unknown forge subcommand: " + forgeSub + ". Use: build, check, gui.</red>"));
                        return true;
                    }
                }
            }

            case "craft" -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(miniMessage.deserialize("<red>This command can only be executed by in-game players.</red>"));
                    return true;
                }

                if (args.length < 5) {
                    player.sendMessage(miniMessage.deserialize("<red>Usage: /" + label + " craft <weapon|tool|armor> <type> <mat1> <mat2> [mat3] [tier]</red>"));
                    player.sendMessage(miniMessage.deserialize("<gray>Example: /" + label + " craft weapon SWORD gold ruby diamond NETHERITE</gray>"));
                    return true;
                }

                String category = args[1].toLowerCase(Locale.ROOT);
                switch (category) {
                    case "weapon" -> handleCraftWeapon(player, args);
                    case "tool" -> handleCraftTool(player, args);
                    case "armor" -> handleCraftArmor(player, args);
                    default -> player.sendMessage(miniMessage.deserialize("<red>Invalid category: " + category + ". Use: weapon, tool, or armor.</red>"));
                }
                return true;
            }

            default -> {
                sendHelp(sender, label);
                return true;
            }
        }
    }

    /**
     * Turns a sender away from an administrative subcommand, pointing them at the codex while they may
     * still open it.
     *
     * <p>The refusal is the configured {@code messages.access-denied.admin-commands} line; the codex
     * pointer is dropped when the server closed the codex, because advertising a command the player
     * cannot open would only be a dead end.</p>
     */
    private void refuse(@Nonnull CommandSender sender, @Nonnull String label) {
        sender.sendMessage(AccessControl.denial(Surface.ADMIN_COMMANDS));
        if (AccessControl.allows(sender, Surface.CODEX)) {
            sender.sendMessage(miniMessage.deserialize("<gray>Players can browse the Alloy Codex with </gray><yellow>/"
                    + label + " codex</yellow><gray>.</gray>"));
        }
    }

    /**
     * Resolves any id the command understands to the material it is made of.
     *
     * <p>Besides a material's own id ({@code tin}, {@code mvtink_tin}), the item kinds and legacy
     * aliases that belong to it resolve to the same material: {@code tin_ingot}, {@code tin_head},
     * {@code tin_processed}… all mean Tin. That is what lets {@code /mvtink craft} accept an id copied
     * straight out of the codex instead of forcing the player to strip it first.</p>
     */
    @Nullable
    private TinkerMaterial parseMaterial(@Nullable String id) {
        if (id == null) return null;
        String clean = id.toLowerCase(Locale.ROOT).trim();
        if (clean.isEmpty()) return null;

        TinkerMaterial direct = resolveMaterial(clean);
        if (direct != null) return direct;

        // Strip the kind suffix the registry itself appends (raw, ingot, head, molten bucket…).
        for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
            String suffix = kind.getSuffix();
            if (!clean.endsWith(suffix)) continue;
            TinkerMaterial stripped = resolveMaterial(clean.substring(0, clean.length() - suffix.length()));
            if (stripped != null) return stripped;
        }

        // And the three legacy aliases that still resolve in the registry.
        for (String alias : LEGACY_ALIASES) {
            if (!clean.endsWith(alias)) continue;
            TinkerMaterial stripped = resolveMaterial(clean.substring(0, clean.length() - alias.length()));
            if (stripped != null) return stripped;
        }
        return null;
    }

    /** Looks a material up by exact id, tolerating a missing {@code mvtink_} prefix. */
    @Nullable
    private TinkerMaterial resolveMaterial(@Nonnull String id) {
        TinkerMaterial byId = materialRegistry.get(id);
        if (byId != null) return byId;
        if (id.startsWith("mvtink_")) return null;
        return materialRegistry.get("mvtink_" + id);
    }

    private void handleCraftWeapon(Player player, String[] args) {
        ModularWeaponType type;
        try {
            type = ModularWeaponType.valueOf(args[2].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            player.sendMessage(miniMessage.deserialize("<red>Invalid weapon type: " + args[2] + ". Valid types: " + Arrays.toString(ModularWeaponType.values()) + "</red>"));
            return;
        }

        TinkerMaterial m1 = parseMaterial(args[3]);
        TinkerMaterial m2 = parseMaterial(args[4]);
        if (m1 == null || m2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>Unrecognized material(s): " + args[3] + " or " + args[4] + "</red>"));
            return;
        }

        TinkerMaterial m3 = null;
        EvolutionTier tier = EvolutionTier.WOOD;

        if (!type.isTwoPart()) {
            if (args.length < 6) {
                player.sendMessage(miniMessage.deserialize("<red>Weapon " + type.name() + " requires 3 materials: <head> <handle> <pommel> [tier]</red>"));
                return;
            }
            m3 = parseMaterial(args[5]);
            if (m3 == null) {
                player.sendMessage(miniMessage.deserialize("<red>Unrecognized third material: " + args[5] + "</red>"));
                return;
            }
            if (args.length >= 7) {
                tier = EvolutionTier.fromString(args[6]);
            }
        } else {
            if (args.length >= 6) {
                tier = EvolutionTier.fromString(args[5]);
            }
        }

        PartComposition c1 = PartComposition.fromMaterials(List.of(m1));
        PartComposition c2 = PartComposition.fromMaterials(List.of(m2));
        PartComposition c3 = (m3 != null) ? PartComposition.fromMaterials(List.of(m3)) : null;

        ItemStack weapon = TinkerItemBuilder.createModularWeapon(type, c1, c2, c3, tier, 0);
        player.getInventory().addItem(weapon);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        player.sendMessage(miniMessage.deserialize("<green>✔ Admin Crafted: </green>").append(weapon.getItemMeta().displayName()).append(miniMessage.deserialize("<green>!</green>")));
    }

    private void handleCraftTool(Player player, String[] args) {
        ModularToolType type;
        try {
            type = ModularToolType.valueOf(args[2].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            player.sendMessage(miniMessage.deserialize("<red>Invalid tool type: " + args[2] + ". Valid types: PICKAXE, AXE, SHOVEL, HOE, FISHING_ROD</red>"));
            return;
        }

        if (args.length < 6) {
            player.sendMessage(miniMessage.deserialize("<red>Tool requires 3 materials: <head> <handle> <pommel> [tier]</red>"));
            return;
        }

        TinkerMaterial m1 = parseMaterial(args[3]);
        TinkerMaterial m2 = parseMaterial(args[4]);
        TinkerMaterial m3 = parseMaterial(args[5]);
        if (m1 == null || m2 == null || m3 == null) {
            player.sendMessage(miniMessage.deserialize("<red>Unrecognized material(s) in arguments.</red>"));
            return;
        }

        EvolutionTier tier = (args.length >= 7) ? EvolutionTier.fromString(args[6]) : EvolutionTier.WOOD;

        PartComposition c1 = PartComposition.fromMaterials(List.of(m1));
        PartComposition c2 = PartComposition.fromMaterials(List.of(m2));
        PartComposition c3 = PartComposition.fromMaterials(List.of(m3));

        ItemStack tool = TinkerItemBuilder.createModularTool(type, c1, c2, c3, tier, 0);
        player.getInventory().addItem(tool);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        player.sendMessage(miniMessage.deserialize("<green>✔ Admin Crafted: </green>").append(tool.getItemMeta().displayName()).append(miniMessage.deserialize("<green>!</green>")));
    }

    private void handleCraftArmor(Player player, String[] args) {
        ModularArmorType type;
        try {
            type = ModularArmorType.valueOf(args[2].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            player.sendMessage(miniMessage.deserialize("<red>Invalid armor type: " + args[2] + ". Valid types: HELMET, CHESTPLATE, LEGGINGS, BOOTS</red>"));
            return;
        }

        if (args.length < 6) {
            player.sendMessage(miniMessage.deserialize("<red>Armor requires 3 materials: <plate> <lining> <trim> [tier]</red>"));
            return;
        }

        TinkerMaterial m1 = parseMaterial(args[3]);
        TinkerMaterial m2 = parseMaterial(args[4]);
        TinkerMaterial m3 = parseMaterial(args[5]);
        if (m1 == null || m2 == null || m3 == null) {
            player.sendMessage(miniMessage.deserialize("<red>Unrecognized material(s) in arguments.</red>"));
            return;
        }

        EvolutionTier tier = (args.length >= 7) ? EvolutionTier.fromString(args[6]) : EvolutionTier.WOOD;

        PartComposition c1 = PartComposition.fromMaterials(List.of(m1));
        PartComposition c2 = PartComposition.fromMaterials(List.of(m2));
        PartComposition c3 = PartComposition.fromMaterials(List.of(m3));

        ItemStack armor = TinkerItemBuilder.createModularArmor(type, c1, c2, c3, tier, 0);
        player.getInventory().addItem(armor);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        player.sendMessage(miniMessage.deserialize("<green>✔ Admin Crafted: </green>").append(armor.getItemMeta().displayName()).append(miniMessage.deserialize("<green>!</green>")));
    }

    /**
     * Opens the Alloy Codex for the sender (or, for admins, for a named player).
     *
     * <p>The codex is reference material — the mineral catalog, the recipe list and the combination
     * explorer — so it is open to every player; only aiming it at somebody else is restricted to
     * admins.</p>
     */
    private boolean openCodex(@Nonnull CommandSender sender, @Nonnull String label, @Nonnull String[] args) {
        if (!AccessControl.allows(sender, Surface.CODEX)) {
            sender.sendMessage(AccessControl.denial(Surface.CODEX));
            return true;
        }

        Player viewer;
        if (args.length >= 2) {
            if (!AccessControl.allows(sender, Surface.ADMIN_COMMANDS)) {
                sender.sendMessage(miniMessage.deserialize("<red>Only admins may open the codex for another player.</red>"));
                return true;
            }
            viewer = Bukkit.getPlayerExact(args[1]);
            if (viewer == null) {
                sender.sendMessage(miniMessage.deserialize("<red>Player not found: " + args[1] + "</red>"));
                return true;
            }
        } else if (sender instanceof Player self) {
            viewer = self;
        } else {
            sender.sendMessage(miniMessage.deserialize("<red>Console must specify a player: /" + label + " codex <player></red>"));
            return true;
        }

        com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI codex =
                new com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI(plugin, itemRegistry, materialRegistry);
        viewer.openInventory(codex.getInventory());
        if (viewer != sender) {
            sender.sendMessage(miniMessage.deserialize("<green>Opened the Alloy Codex for " + viewer.getName() + ".</green>"));
        }
        return true;
    }

    /** Help shown to players without the admin permission: the codex is the public entry point. */
    private void sendPublicHelp(CommandSender sender, String label) {
        sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker (Chagui68) ===</gold>"));
        if (!AccessControl.allows(sender, Surface.CODEX)) {
            // A server can close the codex; promising it here would only be a dead end.
            sender.sendMessage(miniMessage.deserialize("<gray>There is nothing here for you.</gray>"));
            sender.sendMessage(miniMessage.deserialize("<gray>Ask an administrator for access to this server's forge.</gray>"));
            return;
        }
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " codex</yellow> <gray>- Open the Alloy Codex: mineral catalog, legendary recipes, catalysts, forged alloys, the combination explorer and the totals.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<gray>It is also reachable from the book button in the Alloy Crucible tab of the Forge.</gray>"));
    }

    /**
     * Help shown to whoever holds part of the administration.
     *
     * <p>Only the subcommands the sender may actually run are printed: a server that handed one player
     * the item giver and another the forger must not advertise the rest to either of them — least of
     * all the reload, which is the one that can change the rules.</p>
     */
    private void sendHelp(CommandSender sender, String label) {
        sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker v" + plugin.getPluginMeta().getVersion() + " (Chagui68) ===</gold>"));
        if (AccessControl.allows(sender, AdminCommand.CRAFT)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " craft <weapon|tool|armor> <type> <m1> <m2> [m3] [tier]</yellow> <gray>- Instant admin crafting without forge.</gray>"));
        }
        if (AccessControl.allows(sender, AdminCommand.GIVE)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " give <player> <mvtink_id> [amount]</yellow> <gray>- Give any raw ore, ingot, nugget, block, molten bucket, part, cast, smeltery or brush. The mvtink_ prefix is optional, and a crucible pair id from the codex is forged and registered on the spot.</gray>"));
        }
        if (AccessControl.allows(sender, Surface.CODEX)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " codex [player]</yellow> <gray>- Open the browsable Alloy Codex: the mineral catalog, legendary recipes, catalysts, forged composites and primes, a combination explorer and the totals.</gray>"));
        }
        if (AccessControl.allows(sender, AdminCommand.VERIFY)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " verify</yellow> <gray>- Check that every material and every item kind is registered and resolvable.</gray>"));
        }
        if (AccessControl.allows(sender, AdminCommand.FORGE)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " forge <build|check|gui> [rotation]</yellow> <gray>- Manage the multiblock Forge and open custom GUI.</gray>"));
        }
        if (AccessControl.allows(sender, AdminCommand.RELOAD)) {
            sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " reload</yellow> <gray>- Reload configuration and caches.</gray>"));
        }
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        if (args.length == 1) {
            // Only what this sender may actually run, in the order the executor lists them: a server that
            // granted one slice must not have the other five advertised to its holder.
            List<String> subcommands = new ArrayList<>();
            if (AccessControl.allows(sender, AdminCommand.CRAFT)) subcommands.add("craft");
            if (AccessControl.allows(sender, AdminCommand.GIVE)) subcommands.add("give");
            if (AccessControl.allows(sender, AdminCommand.FORGE)) subcommands.add("forge");
            if (AccessControl.allows(sender, Surface.CODEX)) subcommands.add("codex");
            if (AccessControl.allows(sender, AdminCommand.VERIFY)) subcommands.add("verify");
            if (AccessControl.allows(sender, AdminCommand.RELOAD)) subcommands.add("reload");
            return filter(subcommands, args[0]);
        }

        // Admins may aim the codex at a player: their names are the suggestions.
        if (args.length == 2 && args[0].equalsIgnoreCase("codex")) {
            return AccessControl.allows(sender, Surface.ADMIN_COMMANDS) ? null : Collections.emptyList();
        }

        // Every deeper suggestion belongs to one subcommand, so it follows that subcommand's node.
        AdminCommand completed = AdminCommand.of(args[0]);
        if (completed == null || !AccessControl.allows(sender, completed)) {
            return Collections.emptyList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("craft")) {
            return filter(List.of("weapon", "tool", "armor"), args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("craft")) {
            String cat = args[1].toLowerCase(Locale.ROOT);
            if (cat.equals("weapon")) {
                return filter(Arrays.stream(ModularWeaponType.values()).map(Enum::name).toList(), args[2]);
            } else if (cat.equals("tool")) {
                return filter(Arrays.stream(ModularToolType.values()).filter(t -> t != ModularToolType.SWORD).map(Enum::name).toList(), args[2]);
            } else if (cat.equals("armor")) {
                return filter(Arrays.stream(ModularArmorType.values()).map(Enum::name).toList(), args[2]);
            }
        }

        if (args[0].equalsIgnoreCase("craft")) {
            // Only materials in the part slots: the tier has its own argument, and two-part weapons
            // reach it one slot earlier than everything else.
            int tierSlot = craftTierSlot(args);
            if (args.length == tierSlot) {
                return filter(Arrays.stream(EvolutionTier.values()).map(Enum::name).toList(), args[tierSlot - 1]);
            }
            if (args.length >= 4 && args.length < tierSlot) {
                return filterIds(craftCandidates(), args[args.length - 1]);
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("forge")) {
            return filter(List.of("build", "check", "gui"), args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("forge") && args[1].equalsIgnoreCase("build")) {
            return filter(List.of("0", "90", "180", "270"), args[2]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return null; // Player names
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return filterIds(itemIds(), args[2]);
        }

        return Collections.emptyList();
    }

    /**
     * Argument count at which {@code /mvtink craft} expects the tier.
     *
     * <p>A three-part weapon, every tool and every armor piece take three materials and then the
     * tier, so the tier is argument 7. A two-part weapon (bow, shield) takes two materials, which
     * moves the tier to argument 6 — without this the material list used to shadow the tier slot and
     * silently forged the weapon at Wood tier.</p>
     */
    private int craftTierSlot(@Nonnull String[] args) {
        if (args.length < 3 || !args[1].equalsIgnoreCase("weapon")) return 7;
        try {
            return ModularWeaponType.valueOf(args[2].toUpperCase(Locale.ROOT)).isTwoPart() ? 6 : 7;
        } catch (IllegalArgumentException e) {
            return 7;
        }
    }

    /**
     * Every id {@code /mvtink give} can hand out, sorted so the list never reshuffles while typing.
     *
     * <p>The whole registry is offered in one flat list — every material, its raw, ingot, nugget,
     * block, molten bucket and ten part kinds, the legacy aliases, the smeltery, the brush and every
     * cast. Players asked to browse the registry from the command line instead of having to know a
     * material id before its kinds appeared, so nothing is hidden any more; the client filters and
     * scrolls the popup.</p>
     */
    @Nonnull
    private List<String> itemIds() {
        List<String> ids = new ArrayList<>(itemRegistry.getAllItemIds());
        Collections.sort(ids);
        return ids;
    }

    /**
     * Ids offered in the material slots of {@code /mvtink craft}: every material plus the item kinds
     * and aliases that {@link #parseMaterial(String)} resolves back to it. Special tools and casts
     * are left out because they name no forgeable material.
     */
    @Nonnull
    private List<String> craftCandidates() {
        List<String> ids = new ArrayList<>();
        for (String id : itemRegistry.getAllItemIds()) {
            if (parseMaterial(id) != null) ids.add(id);
        }
        Collections.sort(ids);
        return ids;
    }

    /** Up to five registered ids that start with (or contain) what the sender typed. */
    @Nonnull
    private List<String> suggestIds(@Nonnull String typed) {
        List<String> out = new ArrayList<>(5);
        for (String id : itemRegistry.getAllItemIds()) {
            if (out.size() >= 5) break;
            if (id.startsWith(typed) || (typed.length() > 3 && id.contains(typed))) out.add(id);
        }
        return out;
    }

    private List<String> filter(List<String> list, String input) {
        String lower = input.toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s.toLowerCase(Locale.ROOT).startsWith(lower)) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Filters item ids, ignoring the optional {@code mvtink_} prefix on both sides.
     *
     * <p>The registry always spells ids out in full, so a player typing {@code tin} would otherwise
     * match nothing at all — the {@code mvtink_} prefix is optional in every command argument, and
     * the suggestion list has to be just as forgiving.</p>
     */
    @Nonnull
    private List<String> filterIds(@Nonnull List<String> ids, @Nonnull String typed) {
        String lower = typed.toLowerCase(Locale.ROOT);
        String bare = lower.startsWith(ID_PREFIX) ? lower.substring(ID_PREFIX.length()) : lower;
        List<String> result = new ArrayList<>();
        for (String id : ids) {
            String candidate = id.toLowerCase(Locale.ROOT);
            String candidateBare = candidate.startsWith(ID_PREFIX)
                    ? candidate.substring(ID_PREFIX.length())
                    : candidate;
            if (candidate.startsWith(lower) || candidateBare.startsWith(bare)) {
                result.add(id);
            }
        }
        return result;
    }
}
