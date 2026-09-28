package com.chagui68.multiversetinker.api;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

@Getter
public enum MaterialRarity {
    COMMON("Common", NamedTextColor.WHITE, 50),
    UNCOMMON("Uncommon", NamedTextColor.GREEN, 30),
    RARE("Rare", NamedTextColor.AQUA, 14),
    EPIC("Epic", NamedTextColor.LIGHT_PURPLE, 5),
    LEGENDARY("Legendary", TextColor.color(0xFFAA00), 1);

    private final String displayName;
    private final TextColor color;
    private final int defaultWeight;

    MaterialRarity(String displayName, TextColor color, int defaultWeight) {
        this.displayName = displayName;
        this.color = color;
        this.defaultWeight = defaultWeight;
    }
}
