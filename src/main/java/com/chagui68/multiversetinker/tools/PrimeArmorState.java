package com.chagui68.multiversetinker.tools;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * New defensive states unlocked by <b>prime alloys</b> in modular armor.
 *
 * <p>Each state is a deterministic reaction to being hit, so a full prime set has a personality you
 * can read from the lore: frost armor answers with a freezing blast, gravitic armor drags attackers
 * in, storm armor calls lightning down on whoever dares to hit you. States come from the alloy's
 * catalyst sigil when it has one, otherwise from the alloy's dominant essence.</p>
 */
public enum PrimeArmorState {

    FROSTBOUND("Frostbound", NamedTextColor.AQUA, Particle.SNOWFLAKE, Sound.ENTITY_PLAYER_HURT_FREEZE,
            "Answers every hit with a freezing blast that slows and frostbites the attacker.", 200),

    METEOR_WARD("Meteor Ward", NamedTextColor.GOLD, Particle.LAVA, Sound.ENTITY_GENERIC_EXPLODE,
            "Burning meteors crash down on whoever strikes you.", 200),

    GRAVITIC_ANCHOR("Gravitic Anchor", NamedTextColor.DARK_PURPLE, Particle.PORTAL, Sound.BLOCK_BEACON_ACTIVATE,
            "Pulls attackers into melee range and hardens you against knockback.", 160),

    PRIME_AEGIS("Prime Aegis", NamedTextColor.LIGHT_PURPLE, Particle.ENCHANTED_HIT, Sound.BLOCK_ANVIL_LAND,
            "Spins up a hardened aegis of absorption and resistance when struck.", 160),

    STORMCALL("Stormcall", NamedTextColor.YELLOW, Particle.ELECTRIC_SPARK, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,
            "Calls lightning down on the attacker and hastens your own step.", 240),

    EMBER_VEIL("Ember Veil", NamedTextColor.RED, Particle.FLAME, Sound.ENTITY_BLAZE_SHOOT,
            "Sets the attacker ablaze and wraps you in fire resistance.", 120),

    VOID_SHELL("Void Shell", NamedTextColor.DARK_AQUA, Particle.REVERSE_PORTAL, Sound.ENTITY_ENDERMAN_TELEPORT,
            "Lifts the attacker off the ground and lets you fall like a feather.", 200),

    PRISM_BULWARK("Prism Bulwark", NamedTextColor.BLUE, Particle.END_ROD, Sound.BLOCK_AMETHYST_BLOCK_CHIME,
            "Reflects part of the blow back at the attacker and slowly mends your wounds.", 240),

    TECTONIC_GUARD("Tectonic Guard", NamedTextColor.DARK_GREEN, Particle.CAMPFIRE_COSY_SMOKE, Sound.BLOCK_ROOTED_DIRT_BREAK,
            "Erupts a slab of bedrock that knocks the attacker prone.", 180);

    private final String displayName;
    private final NamedTextColor color;
    private final Particle particle;
    private final Sound sound;
    private final String description;
    private final int cooldownTicks;

    PrimeArmorState(String displayName, NamedTextColor color, Particle particle, Sound sound,
                    String description, int cooldownTicks) {
        this.displayName = displayName;
        this.color = color;
        this.particle = particle;
        this.sound = sound;
        this.description = description;
        this.cooldownTicks = cooldownTicks;
    }

    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    @Nonnull
    public NamedTextColor getColor() {
        return color;
    }

    @Nonnull
    public Particle getParticle() {
        return particle;
    }

    @Nonnull
    public Sound getSound() {
        return sound;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    /** How often this state may fire, in ticks. */
    public int getCooldownTicks() {
        return cooldownTicks;
    }

    /** Lore line shown on prime armor pieces. */
    @Nonnull
    public String getLoreLine() {
        return "✦ Prime State: " + displayName + " — " + description;
    }

    /**
     * Resolves the state an armor piece teaches: the catalyst sigil when the prime was forged with
     * one, otherwise the state matching the alloy's dominant essence.
     */
    @Nonnull
    public static PrimeArmorState of(@Nonnull com.chagui68.multiversetinker.materials.TinkerMaterial material) {
        String parents = material.getAlloyParents();
        if (parents != null) {
            for (String parentId : parents.split(",")) {
                VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(parentId.trim());
                if (catalyst != null) return catalyst.getArmorState();
            }
        }
        java.util.Iterator<TraitAffinity> dominant = TraitAffinity.of(material).iterator();
        return fromAffinity(dominant.hasNext() ? dominant.next() : TraitAffinity.PRIMAL);
    }

    /** Deterministic essence fallback used when a prime carries no catalyst sigil. */
    @Nonnull
    public static PrimeArmorState fromAffinity(@Nonnull TraitAffinity affinity) {
        return switch (affinity) {
            case INFERNAL -> METEOR_WARD;
            case VOID -> VOID_SHELL;
            case PRIMAL -> GRAVITIC_ANCHOR;
            case TEMPERED -> TECTONIC_GUARD;
            case RADIANT, RESONANT -> PRISM_BULWARK;
            case VOLATILE -> EMBER_VEIL;
            case TERRAIN -> GRAVITIC_ANCHOR;
            case SWIFT -> STORMCALL;
            case BRUTAL -> EMBER_VEIL;
            case BULWARK -> PRIME_AEGIS;
            case ASCENDANT -> PRIME_AEGIS;
        };
    }

    /**
     * Applies the state's reaction. Deterministic: the same state always produces the same effect,
     * which is what makes it documentable and testable.
     */
    public void apply(@Nonnull Player player, @Nullable LivingEntity attacker) {
        World world = player.getWorld();
        Location at = player.getLocation().add(0, 1, 0);
        world.playSound(player.getLocation(), sound, 1.0f, 1.1f);
        world.spawnParticle(particle, at, 12, 0.4, 0.5, 0.4, 0.03);

        switch (this) {
            case FROSTBOUND -> {
                if (attacker != null) {
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 2, false, true));
                    attacker.setFreezeTicks(Math.max(attacker.getFreezeTicks(), 160));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 40, 0, false, false));
                world.spawnParticle(Particle.SNOWFLAKE, at, 20, 0.6, 0.6, 0.6, 0.02);
            }
            case METEOR_WARD -> {
                if (attacker != null) {
                    Location strike = attacker.getLocation();
                    for (int i = 0; i < 6; i++) {
                        Location point = strike.clone().add(0, 6 - i, 0);
                        world.spawnParticle(Particle.LAVA, point, 2, 0.1, 0.1, 0.1, 0.0);
                        world.spawnParticle(Particle.FLAME, point, 3, 0.15, 0.15, 0.15, 0.02);
                    }
                    attacker.setNoDamageTicks(0);
                    attacker.damage(3.0, player);
                    attacker.setFireTicks(60);
                    world.spawnParticle(Particle.EXPLOSION_EMITTER, attacker.getLocation(), 1);
                }
            }
            case GRAVITIC_ANCHOR -> {
                if (attacker != null) {
                    Vector pull = player.getLocation().toVector().subtract(attacker.getLocation().toVector());
                    if (pull.lengthSquared() > 0.0001) {
                        attacker.setVelocity(pull.normalize().multiply(0.8).setY(0.25));
                    }
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 1, false, false));
            }
            case PRIME_AEGIS -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 80, 1, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 1, false, false));
            }
            case STORMCALL -> {
                if (attacker != null) {
                    world.strikeLightningEffect(attacker.getLocation());
                    attacker.setNoDamageTicks(0);
                    attacker.damage(2.5, player);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, false, false));
            }
            case EMBER_VEIL -> {
                if (attacker != null) attacker.setFireTicks(120);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 120, 0, false, false));
            }
            case VOID_SHELL -> {
                if (attacker != null) {
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 30, 1, false, true));
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 1, false, true));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 120, 0, false, false));
                player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 1.0));
            }
            case PRISM_BULWARK -> {
                if (attacker != null) {
                    Vector away = attacker.getLocation().toVector().subtract(player.getLocation().toVector());
                    if (away.lengthSquared() > 0.0001) {
                        attacker.setVelocity(away.normalize().multiply(0.5).setY(0.3));
                    }
                    attacker.setNoDamageTicks(0);
                    attacker.damage(2.0, player);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 0, false, false));
            }
            case TECTONIC_GUARD -> {
                if (attacker != null) {
                    attacker.setVelocity(new Vector(0, 0.35, 0));
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 2, false, true));
                    world.spawnParticle(Particle.BLOCK, attacker.getLocation(),
                            18, 0.5, 0.4, 0.5, 0.05, Material.STONE.createBlockData());
                }
            }
        }
    }
}
