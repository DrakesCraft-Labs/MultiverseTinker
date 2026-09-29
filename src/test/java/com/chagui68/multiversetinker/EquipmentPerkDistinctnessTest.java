package com.chagui68.multiversetinker;

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
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Every weapon, tool and armor type must own its own perk: a distinct name, a distinct mechanic and
 * a distinct lore line. This guards the regression where every piece of equipment of a kind printed
 * the same perk, with no trace of the minerals it was forged from.
 */
class EquipmentPerkDistinctnessTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private MultiverseTinker plugin;
    private PartComposition head;
    private PartComposition rod;
    private PartComposition binding;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        MaterialRegistry registry = plugin.getMaterialRegistry();

        // Cobalt is an Infernal Nether mineral, iron is Tempered, voidstone is a Void End crystal:
        // the three parts deliberately disagree so the essence focus is not trivially 100%.
        head = PartComposition.fromMaterials(List.of(registry.get("mvtink_cobalt")));
        rod = PartComposition.fromMaterials(List.of(registry.get("mvtink_iron")));
        binding = PartComposition.fromMaterials(List.of(registry.get("mvtink_voidstone")));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("All 16 equipment types show a different perk name and mechanic")
    void everyEquipmentTypeHasItsOwnPerk() {
        Map<String, String> perkByType = new LinkedHashMap<>();
        Map<String, String> typeByName = new LinkedHashMap<>();
        Map<String, String> typeByPerk = new LinkedHashMap<>();

        for (Map.Entry<String, ItemStack> entry : forgeEverything().entrySet()) {
            String type = entry.getKey();
            String perk = perkLine(entry.getValue());

            assertFalse(perk.isBlank(), type + " must print a perk line");
            assertNull(typeByPerk.put(perk, type), "Two equipment types share the same perk: " + perk);
            perkByType.put(type, perk);

            String name = perk.contains(":") ? perk.substring(0, perk.indexOf(':')).trim() : perk;
            assertNull(typeByName.put(name, type), "Two equipment types share the perk name: " + name);
        }

        assertEquals(16, perkByType.size(),
                "Expected 7 weapons + 5 tools + 4 armor pieces, got " + perkByType.keySet());
    }

    @Test
    @DisplayName("Tools and armor perks are material-driven exactly like weapons")
    void toolAndArmorPerksAreMaterialDriven() {
        for (Map.Entry<String, ItemStack> entry : forgeEverything().entrySet()) {
            String type = entry.getKey();
            String lore = plainLore(entry.getValue());

            assertTrue(lore.contains("Essence Focus"), type + " must show the essence focus of its forge");
            assertTrue(lore.contains("%"), type + " must show the focus percentage");

            if (!type.startsWith("SWORD") && !type.startsWith("BOW") && !type.startsWith("CROSSBOW")
                    && !type.startsWith("TRIDENT") && !type.startsWith("SPEAR") && !type.startsWith("MACE")
                    && !type.startsWith("SHIELD")) {
                assertTrue(lore.contains("Infernal"), type + " perk must carry its head mineral's essence");
            }
        }
    }

    @Test
    @DisplayName("Every equipment type labels its trait rows with the moment it fires them")
    void traitRowsAreLabelledPerEquipmentType() {
        Map<String, String> channels = new LinkedHashMap<>();
        channels.put("SWORD", "on sweep");
        channels.put("BOW", "on arrow hit");
        channels.put("CROSSBOW", "on bolt impact");
        channels.put("TRIDENT", "on surge");
        channels.put("SPEAR", "on thrust");
        channels.put("MACE", "on smash");
        channels.put("SHIELD", "on block");
        channels.put("PICKAXE", "while mining");
        channels.put("AXE", "while chopping");
        channels.put("SHOVEL", "while digging");
        channels.put("HOE", "while harvesting");
        channels.put("FISHING_ROD", "while fishing");
        channels.put("HELMET", "when struck");
        channels.put("CHESTPLATE", "when struck");
        channels.put("LEGGINGS", "when struck");
        channels.put("BOOTS", "when struck");

        Set<String> seenChannels = new LinkedHashSet<>();
        Map<String, ItemStack> forged = forgeEverything();

        for (Map.Entry<String, String> entry : channels.entrySet()) {
            String type = entry.getKey();
            String channel = entry.getValue();
            String lore = plainLore(forged.get(type));

            assertTrue(lore.contains("(" + channel + ")"),
                    type + " must label its traits with \"" + channel + "\", got:\n" + lore);
            seenChannels.add(type.contains("HELMET") || type.contains("CHESTPLATE")
                    || type.contains("LEGGINGS") || type.contains("BOOTS") ? "armor" : channel);
        }

        // The 12 offensive/utility types must each own a different firing moment.
        assertEquals(13, seenChannels.size(), "Trait channels must be distinct per equipment type");
    }

    /** Forges one item of every type with the same three materials. */
    private Map<String, ItemStack> forgeEverything() {
        Map<String, ItemStack> forged = new LinkedHashMap<>();

        for (ModularWeaponType type : ModularWeaponType.values()) {
            forged.put(type.name(), TinkerItemBuilder.createModularWeapon(type, head, rod,
                    type.isTwoPart() ? null : binding, EvolutionTier.WOOD, 0));
        }
        for (ModularToolType type : ModularToolType.values()) {
            if (type == ModularToolType.SWORD) continue;
            forged.put(type.name(), TinkerItemBuilder.createModularTool(type, head, rod, binding, EvolutionTier.WOOD, 0));
        }
        for (ModularArmorType type : ModularArmorType.values()) {
            forged.put(type.name(), TinkerItemBuilder.createModularArmor(type, head, rod, binding, EvolutionTier.WOOD, 0));
        }
        return forged;
    }

    /** The rendered perk of an item, e.g. {@code "Infernal Sweeping Cleave: hits multiple ..."}. */
    private String perkLine(ItemStack item) {
        String lore = plainLore(item);
        int marker = lore.indexOf("Perk: ");
        if (marker < 0) return "";

        String perk = lore.substring(marker + "Perk: ".length());
        int focus = perk.indexOf("Essence Focus");
        if (focus >= 0) perk = perk.substring(0, focus);
        return perk.replace("•", "").trim();
    }

    private String plainLore(ItemStack item) {
        List<String> rows = new ArrayList<>();
        for (Component line : item.getItemMeta().lore()) {
            rows.add(PLAIN.serialize(line).trim());
        }
        return String.join(" ", rows);
    }
}
