package com.chagui68.multiversetinker.commands;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
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
 * Covers the {@code /mvtink} command surface: the subcommands it exposes, the ones it deliberately
 * does not (the mineral catalog belongs to the codex), and the suggestions that keep every material
 * reachable through tab completion.
 */
class MultiverseTinkerCommandTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private ServerMock server;
    private MultiverseTinker plugin;
    private PlayerMock admin;
    private PluginCommand command;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        admin = server.addPlayer("archivist");
        admin.setOp(true);
        command = plugin.getCommand("mvtink");
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The command exists as /mvtink with no alias")
    void commandHasNoAliases() {
        assertNotNull(command, "/mvtink must be registered");
        assertEquals("mvtink", command.getName());
        assertTrue(command.getAliases().isEmpty(), "Expected no aliases, got " + command.getAliases());
        assertTrue(admin.performCommand("mvtink verify"), "/mvtink must execute");
    }

    @Test
    @DisplayName("The codex is open to every player, while the other subcommands stay admin-only")
    void codexIsPublic() {
        PlayerMock guest = server.addPlayer("visitor");
        assertFalse(guest.isOp(), "The player must not be an operator");

        drainMessages(guest);
        assertTrue(guest.performCommand("mvtink codex"), "Any player must be able to open the codex");
        assertNotNull(guest.getOpenInventory(), "The codex inventory must actually open");

        // Everything else still refuses, and points the player at the codex.
        drainMessages(guest);
        assertTrue(guest.performCommand("mvtink verify"));
        List<String> refused = drainMessages(guest);
        assertTrue(refused.stream().anyMatch(line -> line.contains("do not have permission")),
                "Admin subcommands must refuse a plain player, got: " + refused);
        assertTrue(refused.stream().anyMatch(line -> line.contains("codex")),
                "The refusal must point at the public codex, got: " + refused);

        // And a plain player may not aim it at somebody else.
        drainMessages(guest);
        assertTrue(guest.performCommand("mvtink codex archivist"));
        assertTrue(drainMessages(guest).stream().anyMatch(line -> line.contains("Only admins")),
                "Opening the codex for another player must stay admin-only");

        TabCompleter completer = command.getTabCompleter();
        assertNotNull(completer);
        List<String> guestSuggestions = completer.onTabComplete(guest, command, "mvtink", new String[]{""});
        assertNotNull(guestSuggestions);
        assertEquals(List.of("codex"), guestSuggestions,
                "A plain player is only offered the codex, got: " + guestSuggestions);

        // No arguments: the public help tells them where the codex is.
        drainMessages(guest);
        assertTrue(guest.performCommand("mvtink"));
        assertTrue(drainMessages(guest).stream().anyMatch(line -> line.contains("/mvtink codex")),
                "The public help must name the codex");
    }

    @Test
    @DisplayName("A server can grant one slice of the administration instead of the whole command surface")
    void aSingleAdministrativeNodeGrantsJustThatSubcommand() {
        PlayerMock host = server.addPlayer("host");
        assertFalse(host.isOp(), "The slice must reach a plain player");
        host.addAttachment(plugin, AccessControl.AdminCommand.GIVE.permission(), true);

        // Exactly the granted subcommand runs: no umbrella, no operator flag.
        drainMessages(host);
        assertTrue(host.performCommand("mvtink give archivist mvtink_tin_ingot 2"));
        List<String> granted = drainMessages(host);
        assertFalse(granted.stream().anyMatch(line -> line.contains("do not have permission")),
                "The granted slice must not be refused, got: " + granted);
        assertTrue(granted.stream().anyMatch(line -> line.contains("Gave 2x mvtink_tin_ingot")),
                "The granted slice must actually run, got: " + granted);

        // Every other slice still refuses, and does nothing.
        for (String invocation : List.of("craft weapon SWORD gold iron diamond", "forge build 0", "verify", "reload")) {
            drainMessages(host);
            assertTrue(host.performCommand("mvtink " + invocation));
            List<String> refused = drainMessages(host);
            assertTrue(refused.stream().anyMatch(line -> line.contains("do not have permission to execute this command")),
                    "/mvtink " + invocation + " must be refused, got: " + refused);
        }

        // The suggestions say the same thing: the granted subcommand, plus the codex everyone may open.
        TabCompleter completer = command.getTabCompleter();
        assertNotNull(completer);
        assertEquals(List.of("give", "codex"),
                completer.onTabComplete(host, command, "mvtink", new String[]{""}),
                "Only the granted slice and the public codex may be suggested");
        List<String> giveArgs = completer.onTabComplete(host, command, "mvtink",
                new String[]{"give", "archivist", ""});
        assertNotNull(giveArgs);
        assertTrue(giveArgs.contains("mvtink_tin_ingot"),
                "A granted subcommand must still complete its own arguments, got " + giveArgs.size() + " ids");
        assertEquals(List.of(),
                completer.onTabComplete(host, command, "mvtink", new String[]{"reload", ""}),
                "A subcommand the sender may not run must suggest nothing");

        // And the help only advertises what this player may run.
        drainMessages(host);
        assertTrue(host.performCommand("mvtink"));
        List<String> help = drainMessages(host);
        assertTrue(help.stream().anyMatch(line -> line.contains("/mvtink give ")),
                "The help must show the granted slice, got: " + help);
        assertFalse(help.stream().anyMatch(line -> line.contains("/mvtink reload")),
                "The help must not advertise a subcommand this player cannot run, got: " + help);

        // A permissions plugin can grant the umbrella alone to a plain player: they keep getting all
        // five subcommands, which is what a server that has always handed it out relies on.
        PlayerMock delegate = server.addPlayer("delegate");
        assertFalse(delegate.isOp());
        delegate.addAttachment(plugin, AccessControl.AdminCommand.UMBRELLA, true);

        drainMessages(delegate);
        assertTrue(delegate.performCommand("mvtink reload"));
        assertTrue(drainMessages(delegate).stream().anyMatch(line -> line.contains("reloaded successfully")),
                "An umbrella holder must run every slice, reload included");
        assertEquals(List.of("craft", "give", "forge", "codex", "verify", "reload"),
                completer.onTabComplete(delegate, command, "mvtink", new String[]{""}),
                "An umbrella holder is offered the whole command surface");

        // The operator holds the umbrella by default, so the whole surface is untouched for them too.
        assertTrue(AccessControl.allows(admin, AccessControl.AdminCommand.CRAFT));
        assertTrue(AccessControl.allows(admin, AccessControl.Surface.ADMIN_COMMANDS));
    }

    @Test
    @DisplayName("The catalog subcommand is gone and the help points at the codex instead")
    void catalogLivesInTheCodex() {
        TabCompleter completer = command.getTabCompleter();
        assertNotNull(completer);

        List<String> subcommands = completer.onTabComplete(admin, command, "mvtink", new String[]{""});
        assertNotNull(subcommands);
        assertFalse(subcommands.contains("list"),
                "The catalog lives in the codex, so 'list' must never be suggested again");
        assertTrue(subcommands.contains("codex"), "The codex is the way in");

        // An unknown subcommand still answers with the help, so old habits only get a pointer.
        drainMessages();
        assertTrue(admin.performCommand("mvtink list 2"));
        List<String> messages = drainMessages();
        assertFalse(messages.isEmpty(), "The command must say something");
        assertTrue(messages.stream().anyMatch(line -> line.contains("codex")),
                "The help must point at the codex, got: " + messages);
    }

    @Test
    @DisplayName("give suggestions hand out the whole registry at once, kinds included")
    void giveSuggestionsStayComplete() {
        TabCompleter completer = command.getTabCompleter();
        assertNotNull(completer);

        List<String> base = completer.onTabComplete(admin, command, "mvtink", new String[]{"give", "archivist", ""});
        assertNotNull(base);
        int registered = plugin.getItemRegistry().getAvailableItemIdCount();
        assertTrue(registered > 2000, "The registry must hold thousands of ids, got " + registered);
        assertEquals(registered, base.size(),
                "Every registered id must be offered without having to type a material id first");
        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            assertTrue(base.contains(material.getId().toLowerCase()),
                    material.getId() + " is missing from the suggestions");
        }
        assertTrue(base.contains("mvtink_smeltery"));
        assertTrue(base.contains("mvtink_brush_prospector"));
        assertTrue(base.contains("mvtink_tin_ingot"), "Item kinds must show up in the first popup");
        assertTrue(base.contains("mvtink_tin_head"));
        assertTrue(base.contains("mvtink_tin_pommel"));

        // The list is stable, so it never reshuffles while the player keeps typing.
        assertEquals(base, completer.onTabComplete(admin, command, "mvtink",
                new String[]{"give", "archivist", ""}));

        // Typing part of an id narrows that same complete list…
        List<String> kinds = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"give", "archivist", "mvtink_tin"});
        assertNotNull(kinds);
        assertTrue(kinds.contains("mvtink_tin_ingot"), "The material's kinds must follow its id");
        assertTrue(kinds.contains("mvtink_tin_raw"));
        assertTrue(kinds.stream().allMatch(id -> id.startsWith("mvtink_tin")),
                "Suggestions must stay filtered by what was typed, got " + kinds);

        // …and the mvtink_ prefix stays optional, exactly like in the command itself.
        List<String> barePrefix = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"give", "archivist", "tin"});
        assertNotNull(barePrefix);
        assertTrue(barePrefix.contains("mvtink_tin"), "Typing 'tin' must find mvtink_tin, got " + barePrefix);
        assertTrue(barePrefix.contains("mvtink_tin_ingot"));
    }

    @Test
    @DisplayName("craft suggestions offer every material and every id that names one")
    void craftSuggestionsCoverEveryMaterial() {
        TabCompleter completer = command.getTabCompleter();

        List<String> materials = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "SWORD", ""});
        assertNotNull(materials);
        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            assertTrue(materials.contains(material.getId().toLowerCase()),
                    material.getId() + " must be suggested, got " + materials.size() + " entries");
        }
        assertTrue(materials.contains("mvtink_cobalt"), "Materials must be suggested");
        assertTrue(materials.contains("mvtink_tin_ingot"),
                "An id copied out of the codex must be usable in craft too");
        assertTrue(materials.contains("mvtink_tin_head"));
        assertFalse(materials.contains("mvtink_smeltery"), "The smeltery is not a forgeable material");
        assertFalse(materials.contains("mvtink_cast_head"), "Casts are not forgeable materials");
        assertFalse(materials.contains("NETHERITE"), "The tier does not belong in a material slot");

        // Typing a bare material name still filters the full list.
        List<String> cobalt = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "SWORD", "cobalt"});
        assertNotNull(cobalt);
        assertTrue(cobalt.contains("mvtink_cobalt"), "Cobalt must be found by its bare name, got " + cobalt);
        assertTrue(cobalt.contains("mvtink_cobalt_raw"));
        assertTrue(cobalt.stream().allMatch(id -> id.startsWith("mvtink_cobalt")),
                "Bare-name matching must stay filtered, got " + cobalt);

        List<String> tiers = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "SWORD", "gold", "ruby", "diamond", ""});
        assertNotNull(tiers);
        assertTrue(tiers.contains("NETHERITE"), "The tier slot must suggest tiers, got " + tiers);

        // A two-part weapon takes two materials, so its tier slot arrives one argument earlier and
        // must not be shadowed by the material list.
        List<String> bowTier = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "BOW", "gold", "ruby", ""});
        assertNotNull(bowTier);
        assertTrue(bowTier.contains("NETHERITE"), "A bow takes its tier at the 6th argument, got " + bowTier);
        assertFalse(bowTier.contains("mvtink_cobalt"), "The tier slot must not offer materials");
    }

    @Test
    @DisplayName("craft accepts the item-id form of a material and refuses everything else")
    void craftResolvesItemIdsBackToTheirMaterial() {
        drainMessages();
        assertTrue(admin.performCommand("mvtink craft weapon BOW mvtink_tin_ingot copper_block"));
        List<String> forged = drainMessages();
        assertTrue(forged.stream().anyMatch(line -> line.contains("Admin Crafted")),
                "tin_ingot and copper_block must resolve to Tin and Copper, got: " + forged);
        assertNotNull(admin.getInventory().getItem(0), "The bow must land in the inventory");

        drainMessages();
        assertTrue(admin.performCommand("mvtink craft weapon BOW mvtink_smeltery mvtink_cast_head"));
        assertTrue(drainMessages().stream().anyMatch(line -> line.contains("Unrecognized material")),
                "Non-material ids must still be refused");
    }

    @Test
    @DisplayName("give forges and registers the alloy a crucible pair id names")
    void giveForgesACruciblePairOnDemand() {
        PlayerMock smith = server.addPlayer("smith");
        String pairId = "mvtink_alloy_tin_zinc";
        assertNull(plugin.getMaterialRegistry().get(pairId), "The pair must not exist before the command");

        drainMessages();
        assertTrue(admin.performCommand("mvtink give smith " + pairId + " 4"));
        List<String> messages = drainMessages();

        assertTrue(messages.stream().anyMatch(line -> line.contains("forged and registered")),
                "The admin must be told an alloy was created, got: " + messages);
        assertTrue(messages.stream().anyMatch(line -> line.contains("Tin-Zinc Alloy")), "got: " + messages);
        assertTrue(messages.stream().anyMatch(line -> line.contains("Gave 4x " + pairId)), "got: " + messages);

        assertNotNull(plugin.getMaterialRegistry().get(pairId),
                "The forged alloy must be a material the rest of the plugin can use");
        assertEquals(1, plugin.getAlloyRegistry().getDynamicAlloyCount(),
                "The alloy is player-forged, so it has to be saved");
        // A bare material id hands out its raw ore, exactly like every other material.
        assertEquals(4, countHeld(smith, pairId + "_raw"),
                "The raw ore of the new alloy must reach the player");
    }

    @Test
    @DisplayName("give resolves the kind inside a pair id, so an ingot of an unblended pair works too")
    void giveResolvesTheKindInsideAPairId() {
        PlayerMock smith = server.addPlayer("smith");

        drainMessages();
        assertTrue(admin.performCommand("mvtink give smith mvtink_alloy_tin_zinc_ingot"));

        assertNotNull(plugin.getMaterialRegistry().get("mvtink_alloy_tin_zinc"),
                "Asking for the ingot must forge the alloy just the same");
        assertEquals(1, countHeld(smith, "mvtink_alloy_tin_zinc_ingot"));
    }

    @Test
    @DisplayName("give names the alloy a curated pair really forges instead of inventing a second one")
    void giveResolvesACuratedPairToItsRecipe() {
        PlayerMock smith = server.addPlayer("smith");

        drainMessages();
        assertTrue(admin.performCommand("mvtink give smith mvtink_alloy_copper_tin"));
        List<String> messages = drainMessages();

        assertTrue(messages.stream().anyMatch(line -> line.contains("Bronze")),
                "Copper and tin forge Bronze, and the admin must hear it, got: " + messages);
        assertTrue(messages.stream().anyMatch(line -> line.contains("would produce")),
                "The message must say the crucible produces it, got: " + messages);
        assertNull(plugin.getMaterialRegistry().get("mvtink_alloy_copper_tin"),
                "A curated pair must not spawn a second alloy for the same minerals");
        assertEquals(0, plugin.getAlloyRegistry().getDynamicAlloyCount());
        assertNotNull(findHeld(smith, "mvtink_bronze_raw"), "Bronze must be what lands in the inventory");
    }

    @Test
    @DisplayName("give still refuses an id that names no pair the crucible would accept")
    void giveRefusesIdsThatNameNoPair() {
        PlayerMock smith = server.addPlayer("smith");

        for (String id : List.of(
                "mvtink_alloy_tin_tin",              // a mineral cannot blend with itself
                "mvtink_alloy_tin_unobtainium",      // an unknown parent
                "mvtink_alloy_zinc_tin",             // not the canonical spelling the crucible produces
                "mvtink_alloy_catalyst_nether_star_tin", // a catalyst only joins a prime fusion
                "mvtink_alloy_bronze_tin",           // a finished alloy only enters through a prime
                "mvtink_prime_tin_zinc")) {          // no legendary parent, so it is no prime
            drainMessages();
            assertTrue(admin.performCommand("mvtink give smith " + id));
            assertTrue(drainMessages().stream().anyMatch(line -> line.contains("Item not found")),
                    id + " must stay unknown, so a typo cannot smuggle an alloy in");
        }

        assertEquals(0, plugin.getAlloyRegistry().getDynamicAlloyCount(),
                "A refused id must never forge or register anything");
        assertNull(findHeld(smith, "mvtink_alloy_tin_tin_raw"));
    }

    /** How much of one registry id the player is carrying. */
    private int countHeld(PlayerMock player, String itemId) {
        int held = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == org.bukkit.Material.AIR || !item.hasItemMeta()) continue;
            String id = item.getItemMeta().getPersistentDataContainer()
                    .get(TinkerKeys.ITEM_ID, PersistentDataType.STRING);
            if (itemId.equals(id)) held += item.getAmount();
        }
        return held;
    }

    /** The stack the player holds under one registry id, or {@code null}. */
    private ItemStack findHeld(PlayerMock player, String itemId) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == org.bukkit.Material.AIR || !item.hasItemMeta()) continue;
            String id = item.getItemMeta().getPersistentDataContainer()
                    .get(TinkerKeys.ITEM_ID, PersistentDataType.STRING);
            if (itemId.equals(id)) return item;
        }
        return null;
    }

    /** Reads and clears every message the player received. */
    private List<String> drainMessages() {
        return drainMessages(admin);
    }

    private List<String> drainMessages(PlayerMock player) {
        List<String> messages = new ArrayList<>();
        Component message;
        while ((message = player.nextComponentMessage()) != null) {
            messages.add(PLAIN.serialize(message));
        }
        return messages;
    }
}
