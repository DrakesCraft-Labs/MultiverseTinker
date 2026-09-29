package com.chagui68.multiversetinker.wiki;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.alloys.TinkerAlloy;
import com.chagui68.multiversetinker.api.ModularWeaponType;
import com.chagui68.multiversetinker.evolution.EvolutionTier;
import com.chagui68.multiversetinker.items.PartComposition;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.VanillaCatalyst;
import com.chagui68.multiversetinker.tools.WeaponPerkProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Ranks the forges the best-build tables of the wiki show.
 *
 * <p>Every row is assembled with {@link TinkerItemBuilder#createModularWeapon} and its damage and durability
 * are read back from the lore the player is going to read, so a table cannot advertise a number the forge
 * does not produce.</p>
 *
 * <p>Every ranked build finishes its head with a <b>prime</b>, because the prime tier is the only way to reach
 * the top of the ladder: a catalyst carries no metallurgical mass, so a prime fused with one is built from the
 * legendary it catalysed alone and the fusion bonus lands on that legendary's own numbers.</p>
 *
 * <p>The rows are forged at the base tier on purpose: an evolution tier adds the same flat bonus to every
 * build, so the ranking is the same from Wood to Netherite and only the numbers shift.</p>
 *
 * <p>Forging a prime registers a material, so this class works from the <b>shipped</b> materials only — the
 * list it is handed — plus the composites and primes it forges itself. Reading the live registry instead would
 * make a second pass over the same tier see thousands of already-forged alloys and try to pair them all again,
 * which is both quadratic and a different answer each time. The result has to be the same for every render.</p>
 */
public final class BestBuilds {

    /** One row of a best-build table: the parts, everything the forged item shows, and the identity behind it. */
    public record Row(@Nonnull List<TinkerMaterial> parts,
                      double damage,
                      int durability,
                      @Nonnull String essence,
                      int essencePercent,
                      @Nullable String identity,
                      boolean tank) {

        /** The three (or two) part names, as the forge screen lists them. */
        @Nonnull
        public String forge() {
            List<String> names = new ArrayList<>();
            for (TinkerMaterial part : parts) names.add(part.getName());
            return String.join(" / ", names);
        }
    }

    private static final Comparator<Row> BY_STRENGTH = Comparator.comparingDouble(Row::damage).reversed()
            .thenComparing(Comparator.comparingInt(Row::durability).reversed());

    /** How many partners of each kind are worth a prime, for the damage and for the durability extremes. */
    private static final int PARTNER_SHORTLIST = 8;

    private final MaterialRegistry materials;
    private final AlloyRegistry alloys;

    /** The shipped materials the tables rank from, frozen so forging cannot feed itself. */
    private final List<TinkerMaterial> base;

    /** The shipped materials plus every composite and prime this instance forged, each listed once. */
    private final Map<String, TinkerMaterial> pool = new LinkedHashMap<>();

    private final List<TinkerMaterial> legendaries = new ArrayList<>();
    private final List<TinkerMaterial> primes = new ArrayList<>();

    private final TinkerMaterial sharpest;
    private final TinkerMaterial hardest;

    /**
     * @param base the shipped materials to rank from, frozen before anything is forged (see the class notes)
     */
    public BestBuilds(@Nonnull MaterialRegistry materials, @Nonnull AlloyRegistry alloys,
                      @Nonnull List<TinkerMaterial> base) {
        this.materials = materials;
        this.alloys = alloys;
        this.base = List.copyOf(base);
        for (TinkerMaterial material : this.base) remember(material);

        forgeTheWholeTier();
        this.legendaries.sort(Comparator.comparing(TinkerMaterial::getName));
        this.sharpest = best(pool.values(), BY_DAMAGE);
        this.hardest = best(pool.values(), BY_DURABILITY);
    }

    /**
     * Best first: sharpest, then hardest to break, then the first catalyst, then the id.
     *
     * <p>The last two steps only order materials that forge identically, which is how a catalyst prime ends up
     * named after the first catalyst instead of after whichever one the registry happened to hand out.</p>
     */
    private static final Comparator<TinkerMaterial> BY_DAMAGE = (first, second) -> {
        int byDamage = Double.compare(second.getAttackDamage(), first.getAttackDamage());
        if (byDamage != 0) return byDamage;
        int byDurability = Integer.compare(second.getDurability(), first.getDurability());
        if (byDurability != 0) return byDurability;
        return sameNumbers(first, second);
    };

    private static final Comparator<TinkerMaterial> BY_DURABILITY = (first, second) -> {
        int byDurability = Integer.compare(second.getDurability(), first.getDurability());
        if (byDurability != 0) return byDurability;
        int byDamage = Double.compare(second.getAttackDamage(), first.getAttackDamage());
        if (byDamage != 0) return byDamage;
        return sameNumbers(first, second);
    };

    private static int sameNumbers(TinkerMaterial first, TinkerMaterial second) {
        int byCatalyst = Integer.compare(catalystRank(first), catalystRank(second));
        if (byCatalyst != 0) return byCatalyst;
        return first.getId().compareTo(second.getId());
    }

    /**
     * Brings the composites and the prime tier into the registry, so the tables can rank what a player can
     * actually forge instead of only the hand-written materials.
     *
     * <p>A legendary can be fused with any alloy, so the full prime space runs to six figures and would cost
     * minutes to forge. Both prime stats grow with the partner's own stats, so only the partners that can move
     * an extreme are worth forging: the sharpest and the toughest materials, every legendary and every catalyst.
     * That is the shortlist the table's head and handle are chosen from.</p>
     */
    private void forgeTheWholeTier() {
        // Every blendable pair of the shipped materials first, because a prime can be fused from any composite.
        for (int first = 0; first < base.size(); first++) {
            for (int second = first + 1; second < base.size(); second++) {
                TinkerMaterial a = base.get(first);
                TinkerMaterial b = base.get(second);
                if (alloys.isCraftablePair(a, b)) remember(materials.get(alloys.findOrCreateAlloy(a, b, materials).id()));
            }
        }

        List<TinkerMaterial> composites = new ArrayList<>();
        List<TinkerMaterial> partners = new ArrayList<>();
        for (TinkerMaterial material : pool.values()) {
            if (material.isAlloy() && !AlloyRegistry.isPrime(material)) composites.add(material);
            if (AlloyRegistry.isLegendary(material) || AlloyRegistry.isCatalyst(material)) partners.add(material);
        }
        partners.addAll(shortlist(composites, BY_DAMAGE));
        partners.addAll(shortlist(composites, BY_DURABILITY));

        // A snapshot, because every fusion below adds to the pool this would otherwise be walking.
        for (TinkerMaterial legendary : List.copyOf(pool.values())) {
            if (!AlloyRegistry.isLegendary(legendary)) continue;
            legendaries.add(legendary);
            for (TinkerMaterial partner : partners) {
                if (!AlloyRegistry.isPrimePair(legendary, partner)) continue;
                TinkerMaterial forged = materials.get(alloys.findOrCreateAlloy(legendary, partner, materials).id());
                remember(forged);
                if (forged != null && AlloyRegistry.isPrime(forged) && !primes.contains(forged)) primes.add(forged);
            }
        }
    }

    /**
     * Adds a material to the pool, so the table ranks the shipped materials and this instance's own forges
     * without ever reaching back into a registry another render has already grown.
     */
    private void remember(@Nullable TinkerMaterial material) {
        if (material == null) return;
        pool.putIfAbsent(material.getId().toLowerCase(Locale.ROOT), material);
    }

    /** The {@code COUNT} strongest materials of the pool, best first. */
    private static List<TinkerMaterial> shortlist(List<TinkerMaterial> pool, Comparator<TinkerMaterial> by) {
        List<TinkerMaterial> sorted = new ArrayList<>(pool);
        sorted.sort(by);
        return sorted.subList(0, Math.min(PARTNER_SHORTLIST, sorted.size()));
    }

    /** The material that wins under a best-first comparator. */
    private static TinkerMaterial best(Iterable<TinkerMaterial> pool, Comparator<TinkerMaterial> by) {
        TinkerMaterial winner = null;
        for (TinkerMaterial material : pool) {
            if (winner == null || by.compare(material, winner) < 0) winner = material;
        }
        if (winner == null) throw new IllegalStateException("The registry holds no material to forge with");
        return winner;
    }

    /** The strongest sword the catalog can forge for every legendary identity, plus the toughest of all. */
    @Nonnull
    public List<Row> swords(int count) {
        return table(ModularWeaponType.SWORD, count, true);
    }

    /** The strongest bow the catalog can forge for every legendary identity, plus the toughest of all. */
    @Nonnull
    public List<Row> bows(int count) {
        return table(ModularWeaponType.BOW, count, false);
    }

    private List<Row> table(ModularWeaponType type, int count, boolean threeParts) {
        List<Row> rows = new ArrayList<>();
        for (TinkerMaterial legendary : legendaries) {
            TinkerMaterial identity = strongestPrimeOf(legendary);
            if (identity == null) continue;
            rows.add(threeParts
                    ? forge(type, List.of(identity, hardest, sharpest), legendary)
                    : forge(type, List.of(identity, sharpest), legendary));
        }
        rows.sort(BY_STRENGTH);

        List<Row> table = new ArrayList<>(rows.subList(0, Math.min(count, rows.size())));
        table.add(threeParts
                ? forge(type, List.of(hardest, hardest, hardest), null)
                : forge(type, List.of(hardest, hardest), null));
        return table;
    }

    /**
     * The legendary's own strongest prime.
     *
     * <p>A catalyst carries no metallurgical mass, so the fusion is built from the legendary alone and the
     * prime bonus lands on the legendary's own stats instead of being averaged against a weaker partner. That
     * is the hardest-hitting head the legendary can reach, and the parent this table names.</p>
     */
    @Nullable
    private TinkerMaterial strongestPrimeOf(TinkerMaterial legendary) {
        TinkerMaterial best = null;
        for (TinkerMaterial material : primes) {
            if (!parents(material).contains(legendary.getId())) continue;
            if (best == null || PRIME_STRENGTH.compare(material, best) < 0) best = material;
        }
        return best;
    }

    /**
     * Sharpest prime first, then the hardest to break, then the catalyst the codex lists first.
     *
     * <p>A catalyst adds no mass to the fusion, so two primes of the same legendary share their numbers and
     * differ only in the ultimate they grant; naming the first catalyst keeps the table reproducible while the
     * text around it explains that the other eleven are just as strong.</p>
     */
    private static final Comparator<TinkerMaterial> PRIME_STRENGTH = BY_DAMAGE;

    private static int catalystRank(TinkerMaterial material) {
        for (String parent : parents(material)) {
            VanillaCatalyst catalyst = VanillaCatalyst.byMaterialId(parent);
            if (catalyst != null) return catalyst.ordinal();
        }
        return VanillaCatalyst.values().length;
    }

    private static List<String> parents(TinkerMaterial material) {
        List<String> ids = new ArrayList<>();
        String parents = material.getAlloyParents();
        if (parents == null) return ids;
        for (String id : parents.split(",")) ids.add(id.trim());
        return ids;
    }

    private Row forge(ModularWeaponType type, List<TinkerMaterial> parts, @Nullable TinkerMaterial identity) {
        List<PartComposition> compositions = new ArrayList<>();
        for (TinkerMaterial part : parts) compositions.add(PartComposition.fromMaterials(List.of(part)));

        ItemStack item = TinkerItemBuilder.createModularWeapon(type, compositions.get(0), compositions.get(1),
                compositions.size() > 2 ? compositions.get(2) : null, EvolutionTier.WOOD, 0);
        List<Component> lore = item.getItemMeta() == null ? List.of() : item.getItemMeta().lore();
        if (lore == null) throw new IllegalStateException("A forged weapon must carry its lore");

        double damage = 0;
        int durability = 0;
        for (Component line : lore) {
            String text = PlainTextComponentSerializer.plainText().serialize(line);
            if (text.contains("Attack Damage:")) damage = Double.parseDouble(text.replaceAll(".*\\+", "").trim());
            if (text.contains("Durability:")) {
                durability = Integer.parseInt(text.replaceAll(".*Durability:\\s*", "").split("/")[0].trim());
            }
        }
        if (damage <= 0 || durability <= 0) {
            throw new IllegalStateException("The forge printed no stats for " + parts);
        }

        WeaponPerkProfile profile = WeaponPerkProfile.of(type, compositions);
        String identityText = identity == null ? null : identity.getTraitName();
        return new Row(parts, damage, durability, profile.getAffinity().getDisplayName(),
                profile.getPotencyPercent(), identityText, identity == null);
    }
}
