package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Guarantees the admin command can hand out <b>every</b> item the plugin defines, including the
 * composites and prime alloys a player forges after startup.
 */
class ItemRegistryCoverageTest {

    private MultiverseTinker plugin;
    private MaterialRegistry materials;
    private AlloyRegistry alloys;
    private TinkerItemRegistry items;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        materials = plugin.getMaterialRegistry();
        alloys = plugin.getAlloyRegistry();
        items = plugin.getItemRegistry();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Every registered material resolves raw, ingot, nugget, block, molten bucket and all parts")
    void everyMaterialResolvesEveryKind() {
        List<String> unresolved = new ArrayList<>();
        int checked = 0;

        for (TinkerMaterial material : materials.getAll()) {
            String baseId = material.getId().toLowerCase(Locale.ROOT);
            assertNotNull(items.getItemById(baseId), "Bare material id must resolve: " + baseId);

            for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
                checked++;
                String id = baseId + kind.getSuffix();
                ItemStack item = items.getItemById(id);
                if (item == null || item.getType() == Material.AIR) {
                    unresolved.add(id);
                }
            }
        }

        assertTrue(checked > 0, "The registry must expose at least one material");
        assertTrue(unresolved.isEmpty(), "Unresolvable item ids: " + unresolved);
    }

    @Test
    @DisplayName("Legacy suffixes and the utility items still resolve, and the prefix is optional")
    void legacyAliasesAndUtilityItemsResolve() {
        for (String id : List.of("mvtink_smeltery", "mvtink_brush_prospector")) {
            assertNotNull(items.getItemById(id), id + " must resolve");
        }
        for (CastType cast : CastType.values()) {
            assertNotNull(items.getItemById(cast.getId().toLowerCase(Locale.ROOT)), cast + " must resolve");
        }

        assertNotNull(items.getItemById("mvtink_tin_processed"), "_processed is a legacy alias of _ingot");
        assertNotNull(items.getItemById("mvtink_tin_handle"), "_handle is a legacy alias of _rod");
        assertNotNull(items.getItemById("mvtink_tin_pommel"), "_pommel is a legacy alias of _binding");

        assertNull(items.getItemById("mvtink_not_a_real_item"), "Unknown ids must still fail cleanly");
    }

    @Test
    @DisplayName("Alloys forged after startup are immediately givable, parts included")
    void runtimeForgedAlloysAreCovered() {
        // A composite and a prime, both created long after the item registry was built.
        TinkerMaterial composite = materials.get(
                alloys.findOrCreateAlloy(materials.get("mvtink_tin"), materials.get("mvtink_zinc"), materials).id());
        TinkerMaterial prime = materials.get(alloys.findOrCreateAlloy(
                materials.get("mvtink_manyullyn"),
                materials.get(VanillaCatalyst.PACKED_ICE.getMaterialId()), materials).id());

        assertNotNull(composite);
        assertNotNull(prime);

        for (TinkerMaterial forged : List.of(composite, prime)) {
            String baseId = forged.getId().toLowerCase(Locale.ROOT);
            for (TinkerItemRegistry.ItemKind kind : TinkerItemRegistry.ItemKind.values()) {
                String id = baseId + kind.getSuffix();
                assertNotNull(items.getItemById(id),
                        "A runtime-forged alloy must be givable: " + id);
                assertTrue(items.getAllItemIds().contains(id),
                        "Tab-completion must offer: " + id);
            }
        }
    }

    @Test
    @DisplayName("The material catalog also covers the 12 vanilla catalysts and every alloy")
    void catalystsAndAlloysAreCovered() {
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            assertNotNull(materials.get(catalyst.getMaterialId()), catalyst + " must be registered");
            assertNotNull(items.getItemById(catalyst.getMaterialId() + "_ingot"),
                    catalyst + " must have a givable ingot");
        }
        for (TinkerMaterial alloy : List.of(
                materials.get("mvtink_bronze"), materials.get("mvtink_cosmic_netherite"))) {
            assertNotNull(alloy);
            assertNotNull(items.getItemById(alloy.getId() + "_ingot"));
        }
    }

    @Test
    @DisplayName("An item id splits into the material it names and the kind it asks for")
    void itemIdsSplitIntoTheirMaterialAndKind() {
        assertEquals(new TinkerItemRegistry.IdRequest("mvtink_tin", TinkerItemRegistry.ItemKind.INGOT, "_ingot"),
                TinkerItemRegistry.parseId("mvtink_tin_ingot"));

        // A bare material id asks for the material itself, and an id that belongs to no material keeps
        // its own name instead of being cut in two.
        assertEquals(new TinkerItemRegistry.IdRequest("mvtink_tin", null, ""),
                TinkerItemRegistry.parseId("mvtink_tin"));
        assertEquals(new TinkerItemRegistry.IdRequest("mvtink_smeltery", null, ""),
                TinkerItemRegistry.parseId("mvtink_smeltery"));

        // A legacy alias keeps the spelling that was asked for, and case never matters.
        TinkerItemRegistry.IdRequest alias = TinkerItemRegistry.parseId("mvtink_tin_processed");
        assertEquals(TinkerItemRegistry.ItemKind.INGOT, alias.kind());
        assertEquals("mvtink_tin", alias.materialId());
        assertEquals("_processed", alias.suffix());

        // The same request re-spelled against another material is what lets an id that resolved to a
        // differently named alloy keep the kind it asked for.
        TinkerItemRegistry.IdRequest request = TinkerItemRegistry.parseId("MVTINK_TIN_INGOT");
        assertEquals("mvtink_tin_ingot", request.idFor("mvtink_tin"));
        assertEquals("mvtink_alloy_tin_zinc_ingot", request.idFor("mvtink_alloy_tin_zinc"));
    }

    @Test
    @DisplayName("No material id ends with a kind suffix, so an id is never cut in the middle of one")
    void materialIdsNeverLookLikeItemKinds() {
        for (TinkerMaterial material : materials.getAll()) {
            String id = material.getId().toLowerCase(Locale.ROOT);
            TinkerItemRegistry.IdRequest request = TinkerItemRegistry.parseId(id);
            assertNull(request.kind(), id + " must not read as a material plus an item kind");
            assertEquals(id, request.materialId());
        }
    }

    @Test
    @DisplayName("Item ids are generated per material without eagerly building stacks")
    void idIndexIsGeneratedPerMaterial() {
        int expectedPerMaterial = TinkerItemRegistry.kindsPerMaterial() + 4; // + bare, _processed, _handle, _pommel
        assertTrue(items.getAvailableItemIdCount() >= materials.getAll().size() * expectedPerMaterial,
                "Every material must expose at least " + expectedPerMaterial + " ids");

        // Composition parsing is memoized per registry, so repeated lookups share one instance.
        String raw = PartComposition.fromMaterials(List.of(materials.get("mvtink_tin"))).serialize();
        assertSame(materials.composition(raw), materials.composition(raw),
                "The composition cache must reuse the parsed instance");
        assertEquals(1.0, materials.composition(raw).getTraitRatio("mvtink_tin"), 1e-9);
    }
}
