package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlloyMixingTest {

    private MaterialRegistry materialRegistry;
    private AlloyRegistry alloyRegistry;

    @BeforeEach
    void setUp() {
        materialRegistry = new MaterialRegistry();
        alloyRegistry = new AlloyRegistry();
        alloyRegistry.registerAlloysIntoMaterialRegistry(materialRegistry);
    }

    @Test
    @DisplayName("Only brush-extracted minerals and vanilla ores may be blended")
    void testMixableSet() {
        assertTrue(AlloyRegistry.isMixable(materialRegistry.get("mvtink_tin")), "Overworld brush minerals are mixable");
        assertTrue(AlloyRegistry.isMixable(materialRegistry.get("mvtink_cobalt")), "Nether brush minerals are mixable");
        assertTrue(AlloyRegistry.isMixable(materialRegistry.get("mvtink_enderite")), "End brush minerals are mixable");
        assertTrue(AlloyRegistry.isMixable(materialRegistry.get("mvtink_iron")), "Vanilla ores are mixable");
        assertTrue(AlloyRegistry.isMixable(materialRegistry.get("mvtink_coal")), "Vanilla coal is mixable");

        assertFalse(AlloyRegistry.isMixable(materialRegistry.get("mvtink_bronze")), "Finished alloys are not mixable");
        assertFalse(AlloyRegistry.isMixable(null), "Null is never mixable");
    }

    @Test
    @DisplayName("Any two distinct brush/vanilla minerals blend into a unique, commutative alloy")
    void testEveryMineralCombinationBlends() {
        Set<String> producedIds = new HashSet<>();
        TinkerMaterial partner = materialRegistry.get("mvtink_tin");
        assertNotNull(partner);
        long expectedAlloys = materialRegistry.getAll().stream().filter(AlloyRegistry::isMixable).count() - 1;

        for (TinkerMaterial material : materialRegistry.getAll()) {
            if (!AlloyRegistry.isMixable(material)) continue;
            if (material.getId().equals(partner.getId())) continue;

            TinkerAlloy alloy = alloyRegistry.findOrCreateAlloy(material, partner, materialRegistry);
            assertNotNull(alloy, "Missing alloy for " + material.getId() + " + tin");
            assertTrue(producedIds.add(alloy.id()), "Duplicate alloy id: " + alloy.id());

            TinkerMaterial result = materialRegistry.get(alloy.id());
            assertNotNull(result, "Blended alloy material must be registered: " + alloy.id());
            assertEquals(MaterialType.ALLOY, result.getType());
            assertNotNull(result.getAlloyParents(), "Blended alloy must record its components");
            assertTrue(result.isAlloy());
        }
        assertEquals(expectedAlloys, producedIds.size(),
                "Every blendable mineral must own exactly one alloy against the partner");
    }

    @Test
    @DisplayName("Alloy creation is commutative and cached")
    void testCommutativeAndCached() {
        TinkerMaterial tin = materialRegistry.get("mvtink_tin");
        TinkerMaterial zinc = materialRegistry.get("mvtink_zinc");

        TinkerAlloy first = alloyRegistry.findOrCreateAlloy(tin, zinc, materialRegistry);
        TinkerAlloy second = alloyRegistry.findOrCreateAlloy(zinc, tin, materialRegistry);

        assertEquals(first.id(), second.id());
        assertSame(first, second, "Repeated blends must reuse the cached alloy instance");
        assertEquals("mvtink_alloy_tin_zinc", first.id());
    }

    @Test
    @DisplayName("Legendary recipes point at real minerals and are all craftable in the crucible")
    void testLegendaryRecipesAreReachable() {
        for (TinkerAlloy alloy : alloyRegistry.getAllAlloys()) {
            TinkerMaterial first = materialRegistry.get(alloy.mat1Id());
            TinkerMaterial second = materialRegistry.get(alloy.mat2Id());

            assertNotNull(first, "Recipe " + alloy.id() + " references a non-existent mineral " + alloy.mat1Id());
            assertNotNull(second, "Recipe " + alloy.id() + " references a non-existent mineral " + alloy.mat2Id());
            assertTrue(alloyRegistry.isCraftablePair(first, second),
                    "Recipe " + alloy.id() + " must be forgeable in the crucible");
        }
    }

    @Test
    @DisplayName("Vanilla netherite may only be blended inside its curated legendary recipes")
    void testNetheriteOnlyWorksInCuratedRecipes() {
        TinkerMaterial netherite = materialRegistry.get("mvtink_netherite");
        TinkerMaterial steel = materialRegistry.get("mvtink_steel");
        TinkerMaterial celestine = materialRegistry.get("mvtink_celestine");
        TinkerMaterial tin = materialRegistry.get("mvtink_tin");

        assertNotNull(netherite);
        assertFalse(AlloyRegistry.isMixable(netherite), "Netherite stays out of free-form blending");

        assertTrue(alloyRegistry.isCraftablePair(netherite, steel), "Cinder Steel must stay forgeable");
        assertTrue(alloyRegistry.isCraftablePair(netherite, celestine), "Cosmic Netherite must stay forgeable");
        assertFalse(alloyRegistry.isCraftablePair(netherite, tin),
                "Netherite must still refuse arbitrary blends with unrelated minerals");
        assertFalse(alloyRegistry.isCraftablePair(tin, tin), "A mineral cannot be paired with itself");
        assertFalse(alloyRegistry.isCraftablePair(materialRegistry.get("mvtink_bronze"), tin),
                "Finished alloys cannot be re-blended");
    }

    @Test
    @DisplayName("Predefined legendary recipes still take priority over dynamic blending")
    void testPredefinedPriority() {
        TinkerMaterial copper = materialRegistry.get("mvtink_copper");
        TinkerMaterial tin = materialRegistry.get("mvtink_tin");

        TinkerAlloy bronze = alloyRegistry.findOrCreateAlloy(tin, copper, materialRegistry);
        assertEquals("mvtink_bronze", bronze.id());
        assertEquals("Bronze", bronze.name());
    }
}
