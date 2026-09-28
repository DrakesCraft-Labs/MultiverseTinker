package com.chagui68.multiversetinker.archaeology;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ArchaeologyListener implements Listener {

    private final ArchaeologyManager archaeologyManager;

    public ArchaeologyListener(@Nonnull ArchaeologyManager archaeologyManager) {
        this.archaeologyManager = archaeologyManager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Solo procesar la mano principal para evitar dobles eventos
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BRUSH) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        BlockFace face = event.getBlockFace();
        Player player = event.getPlayer();

        if (ArchaeologyBlockType.getOrigin(clickedBlock.getType()) != null) {
            event.setCancelled(true);
            archaeologyManager.handleBrushing(player, clickedBlock, face, item);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        archaeologyManager.removeSession(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        archaeologyManager.removeSession(event.getPlayer().getUniqueId());
    }
}
