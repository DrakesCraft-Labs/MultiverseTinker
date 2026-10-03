package com.chagui68.multiversetinker.compat;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The calls that differ between Paper 1.21.11, 26.1 and 26.2. CI runs this class on all three, both
 * on the 1.21.11 bytecode and recompiled, so a removed API surfaces here before it reaches a server.
 */
class ServerCompatTest {

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
    @DisplayName("exactChoice accepts the exact item and nothing that merely shares its material")
    void exactChoiceMatchesOnlyTheExactItem() {
        ItemStack ingot = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = ingot.getItemMeta();
        meta.displayName(Component.text("Forged ingot"));
        ingot.setItemMeta(meta);

        RecipeChoice choice = ServerCompat.exactChoice(ingot);

        assertInstanceOf(RecipeChoice.ExactChoice.class, choice);
        assertTrue(choice.test(ingot.clone()), "The very item must satisfy its own choice");
        assertFalse(choice.test(new ItemStack(Material.IRON_INGOT)), "A vanilla ingot must not pass for a forged one");
    }

    @Test
    @DisplayName("Every material's nugget, ingot and block recipes are built with exact choices")
    void compressionRecipesUseExactChoices() {
        int checked = 0;
        for (TinkerMaterial material : plugin.getMaterialRegistry().getAll()) {
            String id = material.getId();
            if (plugin.getItemRegistry().getNuggetItem(id) == null || plugin.getItemRegistry().getIngotItem(id) == null) {
                continue;
            }
            Recipe toIngot = server.getRecipe(new NamespacedKey(plugin, id + "_nuggets_to_ingot"));
            Recipe toNuggets = server.getRecipe(new NamespacedKey(plugin, id + "_ingot_to_nuggets"));
            assertInstanceOf(ShapedRecipe.class, toIngot, id + " must compress nuggets into an ingot");
            assertInstanceOf(ShapelessRecipe.class, toNuggets, id + " must split an ingot into nuggets");
            assertInstanceOf(RecipeChoice.ExactChoice.class, ((ShapedRecipe) toIngot).getChoiceMap().get('N'));
            assertInstanceOf(RecipeChoice.ExactChoice.class, ((ShapelessRecipe) toNuggets).getChoiceList().get(0));
            checked++;
        }
        assertTrue(checked > 0, "At least one material must have nugget and ingot recipes");
    }

    @Test
    @DisplayName("maxHealth follows the max-health attribute, modifiers included")
    void maxHealthReadsTheAttribute() {
        PlayerMock player = server.addPlayer();
        assertEquals(20.0, ServerCompat.maxHealth(player), 1.0E-9);

        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30.0);
        assertEquals(30.0, ServerCompat.maxHealth(player), 1.0E-9);
    }
}
