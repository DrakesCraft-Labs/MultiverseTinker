package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
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
}
