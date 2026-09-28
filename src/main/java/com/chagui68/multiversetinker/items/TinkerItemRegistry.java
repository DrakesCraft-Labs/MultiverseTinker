package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.api.CastType;
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

            ItemStack raw = TinkerItemBuilder.createRawMineral(material);
            ItemStack ingot = TinkerItemBuilder.createIngot(material);
            ItemStack nugget = TinkerItemBuilder.createNugget(material);
            ItemStack block = TinkerItemBuilder.createBlock(material);
            ItemStack moltenBucket = TinkerItemBuilder.createMoltenBucket(material);

            rawItems.put(baseId, raw);
            ingotItems.put(baseId, ingot);
            nuggetItems.put(baseId, nugget);
            blockItems.put(baseId, block);
            moltenBucketItems.put(baseId, moltenBucket);

            allItemsById.put(baseId + "_raw", raw);
            allItemsById.put(baseId + "_ingot", ingot);
            allItemsById.put(baseId + "_processed", ingot);
            allItemsById.put(baseId + "_nugget", nugget);
            allItemsById.put(baseId + "_block", block);
            allItemsById.put(baseId + "_molten_bucket", moltenBucket);
            allItemsById.put(baseId, raw); // Default to raw if only base ID provided
        }
    }

    @Nullable
    public ItemStack getRawItem(@Nonnull String materialId) {
        ItemStack item = rawItems.get(materialId.toLowerCase(Locale.ROOT));
        return item != null ? item.clone() : null;
    }

    @Nullable
    public ItemStack getIngotItem(@Nonnull String materialId) {
        ItemStack item = ingotItems.get(materialId.toLowerCase(Locale.ROOT));
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
