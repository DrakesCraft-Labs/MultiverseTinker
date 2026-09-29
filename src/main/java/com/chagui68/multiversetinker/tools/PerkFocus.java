package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.TinkerMaterial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Essence focus shared by every forged piece of equipment: which essence leads it and how much of the
 * build actually carries that essence.
 *
 * <p>Weapons, tools and armor all answer the same two questions in the same way — the <b>head part</b>
 * dictates the identity essence while the remaining parts decide how concentrated that identity is —
 * so the maths lives here once instead of being repeated per profile. Everything is pure and
 * deterministic: the same composition always yields the same focus.</p>
 */
public final class PerkFocus {

    /** Relative forging weight of each equipment part (head, handle, pommel/trim). */
    private static final double[] PART_WEIGHTS = {3.0, 2.0, 1.0};

    /**
     * Fixed ranking used to pick the identity essence out of a mineral's affinity blend: elemental
     * and dimensional essences define a piece, generic physical ones only back it up.
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

    private final TraitAffinity affinity;
    private final List<TraitAffinity> headEssences;
    private final List<String> epithets;
    private final double potency;

    private PerkFocus(@Nonnull TraitAffinity affinity, @Nonnull List<TraitAffinity> headEssences,
                      @Nonnull List<String> epithets, double potency) {
        this.affinity = affinity;
        this.headEssences = headEssences;
        this.epithets = epithets;
        this.potency = potency;
    }

    /**
     * Focus of a composition whose parts are ordered head first. {@code null} parts are tolerated so
     * two-part equipment can pass a {@code null} third part while keeping the positional weights.
     */
    @Nonnull
    public static PerkFocus of(@Nonnull List<PartComposition> parts) {
        // 1. The head part owns the elemental identity.
        TinkerMaterial headMaterial = firstMaterial(parts);
        Set<TraitAffinity> headAffinities = (headMaterial != null)
                ? TraitAffinity.of(headMaterial)
                : Set.of(TraitAffinity.PRIMAL);
        List<TraitAffinity> headEssences = orderByPriority(headAffinities);
        TraitAffinity dominant = headEssences.isEmpty() ? TraitAffinity.PRIMAL : headEssences.get(0);

        // 2. Potency: how much of the forged piece actually carries that essence.
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

        // 3. Epithets: every part contributes the forged word of its own mineral, so the perk name
        // identifies the whole build and not just the identity essence of the head.
        List<String> epithets = new ArrayList<>();
        for (PartComposition part : parts) {
            if (part == null) continue;
            String epithet = PerkEpithet.of(part.getPrimaryMaterial());
            if (!epithet.isEmpty()) epithets.add(epithet);
        }

        return new PerkFocus(dominant, headEssences, epithets,
                (total > 0.0) ? Math.min(1.0, carrying / total) : 0.0);
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

    /** Identity essence of the piece, taken from its head part. */
    @Nonnull
    public TraitAffinity affinity() {
        return affinity;
    }

    /** Every essence taught by the head part, in identity-priority order. */
    @Nonnull
    public List<TraitAffinity> headEssences() {
        return List.copyOf(headEssences);
    }

    /** How much of the forged piece carries the identity essence (0.0 - 1.0). */
    public double potency() {
        return potency;
    }

    /** Identity essence share as a whole percentage for display. */
    public int potencyPercent() {
        return (int) Math.round(potency * 100.0);
    }

    /** Human readable blend of the head part's essences, e.g. {@code "Infernal, Tempered"}. */
    @Nonnull
    public String blend() {
        StringBuilder builder = new StringBuilder();
        for (TraitAffinity essence : headEssences) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(essence.getDisplayName());
        }
        return builder.length() == 0 ? affinity.getDisplayName() : builder.toString();
    }

    /** One-line summary of the focus, e.g. {@code "Infernal essence (100%)"}. */
    @Nonnull
    public String focusLine() {
        return String.format(Locale.US, "%s essence (%d%%)", affinity.getDisplayName(), potencyPercent());
    }

    /** The forged epithet each non-empty part contributes, in forge order. */
    @Nonnull
    public List<String> epithets() {
        return List.copyOf(epithets);
    }

    /**
     * Name of the piece's perk: the epithet of every part's mineral, in forge order, ahead of the
     * signature mechanic.
     *
     * <p>This is what stops two items of the same type from sharing a perk when they are forged from
     * different minerals: {@code Fluxforged Resonant Auric Sweeping Cleave} and {@code Fluxforged
     * Resonant Adamant Sweeping Cleave} are the same sword type with a different trim, and they read
     * differently.</p>
     */
    @Nonnull
    public String perkName(@Nonnull String baseName) {
        if (epithets.isEmpty()) return baseName;
        return String.join(" ", epithets) + " " + baseName;
    }
}
