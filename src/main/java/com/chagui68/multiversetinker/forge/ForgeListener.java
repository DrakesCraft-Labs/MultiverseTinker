package com.chagui68.multiversetinker.forge;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.forge.gui.AlloyCodexGUI;
import com.chagui68.multiversetinker.forge.gui.ForgeGUI;
import com.chagui68.multiversetinker.forge.structure.ForgeStructure;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public class ForgeListener implements Listener {

    private final MultiverseTinker plugin;
    private final ForgeManager forgeManager;
    private final TinkerItemRegistry itemRegistry;
    private final MaterialRegistry materialRegistry;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ForgeListener(@Nonnull MultiverseTinker plugin,
                         @Nonnull ForgeManager forgeManager,
                         @Nonnull TinkerItemRegistry itemRegistry,
                         @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.forgeManager = forgeManager;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onAnvilInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material mat = block.getType();
        if (mat != Material.ANVIL && mat != Material.CHIPPED_ANVIL && mat != Material.DAMAGED_ANVIL) {
            return;
        }

        Player player = event.getPlayer();

        // 1. If already recognized as active forge anvil
        if (forgeManager.isForge(block.getLocation())) {
            event.setCancelled(true);
            ForgeGUI gui = new ForgeGUI(plugin, itemRegistry, materialRegistry);
            player.openInventory(gui.getInventory());
            return;
        }

        // 2. Not yet activated: validate multiblock structure
        ForgeStructure.ValidationResult res = forgeManager.checkForge(block.getLocation());
        if (res.isValid()) {
            event.setCancelled(true);
            forgeManager.activateForge(block.getLocation(), player);
            ForgeGUI gui = new ForgeGUI(plugin, itemRegistry, materialRegistry);
            player.openInventory(gui.getInventory());
            return;
        }

        // 3. Incomplete: If player is sneaking, provide helpful feedback
        if (player.isSneaking()) {
            event.setCancelled(true);
            player.sendMessage(miniMessage.deserialize(
                    "<gradient:#ffaa00:#ff5500><b>Multiverse Forge:</b></gradient> "
                            + "<gray>Incomplete structure (<yellow>" + res.matchedBlocks() + "/" + res.totalBlocks()
                            + "</yellow> blocks matched, <gold>" + String.format(java.util.Locale.US, "%.1f", res.percentage())
                            + "%</gold>). Requires 4 corner Lava columns, Chiseled Tuff Bricks, Deepslate Tiles, and Tuff Brick Slabs/Stairs.</gray>"
            ));
        }
        // Otherwise, allow standard vanilla anvil interaction
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnvilBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material mat = block.getType();
        if (mat == Material.ANVIL || mat == Material.CHIPPED_ANVIL || mat == Material.DAMAGED_ANVIL) {
            if (forgeManager.isForge(block.getLocation())) {
                forgeManager.deactivateForge(block.getLocation());
                Player player = event.getPlayer();
                player.sendMessage(miniMessage.deserialize("<red>⚠ Multiverse Forge deactivated: central anvil was broken.</red>"));
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof ForgeGUI gui) {
            gui.handleClick(event, (Player) event.getWhoClicked());
            return;
        }
        if (event.getInventory().getHolder() instanceof AlloyCodexGUI codex) {
            event.setCancelled(true);
            codex.handleClick((Player) event.getWhoClicked(), event.getRawSlot());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof ForgeGUI gui) {
            gui.handleClose(event);
        }
    }
}
