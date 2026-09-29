package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.access.AccessControl.Mode;
import com.chagui68.multiversetinker.access.AccessControl.Surface;
import com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI;
import com.chagui68.multiversetinker.forge.gui.ForgeGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.command.TabCompleter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the access rules of the plugin: which parts of it (codex, forge, archaeology, admin commands)
 * a server can open or close from {@code config.yml} alone, without depending on a permissions plugin.
 *
 * <p>Each surface has three modes — {@code public}, {@code op} and {@code permission} — so the tests
 * check all three for a plain player, an operator and a granted node, and then that the rules actually
 * reach the command, the anvil, the crucible and the brush.</p>
 */
class AccessControlTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private ServerMock server;
    private MultiverseTinker plugin;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
    }

    @AfterEach
    void tearDown() {
        AccessControl.reset();
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The shipped config opens the public surfaces and keeps the admin commands behind the node")
    void shippedDefaultsReproduceTheHistoricalBehaviour() {
        YamlConfiguration config = loadResource("config.yml");

        for (Surface surface : Surface.values()) {
            assertTrue(config.isString(surface.configKey()), surface.configKey() + " must be documented");
            assertTrue(config.isString(surface.messageKey()), surface.messageKey() + " must be documented");
            assertEquals(Mode.parse(config.getString(surface.configKey()), surface.defaultMode()),
                    AccessControl.mode(surface),
                    surface.configKey() + " must be the mode the plugin is actually running with");
        }

        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.CODEX));
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.FORGE));
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.ARCHAEOLOGY));
        assertEquals(Mode.PERMISSION, AccessControl.mode(Surface.ADMIN_COMMANDS),
                "Administrative commands must stay behind the permission node");

        // plugin.yml has to document the node every surface falls back to, forge included.
        YamlConfiguration descriptor = loadResource("plugin.yml");
        for (Surface surface : Surface.values()) {
            assertNotNull(descriptor.getString("permissions." + surface.permission() + ".description"),
                    surface.permission() + " must be declared in plugin.yml");
        }
    }

    @Test
    @DisplayName("public, op and permission each resolve for a plain player, an operator and a granted node")
    void modesDecideWhoMayUseASurface() {
        PlayerMock guest = server.addPlayer("visitor");
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        // public: nobody needs anything, node or operator.
        setEverySurface("public");
        for (Surface surface : Surface.values()) {
            assertTrue(AccessControl.allows(guest, surface), surface + " must be open to everyone");
            assertTrue(AccessControl.isPublic(surface));
        }

        // op: only operators, without involving any permission node.
        setEverySurface("op");
        for (Surface surface : Surface.values()) {
            assertFalse(AccessControl.allows(guest, surface), surface + " must refuse a plain player");
            assertTrue(AccessControl.allows(operator, surface), surface + " must allow an operator");
        }

        // permission: the node decides, so this mode is for servers that mean to narrow a surface
        // further. The codex, forge and archaeology nodes are declared for everyone in plugin.yml, the
        // admin node is not, and a permissions plugin can grant it to anyone it likes.
        setEverySurface("permission");
        assertTrue(AccessControl.allows(guest, Surface.CODEX), "The codex node defaults to everyone");
        assertTrue(AccessControl.allows(guest, Surface.FORGE), "The forge node defaults to everyone");
        assertTrue(AccessControl.allows(guest, Surface.ARCHAEOLOGY), "The archaeology node defaults to everyone");
        assertFalse(AccessControl.allows(guest, Surface.ADMIN_COMMANDS),
                "The admin node defaults to operators only");

        guest.addAttachment(plugin, Surface.ADMIN_COMMANDS.permission(), true);
        assertTrue(AccessControl.allows(guest, Surface.ADMIN_COMMANDS),
                "A permissions plugin must be able to grant the node");

        // The console is above all of this: it owns plugin.yml.
        assertTrue(AccessControl.allows(server.getConsoleSender(), Surface.ADMIN_COMMANDS));
    }

    @Test
    @DisplayName("A mode is read forgivingly and a typo falls back instead of locking the server out")
    void modesAreParsedForgivingly() {
        assertEquals(Mode.PUBLIC, Mode.parse("PUBLIC", Mode.OP));
        assertEquals(Mode.PUBLIC, Mode.parse(" everyone ", Mode.OP));
        assertEquals(Mode.OP, Mode.parse("ops", Mode.PUBLIC));
        assertEquals(Mode.OP, Mode.parse("Admin", Mode.PUBLIC));
        assertEquals(Mode.PERMISSION, Mode.parse("permission", Mode.OP));
        assertEquals(Mode.PERMISSION, Mode.parse("node", Mode.OP));
        assertEquals(Mode.OP, Mode.parse("banana", Mode.OP), "An unknown word keeps the fallback");
        assertEquals(Mode.PUBLIC, Mode.parse(null, Mode.PUBLIC));

        plugin.getConfig().set("access.forge", "flase");
        plugin.applyAccessSettings();
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.FORGE),
                "A typo must leave the previous rule in place, never deny everyone");

        AccessControl.reset();
        for (Surface surface : Surface.values()) {
            assertEquals(surface.defaultMode(), AccessControl.mode(surface), "reset() must restore the defaults");
        }
    }

    @Test
    @DisplayName("Each refusal comes from config.yml, so a server words it in its own language")
    void refusalsComeFromConfig() {
        assertEquals("You do not have permission to use the Forge and the Alloy Crucible.",
                plain(AccessControl.denial(Surface.FORGE)));
        assertEquals("You do not have permission to open the Alloy Codex.",
                plain(AccessControl.denial(Surface.CODEX)));
        assertEquals("You do not have permission to perform geological archaeology.",
                plain(AccessControl.denial(Surface.ARCHAEOLOGY)));

        plugin.getConfig().set("messages.access-denied.forge", "<red>La forja es solo para herreros.</red>");
        plugin.applyAccessSettings();
        assertEquals("La forja es solo para herreros.", plain(AccessControl.denial(Surface.FORGE)));
        assertEquals("You do not have permission to open the Alloy Codex.", plain(AccessControl.denial(Surface.CODEX)),
                "Rewording one surface must not touch the others");
    }

    @Test
    @DisplayName("Closing the codex in config.yml shuts the command without a permissions plugin")
    void codexAccessIsEnforcedByTheCommand() {
        PlayerMock guest = server.addPlayer("visitor");
        assertFalse(guest.isOp());

        // Shipped default: every player opens the codex.
        assertTrue(guest.performCommand("mvtink codex"));
        assertTrue(isCodexOpen(guest), "The codex must open for everyone by default");
        guest.closeInventory();

        plugin.getConfig().set("access.codex", "op");
        plugin.applyAccessSettings();

        drain(guest);
        assertTrue(guest.performCommand("mvtink codex"));
        assertFalse(isCodexOpen(guest), "An op-only codex must not open for a plain player");
        assertTrue(drain(guest).stream().anyMatch(line -> line.contains("do not have permission to open the Alloy Codex")),
                "The refusal must come from the configured message");

        // And a refusal must not advertise the codex to someone who may not open it.
        assertTrue(guest.performCommand("mvtink verify"));
        List<String> refused = drain(guest);
        assertTrue(refused.stream().anyMatch(line -> line.contains("do not have permission")));
        assertFalse(refused.stream().anyMatch(line -> line.contains("codex")),
                "A player barred from the codex must not be pointed at it, got: " + refused);

        // Nothing is suggested to them either, while an operator keeps the whole command surface.
        TabCompleter completer = plugin.getCommand("mvtink").getTabCompleter();
        assertNotNull(completer);
        assertEquals(List.of(),
                completer.onTabComplete(guest, plugin.getCommand("mvtink"), "mvtink", new String[]{""}),
                "A player allowed nothing must be offered nothing");

        // The bare command must not promise a codex this player cannot open.
        assertTrue(guest.performCommand("mvtink"));
        List<String> help = drain(guest);
        assertTrue(help.stream().anyMatch(line -> line.contains("nothing here for you")),
                "A closed-off player must be told, got: " + help);
        assertFalse(help.stream().anyMatch(line -> line.contains("/mvtink codex")),
                "The help must not advertise the closed codex, got: " + help);

        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);
        assertTrue(operator.performCommand("mvtink codex"), "An operator must still get in");
        assertTrue(isCodexOpen(operator), "The codex must open for an operator");
        operator.closeInventory();

        // Reopening the codex brings the suggestion back, without a restart.
        plugin.getConfig().set("access.codex", "public");
        plugin.applyAccessSettings();
        assertEquals(List.of("codex"),
                completer.onTabComplete(guest, plugin.getCommand("mvtink"), "mvtink", new String[]{""}));
    }

    @Test
    @DisplayName("Closing the forge in config.yml stops the anvil, the crucible and the brush where they are")
    void forgeAndArchaeologyAccessIsEnforcedInGame() {
        World world = server.addSimpleWorld("access_world");
        Location anvilLocation = new Location(world, 10, 65, 10);
        plugin.getForgeManager().buildStructure(anvilLocation, 0);
        anvilLocation.getBlock().setType(Material.ANVIL);
        assertTrue(plugin.getForgeManager().isForge(anvilLocation), "The built forge must be active");

        PlayerMock guest = server.addPlayer("visitor");
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        // Public by default: the anvil opens the forge for anyone holding nothing particularly special.
        rightClick(guest, anvilLocation.getBlock());
        assertTrue(isForgeOpen(guest), "The forge must open for everyone by default");
        guest.closeInventory();

        // op: the guest is turned away at the anvil, the operator still gets in.
        plugin.getConfig().set("access.forge", "op");
        plugin.applyAccessSettings();

        drainActionBars(guest);
        rightClick(guest, anvilLocation.getBlock());
        assertFalse(isForgeOpen(guest), "An op-only forge must not open for a plain player");
        assertTrue(plain(guest.nextActionBar()).contains("do not have permission to use the Forge"),
                "The refusal must be the configured forge message");
        rightClick(operator, anvilLocation.getBlock());
        assertTrue(isForgeOpen(operator), "An operator must still open the forge");
        operator.closeInventory();

        // The crucible follows the same rule.
        Block crucible = world.getBlockAt(20, 65, 20);
        crucible.setType(Material.BLAST_FURNACE);
        plugin.getSmelteryManager().registerSmeltery(crucible.getLocation());
        rightClick(guest, crucible);
        assertFalse(isSmelteryOpen(guest), "An op-only crucible must refuse a plain player");
        rightClick(operator, crucible);
        assertTrue(isSmelteryOpen(operator));
        operator.closeInventory();

        // The brush follows access.archaeology, and a granted node must not override an op-only rule.
        Block stone = world.getBlockAt(0, 64, 0);
        stone.setType(Material.STONE);
        Block secondStone = world.getBlockAt(1, 64, 0);
        secondStone.setType(Material.STONE);
        ItemStack brush = plugin.getItemRegistry().getProspectorBrush();

        plugin.getConfig().set("access.archaeology", "op");
        plugin.applyAccessSettings();
        guest.addAttachment(plugin, Surface.ARCHAEOLOGY.permission(), true);

        drainActionBars(guest);
        plugin.getArchaeologyManager().handleBrushing(guest, secondStone, BlockFace.UP, brush);
        assertTrue(plain(guest.nextActionBar()).contains("do not have permission to perform geological archaeology"),
                "An op-only surface must refuse even a granted node");

        plugin.getConfig().set("access.archaeology", "public");
        plugin.applyAccessSettings();
        drainActionBars(guest);
        plugin.getArchaeologyManager().handleBrushing(guest, stone, BlockFace.UP, brush);
        Component started = guest.nextActionBar();
        assertNotNull(started, "A public brush must be able to start a session");
        assertFalse(plain(started).contains("do not have permission"), "Got: " + plain(started));
    }

    // ==========================================
    // HELPERS
    // ==========================================

    private void setEverySurface(String mode) {
        for (Surface surface : Surface.values()) {
            plugin.getConfig().set(surface.configKey(), mode);
        }
        plugin.applyAccessSettings();
    }

    private void rightClick(PlayerMock player, Block block) {
        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,
                player.getInventory().getItemInMainHand(), block, BlockFace.UP, EquipmentSlot.HAND);
        server.getPluginManager().callEvent(event);
    }

    private boolean isCodexOpen(PlayerMock player) {
        return isOpen(player, AlloyCodexGUI.class);
    }

    private boolean isForgeOpen(PlayerMock player) {
        return isOpen(player, ForgeGUI.class);
    }

    private boolean isSmelteryOpen(PlayerMock player) {
        return isOpen(player, com.chagui68.multiversetinker.smeltery.SmelteryGUI.class);
    }

    /** True while the player is looking at an inventory of that holder, null-safe for a closed one. */
    private boolean isOpen(PlayerMock player, Class<?> holder) {
        InventoryView view = player.getOpenInventory();
        if (view == null) return false;
        Inventory top = view.getTopInventory();
        return top != null && holder.isInstance(top.getHolder());
    }

    /** Forgets whatever action bars earlier steps queued, so the next one is the one under test. */
    private void drainActionBars(PlayerMock player) {
        while (player.nextActionBar() != null) {
            // discard
        }
    }

    private String plain(Component component) {
        assertNotNull(component, "Expected a message");
        return PLAIN.serialize(component);
    }

    private List<String> drain(PlayerMock player) {
        List<String> messages = new ArrayList<>();
        Component message;
        while ((message = player.nextComponentMessage()) != null) {
            messages.add(PLAIN.serialize(message));
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
