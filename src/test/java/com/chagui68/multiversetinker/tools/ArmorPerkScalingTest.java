package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.List;

import static com.chagui68.multiversetinker.api.ModularArmorType.BOOTS;
import static com.chagui68.multiversetinker.api.ModularArmorType.CHESTPLATE;
import static com.chagui68.multiversetinker.api.ModularArmorType.HELMET;
import static com.chagui68.multiversetinker.api.ModularArmorType.LEGGINGS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The armor perks answer a hit with a share of it, and that share is rolled with the piece.
 *
 * <p>Kinetic Dampener, Cranium Ward and Feathered Grounding used to mitigate a fixed percentage, so a
 * tin piece protected exactly like a prime-alloy one and the Defense and Toughness printed in the lore
 * changed nothing about the perk. These tests pin both halves of the new contract: the curve keeps the
 * percentages the perks shipped with as its floor, and combat applies the same number the piece's own
 * lore prints.</p>
 *
 * <p>The pieces are forged from minerals the trait engine leaves alone — a plate of Singularite and a
 * lining of Cosmic Netherite, which cost nothing in damage — because a mineral that scales the blow
 * (gold, diamond, obsidian) would sit between the perk and the assertion.</p>
 */
@DisplayName("Armor perks mitigate a share rolled from the piece's Defense and Toughness")
class ArmorPerkScalingTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
    private static final String PERK_LABEL = "✦ Armor Perk: ";

    /** The sharpest inert mineral and the toughest inert alloy: a strong roll that scales no damage. */
    private static final String PLATE = "mvtink_singularite";
    private static final String LINING = "mvtink_cosmic_netherite";
    private static final String TRIM = "mvtink_tin";

    private ServerMock server;
    private WorldMock world;
    private MaterialRegistry registry;
    private Player player;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        MultiverseTinker plugin = MockBukkit.load(MultiverseTinker.class);
        registry = plugin.getMaterialRegistry();
        world = server.addSimpleWorld("world");
        player = server.addPlayer("smith");
        player.teleport(world.getSpawnLocation());
    }

    @AfterEach
    void tearDown() {
        TinkerItemBuilder.resetEquipment();
        LoreWrap.reset();
        MockBukkit.unmock();
    }

    // ==========================================
    // THE CURVE
    // ==========================================

    @Test
    @DisplayName("a bare slot keeps the shipped percentage and every rolled point adds two more")
    void theCurveStartsWhereTheFixedPercentagesWere() {
        // Nothing rolled above the slot's own base: the perk is exactly the percentage it shipped with,
        // so no build that existed before the scaling got weaker.
        assertEquals(0.25, rolled(CHESTPLATE, 0), 1e-9, "Kinetic Dampener used to absorb a flat 25%");
        assertEquals(0.50, rolled(BOOTS, 0), 1e-9, "Feathered Grounding used to negate a flat 50%");
        assertEquals(0.30, rolled(HELMET, 0), 1e-9, "The Cranium Ward floor");
        assertEquals(0.0, rolled(LEGGINGS, 0), 1e-9, "Stride Momentum is mobility, not mitigation");

        // Two percent per point of rolled Defense and Toughness, for every slot that mitigates.
        for (ModularArmorType type : List.of(HELMET, CHESTPLATE, BOOTS)) {
            for (int surplus = 0; surplus < 12; surplus++) {
                assertEquals(rolled(type, 0) + 0.02 * surplus, rolled(type, surplus), 1e-9,
                        type + " must buy exactly 2% per rolled point");
            }
            assertTrue(rolled(type, 20) > rolled(type, 10),
                    type + " must keep growing with a better roll");
        }
    }

    @Test
    @DisplayName("the curve is capped, so a perfect piece cannot become untouchable")
    void theCurveIsCapped() {
        assertEquals(0.65, rolled(HELMET, 10_000), 1e-9);
        assertEquals(0.60, rolled(CHESTPLATE, 10_000), 1e-9);
        assertEquals(0.75, rolled(BOOTS, 10_000), 1e-9);
    }

    @Test
    @DisplayName("a strong plate wards more than a plain one, at the same tier")
    void aStrongerPlateWardsMore() {
        ItemStack plain = forge(ModularArmorType.CHESTPLATE, "mvtink_tin", "mvtink_tin", "mvtink_tin",
                EvolutionTier.WOOD);
        ItemStack strong = forge(ModularArmorType.CHESTPLATE, PLATE, LINING, TRIM, EvolutionTier.WOOD);

        double plainShare = TinkerItemBuilder.mitigationOf(plain, registry);
        double scaled = TinkerItemBuilder.mitigationOf(strong, registry);
        assertTrue(plainShare >= 0.25,
                "A plain plate must never ward less than the 25% the perk shipped with, got " + plainShare);
        assertTrue(scaled > plainShare,
                "A stronger roll must absorb more, got " + scaled + " vs " + plainShare);
    }

    // ==========================================
    // THE LORE
    // ==========================================

    @Test
    @DisplayName("the perk sentence prints the share the piece actually applies")
    void theLorePrintsTheAppliedShare() {
        ItemStack chestplate = forge(ModularArmorType.CHESTPLATE, PLATE, LINING, TRIM, EvolutionTier.WOOD);
        double share = TinkerItemBuilder.mitigationOf(chestplate, registry);
        assertTrue(share > 0.25, "The roll must have bought more than the floor, got " + share);

        String perk = perkRow(chestplate);
        assertTrue(perk.contains("absorbs " + Math.round(share * 100) + "%"),
                "The lore must promise the share it applies, got: " + perk);
        assertTrue(perk.contains("essence:"),
                "The perk sentence must keep the rest of its wording, got: " + perk);
    }

    @Test
    @DisplayName("a tier upgrade rewrites the perk sentence in place and grows its percentage")
    void aTierUpgradeRewritesThePerkRow() {
        PartComposition plate = PartComposition.fromMaterials(List.of(material(PLATE)));
        PartComposition lining = PartComposition.fromMaterials(List.of(material(LINING)));
        PartComposition trim = PartComposition.fromMaterials(List.of(material(TRIM)));

        ItemStack chestplate = TinkerItemBuilder.createModularArmor(
                ModularArmorType.CHESTPLATE, plate, lining, trim, EvolutionTier.WOOD, 0);
        int rows = chestplate.getItemMeta().lore().size();
        double before = TinkerItemBuilder.mitigationOf(chestplate, registry);
        assertTrue(perkRow(chestplate).contains("absorbs " + Math.round(before * 100) + "%"),
                "The wood-tier piece must print its own share, got: " + perkRow(chestplate));

        TinkerItemBuilder.applyArmorDefense(chestplate, ModularArmorType.CHESTPLATE,
                plate, lining, trim, EvolutionTier.NETHERITE);

        double after = TinkerItemBuilder.mitigationOf(chestplate, registry);
        assertTrue(after > before, "A netherite piece must ward more than the same piece at wood tier");
        assertEquals(rows, chestplate.getItemMeta().lore().size(),
                "Refreshing the perk must rewrite its rows, not add or drop one");
        assertEquals(1L, chestplate.getItemMeta().lore().stream()
                        .map(PLAIN::serialize)
                        .filter(row -> row.startsWith(PERK_LABEL))
                        .count(),
                "A refresh must leave exactly one perk row behind");
        assertTrue(perkRow(chestplate).contains("absorbs " + Math.round(after * 100) + "%"),
                "The refreshed lore must print the share the piece now applies, got: " + perkRow(chestplate));
    }

    // ==========================================
    // IN COMBAT
    // ==========================================

    @Test
    @DisplayName("the Kinetic Dampener absorbs the share its own plate rolled")
    void kineticDampenerScalesWithTheRoll() {
        ItemStack chestplate = wear(ModularArmorType.CHESTPLATE, EvolutionTier.WOOD);
        double share = TinkerItemBuilder.mitigationOf(chestplate, registry);
        assertTrue(share > 0.25, "The roll must have bought more than the floor, got " + share);

        Zombie attacker = world.spawn(player.getLocation(), Zombie.class);
        assertEquals(10.0 * (1.0 - share), hitFor(attacker, 10.0), 1e-6,
                "A heavy blow must be dampened by the share the piece rolled");

        // A light tap is under the heavy-blow gate and must pass through untouched.
        assertEquals(3.0, hitFor(attacker, 3.0), 1e-6,
                "Kinetic Dampener only reaches for heavy impacts");
    }

    @Test
    @DisplayName("Feathered Grounding negates the share its own boots rolled")
    void featheredGroundingScalesWithTheRoll() {
        ItemStack boots = wear(ModularArmorType.BOOTS, EvolutionTier.WOOD);
        double share = TinkerItemBuilder.mitigationOf(boots, registry);
        assertTrue(share > 0.50, "The roll must have bought more than the floor, got " + share);

        EntityDamageEvent fall = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.FALL, 20.0);
        server.getPluginManager().callEvent(fall);
        assertEquals(20.0 * (1.0 - share), fall.getDamage(), 1e-6,
                "A fall must be negated by the share the boots rolled");
    }

    @Test
    @DisplayName("the Cranium Ward blunts a headshot and leaves a body blow alone")
    void craniumWardBluntsHeadshots() {
        ItemStack helmet = wear(ModularArmorType.HELMET, EvolutionTier.WOOD);
        double share = TinkerItemBuilder.mitigationOf(helmet, registry);
        assertTrue(share > 0.30, "The roll must have bought more than the floor, got " + share);

        // An arrow always counts as a headshot, because a projectile is what a helmet can ward.
        Arrow arrow = world.spawn(player.getLocation().add(3, 0, 0), Arrow.class);
        assertEquals(10.0 * (1.0 - share), hitFor(arrow, 10.0), 1e-6,
                "An arrow must be warded by the share the helmet rolled");

        // A blow from above the wearer's eyes is a headshot too.
        Zombie above = world.spawn(player.getLocation().add(0, 3, 0), Zombie.class);
        assertEquals(10.0 * (1.0 - share), hitFor(above, 10.0), 1e-6,
                "A descending blow must be warded");

        // A blow from the same ground level is not, so the ward must not touch it.
        Zombie level = world.spawn(player.getLocation(), Zombie.class);
        assertEquals(10.0, hitFor(level, 10.0), 1e-6,
                "A same-level blow is not a headshot and must pass through the ward");
    }

    // ==========================================
    // HELPERS
    // ==========================================

    /** The share a piece with the given Defense/Toughness surplus over its bare slot would mitigate. */
    private static double rolled(ModularArmorType type, int surplus) {
        return TinkerItemBuilder.signatureMitigation(type, new TinkerItemBuilder.ArmorStats(
                type.getBaseDefense() + surplus, type.getBaseToughness(), 0));
    }

    private ItemStack forge(ModularArmorType type, String plate, String lining, String trim,
                            EvolutionTier tier) {
        return TinkerItemBuilder.createModularArmor(type,
                PartComposition.fromMaterials(List.of(material(plate))),
                PartComposition.fromMaterials(List.of(material(lining))),
                PartComposition.fromMaterials(List.of(material(trim))),
                tier, 0);
    }

    /** Forges the strong, inert piece of a slot and puts it on the player. */
    private ItemStack wear(ModularArmorType type, EvolutionTier tier) {
        ItemStack piece = forge(type, PLATE, LINING, TRIM, tier);
        ItemStack[] armor = player.getInventory().getArmorContents();
        armor[switch (type) {
            case HELMET -> 3;
            case CHESTPLATE -> 2;
            case LEGGINGS -> 1;
            case BOOTS -> 0;
        }] = piece;
        player.getInventory().setArmorContents(armor);
        return piece;
    }

    /** Deals a blow and hands back what it cost once every armor perk has had its say. */
    private double hitFor(Entity attacker, double amount) {
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, player,
                EntityDamageEvent.DamageCause.ENTITY_ATTACK, amount);
        server.getPluginManager().callEvent(event);
        return event.getDamage();
    }

    /**
     * The perk sentence of a piece as one string, rejoining the rows the wrap split it over.
     *
     * <p>Only the first row carries the label, so the block runs until a row opens something of its own
     * again — the same rule the plugin uses to rewrite the sentence in place.</p>
     */
    private static String perkRow(ItemStack piece) {
        List<String> rows = piece.getItemMeta().lore().stream().map(PLAIN::serialize).toList();
        StringBuilder sentence = new StringBuilder();
        boolean inside = false;
        for (String row : rows) {
            if (!inside) {
                if (row.startsWith(PERK_LABEL)) {
                    inside = true;
                    sentence.append(row.substring(PERK_LABEL.length()));
                }
                continue;
            }
            if (row.isEmpty() || row.startsWith("✦") || row.startsWith("  • ")) break;
            sentence.append(' ').append(row.trim());
        }
        assertTrue(inside, "The piece carries no armor perk row");
        return sentence.toString();
    }

    private TinkerMaterial material(String id) {
        TinkerMaterial material = registry.get(id);
        assertNotNull(material, id + " must be registered");
        return material;
    }
}
