package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.items.PartComposition;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    private final ModularWeaponType weaponType;
    private final PerkFocus focus;

    private WeaponPerkProfile(@Nonnull ModularWeaponType weaponType, @Nonnull PerkFocus focus) {
        this.weaponType = weaponType;
        this.focus = focus;
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
        return new WeaponPerkProfile(type, PerkFocus.of(parts));
    }

    /** Essence focus of the weapon: identity essence, head blend and how concentrated it is. */
    @Nonnull
    public PerkFocus getFocus() {
        return focus;
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
            case BOW -> "arrows inherit limb and string elemental traits, and a focused bow looses a follow-up volley.";
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
        return focus.affinity().getDisplayName() + " " + baseName(weaponType);
    }

    /** Full perk description: the signature mechanic plus the essence blend its head mineral teaches. */
    @Nonnull
    public String getDescription() {
        String effect = focus.affinity().getWeaponEffect();
        if (!effect.isEmpty()) {
            effect = Character.toLowerCase(effect.charAt(0)) + effect.substring(1);
        }
        return baseDescription(weaponType)
                + " Imbued with " + getEssenceBlend() + " essence: " + effect;
    }

    /** Human readable blend of the head mineral's essences, e.g. {@code "Infernal, Tempered"}. */
    @Nonnull
    public String getEssenceBlend() {
        return focus.blend();
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
        return focus.affinity();
    }

    /** Every essence taught by the head mineral, in identity-priority order. */
    @Nonnull
    public List<TraitAffinity> getHeadEssences() {
        return focus.headEssences();
    }

    /** How much of the forged weapon carries the identity essence (0.0 - 1.0). */
    public double getPotency() {
        return focus.potency();
    }

    /** Identity essence share as a whole percentage for display. */
    public int getPotencyPercent() {
        return focus.potencyPercent();
    }

    @Nonnull
    public NamedTextColor getColor() {
        return focus.affinity().getColor();
    }

    @Nonnull
    public String getFocusLine() {
        return focus.focusLine();
    }

    @Override
    public String toString() {
        return getPerkLine();
    }
}
