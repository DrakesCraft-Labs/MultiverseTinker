package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ForgeGUI implements InventoryHolder {

    // Tab indices
    public static final int TAB_INFO = 0;
    public static final int TAB_PARTS = 1;
    public static final int TAB_ALLOY = 2;
    public static final int TAB_WEAPONS = 3;
    public static final int TAB_TOOLS = 4;

    // Navigation slots (Row 0)
    public static final int SLOT_NAV_INFO = 0;
    public static final int SLOT_NAV_PARTS = 2;
    public static final int SLOT_NAV_ALLOY = 4;
    public static final int SLOT_NAV_WEAPONS = 6;
    public static final int SLOT_NAV_TOOLS = 8;

    // Tab 1: Parts & Molds slots
    public static final int SLOT_CARVE_HEAD = 10;
    public static final int SLOT_CARVE_HANDLE = 11;
    public static final int SLOT_CARVE_POMMEL = 12;
    public static final int SLOT_CARVE_BOW_LIMBS = 13;
    public static final int SLOT_CARVE_BOWSTRING = 14;
    public static final int SLOT_CARVE_SHIELD_PLATE = 15;
    public static final int SLOT_CARVE_SHIELD_BOSS = 16;

    public static final int SLOT_PART_CAST = 28;
    public static final int SLOT_PART_MAT1 = 30;
    public static final int SLOT_PART_MAT2 = 31;
    public static final int SLOT_PART_MAT3 = 32;
    public static final int SLOT_PART_STRIKE = 38;
    public static final int SLOT_PART_OUTPUT = 42;

    // Tab 2: Alloy Crucible slots
    public static final int SLOT_ALLOY_MAT1 = 20;
    public static final int SLOT_ALLOY_FIRE = 22;
    public static final int SLOT_ALLOY_MAT2 = 24;
    public static final int SLOT_ALLOY_SMELT = 31;
    public static final int SLOT_ALLOY_OUTPUT = 33;
    public static final int SLOT_ALLOY_RECIPES = 49;

    // Tab 3: Weapons slots
    public static final int SLOT_WEAPON_SELECTOR = 13;
    public static final int SLOT_WEAPON_PART1 = 29;
    public static final int SLOT_WEAPON_PART2 = 31;
    public static final int SLOT_WEAPON_PART3 = 33;
    public static final int SLOT_WEAPON_ASSEMBLE = 40;
    public static final int SLOT_WEAPON_OUTPUT = 42;

    // Tab 4: Tools slots
    public static final int SLOT_TOOL_SELECTOR = 13;
    public static final int SLOT_TOOL_HEAD = 29;
    public static final int SLOT_TOOL_HANDLE = 31;
    public static final int SLOT_TOOL_POMMEL = 33;
    public static final int SLOT_TOOL_ASSEMBLE = 40;
    public static final int SLOT_TOOL_OUTPUT = 42;

    private final MultiverseTinker plugin;
    private final TinkerItemRegistry itemRegistry;
    private final MaterialRegistry materialRegistry;
    private final AlloyRegistry alloyRegistry;
    private final Inventory inventory;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private int currentTab = TAB_INFO;
    private ModularWeaponType selectedWeaponType = ModularWeaponType.SWORD;
    private ModularToolType selectedToolType = ModularToolType.PICKAXE;

    public ForgeGUI(@Nonnull MultiverseTinker plugin,
                    @Nonnull TinkerItemRegistry itemRegistry,
                    @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
        this.alloyRegistry = (plugin.getAlloyRegistry() != null) ? plugin.getAlloyRegistry() : new AlloyRegistry();
        this.inventory = Bukkit.createInventory(this, 54, miniMessage.deserialize("<gradient:#ff4500:#ffaa00><b>Multiverse Forge</b></gradient>"));
        renderCurrentTab(null);
    }

    private void renderNavBar() {
        ItemStack grayBar = createDecor(Material.GRAY_STAINED_GLASS_PANE, " ");
        inventory.setItem(1, grayBar);
        inventory.setItem(3, grayBar);
        inventory.setItem(5, grayBar);
        inventory.setItem(7, grayBar);

        inventory.setItem(SLOT_NAV_INFO, createNavButton(TAB_INFO, Material.ENCHANTED_BOOK,
                "<gradient:#f1c40f:#e67e22><b>[ 1. Codex & Guide ]</b></gradient>",
                "Comprehensive metallurgical & mechanics guide."));

        inventory.setItem(SLOT_NAV_PARTS, createNavButton(TAB_PARTS, Material.SMITHING_TABLE,
                "<gradient:#e67e22:#d35400><b>[ 2. Molds & Parts ]</b></gradient>",
                "Carve reusable casts & forge multi-material parts."));

        inventory.setItem(SLOT_NAV_ALLOY, createNavButton(TAB_ALLOY, Material.LAVA_BUCKET,
                "<gradient:#e74c3c:#c0392b><b>[ 3. Alloy Crucible ]</b></gradient>",
                "Mix 2 distinct materials to forge legendary alloys."));

        inventory.setItem(SLOT_NAV_WEAPONS, createNavButton(TAB_WEAPONS, Material.NETHERITE_SWORD,
                "<gradient:#3498db:#2980b9><b>[ 4. Weapon Assembly ]</b></gradient>",
                "Assemble Swords, Bows, Crossbows, Tridents, Spears, Maces, Shields."));

        inventory.setItem(SLOT_NAV_TOOLS, createNavButton(TAB_TOOLS, Material.NETHERITE_PICKAXE,
                "<gradient:#2ecc71:#27ae60><b>[ 5. Tool Assembly ]</b></gradient>",
                "Assemble Pickaxes, Axes, Hoes, Shovels, and Fishing Rods."));
    }

    private ItemStack createNavButton(int tabIndex, Material icon, String title, String desc) {
        boolean active = (currentTab == tabIndex);
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String prefix = active ? "<green>▶ </green>" : "<gray>  </gray>";
            meta.displayName(miniMessage.deserialize(prefix + title).decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(desc, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            if (active) {
                lore.add(miniMessage.deserialize("<green><b>✔ Currently Active Section</b></green>").decoration(TextDecoration.ITALIC, false));
            } else {
                lore.add(miniMessage.deserialize("<yellow>Click to switch to this section</yellow>").decoration(TextDecoration.ITALIC, false));
            }
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private void renderCurrentTab(@Nullable Player player) {
        // Return existing items in interactive slots to player before re-rendering
        if (player != null) {
            returnActiveTabItems(player);
        }

        ItemStack grayGlass = createDecor(Material.GRAY_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, grayGlass);
        }

        renderNavBar();

        switch (currentTab) {
            case TAB_INFO -> renderTabInfo();
            case TAB_PARTS -> renderTabParts();
            case TAB_ALLOY -> renderTabAlloy();
            case TAB_WEAPONS -> renderTabWeapons();
            case TAB_TOOLS -> renderTabTools();
        }
    }

    private void returnActiveTabItems(Player player) {
        int[] slotsToClear = switch (currentTab) {
            case TAB_PARTS -> new int[]{SLOT_PART_CAST, SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3, SLOT_PART_OUTPUT};
            case TAB_ALLOY -> new int[]{SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2, SLOT_ALLOY_OUTPUT};
            case TAB_WEAPONS -> new int[]{SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3, SLOT_WEAPON_OUTPUT};
            case TAB_TOOLS -> new int[]{SLOT_TOOL_HEAD, SLOT_TOOL_HANDLE, SLOT_TOOL_POMMEL, SLOT_TOOL_OUTPUT};
            default -> new int[0];
        };

        for (int slot : slotsToClear) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                inventory.setItem(slot, null);
                Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                for (ItemStack drop : leftover.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), drop);
                }
            }
        }
    }

    // ==========================================
    // TAB 1: INFORMATIONAL CODEX & GUIDES
    // ==========================================
    private void renderTabInfo() {
        inventory.setItem(19, createGuideItem(Material.BEACON,
                "<gradient:#f39c12:#e67e22><b>The Multiverse Forge Structure</b></gradient>",
                List.of(
                        "• Center: Centered Anvil with active volcanic aura.",
                        "• Pillars: 4 corner Lava columns providing thermal heat.",
                        "• Architecture: Chiseled Tuff Bricks, Deepslate Tiles,",
                        "  and Tuff Brick Stairs / Slabs spanning 9x3x9 blocks.",
                        "• Status: 100% Thermal Resonance required to activate."
                )));

        inventory.setItem(21, createGuideItem(Material.BRICK,
                "<gradient:#e67e22:#d35400><b>Molds & Multi-Material Casting</b></gradient>",
                List.of(
                        "• Quick Carving: 1 Clay Brick produces 1 reusable mold.",
                        "• Multi-Material Forging: Combine up to 3 materials per part!",
                        "• Concentration Ratios:",
                        "  - 1 Material: 100% trait potency & full stats.",
                        "  - 2 Materials: 50% / 50% split (e.g. Diamond + Quartz).",
                        "  - 3 Materials: 33% / 33% / 33% split across 3 traits.",
                        "• All traits proc with probability based on concentration!"
                )));

        inventory.setItem(23, createGuideItem(Material.BLAST_FURNACE,
                "<gradient:#e74c3c:#c0392b><b>Alloy Crucible & Metal Mixing</b></gradient>",
                List.of(
                        "• Mix up to 2 distinct materials to form new alloys.",
                        "• 16 Unique Alloys with custom traits & bonuses:",
                        "  - Bronze (Copper + Tin): Durability & Knockback Res.",
                        "  - Electrum (Gold + Silver): Attack Speed & Spark.",
                        "  - Manyullyn (Cobalt + Ardite): Ramp-up Damage.",
                        "  - Cosmic Netherite (Netherite + Celestite): Gravity Pull.",
                        "• Smelts 2 materials into 2 finished alloy ingots!"
                )));

        inventory.setItem(25, createGuideItem(Material.EXPERIENCE_BOTTLE,
                "<gradient:#3498db:#2980b9><b>Equipment Tier Evolution</b></gradient>",
                List.of(
                        "• Every weapon and tool starts at Wood Tier upon creation.",
                        "• Progression: Wood → Stone → Copper → Iron → Gold → Diamond → Netherite.",
                        "• Weapons level up through Combat Kills:",
                        "  - 15 kills (Stone), 40 (Copper), 80 (Iron), 150 (Gold), 300 (Diamond), 600 (Netherite).",
                        "• Tools level up through Blocks Broken:",
                        "  - 50 blocks (Stone), 150 (Copper), 350 (Iron), 750 (Gold), 1500 (Diamond), 3000 (Netherite)."
                )));

        inventory.setItem(31, createGuideItem(Material.NETHERITE_SWORD,
                "<gradient:#9b59b6:#8e44ad><b>Specialized Weapon Combat Perks</b></gradient>",
                List.of(
                        "• War Mace: Crushing downward smashes release ground shockwaves.",
                        "• Longbow: Arrows carry limb & string elemental traits.",
                        "• Heavy Crossbow: High-velocity bolts bypass target armor.",
                        "• Elder Trident: Summons hydraulic lightning strikes in water/rain.",
                        "• Kinetic Spear: Extended reach & +30% charge damage.",
                        "• Tower Shield: Reflects 35% damage & retaliates on block.",
                        "• Broadsword: Sweeps elemental traits across adjacent foes."
                )));

        inventory.setItem(33, createGuideItem(Material.NETHERITE_PICKAXE,
                "<gradient:#1abc9c:#16a085><b>Specialized Tool Mining Perks</b></gradient>",
                List.of(
                        "• Pickaxe: Deep Vein Resonance drops extra ores & grants Haste.",
                        "• Battleaxe: Cleaves whole logs & shatters mob shields on crit.",
                        "• Excavator (Shovel): Sneak-digging breaks 3x3 soil areas.",
                        "• Scythe (Hoe): Harvests 3x3 mature crops and auto-replants.",
                        "• Fishing Rod: Abyssal Dredge catches rare submerged minerals."
                )));

        inventory.setItem(49, createGuideItem(Material.NETHER_STAR,
                "<gradient:#ffd700:#ff8c00><b>Multiverse Metallurgy Online</b></gradient>",
                List.of(
                        "Multiverse Forge operates at peak thermal capacity.",
                        "Select any tab above to begin crafting equipment."
                )));
    }

    private ItemStack createGuideItem(Material mat, String title, List<String> lines) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            for (String line : lines) {
                lore.add(Component.text(line, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            }
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // TAB 2: MOLD CARVER & PART FORGING
    // ==========================================
    private void renderTabParts() {
        // Quick Carve Mold Row
        inventory.setItem(SLOT_CARVE_HEAD, createMoldCarveButton(CastType.HEAD, "Tool & Weapon Head (Cabeza)"));
        inventory.setItem(SLOT_CARVE_HANDLE, createMoldCarveButton(CastType.ROD, "Handle / Shaft (Mango)"));
        inventory.setItem(SLOT_CARVE_POMMEL, createMoldCarveButton(CastType.BINDING, "Pommel / Guard (Pomo)"));
        inventory.setItem(SLOT_CARVE_BOW_LIMBS, createMoldCarveButton(CastType.BOW_LIMBS, "Bow Limbs (Brazos del Arco)"));
        inventory.setItem(SLOT_CARVE_BOWSTRING, createMoldCarveButton(CastType.BOWSTRING, "Bowstring (Cuerda Tensora)"));
        inventory.setItem(SLOT_CARVE_SHIELD_PLATE, createMoldCarveButton(CastType.SHIELD_PLATE, "Shield Faceplate (Placa Frontal)"));
        inventory.setItem(SLOT_CARVE_SHIELD_BOSS, createMoldCarveButton(CastType.SHIELD_BOSS, "Shield Boss (Umbo / Armazón)"));

        // Labels for inputs
        inventory.setItem(19, createDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>[ Mold Slot ]</b></gold>"));
        inventory.setItem(21, createDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 1 (Required) ]</b></yellow>"));
        inventory.setItem(22, createDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 2 (Optional) ]</b></yellow>"));
        inventory.setItem(23, createDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 3 (Optional) ]</b></yellow>"));

        inventory.setItem(SLOT_PART_STRIKE, createStrikePartButton());

        // Clear interactive slots
        inventory.setItem(SLOT_PART_CAST, null);
        inventory.setItem(SLOT_PART_MAT1, null);
        inventory.setItem(SLOT_PART_MAT2, null);
        inventory.setItem(SLOT_PART_MAT3, null);
        inventory.setItem(SLOT_PART_OUTPUT, null);
    }

    private ItemStack createMoldCarveButton(CastType castType, String name) {
        ItemStack item = new ItemStack(Material.BRICK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#e67e22:#f39c12><b>Carve " + castType.getDisplayName() + "</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Carves reusable mold for: " + name, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(miniMessage.deserialize("<gold>Cost: </gold><white>1 Clay Brick</white>").decoration(TextDecoration.ITALIC, false));
            lore.add(miniMessage.deserialize("<yellow>Click to carve into your inventory</yellow>").decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createStrikePartButton() {
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#ffaa00:#ff5500><b>⚒ Strike Anvil & Forge Part</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Place 1 Cast in Slot 28 and up to 3 Materials in Slots 30-32.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Supports Molten Buckets and Solid Ingots / Minerals.", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Multi-material parts split traits and stats proportionally!", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // TAB 3: ALLOY CRUCIBLE
    // ==========================================
    private void renderTabAlloy() {
        inventory.setItem(11, createDecor(Material.RED_STAINED_GLASS_PANE, "<red><b>[ Ingot / Liquid 1 ]</b></red>"));
        inventory.setItem(15, createDecor(Material.RED_STAINED_GLASS_PANE, "<red><b>[ Ingot / Liquid 2 ]</b></red>"));

        ItemStack fire = new ItemStack(Material.BLAST_FURNACE);
        ItemMeta fMeta = fire.getItemMeta();
        if (fMeta != null) {
            fMeta.displayName(miniMessage.deserialize("<gradient:#ff4500:#ffaa00><b>Crucible Thermal Core</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Blends 2 distinct metallurgical minerals into new alloys.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            fMeta.lore(lore);
            fire.setItemMeta(fMeta);
        }
        inventory.setItem(SLOT_ALLOY_FIRE, fire);

        inventory.setItem(SLOT_ALLOY_SMELT, createSmeltAlloyButton());
        inventory.setItem(SLOT_ALLOY_RECIPES, createAlloyCodexButton());

        inventory.setItem(SLOT_ALLOY_MAT1, null);
        inventory.setItem(SLOT_ALLOY_MAT2, null);
        inventory.setItem(SLOT_ALLOY_OUTPUT, null);
    }

    private ItemStack createSmeltAlloyButton() {
        ItemStack item = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#ff5500:#ff2200><b>🔥 Melt & Blend Alloy</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Combines the 2 placed minerals into a metallurgical alloy.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Yields: ", NamedTextColor.GOLD)
                    .append(Component.text("2x Finished Alloy Ingots", NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createAlloyCodexButton() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#f1c40f:#e67e22><b>Alloy Formula Codex (16 Recipes)</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
                lore.add(miniMessage.deserialize("<gradient:" + alloy.colorHex() + ":#ffffff>• " + alloy.name() + "</gradient> <gray>(" + alloy.mat1Id().replace("mvtink_", "") + " + " + alloy.mat2Id().replace("mvtink_", "") + ")</gray>").decoration(TextDecoration.ITALIC, false));
            }
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // TAB 4: WEAPON ASSEMBLY
    // ==========================================
    private void renderTabWeapons() {
        updateWeaponTypeSelectorItem();

        if (selectedWeaponType.isTwoPart()) {
            inventory.setItem(20, createDecor(Material.CYAN_STAINED_GLASS_PANE, "<aqua><b>[ Part 1: " + selectedWeaponType.getPart1Name() + " ]</b></aqua>"));
            inventory.setItem(SLOT_WEAPON_PART2, createDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>[ Rigid Frame Spacer ]</b></gold>"));
            inventory.setItem(24, createDecor(Material.CYAN_STAINED_GLASS_PANE, "<aqua><b>[ Part 2: " + selectedWeaponType.getPart2Name() + " ]</b></aqua>"));
        } else {
            inventory.setItem(20, createDecor(Material.CYAN_STAINED_GLASS_PANE, "<aqua><b>[ Part 1: " + selectedWeaponType.getPart1Name() + " ]</b></aqua>"));
            inventory.setItem(22, createDecor(Material.CYAN_STAINED_GLASS_PANE, "<aqua><b>[ Part 2: " + selectedWeaponType.getPart2Name() + " ]</b></aqua>"));
            inventory.setItem(24, createDecor(Material.CYAN_STAINED_GLASS_PANE, "<aqua><b>[ Part 3: " + selectedWeaponType.getPart3Name() + " ]</b></aqua>"));
        }

        inventory.setItem(SLOT_WEAPON_ASSEMBLE, createAssembleWeaponButton());

        inventory.setItem(SLOT_WEAPON_PART1, null);
        if (!selectedWeaponType.isTwoPart()) {
            inventory.setItem(SLOT_WEAPON_PART2, null);
        }
        inventory.setItem(SLOT_WEAPON_PART3, null);
        inventory.setItem(SLOT_WEAPON_OUTPUT, null);
    }

    private void updateWeaponTypeSelectorItem() {
        ItemStack selector = new ItemStack(selectedWeaponType.getBaseMaterial());
        ItemMeta meta = selector.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#00f2fe:#4facfe><b>Weapon: " + selectedWeaponType.getDisplayName() + "</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(selectedWeaponType.getDescription(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Required Components (" + selectedWeaponType.getPartCount() + "):", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 29: " + selectedWeaponType.getPart1Name(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            if (selectedWeaponType.isTwoPart()) {
                lore.add(Component.text("  • Slot 33: " + selectedWeaponType.getPart2Name(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            } else {
                lore.add(Component.text("  • Slot 31: " + selectedWeaponType.getPart2Name(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("  • Slot 33: " + selectedWeaponType.getPart3Name(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            }
            lore.add(Component.empty());
            lore.add(Component.text("Click to cycle weapon type:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  Sword → Bow → Crossbow → Trident → Spear → Mace → Shield", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            selector.setItemMeta(meta);
        }
        inventory.setItem(SLOT_WEAPON_SELECTOR, selector);
    }

    private ItemStack createAssembleWeaponButton() {
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#22c55e:#10b981><b>⚒ Assemble Modular Weapon</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Combines the placed weapon components into a new modular weapon.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(miniMessage.deserialize("<gold>Initial Tier: </gold><gradient:#a0522d:#8b4513>Wood Tier</gradient>").decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Evolves up to Netherite Tier through combat kills!", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // TAB 5: TOOL ASSEMBLY
    // ==========================================
    private void renderTabTools() {
        updateToolTypeSelectorItem();

        inventory.setItem(20, createDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>[ Part 1: Cabeza (Head) ]</b></green>"));
        inventory.setItem(22, createDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>[ Part 2: Mango (Handle) ]</b></green>"));
        inventory.setItem(24, createDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>[ Part 3: Pomo (Pommel) ]</b></green>"));

        inventory.setItem(SLOT_TOOL_ASSEMBLE, createAssembleToolButton());

        inventory.setItem(SLOT_TOOL_HEAD, null);
        inventory.setItem(SLOT_TOOL_HANDLE, null);
        inventory.setItem(SLOT_TOOL_POMMEL, null);
        inventory.setItem(SLOT_TOOL_OUTPUT, null);
    }

    private void updateToolTypeSelectorItem() {
        ItemStack selector = new ItemStack(selectedToolType.getBaseMaterial());
        ItemMeta meta = selector.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#00f2fe:#4facfe><b>Tool: " + selectedToolType.getDisplayName() + "</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(selectedToolType.getDescription(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Required Components (3):", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 29: Cabeza (Tool Head)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 31: Mango (Tool Handle)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 33: Pomo (Tool Pommel / Binding)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Click to cycle tool type:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  Pickaxe → Axe → Hoe → Shovel → Fishing Rod", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            selector.setItemMeta(meta);
        }
        inventory.setItem(SLOT_TOOL_SELECTOR, selector);
    }

    private ItemStack createAssembleToolButton() {
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#22c55e:#10b981><b>⚒ Assemble Modular Tool</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Combines the placed tool parts into a modular excavation tool.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(miniMessage.deserialize("<gold>Initial Tier: </gold><gradient:#a0522d:#8b4513>Wood Tier</gradient>").decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Evolves up to Netherite Tier through blocks broken!", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createDecor(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(name).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // INTERACTION HANDLING
    // ==========================================
    public void handleClick(@Nonnull InventoryClickEvent event, @Nonnull Player player) {
        int rawSlot = event.getRawSlot();

        if (rawSlot >= 54) {
            return;
        }

        // Navigation clicks
        if (rawSlot == SLOT_NAV_INFO) {
            event.setCancelled(true);
            switchTab(TAB_INFO, player);
            return;
        }
        if (rawSlot == SLOT_NAV_PARTS) {
            event.setCancelled(true);
            switchTab(TAB_PARTS, player);
            return;
        }
        if (rawSlot == SLOT_NAV_ALLOY) {
            event.setCancelled(true);
            switchTab(TAB_ALLOY, player);
            return;
        }
        if (rawSlot == SLOT_NAV_WEAPONS) {
            event.setCancelled(true);
            switchTab(TAB_WEAPONS, player);
            return;
        }
        if (rawSlot == SLOT_NAV_TOOLS) {
            event.setCancelled(true);
            switchTab(TAB_TOOLS, player);
            return;
        }

        // Dispatch based on active tab
        switch (currentTab) {
            case TAB_INFO -> handleInfoClicks(event, player, rawSlot);
            case TAB_PARTS -> handlePartsClicks(event, player, rawSlot);
            case TAB_ALLOY -> handleAlloyClicks(event, player, rawSlot);
            case TAB_WEAPONS -> handleWeaponsClicks(event, player, rawSlot);
            case TAB_TOOLS -> handleToolsClicks(event, player, rawSlot);
        }
    }

    private void switchTab(int newTab, Player player) {
        if (this.currentTab == newTab) return;
        this.currentTab = newTab;
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
        renderCurrentTab(player);
    }

    private void handleInfoClicks(InventoryClickEvent event, Player player, int rawSlot) {
        event.setCancelled(true);
    }

    private void handlePartsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        // Quick mold carving clicks
        if (rawSlot == SLOT_CARVE_HEAD) { event.setCancelled(true); carveMold(player, CastType.HEAD); return; }
        if (rawSlot == SLOT_CARVE_HANDLE) { event.setCancelled(true); carveMold(player, CastType.ROD); return; }
        if (rawSlot == SLOT_CARVE_POMMEL) { event.setCancelled(true); carveMold(player, CastType.BINDING); return; }
        if (rawSlot == SLOT_CARVE_BOW_LIMBS) { event.setCancelled(true); carveMold(player, CastType.BOW_LIMBS); return; }
        if (rawSlot == SLOT_CARVE_BOWSTRING) { event.setCancelled(true); carveMold(player, CastType.BOWSTRING); return; }
        if (rawSlot == SLOT_CARVE_SHIELD_PLATE) { event.setCancelled(true); carveMold(player, CastType.SHIELD_PLATE); return; }
        if (rawSlot == SLOT_CARVE_SHIELD_BOSS) { event.setCancelled(true); carveMold(player, CastType.SHIELD_BOSS); return; }

        if (rawSlot == SLOT_PART_STRIKE) {
            event.setCancelled(true);
            strikeForgePart(player);
            return;
        }

        Set<Integer> interactive = Set.of(SLOT_PART_CAST, SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3, SLOT_PART_OUTPUT);
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void carveMold(Player player, CastType castType) {
        if (!player.getInventory().contains(Material.BRICK)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ You need 1 Clay Brick in your inventory to carve this casting mold!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.7f);
            return;
        }

        player.getInventory().removeItem(new ItemStack(Material.BRICK, 1));
        ItemStack cast = itemRegistry.getCastItem(castType);
        if (cast != null) {
            player.getInventory().addItem(cast);
            player.playSound(player.getLocation(), Sound.BLOCK_GRAVEL_PLACE, 1.0f, 1.4f);
            player.sendMessage(miniMessage.deserialize("<green>✔ Successfully carved " + castType.getDisplayName() + "!</green>"));
        }
    }

    private void strikeForgePart(Player player) {
        ItemStack castItem = inventory.getItem(SLOT_PART_CAST);
        ItemStack mat1 = inventory.getItem(SLOT_PART_MAT1);
        ItemStack mat2 = inventory.getItem(SLOT_PART_MAT2);
        ItemStack mat3 = inventory.getItem(SLOT_PART_MAT3);

        if (castItem == null || mat1 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Provide a Cast in slot 28 and at least 1 Material in slot 30.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        ItemMeta castMeta = castItem.getItemMeta();
        if (castMeta == null) return;
        String castTypeName = castMeta.getPersistentDataContainer().get(TinkerKeys.CAST_TYPE, PersistentDataType.STRING);
        if (castTypeName == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid Cast! Must be a recognized Tinker Casting Mold.</red>"));
            return;
        }

        CastType castType;
        try {
            castType = CastType.valueOf(castTypeName);
        } catch (IllegalArgumentException e) {
            return;
        }

        ToolPartType partType = ToolPartType.fromCast(castType);
        if (partType == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ This cast type cannot be forged into a tool/weapon part here.</red>"));
            return;
        }

        List<TinkerMaterial> materials = new ArrayList<>();
        addMaterialFromSlot(mat1, materials);
        addMaterialFromSlot(mat2, materials);
        addMaterialFromSlot(mat3, materials);

        if (materials.isEmpty()) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid input materials! Use valid Tinker/Vanilla ingots, gems, or molten buckets.</red>"));
            return;
        }

        ItemStack outputSlot = inventory.getItem(SLOT_PART_OUTPUT);
        if (outputSlot != null && outputSlot.getType() != Material.AIR) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Output slot is occupied! Remove the finished part first.</red>"));
            return;
        }

        // Consume 1 of each material (returning bucket if molten bucket)
        consumeMaterialSlot(SLOT_PART_MAT1, player);
        consumeMaterialSlot(SLOT_PART_MAT2, player);
        consumeMaterialSlot(SLOT_PART_MAT3, player);

        PartComposition composition = PartComposition.fromMaterials(materials);
        ItemStack finishedPart = TinkerItemBuilder.createModularPart(partType, composition);

        inventory.setItem(SLOT_PART_OUTPUT, finishedPart);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.8f, 1.5f);
        player.spawnParticle(Particle.LAVA, player.getLocation().add(0, 1, 0), 10, 0.3, 0.3, 0.3, 0.0);
        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully forged " + finishedPart.getItemMeta().getDisplayName() + "<green>!</green>"));
    }

    private void addMaterialFromSlot(@Nullable ItemStack item, @Nonnull List<TinkerMaterial> list) {
        if (item == null || item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        String matId = meta.getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        if (matId != null) {
            TinkerMaterial tm = materialRegistry.get(matId);
            if (tm != null) {
                list.add(tm);
            }
        }
    }

    private void consumeMaterialSlot(int slot, Player player) {
        ItemStack item = inventory.getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        boolean isBucket = item.getType() == Material.LAVA_BUCKET ||
                item.getItemMeta().getPersistentDataContainer().has(TinkerKeys.IS_MOLTEN_BUCKET, PersistentDataType.BYTE);

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            if (isBucket) {
                player.getInventory().addItem(new ItemStack(Material.BUCKET));
            }
        } else {
            if (isBucket) {
                inventory.setItem(slot, new ItemStack(Material.BUCKET));
            } else {
                inventory.setItem(slot, null);
            }
        }
    }

    private void handleAlloyClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_ALLOY_SMELT) {
            event.setCancelled(true);
            smeltAlloy(player);
            return;
        }

        Set<Integer> interactive = Set.of(SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2, SLOT_ALLOY_OUTPUT);
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void smeltAlloy(Player player) {
        ItemStack m1 = inventory.getItem(SLOT_ALLOY_MAT1);
        ItemStack m2 = inventory.getItem(SLOT_ALLOY_MAT2);

        if (m1 == null || m2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place 2 different materials in slots 20 and 24.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        ItemMeta meta1 = m1.getItemMeta();
        ItemMeta meta2 = m2.getItemMeta();
        if (meta1 == null || meta2 == null) return;

        String id1 = meta1.getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        String id2 = meta2.getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);

        if (id1 == null || id2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid items! Both items must be valid Tinker materials.</red>"));
            return;
        }

        TinkerAlloy alloy = alloyRegistry.findAlloy(id1, id2);
        if (alloy == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ These two materials do not form a compatible alloy. Check the Alloy Codex.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        ItemStack currentOut = inventory.getItem(SLOT_ALLOY_OUTPUT);
        if (currentOut != null && currentOut.getType() != Material.AIR) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Output slot is occupied! Remove the finished alloy first.</red>"));
            return;
        }

        consumeMaterialSlot(SLOT_ALLOY_MAT1, player);
        consumeMaterialSlot(SLOT_ALLOY_MAT2, player);

        ItemStack ingot = itemRegistry.getIngotItem(alloy.id());
        if (ingot == null) {
            TinkerMaterial tm = materialRegistry.get(alloy.id());
            if (tm != null) {
                ingot = TinkerItemBuilder.createIngot(tm);
            }
        }

        if (ingot != null) {
            ingot.setAmount(2);
            inventory.setItem(SLOT_ALLOY_OUTPUT, ingot);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_BLASTFURNACE_FIRE_CRACKLE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.4f);
        player.spawnParticle(Particle.FLAME, player.getLocation().add(0, 1.2, 0), 20, 0.3, 0.3, 0.3, 0.05);
        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully smelted 2x " + alloy.name() + " Ingots!</green>"));
    }

    private void handleWeaponsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_WEAPON_SELECTOR) {
            event.setCancelled(true);
            ModularWeaponType[] vals = ModularWeaponType.values();
            int next = (selectedWeaponType.ordinal() + 1) % vals.length;
            this.selectedWeaponType = vals[next];
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
            renderCurrentTab(player);
            return;
        }

        if (rawSlot == SLOT_WEAPON_ASSEMBLE) {
            event.setCancelled(true);
            assembleWeapon(player);
            return;
        }

        Set<Integer> interactive = selectedWeaponType.isTwoPart()
                ? Set.of(SLOT_WEAPON_PART1, SLOT_WEAPON_PART3, SLOT_WEAPON_OUTPUT)
                : Set.of(SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3, SLOT_WEAPON_OUTPUT);

        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void assembleWeapon(Player player) {
        ItemStack p1Item = inventory.getItem(SLOT_WEAPON_PART1);
        ItemStack p2Item = selectedWeaponType.isTwoPart() ? null : inventory.getItem(SLOT_WEAPON_PART2);
        ItemStack p3Item = inventory.getItem(SLOT_WEAPON_PART3);

        if (selectedWeaponType.isTwoPart()) {
            if (p1Item == null || p3Item == null) {
                player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place both parts in slots 29 and 33.</red>"));
                return;
            }
        } else {
            if (p1Item == null || p2Item == null || p3Item == null) {
                player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place all 3 parts in slots 29, 31, and 33.</red>"));
                return;
            }
        }

        PartComposition c1 = getCompositionFromPart(p1Item);
        PartComposition c2 = selectedWeaponType.isTwoPart() ? null : getCompositionFromPart(p2Item);
        PartComposition c3 = getCompositionFromPart(p3Item);

        if (c1 == null || c3 == null || (!selectedWeaponType.isTwoPart() && c2 == null)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid parts! Use forged modular components.</red>"));
            return;
        }

        ItemStack weapon = selectedWeaponType.isTwoPart()
                ? TinkerItemBuilder.createModularWeapon(selectedWeaponType, c1, c3, null, EvolutionTier.WOOD, 0)
                : TinkerItemBuilder.createModularWeapon(selectedWeaponType, c1, c2, c3, EvolutionTier.WOOD, 0);

        decrementSlot(SLOT_WEAPON_PART1);
        if (!selectedWeaponType.isTwoPart()) {
            decrementSlot(SLOT_WEAPON_PART2);
        }
        decrementSlot(SLOT_WEAPON_PART3);

        player.getInventory().addItem(weapon);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.2f, 1.4f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully assembled " + selectedWeaponType.getDisplayName() + " (Wood Tier)!</green>"));
    }

    private void handleToolsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_TOOL_SELECTOR) {
            event.setCancelled(true);
            ModularToolType[] vals = ModularToolType.values();
            int next = (selectedToolType.ordinal() + 1) % vals.length;
            if (vals[next] == ModularToolType.SWORD) {
                next = (next + 1) % vals.length;
            }
            this.selectedToolType = vals[next];
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
            renderCurrentTab(player);
            return;
        }

        if (rawSlot == SLOT_TOOL_ASSEMBLE) {
            event.setCancelled(true);
            assembleTool(player);
            return;
        }

        Set<Integer> interactive = Set.of(SLOT_TOOL_HEAD, SLOT_TOOL_HANDLE, SLOT_TOOL_POMMEL, SLOT_TOOL_OUTPUT);
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void assembleTool(Player player) {
        ItemStack headItem = inventory.getItem(SLOT_TOOL_HEAD);
        ItemStack handleItem = inventory.getItem(SLOT_TOOL_HANDLE);
        ItemStack pommelItem = inventory.getItem(SLOT_TOOL_POMMEL);

        if (headItem == null || handleItem == null || pommelItem == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place Head, Handle, and Pommel in slots 29, 31, and 33.</red>"));
            return;
        }

        PartComposition cHead = getCompositionFromPart(headItem);
        PartComposition cHandle = getCompositionFromPart(handleItem);
        PartComposition cPommel = getCompositionFromPart(pommelItem);

        if (cHead == null || cHandle == null || cPommel == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid tool parts! Use forged modular components.</red>"));
            return;
        }

        ItemStack tool = TinkerItemBuilder.createModularTool(selectedToolType, cHead, cHandle, cPommel, EvolutionTier.WOOD, 0);

        decrementSlot(SLOT_TOOL_HEAD);
        decrementSlot(SLOT_TOOL_HANDLE);
        decrementSlot(SLOT_TOOL_POMMEL);

        player.getInventory().addItem(tool);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.2f, 1.4f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully assembled " + selectedToolType.getDisplayName() + " (Wood Tier)!</green>"));
    }

    @Nullable
    private PartComposition getCompositionFromPart(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String rawComp = pdc.get(TinkerKeys.PART_COMPOSITION_DATA, PersistentDataType.STRING);
        if (rawComp != null) {
            return PartComposition.deserialize(rawComp, materialRegistry);
        }

        String matId = pdc.get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        if (matId != null) {
            TinkerMaterial tm = materialRegistry.get(matId);
            if (tm != null) {
                return PartComposition.fromMaterials(List.of(tm));
            }
        }
        return null;
    }

    private void decrementSlot(int slot) {
        ItemStack item = inventory.getItem(slot);
        if (item != null) {
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                inventory.setItem(slot, null);
            }
        }
    }

    public void handleClose(@Nonnull InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        returnActiveTabItems(player);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
