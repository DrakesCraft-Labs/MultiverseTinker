package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.ForgePerkPreview;
import com.chagui68.multiversetinker.tools.ForgeTier;
import com.chagui68.multiversetinker.tools.LegendaryArt;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.SignatureArt;
import com.chagui68.multiversetinker.tools.SignatureArtEngine;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * The Multiverse Forge: a six-section workshop opened from the anvil of a complete Forge multiblock.
 *
 * <p>Every section shares one frame — the navigation row on top, a footer with the pedigree ladder and a
 * close button, and side rails tinted in the section's own colour — and every work area reads
 * left-to-right: inputs, the action button, the result. The sections that consume items show a live
 * preview of what they will produce before anything is spent: the part a cast would forge, the alloy the
 * crucible would fuse (with its pedigree and signature art), and the perk an assembly would name.</p>
 *
 * <p>Items are never lost: an occupied output is never overwritten, shift-clicking routes an item to the
 * slot it belongs in, double-click collection and drags can never pull or push the Forge's own icons,
 * and every input and output is handed back when the section changes or the Forge closes.</p>
 */
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

    // Footer (Row 5)
    public static final int SLOT_PEDIGREE_GUIDE = 45;
    public static final int SLOT_CLOSE = 53;

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
    /** Live preview of the part the cast and materials would forge. */
    public static final int SLOT_PART_PREVIEW = 49;

    // Tab 2: Alloy Crucible slots
    public static final int SLOT_ALLOY_MAT1 = 29;
    public static final int SLOT_ALLOY_SMELT = 31;
    public static final int SLOT_ALLOY_MAT2 = 33;
    public static final int SLOT_ALLOY_OUTPUT = 40;
    public static final int SLOT_ALLOY_RECIPES = 49;
    /** Live preview of the alloy the two inputs would fuse into, drawn above the ignite button. */
    public static final int SLOT_ALLOY_PREVIEW = 22;

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

    /**
     * Where an assembly tab prints the perk the parts in its slots would forge.
     *
     * <p>One slot shared by the three assembly tabs, since only one of them is on screen at a time. It
     * sits directly under the assemble anvil so it reads as that button's answer, and it is a slot no
     * other tab claims — the alloy crucible puts its codex button next door.</p>
     */
    public static final int SLOT_PERK_PREVIEW = 48;

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

    /** Visual identity of each section: its accent rail, its title gradient and its name. */
    private record Theme(Material accent, String from, String to, String name) {
        String gradient(String text) {
            return "<gradient:" + from + ":" + to + ">" + text + "</gradient>";
        }
    }

    private static final Theme[] THEMES = {
            new Theme(Material.YELLOW_STAINED_GLASS_PANE, "#f1c40f", "#e67e22", "Codex & Guide"),
            new Theme(Material.ORANGE_STAINED_GLASS_PANE, "#e67e22", "#d35400", "Molds & Parts"),
            new Theme(Material.RED_STAINED_GLASS_PANE, "#e74c3c", "#ff9f43", "Alloy Crucible"),
            new Theme(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "#3498db", "#74b9ff", "Weapon Assembly"),
            new Theme(Material.LIME_STAINED_GLASS_PANE, "#2ecc71", "#a3e635", "Tool Assembly"),
            new Theme(Material.PURPLE_STAINED_GLASS_PANE, "#9b59b6", "#e056fd", "Armor Assembly")
    };

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
    private boolean refreshQueued;

    public ForgeGUI(@Nonnull MultiverseTinker plugin,
                    @Nonnull TinkerItemRegistry itemRegistry,
                    @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
        this.alloyRegistry = (plugin.getAlloyRegistry() != null) ? plugin.getAlloyRegistry() : new AlloyRegistry();
        this.inventory = Bukkit.createInventory(this, 54, miniMessage.deserialize(
                "<gradient:#ff4500:#ffaa00><b>⚒ Multiverse Forge</b></gradient>"));
        renderCurrentTab();
    }

    // ==========================================
    // FRAME
    // ==========================================

    @Nonnull
    private Theme theme() {
        return THEMES[Math.max(0, Math.min(THEMES.length - 1, currentTab))];
    }

    private void renderCurrentTab() {
        Theme theme = theme();
        ItemStack background = createSystemDecor(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack rail = createSystemDecor(theme.accent(), " ");
        for (int i = 0; i < 54; i++) {
            int column = i % 9;
            int row = i / 9;
            boolean edge = row == 5 || ((row >= 1 && row <= 4) && (column == 0 || column == 8));
            inventory.setItem(i, edge ? rail : background);
        }

        renderNavBar();
        renderFooter();

        switch (currentTab) {
            case TAB_INFO -> renderTabInfo();
            case TAB_PARTS -> renderTabParts();
            case TAB_ALLOY -> renderTabAlloy();
            case TAB_WEAPONS -> renderTabWeapons();
            case TAB_TOOLS -> renderTabTools();
            case TAB_ARMOR -> renderTabArmor();
        }
    }

    private void renderNavBar() {
        Theme theme = theme();
        ItemStack border = createSystemDecor(theme.accent(), " ");
        inventory.setItem(SLOT_NAV_BORDER_L, border);
        inventory.setItem(SLOT_NAV_BORDER_R, border);

        inventory.setItem(SLOT_NAV_DIVIDER, createSystemButton(Material.ANVIL,
                "<gradient:#ff4500:#ffaa00><b>⚒ Multiverse Forge ⚒</b></gradient>",
                List.of("Section: " + theme.name(),
                        "",
                        "Inputs on the left, the action in the centre,",
                        "the result on the right. Previews show what",
                        "you will get before anything is spent.")));

        inventory.setItem(SLOT_NAV_INFO, createNavButton(TAB_INFO, Material.ENCHANTED_BOOK, "1. Codex & Guide",
                "Every Forge mechanic, the pedigree ladder and the 16 legendary arts."));
        inventory.setItem(SLOT_NAV_PARTS, createNavButton(TAB_PARTS, Material.SMITHING_TABLE, "2. Molds & Parts",
                "Carve reusable casts and forge three-material parts."));
        inventory.setItem(SLOT_NAV_ALLOY, createNavButton(TAB_ALLOY, Material.LAVA_BUCKET, "3. Alloy Crucible",
                "Fuse two materials into an alloy — and awaken its signature art."));
        inventory.setItem(SLOT_NAV_WEAPONS, createNavButton(TAB_WEAPONS, Material.NETHERITE_SWORD, "4. Weapon Assembly",
                "Swords, bows, crossbows, tridents, spears, maces and shields."));
        inventory.setItem(SLOT_NAV_TOOLS, createNavButton(TAB_TOOLS, Material.NETHERITE_PICKAXE, "5. Tool Assembly",
                "Pickaxes, battleaxes, excavators, scythes and fishing rods."));
        inventory.setItem(SLOT_NAV_ARMOR, createNavButton(TAB_ARMOR, Material.NETHERITE_CHESTPLATE, "6. Armor Assembly",
                "Helmets, chestplates, leggings and boots."));
    }

    private ItemStack createNavButton(int tabIndex, Material icon, String title, String desc) {
        boolean active = (currentTab == tabIndex);
        Theme theme = THEMES[tabIndex];
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String prefix = active ? "<green>▶ </green>" : "";
            meta.displayName(miniMessage.deserialize(prefix + theme.gradient("<b>" + title + "</b>"))
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>(LoreWrap.wrap(
                    Component.text(desc, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            lore.add(Component.empty());
            lore.add(miniMessage.deserialize(active
                            ? "<green><b>✔ You are here</b></green>"
                            : "<yellow>Click to open this section</yellow>")
                    .decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if (active) glint(meta);
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    private void renderFooter() {
        List<String> ladder = new ArrayList<>();
        ladder.add("The deeper the metallurgy, the louder the weapon:");
        ladder.add("");
        for (ForgeTier tier : ForgeTier.values()) {
            ladder.add(tier.badge() + " <gray>— " + pedigreeHint(tier) + "</gray>");
        }
        ladder.add("");
        ladder.add("<dark_gray>An alloy weapon fires its Signature Art on hit;</dark_gray>");
        ladder.add("<dark_gray>its pedigree sets the chance, reach and power.</dark_gray>");
        inventory.setItem(SLOT_PEDIGREE_GUIDE, createRichItem(Material.AMETHYST_SHARD,
                "<gradient:#ffd700:#e056fd><b>✦ Forge Pedigree</b></gradient>", ladder, false));

        inventory.setItem(SLOT_CLOSE, createSystemButton(Material.BARRIER, "<red><b>✖ Close the Forge</b></red>",
                List.of("Every item still in a slot is returned to you.")));
    }

    @Nonnull
    private static String pedigreeHint(@Nonnull ForgeTier tier) {
        return switch (tier) {
            case MINERAL -> "brushed minerals and vanilla ores";
            case COMPOSITE -> "two minerals fused: a fusion art";
            case LEGENDARY -> "one of the 16 recipes: its own legendary art";
            case PRIME -> "a legendary fused again: the art ascends";
            case MYTHIC -> "legendary + catalyst or legendary: the full cinematic";
        };
    }

    // ==========================================
    // TAB 1: CODEX & GUIDES
    // ==========================================
    private void renderTabInfo() {
        inventory.setItem(13, createRichItem(Material.NETHER_STAR,
                "<gradient:#ffd700:#ff8c00><b>✦ Multiverse Metallurgy Codex ✦</b></gradient>",
                List.of("Hover any book below to read how the Forge works.",
                        "The Alloy Codex (crucible section) lists every material."), true));

        inventory.setItem(20, createGuideItem(Material.BEACON,
                "<gradient:#f39c12:#e67e22><b>The Forge Structure</b></gradient>",
                List.of(
                        "• Centre: an anvil inside an 11×7×11 multiblock (243 blocks).",
                        "• Heat: 4 corner Lava columns feed the thermal resonance.",
                        "• Shell: Chiseled Tuff Bricks, Deepslate Tiles and Bricks,",
                        "  Tuff Brick Slabs and Stairs, in any of the 4 rotations.",
                        "• Sneak-right-click the anvil to see how much is missing."
                )));

        inventory.setItem(21, createGuideItem(Material.BRICK,
                "<gradient:#e67e22:#d35400><b>Molds & Three-Material Parts</b></gradient>",
                List.of(
                        "• Carve a mold: 1 Clay Brick = 1 reusable cast.",
                        "• Every part is cast from exactly 3 materials (33/33/33):",
                        "  each one lends its trait and a third of its stats.",
                        "• Repeat the same mineral 3 times for a pure part.",
                        "• The part preview shows the result before you strike."
                )));

        inventory.setItem(22, createGuideItem(Material.BLAST_FURNACE,
                "<gradient:#e74c3c:#c0392b><b>Alloy Crucible</b></gradient>",
                List.of(
                        "• Fuse any 2 brushed or vanilla minerals: every pair",
                        "  is its own composite alloy with its own fusion art.",
                        "• 16 pairs are legendary recipes (Bronze, Electrum,",
                        "  Manyullyn, Void Damascus, Cosmic Netherite…).",
                        "• The fusion preview names the alloy, its pedigree and",
                        "  its signature art before the crucible is lit."
                )));

        inventory.setItem(23, createGuideItem(Material.EXPERIENCE_BOTTLE,
                "<gradient:#9b59b6:#8e44ad><b>Equipment Evolution Tiers</b></gradient>",
                List.of(
                        "• Wood → Stone → Copper → Iron → Gold → Diamond → Netherite",
                        "• Weapons evolve by kills, tools by blocks broken,",
                        "  armor by damage absorbed.",
                        "• Each tier adds damage, durability and protection."
                )));

        inventory.setItem(24, createGuideItem(Material.END_CRYSTAL,
                "<gradient:#e056fd:#6c5ce7><b>Prime & Mythic Fusion</b></gradient>",
                List.of(
                        "• Fuse a LEGENDARY alloy again with a mineral or a",
                        "  composite: a PRIME alloy that ascends its art.",
                        "• Fuse it with a vanilla catalyst (Nether Star, Echo",
                        "  Shard, Blue Ice…) or a second legendary: MYTHIC.",
                        "• Mythic arts take over the sky: thunder, obelisks,",
                        "  a title on your screen and the catalyst's own finale."
                )));

        inventory.setItem(29, createGuideItem(Material.NETHERITE_SWORD,
                "<gradient:#3498db:#2980b9><b>Weapons & Combat Perks</b></gradient>",
                List.of(
                        "• Broadsword: sweeps traits across adjacent foes.",
                        "• Longbow & Crossbow: trait-infused projectiles.",
                        "• Elder Trident: storm surges & lightning.",
                        "• Kinetic Spear: +30% sprint charge.",
                        "• War Mace: crushing shockwave smashes.",
                        "• Tower Shield: reflects 35% of blocked damage.",
                        "• Every mineral names the perk; 80%+ essence focus",
                        "  unlocks a cinematic essence ultimate."
                )));

        inventory.setItem(30, createGuideItem(Material.NETHERITE_PICKAXE,
                "<gradient:#2ecc71:#27ae60><b>Tools & Mining</b></gradient>",
                List.of(
                        "• Pickaxe: Vein Resonance drops extra ores.",
                        "• Battleaxe: fells whole trees, breaks shields.",
                        "• Excavator: sneak-dig a 3×3 area.",
                        "• Scythe: 3×3 harvest with auto-replant.",
                        "• Fishing Rod: dredges rare minerals."
                )));

        List<String> arts = new ArrayList<>();
        arts.add("<gray>Each legendary alloy owns its own ability:</gray>");
        for (LegendaryArt art : LegendaryArt.values()) {
            TinkerMaterial alloy = materialRegistry.get(art.getAlloyId());
            String alloyName = alloy != null ? alloy.getName() : art.getAlloyId();
            arts.add("<gradient:" + art.getColorHex() + ":" + art.getAccentHex() + ">" + art.getDisplayName()
                    + "</gradient> <dark_gray>← " + alloyName + "</dark_gray>");
        }
        inventory.setItem(31, createRichItem(Material.TOTEM_OF_UNDYING,
                "<gradient:#ffd700:#ff8c00><b>✦ The 16 Legendary Arts</b></gradient>", arts, true));

        inventory.setItem(32, createGuideItem(Material.NETHERITE_CHESTPLATE,
                "<gradient:#9b59b6:#8e44ad><b>Armor & Defense</b></gradient>",
                List.of(
                        "• Helmet: Cranium Ward blunts headshots.",
                        "• Chestplate: Kinetic Dampener soaks heavy blows.",
                        "• Leggings: Stride Momentum.",
                        "• Boots: Feathered Grounding negates falls.",
                        "• Prime plates answer hits with a prime armor state."
                )));

        inventory.setItem(33, createGuideItem(Material.FIREWORK_STAR,
                "<gradient:#55efc4:#00b894><b>Signature Arts</b></gradient>",
                List.of(
                        "• Composite: named after both minerals, echoes both traits.",
                        "• Legendary: a hand-made art with its own mechanic.",
                        "• Prime: the art ascended + an overlay.",
                        "• Mythic: two arts or a catalyst finale, back to back.",
                        "• Fires on hit; the pedigree sets chance and cooldown."
                )));

        inventory.setItem(40, createGuideItem(Material.WRITABLE_BOOK,
                "<gradient:#ffd700:#ff8c00><b>Quick Start</b></gradient>",
                List.of(
                        "1. Carve molds in Molds & Parts with Clay Bricks.",
                        "2. Fuse minerals into alloys in the Alloy Crucible.",
                        "3. Cast parts: 1 mold + 3 materials.",
                        "4. Assemble weapons, tools and armor.",
                        "5. Fight, mine and defend to evolve them."
                )));
    }

    private ItemStack createGuideItem(Material mat, String title, List<String> lines) {
        return createRichItem(mat, title, lines, false);
    }

    /** A system item whose lore lines are MiniMessage (plain text renders grey). */
    private ItemStack createRichItem(Material mat, String title, List<String> lines, boolean shine) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            for (String line : lines) {
                lore.addAll(LoreWrap.wrap(miniMessage.deserialize("<gray>" + line + "</gray>")
                        .decoration(TextDecoration.ITALIC, false)));
            }
            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if (shine) glint(meta);
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

        inventory.setItem(SLOT_MOLD_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Mold</gold>", List.of("Cycle to the previous mold type.")));

        Material moldIcon = (selectedCast == CastType.BLOCK) ? Material.IRON_BLOCK
                : ((selectedCast == CastType.INGOT) ? Material.IRON_INGOT
                : ((selectedCast == CastType.NUGGET) ? Material.IRON_NUGGET : Material.BRICK));

        inventory.setItem(SLOT_MOLD_SELECTOR, createSystemButton(moldIcon,
                "<gradient:#e67e22:#d35400><b>⚒ Carve Mold: " + selectedCast.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedCast.getDescription(),
                        "",
                        "Mold " + (selectedCastIndex + 1) + " of " + CARVABLE_CASTS.size() + " · costs 1 Clay Brick.",
                        "Click to carve it into your inventory."
                )));

        inventory.setItem(SLOT_MOLD_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Mold ▶</gold>", List.of("Cycle to the next mold type.")));

        inventory.setItem(19, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>▼ Mold / Cast</b></gold>"));
        inventory.setItem(21, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>▼ Material 1</b></yellow>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>▼ Material 2</b></yellow>"));
        inventory.setItem(23, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>▼ Material 3</b></yellow>"));
        inventory.setItem(25, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>▼ Forged Part</b></green>"));

        inventory.setItem(SLOT_PART_CAST, null);
        inventory.setItem(29, createSystemDecor(Material.IRON_BARS, "<dark_gray>┃</dark_gray>"));
        inventory.setItem(SLOT_PART_MAT1, null);
        inventory.setItem(SLOT_PART_MAT2, null);
        inventory.setItem(SLOT_PART_MAT3, null);
        inventory.setItem(33, createSystemDecor(Material.IRON_BARS, "<dark_gray>┃</dark_gray>"));
        inventory.setItem(SLOT_PART_OUTPUT, null);

        inventory.setItem(SLOT_PART_STRIKE, createSystemButton(Material.ANVIL,
                "<gradient:#ffaa00:#ff5500><b>⚒ Strike the Anvil</b></gradient>",
                List.of(
                        "Forges 1 Cast + 3 Materials into a part.",
                        "Each material lends 33% of its stats and trait.",
                        "The cast is reusable and never consumed."
                )));

        renderPartPreview();
    }

    private CastType getSelectedCastType() {
        if (selectedCastIndex < 0 || selectedCastIndex >= CARVABLE_CASTS.size()) {
            selectedCastIndex = 0;
        }
        return CARVABLE_CASTS.get(selectedCastIndex);
    }

    /** Live preview of the part the current cast and materials would forge. */
    private void renderPartPreview() {
        List<String> lines = new ArrayList<>();
        CastType cast = castIn(SLOT_PART_CAST);
        ToolPartType partType = cast != null ? ToolPartType.fromCast(cast) : null;
        List<TinkerMaterial> materials = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        if (cast == null) missing.add("a cast");
        else if (partType == null) lines.add("<red>The " + cast.getDisplayName() + " stores metal, it cannot forge a part.</red>");
        int[] materialSlots = {SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3};
        for (int index = 0; index < materialSlots.length; index++) {
            TinkerMaterial material = getMaterialFromItem(inventory.getItem(materialSlots[index]));
            if (material == null) missing.add("material " + (index + 1));
            else materials.add(material);
        }

        lines.add("Part: <white>" + (partType != null ? partType.getDisplayName() : "—") + "</white>");
        if (!materials.isEmpty()) {
            lines.add("");
            lines.add("<gold>Composition:</gold>");
            PartComposition composition = PartComposition.fromMaterials(materials);
            for (PartComposition.Entry entry : composition.getEntries()) {
                TinkerMaterial m = entry.material();
                lines.add("  <gradient:" + m.getColorHex() + ":#ffffff>" + Math.round(entry.ratio() * 100) + "% "
                        + m.getName() + "</gradient> <dark_gray>(" + m.getTraitName() + ")</dark_gray>");
            }
            lines.add(String.format(Locale.US, "Durability <green>+%d</green> · Attack <red>+%.1f</red> · Speed <aqua>%.1fx</aqua>",
                    composition.getDurability(), composition.getAttackDamage(), composition.getMiningSpeed()));

            ForgeTier tier = ForgeTier.of(List.of(composition));
            SignatureArt art = SignatureArt.forWeapon(List.of(composition));
            lines.add("");
            lines.add("Pedigree: " + tier.badge());
            if (art != null) lines.add("Signature Art: " + art.miniName());
        }

        lines.add("");
        if (missing.isEmpty() && partType != null) {
            lines.add("<green><b>✔ Ready — strike the anvil!</b></green>");
        } else if (!missing.isEmpty()) {
            lines.add("<yellow>★ Missing: <white>" + String.join(", ", missing) + "</white></yellow>");
        }

        boolean ready = missing.isEmpty() && partType != null;
        inventory.setItem(SLOT_PART_PREVIEW, createRichItem(ready ? Material.NAME_TAG : Material.PAPER,
                "<gradient:#ffd700:#ff8c00><b>✦ Part Preview</b></gradient>", lines, ready));
    }

    @Nullable
    private CastType castIn(int slot) {
        ItemStack item = inventory.getItem(slot);
        if (item == null || item.getType() == Material.AIR || isSystemItem(item)) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String name = meta.getPersistentDataContainer().get(TinkerKeys.CAST_TYPE, PersistentDataType.STRING);
        if (name == null) return null;
        try {
            return CastType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // ==========================================
    // TAB 3: ALLOY CRUCIBLE
    // ==========================================
    private void renderTabAlloy() {
        inventory.setItem(13, createRichItem(Material.BLAST_FURNACE,
                "<gradient:#e74c3c:#ff9f43><b>♨ Alloy Smelting Crucible ♨</b></gradient>",
                List.of("Two materials in, two alloy ingots out.",
                        "The core below shows what the fusion will become."), false));

        inventory.setItem(20, createSystemDecor(Material.RED_STAINED_GLASS_PANE, "<red><b>▼ Primary Material</b></red>"));
        inventory.setItem(24, createSystemDecor(Material.BLUE_STAINED_GLASS_PANE, "<blue><b>▼ Secondary Material</b></blue>"));

        inventory.setItem(SLOT_ALLOY_MAT1, null);
        inventory.setItem(30, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold>▶</gold>"));
        inventory.setItem(SLOT_ALLOY_SMELT, createSystemButton(Material.CAMPFIRE,
                "<gradient:#ff4500:#ffa500><b>♨ Ignite the Crucible</b></gradient>",
                List.of(
                        "Consumes 1 of each material, yields 2 alloy ingots.",
                        "",
                        "Any 2 brushed/vanilla minerals → composite alloy.",
                        "16 curated pairs → a legendary alloy.",
                        "Legendary + mineral/composite → prime alloy.",
                        "Legendary + catalyst or legendary → mythic prime.",
                        "Prime alloys cannot be reforged."
                )));
        inventory.setItem(32, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold>◀</gold>"));
        inventory.setItem(SLOT_ALLOY_MAT2, null);

        inventory.setItem(39, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Smelted Alloy ▶</b></green>"));
        inventory.setItem(SLOT_ALLOY_OUTPUT, null);
        inventory.setItem(41, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>◀ Smelted Alloy</b></green>"));

        inventory.setItem(SLOT_ALLOY_RECIPES, createSystemButton(Material.BOOK,
                "<gradient:#ffd700:#ff8c00><b>Alloy Codex</b></gradient>",
                List.of(
                        "Click to open the browsable codex.",
                        "Legendary recipes, catalysts, composites, primes",
                        "and a combination explorer in one menu.",
                        "",
                        "Sneak-click to print the recipe list in chat."
                )));

        renderFusionPreview();
    }

    /** Live preview of the alloy the crucible would fuse, with its pedigree and signature art. */
    private void renderFusionPreview() {
        TinkerMaterial first = getMaterialFromItem(inventory.getItem(SLOT_ALLOY_MAT1));
        TinkerMaterial second = getMaterialFromItem(inventory.getItem(SLOT_ALLOY_MAT2));
        String title = "<gradient:#ff4500:#ffaa00><b>✦ Fusion Preview</b></gradient>";

        if (first == null || second == null) {
            List<String> lines = new ArrayList<>();
            lines.add("Primary: <white>" + (first != null ? first.getName() : "—") + "</white>");
            lines.add("Secondary: <white>" + (second != null ? second.getName() : "—") + "</white>");
            lines.add("");
            lines.add("<yellow>Place two materials to preview the alloy.</yellow>");
            inventory.setItem(SLOT_ALLOY_PREVIEW, createRichItem(Material.CAULDRON, title, lines, false));
            return;
        }
        if (first.getId().equalsIgnoreCase(second.getId())) {
            inventory.setItem(SLOT_ALLOY_PREVIEW, createRichItem(Material.BARRIER, title,
                    List.of("<red>The crucible needs two distinct materials.</red>"), false));
            return;
        }
        if (!alloyRegistry.isCraftablePair(first, second)) {
            inventory.setItem(SLOT_ALLOY_PREVIEW, createRichItem(Material.BARRIER, title,
                    List.of("<red>These two cannot be fused.</red>", "", AlloyRegistry.mixRequirementMessage()), false));
            return;
        }

        TinkerAlloy existing = alloyRegistry.findAlloy(first.getId(), second.getId());
        String name = existing != null ? existing.name() : AlloyRegistry.dynamicName(first, second);
        SignatureArt art = SignatureArt.forPair(first, second);
        ForgeTier tier = art != null ? art.tier() : ForgeTier.COMPOSITE;

        List<String> lines = new ArrayList<>();
        lines.add("<white>" + first.getName() + "</white> <gray>+</gray> <white>" + second.getName() + "</white>");
        lines.add("Result: <gradient:" + (art != null ? art.colorHex() : "#ffd700") + ":#ffffff><b>" + name
                + " Ingot</b></gradient> <gray>×2</gray>" + (existing == null ? " <aqua>(new!)</aqua>" : ""));
        lines.add("Pedigree: " + tier.badge());
        if (art != null) {
            lines.add("");
            lines.add("Signature Art: " + art.miniName());
            lines.add("<dark_gray>" + art.description() + "</dark_gray>");
        }
        if (tier.atLeast(ForgeTier.PRIME)) {
            VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(first.getId());
            if (catalyst == null) catalyst = VanillaCatalyst.byMaterialId(second.getId());
            TraitAffinity lead = leadEssence(first, second);
            PrimeUltimate ultimate = catalyst != null ? catalyst.getUltimate() : PrimeUltimate.fromAffinity(lead);
            PrimeArmorState state = catalyst != null ? catalyst.getArmorState() : PrimeArmorState.fromAffinity(lead);
            lines.add("");
            lines.add("Prime ultimate: " + ultimate.getMiniMessageTag());
            lines.add("Armor state: <light_purple>" + state.getDisplayName() + "</light_purple>");
        }
        lines.add("");
        lines.add("<green>Ignite the crucible to fuse it.</green>");

        inventory.setItem(SLOT_ALLOY_PREVIEW, createRichItem(tierIcon(tier), title, lines, tier.atLeast(ForgeTier.LEGENDARY)));
    }

    @Nonnull
    private static TraitAffinity leadEssence(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        String inherited = TraitAffinity.inherit(first, second);
        String token = inherited.contains(",") ? inherited.substring(0, inherited.indexOf(',')) : inherited;
        try {
            return TraitAffinity.valueOf(token.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return TraitAffinity.PRIMAL;
        }
    }

    @Nonnull
    private static Material tierIcon(@Nonnull ForgeTier tier) {
        return switch (tier) {
            case MINERAL -> Material.RAW_IRON;
            case COMPOSITE -> Material.IRON_INGOT;
            case LEGENDARY -> Material.GOLD_INGOT;
            case PRIME -> Material.NETHERITE_INGOT;
            case MYTHIC -> Material.NETHER_STAR;
        };
    }

    // ==========================================
    // TAB 4: WEAPON ASSEMBLY
    // ==========================================
    private void renderTabWeapons() {
        inventory.setItem(SLOT_WEAPON_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Weapon</gold>", List.of("Select the previous weapon type.")));

        int index = selectedWeaponType.ordinal() + 1;
        inventory.setItem(SLOT_WEAPON_SELECTOR, createSystemButton(selectedWeaponType.getBaseMaterial(),
                THEMES[TAB_WEAPONS].gradient("<b>" + selectedWeaponType.getDisplayName() + "</b>"),
                List.of(
                        selectedWeaponType.getDescription(),
                        "",
                        "Type " + index + " of " + ModularWeaponType.values().length
                                + " · takes " + selectedWeaponType.getPartCount() + " parts.",
                        "Click to cycle to the next weapon."
                )));

        inventory.setItem(SLOT_WEAPON_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Weapon ▶</gold>", List.of("Select the next weapon type.")));

        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gold><b>▼ " + selectedWeaponType.getPart1Name() + "</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE,
                "<yellow><b>▼ " + selectedWeaponType.getPart2Name() + "</b></yellow>"));
        if (!selectedWeaponType.isTwoPart()) {
            inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                    "<aqua><b>▼ " + selectedWeaponType.getPart3Name() + "</b></aqua>"));
        }

        inventory.setItem(SLOT_WEAPON_PART1, null);
        inventory.setItem(SLOT_WEAPON_PART2, null);
        if (!selectedWeaponType.isTwoPart()) {
            inventory.setItem(SLOT_WEAPON_PART3, null);
        } else {
            inventory.setItem(SLOT_WEAPON_PART3, createSystemDecor(Material.GRAY_STAINED_GLASS_PANE,
                    "<dark_gray>Two-part weapon</dark_gray>"));
        }

        inventory.setItem(SLOT_WEAPON_ASSEMBLE, createSystemButton(Material.ANVIL,
                THEMES[TAB_WEAPONS].gradient("<b>⚒ Assemble " + selectedWeaponType.getDisplayName() + "</b>"),
                List.of(
                        "Combines the placed parts into a finished weapon.",
                        "An alloy part awakens its Signature Art."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Weapon ▶</b></green>"));
        inventory.setItem(SLOT_WEAPON_OUTPUT, null);

        renderPerkPreview();
    }

    // ==========================================
    // TAB 5: TOOL ASSEMBLY
    // ==========================================
    private void renderTabTools() {
        inventory.setItem(SLOT_TOOL_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Tool</gold>", List.of("Select the previous tool type.")));

        inventory.setItem(SLOT_TOOL_SELECTOR, createSystemButton(selectedToolType.getBaseMaterial(),
                THEMES[TAB_TOOLS].gradient("<b>" + selectedToolType.getDisplayName() + "</b>"),
                List.of(
                        selectedToolType.getDescription(),
                        "",
                        "Takes 3 parts: Head, Handle, Pommel.",
                        "Click to cycle to the next tool."
                )));

        inventory.setItem(SLOT_TOOL_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Tool ▶</gold>", List.of("Select the next tool type.")));

        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE, "<gold><b>▼ Tool Head</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE, "<yellow><b>▼ Tool Handle</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "<aqua><b>▼ Tool Pommel</b></aqua>"));

        inventory.setItem(SLOT_TOOL_HEAD, null);
        inventory.setItem(SLOT_TOOL_HANDLE, null);
        inventory.setItem(SLOT_TOOL_POMMEL, null);

        inventory.setItem(SLOT_TOOL_ASSEMBLE, createSystemButton(Material.ANVIL,
                THEMES[TAB_TOOLS].gradient("<b>⚒ Assemble " + selectedToolType.getDisplayName() + "</b>"),
                List.of(
                        "Combines Head, Handle and Pommel into a finished tool.",
                        "Inherits mining speed, perks and traits."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Tool ▶</b></green>"));
        inventory.setItem(SLOT_TOOL_OUTPUT, null);

        renderPerkPreview();
    }

    // ==========================================
    // TAB 6: ARMOR ASSEMBLY
    // ==========================================
    private void renderTabArmor() {
        inventory.setItem(SLOT_ARMOR_PREV, createSystemButton(Material.ARROW,
                "<gold>◀ Previous Armor</gold>", List.of("Select the previous armor piece.")));

        inventory.setItem(SLOT_ARMOR_SELECTOR, createSystemButton(selectedArmorType.getBaseMaterial(),
                THEMES[TAB_ARMOR].gradient("<b>" + selectedArmorType.getDisplayName() + "</b>"),
                List.of(
                        selectedArmorType.getDescription(),
                        "",
                        "Takes 3 parts: Plate, Lining, Trim.",
                        "Click to cycle to the next piece."
                )));

        inventory.setItem(SLOT_ARMOR_NEXT, createSystemButton(Material.ARROW,
                "<gold>Next Armor ▶</gold>", List.of("Select the next armor piece.")));

        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gold><b>▼ " + selectedArmorType.getPart1Name() + "</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE,
                "<yellow><b>▼ " + selectedArmorType.getPart2Name() + "</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                "<aqua><b>▼ " + selectedArmorType.getPart3Name() + "</b></aqua>"));

        inventory.setItem(SLOT_ARMOR_PLATE, null);
        inventory.setItem(SLOT_ARMOR_LINING, null);
        inventory.setItem(SLOT_ARMOR_TRIM, null);

        inventory.setItem(SLOT_ARMOR_ASSEMBLE, createSystemButton(Material.ANVIL,
                THEMES[TAB_ARMOR].gradient("<b>⚒ Assemble " + selectedArmorType.getDisplayName() + "</b>"),
                List.of(
                        "Combines Plate, Lining and Trim into finished armor.",
                        "Inherits defense, toughness and defensive traits."
                )));
        inventory.setItem(40, createSystemDecor(Material.LIME_STAINED_GLASS_PANE, "<green><b>Forged Armor ▶</b></green>"));
        inventory.setItem(SLOT_ARMOR_OUTPUT, null);

        renderPerkPreview();
    }

    // ==========================================
    // LIVE PREVIEWS
    // ==========================================

    /**
     * Redraws the live preview of the section on screen. Called when a section is drawn and again one
     * tick after every click or drag: a placed item only lands in its slot once the event delivering it
     * has finished, and re-rendering the section instead would clear the very slots being read.
     */
    private void refreshLivePreviews() {
        refreshQueued = false;
        switch (currentTab) {
            case TAB_PARTS -> renderPartPreview();
            case TAB_ALLOY -> renderFusionPreview();
            case TAB_WEAPONS, TAB_TOOLS, TAB_ARMOR -> renderPerkPreview();
            default -> {
            }
        }
    }

    private void scheduleRefresh() {
        if (currentTab == TAB_INFO || refreshQueued) return;
        refreshQueued = true;
        Bukkit.getScheduler().runTask(plugin, this::refreshLivePreviews);
    }

    /**
     * Draws the perk the current assembly would produce into {@link #SLOT_PERK_PREVIEW}.
     *
     * <p>Does nothing outside the three assembly tabs, so the slot keeps the tab's own backing.</p>
     */
    private void renderPerkPreview() {
        ForgePerkPreview preview = switch (currentTab) {
            case TAB_WEAPONS -> ForgePerkPreview.weapon(selectedWeaponType,
                    compositionIn(SLOT_WEAPON_PART1), compositionIn(SLOT_WEAPON_PART2),
                    selectedWeaponType.isTwoPart() ? null : compositionIn(SLOT_WEAPON_PART3));
            case TAB_TOOLS -> ForgePerkPreview.tool(selectedToolType,
                    compositionIn(SLOT_TOOL_HEAD), compositionIn(SLOT_TOOL_HANDLE), compositionIn(SLOT_TOOL_POMMEL));
            case TAB_ARMOR -> ForgePerkPreview.armor(selectedArmorType,
                    compositionIn(SLOT_ARMOR_PLATE), compositionIn(SLOT_ARMOR_LINING), compositionIn(SLOT_ARMOR_TRIM));
            default -> null;
        };
        if (preview == null) return;

        inventory.setItem(SLOT_PERK_PREVIEW, perkPreviewItem(preview));
    }

    @Nullable
    private PartComposition compositionIn(int slot) {
        ItemStack item = inventory.getItem(slot);
        if (isSystemItem(item)) return null;
        return getCompositionFromPart(item);
    }

    /** The compositions placed in the weapon tab, in forge order, skipping empty slots. */
    @Nonnull
    private List<PartComposition> weaponCompositions() {
        List<PartComposition> parts = new ArrayList<>();
        for (int slot : new int[]{SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3}) {
            if (slot == SLOT_WEAPON_PART3 && selectedWeaponType.isTwoPart()) continue;
            PartComposition part = compositionIn(slot);
            if (part != null) parts.add(part);
        }
        return parts;
    }

    /** The preview as an item: the name it would print, the word each part lends, and the mechanic. */
    @Nonnull
    private ItemStack perkPreviewItem(@Nonnull ForgePerkPreview preview) {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.displayName(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>✦ Perk Preview</b></gradient>")
                .decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(preview.equipment() + " · " + preview.placed() + "/" + preview.required()
                + " parts", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Perk: ", NamedTextColor.GOLD)
                .append(Component.text(preview.name(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));

        if (!preview.parts().isEmpty()) {
            lore.add(Component.empty());
            lore.add(Component.text("✦ Compound epithet:", NamedTextColor.GOLD)
                    .decoration(TextDecoration.ITALIC, false));
            for (ForgePerkPreview.PartLine part : preview.parts()) {
                lore.add(Component.text("  • " + part.position() + ": ", NamedTextColor.GRAY)
                        .append(Component.text(part.epithet(), NamedTextColor.AQUA))
                        .append(Component.text(" (" + part.material() + ")", NamedTextColor.DARK_GRAY))
                        .decoration(TextDecoration.ITALIC, false));
            }
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Mechanic: ", NamedTextColor.GOLD)
                .append(Component.text(preview.mechanic(), preview.color()))
                .decoration(TextDecoration.ITALIC, false));

        if (preview.complete() && preview.description() != null) {
            lore.add(Component.text("  " + preview.description(), NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("✦ Essence focus: ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(preview.focusLine(), preview.color()))
                    .decoration(TextDecoration.ITALIC, false));
        } else {
            lore.add(Component.text("★ Missing: ", NamedTextColor.YELLOW)
                    .append(Component.text(String.join(", ", preview.missing()), NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("The full mechanic is written once every part is in place.", NamedTextColor.DARK_GRAY)
                    .decoration(TextDecoration.ITALIC, false));
        }

        // Weapons also preview the art their alloys would awaken.
        if (currentTab == TAB_WEAPONS) {
            List<PartComposition> parts = weaponCompositions();
            ForgeTier tier = ForgeTier.of(parts);
            SignatureArt art = SignatureArt.forWeapon(parts);
            lore.add(Component.empty());
            lore.add(Component.text("✦ Forge Pedigree: ", NamedTextColor.GOLD)
                    .append(miniMessage.deserialize(tier.badge()))
                    .decoration(TextDecoration.ITALIC, false));
            if (art != null) {
                lore.add(Component.text("✦ Signature Art: ", NamedTextColor.GOLD)
                        .append(miniMessage.deserialize(art.miniName()))
                        .decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("  " + art.description(), NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("  • " + art.procLine(), NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.ITALIC, false));
                if (tier.atLeast(ForgeTier.LEGENDARY)) glint(meta);
            } else {
                lore.add(Component.text("  Place an alloy part to awaken a Signature Art.", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.ITALIC, false));
            }
        }

        meta.lore(LoreWrap.wrapAll(lore));
        markAsSystemItem(meta);
        item.setItemMeta(meta);
        return item;
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
                lore.addAll(LoreWrap.wrap(Component.text(line, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            }
            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            markAsSystemItem(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static void glint(@Nonnull ItemMeta meta) {
        try {
            meta.setEnchantmentGlintOverride(true);
        } catch (RuntimeException | LinkageError ignored) {
            // Older or mock implementations: the glint is purely cosmetic.
        }
    }

    private void markAsSystemItem(ItemMeta meta) {
        meta.getPersistentDataContainer().set(TinkerKeys.SYSTEM_GUI_ITEM, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Whether a slot holds nothing a player owns: it is empty, or it carries the Forge's own marker. A
     * player's real glass pane is the player's item like any other and is handed back on close.
     */
    private boolean isSystemItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return true;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(TinkerKeys.SYSTEM_GUI_ITEM, PersistentDataType.BYTE);
    }

    // ==========================================
    // SLOT ROLES
    // ==========================================

    /** Slots of the current section a player may put items into. */
    @Nonnull
    private Set<Integer> inputSlots() {
        return switch (currentTab) {
            case TAB_PARTS -> Set.of(SLOT_PART_CAST, SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3);
            case TAB_ALLOY -> Set.of(SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2);
            case TAB_WEAPONS -> selectedWeaponType.isTwoPart()
                    ? Set.of(SLOT_WEAPON_PART1, SLOT_WEAPON_PART2)
                    : Set.of(SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3);
            case TAB_TOOLS -> Set.of(SLOT_TOOL_HEAD, SLOT_TOOL_HANDLE, SLOT_TOOL_POMMEL);
            case TAB_ARMOR -> Set.of(SLOT_ARMOR_PLATE, SLOT_ARMOR_LINING, SLOT_ARMOR_TRIM);
            default -> Set.of();
        };
    }

    /** The result slot of a section, or {@code -1} when it produces nothing. */
    private int outputSlot(int tab) {
        return switch (tab) {
            case TAB_PARTS -> SLOT_PART_OUTPUT;
            case TAB_ALLOY -> SLOT_ALLOY_OUTPUT;
            case TAB_WEAPONS -> SLOT_WEAPON_OUTPUT;
            case TAB_TOOLS -> SLOT_TOOL_OUTPUT;
            case TAB_ARMOR -> SLOT_ARMOR_OUTPUT;
            default -> -1;
        };
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
            if (item == null || item.getType() == Material.AIR) continue;
            inventory.setItem(slot, null);
            if (!isSystemItem(item)) giveOrDrop(player, item);
        }
    }

    private static void giveOrDrop(@Nonnull Player player, @Nonnull ItemStack item) {
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
        for (ItemStack drop : leftover.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), drop);
        }
    }

    // ==========================================
    // INTERACTION HANDLING
    // ==========================================
    public void handleClick(@Nonnull InventoryClickEvent event, @Nonnull Player player) {
        int rawSlot = event.getRawSlot();

        // A double-click gathers every matching stack, the Forge's own icons included: never allow it.
        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            event.setCancelled(true);
            return;
        }

        if (rawSlot >= 54) {
            // The player's own inventory. Shift-clicks are routed to the slot the item belongs in.
            if (event.isShiftClick()) {
                event.setCancelled(true);
                routeShiftClick(event, player);
                scheduleRefresh();
            }
            return;
        }
        if (rawSlot < 0) return;

        switch (rawSlot) {
            case SLOT_NAV_INFO -> {
                event.setCancelled(true);
                switchTab(TAB_INFO, player);
                return;
            }
            case SLOT_NAV_PARTS -> {
                event.setCancelled(true);
                switchTab(TAB_PARTS, player);
                return;
            }
            case SLOT_NAV_ALLOY -> {
                event.setCancelled(true);
                switchTab(TAB_ALLOY, player);
                return;
            }
            case SLOT_NAV_WEAPONS -> {
                event.setCancelled(true);
                switchTab(TAB_WEAPONS, player);
                return;
            }
            case SLOT_NAV_TOOLS -> {
                event.setCancelled(true);
                switchTab(TAB_TOOLS, player);
                return;
            }
            case SLOT_NAV_ARMOR -> {
                event.setCancelled(true);
                switchTab(TAB_ARMOR, player);
                return;
            }
            case SLOT_NAV_BORDER_L, SLOT_NAV_BORDER_R, SLOT_NAV_DIVIDER, SLOT_PEDIGREE_GUIDE -> {
                event.setCancelled(true);
                return;
            }
            case SLOT_CLOSE -> {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 0.7f, 1.3f);
                Bukkit.getScheduler().runTask(plugin, () -> player.closeInventory());
                return;
            }
            default -> {
            }
        }

        // The result slot only gives: nothing can be put into it.
        if (rawSlot == outputSlot(currentTab) && placesItem(event, player)) {
            event.setCancelled(true);
            return;
        }

        switch (currentTab) {
            case TAB_INFO -> event.setCancelled(true);
            case TAB_PARTS -> handlePartsClicks(event, player, rawSlot);
            case TAB_ALLOY -> handleAlloyClicks(event, player, rawSlot);
            case TAB_WEAPONS -> handleWeaponsClicks(event, player, rawSlot);
            case TAB_TOOLS -> handleToolsClicks(event, player, rawSlot);
            case TAB_ARMOR -> handleArmorClicks(event, player, rawSlot);
        }

        scheduleRefresh();
    }

    /** Drags may only paint into the section's inputs. */
    public void handleDrag(@Nonnull InventoryDragEvent event) {
        Set<Integer> inputs = inputSlots();
        for (int raw : event.getRawSlots()) {
            if (raw < 54 && !inputs.contains(raw)) {
                event.setCancelled(true);
                return;
            }
        }
        scheduleRefresh();
    }

    /** Whether a click would put an item into the clicked slot. */
    private static boolean placesItem(@Nonnull InventoryClickEvent event, @Nonnull Player player) {
        return switch (event.getAction()) {
            case PLACE_ALL, PLACE_ONE, PLACE_SOME, SWAP_WITH_CURSOR -> true;
            case HOTBAR_SWAP -> {
                int button = event.getHotbarButton();
                ItemStack hotbar = button >= 0 ? player.getInventory().getItem(button) : player.getInventory().getItemInOffHand();
                yield hotbar != null && hotbar.getType() != Material.AIR;
            }
            default -> false;
        };
    }

    /** Moves a shift-clicked stack from the player's inventory into the first slot it belongs in. */
    private void routeShiftClick(@Nonnull InventoryClickEvent event, @Nonnull Player player) {
        ItemStack moving = event.getCurrentItem();
        if (moving == null || moving.getType() == Material.AIR) return;

        int[] targets = shiftTargets(moving);
        if (targets.length == 0) return;

        ItemStack remaining = moving.clone();
        for (int slot : targets) {
            ItemStack there = inventory.getItem(slot);
            if (there == null || there.getType() == Material.AIR) {
                inventory.setItem(slot, remaining);
                remaining = null;
                break;
            }
            if (!isSystemItem(there) && there.isSimilar(remaining)) {
                int room = there.getMaxStackSize() - there.getAmount();
                if (room <= 0) continue;
                int moved = Math.min(room, remaining.getAmount());
                there.setAmount(there.getAmount() + moved);
                remaining.setAmount(remaining.getAmount() - moved);
                if (remaining.getAmount() <= 0) {
                    remaining = null;
                    break;
                }
            }
        }
        event.setCurrentItem(remaining);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 0.6f, 1.4f);
    }

    /** Where a shift-clicked item goes in the current section, in preference order. */
    @Nonnull
    private int[] shiftTargets(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta != null ? meta.getPersistentDataContainer() : null;
        boolean isCast = pdc != null && pdc.has(TinkerKeys.CAST_TYPE, PersistentDataType.STRING);
        boolean isPart = pdc != null && pdc.has(TinkerKeys.PART_COMPOSITION_DATA, PersistentDataType.STRING);

        return switch (currentTab) {
            case TAB_PARTS -> isCast ? new int[]{SLOT_PART_CAST}
                    : (getMaterialFromItem(item) != null ? new int[]{SLOT_PART_MAT1, SLOT_PART_MAT2, SLOT_PART_MAT3} : new int[0]);
            case TAB_ALLOY -> getMaterialFromItem(item) != null ? new int[]{SLOT_ALLOY_MAT1, SLOT_ALLOY_MAT2} : new int[0];
            case TAB_WEAPONS -> !isPart ? new int[0] : (selectedWeaponType.isTwoPart()
                    ? new int[]{SLOT_WEAPON_PART1, SLOT_WEAPON_PART2}
                    : new int[]{SLOT_WEAPON_PART1, SLOT_WEAPON_PART2, SLOT_WEAPON_PART3});
            case TAB_TOOLS -> isPart ? new int[]{SLOT_TOOL_HEAD, SLOT_TOOL_HANDLE, SLOT_TOOL_POMMEL} : new int[0];
            case TAB_ARMOR -> isPart ? new int[]{SLOT_ARMOR_PLATE, SLOT_ARMOR_LINING, SLOT_ARMOR_TRIM} : new int[0];
            default -> new int[0];
        };
    }

    /** Clicks on anything that is neither an input nor the output of the section are cancelled. */
    private void guardSlot(@Nonnull InventoryClickEvent event, int rawSlot) {
        if (!inputSlots().contains(rawSlot) && rawSlot != outputSlot(currentTab)) {
            event.setCancelled(true);
        }
    }

    private void switchTab(int newTab, Player player) {
        if (this.currentTab == newTab) return;
        returnActiveTabItems(player, this.currentTab);
        this.currentTab = newTab;
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
        renderCurrentTab();
    }

    /**
     * Puts a freshly made result into the section's output, stacking it on an identical result. Returns
     * {@code false}, and changes nothing, when a different item is still waiting there.
     */
    private boolean canDeliver(int slot, @Nonnull ItemStack result) {
        ItemStack there = inventory.getItem(slot);
        if (there == null || there.getType() == Material.AIR) return true;
        return there.isSimilar(result) && there.getAmount() + result.getAmount() <= there.getMaxStackSize();
    }

    private void deliver(int slot, @Nonnull ItemStack result) {
        ItemStack there = inventory.getItem(slot);
        if (there == null || there.getType() == Material.AIR) {
            inventory.setItem(slot, result);
        } else {
            there.setAmount(there.getAmount() + result.getAmount());
        }
    }

    private void refuseOccupiedOutput(@Nonnull Player player) {
        player.sendMessage(miniMessage.deserialize(
                "<red>⚠ Take the item waiting in the output slot first — the Forge never overwrites a result.</red>"));
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
    }

    private void handlePartsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_MOLD_PREV) {
            event.setCancelled(true);
            cycleMold(false);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            renderMoldSelectorOnly();
            return;
        }
        if (rawSlot == SLOT_MOLD_NEXT) {
            event.setCancelled(true);
            cycleMold(true);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            renderMoldSelectorOnly();
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
        guardSlot(event, rawSlot);
    }

    /** Redraws only the mold selector, so cycling molds never clears the cast and materials in place. */
    private void renderMoldSelectorOnly() {
        CastType selectedCast = getSelectedCastType();
        Material moldIcon = (selectedCast == CastType.BLOCK) ? Material.IRON_BLOCK
                : ((selectedCast == CastType.INGOT) ? Material.IRON_INGOT
                : ((selectedCast == CastType.NUGGET) ? Material.IRON_NUGGET : Material.BRICK));
        inventory.setItem(SLOT_MOLD_SELECTOR, createSystemButton(moldIcon,
                "<gradient:#e67e22:#d35400><b>⚒ Carve Mold: " + selectedCast.getDisplayName() + "</b></gradient>",
                List.of(
                        selectedCast.getDescription(),
                        "",
                        "Mold " + (selectedCastIndex + 1) + " of " + CARVABLE_CASTS.size() + " · costs 1 Clay Brick.",
                        "Click to carve it into your inventory."
                )));
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

        ItemStack cast = itemRegistry.getCastItem(castType);
        if (cast == null) return;
        player.getInventory().removeItem(new ItemStack(Material.BRICK, 1));
        giveOrDrop(player, cast);
        player.playSound(player.getLocation(), Sound.BLOCK_GRAVEL_PLACE, 1.0f, 1.4f);
        sendSuccessMessage(player, "Successfully carved", cast);
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
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place a Cast and all 3 Materials — the part preview lists what is missing.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        CastType castType = castIn(SLOT_PART_CAST);
        if (castType == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid Cast! Must be a recognized Tinker Casting Mold.</red>"));
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
            player.sendMessage(miniMessage.deserialize("<red>⚠ All 3 material slots must contain recognized Tinker Materials or Molten Buckets!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        PartComposition composition = PartComposition.fromMaterials(List.of(m1, m2, m3));
        ItemStack forgedPart = TinkerItemBuilder.createModularPart(partType, composition);
        if (!canDeliver(SLOT_PART_OUTPUT, forgedPart)) {
            refuseOccupiedOutput(player);
            return;
        }

        decrementSlot(SLOT_PART_MAT1);
        decrementSlot(SLOT_PART_MAT2);
        decrementSlot(SLOT_PART_MAT3);
        deliver(SLOT_PART_OUTPUT, forgedPart);

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
            // Opening the codex is a decision of its own, so the book button honours access.codex too.
            if (!AccessControl.allows(player, AccessControl.Surface.CODEX)) {
                player.sendActionBar(AccessControl.denial(AccessControl.Surface.CODEX));
                return;
            }
            if (player.isSneaking()) {
                // Sneak-click keeps the old chat dump for screenshots and logs.
                displayAlloyRecipes(player);
            } else {
                AlloyCodexGUI codex = new AlloyCodexGUI(plugin, itemRegistry, materialRegistry);
                codex.setCameFromForge(true);
                player.openInventory(codex.getInventory());
            }
            return;
        }
        guardSlot(event, rawSlot);
    }

    private void smeltAlloy(Player player) {
        ItemStack mat1 = inventory.getItem(SLOT_ALLOY_MAT1);
        ItemStack mat2 = inventory.getItem(SLOT_ALLOY_MAT2);

        if (mat1 == null || mat2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place 2 materials in the crucible inputs.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        TinkerMaterial m1 = getMaterialFromItem(mat1);
        TinkerMaterial m2 = getMaterialFromItem(mat2);

        if (m1 == null || m2 == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Both slots must contain recognized Tinker materials or molten buckets!</red>"));
            return;
        }

        if (m1.getId().equalsIgnoreCase(m2.getId())) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ The crucible requires 2 distinct minerals to synthesize an alloy!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        // Curated legendary recipes stay craftable even when one parent (vanilla netherite) is not
        // freely blendable; every other pair must be made of two brush/vanilla minerals.
        if (!alloyRegistry.isCraftablePair(m1, m2)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ " + AlloyRegistry.mixRequirementMessage() + "</red>"));
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
        if (!canDeliver(SLOT_ALLOY_OUTPUT, alloyIngot)) {
            refuseOccupiedOutput(player);
            return;
        }

        decrementSlot(SLOT_ALLOY_MAT1);
        decrementSlot(SLOT_ALLOY_MAT2);
        deliver(SLOT_ALLOY_OUTPUT, alloyIngot);

        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.4f);
        player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1.0f, 1.0f);
        player.spawnParticle(Particle.FLAME, player.getLocation().add(0, 1.2, 0), 20, 0.3, 0.3, 0.3, 0.05);

        boolean prime = AlloyRegistry.isPrimeParents(alloy.mat1Id(), alloy.mat2Id());
        SignatureArt art = SignatureArt.of(resultMaterial);
        ForgeTier tier = ForgeTier.of(resultMaterial);
        String headline = prime ? "<gold>♨ PRIME FUSION: </gold>" : "<gold>♨ Crucible Synthesized: </gold>";
        player.sendMessage(miniMessage.deserialize(headline
                + "<gradient:" + resultMaterial.getColorHex() + ":#ffffff><b>" + resultMaterial.getName() + " Ingot</b></gradient>"
                + " <gray>(x2)</gray>! " + tier.badge()));
        player.sendMessage(miniMessage.deserialize("<gray>  ➤ " + alloy.traitName() + ": </gray><dark_aqua>" + alloy.traitDescription() + "</dark_aqua>"));
        if (art != null) {
            player.sendMessage(miniMessage.deserialize("<gray>  ✦ Signature art: </gray>" + art.miniName()));
        }
        if (prime) {
            PrimeArmorState armorState = PrimeArmorState.of(resultMaterial);
            PrimeUltimate ultimate = PrimeUltimate.forWeapon(
                    List.of(PartComposition.fromMaterials(List.of(resultMaterial))), null);
            player.sendMessage(miniMessage.deserialize("<gray>  ⚡ Prime ultimate: </gray><white>"
                    + (ultimate != null ? ultimate.getDisplayName() : "Supernova") + "</white><gray> · armor state: </gray><white>"
                    + armorState.getDisplayName() + "</white>"));
        }
        if (art != null && tier.atLeast(ForgeTier.LEGENDARY)) {
            SignatureArtEngine.playForgeAwakening(plugin, player, art);
        }
    }

    /**
     * Total distinct alloy combinations the crucible can fuse: every pair of brush/vanilla minerals
     * plus the curated recipes whose parent is itself an alloy (Cinder Steel, Cosmic Netherite).
     */
    private long possibleAlloyPairs() {
        long blendable = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count();
        long pairs = blendable * (blendable - 1) / 2;
        return pairs + curatedOnlyPairs();
    }

    /** Pairs accepted by the crucible that are not two freely blendable minerals. */
    private long curatedOnlyPairs() {
        List<TinkerMaterial> all = new ArrayList<>(materialRegistry.getAll());
        long count = 0;
        for (int i = 0; i < all.size(); i++) {
            for (int j = i + 1; j < all.size(); j++) {
                TinkerMaterial a = all.get(i);
                TinkerMaterial b = all.get(j);
                if (AlloyRegistry.isMixable(a) && AlloyRegistry.isMixable(b)) continue;
                if (alloyRegistry.isCraftablePair(a, b)) count++;
            }
        }
        return count;
    }

    private void displayAlloyRecipes(Player player) {
        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>══════════ MULTIVERSE ALLOY CODEX ══════════</b></gradient>"));
        player.sendMessage(miniMessage.deserialize("<gray>Blend any two brush-extracted or vanilla minerals. Every pair yields a unique alloy whose trait adapts to weapons, tools and armor.</gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Curated recipes: <yellow>" + (alloyRegistry.getAllAlloys().size() - alloyRegistry.getDynamicAlloyCount())
                + "</yellow> · player-forged composites: <yellow>" + alloyRegistry.getDynamicAlloyCount()
                + "</yellow> · prime alloys: <yellow>" + alloyRegistry.getPrimeAlloyCount()
                + "</yellow> of <yellow>" + possibleAlloyPairs() + "</yellow> possible alloy combinations.</gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Prime fusion: pair a <yellow>legendary alloy</yellow> with another alloy, a mineral or one of the "
                + VanillaCatalyst.values().length + " vanilla catalysts to unlock prime ultimates and armor states.</gray>"));
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            TinkerMaterial res = materialRegistry.get(alloy.id());
            String resName = (res != null) ? res.getName() : alloy.name();
            String resColor = (res != null) ? res.getColorHex() : "#ffd700";
            TinkerMaterial m1 = materialRegistry.get(alloy.mat1Id());
            TinkerMaterial m2 = materialRegistry.get(alloy.mat2Id());
            String n1 = (m1 != null) ? m1.getName() : alloy.mat1Id();
            String n2 = (m2 != null) ? m2.getName() : alloy.mat2Id();
            SignatureArt art = res != null ? SignatureArt.of(res) : null;

            player.sendMessage(miniMessage.deserialize(
                    " <gradient:" + resColor + ":#ffffff><b>" + resName + "</b></gradient> <gray>←</gray> "
                            + "<yellow>" + n1 + "</yellow> <gray>+</gray> <yellow>" + n2 + "</yellow> "
                            + (art != null ? "<gray>✦</gray> " + art.miniName() + " " : "")
                            + "<dark_gray>(" + alloy.traitDescription() + ")</dark_gray>"
            ));
        }
        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>═════════════════════════════════════════════</b></gradient>"));
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
    }

    private void handleWeaponsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_WEAPON_PREV || rawSlot == SLOT_WEAPON_NEXT || rawSlot == SLOT_WEAPON_SELECTOR) {
            event.setCancelled(true);
            // Changing the type may drop the third slot, so its part goes back to the player first.
            returnActiveTabItems(player, TAB_WEAPONS);
            cycleWeapon(rawSlot != SLOT_WEAPON_PREV);
            renderCurrentTab();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_WEAPON_ASSEMBLE) {
            event.setCancelled(true);
            assembleWeapon(player);
            return;
        }
        guardSlot(event, rawSlot);
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
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing required weapon parts! The perk preview lists what is missing.</red>"));
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
        if (!canDeliver(SLOT_WEAPON_OUTPUT, weapon)) {
            refuseOccupiedOutput(player);
            return;
        }

        decrementSlot(SLOT_WEAPON_PART1);
        decrementSlot(SLOT_WEAPON_PART2);
        if (!selectedWeaponType.isTwoPart()) {
            decrementSlot(SLOT_WEAPON_PART3);
        }
        deliver(SLOT_WEAPON_OUTPUT, weapon);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", weapon);

        List<PartComposition> parts = new ArrayList<>(List.of(c1, c2));
        if (c3 != null) parts.add(c3);
        SignatureArt art = SignatureArt.forWeapon(parts);
        if (art != null) {
            player.sendMessage(miniMessage.deserialize("<gray>  ✦ Signature art awakened: </gray>" + art.miniName()
                    + " " + art.tier().badge()));
            SignatureArtEngine.playForgeAwakening(plugin, player, art);
        }
    }

    private void handleToolsClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_TOOL_PREV || rawSlot == SLOT_TOOL_NEXT || rawSlot == SLOT_TOOL_SELECTOR) {
            event.setCancelled(true);
            cycleTool(rawSlot != SLOT_TOOL_PREV);
            renderToolSelectorOnly();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_TOOL_ASSEMBLE) {
            event.setCancelled(true);
            assembleTool(player);
            return;
        }
        guardSlot(event, rawSlot);
    }

    /** Every tool takes the same three parts, so cycling the type keeps the parts in place. */
    private void renderToolSelectorOnly() {
        inventory.setItem(SLOT_TOOL_SELECTOR, createSystemButton(selectedToolType.getBaseMaterial(),
                THEMES[TAB_TOOLS].gradient("<b>" + selectedToolType.getDisplayName() + "</b>"),
                List.of(
                        selectedToolType.getDescription(),
                        "",
                        "Takes 3 parts: Head, Handle, Pommel.",
                        "Click to cycle to the next tool."
                )));
        inventory.setItem(SLOT_TOOL_ASSEMBLE, createSystemButton(Material.ANVIL,
                THEMES[TAB_TOOLS].gradient("<b>⚒ Assemble " + selectedToolType.getDisplayName() + "</b>"),
                List.of(
                        "Combines Head, Handle and Pommel into a finished tool.",
                        "Inherits mining speed, perks and traits."
                )));
        renderPerkPreview();
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
        if (!canDeliver(SLOT_TOOL_OUTPUT, tool)) {
            refuseOccupiedOutput(player);
            return;
        }

        decrementSlot(SLOT_TOOL_HEAD);
        decrementSlot(SLOT_TOOL_HANDLE);
        decrementSlot(SLOT_TOOL_POMMEL);
        deliver(SLOT_TOOL_OUTPUT, tool);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", tool);
    }

    private void handleArmorClicks(InventoryClickEvent event, Player player, int rawSlot) {
        if (rawSlot == SLOT_ARMOR_PREV || rawSlot == SLOT_ARMOR_NEXT || rawSlot == SLOT_ARMOR_SELECTOR) {
            event.setCancelled(true);
            cycleArmor(rawSlot != SLOT_ARMOR_PREV);
            renderArmorSelectorOnly();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            return;
        }
        if (rawSlot == SLOT_ARMOR_ASSEMBLE) {
            event.setCancelled(true);
            assembleArmor(player);
            return;
        }
        guardSlot(event, rawSlot);
    }

    /** Every armor piece takes plate, lining and trim, so cycling the piece keeps the parts in place. */
    private void renderArmorSelectorOnly() {
        inventory.setItem(SLOT_ARMOR_SELECTOR, createSystemButton(selectedArmorType.getBaseMaterial(),
                THEMES[TAB_ARMOR].gradient("<b>" + selectedArmorType.getDisplayName() + "</b>"),
                List.of(
                        selectedArmorType.getDescription(),
                        "",
                        "Takes 3 parts: Plate, Lining, Trim.",
                        "Click to cycle to the next piece."
                )));
        inventory.setItem(20, createSystemDecor(Material.ORANGE_STAINED_GLASS_PANE,
                "<gold><b>▼ " + selectedArmorType.getPart1Name() + "</b></gold>"));
        inventory.setItem(22, createSystemDecor(Material.YELLOW_STAINED_GLASS_PANE,
                "<yellow><b>▼ " + selectedArmorType.getPart2Name() + "</b></yellow>"));
        inventory.setItem(24, createSystemDecor(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                "<aqua><b>▼ " + selectedArmorType.getPart3Name() + "</b></aqua>"));
        inventory.setItem(SLOT_ARMOR_ASSEMBLE, createSystemButton(Material.ANVIL,
                THEMES[TAB_ARMOR].gradient("<b>⚒ Assemble " + selectedArmorType.getDisplayName() + "</b>"),
                List.of(
                        "Combines Plate, Lining and Trim into finished armor.",
                        "Inherits defense, toughness and defensive traits."
                )));
        renderPerkPreview();
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
        if (!canDeliver(SLOT_ARMOR_OUTPUT, armor)) {
            refuseOccupiedOutput(player);
            return;
        }

        decrementSlot(SLOT_ARMOR_PLATE);
        decrementSlot(SLOT_ARMOR_LINING);
        decrementSlot(SLOT_ARMOR_TRIM);
        deliver(SLOT_ARMOR_OUTPUT, armor);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        sendSuccessMessage(player, "Masterfully assembled", armor);
    }

    @Nullable
    private TinkerMaterial getMaterialFromItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR || isSystemItem(item)) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            // A cast or a forged part is never a crucible or casting material.
            if (pdc.has(TinkerKeys.CAST_TYPE, PersistentDataType.STRING)
                    || pdc.has(TinkerKeys.PART_COMPOSITION_DATA, PersistentDataType.STRING)) {
                return null;
            }
            String matId = pdc.get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
            if (matId != null) {
                TinkerMaterial tm = materialRegistry.get(matId);
                if (tm != null) return tm;
            }
        }

        // Vanilla catalyst items (Nether Star, Blue Ice, Echo Shard…) fuel prime fusions.
        VanillaCatalyst catalyst = VanillaCatalyst.byItem(item.getType());
        if (catalyst != null) {
            TinkerMaterial catalystMaterial = materialRegistry.get(catalyst.getMaterialId());
            if (catalystMaterial != null) return catalystMaterial;
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
