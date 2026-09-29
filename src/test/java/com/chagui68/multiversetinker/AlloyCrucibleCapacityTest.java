package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pins down how many distinct alloys the Alloy Crucible tab (section 3 of the forge GUI) can
 * produce, so the documented figure cannot silently drift when minerals or recipes change.
 */
class AlloyCrucibleCapacityTest {

    private MaterialRegistry materials;
    private AlloyRegistry alloys;

    @BeforeEach
    void setUp() {
        materials = new MaterialRegistry();
        alloys = new AlloyRegistry();
        alloys.registerAlloysIntoMaterialRegistry(materials);
    }

    @Test
    @DisplayName("The crucible can forge 5,997 distinct alloys: 5,995 mineral pairs + 2 netherite recipes")
    void testCrucibleCapacity() {
        List<TinkerMaterial> mixable = materials.getAll().stream()
                .filter(AlloyRegistry::isMixable)
                .toList();

        assertEquals(110, mixable.size(), "110 minerals must be freely blendable");

        long pairs = (long) mixable.size() * (mixable.size() - 1) / 2;
        assertEquals(5_995L, pairs, "Two-slot crucible: C(110, 2) = 5,995 mineral pairs");

        assertEquals(2, curatedOnlyPairs(),
                "Only Cinder Steel and Cosmic Netherite use a parent that is itself an alloy");
        assertEquals(5_997L, pairs + curatedOnlyPairs(),
                "Total distinct alloys forgeable in the Alloy Crucible");
    }

    @Test
    @DisplayName("Finished alloys are rejected by the crucible, keeping the combination space finite")
    void testAlloysAreNotBlendable() {
        for (String id : List.of("mvtink_bronze", "mvtink_manyullyn", "mvtink_netherite", "mvtink_electrum")) {
            TinkerMaterial alloy = materials.get(id);
            assertNotNull(alloy, id + " must exist");
            assertFalse(AlloyRegistry.isMixable(alloy), id + " must not be a free crucible input");
        }
    }

    /** Pairs the crucible accepts that are not two freely blendable minerals. */
    private long curatedOnlyPairs() {
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
