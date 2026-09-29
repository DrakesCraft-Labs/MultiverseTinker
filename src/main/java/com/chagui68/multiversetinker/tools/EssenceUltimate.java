package com.chagui68.multiversetinker.tools;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Cinematic attack ultimate bound to the dominant essence of a forged weapon.
 *
 * <p>Where {@link TraitEffectEngine} adds quiet, repeatable procs, an ultimate is a rare, loud
 * spectacle: a multi-phase animation (wind-up, impact, root hold) that pins the struck enemy in
 * place while meteors, vortexes, light pillars or cages are rendered around it.</p>
 */
public enum EssenceUltimate implements CinematicUltimate {

    INFERNAL("Meteor Storm", Animation.METEOR_STORM, Particle.SOUL_FIRE_FLAME, Particle.LAVA,
            Sound.ENTITY_BLAZE_SHOOT, Sound.ENTITY_GENERIC_EXPLODE, "#FF6B35",
            1.40, 4.0, 60, "Meteors crash down and pin the target in a ring of fire."),

    VOID("Singularity Collapse", Animation.VORTEX, Particle.PORTAL, Particle.REVERSE_PORTAL,
            Sound.BLOCK_PORTAL_TRIGGER, Sound.ENTITY_ENDER_DRAGON_GROWL, "#7B4FBF",
            1.35, 4.0, 60, "Space folds inward, crushing and rooting everything it drags in."),

    PRIMAL("Primal Outburst", Animation.NOVA, Particle.CRIT, Particle.ENCHANTED_HIT,
            Sound.ENTITY_PLAYER_ATTACK_CRIT, Sound.ITEM_TRIDENT_THUNDER, "#2ECC71",
            1.30, 3.5, 40, "A raw shockwave bursts outward and stuns the target."),

    TEMPERED("Tempered Slam", Animation.QUAKE, Particle.CRIT, Particle.CLOUD,
            Sound.BLOCK_ANVIL_LAND, Sound.ITEM_MACE_SMASH_GROUND, "#95A5A6",
            1.25, 3.5, 45, "A hardened slam cracks the ground and nails the target in place."),

    RADIANT("Radiant Judgement", Animation.PILLAR, Particle.END_ROD, Particle.FIREWORK,
            Sound.BLOCK_AMETHYST_BLOCK_CHIME, Sound.ENTITY_PLAYER_LEVELUP, "#F1C40F",
            1.30, 3.5, 50, "A pillar of light descends and freezes the judged in radiance."),

    RESONANT("Harmonic Overload", Animation.NOVA, Particle.NOTE, Particle.CHERRY_LEAVES,
            Sound.BLOCK_AMETHYST_BLOCK_RESONATE, Sound.ENTITY_WARDEN_SONIC_BOOM, "#55FFFF",
            1.30, 4.0, 40, "A sonic overload resonates through nearby foes and locks the target."),

    VOLATILE("Volatile Cataclysm", Animation.METEOR, Particle.FLAME, Particle.LAVA,
            Sound.ENTITY_TNT_PRIMED, Sound.ENTITY_GENERIC_EXPLODE, "#FF4C4C",
            1.45, 4.0, 40, "Unstable fire rains down and detonates in a rooted inferno."),

    TERRAIN("Earthen Grasp", Animation.QUAKE, Particle.CAMPFIRE_COSY_SMOKE, Particle.CRIT,
            Sound.BLOCK_ROOTED_DIRT_BREAK, Sound.ITEM_MACE_SMASH_GROUND_HEAVY, "#D4A017",
            1.30, 4.0, 55, "Stone hands erupt from the soil and hold the target fast."),

    SWIFT("Gale Cyclone", Animation.VORTEX, Particle.CLOUD, Particle.ELECTRIC_SPARK,
            Sound.ENTITY_PHANTOM_FLAP, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, "#00FFFF",
            1.25, 3.5, 40, "A lightning cyclone pins the target at its eye."),

    BRUTAL("Crushing Impact", Animation.QUAKE, Particle.CRIT, Particle.EXPLOSION,
            Sound.ITEM_MACE_SMASH_GROUND_HEAVY, Sound.ENTITY_GENERIC_EXPLODE, "#8B0000",
            1.40, 4.0, 50, "A brutal ground break hurls nearby foes and buries the target."),

    BULWARK("Bastion Cage", Animation.CAGE, Particle.ENCHANTED_HIT, Particle.END_ROD,
            Sound.BLOCK_ANVIL_LAND, Sound.BLOCK_BEACON_ACTIVATE, "#5DADE2",
            1.20, 3.0, 60, "An immovable cage slams shut around the target and holds it."),

    ASCENDANT("Ascendant Descension", Animation.PILLAR, Particle.TOTEM_OF_UNDYING, Particle.END_ROD,
            Sound.BLOCK_BEACON_ACTIVATE, Sound.ENTITY_PLAYER_LEVELUP, "#D2B4DE",
            1.35, 3.5, 50, "Transcendent light descends, roots the target and mends the wielder.");

    private final String displayName;
    private final CinematicUltimate.Animation animation;
    private final Particle trailParticle;
    private final Particle accentParticle;
    private final Sound chargeSound;
    private final Sound impactSound;
    private final String colorHex;
    private final Color color;
    private final double damageMultiplier;
    private final double radius;
    private final int rootTicks;
    private final String description;

    EssenceUltimate(String displayName, CinematicUltimate.Animation animation,
                    Particle trailParticle, Particle accentParticle,
                    Sound chargeSound, Sound impactSound, String colorHex,
                    double damageMultiplier, double radius, int rootTicks, String description) {
        this.displayName = displayName;
        this.animation = animation;
        this.trailParticle = trailParticle;
        this.accentParticle = accentParticle;
        this.chargeSound = chargeSound;
        this.impactSound = impactSound;
        this.colorHex = colorHex;
        this.color = parseColor(colorHex);
        this.damageMultiplier = damageMultiplier;
        this.radius = radius;
        this.rootTicks = rootTicks;
        this.description = description;
    }

    @Nonnull
    private static Color parseColor(@Nonnull String hex) {
        try {
            return Color.fromRGB(Integer.parseInt(hex.replace("#", ""), 16));
        } catch (NumberFormatException e) {
            return Color.WHITE;
        }
    }

    /** Ultimate cast by the dominant essence of a weapon. */
    @Nonnull
    public static EssenceUltimate of(@Nonnull TraitAffinity affinity) {
        return switch (affinity) {
            case INFERNAL -> INFERNAL;
            case VOID -> VOID;
            case PRIMAL -> PRIMAL;
            case TEMPERED -> TEMPERED;
            case RADIANT -> RADIANT;
            case RESONANT -> RESONANT;
            case VOLATILE -> VOLATILE;
            case TERRAIN -> TERRAIN;
            case SWIFT -> SWIFT;
            case BRUTAL -> BRUTAL;
            case BULWARK -> BULWARK;
            case ASCENDANT -> ASCENDANT;
        };
    }

    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    @Nonnull
    @Override
    public CinematicUltimate.Animation getAnimation() {
        return animation;
    }

    @Nonnull
    public Particle getTrailParticle() {
        return trailParticle;
    }

    @Nonnull
    public Particle getAccentParticle() {
        return accentParticle;
    }

    @Nonnull
    public Sound getChargeSound() {
        return chargeSound;
    }

    @Nonnull
    public Sound getImpactSound() {
        return impactSound;
    }

    @Nonnull
    public String getColorHex() {
        return colorHex;
    }

    @Nonnull
    public Color getColor() {
        return color;
    }

    /** Multiplier applied to the triggering hit's damage on the ultimate's impact. */
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    /** Radius of the impact shockwave, in blocks. */
    public double getRadius() {
        return radius;
    }

    /** How long the struck enemy is rooted, in ticks. */
    public int getRootTicks() {
        return rootTicks;
    }

    /** Essence ultimates do not freeze: frost belongs to the prime cryo spectacles. */
    @Override
    public int getFreezeTicks() {
        return 0;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nonnull
    public String getMiniMessageTag() {
        return "<" + colorHex.toLowerCase(Locale.ROOT) + ">" + displayName + "</" + colorHex.toLowerCase(Locale.ROOT) + ">";
    }

    @Override
    public String toString() {
        return displayName + " (" + animation + ")";
    }
}
