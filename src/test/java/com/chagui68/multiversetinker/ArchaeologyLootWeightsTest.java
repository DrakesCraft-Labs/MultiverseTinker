package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.archaeology.ArchaeologyLootTable;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the {@code rarity-weights} section of {@code config.yml}: the numbers a server edits there must
 * be the numbers the archaeology drop table actually rolls with, instead of the built-in weights.
 *
 * <p>The roll is random, so the tests pin the weights down to a single rarity wherever the outcome has to
 * be asserted: with only one rarity weighing anything, every extraction must return that rarity. That
 * keeps them deterministic instead of statistical.</p>
 */
class ArchaeologyLootWeightsTest {

    private ServerMock server;
    private MultiverseTinker plugin;
    private ArchaeologyLootTable lootTable;
    private MaterialRegistry materials;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        lootTable = plugin.getArchaeologyManager().getLootTable();
        materials = plugin.getMaterialRegistry();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("config.yml documents the weights and the table rolls with exactly those numbers")
    void shippedWeightsDriveTheTable() {
        YamlConfiguration config = loadResource("config.yml");

        for (MaterialRarity rarity : MaterialRarity.values()) {
            String key = ArchaeologyLootTable.CONFIG_PREFIX + ArchaeologyLootTable.configKey(rarity);
            assertTrue(config.isInt(key), key + " must be an integer in config.yml");
            assertEquals(config.getInt(key), lootTable.weightOf(rarity),
                    key + " must be the weight the drop table is using");
        }

        assertEquals(50, lootTable.weightOf(MaterialRarity.COMMON));
        assertEquals(1, lootTable.weightOf(MaterialRarity.LEGENDARY));
        assertEquals(100, lootTable.totalWeight(), "The shipped weights add up to 100");
        assertTrue(lootTable.usesDefaultWeights(), "An untouched config must leave the shipped weights in place");
    }

    @Test
    @DisplayName("Changing a weight changes which mineral the stone hands out")
    void configuredWeightDecidesTheDrop() {
        MineralOrigin origin = originTeaching(MaterialRarity.LEGENDARY);
        assertTrue(materials.getByOrigin(origin).size() > 1, "The dimension needs a real pool to roll in");

        // Only legendaries weigh anything, so every single extraction must be a legendary.
        weigh(Map.of(MaterialRarity.LEGENDARY, 1));
        assertFalse(lootTable.usesDefaultWeights(), "The configured weights must be the ones in use");
        for (int roll = 0; roll < 50; roll++) {
            TinkerMaterial found = lootTable.rollMineral(origin);
            assertNotNull(found);
            assertEquals(MaterialRarity.LEGENDARY, found.getRarity(),
                    "Only legendaries carry weight, so nothing else may come out");
        }

        // And back to the shallow end of the table.
        weigh(Map.of(MaterialRarity.COMMON, 1));
        for (int roll = 0; roll < 50; roll++) {
            assertEquals(MaterialRarity.COMMON, lootTable.rollMineral(origin).getRarity());
        }

        // Weighing two rarities leaves exactly those two in the pool, and nothing else sneaks in.
        weigh(Map.of(MaterialRarity.COMMON, 1, MaterialRarity.LEGENDARY, 1));
        assertTrue(lootTable.weightOf(MaterialRarity.COMMON) > 0);
        assertTrue(lootTable.weightOf(MaterialRarity.LEGENDARY) > 0);
        for (MaterialRarity rarity : MaterialRarity.values()) {
            if (rarity == MaterialRarity.COMMON || rarity == MaterialRarity.LEGENDARY) continue;
            assertEquals(0, lootTable.weightOf(rarity), rarity + " must be off the table");
        }
        for (int roll = 0; roll < 100; roll++) {
            MaterialRarity rarity = lootTable.rollMineral(origin).getRarity();
            assertTrue(rarity == MaterialRarity.COMMON || rarity == MaterialRarity.LEGENDARY,
                    "Only the weighted rarities may drop, got " + rarity);
        }
    }

    @Test
    @DisplayName("A rarity at 0 never drops, while the others keep their weight")
    void aZeroWeightRemovesARarity() {
        MineralOrigin origin = originTeaching(MaterialRarity.LEGENDARY);

        // The dimension does teach legendaries, so the check below is not vacuously true.
        assertTrue(materials.getByOrigin(origin).stream()
                        .anyMatch(material -> material.getRarity() == MaterialRarity.LEGENDARY),
                "The chosen dimension must actually hold legendary minerals");

        weigh(Map.of(MaterialRarity.COMMON, 50, MaterialRarity.UNCOMMON, 30,
                MaterialRarity.RARE, 14, MaterialRarity.EPIC, 5, MaterialRarity.LEGENDARY, 0));
        assertEquals(0, lootTable.weightOf(MaterialRarity.LEGENDARY), "A zero weight must be kept as zero");
        for (int roll = 0; roll < 300; roll++) {
            assertNotEquals(MaterialRarity.LEGENDARY, lootTable.rollMineral(origin).getRarity(),
                    "A rarity at 0 must never be handed out");
        }

        // Taking the whole shallow end off the table leaves the legendaries to carry it alone.
        weigh(Map.of(MaterialRarity.LEGENDARY, 5));
        for (int roll = 0; roll < 50; roll++) {
            assertEquals(MaterialRarity.LEGENDARY, lootTable.rollMineral(origin).getRarity());
        }
    }

    @Test
    @DisplayName("An all-zero section falls back to the shipped weights instead of a one-mineral table")
    void allZeroFallsBackToTheShippedWeights() {
        MineralOrigin origin = originTeaching(MaterialRarity.COMMON);

        weigh(Map.of());
        assertTrue(lootTable.usesDefaultWeights(), "Zeroing everything must restore the shipped weights");
        assertEquals(100, lootTable.totalWeight());
        assertEquals(50, lootTable.weightOf(MaterialRarity.COMMON));

        // And the table still hands out minerals from the dimension instead of failing shut.
        TinkerMaterial found = lootTable.rollMineral(origin);
        assertNotNull(found);
        assertEquals(origin, found.getOrigin());
    }

    @Test
    @DisplayName("Negative and non-numeric weights are absorbed without breaking the table")
    void malformedWeightsAreAbsorbed() {
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "common", -5);
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "uncommon", 7);
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "rare", "lots");
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "epic", 0);
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "legendary", 0);
        plugin.applyLootSettings();

        assertEquals(0, lootTable.weightOf(MaterialRarity.COMMON), "A negative weight is read as 0");
        assertEquals(7, lootTable.weightOf(MaterialRarity.UNCOMMON), "A configured weight is used as written");
        assertEquals(MaterialRarity.RARE.getDefaultWeight(), lootTable.weightOf(MaterialRarity.RARE),
                "A non-numeric weight keeps the shipped value");
        assertEquals(0, lootTable.weightOf(MaterialRarity.EPIC));

        // Uncommon carries 7 and rare fell back to its shipped 14, so those two are the whole table.
        MineralOrigin origin = originTeaching(MaterialRarity.UNCOMMON);
        for (int roll = 0; roll < 60; roll++) {
            MaterialRarity rarity = lootTable.rollMineral(origin).getRarity();
            assertTrue(rarity == MaterialRarity.UNCOMMON || rarity == MaterialRarity.RARE,
                    "A rarity whose weight ended at 0 must never drop, got " + rarity);
        }
    }

    @Test
    @DisplayName("The prospector brush still doubles exactly the rare, epic and legendary weights")
    void prospectorDoublesOnlyTheRareRarities() {
        weigh(Map.of(MaterialRarity.COMMON, 4, MaterialRarity.UNCOMMON, 4,
                MaterialRarity.RARE, 4, MaterialRarity.EPIC, 4, MaterialRarity.LEGENDARY, 4));

        for (MaterialRarity rarity : MaterialRarity.values()) {
            int plain = lootTable.weightOf(rarity, false);
            int prospecting = lootTable.weightOf(rarity, true);
            if (rarity == MaterialRarity.RARE || rarity == MaterialRarity.EPIC || rarity == MaterialRarity.LEGENDARY) {
                assertEquals(plain * ArchaeologyLootTable.PROSPECTOR_MULTIPLIER, prospecting,
                        rarity + " must be rolled twice as often while prospecting");
            } else {
                assertEquals(plain, prospecting, rarity + " must be left alone while prospecting");
            }
        }
    }

    @Test
    @DisplayName("/mvtink reload retunes the drop table without a restart")
    void reloadRetunesTheTable() {
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "legendary", 3);
        plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + "common", 0);
        plugin.saveConfig();
        assertEquals(1, lootTable.weightOf(MaterialRarity.LEGENDARY),
                "The edited file is not applied until the reload runs");
        assertEquals(50, lootTable.weightOf(MaterialRarity.COMMON));

        assertTrue(operator.performCommand("mvtink reload"));
        assertEquals(3, lootTable.weightOf(MaterialRarity.LEGENDARY),
                "The reload must re-read rarity-weights");
        assertEquals(0, lootTable.weightOf(MaterialRarity.COMMON), "The rest of the section comes along");
        assertEquals(14, lootTable.weightOf(MaterialRarity.RARE), "Untouched keys keep their value");

        // And the weights a server can see without reading the code: /mvtink verify reports them.
        drain(operator);
        assertTrue(operator.performCommand("mvtink verify"));
        List<String> report = drain(operator);
        assertTrue(report.stream().anyMatch(line -> line.contains("Archaeology rarity weights: common 0")
                        && line.contains("legendary 3")),
                "verify must report the weights in use, got: " + report);
        assertTrue(report.stream().anyMatch(line -> line.contains("(configured)")),
                "verify must say the weights are a server choice, got: " + report);
    }

    // ==========================================
    // HELPERS
    // ==========================================

    /** Writes {@code rarity-weights} with only the given rarities weighing anything, then applies it. */
    private void weigh(Map<MaterialRarity, Integer> weights) {
        EnumMap<MaterialRarity, Integer> read = new EnumMap<>(MaterialRarity.class);
        read.putAll(weights);
        for (MaterialRarity rarity : MaterialRarity.values()) {
            plugin.getConfig().set(ArchaeologyLootTable.CONFIG_PREFIX + ArchaeologyLootTable.configKey(rarity),
                    read.getOrDefault(rarity, 0));
        }
        plugin.applyLootSettings();
    }

    /** First dimension whose pool contains a mineral of that rarity. */
    private MineralOrigin originTeaching(MaterialRarity rarity) {
        for (MineralOrigin origin : MineralOrigin.values()) {
            for (TinkerMaterial material : materials.getByOrigin(origin)) {
                if (material.getRarity() == rarity) return origin;
            }
        }
        throw new AssertionError("No dimension teaches a " + rarity + " mineral, so the weights cannot be tested");
    }

    /** Reads and clears every chat message the player received. */
    private List<String> drain(PlayerMock player) {
        List<String> messages = new ArrayList<>();
        net.kyori.adventure.text.Component message;
        while ((message = player.nextComponentMessage()) != null) {
            messages.add(net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText()
                    .serialize(message));
        }
        return messages;
    }

    private YamlConfiguration loadResource(String name) {
        try (java.io.InputStream stream = getClass().getClassLoader().getResourceAsStream(name)) {
            assertNotNull(stream, name + " must be packaged in the jar");
            return YamlConfiguration.loadConfiguration(new java.io.InputStreamReader(stream,
                    java.nio.charset.StandardCharsets.UTF_8));
        } catch (java.io.IOException exception) {
            throw new java.io.UncheckedIOException(exception);
        }
    }
}
