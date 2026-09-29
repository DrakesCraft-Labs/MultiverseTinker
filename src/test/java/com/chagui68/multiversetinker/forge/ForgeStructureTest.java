package com.chagui68.multiversetinker.forge;

import com.chagui68.multiversetinker.forge.structure.ForgeStructure;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ForgeStructureTest {

    @Test
    public void testLoadForgeStructureFromNbt() throws Exception {
        ForgeStructure structure = new ForgeStructure();
        try (InputStream is = getClass().getResourceAsStream("/structures/forge.nbt")) {
            assertNotNull(is, "forge.nbt resource should exist on classpath");
            structure.loadFromStream(is, null);
        }

        assertEquals(243, structure.getBlocks(0).size(), "Forge structure should have 243 non-air blocks");
        assertEquals(243, structure.getBlocks(90).size(), "90 degree rotation should have 243 blocks");
        assertEquals(243, structure.getBlocks(180).size(), "180 degree rotation should have 243 blocks");
        assertEquals(243, structure.getBlocks(270).size(), "270 degree rotation should have 243 blocks");
    }

    @Test
    public void testParticleDataTypes() {
        org.bukkit.Particle[] particles = {
                org.bukkit.Particle.SMALL_FLAME,
                org.bukkit.Particle.WAX_OFF,
                org.bukkit.Particle.SMOKE,
                org.bukkit.Particle.LARGE_SMOKE,
                org.bukkit.Particle.LAVA,
                org.bukkit.Particle.SOUL_FIRE_FLAME,
                org.bukkit.Particle.ENCHANT,
                org.bukkit.Particle.WAX_ON,
                org.bukkit.Particle.FLASH,
                org.bukkit.Particle.TOTEM_OF_UNDYING,
                org.bukkit.Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                org.bukkit.Particle.HAPPY_VILLAGER,
                org.bukkit.Particle.GLOW,
                org.bukkit.Particle.CAMPFIRE_COSY_SMOKE,
                org.bukkit.Particle.SPLASH,
                org.bukkit.Particle.FLAME,
                org.bukkit.Particle.EXPLOSION,
                org.bukkit.Particle.CRIT,
                org.bukkit.Particle.SWEEP_ATTACK,
                org.bukkit.Particle.SNOWFLAKE,
                org.bukkit.Particle.HEART
        };

        for (org.bukkit.Particle p : particles) {
            System.out.println("PARTICLE: " + p.name() + " -> " + p.getDataType());
            if (p == org.bukkit.Particle.FLASH) {
                assertEquals(org.bukkit.Color.class, p.getDataType());
            } else {
                assertEquals(Void.class, p.getDataType(), "Particle " + p.name() + " should not require data");
            }
        }
    }
}
