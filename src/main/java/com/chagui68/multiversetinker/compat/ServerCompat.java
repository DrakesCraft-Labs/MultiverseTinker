package com.chagui68.multiversetinker.compat;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;

/**
 * The few calls whose API moved between the supported servers (Paper 1.21.11, 26.1 and 26.2).
 *
 * <p>The plugin is compiled once against 1.21.11, so the same jar has to keep linking on every newer
 * Paper. Anything Paper has marked for removal goes through here, resolved once at class load to the
 * newest form the running server offers.</p>
 */
public final class ServerCompat {

    /** {@code RecipeChoice.exactChoice(List)}, the factory 26.x adds; absent on 1.21.11. */
    private static final MethodHandle EXACT_CHOICE_FACTORY = findExactChoiceFactory();
    /** {@code new RecipeChoice.ExactChoice(List)}, the constructor 26.x marks for removal. */
    private static final MethodHandle EXACT_CHOICE_CONSTRUCTOR = findExactChoiceConstructor();

    private ServerCompat() {
    }

    /** A recipe ingredient that only accepts this exact item, NBT included. */
    @Nonnull
    public static RecipeChoice exactChoice(@Nonnull ItemStack item) {
        List<ItemStack> items = List.of(item);
        try {
            if (EXACT_CHOICE_FACTORY != null) {
                return (RecipeChoice) EXACT_CHOICE_FACTORY.invoke(items);
            }
            if (EXACT_CHOICE_CONSTRUCTOR != null) {
                return (RecipeChoice) EXACT_CHOICE_CONSTRUCTOR.invoke(items);
            }
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new IllegalStateException("Could not build an exact recipe choice", e);
        }
        throw new IllegalStateException("This server offers no way to build an exact recipe choice");
    }

    /** The entity's current maximum health, attribute modifiers included. */
    public static double maxHealth(@Nonnull LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attribute.MAX_HEALTH);
        return attribute != null ? attribute.getValue() : 20.0;
    }

    private static MethodHandle findExactChoiceFactory() {
        try {
            return MethodHandles.publicLookup().findStatic(RecipeChoice.class, "exactChoice",
                    MethodType.methodType(RecipeChoice.ExactChoice.class, List.class));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static MethodHandle findExactChoiceConstructor() {
        try {
            return MethodHandles.publicLookup().findConstructor(RecipeChoice.ExactChoice.class,
                    MethodType.methodType(void.class, List.class));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
