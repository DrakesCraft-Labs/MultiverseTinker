package com.chagui68.multiversetinker.api;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

@Getter
public enum ModularArmorType {
    HELMET("Modular Helmet", Material.NETHERITE_HELMET, EquipmentSlot.HEAD,
            "Armor Plate", "Armor Lining", "Armor Trim",
            "Cranium protection with environmental hazard filtering and mental focus perks.",
            3, 2.0, 500),

    CHESTPLATE("Modular Chestplate", Material.NETHERITE_CHESTPLATE, EquipmentSlot.CHEST,
            "Armor Plate", "Armor Lining", "Armor Trim",
            "Heavy torso plating that dampens kinetic shockwaves and projectile impacts.",
            8, 3.0, 750),

    LEGGINGS("Modular Leggings", Material.NETHERITE_LEGGINGS, EquipmentSlot.LEGS,
            "Armor Plate", "Armor Lining", "Armor Trim",
            "Flexible leg armor enhancing stride momentum and reducing sprint fatigue.",
            6, 2.0, 650),

    BOOTS("Modular Boots", Material.NETHERITE_BOOTS, EquipmentSlot.FEET,
            "Armor Plate", "Armor Lining", "Armor Trim",
            "Reinforced footwear offering fall impact absorption and ground traction.",
            3, 2.0, 450);

    private final String displayName;
    private final Material baseMaterial;
    private final EquipmentSlot slot;
    private final String part1Name;
    private final String part2Name;
    private final String part3Name;
    private final String description;
    private final int baseDefense;
    private final double baseToughness;
    private final int baseDurability;

    ModularArmorType(String displayName, Material baseMaterial, EquipmentSlot slot,
                     String part1Name, String part2Name, String part3Name,
                     String description, int baseDefense, double baseToughness, int baseDurability) {
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.slot = slot;
        this.part1Name = part1Name;
        this.part2Name = part2Name;
        this.part3Name = part3Name;
        this.description = description;
        this.baseDefense = baseDefense;
        this.baseToughness = baseToughness;
        this.baseDurability = baseDurability;
    }
}
