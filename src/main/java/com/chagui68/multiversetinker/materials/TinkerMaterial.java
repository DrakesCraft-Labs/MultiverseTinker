package com.chagui68.multiversetinker.materials;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;

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

    // Unique Tinker trait
    @Nonnull
    private final String traitName;
    @Nonnull
    private final String traitDescription;

    /**
     * Validates that the ID starts with 'mvtink_'.
     */
    public boolean isValidId() {
        return id != null && id.startsWith("mvtink_");
    }
}
