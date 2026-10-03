package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.SignatureArt;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AlloyRegistry {

    /** File holding every composite alloy the players have forged so far. */
    private static final String DYNAMIC_FILE = "dynamic-alloys.yml";

    /**
     * Id prefix of a composite: two freely blendable minerals fused in the crucible, e.g.
     * {@code mvtink_alloy_copper_tin}.
     */
    public static final String COMPOSITE_PREFIX = "mvtink_alloy_";

    /** Id prefix of the new prime category (legendary alloy fused with an alloy, mineral or catalyst). */
    public static final String PRIME_PREFIX = "mvtink_prime_";

    /**
     * The 16 curated legendary recipes. Every prime alloy must have at least one of these as a
     * parent, which is what keeps the prime category finite instead of an infinite alloy ladder.
     */
    public static final Set<String> LEGENDARY_IDS = Set.of(
            "mvtink_bronze", "mvtink_electrum", "mvtink_invar", "mvtink_manyullyn", "mvtink_rose_gold",
            "mvtink_astral_brass", "mvtink_void_damascus", "mvtink_cinder_steel", "mvtink_prismatic_quartz",
            "mvtink_shadow_platinum", "mvtink_ender_brass", "mvtink_adamant_steel", "mvtink_hellfire_bismuth",
            "mvtink_glacial_silver", "mvtink_sanguine_gold", "mvtink_cosmic_netherite");

    private final Map<String, TinkerAlloy> alloys = new LinkedHashMap<>();
    /**
     * Canonical parent-pair index, so recipe lookups stay O(1) even after a player has forged
     * thousands of composites and primes (the old implementation scanned every alloy on each hit).
     */
    private final Map<String, TinkerAlloy> pairIndex = new HashMap<>();
    /** Ids of the 16 curated recipes: everything else is player-forged and must be persisted. */
    private final Set<String> curatedIds = new LinkedHashSet<>();

    @Nullable
    private JavaPlugin plugin;
    private boolean saveScheduled;

    public AlloyRegistry() {
        registerDefaultAlloys();
        curatedIds.addAll(alloys.keySet());
    }

    private void registerDefaultAlloys() {
        register(new TinkerAlloy(
                "mvtink_bronze", "Bronze",
                "mvtink_copper", "mvtink_tin",
                "#cd7f32", "Dense Temper",
                "High structural density. Grants +350 Durability and -20% knockback received.",
                350, 7.5f, 5.5
        ));

        register(new TinkerAlloy(
                "mvtink_electrum", "Electrum",
                "mvtink_gold", "mvtink_silver",
                "#fff8a6", "Lightning Conduit",
                "Conductive precious alloy. +25% attack speed and sparks shock damage on critical hits.",
                220, 11.0f, 6.0
        ));

        register(new TinkerAlloy(
                "mvtink_invar", "Invar",
                "mvtink_iron", "mvtink_nickel",
                "#b0b8b0", "Thermal Resilience",
                "Low thermal expansion. Completely immune to fire wear and grants +450 Durability.",
                450, 8.0f, 6.5
        ));

        register(new TinkerAlloy(
                "mvtink_manyullyn", "Manyullyn",
                "mvtink_cobalt", "mvtink_ardite",
                "#9b59b6", "Insatiable",
                "Deep Nether blood alloy. Consecutive strikes ramp up attack damage by +1.0 (stacks to +5.0).",
                800, 10.5f, 9.0
        ));

        register(new TinkerAlloy(
                "mvtink_rose_gold", "Rose Gold",
                "mvtink_gold", "mvtink_copper",
                "#b76e79", "Midas Sparkle",
                "Opulent blend. Increases experience orbs gained from mining and combat by +40%.",
                280, 9.0f, 5.0
        ));

        register(new TinkerAlloy(
                "mvtink_astral_brass", "Astral Brass",
                "mvtink_pyrite", "mvtink_astralite",
                "#f4d03f", "Starlight Grace",
                "Infused with cosmic dust. Grants permanent Feather Falling and radiant starlight particles.",
                500, 8.5f, 6.0
        ));

        register(new TinkerAlloy(
                "mvtink_void_damascus", "Void Damascus",
                "mvtink_tungsten", "mvtink_voidstone",
                "#2c3e50", "Abyssal Cleave",
                "Folded space-metal. True armor piercing attacks that bypass 30% of target defense.",
                950, 9.5f, 9.5
        ));

        register(new TinkerAlloy(
                "mvtink_cinder_steel", "Cinder Steel",
                "mvtink_steel", "mvtink_netherite",
                "#e67e22", "Hellfire Core",
                "Forged in nether magma. Ignites foes for 8 seconds and renders item fireproof.",
                1100, 10.0f, 9.0
        ));

        register(new TinkerAlloy(
                "mvtink_prismatic_quartz", "Prismatic Quartz",
                "mvtink_quartz", "mvtink_amethyst",
                "#e056fd", "Resonance Shock",
                "Harmonic crystal matrix. Striking produces an acoustic wave dealing 2.5 AOE damage.",
                400, 9.0f, 7.0
        ));

        register(new TinkerAlloy(
                "mvtink_shadow_platinum", "Shadow Platinum",
                "mvtink_platinum", "mvtink_obsidianite",
                "#636e72", "Umbral Veil",
                "Light-absorbing noble alloy. Sneaking grants brief invisibility and +50% backstab damage.",
                750, 9.0f, 8.0
        ));

        register(new TinkerAlloy(
                "mvtink_ender_brass", "Ender Brass",
                "mvtink_redstone", "mvtink_enderite",
                "#1abc9c", "Phase Step",
                "Resonant spatial conductor. Shift-Right-Click teleports player forward 10 blocks.",
                650, 8.5f, 7.5
        ));

        register(new TinkerAlloy(
                "mvtink_adamant_steel", "Adamant Steel",
                "mvtink_adamantium", "mvtink_titanium",
                "#2ecc71", "Unbreakable Will",
                "Indomitable metallurgy. 80% chance to completely ignore durability consumption.",
                1600, 11.5f, 8.5
        ));

        register(new TinkerAlloy(
                "mvtink_hellfire_bismuth", "Hellfire Bismuth",
                "mvtink_bismuth", "mvtink_fire_opal",
                "#ff7675", "Combustion",
                "Volatile crystalline metal. Critical hits trigger miniature non-destructive thermal explosions.",
                550, 8.0f, 8.0
        ));

        register(new TinkerAlloy(
                "mvtink_glacial_silver", "Glacial Silver",
                "mvtink_silver", "mvtink_cryolite",
                "#74b9ff", "Absolute Frost",
                "Sub-zero cryo-metal. Freezes targets with Slowness III and powder-snow frostbite for 4s.",
                480, 8.0f, 6.5
        ));

        register(new TinkerAlloy(
                "mvtink_sanguine_gold", "Sanguine Gold",
                "mvtink_gold", "mvtink_sanguinite",
                "#d63031", "Vampiric Touch",
                "Cursed lifedrinking gold. Restores 25% of all melee damage dealt as player health.",
                380, 9.5f, 7.5
        ));

        register(new TinkerAlloy(
                "mvtink_cosmic_netherite", "Cosmic Netherite",
                "mvtink_netherite", "mvtink_celestine",
                "#6c5ce7", "Cosmic Gravity",
                "Singularity-infused netherite. Melee strikes pull surrounding foes within 6 blocks together.",
                1400, 12.0f, 10.5
        ));
    }

    public void register(@Nonnull TinkerAlloy alloy) {
        alloys.put(alloy.id().toLowerCase(Locale.ROOT), alloy);
        pairIndex.put(pairKey(alloy.mat1Id(), alloy.mat2Id()), alloy);
    }

    @Nullable
    public TinkerAlloy findAlloy(@Nonnull String mat1, @Nonnull String mat2) {
        TinkerAlloy indexed = pairIndex.get(pairKey(mat1, mat2));
        if (indexed != null) return indexed;

        // Fallback for hand-registered alloys that may share a parent pair.
        for (TinkerAlloy alloy : alloys.values()) {
            if (alloy.matches(mat1, mat2)) {
                return alloy;
            }
        }
        return null;
    }

    /** Order-independent key for a pair of parent ids. */
    @Nonnull
    private static String pairKey(@Nonnull String mat1, @Nonnull String mat2) {
        String a = mat1.toLowerCase(Locale.ROOT);
        String b = mat2.toLowerCase(Locale.ROOT);
        return a.compareTo(b) <= 0 ? a + "|" + b : b + "|" + a;
    }

    @Nonnull
    public TinkerAlloy findOrCreateAlloy(@Nonnull TinkerMaterial m1, @Nonnull TinkerMaterial m2, @Nonnull MaterialRegistry materialRegistry) {
        TinkerAlloy existing = findAlloy(m1.getId(), m2.getId());
        if (existing != null) {
            return existing;
        }

        // Canonical order by ID to ensure commutativity: (m1, m2) == (m2, m1)
        TinkerMaterial first = m1.getId().compareTo(m2.getId()) <= 0 ? m1 : m2;
        TinkerMaterial second = first == m1 ? m2 : m1;

        boolean prime = isPrimePair(first, second);

        String dynamicAlloyId = dynamicId(first, second, prime);

        TinkerAlloy cached = alloys.get(dynamicAlloyId.toLowerCase(Locale.ROOT));
        if (cached != null) {
            return cached;
        }

        String alloyName = dynamicName(first, second, prime);
        String blendedColor = blendHexColors(first.getColorHex(), second.getColorHex());

        // Catalysts carry no metallurgical mass: a prime forged with one is built from the alloy it
        // catalysed, boosted by the fusion itself.
        TinkerMaterial statA = isCatalyst(first) ? second : first;
        TinkerMaterial statB = isCatalyst(second) ? first : second;
        int durabilityBonus = prime
                ? (int) Math.round((statA.getDurability() + statB.getDurability()) * 0.78) + 120
                : (int) Math.round((statA.getDurability() + statB.getDurability()) * 0.70) + 60;
        float miningSpeed = prime
                ? ((statA.getMiningSpeed() + statB.getMiningSpeed()) / 2.0f) + 1.1f
                : ((statA.getMiningSpeed() + statB.getMiningSpeed()) / 2.0f) + 0.6f;
        double attackDamageBonus = prime
                ? ((statA.getAttackDamage() + statB.getAttackDamage()) / 2.0) + 2.2
                : ((statA.getAttackDamage() + statB.getAttackDamage()) / 2.0) + 1.2;

        // A prime is named after the art it awakens, so two primes of the same legendary never share a
        // trait: Bronze + Nether Star and Bronze + Tin are different abilities and read differently.
        SignatureArt art = prime ? SignatureArt.forPair(first, second) : null;
        String traitName = prime
                ? (art != null ? art.name() : "Prime " + first.getTraitName())
                : first.getTraitName() + "-" + second.getTraitName();
        String traitDesc = prime
                ? primeDescription(first, second)
                : "Composite metallurgy combining " + first.getName() + " and " + second.getName() + " properties.";

        TinkerAlloy dynamicAlloy = new TinkerAlloy(
                dynamicAlloyId,
                alloyName,
                first.getId(),
                second.getId(),
                blendedColor,
                traitName,
                traitDesc,
                durabilityBonus,
                miningSpeed,
                attackDamageBonus
        );
        register(dynamicAlloy);
        markDynamicAlloyDirty();

        if (materialRegistry.get(dynamicAlloyId) == null) {
            TinkerMaterial tm = TinkerMaterial.builder()
                    .id(dynamicAlloyId)
                    .name(alloyName)
                    .origin(MineralOrigin.OVERWORLD)
                    .rarity(prime ? MaterialRarity.LEGENDARY : MaterialRarity.EPIC)
                    .type(MaterialType.ALLOY)
                    .baseVanillaMaterial(Material.RAW_IRON)
                    .processedVanillaMaterial(Material.IRON_INGOT)
                    .nuggetVanillaMaterial(Material.IRON_NUGGET)
                    .blockVanillaMaterial(Material.IRON_BLOCK)
                    .colorHex(blendedColor)
                    .description(traitDesc)
                    .meltingDurationTicks(prime ? 160 : 100)
                    .durabilityBonus(durabilityBonus)
                    .miningSpeed(miningSpeed)
                    .attackDamageBonus(attackDamageBonus)
                    .traitName(traitName)
                    .traitDescription(traitDesc)
                    .weaponTraitDescription("Dual Combat Synergy: Blends " + first.getWeaponTraitDescription() + " and " + second.getWeaponTraitDescription() + ".")
                    .armorTraitDescription("Dual Defensive Synergy: Blends " + first.getArmorTraitDescription() + " and " + second.getArmorTraitDescription() + ".")
                    .alloyParents(first.getId() + "," + second.getId())
                    .inheritedAffinities(TraitAffinity.inherit(first, second))
                    .build();
            materialRegistry.register(tm);
        }

        return dynamicAlloy;
    }

    /** A material id without the {@code mvtink_} prefix, exactly as a pair id spells it. */
    @Nonnull
    private static String bareIdOf(@Nonnull TinkerMaterial material) {
        return material.getId().toLowerCase(Locale.ROOT).replace("mvtink_", "");
    }

    /**
     * The alloy a crucible <b>pair id</b> names, forging and registering it when the id names a valid
     * pair that nobody has blended yet.
     *
     * <p>The codex previews the id of every pair the crucible would accept, including the ones that do
     * not exist yet, so an id copied out of it can name an alloy that was never smelted. This resolves
     * it the same way the crucible would: the two parents are looked up, the pair is checked against the
     * same rules ({@link #isCraftablePair}, so a catalyst still cannot join a plain composite and a prime
     * still cannot be reforged), and the result is registered exactly once.</p>
     *
     * <p>Splitting the id is a search rather than a substring, because both parent ids carry underscores
     * of their own ({@code cosmic_netherite}, {@code catalyst_nether_star}): every material that could be
     * the first parent is tried and the <b>longest</b> one wins, so the answer does not depend on the order
     * a server happens to have registered its materials in. A pair whose alloy is a curated recipe resolves
     * to that recipe, and the id it was asked for is not one of its own.</p>
     *
     * @return the alloy, or {@code null} when the id names no craftable pair
     */
    @Nullable
    public TinkerAlloy alloyForPairId(@Nonnull String pairId, @Nonnull MaterialRegistry materialRegistry) {
        String key = pairId.toLowerCase(Locale.ROOT);

        // Only a pair id is answered: an alloy that already exists under its own name is not one.
        String prefix = key.startsWith(PRIME_PREFIX) ? PRIME_PREFIX
                : key.startsWith(COMPOSITE_PREFIX) ? COMPOSITE_PREFIX
                : null;
        if (prefix == null) return null;

        String rest = key.substring(prefix.length());
        TinkerMaterial first = null;
        TinkerMaterial second = null;
        int firstLength = 0;

        for (TinkerMaterial candidate : materialRegistry.getAll()) {
            String bare = bareIdOf(candidate);
            if (bare.length() <= firstLength) continue;
            if (!rest.startsWith(bare + "_")) continue;

            TinkerMaterial partner = materialRegistry.get("mvtink_" + rest.substring(bare.length() + 1));
            if (partner == null) continue;

            // Longest first parent wins, so a pair never resolves differently on another server.
            first = candidate;
            second = partner;
            firstLength = bare.length();
        }

        if (first == null) return null;

        // The prefix has to be the one this pair really produces: a composite id never names a prime,
        // and a prime id never names a plain composite.
        if (!dynamicId(first, second).equals(key)) return null;
        if (!isCraftablePair(first, second)) return null;
        return findOrCreateAlloy(first, second, materialRegistry);
    }

    /** Narrative for a freshly fused prime alloy, naming the catalyst when it has one. */
    @Nonnull
    private static String primeDescription(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        for (TinkerMaterial parent : List.of(first, second)) {
            VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(parent.getId());
            if (catalyst != null) {
                return "Prime fusion catalysed by " + catalyst.getDisplayName() + ". " + catalyst.getDescription();
            }
        }
        return "Prime fusion of " + first.getName() + " and " + second.getName()
                + ". Inherits the full essence of both legendary components.";
    }

    private String blendHexColors(String hex1, String hex2) {
        try {
            int c1 = Integer.parseInt(hex1.replace("#", ""), 16);
            int c2 = Integer.parseInt(hex2.replace("#", ""), 16);
            int r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
            int r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
            int r = (r1 + r2) / 2;
            int g = (g1 + g2) / 2;
            int b = (b1 + b2) / 2;
            return String.format("#%02X%02X%02X", r, g, b);
        } catch (Exception e) {
            return "#D4AF37";
        }
    }

    @Nullable
    public TinkerAlloy get(@Nonnull String id) {
        return alloys.get(id.toLowerCase(Locale.ROOT));
    }

    @Nonnull
    public Collection<TinkerAlloy> getAllAlloys() {
        return Collections.unmodifiableCollection(alloys.values());
    }

    public void registerAlloysIntoMaterialRegistry(@Nonnull MaterialRegistry materialRegistry) {
        for (TinkerAlloy alloy : alloys.values()) {
            registerMaterial(materialRegistry, alloy);
        }
    }

    private void registerMaterial(@Nonnull MaterialRegistry materialRegistry, @Nonnull TinkerAlloy alloy) {
        if (materialRegistry.get(alloy.id()) != null) return;

        boolean prime = isPrimeParents(alloy.mat1Id(), alloy.mat2Id());

        TinkerMaterial tm = TinkerMaterial.builder()
                .id(alloy.id())
                .name(alloy.name())
                .origin(MineralOrigin.OVERWORLD)
                .rarity(prime ? MaterialRarity.LEGENDARY : MaterialRarity.EPIC)
                .type(MaterialType.ALLOY)
                .baseVanillaMaterial(Material.RAW_IRON)
                .processedVanillaMaterial(Material.IRON_INGOT)
                .nuggetVanillaMaterial(Material.IRON_NUGGET)
                .blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex(alloy.colorHex())
                .description(alloy.traitDescription())
                .meltingDurationTicks(prime ? 160 : 100)
                .durabilityBonus(alloy.durabilityBonus())
                .miningSpeed(alloy.miningSpeed())
                .attackDamageBonus(alloy.attackDamageBonus())
                .traitName(alloy.traitName())
                .traitDescription(alloy.traitDescription())
                .alloyParents(alloy.mat1Id() + "," + alloy.mat2Id())
                .inheritedAffinities(inheritedAffinitiesOf(materialRegistry, alloy.mat1Id(), alloy.mat2Id()))
                .build();
        materialRegistry.register(tm);
    }

    /**
     * Canonical id a freshly forged pair would receive, without registering anything.
     *
     * <p>The crucible codex uses this to preview combinations; {@link #findOrCreateAlloy} uses the
     * same helper, so what the codex shows is exactly what the crucible will produce.</p>
     */
    @Nonnull
    public static String dynamicId(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        return dynamicId(first, second, isPrimePair(first, second));
    }

    @Nonnull
    private static String dynamicId(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second, boolean prime) {
        // Canonical order, exactly like findOrCreateAlloy: (a, b) and (b, a) must preview the same id.
        TinkerMaterial canonicalA = first.getId().compareTo(second.getId()) <= 0 ? first : second;
        TinkerMaterial canonicalB = canonicalA == first ? second : first;
        String id1 = canonicalA.getId().replace("mvtink_", "");
        String id2 = canonicalB.getId().replace("mvtink_", "");
        return (prime ? PRIME_PREFIX : COMPOSITE_PREFIX) + id1 + "_" + id2;
    }

    /** Name a freshly forged pair would receive, without registering anything. */
    @Nonnull
    public static String dynamicName(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second) {
        return dynamicName(first, second, isPrimePair(first, second));
    }

    @Nonnull
    private static String dynamicName(@Nonnull TinkerMaterial first, @Nonnull TinkerMaterial second, boolean prime) {
        TinkerMaterial canonicalA = first.getId().compareTo(second.getId()) <= 0 ? first : second;
        TinkerMaterial canonicalB = canonicalA == first ? second : first;
        return prime
                ? canonicalA.getName() + " " + canonicalB.getName() + " Prime"
                : canonicalA.getName() + "-" + canonicalB.getName() + " Alloy";
    }

    /**
     * Blends the essences of an alloy's two parent minerals so the alloy keeps their identity.
     * Returns an empty string when a parent cannot be resolved (never fails registration).
     */
    @Nonnull
    private String inheritedAffinitiesOf(@Nonnull MaterialRegistry registry, @Nullable String parentA, @Nullable String parentB) {
        if (parentA == null || parentB == null) return "";
        TinkerMaterial first = registry.get(parentA);
        TinkerMaterial second = registry.get(parentB);
        if (first == null || second == null) return "";
        return TraitAffinity.inherit(first, second);
    }

    // ==========================================
    // PERSISTENCE OF PLAYER-FORGED COMPOSITES
    // ==========================================
    /**
     * Restores every composite alloy forged in previous sessions and arms the save pipeline.
     *
     * <p>Without this, an alloy ingot crafted before a restart would refer to a material that no
     * longer exists and would silently stop working as a forging component.</p>
     */
    public void enablePersistence(@Nonnull JavaPlugin plugin, @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        File file = new File(plugin.getDataFolder(), DYNAMIC_FILE);
        if (!file.exists()) return;

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection root = yaml.getConfigurationSection("alloys");
        if (root == null) return;

        // A restored prime inherits the essences of its legendary parent, so the curated recipes must
        // already be materials when it is rebuilt — otherwise it would come back with a generic blend
        // and a different art than the one it was forged with.
        registerAlloysIntoMaterialRegistry(materialRegistry);

        List<TinkerAlloy> restoredAlloys = new ArrayList<>();
        for (String id : root.getKeys(false)) {
            ConfigurationSection section = root.getConfigurationSection(id);
            if (section == null) continue;
            String mat1 = section.getString("mat1");
            String mat2 = section.getString("mat2");
            if (mat1 == null || mat2 == null) continue;

            TinkerAlloy alloy = new TinkerAlloy(
                    id,
                    section.getString("name", id),
                    mat1,
                    mat2,
                    section.getString("color", "#D4AF37"),
                    section.getString("traitName", "Composite"),
                    section.getString("traitDescription", "Composite metallurgy."),
                    section.getInt("durability"),
                    (float) section.getDouble("miningSpeed"),
                    section.getDouble("attackDamage"));
            register(alloy);
            restoredAlloys.add(alloy);
        }

        // Composites first: a prime may have been fused from one of them.
        restoredAlloys.sort(Comparator.comparing(alloy -> isPrimeParents(alloy.mat1Id(), alloy.mat2Id())));
        for (TinkerAlloy alloy : restoredAlloys) {
            TinkerAlloy named = withArtName(alloy, materialRegistry);
            if (named != alloy) register(named);
            registerMaterial(materialRegistry, named);
        }

        if (!restoredAlloys.isEmpty()) {
            plugin.getLogger().info("Restored " + restoredAlloys.size() + " player-forged composite alloys.");
        }
    }

    /**
     * A restored prime takes the trait name of the art it awakens today, so primes saved before arts
     * existed (all named {@code Prime <trait>}) are renamed on load instead of keeping a shared name.
     */
    @Nonnull
    private TinkerAlloy withArtName(@Nonnull TinkerAlloy alloy, @Nonnull MaterialRegistry materialRegistry) {
        if (!isPrimeParents(alloy.mat1Id(), alloy.mat2Id())) return alloy;
        TinkerMaterial first = materialRegistry.get(alloy.mat1Id());
        TinkerMaterial second = materialRegistry.get(alloy.mat2Id());
        if (first == null || second == null) return alloy;
        SignatureArt art = SignatureArt.forPair(first, second);
        if (art == null || art.name().equals(alloy.traitName())) return alloy;
        return new TinkerAlloy(alloy.id(), alloy.name(), alloy.mat1Id(), alloy.mat2Id(), alloy.colorHex(),
                art.name(), alloy.traitDescription(), alloy.durabilityBonus(), alloy.miningSpeed(),
                alloy.attackDamageBonus());
    }

    /** Number of composite alloys players have forged, excluding the 16 curated recipes. */
    public int getDynamicAlloyCount() {
        int count = 0;
        for (String id : alloys.keySet()) {
            if (!curatedIds.contains(id)) count++;
        }
        return count;
    }

    /** Number of prime alloys (legendary fusions) players have forged. */
    public int getPrimeAlloyCount() {
        int count = 0;
        for (TinkerAlloy alloy : alloys.values()) {
            if (isPrimeParents(alloy.mat1Id(), alloy.mat2Id())) count++;
        }
        return count;
    }

    /** Writes every player-forged composite alloy to disk. Safe to call at any time. */
    public void flush() {
        if (plugin == null) return;

        YamlConfiguration yaml = new YamlConfiguration();
        for (TinkerAlloy alloy : alloys.values()) {
            if (curatedIds.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            String path = "alloys." + alloy.id() + ".";
            yaml.set(path + "name", alloy.name());
            yaml.set(path + "mat1", alloy.mat1Id());
            yaml.set(path + "mat2", alloy.mat2Id());
            yaml.set(path + "color", alloy.colorHex());
            yaml.set(path + "traitName", alloy.traitName());
            yaml.set(path + "traitDescription", alloy.traitDescription());
            yaml.set(path + "durability", alloy.durabilityBonus());
            yaml.set(path + "miningSpeed", (double) alloy.miningSpeed());
            yaml.set(path + "attackDamage", alloy.attackDamageBonus());
        }

        File folder = plugin.getDataFolder();
        if (!folder.exists() && !folder.mkdirs()) {
            plugin.getLogger().warning("Could not create the plugin data folder for " + DYNAMIC_FILE + ".");
            return;
        }
        try {
            yaml.save(new File(folder, DYNAMIC_FILE));
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save " + DYNAMIC_FILE + ": " + e.getMessage());
        }
    }

    private void markDynamicAlloyDirty() {
        if (plugin == null || saveScheduled) return;
        saveScheduled = true;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            saveScheduled = false;
            flush();
        }, 40L);
    }

    /**
     * Determines whether a material may be used as a free Alloy Crucible input.
     *
     * <p>Only minerals that can be excavated with the Prospector Brush (Overworld, Nether and
     * End geology) or refined from vanilla Minecraft ores are freely blendable. Alloys and
     * catalysts are rejected here: they only enter the crucible through a prime fusion.</p>
     */
    public static boolean isMixable(@Nullable TinkerMaterial material) {
        if (material == null) return false;
        if (material.getType() == MaterialType.ALLOY) return false;
        if (material.isAlloy()) return false;
        return switch (material.getOrigin()) {
            case OVERWORLD, NETHER, THE_END, VANILLA -> true;
        };
    }

    /** Whether this material is one of the 16 curated legendary recipes. */
    public static boolean isLegendary(@Nullable TinkerMaterial material) {
        return material != null && LEGENDARY_IDS.contains(material.getId().toLowerCase(Locale.ROOT));
    }

    /** Whether this material is a vanilla crucible catalyst (Nether Star, Blue Ice, Echo Shard…). */
    public static boolean isCatalyst(@Nullable TinkerMaterial material) {
        return material != null && material.getId().startsWith(VanillaCatalyst.ID_PREFIX);
    }

    /**
     * Whether this material belongs to the prime category: an alloy forged with at least one
     * legendary parent. Primes cannot seed another prime, so the ladder stops at two alloys deep.
     */
    public static boolean isPrime(@Nullable TinkerMaterial material) {
        if (material == null) return false;
        if (material.getId().startsWith(PRIME_PREFIX)) return true;
        return hasLegendaryParent(material.getAlloyParents());
    }

    /**
     * Whether the crucible accepts this exact pair as a <b>prime fusion</b>: at least one legendary
     * parent fused with another legendary alloy, a composite alloy, a mineral or a vanilla catalyst.
     */
    public static boolean isPrimePair(@Nullable TinkerMaterial first, @Nullable TinkerMaterial second) {
        if (first == null || second == null) return false;
        if (first.getId().equalsIgnoreCase(second.getId())) return false;
        if (isPrime(first) || isPrime(second)) return false;
        if (isLegendary(first)) return isPrimePartner(second, first);
        if (isLegendary(second)) return isPrimePartner(first, second);
        return false;
    }

    private static boolean isPrimePartner(@Nonnull TinkerMaterial partner, @Nonnull TinkerMaterial legendary) {
        if (partner.getId().equalsIgnoreCase(legendary.getId())) return false;
        if (isLegendary(partner)) return true;
        if (partner.getType() == MaterialType.ALLOY) return true;
        return isMixable(partner);
    }

    private static boolean hasLegendaryParent(@Nullable String parents) {
        if (parents == null || !parents.contains(",")) return false;
        for (String parentId : parents.split(",")) {
            if (LEGENDARY_IDS.contains(parentId.trim().toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

    /** Prime status derived straight from the two parent ids, used while registering materials. */
    public static boolean isPrimeParents(@Nullable String parentA, @Nullable String parentB) {
        if (parentA == null || parentB == null) return false;
        return LEGENDARY_IDS.contains(parentA.toLowerCase(Locale.ROOT))
                || LEGENDARY_IDS.contains(parentB.toLowerCase(Locale.ROOT));
    }

    /**
     * Whether the crucible may forge this exact pair.
     *
     * <p>Generic blending stays limited to brush/vanilla minerals, but a pair that matches a curated
     * legendary recipe is always craftable. That is what keeps <b>Cinder Steel</b> (steel +
     * netherite) and <b>Cosmic Netherite</b> (netherite + celestine) reachable even though vanilla
     * netherite is itself typed as an alloy and therefore cannot be blended freely.</p>
     */
    public boolean isCraftablePair(@Nullable TinkerMaterial first, @Nullable TinkerMaterial second) {
        if (first == null || second == null) return false;
        if (first.getId().equalsIgnoreCase(second.getId())) return false;
        if (isMixable(first) && isMixable(second)) return true;
        if (isPrimePair(first, second)) return true;
        return findAlloy(first.getId(), second.getId()) != null;
    }

    /**
     * Registers the vanilla catalyst items as materials so they can carry essences, appear in lore
     * and act as the parent of a prime alloy.
     */
    public void registerCatalystMaterials(@Nonnull MaterialRegistry materialRegistry) {
        for (VanillaCatalyst catalyst : VanillaCatalyst.values()) {
            if (materialRegistry.get(catalyst.getMaterialId()) != null) continue;
            materialRegistry.register(TinkerMaterial.builder()
                    .id(catalyst.getMaterialId())
                    .name(catalyst.getDisplayName())
                    .origin(MineralOrigin.VANILLA)
                    .rarity(MaterialRarity.LEGENDARY)
                    .type(MaterialType.ALLOY)
                    .baseVanillaMaterial(catalyst.getItem())
                    .processedVanillaMaterial(catalyst.getItem())
                    .nuggetVanillaMaterial(catalyst.getItem())
                    .blockVanillaMaterial(catalyst.getItem())
                    .colorHex(catalyst.getColorHex())
                    .description(catalyst.getLoreLine())
                    .meltingDurationTicks(200)
                    .durabilityBonus(400)
                    .miningSpeed(8.0f)
                    .attackDamageBonus(6.0)
                    .traitName("Catalyst: " + catalyst.getDisplayName())
                    .traitDescription(catalyst.getDescription())
                    .weaponTraitDescription(catalyst.getUltimate().getDisplayName() + " — " + catalyst.getUltimate().getDescription())
                    .armorTraitDescription(catalyst.getArmorState().getLoreLine())
                    .build());
        }
    }

    /**
     * Convenience overload used for player feedback messages.
     */
    @Nullable
    public static String mixRequirementMessage() {
        return "The crucible blends two brush/vanilla minerals, or fuses a LEGENDARY alloy with another "
                + "alloy, a mineral or a vanilla catalyst (Nether Star, Blue Ice, Echo Shard…). "
                + "Prime alloys cannot be reforged.";
    }
}
