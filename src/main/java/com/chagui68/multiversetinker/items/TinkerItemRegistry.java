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
import java.util.function.Function;

/**
 * Cache and factory for every tinker item.
 *
 * <p>Items are built <b>lazily</b>: the registry used to precompute one of each item per material at
 * startup (about 2,300 stacks with full lore and persistent data), which wasted memory and, worse,
 * meant that a composite or prime alloy discovered later had no items to hand out. Now each kind is
 * a cache filled on first request, so every material that exists at the moment of the request - the
 * 111 catalogued ones, the 12 catalysts, and every composite or prime a player forges - resolves its
 * raw ore, ingot, nugget, block, molten bucket and all ten part types through {@link #getItemById}.</p>
 */
public class TinkerItemRegistry {

    /** Kind of item a material can be turned into. */
    public enum ItemKind {
        RAW("_raw"),
        INGOT("_ingot"),
        NUGGET("_nugget"),
        BLOCK("_block"),
        MOLTEN_BUCKET("_molten_bucket"),
        HEAD("_head"),
        ROD("_rod"),
        BINDING("_binding"),
        BOW_LIMBS("_bow_limbs"),
        BOWSTRING("_bowstring"),
        SHIELD_PLATE("_shield_plate"),
        SHIELD_BOSS("_shield_boss"),
        ARMOR_PLATE("_armor_plate"),
        ARMOR_LINING("_armor_lining"),
        ARMOR_TRIM("_armor_trim");

        private final String suffix;

        ItemKind(String suffix) {
            this.suffix = suffix;
        }

        @Nonnull
        public String getSuffix() {
            return suffix;
        }
    }

    /** Alternate ids players and older configs may still use. */
    private static final Map<String, ItemKind> ALIASES = Map.of(
            "_processed", ItemKind.INGOT,
            "_handle", ItemKind.ROD,
            "_pommel", ItemKind.BINDING);

    private final MaterialRegistry materialRegistry;

    private final Map<ItemKind, Map<String, ItemStack>> caches = new EnumMap<>(ItemKind.class);
    private final Map<CastType, ItemStack> castItems = new EnumMap<>(CastType.class);

    /** Ids that are not derived from a material (tools, casts, smeltery…). */
    private final Map<String, ItemStack> specialItems = new ConcurrentHashMap<>();

    private ItemStack smelteryItem;
    private ItemStack prospectorBrush;

    public TinkerItemRegistry(@Nonnull MaterialRegistry materialRegistry) {
        this.materialRegistry = materialRegistry;
        for (ItemKind kind : ItemKind.values()) {
            caches.put(kind, new ConcurrentHashMap<>());
        }
        reload();
    }

    /** Drops every cached stack: the next request rebuilds it on demand. */
    public void reload() {
        for (Map<String, ItemStack> cache : caches.values()) {
            cache.clear();
        }
        castItems.clear();
        specialItems.clear();

        this.smelteryItem = TinkerItemBuilder.createSmeltery();
        this.prospectorBrush = TinkerItemBuilder.createProspectorBrush();
        specialItems.put("mvtink_smeltery", smelteryItem);
        specialItems.put("mvtink_brush_prospector", prospectorBrush);

        for (CastType castType : CastType.values()) {
            ItemStack cast = TinkerItemBuilder.createCast(castType);
            castItems.put(castType, cast);
            specialItems.put(castType.getId().toLowerCase(Locale.ROOT), cast);
        }
    }

    // ==========================================
    // LAZY MATERIAL ITEMS
    // ==========================================
    @Nullable
    private ItemStack cached(@Nonnull ItemKind kind, @Nonnull String materialId,
                             @Nonnull Function<TinkerMaterial, ItemStack> factory) {
        String key = materialId.toLowerCase(Locale.ROOT);
        Map<String, ItemStack> cache = caches.get(kind);

        ItemStack hit = cache.get(key);
        if (hit != null) return hit.clone();

        TinkerMaterial material = materialRegistry.get(key);
        if (material == null) return null;

        // Hand-built parts need the material wrapped in a single-entry composition.
        ItemStack built = factory.apply(material);
        cache.put(key, built);
        return built.clone();
    }

    @Nullable
    public ItemStack getRawItem(@Nonnull String materialId) {
        return cached(ItemKind.RAW, materialId, TinkerItemBuilder::createRawMineral);
    }

    @Nullable
    public ItemStack getIngotItem(@Nonnull String materialId) {
        return cached(ItemKind.INGOT, materialId, TinkerItemBuilder::createIngot);
    }

    @Nullable
    public ItemStack getNuggetItem(@Nonnull String materialId) {
        return cached(ItemKind.NUGGET, materialId, TinkerItemBuilder::createNugget);
    }

    @Nullable
    public ItemStack getBlockItem(@Nonnull String materialId) {
        return cached(ItemKind.BLOCK, materialId, TinkerItemBuilder::createBlock);
    }

    @Nullable
    public ItemStack getMoltenBucketItem(@Nonnull String materialId) {
        return cached(ItemKind.MOLTEN_BUCKET, materialId, TinkerItemBuilder::createMoltenBucket);
    }

    @Nullable
    public ItemStack getToolHeadItem(@Nonnull String materialId) {
        return getPartItem(ToolPartType.HEAD, materialId);
    }

    @Nullable
    public ItemStack getToolRodItem(@Nonnull String materialId) {
        return getPartItem(ToolPartType.ROD, materialId);
    }

    @Nullable
    public ItemStack getToolBindingItem(@Nonnull String materialId) {
        return getPartItem(ToolPartType.BINDING, materialId);
    }

    @Nullable
    public ItemStack getPartItem(@Nonnull ToolPartType partType, @Nonnull String materialId) {
        ItemKind kind = kindOf(partType);
        if (kind == null) return null;
        return cached(kind, materialId, material ->
                TinkerItemBuilder.createModularPart(partType, PartComposition.fromMaterials(List.of(material))));
    }

    /** Part kinds share their name with the material item kinds. */
    @Nullable
    private static ItemKind kindOf(@Nonnull ToolPartType partType) {
        try {
            return ItemKind.valueOf(partType.name());
        } catch (IllegalArgumentException e) {
            return null;
        }
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

    // ==========================================
    // ID RESOLUTION
    // ==========================================
    /**
     * Resolves any tinker item id, including materials created after startup (composites, prime
     * alloys and their parts).
     */
    @Nullable
    public ItemStack getItemById(@Nonnull String id) {
        String key = id.toLowerCase(Locale.ROOT);

        ItemStack special = specialItems.get(key);
        if (special != null) return special.clone();

        ItemKind kind = kindOfId(key);
        if (kind != null) {
            String materialId = key.substring(0, key.length() - suffixLengthOf(key, kind));
            ItemStack item = itemFor(kind, materialId);
            if (item != null) return item;
        }

        // A bare material id resolves to its raw ore, exactly like before.
        if (materialRegistry.get(key) != null) return getRawItem(key);
        return null;
    }

    @Nullable
    private ItemStack itemFor(@Nonnull ItemKind kind, @Nonnull String materialId) {
        return switch (kind) {
            case RAW -> getRawItem(materialId);
            case INGOT -> getIngotItem(materialId);
            case NUGGET -> getNuggetItem(materialId);
            case BLOCK -> getBlockItem(materialId);
            case MOLTEN_BUCKET -> getMoltenBucketItem(materialId);
            case HEAD -> getPartItem(ToolPartType.HEAD, materialId);
            case ROD -> getPartItem(ToolPartType.ROD, materialId);
            case BINDING -> getPartItem(ToolPartType.BINDING, materialId);
            case BOW_LIMBS -> getPartItem(ToolPartType.BOW_LIMBS, materialId);
            case BOWSTRING -> getPartItem(ToolPartType.BOWSTRING, materialId);
            case SHIELD_PLATE -> getPartItem(ToolPartType.SHIELD_PLATE, materialId);
            case SHIELD_BOSS -> getPartItem(ToolPartType.SHIELD_BOSS, materialId);
            case ARMOR_PLATE -> getPartItem(ToolPartType.ARMOR_PLATE, materialId);
            case ARMOR_LINING -> getPartItem(ToolPartType.ARMOR_LINING, materialId);
            case ARMOR_TRIM -> getPartItem(ToolPartType.ARMOR_TRIM, materialId);
        };
    }

    /** Kind implied by an id suffix (including legacy aliases), or {@code null}. */
    @Nullable
    private static ItemKind kindOfId(@Nonnull String id) {
        for (Map.Entry<String, ItemKind> alias : ALIASES.entrySet()) {
            if (id.endsWith(alias.getKey())) return alias.getValue();
        }
        for (ItemKind kind : ItemKind.values()) {
            if (id.endsWith(kind.getSuffix())) return kind;
        }
        return null;
    }

    private static int suffixLengthOf(@Nonnull String id, @Nonnull ItemKind kind) {
        for (Map.Entry<String, ItemKind> alias : ALIASES.entrySet()) {
            if (alias.getValue() == kind && id.endsWith(alias.getKey())) return alias.getKey().length();
        }
        return kind.getSuffix().length();
    }

    /**
     * Every id the registry can hand out: the special tools/casts plus, for each registered
     * material, its raw ore, ingot, nugget, block, molten bucket and ten part types. Generated from
     * strings only - no ItemStack is built until it is actually requested.
     */
    @Nonnull
    public Set<String> getAllItemIds() {
        Set<String> ids = new LinkedHashSet<>(specialItems.keySet());
        for (TinkerMaterial material : materialRegistry.getAll()) {
            String baseId = material.getId().toLowerCase(Locale.ROOT);
            ids.add(baseId);
            for (ItemKind kind : ItemKind.values()) {
                ids.add(baseId + kind.getSuffix());
            }
            ids.add(baseId + "_processed");
            ids.add(baseId + "_handle");
            ids.add(baseId + "_pommel");
        }
        return Collections.unmodifiableSet(ids);
    }

    /** How many distinct item ids can be handed out right now (used by /mvtink verify). */
    public int getAvailableItemIdCount() {
        return getAllItemIds().size();
    }

    /** Number of distinct item kinds each material supports. */
    public static int kindsPerMaterial() {
        return ItemKind.values().length;
    }
}
