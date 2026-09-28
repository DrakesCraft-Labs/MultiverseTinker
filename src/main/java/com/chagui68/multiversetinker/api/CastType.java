package com.chagui68.multiversetinker.api;

import lombok.Getter;

@Getter
public enum CastType {
    HEAD("Head Cast", "mvtink_cast_head", "Casts molten metal or composites into durable tool and weapon heads."),
    ROD("Handle Cast", "mvtink_cast_rod", "Casts molten metal or composites into resilient tool/weapon handles and shafts."),
    BINDING("Pommel Cast", "mvtink_cast_binding", "Casts molten metal or composites into tool/weapon pommels and guards."),
    BOW_LIMBS("Bow Limbs Cast", "mvtink_cast_bow_limbs", "Casts flexible composite alloys into resilient bowstaves and limbs."),
    BOWSTRING("Bowstring Mold", "mvtink_cast_bowstring", "Forms flexible alloyed fibers into high-tension bowstrings."),
    SHIELD_PLATE("Shield Faceplate Cast", "mvtink_cast_shield_plate", "Casts dense defensive alloy plates for shields."),
    SHIELD_BOSS("Shield Boss Cast", "mvtink_cast_shield_boss", "Casts reinforced shield bosses and core frames."),
    ARMOR_PLATE("Armor Plate Cast", "mvtink_cast_armor_plate", "Casts protective heavy composite plates for modular armor."),
    ARMOR_LINING("Armor Lining Cast", "mvtink_cast_armor_lining", "Casts flexible chainmail mesh and internal padding for armor."),
    ARMOR_TRIM("Armor Trim Cast", "mvtink_cast_armor_trim", "Casts reinforced trims, joint rivets, and buckles for armor."),
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
