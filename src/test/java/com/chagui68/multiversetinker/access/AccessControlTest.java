package com.chagui68.multiversetinker.access;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.access.AccessControl.Mode;
import com.chagui68.multiversetinker.access.AccessControl.Surface;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI;
import com.chagui68.multiversetinker.forge.gui.ForgeGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.command.TabCompleter;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the access rules of the plugin: which parts of it (codex, forge, archaeology) a server can open
 * or close from {@code config.yml} alone, without depending on a permissions plugin — and that the
 * administrative commands are <b>not</b> one of those parts, whatever a config says.
 *
 * <p>Each configurable surface has three modes — {@code public}, {@code op} and {@code permission} — so
 * the tests check all three for a plain player, an operator and a granted node, and then that the rules
 * actually reach the command, the anvil, the crucible and the brush.</p>
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
    @DisplayName("The shipped defaults open the public surfaces and keep the admin commands behind the node")
    void shippedDefaultsReproduceTheHistoricalBehaviour() {
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.CODEX));
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.FORGE));
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.ARCHAEOLOGY));
        assertEquals(Mode.PERMISSION, AccessControl.mode(Surface.ADMIN_COMMANDS),
                "Administrative commands must stay behind the permission node");
    }

    @Test
    @DisplayName("public, op and permission each resolve for a plain player, an operator and a granted node")
    void modesDecideWhoMayUseASurface() {
        PlayerMock guest = server.addPlayer("visitor");
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        // public: nobody needs anything, node or operator. The administrative surface is deliberately not
        // among the configurable ones, so it is never opened by this and is asserted on its own below.
        setEverySurface("public");
        for (Surface surface : configurableSurfaces()) {
            assertTrue(AccessControl.allows(guest, surface), surface + " must be open to everyone");
            assertTrue(AccessControl.isPublic(surface));
        }
        assertFalse(AccessControl.allows(guest, Surface.ADMIN_COMMANDS),
                "The administrative commands must survive even a config that opens everything");

        // op: only operators, without involving any permission node.
        setEverySurface("op");
        for (Surface surface : configurableSurfaces()) {
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
    @DisplayName("Closing the forge in config.yml stops the anvil and the crucible where they are")
    void forgeAccessIsEnforcedOnTheAnvilAndCrucible() {
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
    }

    @Test
    @DisplayName("The brush follows access.archaeology even when the permission node is granted")
    void archaeologyAccessIsEnforcedOnTheBrush() {
        World world = server.addSimpleWorld("brush_world");
        Block stone = world.getBlockAt(0, 64, 0);
        stone.setType(Material.STONE);

        PlayerMock guest = server.addPlayer("visitor");
        guest.getInventory().setItemInMainHand(new ItemStack(Material.BRUSH));

        // public: brushing a valid block starts a session.
        drainActionBars(guest);
        rightClick(guest, stone);
        Component started = guest.nextActionBar();
        assertNotNull(started, "A public brush must be able to start a session");
        assertFalse(plain(started).contains("do not have permission"), "Got: " + plain(started));

        // op: refused on the way in, even though the node itself was granted.
        plugin.getConfig().set("access.archaeology", "op");
        plugin.applyAccessSettings();
        guest.addAttachment(plugin, Surface.ARCHAEOLOGY.permission(), true);

        drainActionBars(guest);
        rightClick(guest, stone);
        assertTrue(plain(guest.nextActionBar()).contains("do not have permission to perform geological archaeology"),
                "An op-only surface must refuse even a granted node");
    }

    @Test
    @DisplayName("The casting cauldron follows access.forge and keeps the metal when it refuses")
    void castingAccessFollowsTheForgeRule() {
        World world = server.addSimpleWorld("casting_world");
        Block cauldron = world.getBlockAt(0, 64, 0);
        cauldron.setType(Material.WATER_CAULDRON);
        Levelled water = (Levelled) cauldron.getBlockData();
        water.setLevel(3);
        cauldron.setBlockData(water);

        PlayerMock guest = server.addPlayer("visitor");
        ItemStack molten = plugin.getItemRegistry().getMoltenBucketItem("mvtink_tin");
        assertNotNull(molten);
        guest.getInventory().setItemInMainHand(molten);
        guest.getInventory().setItemInOffHand(plugin.getItemRegistry().getCastItem(CastType.INGOT));

        // op-only: refused, and the water level proves nothing was cast.
        plugin.getConfig().set("access.forge", "op");
        plugin.applyAccessSettings();
        drainActionBars(guest);
        rightClick(guest, cauldron);
        assertTrue(plain(guest.nextActionBar()).contains("do not have permission to use the Forge"));
        assertEquals(3, ((Levelled) cauldron.getBlockData()).getLevel(),
                "A refused casting must not consume water or melt the cast");

        // public: the casting completes, so the water drops by one level and the ingot arrives.
        plugin.getConfig().set("access.forge", "public");
        plugin.applyAccessSettings();
        drainActionBars(guest);
        rightClick(guest, cauldron);
        assertEquals(2, ((Levelled) cauldron.getBlockData()).getLevel(),
                "A public casting must consume one water level");
        assertTrue(holdsItem(guest, "mvtink_tin_ingot"), "The cast ingot must land in the inventory");
    }

    @Test
    @DisplayName("The forge's book button honours access.codex, exactly like the command")
    void forgeBookButtonHonoursCodexAccess() {
        World world = server.addSimpleWorld("book_world");
        Location anvilLocation = new Location(world, 10, 65, 10);
        plugin.getForgeManager().buildStructure(anvilLocation, 0);
        anvilLocation.getBlock().setType(Material.ANVIL);

        PlayerMock guest = server.addPlayer("visitor");
        rightClick(guest, anvilLocation.getBlock());
        assertTrue(isForgeOpen(guest), "The forge must open before the book button can be reached");

        // The book lives in the Alloy tab.
        guest.simulateInventoryClick(ForgeGUI.SLOT_NAV_ALLOY);
        drainActionBars(guest);

        plugin.getConfig().set("access.codex", "op");
        plugin.applyAccessSettings();
        guest.simulateInventoryClick(ForgeGUI.SLOT_ALLOY_RECIPES);
        assertFalse(isCodexOpen(guest), "A closed codex must not open from the forge button");
        assertTrue(plain(guest.nextActionBar()).contains("do not have permission to open the Alloy Codex"));

        plugin.getConfig().set("access.codex", "public");
        plugin.applyAccessSettings();
        drainActionBars(guest);
        guest.simulateInventoryClick(ForgeGUI.SLOT_ALLOY_RECIPES);
        assertTrue(isCodexOpen(guest), "The book button must open the codex while the surface is public");
    }

    @Test
    @DisplayName("/mvtink reload applies a changed access rule without a restart")
    void reloadAppliesAccessChanges() {
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        plugin.getConfig().set("access.forge", "op");
        plugin.saveConfig();
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.FORGE),
                "Nothing is applied until the reload runs");

        drain(operator);
        assertTrue(operator.performCommand("mvtink reload"));
        assertEquals(Mode.OP, AccessControl.mode(Surface.FORGE), "The reload must re-read the access block");
        assertEquals(Mode.PUBLIC, AccessControl.mode(Surface.CODEX), "Untouched surfaces keep their value");
        assertTrue(drain(operator).stream().anyMatch(line -> line.contains("reloaded successfully")));
    }

    @Test
    @DisplayName("Of every /mvtink subcommand, the codex is the only one a plain player may run")
    void onlyTheCodexIsOpenToPlayers() {
        PlayerMock guest = server.addPlayer("visitor");
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);

        // The codex is the one command a player gets: the shipped config leaves it open, and it must
        // reach them without any node or operator flag.
        assertTrue(AccessControl.allows(guest, Surface.CODEX), "The codex is public by default");
        drain(guest);
        assertTrue(guest.performCommand("mvtink codex"));
        assertTrue(isCodexOpen(guest), "The codex must open for a plain player");
        guest.closeInventory();

        // Every administrative subcommand is refused, with the configured refusal and no side effect.
        // The arguments are deliberately well-formed, so a refusal can only come from the admin gate.
        List<String> invocations = List.of(
                "craft weapon SWORD gold iron diamond",
                "give visitor mvtink_tin_ingot",
                "forge build 0",
                "verify",
                "reload");

        for (String invocation : invocations) {
            drain(guest);
            assertTrue(guest.performCommand("mvtink " + invocation));
            List<String> messages = drain(guest);
            assertTrue(messages.stream().anyMatch(line -> line.contains("do not have permission to execute this command")),
                    "/mvtink " + invocation + " must be refused for a plain player, got: " + messages);
            assertTrue(messages.stream().noneMatch(line -> line.contains("reloaded successfully")
                            || line.contains("Admin Crafted") || line.contains("Gave ")
                            || line.contains("Every item id resolves")),
                    "/mvtink " + invocation + " must do nothing for a plain player, got: " + messages);
        }

        // An operator holds the node by default and runs the whole surface without ever being refused.
        for (String invocation : invocations) {
            drain(operator);
            operator.performCommand("mvtink " + invocation);
            List<String> messages = drain(operator);
            assertTrue(messages.stream().noneMatch(line -> line.contains("do not have permission")),
                    "An operator must not be refused /mvtink " + invocation + ", got: " + messages);
        }

        // And the suggestions say the same thing: one command for a player, the whole surface for an
        // operator, in the order the executor lists them.
        TabCompleter completer = plugin.getCommand("mvtink").getTabCompleter();
        assertNotNull(completer);
        assertEquals(List.of("codex"),
                completer.onTabComplete(guest, plugin.getCommand("mvtink"), "mvtink", new String[]{""}),
                "A plain player must be offered the codex and nothing else");
        assertEquals(List.of("craft", "give", "forge", "codex", "verify", "reload"),
                completer.onTabComplete(operator, plugin.getCommand("mvtink"), "mvtink", new String[]{""}),
                "An operator must be offered every subcommand");
    }

    @Test
    @DisplayName("A rule a server cannot satisfy is reported, and an operator is enough to satisfy it")
    void opOnlySurfacesAreReportedOnlyWhileNoOperatorExists() {
        // The shipped defaults are usable either way: nothing is op-only, so there is nothing to say.
        assertTrue(AccessControl.advisories(plugin.getConfig(), true).isEmpty());
        assertTrue(AccessControl.advisories(plugin.getConfig(), false).isEmpty());

        plugin.getConfig().set("access.forge", "op");
        plugin.applyAccessSettings();

        // With an operator around, an op-only surface is exactly what the server asked for.
        assertTrue(AccessControl.advisories(plugin.getConfig(), true).isEmpty(),
                "An operator satisfies an op-only surface");

        // Without one, nobody can ever reach it, so the rule is dead and says so.
        AccessControl.Advisory advisory = only(AccessControl.advisories(plugin.getConfig(), false));
        assertEquals(AccessControl.Severity.UNUSABLE, advisory.severity());
        assertEquals("access.forge", advisory.configKey(), "The report must point at the line to edit");
        assertTrue(advisory.message().contains("no operators"), "Got: " + advisory.message());
        assertTrue(advisory.message().contains("Forge"), "The report must say what was closed, got: " + advisory.message());
        assertTrue(advisory.message().contains("\"op\""), "The report must quote the value, got: " + advisory.message());
    }

    @Test
    @DisplayName("Closing the codex on a server with no operators says that no command is left either")
    void theCodexLockoutNamesTheOnlyPlayerCommand() {
        plugin.getConfig().set("access.codex", "op");
        plugin.applyAccessSettings();

        AccessControl.Advisory advisory = only(AccessControl.advisories(plugin.getConfig(), false));
        assertEquals("access.codex", advisory.configKey());
        assertTrue(advisory.message().contains("the only /mvtink command players have"),
                "A server must know it has closed every player command, got: " + advisory.message());
    }

    @Test
    @DisplayName("Only a leftover key that would open the admin commands is called dangerous")
    void aStaleAdminKeyIsDangerousOnlyWhileItWouldOpenTheCommands() {
        // The old shipped value: harmless, since nothing reads it any more.
        plugin.getConfig().set("access.admin-commands", "permission");
        plugin.applyAccessSettings();
        assertTrue(AccessControl.advisories(plugin.getConfig(), true).isEmpty(),
                "A stale key that changes nothing is not a warning");

        // The value that used to hand every player /mvtink give: worth saying plainly, synonyms included.
        for (String opening : List.of("public", "everyone", "all")) {
            plugin.getConfig().set("access.admin-commands", opening);
            plugin.applyAccessSettings();

            AccessControl.Advisory advisory = only(AccessControl.advisories(plugin.getConfig(), true));
            assertEquals(AccessControl.Severity.DANGEROUS, advisory.severity(), "for \"" + opening + "\"");
            assertEquals("access.admin-commands", advisory.configKey());
            assertTrue(advisory.message().contains(Surface.ADMIN_COMMANDS.permission()),
                    "The message must name the node that is actually required, got: " + advisory.message());
            assertEquals(Mode.PERMISSION, AccessControl.mode(Surface.ADMIN_COMMANDS),
                    "And the dangerous value must still not have been obeyed");
        }
    }

    @Test
    @DisplayName("Startup warns about a rule the server cannot use, and stays quiet once it can")
    void startupWarnsAboutRulesTheServerCannotUse() {
        plugin.getConfig().set("access.archaeology", "op");

        List<String> log = captureLog(plugin::applyAccessSettings);
        assertTrue(log.stream().anyMatch(line -> line.contains("UNUSABLE") && line.contains("access.archaeology")),
                "A dead rule must be warned about on enable and reload, got: " + log);

        // An operator satisfies the rule, so the warning disappears — and the report itself stays.
        PlayerMock operator = server.addPlayer("keeper");
        operator.setOp(true);
        List<String> quiet = captureLog(plugin::applyAccessSettings);
        assertFalse(quiet.stream().anyMatch(line -> line.contains("UNUSABLE")),
                "An operator makes the rule usable, got: " + quiet);
        assertTrue(quiet.stream().anyMatch(line -> line.contains("Access control —")),
                "The access report is logged either way, got: " + quiet);
    }

    @Test
    @DisplayName("Startup warns sharply about a leftover key that would open the admin commands")
    void startupWarnsAboutAStaleKeyThatWouldOpenTheCommands() {
        plugin.getConfig().set("access.admin-commands", "public");

        List<String> log = captureLog(plugin::applyAccessSettings);
        assertTrue(log.stream().anyMatch(line -> line.contains("DANGEROUS") && line.contains("access.admin-commands")),
                "Expected a dangerous-rule warning, got: " + log);
        assertEquals(1, log.stream().filter(line -> line.contains("access.admin-commands")).count(),
                "One key must not be reported twice, got: " + log);
    }

    // ==========================================
    // HELPERS
    // ==========================================

    /** The only advisory in a list, asserting there is exactly one. */
    private AccessControl.Advisory only(List<AccessControl.Advisory> advisories) {
        assertEquals(1, advisories.size(), "Expected exactly one advisory, got: " + advisories);
        return advisories.get(0);
    }

    /** Captures what the plugin logs while an action runs, so a startup warning can be asserted on. */
    private List<String> captureLog(Runnable action) {
        List<String> lines = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                lines.add(record.getLevel() + " " + record.getMessage());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }
        };
        handler.setLevel(Level.ALL);
        Logger logger = plugin.getLogger();
        Level previous = logger.getLevel();
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
        try {
            action.run();
        } finally {
            logger.removeHandler(handler);
            logger.setLevel(previous);
        }
        return lines;
    }

    /** The surfaces a server may rule on: everything but the administrative commands. */
    private static List<Surface> configurableSurfaces() {
        return Arrays.stream(Surface.values()).filter(Surface::isConfigurable).toList();
    }

    private void setEverySurface(String mode) {
        for (Surface surface : configurableSurfaces()) {
            plugin.getConfig().set(surface.configKey(), mode);
        }
        plugin.applyAccessSettings();
    }

    private void rightClick(PlayerMock player, Block block) {
        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,
                player.getInventory().getItemInMainHand(), block, BlockFace.UP, EquipmentSlot.HAND);
        server.getPluginManager().callEvent(event);
    }

    /** True when the player is carrying an item with that registry id. */
    private boolean holdsItem(PlayerMock player, String itemId) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || !item.hasItemMeta()) continue;
            String id = item.getItemMeta().getPersistentDataContainer()
                    .get(com.chagui68.multiversetinker.storage.TinkerKeys.ITEM_ID,
                            org.bukkit.persistence.PersistentDataType.STRING);
            if (itemId.equals(id)) return true;
        }
        return false;
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

}
