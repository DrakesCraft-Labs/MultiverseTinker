package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("Every weapon perk is named after the minerals it was forged from")
    void testWeaponPerkVariesWithHeadMineral() {
        TinkerMaterial cobalt = registry.get("mvtink_cobalt");   // Nether  -> Infernal
        TinkerMaterial voidstone = registry.get("mvtink_voidstone"); // The End -> Void
        TinkerMaterial iron = registry.get("mvtink_iron");
        assertNotNull(cobalt);
        assertNotNull(voidstone);
        assertNotNull(iron);

        PartComposition body = PartComposition.fromMaterials(List.of(iron));

        for (ModularWeaponType type : ModularWeaponType.values()) {
            ItemStack infernal = TinkerItemBuilder.createModularWeapon(type,
                    PartComposition.fromMaterials(List.of(cobalt)), body, type.isTwoPart() ? null : body,
                    EvolutionTier.WOOD, 0);
            ItemStack voided = TinkerItemBuilder.createModularWeapon(type,
                    PartComposition.fromMaterials(List.of(voidstone)), body, type.isTwoPart() ? null : body,
                    EvolutionTier.WOOD, 0);

            String infernalPerk = perkLine(infernal);
            String voidPerk = perkLine(voided);

            assertTrue(infernalPerk.contains(WeaponPerkProfile.baseName(type)),
                    type + " must keep its signature perk name, got: " + infernalPerk);
            assertTrue(voidPerk.contains(WeaponPerkProfile.baseName(type)),
                    type + " must keep its signature perk name, got: " + voidPerk);
            assertTrue(infernalPerk.startsWith(PerkEpithet.of(cobalt) + " "),
                    type + " must lead with the epithet of the head mineral it was forged from, got: " + infernalPerk);
            assertTrue(voidPerk.startsWith(PerkEpithet.of(voidstone) + " "),
                    type + " must lead with the epithet of the head mineral it was forged from, got: " + voidPerk);
            assertNotEquals(infernalPerk, voidPerk,
                    type + " perk must vary with the minerals it is forged from");

            assertTrue(flattenLore(infernal).contains("Essence Focus"),
                    type + " must display the dominant essence of its composition");
        }
    }

    @Test
    @DisplayName("Weapon perk profiles are deterministic, blended and stay within bounds")
    void testWeaponPerkProfileDeterminism() {
        TinkerMaterial cobalt = registry.get("mvtink_cobalt");
        TinkerMaterial voidstone = registry.get("mvtink_voidstone");
        TinkerMaterial iron = registry.get("mvtink_iron");

        PartComposition head = PartComposition.fromMaterials(List.of(cobalt));
        PartComposition rod = PartComposition.fromMaterials(List.of(iron));
        PartComposition binding = PartComposition.fromMaterials(List.of(voidstone));

        WeaponPerkProfile first = WeaponPerkProfile.of(ModularWeaponType.SWORD, head, rod, binding);
        WeaponPerkProfile second = WeaponPerkProfile.of(ModularWeaponType.SWORD, head, rod, binding);

        assertEquals(first.getPerkLine(), second.getPerkLine(), "Profiles must be deterministic");
        assertEquals(first.getAffinity(), TraitAffinity.INFERNAL,
                "The head mineral dictates the identity essence");
        assertTrue(first.getEssenceBlend().contains("Infernal"),
                "Description must name the head mineral's essence blend, got: " + first.getEssenceBlend());
        assertTrue(first.getPotency() > 0.0 && first.getPotency() <= 1.0,
                "Potency must be a positive fraction, got: " + first.getPotency());

        WeaponPerkProfile fullyInfernal = WeaponPerkProfile.of(
                ModularWeaponType.SWORD, head, head, head);
        assertEquals(1.0, fullyInfernal.getPotency(), 1e-9,
                "A weapon forged entirely from one essence' minerals is fully focused");
        assertTrue(fullyInfernal.getPotency() > first.getPotency(),
                "Concentrating the essence must raise its potency");

        assertNotEquals(first.getDisplayName(),
                WeaponPerkProfile.of(ModularWeaponType.SWORD, binding, rod, head).getDisplayName(),
                "Swapping the head mineral must change the perk name");
    }

    @Test
    @DisplayName("No lore row of a forged weapon can run off the tooltip")
    void weaponLoreRowsStayWithinTheTooltipWidth() {
        TinkerMaterial cobalt = registry.get("mvtink_cobalt");
        TinkerMaterial voidstone = registry.get("mvtink_voidstone");
        TinkerMaterial gold = registry.get("mvtink_gold");

        for (ModularWeaponType type : ModularWeaponType.values()) {
            ItemStack weapon = TinkerItemBuilder.createModularWeapon(type,
                    PartComposition.fromMaterials(List.of(cobalt)),
                    PartComposition.fromMaterials(List.of(voidstone)),
                    type.isTwoPart() ? null : PartComposition.fromMaterials(List.of(gold)),
                    EvolutionTier.DIAMOND, 300);

            List<Component> lore = weapon.getItemMeta().lore();
            assertNotNull(lore, type + " must carry lore");

            for (int i = 0; i < lore.size(); i++) {
                String row = PlainTextComponentSerializer.plainText().serialize(lore.get(i));
                // The first two rows are the tier tag and the progress bar, rewritten in place on
                // every level up, so they are allowed the wider header budget.
                int budget = (i < 2) ? LoreWrap.HEADER_MAX_PIXELS : LoreWrap.DEFAULT_MAX_PIXELS;
                assertTrue(LoreWrap.pixelWidth(row) <= budget,
                        type + " lore row " + i + " is " + LoreWrap.pixelWidth(row) + "px wide: " + row);
            }
        }
    }

    /**
     * Extracts the rendered perk text, e.g. {@code "Infernal Sweeping Cleave: hits multiple ..."}.
     *
     * <p>Lore is word-wrapped, so the perk is read as plain text instead of from the component
     * repr: the wrapped rows are joined and the leading {@code "✦ Weapon Perk: "} label dropped.</p>
     */
    private String perkLine(ItemStack item) {
        String lore = plainLore(item);
        int marker = lore.indexOf("Weapon Perk: ");
        if (marker < 0) return "";

        String perk = lore.substring(marker + "Weapon Perk: ".length());
        int focus = perk.indexOf("Essence Focus");
        if (focus >= 0) perk = perk.substring(0, focus);
        return perk.replace("•", "").trim();
    }

    private String flattenLore(ItemStack item) {
        return plainLore(item);
    }

    /** Full lore as plain text; wrapped rows are joined with a single space. */
    private String plainLore(ItemStack item) {
        List<Component> lore = item.getItemMeta().lore();
        assertNotNull(lore, "Item lore must not be null");
        return plainRows(lore).stream().collect(Collectors.joining(" "));
    }

    private List<String> plainRows(List<Component> lore) {
        List<String> rows = new ArrayList<>();
        for (Component line : lore) {
            rows.add(PlainTextComponentSerializer.plainText().serialize(line).trim());
        }
        return rows;
    }

    /** The affinity row of a forged item, including the rows the wrap moved it onto. */
    private String affinityLine(ItemStack item) {
        List<String> rows = plainRows(item.getItemMeta().lore());
        StringBuilder builder = new StringBuilder();
        boolean collecting = false;
        for (String row : rows) {
            if (!collecting) {
                if (row.contains("Mineral Affinities")) {
                    collecting = true;
                    builder.append(row);
                }
                continue;
            }
            if (row.isEmpty() || row.startsWith("✦")) break;
            builder.append(' ').append(row);
        }
        return builder.toString();
    }
}
