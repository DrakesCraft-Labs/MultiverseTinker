package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A perk name must identify the whole build, not just the essence of one part.
 *
 * <p>Before this, the name came from the head mineral's essence alone, so a Borax / Amethyst / Gold
 * sword and a Borax / Amethyst / Diamond sword both printed {@code Primal Sweeping Cleave} — Amethyst
 * and Diamond teach the same essences, so swapping the trim changed nothing the player could see. Every
 * part now contributes its own {@link PerkEpithet}, and no two minerals share one.</p>
 */
@DisplayName("Every mineral names the perk of the equipment it is forged into")
class PerksCoverEveryMaterialTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private MultiverseTinker plugin;
    private MaterialRegistry registry;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        PerkEpithet.clearCache();
    }

    @AfterEach
    void tearDown() {
        PerkEpithet.clearCache();
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Every registered mineral owns its own single-word epithet")
    void everyMineralOwnsADistinctEpithet() {
        Map<String, String> ownerByEpithet = new LinkedHashMap<>();
        List<String> missing = new ArrayList<>();
        int checked = 0;

        for (TinkerMaterial material : registry.getAll()) {
            // Runtime-forged composites and primes borrow their parents' words instead of owning one.
            if (isRuntimeForged(material.getId())) continue;
            checked++;

            String epithet = PerkEpithet.of(material);
            assertFalse(epithet.isBlank(), material.getId() + " must name the perk it forges");
            assertTrue(PerkEpithet.curated().containsKey(material.getId()),
                    material.getId() + " has no curated epithet; it fell back to \"" + epithet + "\"");
            assertFalse(epithet.contains(" "),
                    material.getId() + " must be named with a single word, got \"" + epithet + "\"");

            String previous = ownerByEpithet.put(epithet, material.getId());
            if (previous != null) {
                missing.add(epithet + " is shared by " + previous + " and " + material.getId());
            }
        }

        assertEquals(List.of(), missing, "Two minerals sharing a word could give two builds the same perk name");
        assertEquals(PerkEpithet.curatedCount(), checked,
                "Every registered mineral needs a curated epithet, and no entry may be unused");
        assertTrue(checked >= 139, "Expected the full mineral registry, checked only " + checked);
    }

    @Test
    @DisplayName("Composites and primes borrow the words of the minerals they were blended from")
    void forgedAlloysBorrowTheirParentsEpithets() {
        TinkerMaterial tin = registry.get("mvtink_tin");
        TinkerMaterial zinc = registry.get("mvtink_zinc");
        assertNotNull(tin);
        assertNotNull(zinc);

        assertEquals("Malleable", PerkEpithet.of(tin));
        assertEquals("Galvanized", PerkEpithet.of(zinc));

        String compositeId = plugin.getAlloyRegistry().findOrCreateAlloy(tin, zinc, registry).id();
        assertEquals("Malleable-Galvanized", PerkEpithet.of(registry.get(compositeId)),
                "A composite must read as the two minerals it was blended from");

        TinkerMaterial manyullyn = registry.get("mvtink_manyullyn");
        TinkerMaterial netherStar = registry.get("mvtink_catalyst_nether_star");
        assertNotNull(manyullyn);
        assertNotNull(netherStar);

        String primeId = plugin.getAlloyRegistry().findOrCreateAlloy(manyullyn, netherStar, registry).id();
        assertTrue(primeId.startsWith(AlloyRegistry.PRIME_PREFIX), "Expected a prime id, got " + primeId);
        assertTrue(PerkEpithet.of(registry.get(primeId)).startsWith("Prime "),
                "A prime must advertise itself as one, got " + PerkEpithet.of(registry.get(primeId)));
    }

    @Test
    @DisplayName("A mineral without a curated word still gets a readable one")
    void unknownMineralsStillGetAName() {
        TinkerMaterial unmapped = TinkerMaterial.builder()
                .id("mvtink_unobtainium")
                .name("Unobtainium Ore")
                .origin(MineralOrigin.OVERWORLD)
                .rarity(MaterialRarity.COMMON)
                .type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_IRON)
                .processedVanillaMaterial(Material.IRON_INGOT)
                .nuggetVanillaMaterial(Material.IRON_NUGGET)
                .blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#ffffff")
                .description("A mineral the registry has never seen.")
                .traitName("Mystic Resonance")
                .traitDescription("Hums with an unknown frequency.")
                .build();

        assertEquals("Mystic", PerkEpithet.of(unmapped),
                "An unmapped mineral must fall back to its trait instead of leaving the name empty");
        assertEquals("", PerkEpithet.of(null), "No material means no epithet");
    }

    @Test
    @DisplayName("The same weapon type reads differently when only the trim mineral changes")
    void theSameWeaponReadsDifferentlyPerCombination() {
        TinkerMaterial borax = registry.get("mvtink_borax");
        TinkerMaterial amethyst = registry.get("mvtink_amethyst");
        TinkerMaterial gold = registry.get("mvtink_gold");
        TinkerMaterial diamond = registry.get("mvtink_diamond");

        ItemStack goldTrimmed = sword(borax, amethyst, gold);
        ItemStack diamondTrimmed = sword(borax, amethyst, diamond);

        assertEquals("Fluxforged Resonant Auric Sweeping Cleave", perkName(goldTrimmed));
        assertEquals("Fluxforged Resonant Adamant Sweeping Cleave", perkName(diamondTrimmed));
        assertNotEquals(perkName(goldTrimmed), perkName(diamondTrimmed),
                "Two swords of the same type forged from different minerals must not share a perk name");
    }

    @Test
    @DisplayName("Every weapon, tool and armor type names each of its forged minerals")
    void everyEquipmentTypeNamesEachPartMineral() {
        TinkerMaterial borax = registry.get("mvtink_borax");
        TinkerMaterial amethyst = registry.get("mvtink_amethyst");
        TinkerMaterial gold = registry.get("mvtink_gold");

        PartComposition head = PartComposition.fromMaterials(List.of(borax));
        PartComposition rod = PartComposition.fromMaterials(List.of(amethyst));
        PartComposition binding = PartComposition.fromMaterials(List.of(gold));

        for (ModularWeaponType type : ModularWeaponType.values()) {
            PartComposition third = type.isTwoPart() ? null : binding;
            ItemStack weapon = TinkerItemBuilder.createModularWeapon(type, head, rod, third, EvolutionTier.WOOD, 0);
            String expected = type.isTwoPart() ? "Fluxforged Resonant " : "Fluxforged Resonant Auric ";
            assertTrue(perkName(weapon).startsWith(expected),
                    type + " must name every mineral it was forged from, got " + perkName(weapon));
        }
        for (ModularToolType type : ModularToolType.values()) {
            ItemStack tool = TinkerItemBuilder.createModularTool(type, head, rod, binding, EvolutionTier.WOOD, 0);
            assertTrue(perkName(tool).startsWith("Fluxforged Resonant Auric "),
                    type + " must name every mineral it was forged from, got " + perkName(tool));
        }
        for (ModularArmorType type : ModularArmorType.values()) {
            ItemStack armor = TinkerItemBuilder.createModularArmor(type, head, rod, binding, EvolutionTier.WOOD, 0);
            assertTrue(perkName(armor).startsWith("Fluxforged Resonant Auric "),
                    type + " must name every mineral it was forged from, got " + perkName(armor));
        }
    }

    /** A sword forged from the three given minerals, head to pommel. */
    private ItemStack sword(TinkerMaterial head, TinkerMaterial rod, TinkerMaterial pommel) {
        return TinkerItemBuilder.createModularWeapon(ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(head)),
                PartComposition.fromMaterials(List.of(rod)),
                PartComposition.fromMaterials(List.of(pommel)),
                EvolutionTier.WOOD, 0);
    }

    /** The perk name an item prints, e.g. {@code "Fluxforged Resonant Auric Sweeping Cleave"}. */
    private String perkName(ItemStack item) {
        List<String> rows = new ArrayList<>();
        for (Component line : item.getItemMeta().lore()) {
            rows.add(PLAIN.serialize(line).trim());
        }
        String lore = String.join(" ", rows);
        int marker = lore.indexOf("Perk: ");
        assertTrue(marker >= 0, "The item must print a perk line, got:\n" + lore);

        String perk = lore.substring(marker + "Perk: ".length());
        int focus = perk.indexOf("Essence Focus");
        if (focus >= 0) perk = perk.substring(0, focus);
        int separator = perk.indexOf(':');
        return (separator > 0 ? perk.substring(0, separator) : perk).trim();
    }

    /** Runtime-forged alloys are named after their parents rather than by a curated word. */
    private boolean isRuntimeForged(String id) {
        return id.contains("_alloy_") || id.contains("_prime_");
    }
}
