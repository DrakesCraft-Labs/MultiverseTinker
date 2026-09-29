package com.chagui68.multiversetinker.alloys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlloyRegistryTest {

    private AlloyRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new AlloyRegistry();
    }

    @Test
    @DisplayName("Must register all 16 predefined alloy recipes")
    void testPredefinedAlloysCount() {
        assertEquals(16, registry.getAllAlloys().size(), "There should be exactly 16 default alloys");
    }

    @Test
    @DisplayName("Recipe matching must be commutative (order independent)")
    void testAlloyMatchingCommutative() {
        TinkerAlloy bronze1 = registry.findAlloy("mvtink_copper", "mvtink_tin");
        TinkerAlloy bronze2 = registry.findAlloy("mvtink_tin", "mvtink_copper");

        assertNotNull(bronze1);
        assertNotNull(bronze2);
        assertEquals(bronze1.id(), bronze2.id());
        assertEquals("mvtink_bronze", bronze1.id());
        assertEquals("Bronze", bronze1.name());

        TinkerAlloy manyullyn = registry.findAlloy("mvtink_ardite", "mvtink_cobalt");
        assertNotNull(manyullyn);
        assertEquals("mvtink_manyullyn", manyullyn.id());
    }

    @Test
    @DisplayName("All alloys must have non-null stats and descriptions")
    void testAlloyProperties() {
        for (TinkerAlloy alloy : registry.getAllAlloys()) {
            assertTrue(alloy.id().startsWith("mvtink_"));
            assertNotNull(alloy.name());
            assertNotNull(alloy.colorHex());
            assertNotNull(alloy.traitName());
            assertNotNull(alloy.traitDescription());
            assertTrue(alloy.durabilityBonus() > 0);
            assertTrue(alloy.miningSpeed() > 0);
            assertTrue(alloy.attackDamageBonus() > 0);
        }
    }
}
