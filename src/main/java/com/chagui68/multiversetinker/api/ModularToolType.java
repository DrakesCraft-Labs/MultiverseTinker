package com.chagui68.multiversetinker.api;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum ModularToolType {
    PICKAXE("Modular Pickaxe", Material.WOODEN_PICKAXE, "Excavates dense stone and geological ores with high efficiency and vein detection."),
    AXE("Modular Battleaxe", Material.WOODEN_AXE, "Cleaves through wood and shields with crushing kinetic force."),
    SHOVEL("Modular Excavator", Material.WOODEN_SHOVEL, "Excavates soil, gravel, and sand with area tremor vibrations."),
    HOE("Modular Scythe", Material.WOODEN_HOE, "Harvests 3x3 crops and replants seeds automatically from inventory."),
    FISHING_ROD("Modular Fishing Rod", Material.FISHING_ROD, "Dredges deep waters to snag rare minerals and archaeology relics."),
    @Deprecated
    SWORD("Modular Broadsword", Material.WOODEN_SWORD, "Delivers swift melee damage and executes sweeping strikes with elemental traits.");

    private final String displayName;
    private final Material baseMaterial;
    private final String description;

    ModularToolType(String displayName, Material baseMaterial, String description) {
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.description = description;
    }
}
