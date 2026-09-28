package com.chagui68.multiversetinker.api;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum ModularToolType {
    PICKAXE("Modular Pickaxe", Material.NETHERITE_PICKAXE, "Excavates dense stone and geological ores with high efficiency."),
    SWORD("Modular Broadsword", Material.NETHERITE_SWORD, "Delivers swift melee damage and executes sweeping strikes with elemental traits."),
    AXE("Modular Battleaxe", Material.NETHERITE_AXE, "Cleaves through wood and armor with crushing kinetic force."),
    SHOVEL("Modular Excavator", Material.NETHERITE_SHOVEL, "Excavates soil, gravel, sand, and loose minerals effortlessly."),
    HOE("Modular Scythe", Material.NETHERITE_HOE, "Harvests crops, foliage, and organic terrain across wide radii.");

    private final String displayName;
    private final Material baseMaterial;
    private final String description;

    ModularToolType(String displayName, Material baseMaterial, String description) {
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.description = description;
    }
}
