package com.chagui68.multiversetinker.forge;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.forge.structure.ForgeStructure;
import com.chagui68.multiversetinker.forge.structure.ForgeStructureBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ForgeManager {

    private final MultiverseTinker plugin;
    private final ForgeStructure structure = new ForgeStructure();
    private final Set<Location> activeForges = ConcurrentHashMap.newKeySet();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private BukkitTask auraTask;

    public ForgeManager(@Nonnull MultiverseTinker plugin) {
        this.plugin = plugin;
        loadStructure();
    }

    private void loadStructure() {
        try (InputStream is = getClass().getResourceAsStream("/structures/forge.nbt")) {
            if (is == null) {
                plugin.getLogger().warning("Could not find /structures/forge.nbt in resources!");
                return;
            }
            structure.loadFromStream(is, plugin.getLogger());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load /structures/forge.nbt: " + e.getMessage());
        }
    }

    public void startAuraTask() {
        if (auraTask != null && !auraTask.isCancelled()) {
            auraTask.cancel();
        }

        // Run aura animation every 10 ticks (0.5s)
        this.auraTask = new BukkitRunnable() {
            private int tick = 0;

            @Override
            public void run() {
                tick++;
                Iterator<Location> it = activeForges.iterator();
                while (it.hasNext()) {
                    Location loc = it.next();
                    World world = loc.getWorld();
                    if (world == null || !loc.isChunkLoaded()) {
                        continue;
                    }

                    Material mat = loc.getBlock().getType();
                    if (mat != Material.ANVIL && mat != Material.CHIPPED_ANVIL && mat != Material.DAMAGED_ANVIL) {
                        it.remove();
                        continue;
                    }

                    // Orbital aura particles around anvil
                    double angle = (tick * 0.3) % (2 * Math.PI);
                    double radius = 0.65;
                    double x1 = loc.getX() + 0.5 + Math.cos(angle) * radius;
                    double z1 = loc.getZ() + 0.5 + Math.sin(angle) * radius;
                    double y1 = loc.getY() + 0.65 + Math.sin(angle * 2) * 0.15;

                    double x2 = loc.getX() + 0.5 - Math.cos(angle) * radius;
                    double z2 = loc.getZ() + 0.5 - Math.sin(angle) * radius;
                    double y2 = loc.getY() + 0.65 - Math.sin(angle * 2) * 0.15;

                    world.spawnParticle(Particle.SMALL_FLAME, x1, y1, z1, 1, 0, 0, 0, 0);
                    world.spawnParticle(Particle.WAX_OFF, x2, y2, z2, 1, 0, 0, 0, 0);

                    // Central warm ember smoke
                    if (tick % 2 == 0) {
                        world.spawnParticle(Particle.SMOKE, loc.getX() + 0.5, loc.getY() + 0.9, loc.getZ() + 0.5, 1, 0.05, 0.05, 0.05, 0.01);
                    }

                    // Subtle ambient crackle audio every 4 seconds
                    if (tick % 8 == 0) {
                        world.playSound(loc, Sound.BLOCK_BLASTFURNACE_FIRE_CRACKLE, 0.35f, 1.2f);
                    }
                }
            }
        }.runTaskTimer(plugin, 10L, 10L);
    }

    public void stopAuraTask() {
        if (auraTask != null && !auraTask.isCancelled()) {
            auraTask.cancel();
            auraTask = null;
        }
    }

    public ForgeStructure.ValidationResult checkForge(@Nonnull Location anvilLoc) {
        return structure.validate(anvilLoc);
    }

    public boolean isForge(@Nonnull Location anvilLoc) {
        Location normalized = normalizeLocation(anvilLoc);
        return activeForges.contains(normalized);
    }

    public void activateForge(@Nonnull Location anvilLoc, @Nullable Player player) {
        Location normalized = normalizeLocation(anvilLoc);
        activeForges.add(normalized);
        playValidationSequence(normalized, player);
    }

    public void deactivateForge(@Nonnull Location anvilLoc) {
        Location normalized = normalizeLocation(anvilLoc);
        if (activeForges.remove(normalized)) {
            World world = normalized.getWorld();
            if (world != null) {
                world.spawnParticle(Particle.LARGE_SMOKE, normalized.getX() + 0.5, normalized.getY() + 1.0, normalized.getZ() + 0.5, 15, 0.3, 0.3, 0.3, 0.05);
                world.playSound(normalized, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.8f);
            }
        }
    }

    public void playValidationSequence(@Nonnull Location anvilLoc, @Nullable Player player) {
        World world = anvilLoc.getWorld();
        if (world == null) return;

        ForgeStructure.ValidationResult res = structure.validate(anvilLoc);
        int rot = res.rotation();
        List<ForgeStructureBlock> blocks = structure.getBlocks(rot);

        // Notify with sound and titles immediately
        world.playSound(anvilLoc, Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 1.0f);
        world.playSound(anvilLoc, Sound.BLOCK_ANVIL_USE, 1.2f, 1.4f);

        if (player != null) {
            Title title = Title.title(
                    miniMessage.deserialize("<gradient:#ffaa00:#ff4500><b>MULTIVERSE FORGE</b></gradient>"),
                    miniMessage.deserialize("<yellow>Structure validated! The Forge is now active.</yellow>"),
                    Title.Times.times(Duration.ofMillis(200), Duration.ofMillis(2500), Duration.ofMillis(500))
            );
            player.showTitle(title);
            player.sendMessage(miniMessage.deserialize("<green>✔ [MultiverseTinker] The ancient forge awakens! Central anvil is now operational.</green>"));
        }

        // Multi-phase particle sweep animation simulation
        new BukkitRunnable() {
            private int step = 0;

            @Override
            public void run() {
                step++;
                if (step == 1) {
                    // Phase 1: Ignite corner lava pillars
                    for (ForgeStructureBlock b : blocks) {
                        if (b.material() == Material.LAVA) {
                            Location pLoc = anvilLoc.clone().add(b.dx() + 0.5, b.dy() + 0.5, b.dz() + 0.5);
                            world.spawnParticle(Particle.LAVA, pLoc, 3, 0.2, 0.2, 0.2, 0.0);
                            world.spawnParticle(Particle.SOUL_FIRE_FLAME, pLoc, 2, 0.1, 0.3, 0.1, 0.02);
                        }
                    }
                    world.playSound(anvilLoc, Sound.ITEM_FIRECHARGE_USE, 1.0f, 0.8f);
                } else if (step == 2) {
                    // Phase 2: Sweep glowing perimeter inward towards anvil
                    for (ForgeStructureBlock b : blocks) {
                        if (b.dy() == -1) { // Floor blocks
                            Location pLoc = anvilLoc.clone().add(b.dx() + 0.5, b.dy() + 1.1, b.dz() + 0.5);
                            world.spawnParticle(Particle.ENCHANT, pLoc, 2, 0.1, 0.1, 0.1, 0.5);
                            world.spawnParticle(Particle.WAX_ON, pLoc, 1, 0.1, 0.1, 0.1, 0.0);
                        }
                    }
                    world.playSound(anvilLoc, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1.0f, 1.2f);
                } else if (step == 3) {
                    // Phase 3: Converge into anvil with burst
                    Location aCenter = anvilLoc.clone().add(0.5, 0.8, 0.5);
                    world.spawnParticle(Particle.FLASH, aCenter, 1, 0, 0, 0, 0, Color.WHITE);
                    world.spawnParticle(Particle.TOTEM_OF_UNDYING, aCenter, 30, 0.4, 0.4, 0.4, 0.15);
                    world.spawnParticle(Particle.TRIAL_SPAWNER_DETECTION_OMINOUS, aCenter, 20, 0.5, 0.5, 0.5, 0.05);
                    world.playSound(anvilLoc, Sound.ITEM_TOTEM_USE, 0.8f, 1.6f);
                    world.playSound(anvilLoc, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 4L, 6L);
    }

    public void buildStructure(@Nonnull Location anvilLoc, int rotation) {
        structure.build(anvilLoc, rotation);
        activateForge(anvilLoc, null);
    }

    @Nonnull
    public ForgeStructure getStructure() {
        return structure;
    }

    private Location normalizeLocation(Location loc) {
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
