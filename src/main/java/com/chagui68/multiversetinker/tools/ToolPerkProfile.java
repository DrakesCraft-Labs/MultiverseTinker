package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.items.PartComposition;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Material-driven tool perk.
 *
 * <p>Exactly like weapons, a modular tool's signature mechanic (vein resonance, lumber cleave,
 * seismic tremor...) is fixed by its {@link ModularToolType}, but the <b>essence</b> that names and
 * powers it comes from the minerals it was forged from: the head part dictates the tool's elemental
 * identity while the handle and pommel dictate how concentrated that identity is.</p>
 *
 * <p>This is what keeps an Infernal pickaxe, a Void pickaxe and a Radiant pickaxe from being the same
 * tool with a different name, and what stops every tool of a given type from sharing one fixed perk
 * line.</p>
 */
public final class ToolPerkProfile {

    private final ModularToolType toolType;
    private final PerkFocus focus;

    private ToolPerkProfile(@Nonnull ModularToolType toolType, @Nonnull PerkFocus focus) {
        this.toolType = toolType;
        this.focus = focus;
    }

    @Nonnull
    public static ToolPerkProfile of(@Nonnull ModularToolType type, @Nonnull List<PartComposition> parts) {
        return new ToolPerkProfile(type, PerkFocus.of(parts));
    }

    @Nonnull
    public static ToolPerkProfile of(@Nonnull ModularToolType type, @Nonnull PartComposition... parts) {
        List<PartComposition> list = new ArrayList<>();
        if (parts != null) {
            for (PartComposition part : parts) {
                list.add(part);
            }
        }
        return of(type, list);
    }

    /** Signature perk name of the tool type, independent of materials. */
    @Nonnull
    public static String baseName(@Nonnull ModularToolType type) {
        return switch (type) {
            case PICKAXE -> "Vein Resonance";
            case AXE -> "Lumber Cleave";
            case SHOVEL -> "Seismic Tremor";
            case HOE -> "Harvest Scythe";
            case FISHING_ROD -> "Abyssal Dredge";
            case SWORD -> "Broadsword Sweep";
        };
    }

    /** Signature perk mechanic of the tool type, independent of materials. */
    @Nonnull
    public static String baseDescription(@Nonnull ModularToolType type) {
        return switch (type) {
            case PICKAXE -> "bonus ores drop from resonating seams and grant temporary mining haste.";
            case AXE -> "fells whole trunks in one blow and shatters enemy shields.";
            case SHOVEL -> "sneak-digging excavates a 3x3 area of soil, sand and gravel.";
            case HOE -> "reaps a 3x3 field of mature crops and replants the seeds.";
            case FISHING_ROD -> "deep water fishing can hook rare raw minerals.";
            case SWORD -> "channels the head mineral through a sweeping broadsword strike.";
        };
    }

    /** Material-flavoured perk name, e.g. {@code "Infernal Vein Resonance"}. */
    @Nonnull
    public String getDisplayName() {
        return focus.affinity().getDisplayName() + " " + baseName(toolType);
    }

    /** Full perk description: the signature mechanic plus the essence blend its head mineral teaches. */
    @Nonnull
    public String getDescription() {
        String effect = focus.affinity().getToolEffect();
        if (!effect.isEmpty()) {
            effect = Character.toLowerCase(effect.charAt(0)) + effect.substring(1);
        }
        return baseDescription(toolType) + " Imbued with " + focus.blend() + " essence: " + effect;
    }

    /** One-line perk rendered in the tool lore. */
    @Nonnull
    public String getPerkLine() {
        return getDisplayName() + ": " + getDescription();
    }

    @Nonnull
    public ModularToolType getToolType() {
        return toolType;
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
