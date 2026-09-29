package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.items.PartComposition;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
    private final PerkFocus focus;

    private ArmorPerkProfile(@Nonnull ModularArmorType armorType, @Nonnull PerkFocus focus) {
        this.armorType = armorType;
        this.focus = focus;
    }

    @Nonnull
    public static ArmorPerkProfile of(@Nonnull ModularArmorType type, @Nonnull List<PartComposition> parts) {
        return new ArmorPerkProfile(type, PerkFocus.of(parts));
    }

    @Nonnull
    public static ArmorPerkProfile of(@Nonnull ModularArmorType type, @Nonnull PartComposition... parts) {
        List<PartComposition> list = new ArrayList<>();
        if (parts != null) {
            for (PartComposition part : parts) {
                list.add(part);
            }
        }
        return of(type, list);
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

    /** Signature defense mechanic of the armor type, independent of materials. */
    @Nonnull
    public static String baseDescription(@Nonnull ModularArmorType type) {
        return switch (type) {
            case HELMET -> "reduces incoming headshot damage and filters environmental hazards.";
            case CHESTPLATE -> "absorbs 25% of heavy impacts and releases the stored energy.";
            case LEGGINGS -> "mitigates sprint stamina drain and speeds up movement recovery.";
            case BOOTS -> "negates up to 50% of fall damage and keeps traction on any terrain.";
        };
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
        return baseDescription(armorType) + " Imbued with " + focus.blend() + " essence: " + effect;
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
