package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.tools.EquipmentAnimation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the promise that no equipment type is treated better than another: every weapon, tool and
 * armor piece owns an animation, and no two of them may share the same choreography.
 */
@DisplayName("Exclusive perk animation per equipment type")
class EquipmentAnimationTest {

    @AfterEach
    void restoreDefaults() {
        EquipmentAnimation.reset();
    }

    @Test
    @DisplayName("every weapon, tool and armor type owns an animation of its own family")
    void everyEquipmentTypeHasItsAnimation() {
        for (ModularWeaponType type : ModularWeaponType.values()) {
            EquipmentAnimation animation = EquipmentAnimation.forWeapon(type);
            assertNotNull(animation, type + " has no perk animation");
            assertEquals(EquipmentAnimation.Family.WEAPON, animation.getFamily(), type + " is not a weapon animation");
            assertEquals(type.name(), animation.getTypeName());
        }

        for (ModularToolType type : ModularToolType.values()) {
            if (type == ModularToolType.SWORD) continue; // deprecated alias of ModularWeaponType.SWORD
            EquipmentAnimation animation = EquipmentAnimation.forTool(type);
            assertNotNull(animation, type + " has no perk animation");
            assertEquals(EquipmentAnimation.Family.TOOL, animation.getFamily(), type + " is not a tool animation");
            assertEquals(type.name(), animation.getTypeName());
        }

        for (ModularArmorType type : ModularArmorType.values()) {
            EquipmentAnimation animation = EquipmentAnimation.forArmor(type);
            assertNotNull(animation, type + " has no perk animation");
            assertEquals(EquipmentAnimation.Family.ARMOR, animation.getFamily(), type + " is not an armor animation");
            assertEquals(type.name(), animation.getTypeName());
        }

        assertEquals(ModularWeaponType.values().length, EquipmentAnimation.count(EquipmentAnimation.Family.WEAPON));
        assertEquals(ModularArmorType.values().length, EquipmentAnimation.count(EquipmentAnimation.Family.ARMOR));
    }

    @Test
    @DisplayName("no two equipment types share a choreography")
    void animationsAreExclusive() {
        Set<String> names = new HashSet<>();
        Set<String> signatures = new HashSet<>();
        Set<EquipmentAnimation.Pattern> patterns = EnumSet.noneOf(EquipmentAnimation.Pattern.class);
        Set<Object> sounds = new HashSet<>();

        for (EquipmentAnimation animation : EquipmentAnimation.values()) {
            assertTrue(names.add(animation.getDisplayName()),
                    "Two animations are both called " + animation.getDisplayName());
            assertTrue(signatures.add(animation.signature()),
                    animation.getTypeName() + " reuses another type's pattern/particle/sound signature");
            assertTrue(patterns.add(animation.getPattern()),
                    animation.getTypeName() + " reuses the " + animation.getPattern() + " pattern");
            assertTrue(sounds.add(animation.getSound()),
                    animation.getTypeName() + " reuses the sound " + animation.getSound());

            assertNotEquals(animation.getParticle(), animation.getAccentParticle(),
                    animation.getTypeName() + " uses the same particle twice");
            assertFalse(animation.getDescription().isBlank(),
                    animation.getTypeName() + " has no lore description");
        }

        assertEquals(16, EquipmentAnimation.values().length, "The catalogue must cover all 16 equipment types");
        assertEquals(EquipmentAnimation.values().length, patterns.size());
    }

    @Test
    @DisplayName("an animation can be resolved from the equipment type stored on the item")
    void resolvesFromStoredType() {
        assertSame(EquipmentAnimation.SWORD,
                EquipmentAnimation.forType(EquipmentAnimation.Family.WEAPON, "sword"));
        assertSame(EquipmentAnimation.FISHING_ROD,
                EquipmentAnimation.forType(EquipmentAnimation.Family.TOOL, "FISHING_ROD"));
        assertSame(EquipmentAnimation.CHESTPLATE,
                EquipmentAnimation.forType(EquipmentAnimation.Family.ARMOR, "chestplate"));

        assertNull(EquipmentAnimation.forType(EquipmentAnimation.Family.TOOL, "SWORD"),
                "A weapon type must never resolve as a tool animation");
        assertNull(EquipmentAnimation.forType(EquipmentAnimation.Family.WEAPON, "not_a_type"));
        assertNull(EquipmentAnimation.forType(EquipmentAnimation.Family.ARMOR, null));
        assertNull(EquipmentAnimation.forWeapon(null));
    }

    @Test
    @DisplayName("the configuration clamps absurd values and reset restores the defaults")
    void configurationIsClamped() {
        EquipmentAnimation.configure(true, 99.0, true, 999_999);
        assertEquals(EquipmentAnimation.MAX_PARTICLE_SCALE, EquipmentAnimation.configuredParticleScale());
        assertEquals(EquipmentAnimation.MAX_COOLDOWN_MILLIS, EquipmentAnimation.configuredCooldownMillis());

        EquipmentAnimation.configure(true, -5.0, false, -1);
        assertEquals(EquipmentAnimation.MIN_PARTICLE_SCALE, EquipmentAnimation.configuredParticleScale());
        assertEquals(EquipmentAnimation.MIN_COOLDOWN_MILLIS, EquipmentAnimation.configuredCooldownMillis());
        assertTrue(EquipmentAnimation.isEnabled());
        assertFalse(EquipmentAnimation.isSoundsEnabled());

        EquipmentAnimation.reset();
        assertEquals(EquipmentAnimation.DEFAULT_PARTICLE_SCALE, EquipmentAnimation.configuredParticleScale());
        assertEquals(EquipmentAnimation.DEFAULT_COOLDOWN_MILLIS, EquipmentAnimation.configuredCooldownMillis());
        assertTrue(EquipmentAnimation.isEnabled());
        assertTrue(EquipmentAnimation.isSoundsEnabled());
    }

    @Test
    @DisplayName("the particle scale multiplies the base count of a pattern")
    void particleScaleMultipliesCounts() {
        EquipmentAnimation configure = EquipmentAnimation.SWORD;

        EquipmentAnimation.configure(true, 1.0, true, 0);
        int base = configure.scaleCount(10);
        EquipmentAnimation.configure(true, 2.0, true, 0);
        assertEquals(base * 2, configure.scaleCount(10));

        // Even a zero-sized pattern must still emit something so the animation is never invisible.
        EquipmentAnimation.configure(true, 0.25, true, 0);
        assertEquals(1, configure.scaleCount(1));
    }
}
