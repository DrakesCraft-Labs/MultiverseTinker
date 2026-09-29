package com.chagui68.multiversetinker.archaeology;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ArchaeologyLootTable {

    /** Prefix of the section that weights this table, one key per rarity. */
    public static final String CONFIG_PREFIX = "rarity-weights.";

    /** Extra weight the prospector brush gives to rare, epic and legendary minerals. */
    public static final int PROSPECTOR_MULTIPLIER = 2;

    private final MaterialRegistry registry;
    private final Map<MineralOrigin, List<TinkerMaterial>> poolByOrigin = new EnumMap<>(MineralOrigin.class);
    /** Weights currently driving the table, per rarity; the shipped defaults until a config says otherwise. */
    private final EnumMap<MaterialRarity, Integer> weights = new EnumMap<>(MaterialRarity.class);

    public ArchaeologyLootTable(@Nonnull MaterialRegistry registry) {
        this.registry = registry;
        useDefaultWeights();
        reload();
    }

    // ==========================================
    // RARITY WEIGHTS (config.yml)
    // ==========================================

    /**
     * Applies the {@code rarity-weights} section of {@code config.yml}.
     *
     * <p>This is what makes a legendary mineral drop once in a hundred extractions instead of once in
     * three, so it belongs to the server rather than to the code: raise {@code common} for a forgiving
     * world, put {@code legendary} at {@code 0} to take legendaries off the geology table entirely.
     * Negative weights are read as {@code 0}, a missing or non-numeric key keeps the shipped value, and
     * a section whose weights all end at {@code 0} falls back to those shipped weights — otherwise the
     * first mineral of the dimension would be handed out every single time.</p>
     */
    public void configureWeights(@Nullable ConfigurationSection config) {
        EnumMap<MaterialRarity, Integer> read = new EnumMap<>(MaterialRarity.class);
        int total = 0;
        for (MaterialRarity rarity : MaterialRarity.values()) {
            int weight = (config == null)
                    ? rarity.getDefaultWeight()
                    : Math.max(0, config.getInt(CONFIG_PREFIX + configKey(rarity), rarity.getDefaultWeight()));
            read.put(rarity, weight);
            total += weight;
        }

        if (total <= 0) {
            useDefaultWeights();
            return;
        }
        weights.clear();
        weights.putAll(read);
    }

    /** Restores the weights shipped in {@code config.yml}. */
    public void useDefaultWeights() {
        weights.clear();
        for (MaterialRarity rarity : MaterialRarity.values()) {
            weights.put(rarity, rarity.getDefaultWeight());
        }
    }

    /** Config key of a rarity, the one used under {@code rarity-weights}: {@code RARE} → {@code rare}. */
    @Nonnull
    public static String configKey(@Nonnull MaterialRarity rarity) {
        return rarity.name().toLowerCase(Locale.ROOT);
    }

    /** Weight a rarity carries right now, with the prospector bonus when asked for. */
    public int weightOf(@Nonnull MaterialRarity rarity, boolean isProspector) {
        int weight = weights.getOrDefault(rarity, rarity.getDefaultWeight());
        return (isProspector && earnsProspectorBonus(rarity)) ? weight * PROSPECTOR_MULTIPLIER : weight;
    }

    public int weightOf(@Nonnull MaterialRarity rarity) {
        return weightOf(rarity, false);
    }

    /** Sum of the weights in use; {@code 0} would mean an empty table, which never happens. */
    public int totalWeight() {
        int total = 0;
        for (MaterialRarity rarity : MaterialRarity.values()) {
            total += weightOf(rarity);
        }
        return total;
    }

    /** True while the shipped weights are the ones in use, i.e. no server override is driving the table. */
    public boolean usesDefaultWeights() {
        for (MaterialRarity rarity : MaterialRarity.values()) {
            if (weightOf(rarity) != rarity.getDefaultWeight()) return false;
        }
        return true;
    }

    /** One-line report of the weights in use, for the log and {@code /mvtink verify}. */
    @Nonnull
    public String weightsSummary() {
        StringJoiner joiner = new StringJoiner(" · ");
        for (MaterialRarity rarity : MaterialRarity.values()) {
            joiner.add(configKey(rarity) + " " + weightOf(rarity));
        }
        return joiner.toString();
    }

    /** The three rarities a prospector brush rolls twice as often. */
    private static boolean earnsProspectorBonus(@Nonnull MaterialRarity rarity) {
        return switch (rarity) {
            case RARE, EPIC, LEGENDARY -> true;
            default -> false;
        };
    }

    // ==========================================
    // ROLLING
    // ==========================================

    public void reload() {
        poolByOrigin.clear();
        for (MineralOrigin origin : MineralOrigin.values()) {
            List<TinkerMaterial> list = new ArrayList<>(registry.getByOrigin(origin));
            poolByOrigin.put(origin, list);
        }
    }

    @Nullable
    public TinkerMaterial rollMineral(@Nonnull MineralOrigin origin) {
        return rollMineral(origin, false);
    }

    @Nullable
    public TinkerMaterial rollMineral(@Nonnull MineralOrigin origin, boolean isProspector) {
        List<TinkerMaterial> available = poolByOrigin.get(origin);
        if (available == null || available.isEmpty()) {
            return null;
        }

        int totalWeight = 0;
        for (TinkerMaterial material : available) {
            totalWeight += weightOf(material.getRarity(), isProspector);
        }

        // Only reachable if a dimension holds minerals whose rarities all weigh nothing; the configured
        // weights cannot produce it, because an all-zero section falls back to the shipped ones.
        if (totalWeight <= 0) {
            return available.getFirst();
        }

        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        int current = 0;
        for (TinkerMaterial material : available) {
            current += weightOf(material.getRarity(), isProspector);
            if (roll < current) {
                return material;
            }
        }

        return available.getLast();
    }
}
