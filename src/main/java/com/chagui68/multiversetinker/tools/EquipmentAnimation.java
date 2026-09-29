package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import org.bukkit.Particle;
import org.bukkit.Sound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * One exclusive animation per piece of modular equipment.
 *
 * <p>Every weapon, tool and armor type owns its own choreography: a distinct particle pattern, a
 * distinct pairing of particles and its own signature sound. Nothing is shared between two types,
 * so no equipment family can look like it received more (or less) visual work than another — that
 * is enforced by {@code EquipmentAnimationTest}, which fails if two types ever collide.</p>
 *
 * <p>The enum only holds the data; {@link PerkAnimationEngine} renders the pattern. Configuration
 * lives here as static state (like {@code LoreWrap}) so the engine can be called from static item
 * factories and event handlers without a plugin instance.</p>
 */
public enum EquipmentAnimation {

    // ==========================================
    // WEAPONS (7)
    // ==========================================
    SWORD(Family.WEAPON, "SWORD", "Sweeping Arc", Pattern.SWEEP_ARC,
            Particle.SWEEP_ATTACK, Particle.ENCHANTED_HIT, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.1f,
            "A horizontal elemental arc carves the air around every swept foe."),

    BOW(Family.WEAPON, "BOW", "Volley Trail", Pattern.VOLLEY_TRAIL,
            Particle.CRIT, Particle.END_ROD, Sound.ENTITY_ARROW_SHOOT, 0.9f, 1.5f,
            "A dotted trail links the longbow to the arrow that just landed."),

    CROSSBOW(Family.WEAPON, "CROSSBOW", "Piercing Lance", Pattern.PIERCE_LANCE,
            Particle.ELECTRIC_SPARK, Particle.CRIT, Sound.ITEM_CROSSBOW_SHOOT, 1.1f, 0.8f,
            "A taut line of sparks spears straight through the impact point."),

    TRIDENT(Family.WEAPON, "TRIDENT", "Hydraulic Surge", Pattern.SURGE_COLUMN,
            Particle.SPLASH, Particle.ELECTRIC_SPARK, Sound.ITEM_TRIDENT_RIPTIDE_1, 1.1f, 1.2f,
            "A rising surge of water wraps the wielder in a hydraulic column."),

    SPEAR(Family.WEAPON, "SPEAR", "Jousting Thrust", Pattern.THRUST_LINE,
            Particle.CLOUD, Particle.CRIT, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f,
            "A low air-pressure lane marks the reach of the thrust."),

    MACE(Family.WEAPON, "MACE", "Seismic Smash", Pattern.SHOCK_RING,
            Particle.EXPLOSION, Particle.CLOUD, Sound.ITEM_MACE_SMASH_GROUND_HEAVY, 1.0f, 0.8f,
            "An expanding shock ring breaks outward from the point of impact."),

    SHIELD(Family.WEAPON, "SHIELD", "Retaliation Bulwark", Pattern.PARAPET_ARC,
            Particle.ENCHANTED_HIT, Particle.END_ROD, Sound.ITEM_SHIELD_BLOCK, 1.2f, 1.0f,
            "A curved rampart of ward-light rises in front of the blocker."),

    // ==========================================
    // TOOLS (5)
    // ==========================================
    PICKAXE(Family.TOOL, "PICKAXE", "Vein Resonance", Pattern.VEIN_STRIKE,
            Particle.ENCHANTED_HIT, Particle.ELECTRIC_SPARK, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.4f,
            "A resonant vein of light runs down the face of the mined ore."),

    AXE(Family.TOOL, "AXE", "Lumber Cleave", Pattern.LUMBER_SPLASH,
            Particle.CRIT, Particle.CHERRY_LEAVES, Sound.BLOCK_WOOD_BREAK, 1.0f, 0.9f,
            "Splinters and leaves burst sideways from the felled trunk."),

    SHOVEL(Family.TOOL, "SHOVEL", "Seismic Tremor", Pattern.GROUND_WAVE,
            Particle.CLOUD, Particle.CAMPFIRE_COSY_SMOKE, Sound.BLOCK_GRAVEL_BREAK, 1.0f, 0.8f,
            "A dust ring rides across the loosened soil around the dig."),

    HOE(Family.TOOL, "HOE", "Harvest Swirl", Pattern.HARVEST_SWIRL,
            Particle.HAPPY_VILLAGER, Particle.NOTE, Sound.ITEM_CROP_PLANT, 1.0f, 1.2f,
            "A swirl of harvest sparks spirals up over the reaped crops."),

    FISHING_ROD(Family.TOOL, "FISHING_ROD", "Abyssal Dredge", Pattern.DREDGE_DRIP,
            Particle.BUBBLE, Particle.SPLASH, Sound.ENTITY_FISHING_BOBBER_SPLASH, 1.0f, 1.3f,
            "Water and bubbles drip down the line as the dredge pulls something up."),

    // ==========================================
    // ARMOR (4)
    // ==========================================
    HELMET(Family.ARMOR, "HELMET", "Cranium Halo", Pattern.HALO_RING,
            Particle.END_ROD, Particle.ENCHANTED_HIT, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1.0f, 1.5f,
            "A halo of ward-light closes around the wearer's head."),

    CHESTPLATE(Family.ARMOR, "CHESTPLATE", "Kinetic Dome", Pattern.DOME_BURST,
            Particle.ENCHANTED_HIT, Particle.CLOUD, Sound.BLOCK_ANVIL_LAND, 1.0f, 1.4f,
            "A dome of dampening force swells out of the plate and swallows the blow."),

    LEGGINGS(Family.ARMOR, "LEGGINGS", "Stride Coil", Pattern.STRIDE_SPIRAL,
            Particle.CLOUD, Particle.ELECTRIC_SPARK, Sound.ENTITY_PHANTOM_FLAP, 1.0f, 1.2f,
            "A coil of momentum spins up around the legs before the next stride."),

    BOOTS(Family.ARMOR, "BOOTS", "Grounding Puff", Pattern.TRACTION_PUFF,
            Particle.SNOWFLAKE, Particle.CLOUD, Sound.BLOCK_POWDER_SNOW_BREAK, 1.0f, 0.9f,
            "A cushion of air and frost puffs out under the soles on landing.");

    /** Which equipment family an animation belongs to. */
    public enum Family { WEAPON, TOOL, ARMOR }

    /** The rendered geometry of an animation; every type uses exactly one shape. */
    public enum Pattern {
        SWEEP_ARC,
        VOLLEY_TRAIL,
        PIERCE_LANCE,
        SURGE_COLUMN,
        THRUST_LINE,
        SHOCK_RING,
        PARAPET_ARC,
        VEIN_STRIKE,
        LUMBER_SPLASH,
        GROUND_WAVE,
        HARVEST_SWIRL,
        DREDGE_DRIP,
        HALO_RING,
        DOME_BURST,
        STRIDE_SPIRAL,
        TRACTION_PUFF
    }

    /** Config keys read from {@code config.yml}. */
    public static final String CONFIG_ENABLED = "animations.enabled";
    public static final String CONFIG_PARTICLE_SCALE = "animations.particle-scale";
    public static final String CONFIG_SOUNDS = "animations.sounds";
    public static final String CONFIG_COOLDOWN_MILLIS = "animations.cooldown-millis";

    /** Shipped defaults. */
    public static final double DEFAULT_PARTICLE_SCALE = 1.0;
    public static final int DEFAULT_COOLDOWN_MILLIS = 400;
    public static final double MIN_PARTICLE_SCALE = 0.25;
    public static final double MAX_PARTICLE_SCALE = 3.0;
    public static final int MIN_COOLDOWN_MILLIS = 0;
    public static final int MAX_COOLDOWN_MILLIS = 5000;

    private static volatile boolean enabled = true;
    private static volatile double particleScale = DEFAULT_PARTICLE_SCALE;
    private static volatile boolean soundsEnabled = true;
    private static volatile int cooldownMillis = DEFAULT_COOLDOWN_MILLIS;

    private static final Map<String, EquipmentAnimation> BY_TYPE = new HashMap<>();
    private static final Map<Family, Integer> FAMILY_COUNTS = new EnumMap<>(Family.class);

    static {
        for (EquipmentAnimation animation : values()) {
            EquipmentAnimation previous = BY_TYPE.put(animation.typeName, animation);
            if (previous != null) {
                throw new IllegalStateException("Two animations claim equipment type " + animation.typeName);
            }
            FAMILY_COUNTS.merge(animation.family, 1, Integer::sum);
        }
    }

    private final Family family;
    private final String typeName;
    private final String displayName;
    private final Pattern pattern;
    private final Particle particle;
    private final Particle accentParticle;
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final String description;

    EquipmentAnimation(Family family, String typeName, String displayName, Pattern pattern,
                       Particle particle, Particle accentParticle, Sound sound,
                       float volume, float pitch, String description) {
        this.family = family;
        this.typeName = typeName;
        this.displayName = displayName;
        this.pattern = pattern;
        this.particle = particle;
        this.accentParticle = accentParticle;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.description = description;
    }

    // ==========================================
    // LOOKUPS
    // ==========================================

    /** @return the exclusive animation of a weapon type, or {@code null} if it has none. */
    @Nullable
    public static EquipmentAnimation forWeapon(@Nullable ModularWeaponType type) {
        return type == null ? null : BY_TYPE.get(type.name());
    }

    /** @return the exclusive animation of a tool type, or {@code null} if it has none. */
    @Nullable
    public static EquipmentAnimation forTool(@Nullable ModularToolType type) {
        return type == null ? null : BY_TYPE.get(type.name());
    }

    /** @return the exclusive animation of an armor type, or {@code null} if it has none. */
    @Nullable
    public static EquipmentAnimation forArmor(@Nullable ModularArmorType type) {
        return type == null ? null : BY_TYPE.get(type.name());
    }

    /**
     * Resolves an animation from the raw equipment-type string stored in an item's persistent data.
     *
     * @param family the family the item belongs to
     * @param rawType the value of {@code WEAPON_TYPE}, {@code TOOL_TYPE} or {@code ARMOR_TYPE}
     */
    @Nullable
    public static EquipmentAnimation forType(@Nonnull Family family, @Nullable String rawType) {
        if (rawType == null) return null;
        EquipmentAnimation animation = BY_TYPE.get(rawType.toUpperCase(Locale.ROOT));
        return (animation != null && animation.family == family) ? animation : null;
    }

    /** How many animations a family owns; used to prove every type is covered. */
    public static int count(Family family) {
        return FAMILY_COUNTS.getOrDefault(family, 0);
    }

    // ==========================================
    // CONFIGURATION
    // ==========================================

    /**
     * Applies the {@code animations} section of the plugin configuration.
     *
     * @param animationsEnabled whether the choreographies play at all
     * @param scale            particle-count multiplier, clamped to a sane range
     * @param sound            whether the signature sound accompanies the particles
     * @param cooldown         minimum delay in milliseconds between two animations of the same type
     *                         on the same player, so fast procs cannot turn into a strobe
     */
    public static void configure(boolean animationsEnabled, double scale, boolean sound, int cooldown) {
        enabled = animationsEnabled;
        particleScale = Math.min(MAX_PARTICLE_SCALE, Math.max(MIN_PARTICLE_SCALE, scale));
        soundsEnabled = sound;
        cooldownMillis = Math.min(MAX_COOLDOWN_MILLIS, Math.max(MIN_COOLDOWN_MILLIS, cooldown));
    }

    /** Restores the shipped defaults; called by the plugin's own configuration loader. */
    public static void reset() {
        configure(true, DEFAULT_PARTICLE_SCALE, true, DEFAULT_COOLDOWN_MILLIS);
    }

    /** Whether the perk animations are played at all. */
    public static boolean isEnabled() {
        return enabled;
    }

    /** Whether the signature sounds accompany the particles. */
    public static boolean isSoundsEnabled() {
        return soundsEnabled;
    }

    /** Particle-count multiplier currently applied. */
    public static double configuredParticleScale() {
        return particleScale;
    }

    /** Minimum delay between two animations of the same type, in milliseconds. */
    public static int configuredCooldownMillis() {
        return cooldownMillis;
    }

    /** Particle count of a pattern, already scaled by the configured multiplier. */
    public int scaleCount(int baseCount) {
        return Math.max(1, (int) Math.round(baseCount * particleScale));
    }

    // ==========================================
    // DATA ACCESS
    // ==========================================

    public Family getFamily() {
        return family;
    }

    /** The {@code ModularWeaponType}/{@code ModularToolType}/{@code ModularArmorType} name it serves. */
    @Nonnull
    public String getTypeName() {
        return typeName;
    }

    /** Human-readable name of the choreography, e.g. {@code Sweeping Arc}. */
    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    @Nonnull
    public Pattern getPattern() {
        return pattern;
    }

    @Nonnull
    public Particle getParticle() {
        return particle;
    }

    @Nonnull
    public Particle getAccentParticle() {
        return accentParticle;
    }

    @Nonnull
    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    /** One-line description of the choreography, reused by the documentation and the codex. */
    @Nonnull
    public String getDescription() {
        return description;
    }

    /** Signature of the animation: pattern + particles + sound. Two types may never share one. */
    @Nonnull
    public String signature() {
        return pattern + "|" + particle + "|" + accentParticle + "|" + sound;
    }
}
