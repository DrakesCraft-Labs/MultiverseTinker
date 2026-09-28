package com.chagui68.multiversetinker.archaeology;

import com.chagui68.multiversetinker.api.MineralOrigin;
import org.bukkit.Material;

import javax.annotation.Nullable;

public final class ArchaeologyBlockType {

    private ArchaeologyBlockType() {
        throw new UnsupportedOperationException("Utility class");
    }

    @Nullable
    public static MineralOrigin getOrigin(Material material) {
        if (material == null) {
            return null;
        }

        // Bloques del Overworld
        if (material == Material.STONE ||
            material == Material.COBBLESTONE ||
            material == Material.DEEPSLATE ||
            material == Material.COBBLED_DEEPSLATE ||
            material == Material.ANDESITE ||
            material == Material.DIORITE ||
            material == Material.GRANITE ||
            material == Material.TUFF) {
            return MineralOrigin.OVERWORLD;
        }

        // Bloques del Nether
        if (material == Material.NETHERRACK ||
            material == Material.BLACKSTONE ||
            material == Material.BASALT) {
            return MineralOrigin.NETHER;
        }

        // Bloques de The End
        if (material == Material.END_STONE) {
            return MineralOrigin.THE_END;
        }

        return null;
    }

    /**
     * Calcula la siguiente fase de degradación geológica del bloque al ser cepillado.
     */
    @Nullable
    public static Material getDegradedMaterial(Material current) {
        if (current == null) {
            return null;
        }
        return switch (current) {
            case STONE, ANDESITE, DIORITE, GRANITE -> Material.COBBLESTONE;
            case COBBLESTONE -> Material.GRAVEL;
            case DEEPSLATE -> Material.COBBLED_DEEPSLATE;
            case COBBLED_DEEPSLATE -> Material.GRAVEL;
            case TUFF -> Material.GRAVEL;
            case GRAVEL -> Material.AIR;
            case BLACKSTONE -> Material.BASALT;
            case BASALT -> Material.NETHERRACK;
            case NETHERRACK -> Material.AIR;
            case END_STONE -> Material.AIR;
            default -> null;
        };
    }
}
