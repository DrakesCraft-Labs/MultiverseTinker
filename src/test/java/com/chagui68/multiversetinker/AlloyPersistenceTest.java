package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AlloyPersistenceTest {

    private ServerMock server;
    private MultiverseTinker plugin;
    private MaterialRegistry registry;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Composite alloys forged in the crucible survive a server restart")
    void forgedCompositesSurviveRestart() {
        AlloyRegistry live = plugin.getAlloyRegistry();
        TinkerMaterial tin = registry.get("mvtink_tin");
        TinkerMaterial zinc = registry.get("mvtink_zinc");
        assertNotNull(tin);
        assertNotNull(zinc);

        TinkerAlloy forged = live.findOrCreateAlloy(tin, zinc, registry);
        assertEquals("mvtink_alloy_tin_zinc", forged.id());
        assertTrue(live.getDynamicAlloyCount() >= 1, "The composite must be tracked as player-forged");

        live.flush();
        assertTrue(new File(plugin.getDataFolder(), "dynamic-alloys.yml").exists(),
                "Forged composites must be written to disk");

        // Restart: brand new registries, restored from the file the plugin wrote.
        MaterialRegistry freshMaterials = new MaterialRegistry();
        AlloyRegistry restored = new AlloyRegistry();
        restored.enablePersistence(plugin, freshMaterials);

        TinkerMaterial restoredMaterial = freshMaterials.get(forged.id());
        assertNotNull(restoredMaterial, "The forged alloy material must exist again after a restart");
        assertEquals(MaterialType.ALLOY, restoredMaterial.getType());
        assertTrue(restoredMaterial.isAlloy(), "The restored alloy must keep its parent minerals");
        assertEquals(forged.name(), restoredMaterial.getName());
        assertEquals(forged.durabilityBonus(), restoredMaterial.getDurability());
        assertEquals(forged.attackDamageBonus(), restoredMaterial.getAttackDamage(), 1e-9);
        assertFalse(AlloyRegistry.isMixable(restoredMaterial),
                "A restored composite is still a finished alloy and cannot be re-blended");

        assertEquals(16, restored.getAllAlloys().size() - restored.getDynamicAlloyCount(),
                "Only player-forged composites are persisted; the 16 curated recipes are always rebuilt");
        assertTrue(restored.getDynamicAlloyCount() >= 1);
    }

    @Test
    @DisplayName("Prime alloys forged with a vanilla catalyst also survive a restart")
    void forgedPrimesSurviveRestart() {
        AlloyRegistry live = plugin.getAlloyRegistry();
        TinkerMaterial manyullyn = registry.get("mvtink_manyullyn");
        TinkerMaterial blueIce = registry.get("mvtink_catalyst_blue_ice");
        assertNotNull(manyullyn, "Manyullyn must exist");
        assertNotNull(blueIce, "Blue Ice must be registered as a catalyst");

        TinkerAlloy prime = live.findOrCreateAlloy(manyullyn, blueIce, registry);
        assertTrue(prime.id().startsWith(AlloyRegistry.PRIME_PREFIX), "Expected a prime id, got " + prime.id());
        assertEquals(1, live.getPrimeAlloyCount(), "The forged prime must be counted separately");

        live.flush();

        // Restart exactly like the plugin does: catalysts first, then the persisted alloys.
        MaterialRegistry freshMaterials = new MaterialRegistry();
        AlloyRegistry restored = new AlloyRegistry();
        restored.registerCatalystMaterials(freshMaterials);
        restored.enablePersistence(plugin, freshMaterials);

        TinkerMaterial restoredPrime = freshMaterials.get(prime.id());
        assertNotNull(restoredPrime, "The prime material must exist again after a restart");
        assertTrue(AlloyRegistry.isPrime(restoredPrime), "The restored alloy must still be a prime");
        assertEquals(MaterialRarity.LEGENDARY, restoredPrime.getRarity(),
                "Restored primes keep their legendary rarity");
        assertFalse(TraitAffinity.of(restoredPrime).isEmpty(),
                "A restored prime must keep inheriting its parents' essences");
        assertEquals(1, restored.getPrimeAlloyCount());
    }
}
