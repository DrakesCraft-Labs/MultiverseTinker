package com.chagui68.multiversetinker.commands;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
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
import java.util.*;

public class MultiverseTinkerCommand implements CommandExecutor, TabCompleter {

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
        if (!sender.hasPermission("multiversetinker.admin")) {
            sender.sendMessage(miniMessage.deserialize("<red>You do not have permission to execute this command.</red>"));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender, label);
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);

        switch (sub) {
            case "reload" -> {
                plugin.reloadConfig();
                plugin.applyLoreSettings();
                plugin.getArchaeologyManager().getLootTable().reload();
                itemRegistry.reload();
                sender.sendMessage(miniMessage.deserialize("<green>MultiverseTinker configuration, items and loot tables reloaded successfully!</green>"));
                return true;
            }

            case "codex" -> {
                Player viewer;
                if (args.length >= 2) {
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
                if (unresolved.isEmpty()) {
                    sender.sendMessage(miniMessage.deserialize("<green>✔ Every item id resolves correctly. Caches are now warm.</green>"));
                } else {
                    sender.sendMessage(miniMessage.deserialize("<red>✖ " + unresolved.size() + " ids failed to resolve: </red><yellow>"
                            + String.join(", ", unresolved.subList(0, Math.min(10, unresolved.size()))) + "</yellow>"));
                }
                return true;
            }

            case "list" -> {
                MineralOrigin filter = null;
                if (args.length >= 2) {
                    try {
                        filter = MineralOrigin.valueOf(args[1].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker Materials (Chagui68) ===</gold>"));
                Collection<TinkerMaterial> list = filter != null
                        ? materialRegistry.getByOrigin(filter)
                        : materialRegistry.getAll();

                for (TinkerMaterial mat : list) {
                    String line = "<gray>• </gray><gradient:" + mat.getColorHex() + ":#ffffff>" + mat.getName() + "</gradient> "
                            + "<dark_gray>(" + mat.getId() + ")</dark_gray> "
                            + "<yellow>[" + mat.getOrigin().name() + "]</yellow> "
                            + "<aqua>Trait: " + mat.getTraitName() + "</aqua>";
                    sender.sendMessage(miniMessage.deserialize(line));
                }
                sender.sendMessage(miniMessage.deserialize("<gray>Total: " + list.size() + " registered materials.</gray>"));
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
                if (item == null && !itemId.startsWith("mvtink_")) {
                    // Convenience: "tin_ingot" and "mvtink_tin_ingot" are the same item.
                    item = itemRegistry.getItemById("mvtink_" + itemId);
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
                sender.sendMessage(miniMessage.deserialize("<green>Gave " + amount + "x " + itemId + " to " + target.getName() + ".</green>"));
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

    private TinkerMaterial parseMaterial(String id) {
        if (id == null) return null;
        String clean = id.toLowerCase(Locale.ROOT);
        if (!clean.startsWith("mvtink_")) {
            TinkerMaterial tm = materialRegistry.get("mvtink_" + clean);
            if (tm != null) return tm;
        }
        return materialRegistry.get(clean);
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

    private void sendHelp(CommandSender sender, String label) {
        sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker v" + plugin.getDescription().getVersion() + " (Chagui68) ===</gold>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " craft <weapon|tool|armor> <type> <m1> <m2> [m3] [tier]</yellow> <gray>- Instant admin crafting without forge.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " give <player> <mvtink_id> [amount]</yellow> <gray>- Give any raw ore, ingot, nugget, block, molten bucket, part, cast, smeltery or brush. The mvtink_ prefix is optional.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " codex [player]</yellow> <gray>- Open the browsable Alloy Codex: legendary recipes, catalysts, forged composites and primes, a combination explorer and the totals.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " verify</yellow> <gray>- Check that every material and every item kind is registered and resolvable.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " forge <build|check|gui> [rotation]</yellow> <gray>- Manage the multiblock Forge and open custom GUI.</gray>"));                sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " list [OVERWORLD|NETHER|THE_END]</yellow> <gray>- List all 97 geological materials.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " reload</yellow> <gray>- Reload configuration and caches.</gray>"));
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        if (!sender.hasPermission("multiversetinker.admin")) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return filter(List.of("craft", "give", "forge", "codex", "list", "verify", "reload"), args[0]);
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

        if (args.length >= 4 && args.length <= 6 && args[0].equalsIgnoreCase("craft")) {
            List<String> mats = new ArrayList<>();
            for (TinkerMaterial tm : materialRegistry.getAll()) {
                mats.add(tm.getId().replace("mvtink_", ""));
            }
            if (args.length >= 5) {
                for (EvolutionTier et : EvolutionTier.values()) {
                    mats.add(et.name());
                }
            }
            return filter(mats, args[args.length - 1]);
        }

        if (args.length == 7 && args[0].equalsIgnoreCase("craft")) {
            return filter(Arrays.stream(EvolutionTier.values()).map(Enum::name).toList(), args[6]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("forge")) {
            return filter(List.of("build", "check", "gui"), args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("forge") && args[1].equalsIgnoreCase("build")) {
            return filter(List.of("0", "90", "180", "270"), args[2]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("codex"))) {
            return null; // Player names
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            return filter(List.of("OVERWORLD", "NETHER", "THE_END"), args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return filter(new ArrayList<>(itemRegistry.getAllItemIds()), args[2]);
        }

        return Collections.emptyList();
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
}
