package com.chagui68.multiversetinker.tools;

import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Vanilla Minecraft items that act as <b>catalysts</b> in the Alloy Crucible.
 *
 * <p>A catalyst cannot be blended with minerals on its own: it must be paired with one of the 16
 * legendary alloys. The result is a <b>prime alloy</b> that carries the catalyst's sigil, and that
 * sigil decides which cinematic ultimate the forged weapon unleashes and which defensive state the
 * forged armor answers with - so the physical vanilla item you drop in the crucible is what shapes
 * the endgame build.</p>
 */
public enum VanillaCatalyst {

    NETHER_STAR("Nether Star", Material.NETHER_STAR, PrimeUltimate.SUPERNOVA, PrimeArmorState.PRIME_AEGIS,
            "#FFF176", "Stellar core that detonates every strike into a supernova."),

    DRAGON_BREATH("Dragon Breath", Material.DRAGON_BREATH, PrimeUltimate.METEOR_CASCADE, PrimeArmorState.EMBER_VEIL,
            "#B14EFF", "Molten breath that turns the sky into a burning meteor storm."),

    BLUE_ICE("Blue Ice", Material.BLUE_ICE, PrimeUltimate.ABSOLUTE_ZERO, PrimeArmorState.FROSTBOUND,
            "#AEE8FF", "Eternal frost that erupts in ice spikes and freezes the target solid."),

    PACKED_ICE("Packed Ice", Material.PACKED_ICE, PrimeUltimate.GLACIER_TOMB, PrimeArmorState.FROSTBOUND,
            "#7FD8FF", "Compressed glacier that slams a frost tomb shut around the enemy."),

    ECHO_SHARD("Echo Shard", Material.ECHO_SHARD, PrimeUltimate.EVENT_HORIZON, PrimeArmorState.VOID_SHELL,
            "#2C3E50", "Deep-dark resonance that folds space into a crushing singularity."),

    HEART_OF_THE_SEA("Heart of the Sea", Material.HEART_OF_THE_SEA, PrimeUltimate.TECTONIC_RIFT,
            PrimeArmorState.GRAVITIC_ANCHOR, "#1ABC9C", "Tidal pressure that splits the crust and pulls foes inward."),

    TOTEM_OF_UNDYING("Totem of Undying", Material.TOTEM_OF_UNDYING, PrimeUltimate.PRISMATIC_ASCENSION,
            PrimeArmorState.PRIME_AEGIS, "#D2B4DE", "Undying light that descends as a prismatic pillar."),

    END_CRYSTAL("End Crystal", Material.END_CRYSTAL, PrimeUltimate.SUPERNOVA, PrimeArmorState.STORMCALL,
            "#E056FD", "Crystalline charge that detonates and calls lightning on attackers."),

    RESPAWN_ANCHOR("Respawn Anchor", Material.RESPAWN_ANCHOR, PrimeUltimate.METEOR_CASCADE, PrimeArmorState.EMBER_VEIL,
            "#FF8A65", "Charged nether core that rains fire over the battlefield."),

    PRISMARINE_CRYSTALS("Prismarine Crystals", Material.PRISMARINE_CRYSTALS, PrimeUltimate.PRISMATIC_ASCENSION,
            PrimeArmorState.PRISM_BULWARK, "#7FFFD4", "Ocean optics that refract every blow into prismatic light."),

    AMETHYST_CLUSTER("Amethyst Cluster", Material.AMETHYST_CLUSTER, PrimeUltimate.EVENT_HORIZON,
            PrimeArmorState.PRISM_BULWARK, "#C39BD3", "Harmonic geode that resonates space into a void well."),

    ANCIENT_DEBRIS("Ancient Debris", Material.ANCIENT_DEBRIS, PrimeUltimate.TECTONIC_RIFT,
            PrimeArmorState.TECTONIC_GUARD, "#8B4513", "Bedrock-tough debris that topples everything nearby.");

    /** Prefix shared by every catalyst material id. */
    public static final String ID_PREFIX = "mvtink_catalyst_";

    private final String displayName;
    private final Material item;
    private final PrimeUltimate ultimate;
    private final PrimeArmorState armorState;
    private final String colorHex;
    private final String description;

    VanillaCatalyst(String displayName, Material item, PrimeUltimate ultimate, PrimeArmorState armorState,
                    String colorHex, String description) {
        this.displayName = displayName;
        this.item = item;
        this.ultimate = ultimate;
        this.armorState = armorState;
        this.colorHex = colorHex;
        this.description = description;
    }

    @Nonnull
    public String getDisplayName() {
        return displayName;
    }

    /** The vanilla item a player actually drops into the crucible slot. */
    @Nonnull
    public Material getItem() {
        return item;
    }

    @Nonnull
    public PrimeUltimate getUltimate() {
        return ultimate;
    }

    @Nonnull
    public PrimeArmorState getArmorState() {
        return armorState;
    }

    @Nonnull
    public String getColorHex() {
        return colorHex;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    /** Registered material id, e.g. {@code mvtink_catalyst_nether_star}. */
    @Nonnull
    public String getMaterialId() {
        return ID_PREFIX + name().toLowerCase(Locale.ROOT);
    }

    /** Lore line shown on the ingot and on the prime alloys it produced. */
    @Nonnull
    public String getLoreLine() {
        return "❖ Catalyst: " + displayName + " — " + description;
    }

    /** How many prime alloys this catalyst can produce: one per legendary alloy. */
    public int compatiblePrimes() {
        return com.chagui68.multiversetinker.alloys.AlloyRegistry.LEGENDARY_IDS.size();
    }

    @Nullable
    public static VanillaCatalyst byMaterialId(@Nullable String materialId) {
        if (materialId == null || !materialId.startsWith(ID_PREFIX)) return null;
        String key = materialId.substring(ID_PREFIX.length()).toUpperCase(Locale.ROOT);
        for (VanillaCatalyst catalyst : values()) {
            if (catalyst.name().equals(key)) return catalyst;
        }
        return null;
    }

    @Nullable
    public static VanillaCatalyst byItem(@Nullable Material material) {
        if (material == null) return null;
        for (VanillaCatalyst catalyst : values()) {
            if (catalyst.item == material) return catalyst;
        }
        return null;
    }
}
