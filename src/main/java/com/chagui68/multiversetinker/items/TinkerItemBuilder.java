package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
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
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                .append(Component.text(String.format(Locale.US, "%.1fx", material.getMiningSpeed()), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  Attack Bonus: ", NamedTextColor.GRAY)
                .append(Component.text(String.format(Locale.US, "+%.1f", material.getAttackDamageBonus()), NamedTextColor.RED))
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
                .append(Component.text("Place in Forge Anvil with molten or solid metals to forge parts.", NamedTextColor.WHITE))
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
    public static ItemStack createModularPart(@Nonnull ToolPartType partType, @Nonnull PartComposition composition) {
        TinkerMaterial primary = composition.getPrimaryMaterial();
        Material vanillaBase = switch (partType) {
            case HEAD -> primary.getProcessedVanillaMaterial();
            case ROD -> switch (primary.getOrigin()) {
                case NETHER -> Material.BLAZE_ROD;
                case THE_END -> Material.BREEZE_ROD;
                default -> Material.STICK;
            };
            case BINDING -> switch (primary.getOrigin()) {
                case NETHER -> Material.MAGMA_CREAM;
                case THE_END -> Material.PHANTOM_MEMBRANE;
                default -> Material.LEATHER;
            };
            case BOW_LIMBS -> Material.STICK;
            case BOWSTRING -> Material.STRING;
            case SHIELD_PLATE -> primary.getBlockVanillaMaterial();
            case SHIELD_BOSS -> Material.IRON_BLOCK;
        };

        ItemStack item = new ItemStack(vanillaBase);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        String displayNameMini = "<gradient:#ffffff:" + primary.getColorHex() + ">"
                + primary.getName() + " " + partType.getDisplayName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Component: ", NamedTextColor.GRAY)
                .append(Component.text(partType.getDisplayName(), NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(partType.getDescription(), NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("✦ Part Properties:", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability Yield: +" + composition.getDurability(), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Speed Factor: " + String.format(Locale.US, "%.1fx", composition.getMiningSpeed()), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Impact: +" + String.format(Locale.US, "%.1f", composition.getAttackDamage()), NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        // Multi-material breakdown
        lore.addAll(composition.formatLore());

        lore.add(Component.empty());
        lore.add(Component.text("Combine in Multiverse Forge to assemble weapons & tools.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: mvtink_part_" + partType.getIdSuffix() + "</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.ITEM_ID, PersistentDataType.STRING, "mvtink_part_" + partType.getIdSuffix());
        pdc.set(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING, primary.getId());
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_TOOL_PART, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_PART_TYPE, PersistentDataType.STRING, partType.name());
        pdc.set(TinkerKeys.PART_COMPOSITION_DATA, PersistentDataType.STRING, composition.serialize());

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createToolHead(@Nonnull TinkerMaterial material) {
        return createModularPart(ToolPartType.HEAD, PartComposition.fromMaterials(List.of(material)));
    }

    @Nonnull
    public static ItemStack createToolRod(@Nonnull TinkerMaterial material) {
        return createModularPart(ToolPartType.ROD, PartComposition.fromMaterials(List.of(material)));
    }

    @Nonnull
    public static ItemStack createToolBinding(@Nonnull TinkerMaterial material) {
        return createModularPart(ToolPartType.BINDING, PartComposition.fromMaterials(List.of(material)));
    }

    @Nonnull
    public static ItemStack createModularWeapon(@Nonnull ModularWeaponType weaponType,
                                                @Nonnull PartComposition part1,
                                                @Nonnull PartComposition part2,
                                                @Nullable PartComposition part3,
                                                @Nonnull EvolutionTier tier,
                                                int killCount) {
        Material baseMat = weaponType.getBaseMaterial();
        if (weaponType == ModularWeaponType.SWORD || weaponType == ModularWeaponType.SPEAR) {
            baseMat = tier.getMatchingSwordMaterial();
        }

        ItemStack item = new ItemStack(baseMat);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        TinkerMaterial p1 = part1.getPrimaryMaterial();
        TinkerMaterial p2 = part2.getPrimaryMaterial();

        int totalDurability;
        double attackDamage;
        if (weaponType.isTwoPart()) {
            totalDurability = part1.getDurability() + part2.getDurability() + tier.getBonusDurability();
            attackDamage = 4.0 + part1.getAttackDamage() + (part2.getAttackDamage() / 2.0) + tier.getBonusDamage();
        } else {
            int p3Dur = (part3 != null) ? part3.getDurability() / 2 : 0;
            double p3Dmg = (part3 != null) ? part3.getAttackDamage() / 3.0 : 0.0;
            totalDurability = part1.getDurability() + part2.getDurability() + p3Dur + tier.getBonusDurability();
            attackDamage = 4.0 + part1.getAttackDamage() + p3Dmg + tier.getBonusDamage();
        }

        String displayNameMini = "<gradient:" + p1.getColorHex() + ":" + p2.getColorHex() + ">"
                + tier.getDisplayName() + " " + p1.getName() + " " + weaponType.getDisplayName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(MINI_MESSAGE.deserialize(tier.getMiniMessageTag()).decoration(TextDecoration.ITALIC, false));

        // Kill Tracker bar
        int nextKillReq = (tier.getNextTier() != null) ? tier.getNextTier().getKillRequirement() : -1;
        if (nextKillReq == -1) {
            lore.add(MINI_MESSAGE.deserialize("<gradient:#ffd700:#ff8c00>★ MASTER TIER ★ (" + killCount + " Total Kills)</gradient>").decoration(TextDecoration.ITALIC, false));
        } else {
            int prevMilestone = tier.getKillRequirement();
            int needed = nextKillReq - prevMilestone;
            int curProgress = Math.max(0, killCount - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            String barDisplay = "<green>" + "▮".repeat(bars) + "</green><gray>" + "▯".repeat(10 - bars) + "</gray> <yellow>"
                    + curProgress + "/" + needed + " Kills</yellow> <gray>(Next: " + tier.getNextTier().getDisplayName() + ")</gray>";
            lore.add(MINI_MESSAGE.deserialize(barDisplay).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Modular Attributes:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability: " + totalDurability + " / " + totalDurability, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Damage: +" + String.format(Locale.US, "%.1f", attackDamage), NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        lore.add(Component.text("✦ Weapon Composition:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + weaponType.getPart1Name() + ": ", NamedTextColor.GRAY)
                .append(Component.text(p1.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + weaponType.getPart2Name() + ": ", NamedTextColor.GRAY)
                .append(Component.text(p2.getName(), NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        if (!weaponType.isTwoPart() && part3 != null) {
            lore.add(Component.text("  • " + weaponType.getPart3Name() + ": ", NamedTextColor.GRAY)
                    .append(Component.text(part3.getPrimaryMaterial().getName(), NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Weapon Perk: ", NamedTextColor.GOLD)
                .append(Component.text(getWeaponPerkDescription(weaponType), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + p1.getTraitName() + ": " + p1.getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        if (!p2.getId().equals(p1.getId())) {
            lore.add(Component.text("  • " + p2.getTraitName() + ": " + p2.getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }
        if (part3 != null && !part3.getPrimaryMaterial().getId().equals(p1.getId()) && !part3.getPrimaryMaterial().getId().equals(p2.getId())) {
            lore.add(Component.text("  • " + part3.getPrimaryMaterial().getTraitName() + ": " + part3.getPrimaryMaterial().getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_MODULAR_WEAPON, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.WEAPON_TYPE, PersistentDataType.STRING, weaponType.name());
        pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, tier.name());
        pdc.set(TinkerKeys.KILL_COUNT, PersistentDataType.INTEGER, killCount);

        pdc.set(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING, part1.serialize());
        pdc.set(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING, part2.serialize());
        if (part3 != null) {
            pdc.set(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING, part3.serialize());
        }

        pdc.set(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING, p1.getId());
        pdc.set(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING, p2.getId());
        if (part3 != null) {
            pdc.set(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING, part3.getPrimaryMaterial().getId());
        }

        pdc.set(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_ATTACK_DAMAGE, PersistentDataType.FLOAT, (float) attackDamage);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createModularTool(@Nonnull ModularToolType toolType,
                                              @Nonnull PartComposition head,
                                              @Nonnull PartComposition rod,
                                              @Nonnull PartComposition binding,
                                              @Nonnull EvolutionTier tier,
                                              int blocksBroken) {
        Material baseMat = toolType.getBaseMaterial();
        switch (toolType) {
            case PICKAXE -> baseMat = tier.getMatchingPickaxeMaterial();
            case AXE -> baseMat = tier.getMatchingAxeMaterial();
            case SHOVEL -> baseMat = tier.getMatchingShovelMaterial();
            case HOE -> baseMat = tier.getMatchingHoeMaterial();
            default -> {}
        }

        ItemStack item = new ItemStack(baseMat);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        TinkerMaterial hMat = head.getPrimaryMaterial();
        TinkerMaterial rMat = rod.getPrimaryMaterial();
        TinkerMaterial bMat = binding.getPrimaryMaterial();

        int totalDurability = head.getDurability() + rod.getDurability() + (binding.getDurability() / 2) + tier.getBonusDurability();
        double miningSpeed = head.getMiningSpeed() * tier.getSpeedMultiplier();
        double attackDamage = 4.0 + head.getAttackDamage() + (rod.getAttackDamage() / 3.0) + tier.getBonusDamage();

        String displayNameMini = "<gradient:" + hMat.getColorHex() + ":" + rMat.getColorHex() + ">"
                + tier.getDisplayName() + " " + hMat.getName() + " " + toolType.getDisplayName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(MINI_MESSAGE.deserialize(tier.getMiniMessageTag()).decoration(TextDecoration.ITALIC, false));

        // Block Break Progress bar
        int nextBlockReq = (tier.getNextTier() != null) ? tier.getNextTier().getBlockBreakRequirement() : -1;
        if (nextBlockReq == -1) {
            lore.add(MINI_MESSAGE.deserialize("<gradient:#ffd700:#ff8c00>★ MASTER TIER ★ (" + blocksBroken + " Total Blocks Broken)</gradient>").decoration(TextDecoration.ITALIC, false));
        } else {
            int prevMilestone = tier.getBlockBreakRequirement();
            int needed = nextBlockReq - prevMilestone;
            int curProgress = Math.max(0, blocksBroken - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            String barDisplay = "<green>" + "▮".repeat(bars) + "</green><gray>" + "▯".repeat(10 - bars) + "</gray> <yellow>"
                    + curProgress + "/" + needed + " Blocks</yellow> <gray>(Next: " + tier.getNextTier().getDisplayName() + ")</gray>";
            lore.add(MINI_MESSAGE.deserialize(barDisplay).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Modular Attributes:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability: " + totalDurability + " / " + totalDurability, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Mining Speed: " + String.format(Locale.US, "%.1fx", miningSpeed), NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Damage: +" + String.format(Locale.US, "%.1f", attackDamage), NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        lore.add(Component.text("✦ Tool Composition:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Cabeza (Head): ", NamedTextColor.GRAY).append(Component.text(hMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Mango (Handle): ", NamedTextColor.GRAY).append(Component.text(rMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Pomo (Pommel): ", NamedTextColor.GRAY).append(Component.text(bMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Tool Perk: ", NamedTextColor.GOLD)
                .append(Component.text(getToolPerkDescription(toolType), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + hMat.getTraitName() + ": " + hMat.getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        if (!rMat.getId().equals(hMat.getId())) {
            lore.add(Component.text("  • " + rMat.getTraitName() + ": " + rMat.getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }
        if (!bMat.getId().equals(hMat.getId()) && !bMat.getId().equals(rMat.getId())) {
            lore.add(Component.text("  • " + bMat.getTraitName() + ": " + bMat.getTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }

        meta.lore(lore);

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.TOOL_TYPE, PersistentDataType.STRING, toolType.name());
        pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, tier.name());
        pdc.set(TinkerKeys.BLOCKS_BROKEN_COUNT, PersistentDataType.INTEGER, blocksBroken);

        pdc.set(TinkerKeys.TOOL_HEAD_COMP, PersistentDataType.STRING, head.serialize());
        pdc.set(TinkerKeys.TOOL_ROD_COMP, PersistentDataType.STRING, rod.serialize());
        pdc.set(TinkerKeys.TOOL_BINDING_COMP, PersistentDataType.STRING, binding.serialize());

        pdc.set(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING, hMat.getId());
        pdc.set(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING, rMat.getId());
        pdc.set(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING, bMat.getId());

        pdc.set(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_MINING_SPEED, PersistentDataType.FLOAT, (float) miningSpeed);
        pdc.set(TinkerKeys.TOOL_ATTACK_DAMAGE, PersistentDataType.FLOAT, (float) attackDamage);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    public static ItemStack createModularTool(@Nonnull ModularToolType toolType,
                                              @Nonnull TinkerMaterial head,
                                              @Nonnull TinkerMaterial rod,
                                              @Nonnull TinkerMaterial binding) {
        return createModularTool(toolType,
                PartComposition.fromMaterials(List.of(head)),
                PartComposition.fromMaterials(List.of(rod)),
                PartComposition.fromMaterials(List.of(binding)),
                EvolutionTier.WOOD,
                0);
    }

    @Nonnull
    private static String getWeaponPerkDescription(@Nonnull ModularWeaponType type) {
        return switch (type) {
            case SWORD -> "Sweeping Cleave: hits multiple adjacent foes and chains elemental traits.";
            case BOW -> "Infused Volley: arrows inherit limb and string elemental traits.";
            case CROSSBOW -> "Piercing Velocity: armor-penetrating bolts that trigger explosive impact.";
            case TRIDENT -> "Hydraulic Surge: releases lightning or geysers on strike in water or rain.";
            case SPEAR -> "Jousting Reach: extended attack range and +30% damage while sprinting.";
            case MACE -> "Seismic Smash: fall strikes produce crushing ground shockwaves.";
            case SHIELD -> "Retaliation Barrier: blocks reflect 35% damage and apply traits to attackers.";
        };
    }

    @Nonnull
    private static String getToolPerkDescription(@Nonnull ModularToolType type) {
        return switch (type) {
            case PICKAXE -> "Deep Vein Resonance: 15% chance for bonus ores and temporary mining haste.";
            case AXE -> "Lumber Cleave: fells whole logs and shatters enemy shields on critical hits.";
            case SHOVEL -> "Seismic Tremor: sneak-digging excavates a 3x3 area of loose blocks.";
            case HOE -> "Harvest Scythe: harvests 3x3 crops and replants seeds automatically.";
            case FISHING_ROD -> "Abyssal Dredge: deep water fishing has a 15% chance to hook rare minerals.";
            default -> "Elemental Utility";
        };
    }
}
