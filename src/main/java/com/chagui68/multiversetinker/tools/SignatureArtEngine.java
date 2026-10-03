package com.chagui68.multiversetinker.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.data.BlockData;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boss;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;
import java.util.logging.Level;

/**
 * Plays every {@link SignatureArt}: the mechanic and the choreography an alloy awakens in a weapon.
 *
 * <p>Every art is a short timeline of frames, one per tick. A composite plays a single fusion shape; a
 * legendary plays its own dedicated choreography (sixteen of them, one method each); a prime opens with
 * an ascension prelude, plays the legendary choreography at its own, bigger pedigree scale and closes
 * with an overlay chosen by its second ingredient; a mythic adds a finale that takes over the sky —
 * thunder, glowing obelisks rising out of the ground and a title on the wielder's screen.</p>
 *
 * <p>The cosmetic half honours the {@code animations} section of {@code config.yml} (an art still lands
 * its mechanic when animations are switched off); the gating half reads {@code signature-arts}. All the
 * area damage an art deals runs inside the weapon-perk guard of {@link ModularToolListener}, so an art can
 * never re-trigger perks, ultimates or another art through the damage events it causes.</p>
 */
public final class SignatureArtEngine {

    /** Config keys read from {@code config.yml}. */
    public static final String CONFIG_ENABLED = "signature-arts.enabled";
    public static final String CONFIG_CHANCE_MULTIPLIER = "signature-arts.chance-multiplier";
    public static final String CONFIG_COOLDOWN_MULTIPLIER = "signature-arts.cooldown-multiplier";
    public static final String CONFIG_MYTHIC_TITLES = "signature-arts.mythic-titles";
    public static final String CONFIG_DISPLAY_ENTITIES = "signature-arts.display-entities";

    public static final double MAX_CHANCE_MULTIPLIER = 5.0;
    public static final double MIN_COOLDOWN_MULTIPLIER = 0.1;
    public static final double MAX_COOLDOWN_MULTIPLIER = 10.0;

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private static volatile boolean enabled = true;
    private static volatile double chanceMultiplier = 1.0;
    private static volatile double cooldownMultiplier = 1.0;
    private static volatile boolean mythicTitles = true;
    private static volatile boolean displayEntities = true;

    /**
     * Applies one mineral's own forge trait (Ruby's flame edge, Malachite's poison, Gravitite's lift…) to a
     * victim. The modular tool listener owns those traits and installs itself here on startup.
     */
    @FunctionalInterface
    public interface TraitEcho {
        void echo(@Nonnull Player wielder, @Nonnull LivingEntity victim, @Nonnull String materialId);
    }

    private static volatile TraitEcho traitEcho;

    private static final Set<UUID> ON_COOLDOWN = ConcurrentHashMap.newKeySet();
    /** Display entities an art spawned and has not removed yet; cleared on disable. */
    private static final Set<Entity> DISPLAYS = ConcurrentHashMap.newKeySet();

    private SignatureArtEngine() {
    }

    // ==========================================
    // CONFIGURATION
    // ==========================================

    public static void configure(boolean artsEnabled, double chance, double cooldown,
                                 boolean titles, boolean displays) {
        enabled = artsEnabled;
        chanceMultiplier = Math.max(0.0, Math.min(MAX_CHANCE_MULTIPLIER, chance));
        cooldownMultiplier = Math.max(MIN_COOLDOWN_MULTIPLIER, Math.min(MAX_COOLDOWN_MULTIPLIER, cooldown));
        mythicTitles = titles;
        displayEntities = displays;
    }

    public static void reset() {
        configure(true, 1.0, 1.0, true, true);
    }

    /** Installs the hook that lets an art echo its minerals' own forge traits. */
    public static void setTraitEcho(@Nullable TraitEcho echo) {
        traitEcho = echo;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static double configuredChanceMultiplier() {
        return chanceMultiplier;
    }

    public static double configuredCooldownMultiplier() {
        return cooldownMultiplier;
    }

    public static boolean isMythicTitles() {
        return mythicTitles;
    }

    public static boolean isDisplayEntities() {
        return displayEntities;
    }

    // ==========================================
    // ENTRY POINTS
    // ==========================================

    public static boolean isOnCooldown(@Nonnull Player player) {
        return ON_COOLDOWN.contains(player.getUniqueId());
    }

    /** Test / admin helper: lets a player cast again immediately. */
    public static void resetCooldown(@Nonnull UUID playerId) {
        ON_COOLDOWN.remove(playerId);
    }

    /** How many ticks a wielder waits between two arts of this pedigree, with the configured multiplier. */
    public static long cooldownTicks(@Nonnull ForgeTier tier) {
        return Math.max(20L, Math.round(tier.getArtCooldownTicks() * cooldownMultiplier));
    }

    /**
     * Rolls the art of a weapon on a landed hit.
     *
     * @return {@code true} when the art started
     */
    public static boolean tryTrigger(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull LivingEntity target,
                                     @Nonnull SignatureArt art, double baseDamage) {
        return tryTrigger(plugin, player, target, art, baseDamage, ThreadLocalRandom.current().nextDouble());
    }

    /** Same as {@link #tryTrigger(Plugin, Player, LivingEntity, SignatureArt, double)} with a fixed roll. */
    public static boolean tryTrigger(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull LivingEntity target,
                                     @Nonnull SignatureArt art, double baseDamage, double roll) {
        if (!enabled) return false;
        double chance = Math.min(1.0, art.tier().getArtChance() * chanceMultiplier);
        if (chance <= 0.0 || roll >= chance) return false;
        UUID id = player.getUniqueId();
        if (!ON_COOLDOWN.add(id)) return false;
        Bukkit.getScheduler().runTaskLater(plugin, () -> ON_COOLDOWN.remove(id), cooldownTicks(art.tier()));
        play(plugin, player, target, art, baseDamage);
        return true;
    }

    /** Plays an art unconditionally: no chance roll and no cooldown. */
    public static void play(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull LivingEntity target,
                            @Nonnull SignatureArt art, double baseDamage) {
        Ctx ctx = new Ctx(plugin, player, target, art, Math.max(1.0, baseDamage));
        announce(ctx);

        ForgeTier tier = art.tier();
        int cursor = 0;
        if (tier.atLeast(ForgeTier.PRIME)) {
            cursor = prelude(ctx, cursor);
        }

        LegendaryArt legendary = art.legendary();
        if (legendary != null) {
            cursor = legendary(ctx, legendary, cursor);
        } else {
            cursor = fusion(ctx, art.payload(), art.shape(), cursor);
        }

        if (tier.atLeast(ForgeTier.PRIME)) {
            Ctx overlay = ctx.withPower(0.6);
            if (art.secondary() != null) {
                cursor = legendary(overlay.withColors(art.secondary().getColor(), art.secondary().getAccent()),
                        art.secondary(), cursor);
            } else if (art.catalyst() != null) {
                cursor = catalystOverlay(overlay, art.catalyst(), cursor);
            } else {
                cursor = fusion(overlay, art.payload(), art.shape(), cursor);
            }
        }

        if (tier == ForgeTier.MYTHIC) {
            mythicFinale(ctx, cursor);
        }
    }

    /**
     * The Forge's answer when an alloy weapon is assembled: the art announces itself around the smith,
     * as loud as its pedigree.
     */
    public static void playForgeAwakening(@Nonnull Plugin plugin, @Nonnull Player player, @Nonnull SignatureArt art) {
        ForgeTier tier = art.tier();
        World world = player.getWorld();
        Particle.DustTransition dust = new Particle.DustTransition(art.color(), art.accent(), 1.3f);
        Particle.DustTransition tierDust = new Particle.DustTransition(tier.color(), tier.accent(), 1.5f);
        double scale = cosmeticScale(tier);
        boolean cosmetic = EquipmentAnimation.isEnabled();

        new BukkitRunnable() {
            private int t = 0;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }
                Location base = player.getLocation();
                if (cosmetic) {
                    int strands = 1 + tier.getLevel() / 2;
                    for (int s = 0; s < strands; s++) {
                        double angle = t * 0.45 + s * (Math.PI * 2 / strands);
                        double r = 1.1 - t * 0.02;
                        Location p = base.clone().add(Math.cos(angle) * r, t * 0.1, Math.sin(angle) * r);
                        world.spawnParticle(Particle.DUST_COLOR_TRANSITION, p, Math.max(1, (int) Math.round(2 * scale)),
                                0.03, 0.03, 0.03, 0.0, s % 2 == 0 ? dust : tierDust);
                    }
                }
                if (t == 0) {
                    playSound(world, base, Sound.BLOCK_SMITHING_TABLE_USE, 1.0f, 0.8f);
                }
                if (t == 24) {
                    if (cosmetic) {
                        world.spawnParticle(Particle.FIREWORK, base.clone().add(0, 2.4, 0),
                                (int) Math.round(18 * scale), 0.4, 0.4, 0.4, 0.12);
                        if (tier.atLeast(ForgeTier.LEGENDARY)) {
                            world.spawnParticle(Particle.TOTEM_OF_UNDYING, base.clone().add(0, 1.2, 0),
                                    (int) Math.round(30 * scale), 0.5, 0.8, 0.5, 0.35);
                        }
                    }
                    switch (tier) {
                        case COMPOSITE -> playSound(world, base, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.2f);
                        case LEGENDARY -> playSound(world, base, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.9f, 1.0f);
                        case PRIME -> {
                            playSound(world, base, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 0.9f);
                            playSound(world, base, Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.2f);
                        }
                        case MYTHIC -> {
                            playSound(world, base, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 0.8f);
                            playSound(world, base, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1.3f);
                            if (mythicTitles) {
                                player.showTitle(Title.title(MM.deserialize(art.miniName()),
                                        MM.deserialize(tier.gradient("✦ Mythic Art Awakened ✦")),
                                        Title.Times.times(Duration.ofMillis(200), Duration.ofMillis(1800), Duration.ofMillis(600))));
                            }
                        }
                        default -> {
                        }
                    }
                    cancel();
                    return;
                }
                t++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    /** Removes every display entity an art left behind; the plugin calls this on disable. */
    public static void cleanup() {
        for (Entity entity : DISPLAYS) {
            try {
                entity.remove();
            } catch (RuntimeException ignored) {
                // The world may already be unloading.
            }
        }
        DISPLAYS.clear();
        ON_COOLDOWN.clear();
    }

    // ==========================================
    // CONTEXT
    // ==========================================

    /** Everything one running art needs: who, where, how big and how hard. */
    private static final class Ctx {
        final Plugin plugin;
        final Player player;
        final LivingEntity target;
        final World world;
        final SignatureArt art;
        final ForgeTier tier;
        final double base;
        final double scale;
        final double radius;
        final boolean cosmetic;
        final Particle.DustOptions main;
        final Particle.DustOptions accent;
        final Particle.DustTransition transition;
        final double powerScale;
        final double power;
        Location last;

        Ctx(Plugin plugin, Player player, LivingEntity target, SignatureArt art, double base) {
            this(plugin, player, target, art, base, art.color(), art.accent(), 1.0,
                    target.getLocation().add(0, 1.0, 0));
        }

        private Ctx(Plugin plugin, Player player, LivingEntity target, SignatureArt art, double base,
                    Color main, Color accent, double powerScale, Location last) {
            this.powerScale = powerScale;
            this.plugin = plugin;
            this.player = player;
            this.target = target;
            this.world = target.getWorld();
            this.art = art;
            this.tier = art.tier();
            this.base = base;
            this.scale = cosmeticScale(tier);
            this.radius = tier.getArtRadius();
            this.cosmetic = EquipmentAnimation.isEnabled();
            this.main = new Particle.DustOptions(main, (float) Math.min(2.4, 1.0 + 0.15 * tier.getLevel()));
            this.accent = new Particle.DustOptions(accent, (float) Math.min(2.2, 0.9 + 0.12 * tier.getLevel()));
            this.transition = new Particle.DustTransition(main, accent, (float) Math.min(2.4, 1.0 + 0.15 * tier.getLevel()));
            this.power = tier.getArtDamageMultiplier() * powerScale;
            this.last = last;
        }

        Ctx withColors(Color main, Color accent) {
            return new Ctx(plugin, player, target, art, base, main, accent, powerScale, last);
        }

        Ctx withPower(double share) {
            return new Ctx(plugin, player, target, art, base, this.main.getColor(), this.accent.getColor(),
                    powerScale * share, last);
        }

        /** Chest height of the target while it lives, its last known position afterwards. */
        Location center() {
            if (target.isValid() && !target.isDead() && target.getWorld().equals(world)) {
                last = target.getLocation().add(0, 1.0, 0);
            }
            return last.clone();
        }

        Location ground() {
            return center().subtract(0, 0.9, 0);
        }

        /** Particle count scaled by pedigree and configuration; zero when animations are off. */
        int n(int count) {
            if (!cosmetic) return 0;
            return Math.max(1, (int) Math.round(count * scale));
        }

        double damage(double share) {
            return base * power * share;
        }

        /** Horizontal direction from the wielder to the target. */
        Vector facing() {
            Vector v = center().toVector().subtract(player.getLocation().toVector()).setY(0);
            if (v.lengthSquared() < 1.0E-4) v = player.getLocation().getDirection().setY(0);
            if (v.lengthSquared() < 1.0E-4) v = new Vector(0, 0, 1);
            return v.normalize();
        }

        Vector side() {
            Vector f = facing();
            return new Vector(-f.getZ(), 0, f.getX());
        }
    }

    private static double cosmeticScale(@Nonnull ForgeTier tier) {
        return Math.min(4.5, tier.getVisualScale() * EquipmentAnimation.configuredParticleScale());
    }

    // ==========================================
    // TIMELINE
    // ==========================================

    /** Runs {@code frame} once per tick for {@code duration + 1} ticks, starting {@code start} ticks from now. */
    private static void timeline(@Nonnull Ctx ctx, int start, int duration, @Nonnull IntConsumer frame) {
        new BukkitRunnable() {
            private int t = 0;

            @Override
            public void run() {
                try {
                    frame.accept(t);
                } catch (RuntimeException e) {
                    ctx.plugin.getLogger().log(Level.WARNING, "Signature art " + ctx.art.name() + " stopped: " + e.getMessage(), e);
                    cancel();
                    return;
                }
                if (++t > duration) cancel();
            }
        }.runTaskTimer(ctx.plugin, Math.max(0, start), 1L);
    }

    private static void announce(@Nonnull Ctx ctx) {
        String label = switch (ctx.tier) {
            case COMPOSITE -> "Fusion Art";
            case LEGENDARY -> "Legendary Art";
            case PRIME -> "Prime Art";
            case MYTHIC -> "Mythic Art";
            default -> "Art";
        };
        ctx.player.sendActionBar(MM.deserialize("<b>" + ctx.tier.gradient("✦ " + label) + "</b> <dark_gray>»</dark_gray> "
                + ctx.art.miniName()));

        if (!mythicTitles) return;
        Title.Times times = Title.Times.times(Duration.ofMillis(120), Duration.ofMillis(1500), Duration.ofMillis(500));
        if (ctx.tier == ForgeTier.MYTHIC) {
            ctx.player.showTitle(Title.title(MM.deserialize(ctx.art.miniName()), MM.deserialize(ctx.tier.badge()), times));
        } else if (ctx.tier == ForgeTier.PRIME) {
            ctx.player.showTitle(Title.title(Component.empty(), MM.deserialize(ctx.art.miniName()), times));
        }
    }

    // ==========================================
    // PRIME PRELUDE & MYTHIC FINALE
    // ==========================================

    /** Ascension: two rings close in on the target while a pillar of light climbs behind the wielder. */
    private static int prelude(@Nonnull Ctx ctx, int start) {
        int duration = 10;
        timeline(ctx, start, duration, t -> {
            Location c = ctx.ground().add(0, 0.15, 0);
            double progress = t / (double) duration;
            double r = ctx.radius * 1.4 * (1.0 - progress) + 0.8;
            dustRing(ctx, c, r, ctx.n(18), t * 0.35, ctx.main);
            dustRing(ctx, c.clone().add(0, 0.6, 0), r * 0.85, ctx.n(14), -t * 0.35, ctx.accent);

            Location pillar = ctx.player.getLocation().subtract(ctx.facing().multiply(0.7));
            for (int i = 0; i < 4; i++) {
                double y = (t * 0.45) + i * 0.3;
                spawn(ctx, Particle.END_ROD, pillar.clone().add(0, y, 0), 1, 0.05, 0.05, 0.05, 0.0);
                spawnTransition(ctx, pillar.clone().add(0, y, 0), 1, 0.15);
            }
            if (ctx.tier == ForgeTier.MYTHIC) {
                dustRing(ctx, c.clone().add(0, 6.0, 0), ctx.radius * 0.9, ctx.n(16), t * 0.2, ctx.accent);
                spawn(ctx, Particle.SQUID_INK, c.clone().add(0, 6.0, 0), ctx.n(3), ctx.radius * 0.5, 0.1, ctx.radius * 0.5, 0.0);
            }

            if (t == 0) {
                sound(ctx, c, Sound.BLOCK_BEACON_POWER_SELECT, 1.2f, 0.6f);
                if (ctx.tier == ForgeTier.MYTHIC) sound(ctx, c, Sound.ENTITY_WITHER_SPAWN, 0.35f, 1.8f);
            }
            if (t == 5) sound(ctx, c, Sound.BLOCK_BEACON_POWER_SELECT, 1.2f, 1.0f);
            if (t == duration) sound(ctx, c, Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1.0f, 1.3f);
        });
        return start + duration + 1;
    }

    /** The sky answers: thunder, rising obelisks and a shockwave that rolls far past the fight. */
    private static void mythicFinale(@Nonnull Ctx ctx, int start) {
        int duration = 30;
        timeline(ctx, start, duration, t -> {
            Location c = ctx.ground();
            if (t == 0) {
                for (int i = 0; i < 3; i++) {
                    double angle = i * (Math.PI * 2 / 3) + 0.4;
                    Location bolt = c.clone().add(Math.cos(angle) * 2.5, 0, Math.sin(angle) * 2.5);
                    safely(ctx, () -> ctx.world.strikeLightningEffect(bolt));
                }
                sound(ctx, c, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7f, 1.2f);
                for (int i = 0; i < 4; i++) {
                    double angle = i * (Math.PI / 2) + Math.PI / 4;
                    Location at = c.clone().add(Math.cos(angle) * 3.2, 0, Math.sin(angle) * 3.2);
                    obelisk(ctx, at, Material.CRYING_OBSIDIAN, 4.2f, 10, duration + 4);
                }
            }
            if (t <= 10) {
                double r = 1.0 + t * 0.85;
                dustRing(ctx, c.clone().add(0, 0.2, 0), r, ctx.n(28), t * 0.1, ctx.main);
                ring(ctx, c.clone().add(0, 0.3, 0), r * 0.92, ctx.n(14), -t * 0.1, Particle.END_ROD);
            }
            if (t % 4 == 0) {
                spawn(ctx, Particle.TOTEM_OF_UNDYING, c.clone().add(0, 2.0, 0), ctx.n(8), 1.4, 1.2, 1.4, 0.25);
            }
            if (t == duration) {
                spawn(ctx, Particle.EXPLOSION_EMITTER, c.clone().add(0, 1.0, 0), 1, 0, 0, 0, 0);
                sound(ctx, c, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.8f, 1.4f);
            }
        });
    }

    // ==========================================
    // LEGENDARY DISPATCH
    // ==========================================

    private static int legendary(@Nonnull Ctx ctx, @Nonnull LegendaryArt art, int start) {
        return switch (art) {
            case BELL_OF_THE_FIRST_AGE -> bell(ctx, start);
            case THUNDERCHAIN_CONDUIT -> thunderchain(ctx, start);
            case THERMAL_BASTION -> thermalBastion(ctx, start);
            case INSATIABLE_FRENZY -> insatiable(ctx, start);
            case MIDAS_BLOOM -> midasBloom(ctx, start);
            case CONSTELLATION_FALL -> constellation(ctx, start);
            case ABYSSAL_REND -> abyssalRend(ctx, start);
            case HELLFORGE_ERUPTION -> eruption(ctx, start);
            case RESONANCE_CASCADE -> resonance(ctx, start);
            case UMBRAL_EXECUTION -> umbral(ctx, start);
            case PHASE_GATE -> phaseGate(ctx, start);
            case UNBREAKABLE_AEGIS -> aegis(ctx, start);
            case CHAIN_COMBUSTION -> chainCombustion(ctx, start);
            case SILVER_BLIZZARD -> blizzard(ctx, start);
            case CRIMSON_FEAST -> crimsonFeast(ctx, start);
            case GRAVITY_COLLAPSE -> gravityCollapse(ctx, start);
        };
    }

    /** Bronze: a colossal bell drops on the target and tolls three times. */
    private static int bell(@Nonnull Ctx ctx, int start) {
        int duration = 34;
        int land = 12;
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground();
            if (t == 0) {
                displayDrop(ctx, g, Material.BELL, 2.6f, 7.0f, land);
            }
            if (t < land) {
                double drop = 7.0 * (1.0 - t / (double) land);
                drawBell(ctx, g.clone().add(0, drop, 0), 1.0);
                spawn(ctx, Particle.WAX_ON, g.clone().add(0, drop + 1.0, 0), ctx.n(3), 0.6, 0.6, 0.6, 0.0);
            } else {
                drawBell(ctx, g, t % 2 == 0 ? 1.0 : 0.0);
            }

            if (t == land || t == land + 8 || t == land + 16) {
                int toll = (t - land) / 8;
                sound(ctx, g, Sound.BLOCK_BELL_USE, 2.0f, 0.5f + toll * 0.2f);
                sound(ctx, g, Sound.BLOCK_BELL_RESONATE, 1.0f, 0.6f + toll * 0.2f);
                if (toll == 0) {
                    spawn(ctx, Particle.EXPLOSION, g.clone().add(0, 0.5, 0), ctx.n(3), 0.6, 0.2, 0.6, 0.0);
                    strike(ctx, ctx.target, ctx.damage(1.0));
                    buff(ctx.player, PotionEffectType.RESISTANCE, 100, ctx.tier.atLeast(ForgeTier.PRIME) ? 2 : 1);
                }
                for (LivingEntity foe : foes(ctx, g, ctx.radius)) {
                    if (toll > 0 || !foe.equals(ctx.target)) strike(ctx, foe, ctx.damage(0.25));
                    foe.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 3, false, true));
                    foe.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 0, false, true));
                    push(foe, g, 0.45, 0.2);
                }
            }
            for (int toll = 0; toll < 3; toll++) {
                int age = t - (land + toll * 8);
                if (age >= 0 && age <= 6) {
                    double r = 0.6 + (ctx.radius - 0.6) * (age / 6.0);
                    dustRing(ctx, g.clone().add(0, 0.15, 0), r, ctx.n(20), toll * 0.3, toll % 2 == 0 ? ctx.main : ctx.accent);
                    ring(ctx, g.clone().add(0, 0.2, 0), r, ctx.n(8), 0.0, Particle.NOTE);
                }
            }
        });
        return start + duration + 1;
    }

    private static void drawBell(@Nonnull Ctx ctx, @Nonnull Location base, double density) {
        if (density <= 0) return;
        for (int k = 0; k <= 5; k++) {
            double h = k * 0.4;
            double r = 1.7 - (k / 5.0) * 1.15;
            dustRing(ctx, base.clone().add(0, h, 0), r, ctx.n(8 + (5 - k) * 2), k * 0.4, k % 2 == 0 ? ctx.main : ctx.accent);
        }
        spawn(ctx, Particle.WAX_ON, base.clone().add(0, 2.3, 0), ctx.n(1), 0.05, 0.05, 0.05, 0.0);
    }

    /** Electrum: lightning leaps from foe to foe. */
    private static int thunderchain(@Nonnull Ctx ctx, int start) {
        List<LivingEntity> links = new ArrayList<>();
        links.add(ctx.target);
        int maxLinks = 1 + 3 + Math.max(0, ctx.tier.getLevel() - ForgeTier.LEGENDARY.getLevel()) * 2;
        while (links.size() < maxLinks) {
            LivingEntity last = links.get(links.size() - 1);
            LivingEntity next = null;
            double best = Double.MAX_VALUE;
            for (LivingEntity foe : foes(ctx, last.getLocation(), 6.0 + ctx.tier.getLevel() * 0.5)) {
                if (links.contains(foe)) continue;
                double d = foe.getLocation().distanceSquared(last.getLocation());
                if (d < best) {
                    best = d;
                    next = foe;
                }
            }
            if (next == null) break;
            links.add(next);
        }

        int duration = links.size() * 3 + 10;
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            if (t == 0) {
                safely(ctx, () -> ctx.world.strikeLightningEffect(ctx.target.getLocation()));
                strike(ctx, ctx.target, ctx.damage(1.0));
                sound(ctx, c, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.9f, 1.5f);
                spawn(ctx, Particle.ELECTRIC_SPARK, c, ctx.n(30), 0.5, 0.8, 0.5, 0.3);
            }
            for (int i = 1; i < links.size(); i++) {
                int fireAt = i * 3;
                LivingEntity from = links.get(i - 1);
                LivingEntity to = links.get(i);
                if (t >= fireAt && t <= fireAt + 4) {
                    jagged(ctx, from.getLocation().add(0, 1.1, 0), to.getLocation().add(0, 1.1, 0));
                }
                if (t == fireAt && to.isValid()) {
                    strike(ctx, to, ctx.damage(Math.pow(0.85, i)));
                    spawn(ctx, Particle.ELECTRIC_SPARK, to.getLocation().add(0, 1, 0), ctx.n(16), 0.3, 0.5, 0.3, 0.2);
                    sound(ctx, to.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.8f, 1.4f + i * 0.08f);
                    if (i == links.size() - 1) {
                        safely(ctx, () -> ctx.world.strikeLightningEffect(to.getLocation()));
                    }
                }
            }
            // The conduit hums around the wielder for the whole chain.
            double a = t * 0.6;
            Location p = ctx.player.getLocation().add(Math.cos(a) * 0.8, 0.2 + (t % 10) * 0.2, Math.sin(a) * 0.8);
            spawn(ctx, Particle.ELECTRIC_SPARK, p, ctx.n(1), 0, 0, 0, 0);
            spawnDust(ctx, p, 1, 0.02, t % 2 == 0 ? ctx.main : ctx.accent);
        });
        return start + duration + 1;
    }

    /** Invar: a rotating hexagonal wall of heat around the wielder. */
    private static int thermalBastion(@Nonnull Ctx ctx, int start) {
        int duration = 40;
        double r = Math.min(ctx.radius, 4.5);
        timeline(ctx, start, duration, t -> {
            Location p = ctx.player.getLocation();
            double rot = t * 0.08;
            for (int edge = 0; edge < 6; edge++) {
                double a1 = rot + edge * Math.PI / 3;
                double a2 = rot + (edge + 1) * Math.PI / 3;
                for (double h : new double[]{0.1, 1.0, 1.9}) {
                    Location from = p.clone().add(Math.cos(a1) * r, h, Math.sin(a1) * r);
                    Location to = p.clone().add(Math.cos(a2) * r, h, Math.sin(a2) * r);
                    if (h < 0.5) {
                        line(ctx, from, to, 0.6, Particle.FLAME);
                    } else {
                        dustLine(ctx, from, to, 0.45, h > 1.5 ? ctx.accent : ctx.main);
                    }
                }
                spawn(ctx, Particle.LAVA, p.clone().add(Math.cos(a1) * r, 2.0, Math.sin(a1) * r), ctx.n(1), 0, 0, 0, 0);
            }
            if (t == 0) {
                buff(ctx.player, PotionEffectType.ABSORPTION, 200, ctx.tier.atLeast(ForgeTier.PRIME) ? 2 : 1);
                buff(ctx.player, PotionEffectType.FIRE_RESISTANCE, 200, 0);
                strike(ctx, ctx.target, ctx.damage(0.6));
                sound(ctx, p, Sound.ITEM_FIRECHARGE_USE, 1.0f, 0.7f);
            }
            if (t % 10 == 0) {
                sound(ctx, p, Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1.2f, 0.8f);
                for (LivingEntity foe : foes(ctx, p, r + 0.5)) {
                    foe.setFireTicks(Math.max(foe.getFireTicks(), 80));
                    strike(ctx, foe, ctx.damage(0.3));
                }
            }
        });
        return start + duration + 1;
    }

    /** Manyullyn: soul-fire tendrils feed every nearby foe to the wielder. */
    private static int insatiable(@Nonnull Ctx ctx, int start) {
        int duration = 26;
        int feed = 12;
        List<LivingEntity> victims = foes(ctx, ctx.center(), ctx.radius);
        if (!victims.contains(ctx.target) && ctx.target.isValid()) victims.add(0, ctx.target);

        timeline(ctx, start, duration, t -> {
            Location chest = ctx.player.getLocation().add(0, 1.1, 0);
            if (t <= feed) {
                double progress = t / (double) feed;
                for (int v = 0; v < victims.size(); v++) {
                    LivingEntity foe = victims.get(v);
                    Location from = foe.getLocation().add(0, 1.0, 0);
                    Vector bend = new Vector(0, 2.0, 0).add(ctx.side().multiply(v % 2 == 0 ? 1.4 : -1.4));
                    curve(ctx, from, chest, bend, progress, Particle.SOUL_FIRE_FLAME, ctx.main);
                }
            }
            if (t == 0) sound(ctx, chest, Sound.ENTITY_WARDEN_HEARTBEAT, 1.4f, 0.7f);
            if (t == feed) {
                double healed = 0;
                for (LivingEntity foe : victims) {
                    if (!foe.isValid()) continue;
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 1.0 : 0.5));
                    spawn(ctx, Particle.SOUL, foe.getLocation().add(0, 1, 0), ctx.n(6), 0.3, 0.4, 0.3, 0.05);
                    healed += 1.5;
                }
                heal(ctx.player, Math.min(8.0, healed * (ctx.tier.getLevel() - 1) / 2.0));
                buff(ctx.player, PotionEffectType.STRENGTH, 120, ctx.tier.atLeast(ForgeTier.PRIME) ? 1 : 0);
                sound(ctx, chest, Sound.PARTICLE_SOUL_ESCAPE, 1.6f, 0.6f);
                sound(ctx, chest, Sound.ENTITY_WARDEN_HEARTBEAT, 1.6f, 1.2f);
            }
            if (t > feed) {
                double a = t * 0.7;
                double y = (t - feed) * 0.15;
                for (int s = 0; s < 2; s++) {
                    Location p = ctx.player.getLocation().add(Math.cos(a + s * Math.PI) * 0.7, y, Math.sin(a + s * Math.PI) * 0.7);
                    spawn(ctx, Particle.SOUL_FIRE_FLAME, p, ctx.n(1), 0, 0, 0, 0);
                    spawnDust(ctx, p, 1, 0.02, ctx.accent);
                }
            }
        });
        return start + duration + 1;
    }

    /** Rose Gold: a rose of gilded petals blooms under the target. */
    private static int midasBloom(@Nonnull Ctx ctx, int start) {
        int duration = 32;
        int bloom = 16;
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground().add(0, 0.12, 0);
            double grow = Math.min(1.0, t / (double) bloom);
            int points = ctx.n(48);
            for (int i = 0; i < points; i++) {
                double theta = (Math.PI * 2 * i) / points;
                double r = ctx.radius * Math.abs(Math.cos(4 * theta)) * grow;
                Location p = g.clone().add(Math.cos(theta + t * 0.03) * r, 0, Math.sin(theta + t * 0.03) * r);
                spawnDust(ctx, p, 1, 0.0, i % 3 == 0 ? ctx.accent : ctx.main);
            }
            if (t < bloom && t % 2 == 0) {
                spawn(ctx, Particle.CHERRY_LEAVES, g.clone().add(0, 0.4, 0), ctx.n(4), ctx.radius * 0.4, 0.1, ctx.radius * 0.4, 0.0);
            }
            if (t == bloom) {
                spawn(ctx, Particle.FIREWORK, g.clone().add(0, 0.6, 0), ctx.n(30), ctx.radius * 0.4, 0.3, ctx.radius * 0.4, 0.1);
                sound(ctx, g, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.5f, 1.4f);
                sound(ctx, g, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.6f);
                for (LivingEntity foe : foes(ctx, g, ctx.radius)) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 1.0 : 0.6));
                    foe.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0, false, false));
                    foe.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1, false, true));
                }
                ctx.player.giveExp(4 * ctx.tier.getLevel());
                buff(ctx.player, PotionEffectType.LUCK, 400, ctx.tier.atLeast(ForgeTier.PRIME) ? 1 : 0);
            }
            if (t > bloom) {
                spawn(ctx, Particle.CHERRY_LEAVES, g.clone().add(0, (t - bloom) * 0.15, 0), ctx.n(2), ctx.radius * 0.5, 0.2, ctx.radius * 0.5, 0.0);
            }
        });
        return start + duration + 1;
    }

    /** Astral Brass: a constellation is drawn above the target, then every star falls. */
    private static int constellation(@Nonnull Ctx ctx, int start) {
        int stars = 5 + ctx.tier.getLevel();
        Random seeded = new Random(ctx.target.getUniqueId().getMostSignificantBits());
        List<Vector> offsets = new ArrayList<>();
        for (int i = 0; i < stars; i++) {
            double a = seeded.nextDouble() * Math.PI * 2;
            double r = 0.8 + seeded.nextDouble() * (ctx.radius - 0.8);
            offsets.add(new Vector(Math.cos(a) * r, 5.0 + seeded.nextDouble() * 1.5, Math.sin(a) * r));
        }
        List<LivingEntity> victims = foes(ctx, ctx.center(), ctx.radius + 1.0);
        victims.remove(ctx.target);
        victims.add(0, ctx.target);

        int drawn = stars * 2;
        int duration = drawn + stars * 3 + 8;
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground();
            int visible = Math.min(stars, t / 2 + 1);
            for (int i = 0; i < visible; i++) {
                Location star = g.clone().add(offsets.get(i));
                boolean fallen = t >= drawn + i * 3;
                if (fallen) continue;
                spawn(ctx, Particle.END_ROD, star, ctx.n(2), 0.05, 0.05, 0.05, 0.0);
                if (i > 0) {
                    dustLine(ctx, g.clone().add(offsets.get(i - 1)), star, 0.35, ctx.main);
                }
            }
            if (t % 2 == 0 && t < drawn) sound(ctx, g, Sound.BLOCK_AMETHYST_BLOCK_HIT, 0.8f, 1.2f + t * 0.03f);

            for (int i = 0; i < stars; i++) {
                if (t != drawn + i * 3) continue;
                LivingEntity foe = victims.isEmpty() ? ctx.target : victims.get(i % victims.size());
                Location from = g.clone().add(offsets.get(i));
                Location to = foe.isValid() ? foe.getLocation().add(0, 0.5, 0) : g;
                line(ctx, from, to, 0.3, Particle.END_ROD);
                dustLine(ctx, from, to, 0.5, ctx.accent);
                spawn(ctx, Particle.FIREWORK, to, ctx.n(10), 0.3, 0.3, 0.3, 0.08);
                sound(ctx, to, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 0.8f + i * 0.04f);
                if (foe.isValid()) strike(ctx, foe, ctx.damage(i == 0 ? 0.7 : 0.4));
            }
            if (t == 0) {
                buff(ctx.player, PotionEffectType.SLOW_FALLING, 120, 0);
                buff(ctx.player, PotionEffectType.SPEED, 120, 0);
            }
        });
        return start + duration + 1;
    }

    /** Void Damascus: three armor-piercing rifts torn through the air. */
    private static int abyssalRend(@Nonnull Ctx ctx, int start) {
        int duration = 32;
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            Vector side = ctx.side();
            Vector up = new Vector(0, 1, 0);
            Location[][] slashes = {
                    {c.clone().add(side.clone().multiply(-1.5)).add(up.clone().multiply(1.4)),
                            c.clone().add(side.clone().multiply(1.5)).add(up.clone().multiply(-1.0))},
                    {c.clone().add(side.clone().multiply(1.5)).add(up.clone().multiply(1.4)),
                            c.clone().add(side.clone().multiply(-1.5)).add(up.clone().multiply(-1.0))},
                    {c.clone().add(up.clone().multiply(1.7)), c.clone().add(up.clone().multiply(-1.1))}
            };
            for (int s = 0; s < 3; s++) {
                int open = s * 5;
                if (t < open) continue;
                double progress = Math.min(1.0, (t - open + 1) / 3.0);
                Location a = slashes[s][0];
                Location b = a.clone().add(slashes[s][1].toVector().subtract(a.toVector()).multiply(progress));
                boolean fresh = t - open < 4;
                dustLine(ctx, a, b, fresh ? 0.12 : 0.35, ctx.main);
                if (fresh) {
                    line(ctx, a, b, 0.3, Particle.REVERSE_PORTAL);
                    dustLine(ctx, a, b, 0.4, ctx.accent);
                }
                if (t == open + 2) {
                    sound(ctx, c, Sound.ITEM_TRIDENT_RIPTIDE_3, 1.0f, 0.6f + s * 0.15f);
                    sound(ctx, c, Sound.ENTITY_ENDERMAN_SCREAM, 0.5f, 0.5f);
                    pierce(ctx, ctx.target, ctx.damage(0.4));
                    for (LivingEntity foe : foes(ctx, c, 2.5)) {
                        if (!foe.equals(ctx.target)) strike(ctx, foe, ctx.damage(0.2));
                    }
                }
            }
            if (t > 13) {
                for (LivingEntity foe : foes(ctx, c, ctx.radius)) {
                    Vector pull = c.toVector().subtract(foe.getLocation().toVector());
                    if (pull.lengthSquared() > 1.0) foe.setVelocity(pull.normalize().multiply(0.22).setY(0.02));
                }
                spawn(ctx, Particle.PORTAL, c, ctx.n(6), 0.8, 0.8, 0.8, 0.6);
            }
        });
        return start + duration + 1;
    }

    /** Cinder Steel: the ground cracks into magma and erupts in geysers of fire. */
    private static int eruption(@Nonnull Ctx ctx, int start) {
        int duration = 36;
        int erupt = 10;
        BlockData magma = Material.MAGMA_BLOCK.createBlockData();
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground().add(0, 0.1, 0);
            int vents = 6;
            if (t <= erupt) {
                double reach = ctx.radius * (t / (double) erupt);
                for (int v = 0; v < vents; v++) {
                    double a = v * (Math.PI * 2 / vents);
                    Location tip = g.clone().add(Math.cos(a) * reach, 0, Math.sin(a) * reach);
                    line(ctx, g, tip, 0.4, Particle.DRIPPING_LAVA);
                    spawn(ctx, Particle.BLOCK, tip, ctx.n(3), 0.1, 0.05, 0.1, 0.0, magma);
                }
                if (t % 3 == 0) sound(ctx, g, Sound.BLOCK_BASALT_BREAK, 1.2f, 0.6f);
            }
            if (t == erupt) {
                sound(ctx, g, Sound.ITEM_FIRECHARGE_USE, 1.6f, 0.5f);
                sound(ctx, g, Sound.ENTITY_BLAZE_SHOOT, 1.4f, 0.6f);
                for (LivingEntity foe : foes(ctx, g, ctx.radius)) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 1.0 : 0.55));
                    foe.setFireTicks(Math.max(foe.getFireTicks(), 120));
                    Vector out = foe.getLocation().toVector().subtract(g.toVector()).setY(0);
                    if (out.lengthSquared() > 1.0E-4) out.normalize().multiply(0.3);
                    foe.setVelocity(out.setY(0.95));
                }
                for (int v = 0; v < vents; v++) {
                    double a = v * (Math.PI * 2 / vents);
                    displayPillar(ctx, g.clone().add(Math.cos(a) * ctx.radius, -0.1, Math.sin(a) * ctx.radius),
                            Material.MAGMA_BLOCK, 0.7f, 2.6f, 6, 16);
                }
            }
            if (t >= erupt && t <= erupt + 16) {
                double height = Math.min(5.0, (t - erupt) * 0.6);
                for (int v = 0; v <= vents; v++) {
                    Location vent = v == vents ? g.clone()
                            : g.clone().add(Math.cos(v * (Math.PI * 2 / vents)) * ctx.radius, 0,
                            Math.sin(v * (Math.PI * 2 / vents)) * ctx.radius);
                    for (double y = 0; y <= height; y += 0.5) {
                        spawn(ctx, Particle.FLAME, vent.clone().add(0, y, 0), ctx.n(1), 0.12, 0.05, 0.12, 0.02);
                    }
                    spawn(ctx, Particle.LAVA, vent.clone().add(0, height, 0), ctx.n(1), 0.2, 0.1, 0.2, 0.0);
                    spawnDust(ctx, vent.clone().add(0, height * 0.5, 0), 1, 0.2, ctx.main);
                }
            }
            if (t > erupt + 16) {
                spawn(ctx, Particle.ASH, g.clone().add(0, 3.0, 0), ctx.n(6), ctx.radius * 0.6, 1.0, ctx.radius * 0.6, 0.0);
                spawn(ctx, Particle.LARGE_SMOKE, g.clone().add(0, 1.5, 0), ctx.n(2), ctx.radius * 0.4, 0.5, ctx.radius * 0.4, 0.01);
            }
        });
        return start + duration + 1;
    }

    /** Prismatic Quartz: crystal shards orbit the target and resonate in three pulses. */
    private static int resonance(@Nonnull Ctx ctx, int start) {
        int duration = 34;
        BlockData crystal = Material.AMETHYST_CLUSTER.createBlockData();
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            int shards = 8;
            for (int s = 0; s < shards; s++) {
                double a = t * 0.3 + s * (Math.PI * 2 / shards);
                double r = 2.0 - Math.min(1.0, t / 30.0) * 0.6;
                Location p = c.clone().add(Math.cos(a) * r, Math.sin(t * 0.25 + s) * 0.5, Math.sin(a) * r);
                spawn(ctx, Particle.BLOCK, p, ctx.n(1), 0.02, 0.02, 0.02, 0.0, crystal);
                spawnDust(ctx, p, 1, 0.03, s % 2 == 0 ? ctx.main : ctx.accent);
            }
            int[] pulses = {10, 18, 26};
            for (int i = 0; i < pulses.length; i++) {
                int age = t - pulses[i];
                if (age >= 0 && age <= 5) {
                    double r = 0.5 + (ctx.radius - 0.5) * (age / 5.0);
                    ring(ctx, c.clone().subtract(0, 0.7, 0), r, ctx.n(14), i * 0.4, Particle.NOTE);
                    dustRing(ctx, c.clone().subtract(0, 0.6, 0), r, ctx.n(22), -i * 0.4, i % 2 == 0 ? ctx.main : ctx.accent);
                }
                if (age == 0) {
                    sound(ctx, c, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1.6f, 0.8f + i * 0.4f);
                    double share = 0.35 * (1.0 + i * 0.25);
                    for (LivingEntity foe : foes(ctx, c, ctx.radius)) {
                        strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? share * 1.5 : share));
                    }
                    if (i == pulses.length - 1) {
                        spawn(ctx, Particle.SONIC_BOOM, c, 1, 0, 0, 0, 0);
                        sound(ctx, c, Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1.6f, 0.7f);
                    }
                }
            }
        });
        return start + duration + 1;
    }

    /** Shadow Platinum: the wielder vanishes, reappears behind the target and executes it. */
    private static int umbral(@Nonnull Ctx ctx, int start) {
        int duration = 22;
        timeline(ctx, start, duration, t -> {
            if (t == 0) {
                Location from = ctx.player.getLocation();
                spawn(ctx, Particle.LARGE_SMOKE, from.clone().add(0, 1, 0), ctx.n(24), 0.3, 0.6, 0.3, 0.02);
                spawn(ctx, Particle.SQUID_INK, from.clone().add(0, 1, 0), ctx.n(10), 0.3, 0.5, 0.3, 0.05);
                sound(ctx, from, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.2f, 0.7f);
                buff(ctx.player, PotionEffectType.INVISIBILITY, 40, 0);
                buff(ctx.player, PotionEffectType.SPEED, 60, 1);

                if (ctx.target.isValid() && from.distanceSquared(ctx.target.getLocation()) <= 36.0) {
                    Vector behind = ctx.target.getLocation().getDirection().setY(0);
                    if (behind.lengthSquared() < 1.0E-4) behind = ctx.facing();
                    Location spot = ctx.target.getLocation().subtract(behind.normalize().multiply(1.3));
                    if (isStandable(spot)) {
                        Vector look = ctx.target.getLocation().toVector().subtract(spot.toVector());
                        spot.setDirection(look);
                        ctx.player.teleport(spot);
                        spawn(ctx, Particle.LARGE_SMOKE, spot.clone().add(0, 1, 0), ctx.n(16), 0.3, 0.6, 0.3, 0.02);
                    }
                }
            }
            int arc = ctx.n(14);
            if (t >= 2 && t <= 6 && arc > 0) {
                Location eye = ctx.player.getEyeLocation().subtract(0, 0.3, 0);
                Vector f = ctx.center().toVector().subtract(eye.toVector());
                if (f.lengthSquared() < 1.0E-4) f = ctx.facing();
                f.normalize();
                Vector s = new Vector(-f.getZ(), 0, f.getX());
                double sweep = (t - 2) / 4.0;
                for (int i = 0; i <= arc; i++) {
                    double angle = Math.toRadians(-80 + 160 * Math.min(sweep, i / (double) arc));
                    Location p = eye.clone().add(f.clone().multiply(1.2 + Math.cos(angle) * 0.6))
                            .add(s.clone().multiply(Math.sin(angle) * 1.6))
                            .add(0, Math.sin(angle) * -0.5, 0);
                    spawnDust(ctx, p, 1, 0.01, i % 3 == 0 ? ctx.main : ctx.accent);
                }
            }
            if (t == 5) {
                spawn(ctx, Particle.SWEEP_ATTACK, ctx.center(), ctx.n(3), 0.4, 0.2, 0.4, 0.0);
                sound(ctx, ctx.center(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.4f, 0.5f);
                double missing = 0.0;
                double max = maxHealth(ctx.target);
                if (max > 0) missing = Math.max(0.0, 1.0 - ctx.target.getHealth() / max);
                strike(ctx, ctx.target, ctx.damage(1.0 + missing * 1.5));
            }
            if (t > 5) {
                spawn(ctx, Particle.SMOKE, ctx.center(), ctx.n(3), 0.4, 0.6, 0.4, 0.01);
            }
        });
        return start + duration + 1;
    }

    /** Ender Brass: two portals phase the target into the sky and drop it. */
    private static int phaseGate(@Nonnull Ctx ctx, int start) {
        int duration = 34;
        int phase = 8;
        Location floor = ctx.ground();
        double lift = 0;
        for (double h = 1.0; h <= 7.0; h += 1.0) {
            if (!isStandable(floor.clone().add(0, h, 0))) break;
            lift = h;
        }
        final double height = lift;
        boolean movable = !(ctx.target instanceof Boss) && !(ctx.target instanceof ComplexLivingEntity)
                && !(ctx.target instanceof Player p && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR));

        timeline(ctx, start, duration, t -> {
            double open = t < phase ? t / (double) phase : (t > 22 ? Math.max(0.0, (duration - t) / 12.0) : 1.0);
            double r = 1.5 * open + 0.1;
            dustRing(ctx, floor.clone().add(0, 0.1, 0), r, ctx.n(18), t * 0.4, ctx.main);
            ring(ctx, floor.clone().add(0, 0.15, 0), r * 0.8, ctx.n(10), -t * 0.4, Particle.PORTAL);
            if (height >= 2.0) {
                Location top = floor.clone().add(0, height, 0);
                dustRing(ctx, top, r, ctx.n(18), -t * 0.4, ctx.accent);
                ring(ctx, top, r * 0.8, ctx.n(10), t * 0.4, Particle.REVERSE_PORTAL);
            }
            if (t == phase) {
                sound(ctx, floor, Sound.ENTITY_ENDERMAN_TELEPORT, 1.4f, 0.6f);
                strike(ctx, ctx.target, ctx.damage(0.6));
                if (movable && height >= 2.0 && ctx.target.isValid()) {
                    Location up = ctx.target.getLocation().add(0, height, 0);
                    ctx.target.teleport(up);
                    spawn(ctx, Particle.REVERSE_PORTAL, up.clone().add(0, 1, 0), ctx.n(30), 0.4, 0.6, 0.4, 0.1);
                }
            }
            if (t == phase + 2 && movable && ctx.target.isValid()) {
                ctx.target.setVelocity(new Vector(0, -1.3, 0));
            }
            if (t > phase && t < 22) {
                for (LivingEntity foe : foes(ctx, floor, ctx.radius)) {
                    if (foe.equals(ctx.target)) continue;
                    Vector pull = floor.toVector().subtract(foe.getLocation().toVector());
                    if (pull.lengthSquared() > 0.6) foe.setVelocity(pull.normalize().multiply(0.3).setY(0.05));
                }
            }
            if (t == phase + 8) {
                strike(ctx, ctx.target, ctx.damage(0.5));
                spawn(ctx, Particle.PORTAL, floor.clone().add(0, 0.5, 0), ctx.n(40), 0.8, 0.3, 0.8, 1.0);
                sound(ctx, floor, Sound.BLOCK_END_PORTAL_FRAME_FILL, 1.4f, 0.7f);
            }
        });
        return start + duration + 1;
    }

    /** Adamant Steel: a towering sigil hardens the wielder and slams the target away. */
    private static int aegis(@Nonnull Ctx ctx, int start) {
        int duration = 30;
        int slam = 8;
        timeline(ctx, start, duration, t -> {
            Vector f = ctx.facing();
            Vector s = ctx.side();
            Location centre = ctx.player.getLocation().add(0, 1.4, 0).add(f.clone().multiply(1.6));
            double grow = Math.min(1.0, (t + 1) / (double) slam);
            double outer = 1.9 * grow;
            double inner = 1.15 * grow;
            if (t <= 24) {
                verticalRing(ctx, centre, s, outer, ctx.n(28), t * 0.05, ctx.main);
                verticalRing(ctx, centre, s, inner, ctx.n(18), -t * 0.05, ctx.accent);
                dustLine(ctx, centre.clone().add(0, outer, 0), centre.clone().add(0, -outer, 0), 0.22, ctx.accent);
                dustLine(ctx, centre.clone().add(s.clone().multiply(outer)), centre.clone().add(s.clone().multiply(-outer)), 0.22, ctx.accent);
                for (int rune = 0; rune < 6; rune++) {
                    double a = rune * Math.PI / 3 + t * 0.1;
                    Location p = centre.clone().add(s.clone().multiply(Math.cos(a) * (outer + 0.3))).add(0, Math.sin(a) * (outer + 0.3), 0);
                    spawn(ctx, Particle.ENCHANTED_HIT, p, ctx.n(1), 0, 0, 0, 0);
                }
            } else {
                spawn(ctx, Particle.ENCHANTED_HIT, centre, ctx.n(10), 1.0, 1.0, 1.0, 0.3);
            }
            if (t == 0) sound(ctx, centre, Sound.BLOCK_BEACON_ACTIVATE, 1.2f, 1.4f);
            if (t == slam) {
                sound(ctx, centre, Sound.BLOCK_ANVIL_LAND, 1.4f, 0.6f);
                buff(ctx.player, PotionEffectType.RESISTANCE, 120, ctx.tier.atLeast(ForgeTier.PRIME) ? 2 : 1);
                buff(ctx.player, PotionEffectType.ABSORPTION, 160, Math.max(0, ctx.tier.getLevel() - 3));
                strike(ctx, ctx.target, ctx.damage(0.7));
                if (ctx.target.isValid()) ctx.target.setVelocity(f.clone().multiply(1.5).setY(0.45));
                for (LivingEntity foe : foes(ctx, centre, ctx.radius)) {
                    if (foe.equals(ctx.target)) continue;
                    strike(ctx, foe, ctx.damage(0.3));
                    push(foe, ctx.player.getLocation(), 0.9, 0.3);
                }
            }
        });
        return start + duration + 1;
    }

    /** Hellfire Bismuth: an iridescent spiral detonates in a chain of blasts. */
    private static int chainCombustion(@Nonnull Ctx ctx, int start) {
        int blasts = 5 + Math.max(0, ctx.tier.getLevel() - ForgeTier.LEGENDARY.getLevel()) * 2;
        int duration = 6 + blasts * 3 + 6;
        Particle.DustOptions gold = new Particle.DustOptions(Color.fromRGB(0xF1C40F), 1.2f);
        Particle.DustOptions[] palette = {ctx.main, ctx.accent, gold};
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground();
            if (t <= 6) {
                for (int step = 0; step < 4; step++) {
                    double size = 0.4 + step * 0.35;
                    double y = step * 0.35 + t * 0.05;
                    for (int corner = 0; corner < 4; corner++) {
                        double a1 = corner * Math.PI / 2 + t * 0.2;
                        double a2 = (corner + 1) * Math.PI / 2 + t * 0.2;
                        dustLine(ctx, g.clone().add(Math.cos(a1) * size, y, Math.sin(a1) * size),
                                g.clone().add(Math.cos(a2) * size, y, Math.sin(a2) * size), 0.25, palette[(step + corner) % 3]);
                    }
                }
            }
            for (int i = 0; i < blasts; i++) {
                if (t != 6 + i * 3) continue;
                double a = i * 1.15;
                double r = Math.min(ctx.radius, i * 0.45);
                Location blast = g.clone().add(Math.cos(a) * r, 0.6, Math.sin(a) * r);
                spawn(ctx, Particle.EXPLOSION, blast, ctx.n(1), 0.1, 0.1, 0.1, 0.0);
                spawn(ctx, Particle.FLAME, blast, ctx.n(14), 0.4, 0.4, 0.4, 0.06);
                spawnDust(ctx, blast, ctx.n(10), 0.5, palette[i % 3]);
                sound(ctx, blast, Sound.ENTITY_GENERIC_EXPLODE, 0.8f, 1.0f + i * 0.05f);
                for (LivingEntity foe : foes(ctx, blast, 2.2)) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) && i == 0 ? 0.8 : 0.3));
                    foe.setFireTicks(Math.max(foe.getFireTicks(), 40));
                }
            }
        });
        return start + duration + 1;
    }

    /** Glacial Silver: a silver blizzard spins around the target and shatters. */
    private static int blizzard(@Nonnull Ctx ctx, int start) {
        int duration = 40;
        BlockData ice = Material.ICE.createBlockData();
        timeline(ctx, start, duration, t -> {
            Location g = ctx.ground();
            double r = ctx.radius * 0.75;
            if (t < duration) {
                for (int band = 0; band < 4; band++) {
                    for (int k = 0; k < 6; k++) {
                        double h = k * 0.6;
                        double a = t * 0.45 + band * (Math.PI / 2) + h * 0.9;
                        Location p = g.clone().add(Math.cos(a) * r, h, Math.sin(a) * r);
                        spawn(ctx, Particle.SNOWFLAKE, p, ctx.n(1), 0.05, 0.05, 0.05, 0.0);
                        if (k % 2 == 0) spawnDust(ctx, p, 1, 0.05, band % 2 == 0 ? ctx.main : ctx.accent);
                    }
                }
                spawn(ctx, Particle.WHITE_ASH, g.clone().add(0, 2.0, 0), ctx.n(6), r * 0.6, 1.2, r * 0.6, 0.02);
            }
            if (t == 0) {
                sound(ctx, g, Sound.ITEM_ELYTRA_FLYING, 0.6f, 1.6f);
                for (int i = 0; i < 6; i++) {
                    double a = i * (Math.PI / 3);
                    displayPillar(ctx, g.clone().add(Math.cos(a) * (r + 0.6), -0.1, Math.sin(a) * (r + 0.6)),
                            Material.PACKED_ICE, 0.45f, 2.2f + (i % 2) * 0.8f, 12, duration - 2);
                }
            }
            if (t % 5 == 0 && t < duration) {
                sound(ctx, g, Sound.BLOCK_POWDER_SNOW_STEP, 1.0f, 0.6f + t * 0.01f);
                for (LivingEntity foe : foes(ctx, g, ctx.radius)) {
                    foe.setFreezeTicks(Math.min(300, foe.getFreezeTicks() + 40));
                    foe.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 2, false, true));
                    strike(ctx, foe, ctx.damage(0.12));
                }
            }
            if (t == duration) {
                spawn(ctx, Particle.BLOCK, g.clone().add(0, 1.0, 0), ctx.n(60), r * 0.6, 1.0, r * 0.6, 0.1, ice);
                sound(ctx, g, Sound.BLOCK_GLASS_BREAK, 1.6f, 0.6f);
                sound(ctx, g, Sound.ENTITY_PLAYER_HURT_FREEZE, 1.2f, 0.8f);
                for (LivingEntity foe : foes(ctx, g, ctx.radius)) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 0.9 : 0.5));
                }
            }
        });
        return start + duration + 1;
    }

    /** Sanguine Gold: blood arcs out of every foe into the wielder. */
    private static int crimsonFeast(@Nonnull Ctx ctx, int start) {
        int duration = 26;
        int drain = 9;
        List<LivingEntity> victims = foes(ctx, ctx.center(), ctx.radius);
        if (!victims.contains(ctx.target) && ctx.target.isValid()) victims.add(0, ctx.target);
        double[] drained = {0.0};

        timeline(ctx, start, duration, t -> {
            Location chest = ctx.player.getLocation().add(0, 1.1, 0);
            if (t <= 16) {
                double progress = Math.min(1.0, t / 14.0);
                for (int v = 0; v < victims.size(); v++) {
                    LivingEntity foe = victims.get(v);
                    Vector bend = new Vector(0, 1.6 + (v % 3) * 0.4, 0);
                    curve(ctx, foe.getLocation().add(0, 1.0, 0), chest, bend, progress, Particle.DAMAGE_INDICATOR, ctx.main);
                    if (t % 4 == 0) spawnDust(ctx, foe.getLocation().add(0, 1.0, 0), ctx.n(4), 0.3, ctx.main);
                }
            }
            if (t == drain) {
                for (LivingEntity foe : victims) {
                    if (!foe.isValid()) continue;
                    double amount = ctx.damage(foe.equals(ctx.target) ? 0.7 : 0.4);
                    double before = foe.getHealth();
                    strike(ctx, foe, amount);
                    drained[0] += Math.max(0.0, before - Math.max(0.0, foe.getHealth()));
                }
                heal(ctx.player, Math.max(1.0, drained[0] / 3.0));
                buff(ctx.player, PotionEffectType.REGENERATION, 100, ctx.tier.atLeast(ForgeTier.PRIME) ? 1 : 0);
                sound(ctx, chest, Sound.ENTITY_GENERIC_DRINK, 1.4f, 0.6f);
                sound(ctx, chest, Sound.ENTITY_WARDEN_HEARTBEAT, 1.0f, 1.4f);
            }
            if (t > 16) {
                double a = t * 0.8;
                for (int s = 0; s < 3; s++) {
                    Location p = ctx.player.getLocation().add(Math.cos(a + s * 2.1) * 0.8, (t - 16) * 0.2, Math.sin(a + s * 2.1) * 0.8);
                    spawnDust(ctx, p, 1, 0.02, s == 0 ? ctx.accent : ctx.main);
                }
            }
        });
        return start + duration + 1;
    }

    /** Cosmic Netherite: planetary rings drag every foe into a collapsing core. */
    private static int gravityCollapse(@Nonnull Ctx ctx, int start) {
        int duration = 40;
        int collapse = 30;
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            double r = t < collapse ? 2.4 + Math.min(1.0, t / 10.0) * 0.8 : Math.max(0.1, 3.2 * (1.0 - (t - collapse) / 4.0));
            if (t <= collapse + 4) {
                tiltedRing(ctx, c, r, Math.toRadians(30), 0, t * 0.2, ctx.main);
                tiltedRing(ctx, c, r * 0.8, Math.toRadians(-35), Math.toRadians(60), -t * 0.25, ctx.accent);
                for (int planet = 0; planet < 3; planet++) {
                    double a = t * 0.3 + planet * (Math.PI * 2 / 3);
                    Location p = c.clone().add(Math.cos(a) * r, Math.sin(a) * r * 0.5, Math.sin(a) * r);
                    spawn(ctx, Particle.END_ROD, p, ctx.n(1), 0, 0, 0, 0);
                }
                spawnDust(ctx, c, ctx.n(3), 0.15 + t * 0.005, ctx.accent);
            }
            if (t == 0) sound(ctx, c, Sound.BLOCK_BEACON_DEACTIVATE, 1.4f, 0.5f);
            if (t < collapse) {
                for (LivingEntity foe : foes(ctx, c, ctx.radius * 1.5)) {
                    if (foe.equals(ctx.target)) continue;
                    Vector pull = c.toVector().subtract(foe.getLocation().add(0, 1, 0).toVector());
                    if (pull.lengthSquared() > 0.8) foe.setVelocity(pull.normalize().multiply(0.35));
                }
                if (ctx.target.isValid()) ctx.target.setVelocity(new Vector(0, 0.02, 0));
            }
            if (t == collapse + 4) {
                spawn(ctx, Particle.SONIC_BOOM, c, 1, 0, 0, 0, 0);
                spawn(ctx, Particle.EXPLOSION_EMITTER, c, 1, 0, 0, 0, 0);
                sound(ctx, c, Sound.ENTITY_WARDEN_SONIC_BOOM, 1.4f, 0.6f);
                for (LivingEntity foe : foes(ctx, c, ctx.radius)) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 1.1 : 0.8));
                    push(foe, c, 0.7, 0.6);
                }
            }
            if (t > collapse + 4) {
                dustRing(ctx, c, (t - collapse - 4) * 0.9, ctx.n(20), 0, ctx.main);
            }
        });
        return start + duration + 1;
    }

    // ==========================================
    // FUSION SHAPES (COMPOSITES & PRIME OVERLAYS)
    // ==========================================

    private static int fusion(@Nonnull Ctx ctx, @Nonnull TraitAffinity payload, @Nonnull TraitAffinity shape, int start) {
        int duration = 16;
        int impact = 8;
        BlockData stone = Material.DRIPSTONE_BLOCK.createBlockData();
        BlockData anvil = Material.ANVIL.createBlockData();
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            Location g = ctx.ground().add(0, 0.1, 0);
            double progress = t / (double) duration;
            switch (shape) {
                case INFERNAL -> {
                    for (int i = 0; i < ctx.n(12); i++) {
                        double a = i * (Math.PI * 2 / ctx.n(12)) + t * 0.1;
                        Location p = g.clone().add(Math.cos(a) * 1.4, progress * 2.0, Math.sin(a) * 1.4);
                        spawn(ctx, Particle.FLAME, p, 1, 0.03, 0.05, 0.03, 0.01);
                        if (i % 3 == 0) spawnDust(ctx, p, 1, 0.05, ctx.main);
                    }
                }
                case VOID -> {
                    double r = ctx.radius * (1.0 - progress) + 0.2;
                    for (int i = 0; i < ctx.n(14); i++) {
                        double a = i * 0.9 + t;
                        double b = i * 0.45;
                        Location p = c.clone().add(Math.cos(a) * Math.sin(b) * r, Math.cos(b) * r * 0.6, Math.sin(a) * Math.sin(b) * r);
                        spawn(ctx, Particle.REVERSE_PORTAL, p, 1, 0, 0, 0, 0);
                        if (i % 2 == 0) spawnDust(ctx, p, 1, 0.0, ctx.accent);
                    }
                }
                case PRIMAL -> {
                    int claws = Math.min(4, t / 2 + 1);
                    Vector side = ctx.side();
                    for (int k = 0; k < claws; k++) {
                        double off = (k - 1.5) * 0.35;
                        Location a = c.clone().add(side.clone().multiply(off - 0.6)).add(0, 0.9, 0);
                        Location b = c.clone().add(side.clone().multiply(off + 0.6)).add(0, -0.9, 0);
                        dustLine(ctx, a, b, 0.18, k % 2 == 0 ? ctx.main : ctx.accent);
                        if (t == k * 2) line(ctx, a, b, 0.3, Particle.CRIT);
                    }
                }
                case TEMPERED -> {
                    if (t < impact) {
                        double y = 5.0 * (1.0 - t / (double) impact);
                        spawn(ctx, Particle.BLOCK, g.clone().add(0, y + 0.5, 0), ctx.n(8), 0.35, 0.2, 0.35, 0.0, anvil);
                        spawnDust(ctx, g.clone().add(0, y, 0), ctx.n(4), 0.3, ctx.main);
                    } else if (t == impact) {
                        sound(ctx, g, Sound.BLOCK_ANVIL_LAND, 1.2f, 0.8f);
                        dustRing(ctx, g, 1.6, ctx.n(20), 0, ctx.accent);
                    }
                }
                case RADIANT -> {
                    double y = 3.0 * (1.0 - Math.min(1.0, t / 10.0)) + 0.2;
                    ring(ctx, g.clone().add(0, y, 0), 1.2, ctx.n(14), t * 0.2, Particle.END_ROD);
                    dustRing(ctx, g.clone().add(0, y, 0), 1.35, ctx.n(14), -t * 0.2, ctx.main);
                }
                case RESONANT -> {
                    for (int k = 0; k < 3; k++) {
                        int age = t - k * 5;
                        if (age < 0 || age > 5) continue;
                        double r = 0.4 + (ctx.radius - 0.4) * (age / 5.0);
                        ring(ctx, g, r, ctx.n(10), k, Particle.NOTE);
                        dustRing(ctx, g, r, ctx.n(14), -k, k % 2 == 0 ? ctx.main : ctx.accent);
                    }
                }
                case VOLATILE -> {
                    double reach = ctx.radius * Math.min(1.0, (t + 1) / (double) impact);
                    for (int ray = 0; ray < 12; ray++) {
                        double a = ray * (Math.PI / 6);
                        Location tip = c.clone().add(Math.cos(a) * reach, 0, Math.sin(a) * reach);
                        spawn(ctx, Particle.FLAME, tip, ctx.n(1), 0.05, 0.05, 0.05, 0.01);
                        if (ray % 2 == 0) spawnDust(ctx, tip, 1, 0.05, ctx.accent);
                    }
                    if (t == impact) spawn(ctx, Particle.EXPLOSION, c, ctx.n(2), 0.3, 0.3, 0.3, 0);
                }
                case TERRAIN -> {
                    double height = Math.min(2.0, t * 0.25);
                    for (int spire = 0; spire < 8; spire++) {
                        double a = spire * (Math.PI / 4);
                        Location b = g.clone().add(Math.cos(a) * 1.8, 0, Math.sin(a) * 1.8);
                        for (double y = 0; y <= height; y += 0.4) {
                            spawn(ctx, Particle.BLOCK, b.clone().add(0, y, 0), ctx.n(1), 0.05, 0.05, 0.05, 0, stone);
                        }
                        spawnDust(ctx, b.clone().add(0, height, 0), 1, 0.05, ctx.main);
                    }
                }
                case SWIFT -> {
                    for (int s = 0; s < 2; s++) {
                        double a = t * 0.8 + s * Math.PI;
                        Location p = g.clone().add(Math.cos(a) * 1.3, progress * 3.0, Math.sin(a) * 1.3);
                        spawn(ctx, Particle.CLOUD, p, ctx.n(2), 0.05, 0.05, 0.05, 0.01);
                        spawnDust(ctx, p, 1, 0.02, s == 0 ? ctx.main : ctx.accent);
                    }
                }
                case BRUTAL -> {
                    double reach = ctx.radius * Math.min(1.0, (t + 1) / (double) impact);
                    for (int ray = 0; ray < 8; ray++) {
                        double a = ray * (Math.PI / 4) + (ray % 2) * 0.2;
                        dustLine(ctx, g, g.clone().add(Math.cos(a) * reach, 0, Math.sin(a) * reach), 0.35,
                                ray % 2 == 0 ? ctx.main : ctx.accent);
                    }
                    if (t == impact) spawn(ctx, Particle.BLOCK, g, ctx.n(20), 1.2, 0.1, 1.2, 0.1, stone);
                }
                case BULWARK -> {
                    Location p = ctx.player.getLocation();
                    for (double h : new double[]{0.2, 1.0, 1.8}) {
                        double r = 1.4 - (h > 1.5 ? 0.4 : 0.0);
                        for (int edge = 0; edge < 6; edge++) {
                            double a1 = edge * Math.PI / 3 + t * 0.05;
                            double a2 = (edge + 1) * Math.PI / 3 + t * 0.05;
                            dustLine(ctx, p.clone().add(Math.cos(a1) * r, h, Math.sin(a1) * r),
                                    p.clone().add(Math.cos(a2) * r, h, Math.sin(a2) * r), 0.4, ctx.main);
                        }
                    }
                    spawn(ctx, Particle.ENCHANTED_HIT, p.clone().add(0, 1, 0), ctx.n(2), 0.8, 0.8, 0.8, 0.1);
                }
                case ASCENDANT -> {
                    for (int s = 0; s < 3; s++) {
                        double a = t * 0.5 + s * (Math.PI * 2 / 3);
                        Location p = g.clone().add(Math.cos(a) * 0.9, progress * 4.0, Math.sin(a) * 0.9);
                        spawn(ctx, Particle.TOTEM_OF_UNDYING, p, ctx.n(1), 0.02, 0.02, 0.02, 0.0);
                        spawnDust(ctx, p, 1, 0.02, ctx.accent);
                    }
                }
            }

            if (t == impact) {
                sound(ctx, c, impactSound(shape), 1.2f, 1.0f);
                List<LivingEntity> victims = foes(ctx, c, ctx.radius);
                for (LivingEntity foe : victims) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 1.0 : 0.7));
                }
                applyPayload(ctx, payload, c, victims);
                echoTraits(ctx, victims);
            }
        });
        return start + duration + 1;
    }

    /**
     * Every foe a fusion reaches also suffers the own forge traits of the minerals behind it, so two
     * composites that share a shape still play out their two minerals differently. Each echo flashes in
     * its mineral's colour.
     */
    private static void echoTraits(@Nonnull Ctx ctx, @Nonnull List<LivingEntity> victims) {
        TraitEcho echo = traitEcho;
        List<String> ids = ctx.art.echoes();
        if (echo == null || ids.isEmpty()) return;
        for (LivingEntity victim : victims) {
            if (!victim.isValid() || victim.isDead()) continue;
            for (int i = 0; i < ids.size(); i++) {
                boolean claimed = ModularToolListener.enterWeaponPerk();
                try {
                    echo.echo(ctx.player, victim, ids.get(i));
                } finally {
                    if (claimed) ModularToolListener.exitWeaponPerk();
                }
                spawnDust(ctx, victim.getLocation().add(0, 1.0, 0), ctx.n(5), 0.35, i % 2 == 0 ? ctx.main : ctx.accent);
            }
        }
    }

    @Nonnull
    private static Sound impactSound(@Nonnull TraitAffinity shape) {
        return switch (shape) {
            case INFERNAL -> Sound.ITEM_FIRECHARGE_USE;
            case VOID -> Sound.ENTITY_ENDERMAN_TELEPORT;
            case PRIMAL -> Sound.ENTITY_RAVAGER_ATTACK;
            case TEMPERED -> Sound.BLOCK_ANVIL_PLACE;
            case RADIANT -> Sound.BLOCK_BEACON_POWER_SELECT;
            case RESONANT -> Sound.BLOCK_AMETHYST_BLOCK_RESONATE;
            case VOLATILE -> Sound.ENTITY_GENERIC_EXPLODE;
            case TERRAIN -> Sound.BLOCK_POINTED_DRIPSTONE_LAND;
            case SWIFT -> Sound.ENTITY_BREEZE_WIND_BURST;
            case BRUTAL -> Sound.ITEM_MACE_SMASH_GROUND_HEAVY;
            case BULWARK -> Sound.ITEM_SHIELD_BLOCK;
            case ASCENDANT -> Sound.ITEM_TOTEM_USE;
        };
    }

    private static void applyPayload(@Nonnull Ctx ctx, @Nonnull TraitAffinity payload, @Nonnull Location center,
                                     @Nonnull List<LivingEntity> victims) {
        switch (payload) {
            case INFERNAL -> victims.forEach(v -> v.setFireTicks(Math.max(v.getFireTicks(), 100)));
            case VOID -> victims.forEach(v -> {
                Vector pull = center.toVector().subtract(v.getLocation().toVector());
                if (pull.lengthSquared() > 0.5) v.setVelocity(pull.normalize().multiply(0.6).setY(0.1));
            });
            case PRIMAL -> victims.forEach(v -> strike(ctx, v, ctx.damage(0.25)));
            case TEMPERED -> victims.forEach(v -> v.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1, false, true)));
            case RADIANT -> victims.forEach(v -> {
                v.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0, false, false));
                v.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0, false, true));
            });
            case RESONANT -> Bukkit.getScheduler().runTaskLater(ctx.plugin, () -> {
                ring(ctx, center.clone().subtract(0, 0.8, 0), ctx.radius, ctx.n(16), 0.0, Particle.NOTE);
                for (LivingEntity v : victims) {
                    if (v.isValid()) strike(ctx, v, ctx.damage(0.35));
                }
            }, 6L);
            case VOLATILE -> victims.forEach(v -> {
                push(v, center, 0.8, 0.35);
                v.setFireTicks(Math.max(v.getFireTicks(), 40));
            });
            case TERRAIN -> victims.forEach(v -> v.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 2, false, true)));
            case SWIFT -> {
                buff(ctx.player, PotionEffectType.SPEED, 100, 1);
                buff(ctx.player, PotionEffectType.HASTE, 100, 1);
            }
            case BRUTAL -> victims.forEach(v -> {
                strike(ctx, v, ctx.damage(0.2));
                push(v, ctx.player.getLocation(), 1.2, 0.4);
            });
            case BULWARK -> {
                buff(ctx.player, PotionEffectType.ABSORPTION, 160, 0);
                buff(ctx.player, PotionEffectType.RESISTANCE, 100, 0);
            }
            case ASCENDANT -> {
                heal(ctx.player, 3.0 + ctx.tier.getLevel());
                buff(ctx.player, PotionEffectType.REGENERATION, 60, 0);
            }
        }
    }

    // ==========================================
    // CATALYST OVERLAY (MYTHIC)
    // ==========================================

    private static int catalystOverlay(@Nonnull Ctx ctx, @Nonnull VanillaCatalyst catalyst, int start) {
        int duration = 18;
        int impact = 9;
        ItemStack sigil = new ItemStack(catalyst.getItem());
        BlockData packed = Material.PACKED_ICE.createBlockData();
        CinematicUltimate.Animation shape = catalyst.getUltimate().getAnimation();
        timeline(ctx, start, duration, t -> {
            Location c = ctx.center();
            Location g = ctx.ground();
            if (t < impact) {
                for (int s = 0; s < 3; s++) {
                    double a = t * 0.7 + s * (Math.PI * 2 / 3);
                    Location p = c.clone().add(Math.cos(a) * 1.6, -0.8 + t * 0.25, Math.sin(a) * 1.6);
                    spawn(ctx, Particle.ITEM, p, ctx.n(1), 0.02, 0.02, 0.02, 0.0, sigil);
                    spawnDust(ctx, p, 1, 0.03, ctx.accent);
                }
            }
            switch (shape) {
                case CRYO -> {
                    if (t >= impact) {
                        double h = Math.min(2.4, (t - impact) * 0.4);
                        for (int spike = 0; spike < 10; spike++) {
                            double a = spike * (Math.PI / 5);
                            Location b = g.clone().add(Math.cos(a) * ctx.radius * 0.8, 0, Math.sin(a) * ctx.radius * 0.8);
                            for (double y = 0; y <= h; y += 0.45) {
                                spawn(ctx, Particle.BLOCK, b.clone().add(0, y, 0), ctx.n(1), 0.08, 0.05, 0.08, 0, packed);
                            }
                        }
                    }
                }
                case METEOR, METEOR_STORM -> {
                    if (t >= impact - 4 && t < impact) {
                        for (int m = 0; m < 4; m++) {
                            double a = m * (Math.PI / 2) + 0.6;
                            Location from = g.clone().add(Math.cos(a) * 2.5, 8.0 - (t - impact + 4) * 2.0, Math.sin(a) * 2.5);
                            spawn(ctx, Particle.FLAME, from, ctx.n(4), 0.15, 0.15, 0.15, 0.02);
                            spawn(ctx, Particle.LARGE_SMOKE, from, ctx.n(1), 0.1, 0.1, 0.1, 0.0);
                        }
                    }
                }
                case NOVA -> {
                    if (t >= impact) {
                        double r = (t - impact + 1) * 0.7;
                        ring(ctx, c, r, ctx.n(18), t * 0.2, Particle.FIREWORK);
                        dustRing(ctx, c.clone().add(0, 0.4, 0), r * 0.8, ctx.n(14), 0, ctx.accent);
                    }
                }
                case VORTEX -> {
                    double r = Math.max(0.3, ctx.radius * (1.0 - t / (double) duration));
                    for (int i = 0; i < ctx.n(12); i++) {
                        double a = t * 0.6 + i * 0.52;
                        spawn(ctx, Particle.PORTAL, c.clone().add(Math.cos(a) * r, (i % 4) * 0.4 - 0.6, Math.sin(a) * r), 1, 0, 0, 0, 0);
                    }
                }
                case QUAKE -> {
                    if (t >= impact) {
                        double r = (t - impact + 1) * 0.6;
                        dustRing(ctx, g.clone().add(0, 0.1, 0), r, ctx.n(20), 0, ctx.accent);
                        spawn(ctx, Particle.CAMPFIRE_COSY_SMOKE, g, ctx.n(2), r * 0.5, 0.05, r * 0.5, 0.01);
                    }
                }
                case PILLAR, CAGE -> {
                    if (t >= impact) {
                        for (double y = 0; y < 6.0; y += 0.5) {
                            spawn(ctx, Particle.END_ROD, g.clone().add(0, y, 0), ctx.n(1), 0.15, 0.05, 0.15, 0.0);
                        }
                    }
                }
            }
            if (t == impact) {
                sound(ctx, c, catalyst.getUltimate().getImpactSound(), 1.4f, 1.0f);
                spawn(ctx, Particle.ITEM, c, ctx.n(24), 0.6, 0.6, 0.6, 0.15, sigil);
                List<LivingEntity> victims = foes(ctx, c, ctx.radius);
                for (LivingEntity foe : victims) {
                    strike(ctx, foe, ctx.damage(foe.equals(ctx.target) ? 0.6 : 0.45));
                    switch (shape) {
                        case CRYO -> {
                            foe.setFreezeTicks(Math.max(foe.getFreezeTicks(), 220));
                            foe.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 3, false, true));
                        }
                        case METEOR, METEOR_STORM -> foe.setFireTicks(Math.max(foe.getFireTicks(), 100));
                        case NOVA -> push(foe, c, 1.0, 0.4);
                        case VORTEX -> {
                            Vector pull = c.toVector().subtract(foe.getLocation().toVector());
                            if (pull.lengthSquared() > 0.5) foe.setVelocity(pull.normalize().multiply(0.7));
                        }
                        case QUAKE -> foe.setVelocity(foe.getVelocity().setY(0.8));
                        case CAGE -> foe.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 6, false, true));
                        case PILLAR -> foe.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 0, false, false));
                    }
                }
                if (shape == CinematicUltimate.Animation.PILLAR) heal(ctx.player, 4.0);
            }
        });
        return start + duration + 1;
    }

    // ==========================================
    // DISPLAY ENTITIES
    // ==========================================

    /** A block display that falls from {@code height} above {@code ground} and lands after {@code ticks}. */
    private static void displayDrop(@Nonnull Ctx ctx, @Nonnull Location ground, @Nonnull Material block,
                                    float size, float height, int ticks) {
        if (!displayEntities || !ctx.cosmetic) return;
        safely(ctx, () -> {
            BlockDisplay display = ctx.world.spawn(ground.clone(), BlockDisplay.class, d -> {
                d.setBlock(block.createBlockData());
                d.setPersistent(false);
                d.setBrightness(new Display.Brightness(15, 15));
                d.setTransformation(transform(-size / 2f, height, -size / 2f, size, size, size));
            });
            DISPLAYS.add(display);
            Bukkit.getScheduler().runTaskLater(ctx.plugin, () -> {
                if (!display.isValid()) return;
                display.setInterpolationDelay(0);
                display.setInterpolationDuration(Math.max(1, ticks - 2));
                display.setTransformation(transform(-size / 2f, 0f, -size / 2f, size, size, size));
            }, 2L);
            removeLater(ctx, display, ticks + 22L);
        });
    }

    /** A block display that grows out of the ground into a pillar and vanishes after {@code lifetime}. */
    private static void displayPillar(@Nonnull Ctx ctx, @Nonnull Location base, @Nonnull Material block,
                                      float width, float height, int growTicks, int lifetime) {
        if (!displayEntities || !ctx.cosmetic) return;
        safely(ctx, () -> {
            BlockDisplay display = ctx.world.spawn(base.clone(), BlockDisplay.class, d -> {
                d.setBlock(block.createBlockData());
                d.setPersistent(false);
                d.setBrightness(new Display.Brightness(15, 15));
                d.setTransformation(transform(-width / 2f, 0f, -width / 2f, width, 0.05f, width));
            });
            DISPLAYS.add(display);
            Bukkit.getScheduler().runTaskLater(ctx.plugin, () -> {
                if (!display.isValid()) return;
                display.setInterpolationDelay(0);
                display.setInterpolationDuration(growTicks);
                display.setTransformation(transform(-width / 2f, 0f, -width / 2f, width, height, width));
            }, 2L);
            removeLater(ctx, display, lifetime);
        });
    }

    /** A glowing obelisk for the mythic finale. */
    private static void obelisk(@Nonnull Ctx ctx, @Nonnull Location base, @Nonnull Material block,
                                float height, int growTicks, int lifetime) {
        if (!displayEntities || !ctx.cosmetic) return;
        safely(ctx, () -> {
            BlockDisplay display = ctx.world.spawn(base.clone(), BlockDisplay.class, d -> {
                d.setBlock(block.createBlockData());
                d.setPersistent(false);
                d.setGlowing(true);
                d.setGlowColorOverride(ctx.tier.color());
                d.setBrightness(new Display.Brightness(15, 15));
                d.setTransformation(transform(-0.35f, 0f, -0.35f, 0.7f, 0.05f, 0.7f));
            });
            DISPLAYS.add(display);
            Bukkit.getScheduler().runTaskLater(ctx.plugin, () -> {
                if (!display.isValid()) return;
                display.setInterpolationDelay(0);
                display.setInterpolationDuration(growTicks);
                display.setTransformation(transform(-0.35f, 0f, -0.35f, 0.7f, height, 0.7f));
            }, 2L);
            removeLater(ctx, display, lifetime);
        });
    }

    @Nonnull
    private static Transformation transform(float tx, float ty, float tz, float sx, float sy, float sz) {
        return new Transformation(new Vector3f(tx, ty, tz), new AxisAngle4f(),
                new Vector3f(sx, sy, sz), new AxisAngle4f());
    }

    private static void removeLater(@Nonnull Ctx ctx, @Nonnull Entity entity, long ticks) {
        Bukkit.getScheduler().runTaskLater(ctx.plugin, () -> {
            DISPLAYS.remove(entity);
            if (entity.isValid()) entity.remove();
        }, Math.max(1L, ticks));
    }

    // ==========================================
    // COMBAT HELPERS
    // ==========================================

    /** Living foes around a point: never the wielder, an armor stand or the wielder's own pets. */
    @Nonnull
    private static List<LivingEntity> foes(@Nonnull Ctx ctx, @Nonnull Location center, double radius) {
        List<LivingEntity> out = new ArrayList<>();
        if (center.getWorld() == null) return out;
        double r2 = radius * radius;
        for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living.equals(ctx.player) || living.isDead() || !living.isValid()) continue;
            if (living instanceof ArmorStand) continue;
            if (living instanceof Tameable pet && pet.isTamed() && ctx.player.equals(pet.getOwner())) continue;
            if (living instanceof Player p && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR)) continue;
            if (living.getLocation().distanceSquared(center) > r2 * 1.5) continue;
            out.add(living);
        }
        return out;
    }

    /** Damages a victim on behalf of the wielder without letting the hit re-trigger any perk. */
    private static void strike(@Nonnull Ctx ctx, @Nullable LivingEntity victim, double amount) {
        if (victim == null || amount <= 0.0 || victim.isDead() || !victim.isValid()) return;
        boolean claimed = ModularToolListener.enterWeaponPerk();
        try {
            victim.setNoDamageTicks(0);
            victim.damage(amount, ctx.player);
        } finally {
            if (claimed) ModularToolListener.exitWeaponPerk();
        }
    }

    /** Armor-piercing damage: magic ignores armor points. Falls back to a plain strike if unsupported. */
    private static void pierce(@Nonnull Ctx ctx, @Nullable LivingEntity victim, double amount) {
        if (victim == null || amount <= 0.0 || victim.isDead() || !victim.isValid()) return;
        boolean claimed = ModularToolListener.enterWeaponPerk();
        try {
            victim.setNoDamageTicks(0);
            DamageSource source = DamageSource.builder(DamageType.MAGIC)
                    .withCausingEntity(ctx.player)
                    .withDirectEntity(ctx.player)
                    .build();
            victim.damage(amount, source);
        } catch (RuntimeException | LinkageError unsupported) {
            victim.damage(amount, ctx.player);
        } finally {
            if (claimed) ModularToolListener.exitWeaponPerk();
        }
    }

    private static void push(@Nonnull LivingEntity victim, @Nonnull Location from, double strength, double lift) {
        Vector away = victim.getLocation().toVector().subtract(from.toVector()).setY(0);
        if (away.lengthSquared() < 1.0E-4) return;
        victim.setVelocity(away.normalize().multiply(strength).setY(lift));
    }

    private static void buff(@Nonnull Player player, @Nonnull PotionEffectType type, int ticks, int amplifier) {
        player.addPotionEffect(new PotionEffect(type, ticks, Math.max(0, amplifier), false, true));
    }

    private static void heal(@Nonnull Player player, double amount) {
        if (amount <= 0.0 || player.isDead()) return;
        double max = maxHealth(player);
        player.setHealth(Math.min(max, player.getHealth() + amount));
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 2.0, 0), 3, 0.3, 0.2, 0.3, 0.0);
    }

    private static double maxHealth(@Nonnull LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attribute.MAX_HEALTH);
        return attribute != null ? attribute.getValue() : 20.0;
    }

    private static boolean isStandable(@Nonnull Location feet) {
        if (feet.getWorld() == null) return false;
        return !feet.getBlock().getType().isSolid() && !feet.clone().add(0, 1, 0).getBlock().getType().isSolid();
    }

    private static void safely(@Nonnull Ctx ctx, @Nonnull Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            // Purely cosmetic extras (lightning effects, display entities) may be unavailable on some
            // server implementations; the art itself must still land.
            ctx.plugin.getLogger().log(Level.FINE, "Signature art extra skipped: " + e.getMessage());
        }
    }

    // ==========================================
    // RENDER HELPERS
    // ==========================================

    private static void spawn(@Nonnull Ctx ctx, @Nonnull Particle particle, @Nonnull Location at, int count,
                              double dx, double dy, double dz, double speed) {
        if (count <= 0 || !ctx.cosmetic) return;
        ctx.world.spawnParticle(particle, at, count, dx, dy, dz, speed);
    }

    private static <T> void spawn(@Nonnull Ctx ctx, @Nonnull Particle particle, @Nonnull Location at, int count,
                                  double dx, double dy, double dz, double speed, @Nonnull T data) {
        if (count <= 0 || !ctx.cosmetic) return;
        ctx.world.spawnParticle(particle, at, count, dx, dy, dz, speed, data);
    }

    private static void spawnDust(@Nonnull Ctx ctx, @Nonnull Location at, int count, double spread,
                                  @Nonnull Particle.DustOptions dust) {
        if (count <= 0 || !ctx.cosmetic) return;
        ctx.world.spawnParticle(Particle.DUST, at, count, spread, spread, spread, 0.0, dust);
    }

    private static void spawnTransition(@Nonnull Ctx ctx, @Nonnull Location at, int count, double spread) {
        if (count <= 0 || !ctx.cosmetic) return;
        ctx.world.spawnParticle(Particle.DUST_COLOR_TRANSITION, at, count, spread, spread, spread, 0.0, ctx.transition);
    }

    private static void ring(@Nonnull Ctx ctx, @Nonnull Location c, double r, int points, double phase,
                             @Nonnull Particle particle) {
        if (points <= 0 || !ctx.cosmetic) return;
        for (int i = 0; i < points; i++) {
            double a = phase + i * (Math.PI * 2 / points);
            ctx.world.spawnParticle(particle, c.clone().add(Math.cos(a) * r, 0, Math.sin(a) * r), 1, 0, 0, 0, 0);
        }
    }

    private static void dustRing(@Nonnull Ctx ctx, @Nonnull Location c, double r, int points, double phase,
                                 @Nonnull Particle.DustOptions dust) {
        if (points <= 0 || !ctx.cosmetic) return;
        for (int i = 0; i < points; i++) {
            double a = phase + i * (Math.PI * 2 / points);
            ctx.world.spawnParticle(Particle.DUST, c.clone().add(Math.cos(a) * r, 0, Math.sin(a) * r), 1, 0, 0, 0, 0, dust);
        }
    }

    /** A ring standing upright, facing the wielder's line of sight. */
    private static void verticalRing(@Nonnull Ctx ctx, @Nonnull Location c, @Nonnull Vector side, double r,
                                     int points, double phase, @Nonnull Particle.DustOptions dust) {
        if (points <= 0 || !ctx.cosmetic) return;
        for (int i = 0; i < points; i++) {
            double a = phase + i * (Math.PI * 2 / points);
            Location p = c.clone().add(side.clone().multiply(Math.cos(a) * r)).add(0, Math.sin(a) * r, 0);
            ctx.world.spawnParticle(Particle.DUST, p, 1, 0, 0, 0, 0, dust);
        }
    }

    /** A ring tilted around the X axis by {@code tilt} and then turned around Y by {@code yaw}. */
    private static void tiltedRing(@Nonnull Ctx ctx, @Nonnull Location c, double r, double tilt, double yaw,
                                   double phase, @Nonnull Particle.DustOptions dust) {
        int points = ctx.n(22);
        if (points <= 0) return;
        for (int i = 0; i < points; i++) {
            double a = phase + i * (Math.PI * 2 / points);
            Vector v = new Vector(Math.cos(a) * r, 0, Math.sin(a) * r);
            v.rotateAroundX(tilt).rotateAroundY(yaw);
            ctx.world.spawnParticle(Particle.DUST, c.clone().add(v), 1, 0, 0, 0, 0, dust);
        }
    }

    private static void line(@Nonnull Ctx ctx, @Nonnull Location a, @Nonnull Location b, double step,
                             @Nonnull Particle particle) {
        if (!ctx.cosmetic) return;
        Vector delta = b.toVector().subtract(a.toVector());
        double length = delta.length();
        if (length < 1.0E-4) return;
        int points = Math.max(1, (int) Math.ceil(length / Math.max(0.05, step / Math.sqrt(ctx.scale))));
        for (int i = 0; i <= points; i++) {
            ctx.world.spawnParticle(particle, a.clone().add(delta.clone().multiply(i / (double) points)), 1, 0, 0, 0, 0);
        }
    }

    private static void dustLine(@Nonnull Ctx ctx, @Nonnull Location a, @Nonnull Location b, double step,
                                 @Nonnull Particle.DustOptions dust) {
        if (!ctx.cosmetic) return;
        Vector delta = b.toVector().subtract(a.toVector());
        double length = delta.length();
        if (length < 1.0E-4) return;
        int points = Math.max(1, (int) Math.ceil(length / Math.max(0.05, step / Math.sqrt(ctx.scale))));
        for (int i = 0; i <= points; i++) {
            ctx.world.spawnParticle(Particle.DUST, a.clone().add(delta.clone().multiply(i / (double) points)), 1, 0, 0, 0, 0, dust);
        }
    }

    /** A lightning bolt: a line broken into jittered segments. */
    private static void jagged(@Nonnull Ctx ctx, @Nonnull Location a, @Nonnull Location b) {
        if (!ctx.cosmetic) return;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Vector delta = b.toVector().subtract(a.toVector());
        int segments = Math.max(4, (int) Math.round(delta.length() * 1.5));
        Location previous = a.clone();
        for (int i = 1; i <= segments; i++) {
            Location next = a.clone().add(delta.clone().multiply(i / (double) segments));
            if (i < segments) {
                next.add(random.nextDouble(-0.3, 0.3), random.nextDouble(-0.3, 0.3), random.nextDouble(-0.3, 0.3));
            }
            line(ctx, previous, next, 0.25, Particle.ELECTRIC_SPARK);
            dustLine(ctx, previous, next, 0.3, i % 2 == 0 ? ctx.main : ctx.accent);
            previous = next;
        }
    }

    /** A bezier stream from {@code from} to {@code to}, drawn up to {@code progress} with a bright head. */
    private static void curve(@Nonnull Ctx ctx, @Nonnull Location from, @Nonnull Location to, @Nonnull Vector bend,
                              double progress, @Nonnull Particle head, @Nonnull Particle.DustOptions dust) {
        if (!ctx.cosmetic) return;
        Vector p0 = from.toVector();
        Vector p2 = to.toVector();
        Vector p1 = p0.clone().add(p2).multiply(0.5).add(bend);
        int samples = Math.max(4, ctx.n(10));
        int drawn = (int) Math.round(samples * Math.max(0.0, Math.min(1.0, progress)));
        for (int i = 0; i <= drawn; i++) {
            double s = i / (double) samples;
            double u = 1 - s;
            Vector point = p0.clone().multiply(u * u).add(p1.clone().multiply(2 * u * s)).add(p2.clone().multiply(s * s));
            Location at = point.toLocation(ctx.world);
            ctx.world.spawnParticle(Particle.DUST, at, 1, 0, 0, 0, 0, dust);
            if (i == drawn) ctx.world.spawnParticle(head, at, 1, 0.02, 0.02, 0.02, 0.0);
        }
    }

    private static void sound(@Nonnull Ctx ctx, @Nonnull Location at, @Nonnull Sound sound, float volume, float pitch) {
        if (!EquipmentAnimation.isSoundsEnabled()) return;
        ctx.world.playSound(at, sound, volume, pitch);
    }

    private static void playSound(@Nonnull World world, @Nonnull Location at, @Nonnull Sound sound, float volume, float pitch) {
        if (!EquipmentAnimation.isSoundsEnabled()) return;
        world.playSound(at, sound, volume, pitch);
    }
}
