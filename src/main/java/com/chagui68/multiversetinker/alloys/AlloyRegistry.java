package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.tools.TraitAffinity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AlloyRegistry {

    /** File holding every composite alloy the players have forged so far. */
    private static final String DYNAMIC_FILE = "dynamic-alloys.yml";

    private final Map<String, TinkerAlloy> alloys = new LinkedHashMap<>();
    /** Ids of the 16 curated recipes: everything else is player-forged and must be persisted. */
    private final Set<String> curatedIds = new LinkedHashSet<>();

    @Nullable
    private JavaPlugin plugin;
    private boolean saveScheduled;

    public AlloyRegistry() {
        registerDefaultAlloys();
        curatedIds.addAll(alloys.keySet());
    }

    private void registerDefaultAlloys() {
        register(new TinkerAlloy(
                "mvtink_bronze", "Bronze",
                "mvtink_copper", "mvtink_tin",
                "#cd7f32", "Dense Temper",
                "High structural density. Grants +350 Durability and -20% knockback received.",
                350, 7.5f, 5.5
        ));

        register(new TinkerAlloy(
                "mvtink_electrum", "Electrum",
                "mvtink_gold", "mvtink_silver",
                "#fff8a6", "Lightning Conduit",
                "Conductive precious alloy. +25% attack speed and sparks shock damage on critical hits.",
                220, 11.0f, 6.0
        ));

        register(new TinkerAlloy(
                "mvtink_invar", "Invar",
                "mvtink_iron", "mvtink_nickel",
                "#b0b8b0", "Thermal Resilience",
                "Low thermal expansion. Completely immune to fire wear and grants +450 Durability.",
                450, 8.0f, 6.5
        ));

        register(new TinkerAlloy(
                "mvtink_manyullyn", "Manyullyn",
                "mvtink_cobalt", "mvtink_ardite",
                "#9b59b6", "Insatiable",
                "Deep Nether blood alloy. Consecutive strikes ramp up attack damage by +1.0 (stacks to +5.0).",
                800, 10.5f, 9.0
        ));

        register(new TinkerAlloy(
                "mvtink_rose_gold", "Rose Gold",
                "mvtink_gold", "mvtink_copper",
                "#b76e79", "Midas Sparkle",
                "Opulent blend. Increases experience orbs gained from mining and combat by +40%.",
                280, 9.0f, 5.0
        ));

        register(new TinkerAlloy(
                "mvtink_astral_brass", "Astral Brass",
                "mvtink_pyrite", "mvtink_astralite",
                "#f4d03f", "Starlight Grace",
                "Infused with cosmic dust. Grants permanent Feather Falling and radiant starlight particles.",
                500, 8.5f, 6.0
        ));

        register(new TinkerAlloy(
                "mvtink_void_damascus", "Void Damascus",
                "mvtink_tungsten", "mvtink_voidstone",
                "#2c3e50", "Abyssal Cleave",
                "Folded space-metal. True armor piercing attacks that bypass 30% of target defense.",
                950, 9.5f, 9.5
        ));

        register(new TinkerAlloy(
                "mvtink_cinder_steel", "Cinder Steel",
                "mvtink_steel", "mvtink_netherite",
                "#e67e22", "Hellfire Core",
                "Forged in nether magma. Ignites foes for 8 seconds and renders item fireproof.",
                1100, 10.0f, 9.0
        ));

        register(new TinkerAlloy(
                "mvtink_prismatic_quartz", "Prismatic Quartz",
                "mvtink_quartz", "mvtink_amethyst",
                "#e056fd", "Resonance Shock",
                "Harmonic crystal matrix. Striking produces an acoustic wave dealing 2.5 AOE damage.",
                400, 9.0f, 7.0
        ));

        register(new TinkerAlloy(
                "mvtink_shadow_platinum", "Shadow Platinum",
                "mvtink_platinum", "mvtink_obsidianite",
                "#636e72", "Umbral Veil",
                "Light-absorbing noble alloy. Sneaking grants brief invisibility and +50% backstab damage.",
                750, 9.0f, 8.0
        ));

        register(new TinkerAlloy(
                "mvtink_ender_brass", "Ender Brass",
                "mvtink_redstone", "mvtink_enderite",
                "#1abc9c", "Phase Step",
                "Resonant spatial conductor. Shift-Right-Click teleports player forward 10 blocks.",
                650, 8.5f, 7.5
        ));

        register(new TinkerAlloy(
                "mvtink_adamant_steel", "Adamant Steel",
                "mvtink_adamantium", "mvtink_titanium",
                "#2ecc71", "Unbreakable Will",
                "Indomitable metallurgy. 80% chance to completely ignore durability consumption.",
                1600, 11.5f, 8.5
        ));

        register(new TinkerAlloy(
                "mvtink_hellfire_bismuth", "Hellfire Bismuth",
                "mvtink_bismuth", "mvtink_fire_opal",
                "#ff7675", "Combustion",
                "Volatile crystalline metal. Critical hits trigger miniature non-destructive thermal explosions.",
                550, 8.0f, 8.0
        ));

        register(new TinkerAlloy(
                "mvtink_glacial_silver", "Glacial Silver",
                "mvtink_silver", "mvtink_cryolite",
                "#74b9ff", "Absolute Frost",
                "Sub-zero cryo-metal. Freezes targets with Slowness III and powder-snow frostbite for 4s.",
                480, 8.0f, 6.5
        ));

        register(new TinkerAlloy(
                "mvtink_sanguine_gold", "Sanguine Gold",
                "mvtink_gold", "mvtink_sanguinite",
                "#d63031", "Vampiric Touch",
                "Cursed lifedrinking gold. Restores 25% of all melee damage dealt as player health.",
                380, 9.5f, 7.5
        ));

        register(new TinkerAlloy(
                "mvtink_cosmic_netherite", "Cosmic Netherite",
                "mvtink_netherite", "mvtink_celestine",
                "#6c5ce7", "Cosmic Gravity",
                "Singularity-infused netherite. Melee strikes pull surrounding foes within 6 blocks together.",
                1400, 12.0f, 10.5
        ));
    }

    public void register(@Nonnull TinkerAlloy alloy) {
        alloys.put(alloy.id().toLowerCase(Locale.ROOT), alloy);
    }

    @Nullable
    public TinkerAlloy findAlloy(@Nonnull String mat1, @Nonnull String mat2) {
        for (TinkerAlloy alloy : alloys.values()) {
            if (alloy.matches(mat1, mat2)) {
                return alloy;
            }
        }
        return null;
    }

    @Nonnull
    public TinkerAlloy findOrCreateAlloy(@Nonnull TinkerMaterial m1, @Nonnull TinkerMaterial m2, @Nonnull MaterialRegistry materialRegistry) {
        TinkerAlloy existing = findAlloy(m1.getId(), m2.getId());
        if (existing != null) {
            return existing;
        }

        // Canonical order by ID to ensure commutativity: (m1, m2) == (m2, m1)
        TinkerMaterial first = m1.getId().compareTo(m2.getId()) <= 0 ? m1 : m2;
        TinkerMaterial second = first == m1 ? m2 : m1;

        String id1 = first.getId().replace("mvtink_", "");
        String id2 = second.getId().replace("mvtink_", "");
        String dynamicAlloyId = "mvtink_alloy_" + id1 + "_" + id2;

        TinkerAlloy cached = alloys.get(dynamicAlloyId.toLowerCase(Locale.ROOT));
        if (cached != null) {
            return cached;
        }

        String alloyName = first.getName() + "-" + second.getName() + " Alloy";
        String blendedColor = blendHexColors(first.getColorHex(), second.getColorHex());
        int durabilityBonus = (int) Math.round((first.getDurability() + second.getDurability()) * 0.70) + 60;
        float miningSpeed = ((first.getMiningSpeed() + second.getMiningSpeed()) / 2.0f) + 0.6f;
        double attackDamageBonus = ((first.getAttackDamage() + second.getAttackDamage()) / 2.0) + 1.2;

        String traitName = first.getTraitName() + "-" + second.getTraitName();
        String traitDesc = "Composite metallurgy combining " + first.getName() + " and " + second.getName() + " properties.";

        TinkerAlloy dynamicAlloy = new TinkerAlloy(
                dynamicAlloyId,
                alloyName,
                first.getId(),
                second.getId(),
                blendedColor,
                traitName,
                traitDesc,
                durabilityBonus,
                miningSpeed,
                attackDamageBonus
        );
        register(dynamicAlloy);
        markDynamicAlloyDirty();

        if (materialRegistry.get(dynamicAlloyId) == null) {
            TinkerMaterial tm = TinkerMaterial.builder()
                    .id(dynamicAlloyId)
                    .name(alloyName)
                    .origin(MineralOrigin.OVERWORLD)
                    .rarity(MaterialRarity.EPIC)
                    .type(MaterialType.ALLOY)
                    .baseVanillaMaterial(Material.RAW_IRON)
                    .processedVanillaMaterial(Material.IRON_INGOT)
                    .nuggetVanillaMaterial(Material.IRON_NUGGET)
                    .blockVanillaMaterial(Material.IRON_BLOCK)
                    .colorHex(blendedColor)
                    .description(traitDesc)
                    .meltingDurationTicks(100)
                    .durabilityBonus(durabilityBonus)
                    .miningSpeed(miningSpeed)
                    .attackDamageBonus(attackDamageBonus)
                    .traitName(traitName)
                    .traitDescription(traitDesc)
                    .weaponTraitDescription("Dual Combat Synergy: Blends " + first.getWeaponTraitDescription() + " and " + second.getWeaponTraitDescription() + ".")
                    .armorTraitDescription("Dual Defensive Synergy: Blends " + first.getArmorTraitDescription() + " and " + second.getArmorTraitDescription() + ".")
                    .alloyParents(first.getId() + "," + second.getId())
                    .inheritedAffinities(TraitAffinity.inherit(first, second))
                    .build();
            materialRegistry.register(tm);
        }

        return dynamicAlloy;
    }

    private String blendHexColors(String hex1, String hex2) {
        try {
            int c1 = Integer.parseInt(hex1.replace("#", ""), 16);
            int c2 = Integer.parseInt(hex2.replace("#", ""), 16);
            int r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
            int r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
            int r = (r1 + r2) / 2;
            int g = (g1 + g2) / 2;
            int b = (b1 + b2) / 2;
            return String.format("#%02X%02X%02X", r, g, b);
        } catch (Exception e) {
            return "#D4AF37";
        }
    }

    @Nullable
    public TinkerAlloy get(@Nonnull String id) {
        return alloys.get(id.toLowerCase(Locale.ROOT));
    }

    @Nonnull
    public Collection<TinkerAlloy> getAllAlloys() {
        return Collections.unmodifiableCollection(alloys.values());
    }

    public void registerAlloysIntoMaterialRegistry(@Nonnull MaterialRegistry materialRegistry) {
        for (TinkerAlloy alloy : alloys.values()) {
            registerMaterial(materialRegistry, alloy);
        }
    }

    private void registerMaterial(@Nonnull MaterialRegistry materialRegistry, @Nonnull TinkerAlloy alloy) {
        if (materialRegistry.get(alloy.id()) != null) return;

        TinkerMaterial tm = TinkerMaterial.builder()
                .id(alloy.id())
                .name(alloy.name())
                .origin(MineralOrigin.OVERWORLD)
                .rarity(MaterialRarity.EPIC)
                .type(MaterialType.ALLOY)
                .baseVanillaMaterial(Material.RAW_IRON)
                .processedVanillaMaterial(Material.IRON_INGOT)
                .nuggetVanillaMaterial(Material.IRON_NUGGET)
                .blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex(alloy.colorHex())
                .description(alloy.traitDescription())
                .meltingDurationTicks(100)
                .durabilityBonus(alloy.durabilityBonus())
                .miningSpeed(alloy.miningSpeed())
                .attackDamageBonus(alloy.attackDamageBonus())
                .traitName(alloy.traitName())
                .traitDescription(alloy.traitDescription())
                .alloyParents(alloy.mat1Id() + "," + alloy.mat2Id())
                .inheritedAffinities(inheritedAffinitiesOf(materialRegistry, alloy.mat1Id(), alloy.mat2Id()))
                .build();
        materialRegistry.register(tm);
    }

    /**
     * Blends the essences of an alloy's two parent minerals so the alloy keeps their identity.
     * Returns an empty string when a parent cannot be resolved (never fails registration).
     */
    @Nonnull
    private String inheritedAffinitiesOf(@Nonnull MaterialRegistry registry, @Nullable String parentA, @Nullable String parentB) {
        if (parentA == null || parentB == null) return "";
        TinkerMaterial first = registry.get(parentA);
        TinkerMaterial second = registry.get(parentB);
        if (first == null || second == null) return "";
        return TraitAffinity.inherit(first, second);
    }

    // ==========================================
    // PERSISTENCE OF PLAYER-FORGED COMPOSITES
    // ==========================================
    /**
     * Restores every composite alloy forged in previous sessions and arms the save pipeline.
     *
     * <p>Without this, an alloy ingot crafted before a restart would refer to a material that no
     * longer exists and would silently stop working as a forging component.</p>
     */
    public void enablePersistence(@Nonnull JavaPlugin plugin, @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        File file = new File(plugin.getDataFolder(), DYNAMIC_FILE);
        if (!file.exists()) return;

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection root = yaml.getConfigurationSection("alloys");
        if (root == null) return;

        int restored = 0;
        for (String id : root.getKeys(false)) {
            ConfigurationSection section = root.getConfigurationSection(id);
            if (section == null) continue;
            String mat1 = section.getString("mat1");
            String mat2 = section.getString("mat2");
            if (mat1 == null || mat2 == null) continue;

            TinkerAlloy alloy = new TinkerAlloy(
                    id,
                    section.getString("name", id),
                    mat1,
                    mat2,
                    section.getString("color", "#D4AF37"),
                    section.getString("traitName", "Composite"),
                    section.getString("traitDescription", "Composite metallurgy."),
                    section.getInt("durability"),
                    (float) section.getDouble("miningSpeed"),
                    section.getDouble("attackDamage"));
            register(alloy);
            registerMaterial(materialRegistry, alloy);
            restored++;
        }

        if (restored > 0) {
            plugin.getLogger().info("Restored " + restored + " player-forged composite alloys.");
        }
    }

    /** Number of composite alloys players have forged, excluding the 16 curated recipes. */
    public int getDynamicAlloyCount() {
        int count = 0;
        for (String id : alloys.keySet()) {
            if (!curatedIds.contains(id)) count++;
        }
        return count;
    }

    /** Writes every player-forged composite alloy to disk. Safe to call at any time. */
    public void flush() {
        if (plugin == null) return;

        YamlConfiguration yaml = new YamlConfiguration();
        for (TinkerAlloy alloy : alloys.values()) {
            if (curatedIds.contains(alloy.id().toLowerCase(Locale.ROOT))) continue;
            String path = "alloys." + alloy.id() + ".";
            yaml.set(path + "name", alloy.name());
            yaml.set(path + "mat1", alloy.mat1Id());
            yaml.set(path + "mat2", alloy.mat2Id());
            yaml.set(path + "color", alloy.colorHex());
            yaml.set(path + "traitName", alloy.traitName());
            yaml.set(path + "traitDescription", alloy.traitDescription());
            yaml.set(path + "durability", alloy.durabilityBonus());
            yaml.set(path + "miningSpeed", (double) alloy.miningSpeed());
            yaml.set(path + "attackDamage", alloy.attackDamageBonus());
        }

        File folder = plugin.getDataFolder();
        if (!folder.exists() && !folder.mkdirs()) {
            plugin.getLogger().warning("Could not create the plugin data folder for " + DYNAMIC_FILE + ".");
            return;
        }
        try {
            yaml.save(new File(folder, DYNAMIC_FILE));
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save " + DYNAMIC_FILE + ": " + e.getMessage());
        }
    }

    private void markDynamicAlloyDirty() {
        if (plugin == null || saveScheduled) return;
        saveScheduled = true;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            saveScheduled = false;
            flush();
        }, 40L);
    }

    /**
     * Determines whether a material may be used as an Alloy Crucible input.
     *
     * <p>Only minerals that can be excavated with the Prospector Brush (Overworld, Nether and
     * End geology) or refined from vanilla Minecraft ores are blendable. Existing alloys and
     * non-mineral tinker items are rejected so the crucible cannot be looped infinitely.</p>
     */
    public static boolean isMixable(@Nullable TinkerMaterial material) {
        if (material == null) return false;
        if (material.getType() == MaterialType.ALLOY) return false;
        if (material.isAlloy()) return false;
        return switch (material.getOrigin()) {
            case OVERWORLD, NETHER, THE_END, VANILLA -> true;
        };
    }

    /**
     * Whether the crucible may forge this exact pair.
     *
     * <p>Generic blending stays limited to brush/vanilla minerals, but a pair that matches a curated
     * legendary recipe is always craftable. That is what keeps <b>Cinder Steel</b> (steel +
     * netherite) and <b>Cosmic Netherite</b> (netherite + celestine) reachable even though vanilla
     * netherite is itself typed as an alloy and therefore cannot be blended freely.</p>
     */
    public boolean isCraftablePair(@Nullable TinkerMaterial first, @Nullable TinkerMaterial second) {
        if (first == null || second == null) return false;
        if (first.getId().equalsIgnoreCase(second.getId())) return false;
        if (isMixable(first) && isMixable(second)) return true;
        return findAlloy(first.getId(), second.getId()) != null;
    }

    /**
     * Convenience overload used for player feedback messages.
     */
    @Nullable
    public static String mixRequirementMessage() {
        return "Only minerals extracted with the Prospector Brush or refined vanilla ores can be blended in the Alloy Crucible (vanilla netherite is already an alloy, so it cannot be blended).";
    }
}
