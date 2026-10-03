package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Locale;

/**
 * Forge pedigree: how hard the metallurgy inside a piece of equipment was to reach.
 *
 * <p>A pickaxe of raw tin and a broadsword whose blade is a Nether-Star prime of Manyullyn should not
 * feel the same in the hand. The pedigree ranks the most demanding material in a build, and everything
 * cinematic reads it: the {@link SignatureArt} a weapon unlocks, how often it fires, how far it reaches,
 * how hard it hits and how much of the sky its animation is allowed to take over.</p>
 *
 * <ol>
 *     <li>{@link #MINERAL}: brushed minerals and vanilla ores — no signature art.</li>
 *     <li>{@link #COMPOSITE}: a crucible blend of two minerals — a procedural fusion art.</li>
 *     <li>{@link #LEGENDARY}: one of the 16 curated recipes — its own hand-made legendary art.</li>
 *     <li>{@link #PRIME}: a legendary fused again with a mineral or composite — the ascended art.</li>
 *     <li>{@link #MYTHIC}: a legendary fused with a vanilla catalyst or a second legendary — the full
 *     cinematic with its own finale.</li>
 * </ol>
 */
public enum ForgeTier {

    MINERAL(1, "I", "Forged", "#BDC3C7", "#7F8C8D", 1.00, 0.00, 0, 0.00, 0.0),
    COMPOSITE(2, "II", "Alloyed", "#55EFC4", "#00B894", 1.30, 0.15, 160, 0.55, 3.0),
    LEGENDARY(3, "III", "Legendary", "#FFD700", "#FF8C00", 1.70, 0.20, 200, 0.80, 4.0),
    PRIME(4, "IV", "Prime", "#E056FD", "#6C5CE7", 2.20, 0.24, 240, 1.05, 5.0),
    MYTHIC(5, "V", "Mythic", "#FF4757", "#FFD700", 2.80, 0.28, 280, 1.30, 6.0);

    private final int level;
    private final String numeral;
    private final String displayName;
    private final String colorHex;
    private final String accentHex;
    private final double visualScale;
    private final double artChance;
    private final int artCooldownTicks;
    private final double artDamageMultiplier;
    private final double artRadius;

    ForgeTier(int level, String numeral, String displayName, String colorHex, String accentHex,
              double visualScale, double artChance, int artCooldownTicks,
              double artDamageMultiplier, double artRadius) {
        this.level = level;
        this.numeral = numeral;
        this.displayName = displayName;
        this.colorHex = colorHex;
        this.accentHex = accentHex;
        this.visualScale = visualScale;
        this.artChance = artChance;
        this.artCooldownTicks = artCooldownTicks;
        this.artDamageMultiplier = artDamageMultiplier;
        this.artRadius = artRadius;
    }

    // ==========================================
    // RESOLUTION
    // ==========================================

    /** Pedigree of a single material. */
    @Nonnull
    public static ForgeTier of(@Nullable TinkerMaterial material) {
        if (material == null) return MINERAL;
        if (AlloyRegistry.isPrime(material)) {
            return isMythicParents(material.getAlloyParents()) ? MYTHIC : PRIME;
        }
        if (AlloyRegistry.isLegendary(material)) return LEGENDARY;
        if (material.isAlloy()) return COMPOSITE;
        return MINERAL;
    }

    /** Pedigree of a whole build: its most demanding material wins. */
    @Nonnull
    public static ForgeTier of(@Nullable Collection<PartComposition> parts) {
        ForgeTier best = MINERAL;
        if (parts == null) return best;
        for (PartComposition part : parts) {
            if (part == null) continue;
            for (PartComposition.Entry entry : part.getEntries()) {
                ForgeTier tier = of(entry.material());
                if (tier.level > best.level) best = tier;
            }
        }
        return best;
    }

    /**
     * A prime is mythic when its second ingredient was as hard to get as the legendary itself: a vanilla
     * catalyst (Nether Star, Echo Shard…) or a second legendary alloy.
     */
    public static boolean isMythicParents(@Nullable String parents) {
        if (parents == null || !parents.contains(",")) return false;
        int legendary = 0;
        for (String raw : parents.split(",")) {
            String id = raw.trim().toLowerCase(Locale.ROOT);
            if (id.startsWith(VanillaCatalyst.ID_PREFIX)) return true;
            if (AlloyRegistry.LEGENDARY_IDS.contains(id)) legendary++;
        }
        return legendary >= 2;
    }

    // ==========================================
    // PRESENTATION
    // ==========================================

    /** Gradient badge used in lore and chat, e.g. {@code ✦ Tier III · Legendary}. */
    @Nonnull
    public String badge() {
        return "<gradient:" + colorHex + ":" + accentHex + "><b>✦ Tier " + numeral + " · " + displayName + "</b></gradient>";
    }

    /** Wraps a text in this tier's gradient. */
    @Nonnull
    public String gradient(@Nonnull String text) {
        return "<gradient:" + colorHex + ":" + accentHex + ">" + text + "</gradient>";
    }

    @Nonnull
    public Color color() {
        return parse(colorHex);
    }

    @Nonnull
    public Color accent() {
        return parse(accentHex);
    }

    @Nonnull
    static Color parse(@Nonnull String hex) {
        try {
            return Color.fromRGB(Integer.parseInt(hex.replace("#", ""), 16));
        } catch (NumberFormatException e) {
            return Color.WHITE;
        }
    }

    /** Whether this pedigree reaches at least the other one. */
    public boolean atLeast(@Nonnull ForgeTier other) {
        return level >= other.level;
    }

    // ==========================================
    // ACCESSORS
    // ==========================================

    public int getLevel() {
        return level;
    }

    @Nonnull
    public String getNumeral() {
        return numeral;
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

    /** Particle and geometry multiplier of every cinematic this pedigree plays. */
    public double getVisualScale() {
        return visualScale;
    }

    /** Chance per landed hit that the signature art fires (before the configured multiplier). */
    public double getArtChance() {
        return artChance;
    }

    /** Ticks a wielder waits between two signature arts. */
    public int getArtCooldownTicks() {
        return artCooldownTicks;
    }

    /** Share of the triggering hit the signature art deals to its primary target. */
    public double getArtDamageMultiplier() {
        return artDamageMultiplier;
    }

    /** Reach of the signature art's area effects, in blocks. */
    public double getArtRadius() {
        return artRadius;
    }
}
