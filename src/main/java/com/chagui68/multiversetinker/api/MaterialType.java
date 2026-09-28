package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum MaterialType {
    METAL("Smeltable Metal"),
    GEM("Precious Gem"),
    MINERAL("Earth Mineral"),
    CRYSTAL("Resonant Crystal"),
    ELEMENTAL("Primordial Element"),
    ALLOY("Alloy");

    private final String displayTypeName;

    MaterialType(String displayTypeName) {
        this.displayTypeName = displayTypeName;
    }
}
