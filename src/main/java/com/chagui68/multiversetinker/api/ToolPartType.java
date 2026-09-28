package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum ToolPartType {
    HEAD("Tool Head", "head", "Primary cutting or striking component. Determines base damage, mining speed, and primary forge trait."),
    ROD("Tool Rod", "rod", "Resilient handle shaft. Determines structural durability multiplier and handle trait."),
    BINDING("Tool Binding", "binding", "Reinforced connector/guard. Adds bonus durability and secondary utility trait.");

    private final String displayName;
    private final String idSuffix;
    private final String description;

    ToolPartType(String displayName, String idSuffix, String description) {
        this.displayName = displayName;
        this.idSuffix = idSuffix;
        this.description = description;
    }
}
