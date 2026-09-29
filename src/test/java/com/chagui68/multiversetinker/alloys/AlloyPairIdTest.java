package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers resolving a crucible <b>pair id</b>: the id the codex previews for every pair the crucible
 * would accept, including the pairs nobody has smelted yet.
 *
 * <p>Blending only ever happened inside the crucible, so an id copied out of the codex for a pair that
 * had not been smelted named nothing at all. It now resolves to the alloy that pair really forges,
 * under the same rules the crucible enforces — and it stops at nothing when the pair is one the
 * crucible would refuse.</p>
 */
class AlloyPairIdTest {

    private MaterialRegistry materials;
    private AlloyRegistry alloys;

    @BeforeEach
    void setUp() {
        materials = new MaterialRegistry();
        alloys = new AlloyRegistry();
        alloys.registerAlloysIntoMaterialRegistry(materials);
        alloys.registerCatalystMaterials(materials);
    }

    @Test
    @DisplayName("A pair id nobody has smelted yet is forged and registered under that very id")
    void aPairIdIsForgedOnDemand() {
        String pairId = "mvtink_alloy_tin_zinc";
        assertNull(materials.get(pairId), "The pair must not exist before it is resolved");

        TinkerAlloy alloy = alloys.alloyForPairId(pairId, materials);
        assertNotNull(alloy, pairId + " names a valid pair of freely blendable minerals");
        assertEquals(pairId, alloy.id(), "The alloy must land on the id that was asked for");
        assertEquals("Tin-Zinc Alloy", alloy.name());

        TinkerMaterial forged = materials.get(pairId);
        assertNotNull(forged, "The forged alloy must be a material the rest of the plugin can use");
        assertEquals(MaterialType.ALLOY, forged.getType());
        assertEquals("mvtink_tin,mvtink_zinc", forged.getAlloyParents(),
                "The parents are recorded in the canonical order the id spells them in");
        assertFalse(AlloyRegistry.isPrime(forged), "Two plain minerals make a composite, not a prime");
        assertEquals(1, alloys.getDynamicAlloyCount(), "The new alloy is player-forged, so it must be saved");
    }

    @Test
    @DisplayName("Resolving the same pair twice forges one alloy, not two")
    void resolvingTwiceKeepsOneAlloy() {
        int before = alloys.getAllAlloys().size();

        TinkerAlloy first = alloys.alloyForPairId("mvtink_alloy_tin_zinc", materials);
        TinkerAlloy second = alloys.alloyForPairId("mvtink_alloy_tin_zinc", materials);

        assertNotNull(first);
        assertSame(first, second, "A pair already blended must hand back the alloy it produced");
        assertEquals(before + 1, alloys.getAllAlloys().size());
        assertEquals(1, alloys.getDynamicAlloyCount());
    }

    @Test
    @DisplayName("A pair whose alloy is a curated recipe resolves to that recipe, not to a new alloy")
    void curatedPairsResolveToTheirRecipe() {
        String pairId = "mvtink_alloy_copper_tin";

        TinkerAlloy alloy = alloys.alloyForPairId(pairId, materials);

        assertNotNull(alloy);
        assertEquals("mvtink_bronze", alloy.id(), "Copper and tin forge Bronze, so Bronze is the answer");
        assertEquals("Bronze", alloy.name());
        assertNull(materials.get(pairId), "No second alloy may be invented for a curated pair");
        assertEquals(0, alloys.getDynamicAlloyCount(), "Nothing was forged: the recipe already existed");
    }

    @Test
    @DisplayName("A prime pair id forges the prime it names, catalyst and all")
    void primePairIdsForgePrimes() {
        // Canonical spelling: the parents are sorted by id, and the catalyst sorts first.
        String pairId = "mvtink_prime_catalyst_nether_star_cosmic_netherite";

        TinkerAlloy alloy = alloys.alloyForPairId(pairId, materials);

        assertNotNull(alloy, "A legendary alloy fused with a catalyst is a prime pair");
        assertEquals(pairId, alloy.id());
        assertEquals("Nether Star Cosmic Netherite Prime", alloy.name());
        assertNull(alloys.alloyForPairId("mvtink_prime_cosmic_netherite_catalyst_nether_star", materials),
                "The other spelling of the same pair is not the id the crucible would produce");

        TinkerMaterial forged = materials.get(pairId);
        assertNotNull(forged);
        assertTrue(AlloyRegistry.isPrime(forged));
        assertEquals(MaterialRarity.LEGENDARY, forged.getRarity());
        assertEquals(1, alloys.getPrimeAlloyCount());
    }

    @Test
    @DisplayName("An id that names no pair the crucible would accept resolves to nothing")
    void idsThatNameNoCraftablePairResolveToNothing() {
        assertNull(alloys.alloyForPairId("mvtink_tin", materials), "A material id is not a pair id");
        assertNull(alloys.alloyForPairId("mvtink_bronze", materials), "Neither is a finished alloy");
        assertNull(alloys.alloyForPairId("mvtink_alloy_tin", materials), "One parent is not a pair");
        assertNull(alloys.alloyForPairId("mvtink_alloy_tin_tin", materials),
                "A mineral cannot be blended with itself");
        assertNull(alloys.alloyForPairId("mvtink_alloy_tin_unobtainium", materials), "An unknown parent");
        assertNull(alloys.alloyForPairId("mvtink_alloy_zinc_tin", materials),
                "Only the canonical spelling the crucible would produce is a valid id");
        assertNull(alloys.alloyForPairId("mvtink_prime_tin_zinc", materials),
                "The prime prefix does not make a pair prime: neither parent is legendary");
        assertNull(alloys.alloyForPairId(VanillaCatalyst.NETHER_STAR.getMaterialId(), materials),
                "The catalyst's own id is not a pair id");

        // The crucible's own rules still decide: a catalyst joins a prime fusion, never a plain
        // composite, and a finished alloy only enters through a prime.
        assertNull(alloys.alloyForPairId("mvtink_alloy_catalyst_nether_star_tin", materials),
                "A catalyst is not a freely blendable mineral");
        assertNull(alloys.alloyForPairId("mvtink_alloy_bronze_tin", materials),
                "A finished alloy cannot be blended like a mineral");

        assertEquals(0, alloys.getDynamicAlloyCount(), "A refused id must never register anything");
    }

    @Test
    @DisplayName("A prime can never seed another prime, so the id for one resolves to nothing")
    void aPrimeCannotSeedAnotherPrime() {
        TinkerAlloy prime = alloys.findOrCreateAlloy(
                materials.get("mvtink_cosmic_netherite"), materials.get("mvtink_tin"), materials);
        assertTrue(AlloyRegistry.isPrime(materials.get(prime.id())));

        // However the id for "that prime + tin" is spelled, the crucible would refuse it, and so does
        // resolving the id: the prime ladder stops one alloy deep.
        String pairId = AlloyRegistry.dynamicId(materials.get(prime.id()), materials.get("mvtink_tin"));
        assertNull(alloys.alloyForPairId(pairId, materials));

        int forged = alloys.getDynamicAlloyCount();
        assertNull(alloys.alloyForPairId(pairId, materials), "A second try must not smuggle one in either");
        assertEquals(forged, alloys.getDynamicAlloyCount(), "Refusing must not register anything");
    }

    @Test
    @DisplayName("An id that could split two ways resolves as the longest first parent, on every server")
    void ambiguousIdsResolveDeterministically() {
        // Lapis and Lapis Matrix are both minerals, and "matrix_zinc" makes the id ambiguous:
        // lapis_matrix + zinc and lapis + matrix_zinc spell exactly the same pair id, and both are
        // canonical and both craftable. The longest first parent is what keeps the answer the same
        // whatever order a server happened to register its materials in.
        registerMineral("mvtink_matrix_zinc", "Matrix Zinc");
        assertTrue(AlloyRegistry.isMixable(materials.get("mvtink_lapis")));
        assertTrue(AlloyRegistry.isMixable(materials.get("mvtink_lapis_matrix")));

        TinkerAlloy alloy = alloys.alloyForPairId("mvtink_alloy_lapis_matrix_zinc", materials);

        assertNotNull(alloy);
        assertEquals("mvtink_alloy_lapis_matrix_zinc", alloy.id());
        assertEquals("mvtink_lapis_matrix", alloy.mat1Id(), "The longest first parent must win");
        assertEquals("mvtink_zinc", alloy.mat2Id());
    }

    /** Registers a freely blendable Overworld mineral, for the cases the shipped catalog cannot spell. */
    private void registerMineral(String id, String name) {
        materials.register(TinkerMaterial.builder()
                .id(id)
                .name(name)
                .origin(MineralOrigin.OVERWORLD)
                .rarity(MaterialRarity.COMMON)
                .type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_IRON)
                .processedVanillaMaterial(Material.IRON_INGOT)
                .nuggetVanillaMaterial(Material.IRON_NUGGET)
                .blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#c0c0c0")
                .description("A mineral registered by the pair-id tests.")
                .traitName("Test Resonance")
                .traitDescription("Exists only so an id can be ambiguous.")
                .build());
    }
}
