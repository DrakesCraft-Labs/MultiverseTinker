package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the perk preview an assembly prints before its parts are spent.
 *
 * <p>The preview is only worth reading if it says what the finished item would say, so every family is
 * checked against a genuinely forged item rather than against the profile it was derived from: the
 * string the preview promises has to appear in the lore of the piece the Forge itself assembled.</p>
 */
class ForgePerkPreviewTest {

    private MaterialRegistry registry;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        MultiverseTinker plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Every weapon the preview names is the perk its forged item prints")
    void weaponPreviewMatchesTheForgedItem() {
        PartComposition blade = composition("mvtink_diamond");
        PartComposition hilt = composition("mvtink_iron");
        PartComposition guard = composition("mvtink_gold");

        for (ModularWeaponType type : ModularWeaponType.values()) {
            PartComposition third = type.isTwoPart() ? null : guard;
            ForgePerkPreview preview = ForgePerkPreview.weapon(type, blade, hilt, third);
            ItemStack weapon = TinkerItemBuilder.createModularWeapon(type, blade, hilt, third, EvolutionTier.WOOD, 0);
            String lore = normalise(plainLore(weapon));

            assertTrue(preview.complete(), type + " must be complete once every part is placed");
            assertTrue(lore.contains(normalise(preview.name())),
                    type + " forged a perk its preview did not name:\n  preview: " + preview.name());
            assertTrue(lore.contains(normalise(preview.description())),
                    type + " forged a mechanic its preview did not describe:\n  preview: " + preview.description());
            assertTrue(lore.contains(normalise(preview.focusLine())),
                    type + " forged a focus its preview did not report");
        }
    }

    @Test
    @DisplayName("Every tool the preview names is the perk its forged item prints")
    void toolPreviewMatchesTheForgedItem() {
        PartComposition head = composition("mvtink_borax");
        PartComposition handle = composition("mvtink_amethyst");
        PartComposition pommel = composition("mvtink_gold");

        for (ModularToolType type : ModularToolType.values()) {
            // The deprecated sword alias is not offered by the Forge, so nothing previews it.
            if (type == ModularToolType.SWORD) continue;

            ForgePerkPreview preview = ForgePerkPreview.tool(type, head, handle, pommel);
            ItemStack tool = TinkerItemBuilder.createModularTool(type, head, handle, pommel, EvolutionTier.WOOD, 0);
            String lore = normalise(plainLore(tool));

            assertTrue(preview.complete(), type + " must be complete once every part is placed");
            assertTrue(lore.contains(normalise(preview.name())), type + " forged a perk its preview did not name");
            assertTrue(lore.contains(normalise(preview.description())), type + " forged a mechanic its preview did not describe");
        }
    }

    @Test
    @DisplayName("An armor preview promises the share the forged piece actually answers hits with")
    void armorPreviewQuotesTheMitigationThePieceApplies() {
        PartComposition plate = composition("mvtink_diamond");
        PartComposition lining = composition("mvtink_iron");
        PartComposition trim = composition("mvtink_gold");

        for (ModularArmorType type : ModularArmorType.values()) {
            ForgePerkPreview preview = ForgePerkPreview.armor(type, plate, lining, trim);
            ItemStack piece = TinkerItemBuilder.createModularArmor(type, plate, lining, trim, EvolutionTier.WOOD, 0);

            assertTrue(preview.complete(), type + " must be complete once every part is placed");
            assertTrue(normalise(plainLore(piece)).contains(normalise(preview.description())),
                    type + " forged a perk its preview did not describe");

            // Leggings answer a hit with mobility instead of mitigation, so their sentence carries no share.
            if (type == ModularArmorType.LEGGINGS) continue;
            String share = String.format(Locale.US, "%.0f%%", TinkerItemBuilder.mitigationOf(piece, registry) * 100);
            assertTrue(preview.description().contains(share),
                    type + " promises its mitigation as " + preview.description() + ", but the worn piece answers " + share);
        }
    }

    @Test
    @DisplayName("A half-filled assembly names only the parts already in place")
    void partialBuildNamesThePartsAlreadyPlaced() {
        ForgePerkPreview empty = ForgePerkPreview.weapon(ModularWeaponType.SWORD);

        assertFalse(empty.complete());
        assertEquals(3, empty.required());
        assertEquals("Sweeping Cleave", empty.name(), "With no part placed the preview is the mechanic alone");
        assertEquals(List.of("Head / Blade", "Handle / Hilt", "Pommel / Guard"), empty.missing());
        assertTrue(empty.parts().isEmpty());
        assertNull(empty.description(), "A mechanic sentence quotes minerals that are not there yet");

        ForgePerkPreview head = ForgePerkPreview.weapon(ModularWeaponType.SWORD, composition("mvtink_diamond"));
        assertEquals("Adamant Sweeping Cleave", head.name());
        assertEquals(1, head.placed());
        assertEquals("Head / Blade", head.parts().get(0).position());
        assertEquals("Adamant", head.parts().get(0).epithet(), "The word a mineral lends is curated, not derived");
        assertEquals(registry.get("mvtink_diamond").getName(), head.parts().get(0).material());

        ForgePerkPreview two = ForgePerkPreview.weapon(ModularWeaponType.SWORD,
                composition("mvtink_diamond"), composition("mvtink_iron"));
        assertEquals("Adamant Ironclad Sweeping Cleave", two.name());
        assertEquals(List.of("Pommel / Guard"), two.missing());
        assertFalse(two.complete());

        ForgePerkPreview full = ForgePerkPreview.weapon(ModularWeaponType.SWORD,
                composition("mvtink_diamond"), composition("mvtink_iron"), composition("mvtink_gold"));
        assertEquals("Adamant Ironclad Auric Sweeping Cleave", full.name());
        assertTrue(full.complete());
        assertNotNull(full.description());
    }

    @Test
    @DisplayName("A two-part weapon asks for two parts and names both of them")
    void twoPartWeaponAsksForTwoParts() {
        ForgePerkPreview empty = ForgePerkPreview.weapon(ModularWeaponType.BOW);
        assertEquals(2, empty.required());
        assertEquals(List.of("Bow Limbs", "Bowstring"), empty.missing());

        ForgePerkPreview complete = ForgePerkPreview.weapon(ModularWeaponType.BOW,
                composition("mvtink_amethyst"), composition("mvtink_gold"));
        assertTrue(complete.complete(), "A bow never waits for a third part");
        assertEquals("Resonant Auric Infused Volley", complete.name());
    }

    @Test
    @DisplayName("The documented example build previews the name the class documents")
    void theDocumentedBuildKeepsItsName() {
        ForgePerkPreview preview = ForgePerkPreview.weapon(ModularWeaponType.SWORD,
                composition("mvtink_borax"), composition("mvtink_amethyst"), composition("mvtink_gold"));

        assertEquals("Fluxforged Resonant Auric Sweeping Cleave", preview.name());
    }

    @Test
    @DisplayName("Two builds that differ in one part never preview the same perk")
    void onePartIsEnoughToNameADifferentPerk() {
        PartComposition blade = composition("mvtink_borax");
        PartComposition hilt = composition("mvtink_amethyst");

        ForgePerkPreview gold = ForgePerkPreview.weapon(ModularWeaponType.SWORD, blade, hilt, composition("mvtink_gold"));
        ForgePerkPreview diamond = ForgePerkPreview.weapon(ModularWeaponType.SWORD, blade, hilt, composition("mvtink_diamond"));

        assertNotEquals(gold.name(), diamond.name(),
                "Swapping the pommel must be visible before the parts are spent");
        assertEquals("Fluxforged Resonant Auric Sweeping Cleave", gold.name());
        assertEquals("Fluxforged Resonant Adamant Sweeping Cleave", diamond.name());
    }

    @Test
    @DisplayName("An armor preview reports the three parts it rolls its protection from")
    void armorPreviewNamesItsOwnParts() {
        ForgePerkPreview empty = ForgePerkPreview.armor(ModularArmorType.CHESTPLATE);

        assertEquals(3, empty.required());
        assertEquals(List.of("Armor Plate", "Armor Lining", "Armor Trim"), empty.missing());
        assertEquals("Kinetic Dampener", empty.name());

        ForgePerkPreview tool = ForgePerkPreview.tool(ModularToolType.PICKAXE);
        assertEquals(List.of(ToolPartType.HEAD.getDisplayName(), ToolPartType.ROD.getDisplayName(),
                ToolPartType.BINDING.getDisplayName()), tool.missing());
        assertEquals("Vein Resonance", tool.name());
    }

    // ==========================================
    // HELPERS
    // ==========================================

    private PartComposition composition(String materialId) {
        TinkerMaterial material = registry.get(materialId);
        assertNotNull(material, materialId + " must be a registered material");
        return PartComposition.fromMaterials(List.of(material));
    }

    /** The whole tooltip as one line: wrapped rows are joined, so a phrase is searchable across them. */
    private String plainLore(ItemStack item) {
        List<Component> lore = item.getItemMeta().lore();
        assertNotNull(lore, "A forged item must carry lore");
        return lore.stream()
                .map(PlainTextComponentSerializer.plainText()::serialize)
                .collect(Collectors.joining(" "));
    }

    /**
     * Collapses whitespace: the lore is word-wrapped, and a wrapped row is re-joined with a space while
     * the wrap also indents the rows it creates, so the same sentence can come back with runs of spaces.
     */
    private static String normalise(String text) {
        return text == null ? null : text.replaceAll("\\s+", " ").trim();
    }
}
