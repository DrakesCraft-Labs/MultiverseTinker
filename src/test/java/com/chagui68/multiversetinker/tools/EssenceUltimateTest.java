package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EssenceUltimateTest {

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
    @DisplayName("Every essence owns a distinct, fully specified cinematic ultimate")
    void everyEssenceHasItsOwnUltimate() {
        Set<String> names = new HashSet<>();

        for (TraitAffinity affinity : TraitAffinity.values()) {
            EssenceUltimate ultimate = EssenceUltimate.of(affinity);
            assertNotNull(ultimate, affinity + " must map to an ultimate");
            assertTrue(names.add(ultimate.getDisplayName()),
                    "Two essences share the ultimate name " + ultimate.getDisplayName());
            assertNotNull(ultimate.getAnimation());
            assertNotNull(ultimate.getTrailParticle());
            assertNotNull(ultimate.getAccentParticle());
            assertNotNull(ultimate.getChargeSound());
            assertNotNull(ultimate.getImpactSound());
            assertTrue(ultimate.getDamageMultiplier() >= 1.0, affinity + " must not weaken the hit");
            assertTrue(ultimate.getRadius() > 0.0, affinity + " needs a real shockwave radius");
            assertTrue(ultimate.getRootTicks() > 0, affinity + " must root the target");
            assertFalse(ultimate.getDescription().isBlank());
            assertTrue(ultimate.getMiniMessageTag().contains(ultimate.getDisplayName()));
        }

        assertEquals(TraitAffinity.values().length, names.size(),
                "Every essence must have its own spectacle");
    }

    @Test
    @DisplayName("A focused weapon roots and crushes the target with its essence ultimate")
    void focusedWeaponUnleashesUltimate() {
        Player player = server.addPlayer("smith");
        Location origin = world.getSpawnLocation();
        player.teleport(origin);
        player.getInventory().setItemInMainHand(pureEssenceSword("mvtink_cobalt"));

        Zombie target = world.spawn(origin, Zombie.class);
        double healthBefore = target.getHealth();

        server.getPluginManager().callEvent(new EntityDamageByEntityEvent(
                player, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 5.0));

        assertTrue(UltimateEffectEngine.isOnCooldown(player),
                "Casting the ultimate must lock the wielder out for the cooldown");

        server.getScheduler().performTicks(40L);

        assertTrue(target.hasPotionEffect(PotionEffectType.SLOWNESS),
                "The ultimate must root the target in place");
        assertTrue(target.getHealth() <= healthBefore - 6.0,
                "Infernal Meteor Shower must land a heavy impact, health is now " + target.getHealth());
    }

    @Test
    @DisplayName("A diluted essence focus cannot cast an ultimate")
    void dilutedFocusIsGated() {
        Player player = server.addPlayer("smith2");
        Location origin = world.getSpawnLocation();
        player.teleport(origin);

        // Exotic head, common handle: only 50% of the weapon carries the identity essence.
        ItemStack sword = TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(registry.get("mvtink_cobalt"))),
                PartComposition.fromMaterials(List.of(registry.get("mvtink_iron"))),
                PartComposition.fromMaterials(List.of(registry.get("mvtink_iron"))),
                EvolutionTier.WOOD, 0);
        player.getInventory().setItemInMainHand(sword);

        Zombie target = world.spawn(origin, Zombie.class);

        server.getPluginManager().callEvent(new EntityDamageByEntityEvent(
                player, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 5.0));

        assertFalse(UltimateEffectEngine.isOnCooldown(player),
                "A weapon that is only half forged from the essence must not cast an ultimate");
    }

    @Test
    @DisplayName("The ultimate cannot fire twice before its cooldown elapses")
    void ultimateRespectsCooldown() {
        Player player = server.addPlayer("smith3");
        Zombie target = world.spawn(world.getSpawnLocation(), Zombie.class);
        WeaponPerkProfile profile = pureProfile("mvtink_cobalt");

        assertTrue(UltimateEffectEngine.tryTrigger(plugin, player, target, profile, 5.0));
        assertFalse(UltimateEffectEngine.tryTrigger(plugin, player, target, profile, 5.0),
                "A second cast during the cooldown window must be refused");

        server.getScheduler().performTicks(UltimateEffectEngine.COOLDOWN_TICKS + 10L);

        assertFalse(UltimateEffectEngine.isOnCooldown(player), "The cooldown must expire on its own");
        assertTrue(UltimateEffectEngine.tryTrigger(plugin, player, target, profile, 5.0),
                "Once the cooldown expires the ultimate must be castable again");
    }

    // ==========================================
    // HELPERS
    // ==========================================
    private ItemStack pureEssenceSword(String materialId) {
        return TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(registry.get(materialId))),
                PartComposition.fromMaterials(List.of(registry.get(materialId))),
                PartComposition.fromMaterials(List.of(registry.get(materialId))),
                EvolutionTier.WOOD, 0);
    }

    private WeaponPerkProfile pureProfile(String materialId) {
        TinkerMaterial material = registry.get(materialId);
        PartComposition part = PartComposition.fromMaterials(List.of(material));
        return WeaponPerkProfile.of(ModularWeaponType.SWORD, part, part, part);
    }
}
