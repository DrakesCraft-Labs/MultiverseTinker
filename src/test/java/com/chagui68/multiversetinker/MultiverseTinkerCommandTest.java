package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
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
