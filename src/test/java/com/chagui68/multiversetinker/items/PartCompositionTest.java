package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PartCompositionTest {

    private MaterialRegistry registry;
    private TinkerMaterial diamond;
    private TinkerMaterial quartz;
    private TinkerMaterial ruby;

    @BeforeEach
    void setUp() {
        registry = new MaterialRegistry();
        diamond = registry.get("mvtink_diamond");
        quartz = registry.get("mvtink_quartz");
        ruby = registry.get("mvtink_ruby");

        assertNotNull(diamond);
        assertNotNull(quartz);
        assertNotNull(ruby);
    }

    @Test
    @DisplayName("Single material part should have 100% concentration")
    void testSingleMaterial() {
        PartComposition comp = PartComposition.fromMaterials(List.of(diamond));
        assertEquals(1, comp.getEntries().size());
        assertEquals(1.0, comp.getEntries().get(0).ratio(), 0.001);
        assertEquals(diamond.getId(), comp.getPrimaryMaterial().getId());
        assertEquals(diamond.getDurability(), comp.getDurability());
        assertEquals(1.0, comp.getTraitRatio(diamond.getId()), 0.001);
    }

    @Test
    @DisplayName("Two-material part should split 50% / 50%")
    void testDualMaterial() {
        PartComposition comp = PartComposition.fromMaterials(List.of(diamond, quartz));
        assertEquals(2, comp.getEntries().size());
        assertEquals(0.5, comp.getEntries().get(0).ratio(), 0.001);
        assertEquals(0.5, comp.getEntries().get(1).ratio(), 0.001);

        double expectedDur = (diamond.getDurability() * 0.5) + (quartz.getDurability() * 0.5);
        assertEquals((int) Math.round(expectedDur), comp.getDurability());

        assertEquals(0.5, comp.getTraitRatio(diamond.getId()), 0.001);
        assertEquals(0.5, comp.getTraitRatio(quartz.getId()), 0.001);
    }

    @Test
    @DisplayName("Three-material part should split roughly 33% / 33% / 33%")
    void testTripleMaterial() {
        PartComposition comp = PartComposition.fromMaterials(List.of(diamond, quartz, ruby));
        assertEquals(3, comp.getEntries().size());

        double totalRatio = comp.getEntries().stream().mapToDouble(PartComposition.Entry::ratio).sum();
        assertEquals(1.0, totalRatio, 0.01);
    }

    @Test
    @DisplayName("Serialization and deserialization should preserve materials and ratios")
    void testSerialization() {
        PartComposition original = PartComposition.fromMaterials(List.of(diamond, quartz));
        String serialized = original.serialize();
        assertNotNull(serialized);
        assertTrue(serialized.contains("mvtink_diamond"));
        assertTrue(serialized.contains("mvtink_quartz"));

        PartComposition deserialized = PartComposition.deserialize(serialized, registry);
        assertNotNull(deserialized);
        assertEquals(2, deserialized.getEntries().size());
        assertEquals(0.5, deserialized.getTraitRatio(diamond.getId()), 0.01);
        assertEquals(0.5, deserialized.getTraitRatio(quartz.getId()), 0.01);
    }

    @Test
    @DisplayName("Trait trigger check should respect ratio")
    void testTraitTriggerProbability() {
        PartComposition comp = PartComposition.fromMaterials(List.of(diamond));
        Random mockRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.1;
            }
        };
        assertTrue(comp.triggersTrait(diamond.getId(), mockRandom));
        assertFalse(comp.triggersTrait("mvtink_nonexistent", mockRandom));
    }
}
