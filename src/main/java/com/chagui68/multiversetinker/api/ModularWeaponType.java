package com.chagui68.multiversetinker.api;

import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public enum ModularWeaponType {
    SWORD("Modular Broadsword", Material.WOODEN_SWORD, 3,
            "Cabeza (Blade/Head)", "Mango (Hilt/Handle)", "Pomo (Pommel/Guard)",
            "Sweeps agile melee strikes across enemies with elemental traits."),

    BOW("Modular Longbow", Material.BOW, 2,
            "Brazos del Arco (Bow Limbs)", "Cuerda Tensora (Bowstring)", null,
            "Fires high-velocity arrows infused with limb and string traits."),

    CROSSBOW("Modular Heavy Crossbow", Material.CROSSBOW, 3,
            "Cabeza (Prod/Limbs)", "Mango (Stock)", "Pomo (Trigger/Mechanism)",
            "Fires piercing high-tension bolts that bypass target armor."),

    TRIDENT("Modular Elder Trident", Material.TRIDENT, 3,
            "Cabeza (Prongs)", "Mango (Shaft)", "Pomo (Counterweight)",
            "Harnesses oceanic surges and releases hydraulic lightning strikes."),

    SPEAR("Modular Kinetic Spear", Material.WOODEN_SWORD, 3,
            "Cabeza (Spearhead)", "Mango (Long Shaft)", "Pomo (Butt Cap)",
            "Features extended melee reach with deadly jousting thrusts."),

    MACE("Modular War Mace", Material.MACE, 3,
            "Cabeza (Heavy Mace Head)", "Mango (Reinforced Shaft)", "Pomo (Flanged Pommel)",
            "Executes crushing downward smashes that generate seismic shockwaves."),

    SHIELD("Modular Tower Shield", Material.SHIELD, 2,
            "Placa Frontal (Faceplate)", "Umbo / Armazón (Shield Boss)", null,
            "Absorbs heavy kinetic blows, reflects damage, and retaliates with parry traits.");

    private final String displayName;
    private final Material baseMaterial;
    private final int partCount;
    private final String part1Name;
    private final String part2Name;
    private final String part3Name;
    private final String description;

    ModularWeaponType(String displayName, Material baseMaterial, int partCount,
                      String part1Name, String part2Name, @Nullable String part3Name,
                      String description) {
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.partCount = partCount;
        this.part1Name = part1Name;
        this.part2Name = part2Name;
        this.part3Name = part3Name;
        this.description = description;
    }

    public boolean isTwoPart() {
        return partCount == 2;
    }
}
