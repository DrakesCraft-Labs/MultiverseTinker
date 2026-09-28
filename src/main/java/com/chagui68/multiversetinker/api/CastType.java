package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum CastType {
    INGOT("Ingot Cast", "mvtink_cast_ingot", "Casts molten metal into standard ingots."),
    NUGGET("Nugget Cast", "mvtink_cast_nugget", "Casts molten metal into 9 small nuggets."),
    BLOCK("Block Cast", "mvtink_cast_block", "Casts molten metal into dense storage blocks.");

    private final String displayName;
    private final String id;
    private final String description;

    CastType(String displayName, String id, String description) {
        this.displayName = displayName;
        this.id = id;
        this.description = description;
    }
}
