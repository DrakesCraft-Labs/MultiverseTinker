package com.chagui68.multiversetinker.storage;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public final class TinkerKeys {

    private static Plugin plugin;

    public static NamespacedKey ITEM_ID;
    public static NamespacedKey MATERIAL_ID;
    public static NamespacedKey MATERIAL_TYPE;
    public static NamespacedKey IS_TINKER_ITEM;
    public static NamespacedKey IS_TINKER_RAW;
    public static NamespacedKey IS_TINKER_INGOT;
    public static NamespacedKey IS_TINKER_NUGGET;
    public static NamespacedKey IS_TINKER_BLOCK;
    public static NamespacedKey IS_MOLTEN_BUCKET;
    public static NamespacedKey IS_CAST;
    public static NamespacedKey CAST_TYPE;
    public static NamespacedKey IS_SMELTERY;
    public static NamespacedKey PROSPECTOR_BRUSH;
    public static NamespacedKey IS_TOOL_PART;
    public static NamespacedKey TOOL_PART_TYPE;
    public static NamespacedKey IS_MODULAR_TOOL;
    public static NamespacedKey TOOL_TYPE;
    public static NamespacedKey TOOL_HEAD_MAT;
    public static NamespacedKey TOOL_ROD_MAT;
    public static NamespacedKey TOOL_BINDING_MAT;
    public static NamespacedKey TOOL_MAX_DURABILITY;
    public static NamespacedKey TOOL_CURRENT_DURABILITY;
    public static NamespacedKey TOOL_MINING_SPEED;
    public static NamespacedKey TOOL_ATTACK_DAMAGE;

    // Evolution & Weapons
    public static NamespacedKey IS_MODULAR_WEAPON;
    public static NamespacedKey WEAPON_TYPE;
    public static NamespacedKey EVOLUTION_TIER;
    public static NamespacedKey KILL_COUNT;
    public static NamespacedKey BLOCKS_BROKEN_COUNT;
    public static NamespacedKey PART_COMPOSITION_DATA;
    public static NamespacedKey TOOL_HEAD_COMP;
    public static NamespacedKey TOOL_ROD_COMP;
    public static NamespacedKey TOOL_BINDING_COMP;
    public static NamespacedKey BOW_LIMBS_COMP;
    public static NamespacedKey BOWSTRING_COMP;
    public static NamespacedKey SHIELD_PLATE_COMP;
    public static NamespacedKey SHIELD_BOSS_COMP;

    // Armor & System GUI Keys
    public static NamespacedKey IS_MODULAR_ARMOR;
    public static NamespacedKey ARMOR_TYPE;
    public static NamespacedKey ARMOR_PLATE_COMP;
    public static NamespacedKey ARMOR_LINING_COMP;
    public static NamespacedKey ARMOR_TRIM_COMP;
    public static NamespacedKey DAMAGE_ABSORBED;
    public static NamespacedKey SYSTEM_GUI_ITEM;

    private TinkerKeys() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void initialize(@Nonnull Plugin pl) {
        plugin = pl;
        ITEM_ID = new NamespacedKey(plugin, "mvtink_item_id");
        MATERIAL_ID = new NamespacedKey(plugin, "mvtink_material_id");
        MATERIAL_TYPE = new NamespacedKey(plugin, "mvtink_material_type");
        IS_TINKER_ITEM = new NamespacedKey(plugin, "mvtink_is_item");
        IS_TINKER_RAW = new NamespacedKey(plugin, "mvtink_is_raw");
        IS_TINKER_INGOT = new NamespacedKey(plugin, "mvtink_is_ingot");
        IS_TINKER_NUGGET = new NamespacedKey(plugin, "mvtink_is_nugget");
        IS_TINKER_BLOCK = new NamespacedKey(plugin, "mvtink_is_block");
        IS_MOLTEN_BUCKET = new NamespacedKey(plugin, "mvtink_is_molten_bucket");
        IS_CAST = new NamespacedKey(plugin, "mvtink_is_cast");
        CAST_TYPE = new NamespacedKey(plugin, "mvtink_cast_type");
        IS_SMELTERY = new NamespacedKey(plugin, "mvtink_is_smeltery");
        PROSPECTOR_BRUSH = new NamespacedKey(plugin, "mvtink_brush");
        IS_TOOL_PART = new NamespacedKey(plugin, "mvtink_is_tool_part");
        TOOL_PART_TYPE = new NamespacedKey(plugin, "mvtink_tool_part_type");
        IS_MODULAR_TOOL = new NamespacedKey(plugin, "mvtink_is_modular_tool");
        TOOL_TYPE = new NamespacedKey(plugin, "mvtink_modular_tool_type");
        TOOL_HEAD_MAT = new NamespacedKey(plugin, "mvtink_head_mat");
        TOOL_ROD_MAT = new NamespacedKey(plugin, "mvtink_rod_mat");
        TOOL_BINDING_MAT = new NamespacedKey(plugin, "mvtink_binding_mat");
        TOOL_MAX_DURABILITY = new NamespacedKey(plugin, "mvtink_max_durability");
        TOOL_CURRENT_DURABILITY = new NamespacedKey(plugin, "mvtink_cur_durability");
        TOOL_MINING_SPEED = new NamespacedKey(plugin, "mvtink_mining_speed");
        TOOL_ATTACK_DAMAGE = new NamespacedKey(plugin, "mvtink_attack_damage");

        IS_MODULAR_WEAPON = new NamespacedKey(plugin, "mvtink_is_modular_weapon");
        WEAPON_TYPE = new NamespacedKey(plugin, "mvtink_modular_weapon_type");
        EVOLUTION_TIER = new NamespacedKey(plugin, "mvtink_evolution_tier");
        KILL_COUNT = new NamespacedKey(plugin, "mvtink_kill_count");
        BLOCKS_BROKEN_COUNT = new NamespacedKey(plugin, "mvtink_blocks_broken");
        PART_COMPOSITION_DATA = new NamespacedKey(plugin, "mvtink_part_comp_data");
        TOOL_HEAD_COMP = new NamespacedKey(plugin, "mvtink_head_comp");
        TOOL_ROD_COMP = new NamespacedKey(plugin, "mvtink_rod_comp");
        TOOL_BINDING_COMP = new NamespacedKey(plugin, "mvtink_binding_comp");
        BOW_LIMBS_COMP = new NamespacedKey(plugin, "mvtink_bow_limbs_comp");
        BOWSTRING_COMP = new NamespacedKey(plugin, "mvtink_bowstring_comp");
        SHIELD_PLATE_COMP = new NamespacedKey(plugin, "mvtink_shield_plate_comp");
        SHIELD_BOSS_COMP = new NamespacedKey(plugin, "mvtink_shield_boss_comp");

        IS_MODULAR_ARMOR = new NamespacedKey(plugin, "mvtink_is_modular_armor");
        ARMOR_TYPE = new NamespacedKey(plugin, "mvtink_armor_type");
        ARMOR_PLATE_COMP = new NamespacedKey(plugin, "mvtink_armor_plate_comp");
        ARMOR_LINING_COMP = new NamespacedKey(plugin, "mvtink_armor_lining_comp");
        ARMOR_TRIM_COMP = new NamespacedKey(plugin, "mvtink_armor_trim_comp");
        DAMAGE_ABSORBED = new NamespacedKey(plugin, "mvtink_damage_absorbed");
        SYSTEM_GUI_ITEM = new NamespacedKey(plugin, "mvtink_system_gui_item");
    }

    @Nonnull
    public static NamespacedKey key(@Nonnull String suffix) {
        if (!suffix.startsWith("mvtink_")) {
            suffix = "mvtink_" + suffix;
        }
        return new NamespacedKey(plugin, suffix);
    }
}
