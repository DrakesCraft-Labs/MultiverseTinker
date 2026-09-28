package com.chagui68.multiversetinker.archaeology;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ArchaeologyManager {

    private final MultiverseTinker plugin;
    @Getter
    private final ArchaeologyLootTable lootTable;
    private final TinkerItemRegistry itemRegistry;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final Map<UUID, ArchaeologySession> activeSessions = new ConcurrentHashMap<>();
    private final Map<Location, Long> blockCooldowns = new ConcurrentHashMap<>();

    public ArchaeologyManager(@Nonnull MultiverseTinker plugin,
                              @Nonnull ArchaeologyLootTable lootTable,
                              @Nonnull TinkerItemRegistry itemRegistry) {
        this.plugin = plugin;
        this.lootTable = lootTable;
        this.itemRegistry = itemRegistry;
    }

    public void handleBrushing(@Nonnull Player player,
                               @Nonnull Block block,
                               @Nonnull BlockFace face,
                               @Nonnull ItemStack brush) {
        if (!plugin.getConfig().getBoolean("archaeology.enabled", true)) {
            return;
        }

        if (!player.hasPermission("multiversetinker.archaeology")) {
            player.sendActionBar(miniMessage.deserialize(plugin.getConfig().getString(
                    "messages.no-permission", "<red>No tienes permisos de arqueologia.</red>")));
            return;
        }

        MineralOrigin origin = ArchaeologyBlockType.getOrigin(block.getType());
        if (origin == null) {
            return; // No es un bloque geológico válido
        }

        Location blockLoc = block.getLocation();

        // Control de Cooldown por bloque para prevenir macro/autoclick
        long now = System.currentTimeMillis();
        long cooldownDuration = plugin.getConfig().getLong("archaeology.block-cooldown-seconds", 15) * 1000L;
        if (blockCooldowns.containsKey(blockLoc)) {
            long lastBrushed = blockCooldowns.get(blockLoc);
            if (now - lastBrushed < cooldownDuration) {
                player.sendActionBar(miniMessage.deserialize(plugin.getConfig().getString(
                        "messages.block-in-cooldown", "<red>Superficie geológica investigada recientemente.</red>")));
                return;
            }
        }

        boolean isProspector = isProspectorBrush(brush);

        // Obtener o crear sesión de cepillado
        ArchaeologySession session = activeSessions.get(player.getUniqueId());
        if (session == null || !session.getBlockLocation().equals(blockLoc) || session.isExpired(2000L)) {
            session = new ArchaeologySession(player.getUniqueId(), blockLoc, face);
            activeSessions.put(player.getUniqueId(), session);
            String startMsg = isProspector
                    ? "<gradient:#ffaa00:#ffff55>🔍 Prospectando con cerdas de alta precisión...</gradient>"
                    : plugin.getConfig().getString("messages.brushing-started", "<gray>Cepillando la roca con cuidado...</gray>");
            player.sendActionBar(miniMessage.deserialize(startMsg));
        }

        // Avanzar progreso: la brocha de prospector avanza mucho más rápido (+10 vs +6)
        int progressIncrement = isProspector ? 10 : 6;
        session.incrementProgress(progressIncrement);
        int maxTicks = plugin.getConfig().getInt("archaeology.brushing-duration-ticks", 30);

        // Efectos de cepillado
        float pitch = (isProspector ? 1.0f : 0.8f) + ((float) session.getTicksProgress() / (float) maxTicks) * 0.4f;
        block.getWorld().playSound(blockLoc.clone().add(0.5, 0.5, 0.5),
                Sound.ITEM_BRUSH_BRUSHING_GENERIC, 1.0f, pitch);

        // Partículas en la cara del bloque
        spawnBrushingParticles(block, face, isProspector);

        // ¿Alcanzó la duración total de cepillado?
        if (session.getTicksProgress() >= maxTicks) {
            finishArchaeology(player, block, face, brush, origin, session, isProspector);
        }
    }

    private void finishArchaeology(Player player, Block block, BlockFace face, ItemStack brush,
                                   MineralOrigin origin, ArchaeologySession session, boolean isProspector) {
        Location blockLoc = block.getLocation();
        activeSessions.remove(player.getUniqueId());
        blockCooldowns.put(blockLoc, System.currentTimeMillis());

        // Sonidos finales de extracción
        block.getWorld().playSound(blockLoc.clone().add(0.5, 0.5, 0.5),
                Sound.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE, 1.0f, 1.0f);
        block.getWorld().playSound(blockLoc.clone().add(0.5, 0.5, 0.5),
                Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, isProspector ? 1.5f : 1.2f);

        // Desgaste de la brocha
        damageBrush(player, brush, isProspector);

        // Probabilidad de éxito según la dimensión (+15% bono si es prospector)
        double baseChance = switch (origin) {
            case OVERWORLD -> plugin.getConfig().getDouble("archaeology.success-chance.overworld", 0.45);
            case NETHER -> plugin.getConfig().getDouble("archaeology.success-chance.nether", 0.40);
            case THE_END -> plugin.getConfig().getDouble("archaeology.success-chance.the_end", 0.35);
        };

        double finalChance = isProspector ? Math.min(0.95, baseChance + 0.15) : baseChance;
        boolean success = ThreadLocalRandom.current().nextDouble() <= finalChance;

        if (success) {
            TinkerMaterial mineral = lootTable.rollMineral(origin, isProspector);
            if (mineral != null) {
                ItemStack rawDrop = itemRegistry.getRawItem(mineral.getId());
                if (rawDrop != null) {
                    Location dropLoc = blockLoc.clone().add(0.5, 1.0, 0.5);
                    Item dropped = block.getWorld().dropItem(dropLoc, rawDrop);
                    dropped.setVelocity(new Vector(0, 0.15, 0));

                    // Partículas de éxito
                    block.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, dropLoc, isProspector ? 16 : 10, 0.3, 0.3, 0.3, 0.05);
                    if (isProspector) {
                        block.getWorld().spawnParticle(Particle.ENCHANT, dropLoc, 15, 0.4, 0.4, 0.4, 0.5);
                    }

                    String msg = "<gradient:#00ffaa:#00aaff>¡Has extraído: " + mineral.getName() + "!</gradient>";
                    player.sendActionBar(miniMessage.deserialize(msg));
                }
            }
        } else {
            // Extracción fallida: Escombros menores
            Location dropLoc = blockLoc.clone().add(0.5, 0.8, 0.5);
            block.getWorld().spawnParticle(Particle.SMOKE, dropLoc, 8, 0.2, 0.2, 0.2, 0.02);
            player.sendActionBar(miniMessage.deserialize(plugin.getConfig().getString(
                    "messages.brushing-completed-fail", "<gray>Solo has encontrado escombros y polvo.</gray>")));

            // Probabilidad de soltar escombros base
            if (ThreadLocalRandom.current().nextBoolean()) {
                Material rubbleMat = switch (origin) {
                    case OVERWORLD -> Material.GRAVEL;
                    case NETHER -> Material.BASALT;
                    case THE_END -> Material.COBBLESTONE;
                };
                block.getWorld().dropItem(dropLoc, new ItemStack(rubbleMat, 1));
            }
        }

        // Degradación geológica del bloque si está configurado
        String behavior = plugin.getConfig().getString("archaeology.block-behavior", "DEGRADE");
        if ("DEGRADE".equalsIgnoreCase(behavior)) {
            Material degraded = ArchaeologyBlockType.getDegradedMaterial(block.getType());
            if (degraded != null) {
                block.setType(degraded);
            }
        }
    }

    private boolean isProspectorBrush(ItemStack brush) {
        if (brush == null || !brush.hasItemMeta()) {
            return false;
        }
        return brush.getItemMeta().getPersistentDataContainer().has(
                com.chagui68.multiversetinker.storage.TinkerKeys.PROSPECTOR_BRUSH,
                org.bukkit.persistence.PersistentDataType.BYTE);
    }

    private void damageBrush(Player player, ItemStack brush, boolean isProspector) {
        if (!(brush.getItemMeta() instanceof Damageable damageable)) {
            return;
        }

        // Brocha de Prospector: 50% de probabilidad de no gastar durabilidad
        if (isProspector && ThreadLocalRandom.current().nextBoolean()) {
            return;
        }

        int unbreakingLevel = brush.getEnchantmentLevel(Enchantment.UNBREAKING);
        if (unbreakingLevel > 0) {
            // Probabilidad vanilla de Unbreaking
            if (ThreadLocalRandom.current().nextInt(unbreakingLevel + 1) != 0) {
                return;
            }
        }

        int cost = plugin.getConfig().getInt("archaeology.brush-durability-cost", 1);
        int currentDamage = damageable.getDamage();
        int maxDurability = brush.getType().getMaxDurability();

        if (currentDamage + cost >= maxDurability) {
            brush.setAmount(brush.getAmount() - 1);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        } else {
            damageable.setDamage(currentDamage + cost);
            brush.setItemMeta(damageable);
        }
    }

    private void spawnBrushingParticles(Block block, BlockFace face, boolean isProspector) {
        Location center = block.getLocation().add(0.5, 0.5, 0.5);
        Location particleLoc = center.clone().add(face.getModX() * 0.55, face.getModY() * 0.55, face.getModZ() * 0.55);

        block.getWorld().spawnParticle(
                Particle.DUST,
                particleLoc,
                isProspector ? 8 : 5,
                0.2, 0.2, 0.2,
                new Particle.DustOptions(
                        isProspector ? org.bukkit.Color.fromRGB(255, 215, 0) : org.bukkit.Color.fromRGB(160, 160, 160),
                        1.0f
                )
        );

        if (isProspector) {
            block.getWorld().spawnParticle(Particle.GLOW, particleLoc, 2, 0.1, 0.1, 0.1, 0.01);
        }
    }

    public void removeSession(UUID uuid) {
        activeSessions.remove(uuid);
    }

    public void cleanExpiredSessions() {
        activeSessions.entrySet().removeIf(entry -> entry.getValue().isExpired(3000L));
    }
}
