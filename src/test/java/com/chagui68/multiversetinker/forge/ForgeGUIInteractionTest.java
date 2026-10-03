package com.chagui68.multiversetinker.forge;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.forge.gui.ForgeGUI;
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
 * The Forge GUI's workshop rules: results are never overwritten, the live previews describe what a
 * section will produce before anything is spent, and every item a player put in comes back.
 */
class ForgeGUIInteractionTest {

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
    @DisplayName("The crucible previews the alloy, its pedigree and its signature art before it is lit")
    void crucibleFusionPreview() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_ALLOY);
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT1, ingot("mvtink_copper"));
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT2, ingot("mvtink_tin"));
        refresh(13);

        String preview = text(gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_PREVIEW));
        assertTrue(preview.contains("Bronze"), preview);
        assertTrue(preview.contains("Bell of the First Age"), "The legendary art must be named: " + preview);
        assertTrue(preview.contains("Tier III · Legendary"), preview);
        assertNull(gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_OUTPUT), "Previewing must not smelt anything");
    }

    @Test
    @DisplayName("Lighting the crucible never overwrites an alloy still waiting in the output")
    void crucibleNeverOverwritesItsOutput() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_ALLOY);
        ItemStack waiting = new ItemStack(Material.DIAMOND, 3);
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_OUTPUT, waiting);
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT1, ingot("mvtink_copper"));
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT2, ingot("mvtink_tin"));

        smith.simulateInventoryClick(ForgeGUI.SLOT_ALLOY_SMELT);

        assertEquals(Material.DIAMOND, gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_OUTPUT).getType(),
                "The waiting item must still be there");
        assertNotNull(gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_MAT1), "Inputs must not be spent on a refused smelt");
        assertNotNull(gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_MAT2), "Inputs must not be spent on a refused smelt");

        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_OUTPUT, null);
        smith.simulateInventoryClick(ForgeGUI.SLOT_ALLOY_SMELT);
        ItemStack bronze = gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_OUTPUT);
        assertNotNull(bronze, "With the output free the crucible must smelt");
        assertEquals(2, bronze.getAmount());
    }

    @Test
    @DisplayName("The part preview lists what is missing, then the composition once everything is in place")
    void partPreview() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_PARTS);
        refresh(19);
        String empty = text(gui.getInventory().getItem(ForgeGUI.SLOT_PART_PREVIEW));
        assertTrue(empty.contains("Missing"), empty);

        gui.getInventory().setItem(ForgeGUI.SLOT_PART_CAST, plugin.getItemRegistry().getCastItem(CastType.HEAD));
        gui.getInventory().setItem(ForgeGUI.SLOT_PART_MAT1, ingot("mvtink_bronze"));
        gui.getInventory().setItem(ForgeGUI.SLOT_PART_MAT2, ingot("mvtink_tin"));
        gui.getInventory().setItem(ForgeGUI.SLOT_PART_MAT3, ingot("mvtink_tin"));
        refresh(19);

        String ready = text(gui.getInventory().getItem(ForgeGUI.SLOT_PART_PREVIEW));
        assertTrue(ready.contains("Ready"), ready);
        assertTrue(ready.contains("Bell of the First Age"), "A bronze part awakens the bronze art: " + ready);
    }

    @Test
    @DisplayName("Every item a player put in the Forge comes back when it closes, glass panes included")
    void itemsComeBackOnClose() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_NAV_ALLOY);
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT1, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 5));
        gui.getInventory().setItem(ForgeGUI.SLOT_ALLOY_MAT2, ingot("mvtink_tin"));

        smith.closeInventory();

        assertTrue(smith.getInventory().contains(Material.LIME_STAINED_GLASS_PANE, 5),
                "A player's own glass pane is theirs and must be returned");
        assertNull(gui.getInventory().getItem(ForgeGUI.SLOT_ALLOY_MAT2));
    }

    @Test
    @DisplayName("The Forge's own icons cannot be taken")
    void systemIconsCannotBeTaken() {
        smith.simulateInventoryClick(ForgeGUI.SLOT_PEDIGREE_GUIDE);
        assertNotNull(gui.getInventory().getItem(ForgeGUI.SLOT_PEDIGREE_GUIDE));
        assertTrue(smith.getItemOnCursor() == null || smith.getItemOnCursor().getType() == Material.AIR);

        String guide = text(gui.getInventory().getItem(ForgeGUI.SLOT_PEDIGREE_GUIDE));
        assertTrue(guide.contains("Tier V · Mythic"), "The footer lists the whole pedigree ladder: " + guide);
    }

    // ==========================================
    // HELPERS
    // ==========================================

    private ItemStack ingot(String id) {
        ItemStack ingot = plugin.getItemRegistry().getIngotItem(id);
        assertNotNull(ingot, id + " must have an ingot");
        return ingot.clone();
    }

    /** One click on a decorative slot, then the tick the previews are redrawn on. */
    private void refresh(int decorativeSlot) {
        smith.simulateInventoryClick(decorativeSlot);
        server.getScheduler().performTicks(1);
    }

    private static String text(ItemStack item) {
        assertNotNull(item);
        StringBuilder out = new StringBuilder(PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName()));
        List<Component> lore = item.getItemMeta().lore();
        if (lore != null) {
            out.append(' ').append(lore.stream().map(PlainTextComponentSerializer.plainText()::serialize)
                    .collect(Collectors.joining(" ")));
        }
        return out.toString().replaceAll("\\s+", " ");
    }
}
