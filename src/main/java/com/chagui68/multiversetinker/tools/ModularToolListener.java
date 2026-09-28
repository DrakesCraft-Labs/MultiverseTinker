package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Random;

public class ModularToolListener implements Listener {

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final Random random = new Random();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ModularToolListener(@Nonnull MultiverseTinker plugin, @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.materialRegistry = materialRegistry;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onToolCombat(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularTool(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String headMat = pdc.get(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING);
        String rodMat = pdc.get(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING);
        String bindingMat = pdc.get(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING);

        // Apply traits
        triggerCombatTraits(player, target, event, headMat, rodMat, bindingMat);

        // Durability loss
        damageTool(player, hand, meta, pdc, headMat, rodMat, bindingMat);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onToolMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!isModularTool(hand)) return;

        ItemMeta meta = hand.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String headMat = pdc.get(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING);
        String rodMat = pdc.get(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING);
        String bindingMat = pdc.get(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING);

        // Luminescent trait (Fluorite)
        if (hasTrait(headMat, rodMat, bindingMat, "mvtink_fluorite")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0, false, false));
        }

        // Durability loss
        damageTool(player, hand, meta, pdc, headMat, rodMat, bindingMat);
    }

    private void triggerCombatTraits(Player player, LivingEntity target, EntityDamageByEntityEvent event,
                                     String head, String rod, String binding) {
        // Flame Edge (Ruby)
        if (hasTrait(head, rod, binding, "mvtink_ruby")) {
            target.setFireTicks(80);
            target.getWorld().spawnParticle(Particle.FLAME, target.getLocation().add(0, 1, 0), 8, 0.2, 0.2, 0.2, 0.05);
        }

        // Glacial (Sapphire)
        if (hasTrait(head, rod, binding, "mvtink_sapphire")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 1));
            target.getWorld().spawnParticle(Particle.SNOWFLAKE, target.getLocation().add(0, 1, 0), 10, 0.2, 0.2, 0.2, 0.02);
        }

        // Exorcism (Silver)
        if (hasTrait(head, rod, binding, "mvtink_silver") && isUndead(target)) {
            event.setDamage(event.getDamage() * 1.30);
            target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1, 0), 12, 0.3, 0.3, 0.3, 0.1);
        }

        // Toxic Patina (Malachite)
        if (hasTrait(head, rod, binding, "mvtink_malachite")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0));
        }

        // Wither Decay (Witherite)
        if (hasTrait(head, rod, binding, "mvtink_witherite")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1));
        }

        // Blood Feast (Sanguinite)
        if (hasTrait(head, rod, binding, "mvtink_sanguinite")) {
            double newHealth = Math.min(player.getMaxHealth(), player.getHealth() + 1.5);
            player.setHealth(newHealth);
            player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 1.5, 0), 2, 0.2, 0.2, 0.2, 0.0);
        }

        // Levitation Hit (Gravitite)
        if (hasTrait(head, rod, binding, "mvtink_gravitite")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 30, 1));
        }

        // Sparking (Pyrite)
        if (hasTrait(head, rod, binding, "mvtink_pyrite")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0));
        }
    }

    private void damageTool(Player player, ItemStack hand, ItemMeta meta, PersistentDataContainer pdc,
                            String head, String rod, String binding) {
        // Indestructible (Adamantium) - 75% chance to ignore wear
        if (hasTrait(head, rod, binding, "mvtink_adamantium") && random.nextDouble() < 0.75) {
            return;
        }

        int maxDur = pdc.getOrDefault(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, 1000);
        int curDur = pdc.getOrDefault(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, maxDur);

        curDur -= 1;
        if (curDur <= 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            player.spawnParticle(Particle.ITEM, player.getLocation().add(0, 1, 0), 15, 0.2, 0.2, 0.2, 0.1, hand);
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            player.sendMessage(miniMessage.deserialize("<red>⚒ Your modular tool shattered from durability fatigue!</red>"));
            return;
        }

        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, curDur);

        // Update visual vanilla damage bar
        if (meta instanceof Damageable damageable) {
            int vanillaMax = hand.getType().getMaxDurability();
            double damageRatio = 1.0 - ((double) curDur / maxDur);
            damageable.setDamage((int) (vanillaMax * damageRatio));
        }

        hand.setItemMeta(meta);
    }

    private boolean hasTrait(String h, String r, String b, String targetId) {
        return (h != null && h.equalsIgnoreCase(targetId))
                || (r != null && r.equalsIgnoreCase(targetId))
                || (b != null && b.equalsIgnoreCase(targetId));
    }

    private boolean isUndead(LivingEntity entity) {
        return switch (entity.getType()) {
            case ZOMBIE, ZOMBIE_VILLAGER, DROWNED, HUSK, SKELETON, STRAY, WITHER_SKELETON, PHANTOM, WITHER, ZOMBIFIED_PIGLIN, ZOGLIN -> true;
            default -> false;
        };
    }

    private boolean isModularTool(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        Byte b = meta.getPersistentDataContainer().get(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE);
        return b != null && b == (byte) 1;
    }
}
