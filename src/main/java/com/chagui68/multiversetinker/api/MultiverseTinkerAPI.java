package com.chagui68.multiversetinker.api;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fachada publica y estable de MultiverseTinker para que OTROS plugins (p. ej. el
 * addon SlimeTinker de Slimefun) puedan reconocer y obtener materiales/alloys de
 * MultiverseTinker sin acoplarse a sus internos. Espeja el patron de MultiverseNetsAPI.
 *
 * <p>MultiverseTinker sigue siendo 100% standalone: esta API es solo un punto de
 * lectura. Si el plugin no esta activo, {@link #isAvailable()} devuelve false y el
 * resto degrada a null/false/vacio sin lanzar.
 */
public final class MultiverseTinkerAPI {

    private MultiverseTinkerAPI() {
    }

    /** True si MultiverseTinker esta cargado y su registro de items listo. */
    public static boolean isAvailable() {
        MultiverseTinker mt = MultiverseTinker.getInstance();
        return mt != null && mt.getItemRegistry() != null && mt.getMaterialRegistry() != null;
    }

    // -------------------------------------------------- reconocimiento de items

    /** ¿Es un item de MultiverseTinker (raw/ingot/nugget/block/tool/…)? */
    public static boolean isTinkerItem(@Nullable ItemStack item) {
        Byte flag = read(item, TinkerKeys.IS_TINKER_ITEM);
        return flag != null && flag == (byte) 1;
    }

    public static boolean isIngot(@Nullable ItemStack item) {
        Byte flag = read(item, TinkerKeys.IS_TINKER_INGOT);
        return flag != null && flag == (byte) 1;
    }

    /** Id del material del item (p. ej. "bronze"), o null si no es un item Tinker. */
    @Nullable
    public static String getMaterialId(@Nullable ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
    }

    // -------------------------------------------------- obtencion de items

    @Nullable
    public static ItemStack getIngot(@Nonnull String materialId) {
        return isAvailable() ? MultiverseTinker.getInstance().getItemRegistry().getIngotItem(materialId) : null;
    }

    @Nullable
    public static ItemStack getRaw(@Nonnull String materialId) {
        return isAvailable() ? MultiverseTinker.getInstance().getItemRegistry().getRawItem(materialId) : null;
    }

    @Nullable
    public static ItemStack getNugget(@Nonnull String materialId) {
        return isAvailable() ? MultiverseTinker.getInstance().getItemRegistry().getNuggetItem(materialId) : null;
    }

    @Nullable
    public static ItemStack getBlock(@Nonnull String materialId) {
        return isAvailable() ? MultiverseTinker.getInstance().getItemRegistry().getBlockItem(materialId) : null;
    }

    @Nullable
    public static ItemStack getMoltenBucket(@Nonnull String materialId) {
        return isAvailable() ? MultiverseTinker.getInstance().getItemRegistry().getMoltenBucketItem(materialId) : null;
    }

    // -------------------------------------------------- catalogo de materiales

    /** ¿Existe ese material en MultiverseTinker? */
    public static boolean hasMaterial(@Nonnull String materialId) {
        return isAvailable() && MultiverseTinker.getInstance().getMaterialRegistry().get(materialId) != null;
    }

    /** Ids de todos los materiales/alloys registrados (para que SlimeTinker los mapee). */
    @Nonnull
    public static List<String> listMaterialIds() {
        List<String> out = new ArrayList<>();
        if (isAvailable()) {
            for (TinkerMaterial material : MultiverseTinker.getInstance().getMaterialRegistry().getAll()) {
                out.add(material.getId());
            }
        }
        return out;
    }

    // -------------------------------------------------- helpers

    @Nullable
    private static Byte read(@Nullable ItemStack item, @Nullable org.bukkit.NamespacedKey key) {
        if (item == null || key == null || !item.hasItemMeta()) {
            return null;
        }
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        return pdc.get(key, PersistentDataType.BYTE);
    }
}
