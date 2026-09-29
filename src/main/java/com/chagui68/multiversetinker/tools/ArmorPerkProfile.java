package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.items.PartComposition;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Material-driven armor perk.
 *
 * <p>A modular armor piece protects through the mechanic of its slot (cranium ward, kinetic
 * dampener, stride momentum, feathered grounding), while the part that <b>names</b> that protection is
 * the minerals it was forged from: every part contributes its own {@link PerkEpithet} ahead of the
 * mechanic, and the plate additionally dictates the defensive identity and its concentration.</p>
 *
 * <p>So a Void chestplate answers hits with warping retaliation while an Infernal one answers with
 * fire resistance, and no two pieces of armor share one fixed perk line.</p>
 */
public final class ArmorPerkProfile {

    private final ModularArmorType armorType;
    private final double mitigation;
    private final PerkFocus focus;

    private ArmorPerkProfile(@Nonnull ModularArmorType armorType, double mitigation, @Nonnull PerkFocus focus) {
        this.armorType = armorType;
        this.mitigation = mitigation;
        this.focus = focus;
    }

    /**
     * @param mitigation the share of its signature threat the slot mitigates, rolled from the piece's
     *                   own Defense and Toughness (see {@code TinkerItemBuilder.signatureMitigation}), so
     *                   the sentence this profile prints matches the damage the server will subtract
     */
    @Nonnull
    public static ArmorPerkProfile of(@Nonnull ModularArmorType type, double mitigation,
                                      @Nonnull List<PartComposition> parts) {
        return new ArmorPerkProfile(type, mitigation, PerkFocus.of(parts));
    }

    @Nonnull
    public static ArmorPerkProfile of(@Nonnull ModularArmorType type, double mitigation,
                                      @Nonnull PartComposition... parts) {
        List<PartComposition> list = new ArrayList<>();
        if (parts != null) {
            for (PartComposition part : parts) {
                list.add(part);
            }
        }
        return of(type, mitigation, list);
    }

    /** Signature defense name of the armor type, independent of materials. */
    @Nonnull
    public static String baseName(@Nonnull ModularArmorType type) {
        return switch (type) {
            case HELMET -> "Cranium Ward";
            case CHESTPLATE -> "Kinetic Dampener";
            case LEGGINGS -> "Stride Momentum";
            case BOOTS -> "Feathered Grounding";
        };
    }

    /**
     * Signature defense mechanic of the armor type, quoting the share this piece actually mitigates.
     *
     * <p>Independent of materials, but not of the forge: the percentage is the one the piece's rolled
     * Defense and Toughness produce, so a prime-alloy helmet promises a wider ward than a tin one.</p>
     */
    @Nonnull
    public static String baseDescription(@Nonnull ModularArmorType type, double mitigation) {
        String share = percent(mitigation);
        return switch (type) {
            case HELMET -> "wards " + share + " of a headshot's impact and filters environmental hazards.";
            case CHESTPLATE -> "absorbs " + share + " of heavy impacts and releases the stored energy.";
            case LEGGINGS -> "mitigates sprint stamina drain and speeds up movement recovery.";
            case BOOTS -> "negates " + share + " of fall damage and keeps traction on any terrain.";
        };
    }

    /** A share the way the lore prints it: {@code 0.38} becomes {@code 38%}. */
    @Nonnull
    private static String percent(double fraction) {
        return String.format(Locale.US, "%.0f%%", fraction * 100);
    }

    /**
     * Perk name of the forged armor piece: one epithet per mineral ahead of the slot mechanic, e.g.
     * {@code "Fluxforged Resonant Auric Kinetic Dampener"} for a Borax / Amethyst / Gold build.
     */
    @Nonnull
    public String getDisplayName() {
        return focus.perkName(baseName(armorType));
    }

    /** Full description: the slot mechanic plus the defensive essence its plate mineral teaches. */
    @Nonnull
    public String getDescription() {
        String effect = focus.affinity().getArmorEffect();
        if (!effect.isEmpty()) {
            effect = Character.toLowerCase(effect.charAt(0)) + effect.substring(1);
        }
        return baseDescription(armorType, mitigation) + " Imbued with " + focus.blend() + " essence: " + effect;
    }

    /** One-line perk rendered in the armor lore. */
    @Nonnull
    public String getPerkLine() {
        return getDisplayName() + ": " + getDescription();
    }

    @Nonnull
    public ModularArmorType getArmorType() {
        return armorType;
    }

    @Nonnull
    public TraitAffinity getAffinity() {
        return focus.affinity();
    }

    public double getPotency() {
        return focus.potency();
    }

    public int getPotencyPercent() {
        return focus.potencyPercent();
    }

    /** The share of its signature threat this piece mitigates, as a fraction. */
    public double getMitigation() {
        return mitigation;
    }

    @Nonnull
    public String getEssenceBlend() {
        return focus.blend();
    }

    @Nonnull
    public String getFocusLine() {
        return focus.focusLine();
    }

    @Nonnull
    public NamedTextColor getColor() {
        return focus.affinity().getColor();
    }

    @Override
    public String toString() {
        return getPerkLine();
    }
}
