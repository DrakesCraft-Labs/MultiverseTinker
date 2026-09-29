package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.WeaponPerkProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TinkerItemBuilder {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

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

        meta.lore(LoreWrap.wrapAll(lore));
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

        boolean prime = AlloyRegistry.isPrime(material);
        if (prime) {
            lore.add(Component.text("✦ Prime Alloy: ", NamedTextColor.GOLD)
                    .append(Component.text(PrimeUltimate.of(material).getDisplayName() + " ultimate", NamedTextColor.AQUA))
                    .append(Component.text("  ·  ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(PrimeArmorState.of(material).getDisplayName() + " state", NamedTextColor.LIGHT_PURPLE))
                    .decoration(TextDecoration.ITALIC, false));
        } else if (AlloyRegistry.isCatalyst(material)) {
            lore.add(Component.text("❖ Crucible Catalyst: ", NamedTextColor.GOLD)
                    .append(Component.text("pair with a legendary alloy", NamedTextColor.AQUA))
                    .decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: " + material.getId() + "_ingot</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(LoreWrap.wrapAll(lore));
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

        meta.lore(LoreWrap.wrapAll(lore));

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

        meta.lore(LoreWrap.wrapAll(lore));

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

        meta.lore(LoreWrap.wrapAll(lore));

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

        meta.lore(LoreWrap.wrapAll(lore));

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

        meta.lore(LoreWrap.wrapAll(lore));

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

        meta.lore(LoreWrap.wrapAll(lore));

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
            case ARMOR_PLATE -> primary.getBlockVanillaMaterial();
            case ARMOR_LINING -> Material.CHAINMAIL_CHESTPLATE;
            case ARMOR_TRIM -> Material.IRON_NUGGET;
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
        lore.add(Component.text("✦ Mineral Affinities: ", NamedTextColor.GOLD)
                .append(Component.text(describeAffinities(collectAffinities(composition)), NamedTextColor.LIGHT_PURPLE))
                .decoration(TextDecoration.ITALIC, false));
        lore.addAll(primeLore(composition));

        lore.add(Component.empty());
        lore.add(Component.text("Combine in Multiverse Forge to assemble weapons & tools.", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(MINI_MESSAGE.deserialize("<dark_gray>ID: mvtink_part_" + partType.getIdSuffix() + "</dark_gray>")
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(LoreWrap.wrapAll(lore));
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
        if (weaponType == ModularWeaponType.SWORD) {
            baseMat = tier.getMatchingSwordMaterial();
        } else if (weaponType == ModularWeaponType.SPEAR) {
            baseMat = tier.getMatchingSpearMaterial();
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
            lore.add(MINI_MESSAGE.deserialize(masterTierLine("Kills", killCount)).decoration(TextDecoration.ITALIC, false));
        } else {
            int prevMilestone = tier.getKillRequirement();
            int needed = Math.max(1, nextKillReq - prevMilestone);
            int curProgress = Math.max(0, killCount - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            lore.add(MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Kills", tier.getNextTier().getDisplayName()))
                    .decoration(TextDecoration.ITALIC, false));
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

        // Material-driven perk: the essence of the forged minerals names and warps the signature mechanic.
        WeaponPerkProfile perkProfile = WeaponPerkProfile.of(weaponType, part1, part2, part3);
        lore.add(Component.empty());
        lore.add(Component.text("✦ Weapon Perk: ", NamedTextColor.GOLD)
                .append(Component.text(perkProfile.getDisplayName() + ": ", NamedTextColor.AQUA))
                .append(Component.text(perkProfile.getDescription(), NamedTextColor.GRAY))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Essence Focus: ", NamedTextColor.DARK_GRAY)
                .append(Component.text(perkProfile.getFocusLine(), perkProfile.getColor()))
                .decoration(TextDecoration.ITALIC, false));

        Set<String> uniqueMatIds = getUniqueMaterialIds(part1, part2, part3);
        if (uniqueMatIds.size() >= 2) {
            lore.add(Component.empty());
            lore.add(Component.text("✦ Hybrid Synergy: ", NamedTextColor.GOLD)
                    .append(MINI_MESSAGE.deserialize(getSynergyDisplayName(uniqueMatIds, p1, p2)))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • " + getSynergyDescription(uniqueMatIds), NamedTextColor.YELLOW)
                    .decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Combat Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + p1.getTraitName() + ": " + p1.getWeaponTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        if (!p2.getId().equals(p1.getId())) {
            lore.add(Component.text("  • " + p2.getTraitName() + ": " + p2.getWeaponTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }
        if (part3 != null && !part3.getPrimaryMaterial().getId().equals(p1.getId()) && !part3.getPrimaryMaterial().getId().equals(p2.getId())) {
            lore.add(Component.text("  • " + part3.getPrimaryMaterial().getTraitName() + ": " + part3.getPrimaryMaterial().getWeaponTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Mineral Affinities: ", NamedTextColor.GOLD)
                .append(Component.text(describeAffinities(collectAffinities(part1, part2, part3)), NamedTextColor.LIGHT_PURPLE))
                .decoration(TextDecoration.ITALIC, false));
        lore.addAll(primeLore(part1, part2, part3));

        // The first two rows are the tier tag and the kill progress bar: they are rewritten in place
        // on every kill, so they get the wider header budget instead of the tooltip budget.
        meta.lore(LoreWrap.wrapAll(lore, 2));

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
            lore.add(MINI_MESSAGE.deserialize(masterTierLine("Blocks Broken", blocksBroken)).decoration(TextDecoration.ITALIC, false));
        } else {
            int prevMilestone = tier.getBlockBreakRequirement();
            int needed = Math.max(1, nextBlockReq - prevMilestone);
            int curProgress = Math.max(0, blocksBroken - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            lore.add(MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Blocks", tier.getNextTier().getDisplayName()))
                    .decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Modular Attributes:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability: " + totalDurability + " / " + totalDurability, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Mining Speed: " + String.format(Locale.US, "%.1fx", miningSpeed), NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Attack Damage: +" + String.format(Locale.US, "%.1f", attackDamage), NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        lore.add(Component.text("✦ Tool Composition:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Head: ", NamedTextColor.GRAY).append(Component.text(hMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Handle: ", NamedTextColor.GRAY).append(Component.text(rMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Pommel: ", NamedTextColor.GRAY).append(Component.text(bMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Tool Perk: ", NamedTextColor.GOLD)
                .append(Component.text(getToolPerkDescription(toolType), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Mining Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + hMat.getTraitName() + ": " + hMat.getToolTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        if (!rMat.getId().equals(hMat.getId())) {
            lore.add(Component.text("  • " + rMat.getTraitName() + ": " + rMat.getToolTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }
        if (!bMat.getId().equals(hMat.getId()) && !bMat.getId().equals(rMat.getId())) {
            lore.add(Component.text("  • " + bMat.getTraitName() + ": " + bMat.getToolTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Mineral Affinities: ", NamedTextColor.GOLD)
                .append(Component.text(describeAffinities(collectAffinities(head, rod, binding)), NamedTextColor.LIGHT_PURPLE))
                .decoration(TextDecoration.ITALIC, false));
        lore.addAll(primeLore(head, rod, binding));

        // Header rows (tier tag + block progress bar) stay on one row so progress updates can
        // rewrite them in place.
        meta.lore(LoreWrap.wrapAll(lore, 2));

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
    private static Set<TraitAffinity> collectAffinities(@Nullable PartComposition... parts) {
        LinkedHashSet<TraitAffinity> affinities = new LinkedHashSet<>();
        for (PartComposition part : parts) {
            if (part == null) continue;
            for (PartComposition.Entry entry : part.getEntries()) {
                affinities.addAll(TraitAffinity.of(entry.material()));
            }
        }
        return affinities;
    }

    /**
     * Lore block for weapons, tools and armor forged with prime alloys: names the ultimate the
     * piece unleashes and the armor state it answers with, so the endgame build is readable at a
     * glance.
     */
    @Nonnull
    private static List<Component> primeLore(@Nullable PartComposition... parts) {
        LinkedHashSet<String> ultimates = new LinkedHashSet<>();
        LinkedHashSet<String> states = new LinkedHashSet<>();
        for (PartComposition part : parts) {
            if (part == null) continue;
            for (PartComposition.Entry entry : part.getEntries()) {
                TinkerMaterial material = entry.material();
                if (!AlloyRegistry.isPrime(material)) continue;
                ultimates.add(PrimeUltimate.of(material).getDisplayName());
                states.add(PrimeArmorState.of(material).getDisplayName());
            }
        }
        if (ultimates.isEmpty()) return List.of();

        List<Component> lore = new ArrayList<>(2);
        lore.add(Component.empty());
        lore.add(Component.text("✦ Prime Alloy: ", NamedTextColor.GOLD)
                .append(Component.text(String.join(" · ", ultimates) + " ultimate", NamedTextColor.AQUA))
                .append(Component.text("  ·  ", NamedTextColor.DARK_GRAY))
                .append(Component.text(String.join(" · ", states) + " state", NamedTextColor.LIGHT_PURPLE))
                .decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    @Nonnull
    private static String describeAffinities(@Nonnull Set<TraitAffinity> affinities) {
        StringBuilder builder = new StringBuilder();
        for (TraitAffinity affinity : affinities) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(affinity.getDisplayName());
        }
        return builder.length() == 0 ? "Primal" : builder.toString();
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

    @Nonnull
    public static ItemStack createModularArmor(@Nonnull ModularArmorType armorType,
                                               @Nonnull PartComposition plate,
                                               @Nonnull PartComposition lining,
                                               @Nonnull PartComposition trim,
                                               @Nonnull EvolutionTier tier,
                                               int damageAbsorbed) {
        Material baseMat = switch (armorType) {
            case HELMET -> tier.getMatchingHelmetMaterial();
            case CHESTPLATE -> tier.getMatchingChestplateMaterial();
            case LEGGINGS -> tier.getMatchingLeggingsMaterial();
            case BOOTS -> tier.getMatchingBootsMaterial();
        };

        ItemStack item = new ItemStack(baseMat);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        TinkerMaterial pMat = plate.getPrimaryMaterial();
        TinkerMaterial lMat = lining.getPrimaryMaterial();
        TinkerMaterial tMat = trim.getPrimaryMaterial();

        int totalDurability = armorType.getBaseDurability() + plate.getDurability() + lining.getDurability() + (trim.getDurability() / 2) + tier.getBonusDurability();
        int defensePoints = armorType.getBaseDefense() + (int) Math.round(plate.getAttackDamage() / 3.0) + tier.ordinal();
        double toughness = armorType.getBaseToughness() + (lining.getAttackDamage() / 4.0) + (tier.ordinal() * 0.5);
        double knockbackRes = (trim.getEntries().size() * 0.05) + (tier.ordinal() * 0.02);

        String displayNameMini = "<gradient:" + pMat.getColorHex() + ":" + lMat.getColorHex() + ">"
                + tier.getArmorDisplayName() + " " + pMat.getName() + " " + armorType.getDisplayName() + "</gradient>";
        meta.displayName(MINI_MESSAGE.deserialize(displayNameMini).decoration(TextDecoration.ITALIC, false));

        if (meta instanceof LeatherArmorMeta lam) {
            if (tier == EvolutionTier.STONE) {
                lam.setColor(Color.fromRGB(200, 100, 50)); // Polished Copper
            } else if (tier == EvolutionTier.WOOD) {
                lam.setColor(Color.fromRGB(160, 101, 64)); // Natural Tanned Leather
            }
        }

        List<Component> lore = new ArrayList<>();
        lore.add(MINI_MESSAGE.deserialize(tier.getArmorMiniMessageTag()).decoration(TextDecoration.ITALIC, false));

        // Damage Absorbed Progress Bar
        int nextDmgReq = (tier.getNextTier() != null) ? tier.getNextTier().getArmorDamageRequirement() : -1;
        if (nextDmgReq == -1) {
            lore.add(MINI_MESSAGE.deserialize(masterTierLine("Damage Absorbed", damageAbsorbed)).decoration(TextDecoration.ITALIC, false));
        } else {
            int prevMilestone = tier.getArmorDamageRequirement();
            int needed = Math.max(1, nextDmgReq - prevMilestone);
            int curProgress = Math.max(0, damageAbsorbed - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            lore.add(MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Damage Absorbed", tier.getNextTier().getArmorDisplayName()))
                    .decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Modular Attributes:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Durability: " + totalDurability + " / " + totalDurability, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Defense: +" + defensePoints + " Armor Points", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Toughness: +" + String.format(Locale.US, "%.1f", toughness), NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false));
        if (knockbackRes > 0) {
            lore.add(Component.text("  • Knockback Resistance: +" + String.format(Locale.US, "%.0f%%", knockbackRes * 100), NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Armor Composition:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Plate: ", NamedTextColor.GRAY).append(Component.text(pMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Lining: ", NamedTextColor.GRAY).append(Component.text(lMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Trim: ", NamedTextColor.GRAY).append(Component.text(tMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Armor Perk: ", NamedTextColor.GOLD)
                .append(Component.text(getArmorPerkDescription(armorType), NamedTextColor.AQUA))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Defensive Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • " + pMat.getTraitName() + ": " + pMat.getArmorTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        if (!lMat.getId().equals(pMat.getId())) {
            lore.add(Component.text("  • " + lMat.getTraitName() + ": " + lMat.getArmorTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }
        if (!tMat.getId().equals(pMat.getId()) && !tMat.getId().equals(lMat.getId())) {
            lore.add(Component.text("  • " + tMat.getTraitName() + ": " + tMat.getArmorTraitDescription(), NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Mineral Affinities: ", NamedTextColor.GOLD)
                .append(Component.text(describeAffinities(collectAffinities(plate, lining, trim)), NamedTextColor.LIGHT_PURPLE))
                .decoration(TextDecoration.ITALIC, false));
        lore.addAll(primeLore(plate, lining, trim));

        // Header rows (tier tag + damage progress bar) stay on one row so progress updates can
        // rewrite them in place.
        meta.lore(LoreWrap.wrapAll(lore, 2));

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(TinkerKeys.IS_TINKER_ITEM, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE, (byte) 1);
        pdc.set(TinkerKeys.ARMOR_TYPE, PersistentDataType.STRING, armorType.name());
        pdc.set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, tier.name());
        pdc.set(TinkerKeys.DAMAGE_ABSORBED, PersistentDataType.INTEGER, damageAbsorbed);

        pdc.set(TinkerKeys.ARMOR_PLATE_COMP, PersistentDataType.STRING, plate.serialize());
        pdc.set(TinkerKeys.ARMOR_LINING_COMP, PersistentDataType.STRING, lining.serialize());
        pdc.set(TinkerKeys.ARMOR_TRIM_COMP, PersistentDataType.STRING, trim.serialize());

        pdc.set(TinkerKeys.TOOL_HEAD_MAT, PersistentDataType.STRING, pMat.getId());
        pdc.set(TinkerKeys.TOOL_ROD_MAT, PersistentDataType.STRING, lMat.getId());
        pdc.set(TinkerKeys.TOOL_BINDING_MAT, PersistentDataType.STRING, tMat.getId());

        pdc.set(TinkerKeys.TOOL_MAX_DURABILITY, PersistentDataType.INTEGER, totalDurability);
        pdc.set(TinkerKeys.TOOL_CURRENT_DURABILITY, PersistentDataType.INTEGER, totalDurability);

        item.setItemMeta(meta);
        return item;
    }

    @Nonnull
    private static String getArmorPerkDescription(@Nonnull ModularArmorType type) {
        return switch (type) {
            case HELMET -> "Cranium Ward: reduces incoming headshot damage and grants hazard immunity.";
            case CHESTPLATE -> "Kinetic Dampener: absorbs 25% of heavy impacts and releases protective energy.";
            case LEGGINGS -> "Stride Momentum: mitigates sprint stamina drain and boosts movement recovery.";
            case BOOTS -> "Feathered Grounding: negates up to 50% fall damage and grants anti-slip traction.";
        };
    }

    public static void updateWeaponProgress(@Nonnull ItemStack weapon, @Nonnull EvolutionTier tier, int killCount) {
        ItemMeta meta = weapon.getItemMeta();
        if (meta == null) return;

        Component tierTag = MINI_MESSAGE.deserialize(tier.getMiniMessageTag()).decoration(TextDecoration.ITALIC, false);

        int nextKillReq = (tier.getNextTier() != null) ? tier.getNextTier().getKillRequirement() : -1;
        Component progress;
        if (nextKillReq == -1) {
            progress = MINI_MESSAGE.deserialize(masterTierLine("Kills", killCount)).decoration(TextDecoration.ITALIC, false);
        } else {
            int prevMilestone = tier.getKillRequirement();
            int needed = Math.max(1, nextKillReq - prevMilestone);
            int curProgress = Math.max(0, killCount - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            progress = MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Kills", tier.getNextTier().getDisplayName()))
                    .decoration(TextDecoration.ITALIC, false);
        }

        updateProgressHeader(meta, tierTag, progress);
        weapon.setItemMeta(meta);
    }

    public static void updateToolProgress(@Nonnull ItemStack tool, @Nonnull EvolutionTier tier, int blocksBroken) {
        ItemMeta meta = tool.getItemMeta();
        if (meta == null) return;

        Component tierTag = MINI_MESSAGE.deserialize(tier.getMiniMessageTag()).decoration(TextDecoration.ITALIC, false);

        int nextReq = (tier.getNextTier() != null) ? tier.getNextTier().getBlockBreakRequirement() : -1;
        Component progress;
        if (nextReq == -1) {
            progress = MINI_MESSAGE.deserialize(masterTierLine("Blocks", blocksBroken)).decoration(TextDecoration.ITALIC, false);
        } else {
            int prevMilestone = tier.getBlockBreakRequirement();
            int needed = Math.max(1, nextReq - prevMilestone);
            int curProgress = Math.max(0, blocksBroken - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            progress = MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Blocks", tier.getNextTier().getDisplayName()))
                    .decoration(TextDecoration.ITALIC, false);
        }

        updateProgressHeader(meta, tierTag, progress);
        tool.setItemMeta(meta);
    }

    public static void updateArmorProgress(@Nonnull ItemStack armor, @Nonnull EvolutionTier tier, int damageAbsorbed) {
        ItemMeta meta = armor.getItemMeta();
        if (meta == null) return;

        Component tierTag = MINI_MESSAGE.deserialize(tier.getArmorMiniMessageTag()).decoration(TextDecoration.ITALIC, false);

        int nextDmgReq = (tier.getNextTier() != null) ? tier.getNextTier().getArmorDamageRequirement() : -1;
        Component progress;
        if (nextDmgReq == -1) {
            progress = MINI_MESSAGE.deserialize(masterTierLine("Damage Absorbed", damageAbsorbed)).decoration(TextDecoration.ITALIC, false);
        } else {
            int prevMilestone = tier.getArmorDamageRequirement();
            int needed = Math.max(1, nextDmgReq - prevMilestone);
            int curProgress = Math.max(0, damageAbsorbed - prevMilestone);
            int bars = Math.min(10, Math.max(0, (int) Math.round(((double) curProgress / needed) * 10)));
            progress = MINI_MESSAGE.deserialize(progressBar(bars, curProgress, needed, "Damage Absorbed", tier.getNextTier().getArmorDisplayName()))
                    .decoration(TextDecoration.ITALIC, false);
        }

        updateProgressHeader(meta, tierTag, progress);
        armor.setItemMeta(meta);
    }

    /**
     * Rewrites the two lore rows that open a modular item: its tier tag and its progress bar.
     *
     * <p>Lore is word-wrapped when the item is forged, so the header is located by the blank
     * separator row that follows it instead of by fixed indices — a wrapped bar stays intact and the
     * rows below keep their place.</p>
     */
    private static void updateProgressHeader(@Nonnull ItemMeta meta, @Nonnull Component tierTag, @Nonnull Component progressLine) {
        List<Component> lore = meta.lore();
        if (lore == null || lore.isEmpty()) return;

        int boundary = 1;
        while (boundary < lore.size() && !PLAIN.serialize(lore.get(boundary)).isEmpty()) {
            boundary++;
        }

        List<Component> rebuilt = new ArrayList<>();
        rebuilt.add(tierTag);
        rebuilt.addAll(LoreWrap.wrapHeader(progressLine));
        if (boundary < lore.size()) {
            rebuilt.addAll(lore.subList(boundary, lore.size()));
        }
        meta.lore(rebuilt);
    }

    @Nonnull
    private static String masterTierLine(@Nonnull String metric, int total) {
        return "<gradient:#ffd700:#ff8c00>★ MASTER TIER ★ (" + total + " Total " + metric + ")</gradient>";
    }

    @Nonnull
    private static String progressBar(int bars, int current, int needed, @Nonnull String metric, @Nonnull String nextTierName) {
        return "<green>" + "▮".repeat(bars) + "</green><gray>" + "▯".repeat(10 - bars) + "</gray> <yellow>"
                + current + "/" + needed + " " + metric + "</yellow> <gray>(Next: " + nextTierName + ")</gray>";
    }

    @Nonnull
    public static Set<String> getUniqueMaterialIds(PartComposition... compositions) {
        Set<String> set = new LinkedHashSet<>();
        for (PartComposition comp : compositions) {
            if (comp != null) {
                for (PartComposition.Entry e : comp.getEntries()) {
                    set.add(e.material().getId().toLowerCase(Locale.ROOT));
                }
            }
        }
        return set;
    }

    @Nonnull
    public static String getSynergyDisplayName(@Nonnull Set<String> matIds, @Nonnull TinkerMaterial p1, @Nonnull TinkerMaterial p2) {
        if (matIds.contains("mvtink_zircon")) return "<gradient:#AF601A:#f39c12>Tectonic Rupture</gradient>";
        if (matIds.contains("mvtink_gold")) return "<gradient:#f1c40f:#ffffff>Midas Blessing</gradient>";
        if (matIds.contains("mvtink_ruby") && matIds.contains("mvtink_sapphire")) return "<gradient:#e0115f:#0f52ba>Thermal Flash</gradient>";
        if (matIds.contains("mvtink_obsidian")) return "<gradient:#2e1c4d:#9b59b6>Void Cleave</gradient>";
        if (matIds.contains("mvtink_netherite")) return "<gradient:#4a3b32:#e67e22>Ancient Primacy</gradient>";
        if (matIds.contains("mvtink_amethyst")) return "<gradient:#af7ac5:#e056fd>Harmonic Resonance</gradient>";
        return "<gradient:" + p1.getColorHex() + ":" + p2.getColorHex() + ">Composite Overcharge</gradient>";
    }

    @Nonnull
    public static String getSynergyDescription(@Nonnull Set<String> matIds) {
        if (matIds.contains("mvtink_zircon")) return "Cracking blows bypass 20% armor and trigger seismic deepslate vibrations.";
        if (matIds.contains("mvtink_gold")) return "Critical strikes erupt in auric radiant sparks dealing +25% bonus gold damage.";
        if (matIds.contains("mvtink_ruby") && matIds.contains("mvtink_sapphire")) return "Ignites and freezes targets simultaneously, triggering thermal shock vapor.";
        if (matIds.contains("mvtink_obsidian")) return "Cleaves through target defense, ignoring 25% armor and blast deflection.";
        if (matIds.contains("mvtink_netherite")) return "Ignites enemies in enduring soul flame and increases knockback resistance.";
        if (matIds.contains("mvtink_amethyst")) return "Critical impacts chime acoustic shockwaves damaging nearby foes.";
        return "Synchronizes multi-material elements for +15% amplified trait potency and chromatic visual auras.";
    }
}
