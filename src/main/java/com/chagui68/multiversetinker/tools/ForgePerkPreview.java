package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The perk a partly-filled Forge assembly would produce, so a player reads the name and the mechanic of
 * a build <b>before</b> spending the parts on it.
 *
 * <p>A modular piece takes its perk name from every mineral cast into it, so the name only exists once
 * the parts do: a Borax / Amethyst / Gold sword is a {@code Fluxforged Resonant Auric Sweeping Cleave}.
 * Until now the only way to read that name was to forge the item and hover it, which spends the parts.
 * This reads the assembly slots and answers the same question earlier: the compound epithet grows as
 * parts are placed, the mechanic is known from the chosen type from the start, and the entry that names
 * each mineral lets the player see which part contributed which word.</p>
 *
 * <p>Everything here is derived from the same profiles the forged item is built from
 * ({@link WeaponPerkProfile}, {@link ToolPerkProfile}, {@link ArmorPerkProfile}), so the preview and the
 * finished item can never disagree — {@code ForgePerkPreviewTest} asserts exactly that by reading the
 * name off a real forged item.</p>
 *
 * <p>An incomplete build answers with a partial name and no description: the mechanic sentence quotes
 * the essence blend and, for armor, the share the piece would mitigate, and neither exists before the
 * minerals do. {@link #complete()} says whether the preview is the finished perk.</p>
 *
 * @param equipment   what the Forge would assemble, e.g. {@code Modular Broadsword}
 * @param name        the perk name, complete only when every part is placed
 * @param mechanic    the signature mechanic of the equipment type, independent of materials
 * @param description the full mechanic sentence, or {@code null} while a part is still missing
 * @param parts       the parts already placed, in forge order, each naming its mineral and epithet
 * @param missing     the part names still missing, in forge order
 * @param focusLine   the essence focus of the finished build, or {@code null} while incomplete
 * @param color       the identity colour of the head mineral's essence
 * @param required    how many parts this equipment takes
 */
public record ForgePerkPreview(@Nonnull String equipment,
                               @Nonnull String name,
                               @Nonnull String mechanic,
                               @Nullable String description,
                               @Nonnull List<PartLine> parts,
                               @Nonnull List<String> missing,
                               @Nullable String focusLine,
                               @Nonnull NamedTextColor color,
                               int required) {

    /** One placed part: the slot it fills, the mineral forged into it, and the word that mineral lends. */
    public record PartLine(@Nonnull String position, @Nonnull String material, @Nonnull String epithet) {
    }

    /** True when every part the equipment takes has been placed, so the name is the finished one. */
    public boolean complete() {
        return missing.isEmpty();
    }

    /** How many of the required parts are already placed. */
    public int placed() {
        return parts.size();
    }

    // ==========================================
    // FACTORIES
    // ==========================================

    /**
     * The perk a weapon assembly would produce. A two-part weapon is called with two parts, exactly as
     * the Forge itself hands over {@code null} for the missing third.
     */
    @Nonnull
    public static ForgePerkPreview weapon(@Nonnull ModularWeaponType type, @Nullable PartComposition... slots) {
        List<PartComposition> positional = positional(slots, 3);
        WeaponPerkProfile profile = WeaponPerkProfile.of(type, positional);

        List<String> labels = new ArrayList<>(List.of(type.getPart1Name(), type.getPart2Name()));
        if (!type.isTwoPart()) labels.add(type.getPart3Name());

        return assemble(type.getDisplayName(), labels, positional, profile.getDisplayName(),
                WeaponPerkProfile.baseName(type), profile.getDescription(), profile.getFocusLine(),
                profile.getColor());
    }

    /** The perk a tool assembly would produce: always a head, a handle and a pommel. */
    @Nonnull
    public static ForgePerkPreview tool(@Nonnull ModularToolType type, @Nullable PartComposition... slots) {
        List<PartComposition> positional = positional(slots, 3);
        ToolPerkProfile profile = ToolPerkProfile.of(type, positional);

        List<String> labels = List.of(ToolPartType.HEAD.getDisplayName(), ToolPartType.ROD.getDisplayName(),
                ToolPartType.BINDING.getDisplayName());

        return assemble(type.getDisplayName(), labels, positional, profile.getDisplayName(),
                ToolPerkProfile.baseName(type), profile.getDescription(), profile.getFocusLine(),
                profile.getColor());
    }

    /**
     * The perk an armor assembly would produce.
     *
     * <p>The slot mechanic quotes the share the piece would mitigate, which is rolled from its own
     * Defense and Toughness — the same {@link TinkerItemBuilder#signatureMitigation} the worn piece
     * answers a hit with, evaluated at the tier the Forge assembles at. That share only exists once the
     * plate, the lining and the trim do, so an unfinished piece is previewed without its sentence.</p>
     */
    @Nonnull
    public static ForgePerkPreview armor(@Nonnull ModularArmorType type, @Nullable PartComposition... slots) {
        List<PartComposition> positional = positional(slots, 3);
        List<String> labels = List.of(type.getPart1Name(), type.getPart2Name(), type.getPart3Name());

        double mitigation = 0.0;
        if (filled(labels, positional)) {
            TinkerItemBuilder.ArmorStats stats = TinkerItemBuilder.armorStats(type, positional.get(0),
                    positional.get(1), positional.get(2), EvolutionTier.WOOD);
            mitigation = TinkerItemBuilder.signatureMitigation(type, stats);
        }

        ArmorPerkProfile profile = ArmorPerkProfile.of(type, mitigation, positional);

        return assemble(type.getDisplayName(), labels, positional, profile.getDisplayName(),
                ArmorPerkProfile.baseName(type), profile.getDescription(), profile.getFocusLine(),
                profile.getColor());
    }

    // ==========================================
    // SHARED
    // ==========================================

    /**
     * Reads the placed parts into a preview.
     *
     * <p>The labels and the slots are kept apart on purpose: a slot index is the position the mineralogy
     * weights, while a label is what the player reads, and a two-part weapon has one of each fewer.</p>
     *
     * <p>This is also the one place that decides whether the build is complete, so a partial preview can
     * never carry a sentence describing minerals that are not in the assembly yet.</p>
     */
    @Nonnull
    private static ForgePerkPreview assemble(@Nonnull String equipment, @Nonnull List<String> labels,
                                             @Nonnull List<PartComposition> slots, @Nonnull String name,
                                             @Nonnull String mechanic, @Nullable String description,
                                             @Nullable String focusLine, @Nonnull NamedTextColor color) {
        List<PartLine> parts = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (int index = 0; index < labels.size(); index++) {
            PartComposition part = (index < slots.size()) ? slots.get(index) : null;
            if (part == null) {
                missing.add(labels.get(index));
                continue;
            }
            TinkerMaterial material = part.getPrimaryMaterial();
            parts.add(new PartLine(labels.get(index), material.getName(), PerkEpithet.of(material)));
        }

        boolean complete = missing.isEmpty();
        return new ForgePerkPreview(equipment, name, mechanic, complete ? description : null,
                List.copyOf(parts), List.copyOf(missing), complete ? focusLine : null, color, labels.size());
    }

    /** Whether every part the equipment takes already sits in the assembly. */
    private static boolean filled(@Nonnull List<String> labels, @Nonnull List<PartComposition> slots) {
        for (int index = 0; index < labels.size(); index++) {
            if (index >= slots.size() || slots.get(index) == null) return false;
        }
        return true;
    }

    /** Pads the placed parts out to the equipment's arity, keeping every position, so the weights hold. */
    @Nonnull
    private static List<PartComposition> positional(@Nullable PartComposition[] slots, int size) {
        List<PartComposition> out = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            out.add(slots != null && index < slots.length ? slots[index] : null);
        }
        return out;
    }
}
