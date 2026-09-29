package com.chagui68.multiversetinker.evolution;

import org.bukkit.Material;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvolutionTierTest {

    @Test
    @DisplayName("Tier progression must follow exact sequence from Wood to Netherite")
    void testTierProgression() {
        EvolutionTier tier = EvolutionTier.WOOD;
        assertEquals(EvolutionTier.STONE, tier.getNextTier());

        tier = tier.getNextTier();
        assertEquals(EvolutionTier.COPPER, tier.getNextTier());

        tier = tier.getNextTier();
        assertEquals(EvolutionTier.IRON, tier.getNextTier());

        tier = tier.getNextTier();
        assertEquals(EvolutionTier.GOLD, tier.getNextTier());

        tier = tier.getNextTier();
        assertEquals(EvolutionTier.DIAMOND, tier.getNextTier());

        tier = tier.getNextTier();
        assertEquals(EvolutionTier.NETHERITE, tier.getNextTier());

        tier = tier.getNextTier();
        assertNull(tier.getNextTier(), "Netherite is max tier");
    }

    @Test
    @DisplayName("Requirements must be strictly increasing for kills and blocks broken")
    void testIncreasingRequirements() {
        EvolutionTier[] tiers = EvolutionTier.values();
        for (int i = 0; i < tiers.length - 1; i++) {
            assertTrue(tiers[i + 1].getKillRequirement() > tiers[i].getKillRequirement(),
                    "Kills for " + tiers[i + 1] + " must exceed " + tiers[i]);
            assertTrue(tiers[i + 1].getBlockBreakRequirement() > tiers[i].getBlockBreakRequirement(),
                    "Blocks for " + tiers[i + 1] + " must exceed " + tiers[i]);
        }
    }

    @Test
    @DisplayName("Tier matching materials must return corresponding vanilla materials")
    void testMatchingMaterials() {
        assertEquals(Material.WOODEN_SWORD, EvolutionTier.WOOD.getMatchingSwordMaterial());
        assertEquals(Material.DIAMOND_SWORD, EvolutionTier.DIAMOND.getMatchingSwordMaterial());
        assertEquals(Material.NETHERITE_SWORD, EvolutionTier.NETHERITE.getMatchingSwordMaterial());

        assertEquals(Material.WOODEN_PICKAXE, EvolutionTier.WOOD.getMatchingPickaxeMaterial());
        assertEquals(Material.IRON_PICKAXE, EvolutionTier.IRON.getMatchingPickaxeMaterial());
        assertEquals(Material.NETHERITE_PICKAXE, EvolutionTier.NETHERITE.getMatchingPickaxeMaterial());
    }
}
