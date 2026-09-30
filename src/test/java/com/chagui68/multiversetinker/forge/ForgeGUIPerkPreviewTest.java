package com.chagui68.multiversetinker.forge;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.forge.gui.ForgeGUI;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the perk preview as the Forge GUI actually serves it: a player fills the assembly slots and
 * reads the perk before clicking the anvil.
 *
 * <p>The parts are put into the slots directly rather than through simulated drags, and the refresh is
 * triggered by a click on a slot that cannot move anything — which is exactly what a placement click
 * does to the preview, minus MockBukkit's item-move simulation. What is under test is the wiring: a
 * click refreshes the preview one tick later, and the tab it lands in decides whether there is one.</p>
 */
class ForgeGUIPerkPreviewTest {

    private ServerMock server;
    private MultiverseTinker plugin;
    private PlayerMock smith;
    private ForgeGUI gui;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MultiverseTinker.class);
        smith = server.addPlayer("smith");

        gui = new ForgeGUI(plugin, plugin.getItemRegistry(), plugin.getMaterialRegistry());
        smith.openInventory(gui.getInventory());
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("The weapon tab prints the perk of the parts in its slots before they are spent")
    void weaponTabPreviewsThePerk() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_WEAPONS);

        place(ForgeGUI.SLOT_WEAPON_PART1, "mvtink_borax");
        place(ForgeGUI.SLOT_WEAPON_PART2, "mvtink_amethyst");
        place(ForgeGUI.SLOT_WEAPON_PART3, "mvtink_gold");
        refresh();

        String preview = preview();
        assertTrue(preview.contains("Fluxforged Resonant Auric Sweeping Cleave"),
                "The preview must name the build the three parts would forge, got: " + preview);
        assertTrue(preview.contains("Compound epithet"), "The words each part lends must be spelled out");
        // Each part is listed with the word it contributes, next to the mineral that contributed it.
        assertTrue(preview.contains("Fluxforged (" + plugin.getMaterialRegistry().get("mvtink_borax").getName() + ")"),
                "The head's own word must be attributed to the mineral that lends it: " + preview);
        assertTrue(preview.contains("3/3 parts"), "A complete assembly must say so: " + preview);
        assertTrue(preview.contains("Sweeping Cleave") && preview.contains("Modular Broadsword"),
                "The mechanic of the chosen weapon type must be named");
    }

    @Test
    @DisplayName("The preview grows part by part and names what is still missing")
    void previewGrowsWithTheParts() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_WEAPONS);

        place(ForgeGUI.SLOT_WEAPON_PART1, "mvtink_borax");
        refresh();
        String one = preview();
        assertTrue(one.contains("1/3 parts"), one);
        assertTrue(one.contains("Fluxforged"), one);
        assertTrue(one.contains("Handle / Hilt"), "The preview must name the part still missing: " + one);

        place(ForgeGUI.SLOT_WEAPON_PART2, "mvtink_amethyst");
        refresh();
        String two = preview();
        assertTrue(two.contains("2/3 parts"), two);
        assertTrue(two.contains("Fluxforged Resonant"), "The name must grow with the parts: " + two);
        assertFalse(two.contains("Auric"), "A part that has not been placed cannot name the build: " + two);

        place(ForgeGUI.SLOT_WEAPON_PART3, "mvtink_gold");
        refresh();
        assertTrue(preview().contains("Fluxforged Resonant Auric"), preview());
    }

    @Test
    @DisplayName("Cycling the equipment type updates the mechanic the preview promises")
    void cyclingTheTypeUpdatesTheMechanic() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_WEAPONS);
        assertTrue(preview().contains("Sweeping Cleave"), "A sword is selected to begin with");

        // The selector cycles to the next weapon type, which is the bow.
        smith.simulateInventoryClick(ForgeGUI.SLOT_WEAPON_NEXT);
        String preview = preview();
        assertTrue(preview.contains("Infused Volley"), "A bow must promise its own mechanic: " + preview);
        assertTrue(preview.contains("0/2 parts"), "A two-part weapon takes two parts, not three: " + preview);
    }

    @Test
    @DisplayName("The tool and armor tabs print their own previews")
    void everyAssemblyTabHasItsOwnPreview() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_TOOLS);
        place(ForgeGUI.SLOT_TOOL_HEAD, "mvtink_borax");
        refresh();
        assertTrue(preview().contains("Vein Resonance"), preview());

        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_ARMOR);
        final String armor = preview();
        assertTrue(armor.contains("Cranium Ward"), "The armor tab must preview its own slot mechanic: " + armor);
        assertFalse(armor.contains("Vein Resonance"), "A tool's mechanic must not follow the player into armor");
    }

    @Test
    @DisplayName("Tabs that assemble nothing carry no perk preview")
    void nonAssemblyTabsCarryNoPreview() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_WEAPONS);
        refresh();
        assertNotNull(gui.getInventory().getItem(ForgeGUI.SLOT_PERK_PREVIEW), "The weapon tab previews");

        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_ALLOY);
        ItemStack stale = gui.getInventory().getItem(ForgeGUI.SLOT_PERK_PREVIEW);
        assertTrue(stale == null || stale.getType().name().endsWith("_GLASS_PANE"),
                "The preview belongs to the assembly tabs, got " + (stale == null ? "null" : stale.getType()));
    }

    @Test
    @DisplayName("The preview is the Forge's own item, so a player cannot walk off with it")
    void thePreviewCannotBeTaken() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_WEAPONS);
        place(ForgeGUI.SLOT_WEAPON_PART1, "mvtink_borax");
        refresh();

        ItemStack before = gui.getInventory().getItem(ForgeGUI.SLOT_PERK_PREVIEW);
        assertNotNull(before);
        smith.simulateInventoryClick(ForgeGUI.SLOT_PERK_PREVIEW);

        assertEquals(Material.NAME_TAG, gui.getInventory().getItem(ForgeGUI.SLOT_PERK_PREVIEW).getType(),
                "Clicking the preview must not remove it");
        assertTrue(smith.getItemOnCursor() == null || smith.getItemOnCursor().getType() == Material.AIR,
                "The preview must not end up on the player's cursor");
    }

    // ==========================================
    // HELPERS
    // ==========================================

    /** Puts a forged part into an assembly slot, which is what the player does by hand. */
    private void place(int slot, String materialId) {
        TinkerMaterial material = plugin.getMaterialRegistry().get(materialId);
        assertNotNull(material, materialId + " must be a registered material");
        gui.getInventory().setItem(slot, TinkerItemBuilder.createModularPart(ToolPartType.HEAD,
                PartComposition.fromMaterials(List.of(material))));
    }

    /**
     * Asks for a refresh the way a placement does: one click in the inventory, then the tick the preview
     * is drawn on. The clicked slot is a part label, which cannot move anything.
     */
    private void refresh() {
        smith.simulateInventoryClick(20);
        server.getScheduler().performTicks(1);
    }

    /** The preview tooltip as one line, display name included. */
    private String preview() {
        ItemStack item = gui.getInventory().getItem(ForgeGUI.SLOT_PERK_PREVIEW);
        assertNotNull(item, "The preview slot must not be empty while an assembly tab is open");
        assertNotEquals(Material.GRAY_STAINED_GLASS_PANE, item.getType(), "The preview must be drawn, not left as backing");

        StringBuilder text = new StringBuilder(PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName()));
        List<Component> lore = item.getItemMeta().lore();
        if (lore != null) {
            text.append(' ').append(lore.stream()
                    .map(PlainTextComponentSerializer.plainText()::serialize)
                    .collect(Collectors.joining(" ")));
        }
        return text.toString().replaceAll("\\s+", " ").trim();
    }
}
