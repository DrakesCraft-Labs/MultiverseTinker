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
    @DisplayName("give suggestions offer every material, then that material's item kinds")
    void giveSuggestionsStayComplete() {
        TabCompleter completer = command.getTabCompleter();
        assertNotNull(completer);

        List<String> base = completer.onTabComplete(admin, command, "mvtink", new String[]{"give", "archivist", ""});
        assertNotNull(base);
        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            assertTrue(base.contains(material.getId().toLowerCase()),
                    material.getId() + " is missing from the suggestions");
        }
        assertTrue(base.contains("mvtink_smeltery"));
        assertTrue(base.contains("mvtink_brush_prospector"));

        List<String> kinds = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"give", "archivist", "mvtink_tin"});
        assertNotNull(kinds);
        assertTrue(kinds.contains("mvtink_tin_ingot"), "The material's kinds must follow its id");
        assertTrue(kinds.contains("mvtink_tin_raw"));
        assertTrue(kinds.contains("mvtink_tin_hammer".replace("hammer", "head")));
        assertTrue(kinds.stream().allMatch(id -> id.startsWith("mvtink_tin")),
                "Suggestions must stay filtered by what was typed, got " + kinds);
    }

    @Test
    @DisplayName("craft suggestions offer materials only, and tiers in the tier slot")
    void craftSuggestionsSeparateMaterialsFromTiers() {
        TabCompleter completer = command.getTabCompleter();

        List<String> materials = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "SWORD", ""});
        assertNotNull(materials);
        assertTrue(materials.contains("cobalt"), "Materials must be suggested, got " + materials.size() + " entries");
        assertFalse(materials.contains("NETHERITE"), "The tier does not belong in a material slot");

        List<String> tiers = completer.onTabComplete(admin, command, "mvtink",
                new String[]{"craft", "weapon", "SWORD", "gold", "ruby", "diamond", ""});
        assertNotNull(tiers);
        assertTrue(tiers.contains("NETHERITE"), "The tier slot must suggest tiers, got " + tiers);
    }

    /** Reads and clears every message the player received. */
    private List<String> drainMessages() {
        List<String> messages = new ArrayList<>();
        Component message;
        while ((message = admin.nextComponentMessage()) != null) {
            messages.add(PLAIN.serialize(message));
        }
        return messages;
    }
}
