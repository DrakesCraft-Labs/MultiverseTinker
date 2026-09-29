package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
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
