package com.chagui68.multiversetinker.smeltery;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SmelteryManager {

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final TinkerItemRegistry itemRegistry;

    @Getter
    private final Set<Location> placedSmelteries = ConcurrentHashMap.newKeySet();
    private final Map<Location, SmelteryGUI> activeGUIs = new ConcurrentHashMap<>();

    public SmelteryManager(@Nonnull MultiverseTinker plugin,
                           @Nonnull MaterialRegistry materialRegistry,
                           @Nonnull TinkerItemRegistry itemRegistry) {
        this.plugin = plugin;
        this.materialRegistry = materialRegistry;
        this.itemRegistry = itemRegistry;
    }

    public void registerSmeltery(@Nonnull Location location) {
        placedSmelteries.add(location.getBlock().getLocation());
    }

    public void unregisterSmeltery(@Nonnull Location location) {
        Location loc = location.getBlock().getLocation();
        placedSmelteries.remove(loc);
        SmelteryGUI gui = activeGUIs.remove(loc);
        if (gui != null) {
            // Drop remaining items if smeltery is broken
            Inventory inv = gui.getInventory();
            dropSlot(loc, inv, SmelteryGUI.SLOT_RAW_INPUT);
            dropSlot(loc, inv, SmelteryGUI.SLOT_BUCKET_INPUT);
            dropSlot(loc, inv, SmelteryGUI.SLOT_OUTPUT);
        }
    }

    private void dropSlot(Location loc, Inventory inv, int slot) {
        ItemStack item = inv.getItem(slot);
        if (item != null && item.getType() != Material.AIR) {
            loc.getWorld().dropItemNaturally(loc.clone().add(0.5, 0.5, 0.5), item);
            inv.setItem(slot, null);
        }
    }

    public boolean isSmeltery(@Nonnull Block block) {
        return placedSmelteries.contains(block.getLocation());
    }

    public boolean hasLavaBeneath(@Nonnull Block block) {
        Material down = block.getRelative(BlockFace.DOWN).getType();
        return down == Material.LAVA;
    }

    public void openGUI(@Nonnull Player player, @Nonnull Block block) {
        Location loc = block.getLocation();
        SmelteryGUI gui = activeGUIs.computeIfAbsent(loc, k -> new SmelteryGUI(block));
        boolean hasLava = hasLavaBeneath(block);
        gui.updateStatus(hasLava, false, 0.0f);
        player.openInventory(gui.getInventory());
    }

    public void tickSmelteries() {
        for (Map.Entry<Location, SmelteryGUI> entry : activeGUIs.entrySet()) {
            Location loc = entry.getKey();
            SmelteryGUI gui = entry.getValue();
            Block block = loc.getBlock();

            // Verify block is still valid
            if (!isSmeltery(block)) {
                activeGUIs.remove(loc);
                continue;
            }

            Inventory inv = gui.getInventory();
            boolean hasLava = hasLavaBeneath(block);

            ItemStack rawInput = inv.getItem(SmelteryGUI.SLOT_RAW_INPUT);
            ItemStack bucketInput = inv.getItem(SmelteryGUI.SLOT_BUCKET_INPUT);
            ItemStack output = inv.getItem(SmelteryGUI.SLOT_OUTPUT);

            TinkerMaterial material = getTinkerMaterialFromRaw(rawInput);

            if (material == null || bucketInput == null || bucketInput.getType() != Material.BUCKET) {
                gui.setCurrentProgressTicks(0);
                gui.updateStatus(hasLava, false, 0.0f);
                continue;
            }

            if (!hasLava) {
                gui.setCurrentProgressTicks(0);
                gui.updateStatus(false, false, 0.0f);
                continue;
            }

            // Can output receive item?
            ItemStack moltenBucket = itemRegistry.getMoltenBucketItem(material.getId());
            if (moltenBucket == null) {
                gui.updateStatus(true, false, 0.0f);
                continue;
            }

            if (output != null && output.getType() != Material.AIR) {
                // Lava buckets cannot stack past 1
                gui.updateStatus(true, false, 1.0f);
                continue;
            }

            int reqTicks = material.getMeltingDurationTicks();
            int currentTicks = gui.getCurrentProgressTicks() + 5; // Ticks advance by 5 per check
            gui.setCurrentProgressTicks(currentTicks);
            gui.setRequiredProgressTicks(reqTicks);

            float ratio = Math.min(1.0f, (float) currentTicks / (float) reqTicks);
            gui.updateStatus(true, true, ratio);

            // Smelting particle effects at block
            loc.getWorld().spawnParticle(Particle.FLAME, loc.clone().add(0.5, 0.6, 0.5), 3, 0.15, 0.15, 0.15, 0.02);

            if (currentTicks >= reqTicks) {
                // Completed melting!
                rawInput.setAmount(rawInput.getAmount() - 1);
                bucketInput.setAmount(bucketInput.getAmount() - 1);
                inv.setItem(SmelteryGUI.SLOT_OUTPUT, moltenBucket);

                gui.setCurrentProgressTicks(0);
                gui.updateStatus(true, false, 0.0f);

                loc.getWorld().playSound(loc.clone().add(0.5, 0.5, 0.5), Sound.BLOCK_LAVA_AMBIENT, 1.0f, 1.2f);
                loc.getWorld().playSound(loc.clone().add(0.5, 0.5, 0.5), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1.0f, 1.0f);
                loc.getWorld().spawnParticle(Particle.LAVA, loc.clone().add(0.5, 0.8, 0.5), 5, 0.2, 0.2, 0.2, 0.05);
            }
        }
    }

    @Nullable
    private TinkerMaterial getTinkerMaterialFromRaw(@Nullable ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        String matId = item.getItemMeta().getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        if (matId == null) {
            return null;
        }
        return materialRegistry.get(matId);
    }
}
