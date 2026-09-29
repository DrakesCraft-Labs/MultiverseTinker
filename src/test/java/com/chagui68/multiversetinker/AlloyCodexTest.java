package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.PerkEpithet;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the in-game Alloy Codex: its sections, the mineral catalog, pagination and the combination
 * explorer, which must preview every valid partner without creating a single alloy.
 */
class AlloyCodexTest {

    private static final int CONTENT_START = 9;
    private static final int SLOT_SECTION_FIRST = 1;
    private static final int SLOT_PREV = 45;
    private static final int SLOT_NEXT = 53;
    private static final int SLOT_SCOPE = 46;
    private static final int SLOT_KIND = 48;
    private static final int SLOT_INFO = 49;
    private static final int SLOT_ORIGIN_FILTER = 50;
    private static final int SLOT_RARITY_FILTER = 51;
    private static final int SLOT_ESSENCE_FILTER = 52;
    private static final int CATALOG_SECTION = SLOT_SECTION_FIRST + 6;

    private ServerMock server;
    private MultiverseTinker plugin;
    private MaterialRegistry materials;
    private AlloyRegistry alloys;
    private AlloyCodexGUI codex;
    private Player player;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        materials = plugin.getMaterialRegistry();
        alloys = plugin.getAlloyRegistry();

        codex = new AlloyCodexGUI(plugin, plugin.getItemRegistry(), materials);
        player = server.addPlayer("scholar");
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The codex opens as a 54-slot inventory with every section populated")
    void sectionsArePopulated() {
        Inventory inventory = codex.getInventory();
        assertEquals(54, inventory.getSize());
        assertSame(codex, inventory.getHolder());

        assertEquals(16, codex.entryCount(AlloyCodexGUI.Section.LEGENDARY),
                "All 16 legendary recipes must be listed");
        assertEquals(VanillaCatalyst.values().length, codex.entryCount(AlloyCodexGUI.Section.CATALYSTS),
                "All 12 vanilla catalysts must be listed");
        assertEquals(alloys.getDynamicAlloyCount() - alloys.getPrimeAlloyCount(),
                codex.entryCount(AlloyCodexGUI.Section.COMPOSITES),
                "Composites must exclude primes");
        assertEquals(alloys.getPrimeAlloyCount(), codex.entryCount(AlloyCodexGUI.Section.PRIMES));
        assertTrue(codex.entryCount(AlloyCodexGUI.Section.SUMMARY) >= 5,
                "The summary must explain the whole alloy space");
        assertEquals(materials.getAll().size(), codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "The mineral catalog must list every registered material, including forged alloys");

        long mixable = materials.getAll().stream().filter(AlloyRegistry::isMixable).count();
        assertEquals(16 + VanillaCatalyst.values().length + mixable,
                codex.entryCount(AlloyCodexGUI.Section.EXPLORER),
                "The explorer must offer every legendary, catalyst and blendable mineral");
    }

    @Test
    @DisplayName("Section buttons switch the view and pagination never leaves the page range")
    void navigationWorks() {
        assertEquals(AlloyCodexGUI.Section.LEGENDARY, codex.getSection());

        codex.handleClick(player, SLOT_SECTION_FIRST + 4);
        assertEquals(AlloyCodexGUI.Section.EXPLORER, codex.getSection());
        assertNull(codex.getExplorerTarget(), "Switching sections resets the explorer");

        // The catalog is the last tab, and clicking it shows materials rather than forging anything.
        codex.handleClick(player, SLOT_SECTION_FIRST + 6);
        assertEquals(AlloyCodexGUI.Section.MINERALS, codex.getSection());
        assertNotNull(codex.getInventory().getItem(CONTENT_START), "The catalog must show materials");
        codex.handleClick(player, SLOT_SECTION_FIRST + 4);
        assertEquals(AlloyCodexGUI.Section.EXPLORER, codex.getSection());

        // The explorer picker has 138 entries across 4 pages of 36.
        long mixable = materials.getAll().stream().filter(AlloyRegistry::isMixable).count();
        int pages = (int) Math.ceil((16 + VanillaCatalyst.values().length + mixable) / 36.0);

        codex.handleClick(player, SLOT_PREV);
        assertNotNull(codex.getInventory().getItem(CONTENT_START), "Previous on page 1 must do nothing");

        for (int i = 0; i < pages + 3; i++) {
            codex.handleClick(player, SLOT_NEXT);
        }
        assertEquals(org.bukkit.Material.GRAY_STAINED_GLASS_PANE,
                codex.getInventory().getItem(SLOT_NEXT).getType(),
                "The last page must not offer another page");

        for (int i = 0; i < pages + 3; i++) {
            codex.handleClick(player, SLOT_PREV);
        }
        assertEquals(org.bukkit.Material.GRAY_STAINED_GLASS_PANE,
                codex.getInventory().getItem(SLOT_PREV).getType(),
                "The first page must not offer a previous page");
    }

    @Test
    @DisplayName("The registry scope lists every registered item id, filterable by kind")
    void registryScopeListsEveryItemId() {
        codex.handleClick(player, SLOT_SECTION_FIRST + 6);
        assertEquals(AlloyCodexGUI.Section.MINERALS, codex.getSection());
        assertFalse(codex.isRegistryScope(), "The catalog opens on the curated materials");

        int registryIds = plugin.getItemRegistry().getAvailableItemIdCount();
        assertTrue(registryIds > 2000, "The registry must hold thousands of ids, got " + registryIds);

        codex.handleClick(player, SLOT_SCOPE);
        assertTrue(codex.isRegistryScope());
        assertEquals(registryIds, codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "The flat scope must expose every registered id, not just one per material");
        assertTrue(registryIds > materials.getAll().size() * 10,
                "Every material contributes far more than one id");

        // A kind filter narrows the listing to ids of that kind only.
        int expectedRaw = 0;
        for (String id : plugin.getItemRegistry().getAllItemIds()) {
            if (id.endsWith("_raw")) expectedRaw++;
        }
        codex.handleClick(player, SLOT_KIND);
        assertEquals(com.chagui68.multiversetinker.items.TinkerItemRegistry.ItemKind.RAW, codex.getKindFilter());
        assertEquals(expectedRaw, codex.entryCount(AlloyCodexGUI.Section.MINERALS));

        // And the page can be walked without ever leaving the range.
        int pages = (int) Math.ceil(expectedRaw / 36.0);
        for (int i = 0; i < pages + 2; i++) {
            codex.handleClick(player, SLOT_NEXT);
        }
        assertEquals(org.bukkit.Material.GRAY_STAINED_GLASS_PANE,
                codex.getInventory().getItem(SLOT_NEXT).getType(), "The last page must close the listing");
        assertNotNull(codex.registryIdAt(expectedRaw - 1), "The final id must still be reachable");
        assertNull(codex.registryIdAt(expectedRaw), "Out-of-range ids must not resolve");

        // Cycling the filter all the way back to ALL restores the full registry.
        for (int i = 0; i < com.chagui68.multiversetinker.items.TinkerItemRegistry.ItemKind.values().length; i++) {
            codex.handleClick(player, SLOT_KIND);
        }
        assertNull(codex.getKindFilter(), "The filter must cycle back to ALL");
        assertEquals(registryIds, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
    }

    @Test
    @DisplayName("Clicking a material drills into every id that belongs to it")
    void catalogDrillsIntoAMaterial() {
        codex.handleClick(player, SLOT_SECTION_FIRST + 6);

        TinkerMaterial copper = materials.get("mvtink_copper");
        assertNotNull(copper);
        int copperIndex = new ArrayList<>(materials.getAll()).indexOf(copper);
        assertTrue(copperIndex >= 0, "Copper must be in the catalog");

        int slot = CONTENT_START + (copperIndex % 36);
        for (int page = 0; page < copperIndex / 36; page++) {
            codex.handleClick(player, SLOT_NEXT);
        }
        codex.handleClick(player, slot);

        assertNotNull(codex.getCatalogTarget(), "Clicking a material must drill into it");
        assertEquals(copper.getId(), codex.getCatalogTarget().getId());

        long expected = plugin.getItemRegistry().getAllItemIds().stream()
                .filter(id -> id.startsWith(copper.getId()))
                .count();
        assertEquals(expected, codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "The drill-down must list every id of that material");
        assertTrue(expected >= 15, "A material owns a raw, ingot, nugget, block, bucket and ten parts");
        // Each entry names its own id, so a player can copy it into /mvtink give.
        String entryText = plainText(codex.getInventory().getItem(CONTENT_START));
        assertTrue(entryText.contains(copper.getId()), "The entry must name the id, got: " + entryText);

        // Any click in the drill-down returns to the material list.
        codex.handleClick(player, CONTENT_START);
        assertNull(codex.getCatalogTarget());
    }

    @Test
    @DisplayName("The catalog lists every material with its trait and never forges anything")
    void catalogListsEveryMaterial() {
        codex.handleClick(player, SLOT_SECTION_FIRST + 6);
        assertEquals(AlloyCodexGUI.Section.MINERALS, codex.getSection());

        TinkerMaterial copper = materials.get("mvtink_copper");
        assertNotNull(copper);

        long pages = (long) Math.ceil(materials.getAll().size() / 36.0);
        int found = 0;
        for (long page = 0; page < pages; page++) {
            for (int slot = CONTENT_START; slot < CONTENT_START + 36; slot++) {
                ItemStack entry = codex.getInventory().getItem(slot);
                if (entry == null || entry.getType() == org.bukkit.Material.GRAY_STAINED_GLASS_PANE) continue;
                String text = plainText(entry);
                if (text.contains("ID: " + copper.getId())) {
                    found++;
                    assertTrue(text.contains(copper.getTraitName()),
                            "The catalog entry must name the material trait, got: " + text);
                    assertTrue(text.contains(copper.getOrigin().name()),
                            "The catalog entry must name the dimension, got: " + text);
                }
            }
            codex.handleClick(player, SLOT_NEXT);
        }
        assertEquals(1, found, "Copper must appear exactly once in the catalog");
    }

    @Test
    @DisplayName("Every codex entry prints the perk word its mineral lends, before it is spent")
    void codexPrintsThePerkEpithet() {
        // The legend, so the word printed on each entry has a meaning a player can look up in the codex.
        codex.handleClick(player, CATALOG_SECTION);
        String info = plainText(codex.getInventory().getItem(SLOT_INFO));
        assertTrue(info.contains("Perk epithet"), "The catalog must explain the word it prints: " + info);

        // The mineral catalog: every entry that names a material prints that material's exact word.
        Pattern id = Pattern.compile("ID: (mvtink_[a-z0-9_]+)");
        int checked = 0;
        long pages = (long) Math.ceil(materials.getAll().size() / 36.0);
        for (long page = 0; page < pages; page++) {
            for (int slot = CONTENT_START; slot < CONTENT_START + 36; slot++) {
                ItemStack entry = codex.getInventory().getItem(slot);
                if (entry == null || entry.getType() == org.bukkit.Material.GRAY_STAINED_GLASS_PANE) continue;
                String text = plainText(entry);
                Matcher matcher = id.matcher(text);
                if (!matcher.find()) continue;
                TinkerMaterial material = materials.get(matcher.group(1));
                assertNotNull(material, matcher.group(1) + " must be registered");
                assertTrue(text.contains("Perk epithet: " + PerkEpithet.of(material)),
                        "The entry must print the word the forge will use, got: " + text);
                checked++;
            }
            codex.handleClick(player, SLOT_NEXT);
        }
        assertTrue(checked > 30, "The whole catalog must be walked, only " + checked + " entries checked");

        // The legendary recipes and the catalysts carry their own word too.
        codex.handleClick(player, SLOT_SECTION_FIRST);
        assertEquals(AlloyCodexGUI.Section.LEGENDARY, codex.getSection());
        assertTrue(pageText().contains("Perk epithet: " + PerkEpithet.of(materials.get("mvtink_cosmic_netherite"))),
                "A legendary entry must print the word it lends");

        codex.handleClick(player, SLOT_SECTION_FIRST + 1);
        assertEquals(AlloyCodexGUI.Section.CATALYSTS, codex.getSection());
        assertTrue(pageText().contains("Perk epithet: "
                        + PerkEpithet.of(materials.get("mvtink_catalyst_nether_star"))),
                "A catalyst entry must print the word it lends");

        // The explorer picker is the page a player reads right before choosing what to blend.
        codex.handleClick(player, SLOT_SECTION_FIRST + 4);
        assertEquals(AlloyCodexGUI.Section.EXPLORER, codex.getSection());
        assertTrue(pageText().contains("Perk epithet: "),
                "The explorer choices must print the words they lend");
    }

    /** Plain text of every entry the codex is showing on the current page. */
    private String pageText() {
        StringBuilder text = new StringBuilder();
        for (int slot = CONTENT_START; slot < CONTENT_START + 36; slot++) {
            ItemStack entry = codex.getInventory().getItem(slot);
            if (entry == null) continue;
            text.append(plainText(entry)).append('\n');
        }
        return text.toString();
    }

    @Test
    @DisplayName("The explorer lists every valid partner for a mineral without forging anything")
    void explorerListsPartnersWithoutSideEffects() {
        codex.handleClick(player, SLOT_SECTION_FIRST + 4);
        assertEquals(AlloyCodexGUI.Section.EXPLORER, codex.getSection());

        // Select copper: it sits after the 16 legendaries and 12 catalysts in the picker.
        int copperIndex = 16 + VanillaCatalyst.values().length + indexOfMixable("mvtink_copper");
        int slot = CONTENT_START + (copperIndex % 36);
        for (int page = 0; page < copperIndex / 36; page++) {
            codex.handleClick(player, SLOT_NEXT);
        }
        codex.handleClick(player, slot);

        TinkerMaterial copper = materials.get("mvtink_copper");
        assertNotNull(copper);
        assertEquals(copper.getId(), codex.getExplorerTarget().getId(), "Copper must be the selected material");

        int before = alloys.getAllAlloys().size();

        long expected = materials.getAll().stream()
                .filter(candidate -> !candidate.getId().equalsIgnoreCase(copper.getId()))
                .filter(candidate -> !AlloyRegistry.isPrime(candidate))
                .filter(candidate -> alloys.isCraftablePair(copper, candidate))
                .count();

        int listed = codex.entryCount(AlloyCodexGUI.Section.EXPLORER);
        assertEquals(expected, listed,
                "The explorer must list exactly the partners the crucible accepts");
        assertTrue(listed > 0, "Copper must have partners");

        int after = alloys.getAllAlloys().size();
        assertEquals(before, after, "Browsing combinations must never forge an alloy");
    }

    @Test
    @DisplayName("Explorer previews match the id and name the crucible would actually create")
    void previewsMatchCrucibleNaming() {
        TinkerMaterial copper = materials.get("mvtink_copper");
        TinkerMaterial zinc = materials.get("mvtink_zinc");
        TinkerMaterial manyullyn = materials.get("mvtink_manyullyn");
        TinkerMaterial blueIce = materials.get(VanillaCatalyst.BLUE_ICE.getMaterialId());

        TinkerAlloy composite = alloys.findOrCreateAlloy(copper, zinc, materials);
        assertEquals(composite.id(), AlloyRegistry.dynamicId(copper, zinc), "Composite preview id must match");
        assertEquals(composite.name(), AlloyRegistry.dynamicName(copper, zinc), "Composite preview name must match");

        TinkerAlloy prime = alloys.findOrCreateAlloy(manyullyn, blueIce, materials);
        assertEquals(prime.id(), AlloyRegistry.dynamicId(manyullyn, blueIce), "Prime preview id must match");
        assertEquals(prime.name(), AlloyRegistry.dynamicName(manyullyn, blueIce), "Prime preview name must match");
        assertTrue(prime.id().startsWith(AlloyRegistry.PRIME_PREFIX));
    }

    @Test
    @DisplayName("A legendary in the explorer offers minerals, legendaries, composites and catalysts")
    void legendaryExplorerCoversAllPrimePartners() {
        List<String> partners = partnerIds(materials.get("mvtink_bronze"));

        assertTrue(partners.contains("mvtink_tin"), "A legendary must list minerals");
        assertTrue(partners.contains("mvtink_manyullyn"), "A legendary must list other legendaries");
        assertTrue(partners.contains("mvtink_catalyst_nether_star"), "A legendary must list the catalysts");
        assertTrue(partners.stream().noneMatch(AlloyRegistry.PRIME_PREFIX::startsWith),
                "Primes must never appear as partners");
    }

    @Test
    @DisplayName("An empty Prime Alloys section explains why it is empty and fills up once a prime is forged")
    void emptyPrimeSectionExplainsItself() {
        assertEquals(0, codex.entryCount(AlloyCodexGUI.Section.PRIMES), "A fresh server has no primes");

        codex.handleClick(player, SLOT_SECTION_FIRST + 3);
        assertEquals(AlloyCodexGUI.Section.PRIMES, codex.getSection());

        String hint = plainText(codex.getInventory().getItem(22));
        assertTrue(hint.contains("No prime alloys forged yet"), "Empty section must say so, got: " + hint);
        assertTrue(hint.contains("this server"), "It must be clear only forged primes are listed");
        assertTrue(hint.contains("Legendary alloy"), "The unlock recipe must be spelled out");

        alloys.findOrCreateAlloy(materials.get("mvtink_manyullyn"),
                materials.get(VanillaCatalyst.BLUE_ICE.getMaterialId()), materials);
        codex.render();

        assertEquals(1, codex.entryCount(AlloyCodexGUI.Section.PRIMES), "The forged prime must be listed");
        assertNull(codex.getInventory().getItem(22), "The explanation must disappear once a prime exists");
    }

    @Test
    @DisplayName("The catalog filters by dimension, rarity and essence, and the three stack")
    void catalogFiltersStack() {
        codex.handleClick(player, CATALOG_SECTION);
        assertEquals(materials.getAll().size(), codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        assertFalse(codex.hasMaterialFilters(), "The catalog must open unfiltered");

        // A filter button lists its values instead of cycling them, so one essence is two clicks away.
        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        assertEquals(AlloyCodexGUI.FilterKind.ORIGIN, codex.getFilterPicker());
        assertEquals(1 + MineralOrigin.values().length, codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "The picker must offer Any plus every dimension");
        codex.handleClick(player, CONTENT_START + 1 + MineralOrigin.NETHER.ordinal());

        assertNull(codex.getFilterPicker(), "Picking a value must close the picker");
        assertEquals(MineralOrigin.NETHER, codex.getOriginFilter());
        assertTrue(codex.hasMaterialFilters());
        int byOrigin = matchingMaterials(MineralOrigin.NETHER, null, null);
        assertEquals(byOrigin, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        assertEquals(byOrigin, codex.filteredMaterials().size());
        codex.filteredMaterials().forEach(material -> assertEquals(MineralOrigin.NETHER, material.getOrigin()));

        // Rarity narrows what the dimension left, never restoring what it dropped.
        codex.handleClick(player, SLOT_RARITY_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + MaterialRarity.EPIC.ordinal());
        assertEquals(MaterialRarity.EPIC, codex.getRarityFilter());
        int byRarity = matchingMaterials(MineralOrigin.NETHER, MaterialRarity.EPIC, null);
        assertEquals(byRarity, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        assertTrue(byRarity < byOrigin, "Adding a rarity must narrow the list");

        // And the essence stacks on top of both.
        codex.handleClick(player, SLOT_ESSENCE_FILTER);
        assertEquals(1 + TraitAffinity.values().length, codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "The picker must offer Any plus every essence");
        codex.handleClick(player, CONTENT_START + 1 + TraitAffinity.INFERNAL.ordinal());
        assertEquals(TraitAffinity.INFERNAL, codex.getEssenceFilter());

        int byEssence = matchingMaterials(MineralOrigin.NETHER, MaterialRarity.EPIC, TraitAffinity.INFERNAL);
        assertEquals(byEssence, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        assertTrue(byEssence > 0 && byEssence <= byRarity, "Expected the intersection to narrow further");
        codex.filteredMaterials().forEach(material ->
                assertTrue(TraitAffinity.of(material).contains(TraitAffinity.INFERNAL),
                        material.getId() + " must teach the filtered essence"));

        // Filters must never move the entries around: the first slot is the first material left.
        codex.handleClick(player, CONTENT_START);
        assertEquals(codex.filteredMaterials().get(0).getId(), codex.getCatalogTarget().getId(),
                "Clicking a filtered entry must drill into the material on that slot");
        codex.handleClick(player, CONTENT_START);
        assertNull(codex.getCatalogTarget());

        // The info button spells the active filters out, so a narrowed list is never a mystery.
        String info = plainText(codex.getInventory().getItem(SLOT_INFO));
        assertTrue(info.contains("Filters:"), "The info button must report the filters, got: " + info);
        assertTrue(info.contains(MineralOrigin.NETHER.getDescription()), "It must name the dimension: " + info);
        assertTrue(info.contains(MaterialRarity.EPIC.getDisplayName()), "It must name the rarity: " + info);
        assertTrue(info.contains(TraitAffinity.INFERNAL.getDisplayName()), "It must name the essence: " + info);
    }

    @Test
    @DisplayName("The filter picker opens, closes and clears without ever leaving the catalog")
    void filterPickerLifecycle() {
        codex.handleClick(player, CATALOG_SECTION);

        // Clicking the same button again closes the picker and applies nothing.
        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        assertEquals(AlloyCodexGUI.FilterKind.ORIGIN, codex.getFilterPicker());
        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        assertNull(codex.getFilterPicker(), "The same button must close the picker");
        assertNull(codex.getOriginFilter(), "Closing a picker must not filter anything");

        // "Any" is the first option, and clears that filter only.
        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + MineralOrigin.THE_END.ordinal());
        codex.handleClick(player, SLOT_RARITY_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + MaterialRarity.RARE.ordinal());
        assertEquals(MineralOrigin.THE_END, codex.getOriginFilter());
        assertEquals(MaterialRarity.RARE, codex.getRarityFilter());

        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        String anyOption = plainText(codex.getInventory().getItem(CONTENT_START));
        assertTrue(anyOption.contains("Any"), "The first option must clear the filter, got: " + anyOption);
        codex.handleClick(player, CONTENT_START);
        assertNull(codex.getOriginFilter(), "Any must clear the dimension");
        assertEquals(MaterialRarity.RARE, codex.getRarityFilter(), "It must leave the other filters alone");

        codex.handleClick(player, SLOT_RARITY_FILTER);
        codex.handleClick(player, CONTENT_START);
        assertFalse(codex.hasMaterialFilters(), "Picking Any on the last filter must restore the catalog");
        assertEquals(materials.getAll().size(), codex.entryCount(AlloyCodexGUI.Section.MINERALS));

        // Each option announces how many materials it would leave, before clicking it.
        codex.handleClick(player, SLOT_RARITY_FILTER);
        String rare = plainText(codex.getInventory().getItem(CONTENT_START + 1 + MaterialRarity.RARE.ordinal()));
        assertTrue(rare.contains(String.valueOf(matchingMaterials(null, MaterialRarity.RARE, null))),
                "The option must show how many materials it leaves, got: " + rare);

        // A picker never survives a section change: leaving the catalog drops it.
        codex.handleClick(player, SLOT_SECTION_FIRST);
        assertEquals(AlloyCodexGUI.Section.LEGENDARY, codex.getSection());
        assertNull(codex.getFilterPicker(), "Switching sections must close the picker");
    }

    @Test
    @DisplayName("Filters narrow the every-item scope and hide the items that belong to no material")
    void filtersNarrowTheEveryItemScope() {
        codex.handleClick(player, CATALOG_SECTION);
        codex.handleClick(player, SLOT_SCOPE);
        assertTrue(codex.isRegistryScope());

        int unfiltered = codex.entryCount(AlloyCodexGUI.Section.MINERALS);
        assertEquals(plugin.getItemRegistry().getAvailableItemIdCount(), unfiltered);
        assertTrue(registryIdAt(0).startsWith("mvtink_"), "Ids are listed, not materials");

        // The standalone items (the crucible, the brush and the casts) belong to no mineral, so they
        // are the only ids a dimension filter can drop beyond the materials of the other dimensions.
        long orphans = plugin.getItemRegistry().getAllItemIds().stream()
                .filter(id -> materialOf(id) == null)
                .count();
        assertTrue(orphans >= 3, "The crucible, the brush and the casts must be registered, got " + orphans);
        assertTrue(listedIds(unfiltered).contains("mvtink_smeltery"), "The crucible is listed while unfiltered");

        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + MineralOrigin.OVERWORLD.ordinal());
        assertEquals(MineralOrigin.OVERWORLD, codex.getOriginFilter());

        int filtered = codex.entryCount(AlloyCodexGUI.Section.MINERALS);
        long owned = plugin.getItemRegistry().getAllItemIds().stream()
                .filter(id -> {
                    TinkerMaterial owner = materialOf(id);
                    return owner != null && owner.getOrigin() == MineralOrigin.OVERWORLD;
                })
                .count();
        assertEquals(owned, filtered, "Only the ids of the Overworld materials may survive the filter");
        assertTrue(filtered < unfiltered - orphans,
                "Filtering must drop the ids of every other dimension as well as the standalone items");
        List<String> ids = listedIds(filtered);
        assertFalse(ids.contains("mvtink_smeltery"), "A filter must hide the items that belong to no material");
        for (String id : ids) {
            TinkerMaterial owner = materialOf(id);
            assertNotNull(owner, id + " must belong to a material once a filter is applied");
            assertEquals(MineralOrigin.OVERWORLD, owner.getOrigin(), id + " belongs to " + owner.getId());
        }

        // The kind filter and the catalog filters are independent and combine.
        codex.handleClick(player, SLOT_KIND);
        assertEquals(com.chagui68.multiversetinker.items.TinkerItemRegistry.ItemKind.RAW, codex.getKindFilter());
        int rawOverworld = 0;
        for (String id : ids) {
            if (id.endsWith(com.chagui68.multiversetinker.items.TinkerItemRegistry.ItemKind.RAW.getSuffix())) rawOverworld++;
        }
        assertEquals(rawOverworld, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        assertTrue(rawOverworld > 0, "An Overworld material must have a raw id");

        // The kind filter and the catalog filters combine: one raw id per material left.
        codex.handleClick(player, SLOT_ESSENCE_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + TraitAffinity.TERRAIN.ordinal());
        assertEquals(TraitAffinity.TERRAIN, codex.getEssenceFilter());
        int terrain = matchingMaterials(MineralOrigin.OVERWORLD, null, TraitAffinity.TERRAIN);
        assertTrue(terrain > 0, "Terrain is an Overworld essence");
        assertEquals(terrain, codex.entryCount(AlloyCodexGUI.Section.MINERALS),
                "With the RAW kind filter on, every material left contributes exactly one id");
        for (String id : listedIds(terrain)) {
            assertTrue(TraitAffinity.of(materialOf(id)).contains(TraitAffinity.TERRAIN), id + " must teach Terrain");
        }
    }

    @Test
    @DisplayName("A filter combination that matches nothing explains itself instead of showing a blank grid")
    void emptyFilterCombinationExplainsItself() {
        codex.handleClick(player, CATALOG_SECTION);

        // Find a dimension/rarity/essence triple the registry cannot satisfy.
        MineralOrigin origin = null;
        MaterialRarity rarity = null;
        TraitAffinity essence = null;
        for (MineralOrigin candidateOrigin : MineralOrigin.values()) {
            for (MaterialRarity candidateRarity : MaterialRarity.values()) {
                for (TraitAffinity candidateEssence : TraitAffinity.values()) {
                    if (matchingMaterials(candidateOrigin, candidateRarity, candidateEssence) != 0) continue;
                    origin = candidateOrigin;
                    rarity = candidateRarity;
                    essence = candidateEssence;
                    break;
                }
                if (origin != null) break;
            }
            if (origin != null) break;
        }
        assertNotNull(origin, "The registry must have at least one empty filter combination to test");

        codex.handleClick(player, SLOT_ORIGIN_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + origin.ordinal());
        codex.handleClick(player, SLOT_RARITY_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + rarity.ordinal());
        codex.handleClick(player, SLOT_ESSENCE_FILTER);
        codex.handleClick(player, CONTENT_START + 1 + essence.ordinal());

        assertEquals(0, codex.entryCount(AlloyCodexGUI.Section.MINERALS));
        String hint = plainText(codex.getInventory().getItem(22));
        assertTrue(hint.contains("No item matches this filter"), "The empty catalog must say so, got: " + hint);
        assertTrue(hint.contains("Any"), "It must point at the way out, got: " + hint);

        // Widening any of the three brings the catalog back.
        codex.handleClick(player, SLOT_RARITY_FILTER);
        codex.handleClick(player, CONTENT_START);
        assertNull(codex.getRarityFilter());
        assertEquals(matchingMaterials(origin, null, essence), codex.entryCount(AlloyCodexGUI.Section.MINERALS));
    }

    // ==========================================
    // HELPERS
    // ==========================================
    private String plainText(ItemStack item) {
        assertNotNull(item, "Expected an item in the codex slot");
        StringBuilder builder = new StringBuilder(
                PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName()));
        if (item.getItemMeta().lore() != null) {
            for (Component line : item.getItemMeta().lore()) {
                builder.append(' ').append(PlainTextComponentSerializer.plainText().serialize(line));
            }
        }
        return builder.toString();
    }

    private List<String> partnerIds(TinkerMaterial target) {
        List<String> ids = new ArrayList<>();
        for (TinkerMaterial candidate : materials.getAll()) {
            if (candidate.getId().equalsIgnoreCase(target.getId())) continue;
            if (AlloyRegistry.isPrime(candidate)) continue;
            if (alloys.isCraftablePair(target, candidate)) ids.add(candidate.getId());
        }
        return ids;
    }

    /** How many materials pass a dimension/rarity/essence combination, the definition of the filters. */
    private int matchingMaterials(MineralOrigin origin, MaterialRarity rarity, TraitAffinity essence) {
        int count = 0;
        for (TinkerMaterial material : materials.getAll()) {
            if (origin != null && material.getOrigin() != origin) continue;
            if (rarity != null && material.getRarity() != rarity) continue;
            if (essence != null && !TraitAffinity.of(material).contains(essence)) continue;
            count++;
        }
        return count;
    }

    /**
     * Material a registered id belongs to, resolved by the longest registered material id that prefixes
     * it. Standalone ids (the crucible, the brush, the casts) resolve to {@code null}.
     */
    private TinkerMaterial materialOf(String id) {
        String lower = id.toLowerCase(Locale.ROOT);
        TinkerMaterial owner = null;
        for (TinkerMaterial material : materials.getAll()) {
            String base = material.getId().toLowerCase(Locale.ROOT);
            if (!lower.startsWith(base)) continue;
            if (owner == null || base.length() > owner.getId().length()) owner = material;
        }
        return owner;
    }

    /** First {@code count} ids the catalog is showing in its every-item scope. */
    private List<String> listedIds(int count) {
        List<String> ids = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            String id = codex.registryIdAt(index);
            assertNotNull(id, "The catalog must resolve the id at index " + index);
            ids.add(id);
        }
        return ids;
    }

    private String registryIdAt(int index) {
        String id = codex.registryIdAt(index);
        assertNotNull(id, "The catalog must resolve the id at index " + index);
        return id;
    }

    private int indexOfMixable(String materialId) {
        int index = 0;
        for (TinkerMaterial material : materials.getAll()) {
            if (!AlloyRegistry.isMixable(material)) continue;
            if (material.getId().equalsIgnoreCase(materialId)) return index;
            index++;
        }
        return -1;
    }
}
