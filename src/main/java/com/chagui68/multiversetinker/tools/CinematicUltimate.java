package com.chagui68.multiversetinker.tools;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;

import javax.annotation.Nonnull;

/**
 * Shared contract for every cinematic attack animation the plugin can render.
 *
 * <p>{@link UltimateEffectEngine} only knows about this interface, so new spectacles (essence
 * ultimates, prime alloy ultimates…) plug in without touching the animation code. Each entry
 * describes its own colour, particles, sounds and the physical punishment it lands: a damage
 * multiplier, an impact radius, how long the victim stays rooted and how many freeze ticks of
 * frost it takes.</p>
 */
public interface CinematicUltimate {

    /** Shape of the rendered spectacle. */
    enum Animation {
        /** A single column of meteors falls on the target. */
        METEOR,
        /** A spiral storm of meteors rains over the whole area. */
        METEOR_STORM,
        /** Space folds inward around the target. */
        VORTEX,
        /** A raw shockwave bursts outward. */
        NOVA,
        /** A pillar of light descends from the sky. */
        PILLAR,
        /** A cage of particles slams shut around the target. */
        CAGE,
        /** The ground cracks in concentric waves. */
        QUAKE,
        /** Ice spikes erupt and encase the area: the freezing spectacle. */
        CRYO
    }

    @Nonnull
    String getDisplayName();

    @Nonnull
    Animation getAnimation();

    @Nonnull
    Particle getTrailParticle();

    @Nonnull
    Particle getAccentParticle();

    @Nonnull
    Sound getChargeSound();

    @Nonnull
    Sound getImpactSound();

    @Nonnull
    String getColorHex();

    @Nonnull
    Color getColor();

    /** Multiplier applied to the triggering hit's damage on impact. */
    double getDamageMultiplier();

    /** Radius of the impact shockwave, in blocks. */
    double getRadius();

    /** How long the struck enemy is rooted, in ticks. */
    int getRootTicks();

    /** How much frost the victim takes, in freeze ticks ({@code 140} fully freezes a mob). */
    int getFreezeTicks();

    @Nonnull
    String getDescription();

    /** Adventure MiniMessage tag with the ultimate's own colour. */
    @Nonnull
    String getMiniMessageTag();
}
