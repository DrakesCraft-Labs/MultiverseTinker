package com.chagui68.multiversetinker.archaeology;

import com.chagui68.multiversetinker.api.MineralOrigin;
import org.bukkit.Material;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchaeologyBlockTypeTest {

    @Test
    @DisplayName("Debe clasificar correctamente los bloques geologicos por dimension")
    void testBlockOriginMapping() {
        assertEquals(MineralOrigin.OVERWORLD, ArchaeologyBlockType.getOrigin(Material.STONE));
        assertEquals(MineralOrigin.OVERWORLD, ArchaeologyBlockType.getOrigin(Material.COBBLESTONE));
        assertEquals(MineralOrigin.OVERWORLD, ArchaeologyBlockType.getOrigin(Material.DEEPSLATE));

        assertEquals(MineralOrigin.NETHER, ArchaeologyBlockType.getOrigin(Material.NETHERRACK));
        assertEquals(MineralOrigin.NETHER, ArchaeologyBlockType.getOrigin(Material.BLACKSTONE));
        assertEquals(MineralOrigin.NETHER, ArchaeologyBlockType.getOrigin(Material.BASALT));

        assertEquals(MineralOrigin.THE_END, ArchaeologyBlockType.getOrigin(Material.END_STONE));

        assertNull(ArchaeologyBlockType.getOrigin(Material.DIRT));
        assertNull(ArchaeologyBlockType.getOrigin(Material.DIAMOND_BLOCK));
    }

    @Test
    @DisplayName("Degradacion geologica debe ser gradual")
    void testDegradation() {
        assertEquals(Material.COBBLESTONE, ArchaeologyBlockType.getDegradedMaterial(Material.STONE));
        assertEquals(Material.GRAVEL, ArchaeologyBlockType.getDegradedMaterial(Material.COBBLESTONE));
        assertEquals(Material.AIR, ArchaeologyBlockType.getDegradedMaterial(Material.NETHERRACK));
        assertEquals(Material.AIR, ArchaeologyBlockType.getDegradedMaterial(Material.END_STONE));
    }
}
