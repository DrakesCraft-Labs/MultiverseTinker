package com.chagui68.multiversetinker.archaeology;

import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ArchaeologyLootTable {

    private final MaterialRegistry registry;
    private final Map<MineralOrigin, List<TinkerMaterial>> poolByOrigin = new EnumMap<>(MineralOrigin.class);

    public ArchaeologyLootTable(@Nonnull MaterialRegistry registry) {
        this.registry = registry;
        reload();
    }

    public void reload() {
        poolByOrigin.clear();
        for (MineralOrigin origin : MineralOrigin.values()) {
            List<TinkerMaterial> list = new ArrayList<>(registry.getByOrigin(origin));
            poolByOrigin.put(origin, list);
        }
    }

    @Nullable
    public TinkerMaterial rollMineral(@Nonnull MineralOrigin origin) {
        return rollMineral(origin, false);
    }

    @Nullable
    public TinkerMaterial rollMineral(@Nonnull MineralOrigin origin, boolean isProspector) {
        List<TinkerMaterial> available = poolByOrigin.get(origin);
        if (available == null || available.isEmpty()) {
            return null;
        }

        int totalWeight = 0;
        for (TinkerMaterial mat : available) {
            int weight = mat.getRarity().getDefaultWeight();
            if (isProspector && (mat.getRarity().name().equals("RARE") ||
                                 mat.getRarity().name().equals("EPIC") ||
                                 mat.getRarity().name().equals("LEGENDARY"))) {
                weight *= 2; // Duplica la probabilidad de minerales raros y legendarios con la brocha de prospector
            }
            totalWeight += weight;
        }

        if (totalWeight <= 0) {
            return available.getFirst();
        }

        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        int current = 0;
        for (TinkerMaterial mat : available) {
            int weight = mat.getRarity().getDefaultWeight();
            if (isProspector && (mat.getRarity().name().equals("RARE") ||
                                 mat.getRarity().name().equals("EPIC") ||
                                 mat.getRarity().name().equals("LEGENDARY"))) {
                weight *= 2;
            }
            current += weight;
            if (roll < current) {
                return mat;
            }
        }

        return available.getLast();
    }
}
