package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Deterministic "essence" classification for every geological mineral, vanilla ore and alloy.
 *
 * <p>Each material resolves to at most {@link #MAX_AFFINITIES} affinities derived from its
 * immutable properties (type, dimension of origin, rarity and forged statistics). Because the
 * mapping is pure, the same material always teaches the same essences, and every binary alloy
 * (the union of its two parents) therefore owns a unique, reproducible combination of effects
 * for weapons, tools and armor alike.</p>
 */
public enum TraitAffinity {

    // Origin-flavoured essences
    INFERNAL("Infernal", NamedTextColor.GOLD,
            "Searing hits that set foes ablaze.",
            "Chance to auto-smelt excavated ores.",
            "Grants Fire Resistance when struck."),
    VOID("Void", NamedTextColor.DARK_PURPLE,
            "Warping strikes that drag foes inward.",
            "Chance to rip bonus experience from stone.",
            "Grants Slow Falling when struck."),
    PRIMAL("Primal", NamedTextColor.GREEN,
            "Raw additional impact damage.",
            "Chance for bonus natural drops.",
            "Grants a small Absorption shield when struck."),

    // Material-type essences
    TEMPERED("Tempered", NamedTextColor.GRAY,
            "Hardened edge that boosts raw damage.",
            "Chance to self-repair the tool while mining.",
            "Grants Resistance when struck."),
    RADIANT("Radiant", NamedTextColor.YELLOW,
            "Radiant blows that mark struck foes.",
            "Grants Night Vision while excavating.",
            "Mends the wielder's wounds when struck."),
    RESONANT("Resonant", NamedTextColor.AQUA,
            "Harmonic shockwaves damage nearby foes.",
            "Chimes reveal the surrounding ore seams.",
            "Releases a concussive pulse that shoves attackers."),
    VOLATILE("Volatile", NamedTextColor.RED,
            "Unstable strikes that erupt in flame.",
            "Chance to ignite a volatile spark for experience.",
            "Sets melee attackers alight."),
    TERRAIN("Terrain", NamedTextColor.GOLD,
            "Earthen blows that slow struck foes.",
            "Chance for bonus excavated blocks.",
            "Stuns attackers with heavy Slow."),

    // Forged-statistic essences
    SWIFT("Swift", NamedTextColor.AQUA,
            "Lightning cadence that hastens the wielder.",
            "Grants Haste while mining.",
            "Grants Speed when struck."),
    BRUTAL("Brutal", NamedTextColor.DARK_RED,
            "Crushing force that hurls foes backward.",
            "Chance to shatter out extra ore.",
            "Reflects part of the damage back at attackers."),
    BULWARK("Bulwark", NamedTextColor.BLUE,
            "Immovable mass that hardens the wielder.",
            "Chance to absorb durability wear entirely.",
            "Grants extra Resistance when struck."),
    ASCENDANT("Ascendant", NamedTextColor.LIGHT_PURPLE,
            "Transcendent strikes that sustain the wielder.",
            "Chance for bonus experience while mining.",
            "Grants Regeneration when struck.");

    /** Hard cap so multi-material parts stay readable and balanced. */
    public static final int MAX_AFFINITIES = 3;

    private final String displayName;
    private final NamedTextColor color;
    private final String weaponEffect;
    private final String toolEffect;
    private final String armorEffect;

    TraitAffinity(String displayName, NamedTextColor color,
                  String weaponEffect, String toolEffect, String armorEffect) {
        this.displayName = displayName;
        this.color = color;
        this.weaponEffect = weaponEffect;
        this.toolEffect = toolEffect;
        this.armorEffect = armorEffect;
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
    public String getWeaponEffect() {
        return weaponEffect;
    }

    @Nonnull
    public String getToolEffect() {
        return toolEffect;
    }

    @Nonnull
    public String getArmorEffect() {
        return armorEffect;
    }

    /**
     * Resolves the deterministic affinity set of a single material.
     */
    @Nonnull
    public static Set<TraitAffinity> of(@Nonnull TinkerMaterial material) {
        LinkedHashSet<TraitAffinity> ordered = new LinkedHashSet<>();

        // 1. Dimensional essence always takes priority.
        switch (material.getOrigin()) {
            case NETHER -> ordered.add(INFERNAL);
            case THE_END -> ordered.add(VOID);
            case VANILLA -> ordered.add(PRIMAL);
            case OVERWORLD -> ordered.add(TERRAIN);
        }

        // 2. Material class essence.
        switch (material.getType()) {
            case METAL, ALLOY -> ordered.add(TEMPERED);
            case GEM -> ordered.add(RADIANT);
            case CRYSTAL -> ordered.add(RESONANT);
            case ELEMENTAL -> ordered.add(VOLATILE);
            case MINERAL -> ordered.add(TERRAIN);
        }

        // 3. Forged statistics essence.
        if (material.getMiningSpeed() >= 9.0f) {
            ordered.add(SWIFT);
        }
        if (material.getAttackDamage() >= 3.5) {
            ordered.add(BRUTAL);
        }
        if (material.getDurability() >= 900) {
            ordered.add(BULWARK);
        }
        if (material.getRarity() == MaterialRarity.EPIC || material.getRarity() == MaterialRarity.LEGENDARY) {
            ordered.add(ASCENDANT);
        }

        Set<TraitAffinity> result = EnumSet.noneOf(TraitAffinity.class);
        int taken = 0;
        for (TraitAffinity affinity : ordered) {
            if (taken++ >= MAX_AFFINITIES) break;
            result.add(affinity);
        }
        if (result.isEmpty()) {
            result.add(PRIMAL);
        }
        return result;
    }
}
