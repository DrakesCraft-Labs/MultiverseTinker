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
    }

    @Nonnull
    public static NamespacedKey key(@Nonnull String suffix) {
        if (!suffix.startsWith("mvtink_")) {
            suffix = "mvtink_" + suffix;
        }
        return new NamespacedKey(plugin, suffix);
    }
}
