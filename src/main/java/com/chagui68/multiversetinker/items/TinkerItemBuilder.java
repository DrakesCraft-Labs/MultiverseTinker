package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.ArmorPerkProfile;
import com.chagui68.multiversetinker.tools.PrimeArmorState;
import com.chagui68.multiversetinker.tools.PrimeUltimate;
import com.chagui68.multiversetinker.tools.ToolPerkProfile;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import com.chagui68.multiversetinker.tools.WeaponPerkProfile;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TinkerItemBuilder {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    /** Armor always answers hits, so every armor trait block is labelled the same way. */
    private static final String ARMOR_TRAIT_CHANNEL = "when struck";

    /** Config key that decides whether the rolled attack damage replaces the vanilla one. */
    public static final String CONFIG_MODULAR_ATTACK_DAMAGE = "equipment.modular-attack-damage";

    /** Config key that decides whether the rolled armor protection replaces the vanilla one. */
    public static final String CONFIG_MODULAR_ARMOR_DEFENSE = "equipment.modular-armor-defense";

    /** Label of the lore row that promises an armor piece's mitigation, wrapped over several rows. */
    private static final String ARMOR_PERK_LABEL = "✦ Armor Perk: ";

    /** Config key of the mitigation one point of rolled surplus buys a slot. */
    public static final String CONFIG_PERK_SCALE_PER_POINT = "equipment.armor-perk.scale-per-point";

    /** Config key prefix of a slot's mitigation ceiling, e.g. {@code equipment.armor-perk.caps.helmet}. */
    public static final String CONFIG_PERK_CAP_PREFIX = "equipment.armor-perk.caps.";

    /**
     * What one extra point of rolled Defense and Toughness buys a slot's mitigation, as shipped.
     *
     * <p>Two percent is deliberate: a piece rolls the bulk of its surplus on a prime plate at
     * netherite tier, where the sum lands around 16 points, so a strong build climbs about a third of
     * the way from its floor to its ceiling instead of pinning it.</p>
     */
    public static final double DEFAULT_PERK_SCALE_PER_POINT = 0.02;

    /**
     * The share each slot mitigates before a single point of surplus is bought.
     *
     * <p>These are the percentages the perks shipped with when they were fixed numbers, so no build that
     * existed before the scaling got weaker. Leggings are absent on purpose: they answer a hit with
     * mobility, not mitigation, and have no share to print.</p>
     */
    private static final Map<ModularArmorType, Double> PERK_FLOORS = Map.of(
            ModularArmorType.HELMET, 0.30,
            ModularArmorType.CHESTPLATE, 0.25,
            ModularArmorType.BOOTS, 0.50);

    /**
     * The ceiling each floor climbs towards, as shipped.
     *
     * <p>Every one of them is a two-digit percentage, which is what keeps the perk sentence the same
     * width at every tier.</p>
     */
    public static final Map<ModularArmorType, Double> DEFAULT_PERK_CAPS = Map.of(
            ModularArmorType.HELMET, 0.65,
            ModularArmorType.CHESTPLATE, 0.60,
            ModularArmorType.BOOTS, 0.75);

    /** Namespace of the armor modifiers the plugin writes in place of the vanilla ones. */
    private static final String ARMOR_MODIFIER_NAMESPACE = "multiversetinker";

    private static volatile boolean modularAttackDamage = true;
    private static volatile boolean modularArmorDefense = true;

    /** The armor perk curve, replaced wholesale by {@link #configurePerkScaling} on every config read. */
    private static volatile double perkScalePerPoint = DEFAULT_PERK_SCALE_PER_POINT;
    private static volatile Map<ModularArmorType, Double> perkCaps = DEFAULT_PERK_CAPS;

    /**
     * Applies the offensive half of the equipment rules of {@code config.yml}.
     *
     * @param replaceVanillaDamage whether the mineral-rolled attack damage replaces the base
     *                             material's vanilla damage; {@code false} keeps vanilla combat
     *                             values while still stopping vanilla wear
     */
    public static void configureEquipment(boolean replaceVanillaDamage) {
        modularAttackDamage = replaceVanillaDamage;
    }

    /**
     * Applies the defensive half of the equipment rules of {@code config.yml}.
     *
     * @param replaceVanillaDefense whether the mineral-rolled Defense, Toughness and knockback
     *                              resistance replace the base material's vanilla armor values;
     *                              {@code false} keeps the protection of the tier's own material
     */
    public static void configureArmorDefense(boolean replaceVanillaDefense) {
        modularArmorDefense = replaceVanillaDefense;
    }

    /**
     * Applies the armor perk curve of {@code config.yml}.
     *
     * <p>How much of its signature threat a slot answers grows with the piece's rolled Defense and
     * Toughness, and a server may retune both ends of that curve without a rebuild: one step shared by
     * every slot, and a ceiling per slot. Values are clamped rather than trusted — a negative step would
     * invert the curve, and a share above 1 would promise more mitigation than there is damage — and a
     * slot the map leaves out keeps the ceiling it shipped with, so removing a key restores it.</p>
     *
     * @param scalePerPoint the mitigation one point of rolled surplus buys,
     *                      {@value #DEFAULT_PERK_SCALE_PER_POINT} as shipped
     * @param caps          the ceiling of each slot that prints a number
     */
    public static void configurePerkScaling(double scalePerPoint, @Nonnull Map<ModularArmorType, Double> caps) {
        perkScalePerPoint = Math.max(0.0, scalePerPoint);

        Map<ModularArmorType, Double> effective = new EnumMap<>(ModularArmorType.class);
        for (Map.Entry<ModularArmorType, Double> shipped : DEFAULT_PERK_CAPS.entrySet()) {
            Double configured = caps.get(shipped.getKey());
            double cap = configured == null ? shipped.getValue() : configured;
            effective.put(shipped.getKey(), Math.min(1.0, Math.max(0.0, cap)));
        }
        perkCaps = Map.copyOf(effective);
    }

    /** Restores the shipped defaults (rolled damage, rolled armor protection and the perk curve). */
    public static void resetEquipment() {
        configureEquipment(true);
        configureArmorDefense(true);
        configurePerkScaling(DEFAULT_PERK_SCALE_PER_POINT, DEFAULT_PERK_CAPS);
    }

    /** Whether a slot answers a hit with a share of it, and therefore has a curve to tune. */
    public static boolean printsMitigation(@Nonnull ModularArmorType armorType) {
        return PERK_FLOORS.containsKey(armorType);
    }

    /** The mitigation one point of rolled surplus currently buys, as {@code config.yml} sets it. */
    public static double getPerkScalePerPoint() {
        return perkScalePerPoint;
    }

    /**
     * The mitigation ceiling of a slot as configured.
     *
     * @return the ceiling, or {@code 0} for a slot that mitigates nothing at all
     */
    public static double getPerkCap(@Nonnull ModularArmorType armorType) {
        return perkCaps.getOrDefault(armorType, 0.0);
    }

    /** Whether forged equipment fights with the damage printed in its own lore. */
    public static boolean isModularAttackDamage() {
        return modularAttackDamage;
    }

    /** Whether forged armor defends with the protection printed in its own lore. */
    public static boolean isModularArmorDefense() {
        return modularArmorDefense;
    }

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
        String weaponChannel = weaponTraitChannel(weaponType);
        lore.add(traitLine(p1, p1.getWeaponTraitDescription(), weaponChannel));
        if (!p2.getId().equals(p1.getId())) {
            lore.add(traitLine(p2, p2.getWeaponTraitDescription(), weaponChannel));
        }
        if (part3 != null && !part3.getPrimaryMaterial().getId().equals(p1.getId()) && !part3.getPrimaryMaterial().getId().equals(p2.getId())) {
            lore.add(traitLine(part3.getPrimaryMaterial(), part3.getPrimaryMaterial().getWeaponTraitDescription(), weaponChannel));
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

        // Vanilla must not wear the weapon out: its own counter is the only durability it can lose.
        // The rolled attack damage is applied on the strike, where the live swing multipliers are known.
        applyUnbreakable(meta);

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

        // Material-driven tool perk: the head mineral's essence names and warps the signature mechanic.
        ToolPerkProfile toolPerk = ToolPerkProfile.of(toolType, head, rod, binding);
        lore.add(Component.empty());
        lore.add(Component.text("✦ Tool Perk: ", NamedTextColor.GOLD)
                .append(Component.text(toolPerk.getDisplayName() + ": ", NamedTextColor.AQUA))
                .append(Component.text(toolPerk.getDescription(), NamedTextColor.GRAY))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Essence Focus: ", NamedTextColor.DARK_GRAY)
                .append(Component.text(toolPerk.getFocusLine(), toolPerk.getColor()))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Mining Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        String toolChannel = toolTraitChannel(toolType);
        lore.add(traitLine(hMat, hMat.getToolTraitDescription(), toolChannel));
        if (!rMat.getId().equals(hMat.getId())) {
            lore.add(traitLine(rMat, rMat.getToolTraitDescription(), toolChannel));
        }
        if (!bMat.getId().equals(hMat.getId()) && !bMat.getId().equals(rMat.getId())) {
            lore.add(traitLine(bMat, bMat.getToolTraitDescription(), toolChannel));
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

        // Same rule as weapons: vanilla wear is off, the damage is applied on the strike.
        applyUnbreakable(meta);

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

    /**
     * One material trait row, labelled with the moment this equipment type actually fires it, so a
     * sword, a bow and a pair of boots never print the same trait block.
     */
    @Nonnull
    private static Component traitLine(@Nonnull TinkerMaterial material, @Nonnull String description,
                                       @Nonnull String channel) {
        return Component.text("  • " + material.getTraitName() + " ", NamedTextColor.DARK_AQUA)
                .append(Component.text("(" + channel + ") ", NamedTextColor.DARK_GRAY))
                .append(Component.text(description, NamedTextColor.DARK_AQUA))
                .decoration(TextDecoration.ITALIC, false);
    }

    /** When a weapon's material traits fire, per weapon type. */
    @Nonnull
    private static String weaponTraitChannel(@Nonnull ModularWeaponType type) {
        return switch (type) {
            case SWORD -> "on sweep";
            case BOW -> "on arrow hit";
            case CROSSBOW -> "on bolt impact";
            case TRIDENT -> "on surge";
            case SPEAR -> "on thrust";
            case MACE -> "on smash";
            case SHIELD -> "on block";
        };
    }

    /** When a tool's material traits fire, per tool type. */
    @Nonnull
    private static String toolTraitChannel(@Nonnull ModularToolType type) {
        return switch (type) {
            case PICKAXE -> "while mining";
            case AXE -> "while chopping";
            case SHOVEL -> "while digging";
            case HOE -> "while harvesting";
            case FISHING_ROD -> "while fishing";
            case SWORD -> "on sweep";
        };
    }

    /**
     * The defensive profile a forged armor piece rolls from its three minerals and its tier.
     *
     * <p>This is the single source of truth for the armor's numbers: the lore rows and the attribute
     * modifiers the server applies are both built from it, so the tooltip can never promise protection
     * the piece does not have. The plate's attack damage becomes Defense, the lining's Toughness, and
     * the trim's material count knockback resistance; the evolution tier adds its ordinal to Defense
     * and half of it to Toughness, which is why a piece defends harder as it levels up.</p>
     */
    public record ArmorStats(int defense, double toughness, double knockbackResistance) {}

    @Nonnull
    public static ArmorStats armorStats(@Nonnull ModularArmorType armorType,
                                        @Nonnull PartComposition plate,
                                        @Nonnull PartComposition lining,
                                        @Nonnull PartComposition trim,
                                        @Nonnull EvolutionTier tier) {
        int defense = armorType.getBaseDefense() + (int) Math.round(plate.getAttackDamage() / 3.0) + tier.ordinal();
        double toughness = armorType.getBaseToughness() + (lining.getAttackDamage() / 4.0) + (tier.ordinal() * 0.5);
        double knockback = (trim.getEntries().size() * 0.05) + (tier.ordinal() * 0.02);
        return new ArmorStats(defense, toughness, knockback);
    }

    /**
     * How much of its signature threat the slot mitigates, as a fraction: {@code 0.25} is "absorbs 25%".
     *
     * <p>These used to be fixed numbers, so a tin chestplate dampened a hammer blow exactly like a
     * prime-alloy one and the piece's own Defense and Toughness only mattered for the damage vanilla
     * already subtracted. The share now grows with the piece's roll: every point of Defense and
     * Toughness above what the bare slot rolls buys the configured step more, and each floor is the
     * percentage the perk shipped with, so no build that existed got weaker.</p>
     *
     * <p>The step and the ceilings come from {@code config.yml} ({@link #configurePerkScaling}), so a
     * server retunes the curve without a rebuild. The shipped ceilings keep every value a two-digit
     * percentage, which is what keeps the perk sentence the same width at every tier — a server that
     * raises one to {@code 1.0} trades that for a three-digit row. A ceiling below a slot's floor pins
     * the slot at the ceiling: what a server sets always wins over what the perk shipped with.</p>
     *
     * <p>Leggings answer a hit with mobility (Stride Momentum) rather than mitigation, so their share
     * is zero and only the other three slots have a number worth printing.</p>
     */
    public static double signatureMitigation(@Nonnull ModularArmorType armorType, @Nonnull ArmorStats stats) {
        Double floor = PERK_FLOORS.get(armorType);
        if (floor == null) return 0.0;

        double surplus = Math.max(0.0, stats.defense() - armorType.getBaseDefense())
                + Math.max(0.0, stats.toughness() - armorType.getBaseToughness());

        double cap = getPerkCap(armorType);
        return Math.min(cap, Math.min(floor, cap) + perkScalePerPoint * surplus);
    }

    /**
     * The stats a forged armor piece rolled, read back from the piece itself.
     *
     * <p>The three parts and the tier live on the item, so its protection can be recomputed wherever
     * it is worn instead of being cached somewhere that could drift away from the lore.</p>
     *
     * @return the piece's stats, or {@code null} when the item is not a forged armor piece
     */
    @Nullable
    public static ArmorStats armorStatsOf(@Nonnull ItemStack item, @Nonnull MaterialRegistry registry) {
        ItemMeta meta = item.getItemMeta();
        return meta == null ? null : armorStatsOf(meta.getPersistentDataContainer(), registry);
    }

    @Nullable
    private static ArmorStats armorStatsOf(@Nonnull PersistentDataContainer pdc,
                                           @Nonnull MaterialRegistry registry) {
        if (!pdc.has(TinkerKeys.IS_MODULAR_ARMOR, PersistentDataType.BYTE)) return null;
        ModularArmorType armorType = armorTypeOf(pdc);
        if (armorType == null) return null;

        PartComposition plate = composition(pdc, TinkerKeys.ARMOR_PLATE_COMP, registry);
        PartComposition lining = composition(pdc, TinkerKeys.ARMOR_LINING_COMP, registry);
        PartComposition trim = composition(pdc, TinkerKeys.ARMOR_TRIM_COMP, registry);
        if (plate == null || lining == null || trim == null) return null;

        EvolutionTier tier = EvolutionTier.fromString(pdc.getOrDefault(TinkerKeys.EVOLUTION_TIER,
                PersistentDataType.STRING, EvolutionTier.WOOD.name()));
        return armorStats(armorType, plate, lining, trim, tier);
    }

    /**
     * The fraction of its signature threat a forged armor piece mitigates, read back from the piece.
     *
     * <p>Combat asks the piece instead of carrying its own copy of the numbers, so what a player feels
     * when the hit lands is what the tooltip promised.</p>
     *
     * @return the fraction, or {@code 0} when the item is not a forged armor piece
     */
    public static double mitigationOf(@Nonnull ItemStack item, @Nonnull MaterialRegistry registry) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0.0;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        ModularArmorType armorType = armorTypeOf(pdc);
        ArmorStats stats = armorStatsOf(pdc, registry);
        return armorType == null || stats == null ? 0.0 : signatureMitigation(armorType, stats);
    }

    @Nullable
    private static ModularArmorType armorTypeOf(@Nonnull PersistentDataContainer pdc) {
        String kind = pdc.get(TinkerKeys.ARMOR_TYPE, PersistentDataType.STRING);
        if (kind == null) return null;
        try {
            return ModularArmorType.valueOf(kind);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    @Nullable
    private static PartComposition composition(@Nonnull PersistentDataContainer pdc,
                                               @Nonnull NamespacedKey key,
                                               @Nonnull MaterialRegistry registry) {
        String raw = pdc.get(key, PersistentDataType.STRING);
        return raw == null ? null : PartComposition.deserialize(raw, registry);
    }

    /**
     * Gives a forged armor piece the protection its own minerals rolled.
     *
     * <p>Vanilla decides how hard armor defends from the base material's default attribute modifiers,
     * so a piece forged from a tin plate but built on a netherite chestplate used to protect exactly
     * like netherite. Replacing those modifiers with the rolled Defense, Toughness and knockback
     * resistance makes the stats printed in the lore the ones the server actually applies. The vanilla
     * attribute tooltip is hidden as well, so the same numbers are not printed twice.</p>
     *
     * <p>Safe to call again on the same piece: it re-applies the modifiers and rewrites the armor rows
     * and the perk sentence of the lore in place, which is how an evolution tier refresh makes a piece
     * that has just grown stronger defend — and promise — with its new numbers.</p>
     */
    public static void applyArmorDefense(@Nonnull ItemStack item,
                                         @Nonnull ModularArmorType armorType,
                                         @Nonnull PartComposition plate,
                                         @Nonnull PartComposition lining,
                                         @Nonnull PartComposition trim,
                                         @Nonnull EvolutionTier tier) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        ArmorStats stats = armorStats(armorType, plate, lining, trim, tier);
        // Record the tier the piece is being armed for as well. Combat recomputes the perk share from the
        // piece's own data, so a lore and a modifier set that grew at a new tier must not sit next to a
        // stale tier, or the piece would print one promise and apply another.
        meta.getPersistentDataContainer().set(TinkerKeys.EVOLUTION_TIER, PersistentDataType.STRING, tier.name());
        if (modularArmorDefense) {
            meta.setAttributeModifiers(armorModifiers(armorType, stats));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            List<Component> rebuilt = new ArrayList<>(lore);
            setLoreRow(rebuilt, "  • Defense: ",
                    Component.text("  • Defense: +" + stats.defense() + " Armor Points", NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false),
                    "  • Durability: ");
            setLoreRow(rebuilt, "  • Toughness: ",
                    Component.text("  • Toughness: +" + String.format(Locale.US, "%.1f", stats.toughness()), NamedTextColor.BLUE)
                            .decoration(TextDecoration.ITALIC, false),
                    "  • Defense: ");
            setLoreRow(rebuilt, "  • Knockback Resistance: ",
                    Component.text("  • Knockback Resistance: +" + String.format(Locale.US, "%.0f%%", stats.knockbackResistance() * 100), NamedTextColor.LIGHT_PURPLE)
                            .decoration(TextDecoration.ITALIC, false),
                    "  • Toughness: ");
            setLoreBlock(rebuilt, ARMOR_PERK_LABEL, armorPerkRow(ArmorPerkProfile.of(armorType,
                    signatureMitigation(armorType, stats), plate, lining, trim)));
            meta.lore(rebuilt);
        }

        item.setItemMeta(meta);
    }

    /**
     * Builds the armor modifiers of a piece: its Defense, Toughness and knockback resistance, all
     * bound to the equipment slot the piece is worn in.
     */
    @Nonnull
    private static Multimap<Attribute, AttributeModifier> armorModifiers(@Nonnull ModularArmorType armorType,
                                                                        @Nonnull ArmorStats stats) {
        EquipmentSlotGroup group = switch (armorType) {
            case HELMET -> EquipmentSlotGroup.HEAD;
            case CHESTPLATE -> EquipmentSlotGroup.CHEST;
            case LEGGINGS -> EquipmentSlotGroup.LEGS;
            case BOOTS -> EquipmentSlotGroup.FEET;
        };

        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(Attribute.ARMOR, new AttributeModifier(
                new NamespacedKey(ARMOR_MODIFIER_NAMESPACE, "modular_armor"), stats.defense(),
                AttributeModifier.Operation.ADD_NUMBER, group));
        modifiers.put(Attribute.ARMOR_TOUGHNESS, new AttributeModifier(
                new NamespacedKey(ARMOR_MODIFIER_NAMESPACE, "modular_armor_toughness"), stats.toughness(),
                AttributeModifier.Operation.ADD_NUMBER, group));
        if (stats.knockbackResistance() > 0) {
            modifiers.put(Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(
                    new NamespacedKey(ARMOR_MODIFIER_NAMESPACE, "modular_armor_knockback"),
                    stats.knockbackResistance(), AttributeModifier.Operation.ADD_NUMBER, group));
        }
        return modifiers;
    }

    /**
     * Replaces the lore row carrying {@code label}, or inserts it after {@code anchorLabel} when the
     * piece does not have it yet, so a tier upgrade can add a row an earlier tier never printed.
     */
    private static void setLoreRow(@Nonnull List<Component> lore, @Nonnull String label,
                                   @Nonnull Component value, @Nonnull String anchorLabel) {
        int anchor = -1;
        for (int i = 0; i < lore.size(); i++) {
            String row = PLAIN.serialize(lore.get(i));
            if (row.startsWith(label)) {
                lore.set(i, value);
                return;
            }
            if (row.startsWith(anchorLabel)) anchor = i;
        }
        lore.add(Math.min(lore.size(), anchor + 1), value);
    }

    /**
     * Replaces a lore block that word-wrapping may have spread over several rows.
     *
     * <p>A row longer than the line budget is stored as the rows it wrapped into, so rewriting it in
     * place means finding the row that opens the block and dropping the continuation rows after it. The
     * block ends at the next row that opens something of its own — a blank separator, a {@code ✦}
     * section or a {@code •} bullet — which is what stops a refresh from eating the rows below it.</p>
     */
    private static void setLoreBlock(@Nonnull List<Component> lore, @Nonnull String label,
                                     @Nonnull Component value) {
        int start = -1;
        for (int i = 0; i < lore.size(); i++) {
            if (PLAIN.serialize(lore.get(i)).startsWith(label)) {
                start = i;
                break;
            }
        }
        if (start < 0) return;

        int end = start + 1;
        while (end < lore.size() && isContinuationRow(PLAIN.serialize(lore.get(end)))) {
            end++;
        }
        lore.subList(start, end).clear();
        lore.addAll(start, LoreWrap.wrap(value));
    }

    /** A wrapped continuation row: body text that neither opens a section nor starts a bullet. */
    private static boolean isContinuationRow(@Nonnull String row) {
        return !row.isEmpty() && !row.startsWith("✦") && !row.startsWith("  • ");
    }

    /** The one lore row that announces an armor piece's perk and the share it now mitigates. */
    @Nonnull
    private static Component armorPerkRow(@Nonnull ArmorPerkProfile perk) {
        return Component.text(ARMOR_PERK_LABEL, NamedTextColor.GOLD)
                .append(Component.text(perk.getDisplayName() + ": ", NamedTextColor.AQUA))
                .append(Component.text(perk.getDescription(), NamedTextColor.GRAY))
                .decoration(TextDecoration.ITALIC, false);
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
        ArmorStats stats = armorStats(armorType, plate, lining, trim, tier);

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
        lore.add(Component.text("  • Defense: +" + stats.defense() + " Armor Points", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Toughness: +" + String.format(Locale.US, "%.1f", stats.toughness()), NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false));
        if (stats.knockbackResistance() > 0) {
            lore.add(Component.text("  • Knockback Resistance: +" + String.format(Locale.US, "%.0f%%", stats.knockbackResistance() * 100), NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.empty());
        lore.add(Component.text("✦ Armor Composition:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Plate: ", NamedTextColor.GRAY).append(Component.text(pMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Lining: ", NamedTextColor.GRAY).append(Component.text(lMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("  • Armor Trim: ", NamedTextColor.GRAY).append(Component.text(tMat.getName(), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));

        // Material-driven armor perk: the plate mineral's essence colours the slot's defense, and the
        // piece's own rolled protection decides how much of the threat it actually mitigates.
        ArmorPerkProfile armorPerk = ArmorPerkProfile.of(armorType, signatureMitigation(armorType, stats),
                plate, lining, trim);
        lore.add(Component.empty());
        lore.add(armorPerkRow(armorPerk));
        lore.add(Component.text("  • Essence Focus: ", NamedTextColor.DARK_GRAY)
                .append(Component.text(armorPerk.getFocusLine(), armorPerk.getColor()))
                .decoration(TextDecoration.ITALIC, false));

        lore.add(Component.empty());
        lore.add(Component.text("✦ Active Defensive Traits:", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(traitLine(pMat, pMat.getArmorTraitDescription(), ARMOR_TRAIT_CHANNEL));
        if (!lMat.getId().equals(pMat.getId())) {
            lore.add(traitLine(lMat, lMat.getArmorTraitDescription(), ARMOR_TRAIT_CHANNEL));
        }
        if (!tMat.getId().equals(pMat.getId()) && !tMat.getId().equals(lMat.getId())) {
            lore.add(traitLine(tMat, tMat.getArmorTraitDescription(), ARMOR_TRAIT_CHANNEL));
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

        applyUnbreakable(meta);

        item.setItemMeta(meta);
        // The piece defends with the numbers it just printed, not with the base material's vanilla ones.
        applyArmorDefense(item, armorType, plate, lining, trim, tier);
        return item;
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
    /**
     * Keeps vanilla durability out of modular equipment.
     *
     * <p>Forged gear tracks its own counter, so if the server were allowed to spend vanilla durability
     * a sword would wear on every swing and break on a vanilla schedule while the modular counter sat
     * untouched. Marking the item unbreakable hands durability entirely to the plugin, and the flag is
     * hidden so the client does not print an "Unbreakable" row either.</p>
     */
    private static void applyUnbreakable(@Nonnull ItemMeta meta) {
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    }

    /**
     * Rewrites the {@code • Durability: current / max} row of a modular item in place.
     *
     * <p>Because the item is unbreakable for the server, the vanilla durability bar no longer shows
     * wear; this row is the item's durability readout instead. It is located by its text, so the
     * wrapped lore around it can change without breaking the update, and it is coloured by how much
     * of the piece is left.</p>
     */
    public static void updateDurabilityLine(@Nonnull ItemStack item, int current, int max) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        List<Component> lore = meta.lore();
        if (lore == null || lore.isEmpty()) return;

        String label = "  • Durability: ";
        for (int i = 0; i < lore.size(); i++) {
            if (!PLAIN.serialize(lore.get(i)).startsWith(label)) continue;

            double ratio = max <= 0 ? 1.0 : (double) current / max;
            NamedTextColor color = ratio > 0.5 ? NamedTextColor.GREEN
                    : ratio > 0.2 ? NamedTextColor.YELLOW : NamedTextColor.RED;

            List<Component> rebuilt = new ArrayList<>(lore);
            rebuilt.set(i, Component.text(label + Math.max(0, current) + " / " + max, color)
                    .decoration(TextDecoration.ITALIC, false));
            meta.lore(rebuilt);
            item.setItemMeta(meta);
            return;
        }
    }

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
