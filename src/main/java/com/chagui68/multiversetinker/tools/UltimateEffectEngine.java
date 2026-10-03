package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.compat.ServerCompat;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Plays every cinematic attack of the plugin: the essence ultimates of {@link EssenceUltimate} and
 * the louder, freezing spectacle of {@link PrimeUltimate}.
 *
 * <p>An ultimate runs in three phases across roughly two seconds: a wind-up that raises rings and
 * streaks around the target, the impact that drops meteors, ice spikes or pillars and lands a heavy
 * blow, and a hold that keeps the enemy rooted inside an animated cage of particles.</p>
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

    /** Freeze ticks applied by a cryo hold phase, enough to keep a mob fully frosted. */
    private static final int CRYO_HOLD_FREEZE = 140;

    private static final int CHARGE_STEPS = 8;
    private static final int HOLD_STEPS = 16;
    private static final long PERIOD_TICKS = 2L;

    private static final Set<UUID> ON_COOLDOWN = ConcurrentHashMap.newKeySet();

    private UltimateEffectEngine() {
    }

    // ==========================================
    // ENTRY POINTS
    // ==========================================
    public static boolean isOnCooldown(@Nonnull Player player) {
        return ON_COOLDOWN.contains(player.getUniqueId());
    }

    /** Test / admin helper: makes a player ready to cast again immediately. */
    public static void resetCooldown(@Nonnull UUID playerId) {
        ON_COOLDOWN.remove(playerId);
    }

    /**
     * Attempts to unleash the weapon's essence ultimate on a struck enemy.
     *
     * @return {@code true} when the spectacle started, {@code false} when it was gated (not enough
     *         essence focus, or the wielder is still on cooldown).
     */
    public static boolean tryTrigger(@Nonnull Plugin plugin,
                                     @Nonnull Player player,
                                     @Nonnull LivingEntity target,
                                     @Nonnull WeaponPerkProfile profile,
                                     double baseDamage) {
        return tryTrigger(plugin, player, target, profile, baseDamage, ForgeTier.MINERAL);
    }

    /**
     * Same as {@link #tryTrigger(Plugin, Player, LivingEntity, WeaponPerkProfile, double)}, scaled by the
     * pedigree of the weapon: an essence ultimate cast from a legendary alloy reaches further, hits harder
     * and fills more of the screen than the same ultimate cast from raw minerals.
     */
    public static boolean tryTrigger(@Nonnull Plugin plugin,
                                     @Nonnull Player player,
                                     @Nonnull LivingEntity target,
                                     @Nonnull WeaponPerkProfile profile,
                                     double baseDamage,
                                     @Nonnull ForgeTier tier) {
        if (profile.getPotency() < MIN_POTENCY) return false;
        if (!beginCooldown(plugin, player)) return false;

        start(plugin, player, target, EssenceUltimate.of(profile.getAffinity()), Math.max(1.0, baseDamage), tier);
        return true;
    }

    /**
     * Unleashes a prime alloy's ultimate. Prime weapons are endgame builds and already demand a
     * legendary alloy, so they skip the essence-focus gate but still share the ultimate cooldown.
     *
     * @return {@code true} when the spectacle started, {@code false} when the wielder is on cooldown.
     */
    public static boolean tryPrimeTrigger(@Nonnull Plugin plugin,
                                          @Nonnull Player player,
                                          @Nonnull LivingEntity target,
                                          @Nonnull PrimeUltimate ultimate,
                                          double baseDamage) {
        return tryPrimeTrigger(plugin, player, target, ultimate, baseDamage, ForgeTier.PRIME);
    }

    /** Prime ultimate scaled by the weapon's pedigree: a mythic prime adds its own finale. */
    public static boolean tryPrimeTrigger(@Nonnull Plugin plugin,
                                          @Nonnull Player player,
                                          @Nonnull LivingEntity target,
                                          @Nonnull PrimeUltimate ultimate,
                                          double baseDamage,
                                          @Nonnull ForgeTier tier) {
        if (!beginCooldown(plugin, player)) return false;

        start(plugin, player, target, ultimate, Math.max(1.0, baseDamage), tier);
        return true;
    }

    /** Geometry multiplier of a pedigree: every level past the first widens the spectacle by 12%. */
    public static double radiusScale(@Nonnull ForgeTier tier) {
        return 1.0 + 0.12 * (tier.getLevel() - 1);
    }

    /** Damage bonus of a pedigree, added to the ultimate's own multiplier. */
    public static double damageBonus(@Nonnull ForgeTier tier) {
        return 0.05 * (tier.getLevel() - 1);
    }

    private static boolean beginCooldown(@Nonnull Plugin plugin, @Nonnull Player player) {
        UUID id = player.getUniqueId();
        if (!ON_COOLDOWN.add(id)) return false;
        Bukkit.getScheduler().runTaskLater(plugin, () -> ON_COOLDOWN.remove(id), COOLDOWN_TICKS);
        return true;
    }

    // ==========================================
    // SPECTACLE
    // ==========================================
    private static void start(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull LivingEntity target,
                              @Nonnull CinematicUltimate ultimate, double baseDamage, @Nonnull ForgeTier tier) {
        World world = target.getWorld();
        Spectacle spectacle = new Spectacle(tier,
                Math.min(4.5, tier.getVisualScale() * EquipmentAnimation.configuredParticleScale()));
        player.sendActionBar(MiniMessage.miniMessage().deserialize(
                "<bold>⚡ " + ultimate.getMiniMessageTag() + "!</bold> <gray>" + ultimate.getDescription() + "</gray>"));
        if (tier.atLeast(ForgeTier.PRIME) && SignatureArtEngine.isMythicTitles()) {
            player.showTitle(Title.title(
                    MiniMessage.miniMessage().deserialize("<bold>⚡ " + ultimate.getMiniMessageTag() + "</bold>"),
                    MiniMessage.miniMessage().deserialize(tier.badge()),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(1400), Duration.ofMillis(400))));
        }

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
                    renderCharge(world, center, ultimate, step, spectacle);
                } else if (!impacted) {
                    impacted = true;
                    renderSkyFall(world, center, ultimate);
                    renderImpact(world, center, ultimate, player, target, baseDamage, spectacle);
                    if (tier == ForgeTier.MYTHIC) {
                        renderMythicImpact(world, center, ultimate, spectacle);
                    }
                    if (!target.isDead()) {
                        target.setVelocity(new Vector(0, 0, 0));
                    }
                } else {
                    renderHold(world, center, ultimate, step - CHARGE_STEPS, spectacle);
                    if (!target.isDead()) {
                        target.setVelocity(new Vector(0, 0, 0));
                        if (ultimate.getFreezeTicks() > 0) {
                            target.setFreezeTicks(Math.max(target.getFreezeTicks(), CRYO_HOLD_FREEZE));
                        }
                    }
                }

                if (step++ > CHARGE_STEPS + HOLD_STEPS) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, PERIOD_TICKS);
    }

    private static void renderCharge(@Nonnull World world, @Nonnull Location center,
                                     @Nonnull CinematicUltimate ultimate, int step, @Nonnull Spectacle spectacle) {
        double progress = (step + 1) / (double) CHARGE_STEPS;
        double radius = (0.7 + progress * 2.3) * radiusScale(spectacle.tier());

        // Ground ring that swells while the ultimate charges.
        int points = spectacle.count(14);
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI * i / points) + (step * 0.35);
            Location ring = center.clone().add(Math.cos(angle) * radius, 0.15, Math.sin(angle) * radius);
            world.spawnParticle(ultimate.getTrailParticle(), ring, 1, 0, 0, 0, 0.0);
        }

        // Rising streaks that climb skyward; a meteor storm charges through more lanes.
        int lanes = (ultimate.getAnimation() == CinematicUltimate.Animation.METEOR_STORM ? 6 : 3)
                + Math.max(0, spectacle.tier().getLevel() - 2);
        for (int i = 0; i < lanes; i++) {
            double angle = (2 * Math.PI * i / lanes) + (step * 0.5);
            Location streak = center.clone().add(
                    Math.cos(angle) * (radius + 0.6), 2.2 + progress * 2.2, Math.sin(angle) * (radius + 0.6));
            world.spawnParticle(ultimate.getAccentParticle(), streak, 2, 0.05, 0.05, 0.05, 0.0);
        }

        if (ultimate.getAnimation() == CinematicUltimate.Animation.CRYO) {
            renderCryoCharge(world, center, radius, step);
        }

        spawnColoredDust(world, center.clone().add(0, 0.25, 0), spectacle.count(4), ultimate);
        if (spectacle.tier().atLeast(ForgeTier.LEGENDARY)) {
            // A second, counter-rotating ring in the pedigree's own colours.
            Particle.DustOptions tierDust = new Particle.DustOptions(spectacle.tier().color(), 1.4f);
            int tierPoints = spectacle.count(10);
            for (int i = 0; i < tierPoints; i++) {
                double angle = (2 * Math.PI * i / tierPoints) - (step * 0.45);
                Location ring = center.clone().add(Math.cos(angle) * radius * 0.7, 0.6, Math.sin(angle) * radius * 0.7);
                world.spawnParticle(Particle.DUST, ring, 1, 0, 0, 0, 0.0, tierDust);
            }
        }
        if (step == 0 || step == CHARGE_STEPS - 1) {
            world.playSound(center, ultimate.getChargeSound(), 1.1f, (float) (0.6 + progress * 0.8));
        }
    }

    /** Freezing wind-up: frost creeps across the floor and snow falls from the sky. */
    private static void renderCryoCharge(@Nonnull World world, @Nonnull Location center,
                                         double radius, int step) {
        world.spawnParticle(Particle.SNOWFLAKE, center.clone().add(0, 0.1, 0), 14,
                radius * 0.6, 0.1, radius * 0.6, 0.01);
        world.spawnParticle(Particle.WHITE_ASH, center.clone().add(0, 4.0 + step * 0.4, 0), 10,
                radius * 0.8, 0.6, radius * 0.8, 0.02);
        if (step % 3 == 0) {
            world.playSound(center, Sound.BLOCK_POWDER_SNOW_STEP, 0.8f, (float) (1.4 - step * 0.05));
        }
    }

    private static void renderSkyFall(@Nonnull World world, @Nonnull Location center,
                                      @Nonnull CinematicUltimate ultimate) {
        switch (ultimate.getAnimation()) {
            case METEOR -> {
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
            case METEOR_STORM -> renderMeteorStorm(world, center, ultimate);
            default -> {
                // Other animations fall straight to the impact.
            }
        }
    }

    /** A spiral storm of burning rock: eight meteors spiral down into the epicentre. */
    private static void renderMeteorStorm(@Nonnull World world, @Nonnull Location center,
                                          @Nonnull CinematicUltimate ultimate) {
        int meteors = 8;
        for (int m = 0; m < meteors; m++) {
            double angle = (2 * Math.PI * m / meteors) + 1.1;
            double spiral = 2.2 + (m % 3) * 1.4;
            Location from = center.clone().add(Math.cos(angle) * spiral, 16.0 + (m % 2) * 3.0, Math.sin(angle) * spiral);
            world.playSound(from, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.2f, 0.6f + (m * 0.05f));
            for (int i = 0; i < 20; i++) {
                double shrink = 1.0 - (i / 20.0);
                Location point = from.clone().add(
                        -Math.cos(angle) * i * 0.11 * shrink,
                        -i * 0.85,
                        -Math.sin(angle) * i * 0.11 * shrink);
                world.spawnParticle(ultimate.getAccentParticle(), point, 2, 0.07, 0.07, 0.07, 0.0);
                world.spawnParticle(Particle.SMOKE, point, 1, 0.05, 0.05, 0.05, 0.0);
                if (i % 4 == 0) {
                    spawnColoredDust(world, point, 1, ultimate);
                }
            }
            world.spawnParticle(Particle.LAVA, center.clone().add(Math.cos(angle) * 1.2, 0.4, Math.sin(angle) * 1.2), 4);
        }
    }

    private static void renderImpact(@Nonnull World world, @Nonnull Location center,
                                     @Nonnull CinematicUltimate ultimate, @Nonnull Player player,
                                     @Nonnull LivingEntity target, double baseDamage,
                                     @Nonnull Spectacle spectacle) {
        double radius = ultimate.getRadius() * radiusScale(spectacle.tier());

        world.playSound(center, ultimate.getImpactSound(), 1.6f, 0.9f);
        world.spawnParticle(Particle.EXPLOSION_EMITTER, center, 1);
        switch (ultimate.getAnimation()) {
            case PILLAR, VORTEX, NOVA -> world.spawnParticle(Particle.SONIC_BOOM, center, 1);
            case CRYO -> renderIceField(world, center, radius, ultimate);
            case METEOR_STORM -> world.spawnParticle(Particle.EXPLOSION_EMITTER, center.clone().add(0, 1, 0), 2);
            default -> {
                // QUAKE, CAGE and METEOR rely on the shockwave rings only.
            }
        }

        // Expanding shockwave rings: one more per pedigree level past composite.
        int rings = 3 + Math.max(0, spectacle.tier().getLevel() - 2);
        for (int ring = 0; ring < rings; ring++) {
            double r = radius * (0.4 + (ring * 0.6 / Math.max(1, rings - 1)));
            int points = spectacle.count(22);
            for (int i = 0; i < points; i++) {
                double angle = (2 * Math.PI * i / points) + (ring * 0.2);
                Location point = center.clone().add(Math.cos(angle) * r, 0.25, Math.sin(angle) * r);
                world.spawnParticle(ultimate.getTrailParticle(), point, 1, 0, 0, 0, 0.0);
                spawnColoredDust(world, point, 1, ultimate);
            }
        }

        double damage = baseDamage * (ultimate.getDamageMultiplier() + damageBonus(spectacle.tier()));

        target.setNoDamageTicks(0);
        target.damage(damage, player);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, ultimate.getRootTicks(), 6, false, true));
        if (ultimate.getFreezeTicks() > 0) {
            target.setFreezeTicks(Math.max(target.getFreezeTicks(), ultimate.getFreezeTicks()));
            world.spawnParticle(Particle.SNOWFLAKE, center, 40, radius * 0.5, 0.8, radius * 0.5, 0.05);
        }
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
                if (ultimate.getFreezeTicks() > 0) {
                    mob.setFreezeTicks(Math.max(mob.getFreezeTicks(), ultimate.getFreezeTicks()));
                }
            }
        }

        if (ultimate == EssenceUltimate.ASCENDANT || ultimate == PrimeUltimate.PRISMATIC_ASCENSION) {
            player.setHealth(Math.min(ServerCompat.maxHealth(player), player.getHealth() + 4.0));
            world.spawnParticle(Particle.HEART, player.getLocation().add(0, 1.6, 0), 6, 0.3, 0.3, 0.3, 0.0);
        }
    }

    /** The freezing payoff: ice spikes erupt out of the ground in a ring around the epicentre. */
    private static void renderIceField(@Nonnull World world, @Nonnull Location center,
                                       double radius, @Nonnull CinematicUltimate ultimate) {
        int spikes = 10;
        for (int s = 0; s < spikes; s++) {
            double angle = 2 * Math.PI * s / spikes;
            double r = radius * 0.85;
            Location base = center.clone().add(Math.cos(angle) * r, 0, Math.sin(angle) * r);
            for (int h = 0; h < 7; h++) {
                Location point = base.clone().add(0, h * 0.35, 0);
                world.spawnParticle(Particle.BLOCK, point, 2, 0.12, 0.12, 0.12, 0.0, Material.PACKED_ICE.createBlockData());
                if (h % 2 == 0) {
                    world.spawnParticle(Particle.SNOWFLAKE, point, 1, 0, 0, 0, 0.0);
                }
            }
        }
        world.spawnParticle(Particle.BLOCK, center, 40, radius * 0.6, 0.3, radius * 0.6, 0.02,
                Material.BLUE_ICE.createBlockData());
        world.playSound(center, Sound.BLOCK_GLASS_BREAK, 1.4f, 0.7f);
        world.playSound(center, Sound.BLOCK_POWDER_SNOW_BREAK, 1.4f, 0.8f);
        spawnColoredDust(world, center, 20, ultimate);
    }

    private static void renderHold(@Nonnull World world, @Nonnull Location center,
                                   @Nonnull CinematicUltimate ultimate, int holdStep, @Nonnull Spectacle spectacle) {
        double angleBase = holdStep * 0.35;
        int points = spectacle.count(8);
        for (int i = 0; i < points; i++) {
            double angle = angleBase + (2 * Math.PI * i / points);
            Location point = center.clone().add(
                    Math.cos(angle) * 1.3, 0.55 + (Math.sin(holdStep * 0.4) * 0.2), Math.sin(angle) * 1.3);
            world.spawnParticle(ultimate.getTrailParticle(), point, 1, 0, 0, 0, 0.0);
        }

        if (ultimate.getFreezeTicks() > 0 && holdStep % 2 == 0) {
            world.spawnParticle(Particle.SNOWFLAKE, center, 6, 0.7, 1.2, 0.7, 0.01);
        }

        if (holdStep % 3 == 0) {
            world.spawnParticle(ultimate.getAccentParticle(), center, 3, 0.3, 0.6, 0.3, 0.0);
            spawnColoredDust(world, center, 3, ultimate);
        }
    }

    /** A mythic weapon's ultimate tears the sky open: thunder around the epicentre and a column of light. */
    private static void renderMythicImpact(@Nonnull World world, @Nonnull Location center,
                                           @Nonnull CinematicUltimate ultimate, @Nonnull Spectacle spectacle) {
        for (int i = 0; i < 3; i++) {
            double angle = i * (2 * Math.PI / 3);
            Location bolt = center.clone().add(Math.cos(angle) * 3.0, -1.0, Math.sin(angle) * 3.0);
            try {
                world.strikeLightningEffect(bolt);
            } catch (RuntimeException ignored) {
                // Purely cosmetic; some server implementations cannot render it.
            }
        }
        for (int i = 0; i < 30; i++) {
            Location column = center.clone().add(0, i * 0.4, 0);
            world.spawnParticle(Particle.END_ROD, column, 1, 0.12, 0.0, 0.12, 0.0);
            if (i % 2 == 0) spawnColoredDust(world, column, 2, ultimate);
        }
        world.spawnParticle(Particle.TOTEM_OF_UNDYING, center, spectacle.count(30), 1.5, 1.0, 1.5, 0.4);
        world.playSound(center, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.8f, 1.1f);
    }

    /** Pedigree of the weapon that cast an ultimate, and the particle multiplier it earned. */
    private record Spectacle(@Nonnull ForgeTier tier, double scale) {
        int count(int base) {
            return Math.max(1, (int) Math.round(base * scale));
        }
    }

    private static void spawnColoredDust(@Nonnull World world, @Nonnull Location location,
                                         int count, @Nonnull CinematicUltimate ultimate) {
        world.spawnParticle(Particle.DUST, location, count, 0.15, 0.15, 0.15, 0.0,
                new Particle.DustOptions(ultimate.getColor(), 1.3f));
    }
}
