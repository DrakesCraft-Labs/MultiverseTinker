package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end check of the prime spectacle: a weapon forged from a prime alloy must cast its prime
 * ultimate (freezing the victim for Absolute Zero) and lock the wielder on cooldown.
 */
class PrimeUltimateTest {

    private ServerMock server;
    private MultiverseTinker plugin;
    private MaterialRegistry registry;
    private WorldMock world;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        world = server.addSimpleWorld("world");
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("A Blue Ice prime sword freezes the target with the Absolute Zero spectacle")
    void primeSwordFreezesTarget() {
        TinkerMaterial prime = primeWith(VanillaCatalyst.BLUE_ICE);
        assertEquals(PrimeUltimate.ABSOLUTE_ZERO, PrimeUltimate.of(prime));

        Player player = server.addPlayer("frostsmith");
        Location origin = world.getSpawnLocation();
        player.teleport(origin);
        player.getInventory().setItemInMainHand(swordOf(prime));

        Zombie target = world.spawn(origin, Zombie.class);
        double healthBefore = target.getHealth();

        server.getPluginManager().callEvent(new EntityDamageByEntityEvent(
                player, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 5.0));

        assertTrue(UltimateEffectEngine.isOnCooldown(player),
                "Casting the prime ultimate must lock the wielder on cooldown");

        server.getScheduler().performTicks(40L);

        assertTrue(target.getFreezeTicks() > 0,
                "Absolute Zero must leave the target freezing, was " + target.getFreezeTicks());
        assertTrue(target.hasPotionEffect(PotionEffectType.SLOWNESS),
                "The ice field must root the target in place");
        assertTrue(target.getHealth() <= healthBefore - 7.0,
                "Absolute Zero must land 1.6x the triggering hit, health is now " + target.getHealth());
    }

    @Test
    @DisplayName("Base essentials: a prime weapon out-damages the same weapon made of a plain mineral")
    void primeWeaponBeatsPlainMineral() {
        TinkerMaterial prime = primeWith(VanillaCatalyst.NETHER_STAR);
        assertTrue(prime.getAttackDamageBonus() > registry.get("mvtink_iron").getAttackDamageBonus(),
                "Prime metallurgy must hit harder than plain iron");
        assertTrue(prime.getDurability() > registry.get("mvtink_iron").getDurability(),
                "Prime metallurgy must last longer than plain iron");
    }

    // ==========================================
    // HELPERS
    // ==========================================
    private TinkerMaterial primeWith(VanillaCatalyst catalyst) {
        AlloyRegistry alloys = plugin.getAlloyRegistry();
        TinkerAlloy prime = alloys.findOrCreateAlloy(
                registry.get("mvtink_manyullyn"), registry.get(catalyst.getMaterialId()), registry);
        TinkerMaterial material = registry.get(prime.id());
        assertNotNull(material, "The prime material must be registered");
        return material;
    }

    private ItemStack swordOf(TinkerMaterial material) {
        PartComposition part = PartComposition.fromMaterials(List.of(material));
        return TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD, part, part, part,
                EvolutionTier.WOOD, 0);
    }
}
