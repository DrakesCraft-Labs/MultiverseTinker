package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum ToolPartType {
    HEAD("Modular Head", "head", "Primary cutting or striking component. Determines base damage, mining speed, and primary strike trait."),
    ROD("Modular Handle", "rod", "Resilient handle shaft. Determines structural durability multiplier and handle trait."),
    BINDING("Modular Pommel", "binding", "Reinforced pommel and counterweight. Adds bonus durability and secondary utility trait."),
    BOW_LIMBS("Bow Limbs", "bow_limbs", "Bowstave limbs. Determines draw speed, projectile velocity, and ranged trait."),
    BOWSTRING("Bowstring", "bowstring", "High-tension woven cord. Dictates firing kinetic energy and arrow trait."),
    SHIELD_PLATE("Shield Faceplate", "shield_plate", "Defensive front plate. Dictates frontal block absorption and reflection trait."),
    SHIELD_BOSS("Shield Boss", "shield_boss", "Central reinforcement frame. Determines parry stability and secondary guard trait."),
    ARMOR_PLATE("Modular Armor Plate", "armor_plate", "Heavy protective plating. Determines defense points and primary protection trait."),
    ARMOR_LINING("Modular Armor Lining", "armor_lining", "Flexible internal mesh. Determines armor toughness and secondary defense trait."),
    ARMOR_TRIM("Modular Armor Trim", "armor_trim", "Reinforced joints and trim. Determines knockback resistance and passive utility trait.");

    private final String displayName;
    private final String idSuffix;
    private final String description;

    ToolPartType(String displayName, String idSuffix, String description) {
        this.displayName = displayName;
        this.idSuffix = idSuffix;
        this.description = description;
    }

    public static ToolPartType fromCast(CastType cast) {
        return switch (cast) {
            case HEAD -> HEAD;
            case ROD -> ROD;
            case BINDING -> BINDING;
            case BOW_LIMBS -> BOW_LIMBS;
            case BOWSTRING -> BOWSTRING;
            case SHIELD_PLATE -> SHIELD_PLATE;
            case SHIELD_BOSS -> SHIELD_BOSS;
            case ARMOR_PLATE -> ARMOR_PLATE;
            case ARMOR_LINING -> ARMOR_LINING;
            case ARMOR_TRIM -> ARMOR_TRIM;
            default -> null;
        };
    }
}
