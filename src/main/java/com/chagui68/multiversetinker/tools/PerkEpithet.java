package com.chagui68.multiversetinker.tools;

import com.chagui68.multiversetinker.materials.TinkerMaterial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Forge epithet of every mineral: the word that names a piece's perk after the minerals it was made of.
 *
 * <p>Equipment used to take its perk name from the <b>essence</b> of its head part alone, so two items
 * of the same type built from different minerals could print the exact same perk — a Borax / Amethyst /
 * Gold sword and a Borax / Amethyst / Diamond sword both read as {@code Primal Sweeping Cleave},
 * because Amethyst and Diamond happen to teach the same essences. The perk name is now the epithet of
 * <b>every</b> part's mineral, in forge order, ahead of the signature mechanic:</p>
 *
 * <pre>Borax + Amethyst + Gold    ->  Fluxforged Resonant Auric Sweeping Cleave
 * Borax + Amethyst + Diamond ->  Fluxforged Resonant Adamant Sweeping Cleave</pre>
 *
 * <p>Every entry below is a single word and no two entries repeat, so a sequence of epithets identifies
 * exactly one mineral sequence and two different builds can never share a perk name. Composites and
 * primes forged at runtime borrow their parents' epithets (see {@link #resolve}), and anything without
 * a curated word falls back to its trait so the name is never empty.</p>
 */
public final class PerkEpithet {

    /** How deep a runtime-forged alloy may borrow from its parents before falling back. */
    private static final int MAX_DEPTH = 3;

    /**
     * The forged epithet of every registered mineral, alloy and catalyst.
     *
     * <p>Keys are full material ids, values are the adjective printed ahead of the perk's mechanic.
     * {@link com.chagui68.multiversetinker.PerksCoverEveryMaterialTest} checks that no entry is
     * missing, repeated or more than one word, so the names stay unique as the registry grows.</p>
     */
    private static final Map<String, String> EPITHETS = Map.ofEntries(
            Map.entry("mvtink_adamant_steel", "Unyielding"),
            Map.entry("mvtink_adamantium", "Adamantine"),
            Map.entry("mvtink_adamita", "Adamitic"),
            Map.entry("mvtink_aether_pearl", "Pearlescent"),
            Map.entry("mvtink_aetherium", "Aetheric"),
            Map.entry("mvtink_amber", "Amberbound"),
            Map.entry("mvtink_amethyst", "Resonant"),
            Map.entry("mvtink_amethyst_cluster_gem", "Geodic"),
            Map.entry("mvtink_ancient_slag", "Ancestral"),
            Map.entry("mvtink_aquamarine", "Tidal"),
            Map.entry("mvtink_ardite", "Ardent"),
            Map.entry("mvtink_astral_brass", "Astral"),
            Map.entry("mvtink_astralite", "Starlit"),
            Map.entry("mvtink_bauxite", "Aluminous"),
            Map.entry("mvtink_beryllium", "Featherlight"),
            Map.entry("mvtink_bismuth", "Iridescent"),
            Map.entry("mvtink_blackstone_pyrite", "Basaltic"),
            Map.entry("mvtink_blazesteel_ore", "Blazeforged"),
            Map.entry("mvtink_borax", "Fluxforged"),
            Map.entry("mvtink_bronze", "Burnished"),
            Map.entry("mvtink_calcite_gem", "Calcified"),
            Map.entry("mvtink_catalyst_amethyst_cluster", "Clusterborn"),
            Map.entry("mvtink_catalyst_ancient_debris", "Deepforged"),
            Map.entry("mvtink_catalyst_blue_ice", "Frozen"),
            Map.entry("mvtink_catalyst_dragon_breath", "Drakebreathed"),
            Map.entry("mvtink_catalyst_echo_shard", "Echoing"),
            Map.entry("mvtink_catalyst_end_crystal", "Crystalline"),
            Map.entry("mvtink_catalyst_heart_of_the_sea", "Abyssal"),
            Map.entry("mvtink_catalyst_nether_star", "Starforged"),
            Map.entry("mvtink_catalyst_packed_ice", "Glaciated"),
            Map.entry("mvtink_catalyst_prismarine_crystals", "Pristine"),
            Map.entry("mvtink_catalyst_respawn_anchor", "Anchored"),
            Map.entry("mvtink_catalyst_totem_of_undying", "Undying"),
            Map.entry("mvtink_celestine", "Celestial"),
            Map.entry("mvtink_chorus_crystal", "Chorusing"),
            Map.entry("mvtink_chromite", "Chromed"),
            Map.entry("mvtink_chrono_crystal", "Chronal"),
            Map.entry("mvtink_cinder_steel", "Hellfire"),
            Map.entry("mvtink_cinderite", "Emberwrought"),
            Map.entry("mvtink_cinnabar", "Vermilion"),
            Map.entry("mvtink_coal", "Kindled"),
            Map.entry("mvtink_cobalt", "Lightfooted"),
            Map.entry("mvtink_copper", "Conductive"),
            Map.entry("mvtink_cosmic_netherite", "Cosmic"),
            Map.entry("mvtink_cosmium", "Stellar"),
            Map.entry("mvtink_crimson_gold", "Crimsoned"),
            Map.entry("mvtink_crimson_quartz", "Serrated"),
            Map.entry("mvtink_cryolite", "Cryotic"),
            Map.entry("mvtink_cursed_brimstone", "Accursed"),
            Map.entry("mvtink_diamond", "Adamant"),
            Map.entry("mvtink_dragon_shard", "Drakeforged"),
            Map.entry("mvtink_eclipse_gem", "Eclipsed"),
            Map.entry("mvtink_electrum", "Voltaic"),
            Map.entry("mvtink_emerald", "Mercantile"),
            Map.entry("mvtink_end_crystal_shard", "Resurrective"),
            Map.entry("mvtink_ender_brass", "Phasing"),
            Map.entry("mvtink_enderite", "Riftborn"),
            Map.entry("mvtink_fire_opal", "Emberlit"),
            Map.entry("mvtink_flint", "Jagged"),
            Map.entry("mvtink_fluorite", "Luminescent"),
            Map.entry("mvtink_galena", "Leaden"),
            Map.entry("mvtink_ghast_tear_shard", "Weeping"),
            Map.entry("mvtink_glacial_silver", "Glacial"),
            Map.entry("mvtink_glowstone_gem", "Glowing"),
            Map.entry("mvtink_gold", "Auric"),
            Map.entry("mvtink_graphite", "Lubricious"),
            Map.entry("mvtink_gravitite", "Gravitic"),
            Map.entry("mvtink_gypsum", "Alabaster"),
            Map.entry("mvtink_hellfire_bismuth", "Combustive"),
            Map.entry("mvtink_helliron", "Scorched"),
            Map.entry("mvtink_ignis_ferrum", "Whitehot"),
            Map.entry("mvtink_infernal_obsidian", "Searing"),
            Map.entry("mvtink_infused_quartz", "Infused"),
            Map.entry("mvtink_invar", "Invariant"),
            Map.entry("mvtink_iron", "Ironclad"),
            Map.entry("mvtink_jade", "Serpentine"),
            Map.entry("mvtink_kaolinite", "Porcelainic"),
            Map.entry("mvtink_lapis", "Enchanted"),
            Map.entry("mvtink_lapis_matrix", "Latticebound"),
            Map.entry("mvtink_magma_brimstone", "Magmatic"),
            Map.entry("mvtink_magmacite", "Slagborn"),
            Map.entry("mvtink_magnetite", "Magnetic"),
            Map.entry("mvtink_malachite", "Verdant"),
            Map.entry("mvtink_manyullyn", "Insatiable"),
            Map.entry("mvtink_nebulite", "Nebulous"),
            Map.entry("mvtink_nether_bismuth", "Chromatic"),
            Map.entry("mvtink_nether_tungsten", "Netherwrought"),
            Map.entry("mvtink_netherite", "Netherforged"),
            Map.entry("mvtink_netherite_shard", "Scrapforged"),
            Map.entry("mvtink_nickel", "Nickelated"),
            Map.entry("mvtink_null_shard", "Nullifying"),
            Map.entry("mvtink_obsidian", "Obsidianbound"),
            Map.entry("mvtink_obsidianite", "Vitreous"),
            Map.entry("mvtink_opal", "Opalescent"),
            Map.entry("mvtink_pearl_core", "Teleportive"),
            Map.entry("mvtink_phantomite", "Phantasmal"),
            Map.entry("mvtink_platinum", "Platinous"),
            Map.entry("mvtink_prismarine", "Tidebound"),
            Map.entry("mvtink_prismatic_quartz", "Prismatic"),
            Map.entry("mvtink_pyrite", "Gilded"),
            Map.entry("mvtink_pyrophore", "Firebearing"),
            Map.entry("mvtink_quartz", "Quartzhewn"),
            Map.entry("mvtink_redstone", "Charged"),
            Map.entry("mvtink_resonite", "Resounding"),
            Map.entry("mvtink_rose_gold", "Blushing"),
            Map.entry("mvtink_ruby", "Rubicund"),
            Map.entry("mvtink_sanguine_gold", "Sanguine"),
            Map.entry("mvtink_sanguinite", "Bloodforged"),
            Map.entry("mvtink_sapphire", "Sapphiric"),
            Map.entry("mvtink_shadow_platinum", "Umbral"),
            Map.entry("mvtink_shadowgem", "Shadowed"),
            Map.entry("mvtink_shulkerite", "Shulkerbound"),
            Map.entry("mvtink_silver", "Silvered"),
            Map.entry("mvtink_singularite", "Singular"),
            Map.entry("mvtink_soulsand_crystal", "Soulbound"),
            Map.entry("mvtink_spatial_platinum", "Spatial"),
            Map.entry("mvtink_starlight_silver", "Moonlit"),
            Map.entry("mvtink_steel", "Steelclad"),
            Map.entry("mvtink_stibnite", "Antimonic"),
            Map.entry("mvtink_sulfur", "Sulfurous"),
            Map.entry("mvtink_talc", "Powdery"),
            Map.entry("mvtink_tesseract_crystal", "Tesseractic"),
            Map.entry("mvtink_tin", "Malleable"),
            Map.entry("mvtink_titanium", "Titanous"),
            Map.entry("mvtink_topaz", "Cleaving"),
            Map.entry("mvtink_tourmaline", "Piezonic"),
            Map.entry("mvtink_tungsten", "Tungstenous"),
            Map.entry("mvtink_void_damascus", "Damascened"),
            Map.entry("mvtink_void_pyrite", "Entropic"),
            Map.entry("mvtink_void_titanium", "Voidbound"),
            Map.entry("mvtink_voidstone", "Warping"),
            Map.entry("mvtink_volcanic_ash", "Ashbound"),
            Map.entry("mvtink_warped_emerald", "Warped"),
            Map.entry("mvtink_warped_quartz", "Twisted"),
            Map.entry("mvtink_weeping_shard", "Tearbound"),
            Map.entry("mvtink_witherite", "Withering"),
            Map.entry("mvtink_zero_point", "Zeroed"),
            Map.entry("mvtink_zinc", "Galvanized"),
            Map.entry("mvtink_zircon", "Tectonic"));

    /** Resolved epithets, so a runtime-forged prime does not re-walk its parents on every forge. */
    private static final Map<String, String> RESOLVED = new ConcurrentHashMap<>();

    private PerkEpithet() {
    }

    /**
     * Epithet of a mineral: the word this mineral contributes to a forged piece's perk name.
     *
     * @return a single word, or an empty string when the material carries no id at all
     */
    @Nonnull
    public static String of(@Nullable TinkerMaterial material) {
        if (material == null) return "";
        String id = material.getId();
        if (id == null || id.isBlank()) return fallback(material);
        String key = id.toLowerCase(Locale.ROOT);
        String cached = RESOLVED.get(key);
        if (cached != null) return cached;

        String epithet = resolve(material, key, 0);
        if (epithet.isEmpty()) epithet = fallback(material);
        RESOLVED.put(key, epithet);
        return epithet;
    }

    /** How many minerals carry a hand-picked epithet (every registered one does). */
    public static int curatedCount() {
        return EPITHETS.size();
    }

    /** The curated epithets, for the reference page and the coverage test. */
    @Nonnull
    public static Map<String, String> curated() {
        return EPITHETS;
    }

    /** Forgets the runtime-forged resolutions; the plugin calls this on reload. */
    public static void clearCache() {
        RESOLVED.clear();
    }

    /**
     * Curated word first, then the parents of a runtime-forged alloy, so a composite reads as its two
     * minerals ({@code Malleable-Galvanized}) and a prime as the legendary it was built on
     * ({@code Prime Auric}).
     */
    @Nonnull
    private static String resolve(@Nonnull TinkerMaterial material, @Nonnull String id, int depth) {
        String curated = EPITHETS.get(id);
        if (curated != null) return curated;
        if (depth >= MAX_DEPTH) return fallback(material);

        String parents = material.getAlloyParents();
        if (parents == null || !parents.contains(",")) return fallback(material);

        String[] split = parents.split(",");
        String left = parentEpithet(split[0], material, depth);
        String right = parentEpithet(split[1], material, depth);
        if (left.isEmpty() || right.isEmpty()) return fallback(material);

        return id.contains("_prime_") ? "Prime " + left : left + "-" + right;
    }

    /**
     * Epithet of one parent of a runtime-forged alloy. Parents are named by id and are normally
     * registered minerals, so no live registry lookup is needed here.
     */
    @Nonnull
    private static String parentEpithet(@Nullable String rawParent, @Nonnull TinkerMaterial child, int depth) {
        if (rawParent == null) return "";
        String parentId = rawParent.trim().toLowerCase(Locale.ROOT);
        if (parentId.isEmpty()) return "";
        return resolve(child, parentId, depth + 1);
    }

    /**
     * Last resort so a name is never empty: the first word of the mineral's trait, then of its name.
     *
     * <p>A material that reaches this path is one the registry grew without an epithet; the coverage
     * test fails in that case, because the fallback could repeat a curated word.</p>
     */
    @Nonnull
    private static String fallback(@Nonnull TinkerMaterial material) {
        String trait = material.getTraitName();
        if (trait != null && !trait.isBlank()) return firstWord(trait);
        String name = material.getName();
        if (name != null && !name.isBlank()) return firstWord(name);
        return "Unnamed";
    }

    @Nonnull
    private static String firstWord(@Nonnull String text) {
        String trimmed = text.trim();
        int space = trimmed.indexOf(' ');
        String word = (space > 0) ? trimmed.substring(0, space) : trimmed;
        return word.isEmpty() ? trimmed : Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
