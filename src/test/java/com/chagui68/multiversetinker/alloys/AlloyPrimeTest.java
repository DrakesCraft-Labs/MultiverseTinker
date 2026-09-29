package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.CinematicUltimate;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the prime alloy category: 16 legendary alloys fused with another alloy, a mineral or one of
 * the 12 vanilla catalysts.
 */
class AlloyPrimeTest {

    private MaterialRegistry materials;
    private AlloyRegistry alloys;

    @BeforeEach
    void setUp() {
        materials = new MaterialRegistry();
        alloys = new AlloyRegistry();
        alloys.registerCatalystMaterials(materials);
        alloys.registerAlloysIntoMaterialRegistry(materials);
    }

    @Test
    @DisplayName("Prime fusions require exactly one legendary parent and never chain into a third tier")
    void testPrimePairRules() {
        TinkerMaterial manyullyn = materials.get("mvtink_manyullyn");
        TinkerMaterial bronze = materials.get("mvtink_bronze");
        TinkerMaterial tin = materials.get("mvtink_tin");
        TinkerMaterial netherStar = materials.get(VanillaCatalyst.NETHER_STAR.getMaterialId());
        TinkerMaterial echoShard = materials.get(VanillaCatalyst.ECHO_SHARD.getMaterialId());

        assertTrue(AlloyRegistry.isLegendary(manyullyn), "Manyullyn is a legendary recipe");
        assertTrue(AlloyRegistry.isCatalyst(netherStar), "The Nether Star must register as a catalyst");

        assertTrue(AlloyRegistry.isPrimePair(manyullyn, bronze), "Legendary x legendary fuses");
        assertTrue(AlloyRegistry.isPrimePair(manyullyn, tin), "Legendary x mineral fuses");
        assertTrue(AlloyRegistry.isPrimePair(manyullyn, netherStar), "Legendary x catalyst fuses");
        assertTrue(AlloyRegistry.isPrimePair(netherStar, manyullyn), "Order must not matter");

        assertFalse(AlloyRegistry.isPrimePair(tin, netherStar), "A catalyst alone is not enough");
        assertFalse(AlloyRegistry.isPrimePair(netherStar, echoShard), "Two catalysts cannot fuse");
        assertFalse(AlloyRegistry.isPrimePair(manyullyn, manyullyn), "A prime needs two distinct inputs");

        TinkerMaterial prime = materials.get(
                alloys.findOrCreateAlloy(manyullyn, netherStar, materials).id());
        assertNotNull(prime);
        assertTrue(AlloyRegistry.isPrime(prime), "A legendary fusion must register as a prime");
        assertFalse(AlloyRegistry.isPrimePair(prime, tin), "Prime alloys cannot seed another prime");
        assertFalse(AlloyRegistry.isPrimePair(prime, manyullyn), "Prime alloys cannot seed another prime");
    }

    @Test
    @DisplayName("A prime alloy keeps legendary rarity, boosted stats and both parents' essences")
    void testPrimeAlloyProperties() {
        TinkerMaterial manyullyn = materials.get("mvtink_manyullyn");
        TinkerMaterial echoShard = materials.get(VanillaCatalyst.ECHO_SHARD.getMaterialId());

        TinkerAlloy prime = alloys.findOrCreateAlloy(manyullyn, echoShard, materials);
        assertTrue(prime.id().startsWith(AlloyRegistry.PRIME_PREFIX),
                "Prime ids must use the prime prefix, was " + prime.id());
        assertTrue(prime.name().endsWith(" Prime"), "Prime names must be explicit, was " + prime.name());

        TinkerMaterial primeMaterial = materials.get(prime.id());
        assertNotNull(primeMaterial);
        assertEquals(MaterialRarity.LEGENDARY, primeMaterial.getRarity(), "Primes are legendary metallurgy");
        assertTrue(AlloyRegistry.isPrime(primeMaterial));
        assertEquals(Set.of(manyullyn.getId(), echoShard.getId()),
                Set.of(primeMaterial.getAlloyParents().split(",")),
                "Both parents must be recorded (in canonical id order)");

        // Stats must beat the plain composite formula on the same pair.
        int compositeDurability = (int) Math.round((manyullyn.getDurability() + echoShard.getDurability()) * 0.70) + 60;
        assertTrue(primeMaterial.getDurability() > compositeDurability,
                "A prime must outscale a composite of the same parents");
        assertTrue(primeMaterial.getMiningSpeed() > manyullyn.getMiningSpeed() * 0.5f,
                "A catalyst must not drag the prime's mining speed down");

        Set<TraitAffinity> essence = TraitAffinity.of(primeMaterial);
        assertTrue(essence.contains(TraitAffinity.INFERNAL),
                "The Manyullyn parent must keep teaching Infernal");
        assertTrue(essence.contains(TraitAffinity.PRIMAL),
                "The catalyst parent must teach Primal");
    }

    @Test
    @DisplayName("Catalyst sigils decide the prime ultimate and the prime armor state")
    void testCatalystSigils() {
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            TinkerMaterial legendary = materials.get("mvtink_cosmic_netherite");
            TinkerMaterial catalystMaterial = materials.get(catalyst.getMaterialId());
            TinkerMaterial prime = materials.get(
                    alloys.findOrCreateAlloy(legendary, catalystMaterial, materials).id());

            assertNotNull(prime, catalyst + " must produce a prime material");
            assertEquals(catalyst.getUltimate(), PrimeUltimate.of(prime),
                    catalyst + " must grant its own ultimate");
            assertEquals(catalyst.getArmorState(), PrimeArmorState.of(prime),
                    catalyst + " must grant its own armor state");
        }
    }

    @Test
    @DisplayName("A prime without a catalyst falls back to the essence-matching spectacle")
    void testPrimeWithoutCatalystUsesEssence() {
        TinkerMaterial manyullyn = materials.get("mvtink_manyullyn");
        TinkerMaterial voidDamascus = materials.get("mvtink_void_damascus");
        TinkerMaterial prime = materials.get(
                alloys.findOrCreateAlloy(manyullyn, voidDamascus, materials).id());

        assertNotNull(prime);
        TraitAffinity dominant = TraitAffinity.of(prime).iterator().next();
        assertEquals(PrimeUltimate.fromAffinity(dominant), PrimeUltimate.of(prime),
                "Without a catalyst the prime ultimate follows the dominant essence");
        assertEquals(PrimeArmorState.fromAffinity(dominant), PrimeArmorState.of(prime),
                "Without a catalyst the armor state follows the dominant essence");
    }

    @Test
    @DisplayName("PrimeUltimate.forWeapon only fires for weapons that contain a prime alloy")
    void testPrimeUltimateDetection() {
        TinkerMaterial prime = materials.get(alloys.findOrCreateAlloy(
                materials.get("mvtink_bronze"),
                materials.get(VanillaCatalyst.BLUE_ICE.getMaterialId()), materials).id());
        assertNotNull(prime);

        PartComposition primePart = PartComposition.fromMaterials(List.of(prime));
        PartComposition plainPart = PartComposition.fromMaterials(List.of(materials.get("mvtink_iron")));

        assertNotNull(PrimeUltimate.forWeapon(List.of(primePart, plainPart), null),
                "A weapon with a prime part must cast a prime ultimate");
        assertNull(PrimeUltimate.forWeapon(List.of(plainPart), null),
                "A weapon without primes must fall back to its essence ultimate");

        // Every prime spectacle is fully specified and the cryo ones actually freeze.
        Set<String> names = new HashSet<>();
        for (PrimeUltimate ultimate : PrimeUltimate.values()) {
            assertTrue(names.add(ultimate.getDisplayName()), "Duplicate prime ultimate name");
            assertNotNull(ultimate.getAnimation());
            assertTrue(ultimate.getDamageMultiplier() >= 1.5, ultimate + " must hit hard");
            assertTrue(ultimate.getRootTicks() > 0, ultimate + " must root the target");
            assertFalse(ultimate.getDescription().isBlank());
            if (ultimate.isCryo()) {
                assertTrue(ultimate.getFreezeTicks() >= 140, ultimate + " must actually freeze its victim");
            }
        }
        assertTrue(names.size() >= 7, "The prime category ships several distinct spectacles");

        assertNotEquals(CinematicUltimate.Animation.METEOR, PrimeUltimate.METEOR_CASCADE.getAnimation(),
                "The cascade uses the new meteor-storm animation");
        assertTrue(PrimeUltimate.ABSOLUTE_ZERO.isCryo() && PrimeUltimate.GLACIER_TOMB.isCryo(),
                "Two prime spectacles freeze the battlefield");
    }

    @Test
    @DisplayName("Every prime armor state is documented and fully specified")
    void testPrimeArmorStates() {
        Set<String> names = new HashSet<>();
        Set<String> descriptions = new HashSet<>();
        for (PrimeArmorState state : PrimeArmorState.values()) {
            assertTrue(names.add(state.getDisplayName()), "Duplicate armor state name");
            assertTrue(descriptions.add(state.getDescription()), "Duplicate armor state description");
            assertNotNull(state.getParticle());
            assertNotNull(state.getSound());
            assertNotNull(state.getColor());
            assertTrue(state.getCooldownTicks() > 0, state + " needs a cooldown");
            assertTrue(state.getLoreLine().contains(state.getDisplayName()));
        }
        assertTrue(names.size() >= 9, "At least 9 new armor states must exist");
    }
}
