package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.EquipmentAnimation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiverseTinkerPluginTest {

    private ServerMock server;
    private MultiverseTinker plugin;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("plugin.yml stays in sync with the command the plugin registers")
    void testPluginDescriptor() {
        YamlConfiguration descriptor = loadResource("plugin.yml");

        assertEquals("MultiverseTinker", descriptor.getString("name"));
        assertEquals("com.chagui68.multiversetinker.MultiverseTinker", descriptor.getString("main"));
        assertNotNull(descriptor.getString("version"), "The maven filter must fill the version");
        assertEquals("1.21", descriptor.getString("api-version"));

        // /mvtink is the only command name: no /mvt and no /multiversetinker alias may come back.
        assertNull(descriptor.getConfigurationSection("commands.multiversetinker"),
                "The long command name must be gone");
        assertNotNull(descriptor.getConfigurationSection("commands.mvtink"), "/mvtink must be declared");
        assertTrue(descriptor.getStringList("commands.mvtink.aliases").isEmpty(), "No aliases are allowed");
        assertNull(descriptor.getString("commands.mvtink.permission"),
                "The command must carry no permission, or /mvtink codex could not be public");
        assertNotNull(descriptor.getConfigurationSection("permissions"), "Permissions must stay documented");
        assertTrue(descriptor.getBoolean("permissions.multiversetinker.codex.default"),
                "The codex must be open to every player by default");
        assertEquals("op", descriptor.getString("permissions.multiversetinker.admin.default"),
                "Administrative access must stay restricted");

        String usage = descriptor.getString("commands.mvtink.usage");
        assertNotNull(usage);
        for (String sub : List.of("craft", "give", "forge", "codex", "verify", "reload")) {
            assertTrue(usage.contains(sub), "plugin.yml usage is missing /mvtink " + sub);
        }
        assertFalse(usage.contains("list"),
                "The catalog lives in the codex, so /mvtink list must not be advertised");
    }

    @Test
    @DisplayName("config.yml documents the lore wrapping options and the plugin applies them on enable")
    void testLoreConfiguration() {
        YamlConfiguration config = loadResource("config.yml");

        assertTrue(config.isBoolean(LoreWrap.CONFIG_ENABLED), LoreWrap.CONFIG_ENABLED + " must be a boolean");
        assertTrue(config.isInt(LoreWrap.CONFIG_MAX_PIXELS), LoreWrap.CONFIG_MAX_PIXELS + " must be an integer");
        assertTrue(config.isInt(LoreWrap.CONFIG_HEADER_PIXELS), LoreWrap.CONFIG_HEADER_PIXELS + " must be an integer");

        assertEquals(config.getBoolean(LoreWrap.CONFIG_ENABLED), LoreWrap.isEnabled());
        assertEquals(config.getInt(LoreWrap.CONFIG_MAX_PIXELS), LoreWrap.configuredMaxPixels());
        assertEquals(config.getInt(LoreWrap.CONFIG_HEADER_PIXELS), LoreWrap.configuredHeaderPixels());
    }

    @Test
    @DisplayName("config.yml documents the perk animations and the plugin applies them on enable")
    void testAnimationConfiguration() {
        YamlConfiguration config = loadResource("config.yml");

        assertTrue(config.isBoolean(EquipmentAnimation.CONFIG_ENABLED),
                EquipmentAnimation.CONFIG_ENABLED + " must be a boolean");
        assertTrue(config.isDouble(EquipmentAnimation.CONFIG_PARTICLE_SCALE),
                EquipmentAnimation.CONFIG_PARTICLE_SCALE + " must be a number");
        assertTrue(config.isBoolean(EquipmentAnimation.CONFIG_SOUNDS),
                EquipmentAnimation.CONFIG_SOUNDS + " must be a boolean");
        assertTrue(config.isInt(EquipmentAnimation.CONFIG_COOLDOWN_MILLIS),
                EquipmentAnimation.CONFIG_COOLDOWN_MILLIS + " must be an integer");

        assertEquals(config.getBoolean(EquipmentAnimation.CONFIG_ENABLED), EquipmentAnimation.isEnabled());
        assertEquals(config.getDouble(EquipmentAnimation.CONFIG_PARTICLE_SCALE), EquipmentAnimation.configuredParticleScale());
        assertEquals(config.getBoolean(EquipmentAnimation.CONFIG_SOUNDS), EquipmentAnimation.isSoundsEnabled());
        assertEquals(config.getInt(EquipmentAnimation.CONFIG_COOLDOWN_MILLIS), EquipmentAnimation.configuredCooldownMillis());
    }

    @Test
    @DisplayName("config.yml documents the modular equipment rules and the plugin applies them")
    void testEquipmentConfiguration() {
        YamlConfiguration config = loadResource("config.yml");

        assertTrue(config.isBoolean(TinkerItemBuilder.CONFIG_MODULAR_ATTACK_DAMAGE),
                TinkerItemBuilder.CONFIG_MODULAR_ATTACK_DAMAGE + " must be a boolean");
        assertEquals(config.getBoolean(TinkerItemBuilder.CONFIG_MODULAR_ATTACK_DAMAGE),
                TinkerItemBuilder.isModularAttackDamage());

        assertTrue(config.isBoolean(TinkerItemBuilder.CONFIG_MODULAR_ARMOR_DEFENSE),
                TinkerItemBuilder.CONFIG_MODULAR_ARMOR_DEFENSE + " must be a boolean");
        assertEquals(config.getBoolean(TinkerItemBuilder.CONFIG_MODULAR_ARMOR_DEFENSE),
                TinkerItemBuilder.isModularArmorDefense());
    }

    /** Reads a packaged resource such as plugin.yml or config.yml. */
    private YamlConfiguration loadResource(String name) {
        try (java.io.InputStream stream = getClass().getClassLoader().getResourceAsStream(name)) {
            assertNotNull(stream, name + " must be packaged in the jar");
            return YamlConfiguration.loadConfiguration(new java.io.InputStreamReader(stream,
                    java.nio.charset.StandardCharsets.UTF_8));
        } catch (java.io.IOException exception) {
            throw new java.io.UncheckedIOException(exception);
        }
    }

    @Test
    @DisplayName("Plugin must enable correctly with all registries and managers")
    void testPluginEnable() {
        assertNotNull(plugin);
        assertTrue(plugin.isEnabled());
        assertNotNull(plugin.getMaterialRegistry());
        assertNotNull(plugin.getItemRegistry());
        assertNotNull(plugin.getArchaeologyManager());
        assertNotNull(plugin.getSmelteryManager());
    }

    @Test
    @DisplayName("Generated items must contain PDC tags with mvtink_ prefix")
    void testItemPdcTags() {
        ItemStack rawTin = plugin.getItemRegistry().getRawItem("mvtink_tin");
        assertNotNull(rawTin);
        assertNotNull(rawTin.getItemMeta());

        String itemId = rawTin.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING);
        assertNotNull(itemId);
        assertTrue(itemId.startsWith("mvtink_"));
        assertEquals("mvtink_tin_raw", itemId);

        ItemStack nuggetTin = plugin.getItemRegistry().getNuggetItem("mvtink_tin");
        assertNotNull(nuggetTin);
        assertEquals("mvtink_tin_nugget", nuggetTin.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING));

        ItemStack blockTin = plugin.getItemRegistry().getBlockItem("mvtink_tin");
        assertNotNull(blockTin);
        assertEquals("mvtink_tin_block", blockTin.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING));

        ItemStack moltenBucket = plugin.getItemRegistry().getMoltenBucketItem("mvtink_tin");
        assertNotNull(moltenBucket);
        assertEquals("mvtink_tin_molten_bucket", moltenBucket.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING));
        assertTrue(moltenBucket.getItemMeta().getPersistentDataContainer().has(TinkerKeys.IS_MOLTEN_BUCKET, PersistentDataType.BYTE));

        ItemStack brush = plugin.getItemRegistry().getProspectorBrush();
        assertNotNull(brush);
        String brushId = brush.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING);
        assertEquals("mvtink_brush_prospector", brushId);

        ItemStack smeltery = plugin.getItemRegistry().getSmelteryItem();
        assertNotNull(smeltery);
        assertEquals("mvtink_smeltery", smeltery.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING));

        ItemStack ingotCast = plugin.getItemRegistry().getCastItem(CastType.INGOT);
        assertNotNull(ingotCast);
        assertEquals("mvtink_cast_ingot", ingotCast.getItemMeta().getPersistentDataContainer().get(TinkerKeys.ITEM_ID, PersistentDataType.STRING));
    }

    @Test
    @DisplayName("Smeltery heat source detection (Lava & Magma Block) should work correctly")
    void testSmelteryHeatSourceDetection() {
        org.bukkit.World world = server.addSimpleWorld("test_world");
        Block smelteryBlock = world.getBlockAt(0, 65, 0);
        Block heatBlock = world.getBlockAt(0, 64, 0);

        smelteryBlock.setType(Material.BLAST_FURNACE);
        heatBlock.setType(Material.LAVA);

        assertTrue(plugin.getSmelteryManager().hasLavaBeneath(smelteryBlock));
        assertFalse(plugin.getSmelteryManager().hasMagmaBeneath(smelteryBlock));
        assertTrue(plugin.getSmelteryManager().hasHeatSourceBeneath(smelteryBlock));
        assertEquals(com.chagui68.multiversetinker.smeltery.HeatSource.LAVA, plugin.getSmelteryManager().getHeatSourceBeneath(smelteryBlock));

        heatBlock.setType(Material.MAGMA_BLOCK);
        assertFalse(plugin.getSmelteryManager().hasLavaBeneath(smelteryBlock));
        assertTrue(plugin.getSmelteryManager().hasMagmaBeneath(smelteryBlock));
        assertTrue(plugin.getSmelteryManager().hasHeatSourceBeneath(smelteryBlock));
        assertEquals(com.chagui68.multiversetinker.smeltery.HeatSource.MAGMA_BLOCK, plugin.getSmelteryManager().getHeatSourceBeneath(smelteryBlock));

        heatBlock.setType(Material.WATER);
        assertFalse(plugin.getSmelteryManager().hasLavaBeneath(smelteryBlock));
        assertFalse(plugin.getSmelteryManager().hasMagmaBeneath(smelteryBlock));
        assertFalse(plugin.getSmelteryManager().hasHeatSourceBeneath(smelteryBlock));
        assertEquals(com.chagui68.multiversetinker.smeltery.HeatSource.NONE, plugin.getSmelteryManager().getHeatSourceBeneath(smelteryBlock));
    }

    @Test
    @DisplayName("Command /mvtink give should distribute brush, smeltery, and materials")
    void testCommands() {
        Player player = server.addPlayer("Chagui68");
        player.setOp(true);

        boolean giveBrush = player.performCommand("mvtink give Chagui68 mvtink_brush_prospector 1");
        assertTrue(giveBrush);
        assertTrue(player.getInventory().contains(plugin.getItemRegistry().getProspectorBrush()));

        boolean giveSmeltery = player.performCommand("mvtink give Chagui68 mvtink_smeltery 1");
        assertTrue(giveSmeltery);

        boolean giveCobalt = player.performCommand("mvtink give Chagui68 mvtink_cobalt_raw 3");
        assertTrue(giveCobalt);

        boolean giveCast = player.performCommand("mvtink give Chagui68 mvtink_cast_ingot 1");
        assertTrue(giveCast);

        boolean giveBlockCast = player.performCommand("mvtink give Chagui68 mvtink_cast_block 1");
        assertTrue(giveBlockCast);
    }

    @Test
    @DisplayName("Every material must have raw, nugget, ingot, molten bucket, block, head, rod, and binding variants")
    void testAllItemStatesExist() {
        for (com.chagui68.multiversetinker.materials.TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            String id = material.getId();
            assertNotNull(plugin.getItemRegistry().getRawItem(id), "Raw missing for " + id);
            assertNotNull(plugin.getItemRegistry().getNuggetItem(id), "Nugget missing for " + id);
            assertNotNull(plugin.getItemRegistry().getIngotItem(id), "Ingot missing for " + id);
            assertNotNull(plugin.getItemRegistry().getMoltenBucketItem(id), "Molten bucket missing for " + id);
            assertNotNull(plugin.getItemRegistry().getBlockItem(id), "Block missing for " + id);
            assertNotNull(plugin.getItemRegistry().getToolHeadItem(id), "Tool Head missing for " + id);
            assertNotNull(plugin.getItemRegistry().getToolRodItem(id), "Tool Rod missing for " + id);
            assertNotNull(plugin.getItemRegistry().getToolBindingItem(id), "Tool Binding missing for " + id);
        }

        // Verify all 6 casts
        for (CastType castType : CastType.values()) {
            assertNotNull(plugin.getItemRegistry().getCastItem(castType), "Cast missing for " + castType);
        }
    }

    @Test
    @DisplayName("ForgeManager can build structure and validate it successfully")
    void testForgeValidationAndBuild() {
        assertNotNull(plugin.getForgeManager());
        org.bukkit.World world = server.addSimpleWorld("forge_world");
        org.bukkit.Location anvilLoc = new org.bukkit.Location(world, 10, 65, 10);

        plugin.getForgeManager().buildStructure(anvilLoc, 0);

        com.chagui68.multiversetinker.forge.structure.ForgeStructure.ValidationResult res =
                plugin.getForgeManager().checkForge(anvilLoc);

        assertTrue(res.isValid(), "Constructed Forge multiblock must be valid");
        assertTrue(res.percentage() >= 90.0, "Constructed Forge must match at least 90%");
        assertTrue(plugin.getForgeManager().isForge(anvilLoc), "Anvil must be recognized as active forge");
    }
}
