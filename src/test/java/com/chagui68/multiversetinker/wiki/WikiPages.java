package com.chagui68.multiversetinker.wiki;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.PerkEpithet;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Renders the data blocks of the wiki from the live registries.
 *
 * <p>Prose belongs to humans and data belongs to the code, so the pages keep their hand-written headers
 * and the blocks that mirror a registry are bounded by two markers:</p>
 *
 * <pre>
 * &lt;!-- mvtink:generated --&gt;
 * … generated here, never edited by hand …
 * &lt;!-- mvtink:generated:end --&gt;
 * </pre>
 *
 * <p>{@link com.chagui68.multiversetinker.WikiDocumentationTest} re-renders every marked block and fails
 * when a page has drifted from the registry, which is what stops the documentation from going stale. The
 * test also rewrites the blocks when it is run with {@code -Dmvtink.wiki.write=true}.</p>
 *
 * <p>Three pages are deliberately <b>not</b> regenerated, because their tables are curated rather than
 * derived: the mineral catalogs ({@code Minerals.md} / {@code Minerales.md}) print a short, translated
 * wording for every trait that no registry field holds. Their counts are still guarded — see
 * {@link #catalogHeadings(boolean)}.</p>
 */
public final class WikiPages {

    /** Opens a generated block; the text until the end marker is owned by this class. */
    public static final String START_MARKER = "<!-- mvtink:generated -->";

    /** Closes a generated block. */
    public static final String END_MARKER = "<!-- mvtink:generated:end -->";

    /** Set to {@code true} to rewrite the marked blocks instead of only verifying them. */
    public static final String WRITE_PROPERTY = "mvtink.wiki.write";

    /**
     * Source notes a registry field cannot carry, english first.
     *
     * <p>Netherite is the one entry that also has to say <em>why</em> it never blends freely, and that
     * sentence lives only in the page. Keeping it here is what stops a regeneration from flattening the
     * entry into the section default.</p>
     */
    private static final Map<String, String[]> SOURCE_NOTES = Map.of(
            "mvtink_netherite", new String[]{
                    "vanilla **Netherite** is refined from Ancient Debris. It is already an alloy, so it never"
                            + " blends freely: it only enters its two curated recipes (**Cinder Steel**: steel +"
                            + " netherite; **Cosmic Netherite**: netherite + celestine).",
                    "la **Netherita** vanilla se refina de escombros ancestrales. Ya es una aleaci\u00F3n, as\u00ED"
                            + " que no se mezcla libremente: solo entra en sus dos recetas curadas"
                            + " (**Cinder Steel**: acero + netherita; **Cosmic Netherite**: netherita + celestina)."});

    /** The hand-written mineral catalogs, whose headings are checked but never rewritten. */
    public static final List<String> CATALOG_PATHS = List.of("Wiki-en/Minerals.md", "Wiki-es/Minerales.md");

    private final MaterialRegistry materials;
    private final AlloyRegistry alloys;

    /**
     * The materials the wiki documents, frozen before anything is rendered.
     *
     * <p>Rendering a pair row forges the alloy it describes, and forging registers a new material in the
     * registry. Reading the registry lazily would let those runtime alloys leak into the material
     * reference, so the page list is snapshotted once: what ships is what gets documented.</p>
     */
    private final List<TinkerMaterial> documented;

    public WikiPages(@Nonnull MaterialRegistry materials, @Nonnull AlloyRegistry alloys) {
        this.materials = materials;
        this.alloys = alloys;
        this.documented = List.copyOf(materials.getAll());
    }

    // ==========================================
    // PAGES
    // ==========================================

    /** Every page with a generated block, in the order the wiki lists them. */
    @Nonnull
    public List<String> generatedPaths() {
        return List.of(
                "Wiki-en/Material-Reference.md",
                "Wiki-es/Fuentes-de-Materiales.md",
                "Wiki-en/Perk-Names.md",
                "Wiki-es/Nombres-de-Perk.md",
                "Wiki-en/Alloy-Pairs-Overworld-1.md",
                "Wiki-en/Alloy-Pairs-Overworld-2.md",
                "Wiki-en/Alloy-Pairs-Nether-1.md",
                "Wiki-en/Alloy-Pairs-Nether-2.md",
                "Wiki-en/Alloy-Pairs-The-End-1.md",
                "Wiki-en/Alloy-Pairs-The-End-2.md",
                "Wiki-en/Alloy-Pairs-Vanilla.md",
                "Wiki-es/Pares-Overworld-1.md",
                "Wiki-es/Pares-Overworld-2.md",
                "Wiki-es/Pares-Nether-1.md",
                "Wiki-es/Pares-Nether-2.md",
                "Wiki-es/Pares-The-End-1.md",
                "Wiki-es/Pares-The-End-2.md",
                "Wiki-es/Pares-Vanilla.md");
    }

    /**
     * Renders the block that belongs in a page.
     *
     * @param path    the wiki page
     * @param content what the page currently holds, so a pair page can be rendered from the parents it
     *                already declares (the page split is a human decision, the rows are not)
     */
    @Nonnull
    public String render(@Nonnull String path, @Nonnull String content) {
        boolean spanish = path.startsWith("Wiki-es/");

        if (path.endsWith("/Material-Reference.md") || path.endsWith("/Fuentes-de-Materiales.md")) {
            return materialReference(spanish);
        }
        if (path.endsWith("/Perk-Names.md") || path.endsWith("/Nombres-de-Perk.md")) {
            return epithetTable(spanish);
        }
        return pairSections(declaredParents(content), spanish);
    }

    /** Text between the markers of a page, with the newline that follows the opening marker removed. */
    @Nonnull
    public static String generatedBlock(@Nonnull String content) {
        int start = content.indexOf(START_MARKER);
        int end = content.indexOf(END_MARKER);
        if (start < 0 || end < 0 || end < start) {
            throw new IllegalStateException("A generated page must carry the " + START_MARKER
                    + " and " + END_MARKER + " markers");
        }
        String block = content.substring(start + START_MARKER.length(), end);
        if (block.startsWith("\r\n")) return block.substring(2);
        if (block.startsWith("\n")) return block.substring(1);
        return block;
    }

    /** Replaces the generated block of a page, leaving every hand-written line untouched. */
    @Nonnull
    public static String withGeneratedBlock(@Nonnull String content, @Nonnull String block) {
        int start = content.indexOf(START_MARKER);
        int end = content.indexOf(END_MARKER);
        if (start < 0 || end < 0 || end < start) {
            throw new IllegalStateException("A generated page must carry the " + START_MARKER
                    + " and " + END_MARKER + " markers");
        }
        return content.substring(0, start + START_MARKER.length()) + "\n" + block + content.substring(end);
    }

    // ==========================================
    // MATERIAL REFERENCE
    // ==========================================

    private String materialReference(boolean spanish) {
        List<String> sections = new ArrayList<>();
        for (Section section : sections()) {
            sections.add(section.render(spanish));
        }
        return String.join("\n", sections);
    }

    private String referenceBlock(TinkerMaterial material, boolean spanish, Section section) {
        StringBuilder out = new StringBuilder("### " + material.getName() + " · `" + material.getId() + "`\n\n");
        out.append("- **").append(spanish ? "Cómo se obtiene:" : "How to obtain:").append("** ")
                .append(obtain(material, spanish, section)).append('\n');
        out.append("- **").append(spanish ? "Clase:" : "Class:").append("** ")
                .append(material.getType().getDisplayTypeName())
                .append(" · ").append(material.getRarity().getDisplayName())
                .append(" · ").append(spanish ? "color" : "colour").append(" `").append(material.getColorHex())
                .append("` · ").append(spanish ? "funde en " : "melts in ").append(material.getMeltingDurationTicks())
                .append("t (~").append(Math.round(material.getMeltingDurationTicks() / 20.0)).append("s)\n");
        out.append("- **").append(spanish ? "Estadísticas de forja:" : "Forge stats:").append("** ")
                .append('+').append(material.getDurabilityBonus()).append(spanish ? " durabilidad" : " durability")
                .append(" · ").append(trim(material.getMiningSpeed())).append('x')
                .append(spanish ? " velocidad de minado" : " mining speed")
                .append(" · +").append(trim(material.getAttackDamageBonus()))
                .append(spanish ? " daño de ataque" : " attack damage").append('\n');
        out.append("- **").append(spanish ? "Rasgo — " : "Trait — ").append(material.getTraitName()).append(":** ")
                .append(material.getTraitDescription()).append('\n');
        if (section.showsTraitChannels) {
            // The channels are only worth a line when they say something the trait does not: an entry whose
            // weapon and armor text fall back to the trait description would just repeat it.
            if (!material.getWeaponTraitDescription().equals(material.getTraitDescription())) {
                out.append("  - ⚔ **").append(spanish ? "En arma:" : "On weapons:").append("** ")
                        .append(material.getWeaponTraitDescription()).append('\n');
            }
            if (!material.getArmorTraitDescription().equals(material.getTraitDescription())) {
                out.append("  - 🛡 **").append(spanish ? "En armadura:" : "On armor:").append("** ")
                        .append(material.getArmorTraitDescription()).append('\n');
            }
        }
        out.append("- **").append(spanish ? "Esencias:" : "Essences:").append("** ")
                .append(essenceNames(material)).append('\n');
        if (section.showsPrimeEffects) {
            // A catalyst table entry describes the prime its own sigil grants, so it has to ask the catalyst
            // rather than the material: the material has no alloy parents to read a sigil from.
            VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(material.getId());
            PrimeUltimate ultimate = catalyst != null ? catalyst.getUltimate() : PrimeUltimate.of(material);
            PrimeArmorState state = catalyst != null ? catalyst.getArmorState() : PrimeArmorState.of(material);
            out.append("- **").append(spanish ? "Ultimate primordial:" : "Prime ultimate:").append("** ")
                    .append(ultimate.getDisplayName()).append(" — ").append(ultimate.getDescription()).append('\n');
            out.append("- **").append(spanish ? "Estado de armadura:" : "Prime armor state:").append("** ")
                    .append(state.getDisplayName()).append(" — ").append(state.getDescription()).append('\n');
        }
        return out.toString();
    }

    private String obtain(TinkerMaterial material, boolean spanish, Section section) {
        String[] note = SOURCE_NOTES.get(material.getId());
        if (note != null) {
            return spanish ? note[1] : note[0];
        }
        if (isLegendary(material)) {
            String parents = material.getAlloyParents();
            if (parents != null) {
                String[] ids = parents.split(",");
                if (ids.length >= 2) {
                    TinkerMaterial first = materials.get(ids[0].trim());
                    TinkerMaterial second = materials.get(ids[1].trim());
                    if (first != null && second != null) {
                        return (spanish ? "Crisol — funde **" : "Crucible — blend **") + first.getName() + "** (`"
                                + first.getId() + "`) + **" + second.getName() + "** (`" + second.getId() + "`).";
                    }
                }
            }
        }
        if (isCatalyst(material.getId())) {
            return spanish
                    ? "deja un **" + material.getName() + "** real en el crisol junto a una aleación"
                            + " legendaria (fusión primordial)."
                    : "drop a real **" + material.getName() + "** in the crucible next to a legendary alloy"
                            + " (prime fusion).";
        }
        return spanish ? section.obtainEs : section.obtainEn;
    }

    // ==========================================
    // EPITHET TABLE
    // ==========================================

    private String epithetTable(boolean spanish) {
        StringBuilder out = new StringBuilder();
        out.append(spanish ? "| Mineral | ID | Epíteto |\n" : "| Mineral | ID | Epithet |\n")
                .append("|---|---|---|\n");
        for (TinkerMaterial material : sorted(true)) {
            out.append("| **").append(material.getName()).append("** | `").append(material.getId())
                    .append("` | **").append(PerkEpithet.of(material)).append("** |\n");
        }
        return out.toString();
    }

    // ==========================================
    // MINERAL CATALOG HEADINGS
    // ==========================================

    /**
     * The section headings the hand-written mineral catalogs must carry.
     *
     * <p>The catalogs themselves are curated — each trait is described in a short wording, translated into
     * Spanish, that no registry field holds — so they are not rewritten. Their counts are still derived
     * here, which is enough to catch the failure that started all of this: a catalog that keeps advertising
     * a number of materials the registry has long outgrown.</p>
     */
    @Nonnull
    public List<String> catalogHeadings(boolean spanish) {
        List<String> headings = new ArrayList<>();
        for (Section section : sections()) {
            if (section.catalogTitleEn == null) continue;
            String title = spanish ? section.catalogTitleEs : section.catalogTitleEn;
            String unit = spanish ? section.catalogUnitEs : section.catalogUnitEn;
            headings.add("## " + section.icon + " " + title + " (" + section.members().size() + unit + ")");
        }
        return headings;
    }

    // ==========================================
    // PAIR PAGES
    // ==========================================

    /** The parents a pair page declares, in the order it lists them. */
    @Nonnull
    public List<TinkerMaterial> declaredParents(@Nonnull String content) {
        List<TinkerMaterial> parents = new ArrayList<>();
        for (String line : generatedBlock(content).split("\n")) {
            if (!line.startsWith("### ")) continue;
            int tick = line.indexOf('`');
            int last = line.lastIndexOf('`');
            if (tick < 0 || last <= tick) continue;
            TinkerMaterial material = materials.get(line.substring(tick + 1, last));
            if (material != null && !parents.contains(material)) parents.add(material);
        }
        return parents;
    }

    private String pairSections(List<TinkerMaterial> parents, boolean spanish) {
        List<String> sections = new ArrayList<>();
        for (TinkerMaterial parent : parents) {
            sections.add(pairSection(parent, spanish));
        }
        return String.join("\n", sections);
    }

    private String pairSection(TinkerMaterial parent, boolean spanish) {
        StringBuilder out = new StringBuilder("### " + parent.getName() + " · `" + parent.getId() + "`\n\n");
        out.append("- **").append(spanish ? "Estadísticas:" : "Stats:").append("** ")
                .append('+').append(parent.getDurabilityBonus()).append(" · ")
                .append(trim(parent.getMiningSpeed())).append("x · +")
                .append(trim(parent.getAttackDamageBonus())).append('\n');
        out.append("- **").append(spanish ? "Rasgo:" : "Trait:").append("** ")
                .append(parent.getTraitName()).append(" — ").append(parent.getTraitDescription()).append('\n');
        out.append("- **").append(spanish ? "Esencias:" : "Essences:").append("** ")
                .append(essenceLinks(parent, spanish)).append('\n');

        List<TinkerMaterial> partners = partnersOf(parent);
        out.append("- **").append(spanish ? "Mezcla con (" : "Blends with (").append(partners.size()).append("):** ");
        List<String> names = new ArrayList<>();
        for (TinkerMaterial partner : partners) names.add(partner.getName());
        out.append(String.join(", ", names)).append("\n\n");

        out.append(spanish
                ? "| Socio | Aleación resultante | ID | Estadísticas (dur · vel · daño) | Rasgo | Esencias |\n"
                : "| Partner | Resulting alloy | ID | Stats (dur · speed · damage) | Trait | Essences |\n");
        out.append("|---|---|---|---|---|---|\n");
        for (TinkerMaterial partner : partners) {
            if (partner.getId().compareTo(parent.getId()) <= 0) continue;
            out.append(pairRow(parent, partner, spanish)).append('\n');
        }
        return out.toString();
    }

    /**
     * Every mineral this one can be blended with, in id order.
     *
     * <p>Only the brush and vanilla minerals take part: a legendary alloy or a catalyst is never forged as
     * a plain pair, it forges a <b>prime</b>, and the prime ladder has its own page.</p>
     */
    private List<TinkerMaterial> partnersOf(TinkerMaterial parent) {
        List<TinkerMaterial> partners = new ArrayList<>();
        for (TinkerMaterial candidate : sorted(false)) {
            if (candidate.getId().equalsIgnoreCase(parent.getId())) continue;
            if (!AlloyRegistry.isMixable(candidate)) continue;
            partners.add(candidate);
        }
        return partners;
    }

    private String pairRow(TinkerMaterial parent, TinkerMaterial partner, boolean spanish) {
        TinkerAlloy curated = alloys.findAlloy(parent.getId(), partner.getId());
        if (curated != null && curated.id().equals(AlloyRegistry.dynamicId(parent, partner))) {
            // A dynamic alloy is only ever curated by name after the fact; the id is what tells them apart.
            curated = null;
        }
        TinkerAlloy alloy = curated != null
                ? curated
                : alloys.findOrCreateAlloy(parent, partner, materials);
        TinkerMaterial alloyMaterial = materials.get(alloy.id());

        // A curated alloy is hand-tuned and prints as written; a forged one is reported the way the plugin
        // reports it to the player, one decimal and all.
        String speed = curated != null ? trim(alloy.miningSpeed()) : oneDecimal(alloy.miningSpeed());
        String damage = curated != null ? trim(alloy.attackDamageBonus()) : oneDecimal(alloy.attackDamageBonus());

        StringBuilder out = new StringBuilder("| ").append(partner.getName()).append(" (`").append(partner.getId())
                .append("`)").append(curated != null ? " ★" : "").append(" | ")
                .append(alloy.name()).append(" | `").append(alloy.id()).append("` | +")
                .append(alloy.durabilityBonus()).append(" · ").append(speed).append("x · +")
                .append(damage).append(" | ").append(alloy.traitName()).append(" | ");

        Set<TraitAffinity> essences = alloyMaterial != null
                ? TraitAffinity.of(alloyMaterial)
                : TraitAffinity.of(parent);
        List<String> links = new ArrayList<>();
        List<String> shorthands = new ArrayList<>();
        for (TraitAffinity essence : essences) {
            links.add("[" + essence.getDisplayName() + "](" + indexPage(spanish)
                    + "#" + essence.getDisplayName().toLowerCase(Locale.ROOT) + ")");
            shorthands.add(shortHand(essence, spanish));
        }
        out.append(String.join(", ", links)).append("<br><sub>").append(String.join(" · ", shorthands))
                .append("</sub> |");
        return out.toString();
    }

    private String essenceLinks(TinkerMaterial material, boolean spanish) {
        List<String> links = new ArrayList<>();
        for (TraitAffinity essence : TraitAffinity.of(material)) {
            links.add("[" + essence.getDisplayName() + "](" + indexPage(spanish)
                    + "#" + essence.getDisplayName().toLowerCase(Locale.ROOT) + ")");
        }
        return String.join(", ", links);
    }

    private static String indexPage(boolean spanish) {
        return spanish ? "Indice-de-Recetas.md" : "Alloy-Recipe-Index.md";
    }

    // ==========================================
    // SHORTHANDS AND HELPERS
    // ==========================================

    /** The one-line reminder of what an essence does on weapon / tool / armor, as the pair pages print it. */
    static String shortHand(TraitAffinity essence, boolean spanish) {
        if (spanish) {
            return switch (essence) {
                case INFERNAL -> "quema, autofunde, resist. fuego";
                case VOID -> "distorsion, XP extra, caida lenta";
                case PRIMAL -> "dano de impacto, drops extra, absorcion";
                case TEMPERED -> "+10% dano, autoreparacion, resistencia";
                case RADIANT -> "marca, vision nocturna, cura";
                case RESONANT -> "onda, eco de menas, pulso";
                case VOLATILE -> "estallido de fuego, chispa, prende";
                case TERRAIN -> "ralentiza, bloques extra, aturde";
                case SWIFT -> "velocidad de ataque, prisa, rapidez";
                case BRUTAL -> "empuje, mena extra, refleja";
                case BULWARK -> "endurece, absorbe desgaste, resist. extra";
                case ASCENDANT -> "trasciende, XP extra, regeneracion";
            };
        }
        return switch (essence) {
            case INFERNAL -> "burn, auto-smelt, fire res";
            case VOID -> "warp, bonus XP, slow fall";
            case PRIMAL -> "impact dmg, extra drops, absorption";
            case TEMPERED -> "+10% dmg, self-repair, resistance";
            case RADIANT -> "mark, night vision, heal";
            case RESONANT -> "shockwave, ore chime, pulse";
            case VOLATILE -> "flame burst, spark, ignite";
            case TERRAIN -> "slow, extra blocks, stun";
            case SWIFT -> "attack speed, haste, swiftness";
            case BRUTAL -> "knockback, extra ore, reflect";
            case BULWARK -> "harden, absorb wear, extra resist";
            case ASCENDANT -> "transcend, bonus XP, regeneration";
        };
    }

    private String essenceNames(TinkerMaterial material) {
        List<String> names = new ArrayList<>();
        for (TraitAffinity essence : TraitAffinity.of(material)) names.add(essence.getDisplayName());
        return String.join(", ", names);
    }

    /** Trims a trailing {@code .0}, so 8.0 prints as {@code 8} and 6.8 stays {@code 6.8}. */
    static String trim(double value) {
        String text = oneDecimal(value);
        return text.endsWith(".0") ? text.substring(0, text.length() - 2) : text;
    }

    /** One decimal, exactly as the plugin prints a stat in an item's lore. */
    static String oneDecimal(double value) {
        return String.format(Locale.US, "%.1f", value);
    }

    private List<TinkerMaterial> sorted(boolean byName) {
        List<TinkerMaterial> all = new ArrayList<>(documented);
        all.sort(byName
                ? Comparator.comparing(TinkerMaterial::getName).thenComparing(TinkerMaterial::getId)
                : Comparator.comparing(TinkerMaterial::getId));
        return all;
    }

    // ==========================================
    // SECTIONS
    // ==========================================

    private List<Section> sections() {
        return List.of(
                new Section("🌍", "Overworld", "Overworld",
                        material -> isBase(material) && material.getOrigin() == MineralOrigin.OVERWORLD,
                        true, false)
                        .withObtain("Prospector Brush on Overworld geology (Stone / Cobblestone).",
                                "Brocha de Prospector sobre geología del Overworld (Stone / Cobblestone).")
                        .withUnit(" materials", " materiales")
                        .withCatalog("Overworld", "Overworld", " Minerals", " Minerales"),
                new Section("🔥", "The Nether", "The Nether",
                        material -> isBase(material) && material.getOrigin() == MineralOrigin.NETHER,
                        true, false)
                        .withObtain("Prospector Brush on Nether geology (Netherrack / Blackstone).",
                                "Brocha de Prospector sobre geología del Nether (Netherrack / Blackstone).")
                        .withUnit(" materials", " materiales")
                        .withCatalog("The Nether", "The Nether", " Minerals", " Minerales"),
                new Section("🌌", "The End", "The End",
                        material -> isBase(material) && material.getOrigin() == MineralOrigin.THE_END,
                        true, false)
                        .withObtain("Prospector Brush on End geology (End Stone).",
                                "Brocha de Prospector sobre geología del End (End Stone).")
                        .withUnit(" materials", " materiales")
                        .withCatalog("The End", "The End", " Minerals", " Minerales"),
                new Section("🧱", "Vanilla Ores", "Vanilla Ores",
                        material -> isBase(material) && material.getOrigin() == MineralOrigin.VANILLA,
                        true, false)
                        .withObtain("Refined from vanilla Minecraft ores (smeltery + casting cauldron).",
                                "Refinado de menas vanilla de Minecraft (fundición + caldero de moldeo).")
                        .withUnit(" materials", " materiales"),
                new Section("⚗", "Legendary alloys", "Aleaciones legendarias", WikiPages::isLegendary,
                        true, false)
                        .withUnit("", ""),
                new Section("❖", "Crucible catalysts", "Catalizadores del crisol",
                        material -> isCatalyst(material.getId()), false, true)
                        .withUnit("", "")
                        .withCatalog("Crucible Catalysts", "Catalizadores del Crisol",
                                " vanilla items", " objetos vanilla")
                        .withNote("Catalysts are never blended on their own: they pair with one of the 16 legendary"
                                        + " alloys to forge a **prime alloy**. The catalyst decides the weapon ultimate"
                                        + " and the armor state.",
                                "Los catalizadores no se mezclan solos: se emparejan con una de las 16 aleaciones"
                                        + " legendarias para forjar una **aleación primordial**. El catalizador decide el"
                                        + " ultimate del arma y el estado de la armadura."));
    }

    /**
     * Whether a material is a hand-registered mineral rather than something the crucible forged.
     *
     * <p>Forged alloys (composites and primes) carry alloy parents, catalysts are vanilla items typed as
     * alloys, and the legendary recipes have their own section — none of them belong in the geological
     * sections, which only describe what a Prospector Brush or a vanilla ore yields.</p>
     */
    private static boolean isBase(TinkerMaterial material) {
        return !material.isAlloy() && !isLegendary(material) && !isCatalyst(material.getId());
    }

    private static boolean isLegendary(TinkerMaterial material) {
        return AlloyRegistry.LEGENDARY_IDS.contains(material.getId().toLowerCase(Locale.ROOT));
    }

    private static boolean isCatalyst(String id) {
        return id.toLowerCase(Locale.ROOT).startsWith("mvtink_catalyst_");
    }

    /** One group of materials, with the wording of both its heading and its bullets. */
    private final class Section {
        private final String icon;
        private final String titleEn;
        private final String titleEs;
        private final Predicate<TinkerMaterial> member;
        private final boolean showsTraitChannels;
        private final boolean showsPrimeEffects;
        private String obtainEn;
        private String obtainEs;
        private String noteEn;
        private String noteEs;
        private String unitEn = "";
        private String unitEs = "";
        private String catalogTitleEn;
        private String catalogTitleEs;
        private String catalogUnitEn = "";
        private String catalogUnitEs = "";

        private Section(String icon, String titleEn, String titleEs, Predicate<TinkerMaterial> member,
                        boolean showsTraitChannels, boolean showsPrimeEffects) {
            this.icon = icon;
            this.titleEn = titleEn;
            this.titleEs = titleEs;
            this.member = member;
            this.showsTraitChannels = showsTraitChannels;
            this.showsPrimeEffects = showsPrimeEffects;
        }

        private Section withObtain(String english, String spanish) {
            this.obtainEn = english;
            this.obtainEs = spanish;
            return this;
        }

        private Section withNote(String english, String spanish) {
            this.noteEn = english;
            this.noteEs = spanish;
            return this;
        }

        /** The word that follows the count in the section heading, with its leading space. */
        private Section withUnit(String english, String spanish) {
            this.unitEn = english;
            this.unitEs = spanish;
            return this;
        }

        /** Marks the section as one of the mineral catalog's sections, which count items instead. */
        private Section withCatalog(String titleEn, String titleEs, String unitEn, String unitEs) {
            this.catalogTitleEn = titleEn;
            this.catalogTitleEs = titleEs;
            this.catalogUnitEn = unitEn;
            this.catalogUnitEs = unitEs;
            return this;
        }

        private String render(boolean spanish) {
            List<TinkerMaterial> members = members();
            StringBuilder out = new StringBuilder("## ").append(icon).append(' ')
                    .append(spanish ? titleEs : titleEn).append(" (").append(members.size())
                    .append(spanish ? unitEs : unitEn).append(")\n\n");
            if (noteEn != null) {
                out.append(spanish ? noteEs : noteEn).append("\n\n");
            }
            List<String> blocks = new ArrayList<>();
            for (TinkerMaterial material : members) {
                blocks.add(referenceBlock(material, spanish, this));
            }
            return out.append(String.join("\n", blocks)).toString();
        }

        private List<TinkerMaterial> members() {
            List<TinkerMaterial> list = new ArrayList<>();
            for (TinkerMaterial material : documented) {
                if (member.test(material)) list.add(material);
            }
            list.sort(Comparator.comparing(TinkerMaterial::getName).thenComparing(TinkerMaterial::getId));
            return list;
        }
    }

    // ==========================================
    // WRITING
    // ==========================================

    /** Rewrites the generated block of every page in place, keeping the rest of the file untouched. */
    public void write(@Nonnull Path root) {
        for (String path : generatedPaths()) {
            Path file = root.resolve(path);
            String content = read(file);
            String updated = withGeneratedBlock(content, render(path, content));
            if (updated.equals(content)) continue;
            try {
                Files.writeString(file, updated, StandardCharsets.UTF_8);
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        }
    }

    @Nonnull
    public static String read(@Nonnull Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    /** Pages in the order the wiki lists them, mapped to the block they must hold. */
    @Nonnull
    public Map<String, String> expectedBlocks(@Nonnull Path root) {
        Map<String, String> blocks = new LinkedHashMap<>();
        for (String path : generatedPaths()) {
            String content = read(root.resolve(path));
            blocks.put(path, render(path, content));
        }
        return blocks;
    }
}
