package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
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
import org.bukkit.event.inventory.ClickType;
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
    public static final int TAB_ARMOR = 5;

    // Navigation slots (Row 0)
    public static final int SLOT_NAV_BORDER_L = 0;
    public static final int SLOT_NAV_INFO = 1;
    public static final int SLOT_NAV_PARTS = 2;
    public static final int SLOT_NAV_ALLOY = 3;
    public static final int SLOT_NAV_DIVIDER = 4;
    public static final int SLOT_NAV_WEAPONS = 5;
    public static final int SLOT_NAV_TOOLS = 6;
    public static final int SLOT_NAV_ARMOR = 7;
    public static final int SLOT_NAV_BORDER_R = 8;

    // Tab 1: Molds & Parts slots
    public static final int SLOT_MOLD_PREV = 12;
    public static final int SLOT_MOLD_SELECTOR = 13;
    public static final int SLOT_MOLD_NEXT = 14;

    public static final int SLOT_PART_CAST = 28;
    public static final int SLOT_PART_MAT1 = 30;
    public static final int SLOT_PART_MAT2 = 31;
    public static final int SLOT_PART_MAT3 = 32;
    public static final int SLOT_PART_OUTPUT = 34;
    public static final int SLOT_PART_STRIKE = 40;

    // Tab 2: Alloy Crucible slots
    public static final int SLOT_ALLOY_MAT1 = 29;
    public static final int SLOT_ALLOY_SMELT = 31;
    public static final int SLOT_ALLOY_MAT2 = 33;
    public static final int SLOT_ALLOY_OUTPUT = 40;
    public static final int SLOT_ALLOY_RECIPES = 49;

    // Tab 3: Weapons slots
    public static final int SLOT_WEAPON_PREV = 12;
    public static final int SLOT_WEAPON_SELECTOR = 13;
    public static final int SLOT_WEAPON_NEXT = 14;
    public static final int SLOT_WEAPON_PART1 = 29;
    public static final int SLOT_WEAPON_PART2 = 31;
    public static final int SLOT_WEAPON_PART3 = 33;
    public static final int SLOT_WEAPON_ASSEMBLE = 39;
    public static final int SLOT_WEAPON_OUTPUT = 41;

    // Tab 4: Tools slots
    public static final int SLOT_TOOL_PREV = 12;
    public static final int SLOT_TOOL_SELECTOR = 13;
    public static final int SLOT_TOOL_NEXT = 14;
    public static final int SLOT_TOOL_HEAD = 29;
    public static final int SLOT_TOOL_HANDLE = 31;
    public static final int SLOT_TOOL_POMMEL = 33;
    public static final int SLOT_TOOL_ASSEMBLE = 39;
    public static final int SLOT_TOOL_OUTPUT = 41;

    // Tab 5: Armor slots
    public static final int SLOT_ARMOR_PREV = 12;
    public static final int SLOT_ARMOR_SELECTOR = 13;
    public static final int SLOT_ARMOR_NEXT = 14;
    public static final int SLOT_ARMOR_PLATE = 29;
    public static final int SLOT_ARMOR_LINING = 31;
    public static final int SLOT_ARMOR_TRIM = 33;
    public static final int SLOT_ARMOR_ASSEMBLE = 39;
    public static final int SLOT_ARMOR_OUTPUT = 41;

    private static final List<CastType> CARVABLE_CASTS = List.of(
            CastType.HEAD,
            CastType.ROD,
            CastType.BINDING,
            CastType.BOW_LIMBS,
            CastType.BOWSTRING,
            CastType.SHIELD_PLATE,
            CastType.SHIELD_BOSS,
            CastType.ARMOR_PLATE,
            CastType.ARMOR_LINING,
            CastType.ARMOR_TRIM,
            CastType.INGOT,
            CastType.NUGGET,
            CastType.BLOCK
    );

    private final MultiverseTinker plugin;
    private final TinkerItemRegistry itemRegistry;
    private final MaterialRegistry materialRegistry;
    private final AlloyRegistry alloyRegistry;
    private final Inventory inventory;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private int currentTab = TAB_INFO;
    private int selectedCastIndex = 0;
    private ModularWeaponType selectedWeaponType = ModularWeaponType.SWORD;
    private ModularToolType selectedToolType = ModularToolType.PICKAXE;
    private ModularArmorType selectedArmorType = ModularArmorType.HELMET;

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
        ItemStack borderPane = createSystemDecor(Material.BLACK_STAINED_GLASS_PANE, " ");
        inventory.setItem(SLOT_NAV_BORDER_L, borderPane);
        inventory.setItem(SLOT_NAV_BORDER_R, borderPane);

        ItemStack centerDivider = createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gradient:#ff4500:#ffaa00><b>⚒ Multiverse Forge ⚒</b></gradient>");
        inventory.setItem(SLOT_NAV_DIVIDER, centerDivider);

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

        inventory.setItem(SLOT_NAV_ARMOR, createNavButton(TAB_ARMOR, Material.NETHERITE_CHESTPLATE,
                "<gradient:#9b59b6:#8e44ad><b>[ 6. Armor Assembly ]</b></gradient>",
                "Assemble Helmets, Chestplates, Leggings, and Boots."));
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
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    private void renderCurrentTab(@Nullable Player player) {
        ItemStack grayGlass = createSystemDecor(Material.GRAY_STAINED_GLASS_PANE, " ");
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
            case TAB_ARMOR -> renderTabArmor();
        }
    }

    private void returnActiveTabItems(@Nonnull Player player, int tabIndex) {
        int[] slotsToClear = switch (tabIndex) {
            case TAB_PARTS -> new int[]{SLOT_PART_CAST, SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3, SLOT_PART_OUTPUT};
            case TAB_ALLOY -> new int[]{SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2, SLOT_ALLOY_OUTPUT};
            case TAB_WEAPONS -> new int[]{SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3, SLOT_WEAPON_OUTPUT};
            case TAB_TOOLS -> new int[]{SLOT_TOOL_HEAD, SLOT_TOOL_HANDLE, SLOT_TOOL_POMMEL, SLOT_TOOL_OUTPUT};
            case TAB_ARMOR -> new int[]{SLOT_ARMOR_PLATE, SLOT_ARMOR_LINING, SLOT_ARMOR_TRIM, SLOT_ARMOR_OUTPUT};
            default -> new int[0];
        };

        for (int slot : slotsToClear) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                if (!isSystemItem(item)) {
                    inventory.setItem(slot, null);
                    Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                    for (ItemStack drop : leftover.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), drop);
                    }
                } else {
                    inventory.setItem(slot, null);
                }
            }
        }
    }

    // ==========================================
    // TAB 1: INFORMATIONAL CODEX & GUIDES
    // ==========================================
    private void renderTabInfo() {
        // Row 1 Title
        inventory.setItem(13, createSystemDecor(Material.NETHER_STAR,
                "<gradient:#ffd700:#ff8c00><b>✦ Multiverse Metallurgy Codex ✦</b></gradient>"));

        // Row 2: 4 Core Guides
        inventory.setItem(20, createGuideItem(Material.BEACON,
                "<gradient:#f39c12:#e67e22><b>The Forge Structure</b></gradient>",
                List.of(
                        "• Center: Anvil surrounded by thermal resonance.",
                        "• Heat: 4 corner Lava columns providing thermal power.",
                        "• Base: Chiseled Tuff Bricks, Deepslate Tiles,",
                        "  and Tuff Brick Stairs / Slabs spanning 9x3x9 blocks.",
                        "• Status: 100% Thermal Resonance required to activate."
                )));

        inventory.setItem(21, createGuideItem(Material.BRICK,
                "<gradient:#e67e22:#d35400><b>Molds & Multi-Material Casting</b></gradient>",
                List.of(
                        "• Mold Carver: 1 Clay Brick produces 1 reusable mold.",
                        "• Multi-Material Forging: Combine up to 3 materials per part!",
                        "• Concentration Ratios:",
                        "  - 1 Material: 100% trait potency & full stats.",
                        "  - 2 Materials: 50% / 50% split across both traits.",
                        "  - 3 Materials: 33% / 33% / 33% split across 3 traits.",
                        "• Multi-material parts proc all traits proportionally!"
                )));

        inventory.setItem(23, createGuideItem(Material.BLAST_FURNACE,
                "<gradient:#e74c3c:#c0392b><b>Alloy Crucible Smelting</b></gradient>",
                List.of(
                        "• Blend any 2 brush-extracted or vanilla minerals.",
                        "• Each combination owns its own composite alloy trait.",
                        "• 16 legendary recipes have curated traits & bonuses:",
                        "  - Bronze (Copper + Tin): Durability & Knockback Res.",
                        "  - Electrum (Gold + Silver): Swift attack speed & Luck.",
                        "  - Invar (Iron + Nickel): Extreme armor toughness.",
                        "• Alloy effects adapt to weapons, tools and armor!"
                )));

        inventory.setItem(24, createGuideItem(Material.EXPERIENCE_BOTTLE,
                "<gradient:#9b59b6:#8e44ad><b>Equipment Evolution Tiers</b></gradient>",
                List.of(
                        "• Your forged equipment levels up as you use it!",
                        "• Progression Ladder:",
                        "  Wood -> Stone -> Copper -> Iron -> Gold -> Diamond -> Netherite",
                        "• Weapons evolve by defeating hostile mobs.",
                        "• Tools evolve by breaking harvestable blocks.",
                        "• Armor evolves by absorbing incoming damage.",
                        "• Each tier grants bonus stats, damage, and durability!"
                )));

        // Row 3: 3 Equipment Guides
        inventory.setItem(29, createGuideItem(Material.NETHERITE_SWORD,
                "<gradient:#3498db:#2980b9><b>Modular Weapons & Combat Perks</b></gradient>",
                List.of(
                        "• Broadsword: Sweeps elemental traits across adjacent foes.",
                        "• Longbow & Crossbow: Fires trait-infused projectiles.",
                        "• Elder Trident: Unleashes storm surges & lightning.",
                        "• Kinetic Spear: Extended reach & +30% sprint charge.",
                        "• War Mace: Crushing downward smashes with shockwaves.",
                        "• Tower Shield: Reflects 35% damage & retaliates on block.",
                        "• Perks are material-driven: the head part's mineral names and powers them",
                        "  (Cobalt => Infernal Piercing Velocity, Voidstone => Void Piercing Velocity).",
                        "• Focus a weapon to 80%+ essence to unleash cinematic essence ultimates."
                )));

        inventory.setItem(31, createGuideItem(Material.NETHERITE_PICKAXE,
                "<gradient:#2ecc71:#27ae60><b>Modular Tools & Mining Mechanics</b></gradient>",
                List.of(
                        "• Pickaxe: Deep Vein Resonance drops extra ores & Haste.",
                        "• Battleaxe: Cleaves whole logs & shatters mob shields.",
                        "• Excavator (Shovel): Sneak-digging breaks 3x3 soil areas.",
                        "• Scythe (Hoe): Harvests 3x3 mature crops & auto-replants.",
                        "• Fishing Rod: Abyssal Dredge catches rare minerals."
                )));

        inventory.setItem(33, createGuideItem(Material.NETHERITE_CHESTPLATE,
                "<gradient:#9b59b6:#8e44ad><b>Modular Armor & Defensive Traits</b></gradient>",
                List.of(
                        "• Helmet: Reduces headshot impact & grants hazard warding.",
                        "• Chestplate: Kinetic Dampener absorbs 25% heavy blows.",
                        "• Leggings: Stride Momentum mitigates sprint fatigue.",
                        "• Boots: Negates up to 50% fall damage with ground traction.",
                        "• Assembled from Plate, Lining, and Trim parts."
                )));

        // Row 4: Summary / Quick Start
        inventory.setItem(40, createGuideItem(Material.BOOK,
                "<gradient:#ffd700:#ff8c00><b>Quick Start Guide</b></gradient>",
                List.of(
                        "1. Carve molds in Tab 2 with Clay Bricks.",
                        "2. Cast parts in Tab 2 using molds and raw/molten minerals.",
                        "3. Blend metals in Tab 3 to synthesize advanced alloys.",
                        "4. Assemble weapons, tools, and armor in Tabs 4, 5, and 6!",
                        "5. Level up your equipment through combat, mining, and defense."
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
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    // ==========================================
    // TAB 2: MOLD CARVER & PART FORGING
    // ==========================================
    private void renderTabParts() {
        CastType selectedCast = getSelectedCastType();

        // Row 1: Symmetrical Mold Navigation (◀ and ▶ arrows) with Central Mold Carver
        inventory.setItem(SLOT_MOLD_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Mold</gold>",
                List.of("Click to cycle to the previous mold type.")));

        Material moldIcon = (selectedCast == CastType.BLOCK) ? Material.IRON_BLOCK
                : ((selectedCast == CastType.INGOT) ? Material.IRON_INGOT
                : ((selectedCast == CastType.NUGGET) ? Material.IRON_NUGGET : Material.BRICK));

        inventory.setItem(SLOT_MOLD_SELECTOR, createSystemButton(moldIcon,
                "<gradient:#e67e22:#d35400><b>⚒ Carve Mold: " + selectedCast.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedCast.getDescription(),
                        "",
                        "Cost: 1 Clay Brick in your inventory.",
                        "Click to carve this mold into your inventory!"
                )));

        inventory.setItem(SLOT_MOLD_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Mold ▶</gold>",
                List.of("Click to cycle to the next mold type.")));

        // Row 2: Symmetrical Labels (Mold clearly separated from the 3 materials)
        inventory.setItem(19, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>[ Mold / Cast Slot ]</b></gold>"));
        inventory.setItem(20, createSystemDecor(Material.GRAY_STAINED_GLASS_PANE, "<dark_gray>┃ Divider ┃</dark_gray>"));
        inventory.setItem(21, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 1 (Mandatory) ]</b></yellow>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 2 (Mandatory) ]</b></yellow>"));
        inventory.setItem(23, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Material 3 (Mandatory) ]</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.GRAY_STAINED_GLASS_PANE, "<dark_gray>┃ Divider ┃</dark_gray>"));
        inventory.setItem(25, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>[ Forged Part Output ]</b></green>"));

        // Row 3: Interactive Slots & Physical Chamber Dividers
        inventory.setItem(SLOT_PART_CAST, null);
        inventory.setItem(29, createSystemDecor(Material.IRON_BARS, "<dark_gray>┃ Mold Chamber Divider ┃</dark_gray>"));
        inventory.setItem(SLOT_PART_MAT1, null);
        inventory.setItem(SLOT_PART_MAT2, null);
        inventory.setItem(SLOT_PART_MAT3, null);
        inventory.setItem(33, createSystemDecor(Material.IRON_BARS, "<dark_gray>┃ Output Chamber Divider ┃</dark_gray>"));
        inventory.setItem(SLOT_PART_OUTPUT, null);

        // Row 4: Centered Strike Anvil Button
        inventory.setItem(SLOT_PART_STRIKE, createSystemButton(Material.ANVIL,
                "<gradient:#ffaa00:#ff5500><b>⚒ Strike Anvil & Forge Part</b></gradient>",
                List.of(
                        "Place 1 Cast in Slot 28 and all 3 required Materials in Slots 30-32.",
                        "",
                        "All 3 material slots are strictly mandatory.",
                        "Multi-material parts combine all 3 traits & stats (33% / 33% / 33%)!"
                )));
    }

    private CastType getSelectedCastType() {
        if (selectedCastIndex < 0 || selectedCastIndex >= CARVABLE_CASTS.size()) {
            selectedCastIndex = 0;
        }
        return CARVABLE_CASTS.get(selectedCastIndex);
    }

    // ==========================================
    // TAB 3: ALLOY CRUCIBLE
    // ==========================================
    private void renderTabAlloy() {
        // Row 1 Title
        inventory.setItem(13, createSystemDecor(Material.BLAST_FURNACE,
                "<gradient:#e74c3c:#c0392b><b>♨ Alloy Smelting Crucible ♨</b></gradient>"));

        // Row 2 Labels
        inventory.setItem(20, createSystemDecor(Material.RED_STAINED_GLASS_PANE, "<red><b>[ Primary Material ]</b></red>"));
        inventory.setItem(22, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>[ Thermal Core ]</b></gold>"));
        inventory.setItem(24, createSystemDecor(Material.BLUE_STAINED_GLASS_PANE, "<blue><b>[ Secondary Material ]</b></blue>"));

        // Row 3 Inputs & Smelt Core
        inventory.setItem(SLOT_ALLOY_MAT1, null);
        inventory.setItem(SLOT_ALLOY_SMELT, createSystemButton(Material.CAMPFIRE,
                "<gradient:#ff4500:#ffa500><b>♨ Ignite Crucible & Smelt Alloy</b></gradient>",
                List.of(
                        "Place 2 distinct brush or vanilla minerals in 29 and 33.",
                        "All 16 legendary recipes work, netherite included in its two.",
                        "",
                        "Every mineral combination yields its own unique alloy.",
                        "Consumes 1 of each item to produce 2 alloy ingots."
                )));
        inventory.setItem(SLOT_ALLOY_MAT2, null);

        // Row 4 Output Flow
        inventory.setItem(39, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Smelted Alloy ▶</b></green>"));
        inventory.setItem(SLOT_ALLOY_OUTPUT, null);
        inventory.setItem(41, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>◀ Smelted Alloy</b></green>"));

        // Row 5 Recipe Codex
        inventory.setItem(SLOT_ALLOY_RECIPES, createSystemButton(Material.BOOK,
                "<gradient:#ffd700:#ff8c00><b>Alloy Recipes Codex</b></gradient>",
                List.of(
                        "Click to browse every registered alloy in chat.",
                        "Any 2 brush or vanilla minerals can be blended together!",
                        "16 legendary recipes are predefined; all other pairs",
                        "synthesize their own unique composite alloy on the spot."
                )));
    }

    // ==========================================
    // TAB 4: WEAPON ASSEMBLY
    // ==========================================
    private void renderTabWeapons() {
        // Row 1 Weapon Selector
        inventory.setItem(SLOT_WEAPON_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Weapon</gold>",
                List.of("Click to select previous weapon type.")));

        inventory.setItem(SLOT_WEAPON_SELECTOR, createSystemButton(selectedWeaponType.getBaseMaterial(),
                "<gradient:#3498db:#2980b9><b>Selected Weapon: " + selectedWeaponType.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedWeaponType.getDescription(),
                        "",
                        "Requires " + selectedWeaponType.getPartCount() + " modular components.",
                        "Click to cycle next weapon type"
                )));

        inventory.setItem(SLOT_WEAPON_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Weapon ▶</gold>",
                List.of("Click to select next weapon type.")));

        // Row 2 Part Labels
        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gold><b>[ Part 1: " + selectedWeaponType.getPart1Name() + " ]</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE,
                "<yellow><b>[ Part 2: " + selectedWeaponType.getPart2Name() + " ]</b></yellow>"));

        if (!selectedWeaponType.isTwoPart()) {
            inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                    "<aqua><b>[ Part 3: " + selectedWeaponType.getPart3Name() + " ]</b></aqua>"));
        } else {
            inventory.setItem(24, createSystemDecor(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        // Row 3 Input Slots
        inventory.setItem(SLOT_WEAPON_PART1, null);
        inventory.setItem(SLOT_WEAPON_PART2, null);
        if (!selectedWeaponType.isTwoPart()) {
            inventory.setItem(SLOT_WEAPON_PART3, null);
        } else {
            inventory.setItem(SLOT_WEAPON_PART3, createSystemDecor(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        // Row 4 Action & Output
        inventory.setItem(SLOT_WEAPON_ASSEMBLE, createSystemButton(Material.ANVIL,
                "<gradient:#3498db:#2980b9><b>⚒ Assemble " + selectedWeaponType.getDisplayName() + "</b></gradient>",
                List.of(
                        "Combines the placed modular parts into a finished weapon.",
                        "Inherits all elemental traits and evolution stats."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Weapon ▶</b></green>"));
        inventory.setItem(SLOT_WEAPON_OUTPUT, null);
    }

    // ==========================================
    // TAB 5: TOOL ASSEMBLY
    // ==========================================
    private void renderTabTools() {
        // Row 1 Tool Selector
        inventory.setItem(SLOT_TOOL_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Tool</gold>",
                List.of("Click to select previous tool type.")));

        inventory.setItem(SLOT_TOOL_SELECTOR, createSystemButton(selectedToolType.getBaseMaterial(),
                "<gradient:#2ecc71:#27ae60><b>Selected Tool: " + selectedToolType.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedToolType.getDescription(),
                        "",
                        "Requires 3 modular components: Head, Handle, Pommel.",
                        "Click to cycle next tool type"
                )));

        inventory.setItem(SLOT_TOOL_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Tool ▶</gold>",
                List.of("Click to select next tool type.")));

        // Row 2 Part Labels
        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>[ Part 1: Tool Head ]</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>[ Part 2: Tool Handle ]</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "<aqua><b>[ Part 3: Tool Pommel ]</b></aqua>"));

        // Row 3 Input Slots
        inventory.setItem(SLOT_TOOL_HEAD, null);
        inventory.setItem(SLOT_TOOL_HANDLE, null);
        inventory.setItem(SLOT_TOOL_POMMEL, null);

        // Row 4 Action & Output
        inventory.setItem(SLOT_TOOL_ASSEMBLE, createSystemButton(Material.ANVIL,
                "<gradient:#2ecc71:#27ae60><b>⚒ Assemble " + selectedToolType.getDisplayName() + "</b></gradient>",
                List.of(
                        "Combines Head, Handle, and Pommel into a finished tool.",
                        "Inherits mining speed, perks, and traits."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Tool ▶</b></green>"));
        inventory.setItem(SLOT_TOOL_OUTPUT, null);
    }

    // ==========================================
    // TAB 6: ARMOR ASSEMBLY
    // ==========================================
    private void renderTabArmor() {
        // Row 1 Armor Selector
        inventory.setItem(SLOT_ARMOR_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Armor</gold>",
                List.of("Click to select previous armor piece.")));

        inventory.setItem(SLOT_ARMOR_SELECTOR, createSystemButton(selectedArmorType.getBaseMaterial(),
                "<gradient:#9b59b6:#8e44ad><b>Selected Armor: " + selectedArmorType.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedArmorType.getDescription(),
                        "",
                        "Requires 3 modular components: Plate, Lining, Trim.",
                        "Click to cycle next armor piece"
                )));

        inventory.setItem(SLOT_ARMOR_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Armor ▶</gold>",
                List.of("Click to select next armor piece.")));

        // Row 2 Part Labels
        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gold><b>[ Part 1: " + selectedArmorType.getPart1Name() + " ]</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE,
                "<yellow><b>[ Part 2: " + selectedArmorType.getPart2Name() + " ]</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                "<aqua><b>[ Part 3: " + selectedArmorType.getPart3Name() + " ]</b></aqua>"));

        // Row 3 Input Slots
        inventory.setItem(SLOT_ARMOR_PLATE, null);
        inventory.setItem(SLOT_ARMOR_LINING, null);
        inventory.setItem(SLOT_ARMOR_TRIM, null);

        // Row 4 Action & Output
        inventory.setItem(SLOT_ARMOR_ASSEMBLE, createSystemButton(Material.ANVIL,
                "<gradient:#9b59b6:#8e44ad><b>⚒ Assemble " + selectedArmorType.getDisplayName() + "</b></gradient>",
                List.of(
                        "Combines Plate, Lining, and Trim into finished armor.",
                        "Inherits defense points, toughness, and defensive traits."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Armor ▶</b></green>"));
        inventory.setItem(SLOT_ARMOR_OUTPUT, null);
    }

    // ==========================================
    // HELPER CREATORS & SYSTEM MARKERS
    // ==========================================
    private ItemStack createSystemDecor(Material mat, String title) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createSystemButton(Material mat, String title, List<String> loreLines) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            for (String line : loreLines) {
                lore.add(Component.text(line, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            }
            meta.lore(lore);
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    private void markAsSystemItem(ItemMeta meta) {
        meta.getPersistentDataContainer().set(TinkerKeys.SYSTEM_GUI_ITEM, PersistentDataType.BYTE, (byte) 1);
    }

    private boolean isSystemItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return true;
        if (item.getType().name().endsWith("_GLASS_PANE")) return true;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(TinkerKeys.SYSTEM_GUI_ITEM, PersistentDataType.BYTE);
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
        if (rawSlot == SLOT_NAV_ARMOR) {
            event.setCancelled(true);
            switchTab(TAB_ARMOR, player);
            return;
        }
        if (rawSlot == SLOT_NAV_BORDER_L || rawSlot == SLOT_NAV_BORDER_R || rawSlot == SLOT_NAV_DIVIDER) {
            event.setCancelled(true);
            return;
        }

        // Dispatch based on active tab
        switch (currentTab) {
            case TAB_INFO -> handleInfoClicks(event, player, rawSlot);
            case TAB_PARTS -> handlePartsClicks(event, player, rawSlot);
            case TAB_ALLOY -> handleAlloyClicks(event, player, rawSlot);
            case TAB_WEAPONS -> handleWeaponsClicks(event, player, rawSlot);
            case TAB_TOOLS -> handleToolsClicks(event, player, rawSlot);
            case TAB_ARMOR -> handleArmorClicks(event, player, rawSlot);
        }
    }

    private void switchTab(int newTab, Player player) {
        if (this.currentTab == newTab) return;
        returnActiveTabItems(player, this.currentTab);
        this.currentTab = newTab;
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
        renderCurrentTab(null);
    }

    private void handleInfoClicks(InventoryClickEvent event, Player player, int rawSlot) {
        event.setCancelled(true);
    }

    private void handlePartsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_MOLD_PREV) {
            event.setCancelled(true);
            cycleMold(false);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            renderTabParts();
            return;
        }
        if (rawSlot == SLOT_MOLD_NEXT) {
            event.setCancelled(true);
            cycleMold(true);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            renderTabParts();
            return;
        }
        if (rawSlot == SLOT_MOLD_SELECTOR) {
            event.setCancelled(true);
            carveMold(player, getSelectedCastType());
            return;
        }
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

    private void cycleMold(boolean forward) {
        if (forward) {
            selectedCastIndex = (selectedCastIndex + 1) % CARVABLE_CASTS.size();
        } else {
            selectedCastIndex = (selectedCastIndex - 1 + CARVABLE_CASTS.size()) % CARVABLE_CASTS.size();
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
            sendSuccessMessage(player, "Successfully carved", cast);
        }
    }

    private void sendSuccessMessage(Player player, String actionVerb, @Nullable ItemStack item) {
        Component itemName = Component.text("Equipment", NamedTextColor.WHITE);
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            Component dn = item.getItemMeta().displayName();
            if (dn != null) {
                itemName = dn;
            }
        }
        player.sendMessage(Component.text("✔ " + actionVerb + " ", NamedTextColor.GREEN)
                .append(itemName)
                .append(Component.text("!", NamedTextColor.GREEN)));
    }

    private void strikeForgePart(Player player) {
        ItemStack castItem = inventory.getItem(SLOT_PART_CAST);
        ItemStack mat1 = inventory.getItem(SLOT_PART_MAT1);
        ItemStack mat2 = inventory.getItem(SLOT_PART_MAT2);
        ItemStack mat3 = inventory.getItem(SLOT_PART_MAT3);

        if (castItem == null || mat1 == null || mat2 == null || mat3 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place a Cast in slot 28 and all 3 required Materials in slots 30, 31, and 32.</red>"));
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
            player.sendMessage(miniMessage.deserialize("<red>⚠ Unrecognized Cast type.</red>"));
            return;
        }

        ToolPartType partType = ToolPartType.fromCast(castType);
        if (partType == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ This cast cannot be forged into a tool or equipment part.</red>"));
            return;
        }

        TinkerMaterial m1 = getMaterialFromItem(mat1);
        TinkerMaterial m2 = getMaterialFromItem(mat2);
        TinkerMaterial m3 = getMaterialFromItem(mat3);
        if (m1 == null || m2 == null || m3 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Slots 30, 31, and 32 must all contain recognized Tinker Materials or Molten Buckets!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        List<TinkerMaterial> materials = List.of(m1, m2, m3);
        PartComposition composition = PartComposition.fromMaterials(materials);
        ItemStack forgedPart = TinkerItemBuilder.createModularPart(partType, composition);

        decrementSlot(SLOT_PART_MAT1);
        decrementSlot(SLOT_PART_MAT2);
        decrementSlot(SLOT_PART_MAT3);

        inventory.setItem(SLOT_PART_OUTPUT, forgedPart);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.spawnParticle(Particle.LAVA, player.getLocation().add(0, 1, 0), 10, 0.3, 0.3, 0.3, 0.0);
        sendSuccessMessage(player, "Successfully forged", forgedPart);
    }

    private void handleAlloyClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_ALLOY_SMELT) {
            event.setCancelled(true);
            smeltAlloy(player);
            return;
        }
        if (rawSlot == SLOT_ALLOY_RECIPES) {
            event.setCancelled(true);
            displayAlloyRecipes(player);
            return;
        }

        Set<Integer> interactive = Set.of(SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2, SLOT_ALLOY_OUTPUT);
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void smeltAlloy(Player player) {
        ItemStack mat1 = inventory.getItem(SLOT_ALLOY_MAT1);
        ItemStack mat2 = inventory.getItem(SLOT_ALLOY_MAT2);

        if (mat1 == null || mat2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place 2 materials in slots 29 and 33.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        TinkerMaterial m1 = getMaterialFromItem(mat1);
        TinkerMaterial m2 = getMaterialFromItem(mat2);

        if (m1 == null || m2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Both slots must contain recognized Tinker materials or molten buckets!</red>"));
            return;
        }

        // Curated legendary recipes stay craftable even when one parent (vanilla netherite) is not
        // freely blendable; every other pair must be made of two brush/vanilla minerals.
        if (!alloyRegistry.isCraftablePair(m1, m2)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ " + AlloyRegistry.mixRequirementMessage() + "</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        if (m1.getId().equalsIgnoreCase(m2.getId())) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ The crucible requires 2 distinct minerals to synthesize an alloy!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        TinkerAlloy alloy = alloyRegistry.findOrCreateAlloy(m1, m2, materialRegistry);
        TinkerMaterial resultMaterial = materialRegistry.get(alloy.id());
        if (resultMaterial == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Internal error: alloy result material could not be synthesized.</red>"));
            return;
        }

        ItemStack alloyIngot = itemRegistry.getIngotItem(resultMaterial.getId());
        if (alloyIngot == null) {
            alloyIngot = TinkerItemBuilder.createIngot(resultMaterial);
        }
        alloyIngot.setAmount(2);

        decrementSlot(SLOT_ALLOY_MAT1);
        decrementSlot(SLOT_ALLOY_MAT2);

        inventory.setItem(SLOT_ALLOY_OUTPUT, alloyIngot);

        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.4f);
        player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1.0f, 1.0f);
        player.spawnParticle(Particle.FLAME, player.getLocation().add(0, 1.2, 0), 20, 0.3, 0.3, 0.3, 0.05);

        player.sendMessage(miniMessage.deserialize("<gold>♨ Crucible Synthesized: </gold>"
                + "<gradient:" + resultMaterial.getColorHex() + ":#ffffff><b>" + resultMaterial.getName() + " Ingot</b></gradient>"
                + " <gray>(x2)</gray>!"));
        player.sendMessage(miniMessage.deserialize("<gray>  ➤ " + alloy.traitName() + ": </gray><dark_aqua>" + alloy.traitDescription() + "</dark_aqua>"));
    }

    private void displayAlloyRecipes(Player player) {
        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>══════════ MULTIVERSE ALLOY CODEX ══════════</b></gradient>"));
        player.sendMessage(miniMessage.deserialize("<gray>Blend any two brush-extracted or vanilla minerals. Every pair yields a unique alloy whose trait adapts to weapons, tools and armor.</gray>"));
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            TinkerMaterial res = materialRegistry.get(alloy.id());
            String resName = (res != null) ? res.getName() : alloy.name();
            String resColor = (res != null) ? res.getColorHex() : "#ffd700";
            TinkerMaterial m1 = materialRegistry.get(alloy.mat1Id());
            TinkerMaterial m2 = materialRegistry.get(alloy.mat2Id());
            String n1 = (m1 != null) ? m1.getName() : alloy.mat1Id();
            String n2 = (m2 != null) ? m2.getName() : alloy.mat2Id();

            player.sendMessage(miniMessage.deserialize(
                    " <gradient:" + resColor + ":#ffffff><b>" + resName + "</b></gradient> <gray>←</gray> "
                            + "<yellow>" + n1 + "</yellow> <gray>+</gray> <yellow>" + n2 + "</yellow> "
                            + "<dark_gray>(" + alloy.traitDescription() + ")</dark_gray>"
            ));
        }
        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>═════════════════════════════════════════════</b></gradient>"));
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
    }

    private void handleWeaponsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_WEAPON_PREV) {
            event.setCancelled(true);
            cycleWeapon(false);
            renderTabWeapons();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_WEAPON_NEXT || rawSlot == SLOT_WEAPON_SELECTOR) {
            event.setCancelled(true);
            cycleWeapon(true);
            renderTabWeapons();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_WEAPON_ASSEMBLE) {
            event.setCancelled(true);
            assembleWeapon(player);
            return;
        }

        Set<Integer> interactive = new HashSet<>(Set.of(SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_OUTPUT));
        if (!selectedWeaponType.isTwoPart()) {
            interactive.add(SLOT_WEAPON_PART3);
        }
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void cycleWeapon(boolean forward) {
        ModularWeaponType[] types = ModularWeaponType.values();
        int cur = selectedWeaponType.ordinal();
        int next = forward ? (cur + 1) % types.length : (cur - 1 + types.length) % types.length;
        this.selectedWeaponType = types[next];
    }

    private void assembleWeapon(Player player) {
        ItemStack p1Item = inventory.getItem(SLOT_WEAPON_PART1);
        ItemStack p2Item = inventory.getItem(SLOT_WEAPON_PART2);
        ItemStack p3Item = selectedWeaponType.isTwoPart() ? null : inventory.getItem(SLOT_WEAPON_PART3);

        if (p1Item == null || p2Item == null || (!selectedWeaponType.isTwoPart() && p3Item == null)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing required weapon parts!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        PartComposition c1 = getCompositionFromPart(p1Item);
        PartComposition c2 = getCompositionFromPart(p2Item);
        PartComposition c3 = (p3Item != null) ? getCompositionFromPart(p3Item) : null;

        if (c1 == null || c2 == null || (!selectedWeaponType.isTwoPart() && c3 == null)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ One or more items in the assembly slots are not recognized forged modular parts!</red>"));
            return;
        }

        ItemStack weapon = TinkerItemBuilder.createModularWeapon(selectedWeaponType, c1, c2, c3, EvolutionTier.WOOD, 0);

        decrementSlot(SLOT_WEAPON_PART1);
        decrementSlot(SLOT_WEAPON_PART2);
        if (!selectedWeaponType.isTwoPart()) {
            decrementSlot(SLOT_WEAPON_PART3);
        }

        inventory.setItem(SLOT_WEAPON_OUTPUT, weapon);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", weapon);
    }

    private void handleToolsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_TOOL_PREV) {
            event.setCancelled(true);
            cycleTool(false);
            renderTabTools();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_TOOL_NEXT || rawSlot == SLOT_TOOL_SELECTOR) {
            event.setCancelled(true);
            cycleTool(true);
            renderTabTools();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
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

    private void cycleTool(boolean forward) {
        ModularToolType[] types = Arrays.stream(ModularToolType.values())
                .filter(t -> t != ModularToolType.SWORD)
                .toArray(ModularToolType[]::new);
        int cur = 0;
        for (int i = 0; i < types.length; i++) {
            if (types[i] == selectedToolType) { cur = i; break; }
        }
        int next = forward ? (cur + 1) % types.length : (cur - 1 + types.length) % types.length;
        this.selectedToolType = types[next];
    }

    private void assembleTool(Player player) {
        ItemStack headItem = inventory.getItem(SLOT_TOOL_HEAD);
        ItemStack handleItem = inventory.getItem(SLOT_TOOL_HANDLE);
        ItemStack pommelItem = inventory.getItem(SLOT_TOOL_POMMEL);

        if (headItem == null || handleItem == null || pommelItem == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing required tool parts! Provide Head, Handle, and Pommel.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        PartComposition cHead = getCompositionFromPart(headItem);
        PartComposition cHandle = getCompositionFromPart(handleItem);
        PartComposition cPommel = getCompositionFromPart(pommelItem);

        if (cHead == null || cHandle == null || cPommel == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ One or more items are not recognized modular parts!</red>"));
            return;
        }

        ItemStack tool = TinkerItemBuilder.createModularTool(selectedToolType, cHead, cHandle, cPommel, EvolutionTier.WOOD, 0);

        decrementSlot(SLOT_TOOL_HEAD);
        decrementSlot(SLOT_TOOL_HANDLE);
        decrementSlot(SLOT_TOOL_POMMEL);

        inventory.setItem(SLOT_TOOL_OUTPUT, tool);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", tool);
    }

    private void handleArmorClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_ARMOR_PREV) {
            event.setCancelled(true);
            cycleArmor(false);
            renderTabArmor();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_ARMOR_NEXT || rawSlot == SLOT_ARMOR_SELECTOR) {
            event.setCancelled(true);
            cycleArmor(true);
            renderTabArmor();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_ARMOR_ASSEMBLE) {
            event.setCancelled(true);
            assembleArmor(player);
            return;
        }

        Set<Integer> interactive = Set.of(SLOT_ARMOR_PLATE, SLOT_ARMOR_LINING, SLOT_ARMOR_TRIM, SLOT_ARMOR_OUTPUT);
        if (!interactive.contains(rawSlot)) {
            event.setCancelled(true);
        }
    }

    private void cycleArmor(boolean forward) {
        ModularArmorType[] types = ModularArmorType.values();
        int cur = selectedArmorType.ordinal();
        int next = forward ? (cur + 1) % types.length : (cur - 1 + types.length) % types.length;
        this.selectedArmorType = types[next];
    }

    private void assembleArmor(Player player) {
        ItemStack plateItem = inventory.getItem(SLOT_ARMOR_PLATE);
        ItemStack liningItem = inventory.getItem(SLOT_ARMOR_LINING);
        ItemStack trimItem = inventory.getItem(SLOT_ARMOR_TRIM);

        if (plateItem == null || liningItem == null || trimItem == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing required armor parts! Provide Plate, Lining, and Trim.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        PartComposition cPlate = getCompositionFromPart(plateItem);
        PartComposition cLining = getCompositionFromPart(liningItem);
        PartComposition cTrim = getCompositionFromPart(trimItem);

        if (cPlate == null || cLining == null || cTrim == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ One or more items are not recognized modular parts!</red>"));
            return;
        }

        ItemStack armor = TinkerItemBuilder.createModularArmor(selectedArmorType, cPlate, cLining, cTrim, EvolutionTier.WOOD, 0);

        decrementSlot(SLOT_ARMOR_PLATE);
        decrementSlot(SLOT_ARMOR_LINING);
        decrementSlot(SLOT_ARMOR_TRIM);

        inventory.setItem(SLOT_ARMOR_OUTPUT, armor);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", armor);
    }

    @Nullable
    private TinkerMaterial getMaterialFromItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            String matId = pdc.get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
            if (matId != null) {
                TinkerMaterial tm = materialRegistry.get(matId);
                if (tm != null) return tm;
            }
        }

        // Direct vanilla material fallback
        return switch (item.getType()) {
            case IRON_INGOT, RAW_IRON, IRON_BLOCK, IRON_NUGGET -> materialRegistry.get("mvtink_iron");
            case COPPER_INGOT, RAW_COPPER, COPPER_BLOCK -> materialRegistry.get("mvtink_copper");
            case GOLD_INGOT, RAW_GOLD, GOLD_BLOCK, GOLD_NUGGET -> materialRegistry.get("mvtink_gold");
            case DIAMOND, DIAMOND_BLOCK -> materialRegistry.get("mvtink_diamond");
            case EMERALD, EMERALD_BLOCK -> materialRegistry.get("mvtink_emerald");
            case NETHERITE_INGOT, NETHERITE_SCRAP, NETHERITE_BLOCK -> materialRegistry.get("mvtink_netherite");
            case COAL, CHARCOAL, COAL_BLOCK -> materialRegistry.get("mvtink_coal");
            case REDSTONE, REDSTONE_BLOCK -> materialRegistry.get("mvtink_redstone");
            case LAPIS_LAZULI, LAPIS_BLOCK -> materialRegistry.get("mvtink_lapis");
            case QUARTZ, QUARTZ_BLOCK -> materialRegistry.get("mvtink_quartz");
            case AMETHYST_SHARD, AMETHYST_BLOCK -> materialRegistry.get("mvtink_amethyst");
            case FLINT -> materialRegistry.get("mvtink_flint");
            case OBSIDIAN, CRYING_OBSIDIAN -> materialRegistry.get("mvtink_obsidian");
            case PRISMARINE_SHARD, PRISMARINE_CRYSTALS, PRISMARINE -> materialRegistry.get("mvtink_prismarine");
            default -> null;
        };
    }

    @Nullable
    private PartComposition getCompositionFromPart(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return null;
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
        returnActiveTabItems(player, this.currentTab);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
