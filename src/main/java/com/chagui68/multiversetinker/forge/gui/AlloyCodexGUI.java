package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.PerkEpithet;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Browsable alloy codex: every curated recipe, every vanilla catalyst, every composite and prime the
 * server has discovered, plus a combination explorer that lists - for any single material - every
 * partner the crucible would accept.
 *
 * <p>Browsing is completely side effect free: previewed combinations are named with the same helpers
 * the crucible uses, but nothing is registered until a player actually smelts it.</p>
 */
public class AlloyCodexGUI implements InventoryHolder {

    /** Sections reachable from the top navigation row. */
    public enum Section {
        LEGENDARY(Material.NETHERITE_INGOT, "<gradient:#ffd700:#ff8c00><b>Legendary Recipes</b></gradient>",
                "The 16 curated alloys with hand-written traits."),
        CATALYSTS(Material.NETHER_STAR, "<gradient:#9b59b6:#e056fd><b>Prime Catalysts</b></gradient>",
                "The 12 vanilla items that catalyse prime alloys."),
        COMPOSITES(Material.CRAFTING_TABLE, "<gradient:#3498db:#2980b9><b>Forged Composites</b></gradient>",
                "Every composite alloy this server has discovered."),
        PRIMES(Material.BEACON, "<gradient:#6c5ce7:#a29bfe><b>Prime Alloys</b></gradient>",
                "Every legendary fusion forged so far, with its spectacle."),
        EXPLORER(Material.COMPASS, "<gradient:#2ecc71:#27ae60><b>Combination Explorer</b></gradient>",
                "Pick a material and see every partner the crucible accepts."),
        SUMMARY(Material.BOOK, "<gradient:#f1c40f:#e67e22><b>Alloy Space Summary</b></gradient>",
                "How many alloys exist, by category."),
        MINERALS(Material.AMETHYST_CLUSTER, "<gradient:#00ffaa:#00aaff><b>Mineral Catalog</b></gradient>",
                "Every material this server knows — and every registered item id, down to the last part.");

        private final Material icon;
        private final String title;
        private final String description;

        Section(Material icon, String title, String description) {
            this.icon = icon;
            this.title = title;
            this.description = description;
        }
    }

    private static final int SIZE = 54;
    private static final int CONTENT_START = 9;
    private static final int CONTENT_SLOTS = 36;
    private static final int SLOT_BACK = 0;
    private static final int SLOT_SECTION_FIRST = 1;
    /** The top row is fully used by the sections, so the controls live in the bottom row. */
    private static final int SLOT_PRINT = 47;
    /** Catalog scope toggle: curated materials or the flat item registry. */
    private static final int SLOT_SCOPE = 46;
    /** Kind filter of the flat item registry. */
    private static final int SLOT_KIND = 48;
    private static final int SLOT_CLOSE = 8;
    private static final int SLOT_PREV = 45;
    private static final int SLOT_INFO = 49;
    private static final int SLOT_NEXT = 53;
    /** Catalog filter buttons: dimension, rarity and essence. */
    private static final int SLOT_ORIGIN_FILTER = 50;
    private static final int SLOT_RARITY_FILTER = 51;
    private static final int SLOT_ESSENCE_FILTER = 52;

    /**
     * Which catalog filter the player is choosing.
     *
     * <p>Clicking a filter button swaps the content grid for the list of values it accepts, so a
     * specific essence is two clicks away instead of twelve: cycling a button through 12 essences, 5
     * rarities and 4 dimensions would have hidden the filter behind a dozen clicks.</p>
     */
    public enum FilterKind {
        ORIGIN,
        RARITY,
        ESSENCE
    }

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final AlloyRegistry alloyRegistry;
    private final TinkerItemRegistry itemRegistry;
    private final Inventory inventory;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private Section section = Section.LEGENDARY;
    private int page = 0;
    /** Material selected in the explorer, or {@code null} while choosing one. */
    @Nullable
    private TinkerMaterial explorerTarget;
    /** True while the catalog lists every registered item id instead of the curated materials. */
    private boolean registryScope;
    /** Kind filter of the registry scope; {@code null} lists every kind. */
    @Nullable
    private TinkerItemRegistry.ItemKind kindFilter;
    /** Material the catalog is drilled into, or {@code null} while listing materials. */
    @Nullable
    private TinkerMaterial catalogTarget;
    /** Catalog filter by dimension, or {@code null} for every dimension. */
    @Nullable
    private MineralOrigin originFilter;
    /** Catalog filter by rarity, or {@code null} for every rarity. */
    @Nullable
    private MaterialRarity rarityFilter;
    /** Catalog filter by essence, or {@code null} for every essence. */
    @Nullable
    private TraitAffinity essenceFilter;
    /** Filter the player is currently choosing, or {@code null} while browsing the catalog. */
    @Nullable
    private FilterKind filterPicker;
    /** True when the codex was opened from the forge GUI, so closing returns there. */
    private boolean cameFromForge;

    public AlloyCodexGUI(@Nonnull MultiverseTinker plugin,
                         @Nonnull TinkerItemRegistry itemRegistry,
                         @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
        this.alloyRegistry = (plugin.getAlloyRegistry() != null) ? plugin.getAlloyRegistry() : new AlloyRegistry();
        this.inventory = Bukkit.createInventory(this, SIZE,
                miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>Alloy Codex</b></gradient>"));
        render();
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /** Marks the codex as opened from the forge so closing it returns to the forge GUI. */
    public void setCameFromForge(boolean cameFromForge) {
        this.cameFromForge = cameFromForge;
    }

    public boolean isCameFromForge() {
        return cameFromForge;
    }

    @Nullable
    public TinkerMaterial getExplorerTarget() {
        return explorerTarget;
    }

    // ==========================================
    // RENDERING
    // ==========================================
    public void render() {
        inventory.clear();
        renderNavBar();

        if (isChoosingFilter()) {
            renderContent(filterPickerEntries());
            renderCatalogControls();
            return;
        }

        if (isFlatRegistry()) {
            // The registry holds thousands of ids, so only the visible page is ever turned into items.
            List<String> ids = registryIds();
            int pages = Math.max(1, (int) Math.ceil(ids.size() / (double) CONTENT_SLOTS));
            page = Math.max(0, Math.min(page, pages - 1));

            int from = page * CONTENT_SLOTS;
            for (int i = 0; i < CONTENT_SLOTS && from + i < ids.size(); i++) {
                inventory.setItem(CONTENT_START + i, registryEntry(ids.get(from + i)));
            }
            if (ids.isEmpty()) {
                inventory.setItem(22, emptyState(section));
            }
            renderPager(pages);
            renderInfo(ids.size(), pages);
            renderCatalogControls();
            return;
        }

        renderContent(contentFor(section));
    }

    /**
     * Draws one page of a content list and its pager, clamping the page to the available range.
     *
     * <p>Shared by every section, including the catalog filter picker and the flat registry.</p>
     */
    private void renderContent(@Nonnull List<ItemStack> content) {
        int pages = Math.max(1, (int) Math.ceil(content.size() / (double) CONTENT_SLOTS));
        page = Math.max(0, Math.min(page, pages - 1));

        int from = page * CONTENT_SLOTS;
        for (int i = 0; i < CONTENT_SLOTS && from + i < content.size(); i++) {
            inventory.setItem(CONTENT_START + i, content.get(from + i));
        }

        if (content.isEmpty()) {
            inventory.setItem(22, emptyState(section));
        }

        renderPager(pages);
        renderInfo(content.size(), pages);
        renderCatalogControls();
    }

    private void renderPager(int pages) {
        inventory.setItem(SLOT_PREV, page > 0
                ? button(Material.ARROW, "<gold>◀ Previous Page</gold>", List.of("Page " + (page + 1) + " of " + pages))
                : decor(Material.GRAY_STAINED_GLASS_PANE, " "));
        inventory.setItem(SLOT_NEXT, page + 1 < pages
                ? button(Material.ARROW, "<gold>Next Page ▶</gold>", List.of("Page " + (page + 1) + " of " + pages))
                : decor(Material.GRAY_STAINED_GLASS_PANE, " "));
    }

    private void renderInfo(int entries, int pages) {
        List<String> lore = new ArrayList<>();
        lore.add("<gray>" + section.description + "</gray>");
        if (section != Section.SUMMARY) {
            // One legend for every section that lists a mineral, so the word on each entry has a meaning
            // a player can find without leaving the codex.
            lore.add("");
            lore.add("<gold>✦ Perk epithet</gold> <gray>is the word a mineral lends to the name of the"
                    + " perk it forges.</gray>");
            lore.add("<gray>Read a mineral's word here before you spend it.</gray>");
        }
        lore.add("");
        lore.add("<gray>Entries: <yellow>" + entries + "</yellow> · Page <yellow>" + (page + 1)
                + "</yellow>/<yellow>" + pages + "</yellow></gray>");
        if (section == Section.MINERALS) {
            if (isChoosingFilter()) {
                lore.add("<yellow>Choosing " + filterPicker.name().toLowerCase(Locale.ROOT)
                        + ":</yellow> <gray>click an option to apply it.</gray>");
                lore.add("<gray>Next page for more values.</gray>");
            } else {
                lore.add("<gray>Scope: <yellow>" + (registryScope ? "every item id" : "materials") + "</yellow>"
                        + (catalogTarget != null ? " · drilled into <yellow>" + catalogTarget.getName() + "</yellow>" : "")
                        + "</gray>");
                if (registryScope) {
                    lore.add("<gray>Kind filter: <yellow>" + (kindFilter != null ? kindFilter.name() : "ALL") + "</yellow></gray>");
                }
                lore.add("<gray>Filters: " + filterSummary() + "</gray>");
            }
        }
        inventory.setItem(SLOT_INFO, button(Material.PAPER,
                "<yellow><b>" + section.title.replaceAll("<[^>]*>", "") + "</b></yellow>", lore));
    }

    /** Scope and kind controls, shown only by the catalog section. */
    private void renderCatalogControls() {
        if (section != Section.MINERALS) return;

        boolean flat = registryScope && catalogTarget == null;
        List<String> scopeLore = new ArrayList<>();
        scopeLore.add("<gray>Materials: <yellow>" + materialRegistry.getAll().size()
                + "</yellow> curated entries with their traits.</gray>");
        scopeLore.add("<gray>Every item: <yellow>" + itemRegistry.getAvailableItemIdCount()
                + "</yellow> registered ids, including parts and casts.</gray>");
        scopeLore.add("");
        scopeLore.add("<yellow>Click to switch scope.</yellow>");
        inventory.setItem(SLOT_SCOPE, button(Material.CHEST,
                "<gold><b>Scope: " + (flat ? "Every item" : "Materials") + "</b></gold>", scopeLore));

        List<String> kindLore = new ArrayList<>();
        kindLore.add("<gray>Filters the <yellow>Every item</yellow> scope by item kind:</gray>");
        kindLore.add("<gray>raw, ingot, nugget, block, molten bucket and the ten part types.</gray>");
        kindLore.add("");
        kindLore.add(flat ? "<yellow>Click to cycle the kind.</yellow>" : "<dark_gray>Switch scope to use it.</dark_gray>");
        inventory.setItem(SLOT_KIND, button(flat ? Material.HOPPER : Material.GRAY_DYE,
                "<gold><b>Kind: " + (kindFilter != null ? kindFilter.name() : "ALL") + "</b></gold>", kindLore));

        // The three catalog filters. Each one is a picker: cycling 12 essences on click would hide the
        // filter behind a dozen presses.
        inventory.setItem(SLOT_ORIGIN_FILTER, button(originFilter == null ? Material.COMPASS : Material.FILLED_MAP,
                "<gold><b>Dimension: " + (originFilter == null ? "Any" : originFilter.getDescription()) + "</b></gold>",
                filterLore(FilterKind.ORIGIN, originFilter == null ? null : originFilter.getDescription(),
                        countMatching(originFilter, rarityFilter, essenceFilter))));
        inventory.setItem(SLOT_RARITY_FILTER, button(rarityFilter == null ? Material.PAPER : Material.NAME_TAG,
                "<gold><b>Rarity: " + (rarityFilter == null ? "Any" : rarityFilter.getDisplayName()) + "</b></gold>",
                filterLore(FilterKind.RARITY, rarityFilter == null ? null : rarityFilter.getDisplayName(),
                        countMatching(originFilter, rarityFilter, essenceFilter))));
        inventory.setItem(SLOT_ESSENCE_FILTER, button(essenceFilter == null ? Material.AMETHYST_SHARD : Material.GLOWSTONE_DUST,
                "<gold><b>Essence: " + (essenceFilter == null ? "Any" : essenceFilter.getDisplayName()) + "</b></gold>",
                filterLore(FilterKind.ESSENCE, essenceFilter == null ? null : essenceFilter.getDisplayName(),
                        countMatching(originFilter, rarityFilter, essenceFilter))));
    }

    /** Lore shared by the three filter buttons: what it narrows, what is active and how many match. */
    @Nonnull
    private List<String> filterLore(@Nonnull FilterKind kind, @Nullable String current, int matches) {
        List<String> lore = new ArrayList<>();
        lore.add(switch (kind) {
            case ORIGIN -> "<gray>Narrows the catalog to one <yellow>dimension</yellow>.</gray>";
            case RARITY -> "<gray>Narrows the catalog to one <yellow>rarity</yellow>.</gray>";
            case ESSENCE -> "<gray>Narrows the catalog to the materials that teach</gray>\n<gray>one <yellow>essence</yellow>, whatever else they carry.</gray>";
        });
        lore.add(switch (kind) {
            case ORIGIN -> "<dark_gray>Overworld · The Nether · The End · Vanilla ores</dark_gray>";
            case RARITY -> "<dark_gray>Common · Uncommon · Rare · Epic · Legendary</dark_gray>";
            case ESSENCE -> "<dark_gray>" + TraitAffinity.values().length + " essences, from Infernal to Ascendant</dark_gray>";
        });
        lore.add("");
        lore.add("<gray>Current: " + (current == null ? "<yellow>Any</yellow>" : "<yellow>" + current + "</yellow>")
                + " · Matching materials: <yellow>" + matches + "</yellow></gray>");
        lore.add("");
        lore.add(isChoosingFilter(kind)
                ? "<green>✔ Choosing it right now — click an option above.</green>"
                : "<yellow>Click to choose a value.</yellow>");
        return lore;
    }

    /** True while the given filter is the one whose options are on screen. */
    private boolean isChoosingFilter(@Nonnull FilterKind kind) {
        return filterPicker == kind;
    }

    /** True while the catalog is listing the options of a filter instead of the catalog itself. */
    private boolean isChoosingFilter() {
        return section == Section.MINERALS && filterPicker != null;
    }

    /**
     * Options of the filter being chosen: <b>Any</b> first, then every accepted value.
     *
     * <p>Each option announces how many materials it would leave, so the player can tell an empty
     * combination from a populated one before clicking.</p>
     */
    @Nonnull
    private List<ItemStack> filterPickerEntries() {
        List<ItemStack> entries = new ArrayList<>();
        FilterKind picking = filterPicker;
        if (picking == null) return entries;

        entries.add(button(Material.BARRIER, "<red><b>Any</b></red>",
                List.of("<gray>Clear the " + picking.name().toLowerCase(Locale.ROOT) + " filter.</gray>",
                        "<gray>Matching materials: <yellow>"
                                + countMatching(picking == FilterKind.ORIGIN ? null : originFilter,
                                        picking == FilterKind.RARITY ? null : rarityFilter,
                                        picking == FilterKind.ESSENCE ? null : essenceFilter) + "</yellow></gray>",
                        "",
                        "<yellow>Click to list every material again.</yellow>")));

        switch (picking) {
            case ORIGIN -> {
                for (MineralOrigin origin : MineralOrigin.values()) {
                    boolean active = originFilter == origin;
                    entries.add(button(originIcon(origin),
                            "<gold><b>" + origin.getDescription() + "</b></gold>",
                            List.of("<gray>Source: <yellow>" + origin.getSourceBlockName() + "</yellow></gray>",
                                    "<gray>Materials: <yellow>" + countMatching(origin, rarityFilter, essenceFilter) + "</yellow></gray>",
                                    "",
                                    active ? "<green>✔ Active filter</green>" : "<yellow>Click to filter by this dimension.</yellow>")));
                }
            }
            case RARITY -> {
                for (MaterialRarity rarity : MaterialRarity.values()) {
                    boolean active = rarityFilter == rarity;
                    entries.add(button(rarityIcon(rarity),
                            "<gold><b>" + rarity.getDisplayName() + "</b></gold>",
                            List.of("<gray>Rarer materials drop less often and forge stronger.</gray>",
                                    "<gray>Materials: <yellow>" + countMatching(originFilter, rarity, essenceFilter) + "</yellow></gray>",
                                    "",
                                    active ? "<green>✔ Active filter</green>" : "<yellow>Click to filter by this rarity.</yellow>")));
                }
            }
            case ESSENCE -> {
                for (TraitAffinity essence : TraitAffinity.values()) {
                    boolean active = essenceFilter == essence;
                    entries.add(button(essenceIcon(essence),
                            "<gold><b>" + essence.getDisplayName() + "</b></gold>",
                            List.of("<gray>On weapons: <yellow>" + essence.getWeaponEffect() + "</yellow></gray>",
                                    "<gray>On tools: <yellow>" + essence.getToolEffect() + "</yellow></gray>",
                                    "<gray>On armor: <yellow>" + essence.getArmorEffect() + "</yellow></gray>",
                                    "<gray>Materials: <yellow>" + countMatching(originFilter, rarityFilter, essence) + "</yellow></gray>",
                                    "",
                                    active ? "<green>✔ Active filter</green>" : "<yellow>Click to filter by this essence.</yellow>")));
                }
            }
        }
        return entries;
    }

    /** Icon that identifies a dimension in the filter grid. */
    @Nonnull
    private static Material originIcon(@Nonnull MineralOrigin origin) {
        return switch (origin) {
            case OVERWORLD -> Material.GRASS_BLOCK;
            case NETHER -> Material.NETHERRACK;
            case THE_END -> Material.END_STONE;
            case VANILLA -> Material.CRAFTING_TABLE;
        };
    }

    /** Icon that identifies a rarity in the filter grid. */
    @Nonnull
    private static Material rarityIcon(@Nonnull MaterialRarity rarity) {
        return switch (rarity) {
            case COMMON -> Material.LIGHT_GRAY_DYE;
            case UNCOMMON -> Material.LIME_DYE;
            case RARE -> Material.LIGHT_BLUE_DYE;
            case EPIC -> Material.PURPLE_DYE;
            case LEGENDARY -> Material.ORANGE_DYE;
        };
    }

    /** Icon that identifies an essence in the filter grid. */
    @Nonnull
    private static Material essenceIcon(@Nonnull TraitAffinity essence) {
        return switch (essence) {
            case INFERNAL -> Material.BLAZE_POWDER;
            case VOID -> Material.ENDER_PEARL;
            case PRIMAL -> Material.BONE;
            case TEMPERED -> Material.IRON_INGOT;
            case RADIANT -> Material.GLOWSTONE_DUST;
            case RESONANT -> Material.AMETHYST_SHARD;
            case VOLATILE -> Material.GUNPOWDER;
            case TERRAIN -> Material.DIRT;
            case SWIFT -> Material.SUGAR;
            case BRUTAL -> Material.IRON_AXE;
            case BULWARK -> Material.SHIELD;
            case ASCENDANT -> Material.NETHER_STAR;
        };
    }

    /** Applies the option the player clicked in the filter picker; index 0 clears that filter. */
    private void applyFilterOption(int index) {
        FilterKind picking = filterPicker;
        if (picking == null || index < 0) return;

        switch (picking) {
            case ORIGIN -> {
                if (index > MineralOrigin.values().length) return;
                originFilter = (index == 0) ? null : MineralOrigin.values()[index - 1];
            }
            case RARITY -> {
                if (index > MaterialRarity.values().length) return;
                rarityFilter = (index == 0) ? null : MaterialRarity.values()[index - 1];
            }
            case ESSENCE -> {
                if (index > TraitAffinity.values().length) return;
                essenceFilter = (index == 0) ? null : TraitAffinity.values()[index - 1];
            }
        }

        filterPicker = null;
        page = 0;
        render();
    }

    /** Materials that pass the three catalog filters, in registry order. */
    @Nonnull
    public List<TinkerMaterial> filteredMaterials() {
        List<TinkerMaterial> materials = new ArrayList<>();
        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (matchesFilters(material)) materials.add(material);
        }
        return materials;
    }

    /** True when at least one catalog filter is narrowing the list. */
    public boolean hasMaterialFilters() {
        return originFilter != null || rarityFilter != null || essenceFilter != null;
    }

    private boolean matchesFilters(@Nonnull TinkerMaterial material) {
        if (originFilter != null && material.getOrigin() != originFilter) return false;
        if (rarityFilter != null && material.getRarity() != rarityFilter) return false;
        return essenceFilter == null || TraitAffinity.of(material).contains(essenceFilter);
    }

    /** How many materials the given combination of filters would leave. */
    private int countMatching(@Nullable MineralOrigin origin, @Nullable MaterialRarity rarity,
                              @Nullable TraitAffinity essence) {
        int count = 0;
        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (origin != null && material.getOrigin() != origin) continue;
            if (rarity != null && material.getRarity() != rarity) continue;
            if (essence != null && !TraitAffinity.of(material).contains(essence)) continue;
            count++;
        }
        return count;
    }

    /** One-line description of the active filters, for the info button. */
    @Nonnull
    private String filterSummary() {
        if (!hasMaterialFilters()) return "<yellow>none</yellow> <dark_gray>(click the buttons below)</dark_gray>";
        List<String> parts = new ArrayList<>();
        if (originFilter != null) parts.add("<yellow>" + originFilter.getDescription() + "</yellow>");
        if (rarityFilter != null) parts.add("<yellow>" + rarityFilter.getDisplayName() + "</yellow>");
        if (essenceFilter != null) parts.add("<yellow>" + essenceFilter.getDisplayName() + "</yellow>");
        return String.join(" <gray>·</gray> ", parts);
    }

    /**
     * Material an item id belongs to, or {@code null} for the standalone items that are not forged
     * from a mineral (the crucible, the prospector brush and the thirteen casts).
     */
    @Nullable
    private TinkerMaterial materialOfId(@Nonnull String id) {
        String lower = id.toLowerCase(Locale.ROOT);
        TinkerMaterial direct = materialRegistry.get(lower);
        if (direct != null) return direct;

        for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
            String suffix = kind.getSuffix();
            if (!lower.endsWith(suffix)) continue;
            TinkerMaterial owner = materialRegistry.get(lower.substring(0, lower.length() - suffix.length()));
            if (owner != null) return owner;
        }
        for (String alias : List.of("_processed", "_handle", "_pommel")) {
            if (!lower.endsWith(alias)) continue;
            TinkerMaterial owner = materialRegistry.get(lower.substring(0, lower.length() - alias.length()));
            if (owner != null) return owner;
        }
        return null;
    }

    /** True when the catalog is showing the flat list of every registered id. */
    private boolean isFlatRegistry() {
        return section == Section.MINERALS && registryScope && catalogTarget == null;
    }

    private void renderNavBar() {
        inventory.setItem(SLOT_BACK, button(cameFromForge ? Material.ANVIL : Material.BARRIER,
                cameFromForge ? "<gold>◀ Back to the Forge</gold>" : "<red>◀ Close</red>",
                List.of(cameFromForge ? "Return to the Multiverse Forge GUI." : "Close the codex.")));

        Section[] sections = Section.values();
        for (int i = 0; i < sections.length && i < SLOT_CLOSE - SLOT_SECTION_FIRST; i++) {
            Section candidate = sections[i];
            boolean active = candidate == section;
            inventory.setItem(SLOT_SECTION_FIRST + i, button(candidate.icon,
                    (active ? "<green>▶ </green>" : "<gray>  </gray>") + candidate.title,
                    List.of("<gray>" + candidate.description + "</gray>",
                            "",
                            active ? "<green>✔ Currently open</green>" : "<yellow>Click to open</yellow>")));
        }

        inventory.setItem(SLOT_PRINT, button(Material.WRITABLE_BOOK,
                "<yellow><b>Print Summary to Chat</b></yellow>",
                List.of("<gray>Writes the alloy space summary to chat,", "<gray>useful for screenshots and logs.</gray>")));
        inventory.setItem(SLOT_CLOSE, button(Material.BARRIER, "<red><b>Close</b></red>", List.of("<gray>Exit the codex.</gray>")));
    }

    /**
     * Explains an empty section instead of leaving the player guessing.
     *
     * <p>The codex only lists what <b>this server</b> has actually forged, so {@code Prime Alloys}
     * and {@code Forged Composites} start empty on every fresh world: nothing is broken, the list
     * simply fills itself as the crucible is used. This is exactly why the Prime Alloys tab looks
     * empty until the first legendary fusion happens.</p>
     */
    @Nonnull
    private ItemStack emptyState(@Nonnull Section section) {
        return switch (section) {
            case PRIMES -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No prime alloys forged yet</b></gray>",
                    List.of("<gray>Nothing is missing here - this tab only lists the",
                            "<gray>primes <white>this server</white> has already fused.</gray>",
                            "",
                            "<gold>How to forge a prime alloy:</gold>",
                            "<gray>1. Forge a <yellow>Legendary alloy</yellow> in the Alloy Crucible.</gray>",
                            "<gray>2. Fuse it again with another alloy, any mineral,</gray>",
                            "<gray>   or one of the <yellow>12 vanilla catalysts</yellow>.</gray>",
                            "",
                            "<gray>The prime comes out Legendary, with its own",
                            "<gray>cinematic ultimate and armor state, and is listed here.",
                            "",
                            "<yellow>► Tip: the Combination Explorer lists every</yellow>",
                            "<yellow>partner each legendary alloy accepts.</yellow>"));
            case COMPOSITES -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No composite alloys forged yet</b></gray>",
                    List.of("<gray>Blend any <yellow>two minerals</yellow> in the Alloy Crucible and</gray>",
                            "<gray>the composite they create is recorded here.</gray>",
                            "",
                            "<gray>This tab stays empty until this server forges one.</gray>",
                            "",
                            "<yellow>► Tip: browse the Combination Explorer to see how</yellow>",
                            "<yellow>many pairs each mineral accepts.</yellow>"));
            case MINERALS -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No item matches this filter</b></gray>",
                    List.of("<gray>Nothing passes the filters set below.",
                            "",
                            "<yellow>► Dimension, rarity and essence stack:</yellow>",
                            "<gray>open one and pick <white>Any</white> to widen it again,</gray>",
                            "<gray>and Kind cycles the item kinds of that scope.</gray>"));
            case EXPLORER -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>No partner for this material</b></gray>",
                    List.of("<gray>This material cannot be blended right now.</gray>",
                            "",
                            "<gray>Prime alloys cannot be fused again, and a</gray>",
                            "<gray>material needs a distinct partner to mix with.</gray>",
                            "",
                            "<yellow>► Tip: press any entry to pick another material.</yellow>"));
            default -> button(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "<gray><b>Nothing here yet</b></gray>",
                    List.of("Forge something in the Alloy Crucible and it appears here."));
        };
    }

    private List<ItemStack> contentFor(Section section) {
        return switch (section) {
            case LEGENDARY -> legendaryEntries();
            case CATALYSTS -> catalystEntries();
            case COMPOSITES -> forgedEntries(false);
            case PRIMES -> forgedEntries(true);
            case EXPLORER -> explorerTarget == null ? explorerChoices() : explorerPartners();
            case SUMMARY -> summaryEntries();
            case MINERALS -> catalogEntries();
        };
    }

    /**
     * The catalog: the material list, or the ids of the material the player drilled into.
     *
     * <p>The flat registry is rendered separately, because building thousands of item stacks just to
     * count the pages would be wasteful — the ids are generated as strings and only the entries of
     * the visible page become items.</p>
     */
    private List<ItemStack> catalogEntries() {
        return catalogTarget != null ? drilledEntries(catalogTarget) : materialEntries();
    }

    /** Every registered id, narrowed by the kind filter. Strings only: nothing is built here. */
    private List<String> registryIds() {
        boolean filtered = hasMaterialFilters();
        List<String> ids = new ArrayList<>();
        for (String id : itemRegistry.getAllItemIds()) {
            if (kindFilter != null && kindOfId(id) != kindFilter) continue;
            if (filtered) {
                // The dimension, rarity and essence of an id are the ones of the material it belongs to;
                // the standalone items (crucible, brush, casts) belong to none, so a filter hides them.
                TinkerMaterial owner = materialOfId(id);
                if (owner == null || !matchesFilters(owner)) continue;
            }
            ids.add(id);
        }
        return ids;
    }

    /**
     * Every id that belongs to one material: its raw ore, ingot, nugget, block, molten bucket, ten
     * part types and the legacy aliases that still resolve.
     */
    private List<ItemStack> drilledEntries(@Nonnull TinkerMaterial material) {
        List<ItemStack> entries = new ArrayList<>();
        String baseId = material.getId().toLowerCase(Locale.ROOT);
        for (String id : itemRegistry.getAllItemIds()) {
            if (!id.startsWith(baseId)) continue;
            entries.add(registryEntry(id));
        }
        return entries;
    }

    /** Renders one registered id as its own item, with the id spelled out for commands. */
    private ItemStack registryEntry(@Nonnull String id) {
        ItemStack item = itemRegistry.getItemById(id);
        if (item == null || item.getType() == Material.AIR) {
            return button(Material.BARRIER, "<red><b>" + id + "</b></red>",
                    List.of("<gray>Registered id with no item stack behind it.</gray>"));
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        List<Component> lore = meta.lore() == null ? new ArrayList<>() : new ArrayList<>(meta.lore());
        lore.add(Component.empty());
        lore.add(miniMessage.deserialize("<dark_gray>ID: " + id + "</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));
        lore.add(miniMessage.deserialize("<dark_gray>Give it with: <gray>/mvtink give <player> " + id + "</gray></dark_gray>")
                .decoration(TextDecoration.ITALIC, false));
        lore.addAll(LoreWrap.wrap(miniMessage.deserialize("<yellow>Click to print the id in chat.</yellow>")
                .decoration(TextDecoration.ITALIC, false)));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /** Item kind implied by an id, or {@code null} for ids that are not a material kind. */
    @Nullable
    private static TinkerItemRegistry.ItemKind kindOfId(@Nonnull String id) {
        for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
            if (id.endsWith(kind.getSuffix())) return kind;
        }
        // Legacy aliases: _processed is an ingot, _handle a rod and _pommel a binding.
        if (id.endsWith("_processed")) return TinkerItemRegistry.ItemKind.INGOT;
        if (id.endsWith("_handle")) return TinkerItemRegistry.ItemKind.ROD;
        if (id.endsWith("_pommel")) return TinkerItemRegistry.ItemKind.BINDING;
        return null;
    }

    /**
     * The catalog of everything that can be forged with, in one browsable tab.
     *
     * <p>This is the codex replacement for the old chat listing: each material carries its id,
     * dimension, rarity, type, trait and essences, so a player can plan a build without leaving the
     * inventory. Composites and primes forged on this server appear here as well, because they become
     * forgeable materials the moment they are smelted.</p>
     */
    private List<ItemStack> materialEntries() {
        List<ItemStack> entries = new ArrayList<>();
        for (TinkerMaterial material : filteredMaterials()) {
            List<String> lore = new ArrayList<>();
            lore.add("<dark_gray>ID: " + material.getId() + "</dark_gray>");
            lore.add("<gray>Origin: <yellow>" + material.getOrigin().name() + "</yellow> · Rarity: <yellow>"
                    + material.getRarity().getDisplayName() + "</yellow></gray>");
            lore.add("<gray>Type: <yellow>" + material.getType().getDisplayTypeName() + "</yellow> · Source: <yellow>"
                    + material.getOrigin().getSourceBlockName() + "</yellow></gray>");
            lore.add("");
            lore.add("<gray>Durability: <green>+" + material.getDurabilityBonus() + "</green> · Speed: <aqua>"
                    + String.format(Locale.US, "%.1fx", material.getMiningSpeed()) + "</aqua> · Damage: <red>+"
                    + String.format(Locale.US, "%.1f", material.getAttackDamageBonus()) + "</red></gray>");
            lore.add("<gold>✦ Trait: </gold><aqua>" + material.getTraitName() + "</aqua>");
            lore.add("<dark_aqua>" + material.getTraitDescription() + "</dark_aqua>");
            lore.add("<gray>Essences: <light_purple>" + essenceLine(material) + "</light_purple></gray>");
            addEpithet(lore, material);
            lore.add("");
            lore.add("<dark_gray>" + material.getDescription() + "</dark_gray>");
            lore.add("");
            lore.add("<yellow>▶ Click: open it in the <b>Combination Explorer</b></yellow>");
            lore.add("<dark_gray>Shift-click: list every item id it owns.</dark_gray>");

            // Show the material as it drops in the world, so the catalog is recognisable at a glance;
            // alloys have no raw form, so they fall back to their ingot and then to a generic shard.
            ItemStack raw = itemRegistry.getRawItem(material.getId());
            ItemStack icon = (raw != null && raw.getType() != Material.AIR) ? raw : null;
            if (icon == null) {
                ItemStack ingot = TinkerItemBuilder.createIngot(material);
                icon = (ingot.getType() != Material.AIR) ? ingot : new ItemStack(Material.AMETHYST_SHARD);
            }
            applyMeta(icon, "<gradient:" + material.getColorHex() + ":#ffffff><b>" + material.getName()
                    + "</b></gradient>", lore);
            entries.add(icon);
        }
        return entries;
    }

    private List<ItemStack> legendaryEntries() {
        List<ItemStack> entries = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial parentA = materialRegistry.get(alloy.mat1Id());
            TinkerMaterial parentB = materialRegistry.get(alloy.mat2Id());
            TinkerMaterial result = materialRegistry.get(alloy.id());

            List<String> lore = new ArrayList<>();
            lore.add("<gray>Parents: <yellow>" + nameOf(parentA, alloy.mat1Id()) + "</yellow> + <yellow>"
                    + nameOf(parentB, alloy.mat2Id()) + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + alloy.id() + "</dark_gray>");
            lore.add("");
            if (result != null) {
                lore.add("<gray>Durability: <green>+" + result.getDurabilityBonus() + "</green> · Speed: <aqua>"
                        + String.format(Locale.US, "%.1fx", result.getMiningSpeed()) + "</aqua> · Damage: <red>+"
                        + String.format(Locale.US, "%.1f", result.getAttackDamageBonus()) + "</red></gray>");
                lore.add("<gray>Essences: <light_purple>" + essenceLine(result) + "</light_purple></gray>");
                addEpithet(lore, result);
            }
            lore.add("");
            lore.add("<gold>✦ " + alloy.traitName() + "</gold>");
            lore.add("<dark_aqua>" + alloy.traitDescription() + "</dark_aqua>");
            lore.add("");
            lore.add("<gray>Can be fused again into a <yellow>prime alloy</yellow> with another alloy,");
            lore.add("<gray>a mineral or one of the 12 vanilla catalysts.</gray>");
            entries.add(entry(result, Material.IRON_INGOT,
                    "<gradient:" + alloy.colorHex() + ":#ffffff><b>" + alloy.name() + "</b></gradient>", lore));
        }
        return entries;
    }

    private List<ItemStack> catalystEntries() {
        List<ItemStack> entries = new ArrayList<>();
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            List<String> lore = new ArrayList<>();
            lore.add("<gray>Item: <yellow>" + catalyst.getItem().name() + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + catalyst.getMaterialId() + "</dark_gray>");
            lore.add("");
            lore.add("<gold>⚡ Ultimate: </gold><aqua>" + catalyst.getUltimate().getDisplayName() + "</aqua>");
            lore.add("<gray>  " + catalyst.getUltimate().getDescription() + "</gray>");
            lore.add("<gold>🛡 Armor State: </gold><light_purple>" + catalyst.getArmorState().getDisplayName() + "</light_purple>");
            lore.add("<gray>  " + catalyst.getArmorState().getDescription() + "</gray>");
            addEpithet(lore, material);
            lore.add("");
            lore.add("<gray>Fuse with any of the <yellow>16 legendary alloys</yellow> → <bold>"
                    + catalyst.compatiblePrimes() + "</bold> prime alloys.</gray>");
            entries.add(entry(material, catalyst.getItem(), "<gradient:" + catalyst.getColorHex()
                    + ":#ffffff><b>" + catalyst.getDisplayName() + " (Catalyst)</b></gradient>", lore));
        }
        return entries;
    }

    /** Composites or primes, both limited to what the server has actually forged. */
    private List<ItemStack> forgedEntries(boolean primes) {
        List<ItemStack> entries = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            // The 16 curated recipes have their own section; only what players forged belongs here.
            if (AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            boolean isPrime = AlloyRegistry.isPrimeParents(alloy.mat1Id(), alloy.mat2Id());
            if (isPrime != primes) continue;

            TinkerMaterial result = materialRegistry.get(alloy.id());
            List<String> lore = new ArrayList<>();
            lore.add("<gray>Parents: <yellow>" + nameOf(materialRegistry.get(alloy.mat1Id()), alloy.mat1Id())
                    + "</yellow> + <yellow>" + nameOf(materialRegistry.get(alloy.mat2Id()), alloy.mat2Id()) + "</yellow></gray>");
            lore.add("<dark_gray>ID: " + alloy.id() + "</dark_gray>");
            lore.add("");
            if (result != null) {
                lore.add("<gray>Durability: <green>+" + result.getDurabilityBonus() + "</green> · Speed: <aqua>"
                        + String.format(Locale.US, "%.1fx", result.getMiningSpeed()) + "</aqua> · Damage: <red>+"
                        + String.format(Locale.US, "%.1f", result.getAttackDamageBonus()) + "</red></gray>");
                lore.add("<gray>Essences: <light_purple>" + essenceLine(result) + "</light_purple></gray>");
                addEpithet(lore, result);
                if (primes) {
                    lore.add("");
                    lore.add("<gold>⚡ Prime ultimate: </gold><aqua>" + PrimeUltimate.of(result).getDisplayName() + "</aqua>");
                    lore.add("<gold>🛡 Prime state: </gold><light_purple>"
                            + PrimeArmorState.of(result).getDisplayName() + "</light_purple>");
                }
            }
            lore.add("");
            lore.add("<gold>✦ " + alloy.traitName() + "</gold>");
            lore.add("<dark_aqua>" + alloy.traitDescription() + "</dark_aqua>");
            entries.add(entry(result, primes ? Material.BEACON : Material.IRON_INGOT,
                    "<gradient:" + alloy.colorHex() + ":#ffffff><b>" + alloy.name() + "</b></gradient>", lore));
        }
        return entries;
    }

    /** Explorer step 1: everything you can pick a partner for. */
    private List<ItemStack> explorerChoices() {
        List<ItemStack> entries = new ArrayList<>();

        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial material = materialRegistry.get(alloy.id());
            if (material == null) continue;
            entries.add(explorerChoice(material, alloy.colorHex(), "<gradient:" + alloy.colorHex()
                    + ":#ffffff><b>" + alloy.name() + "</b></gradient>", "Legendary alloy"));
        }

        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            if (material == null) continue;
            entries.add(explorerChoice(material, catalyst.getColorHex(), "<gradient:" + catalyst.getColorHex()
                    + ":#ffffff><b>" + catalyst.getDisplayName() + "</b></gradient>", "Vanilla catalyst"));
        }

        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (!AlloyRegistry.isMixable(material)) continue;
            entries.add(explorerChoice(material, material.getColorHex(), "<gradient:" + material.getColorHex()
                    + ":#ffffff><b>" + material.getName() + "</b></gradient>",
                    material.getOrigin().name() + " " + material.getType().name().toLowerCase(Locale.ROOT)));
        }
        return entries;
    }

    private ItemStack explorerChoice(@Nonnull TinkerMaterial material, @Nonnull String colorHex,
                                     @Nonnull String title, @Nonnull String kind) {
        List<String> lore = new ArrayList<>();
        lore.add("<gray>" + kind + "</gray>");
        lore.add("<dark_gray>ID: " + material.getId() + "</dark_gray>");
        lore.add("");
        lore.add("<gray>Essences: <light_purple>" + essenceLine(material) + "</light_purple></gray>");
        addEpithet(lore, material);
        lore.add("");
        lore.add("<yellow>▶ Click to list every compatible partner</yellow>");
        return entry(material, Material.IRON_INGOT, title, lore);
    }

    /** Explorer step 2: every partner the crucible accepts for the selected material. */
    private List<ItemStack> explorerPartners() {
        List<ItemStack> entries = new ArrayList<>();
        TinkerMaterial target = explorerTarget;
        if (target == null) return entries;

        for (TinkerMaterial candidate : materialRegistry.getAll()) {
            if (candidate.getId().equalsIgnoreCase(target.getId())) continue;
            if (AlloyRegistry.isPrime(candidate)) continue; // primes cannot be reforged
            if (!alloyRegistry.isCraftablePair(target, candidate)) continue;
            entries.add(partnerEntry(target, candidate));
        }
        return entries;
    }

    private ItemStack partnerEntry(@Nonnull TinkerMaterial a, @Nonnull TinkerMaterial b) {
        boolean prime = AlloyRegistry.isPrimePair(a, b);
        boolean curated = alloyRegistry.findAlloy(a.getId(), b.getId()) != null;

        String previewId = AlloyRegistry.dynamicId(a, b);
        String previewName = AlloyRegistry.dynamicName(a, b);

        String tag = curated ? "<gold>[CURATED RECIPE]</gold>" : prime ? "<light_purple>[PRIME FUSION]</light_purple>"
                : "<aqua>[NEW COMPOSITE]</aqua>";

        List<String> lore = new ArrayList<>();
        lore.add(tag);
        lore.add("<gray>With: <yellow>" + b.getName() + "</yellow> <dark_gray>(" + b.getId() + ")</dark_gray>");
        lore.add("<gray>Result: <white>" + previewName + "</white></gray>");
        lore.add("<dark_gray>ID: " + previewId + "</dark_gray>");
        lore.add("");
        lore.add("<gray>Essences of the pair: <light_purple>" + essenceLine(a, b) + "</light_purple></gray>");
        if (prime) {
            VanillaCatalyst sigil = VanillaCatalyst.byMaterialId(a.getId());
            if (sigil == null) sigil = VanillaCatalyst.byMaterialId(b.getId());
            if (sigil != null) {
                lore.add("<gold>⚡ Ultimate: </gold><aqua>" + sigil.getUltimate().getDisplayName() + "</aqua>");
                lore.add("<gold>🛡 State: </gold><light_purple>" + sigil.getArmorState().getDisplayName() + "</light_purple>");
            }
            lore.add("<gray>Anything can be fused into a prime, but primes cannot be reforged.</gray>");
        }
        lore.add("");
        lore.add("<dark_gray>Preview only — smelt it in the crucible to unlock it.</dark_gray>");
        return entry(b, prime ? Material.BEACON : Material.IRON_INGOT,
                "<gradient:" + b.getColorHex() + ":#ffffff><b>" + b.getName() + "</b></gradient>", lore);
    }

    private List<ItemStack> summaryEntries() {
        List<ItemStack> entries = new ArrayList<>();

        long mixable = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count();
        long mineralPairs = mixable * (mixable - 1) / 2;
        int curated = AlloyRegistry.LEGENDARY_IDS.size();
        int forged = alloyRegistry.getDynamicAlloyCount();
        int primes = alloyRegistry.getPrimeAlloyCount();
        long catalysts = VanillaCatalyst.values().length;
        long legendaryFusions = permittedPairs(mixable, catalysts);

        entries.add(info(Material.DIAMOND_PICKAXE, "<aqua><b>Mineral alloys</b></aqua>", List.of(
                "<gray>Blendable minerals: <yellow>" + mixable + "</yellow></gray>",
                "<gray>Distinct mineral pairs: <yellow>" + mineralPairs + "</yellow></gray>",
                "<dark_gray>Any two brush/vanilla minerals fuse into a unique composite.</dark_gray>")));

        entries.add(info(Material.NETHERITE_INGOT, "<gold><b>Curated legendary recipes</b></gold>", List.of(
                "<gray>Legendary alloys: <yellow>" + curated + "</yellow></gray>",
                "<gray>Free-form mineral pairs: <yellow>14</yellow></gray>",
                "<gray>Extra netherite recipes: <yellow>2</yellow> (Cinder Steel, Cosmic Netherite)</gray>")));

        entries.add(info(Material.BLAZE_POWDER, "<light_purple><b>Prime fusions</b></light_purple>", List.of(
                "<gray>Vanilla catalysts: <yellow>" + catalysts + "</yellow></gray>",
                "<gray>Legendary × catalyst: <yellow>" + (curated * catalysts) + "</yellow></gray>",
                "<gray>Legendary × legendary: <yellow>" + (curated * (curated - 1) / 2) + "</yellow></gray>",
                "<gray>Legendary × mineral: <yellow>" + (curated * mixable) + "</yellow></gray>",
                "<gray>Legendary × other alloy: <yellow>" + (curated * (mineralPairs - 14 + 1)) + "</yellow></gray>")));

        entries.add(info(Material.WRITTEN_BOOK, "<yellow><b>Discovered on this server</b></yellow>", List.of(
                "<gray>Composite alloys: <yellow>" + forged + "</yellow></gray>",
                "<gray>Prime alloys: <yellow>" + primes + "</yellow></gray>",
                "<gray>Total registered alloys: <yellow>" + alloyRegistry.getAllAlloys().size() + "</yellow></gray>")));

        entries.add(info(Material.END_PORTAL_FRAME, "<gradient:#6c5ce7:#a29bfe><b>Grand total</b></gradient>", List.of(
                "<gray>Base combinations: <yellow>" + (mineralPairs + 2) + "</yellow></gray>",
                "<gray>With every composite known: <yellow>" + (mineralPairs + 2 + legendaryFusions) + "</yellow></gray>",
                "<dark_gray>Every prime is unique: id, colour, stats, essence blend,</dark_gray>",
                "<dark_gray>ultimate and armor state are all derived deterministically.</dark_gray>")));

        entries.add(info(Material.COMPASS, "<green><b>How to read this codex</b></green>", List.of(
                "<gray>1. <yellow>Legendary Recipes</yellow>: the 16 curated alloys.</gray>",
                "<gray>2. <yellow>Prime Catalysts</yellow>: the 12 vanilla items.</gray>",
                "<gray>3. <yellow>Forged Composites / Prime Alloys</yellow>: what this server made.</gray>",
                "<gray>4. <yellow>Combination Explorer</yellow>: pick a material, see all partners.</gray>")));

        return entries;
    }

    /** Legendary × (other alloys + minerals + catalysts), the reachable prime ceiling. */
    private long permittedPairs(long mixable, long catalysts) {
        long otherAlloys = alloyRegistry.getAllAlloys().size() - AlloyRegistry.LEGENDARY_IDS.size();
        return AlloyRegistry.LEGENDARY_IDS.size() * (otherAlloys + mixable + catalysts);
    }

    // ==========================================
    // CLICKS
    // ==========================================
    /** Handles a plain click, which opens what the entry stands for rather than listing its ids. */
    public void handleClick(@Nonnull Player player, int rawSlot) {
        handleClick(player, rawSlot, false);
    }

    /**
     * Handles a click, told apart by whether the player asked for the entry's ids.
     *
     * @param drill {@code true} when the click is a shift-click, which asks for the item ids of the
     *              material under it instead of what that material can be blended with
     */
    public void handleClick(@Nonnull Player player, int rawSlot, boolean drill) {
        switch (rawSlot) {
            case SLOT_CLOSE -> {
                player.closeInventory();
                return;
            }
            case SLOT_BACK -> {
                if (cameFromForge) {
                    player.closeInventory();
                    ForgeGUI forge = new ForgeGUI(plugin, itemRegistry, materialRegistry);
                    // Delay one tick so the close event finishes before the new inventory opens.
                    Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(forge.getInventory()));
                } else {
                    player.closeInventory();
                }
                return;
            }
            case SLOT_PRINT -> {
                printSummary(player);
                return;
            }
            case SLOT_PREV -> {
                if (page > 0) page--;
                render();
                return;
            }
            case SLOT_NEXT -> {
                page++;
                render();
                return;
            }
            case SLOT_SCOPE -> {
                if (section != Section.MINERALS) return;
                registryScope = !registryScope;
                catalogTarget = null;
                if (!registryScope) kindFilter = null;
                page = 0;
                render();
                return;
            }
            case SLOT_KIND -> {
                if (section != Section.MINERALS || !registryScope || catalogTarget != null) return;
                kindFilter = nextKindFilter();
                page = 0;
                render();
                return;
            }
            case SLOT_ORIGIN_FILTER -> {
                openFilterPicker(FilterKind.ORIGIN);
                return;
            }
            case SLOT_RARITY_FILTER -> {
                openFilterPicker(FilterKind.RARITY);
                return;
            }
            case SLOT_ESSENCE_FILTER -> {
                openFilterPicker(FilterKind.ESSENCE);
                return;
            }
            default -> {
                // handled below
            }
        }

        Section[] sections = Section.values();
        int sectionIndex = rawSlot - SLOT_SECTION_FIRST;
        if (sectionIndex >= 0 && sectionIndex < sections.length) {
            section = sections[sectionIndex];
            page = 0;
            explorerTarget = null;
            catalogTarget = null;
            filterPicker = null;
            render();
            return;
        }

        if (rawSlot < CONTENT_START || rawSlot >= CONTENT_START + CONTENT_SLOTS) return;

        int index = page * CONTENT_SLOTS + (rawSlot - CONTENT_START);

        if (isChoosingFilter()) {
            // The grid is listing filter values, so a click applies one instead of opening an entry.
            applyFilterOption(index);
            return;
        }

        if (section == Section.MINERALS) {
            if (isFlatRegistry()) {
                // Every registered id is listed: clicking one hands the player the id for commands.
                String id = registryIdAt(index);
                if (id != null) {
                    player.sendMessage(miniMessage.deserialize("<gray>ID: <yellow>" + id
                            + "</yellow> · give it with <yellow>/mvtink give <player> " + id + "</yellow></gray>"));
                }
            } else if (catalogTarget != null) {
                // Drilled into a material: any click goes back to the material list.
                catalogTarget = null;
                page = 0;
                render();
            } else {
                TinkerMaterial clicked = materialAt(index);
                if (clicked == null) return;
                if (drill) {
                    // Shift-click still reaches the ids, because that is how a player copies one into a
                    // command; a plain click answers the question the entry raises.
                    catalogTarget = clicked;
                    page = 0;
                    render();
                } else {
                    openExplorerFor(clicked);
                }
            }
            return;
        }

        if (section == Section.EXPLORER) {
            if (explorerTarget == null) {
                TinkerMaterial clicked = selectedChoice(rawSlot);
                if (clicked == null) return;
                explorerTarget = clicked;
                page = 0;
                render();
            } else {
                // Already listing partners: go back to the material picker.
                explorerTarget = null;
                page = 0;
                render();
            }
        }
    }

    /**
     * Hands a mineral from the catalog to the Combination Explorer, already selected.
     *
     * <p>The catalog is where a player discovers a mineral and the explorer is where they learn what it
     * blends with, so the click carries the mineral across rather than making them find it again among
     * the sixteen legendaries, twelve catalysts and hundred-odd minerals of the picker.</p>
     *
     * <p>A prime has no partner left to offer and lands on the explorer's own explanation, which says
     * exactly that — it is still a true answer to the click, and shift-click reaches its ids.</p>
     */
    private void openExplorerFor(@Nonnull TinkerMaterial material) {
        section = Section.EXPLORER;
        explorerTarget = material;
        catalogTarget = null;
        filterPicker = null;
        page = 0;
        render();
    }

    /** Material shown at the given index of the catalog's material list, or {@code null}. */
    @Nullable
    private TinkerMaterial materialAt(int index) {
        if (index < 0) return null;
        // The filtered order, so a click opens the material that is actually on that slot.
        List<TinkerMaterial> materials = filteredMaterials();
        return index < materials.size() ? materials.get(index) : null;
    }

    /**
     * Swaps the catalog grid for the values of one filter, or closes it when the same button is
     * clicked again.
     */
    private void openFilterPicker(@Nonnull FilterKind kind) {
        if (section != Section.MINERALS) return;
        filterPicker = (filterPicker == kind) ? null : kind;
        page = 0;
        render();
    }

    /** Cycles the registry kind filter: ALL → each item kind → ALL. */
    @Nullable
    private TinkerItemRegistry.ItemKind nextKindFilter() {
        TinkerItemRegistry.ItemKind[] kinds = TinkerItemRegistry.ItemKind.values();
        if (kindFilter == null) return kinds[0];
        int next = kindFilter.ordinal() + 1;
        return next < kinds.length ? kinds[next] : null;
    }

    /** Maps a clicked explorer slot back to the material it belongs to. */
    @Nullable
    private TinkerMaterial selectedChoice(int rawSlot) {
        List<ItemStack> choices = explorerChoices();
        int index = page * CONTENT_SLOTS + (rawSlot - CONTENT_START);
        if (index < 0 || index >= choices.size()) return null;

        // The choice list is built in a fixed order (legendaries, catalysts, minerals), so the index
        // identifies the material through the same traversal.
        List<TinkerMaterial> ordered = new ArrayList<>();
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            if (!AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            TinkerMaterial material = materialRegistry.get(alloy.id());
            if (material != null) ordered.add(material);
        }
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materialRegistry.get(catalyst.getMaterialId());
            if (material != null) ordered.add(material);
        }
        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (AlloyRegistry.isMixable(material)) ordered.add(material);
        }
        return index < ordered.size() ? ordered.get(index) : null;
    }

    private void printSummary(@Nonnull Player player) {
        long mixable = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count();
        long mineralPairs = mixable * (mixable - 1) / 2;
        long ceiling = mineralPairs + 2 + permittedPairs(mixable, VanillaCatalyst.values().length);

        player.sendMessage(miniMessage.deserialize("<gradient:#ffd700:#ff8c00><b>══════ MULTIVERSE ALLOY CODEX ══════</b></gradient>"));
        player.sendMessage(miniMessage.deserialize("<gray>Blendable minerals: <yellow>" + mixable
                + "</yellow> · mineral pairs: <yellow>" + mineralPairs + "</yellow></gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Legendary recipes: <yellow>" + AlloyRegistry.LEGENDARY_IDS.size()
                + "</yellow> · vanilla catalysts: <yellow>" + VanillaCatalyst.values().length + "</yellow></gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Forged on this server: <yellow>"
                + alloyRegistry.getDynamicAlloyCount() + "</yellow> composites, <yellow>"
                + alloyRegistry.getPrimeAlloyCount() + "</yellow> primes</gray>"));
        player.sendMessage(miniMessage.deserialize("<gray>Maximum distinct alloys once every composite is known: <yellow>"
                + ceiling + "</yellow></gray>"));
    }

    // ==========================================
    // ITEM HELPERS
    // ==========================================
    @Nonnull
    private ItemStack entry(@Nullable TinkerMaterial material, @Nonnull Material fallback,
                            @Nonnull String title, @Nonnull List<String> lore) {
        ItemStack item;
        if (material != null) {
            ItemStack ingot = TinkerItemBuilder.createIngot(material);
            item = (ingot.getType() == Material.AIR) ? new ItemStack(fallback) : ingot;
        } else {
            item = new ItemStack(fallback);
        }
        applyMeta(item, title, lore);
        return item;
    }

    @Nonnull
    private ItemStack button(@Nonnull Material icon, @Nonnull String title, @Nonnull List<String> lore) {
        ItemStack item = new ItemStack(icon);
        applyMeta(item, title, lore);
        return item;
    }

    /** Explanatory entry used by the summary section; same rendering as a button. */
    @Nonnull
    private ItemStack info(@Nonnull Material icon, @Nonnull String title, @Nonnull List<String> lore) {
        return button(icon, title, lore);
    }

    @Nonnull
    private ItemStack decor(@Nonnull Material icon, @Nonnull String title) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void applyMeta(@Nonnull ItemStack item, @Nonnull String title, @Nonnull List<String> loreLines) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.displayName(miniMessage.deserialize(title).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        for (String line : loreLines) {
            // Codex entries carry long trait descriptions: wrap them so nothing is clipped.
            lore.addAll(LoreWrap.wrap(miniMessage.deserialize(line).decoration(TextDecoration.ITALIC, false)));
        }
        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    @Nonnull
    private String nameOf(@Nullable TinkerMaterial material, @Nonnull String fallbackId) {
        return material != null ? material.getName() : fallbackId;
    }

    /**
     * Adds the mineral's perk epithet to an entry's lore.
     *
     * <p>The word that names a forged perk is only useful before the forge, so it belongs on every codex
     * entry that describes something a player can forge with — the mineral catalog, the explorer picker,
     * the legendary recipes, the catalysts and everything this server has already blended. It is added by
     * the same {@link PerkEpithet} the forge uses, so the word a player reads here is the word their item
     * will print.</p>
     */
    private static void addEpithet(@Nonnull List<String> lore, @Nullable TinkerMaterial material) {
        String epithet = PerkEpithet.of(material);
        if (epithet.isEmpty()) return;
        lore.add("<gold>✦ Perk epithet: </gold><light_purple>" + epithet + "</light_purple>");
    }

    @Nonnull
    private String essenceLine(@Nonnull TinkerMaterial material, @Nullable TinkerMaterial... others) {
        StringBuilder builder = new StringBuilder();
        for (TraitAffinity affinity : TraitAffinity.of(material)) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(affinity.getDisplayName());
        }
        for (TinkerMaterial other : others) {
            if (other == null) continue;
            for (TraitAffinity affinity : TraitAffinity.of(other)) {
                if (builder.indexOf(affinity.getDisplayName()) >= 0) continue;
                if (builder.length() > 0) builder.append(", ");
                builder.append(affinity.getDisplayName());
            }
        }
        return builder.length() == 0 ? "Primal" : builder.toString();
    }

    /** Number of entries the given section would show right now (used by tests and diagnostics). */
    public int entryCount(@Nonnull Section section) {
        if (section == Section.MINERALS) {
            if (isChoosingFilter()) return filterPickerEntries().size();
            if (registryScope && catalogTarget == null) return registryIds().size();
        }
        return contentFor(section).size();
    }

    /** Filter by dimension currently applied to the catalog, or {@code null} for every dimension. */
    @Nullable
    public MineralOrigin getOriginFilter() {
        return originFilter;
    }

    /** Filter by rarity currently applied to the catalog, or {@code null} for every rarity. */
    @Nullable
    public MaterialRarity getRarityFilter() {
        return rarityFilter;
    }

    /** Filter by essence currently applied to the catalog, or {@code null} for every essence. */
    @Nullable
    public TraitAffinity getEssenceFilter() {
        return essenceFilter;
    }

    /** Filter whose options are on screen, or {@code null} while browsing the catalog. */
    @Nullable
    public FilterKind getFilterPicker() {
        return filterPicker;
    }

    /** True while the catalog lists every registered item id (tests and diagnostics). */
    public boolean isRegistryScope() {
        return registryScope;
    }

    /** Kind filter currently applied to the registry scope, or {@code null} for every kind. */
    @Nullable
    public TinkerItemRegistry.ItemKind getKindFilter() {
        return kindFilter;
    }

    /** Material the catalog is drilled into, or {@code null} while listing materials. */
    @Nullable
    public TinkerMaterial getCatalogTarget() {
        return catalogTarget;
    }

    /** One registered id shown by the catalog, by its position in the current scope. */
    @Nullable
    public String registryIdAt(int index) {
        if (!isFlatRegistry()) return null;
        List<String> ids = registryIds();
        return index >= 0 && index < ids.size() ? ids.get(index) : null;
    }

    @Nonnull
    public Section getSection() {
        return section;
    }
}
