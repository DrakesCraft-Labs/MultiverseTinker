package com.chagui68.multiversetinker.tools;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Plays the cinematic attack ultimates of {@link EssenceUltimate}.
 *
 * <p>An ultimate runs in three phases across roughly two seconds: a wind-up that raises rings and
 * streaks around the target, the impact that drops meteors or pillars and lands a heavy blow, and
 * a hold that keeps the enemy rooted inside an animated cage of particles.</p>
 *
 * <p>Triggers are gated by the weapon's essence focus and by a long per-player cooldown, so the
 * spectacle stays special instead of becoming the default attack.</p>
 */
public final class UltimateEffectEngine {

    /** A player can unleash an ultimate this often (ticks). */
    public static final long COOLDOWN_TICKS = 400L;

    /**
     * Minimum essence focus of the weapon before ultimates unlock. A three-part weapon forged
     * entirely from one essence' minerals sits at 1.0, an exotic head with a common handle at 0.5,
     * so ultimates demand a deliberately focused build.
     */
    public static final double MIN_POTENCY = 0.8;

    private static final int CHARGE_STEPS = 8;
    private static final int HOLD_STEPS = 16;
    private static final long PERIOD_TICKS = 2L;

    private static final Set<UUID> ON_COOLDOWN = ConcurrentHashMap.newKeySet();

    private UltimateEffectEngine() {
    }

    // ==========================================
    // ENTRY POINT
    // ==========================================
    public static boolean isOnCooldown(@Nonnull Player player) {
        return ON_COOLDOWN.contains(player.getUniqueId());
    }

    /** Test / admin helper: makes a player ready to cast again immediately. */
    public static void resetCooldown(@Nonnull UUID playerId) {
        ON_COOLDOWN.remove(playerId);
    }

    /**
     * Attempts to unleash the weapon's ultimate on a struck enemy.
     *
     * @return {@code true} when the spectacle started, {@code false} when it was gated (not enough
     *         essence focus, or the wielder is still on cooldown).
     */
    public static boolean tryTrigger(@Nonnull Plugin plugin,
                                     @Nonnull Player player,
                                     @Nonnull LivingEntity target,
                                     @Nonnull WeaponPerkProfile profile,
                                     double baseDamage) {
        if (profile.getPotency() < MIN_POTENCY) return false;
        if (!ON_COOLDOWN.add(player.getUniqueId())) return false;

        UUID id = player.getUniqueId();
        Bukkit.getScheduler().runTaskLater(plugin, () -> ON_COOLDOWN.remove(id), COOLDOWN_TICKS);

        start(plugin, player, target, EssenceUltimate.of(profile.getAffinity()), Math.max(1.0, baseDamage));
        return true;
    }

    // ==========================================
    // SPECTACLE
    // ==========================================
    private static void start(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull LivingEntity target,
                              @Nonnull EssenceUltimate ultimate, double baseDamage) {
        World world = target.getWorld();
        player.sendActionBar(MiniMessage.miniMessage().deserialize(
                "<bold>⚡ " + ultimate.getMiniMessageTag() + "!</bold> <gray>" + ultimate.getDescription() + "</gray>"));

        new BukkitRunnable() {
            private int step = 0;
            private boolean impacted = false;

            @Override
            public void run() {
                if (target.isDead()) {
                    cancel();
                    return;
                }

                Location center = target.getLocation().add(0, 1.0, 0);
                if (step < CHARGE_STEPS) {
                    renderCharge(world, center, ultimate, step);
                } else if (!impacted) {
                    impacted = true;
                    renderSkyFall(world, center, ultimate);
                    renderImpact(world, center, ultimate, player, target, baseDamage);
                    if (!target.isDead()) {
                        target.setVelocity(new Vector(0, 0, 0));
                    }
                } else {
                    renderHold(world, center, ultimate, step - CHARGE_STEPS);
                    if (!target.isDead()) {
                        target.setVelocity(new Vector(0, 0, 0));
                    }
                }

                if (step++ > CHARGE_STEPS + HOLD_STEPS) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, PERIOD_TICKS);
    }

    private static void renderCharge(@Nonnull World world, @Nonnull Location center,
                                     @Nonnull EssenceUltimate ultimate, int step) {
        double progress = (step + 1) / (double) CHARGE_STEPS;
        double radius = 0.7 + progress * 2.3;

        // Ground ring that swells while the ultimate charges.
        int points = 14;
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI * i / points) + (step * 0.35);
            Location ring = center.clone().add(Math.cos(angle) * radius, 0.15, Math.sin(angle) * radius);
            world.spawnParticle(ultimate.getTrailParticle(), ring, 1, 0, 0, 0, 0.0);
        }

        // Rising streaks that climb skyward.
        for (int i = 0; i < 3; i++) {
            double angle = (2 * Math.PI * i / 3) + (step * 0.5);
            Location streak = center.clone().add(
                    Math.cos(angle) * (radius + 0.6), 2.2 + progress * 2.2, Math.sin(angle) * (radius + 0.6));
            world.spawnParticle(ultimate.getAccentParticle(), streak, 2, 0.05, 0.05, 0.05, 0.0);
        }

        spawnColoredDust(world, center.clone().add(0, 0.25, 0), 4, ultimate);
        if (step == 0 || step == CHARGE_STEPS - 1) {
            world.playSound(center, ultimate.getChargeSound(), 1.1f, (float) (0.6 + progress * 0.8));
        }
    }

    private static void renderSkyFall(@Nonnull World world, @Nonnull Location center,
                                      @Nonnull EssenceUltimate ultimate) {
        if (ultimate.getAnimation() != EssenceUltimate.Animation.METEOR) return;

        for (int lane = 0; lane < 3; lane++) {
            double angle = (2 * Math.PI * lane / 3) + 0.4;
            Location from = center.clone().add(Math.cos(angle) * 3.0, 13.0, Math.sin(angle) * 3.0);
            for (int i = 0; i < 16; i++) {
                Location point = from.clone().add(
                        -Math.cos(angle) * i * 0.18, -i * 0.8, -Math.sin(angle) * i * 0.18);
                world.spawnParticle(ultimate.getAccentParticle(), point, 2, 0.06, 0.06, 0.06, 0.0);
            }
            world.playSound(from, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.3f, 0.7f + (lane * 0.1f));
        }
    }

    private static void renderImpact(@Nonnull World world, @Nonnull Location center,
                                     @Nonnull EssenceUltimate ultimate, @Nonnull Player player,
                                     @Nonnull LivingEntity target, double baseDamage) {
        double radius = ultimate.getRadius();

        world.playSound(center, ultimate.getImpactSound(), 1.6f, 0.9f);
        world.spawnParticle(Particle.EXPLOSION_EMITTER, center, 1);
        if (ultimate.getAnimation() == EssenceUltimate.Animation.PILLAR
                || ultimate.getAnimation() == EssenceUltimate.Animation.VORTEX
                || ultimate.getAnimation() == EssenceUltimate.Animation.NOVA) {
            world.spawnParticle(Particle.SONIC_BOOM, center, 1);
        }

        // Expanding shockwave rings.
        for (int ring = 0; ring < 3; ring++) {
            double r = radius * (0.4 + (ring * 0.3));
            int points = 22;
            for (int i = 0; i < points; i++) {
                double angle = (2 * Math.PI * i / points) + (ring * 0.2);
                Location point = center.clone().add(Math.cos(angle) * r, 0.25, Math.sin(angle) * r);
                world.spawnParticle(ultimate.getTrailParticle(), point, 1, 0, 0, 0, 0.0);
                spawnColoredDust(world, point, 1, ultimate);
            }
        }

        double damage = baseDamage * ultimate.getDamageMultiplier();

        target.setNoDamageTicks(0);
        target.damage(damage, player);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, ultimate.getRootTicks(), 6, false, true));
        target.setVelocity(new Vector(0, 0, 0));
        spawnColoredDust(world, center, 12, ultimate);

        for (Entity nearby : world.getNearbyEntities(center, radius, radius, radius)) {
            if (nearby instanceof LivingEntity mob && !mob.equals(player) && !mob.equals(target)) {
                mob.setNoDamageTicks(0);
                mob.damage(damage * 0.6, player);
                Vector push = mob.getLocation().toVector().subtract(center.toVector());
                if (push.lengthSquared() > 0.0001) {
                    mob.setVelocity(push.normalize().multiply(0.7).setY(0.3));
                }
            }
        }

        if (ultimate == EssenceUltimate.ASCENDANT) {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 4.0));
            world.spawnParticle(Particle.HEART, player.getLocation().add(0, 1.6, 0), 6, 0.3, 0.3, 0.3, 0.0);
        }
    }

    private static void renderHold(@Nonnull World world, @Nonnull Location center,
                                   @Nonnull EssenceUltimate ultimate, int holdStep) {
        double angleBase = holdStep * 0.35;
        for (int i = 0; i < 8; i++) {
            double angle = angleBase + (2 * Math.PI * i / 8);
            Location point = center.clone().add(
                    Math.cos(angle) * 1.3, 0.55 + (Math.sin(holdStep * 0.4) * 0.2), Math.sin(angle) * 1.3);
            world.spawnParticle(ultimate.getTrailParticle(), point, 1, 0, 0, 0, 0.0);
        }

        if (holdStep % 3 == 0) {
            world.spawnParticle(ultimate.getAccentParticle(), center, 3, 0.3, 0.6, 0.3, 0.0);
            spawnColoredDust(world, center, 3, ultimate);
        }
    }

    private static void spawnColoredDust(@Nonnull World world, @Nonnull Location location,
                                         int count, @Nonnull EssenceUltimate ultimate) {
        world.spawnParticle(Particle.DUST, location, count, 0.15, 0.15, 0.15, 0.0,
                new Particle.DustOptions(ultimate.getColor(), 1.3f));
    }
}
