package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * The named ability an alloy awakens in a forged weapon.
 *
 * <p>Plain minerals already carry their traits and the essence ultimates; an alloy is the result of
 * going back to the crucible, and the deeper the metallurgy the louder the payoff:</p>
 *
 * <ul>
 *     <li><b>Composite</b> (two minerals): a <i>fusion art</i> built from each parent on its own. It is named
 *     after both minerals' epithets ({@code Fluxforged-Resonant Spire}), so no two pairs share a name; the
 *     first mineral's character is the payload (fire, gravity, frost…), the second mineral's most specific
 *     essence is the shape the payload takes (a pyre, a rift, a gale…), the choreography is drawn in both
 *     minerals' colours, and every foe it reaches also suffers both minerals' own forge traits.</li>
 *     <li><b>Legendary</b>: the alloy's own {@link LegendaryArt}.</li>
 *     <li><b>Prime</b>: the legendary art <i>ascended</i>, followed by an overlay named after the second
 *     ingredient, which also echoes that ingredient's own forge traits.</li>
 *     <li><b>Mythic</b>: a prime whose second ingredient is a vanilla catalyst (the overlay is the catalyst's
 *     own ultimate) or another legendary (both legendary arts play back to back).</li>
 * </ul>
 *
 * <p>Resolution is pure and deterministic, so the lore, the Forge preview and combat always agree on what
 * an alloy does.</p>
 *
 * @param name        the art's display name
 * @param tier        the pedigree it was forged at
 * @param legendary   the legendary art it ascends from, or {@code null} for a composite
 * @param secondary   the second legendary art of a double-legendary mythic, otherwise {@code null}
 * @param catalyst    the catalyst sigil of a catalyst mythic, otherwise {@code null}
 * @param payload     the essence whose effect the art delivers
 * @param shape       the essence whose geometry the fusion (or the overlay) is drawn with
 * @param colorHex    main colour of the choreography
 * @param accentHex   secondary colour of the choreography
 * @param description one sentence describing what the art does
 * @param sourceId    the id of the alloy the art was resolved from
 * @param echoes      ids of the minerals whose own forge traits the art echoes on every foe it reaches
 */
public record SignatureArt(@Nonnull String name,
                           @Nonnull ForgeTier tier,
                           @Nullable LegendaryArt legendary,
                           @Nullable LegendaryArt secondary,
                           @Nullable VanillaCatalyst catalyst,
                           @Nonnull TraitAffinity payload,
                           @Nonnull TraitAffinity shape,
                           @Nonnull String colorHex,
                           @Nonnull String accentHex,
                           @Nonnull String description,
                           @Nonnull String sourceId,
                           @Nonnull List<String> echoes) {

    /** Resolved arts by alloy id: an alloy's art never changes, and combat asks for it on every hit. */
    private static final Map<String, SignatureArt> CACHE = new ConcurrentHashMap<>();

    /**
     * Finds a registered material by id, so a composite can be read through its two parents. The plugin
     * installs the live registry on enable; without one, arts fall back to the alloy's inherited essences.
     */
    private static volatile Function<String, TinkerMaterial> lookup = id -> null;

    /**
     * Fixed ranking that picks a mineral's character: elemental and dimensional essences describe what a
     * mineral is, generic physical ones only back it up. Mirrors the identity order of {@link PerkFocus}.
     */
    private static final List<TraitAffinity> IDENTITY_ORDER = List.of(
            TraitAffinity.INFERNAL, TraitAffinity.VOID, TraitAffinity.VOLATILE, TraitAffinity.RESONANT,
            TraitAffinity.RADIANT, TraitAffinity.TERRAIN, TraitAffinity.TEMPERED, TraitAffinity.PRIMAL,
            TraitAffinity.SWIFT, TraitAffinity.BRUTAL, TraitAffinity.BULWARK, TraitAffinity.ASCENDANT);

    public SignatureArt {
        echoes = List.copyOf(echoes);
    }

    /** Installs the material lookup used to read a composite through its parents. */
    public static void setMaterialLookup(@Nullable Function<String, TinkerMaterial> materials) {
        lookup = materials != null ? materials : id -> null;
        CACHE.clear();
    }

    // ==========================================
    // RESOLUTION
    // ==========================================

    /**
     * The art a single material awakens, or {@code null} for a plain mineral, a vanilla catalyst on its
     * own, or anything else that is not an alloy forged in the crucible.
     */
    @Nullable
    public static SignatureArt of(@Nullable TinkerMaterial material) {
        if (material == null) return null;
        ForgeTier tier = ForgeTier.of(material);
        if (tier == ForgeTier.MINERAL) return null;

        String key = material.getId().toLowerCase(Locale.ROOT);
        SignatureArt cached = CACHE.get(key);
        if (cached != null) return cached;

        SignatureArt art = resolve(key, material.getAlloyParents(), ordered(TraitAffinity.of(material)),
                material.getColorHex(), tier, lookup);
        if (art != null) CACHE.put(key, art);
        return art;
    }

    /**
     * The art a pair would awaken once the crucible fuses it, computed from the parents alone so the
     * crucible can name an alloy before it exists.
     */
    @Nullable
    public static SignatureArt forPair(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        String id = AlloyRegistry.dynamicId(first, second);
        LegendaryArt curated = curatedRecipe(first, second);
        if (curated != null) {
            return legendary(curated, curated.getAlloyId());
        }
        TinkerMaterial canonicalA = first.getId().compareTo(second.getId()) <= 0 ? first : second;
        TinkerMaterial canonicalB = canonicalA == first ? second : first;
        String parents = canonicalA.getId() + "," + canonicalB.getId();
        boolean prime = AlloyRegistry.isPrimePair(first, second);
        ForgeTier tier = prime
                ? (ForgeTier.isMythicParents(parents) ? ForgeTier.MYTHIC : ForgeTier.PRIME)
                : ForgeTier.COMPOSITE;
        List<TraitAffinity> essences = parseOrdered(TraitAffinity.inherit(first, second));
        Function<String, TinkerMaterial> find = parentId -> {
            if (first.getId().equalsIgnoreCase(parentId)) return first;
            if (second.getId().equalsIgnoreCase(parentId)) return second;
            return lookup.apply(parentId);
        };
        return resolve(id, parents, essences, blend(first.getColorHex(), second.getColorHex()), tier, find);
    }

    /**
     * The art of a whole weapon: the most demanding alloy in it, head part first when two alloys share a
     * pedigree. {@code null} when the weapon is forged from plain minerals only.
     */
    @Nullable
    public static SignatureArt forWeapon(@Nullable Collection<PartComposition> parts) {
        if (parts == null) return null;
        SignatureArt best = null;
        for (PartComposition part : parts) {
            if (part == null) continue;
            for (PartComposition.Entry entry : part.getEntries()) {
                SignatureArt art = of(entry.material());
                if (art == null) continue;
                if (best == null || art.tier().getLevel() > best.tier().getLevel()) best = art;
            }
        }
        return best;
    }

    /** Forgets every resolved art; the plugin calls this on reload. */
    public static void clearCache() {
        CACHE.clear();
    }

    @Nullable
    private static SignatureArt resolve(@Nonnull String id, @Nullable String parents,
                                        @Nonnull List<TraitAffinity> essences, @Nonnull String colorHex,
                                        @Nonnull ForgeTier tier, @Nonnull Function<String, TinkerMaterial> find) {
        LegendaryArt own = LegendaryArt.byAlloyId(id);
        if (own != null) return legendary(own, id);

        TraitAffinity payload = essences.isEmpty() ? TraitAffinity.PRIMAL : essences.get(0);
        TraitAffinity shape = essences.isEmpty() ? TraitAffinity.PRIMAL : essences.get(essences.size() - 1);
        List<String> parentIds = new ArrayList<>();
        if (parents != null) {
            for (String raw : parents.split(",")) {
                String parentId = raw.trim().toLowerCase(Locale.ROOT);
                if (!parentId.isEmpty()) parentIds.add(parentId);
            }
        }

        if (tier == ForgeTier.COMPOSITE) {
            TinkerMaterial a = parentIds.size() == 2 ? find.apply(parentIds.get(0)) : null;
            TinkerMaterial b = parentIds.size() == 2 ? find.apply(parentIds.get(1)) : null;
            if (a == null || b == null) {
                // No registry to read the parents through: the alloy's own blend still names an art.
                String name = payloadWord(payload) + " " + shapeWord(shape);
                String description = "Fusion art: " + payloadEffect(payload) + " " + lowerFirst(shapeEffect(shape));
                return new SignatureArt(name, tier, null, null, null, payload, shape,
                        colorHex, accentOf(shape), description, id, List.of());
            }
            TraitAffinity character = character(a);
            TraitAffinity form = specialty(b);
            String name = PerkEpithet.of(a) + "-" + PerkEpithet.of(b) + " " + shapeWord(form);
            String description = "Fusion of " + a.getName() + " and " + b.getName() + ": "
                    + payloadEffect(character) + " " + lowerFirst(shapeEffect(form))
                    + " Every foe it reaches also suffers " + a.getTraitName() + " and " + b.getTraitName() + ".";
            return new SignatureArt(name, tier, null, null, null, character, form,
                    a.getColorHex(), b.getColorHex(), description, id, List.of(a.getId(), b.getId()));
        }

        // Prime or mythic: find the legendary it ascends from and what the second ingredient was.
        LegendaryArt first = null;
        LegendaryArt second = null;
        VanillaCatalyst catalyst = null;
        String partnerId = null;
        for (String parentId : parentIds) {
            LegendaryArt art = LegendaryArt.byAlloyId(parentId);
            VanillaCatalyst sigil = VanillaCatalyst.byMaterialId(parentId);
            if (art != null) {
                if (first == null) first = art;
                else second = art;
            } else if (sigil != null) {
                catalyst = sigil;
            } else {
                partnerId = parentId;
            }
        }
        if (first == null) return null;

        // A mineral or composite partner lends its own word, its own shape and its own traits.
        TinkerMaterial partner = (second == null && catalyst == null && partnerId != null) ? find.apply(partnerId) : null;
        List<String> echoes = List.of();
        String partnerWord = shapeWord(shape);
        String partnerTraits = null;
        if (partner != null) {
            payload = character(partner);
            shape = specialty(partner);
            partnerWord = PerkEpithet.of(partner);
            echoes = partner.isAlloy() && partner.getAlloyParents() != null
                    ? parentList(partner.getAlloyParents())
                    : List.of(partner.getId());
            partnerTraits = partner.getTraitName();
        }

        String overlay;
        String overlayEffect;
        if (second != null) {
            overlay = second.getDisplayName();
            overlayEffect = "then " + second.getDisplayName() + " erupts on the same target.";
        } else if (catalyst != null) {
            overlay = catalyst.getDisplayName() + " " + catalyst.getUltimate().getDisplayName();
            overlayEffect = "then the " + catalyst.getDisplayName() + " sigil detonates as a miniature "
                    + catalyst.getUltimate().getDisplayName() + ".";
        } else {
            overlay = partnerWord + " Ascension";
            overlayEffect = "then " + shapeEffect(shape).toLowerCase(Locale.ROOT)
                    + (partnerTraits != null ? " Every foe it reaches also suffers " + partnerTraits + "." : "");
        }

        String joiner = (second != null) ? " ⨯ " : " · ";
        String name = first.getDisplayName() + joiner + overlay;
        String prefix = (tier == ForgeTier.MYTHIC) ? "Mythic ascension of " : "Ascended ";
        String description = prefix + first.getDisplayName() + ": " + lowerFirst(first.getDescription())
                + " " + capitalize(overlayEffect);
        String accent = (catalyst != null) ? catalyst.getColorHex()
                : (second != null) ? second.getColorHex() : first.getAccentHex();
        if (partner != null) accent = partner.getColorHex();
        return new SignatureArt(name, tier, first, second, catalyst, payload, shape,
                first.getColorHex(), accent, description, id, echoes);
    }

    /** What a mineral is: its leading essence in identity order. */
    @Nonnull
    static TraitAffinity character(@Nonnull TinkerMaterial material) {
        List<TraitAffinity> ranked = ranked(material);
        return ranked.isEmpty() ? TraitAffinity.PRIMAL : ranked.get(0);
    }

    /** What sets a mineral apart: its most specific essence, the last one in identity order. */
    @Nonnull
    static TraitAffinity specialty(@Nonnull TinkerMaterial material) {
        List<TraitAffinity> ranked = ranked(material);
        return ranked.isEmpty() ? TraitAffinity.PRIMAL : ranked.get(ranked.size() - 1);
    }

    @Nonnull
    private static List<TraitAffinity> ranked(@Nonnull TinkerMaterial material) {
        Set<TraitAffinity> essences = TraitAffinity.of(material);
        List<TraitAffinity> out = new ArrayList<>();
        for (TraitAffinity candidate : IDENTITY_ORDER) {
            if (essences.contains(candidate)) out.add(candidate);
        }
        return out;
    }

    @Nonnull
    private static List<String> parentList(@Nonnull String parents) {
        List<String> out = new ArrayList<>();
        for (String raw : parents.split(",")) {
            String parentId = raw.trim().toLowerCase(Locale.ROOT);
            if (!parentId.isEmpty() && !out.contains(parentId)) out.add(parentId);
        }
        return out;
    }

    @Nonnull
    private static SignatureArt legendary(@Nonnull LegendaryArt art, @Nonnull String id) {
        return new SignatureArt(art.getDisplayName(), ForgeTier.LEGENDARY, art, null, null,
                TraitAffinity.PRIMAL, TraitAffinity.PRIMAL, art.getColorHex(), art.getAccentHex(),
                art.getDescription(), id, List.of());
    }

    /** The curated legendary recipe this exact pair forges, if any. */
    @Nullable
    private static LegendaryArt curatedRecipe(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        for (LegendaryArt art : LegendaryArt.values()) {
            String[] parents = RECIPES.get(art);
            if (parents == null) continue;
            String a = first.getId().toLowerCase(Locale.ROOT);
            String b = second.getId().toLowerCase(Locale.ROOT);
            if ((parents[0].equals(a) && parents[1].equals(b)) || (parents[0].equals(b) && parents[1].equals(a))) {
                return art;
            }
        }
        return null;
    }

    /** Parents of the 16 curated recipes, mirroring {@link AlloyRegistry}'s defaults. */
    private static final Map<LegendaryArt, String[]> RECIPES = Map.ofEntries(
            Map.entry(LegendaryArt.BELL_OF_THE_FIRST_AGE, new String[]{"mvtink_copper", "mvtink_tin"}),
            Map.entry(LegendaryArt.THUNDERCHAIN_CONDUIT, new String[]{"mvtink_gold", "mvtink_silver"}),
            Map.entry(LegendaryArt.THERMAL_BASTION, new String[]{"mvtink_iron", "mvtink_nickel"}),
            Map.entry(LegendaryArt.INSATIABLE_FRENZY, new String[]{"mvtink_cobalt", "mvtink_ardite"}),
            Map.entry(LegendaryArt.MIDAS_BLOOM, new String[]{"mvtink_gold", "mvtink_copper"}),
            Map.entry(LegendaryArt.CONSTELLATION_FALL, new String[]{"mvtink_pyrite", "mvtink_astralite"}),
            Map.entry(LegendaryArt.ABYSSAL_REND, new String[]{"mvtink_tungsten", "mvtink_voidstone"}),
            Map.entry(LegendaryArt.HELLFORGE_ERUPTION, new String[]{"mvtink_steel", "mvtink_netherite"}),
            Map.entry(LegendaryArt.RESONANCE_CASCADE, new String[]{"mvtink_quartz", "mvtink_amethyst"}),
            Map.entry(LegendaryArt.UMBRAL_EXECUTION, new String[]{"mvtink_platinum", "mvtink_obsidianite"}),
            Map.entry(LegendaryArt.PHASE_GATE, new String[]{"mvtink_redstone", "mvtink_enderite"}),
            Map.entry(LegendaryArt.UNBREAKABLE_AEGIS, new String[]{"mvtink_adamantium", "mvtink_titanium"}),
            Map.entry(LegendaryArt.CHAIN_COMBUSTION, new String[]{"mvtink_bismuth", "mvtink_fire_opal"}),
            Map.entry(LegendaryArt.SILVER_BLIZZARD, new String[]{"mvtink_silver", "mvtink_cryolite"}),
            Map.entry(LegendaryArt.CRIMSON_FEAST, new String[]{"mvtink_gold", "mvtink_sanguinite"}),
            Map.entry(LegendaryArt.GRAVITY_COLLAPSE, new String[]{"mvtink_netherite", "mvtink_celestine"}));

    // ==========================================
    // FUSION GRAMMAR
    // ==========================================

    /** Adjective of the payload essence: what the fusion does. */
    @Nonnull
    public static String payloadWord(@Nonnull TraitAffinity essence) {
        return switch (essence) {
            case INFERNAL -> "Searing";
            case VOID -> "Warping";
            case PRIMAL -> "Feral";
            case TEMPERED -> "Hardened";
            case RADIANT -> "Blinding";
            case RESONANT -> "Echoing";
            case VOLATILE -> "Volatile";
            case TERRAIN -> "Earthen";
            case SWIFT -> "Galeborn";
            case BRUTAL -> "Brutal";
            case BULWARK -> "Stalwart";
            case ASCENDANT -> "Ascendant";
        };
    }

    /** Noun of the shape essence: what the fusion looks like. */
    @Nonnull
    public static String shapeWord(@Nonnull TraitAffinity essence) {
        return switch (essence) {
            case INFERNAL -> "Pyre";
            case VOID -> "Rift";
            case PRIMAL -> "Fang";
            case TEMPERED -> "Anvil";
            case RADIANT -> "Halo";
            case RESONANT -> "Chime";
            case VOLATILE -> "Flare";
            case TERRAIN -> "Spire";
            case SWIFT -> "Gale";
            case BRUTAL -> "Fault";
            case BULWARK -> "Ward";
            case ASCENDANT -> "Ascent";
        };
    }

    /** Sentence fragment describing the payload. */
    @Nonnull
    public static String payloadEffect(@Nonnull TraitAffinity essence) {
        return switch (essence) {
            case INFERNAL -> "sets every foe it reaches ablaze for 5s,";
            case VOID -> "drags every foe it reaches into its centre,";
            case PRIMAL -> "lands a raw +25% impact on every foe it reaches,";
            case TEMPERED -> "cracks the guard of every foe it reaches into Weakness II,";
            case RADIANT -> "blinds and marks every foe it reaches,";
            case RESONANT -> "rings a second, softer pulse through every foe it reaches,";
            case VOLATILE -> "blasts every foe it reaches backwards in flame,";
            case TERRAIN -> "roots every foe it reaches in Slowness III,";
            case SWIFT -> "hurls the wielder into Speed II and Haste II,";
            case BRUTAL -> "hurls every foe it reaches away for +20% damage,";
            case BULWARK -> "wraps the wielder in Absorption and Resistance,";
            case ASCENDANT -> "mends the wielder's wounds,";
        };
    }

    /** Sentence describing the geometry. */
    @Nonnull
    public static String shapeEffect(@Nonnull TraitAffinity essence) {
        return switch (essence) {
            case INFERNAL -> "Rises as a ring of pyre-fire around the target.";
            case VOID -> "Opens as a rift that implodes on the target.";
            case PRIMAL -> "Tears four claw-marks across the target.";
            case TEMPERED -> "Falls as a phantom anvil on the target.";
            case RADIANT -> "Descends as a halo of light around the target.";
            case RESONANT -> "Rings out in three expanding chimes.";
            case VOLATILE -> "Bursts as a radial flare around the target.";
            case TERRAIN -> "Juts out of the ground as a circle of stone spires.";
            case SWIFT -> "Spins up as a gale helix around the target.";
            case BRUTAL -> "Splits the ground in a star of faults.";
            case BULWARK -> "Closes as a hexagonal ward around the wielder.";
            case ASCENDANT -> "Climbs as a spiral of undying light.";
        };
    }

    /** Accent colour of each shape, so the second half of a fusion is visibly its own. */
    @Nonnull
    private static String accentOf(@Nonnull TraitAffinity shape) {
        return switch (shape) {
            case INFERNAL -> "#FF6B35";
            case VOID -> "#7B4FBF";
            case PRIMAL -> "#2ECC71";
            case TEMPERED -> "#95A5A6";
            case RADIANT -> "#F1C40F";
            case RESONANT -> "#55FFFF";
            case VOLATILE -> "#FF4C4C";
            case TERRAIN -> "#B9770E";
            case SWIFT -> "#E0FFFF";
            case BRUTAL -> "#8B0000";
            case BULWARK -> "#5DADE2";
            case ASCENDANT -> "#D2B4DE";
        };
    }

    // ==========================================
    // PRESENTATION
    // ==========================================

    @Nonnull
    public Color color() {
        return ForgeTier.parse(colorHex);
    }

    @Nonnull
    public Color accent() {
        return ForgeTier.parse(accentHex);
    }

    /** The art's name in its own colours. */
    @Nonnull
    public String miniName() {
        return "<gradient:" + colorHex + ":" + accentHex + "><b>" + name + "</b></gradient>";
    }

    /** One-line summary of how often and how far the art reaches, e.g. {@code 20% on hit · 10s cooldown}. */
    @Nonnull
    public String procLine() {
        int chance = (int) Math.round(tier.getArtChance() * SignatureArtEngine.configuredChanceMultiplier() * 100.0);
        int seconds = Math.round(tier.getArtCooldownTicks() / 20.0f);
        return Math.min(100, chance) + "% on hit · " + seconds + "s cooldown · "
                + String.format(Locale.US, "%.0f", tier.getArtRadius()) + "-block reach";
    }

    // ==========================================
    // HELPERS
    // ==========================================

    @Nonnull
    private static List<TraitAffinity> ordered(@Nonnull Set<TraitAffinity> essences) {
        return new ArrayList<>(essences);
    }

    @Nonnull
    private static List<TraitAffinity> parseOrdered(@Nonnull String raw) {
        List<TraitAffinity> out = new ArrayList<>();
        for (String token : raw.split(",")) {
            String name = token.trim().toUpperCase(Locale.ROOT);
            if (name.isEmpty()) continue;
            try {
                out.add(TraitAffinity.valueOf(name));
            } catch (IllegalArgumentException ignored) {
                // Unknown essence: skip it rather than lose the whole art.
            }
        }
        return out;
    }

    @Nonnull
    private static String blend(@Nonnull String hex1, @Nonnull String hex2) {
        try {
            int c1 = Integer.parseInt(hex1.replace("#", ""), 16);
            int c2 = Integer.parseInt(hex2.replace("#", ""), 16);
            int r = (((c1 >> 16) & 0xFF) + ((c2 >> 16) & 0xFF)) / 2;
            int g = (((c1 >> 8) & 0xFF) + ((c2 >> 8) & 0xFF)) / 2;
            int b = ((c1 & 0xFF) + (c2 & 0xFF)) / 2;
            return String.format("#%02X%02X%02X", r, g, b);
        } catch (NumberFormatException e) {
            return "#D4AF37";
        }
    }

    @Nonnull
    private static String lowerFirst(@Nonnull String text) {
        return text.isEmpty() ? text : Character.toLowerCase(text.charAt(0)) + text.substring(1);
    }

    @Nonnull
    private static String capitalize(@Nonnull String text) {
        return text.isEmpty() ? text : Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
