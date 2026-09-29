package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class WeaponPerkRecursionTest {

    private ServerMock server;
    private MaterialRegistry registry;
    private WorldMock world;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        MultiverseTinker plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        world = server.addSimpleWorld("world");
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The weapon perk guard rejects re-entry and resets when the perk completes")
    void weaponPerkGuardBlocksReentrancy() {
        assertTrue(ModularToolListener.enterWeaponPerk(), "The first perk call must be accepted");
        assertTrue(ModularToolListener.isWeaponPerkActive());
        assertFalse(ModularToolListener.enterWeaponPerk(),
                "A perk's own area damage must not re-trigger the perk");
        assertFalse(ModularToolListener.enterWeaponPerk(),
                "Re-entry must stay blocked for the whole perk call stack");

        ModularToolListener.exitWeaponPerk();
        assertFalse(ModularToolListener.isWeaponPerkActive(), "The guard must reset when the perk ends");
        assertTrue(ModularToolListener.enterWeaponPerk(), "Later, unrelated attacks must still work");
        ModularToolListener.exitWeaponPerk();
    }

    @Test
    @DisplayName("The weapon perk guard is per-thread so parallel region work never blocks each other")
    void weaponPerkGuardIsPerThread() throws InterruptedException {
        assertTrue(ModularToolListener.enterWeaponPerk());
        try {
            AtomicBoolean otherThreadEntered = new AtomicBoolean(false);
            Thread worker = new Thread(() -> {
                otherThreadEntered.set(ModularToolListener.enterWeaponPerk());
                ModularToolListener.exitWeaponPerk();
            });
            worker.start();
            worker.join();
            assertTrue(otherThreadEntered.get(), "Another thread must run its own independent perk");
        } finally {
            ModularToolListener.exitWeaponPerk();
        }
    }

    @Test
    @DisplayName("Sweeping a mob cluster must not re-trigger the perk into a stack overflow")
    void swordSweepDoesNotRecurse() {
        Player player = server.addPlayer("smith");
        Location origin = world.getSpawnLocation();
        player.teleport(origin);

        ItemStack sword = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(registry.get("mvtink_cobalt"))),
                PartComposition.fromMaterials(List.of(registry.get("mvtink_iron"))),
                PartComposition.fromMaterials(List.of(registry.get("mvtink_iron"))),
                EvolutionTier.WOOD, 0);
        player.getInventory().setItemInMainHand(sword);

        Zombie target = world.spawn(origin, Zombie.class);
        Zombie left = world.spawn(origin, Zombie.class);
        Zombie right = world.spawn(origin, Zombie.class);

        double targetHealth = target.getHealth();

        assertDoesNotThrow(() -> server.getPluginManager().callEvent(
                        new EntityDamageByEntityEvent(player, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 5.0)),
                "The weapon perk must never re-enter itself through its own area damage");

        // The sweep must still reach the mobs standing next to the primary target.
        assertTrue(left.getHealth() < left.getMaxHealth() || right.getHealth() < right.getMaxHealth(),
                "Sweeping Cleave must still damage adjacent foes");
        assertTrue(target.getHealth() <= targetHealth, "The primary target must keep its damage");
    }
}
