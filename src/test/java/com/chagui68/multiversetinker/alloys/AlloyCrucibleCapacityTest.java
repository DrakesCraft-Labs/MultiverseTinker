package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pins down how many distinct alloys the Alloy Crucible tab (section 3 of the forge GUI) can
 * produce, so the documented figures cannot silently drift when minerals, catalysts or recipes
 * change.
 */
class AlloyCrucibleCapacityTest {

    private MaterialRegistry materials;
    private AlloyRegistry alloys;

    @BeforeEach
    void setUp() {
        materials = new MaterialRegistry();
        alloys = new AlloyRegistry();
        alloys.registerCatalystMaterials(materials);
        alloys.registerAlloysIntoMaterialRegistry(materials);
    }

    @Test
    @DisplayName("Base crucible offers 8,085 alloys: 5,995 mineral pairs + 2,090 curated/prime fusions")
    void testBaseCrucibleCapacity() {
        List<TinkerMaterial> mixable = materials.getAll().stream()
                .filter(AlloyRegistry::isMixable)
                .toList();
        assertEquals(110, mixable.size(), "110 minerals must be freely blendable");

        long pairs = (long) mixable.size() * (mixable.size() - 1) / 2;
        assertEquals(5_995L, pairs, "Two-slot crucible: C(110, 2) = 5,995 mineral pairs");

        // 120 legendary pairs + 16 netherite pairs + 1,760 legendary-mineral + 192 legendary-catalyst
        // + the 2 curated recipes that use netherite.
        assertEquals(2_090L, extendedPairs(),
                "A fresh registry must expose exactly 2,090 non-mineral crucible combinations");
        assertEquals(8_085L, pairs + extendedPairs(), "Total distinct alloys forgeable in the crucible");
    }

    @Test
    @DisplayName("With every composite discovered the crucible tops out at 103,781 distinct alloys")
    void testCeilingCapacity() {
        long legendaries = materials.getAll().stream().filter(AlloyRegistry::isLegendary).count();
        assertEquals(16, legendaries, "There must be 16 legendary recipes");

        long catalysts = materials.getAll().stream().filter(AlloyRegistry::isCatalyst).count();
        assertEquals(VanillaCatalyst.values().length, catalysts, "Every catalyst must be registered");

        List<TinkerMaterial> mixable = materials.getAll().stream().filter(AlloyRegistry::isMixable).toList();
        long freeFormLegendaries = alloys.getAllAlloys().stream()
                .filter(alloy -> AlloyRegistry.isMixable(materials.get(alloy.mat1Id()))
                        && AlloyRegistry.isMixable(materials.get(alloy.mat2Id())))
                .count();
        assertEquals(14, freeFormLegendaries, "14 legendary recipes are plain mineral pairs");

        long mineralPairs = (long) mixable.size() * (mixable.size() - 1) / 2;
        long composites = mineralPairs - freeFormLegendaries;
        assertEquals(5_981L, composites, "5,981 of the mineral pairs are player-forged composites");

        // One non-legendary alloy already exists in the base registry: vanilla netherite.
        long nonLegendaryAlloys = 1 + composites;
        long primeFusions = legendaries * (legendaries - 1) / 2
                + legendaries * nonLegendaryAlloys
                + legendaries * mixable.size()
                + legendaries * catalysts;
        assertEquals(97_784L, primeFusions, "Prime fusions reachable once every composite is known");

        long tierOne = mineralPairs + 2; // the two curated netherite recipes
        assertEquals(103_781L, tierOne + primeFusions, "Ceiling of distinct alloys in the crucible");
    }

    @Test
    @DisplayName("Finished alloys and catalysts are rejected by free-form blending")
    void testAlloysAreNotFreelyBlendable() {
        for (String id : List.of("mvtink_bronze", "mvtink_manyullyn", "mvtink_netherite", "mvtink_electrum")) {
            TinkerMaterial alloy = materials.get(id);
            assertNotNull(alloy, id + " must exist");
            assertFalse(AlloyRegistry.isMixable(alloy), id + " must not be a free crucible input");
        }
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial material = materials.get(catalyst.getMaterialId());
            assertNotNull(material, catalyst + " must be registered as a material");
            assertFalse(AlloyRegistry.isMixable(material), catalyst + " must not blend with minerals on its own");
        }
    }

    /** Pairs the crucible accepts that are not two freely blendable minerals. */
    private long extendedPairs() {
        List<TinkerMaterial> all = new ArrayList<>(materials.getAll());
        long count = 0;
        for (int i = 0; i < all.size(); i++) {
            for (int j = i + 1; j < all.size(); j++) {
                TinkerMaterial a = all.get(i);
                TinkerMaterial b = all.get(j);
                if (AlloyRegistry.isMixable(a) && AlloyRegistry.isMixable(b)) continue;
                if (alloys.isCraftablePair(a, b)) count++;
            }
        }
        return count;
    }
}
