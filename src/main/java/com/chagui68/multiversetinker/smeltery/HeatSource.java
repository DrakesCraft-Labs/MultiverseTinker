package com.chagui68.multiversetinker.smeltery;

import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;

@Getter
public enum HeatSource {
    NONE("No Heat Source", 0.0f, false),
    LAVA("Lava", 1.0f, true),
    MAGMA_BLOCK("Magma Block", 0.70f, false);

    private final String displayName;
    private final float speedFactor;
    private final boolean consumable;

    HeatSource(String displayName, float speedFactor, boolean consumable) {
        this.displayName = displayName;
        this.speedFactor = speedFactor;
        this.consumable = consumable;
    }

    public static HeatSource fromMaterial(@Nonnull Material material) {
        if (material == Material.LAVA) {
            return LAVA;
        } else if (material == Material.MAGMA_BLOCK) {
            return MAGMA_BLOCK;
        }
        return NONE;
    }
}
