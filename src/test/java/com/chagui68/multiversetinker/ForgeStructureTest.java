package com.chagui68.multiversetinker;

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
}
