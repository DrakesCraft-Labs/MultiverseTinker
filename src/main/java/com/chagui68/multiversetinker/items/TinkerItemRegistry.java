package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TinkerItemRegistry {

    private final MaterialRegistry materialRegistry;

    private final Map<String, ItemStack> rawItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> ingotItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> nuggetItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> blockItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> moltenBucketItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> toolHeadItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> toolRodItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> toolBindingItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> bowLimbsItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> bowstringItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> shieldPlateItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> shieldBossItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> armorPlateItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> armorLiningItems = new ConcurrentHashMap<>();
    private final Map<String, ItemStack> armorTrimItems = new ConcurrentHashMap<>();
    private final Map<CastType, ItemStack> castItems = new EnumMap<>(CastType.class);

    private final Map<String, ItemStack> allItemsById = new ConcurrentHashMap<>();

    private ItemStack smelteryItem;
    private ItemStack prospectorBrush;

    public TinkerItemRegistry(@Nonnull MaterialRegistry materialRegistry) {
        this.materialRegistry = materialRegistry;
        reload();
    }

    public void reload() {
        rawItems.clear();
        ingotItems.clear();
        nuggetItems.clear();
        blockItems.clear();
        moltenBucketItems.clear();
        toolHeadItems.clear();
        toolRodItems.clear();
        toolBindingItems.clear();
        bowLimbsItems.clear();
        bowstringItems.clear();
        shieldPlateItems.clear();
        shieldBossItems.clear();
        armorPlateItems.clear();
        armorLiningItems.clear();
        armorTrimItems.clear();
        castItems.clear();
        allItemsById.clear();

        this.smelteryItem = TinkerItemBuilder.createSmeltery();
        allItemsById.put("mvtink_smeltery", smelteryItem);

        this.prospectorBrush = TinkerItemBuilder.createProspectorBrush();
        allItemsById.put("mvtink_brush_prospector", prospectorBrush);

        for (CastType castType : CastType.values()) {
            ItemStack cast = TinkerItemBuilder.createCast(castType);
            castItems.put(castType, cast);
            allItemsById.put(castType.getId().toLowerCase(Locale.ROOT), cast);
        }

        for (TinkerMaterial material : materialRegistry.getAll()) {
            String baseId = material.getId().toLowerCase(Locale.ROOT);
            PartComposition comp = PartComposition.fromMaterials(List.of(material));

            ItemStack raw = TinkerItemBuilder.createRawMineral(material);
            ItemStack ingot = TinkerItemBuilder.createIngot(material);
            ItemStack nugget = TinkerItemBuilder.createNugget(material);
            ItemStack block = TinkerItemBuilder.createBlock(material);
            ItemStack moltenBucket = TinkerItemBuilder.createMoltenBucket(material);
            ItemStack head = TinkerItemBuilder.createModularPart(ToolPartType.HEAD, comp);
            ItemStack rod = TinkerItemBuilder.createModularPart(ToolPartType.ROD, comp);
            ItemStack binding = TinkerItemBuilder.createModularPart(ToolPartType.BINDING, comp);
            ItemStack bowLimbs = TinkerItemBuilder.createModularPart(ToolPartType.BOW_LIMBS, comp);
            ItemStack bowstring = TinkerItemBuilder.createModularPart(ToolPartType.BOWSTRING, comp);
            ItemStack shieldPlate = TinkerItemBuilder.createModularPart(ToolPartType.SHIELD_PLATE, comp);
            ItemStack shieldBoss = TinkerItemBuilder.createModularPart(ToolPartType.SHIELD_BOSS, comp);
            ItemStack armorPlate = TinkerItemBuilder.createModularPart(ToolPartType.ARMOR_PLATE, comp);
            ItemStack armorLining = TinkerItemBuilder.createModularPart(ToolPartType.ARMOR_LINING, comp);
            ItemStack armorTrim = TinkerItemBuilder.createModularPart(ToolPartType.ARMOR_TRIM, comp);

            rawItems.put(baseId, raw);
            ingotItems.put(baseId, ingot);
            nuggetItems.put(baseId, nugget);
            blockItems.put(baseId, block);
            moltenBucketItems.put(baseId, moltenBucket);
            toolHeadItems.put(baseId, head);
            toolRodItems.put(baseId, rod);
            toolBindingItems.put(baseId, binding);
            bowLimbsItems.put(baseId, bowLimbs);
            bowstringItems.put(baseId, bowstring);
            shieldPlateItems.put(baseId, shieldPlate);
            shieldBossItems.put(baseId, shieldBoss);
            armorPlateItems.put(baseId, armorPlate);
            armorLiningItems.put(baseId, armorLining);
            armorTrimItems.put(baseId, armorTrim);

            allItemsById.put(baseId + "_raw", raw);
            allItemsById.put(baseId + "_ingot", ingot);
            allItemsById.put(baseId + "_processed", ingot);
            allItemsById.put(baseId + "_nugget", nugget);
            allItemsById.put(baseId + "_block", block);
            allItemsById.put(baseId + "_molten_bucket", moltenBucket);
            allItemsById.put(baseId + "_head", head);
            allItemsById.put(baseId + "_rod", rod);
            allItemsById.put(baseId + "_handle", rod);
            allItemsById.put(baseId + "_binding", binding);
            allItemsById.put(baseId + "_pommel", binding);
            allItemsById.put(baseId + "_bow_limbs", bowLimbs);
            allItemsById.put(baseId + "_bowstring", bowstring);
            allItemsById.put(baseId + "_shield_plate", shieldPlate);
            allItemsById.put(baseId + "_shield_boss", shieldBoss);
            allItemsById.put(baseId + "_armor_plate", armorPlate);
            allItemsById.put(baseId + "_armor_lining", armorLining);
            allItemsById.put(baseId + "_armor_trim", armorTrim);
            allItemsById.put(baseId, raw);
        }
    }

    @Nullable
    public ItemStack getToolHeadItem(@Nonnull String materialId) {
        ItemStack item = toolHeadItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getToolRodItem(@Nonnull String materialId) {
        ItemStack item = toolRodItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getToolBindingItem(@Nonnull String materialId) {
        ItemStack item = toolBindingItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getPartItem(@Nonnull ToolPartType partType, @Nonnull String materialId) {
        String id = materialId.toLowerCase(Locale.ROOT);
        ItemStack item = switch (partType) {
            case HEAD -> toolHeadItems.get(id);
            case ROD -> toolRodItems.get(id);
            case BINDING -> toolBindingItems.get(id);
            case BOW_LIMBS -> bowLimbsItems.get(id);
            case BOWSTRING -> bowstringItems.get(id);
            case SHIELD_PLATE -> shieldPlateItems.get(id);
            case SHIELD_BOSS -> shieldBossItems.get(id);
            case ARMOR_PLATE -> armorPlateItems.get(id);
            case ARMOR_LINING -> armorLiningItems.get(id);
            case ARMOR_TRIM -> armorTrimItems.get(id);
        };
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getRawItem(@Nonnull String materialId) {
        ItemStack item = rawItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getIngotItem(@Nonnull String materialId) {
        String key = materialId.toLowerCase(Locale.ROOT);
        ItemStack item = ingotItems.get(key);
        if (item == null) {
            TinkerMaterial tm = materialRegistry.get(key);
            if (tm != null) {
                item = TinkerItemBuilder.createIngot(tm);
                ingotItems.put(key, item);
                allItemsById.put(key + "_ingot", item);
                allItemsById.put(key + "_processed", item);
            }
        }
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getNuggetItem(@Nonnull String materialId) {
        ItemStack item = nuggetItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getBlockItem(@Nonnull String materialId) {
        ItemStack item = blockItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getMoltenBucketItem(@Nonnull String materialId) {
        ItemStack item = moltenBucketItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getCastItem(@Nonnull CastType castType) {
        ItemStack item = castItems.get(castType);
        return item != null ? item.clone() : null;
    }

    @Nonnull
    public ItemStack getSmelteryItem() {
        return smelteryItem.clone();
    }

    @Nonnull
    public ItemStack getProspectorBrush() {
        return prospectorBrush.clone();
    }

    @Nullable
    public ItemStack getItemById(@Nonnull String id) {
        ItemStack item = allItemsById.get(id.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nonnull
    public Set<String> getAllItemIds() {
        return Collections.unmodifiableSet(allItemsById.keySet());
    }
}
