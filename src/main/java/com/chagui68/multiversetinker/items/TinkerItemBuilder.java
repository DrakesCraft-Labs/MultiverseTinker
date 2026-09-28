package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TinkerItemBuilder {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Nonnull
    public static ItemStack createRawMineral(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(material.getBaseVanillaMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:" + material.getColorHex() + ":#ffffff>Raw " + material.getName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Rarity: ", NamedTextColor.GRAY)
                .append(Component.text(material.getRarity().getDisplayName(), material.getRarity().getColor()))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Type: ", NamedTextColor.GRAY)
                .append(Component.text(material.getType().getDisplayTypeName(), NamedTextColor.YELLOW))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Origin: ", NamedTextColor.GRAY)
                .append(Component.text(material.getOrigin().getSourceBlockName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text(material.getDescription(), NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Forge Trait: ", NamedTextColor.GOLD)
                .append(Component.text(material.getTraitName(), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  " + material.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("♨ Smelt in a Smeltery over lava or magma to melt.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_raw</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_raw");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.MATERIAL_TYPE, PersistentDataType.STRING, "RAW");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TINKER_RAW, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createIngot(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(material.getProcessedVanillaMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String suffix = switch (material.getType()) {
            case METAL, ALLOY -> " Ingot";
            case GEM -> " Gem";
            case CRYSTAL -> " Crystal";
            default -> " Extract";
        };

        String displayNameMini = "<gradient:" + material.getColorHex() + ":#ffffff>"
                + material.getName() + suffix + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Rarity: ", NamedTextColor.GRAY)
                .append(Component.text(material.getRarity().getDisplayName(), material.getRarity().getColor()))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Type: ", NamedTextColor.GRAY)
                .append(Component.text(material.getType().getDisplayTypeName(), NamedTextColor.YELLOW))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Tinker Statistics:", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Durability: ", NamedTextColor.GRAY)
                .append(Component.text("+" + material.getDurabilityBonus(), NamedTextColor.GREEN))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Speed: ", NamedTextColor.GRAY)
                .append(Component.text(String.format("%.1fx", material.getMiningSpeed()), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Attack Bonus: ", NamedTextColor.GRAY)
                .append(Component.text(String.format("+%.1f", material.getAttackDamageBonus()), NamedTextColor.RED))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Forge Trait: ", NamedTextColor.GOLD)
                .append(Component.text(material.getTraitName(), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  " + material.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_ingot</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_ingot");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.MATERIAL_TYPE, PersistentDataType.STRING, "INGOT");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TINKER_INGOT, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createNugget(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(material.getNuggetVanillaMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:" + material.getColorHex() + ":#ffffff>"
                + material.getName() + " Nugget</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Rarity: ", NamedTextColor.GRAY)
                .append(Component.text(material.getRarity().getDisplayName(), material.getRarity().getColor()))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Combine 9 in a crafting table to form 1 Ingot.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_nugget</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_nugget");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.MATERIAL_TYPE, PersistentDataType.STRING, "NUGGET");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TINKER_NUGGET, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createBlock(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(material.getBlockVanillaMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:" + material.getColorHex() + ":#ffffff>Block of "
                + material.getName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Rarity: ", NamedTextColor.GRAY)
                .append(Component.text(material.getRarity().getDisplayName(), material.getRarity().getColor()))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Dense storage block crafted from 9 ingots.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_block</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_block");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.MATERIAL_TYPE, PersistentDataType.STRING, "BLOCK");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TINKER_BLOCK, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createMoltenBucket(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:#ff4500:" + material.getColorHex() + ">Molten "
                + material.getName() + " Bucket</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("State: ", NamedTextColor.GRAY)
                .append(Component.text("Molten Liquid (Heated)", NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Material: ", NamedTextColor.GRAY)
                .append(Component.text(material.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Cooling & Casting:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Right-click a Water Cauldron with this bucket", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  while holding an Ingot Cast to solidify it.", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_molten_bucket</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_molten_bucket");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.MATERIAL_TYPE, PersistentDataType.STRING, "MOLTEN_BUCKET");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_MOLTEN_BUCKET, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createCast(@Nonnull CastType castType) {
        ItemStack item = new ItemStack(Material.BRICK);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        meta.displayName(MINI_MESSAGE.deserialize("<gradient:#e67e22:#f39c12>" + castType.getDisplayName() + "</gradient>")
                .decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Reusable Casting Mold", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(castType.getDescription(), NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Usage: ", NamedTextColor.GOLD)
                .append(Component.text("Hold in off-hand and right-click a Water Cauldron with molten metal.", NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + castType.getId() + "</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, castType.getId());
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_CAST, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.CAST_TYPE, PersistentDataType.STRING, castType.name());

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createSmeltery() {
        ItemStack item = new ItemStack(Material.BLAST_FURNACE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        meta.displayName(MINI_MESSAGE.deserialize("<gradient:#ff4500:#ffaa00>Tinker Smeltery Crucible</gradient>")
                .decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Heavy-duty metallurgical melting furnace.", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("⚠ Heat Source Requirement:", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Lava: 100% Speed (10% consume chance per melt)", NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Magma Block: 70% Speed (Infinite, non-consumable)", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Operation:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Melts raw ores into molten liquid buckets.", NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: mvtink_smeltery</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, "mvtink_smeltery");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_SMELTERY, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createProspectorBrush() {
        ItemStack item = new ItemStack(Material.BRUSH);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        meta.displayName(MINI_MESSAGE.deserialize("<gradient:#ffaa00:#ffff55>Archaeological Prospector Brush</gradient>")
                .decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Specialized geological excavation tool.", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Features:", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • +40% faster brushing speed.", NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • +15% increased extraction success chance.", NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • 2x chance to discover Rare, Epic & Legendary minerals.", NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • 50% chance to preserve bristle durability.", NamedTextColor.GREEN)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Right-click geological stones (Stone, Netherrack, End Stone)", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: mvtink_brush_prospector</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, "mvtink_brush_prospector");
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.PROSPECTOR_BRUSH, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createToolHead(@Nonnull TinkerMaterial material) {
        ItemStack item = new ItemStack(material.getProcessedVanillaMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:#ffffff:" + material.getColorHex() + ">"
                + material.getName() + " Tool Head</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Part: ", NamedTextColor.GRAY)
                .append(Component.text("Modular Tool Head (Cabeza)", NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Material: ", NamedTextColor.GRAY)
                .append(Component.text(material.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Head Properties:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Mining Speed: " + material.getMiningSpeed() + "x", NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Damage: +" + material.getAttackDamage(), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Primary Trait: " + material.getTraitName(), NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("    " + material.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Combine in the Multiverse Forge Anvil to assemble.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_head</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_head");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TOOL_PART, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_PART_TYPE, PersistentDataType.STRING, ToolPartType.HEAD.name());

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createToolRod(@Nonnull TinkerMaterial material) {
        Material rodMat = switch (material.getOrigin()) {
            case NETHER -> Material.BLAZE_ROD;
            case THE_END -> Material.BREEZE_ROD;
            default -> Material.STICK;
        };

        ItemStack item = new ItemStack(rodMat);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:#ffffff:" + material.getColorHex() + ">"
                + material.getName() + " Tool Rod</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Part: ", NamedTextColor.GRAY)
                .append(Component.text("Modular Tool Handle/Rod (Palo)", NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Material: ", NamedTextColor.GRAY)
                .append(Component.text(material.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Rod Properties:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Base Durability: +" + material.getDurability(), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Handle Trait: " + material.getTraitName(), NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("    " + material.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Combine in the Multiverse Forge Anvil to assemble.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_rod</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_rod");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TOOL_PART, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_PART_TYPE, PersistentDataType.STRING, ToolPartType.ROD.name());

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createToolBinding(@Nonnull TinkerMaterial material) {
        Material bindingMat = switch (material.getOrigin()) {
            case NETHER -> Material.MAGMA_CREAM;
            case THE_END -> Material.PHANTOM_MEMBRANE;
            default -> Material.LEATHER;
        };

        ItemStack item = new ItemStack(bindingMat);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String displayNameMini = "<gradient:#ffffff:" + material.getColorHex() + ">"
                + material.getName() + " Tool Binding</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Part: ", NamedTextColor.GRAY)
                .append(Component.text("Modular Tool Binding (Mango/Unión)", NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Material: ", NamedTextColor.GRAY)
                .append(Component.text(material.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Binding Properties:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Auxiliary Durability: +" + (material.getDurability() / 2), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Secondary Trait: " + material.getTraitName(), NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("    " + material.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Combine in the Multiverse Forge Anvil to assemble.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_binding</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, material.getId() + "_binding");
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, material.getId());
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TOOL_PART, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_PART_TYPE, PersistentDataType.STRING, ToolPartType.BINDING.name());

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createModularTool(@Nonnull ModularToolType toolType,
                                              @Nonnull TinkerMaterial head,
                                              @Nonnull TinkerMaterial rod,
                                              @Nonnull TinkerMaterial binding) {
        ItemStack item = new ItemStack(toolType.getBaseMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        int totalDurability = head.getDurability() + rod.getDurability() + (binding.getDurability() / 2);
        double miningSpeed = head.getMiningSpeed();
        double attackDamage = 4.0 + head.getAttackDamage() + (rod.getAttackDamage() / 3.0);

        String displayNameMini = "<gradient:" + head.getColorHex() + ":" + rod.getColorHex() + ">"
                + head.getName() + " " + toolType.getDisplayName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Modular Forged Equipment", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("• Head: ", NamedTextColor.GRAY)
                .append(Component.text(head.getName(), NamedTextColor.WHITE))
                .append(Component.text(" (" + head.getTraitName() + ")", NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("• Handle: ", NamedTextColor.GRAY)
                .append(Component.text(rod.getName(), NamedTextColor.WHITE))
                .append(Component.text(" (" + rod.getTraitName() + ")", NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("• Binding: ", NamedTextColor.GRAY)
                .append(Component.text(binding.getName(), NamedTextColor.WHITE))
                .append(Component.text(" (" + binding.getTraitName() + ")", NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Modular Attributes:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability: " + totalDurability + " / " + totalDurability, NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Mining Speed: " + String.format(java.util.Locale.US, "%.1f", miningSpeed) + "x", NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Damage: +" + String.format(java.util.Locale.US, "%.1f", attackDamage), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Traits:", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + head.getTraitName() + ": " + head.getTraitDescription(), NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        if (!rod.getId().equals(head.getId())) {
            lore.add(Component.text("  • " + rod.getTraitName() + ": " + rod.getTraitDescription(), NamedTextColor.DARK_AQUA)
                    .decoration(TextDecoration.ITALIC, false));
        }
        if (!binding.getId().equals(head.getId()) && !binding.getId().equals(rod.getId())) {
            lore.add(Component.text("  • " + binding.getTraitName() + ": " + binding.getTraitDescription(), NamedTextColor.DARK_AQUA)
                    .decoration(TextDecoration.ITALIC, false));
        }
        lore.add(Component.empty());
        lore.add(Component.text("⚒ Forged in the Multiverse Forge Anvil", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.setUnbreakable(false);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING, toolType.name());
        pdc.set(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING, head.getId());
        pdc.set(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING, rod.getId());
        pdc.set(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING, binding.getId());
        pdc.set(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_MINING_SPEED, PersistentDataType.FLOAT, (float) miningSpeed);
        pdc.set(TinkerKeys.TOOL_ATTACK_DAMAGE, PersistentDataType.FLOAT, (float) attackDamage);

        item.setItemMeta(meta);
        return item;
    }
}
