package com.chagui68.multiversetinker.items;

import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class PartComposition {

    public record Entry(@Nonnull TinkerMaterial material, double ratio) {}

    private final List<Entry> entries = new ArrayList<>();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public PartComposition(@Nonnull List<Entry> entries) {
        this.entries.addAll(entries);
    }

    @Nonnull
    public static PartComposition fromMaterials(@Nonnull List<TinkerMaterial> materials) {
        if (materials.isEmpty()) {
            throw new IllegalArgumentException("Cannot create PartComposition from empty material list");
        }
        int count = Math.min(3, materials.size());
        List<Entry> list = new ArrayList<>();

        if (count == 1) {
            list.add(new Entry(materials.get(0), 1.0));
        } else if (count == 2) {
            list.add(new Entry(materials.get(0), 0.5));
            list.add(new Entry(materials.get(1), 0.5));
        } else {
            list.add(new Entry(materials.get(0), 0.333));
            list.add(new Entry(materials.get(1), 0.333));
            list.add(new Entry(materials.get(2), 0.334));
        }
        return new PartComposition(list);
    }

    @Nonnull
    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Nonnull
    public TinkerMaterial getPrimaryMaterial() {
        if (entries.isEmpty()) {
            throw new IllegalStateException("Empty composition");
        }
        return entries.get(0).material();
    }

    public int getDurability() {
        double sum = 0.0;
        for (Entry e : entries) {
            sum += e.material().getDurability() * e.ratio();
        }
        return (int) Math.round(sum);
    }

    public double getMiningSpeed() {
        double sum = 0.0;
        for (Entry e : entries) {
            sum += e.material().getMiningSpeed() * e.ratio();
        }
        return sum;
    }

    public double getAttackDamage() {
        double sum = 0.0;
        for (Entry e : entries) {
            sum += e.material().getAttackDamage() * e.ratio();
        }
        return sum;
    }

    public double getTraitRatio(@Nonnull String materialId) {
        for (Entry e : entries) {
            if (e.material().getId().equalsIgnoreCase(materialId)) {
                return e.ratio();
            }
        }
        return 0.0;
    }

    public boolean triggersTrait(@Nonnull String materialId, @Nonnull Random random) {
        double ratio = getTraitRatio(materialId);
        if (ratio <= 0.0) return false;
        if (ratio >= 0.99) return true;
        return random.nextDouble() < ratio;
    }

    @Nonnull
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            if (i > 0) sb.append(";");
            Entry e = entries.get(i);
            sb.append(e.material().getId()).append(":").append(String.format(Locale.US, "%.3f", e.ratio()));
        }
        return sb.toString();
    }

    /**
     * Parses a serialized composition, reusing the registry's memo. Compositions are immutable once
     * built, so sharing the parsed instance across every combat, mining and aura proc is safe and
     * removes the repeated string splitting that used to run several times per hit.
     */
    @Nullable
    public static PartComposition deserialize(@Nullable String raw, @Nonnull MaterialRegistry registry) {
        return registry.composition(raw);
    }

    /**
     * Unmemoized parser. Call this only from {@link MaterialRegistry#composition(String)}: every
     * other caller should use {@link #deserialize(String, MaterialRegistry)} so the cache is used.
     */
    @Nullable
    public static PartComposition deserializeUncached(@Nullable String raw, @Nonnull MaterialRegistry registry) {
        if (raw == null || raw.trim().isEmpty()) return null;
        List<Entry> list = new ArrayList<>();
        String[] tokens = raw.split(";");
        for (String token : tokens) {
            String[] parts = token.split(":");
            if (parts.length >= 2) {
                TinkerMaterial mat = registry.get(parts[0]);
                if (mat != null) {
                    try {
                        double ratio = Double.parseDouble(parts[1]);
                        list.add(new Entry(mat, ratio));
                    } catch (NumberFormatException ignored) {}
                }
            } else if (parts.length == 1) {
                TinkerMaterial mat = registry.get(parts[0]);
                if (mat != null) {
                    list.add(new Entry(mat, 1.0));
                }
            }
        }
        if (list.isEmpty()) return null;
        return new PartComposition(list);
    }

    @Nonnull
    public List<Component> formatLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("✦ Alloyed Part Composition:", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        for (Entry e : entries) {
            int pct = (int) Math.round(e.ratio() * 100);
            String mini = "<gradient:" + e.material().getColorHex() + ":#ffffff>" + pct + "% "
                    + e.material().getName() + "</gradient> <gray>(" + e.material().getTraitName() + ": " + pct + "% potency)</gray>";
            lore.add(MINI_MESSAGE.deserialize(mini).decoration(TextDecoration.ITALIC, false));
        }
        return lore;
    }
}
