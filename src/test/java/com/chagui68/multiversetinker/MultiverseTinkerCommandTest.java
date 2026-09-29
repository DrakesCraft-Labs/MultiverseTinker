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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the {@code /mvtink} listing and its suggestions: every registered material has to stay
 * visible, whether it is read through the paged chat listing or discovered through tab completion.
 */
class MultiverseTinkerCommandTest {

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
    private static final Pattern MATERIAL_ID = Pattern.compile("\\((mvtink_[a-z0-9_]+)\\)");

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
    @DisplayName("The paged listing walks through every registered material exactly once")
    void listingShowsEveryMaterial() {
        List<TinkerMaterial> all = new ArrayList<>(plugin.getMaterialRegistry().getAll());
        Set<String> listed = new LinkedHashSet<>();

        int pages = (int) Math.ceil(all.size() / 20.0);
        for (int page = 1; page <= pages; page++) {
            admin.nextComponentMessage();
            drainMessages();
            assertTrue(admin.performCommand("mvtink list " + page), "Page " + page + " must run");

            for (String message : drainMessages()) {
                Matcher matcher = MATERIAL_ID.matcher(message);
                if (matcher.find()) listed.add(matcher.group(1));
            }
        }

        assertEquals(all.size(), listed.size(),
                "Every registered material must appear in the listing, missing: "
                        + all.stream().map(TinkerMaterial::getId).filter(id -> !listed.contains(id)).toList());
        assertTrue(listed.contains("mvtink_singularite"), "Alloys belong to the listing too");
    }

    @Test
    @DisplayName("A page beyond the end is clamped instead of showing nothing")
    void listingClampsThePage() {
        drainMessages();
        assertTrue(admin.performCommand("mvtink list 999"));

        List<String> messages = drainMessages();
        assertFalse(messages.isEmpty(), "The listing must still answer on an out-of-range page");
        assertTrue(messages.get(0).contains("page"), "The header must report the page it settled on");
        assertTrue(messages.stream().anyMatch(line -> line.startsWith("• ")),
                "The last page must still show materials");
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
