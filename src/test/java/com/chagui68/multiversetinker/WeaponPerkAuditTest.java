package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeaponPerkAuditTest {

    private MaterialRegistry registry;
    private PartComposition blade;
    private PartComposition hilt;
    private PartComposition guard;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        MultiverseTinker plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();

        TinkerMaterial diamond = registry.get("mvtink_diamond");
        TinkerMaterial iron = registry.get("mvtink_iron");
        TinkerMaterial gold = registry.get("mvtink_gold");
        assertNotNull(diamond);
        assertNotNull(iron);
        assertNotNull(gold);

        blade = PartComposition.fromMaterials(List.of(diamond));
        hilt = PartComposition.fromMaterials(List.of(iron));
        guard = PartComposition.fromMaterials(List.of(gold));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("All 7 modular weapon types carry their PDC type, perk and affinity lore")
    void testAllWeaponPerksPresent() {
        for (ModularWeaponType type : ModularWeaponType.values()) {
            ItemStack weapon = TinkerItemBuilder.createModularWeapon(
                    type, blade, hilt, type.isTwoPart() ? null : guard, EvolutionTier.WOOD, 0);

            assertNotNull(weapon);
            assertTrue(weapon.hasItemMeta(), type + " must have item meta");
            var pdc = weapon.getItemMeta().getPersistentDataContainer();
            assertTrue(pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE));
            assertEquals(type.name(), pdc.get(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING));

            String lore = flattenLore(weapon);
            assertTrue(lore.contains("Weapon Perk"), type + " is missing its perk lore");
            assertTrue(lore.contains("Mineral Affinities"), type + " is missing its affinity lore");
        }
    }

    @Test
    @DisplayName("All 5 modular tool types carry their PDC type, perk and affinity lore")
    void testAllToolPerksPresent() {
        for (ModularToolType type : ModularToolType.values()) {
            if (type == ModularToolType.SWORD) continue;
            ItemStack tool = TinkerItemBuilder.createModularTool(type, blade, hilt, guard, EvolutionTier.WOOD, 0);

            assertNotNull(tool);
            var pdc = tool.getItemMeta().getPersistentDataContainer();
            assertEquals(type.name(), pdc.get(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING));

            String lore = flattenLore(tool);
            assertTrue(lore.contains("Tool Perk"), type + " is missing its perk lore");
            assertTrue(lore.contains("Mineral Affinities"), type + " is missing its affinity lore");
        }
    }

    @Test
    @DisplayName("All 4 modular armor types carry their PDC type, perk and affinity lore")
    void testAllArmorPerksPresent() {
        for (ModularArmorType type : ModularArmorType.values()) {
            ItemStack armor = TinkerItemBuilder.createModularArmor(type, blade, hilt, guard, EvolutionTier.WOOD, 0);

            assertNotNull(armor);
            var pdc = armor.getItemMeta().getPersistentDataContainer();
            assertEquals(type.name(), pdc.get(TinkerKeys.ARMOR_TYPE, PersistentDataType.STRING));

            String lore = flattenLore(armor);
            assertTrue(lore.contains("Armor Perk"), type + " is missing its perk lore");
            assertTrue(lore.contains("Mineral Affinities"), type + " is missing its affinity lore");
        }
    }

    @Test
    @DisplayName("Different mineral combinations expose different affinity blends in lore")
    void testDifferentCombinationsShowDifferentAffinities() {
        TinkerMaterial tin = registry.get("mvtink_tin");
        TinkerMaterial enderite = registry.get("mvtink_enderite");
        TinkerMaterial netherite = registry.get("mvtink_netherite");

        ItemStack first = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(tin)),
                PartComposition.fromMaterials(List.of(netherite)),
                PartComposition.fromMaterials(List.of(netherite)),
                EvolutionTier.WOOD, 0);

        ItemStack second = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD,
                PartComposition.fromMaterials(List.of(enderite)),
                PartComposition.fromMaterials(List.of(enderite)),
                PartComposition.fromMaterials(List.of(tin)),
                EvolutionTier.WOOD, 0);

        assertNotEquals(affinityLine(first), affinityLine(second),
                "Distinct material combinations must produce distinct affinity profiles");
    }

    @Test
    @DisplayName("Crossbow perk explicitly advertises the explosive piercing bolts")
    void testCrossbowPerkText() {
        ItemStack crossbow = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.CROSSBOW, blade, hilt, guard, EvolutionTier.WOOD, 0);
        String lore = flattenLore(crossbow);
        assertTrue(lore.contains("Piercing Velocity"));
        assertTrue(lore.contains("explosive impact"));
    }

    private String flattenLore(ItemStack item) {
        List<Component> lore = item.getItemMeta().lore();
        assertNotNull(lore, "Item lore must not be null");
        StringBuilder builder = new StringBuilder();
        for (Component line : lore) {
            builder.append(line.toString()).append('\n');
        }
        return builder.toString();
    }

    private String affinityLine(ItemStack item) {
        for (Component line : item.getItemMeta().lore()) {
            String text = line.toString();
            if (text.contains("Mineral Affinities")) return text;
        }
        return "";
    }
}
