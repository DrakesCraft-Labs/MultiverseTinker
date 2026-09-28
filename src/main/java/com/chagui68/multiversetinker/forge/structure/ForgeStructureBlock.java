package com.chagui68.multiversetinker.forge.structure;

import org.bukkit.Material;

import java.util.Map;

public record ForgeStructureBlock(int dx, int dy, int dz, Material material, Map<String, String> properties) {

    public ForgeStructureBlock rotate(int degrees) {
        return switch ((degrees % 360 + 360) % 360) {
            case 90 -> new ForgeStructureBlock(-dz, dy, dx, material, properties);
            case 180 -> new ForgeStructureBlock(-dx, dy, -dz, material, properties);
            case 270 -> new ForgeStructureBlock(dz, dy, -dx, material, properties);
            default -> this;
        };
    }
}
