package com.chagui68.multiversetinker.alloys;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class AlloyRegistry {

    private final Map<String, TinkerAlloy> alloys = new LinkedHashMap<>();

    public AlloyRegistry() {
        registerDefaultAlloys();
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
                "mvtink_netherite", "mvtink_celestite",
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
            if (materialRegistry.get(alloy.id()) != null) continue;

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
                    .build();
            materialRegistry.register(tm);
        }
    }
}
