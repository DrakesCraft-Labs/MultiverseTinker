package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.*;

class TraitEffectEngineTest {

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
    @DisplayName("Every base mineral resolves weapon effects without errors")
    void testWeaponAffinitiesForEveryMineral() {
        World world = server.addSimpleWorld("trait_weapon");
        Player player = server.addPlayer("BladeTester");
        Zombie target = world.spawn(new Location(world, 0, 65, 0), Zombie.class);

        for (TinkerMaterial material : registry.getAll()) {
            if (material.getType() == MaterialType.ALLOY) continue;
            assertDoesNotThrow(() -> TraitEffectEngine.applyWeaponAffinities(
                            player, target, null, material.getId(), 1.0, registry),
                    "Weapon affinities failed for " + material.getId());
        }
    }

    @Test
    @DisplayName("Every base mineral resolves tool effects without errors")
    void testToolAffinitiesForEveryMineral() {
        World world = server.addSimpleWorld("trait_tool");
        Player player = server.addPlayer("DigTester");
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);

        for (TinkerMaterial material : registry.getAll()) {
            if (material.getType() == MaterialType.ALLOY) continue;
            Block block = world.getBlockAt(4, 65, 4);
            block.setType(Material.STONE);
            BlockBreakEvent event = new BlockBreakEvent(block, player);
            assertDoesNotThrow(() -> TraitEffectEngine.applyToolAffinities(
                            player, event, pickaxe, material.getId(), 1.0, registry),
                    "Tool affinities failed for " + material.getId());
        }
    }

    @Test
    @DisplayName("Every base mineral resolves armor effects without errors")
    void testArmorAffinitiesForEveryMineral() {
        World world = server.addSimpleWorld("trait_armor");
        Player player = server.addPlayer("GuardTester");
        Zombie attacker = world.spawn(new Location(world, 0, 65, 0), Zombie.class);

        for (TinkerMaterial material : registry.getAll()) {
            if (material.getType() == MaterialType.ALLOY) continue;
            assertDoesNotThrow(() -> TraitEffectEngine.applyArmorAffinities(
                            player, attacker, null, material.getId(), 1.0, registry),
                    "Armor affinities failed for " + material.getId());
        }
    }

    @Test
    @DisplayName("A blended alloy records its parents and expands without errors")
    void testDynamicAlloyExpansion() {
        AlloyRegistry alloyRegistry = plugin.getAlloyRegistry();
        TinkerMaterial tin = registry.get("mvtink_tin");
        TinkerMaterial zinc = registry.get("mvtink_zinc");

        TinkerAlloy alloy = alloyRegistry.findOrCreateAlloy(tin, zinc, registry);
        assertNotNull(alloy);
        TinkerMaterial blended = registry.get(alloy.id());
        assertNotNull(blended);
        assertEquals("mvtink_tin,mvtink_zinc", blended.getAlloyParents());
        assertTrue(blended.isAlloy());

        World world = server.addSimpleWorld("trait_alloy");
        Player player = server.addPlayer("AlloyTester");
        Zombie target = world.spawn(new Location(world, 0, 65, 0), Zombie.class);

        assertDoesNotThrow(() -> TraitEffectEngine.applyWeaponAffinities(
                player, target, null, alloy.id(), 1.0, registry));
        assertDoesNotThrow(() -> TraitEffectEngine.applyToolAffinities(
                player, new BlockBreakEvent(world.getBlockAt(6, 65, 6), player),
                new ItemStack(Material.NETHERITE_PICKAXE), alloy.id(), 1.0, registry));
        assertDoesNotThrow(() -> TraitEffectEngine.applyArmorAffinities(
                player, target, null, alloy.id(), 1.0, registry));
    }

    @Test
    @DisplayName("Predefined alloy traits (Bronze, Invar) execute safely")
    void testPredefinedAlloyTraits() {
        World world = server.addSimpleWorld("trait_named_alloy");
        Player player = server.addPlayer("NamedAlloyTester");
        Zombie target = world.spawn(new Location(world, 0, 65, 0), Zombie.class);

        assertDoesNotThrow(() -> TraitEffectEngine.applyWeaponAffinities(
                player, target, null, "mvtink_bronze", 1.0, registry));
        assertDoesNotThrow(() -> TraitEffectEngine.applyWeaponAffinities(
                player, target, null, "mvtink_invar", 1.0, registry));
        assertDoesNotThrow(() -> TraitEffectEngine.applyArmorAffinities(
                player, target, null, "mvtink_bronze", 1.0, registry));
    }
}
