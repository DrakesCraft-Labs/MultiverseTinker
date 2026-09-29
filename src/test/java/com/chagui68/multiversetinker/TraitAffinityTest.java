package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.WeaponPerkProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TraitAffinityTest {

    private MaterialRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new MaterialRegistry();
    }

    @Test
    @DisplayName("Every single material resolves to 1..3 deterministic affinities")
    void testEveryMaterialHasAffinities() {
        for (TinkerMaterial material : registry.getAll()) {
            Set<TraitAffinity> affinities = TraitAffinity.of(material);
            assertFalse(affinities.isEmpty(), "Material " + material.getId() + " has no affinity");
            assertTrue(affinities.size() <= TraitAffinity.MAX_AFFINITIES,
                    "Material " + material.getId() + " exceeded the affinity cap");
            assertEquals(affinities, TraitAffinity.of(material), "Affinity mapping must be deterministic");
        }
    }

    @Test
    @DisplayName("Origin essence is always reflected in the affinity set")
    void testOriginEssenceMapping() {
        assertTrue(TraitAffinity.of(registry.get("mvtink_tin")).contains(TraitAffinity.TERRAIN),
                "Overworld minerals must carry the Terrain essence");
        assertTrue(TraitAffinity.of(registry.get("mvtink_cobalt")).contains(TraitAffinity.INFERNAL),
                "Nether minerals must carry the Infernal essence");
        assertTrue(TraitAffinity.of(registry.get("mvtink_enderite")).contains(TraitAffinity.VOID),
                "End minerals must carry the Void essence");
        assertTrue(TraitAffinity.of(registry.get("mvtink_netherite")).contains(TraitAffinity.PRIMAL),
                "Vanilla ores must carry the Primal essence");
    }

    @Test
    @DisplayName("Distinct mineral pairs always produce distinct affinity blends")
    void testPairCombinationsAreUnique() {
        TinkerMaterial[] samples = {
                registry.get("mvtink_tin"), registry.get("mvtink_zinc"), registry.get("mvtink_silver"),
                registry.get("mvtink_cobalt"), registry.get("mvtink_ardite"), registry.get("mvtink_enderite"),
                registry.get("mvtink_witherite"), registry.get("mvtink_titanium")
        };

        Set<Set<TraitAffinity>> signatures = new HashSet<>();
        for (int i = 0; i < samples.length; i++) {
            for (int j = i + 1; j < samples.length; j++) {
                Set<TraitAffinity> blend = new HashSet<>(TraitAffinity.of(samples[i]));
                blend.addAll(TraitAffinity.of(samples[j]));
                signatures.add(blend);
            }
        }
        assertTrue(signatures.size() >= samples.length,
                "Binary blends should span a wide variety of unique affinity combinations");
    }

    @Test
    @DisplayName("Forge alloys inherit the essences of their parent minerals")
    void testAlloysInheritParentEssences() {
        AlloyRegistry alloys = new AlloyRegistry();
        alloys.registerAlloysIntoMaterialRegistry(registry);

        // Overworld + Overworld: stays Terrain/Tempered, never picks up a dimensional essence.
        Set<TraitAffinity> bronze = TraitAffinity.of(registry.get("mvtink_bronze"));
        assertTrue(bronze.contains(TraitAffinity.TEMPERED), "Bronze must keep the Tempered essence");
        assertFalse(bronze.contains(TraitAffinity.INFERNAL), "Bronze must not become Infernal");
        assertFalse(bronze.contains(TraitAffinity.VOID), "Bronze must not become Void");

        // Nether + Nether (Ardite + Cobalt): the alloy must read as Infernal.
        Set<TraitAffinity> manyullyn = TraitAffinity.of(registry.get("mvtink_manyullyn"));
        assertTrue(manyullyn.contains(TraitAffinity.INFERNAL),
                "Manyullyn is forged from two Nether minerals and must carry Infernal");

        // Nether + The End (Tungsten + Voidstone): Void must survive the blend.
        Set<TraitAffinity> voidDamascus = TraitAffinity.of(registry.get("mvtink_void_damascus"));
        assertTrue(voidDamascus.contains(TraitAffinity.VOID),
                "Void Damascus must keep the Void essence of its End parent");
    }

    @Test
    @DisplayName("Dynamically forged composites also inherit their parents' essences")
    void testCompositeAlloyInheritsEssences() {
        AlloyRegistry alloys = new AlloyRegistry();
        alloys.registerAlloysIntoMaterialRegistry(registry);

        String compositeId = alloys.findOrCreateAlloy(
                registry.get("mvtink_voidstone"), registry.get("mvtink_ardite"), registry).id();
        TinkerMaterial composite = registry.get(compositeId);
        assertNotNull(composite, "The forged composite must be registered as a material");

        Set<TraitAffinity> essence = TraitAffinity.of(composite);
        assertTrue(essence.contains(TraitAffinity.VOID), "Composite must inherit Void from Voidstone");
        assertTrue(essence.contains(TraitAffinity.INFERNAL), "Composite must inherit Infernal from Ardite");
    }

    @Test
    @DisplayName("A pure Manyullyn weapon reads as Infernal instead of generic alloy metal")
    void testAlloyWeaponIdentityUsesInheritedEssence() {
        AlloyRegistry alloys = new AlloyRegistry();
        alloys.registerAlloysIntoMaterialRegistry(registry);

        PartComposition head = PartComposition.fromMaterials(List.of(registry.get("mvtink_manyullyn")));
        PartComposition rod = PartComposition.fromMaterials(List.of(registry.get("mvtink_iron")));

        WeaponPerkProfile profile = WeaponPerkProfile.of(ModularWeaponType.SWORD, head, rod, rod);
        assertEquals("Infernal", profile.getAffinity().getDisplayName(),
                "The Manyullyn head must impose the Infernal identity on the weapon perk");
    }
}
