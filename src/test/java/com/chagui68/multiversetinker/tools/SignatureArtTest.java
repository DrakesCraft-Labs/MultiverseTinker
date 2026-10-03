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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the signature arts: every alloy awakens its own named ability, the pedigree ladder ranks
 * builds by how hard their metallurgy was to reach, and every art actually plays and lands its blow.
 */
class SignatureArtTest {

    private ServerMock server;
    private MultiverseTinker plugin;
    private MaterialRegistry registry;
    private AlloyRegistry alloys;
    private WorldMock world;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        alloys = plugin.getAlloyRegistry();
        world = server.addSimpleWorld("arts");
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Each of the 16 legendary alloys owns its own, distinct legendary art")
    void everyLegendaryHasItsOwnArt() {
        Set<String> names = new HashSet<>();
        Set<String> colours = new HashSet<>();
        for (String id : AlloyRegistry.LEGENDARY_IDS) {
            LegendaryArt art = LegendaryArt.byAlloyId(id);
            assertNotNull(art, id + " must own a legendary art");
            assertTrue(names.add(art.getDisplayName()), "Two legendaries share the art " + art.getDisplayName());
            assertTrue(colours.add(art.getColorHex() + art.getAccentHex()), art + " repeats another art's colours");
            assertFalse(art.getDescription().isBlank());

            SignatureArt resolved = SignatureArt.of(registry.get(id));
            assertNotNull(resolved, id + " must resolve to its art");
            assertEquals(ForgeTier.LEGENDARY, resolved.tier());
            assertEquals(art, resolved.legendary());
        }
        assertEquals(AlloyRegistry.LEGENDARY_IDS.size(), LegendaryArt.values().length);
    }

    @Test
    @DisplayName("The pedigree ladder ranks minerals, composites, legendaries, primes and mythics")
    void pedigreeLadder() {
        TinkerMaterial tin = registry.get("mvtink_tin");
        TinkerMaterial bronze = registry.get("mvtink_bronze");

        assertEquals(ForgeTier.MINERAL, ForgeTier.of(tin));
        assertNull(SignatureArt.of(tin), "A plain mineral awakens no art");
        assertEquals(ForgeTier.COMPOSITE, ForgeTier.of(fuse("mvtink_zinc", "mvtink_tin")));
        assertEquals(ForgeTier.LEGENDARY, ForgeTier.of(bronze));
        assertEquals(ForgeTier.PRIME, ForgeTier.of(fuse("mvtink_bronze", "mvtink_ruby")));
        assertEquals(ForgeTier.MYTHIC, ForgeTier.of(fuse("mvtink_bronze", VanillaCatalyst.NETHER_STAR.getMaterialId())),
                "A catalyst prime is mythic");
        assertEquals(ForgeTier.MYTHIC, ForgeTier.of(fuse("mvtink_bronze", "mvtink_electrum")),
                "A double-legendary prime is mythic");
    }

    @Test
    @DisplayName("A composite's fusion art is built from each of its two minerals")
    void compositeFusionArt() {
        TinkerMaterial ruby = registry.get("mvtink_ruby");
        TinkerMaterial malachite = registry.get("mvtink_malachite");
        SignatureArt art = SignatureArt.of(fuse("mvtink_ruby", "mvtink_malachite"));
        assertNotNull(art);
        assertEquals(ForgeTier.COMPOSITE, art.tier());
        assertNull(art.legendary());

        // Canonical order: malachite before ruby. Both epithets name it, the second mineral shapes it.
        assertEquals(PerkEpithet.of(malachite) + "-" + PerkEpithet.of(ruby) + " " + SignatureArt.shapeWord(art.shape()),
                art.name());
        assertEquals(SignatureArt.character(malachite), art.payload(), "The first mineral's character is the payload");
        assertEquals(SignatureArt.specialty(ruby), art.shape(), "The second mineral's specialty is the shape");
        assertEquals(malachite.getColorHex(), art.colorHex());
        assertEquals(ruby.getColorHex(), art.accentHex());
        assertEquals(List.of("mvtink_malachite", "mvtink_ruby"), art.echoes(), "Both minerals' traits are echoed");
        assertTrue(art.description().contains(ruby.getTraitName()) && art.description().contains(malachite.getTraitName()),
                art.description());
    }

    @Test
    @DisplayName("Every one of the ~6,000 composite pairs owns its own fusion art name")
    void everyCompositeIsUnique() {
        List<TinkerMaterial> all = new java.util.ArrayList<>(registry.getAll());
        Set<String> names = new HashSet<>();
        int pairs = 0;
        for (int i = 0; i < all.size(); i++) {
            for (int j = i + 1; j < all.size(); j++) {
                TinkerMaterial a = all.get(i);
                TinkerMaterial b = all.get(j);
                if (!AlloyRegistry.isMixable(a) || !AlloyRegistry.isMixable(b)) continue;
                SignatureArt art = SignatureArt.forPair(a, b);
                if (art == null || art.tier() != ForgeTier.COMPOSITE) continue;
                pairs++;
                assertTrue(names.add(art.name()), "Two composites share the art " + art.name());
            }
        }
        assertTrue(pairs > 5_000, "Every blendable pair must be covered, saw " + pairs);
    }

    @Test
    @DisplayName("Every prime and mythic fusion owns its own art name")
    void everyPrimeIsUnique() {
        Set<String> names = new HashSet<>();
        for (String legendaryId : AlloyRegistry.LEGENDARY_IDS) {
            TinkerMaterial legendary = registry.get(legendaryId);
            for (TinkerMaterial partner : registry.getAll()) {
                if (!AlloyRegistry.isPrimePair(legendary, partner)) continue;
                // A double-legendary pair is met from both sides: count it once.
                if (AlloyRegistry.isLegendary(partner) && partner.getId().compareTo(legendaryId) < 0) continue;
                SignatureArt art = SignatureArt.forPair(legendary, partner);
                assertNotNull(art);
                assertTrue(names.add(art.name()), "Two primes share the art " + art.name());
            }
        }
    }

    @Test
    @DisplayName("A fusion art echoes its minerals' own traits on the foes it reaches")
    void fusionEchoesMineralTraits() {
        PlayerMock player = server.addPlayer("echo");
        Location origin = world.getSpawnLocation();
        player.teleport(origin);
        Zombie target = world.spawn(origin.clone().add(2, 0, 0), Zombie.class);
        target.setFireTicks(0);

        SignatureArt art = SignatureArt.of(fuse("mvtink_ruby", "mvtink_malachite"));
        SignatureArtEngine.play(plugin, player, target, art, 1.0);
        server.getScheduler().performTicks(20L);

        assertTrue(target.hasPotionEffect(org.bukkit.potion.PotionEffectType.POISON),
                "Malachite's Toxic Patina must poison the foe");
        assertTrue(target.getFireTicks() > 0, "Ruby's Flame Edge must set the foe alight");
    }

    @Test
    @DisplayName("Primes of the same legendary never share a name, and their trait carries the art's name")
    void primesAreDistinct() {
        TinkerMaterial withStar = fuse("mvtink_bronze", VanillaCatalyst.NETHER_STAR.getMaterialId());
        TinkerMaterial withIce = fuse("mvtink_bronze", VanillaCatalyst.BLUE_ICE.getMaterialId());
        TinkerMaterial withElectrum = fuse("mvtink_bronze", "mvtink_electrum");

        Set<String> names = new HashSet<>();
        for (TinkerMaterial prime : List.of(withStar, withIce, withElectrum)) {
            SignatureArt art = SignatureArt.of(prime);
            assertNotNull(art, prime.getId() + " must awaken an art");
            assertTrue(names.add(art.name()), "Two primes share the art " + art.name());
            assertEquals(art.name(), prime.getTraitName(), "A prime's trait must be its art's name");
            assertFalse(prime.getTraitName().startsWith("Prime "), "Primes no longer share a generic trait");
        }

        SignatureArt star = SignatureArt.of(withStar);
        assertEquals(VanillaCatalyst.NETHER_STAR, star.catalyst());
        assertTrue(star.name().contains(VanillaCatalyst.NETHER_STAR.getUltimate().getDisplayName()));

        SignatureArt duo = SignatureArt.of(withElectrum);
        assertNotNull(duo.legendary());
        assertNotNull(duo.secondary(), "A double-legendary prime plays both legendary arts");
        assertNotEquals(duo.legendary(), duo.secondary());
    }

    @Test
    @DisplayName("The crucible preview names the same art the forged alloy awakens")
    void pairPreviewMatchesForgedArt() {
        for (String[] pair : new String[][]{
                {"mvtink_cobalt", "mvtink_tin"},
                {"mvtink_copper", "mvtink_tin"},
                {"mvtink_glacial_silver", VanillaCatalyst.ECHO_SHARD.getMaterialId()},
                {"mvtink_manyullyn", "mvtink_ruby"}}) {
            TinkerMaterial a = registry.get(pair[0]);
            TinkerMaterial b = registry.get(pair[1]);
            SignatureArt preview = SignatureArt.forPair(a, b);
            SignatureArt forged = SignatureArt.of(fuse(pair[0], pair[1]));
            assertNotNull(preview);
            assertNotNull(forged);
            assertEquals(forged.name(), preview.name(), "Preview and forge disagree on " + pair[0] + " + " + pair[1]);
            assertEquals(forged.tier(), preview.tier());
        }
    }

    @Test
    @DisplayName("A weapon's art is the most demanding alloy in it, and its lore says so")
    void weaponLoreNamesTheArt() {
        TinkerMaterial prime = fuse("mvtink_cosmic_netherite", "mvtink_ruby");
        List<PartComposition> parts = List.of(part("mvtink_tin"), part(prime.getId()), part("mvtink_bronze"));
        SignatureArt art = SignatureArt.forWeapon(parts);
        assertNotNull(art);
        assertEquals(ForgeTier.PRIME, art.tier(), "The prime outranks the legendary pommel");
        assertEquals(LegendaryArt.GRAVITY_COLLAPSE, art.legendary());

        ItemStack sword = TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD,
                parts.get(0), parts.get(1), parts.get(2), EvolutionTier.WOOD, 0);
        String lore = plainLore(sword);
        assertTrue(lore.contains("Signature Art: " + art.name()), lore);
        assertTrue(lore.contains("Tier IV · Prime"), lore);

        ItemStack plain = TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD,
                part("mvtink_tin"), part("mvtink_iron"), part("mvtink_tin"), EvolutionTier.WOOD, 0);
        String plainLore = plainLore(plain);
        assertTrue(plainLore.contains("Tier I · Forged"), plainLore);
        assertFalse(plainLore.contains("Signature Art:"), "A mineral weapon has no art to print");
    }

    @Test
    @DisplayName("Arts honour their chance and their cooldown")
    void artsAreGated() {
        PlayerMock player = server.addPlayer("gated");
        Zombie target = world.spawn(world.getSpawnLocation(), Zombie.class);
        SignatureArt art = SignatureArt.of(registry.get("mvtink_electrum"));
        assertNotNull(art);

        assertFalse(SignatureArtEngine.tryTrigger(plugin, player, target, art, 5.0, 0.99),
                "A roll above the chance must not fire");
        assertTrue(SignatureArtEngine.tryTrigger(plugin, player, target, art, 5.0, 0.0));
        assertTrue(SignatureArtEngine.isOnCooldown(player));
        assertFalse(SignatureArtEngine.tryTrigger(plugin, player, target, art, 5.0, 0.0),
                "A second art inside the cooldown must be refused");

        server.getScheduler().performTicks(SignatureArtEngine.cooldownTicks(art.tier()) + 5L);
        assertFalse(SignatureArtEngine.isOnCooldown(player), "The cooldown must expire on its own");
    }

    @Test
    @DisplayName("Every legendary art and every mythic variant plays to the end and hurts its target")
    void everyArtPlaysAndLands() {
        List<SignatureArt> arts = new java.util.ArrayList<>();
        for (LegendaryArt legendary : LegendaryArt.values()) {
            arts.add(SignatureArt.of(registry.get(legendary.getAlloyId())));
        }
        arts.add(SignatureArt.of(fuse("mvtink_zinc", "mvtink_tin")));
        arts.add(SignatureArt.of(fuse("mvtink_invar", "mvtink_ruby")));
        arts.add(SignatureArt.of(fuse("mvtink_invar", VanillaCatalyst.DRAGON_BREATH.getMaterialId())));
        arts.add(SignatureArt.of(fuse("mvtink_invar", "mvtink_sanguine_gold")));

        int index = 0;
        for (SignatureArt art : arts) {
            assertNotNull(art);
            PlayerMock player = server.addPlayer("artist" + index);
            Location origin = world.getSpawnLocation().clone().add(index * 40, 0, 0);
            player.teleport(origin);
            Zombie target = world.spawn(origin.clone().add(2, 0, 0), Zombie.class);
            double before = target.getHealth();

            SignatureArtEngine.play(plugin, player, target, art, 4.0);
            server.getScheduler().performTicks(160L);

            assertTrue(target.isDead() || target.getHealth() < before,
                    art.name() + " must land its blow, health stayed at " + target.getHealth());
            index++;
        }
    }

    // ==========================================
    // HELPERS
    // ==========================================

    private TinkerMaterial fuse(String first, String second) {
        TinkerMaterial a = registry.get(first);
        TinkerMaterial b = registry.get(second);
        assertNotNull(a, first);
        assertNotNull(b, second);
        assertTrue(alloys.isCraftablePair(a, b), first + " + " + second + " must be craftable");
        TinkerAlloy alloy = alloys.findOrCreateAlloy(a, b, registry);
        TinkerMaterial material = registry.get(alloy.id());
        assertNotNull(material);
        return material;
    }

    private PartComposition part(String id) {
        TinkerMaterial material = registry.get(id);
        assertNotNull(material, id);
        return PartComposition.fromMaterials(List.of(material));
    }

    private static String plainLore(ItemStack item) {
        List<Component> lore = item.getItemMeta().lore();
        assertNotNull(lore);
        return lore.stream().map(PlainTextComponentSerializer.plainText()::serialize)
                .collect(Collectors.joining(" ")).replaceAll("\\s+", " ");
    }
}
