package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * Cinematic attack unleashed by weapons forged from <b>prime alloys</b>.
 *
 * <p>Where an {@link EssenceUltimate} is the payoff of a pure mineral focus, a prime ultimate is the
 * reward for fusing legendary metallurgy: the spectacles are louder, hit harder and two of them -
 * {@link #ABSOLUTE_ZERO} and {@link #GLACIER_TOMB} - freeze the battlefield solid with an erupting
 * ice field, while {@link #METEOR_CASCADE} and {@link #SUPERNOVA} drop burning rock from the sky.</p>
 */
public enum PrimeUltimate implements CinematicUltimate {

    ABSOLUTE_ZERO("Absolute Zero", Animation.CRYO, Particle.SNOWFLAKE, Particle.ITEM_SNOWBALL,
            Sound.BLOCK_GLASS_BREAK, Sound.ENTITY_PLAYER_HURT_FREEZE, "#AEE8FF",
            1.60, 5.0, 70, 400,
            "The air turns to glass: ice spikes erupt around the target and it freezes solid."),

    METEOR_CASCADE("Meteor Cascade", Animation.METEOR_STORM, Particle.FLAME, Particle.LAVA,
            Sound.ENTITY_BLAZE_SHOOT, Sound.ENTITY_GENERIC_EXPLODE, "#FF6B35",
            1.75, 5.0, 70, 0,
            "A burning storm of meteors rains over the whole area and buries the target."),

    SUPERNOVA("Supernova", Animation.NOVA, Particle.FIREWORK, Particle.END_ROD,
            Sound.ENTITY_ILLUSIONER_CAST_SPELL, Sound.ENTITY_WITHER_SPAWN, "#FFF176",
            1.70, 5.5, 60, 0,
            "The alloy detonates like a star, blasting everything nearby away from the epicentre."),

    EVENT_HORIZON("Event Horizon", Animation.VORTEX, Particle.PORTAL, Particle.REVERSE_PORTAL,
            Sound.BLOCK_BEACON_ACTIVATE, Sound.ENTITY_ENDER_DRAGON_GROWL, "#8E44AD",
            1.65, 5.5, 80, 0,
            "A black hole opens and drags nearby foes into the crushing centre."),

    GLACIER_TOMB("Glacier Tomb", Animation.CRYO, Particle.SNOWFLAKE, Particle.WHITE_ASH,
            Sound.BLOCK_POWDER_SNOW_BREAK, Sound.ENTITY_PLAYER_HURT_FREEZE, "#7FD8FF",
            1.55, 4.5, 90, 300,
            "A glacier slams shut around the target, locking it inside a tomb of frost."),

    TECTONIC_RIFT("Tectonic Rift", Animation.QUAKE, Particle.CAMPFIRE_COSY_SMOKE, Particle.EXPLOSION,
            Sound.BLOCK_ANVIL_LAND, Sound.ITEM_MACE_SMASH_GROUND_HEAVY, "#D4A017",
            1.65, 5.0, 60, 0,
            "The crust splits and the shockwave hurls every nearby foe off their feet."),

    PRISMATIC_ASCENSION("Prismatic Ascension", Animation.PILLAR, Particle.END_ROD, Particle.FIREWORK,
            Sound.BLOCK_AMETHYST_BLOCK_RESONATE, Sound.ENTITY_PLAYER_LEVELUP, "#E056FD",
            1.60, 4.5, 60, 0,
            "A prismatic pillar descends, transfixing the target and mending the wielder.");

    private final String displayName;
    private final Animation animation;
    private final Particle trailParticle;
    private final Particle accentParticle;
    private final Sound chargeSound;
    private final Sound impactSound;
    private final String colorHex;
    private final Color color;
    private final double damageMultiplier;
    private final double radius;
    private final int rootTicks;
    private final int freezeTicks;
    private final String description;

    PrimeUltimate(String displayName, Animation animation,
                  Particle trailParticle, Particle accentParticle,
                  Sound chargeSound, Sound impactSound, String colorHex,
                  double damageMultiplier, double radius, int rootTicks, int freezeTicks,
                  String description) {
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
        this.freezeTicks = freezeTicks;
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

    /** True when this spectacle freezes its victims instead of burning them. */
    public boolean isCryo() {
        return animation == Animation.CRYO;
    }

    /**
     * Resolves the prime ultimate of a forged weapon.
     *
     * <p>The strongest prime material in the composition wins: a catalyst-sigil prime uses the
     * catalyst's own spectacle, every other prime falls back to the ultimate matching the weapon's
     * dominant essence. Returns {@code null} when the weapon holds no prime alloy at all, which is
     * the signal for the caller to play the ordinary {@link EssenceUltimate} instead.</p>
     */
    @Nullable
    public static PrimeUltimate forWeapon(@Nonnull List<PartComposition> parts,
                                          @Nullable WeaponPerkProfile profile) {
        PrimeUltimate sigil = null;
        TinkerMaterial firstPrime = null;

        for (PartComposition part : parts) {
            for (PartComposition.Entry entry : part.getEntries()) {
                TinkerMaterial material = entry.material();
                if (!AlloyRegistry.isPrime(material)) continue;
                if (firstPrime == null) firstPrime = material;
                PrimeUltimate fromSigil = sigilOf(material);
                if (fromSigil != null && (sigil == null || fromSigil.ordinal() < sigil.ordinal())) {
                    sigil = fromSigil;
                }
            }
        }

        if (firstPrime == null) return null;
        if (sigil != null) return sigil;
        if (profile != null) return fromAffinity(profile.getAffinity());
        return of(firstPrime);
    }

    /** Ultimate a prime alloy casts on its own, without the context of a whole weapon. */
    @Nonnull
    public static PrimeUltimate of(@Nonnull TinkerMaterial prime) {
        PrimeUltimate sigil = sigilOf(prime);
        if (sigil != null) return sigil;
        return fromAffinity(TraitAffinity.of(prime).iterator().next());
    }

    /** Spectacle granted by the catalyst sigil carried by this prime, if any. */
    @Nullable
    private static PrimeUltimate sigilOf(@Nonnull TinkerMaterial prime) {
        String parents = prime.getAlloyParents();
        if (parents == null) return null;
        for (String parentId : parents.split(",")) {
            VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(parentId.trim());
            if (catalyst != null) return catalyst.getUltimate();
        }
        return null;
    }

    /** Essence-flavoured default for primes that were not forged with a vanilla catalyst. */
    @Nonnull
    public static PrimeUltimate fromAffinity(@Nonnull TraitAffinity affinity) {
        return switch (affinity) {
            case INFERNAL -> METEOR_CASCADE;
            case VOID -> EVENT_HORIZON;
            case PRIMAL -> TECTONIC_RIFT;
            case TEMPERED -> SUPERNOVA;
            case RADIANT -> PRISMATIC_ASCENSION;
            case RESONANT -> SUPERNOVA;
            case VOLATILE -> METEOR_CASCADE;
            case TERRAIN -> TECTONIC_RIFT;
            case SWIFT -> PRISMATIC_ASCENSION;
            case BRUTAL -> TECTONIC_RIFT;
            case BULWARK -> EVENT_HORIZON;
            case ASCENDANT -> PRISMATIC_ASCENSION;
        };
    }

    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    @Nonnull
    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Nonnull
    @Override
    public Particle getTrailParticle() {
        return trailParticle;
    }

    @Nonnull
    @Override
    public Particle getAccentParticle() {
        return accentParticle;
    }

    @Nonnull
    @Override
    public Sound getChargeSound() {
        return chargeSound;
    }

    @Nonnull
    @Override
    public Sound getImpactSound() {
        return impactSound;
    }

    @Nonnull
    @Override
    public String getColorHex() {
        return colorHex;
    }

    @Nonnull
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public int getRootTicks() {
        return rootTicks;
    }

    @Override
    public int getFreezeTicks() {
        return freezeTicks;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }

    @Nonnull
    @Override
    public String getMiniMessageTag() {
        return "<" + colorHex.toLowerCase(Locale.ROOT) + ">" + displayName + "</" + colorHex.toLowerCase(Locale.ROOT) + ">";
    }

    @Override
    public String toString() {
        return displayName + " (" + animation + ")";
    }
}
