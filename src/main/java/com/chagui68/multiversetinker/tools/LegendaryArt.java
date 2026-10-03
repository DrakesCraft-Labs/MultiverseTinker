package com.chagui68.multiversetinker.tools;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * The hand-made art of each of the 16 curated legendary alloys.
 *
 * <p>Composites share a procedural fusion grammar, but a legendary alloy is a recipe somebody had to
 * discover, so each one owns a named ability with its own mechanic and its own choreography: Electrum
 * chains lightning between foes, Void Damascus tears three armor-piercing rifts through the air, Cosmic
 * Netherite collapses everything nearby into a gravity well. {@link SignatureArtEngine} renders the
 * choreography (one dedicated method per entry) and applies the mechanic.</p>
 *
 * <p>A prime alloy keeps the art of the legendary it was fused from and plays it <i>ascended</i>, at a
 * bigger pedigree scale, followed by an overlay picked by its second ingredient. {@code
 * SignatureArtTest} asserts that no two entries share a name, an alloy or a pair of colours.</p>
 */
public enum LegendaryArt {

    BELL_OF_THE_FIRST_AGE("mvtink_bronze", "Bell of the First Age", "#CD7F32", "#FFE0B2",
            Particle.WAX_ON, Sound.BLOCK_BELL_USE,
            "A colossal bronze bell drops on the target and tolls three times: every toll staggers nearby "
                    + "foes, and the wielder stands behind a bronze Resistance II."),

    THUNDERCHAIN_CONDUIT("mvtink_electrum", "Thunderchain Conduit", "#FFF8A6", "#E6E8FA",
            Particle.ELECTRIC_SPARK, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,
            "Lightning leaps from foe to foe, chaining through up to 4 enemies (more at higher pedigree) "
                    + "and striking the first and the last link with a real thunderbolt."),

    THERMAL_BASTION("mvtink_invar", "Thermal Bastion", "#B0B8B0", "#FF7043",
            Particle.FLAME, Sound.BLOCK_FURNACE_FIRE_CRACKLE,
            "A rotating hexagonal wall of heat rises around the wielder, scorching every foe inside it "
                    + "while granting Absorption II and Fire Resistance."),

    INSATIABLE_FRENZY("mvtink_manyullyn", "Insatiable Frenzy", "#9B59B6", "#4A148C",
            Particle.SOUL_FIRE_FLAME, Sound.ENTITY_WARDEN_HEARTBEAT,
            "Soul-fire tendrils tear life out of every nearby foe and pour it into the wielder, who heals for "
                    + "each victim and is driven into a Strength frenzy."),

    MIDAS_BLOOM("mvtink_rose_gold", "Midas Bloom", "#B76E79", "#FFD700",
            Particle.CHERRY_LEAVES, Sound.BLOCK_AMETHYST_BLOCK_CHIME,
            "A rose of gilded petals blooms under the target: foes caught in the bloom glow and wither into "
                    + "Weakness II, and the wielder is showered with experience."),

    CONSTELLATION_FALL("mvtink_astral_brass", "Constellation Fall", "#F4D03F", "#AED6F1",
            Particle.END_ROD, Sound.BLOCK_BEACON_POWER_SELECT,
            "A constellation is drawn in the sky above the target, star by star, then every star falls as a "
                    + "meteor on a different foe."),

    ABYSSAL_REND("mvtink_void_damascus", "Abyssal Rend", "#2C3E50", "#8E44AD",
            Particle.REVERSE_PORTAL, Sound.ENTITY_ENDERMAN_SCREAM,
            "Three rifts are torn through the air in front of the target; every rift deals armor-piercing "
                    + "damage and the wound keeps dragging foes toward it."),

    HELLFORGE_ERUPTION("mvtink_cinder_steel", "Hellforge Eruption", "#E67E22", "#C0392B",
            Particle.LAVA, Sound.ITEM_FIRECHARGE_USE,
            "The ground cracks open in a ring of magma and erupts into geysers of fire that launch and "
                    + "ignite everything standing on them."),

    RESONANCE_CASCADE("mvtink_prismatic_quartz", "Resonance Cascade", "#E056FD", "#F5F5F5",
            Particle.NOTE, Sound.BLOCK_AMETHYST_BLOCK_RESONATE,
            "Crystal shards orbit the target and resonate in three escalating sonic pulses, each one "
                    + "shattering through every foe in range."),

    UMBRAL_EXECUTION("mvtink_shadow_platinum", "Umbral Execution", "#636E72", "#000000",
            Particle.LARGE_SMOKE, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE,
            "The wielder dissolves into smoke, reappears behind the target and carves a black crescent that "
                    + "hits harder the more wounded the victim already is."),

    PHASE_GATE("mvtink_ender_brass", "Phase Gate", "#1ABC9C", "#7D3C98",
            Particle.PORTAL, Sound.ENTITY_ENDERMAN_TELEPORT,
            "Two portals open, one beneath the target and one high above it: the target is phased into the "
                    + "sky and dropped, while the lower gate drags nearby foes into it."),

    UNBREAKABLE_AEGIS("mvtink_adamant_steel", "Unbreakable Aegis", "#2ECC71", "#ECF0F1",
            Particle.ENCHANTED_HIT, Sound.BLOCK_ANVIL_LAND,
            "A towering adamant sigil forms between the wielder and the target, hardening the wielder with "
                    + "Resistance and Absorption and slamming the target away."),

    CHAIN_COMBUSTION("mvtink_hellfire_bismuth", "Chain Combustion", "#FF7675", "#74B9FF",
            Particle.FLAME, Sound.ENTITY_GENERIC_EXPLODE,
            "Iridescent bismuth spirals out of the target and detonates in a chain of block-safe blasts that "
                    + "hop around it, each one hitting everything nearby."),

    SILVER_BLIZZARD("mvtink_glacial_silver", "Silver Blizzard", "#74B9FF", "#E6E8FA",
            Particle.SNOWFLAKE, Sound.ENTITY_PLAYER_HURT_FREEZE,
            "A howling silver blizzard spins around the target, freezing and slowing every foe inside it "
                    + "before shattering like glass."),

    CRIMSON_FEAST("mvtink_sanguine_gold", "Crimson Feast", "#D63031", "#FFD700",
            Particle.DAMAGE_INDICATOR, Sound.ENTITY_WITCH_DRINK,
            "Streams of blood arc out of every nearby foe into the wielder: each victim is drained, and the "
                    + "wielder heals for a third of everything the feast took."),

    GRAVITY_COLLAPSE("mvtink_cosmic_netherite", "Gravity Collapse", "#6C5CE7", "#0984E3",
            Particle.END_ROD, Sound.BLOCK_BEACON_DEACTIVATE,
            "Planetary rings spin up around the target and drag every foe within reach into the core, "
                    + "which then collapses in a crushing burst.");

    private final String alloyId;
    private final String displayName;
    private final String colorHex;
    private final String accentHex;
    private final Particle particle;
    private final Sound sound;
    private final String description;

    LegendaryArt(String alloyId, String displayName, String colorHex, String accentHex,
                 Particle particle, Sound sound, String description) {
        this.alloyId = alloyId;
        this.displayName = displayName;
        this.colorHex = colorHex;
        this.accentHex = accentHex;
        this.particle = particle;
        this.sound = sound;
        this.description = description;
    }

    /** The art of a legendary alloy id, or {@code null} when the id is not one of the 16 recipes. */
    @Nullable
    public static LegendaryArt byAlloyId(@Nullable String alloyId) {
        if (alloyId == null) return null;
        String key = alloyId.trim().toLowerCase(Locale.ROOT);
        for (LegendaryArt art : values()) {
            if (art.alloyId.equals(key)) return art;
        }
        return null;
    }

    @Nonnull
    public String getAlloyId() {
        return alloyId;
    }

    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    @Nonnull
    public String getColorHex() {
        return colorHex;
    }

    @Nonnull
    public String getAccentHex() {
        return accentHex;
    }

    @Nonnull
    public Color getColor() {
        return ForgeTier.parse(colorHex);
    }

    @Nonnull
    public Color getAccent() {
        return ForgeTier.parse(accentHex);
    }

    /** Signature particle of the choreography. */
    @Nonnull
    public Particle getParticle() {
        return particle;
    }

    /** Signature sound of the choreography. */
    @Nonnull
    public Sound getSound() {
        return sound;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }
}
