package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Renders the exclusive perk animation of each piece of modular equipment.
 *
 * <p>The catalogue lives in {@link EquipmentAnimation}; this engine turns its pattern into
 * particles on the server and optionally plays the signature sound. Every type is rendered by its
 * own geometry — an arc for a sword, a trail for a longbow, a ring for a mace, a halo for a helmet —
 * so the visuals never look like one weapon family received all the attention.</p>
 *
 * <p>When the equipment was forged from coloured minerals the animation is tinted with the dominant
 * material colour, which keeps the choreography tied to the build without changing its shape.</p>
 */
public final class PerkAnimationEngine {

    /** Per-player, per-animation deadline in milliseconds; keeps fast procs from strobing. */
    private static final Map<UUID, Map<EquipmentAnimation, Long>> COOLDOWNS = new ConcurrentHashMap<>();

    private PerkAnimationEngine() {
    }

    // ==========================================
    // PUBLIC ENTRY POINTS
    // ==========================================

    /** Plays the exclusive animation of a weapon at the impact point. */
    public static void playWeapon(@Nullable ModularWeaponType type, @Nonnull Player player,
                                  @Nonnull Location impact, @Nullable Color tint) {
        play(EquipmentAnimation.forWeapon(type), player, impact, tint);
    }

    /** Plays the exclusive animation of a tool on the block it just worked. */
    public static void playTool(@Nullable ModularToolType type, @Nonnull Player player,
                                @Nonnull Block block, @Nullable Color tint) {
        play(EquipmentAnimation.forTool(type), player, block.getLocation().add(0.5, 0.5, 0.5), tint);
    }

    /** Plays the exclusive animation of an armor piece on the wearer. */
    public static void playArmor(@Nullable ModularArmorType type, @Nonnull Player player, @Nullable Color tint) {
        play(EquipmentAnimation.forArmor(type), player, player.getLocation(), tint);
    }

    /**
     * Plays an animation resolved from the raw equipment-type stored in an item's persistent data.
     */
    public static void playStored(@Nonnull EquipmentAnimation.Family family, @Nullable String rawType,
                                  @Nonnull Player player, @Nonnull Location origin, @Nullable Color tint) {
        play(EquipmentAnimation.forType(family, rawType), player, origin, tint);
    }

    /** Releases the cooldown state of a player; the listener calls this when they disconnect. */
    public static void clearPlayer(@Nonnull UUID playerId) {
        COOLDOWNS.remove(playerId);
    }

    // ==========================================
    // DISPATCH
    // ==========================================

    private static void play(@Nullable EquipmentAnimation animation, @Nonnull Player player,
                             @Nonnull Location origin, @Nullable Color tint) {
        if (animation == null || !EquipmentAnimation.isEnabled()) return;

        Location anchor = origin.getWorld() == null ? player.getLocation() : origin;
        World world = anchor.getWorld();
        if (world == null) return;

        if (!claimCooldown(player.getUniqueId(), animation)) return;

        Vector facing = player.getLocation().getDirection().setY(0);
        if (facing.lengthSquared() < 1.0E-6) {
            facing = new Vector(0, 0, 1);
        }
        facing = facing.normalize();
        Vector side = new Vector(-facing.getZ(), 0, facing.getX());

        Particle.DustOptions dust = tint == null ? null : new Particle.DustOptions(tint, 1.0f);

        switch (animation.getPattern()) {
            case SWEEP_ARC -> sweepArc(animation, world, anchor, facing, side, dust);
            case VOLLEY_TRAIL -> volleyTrail(animation, player, world, anchor, dust);
            case PIERCE_LANCE -> pierceLance(animation, world, anchor, facing, side, dust);
            case SURGE_COLUMN -> surgeColumn(animation, world, player.getLocation(), dust);
            case THRUST_LINE -> thrustLine(animation, player, world, anchor, facing, dust);
            case SHOCK_RING -> shockRing(animation, world, anchor, dust);
            case PARAPET_ARC -> parapetArc(animation, player, world, facing, side, dust);
            case VEIN_STRIKE -> veinStrike(animation, world, anchor, dust);
            case LUMBER_SPLASH -> lumberSplash(animation, world, anchor, dust);
            case GROUND_WAVE -> groundWave(animation, world, anchor, dust);
            case HARVEST_SWIRL -> harvestSwirl(animation, world, player.getLocation(), dust);
            case DREDGE_DRIP -> dredgeDrip(animation, world, anchor, dust);
            case HALO_RING -> haloRing(animation, world, player.getLocation(), dust);
            case DOME_BURST -> domeBurst(animation, world, player.getLocation(), dust);
            case STRIDE_SPIRAL -> strideSpiral(animation, world, player.getLocation(), dust);
            case TRACTION_PUFF -> tractionPuff(animation, world, player.getLocation(), dust);
        }

        if (EquipmentAnimation.isSoundsEnabled()) {
            world.playSound(anchor, animation.getSound(), animation.getVolume(), animation.getPitch());
        }
    }

    // ==========================================
    // PATTERNS
    // ==========================================

    /** Sword: a horizontal elemental arc in front of the wielder. */
    private static void sweepArc(EquipmentAnimation animation, World world, Location origin,
                                 Vector facing, Vector side, @Nullable Particle.DustOptions dust) {
        int steps = animation.scaleCount(13);
        for (int i = 0; i <= steps; i++) {
            double angle = Math.toRadians(-70 + (i * 140.0 / steps));
            Vector direction = facing.clone().multiply(Math.cos(angle)).add(side.clone().multiply(Math.sin(angle)));
            Location point = origin.clone().add(direction.multiply(1.4)).add(0, 0.9, 0);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(),
                origin.clone().add(facing.clone().multiply(1.4)).add(0, 0.9, 0), animation.scaleCount(6), 0.25, 0.25, 0.25, 0.02);
    }

    /** Longbow: a dotted trail from the archer's eye to the arrow that just landed. */
    private static void volleyTrail(EquipmentAnimation animation, Player player, World world,
                                    Location impact, @Nullable Particle.DustOptions dust) {
        Location from = player.getEyeLocation();
        Vector line = impact.toVector().subtract(from.toVector());
        int steps = animation.scaleCount(10);
        for (int i = 1; i <= steps; i++) {
            Location point = from.clone().add(line.clone().multiply((double) i / steps));
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), impact, animation.scaleCount(8), 0.2, 0.2, 0.2, 0.03);
    }

    /** Crossbow: a taut line of sparks that spears straight through the impact. */
    private static void pierceLance(EquipmentAnimation animation, World world, Location origin,
                                    Vector facing, Vector side, @Nullable Particle.DustOptions dust) {
        int steps = animation.scaleCount(11);
        for (int i = 0; i <= steps; i++) {
            Location point = origin.clone().add(facing.clone().multiply(0.3 + i * 0.25));
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 3 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        for (int i = -3; i <= 3; i++) {
            Location barb = origin.clone().add(side.clone().multiply(i * 0.18)).add(0, i * 0.12, 0);
            world.spawnParticle(animation.getAccentParticle(), barb, 1, 0, 0, 0, 0);
        }
    }

    /** Trident: a rising hydraulic column that wraps the wielder. */
    private static void surgeColumn(EquipmentAnimation animation, World world, Location base,
                                    @Nullable Particle.DustOptions dust) {
        int ring = animation.scaleCount(10);
        for (int level = 0; level < 4; level++) {
            double y = 0.25 + level * 0.55;
            double radius = 0.75 - level * 0.12;
            for (int i = 0; i < ring; i++) {
                double angle = (Math.PI * 2 * i) / ring;
                Location point = base.clone().add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
                world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
                if (dust != null && i % 3 == 0) {
                    world.spawnParticle(Particle.DUST, point, 1, dust);
                }
            }
        }
        world.spawnParticle(animation.getAccentParticle(), base.clone().add(0, 2.1, 0), animation.scaleCount(7), 0.3, 0.2, 0.3, 0.05);
    }

    /** Spear: a low air-pressure lane that marks the reach of the thrust. */
    private static void thrustLine(EquipmentAnimation animation, Player player, World world,
                                   Location impact, Vector facing, @Nullable Particle.DustOptions dust) {
        Location from = player.getEyeLocation().subtract(0, 0.35, 0);
        int steps = animation.scaleCount(12);
        for (int i = 1; i <= steps; i++) {
            Location point = from.clone().add(facing.clone().multiply(i * 0.24));
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 3 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), impact, animation.scaleCount(6), 0.15, 0.15, 0.15, 0.02);
    }

    /** Mace: an expanding shock ring that breaks outward from the impact. */
    private static void shockRing(EquipmentAnimation animation, World world, Location origin,
                                  @Nullable Particle.DustOptions dust) {
        int perRing = animation.scaleCount(12);
        for (double radius : new double[]{1.0, 1.8, 2.6}) {
            for (int i = 0; i < perRing; i++) {
                double angle = (Math.PI * 2 * i) / perRing;
                Location point = origin.clone().add(Math.cos(angle) * radius, 0.15, Math.sin(angle) * radius);
                world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            }
        }
        if (dust != null) {
            world.spawnParticle(Particle.DUST, origin.clone().add(0, 0.2, 0), animation.scaleCount(10), 0.8, 0.05, 0.8, 0.4, dust);
        }
        world.spawnParticle(animation.getAccentParticle(), origin.clone().add(0, 0.4, 0), animation.scaleCount(8), 0.5, 0.2, 0.5, 0.05);
    }

    /** Shield: a curved rampart of ward-light raised in front of the blocker. */
    private static void parapetArc(EquipmentAnimation animation, Player player, World world,
                                   Vector facing, Vector side, @Nullable Particle.DustOptions dust) {
        Location base = player.getLocation().add(0, 0.1, 0);
        int steps = animation.scaleCount(13);
        for (int i = 0; i <= steps; i++) {
            double t = -1.0 + (2.0 * i / steps);
            Location point = base.clone()
                    .add(facing.clone().multiply(1.0))
                    .add(side.clone().multiply(t))
                    .add(0, 1.0 + Math.cos(t * Math.PI / 2) * 0.5, 0);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(),
                base.clone().add(facing.clone().multiply(1.0)).add(0, 1.1, 0), animation.scaleCount(7), 0.35, 0.15, 0.35, 0.02);
    }

    /** Pickaxe: a resonant vein of light running down the face of the mined ore. */
    private static void veinStrike(EquipmentAnimation animation, World world, Location origin,
                                   @Nullable Particle.DustOptions dust) {
        int steps = animation.scaleCount(8);
        for (int i = 0; i <= steps; i++) {
            Location point = origin.clone().add(0, -0.6 + i * 0.2, 0);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (i % 2 == 0) {
                world.spawnParticle(animation.getAccentParticle(), point, 1, 0.12, 0.12, 0.12, 0.0);
            }
            if (dust != null && i % 3 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
    }

    /** Axe: splinters and leaves bursting sideways from the felled trunk. */
    private static void lumberSplash(EquipmentAnimation animation, World world, Location origin,
                                     @Nullable Particle.DustOptions dust) {
        world.spawnParticle(animation.getParticle(), origin, animation.scaleCount(18), 0.6, 0.5, 0.6, 0.12);
        world.spawnParticle(animation.getAccentParticle(), origin.clone().add(0, 1.1, 0), animation.scaleCount(10), 0.5, 0.4, 0.5, 0.03);
        if (dust != null) {
            world.spawnParticle(Particle.DUST, origin, animation.scaleCount(8), 0.5, 0.4, 0.5, 0.05, dust);
        }
    }

    /** Excavator: a dust ring riding across the loosened soil. */
    private static void groundWave(EquipmentAnimation animation, World world, Location origin,
                                   @Nullable Particle.DustOptions dust) {
        for (int radius = 1; radius <= 2; radius++) {
            int steps = animation.scaleCount(radius * 8);
            for (int i = 0; i < steps; i++) {
                double angle = (Math.PI * 2 * i) / steps;
                Location point = origin.clone().add(Math.cos(angle) * radius, 0.1, Math.sin(angle) * radius);
                world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), origin.clone().add(0, 0.35, 0), animation.scaleCount(10), 0.9, 0.15, 0.9, 0.02);
        if (dust != null) {
            world.spawnParticle(Particle.DUST, origin.clone().add(0, 0.5, 0), animation.scaleCount(6), 0.7, 0.2, 0.7, 0.05, dust);
        }
    }

    /** Scythe: a swirl of harvest sparks spiralling up over the reaped crops. */
    private static void harvestSwirl(EquipmentAnimation animation, World world, Location base,
                                     @Nullable Particle.DustOptions dust) {
        int steps = animation.scaleCount(18);
        for (int i = 0; i < steps; i++) {
            double progress = (double) i / steps;
            double angle = progress * Math.PI * 3;
            double radius = 0.75 - progress * 0.25;
            Location point = base.clone().add(Math.cos(angle) * radius, 0.2 + progress * 1.5, Math.sin(angle) * radius);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 3 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), base.clone().add(0, 1.75, 0), animation.scaleCount(6), 0.2, 0.1, 0.2, 0.05);
    }

    /** Fishing rod: water and bubbles dripping down the line as the dredge pulls something up. */
    private static void dredgeDrip(EquipmentAnimation animation, World world, Location origin,
                                   @Nullable Particle.DustOptions dust) {
        for (int level = 0; level < 5; level++) {
            Location point = origin.clone().add(0, 1.6 - level * 0.35, 0);
            world.spawnParticle(animation.getParticle(), point, 1, 0.15, 0, 0.15, 0);
            if (dust != null && level % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), origin.clone().add(0, 0.2, 0), animation.scaleCount(12), 0.3, 0.1, 0.3, 0.08);
    }

    /** Helmet: a halo of ward-light closing around the wearer's head. */
    private static void haloRing(EquipmentAnimation animation, World world, Location base,
                                 @Nullable Particle.DustOptions dust) {
        int ring = animation.scaleCount(14);
        for (int i = 0; i < ring; i++) {
            double angle = (Math.PI * 2 * i) / ring;
            Location point = base.clone().add(Math.cos(angle) * 0.55, 2.25, Math.sin(angle) * 0.55);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), base.clone().add(0, 2.45, 0), animation.scaleCount(6), 0.25, 0.1, 0.25, 0.02);
    }

    /** Chestplate: a dome of dampening force swelling out of the plate. */
    private static void domeBurst(EquipmentAnimation animation, World world, Location base,
                                  @Nullable Particle.DustOptions dust) {
        double[][] rings = {{0.7, 1.0}, {1.15, 0.8}, {1.55, 0.5}};
        for (double[] ring : rings) {
            int points = animation.scaleCount(ring[1] > 0.9 ? 8 : 6);
            for (int i = 0; i < points; i++) {
                double angle = (Math.PI * 2 * i) / points;
                Location point = base.clone().add(Math.cos(angle) * ring[1], ring[0], Math.sin(angle) * ring[1]);
                world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), base.clone().add(0, 1.7, 0), animation.scaleCount(8), 0.4, 0.2, 0.4, 0.04);
        if (dust != null) {
            world.spawnParticle(Particle.DUST, base.clone().add(0, 1.1, 0), animation.scaleCount(10), 0.9, 0.6, 0.9, 0.1, dust);
        }
    }

    /** Leggings: a coil of momentum spinning up around the legs. */
    private static void strideSpiral(EquipmentAnimation animation, World world, Location base,
                                     @Nullable Particle.DustOptions dust) {
        int steps = animation.scaleCount(16);
        for (int i = 0; i < steps; i++) {
            double progress = (double) i / steps;
            double angle = progress * Math.PI * 2.5;
            Location point = base.clone().add(Math.cos(angle) * 0.5, 0.1 + progress * 1.3, Math.sin(angle) * 0.5);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (i % 4 == 0) {
                world.spawnParticle(animation.getAccentParticle(), point, 1, 0, 0, 0, 0);
            }
            if (dust != null && i % 4 == 2) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
    }

    /** Boots: a cushion of air and frost puffing out under the soles. */
    private static void tractionPuff(EquipmentAnimation animation, World world, Location base,
                                     @Nullable Particle.DustOptions dust) {
        int ring = animation.scaleCount(12);
        for (int i = 0; i < ring; i++) {
            double angle = (Math.PI * 2 * i) / ring;
            Location point = base.clone().add(Math.cos(angle) * 0.45, 0.08, Math.sin(angle) * 0.45);
            world.spawnParticle(animation.getParticle(), point, 1, 0, 0, 0, 0);
            if (dust != null && i % 2 == 0) {
                world.spawnParticle(Particle.DUST, point, 1, dust);
            }
        }
        world.spawnParticle(animation.getAccentParticle(), base.clone().add(0, 0.2, 0), animation.scaleCount(9), 0.35, 0.08, 0.35, 0.03);
    }

    // ==========================================
    // COOLDOWN
    // ==========================================

    /**
     * @return {@code true} when the animation may play, recording the new deadline; {@code false}
     *         when the same animation fired too recently for this player.
     */
    private static boolean claimCooldown(@Nonnull UUID playerId, @Nonnull EquipmentAnimation animation) {
        int cooldown = EquipmentAnimation.configuredCooldownMillis();
        if (cooldown <= 0) return true;

        long now = System.currentTimeMillis();
        Map<EquipmentAnimation, Long> perAnimation = COOLDOWNS.computeIfAbsent(playerId, id -> new ConcurrentHashMap<>());
        Long readyAt = perAnimation.get(animation);
        if (readyAt != null && now < readyAt) return false;

        perAnimation.put(animation, now + cooldown);
        return true;
    }
}
