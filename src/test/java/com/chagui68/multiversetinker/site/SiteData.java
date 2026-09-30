package com.chagui68.multiversetinker.site;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.PerkEpithet;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import com.chagui68.multiversetinker.wiki.WikiPages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Publishes the registries as the JSON behind the interactive alloy site.
 *
 * <p>The site is a static page ({@code docs/}) that lets a player browse every material and preview
 * what two of them would forge. It reads one file — {@code docs/data/alloys.json} — and that file is
 * rendered here from the live registries, so the page can never advertise a material, a trait or an
 * essence the plugin does not have. {@link com.chagui68.multiversetinker.SiteDataTest} re-renders it
 * and fails when the committed file has drifted.</p>
 *
 * <p>Two things the browser cannot ask the plugin for are baked into the file instead:</p>
 * <ul>
 *   <li>the <b>golden pairs</b>, a handful of pairs the <em>real</em> crucible has already fused here,
 *       which the page checks its own port of the mixing math against;</li>
 *   <li>the <b>essence inheritance order</b>, so a fused pair keeps the identity the plugin would
 *       give it rather than the order the browser happens to see.</li>
 * </ul>
 */
public final class SiteData {

    /** Where the page looks for its data, relative to the repository root. */
    public static final String PATH = "docs/data/alloys.json";

    /** Set to {@code true} to rewrite the file instead of only verifying it. */
    public static final String WRITE_PROPERTY = "mvtink.site.write";

    /** Id prefixes of everything the crucible forges at runtime, which is not shipped data. */
    private static final String COMPOSITE_PREFIX = "mvtink_alloy_";
    private static final String PRIME_PREFIX = "mvtink_prime_";

    private final MaterialRegistry materials;
    private final AlloyRegistry alloys;

    /**
     * The materials the site publishes, frozen when this object is built.
     *
     * <p>What ships is what gets published: a composite or a prime only exists on a server where a
     * player already forged it, so listing one would advertise a material nobody can find. Forging is
     * also what makes the number of materials grow, and a page that ranked its own output would drift
     * from one render to the next — the trap the best-build tables already fell into once.</p>
     */
    private final List<TinkerMaterial> shipped;

    public SiteData(@Nonnull MaterialRegistry materials, @Nonnull AlloyRegistry alloys) {
        this.materials = materials;
        this.alloys = alloys;
        this.shipped = shippingMaterials();
    }

    // ==========================================
    // THE FILE
    // ==========================================

    /** The whole {@code alloys.json}, as it is committed. */
    @Nonnull
    public String json() {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("schema", 1);
        root.put("counts", counts());
        root.put("essenceOrder", inheritanceOrder());
        root.put("essences", essences());
        root.put("materials", shipped.stream().map(this::material).toList());
        root.put("recipes", recipes());
        root.put("equipment", equipment());
        root.put("golden", goldenPairs());
        root.put("goldenBuilds", goldenBuilds());
        return Json.write(root);
    }

    /** Rewrites the committed file in place. */
    public void write(@Nonnull Path root) {
        Path file = root.resolve(PATH);
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, json(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
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

    /** How many materials the site publishes; the drift test uses it to prove it saw a full catalog. */
    public int publishedMaterials() {
        return shipped.size();
    }

    // ==========================================
    // MATERIALS
    // ==========================================

    private Map<String, Object> material(@Nonnull TinkerMaterial material) {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("id", material.getId());
        out.put("name", material.getName());
        out.put("kind", kindOf(material));
        out.put("origin", material.getOrigin().name());
        out.put("source", material.getOrigin().getSourceBlockName());
        out.put("rarity", material.getRarity().name());
        out.put("type", material.getType().name());
        out.put("color", material.getColorHex());
        out.put("description", material.getDescription());
        out.put("melt", material.getMeltingDurationTicks());

        // The three numbers a part rolls from the material.
        out.put("durability", material.getDurabilityBonus());
        out.put("speed", material.getMiningSpeed());
        out.put("damage", material.getAttackDamageBonus());

        // The trait and what it does per equipment channel. The tool channel is the trait description
        // itself — the plugin only overrides the weapon and armor wording — so it is not repeated here.
        out.put("trait", material.getTraitName());
        out.put("traitDesc", material.getTraitDescription());
        out.put("weapon", material.getWeaponTraitDescription());
        out.put("armor", material.getArmorTraitDescription());

        out.put("essences", essenceIds(material));
        out.put("epithet", PerkEpithet.of(material));
        out.put("mixable", AlloyRegistry.isMixable(material));
        out.put("parents", parents(material));

        VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(material.getId());
        if (catalyst != null) {
            out.put("catalyst", catalyst(catalyst));
        }
        return out;
    }

    /** A material's category as the page groups it: a mineral, one of the 16 recipes, or a catalyst. */
    @Nonnull
    private static String kindOf(@Nonnull TinkerMaterial material) {
        if (AlloyRegistry.isLegendary(material)) return "legendary";
        if (AlloyRegistry.isCatalyst(material)) return "catalyst";
        return "mineral";
    }

    /** The two parents of a legendary recipe, so the page can show the recipe and link to them. */
    @Nullable
    private static List<String> parents(@Nonnull TinkerMaterial material) {
        if (!AlloyRegistry.isLegendary(material)) return null;
        String parents = material.getAlloyParents();
        if (parents == null) return null;
        List<String> ids = new ArrayList<>();
        for (String id : parents.split(",")) {
            if (!id.isBlank()) ids.add(id.trim());
        }
        return ids.size() == 2 ? ids : null;
    }

    /** What a prime forged with this catalyst unleashes, and the state its armor answers with. */
    @Nonnull
    private static Map<String, Object> catalyst(@Nonnull VanillaCatalyst catalyst) {
        PrimeUltimate ultimate = catalyst.getUltimate();
        PrimeArmorState state = catalyst.getArmorState();

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("item", catalyst.getItem().name());
        out.put("ultimate", ultimate.getDisplayName());
        out.put("ultimateDesc", ultimate.getDescription());
        out.put("ultimateColor", ultimate.getColorHex());
        out.put("armorState", state.getDisplayName());
        out.put("armorStateDesc", state.getDescription());
        out.put("primes", catalyst.compatiblePrimes());
        return out;
    }

    /** The 16 curated recipes, so a pair that is a recipe is never reported as a plain composite. */
    @Nonnull
    private List<Map<String, Object>> recipes() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (TinkerAlloy alloy : sortedAlloys()) {
            Map<String, Object> recipe = new LinkedHashMap<>();
            recipe.put("id", alloy.id());
            recipe.put("a", alloy.mat1Id());
            recipe.put("b", alloy.mat2Id());
            out.add(recipe);
        }
        return out;
    }

    /** The curated recipes in id order, ignoring anything a previous render forged. */
    @Nonnull
    private List<TinkerAlloy> sortedAlloys() {
        List<TinkerAlloy> curated = new ArrayList<>();
        for (TinkerAlloy alloy : alloys.getAllAlloys()) {
            if (AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) curated.add(alloy);
        }
        curated.sort(Comparator.comparing(TinkerAlloy::id));
        return curated;
    }

    // ==========================================
    // ESSENCES
    // ==========================================

    private List<Map<String, Object>> essences() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (TraitAffinity essence : TraitAffinity.values()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("id", essence.name());
            entry.put("name", essence.getDisplayName());
            entry.put("color", hex(essence));
            entry.put("weapon", essence.getWeaponEffect());
            entry.put("tool", essence.getToolEffect());
            entry.put("armor", essence.getArmorEffect());
            // The curated Spanish one-liner the wiki prints, so the Spanish page does not have to
            // describe the same essence a second time.
            entry.put("es", WikiPages.shortHand(essence, true));
            out.add(entry);
        }
        return out;
    }

    /** The order a fused pair inherits its essences in, read from the plugin's own constant. */
    @Nonnull
    private static List<String> inheritanceOrder() {
        List<String> order = new ArrayList<>();
        for (TraitAffinity essence : TraitAffinity.INHERITANCE_PRIORITY) order.add(essence.name());
        return order;
    }

    @Nonnull
    private static List<String> essenceIds(@Nullable TinkerMaterial material) {
        List<String> ids = new ArrayList<>();
        if (material == null) return ids;
        for (TraitAffinity essence : TraitAffinity.of(material)) ids.add(essence.name());
        return ids;
    }

    @Nonnull
    private static String hex(@Nonnull TraitAffinity essence) {
        return String.format("#%06X", essence.getColor().value() & 0xFFFFFF);
    }

    // ==========================================
    // GOLDEN PAIRS
    // ==========================================

    /**
     * Pairs the page has to reproduce, each one written down for a reason.
     *
     * <p>They cover every branch of the mixing rules: a curated recipe that shadows a mineral pair, a
     * curated recipe the crucible accepts even though neither parent blends freely, a plain composite,
     * a catalyst fusion (which contributes no mass to the prime it catalyses), a legendary fused with a
     * mineral and with another legendary, the same pair given in both orders, and two pairs the
     * crucible refuses.</p>
     */
    private static final List<Pair> GOLDEN = List.of(
            new Pair("mvtink_copper", "mvtink_tin", "curated recipe that shadows a mineral pair"),
            new Pair("mvtink_tin", "mvtink_copper", "the same recipe with the parents the other way round"),
            new Pair("mvtink_gold", "mvtink_silver", "curated recipe, both parents freely blendable"),
            new Pair("mvtink_steel", "mvtink_netherite", "curated recipe whose netherite parent never blends freely"),
            new Pair("mvtink_netherite", "mvtink_celestine", "the other curated netherite recipe"),
            new Pair("mvtink_cobalt", "mvtink_ardite", "curated recipe from two Nether minerals"),
            new Pair("mvtink_tin", "mvtink_zinc", "plain composite from two Overworld metals"),
            new Pair("mvtink_amber", "mvtink_ruby", "plain composite from two precious gems"),
            new Pair("mvtink_ardite", "mvtink_voidstone", "plain composite from two End minerals"),
            new Pair("mvtink_bronze", "mvtink_catalyst_nether_star", "prime catalysed by the Nether Star"),
            new Pair("mvtink_catalyst_nether_star", "mvtink_bronze", "the same prime, catalyst first"),
            new Pair("mvtink_void_damascus", "mvtink_catalyst_echo_shard", "prime whose catalyst grants Event Horizon"),
            new Pair("mvtink_cosmic_netherite", "mvtink_catalyst_blue_ice", "prime catalysed by Blue Ice"),
            new Pair("mvtink_manyullyn", "mvtink_cobalt", "prime of a legendary with a plain mineral"),
            new Pair("mvtink_glacial_silver", "mvtink_quartz", "prime of a legendary with another mineral"),
            new Pair("mvtink_cosmic_netherite", "mvtink_adamant_steel", "prime of two legendaries"),
            new Pair("mvtink_bronze", "mvtink_manyullyn", "prime of two legendaries, both from the Nether"),
            new Pair("mvtink_catalyst_nether_star", "mvtink_catalyst_blue_ice", "two catalysts alone are refused"),
            new Pair("mvtink_netherite", "mvtink_zinc", "netherite alone is refused: it is not freely blendable"));

    /** A pair worth pinning, with the reason it is pinned, kept next to the expectation. */
    private record Pair(String a, String b, String why) {
    }

    /**
     * Forges every golden pair in a registry of its own.
     *
     * <p>Forging registers the alloy, so doing it against the live registry would push a dozen
     * materials into the catalog the page had just published. The isolated ring holds exactly the
     * shipped materials and runs the same {@link AlloyRegistry} code, which is what makes these rows
     * ground truth rather than a second implementation of the formulas.</p>
     */
    @Nonnull
    private List<Map<String, Object>> goldenPairs() {
        MaterialRegistry ring = new MaterialRegistry();
        AlloyRegistry ringAlloys = new AlloyRegistry();
        ringAlloys.registerCatalystMaterials(ring);
        ringAlloys.registerAlloysIntoMaterialRegistry(ring);

        List<Map<String, Object>> rows = new ArrayList<>();
        for (Pair pair : GOLDEN) {
            TinkerMaterial first = ring.get(pair.a());
            TinkerMaterial second = ring.get(pair.b());
            Objects.requireNonNull(first, "Golden pair parent " + pair.a() + " must be a registered material");
            Objects.requireNonNull(second, "Golden pair parent " + pair.b() + " must be a registered material");

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("a", pair.a());
            row.put("b", pair.b());
            row.put("case", pair.why());

            boolean craftable = ringAlloys.isCraftablePair(first, second);
            row.put("craftable", craftable);
            if (craftable) {
                TinkerAlloy fused = ringAlloys.findOrCreateAlloy(first, second, ring);
                row.put("name", fused.name());
                row.put("id", fused.id());
                row.put("durability", fused.durabilityBonus());
                row.put("speed", fused.miningSpeed());
                row.put("damage", fused.attackDamageBonus());
                row.put("fusion", fusionKind(fused));
                row.put("essences", essenceIds(ring.get(fused.id())));
            }
            rows.add(row);
        }
        return rows;
    }

    /** Which of the three fusions a pair produced: a curated recipe, a prime, or a plain composite. */
    @Nonnull
    private static String fusionKind(@Nonnull TinkerAlloy alloy) {
        if (AlloyRegistry.LEGENDARY_IDS.contains(alloy.id().toLowerCase(Locale.ROOT))) return "legendary";
        if (alloy.id().startsWith(PRIME_PREFIX)) return "prime";
        return "composite";
    }

    // ==========================================
    // EQUIPMENT TABLES
    // ==========================================

    /**
     * The three equipment families and the evolution ladder, read off the enums the Forge builds from.
     *
     * <p>The page's build calculator adds numbers together, and every number it adds comes from here: a
     * tier's durability and damage bonus, an armor slot's bare protection, the name the game gives each
     * part. Publishing them keeps the calculator honest for the same reason the catalog is generated — a
     * tier that gains a bonus in the plugin changes the page with it, and a slot that grows a fourth part
     * stops being described as three.</p>
     */
    @Nonnull
    private static Map<String, Object> equipment() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("tiers", tiers());
        out.put("weapons", weapons());
        out.put("tools", tools());
        out.put("armor", armor());
        return out;
    }

    @Nonnull
    private static List<Map<String, Object>> tiers() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (EvolutionTier tier : EvolutionTier.values()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", tier.name());
            row.put("ordinal", tier.ordinal());
            row.put("displayName", tier.getDisplayName());
            row.put("armorDisplayName", tier.getArmorDisplayName());
            row.put("bonusDurability", tier.getBonusDurability());
            row.put("bonusDamage", tier.getBonusDamage());
            row.put("speedMultiplier", tier.getSpeedMultiplier());
            out.add(row);
        }
        return out;
    }

    /** Every weapon the Forge assembles, with how many parts it takes and what the game calls them. */
    @Nonnull
    private static List<Map<String, Object>> weapons() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (ModularWeaponType weapon : ModularWeaponType.values()) {
            List<String> parts = new ArrayList<>();
            parts.add(weapon.getPart1Name());
            parts.add(weapon.getPart2Name());
            if (weapon.getPart3Name() != null) parts.add(weapon.getPart3Name());

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", weapon.name());
            row.put("displayName", weapon.getDisplayName());
            row.put("parts", parts);
            row.put("twoPart", weapon.isTwoPart());
            out.add(row);
        }
        return out;
    }

    /** Every tool the Forge assembles: always a head, a handle and a pommel. */
    @Nonnull
    private static List<Map<String, Object>> tools() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (ModularToolType tool : ModularToolType.values()) {
            // The deprecated sword alias is the weapon enum's business: its id is already published as a
            // weapon, and listing it twice would offer the reader the same item under two families.
            if (isDeprecated(tool)) continue;

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", tool.name());
            row.put("displayName", tool.getDisplayName());
            row.put("parts", List.of(ToolPartType.HEAD.getDisplayName(), ToolPartType.ROD.getDisplayName(),
                    ToolPartType.BINDING.getDisplayName()));
            out.add(row);
        }
        return out;
    }

    /** Every armor slot, with the protection its bare form rolls before any mineral is cast into it. */
    @Nonnull
    private static List<Map<String, Object>> armor() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (ModularArmorType slot : ModularArmorType.values()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", slot.name());
            row.put("displayName", slot.getDisplayName());
            row.put("baseDefense", slot.getBaseDefense());
            row.put("baseToughness", slot.getBaseToughness());
            row.put("baseDurability", slot.getBaseDurability());
            row.put("parts", List.of(slot.getPart1Name(), slot.getPart2Name(), slot.getPart3Name()));
            out.add(row);
        }
        return out;
    }

    /** Whether an enum constant carries {@code @Deprecated}, so a retired alias can be skipped. */
    private static boolean isDeprecated(@Nonnull Enum<?> constant) {
        try {
            return constant.getDeclaringClass().getField(constant.name()).isAnnotationPresent(Deprecated.class);
        } catch (NoSuchFieldException exception) {
            return false;
        }
    }

    // ==========================================
    // GOLDEN BUILDS
    // ==========================================

    /**
     * Builds the page has to reproduce, each one written down for a reason.
     *
     * <p>A weapon reads its damage from different parts depending on whether it takes two or three, a tool
     * scales its head with the tier, and armor rolls its protection from three different parts. These rows
     * cover each of those, at both ends of the tier ladder, so the calculator cannot quietly apply one
     * equipment family's arithmetic to another's.</p>
     */
    private static final List<Build> GOLDEN_BUILDS = List.of(
            new Build("weapon", "SWORD", "WOOD", List.of("mvtink_copper", "mvtink_gold", "mvtink_tin"),
                    "three-part weapon at the first tier, where the pommel counts for a third"),
            new Build("weapon", "BOW", "DIAMOND", List.of("mvtink_bronze", "mvtink_amethyst"),
                    "two-part weapon, where the second part counts for half and no pommel is added"),
            new Build("weapon", "SHIELD", "COPPER", List.of("mvtink_bronze", "mvtink_silver"),
                    "the other two-part weapon, whose parts differ from the bow's"),
            new Build("weapon", "SWORD", "NETHERITE", List.of("mvtink_steel", "mvtink_silver", "mvtink_celestine"),
                    "three-part weapon at the top tier, where the tier bonus dominates the parts"),
            new Build("weapon", "SPEAR", "IRON", List.of("mvtink_cosmic_netherite", "mvtink_manyullyn", "mvtink_cobalt"),
                    "three-part weapon forged from legendary alloys"),
            new Build("tool", "PICKAXE", "IRON", List.of("mvtink_iron", "mvtink_gold", "mvtink_tin"),
                    "tool, where the tier multiplies the head's mining speed"),
            new Build("tool", "AXE", "GOLD", List.of("mvtink_bronze", "mvtink_silver", "mvtink_copper"),
                    "tool whose handle adds a third of its attack damage"),
            new Build("tool", "HOE", "NETHERITE", List.of("mvtink_adamant_steel", "mvtink_glacial_silver", "mvtink_zinc"),
                    "tool at the top tier, where the speed multiplier is largest"),
            new Build("armor", "HELMET", "STONE", List.of("mvtink_copper", "mvtink_tin", "mvtink_zinc"),
                    "armor, where the plate rolls defense and the lining rolls toughness"),
            new Build("armor", "CHESTPLATE", "NETHERITE", List.of("mvtink_cosmic_netherite", "mvtink_adamant_steel", "mvtink_bronze"),
                    "armor at the top tier, where the tier raises defense and toughness at once"),
            new Build("armor", "BOOTS", "DIAMOND", List.of("mvtink_amber", "mvtink_ruby", "mvtink_quartz"),
                    "armor whose trim rolls the knockback resistance"));

    /** One build worth pinning: the equipment, its parts, its tier, and the reason it is pinned. */
    private record Build(String kind, String type, String tier, List<String> parts, String why) {
    }

    /**
     * Assembles every golden build through the Forge's own code and records the numbers it produced.
     *
     * <p>The rows are read back off the assembled item — the maximum durability it recorded, its attack
     * damage, its mining speed — so they are the plugin's output rather than a second telling of its
     * formulas. Armor is the exception: its protection rides on attribute modifiers rather than in the
     * item's data, so those three numbers come from the same {@link TinkerItemBuilder#armorStats} call the
     * piece itself was armed with.</p>
     */
    @Nonnull
    private List<Map<String, Object>> goldenBuilds() {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Build build : GOLDEN_BUILDS) {
            List<PartComposition> parts = new ArrayList<>();
            for (String id : build.parts()) {
                TinkerMaterial material = materials.get(id);
                Objects.requireNonNull(material, "Golden build part " + id + " must be a registered material");
                parts.add(PartComposition.fromMaterials(List.of(material)));
            }

            EvolutionTier tier = EvolutionTier.valueOf(build.tier());
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("kind", build.kind());
            row.put("type", build.type());
            row.put("tier", build.tier());
            row.put("parts", build.parts());
            row.put("case", build.why());

            ItemMeta meta = assemble(build, parts, tier).getItemMeta();
            Objects.requireNonNull(meta, "A forged item always has meta");
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            row.put("durability", pdc.get(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER));

            if (build.kind().equals("armor")) {
                TinkerItemBuilder.ArmorStats stats = TinkerItemBuilder.armorStats(ModularArmorType.valueOf(build.type()),
                        parts.get(0), parts.get(1), parts.get(2), tier);
                row.put("defense", stats.defense());
                row.put("toughness", stats.toughness());
                row.put("knockback", stats.knockbackResistance());
            } else {
                row.put("damage", pdc.get(TinkerKeys.TOOL_ATTACK_DAMAGE, PersistentDataType.FLOAT));
            }
            if (build.kind().equals("tool")) {
                row.put("speed", pdc.get(TinkerKeys.TOOL_MINING_SPEED, PersistentDataType.FLOAT));
            }
            rows.add(row);
        }
        return rows;
    }

    /** Runs one golden build through the Forge, exactly as the in-game GUI does. */
    @Nonnull
    private static ItemStack assemble(@Nonnull Build build, @Nonnull List<PartComposition> parts,
                                      @Nonnull EvolutionTier tier) {
        return switch (build.kind()) {
            case "weapon" -> TinkerItemBuilder.createModularWeapon(ModularWeaponType.valueOf(build.type()),
                    parts.get(0), parts.get(1), parts.size() > 2 ? parts.get(2) : null, tier, 0);
            case "tool" -> TinkerItemBuilder.createModularTool(ModularToolType.valueOf(build.type()),
                    parts.get(0), parts.get(1), parts.get(2), tier, 0);
            case "armor" -> TinkerItemBuilder.createModularArmor(ModularArmorType.valueOf(build.type()),
                    parts.get(0), parts.get(1), parts.get(2), tier, 0);
            default -> throw new IllegalArgumentException("Unknown golden build kind " + build.kind());
        };
    }

    // ==========================================
    // SHIPPED MATERIALS
    // ==========================================

    /** Everything the plugin ships, in a fixed order: minerals, then recipes, then catalysts. */
    @Nonnull
    private List<TinkerMaterial> shippingMaterials() {
        List<TinkerMaterial> shipped = new ArrayList<>();
        for (TinkerMaterial material : materials.getAll()) {
            String id = material.getId().toLowerCase(Locale.ROOT);
            if (id.startsWith(COMPOSITE_PREFIX) || id.startsWith(PRIME_PREFIX)) continue;
            shipped.add(material);
        }
        shipped.sort(Comparator.comparingInt(SiteData::kindRank)
                .thenComparing(TinkerMaterial::getName)
                .thenComparing(TinkerMaterial::getId));
        return shipped;
    }

    private static int kindRank(@Nonnull TinkerMaterial material) {
        return switch (kindOf(material)) {
            case "mineral" -> 0;
            case "legendary" -> 1;
            default -> 2;
        };
    }

    @Nonnull
    private Map<String, Object> counts() {
        Map<String, Object> counts = new LinkedHashMap<>();
        counts.put("materials", shipped.size());
        counts.put("minerals", shipped.stream().filter(m -> kindOf(m).equals("mineral")).count());
        counts.put("legendary", shipped.stream().filter(m -> kindOf(m).equals("legendary")).count());
        counts.put("catalysts", shipped.stream().filter(m -> kindOf(m).equals("catalyst")).count());
        counts.put("essences", TraitAffinity.values().length);
        counts.put("recipes", sortedAlloys().size());
        return counts;
    }

    // ==========================================
    // JSON
    // ==========================================

    /**
     * A minimal JSON writer: two spaces of indentation and stable key order, because the result is a
     * committed file whose diffs a human has to read.
     */
    private static final class Json {

        private Json() {
        }

        @Nonnull
        static String write(@Nonnull Object value) {
            StringBuilder out = new StringBuilder();
            append(out, value, 0);
            return out.append('\n').toString();
        }

        private static void append(StringBuilder out, Object value, int depth) {
            if (value == null) {
                out.append("null");
            } else if (value instanceof String text) {
                appendString(out, text);
            } else if (value instanceof Boolean) {
                out.append(value);
            } else if (value instanceof Number number) {
                out.append(number(number));
            } else if (value instanceof Map<?, ?> map) {
                appendMap(out, map, depth);
            } else if (value instanceof Iterable<?> list) {
                appendList(out, list, depth);
            } else {
                throw new IllegalArgumentException("Cannot write " + value.getClass().getName() + " as JSON");
            }
        }

        private static void appendMap(StringBuilder out, Map<?, ?> map, int depth) {
            if (map.isEmpty()) {
                out.append("{}");
                return;
            }
            out.append("{\n");
            int index = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                indent(out, depth + 1);
                appendString(out, String.valueOf(entry.getKey()));
                out.append(": ");
                append(out, entry.getValue(), depth + 1);
                if (++index < map.size()) out.append(',');
                out.append('\n');
            }
            indent(out, depth);
            out.append('}');
        }

        private static void appendList(StringBuilder out, Iterable<?> list, int depth) {
            List<Object> values = new ArrayList<>();
            for (Object value : list) values.add(value);
            if (values.isEmpty()) {
                out.append("[]");
                return;
            }
            out.append("[\n");
            for (int index = 0; index < values.size(); index++) {
                indent(out, depth + 1);
                append(out, values.get(index), depth + 1);
                if (index + 1 < values.size()) out.append(',');
                out.append('\n');
            }
            indent(out, depth);
            out.append(']');
        }

        private static void indent(StringBuilder out, int depth) {
            out.append("  ".repeat(depth));
        }

        /**
         * Numbers without a needless tail: {@code 350} not {@code 350.0}, and {@code 7.7} rather than
         * the {@code 7.699999809265137} a float promotes into a double.
         */
        @Nonnull
        private static String number(@Nonnull Number value) {
            if (value instanceof Integer || value instanceof Long) return value.toString();
            BigDecimal decimal = value instanceof Float single
                    ? new BigDecimal(Float.toString(single))
                    : BigDecimal.valueOf(value.doubleValue());
            return decimal.stripTrailingZeros().toPlainString();
        }

        private static void appendString(StringBuilder out, String text) {
            out.append('"');
            for (int index = 0; index < text.length(); index++) {
                char character = text.charAt(index);
                switch (character) {
                    case '"' -> out.append("\\\"");
                    case '\\' -> out.append("\\\\");
                    case '\n' -> out.append("\\n");
                    case '\r' -> out.append("\\r");
                    case '\t' -> out.append("\\t");
                    case '\b' -> out.append("\\b");
                    case '\f' -> out.append("\\f");
                    default -> {
                        if (character < 0x20) {
                            out.append(String.format("\\u%04x", (int) character));
                        } else {
                            out.append(character);
                        }
                    }
                }
            }
            out.append('"');
        }
    }
}
