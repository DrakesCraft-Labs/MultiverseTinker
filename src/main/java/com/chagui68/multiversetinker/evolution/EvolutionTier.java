package com.chagui68.multiversetinker.evolution;

import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public enum EvolutionTier {
    WOOD("Wood Tier", "<gradient:#a0522d:#8b4513><b>[Wood Tier]</b></gradient>", 0, 0, 0, 0.0, 1.0f),
    STONE("Stone Tier", "<gradient:#95a5a6:#7f8c8d><b>[Stone Tier]</b></gradient>", 15, 50, 150, 0.8, 1.15f),
    COPPER("Copper Tier", "<gradient:#d35400:#e67e22><b>[Copper Tier]</b></gradient>", 40, 150, 350, 1.5, 1.30f),
    IRON("Iron Tier", "<gradient:#ecf0f1:#bdc3c7><b>[Iron Tier]</b></gradient>", 80, 350, 650, 2.5, 1.50f),
    GOLD("Gold Tier", "<gradient:#f1c40f:#f39c12><b>[Gold Tier]</b></gradient>", 150, 750, 900, 3.5, 1.80f),
    DIAMOND("Diamond Tier", "<gradient:#00f2fe:#4facfe><b>[Diamond Tier]</b></gradient>", 300, 1500, 1500, 5.0, 2.20f),
    NETHERITE("Netherite Tier", "<gradient:#4b3832:#1e1e24><b>[Netherite Tier ★ MAX]</b></gradient>", 600, 3000, 2500, 7.0, 2.80f);

    private final String displayName;
    private final String miniMessageTag;
    private final int killRequirement;
    private final int blockBreakRequirement;
    private final int bonusDurability;
    private final double bonusDamage;
    private final float speedMultiplier;

    EvolutionTier(String displayName, String miniMessageTag, int killRequirement, int blockBreakRequirement,
                  int bonusDurability, double bonusDamage, float speedMultiplier) {
        this.displayName = displayName;
        this.miniMessageTag = miniMessageTag;
        this.killRequirement = killRequirement;
        this.blockBreakRequirement = blockBreakRequirement;
        this.bonusDurability = bonusDurability;
        this.bonusDamage = bonusDamage;
        this.speedMultiplier = speedMultiplier;
    }

    @Nullable
    public EvolutionTier getNextTier() {
        EvolutionTier[] values = values();
        int next = this.ordinal() + 1;
        if (next < values.length) {
            return values[next];
        }
        return null;
    }

    @Nonnull
    public static EvolutionTier fromString(@Nullable String name) {
        if (name == null) return WOOD;
        try {
            return EvolutionTier.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return WOOD;
        }
    }

    @Nonnull
    public Material getMatchingSwordMaterial() {
        return switch (this) {
            case WOOD -> Material.WOODEN_SWORD;
            case STONE, COPPER -> Material.STONE_SWORD;
            case IRON -> Material.IRON_SWORD;
            case GOLD -> Material.GOLDEN_SWORD;
            case DIAMOND -> Material.DIAMOND_SWORD;
            case NETHERITE -> Material.NETHERITE_SWORD;
        };
    }

    @Nonnull
    public Material getMatchingPickaxeMaterial() {
        return switch (this) {
            case WOOD -> Material.WOODEN_PICKAXE;
            case STONE, COPPER -> Material.STONE_PICKAXE;
            case IRON -> Material.IRON_PICKAXE;
            case GOLD -> Material.GOLDEN_PICKAXE;
            case DIAMOND -> Material.DIAMOND_PICKAXE;
            case NETHERITE -> Material.NETHERITE_PICKAXE;
        };
    }

    @Nonnull
    public Material getMatchingAxeMaterial() {
        return switch (this) {
            case WOOD -> Material.WOODEN_AXE;
            case STONE, COPPER -> Material.STONE_AXE;
            case IRON -> Material.IRON_AXE;
            case GOLD -> Material.GOLDEN_AXE;
            case DIAMOND -> Material.DIAMOND_AXE;
            case NETHERITE -> Material.NETHERITE_AXE;
        };
    }

    @Nonnull
    public Material getMatchingShovelMaterial() {
        return switch (this) {
            case WOOD -> Material.WOODEN_SHOVEL;
            case STONE, COPPER -> Material.STONE_SHOVEL;
            case IRON -> Material.IRON_SHOVEL;
            case GOLD -> Material.GOLDEN_SHOVEL;
            case DIAMOND -> Material.DIAMOND_SHOVEL;
            case NETHERITE -> Material.NETHERITE_SHOVEL;
        };
    }

    @Nonnull
    public Material getMatchingHoeMaterial() {
        return switch (this) {
            case WOOD -> Material.WOODEN_HOE;
            case STONE, COPPER -> Material.STONE_HOE;
            case IRON -> Material.IRON_HOE;
            case GOLD -> Material.GOLDEN_HOE;
            case DIAMOND -> Material.DIAMOND_HOE;
            case NETHERITE -> Material.NETHERITE_HOE;
        };
    }

    @Nonnull
    public Material getMatchingHelmetMaterial() {
        return switch (this) {
            case WOOD -> Material.LEATHER_HELMET;
            case STONE, COPPER -> Material.CHAINMAIL_HELMET;
            case IRON -> Material.IRON_HELMET;
            case GOLD -> Material.GOLDEN_HELMET;
            case DIAMOND -> Material.DIAMOND_HELMET;
            case NETHERITE -> Material.NETHERITE_HELMET;
        };
    }

    @Nonnull
    public Material getMatchingChestplateMaterial() {
        return switch (this) {
            case WOOD -> Material.LEATHER_CHESTPLATE;
            case STONE, COPPER -> Material.CHAINMAIL_CHESTPLATE;
            case IRON -> Material.IRON_CHESTPLATE;
            case GOLD -> Material.GOLDEN_CHESTPLATE;
            case DIAMOND -> Material.DIAMOND_CHESTPLATE;
            case NETHERITE -> Material.NETHERITE_CHESTPLATE;
        };
    }

    @Nonnull
    public Material getMatchingLeggingsMaterial() {
        return switch (this) {
            case WOOD -> Material.LEATHER_LEGGINGS;
            case STONE, COPPER -> Material.CHAINMAIL_LEGGINGS;
            case IRON -> Material.IRON_LEGGINGS;
            case GOLD -> Material.GOLDEN_LEGGINGS;
            case DIAMOND -> Material.DIAMOND_LEGGINGS;
            case NETHERITE -> Material.NETHERITE_LEGGINGS;
        };
    }

    @Nonnull
    public Material getMatchingBootsMaterial() {
        return switch (this) {
            case WOOD -> Material.LEATHER_BOOTS;
            case STONE, COPPER -> Material.CHAINMAIL_BOOTS;
            case IRON -> Material.IRON_BOOTS;
            case GOLD -> Material.GOLDEN_BOOTS;
            case DIAMOND -> Material.DIAMOND_BOOTS;
            case NETHERITE -> Material.NETHERITE_BOOTS;
        };
    }

    public int getArmorDamageRequirement() {
        return switch (this) {
            case WOOD -> 0;
            case STONE -> 50;
            case COPPER -> 150;
            case IRON -> 350;
            case GOLD -> 750;
            case DIAMOND -> 1500;
            case NETHERITE -> 3000;
        };
    }
}
