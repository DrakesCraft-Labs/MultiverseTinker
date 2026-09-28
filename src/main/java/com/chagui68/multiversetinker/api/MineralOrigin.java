package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum MineralOrigin {
    OVERWORLD("Overworld", "Stone / Cobblestone"),
    NETHER("The Nether", "Netherrack / Blackstone"),
    THE_END("The End", "End Stone");

    private final String description;
    private final String sourceBlockName;

    MineralOrigin(String description, String sourceBlockName) {
        this.description = description;
        this.sourceBlockName = sourceBlockName;
    }
}
