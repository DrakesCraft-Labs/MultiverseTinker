package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Material-driven weapon perk.
 *
 * <p>A modular weapon's signature mechanic (sweeping cleave, piercing velocity, hydraulic surge...)
 * is fixed by its {@link ModularWeaponType}, but the <b>essence</b> that names and powers that perk
 * is decided by the minerals it was forged from: the head part's mineral dictates the weapon's
 * elemental identity while the rod and binding dictate how concentrated that identity is.</p>
 *
 * <p>Everything here is pure and deterministic, so the same composition always yields the same perk
 * name, description and potency — keeping lore and combat behaviour in lock-step.</p>
 */
public final class WeaponPerkProfile {

    /** Relative forging weight of each weapon part (head, rod, binding). */
    private static final double[] PART_WEIGHTS = {3.0, 2.0, 1.0};

    /**
     * Fixed ranking used to pick the identity essence out of a mineral's affinity blend: elemental
     * and dimensional essences define a weapon, generic physical ones only back it up.
     */
    private static final List<TraitAffinity> IDENTITY_ORDER = List.of(
            TraitAffinity.INFERNAL,
            TraitAffinity.VOID,
            TraitAffinity.VOLATILE,
            TraitAffinity.RESONANT,
            TraitAffinity.RADIANT,
            TraitAffinity.TERRAIN,
            TraitAffinity.TEMPERED,
            TraitAffinity.PRIMAL,
            TraitAffinity.SWIFT,
            TraitAffinity.BRUTAL,
            TraitAffinity.BULWARK,
            TraitAffinity.ASCENDANT);

    private final ModularWeaponType weaponType;
    private final TraitAffinity affinity;
    private final List<TraitAffinity> headEssences;
    private final double potency;

    private WeaponPerkProfile(@Nonnull ModularWeaponType weaponType,
                              @Nonnull TraitAffinity affinity,
                              @Nonnull List<TraitAffinity> headEssences,
                              double potency) {
        this.weaponType = weaponType;
        this.affinity = affinity;
        this.headEssences = headEssences;
        this.potency = potency;
    }

    // ==========================================
    // FACTORIES
    // ==========================================
    /**
     * Builds the perk profile of a weapon from its forged parts, ordered head, rod, binding.
     * {@code null} parts and {@code null} entries are tolerated so two-part weapons can pass a
     * {@code null} binding while keeping the positional part weights intact.
     */
    @Nonnull
    public static WeaponPerkProfile of(@Nonnull ModularWeaponType type, @Nullable PartComposition... parts) {
        List<PartComposition> list = new ArrayList<>();
        if (parts != null) {
            for (PartComposition part : parts) {
                list.add(part);
            }
        }
        return of(type, list);
    }

    @Nonnull
    public static WeaponPerkProfile of(@Nonnull ModularWeaponType type, @Nonnull List<PartComposition> parts) {
        // 1. The head part owns the weapon's elemental identity.
        TinkerMaterial headMaterial = firstMaterial(parts);
        Set<TraitAffinity> headAffinities = (headMaterial != null)
                ? TraitAffinity.of(headMaterial)
                : Set.of(TraitAffinity.PRIMAL);
        List<TraitAffinity> headEssences = orderByPriority(headAffinities);

        TraitAffinity dominant = headEssences.isEmpty() ? TraitAffinity.PRIMAL : headEssences.get(0);

        // 2. Potency: how much of the forged weapon actually carries that essence.
        double total = 0.0;
        double carrying = 0.0;
        for (int i = 0; i < parts.size(); i++) {
            PartComposition part = parts.get(i);
            if (part == null) continue;
            double weight = (i < PART_WEIGHTS.length) ? PART_WEIGHTS[i] : 1.0;
            total += weight;
            for (PartComposition.Entry entry : part.getEntries()) {
                if (entry.material() == null) continue;
                if (TraitAffinity.of(entry.material()).contains(dominant)) {
                    carrying += weight * Math.max(0.0001, entry.ratio());
                    break;
                }
            }
        }
        double potency = (total > 0.0) ? Math.min(1.0, carrying / total) : 0.0;

        return new WeaponPerkProfile(type, dominant, headEssences, potency);
    }

    @Nullable
    private static TinkerMaterial firstMaterial(@Nonnull List<PartComposition> parts) {
        for (PartComposition part : parts) {
            if (part != null && part.getPrimaryMaterial() != null) {
                return part.getPrimaryMaterial();
            }
        }
        return null;
    }

    @Nonnull
    private static List<TraitAffinity> orderByPriority(@Nonnull Set<TraitAffinity> affinities) {
        LinkedHashSet<TraitAffinity> ordered = new LinkedHashSet<>();
        for (TraitAffinity candidate : IDENTITY_ORDER) {
            if (affinities.contains(candidate)) ordered.add(candidate);
        }
        for (TraitAffinity candidate : TraitAffinity.values()) {
            if (affinities.contains(candidate)) ordered.add(candidate);
        }
        return new ArrayList<>(ordered);
    }

    // ==========================================
    // PERK TEXT
    // ==========================================
    /** Signature perk name of the weapon type, independent of materials. */
    @Nonnull
    public static String baseName(@Nonnull ModularWeaponType type) {
        return switch (type) {
            case SWORD -> "Sweeping Cleave";
            case BOW -> "Infused Volley";
            case CROSSBOW -> "Piercing Velocity";
            case TRIDENT -> "Hydraulic Surge";
            case SPEAR -> "Jousting Reach";
            case MACE -> "Seismic Smash";
            case SHIELD -> "Retaliation Barrier";
        };
    }

    /** Signature perk mechanic of the weapon type, independent of materials. */
    @Nonnull
    public static String baseDescription(@Nonnull ModularWeaponType type) {
        return switch (type) {
            case SWORD -> "hits multiple adjacent foes and chains elemental traits.";
            case BOW -> "arrows inherit limb and string elemental traits.";
            case CROSSBOW -> "armor-penetrating bolts that trigger explosive impact.";
            case TRIDENT -> "releases lightning or geysers on strike in water or rain.";
            case SPEAR -> "extended attack range and +30% damage while sprinting.";
            case MACE -> "fall strikes produce crushing ground shockwaves.";
            case SHIELD -> "blocks reflect 35% damage and apply traits to attackers.";
        };
    }

    /** Material-flavoured perk name, e.g. {@code "Infernal Piercing Velocity"}. */
    @Nonnull
    public String getDisplayName() {
        return affinity.getDisplayName() + " " + baseName(weaponType);
    }

    /** Full perk description: the signature mechanic plus the essence blend its head mineral teaches. */
    @Nonnull
    public String getDescription() {
        String effect = affinity.getWeaponEffect();
        if (!effect.isEmpty()) {
            effect = Character.toLowerCase(effect.charAt(0)) + effect.substring(1);
        }
        return baseDescription(weaponType)
                + " Imbued with " + getEssenceBlend() + " essence: " + effect;
    }

    /** Human readable blend of the head mineral's essences, e.g. {@code "Infernal, Tempered"}. */
    @Nonnull
    public String getEssenceBlend() {
        StringBuilder builder = new StringBuilder();
        for (TraitAffinity essence : headEssences) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(essence.getDisplayName());
        }
        return builder.length() == 0 ? affinity.getDisplayName() : builder.toString();
    }

    /** One-line perk rendered in the weapon lore. */
    @Nonnull
    public String getPerkLine() {
        return getDisplayName() + ": " + getDescription();
    }

    // ==========================================
    // ACCESSORS
    // ==========================================
    @Nonnull
    public ModularWeaponType getWeaponType() {
        return weaponType;
    }

    /** Identity essence of the weapon, taken from its head mineral; drives the in-combat perk echo. */
    @Nonnull
    public TraitAffinity getAffinity() {
        return affinity;
    }

    /** Every essence taught by the head mineral, in identity-priority order. */
    @Nonnull
    public List<TraitAffinity> getHeadEssences() {
        return List.copyOf(headEssences);
    }

    /** How much of the forged weapon carries the identity essence (0.0 - 1.0). */
    public double getPotency() {
        return potency;
    }

    /** Identity essence share as a whole percentage for display. */
    public int getPotencyPercent() {
        return (int) Math.round(potency * 100.0);
    }

    @Nonnull
    public NamedTextColor getColor() {
        return affinity.getColor();
    }

    @Nonnull
    public String getFocusLine() {
        return String.format(Locale.US, "%s essence (%d%%)", affinity.getDisplayName(), getPotencyPercent());
    }

    @Override
    public String toString() {
        return getPerkLine();
    }
}
