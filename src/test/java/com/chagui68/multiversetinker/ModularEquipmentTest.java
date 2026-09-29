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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModularEquipmentTest {

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
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Should create 3-part modular broadsword starting at Wood Tier")
    void testCreateModularSword() {
        PartComposition blade = PartComposition.fromMaterials(List.of(diamond));
        PartComposition hilt = PartComposition.fromMaterials(List.of(iron));
        PartComposition guard = PartComposition.fromMaterials(List.of(gold));

        ItemStack sword = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD, blade, hilt, guard, EvolutionTier.WOOD, 0
        );

        assertNotNull(sword);
        assertEquals(Material.WOODEN_SWORD, sword.getType());
        assertTrue(sword.hasItemMeta());

        var pdc = sword.getItemMeta().getPersistentDataContainer();
        assertTrue(pdc.has(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE));
        assertEquals(EvolutionTier.WOOD.name(), pdc.get(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING));
        assertEquals(0, pdc.get(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER));
        assertEquals("mvtink_diamond", pdc.get(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING));
    }

    @Test
    @DisplayName("Should create 2-part modular longbow starting at Wood Tier")
    void testCreateModularBow() {
        PartComposition limbs = PartComposition.fromMaterials(List.of(diamond));
        PartComposition string = PartComposition.fromMaterials(List.of(iron));

        ItemStack bow = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.BOW, limbs, string, null, EvolutionTier.WOOD, 0
        );

        assertNotNull(bow);
        assertEquals(Material.BOW, bow.getType());
        var pdc = bow.getItemMeta().getPersistentDataContainer();
        assertEquals(EvolutionTier.WOOD.name(), pdc.get(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING));
    }

    @Test
    @DisplayName("Should create 3-part modular pickaxe starting at Wood Tier")
    void testCreateModularPickaxe() {
        PartComposition head = PartComposition.fromMaterials(List.of(diamond));
        PartComposition handle = PartComposition.fromMaterials(List.of(iron));
        PartComposition pommel = PartComposition.fromMaterials(List.of(gold));

        ItemStack pickaxe = TinkerItemBuilder.createModularTool(
                ModularToolType.PICKAXE, head, handle, pommel, EvolutionTier.WOOD, 0
        );

        assertNotNull(pickaxe);
        assertEquals(Material.WOODEN_PICKAXE, pickaxe.getType());
        var pdc = pickaxe.getItemMeta().getPersistentDataContainer();
        assertTrue(pdc.has(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE));
        assertEquals(EvolutionTier.WOOD.name(), pdc.get(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING));
        assertEquals(0, pdc.get(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER));
    }

    @Test
    @DisplayName("Should create 3-part modular armor across all 4 pieces and evolution tiers")
    void testCreateModularArmor() {
        PartComposition plate = PartComposition.fromMaterials(List.of(diamond));
        PartComposition lining = PartComposition.fromMaterials(List.of(iron));
        PartComposition trim = PartComposition.fromMaterials(List.of(gold));

        // Helmet
        ItemStack helmet = TinkerItemBuilder.createModularArmor(
                ModularArmorType.HELMET, plate, lining, trim, EvolutionTier.WOOD, 0
        );
        assertNotNull(helmet);
        assertEquals(Material.LEATHER_HELMET, helmet.getType());
        var hPdc = helmet.getItemMeta().getPersistentDataContainer();
        assertTrue(hPdc.has(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE));
        assertEquals(ModularArmorType.HELMET.name(), hPdc.get(TinkerKeys.ARMOR_TYPE, PersistentDataType.STRING));
        assertEquals(EvolutionTier.WOOD.name(), hPdc.get(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING));

        // Chestplate at Diamond Tier
        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.DIAMOND, 1500
        );
        assertNotNull(chestplate);
        assertEquals(Material.DIAMOND_CHESTPLATE, chestplate.getType());
        var cPdc = chestplate.getItemMeta().getPersistentDataContainer();
        assertEquals(EvolutionTier.DIAMOND.name(), cPdc.get(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING));
        assertEquals(1500, cPdc.get(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER));

        // Leggings
        ItemStack leggings = TinkerItemBuilder.createModularArmor(
                ModularArmorType.LEGGINGS, plate, lining, trim, EvolutionTier.IRON, 350
        );
        assertNotNull(leggings);
        assertEquals(Material.IRON_LEGGINGS, leggings.getType());

        // Boots at Netherite Tier
        ItemStack boots = TinkerItemBuilder.createModularArmor(
                ModularArmorType.BOOTS, plate, lining, trim, EvolutionTier.NETHERITE, 3000
        );
        assertNotNull(boots);
        assertEquals(Material.NETHERITE_BOOTS, boots.getType());
    }

    @Test
    @DisplayName("Should create modular spear using native vanilla spear materials")
    void testCreateModularSpear() {
        PartComposition head = PartComposition.fromMaterials(List.of(diamond));
        PartComposition shaft = PartComposition.fromMaterials(List.of(iron));
        PartComposition buttCap = PartComposition.fromMaterials(List.of(gold));

        // Wood tier spear
        ItemStack woodSpear = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SPEAR, head, shaft, buttCap, EvolutionTier.WOOD, 0
        );
        assertNotNull(woodSpear);
        assertEquals(Material.WOODEN_SPEAR, woodSpear.getType());

        // Diamond tier spear
        ItemStack diamondSpear = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SPEAR, head, shaft, buttCap, EvolutionTier.DIAMOND, 300
        );
        assertNotNull(diamondSpear);
        assertEquals(Material.DIAMOND_SPEAR, diamondSpear.getType());
    }

    @Test
    @DisplayName("Should update weapon progress and lore accurately on kills")
    void testWeaponKillProgress() {
        PartComposition head = PartComposition.fromMaterials(List.of(diamond));
        PartComposition hilt = PartComposition.fromMaterials(List.of(iron));
        PartComposition guard = PartComposition.fromMaterials(List.of(gold));

        ItemStack sword = TinkerItemBuilder.createModularWeapon(
                ModularWeaponType.SWORD, head, hilt, guard, EvolutionTier.WOOD, 0
        );
        assertNotNull(sword);

        // Update progress to 10 kills
        TinkerItemBuilder.updateWeaponProgress(sword, EvolutionTier.WOOD, 10);
        assertNotNull(sword.getItemMeta().lore());
        assertTrue(sword.getItemMeta().lore().get(1).toString().contains("10/15 Kills"));
    }
}
