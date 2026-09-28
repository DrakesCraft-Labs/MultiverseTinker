package com.chagui68.multiversetinker.alloys;

import javax.annotation.Nonnull;

public record TinkerAlloy(
        @Nonnull String id,
        @Nonnull String name,
        @Nonnull String mat1Id,
        @Nonnull String mat2Id,
        @Nonnull String colorHex,
        @Nonnull String traitName,
        @Nonnull String traitDescription,
        int durabilityBonus,
        float miningSpeed,
        double attackDamageBonus
) {
    public boolean matches(@Nonnull String m1, @Nonnull String m2) {
        return (mat1Id.equalsIgnoreCase(m1) && mat2Id.equalsIgnoreCase(m2)) ||
               (mat1Id.equalsIgnoreCase(m2) && mat2Id.equalsIgnoreCase(m1));
    }
}
