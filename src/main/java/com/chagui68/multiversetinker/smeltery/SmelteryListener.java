package com.chagui68.multiversetinker.smeltery;

import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class SmelteryListener implements Listener {

    private final SmelteryManager smelteryManager;
    private final TinkerItemRegistry itemRegistry;

    public SmelteryListener(@Nonnull SmelteryManager smelteryManager, @Nonnull TinkerItemRegistry itemRegistry) {
        this.smelteryManager = smelteryManager;
        this.itemRegistry = itemRegistry;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(TinkerKeys.IS_SMELTERY, PersistentDataType.BYTE)) {
            smelteryManager.registerSmeltery(event.getBlockPlaced().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (smelteryManager.isSmeltery(block)) {
            event.setDropItems(false);
            smelteryManager.unregisterSmeltery(block.getLocation());
            block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), itemRegistry.getSmelteryItem());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        if (smelteryManager.isSmeltery(block)) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            // The crucible is the melting half of the Forge, so it follows the same access rule.
            if (!AccessControl.allows(player, AccessControl.Surface.FORGE)) {
                player.sendActionBar(AccessControl.denial(AccessControl.Surface.FORGE));
                return;
            }
            smelteryManager.openGUI(player, block);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof SmelteryGUI)) {
            return;
        }

        int rawSlot = event.getRawSlot();
        if (rawSlot < 0) {
            return;
        }

        // If clicking within the Smeltery GUI (slots 0 - 26)
        if (rawSlot < 27) {
            if (rawSlot != SmelteryGUI.SLOT_RAW_INPUT &&
                rawSlot != SmelteryGUI.SLOT_BUCKET_INPUT &&
                rawSlot != SmelteryGUI.SLOT_OUTPUT) {
                // Cancel clicking borders or indicators
                event.setCancelled(true);
            }
        } else if (event.isShiftClick()) {
            // Safe shift-click handling
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null && clicked.getType() != Material.AIR) {
                SmelteryGUI gui = (SmelteryGUI) event.getInventory().getHolder();
                if (clicked.getType() == Material.BUCKET) {
                    event.setCancelled(true);
                    ItemStack existing = gui.getInventory().getItem(SmelteryGUI.SLOT_BUCKET_INPUT);
                    if (existing == null || existing.getType() == Material.AIR) {
                        gui.getInventory().setItem(SmelteryGUI.SLOT_BUCKET_INPUT, clicked.clone());
                        clicked.setAmount(0);
                    }
                } else if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(TinkerKeys.IS_TINKER_RAW, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    ItemStack existing = gui.getInventory().getItem(SmelteryGUI.SLOT_RAW_INPUT);
                    if (existing == null || existing.getType() == Material.AIR) {
                        gui.getInventory().setItem(SmelteryGUI.SLOT_RAW_INPUT, clicked.clone());
                        clicked.setAmount(0);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getInventory().getHolder() instanceof SmelteryGUI)) {
            return;
        }

        for (int slot : event.getRawSlots()) {
            if (slot < 27 && slot != SmelteryGUI.SLOT_RAW_INPUT && slot != SmelteryGUI.SLOT_BUCKET_INPUT) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
