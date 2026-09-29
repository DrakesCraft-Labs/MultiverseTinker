package com.chagui68.multiversetinker.materials;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Builder
public class TinkerMaterial {

    @Nonnull
    private final String id; // Must start with mvtink_
    @Nonnull
    private final String name;
    @Nonnull
    private final MineralOrigin origin;
    @Nonnull
    private final MaterialRarity rarity;
    @Nonnull
    private final MaterialType type;
    @Nonnull
    private final Material baseVanillaMaterial;
    @Nonnull
    private final Material processedVanillaMaterial;
    @Nonnull
    private final Material nuggetVanillaMaterial;
    @Nonnull
    private final Material blockVanillaMaterial;
    @Nonnull
    private final String colorHex;
    @Nonnull
    private final String description;

    // Smelting parameters
    private final int meltingDurationTicks;

    // Forge and tool statistics
    private final int durabilityBonus;
    private final float miningSpeed;
    private final double attackDamageBonus;

    // Unique Tinker traits
    @Nonnull
    private final String traitName;
    @Nonnull
    private final String traitDescription;
    private final String weaponTraitDescription;
    private final String armorTraitDescription;

    /**
     * For alloys only: comma separated parent material IDs that this alloy was blended from.
     * Used by the trait engine to unfold an alloy into the essences of its two components.
     */
    private final String alloyParents;

    public int getDurability() {
        return durabilityBonus;
    }

    public double getAttackDamage() {
        return attackDamageBonus;
    }

    @Nonnull
    public String getWeaponTraitDescription() {
        if (weaponTraitDescription != null && !weaponTraitDescription.trim().isEmpty()) {
            return weaponTraitDescription;
        }
        if (traitDescription.toLowerCase().contains("mining") || traitDescription.toLowerCase().contains("mine") || traitDescription.toLowerCase().contains("ores")) {
            return "Subterranean Strike: Smashes through defenses with " + traitName + " combat power.";
        }
        return traitDescription;
    }

    @Nonnull
    public String getArmorTraitDescription() {
        if (armorTraitDescription != null && !armorTraitDescription.trim().isEmpty()) {
            return armorTraitDescription;
        }
        if (traitDescription.toLowerCase().contains("mining") || traitDescription.toLowerCase().contains("mine") || traitDescription.toLowerCase().contains("ores")) {
            return "Fortified Aegis: Reduces incoming physical damage with " + traitName + " structural resilience.";
        }
        return traitDescription;
    }

    @Nonnull
    public String getToolTraitDescription() {
        return traitDescription;
    }

    /**
     * Returns the comma separated parent IDs of an alloy, or {@code null} for base minerals.
     */
    @Nullable
    public String getAlloyParents() {
        return alloyParents;
    }

    /**
     * True when this material is a forge alloy blended from two distinct minerals.
     */
    public boolean isAlloy() {
        return alloyParents != null && alloyParents.contains(",");
    }

    /**
     * Validates that the ID starts with 'mvtink_'.
     */
    public boolean isValidId() {
        return id != null && id.startsWith("mvtink_");
    }
}
