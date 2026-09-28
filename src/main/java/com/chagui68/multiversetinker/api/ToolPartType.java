package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum ToolPartType {
    HEAD("Modular Head (Cabeza)", "head", "Primary cutting or striking component. Determines base damage, mining speed, and primary strike trait."),
    ROD("Modular Handle (Mango)", "rod", "Resilient handle shaft. Determines structural durability multiplier and handle trait."),
    BINDING("Modular Pommel (Pomo)", "binding", "Reinforced pommel and counterweight. Adds bonus durability and secondary utility trait."),
    BOW_LIMBS("Bow Limbs (Brazos del Arco)", "bow_limbs", "Bowstave limbs. Determines draw speed, projectile velocity, and ranged trait."),
    BOWSTRING("Bowstring (Cuerda Tensora)", "bowstring", "High-tension woven cord. Dictates firing kinetic energy and arrow trait."),
    SHIELD_PLATE("Shield Faceplate (Placa Frontal)", "shield_plate", "Defensive front plate. Dictates frontal block absorption and reflection trait."),
    SHIELD_BOSS("Shield Boss (Umbo / Armazón)", "shield_boss", "Central reinforcement frame. Determines parry stability and secondary guard trait.");

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
            default -> null;
        };
    }
}
