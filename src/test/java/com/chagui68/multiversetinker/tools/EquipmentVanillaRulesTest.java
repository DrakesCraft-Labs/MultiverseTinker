package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Modular equipment tracks its own durability and rolls its own attack damage, so Minecraft must not
 * add vanilla wear on every swing nor decide the damage from the base material alone.
 */
@DisplayName("Modular equipment is immune to vanilla wear and fights with its own damage")
class EquipmentVanillaRulesTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
    private static final String DURABILITY_LABEL = "  • Durability: ";

    private MaterialRegistry registry;
    private TinkerMaterial diamond;
    private TinkerMaterial iron;
    private TinkerMaterial gold;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        MultiverseTinker plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        diamond = registry.get("mvtink_diamond");
        iron = registry.get("mvtink_iron");
        gold = registry.get("mvtink_gold");

        assertNotNull(diamond);
        assertNotNull(iron);
        assertNotNull(gold);
    }

    @AfterEach
    void tearDown() {
        TinkerItemBuilder.resetEquipment();
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("a forged sword, tool and armor piece are unbreakable for vanilla")
    void forgedEquipmentIsUnbreakable() {
        ItemStack sword = forgeSword();
        assertTrue(sword.getItemMeta().isUnbreakable(),
                "A modular sword must not lose vanilla durability on every swing");
        assertTrue(sword.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE),
                "The Unbreakable flag must not be printed on the tooltip");

        ItemStack pickaxe = TinkerItemBuilder.createModularTool(
                ModularToolType.PICKAXE,
                PartComposition.fromMaterials(List.of(diamond)),
                PartComposition.fromMaterials(List.of(iron)),
                PartComposition.fromMaterials(List.of(gold)),
                EvolutionTier.WOOD, 0);
        assertTrue(pickaxe.getItemMeta().isUnbreakable());

        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE,
                PartComposition.fromMaterials(List.of(diamond)),
                PartComposition.fromMaterials(List.of(iron)),
                PartComposition.fromMaterials(List.of(gold)),
                EvolutionTier.WOOD, 0);
        assertTrue(chestplate.getItemMeta().isUnbreakable());
    }

    @Test
    @DisplayName("wear is reported in the lore row instead of the vanilla durability bar")
    void wearIsWrittenIntoTheLore() {
        ItemStack sword = forgeSword();
        List<Component> before = sword.getItemMeta().lore();
        assertNotNull(before);
        int rows = before.size();

        int maxDur = sword.getItemMeta().getPersistentDataContainer()
                .getOrDefault(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, 1000);
        TinkerItemBuilder.updateDurabilityLine(sword, maxDur / 4, maxDur);

        List<Component> after = sword.getItemMeta().lore();
        assertNotNull(after);
        assertEquals(rows, after.size(), "Updating wear must rewrite the row, not add one");

        String expected = DURABILITY_LABEL + (maxDur / 4) + " / " + maxDur;
        assertEquals(1, after.stream().filter(row -> PLAIN.serialize(row).equals(expected)).count(),
                "The durability row must read '" + expected + "'");
        assertEquals(PLAIN.serialize(before.get(0)), PLAIN.serialize(after.get(0)),
                "The tier header above the wear row must stay where it was");
    }

    @Test
    @DisplayName("the rolled attack damage replaces the vanilla base and keeps the swing multipliers")
    void rolledDamageReplacesTheVanillaBase() {
        // A wooden sword hits for 4; a sword rolled to 9 must hit for 9, and a critical (1.5x) for 13.5.
        assertEquals(9.0, ModularToolListener.scaleToRolledDamage(9.0, 4.0, 4.0), 0.001);
        assertEquals(13.5, ModularToolListener.scaleToRolledDamage(9.0, 4.0, 6.0), 0.001);
        // Strength or sharpness already included in the event keep scaling the rolled damage.
        assertEquals(15.75, ModularToolListener.scaleToRolledDamage(9.0, 4.0, 7.0), 0.001);
    }

    @Test
    @DisplayName("a missing or disabled weapon damage never breaks the strike")
    void degenerateDamageInputsAreLeftAlone() {
        assertEquals(4.0, ModularToolListener.scaleToRolledDamage(0.0, 4.0, 4.0), 0.001);
        assertEquals(4.0, ModularToolListener.scaleToRolledDamage(-3.0, 4.0, 4.0), 0.001);
        assertEquals(4.0, ModularToolListener.scaleToRolledDamage(9.0, 0.0, 4.0), 0.001);

        TinkerItemBuilder.configureEquipment(false);
        assertFalse(TinkerItemBuilder.isModularAttackDamage(),
                "Servers that prefer vanilla damage must be able to turn the rule off");
    }

    @Test
    @DisplayName("the forged weapon still records the damage its lore prints")
    void forgedWeaponKeepsItsRolledDamage() {
        ItemStack sword = forgeSword();
        float rolled = sword.getItemMeta().getPersistentDataContainer()
                .getOrDefault(TinkerKeys.TOOL_ATTACK_DAMAGE, PersistentDataType.FLOAT, 0.0f);
        assertTrue(rolled > 0.0f, "The forged weapon must record its rolled attack damage");

        String loreRow = sword.getItemMeta().lore().stream()
                .map(PLAIN::serialize)
                .filter(row -> row.startsWith("  • Attack Damage: +"))
                .findFirst()
                .orElse(null);
        assertNotNull(loreRow, "The weapon must announce its attack damage");
        assertTrue(loreRow.contains(String.format(java.util.Locale.US, "%.1f", rolled)),
                "The lore row '" + loreRow + "' must match the recorded damage " + rolled);
    }

    @Test
    @DisplayName("armor defends with the Defense and Toughness rolled from its minerals")
    void armorDefendsWithItsRolledStats() {
        PartComposition plate = PartComposition.fromMaterials(List.of(diamond));
        PartComposition lining = PartComposition.fromMaterials(List.of(iron));
        PartComposition trim = PartComposition.fromMaterials(List.of(gold));
        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.WOOD, 0);

        TinkerItemBuilder.ArmorStats stats = TinkerItemBuilder.armorStats(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.WOOD);

        // The piece is built on a leather chestplate, which protects for 3: a diamond plate must not be
        // silently worth the vanilla base it happens to be made of.
        assertTrue(stats.defense() > ModularArmorType.CHESTPLATE.getBaseDefense(),
                "A diamond-plated chestplate must roll more Defense than the vanilla base material");

        assertEquals((double) stats.defense(), modularAmount(chestplate, "modular_armor"), 0.001,
                "The server must apply the Defense the armor lore prints");
        assertEquals(stats.toughness(), modularAmount(chestplate, "modular_armor_toughness"), 0.001,
                "The server must apply the Toughness the armor lore prints");
        assertEquals(stats.knockbackResistance(), modularAmount(chestplate, "modular_armor_knockback"), 0.001,
                "The server must apply the knockback resistance the armor lore prints");

        assertEquals(EquipmentSlotGroup.CHEST, modularModifier(chestplate, "modular_armor").getSlotGroup(),
                "The protection must only apply while the piece is worn in its own slot");
        assertTrue(chestplate.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES),
                "Vanilla must not print the same protection a second time");

        String defenseRow = chestplate.getItemMeta().lore().stream()
                .map(PLAIN::serialize)
                .filter(row -> row.startsWith("  • Defense: "))
                .findFirst()
                .orElse(null);
        assertNotNull(defenseRow, "The armor must announce its Defense");
        assertTrue(defenseRow.contains("+" + stats.defense() + " "),
                "The lore row '" + defenseRow + "' must match the applied Defense " + stats.defense());
    }

    @Test
    @DisplayName("evolving the armor tier grows the real protection and rewrites the lore rows")
    void evolvingTheTierGrowsTheProtection() {
        PartComposition plate = PartComposition.fromMaterials(List.of(diamond));
        PartComposition lining = PartComposition.fromMaterials(List.of(iron));
        PartComposition trim = PartComposition.fromMaterials(List.of(gold));
        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.WOOD, 0);
        int rows = chestplate.getItemMeta().lore().size();
        double before = modularAmount(chestplate, "modular_armor");

        TinkerItemBuilder.applyArmorDefense(chestplate, ModularArmorType.CHESTPLATE,
                plate, lining, trim, EvolutionTier.NETHERITE);

        TinkerItemBuilder.ArmorStats upgraded = TinkerItemBuilder.armorStats(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.NETHERITE);
        assertEquals((double) upgraded.defense(), modularAmount(chestplate, "modular_armor"), 0.001,
                "A tier upgrade must raise the protection the server applies");
        assertTrue(upgraded.defense() > before,
                "A netherite-tier piece must defend harder than the same piece at wood tier");
        assertEquals(rows, chestplate.getItemMeta().lore().size(),
                "Refreshing the armor rows must rewrite them, not add or drop one");
        assertNotNull(chestplate.getItemMeta().lore().stream()
                        .map(PLAIN::serialize)
                        .filter(row -> row.contains("+" + upgraded.toughness()))
                        .findFirst()
                        .orElse(null),
                "The refreshed Toughness must be printed in the lore");
    }

    @Test
    @DisplayName("a server can keep the vanilla armor values")
    void armorDefenseCanBeTurnedOff() {
        TinkerItemBuilder.configureArmorDefense(false);
        assertFalse(TinkerItemBuilder.isModularArmorDefense(),
                "Servers that prefer vanilla protection must be able to turn the rule off");

        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE,
                PartComposition.fromMaterials(List.of(diamond)),
                PartComposition.fromMaterials(List.of(iron)),
                PartComposition.fromMaterials(List.of(gold)),
                EvolutionTier.WOOD, 0);

        assertNull(modularAmount(chestplate, "modular_armor"),
                "With the rule off the piece must keep the protection of its tier material");
    }

    /** Amount of one of the armor modifiers this plugin writes, or {@code null} when it is absent. */
    private static Double modularAmount(ItemStack item, String modifierName) {
        AttributeModifier modifier = modularModifier(item, modifierName);
        return modifier == null ? null : modifier.getAmount();
    }

    private static AttributeModifier modularModifier(ItemStack item, String modifierName) {
        Multimap<Attribute, AttributeModifier> modifiers = item.getItemMeta().getAttributeModifiers();
        if (modifiers == null) return null;
        for (AttributeModifier modifier : modifiers.values()) {
            if (modifier.getKey().getKey().equals(modifierName)) return modifier;
        }
        return null;
    }

    private ItemStack forgeSword() {
        return TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(diamond)),
                PartComposition.fromMaterials(List.of(iron)),
                PartComposition.fromMaterials(List.of(gold)),
                EvolutionTier.WOOD, 0);
    }
}
