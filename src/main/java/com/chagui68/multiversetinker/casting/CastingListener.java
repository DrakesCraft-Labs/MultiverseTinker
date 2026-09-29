package com.chagui68.multiversetinker.casting;

import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class CastingListener implements Listener {

    private final TinkerItemRegistry itemRegistry;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public CastingListener(@Nonnull TinkerItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCauldronInteract(PlayerInteractEvent event) {
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

        // Must interact with a water cauldron
        if (block.getType() != Material.WATER_CAULDRON && block.getType() != Material.CAULDRON) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();

        // Check if holding a molten liquid bucket
        if (!isMoltenBucket(handItem)) {
            return;
        }

        // Casting is the last step of the Forge chain, so it follows access.forge too.
        if (!AccessControl.allows(player, AccessControl.Surface.FORGE)) {
            event.setCancelled(true);
            player.sendActionBar(AccessControl.denial(AccessControl.Surface.FORGE));
            return;
        }

        // Prevent vanilla lava bucket emptying
        event.setCancelled(true);

        // Verify water level in cauldron
        if (block.getType() == Material.CAULDRON || !(block.getBlockData() instanceof Levelled levelled) || levelled.getLevel() <= 0) {
            player.sendActionBar(miniMessage.deserialize("<red>⚠ Cauldron is empty! Fill with water to cool molten metal.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.5f);
            return;
        }

        // Check for Cast in offhand or inventory
        CastType castType = findCastType(player);
        if (castType == null) {
            player.sendActionBar(miniMessage.deserialize("<red>⚠ Missing Casting Mold! Hold an Ingot, Nugget, or Block Cast.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.5f);
            return;
        }

        String materialId = handItem.getItemMeta().getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        if (materialId == null) {
            return;
        }

        ItemStack resultItem = switch (castType) {
            case INGOT -> itemRegistry.getIngotItem(materialId);
            case NUGGET -> {
                ItemStack nugget = itemRegistry.getNuggetItem(materialId);
                if (nugget != null) {
                    nugget.setAmount(9);
                }
                yield nugget;
            }
            case BLOCK -> itemRegistry.getBlockItem(materialId);
            case HEAD -> itemRegistry.getToolHeadItem(materialId);
            case ROD -> itemRegistry.getToolRodItem(materialId);
            case BINDING -> itemRegistry.getToolBindingItem(materialId);
            case BOW_LIMBS -> itemRegistry.getPartItem(ToolPartType.BOW_LIMBS, materialId);
            case BOWSTRING -> itemRegistry.getPartItem(ToolPartType.BOWSTRING, materialId);
            case SHIELD_PLATE -> itemRegistry.getPartItem(ToolPartType.SHIELD_PLATE, materialId);
            case SHIELD_BOSS -> itemRegistry.getPartItem(ToolPartType.SHIELD_BOSS, materialId);
            case ARMOR_PLATE -> itemRegistry.getPartItem(ToolPartType.ARMOR_PLATE, materialId);
            case ARMOR_LINING -> itemRegistry.getPartItem(ToolPartType.ARMOR_LINING, materialId);
            case ARMOR_TRIM -> itemRegistry.getPartItem(ToolPartType.ARMOR_TRIM, materialId);
        };

        if (resultItem == null) {
            return;
        }

        // Lower water level in cauldron (evaporation due to extreme heat)
        int newLevel = levelled.getLevel() - 1;
        if (newLevel <= 0) {
            block.setType(Material.CAULDRON);
        } else {
            levelled.setLevel(newLevel);
            block.setBlockData(levelled);
        }

        // Consume molten bucket and return empty bucket
        handItem.setAmount(handItem.getAmount() - 1);
        player.getInventory().addItem(new ItemStack(Material.BUCKET, 1));

        // Give the solidified metal result
        player.getInventory().addItem(resultItem);

        // Sound & Particle Effects
        block.getWorld().playSound(block.getLocation().add(0.5, 0.8, 0.5), Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f);
        block.getWorld().playSound(block.getLocation().add(0.5, 0.8, 0.5), Sound.BLOCK_ANVIL_USE, 0.8f, 1.2f);
        block.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, block.getLocation().add(0.5, 0.9, 0.5), 12, 0.2, 0.3, 0.2, 0.05);
        block.getWorld().spawnParticle(Particle.SPLASH, block.getLocation().add(0.5, 0.8, 0.5), 20, 0.3, 0.1, 0.3, 0.1);

        player.sendActionBar(miniMessage.deserialize("<gradient:#55ffff:#ffffff>✦ Casting Complete! Solidified into " + castType.getDisplayName() + ".</gradient>"));
    }

    private boolean isMoltenBucket(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(TinkerKeys.IS_MOLTEN_BUCKET, PersistentDataType.BYTE);
    }

    private CastType findCastType(Player player) {
        // Priority 1: Check Off-Hand
        ItemStack offHand = player.getInventory().getItemInOffHand();
        CastType offCast = getCastFromItem(offHand);
        if (offCast != null) {
            return offCast;
        }

        // Priority 2: Check Inventory
        for (ItemStack item : player.getInventory().getContents()) {
            CastType cast = getCastFromItem(item);
            if (cast != null) {
                return cast;
            }
        }

        return null;
    }

    private CastType getCastFromItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        String typeName = item.getItemMeta().getPersistentDataContainer().get(TinkerKeys.CAST_TYPE, PersistentDataType.STRING);
        if (typeName == null) {
            return null;
        }
        try {
            return CastType.valueOf(typeName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
