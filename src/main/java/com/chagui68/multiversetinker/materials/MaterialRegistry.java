package com.chagui68.multiversetinker.materials;

import com.chagui68.multiversetinker.api.MaterialRarity;
import com.chagui68.multiversetinker.api.MaterialType;
import com.chagui68.multiversetinker.api.MineralOrigin;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MaterialRegistry {

    private final Map<String, TinkerMaterial> materials = new ConcurrentHashMap<>();
    private final Map<MineralOrigin, List<TinkerMaterial>> materialsByOrigin = new EnumMap<>(MineralOrigin.class);

    public MaterialRegistry() {
        for (MineralOrigin origin : MineralOrigin.values()) {
            materialsByOrigin.put(origin, new ArrayList<>());
        }
        registerAllMaterials();
    }

    public void register(@Nonnull TinkerMaterial material) {
        if (!material.isValidId()) {
            throw new IllegalArgumentException("Material " + material.getName() + " must have an ID starting with 'mvtink_'!");
        }
        materials.put(material.getId().toLowerCase(Locale.ROOT), material);
        materialsByOrigin.get(material.getOrigin()).add(material);
    }

    @Nullable
    public TinkerMaterial get(@Nonnull String id) {
        return materials.get(id.toLowerCase(Locale.ROOT));
    }

    @Nonnull
    public Collection<TinkerMaterial> getAll() {
        return Collections.unmodifiableCollection(materials.values());
    }

    @Nonnull
    public List<TinkerMaterial> getByOrigin(@Nonnull MineralOrigin origin) {
        return Collections.unmodifiableList(materialsByOrigin.getOrDefault(origin, List.of()));
    }

    private void registerAllMaterials() {
        // ==========================================
        // 1. OVERWORLD (33 Minerals)
        // ==========================================
        register(TinkerMaterial.builder()
                .id("mvtink_tin").name("Tin").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#A8B2B8").description("Malleable metal essential for Bronze alloy smelting.").meltingDurationTicks(60)
                .durabilityBonus(180).miningSpeed(6.0f).attackDamageBonus(1.5).traitName("Malleable").traitDescription("Reduces tool repair costs significantly.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_zinc").name("Zinc").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#D0D8D9").description("Corrosion-resistant metal ideal for Brass forging.").meltingDurationTicks(65)
                .durabilityBonus(210).miningSpeed(6.2f).attackDamageBonus(1.8).traitName("Galvanized").traitDescription("Immune to durability degradation in water or rain.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_silver").name("Silver").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#E6E8FA").description("Highly lustrous conductive metal lethal to undead.").meltingDurationTicks(80)
                .durabilityBonus(250).miningSpeed(7.5f).attackDamageBonus(2.2).traitName("Exorcism").traitDescription("+30% sacred damage against undead monsters.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_ruby").name("Ruby").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.REDSTONE).processedVanillaMaterial(Material.REDSTONE).nuggetVanillaMaterial(Material.REDSTONE).blockVanillaMaterial(Material.REDSTONE_BLOCK)
                .colorHex("#E0115F").description("Fiery gem providing abrasive cutting power.").meltingDurationTicks(110)
                .durabilityBonus(550).miningSpeed(8.5f).attackDamageBonus(3.5).traitName("Flame Edge").traitDescription("Inflicts quick burns when striking enemies.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_sapphire").name("Sapphire").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.LAPIS_LAZULI).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.LAPIS_LAZULI).blockVanillaMaterial(Material.LAPIS_BLOCK)
                .colorHex("#0F52BA").description("Deep frost gemstone providing stability and chilling touch.").meltingDurationTicks(110)
                .durabilityBonus(580).miningSpeed(8.2f).attackDamageBonus(3.0).traitName("Glacial").traitDescription("Slows enemy attack and movement speeds.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_talc").name("Talc").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.SUGAR).processedVanillaMaterial(Material.QUARTZ).nuggetVanillaMaterial(Material.SUGAR).blockVanillaMaterial(Material.WHITE_CONCRETE)
                .colorHex("#F5F5F0").description("Softest mineral used for lubrication and quick strikes.").meltingDurationTicks(50)
                .durabilityBonus(80).miningSpeed(5.0f).attackDamageBonus(0.5).traitName("Slick").traitDescription("Boosts attack swing speed at the cost of lower durability.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_gypsum").name("Gypsum").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.BONE_MEAL).processedVanillaMaterial(Material.CLAY_BALL).nuggetVanillaMaterial(Material.BONE_MEAL).blockVanillaMaterial(Material.WHITE_TERRACOTTA)
                .colorHex("#E8ECEF").description("Sedimentary mineral essential for crafting reusable casts.").meltingDurationTicks(55)
                .durabilityBonus(90).miningSpeed(5.2f).attackDamageBonus(0.6).traitName("Moldable").traitDescription("Makes casting and cooling items more efficient.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_pyrite").name("Pyrite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_NUGGET).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.RAW_GOLD_BLOCK)
                .colorHex("#DAA520").description("Fool's gold; abrasive and sparks brightly when struck.").meltingDurationTicks(75)
                .durabilityBonus(160).miningSpeed(9.0f).attackDamageBonus(1.8).traitName("Sparking").traitDescription("Emits abrasive sparks on impact.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_fluorite").name("Fluorite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.PRISMARINE_CRYSTALS).nuggetVanillaMaterial(Material.PRISMARINE_CRYSTALS).blockVanillaMaterial(Material.PRISMARINE)
                .colorHex("#7FFFD4").description("Phosphorescent crystal with catalytic flux properties.").meltingDurationTicks(85)
                .durabilityBonus(320).miningSpeed(7.8f).attackDamageBonus(2.0).traitName("Luminescent").traitDescription("Grants brief night vision when breaking ores in dark caves.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_galena").name("Galena").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.FLINT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.DEEPSLATE)
                .colorHex("#5A5A66").description("Heavy lead sulfide mineral with immense mass.").meltingDurationTicks(70)
                .durabilityBonus(390).miningSpeed(5.5f).attackDamageBonus(2.5).traitName("Heavyweight").traitDescription("Deals increased knockback to targets.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_magnetite").name("Magnetite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#36454F").description("Natural iron oxide with a powerful magnetic field.").meltingDurationTicks(90)
                .durabilityBonus(400).miningSpeed(7.0f).attackDamageBonus(2.4).traitName("Magnetic Pull").traitDescription("Pulls freshly mined items directly toward the player.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_borax").name("Borax").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.WHITE_DYE).processedVanillaMaterial(Material.SUGAR).nuggetVanillaMaterial(Material.WHITE_DYE).blockVanillaMaterial(Material.WHITE_CONCRETE)
                .colorHex("#ECEFF1").description("Natural flux mineral that accelerates melting temperatures.").meltingDurationTicks(50)
                .durabilityBonus(120).miningSpeed(5.0f).attackDamageBonus(1.0).traitName("Flux Agent").traitDescription("Accelerates smelting and alloy blending.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_beryllium").name("Beryllium").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.COPPER_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.COPPER_BLOCK)
                .colorHex("#40E0D0").description("Ultra-lightweight and highly elastic metallic element.").meltingDurationTicks(120)
                .durabilityBonus(620).miningSpeed(9.5f).attackDamageBonus(2.6).traitName("Featherweight").traitDescription("+25% bonus attack swing speed.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_calcite_gem").name("Pure Calcite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.QUARTZ).processedVanillaMaterial(Material.QUARTZ).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.CALCITE)
                .colorHex("#FAF0E6").description("Doubly-refracting crystal that bends optical focus.").meltingDurationTicks(60)
                .durabilityBonus(200).miningSpeed(6.8f).attackDamageBonus(1.6).traitName("Prism").traitDescription("Chance to double dropped experience orbs.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_graphite").name("Graphite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.COAL).processedVanillaMaterial(Material.CHARCOAL).nuggetVanillaMaterial(Material.COAL).blockVanillaMaterial(Material.COAL_BLOCK)
                .colorHex("#2B2B2B").description("Refractory layered carbon with dry lubricating traits.").meltingDurationTicks(65)
                .durabilityBonus(280).miningSpeed(6.5f).attackDamageBonus(1.7).traitName("Lubricious").traitDescription("Reduces tool wear when mining high-hardness blocks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_jade").name("Jade").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.EMERALD).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.EMERALD).blockVanillaMaterial(Material.EMERALD_BLOCK)
                .colorHex("#00A86B").description("Sacred ornamental mineral blessed with ancestral luck.").meltingDurationTicks(115)
                .durabilityBonus(640).miningSpeed(8.0f).attackDamageBonus(2.8).traitName("Ancestral Fortune").traitDescription("Increases natural loot dropped from slain monsters.").build());

        // 14 Additional Overworld Minerals (Total: 30)
        register(TinkerMaterial.builder()
                .id("mvtink_amethyst_cluster_gem").name("Amethyst Geode Crystal").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.AMETHYST_SHARD).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.AMETHYST_BLOCK)
                .colorHex("#9B59B6").description("Harmonic resonating crystal found deep inside geode chambers.").meltingDurationTicks(80)
                .durabilityBonus(350).miningSpeed(7.4f).attackDamageBonus(2.2).traitName("Resonant Echo").traitDescription("Creates chime waves that alert nearby ores.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_malachite").name("Malachite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.COPPER_INGOT).blockVanillaMaterial(Material.OXIDIZED_COPPER)
                .colorHex("#1ABC9C").description("Vibrant green copper carbonate mineral.").meltingDurationTicks(85)
                .durabilityBonus(380).miningSpeed(7.1f).attackDamageBonus(2.3).traitName("Toxic Patina").traitDescription("Applies poison on hit to living targets.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_lapis_matrix").name("Lapis Matrix").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.LAPIS_LAZULI).processedVanillaMaterial(Material.LAPIS_LAZULI).nuggetVanillaMaterial(Material.LAPIS_LAZULI).blockVanillaMaterial(Material.LAPIS_BLOCK)
                .colorHex("#2980B9").description("Concentrated crystalline lapis saturated with magical charge.").meltingDurationTicks(90)
                .durabilityBonus(420).miningSpeed(7.5f).attackDamageBonus(2.1).traitName("Enchanter's Boon").traitDescription("Increases enchantment potency and fortune drops.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_titanium").name("Titanium").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#BDC3C7").description("Lustrous transition metal with the highest strength-to-density ratio.").meltingDurationTicks(160)
                .durabilityBonus(1400).miningSpeed(9.0f).attackDamageBonus(4.0).traitName("Titan Hardened").traitDescription("Greatly increases tool durability.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_platinum").name("Platinum").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#ECF0F1").description("Noble precious metal immune to tarnishing and chemical erosion.").meltingDurationTicks(140)
                .durabilityBonus(850).miningSpeed(10.0f).attackDamageBonus(3.4).traitName("Noble Purity").traitDescription("Cleanses negative potion effects on hit.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_amber").name("Amber").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.HONEYCOMB).processedVanillaMaterial(Material.GLOWSTONE_DUST).nuggetVanillaMaterial(Material.HONEYCOMB).blockVanillaMaterial(Material.HONEYCOMB_BLOCK)
                .colorHex("#F39C12").description("Fossilized tree resin capturing primordial solar heat.").meltingDurationTicks(60)
                .durabilityBonus(260).miningSpeed(6.8f).attackDamageBonus(1.9).traitName("Preservation").traitDescription("Gradually restores tool durability while resting.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_topaz").name("Topaz").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GOLD_BLOCK)
                .colorHex("#E67E22").description("Hard silicate mineral with exceptional cleavage planes.").meltingDurationTicks(115)
                .durabilityBonus(600).miningSpeed(8.6f).attackDamageBonus(3.2).traitName("Cleaving").traitDescription("Armor-penetrating strikes against armored foes.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_opal").name("Opal").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.PRISMARINE_CRYSTALS).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.PRISMARINE_CRYSTALS).blockVanillaMaterial(Material.SEA_LANTERN)
                .colorHex("#A2D9CE").description("Amorphous silica exhibiting stunning iridescent play-of-color.").meltingDurationTicks(105)
                .durabilityBonus(520).miningSpeed(8.0f).attackDamageBonus(2.8).traitName("Iridescence").traitDescription("Refracts incoming projectile attacks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_aquamarine").name("Aquamarine").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.PRISMARINE_SHARD).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.PRISMARINE_SHARD).blockVanillaMaterial(Material.PRISMARINE)
                .colorHex("#76D7C4").description("Beryl variety infused with the serenity of ocean depths.").meltingDurationTicks(95)
                .durabilityBonus(460).miningSpeed(7.8f).attackDamageBonus(2.4).traitName("Aquatic Affinity").traitDescription("Mines at full speed while submerged in water.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_bauxite").name("Bauxite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.COPPER_INGOT).nuggetVanillaMaterial(Material.RAW_COPPER).blockVanillaMaterial(Material.GRANITE)
                .colorHex("#D35400").description("Sedimentary rock that serves as the principal ore of Aluminum.").meltingDurationTicks(65)
                .durabilityBonus(240).miningSpeed(6.4f).attackDamageBonus(1.6).traitName("Alloy Base").traitDescription("Smelts into versatile lightweight tool alloys.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_cinnabar").name("Cinnabar").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.REDSTONE).processedVanillaMaterial(Material.REDSTONE).nuggetVanillaMaterial(Material.REDSTONE).blockVanillaMaterial(Material.RED_TERRACOTTA)
                .colorHex("#C0392B").description("Toxic bright scarlet mercury sulfide mineral.").meltingDurationTicks(75)
                .durabilityBonus(310).miningSpeed(7.0f).attackDamageBonus(2.6).traitName("Quicksilver").traitDescription("Spike of burst attack speed when hitting.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_kaolinite").name("Kaolinite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.CLAY_BALL).processedVanillaMaterial(Material.CLAY_BALL).nuggetVanillaMaterial(Material.CLAY_BALL).blockVanillaMaterial(Material.WHITE_TERRACOTTA)
                .colorHex("#EAEDED").description("Layered silicate clay mineral used for ceramic refractories.").meltingDurationTicks(50)
                .durabilityBonus(130).miningSpeed(5.4f).attackDamageBonus(0.9).traitName("Ceramic Shell").traitDescription("Absorbs environmental thermal shock.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_zircon").name("Zircon").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.QUARTZ).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.SMOOTH_QUARTZ)
                .colorHex("#AF601A").description("Oldest known mineral on Earth with diamond-like fire.").meltingDurationTicks(125)
                .durabilityBonus(700).miningSpeed(9.2f).attackDamageBonus(3.3).traitName("Deep Time")
                .traitDescription("Preserves its edge and mines with maximum efficiency in deepslate layers.")
                .weaponTraitDescription("Subterranean Strike: Smashes enemy armor with +25% bonus armor-shredding damage at Y < 0.")
                .armorTraitDescription("Epoch Barrier: Grants +15% damage reduction against physical and crushing blows.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_tourmaline").name("Tourmaline").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.EMERALD_BLOCK)
                .colorHex("#117864").description("Borosilicate mineral possessing pyroelectric and piezoelectric energy.").meltingDurationTicks(110)
                .durabilityBonus(590).miningSpeed(8.4f).attackDamageBonus(3.0).traitName("Piezo Shock").traitDescription("Releases electrical sparks when taking impact.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_nickel").name("Nickel").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#B6C2C6").description("Tough corrosion-resistant metal that hardens every alloy it joins.").meltingDurationTicks(90)
                .durabilityBonus(420).miningSpeed(7.4f).attackDamageBonus(2.9).traitName("Galvanic Hardening").traitDescription("Reinforces alloys against wear and improves durability retention.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_cryolite").name("Cryolite").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.CLAY_BALL).processedVanillaMaterial(Material.QUARTZ).nuggetVanillaMaterial(Material.SUGAR).blockVanillaMaterial(Material.WHITE_CONCRETE)
                .colorHex("#EAF6F6").description("Frost-white fluoride flux that melts at remarkably low heat.").meltingDurationTicks(60)
                .durabilityBonus(300).miningSpeed(6.6f).attackDamageBonus(1.6).traitName("Fluxing Frost").traitDescription("Lowers smelting heat and keeps blended metals cold-stable.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_bismuth").name("Bismuth").origin(MineralOrigin.OVERWORLD).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.COPPER_INGOT).nuggetVanillaMaterial(Material.COPPER_INGOT).blockVanillaMaterial(Material.COPPER_BLOCK)
                .colorHex("#D8A0C8").description("Iridescent stair-stepped metal that shatters into brittle thermal prisms.").meltingDurationTicks(85)
                .durabilityBonus(390).miningSpeed(7.8f).attackDamageBonus(3.1).traitName("Brittle Echo").traitDescription("Shatters on impact, releasing a sharp thermal crack.").build());

        // ==========================================
        // 2. THE NETHER (34 Minerals)
        // ==========================================
        register(TinkerMaterial.builder()
                .id("mvtink_cobalt").name("Cobalt").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.LAPIS_LAZULI).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.LAPIS_BLOCK)
                .colorHex("#1E90FF").description("Legendary blue Nether metal with lightning-fast mining speed.").meltingDurationTicks(130)
                .durabilityBonus(800).miningSpeed(12.0f).attackDamageBonus(3.0).traitName("Abyssal Lightness").traitDescription("Extreme mining velocity and swift consecutive attacks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_ardite").name("Ardite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.COPPER_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.RAW_COPPER_BLOCK)
                .colorHex("#FF4500").description("Dense orange volcanic metal resilient against molten magma.").meltingDurationTicks(130)
                .durabilityBonus(990).miningSpeed(6.0f).attackDamageBonus(4.0).traitName("Petramor").traitDescription("Consumes stone to repair tool durability over time.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_sulfur").name("Sulfur").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.GLOWSTONE_DUST).processedVanillaMaterial(Material.GUNPOWDER).nuggetVanillaMaterial(Material.GLOWSTONE_DUST).blockVanillaMaterial(Material.GLOWSTONE)
                .colorHex("#FFD700").description("Volatile volcanic compound that ignites rapidly.").meltingDurationTicks(70)
                .durabilityBonus(150).miningSpeed(7.0f).attackDamageBonus(2.5).traitName("Combustion").traitDescription("Triggers minor fiery concussions on strike.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_stibnite").name("Stibnite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.FLINT).processedVanillaMaterial(Material.IRON_NUGGET).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.BASALT)
                .colorHex("#708090").description("Needle-like antimony sulfide mineral with sharp crystal grains.").meltingDurationTicks(85)
                .durabilityBonus(340).miningSpeed(7.2f).attackDamageBonus(2.8).traitName("Sulfuric Thorns").traitDescription("Reflects a portion of received melee damage back at attackers.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_chromite").name("Chromite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.BLACKSTONE)
                .colorHex("#4A3B32").description("Iron chromium oxide resistant to scorching heat.").meltingDurationTicks(95)
                .durabilityBonus(650).miningSpeed(7.8f).attackDamageBonus(2.9).traitName("Refractory").traitDescription("Complete immunity to durability loss from fire damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_pyrophore").name("Pyrophore").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.FIRE_CHARGE).processedVanillaMaterial(Material.BLAZE_POWDER).nuggetVanillaMaterial(Material.BLAZE_POWDER).blockVanillaMaterial(Material.MAGMA_BLOCK)
                .colorHex("#FF6347").description("Spontaneously combusts upon contact with ambient air.").meltingDurationTicks(100)
                .durabilityBonus(400).miningSpeed(8.5f).attackDamageBonus(3.8).traitName("Auto-Smelt").traitDescription("Automatically smelts compatible ores when mined.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_cinderite").name("Cinderite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.MAGMA_CREAM).processedVanillaMaterial(Material.BLAZE_ROD).nuggetVanillaMaterial(Material.BLAZE_POWDER).blockVanillaMaterial(Material.MAGMA_BLOCK)
                .colorHex("#FF5722").description("Glowing ash stone extracted from deep Nether lava shores.").meltingDurationTicks(85)
                .durabilityBonus(380).miningSpeed(7.0f).attackDamageBonus(2.6).traitName("Living Embers").traitDescription("Blinds targets and ignites lingering fire upon hitting.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_sanguinite").name("Sanguinite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.REDSTONE).processedVanillaMaterial(Material.NETHER_BRICK).nuggetVanillaMaterial(Material.REDSTONE).blockVanillaMaterial(Material.NETHER_BRICKS)
                .colorHex("#8B0000").description("Metal forged with dormant bloodlines of ancient fortresses.").meltingDurationTicks(140)
                .durabilityBonus(780).miningSpeed(8.0f).attackDamageBonus(4.5).traitName("Blood Feast").traitDescription("Restores player health on landing critical blows.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_nether_tungsten").name("Nether Tungsten").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#424242").description("Metal boasting the highest thermal melting point known.").meltingDurationTicks(170)
                .durabilityBonus(1350).miningSpeed(7.0f).attackDamageBonus(4.2).traitName("Unyielding").traitDescription("Massive resistance against durability consumption.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_magma_brimstone").name("Magma Brimstone").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.GLOWSTONE_DUST).processedVanillaMaterial(Material.BLAZE_POWDER).nuggetVanillaMaterial(Material.GLOWSTONE_DUST).blockVanillaMaterial(Material.MAGMA_BLOCK)
                .colorHex("#E65100").description("Hyper-concentrated sulfur crust dried over magma pools.").meltingDurationTicks(80)
                .durabilityBonus(310).miningSpeed(7.5f).attackDamageBonus(2.7).traitName("Caldera").traitDescription("Damage scales higher in hot biomes and the Nether.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_weeping_shard").name("Pure Weeping Shard").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.CRYING_OBSIDIAN).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.CRYING_OBSIDIAN)
                .colorHex("#9C27B0").description("Obsidian fragment distilling dimensional tear energy.").meltingDurationTicks(150)
                .durabilityBonus(1100).miningSpeed(7.9f).attackDamageBonus(3.6).traitName("Respawn Anchor").traitDescription("Prevents item loss if the player dies in lava.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_volcanic_ash").name("Compacted Volcanic Ash").origin(MineralOrigin.NETHER).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.GUNPOWDER).processedVanillaMaterial(Material.CLAY_BALL).nuggetVanillaMaterial(Material.GUNPOWDER).blockVanillaMaterial(Material.TUFF)
                .colorHex("#616161").description("Compressed volcanic tuff hardened by infernal centuries.").meltingDurationTicks(65)
                .durabilityBonus(240).miningSpeed(6.0f).attackDamageBonus(1.5).traitName("Thermal Buffer").traitDescription("Cushions explosion blast damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_crimson_quartz").name("Crimson Quartz").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.QUARTZ).processedVanillaMaterial(Material.REDSTONE).nuggetVanillaMaterial(Material.REDSTONE).blockVanillaMaterial(Material.NETHER_QUARTZ_ORE)
                .colorHex("#D32F2F").description("Mutated quartz variant steeped in crimson fungus spores.").meltingDurationTicks(90)
                .durabilityBonus(450).miningSpeed(8.4f).attackDamageBonus(3.2).traitName("Serrated").traitDescription("Applies stacking bleed damage over time.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_blazesteel_ore").name("Blazesteel Shard").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GOLD_BLOCK)
                .colorHex("#FFA000").description("Incandescent alloy shard pulsing with raw Blaze essence.").meltingDurationTicks(135)
                .durabilityBonus(820).miningSpeed(10.0f).attackDamageBonus(4.0).traitName("Blaze Aura").traitDescription("Nearby hostile monsters take passive radiant fire damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_helliron").name("Helliron").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.NETHERITE_SCRAP).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#3E2723").description("Corroded Nether iron transformed by abyssal lava flows.").meltingDurationTicks(95)
                .durabilityBonus(720).miningSpeed(7.6f).attackDamageBonus(3.1).traitName("Tenacity").traitDescription("Substantially boosts player knockback resistance.").build());

        // 15 Additional Nether Minerals (Total: 30)
        register(TinkerMaterial.builder()
                .id("mvtink_ancient_slag").name("Ancient Debris Slag").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.ANCIENT_DEBRIS).processedVanillaMaterial(Material.NETHERITE_SCRAP).nuggetVanillaMaterial(Material.NETHERITE_SCRAP).blockVanillaMaterial(Material.ANCIENT_DEBRIS)
                .colorHex("#5D4037").description("Dense unrefined slag left over from primordial Netherite strata.").meltingDurationTicks(180)
                .durabilityBonus(1600).miningSpeed(8.5f).attackDamageBonus(4.6).traitName("Ancient Fortitude").traitDescription("Grants resistance to high-tier armor-piercing damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_soulsand_crystal").name("Soul Glass Crystal").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.SOUL_SAND).processedVanillaMaterial(Material.QUARTZ).nuggetVanillaMaterial(Material.SOUL_SAND).blockVanillaMaterial(Material.SOUL_SOIL)
                .colorHex("#6E2C00").description("Glass-like crystal fused from souls trapped in burning soul fire.").meltingDurationTicks(75)
                .durabilityBonus(330).miningSpeed(8.0f).attackDamageBonus(2.4).traitName("Soul Speed").traitDescription("Grants movement speed bursts on soul blocks and sand.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_magmacite").name("Magmacite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.MAGMA_CREAM).processedVanillaMaterial(Material.FIRE_CHARGE).nuggetVanillaMaterial(Material.BLAZE_POWDER).blockVanillaMaterial(Material.MAGMA_BLOCK)
                .colorHex("#E74C3C").description("Superheated magma crystallized into a solid glowing matrix.").meltingDurationTicks(120)
                .durabilityBonus(680).miningSpeed(8.8f).attackDamageBonus(3.5).traitName("Magmatic Core").traitDescription("Walking over lava pools restores tool energy.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_glowstone_gem").name("Glowstone Gem").origin(MineralOrigin.NETHER).rarity(MaterialRarity.COMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.GLOWSTONE_DUST).processedVanillaMaterial(Material.GLOWSTONE_DUST).nuggetVanillaMaterial(Material.GLOWSTONE_DUST).blockVanillaMaterial(Material.GLOWSTONE)
                .colorHex("#F4D03F").description("Condensed glowstone dust forged under pressure into a gemstone.").meltingDurationTicks(65)
                .durabilityBonus(220).miningSpeed(7.2f).attackDamageBonus(1.8).traitName("Radiance").traitDescription("Illuminates mined areas with lingering glow.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_blackstone_pyrite").name("Blackstone Pyrite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.GILDED_BLACKSTONE).processedVanillaMaterial(Material.GOLD_NUGGET).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GILDED_BLACKSTONE)
                .colorHex("#2C3E50").description("Gold flakes deeply fossilized in volcanic blackstone.").meltingDurationTicks(85)
                .durabilityBonus(410).miningSpeed(7.4f).attackDamageBonus(2.5).traitName("Blackstone Plating").traitDescription("Reduces durability loss from blunt impacts.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_witherite").name("Witherite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.COAL).processedVanillaMaterial(Material.NETHER_STAR).nuggetVanillaMaterial(Material.COAL).blockVanillaMaterial(Material.COAL_BLOCK)
                .colorHex("#17202A").description("Charred mineral contaminated by Wither Skeleton essence.").meltingDurationTicks(155)
                .durabilityBonus(1100).miningSpeed(9.0f).attackDamageBonus(4.4).traitName("Wither Decay").traitDescription("Inflicts Wither II on struck adversaries.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_ignis_ferrum").name("Ignis Ferrum").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERRACK)
                .colorHex("#922B21").description("Ancient iron veins soaked in eternal hellfire.").meltingDurationTicks(115)
                .durabilityBonus(690).miningSpeed(8.3f).attackDamageBonus(3.3).traitName("Heat Wave").traitDescription("Striking causes an outward blast of hot cinder.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_nether_bismuth").name("Nether Bismuth").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.PRISMARINE_CRYSTALS).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.WARPED_NYLIUM)
                .colorHex("#85929E").description("Hopper crystal structure showing brilliant rainbow iridescence.").meltingDurationTicks(110)
                .durabilityBonus(620).miningSpeed(8.7f).attackDamageBonus(3.1).traitName("Prismatic Edge").traitDescription("Deals bonus damage scaling with player speed.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_cursed_brimstone").name("Cursed Brimstone").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.GLOWSTONE_DUST).processedVanillaMaterial(Material.GUNPOWDER).nuggetVanillaMaterial(Material.GLOWSTONE_DUST).blockVanillaMaterial(Material.NETHER_WART_BLOCK)
                .colorHex("#B7950B").description("Volcanic brimstone bearing a dark sinister mark.").meltingDurationTicks(80)
                .durabilityBonus(300).miningSpeed(7.3f).attackDamageBonus(2.7).traitName("Nether Curse").traitDescription("Hostile mobs near you take increased damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_ghast_tear_shard").name("Ghast Tear Shard").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.GHAST_TEAR).processedVanillaMaterial(Material.GHAST_TEAR).nuggetVanillaMaterial(Material.GHAST_TEAR).blockVanillaMaterial(Material.QUARTZ_BLOCK)
                .colorHex("#EAEDED").description("Solidified tear of a weeping Nether leviathan.").meltingDurationTicks(105)
                .durabilityBonus(540).miningSpeed(8.2f).attackDamageBonus(2.8).traitName("Spectral Float").traitDescription("Reduces gravity and prevents fall damage while attacking.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_infused_quartz").name("Netherite-Infused Quartz").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.QUARTZ).processedVanillaMaterial(Material.NETHERITE_SCRAP).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.QUARTZ_BLOCK)
                .colorHex("#7B7D7D").description("Quartz matrix naturally hybridized with ancient debris veins.").meltingDurationTicks(165)
                .durabilityBonus(1200).miningSpeed(11.0f).attackDamageBonus(4.3).traitName("Overclock").traitDescription("Accelerates mining speed continuously while swinging.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_infernal_obsidian").name("Infernal Obsidian").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.OBSIDIAN).processedVanillaMaterial(Material.OBSIDIAN).nuggetVanillaMaterial(Material.FLINT).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#4A235A").description("Obsidian cooled by hellfire rather than water.").meltingDurationTicks(145)
                .durabilityBonus(1050).miningSpeed(7.8f).attackDamageBonus(3.7).traitName("Thermal Armor").traitDescription("Negates all lava burning damage to equipment.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_crimson_gold").name("Crimson Gold").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GOLD_BLOCK)
                .colorHex("#C0392B").description("Nether gold transformed by crimson fungi roots.").meltingDurationTicks(80)
                .durabilityBonus(320).miningSpeed(10.5f).attackDamageBonus(2.2).traitName("Piglin Respect").traitDescription("Piglins never become hostile towards the wielder.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_warped_emerald").name("Warped Emerald").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.EMERALD).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.EMERALD).blockVanillaMaterial(Material.EMERALD_BLOCK)
                .colorHex("#138D75").description("Emerald gemstone mutated by warped forest mycelium.").meltingDurationTicks(115)
                .durabilityBonus(620).miningSpeed(8.4f).attackDamageBonus(3.1).traitName("Spore Shield").traitDescription("Releases defensive spore clouds when damaged.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_netherite_shard").name("Netherite Scrap Shard").origin(MineralOrigin.NETHER).rarity(MaterialRarity.LEGENDARY).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.NETHERITE_SCRAP).processedVanillaMaterial(Material.NETHERITE_INGOT).nuggetVanillaMaterial(Material.NETHERITE_SCRAP).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#34495E").description("Refined pure scrap of primeval ancient debris.").meltingDurationTicks(210)
                .durabilityBonus(2100).miningSpeed(11.5f).attackDamageBonus(5.2).traitName("Prime Armor").traitDescription("Grants maximum durability and lava immunity.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_steel").name("Steel").origin(MineralOrigin.NETHER).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#8E9BA8").description("Nether-forged carbon steel quenched in cinder and brimstone.").meltingDurationTicks(120)
                .durabilityBonus(620).miningSpeed(7.0f).attackDamageBonus(3.4).traitName("Cinder Temper").traitDescription("Keeps a searing temper that scorches whatever it strikes.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_tungsten").name("Tungsten").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.BLACKSTONE)
                .colorHex("#5D6D7E").description("Ultra-dense refractory metal with the highest melting point in the multiverse.").meltingDurationTicks(180)
                .durabilityBonus(980).miningSpeed(8.6f).attackDamageBonus(4.4).traitName("Refractory Mass").traitDescription("Extreme density converts weight into crushing kinetic force.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_obsidianite").name("Obsidianite").origin(MineralOrigin.NETHER).rarity(MaterialRarity.RARE).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.OBSIDIAN).processedVanillaMaterial(Material.CRYING_OBSIDIAN).nuggetVanillaMaterial(Material.FLINT).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#241E34").description("Vitrified volcanic glass shot through with umbral light-drinking veins.").meltingDurationTicks(150)
                .durabilityBonus(820).miningSpeed(7.0f).attackDamageBonus(3.8).traitName("Umbral Shroud").traitDescription("Drinks ambient light to veil the wielder in shadow.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_fire_opal").name("Fire Opal").origin(MineralOrigin.NETHER).rarity(MaterialRarity.EPIC).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.MAGMA_CREAM).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.MAGMA_BLOCK)
                .colorHex("#FF6B35").description("Molten opal flickering with trapped volcanic fire.").meltingDurationTicks(140)
                .durabilityBonus(640).miningSpeed(9.4f).attackDamageBonus(4.0).traitName("Emberflicker").traitDescription("Trapped fire flares outward whenever the gem is struck.").build());

        // ==========================================
        // 3. THE END (30 Minerals)
        // ==========================================
        register(TinkerMaterial.builder()
                .id("mvtink_enderite").name("Enderite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.NETHERITE_SCRAP).processedVanillaMaterial(Material.NETHERITE_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#14B8A6").description("The ultimate Void metal transcending dimensional space.").meltingDurationTicks(200)
                .durabilityBonus(2400).miningSpeed(13.5f).attackDamageBonus(5.5).traitName("Dimensional Rift").traitDescription("Shift + Right-Click to trigger short-range blink warp.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_adamantium").name("Adamantium").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.NETHERITE_SCRAP).processedVanillaMaterial(Material.NETHERITE_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#2F4F4F").description("Nearly indestructible metal of boundless density.").meltingDurationTicks(220)
                .durabilityBonus(3200).miningSpeed(11.0f).attackDamageBonus(6.0).traitName("Indestructible").traitDescription("75% chance to negate durability consumption.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_adamita").name("Adamite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.EMERALD).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.EMERALD).blockVanillaMaterial(Material.EMERALD_BLOCK)
                .colorHex("#98FB98").description("Resonant harmonic crystal pulsating with quantum energy.").meltingDurationTicks(160)
                .durabilityBonus(1400).miningSpeed(10.5f).attackDamageBonus(4.2).traitName("Quantum Harmony").traitDescription("Boosts all gained experience by +50%.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_celestine").name("Celestine").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.DIAMOND_BLOCK)
                .colorHex("#87CEEB").description("Celestial crystal defying gravitational fields.").meltingDurationTicks(150)
                .durabilityBonus(1100).miningSpeed(11.2f).attackDamageBonus(3.8).traitName("Zero Gravity").traitDescription("Grants passive Slow Falling while held in main hand.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_voidstone").name("Voidstone").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.ECHO_SHARD).processedVanillaMaterial(Material.NETHERITE_SCRAP).nuggetVanillaMaterial(Material.ECHO_SHARD).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#212121").description("Dark matter stone drawn from the abyss of outer End voids.").meltingDurationTicks(140)
                .durabilityBonus(1250).miningSpeed(9.0f).attackDamageBonus(4.0).traitName("Void Ward").traitDescription("Nullifies void falling damage, bouncing player back up.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_resonite").name("Resonite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.ECHO_SHARD).processedVanillaMaterial(Material.AMETHYST_SHARD).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.AMETHYST_BLOCK)
                .colorHex("#00BCD4").description("Crystal attuned to the deep resonance frequencies of the End.").meltingDurationTicks(130)
                .durabilityBonus(950).miningSpeed(10.0f).attackDamageBonus(3.5).traitName("Shockwave").traitDescription("Strikes sweep through surrounding foes in an area shockwave.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_chorus_crystal").name("Chorus Crystal").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.CHORUS_FRUIT).processedVanillaMaterial(Material.AMETHYST_SHARD).nuggetVanillaMaterial(Material.POPPED_CHORUS_FRUIT).blockVanillaMaterial(Material.PURPUR_BLOCK)
                .colorHex("#AB47BC").description("Crystal formed from condensed, concentrated Chorus sap.").meltingDurationTicks(90)
                .durabilityBonus(680).miningSpeed(8.5f).attackDamageBonus(2.9).traitName("Evasion").traitDescription("Chance to automatically evade incoming attacks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_astralite").name("Astralite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.PRISMARINE_CRYSTALS).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.PRISMARINE_CRYSTALS).blockVanillaMaterial(Material.DIAMOND_BLOCK)
                .colorHex("#E1BEE7").description("Star metal harvested from falling comets across the Void.").meltingDurationTicks(160)
                .durabilityBonus(1500).miningSpeed(12.0f).attackDamageBonus(4.8).traitName("Comet Trail").traitDescription("Leaves radiant glowing particles illuminating the dark.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_aetherium").name("Aetherium").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.FEATHER).processedVanillaMaterial(Material.PHANTOM_MEMBRANE).nuggetVanillaMaterial(Material.FEATHER).blockVanillaMaterial(Material.WHITE_WOOL)
                .colorHex("#B2EBF2").description("Solidified ethereal gas gathered from deep space currents.").meltingDurationTicks(140)
                .durabilityBonus(900).miningSpeed(14.0f).attackDamageBonus(3.4).traitName("Phantom Strike").traitDescription("Pierces directly through enemy shields and armor.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_shadowgem").name("Shadowgem").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.COAL).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.COAL).blockVanillaMaterial(Material.COAL_BLOCK)
                .colorHex("#1A237E").description("Light-devouring gemstone that thrives in total darkness.").meltingDurationTicks(130)
                .durabilityBonus(1050).miningSpeed(9.2f).attackDamageBonus(4.3).traitName("Sneak Attack").traitDescription("+40% bonus damage when striking from behind.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_gravitite").name("Gravitite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#7E57C2").description("Metal possessing an inverted gravitational charge.").meltingDurationTicks(155)
                .durabilityBonus(1300).miningSpeed(9.8f).attackDamageBonus(4.4).traitName("Levitation Hit").traitDescription("Launches struck enemies into the air.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_shulkerite").name("Shulkerite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.SHULKER_SHELL).processedVanillaMaterial(Material.POPPED_CHORUS_FRUIT).nuggetVanillaMaterial(Material.SHULKER_SHELL).blockVanillaMaterial(Material.PURPUR_BLOCK)
                .colorHex("#CE93D8").description("Fossilized Shulker carapace shell with immense deflection.").meltingDurationTicks(105)
                .durabilityBonus(1150).miningSpeed(8.0f).attackDamageBonus(3.1).traitName("Carapace").traitDescription("Provides passive armor and projectile damage reduction.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_null_shard").name("Null-Shard").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.ECHO_SHARD).processedVanillaMaterial(Material.PRISMARINE_SHARD).nuggetVanillaMaterial(Material.ECHO_SHARD).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#311B92").description("Condensed antimatter captured in a stable crystalline matrix.").meltingDurationTicks(210)
                .durabilityBonus(2000).miningSpeed(12.5f).attackDamageBonus(5.8).traitName("Disintegration").traitDescription("Chance to instantaneously disintegrate lesser monsters.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_warped_quartz").name("Warped Quartz").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.QUARTZ).processedVanillaMaterial(Material.PRISMARINE_CRYSTALS).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.WARPED_WART_BLOCK)
                .colorHex("#00897B").description("Quartz crystal distorted by continuous teleportation fields.").meltingDurationTicks(90)
                .durabilityBonus(750).miningSpeed(9.1f).attackDamageBonus(3.3).traitName("Blink Step").traitDescription("Micro-teleports forward after sustained mining.").build());

        // 16 Additional The End Minerals (Total: 30)
        register(TinkerMaterial.builder()
                .id("mvtink_pearl_core").name("Ender Pearl Core").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.ENDER_PEARL).processedVanillaMaterial(Material.ENDER_EYE).nuggetVanillaMaterial(Material.ENDER_PEARL).blockVanillaMaterial(Material.END_STONE_BRICKS)
                .colorHex("#0E6251").description("Condensed core of ender energy pulled from ancient pearls.").meltingDurationTicks(85)
                .durabilityBonus(510).miningSpeed(8.6f).attackDamageBonus(2.7).traitName("Kinetic Warp").traitDescription("Throws miniature teleport pearls on critical hits.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_void_titanium").name("Void Titanium").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.NETHERITE_SCRAP).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#1F618D").description("Titanium exposed to cosmic vacuum radiation for eons.").meltingDurationTicks(185)
                .durabilityBonus(1750).miningSpeed(11.2f).attackDamageBonus(4.9).traitName("Abyssal Density").traitDescription("Massive resistance to armor decay and durability damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_tesseract_crystal").name("Tesseract Crystal").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.PRISMARINE_CRYSTALS).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.PRISMARINE_CRYSTALS).blockVanillaMaterial(Material.SEA_LANTERN)
                .colorHex("#5DADE2").description("4-dimensional geometric crystal bending surrounding space.").meltingDurationTicks(160)
                .durabilityBonus(1300).miningSpeed(12.0f).attackDamageBonus(4.1).traitName("Spacetime Fold").traitDescription("Increases block interaction and attack reach distance.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_cosmium").name("Cosmium").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.NETHERITE_SCRAP).processedVanillaMaterial(Material.NETHERITE_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#6C3483").description("Pure cosmic starlight condensed into a heavy reflective metal.").meltingDurationTicks(215)
                .durabilityBonus(2600).miningSpeed(13.0f).attackDamageBonus(5.6).traitName("Stellar Core").traitDescription("Discharges cosmic stellar beams on sweeping attacks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_dragon_shard").name("Dragon Scale Shard").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.OBSIDIAN).processedVanillaMaterial(Material.DRAGON_BREATH).nuggetVanillaMaterial(Material.FLINT).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#512E5F").description("Fossilized scale of the primordial Ender Dragon.").meltingDurationTicks(230)
                .durabilityBonus(2900).miningSpeed(12.0f).attackDamageBonus(6.2).traitName("Dragon's Fury").traitDescription("Releases lingering dragon breath on landing heavy strikes.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_nebulite").name("Nebulite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.PRISMARINE_SHARD).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.PURPLE_CONCRETE)
                .colorHex("#BB8FCE").description("Vaporized nebula gas solidified into an ethereal mineral.").meltingDurationTicks(120)
                .durabilityBonus(850).miningSpeed(10.2f).attackDamageBonus(3.6).traitName("Nebula Cloak").traitDescription("Grants temporary invisibility after defeating a foe.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_singularite").name("Singularite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.ECHO_SHARD).processedVanillaMaterial(Material.NETHERITE_BLOCK).nuggetVanillaMaterial(Material.ECHO_SHARD).blockVanillaMaterial(Material.BLACK_CONCRETE)
                .colorHex("#111111").description("Condensed microscopic black hole crust with infinite pull.").meltingDurationTicks(240)
                .durabilityBonus(3500).miningSpeed(14.0f).attackDamageBonus(6.5).traitName("Event Horizon").traitDescription("Draws all nearby enemies into an inescapable vortex.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_end_crystal_shard").name("End Crystal Shard").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.END_CRYSTAL).processedVanillaMaterial(Material.GHAST_TEAR).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.WHITE_STAINED_GLASS)
                .colorHex("#F5EEF8").description("Shard salvaged from the regeneration crystals of obsidian pillars.").meltingDurationTicks(135)
                .durabilityBonus(980).miningSpeed(9.5f).attackDamageBonus(3.8).traitName("Beam Rejuvenation").traitDescription("Regenerates player health continuously while mining.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_phantomite").name("Phantomite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.PHANTOM_MEMBRANE).processedVanillaMaterial(Material.PHANTOM_MEMBRANE).nuggetVanillaMaterial(Material.FEATHER).blockVanillaMaterial(Material.CYAN_WOOL)
                .colorHex("#48C9B0").description("Spectral tissue extracted from Void Phantoms of the outer islands.").meltingDurationTicks(95)
                .durabilityBonus(580).miningSpeed(8.9f).attackDamageBonus(3.0).traitName("Incorporeal").traitDescription("Passes through physical walls on sustained sprint.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_eclipse_gem").name("Eclipse Gem").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.COAL).processedVanillaMaterial(Material.GOLD_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GILDED_BLACKSTONE)
                .colorHex("#B7950B").description("Gemstone that captures the rare alignment of celestial bodies.").meltingDurationTicks(130)
                .durabilityBonus(890).miningSpeed(9.4f).attackDamageBonus(3.9).traitName("Total Eclipse").traitDescription("Deals double damage during solar and lunar events.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_starlight_silver").name("Starlight Silver").origin(MineralOrigin.THE_END).rarity(MaterialRarity.RARE).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#FDFEFE").description("Refined silver infused with the purest rays of distant stars.").meltingDurationTicks(125)
                .durabilityBonus(840).miningSpeed(10.8f).attackDamageBonus(3.5).traitName("Lunar Glow").traitDescription("Blinds enemies and grants speed under the open night sky.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_chrono_crystal").name("Chrono Crystal").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_SHARD).processedVanillaMaterial(Material.CLOCK).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.YELLOW_CONCRETE)
                .colorHex("#F1C40F").description("Crystalline anomaly that warps localized temporal velocity.").meltingDurationTicks(170)
                .durabilityBonus(1250).miningSpeed(11.8f).attackDamageBonus(4.2).traitName("Time Warp").traitDescription("Slows down struck targets by 50% for 3 seconds.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_aether_pearl").name("Aether Pearl").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.ENDER_PEARL).processedVanillaMaterial(Material.PRISMARINE_SHARD).nuggetVanillaMaterial(Material.ENDER_PEARL).blockVanillaMaterial(Material.PURPUR_BLOCK)
                .colorHex("#D7BDE2").description("Lustrous pearl radiating buoyant atmospheric energy.").meltingDurationTicks(90)
                .durabilityBonus(620).miningSpeed(8.4f).attackDamageBonus(2.8).traitName("Feather Fall").traitDescription("Immunity to kinetic impact and collision damage.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_spatial_platinum").name("Spatial Platinum").origin(MineralOrigin.THE_END).rarity(MaterialRarity.EPIC).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.PURPLE_TERRACOTTA)
                .colorHex("#A569BD").description("Platinum isotope existing simultaneously in two spatial realms.").meltingDurationTicks(160)
                .durabilityBonus(1450).miningSpeed(11.0f).attackDamageBonus(4.5).traitName("Transdimensional").traitDescription("Swinging the tool mines linked adjacent ore blocks.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_void_pyrite").name("Void Pyrite").origin(MineralOrigin.THE_END).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_NUGGET).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.RAW_GOLD_BLOCK)
                .colorHex("#7D6608").description("Mineral exhibiting dark purple sparks when struck against stone.").meltingDurationTicks(85)
                .durabilityBonus(450).miningSpeed(8.5f).attackDamageBonus(2.6).traitName("Void Sparks").traitDescription("Triggers purple plasma explosions upon impact.").build());

        register(TinkerMaterial.builder()
                .id("mvtink_zero_point").name("Zero-Point Shard").origin(MineralOrigin.THE_END).rarity(MaterialRarity.LEGENDARY).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.ECHO_SHARD).processedVanillaMaterial(Material.NETHER_STAR).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.ICE)
                .colorHex("#D4EFDF").description("Antimatter crystal extracting energy from the quantum vacuum.").meltingDurationTicks(240)
                .durabilityBonus(3000).miningSpeed(14.5f).attackDamageBonus(6.4).traitName("Absolute Zero").traitDescription("Completely freezes and immobilizes targets on impact.").build());

        // ==========================================
        // 4. VANILLA MINECRAFT MINERALS (14 Materials)
        // ==========================================
        register(TinkerMaterial.builder()
                .id("mvtink_coal").name("Coal").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.COAL_ORE).processedVanillaMaterial(Material.COAL).nuggetVanillaMaterial(Material.GUNPOWDER).blockVanillaMaterial(Material.COAL_BLOCK)
                .colorHex("#2C3E50").description("Combustible carbon mineral.").meltingDurationTicks(40)
                .durabilityBonus(150).miningSpeed(5.5f).attackDamageBonus(1.0).traitName("Kindling")
                .traitDescription("Smelts mined items directly with thermal friction.")
                .weaponTraitDescription("Combustion: Inflicts fiery burns on struck enemies.")
                .armorTraitDescription("Thermal Insulation: Grants resistance to freezing and powder snow.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_iron").name("Iron").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.COMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_IRON).processedVanillaMaterial(Material.IRON_INGOT).nuggetVanillaMaterial(Material.IRON_NUGGET).blockVanillaMaterial(Material.IRON_BLOCK)
                .colorHex("#D8D8D8").description("Classic foundational metal.").meltingDurationTicks(60)
                .durabilityBonus(250).miningSpeed(6.0f).attackDamageBonus(2.0).traitName("Reinforced")
                .traitDescription("Solid baseline durability and reliable strike defense.")
                .weaponTraitDescription("Tempered Steel: Delivers +15% reliable physical impact damage.")
                .armorTraitDescription("Ironclad Wall: Sturdy physical defense against melee strikes.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_copper").name("Copper").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.COMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_COPPER).processedVanillaMaterial(Material.COPPER_INGOT).nuggetVanillaMaterial(Material.COPPER_INGOT).blockVanillaMaterial(Material.COPPER_BLOCK)
                .colorHex("#C06C46").description("Highly conductive ductile metal.").meltingDurationTicks(50)
                .durabilityBonus(200).miningSpeed(5.8f).attackDamageBonus(1.8).traitName("Conductive")
                .traitDescription("Channels kinetic electricity upon striking blocks or enemies.")
                .weaponTraitDescription("Static Discharge: Strikes have a 25% chance to zap targets with electric shock.")
                .armorTraitDescription("Grounding Lattice: Absorbs and dissipates lightning and electrical shocks.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_gold").name("Gold").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.METAL)
                .baseVanillaMaterial(Material.RAW_GOLD).processedVanillaMaterial(Material.GOLD_INGOT).nuggetVanillaMaterial(Material.GOLD_NUGGET).blockVanillaMaterial(Material.GOLD_BLOCK)
                .colorHex("#F1C40F").description("Precious lustrous noble metal.").meltingDurationTicks(45)
                .durabilityBonus(100).miningSpeed(12.0f).attackDamageBonus(1.5).traitName("Midas Touch")
                .traitDescription("Enormous mining speed, pacifies piglins, and boosts bonus mob drops.")
                .weaponTraitDescription("Auric Strike: Increases weapon damage by +30% and pacifies Piglins.")
                .armorTraitDescription("Gilded Splendor: Pacifies Piglins and converts 10% of damage taken into golden aura.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_redstone").name("Redstone").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.ELEMENTAL)
                .baseVanillaMaterial(Material.REDSTONE_ORE).processedVanillaMaterial(Material.REDSTONE).nuggetVanillaMaterial(Material.REDSTONE).blockVanillaMaterial(Material.REDSTONE_BLOCK)
                .colorHex("#E74C3C").description("Energy-pulsing resonant mineral.").meltingDurationTicks(50)
                .durabilityBonus(180).miningSpeed(8.0f).attackDamageBonus(2.2).traitName("Energized")
                .traitDescription("Grants high attack swing speed and bursts of haste.")
                .weaponTraitDescription("Overcharged Cadence: Increases weapon attack swing speed by +25%.")
                .armorTraitDescription("Conduit Pulse: Taking damage triggers a repulsion burst pushing foes back.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_lapis").name("Lapis Lazuli").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.LAPIS_ORE).processedVanillaMaterial(Material.LAPIS_LAZULI).nuggetVanillaMaterial(Material.LAPIS_LAZULI).blockVanillaMaterial(Material.LAPIS_BLOCK)
                .colorHex("#2980B9").description("Metamorphic deep blue gemstone.").meltingDurationTicks(55)
                .durabilityBonus(220).miningSpeed(6.5f).attackDamageBonus(1.9).traitName("Fortune Affinity")
                .traitDescription("Amplifies dropped experience orbs and extra mineral drops.")
                .weaponTraitDescription("Arcane Siphon: Defeating enemies yields +50% bonus experience orbs.")
                .armorTraitDescription("Enchanted Ward: Reduces incoming magic and potion damage by 25%.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_diamond").name("Diamond").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.DIAMOND_ORE).processedVanillaMaterial(Material.DIAMOND).nuggetVanillaMaterial(Material.DIAMOND).blockVanillaMaterial(Material.DIAMOND_BLOCK)
                .colorHex("#5DADE2").description("Supreme crystalline carbon structure.").meltingDurationTicks(120)
                .durabilityBonus(1560).miningSpeed(8.0f).attackDamageBonus(4.0).traitName("Adamant Edge")
                .traitDescription("Unmatched natural toughness and armor cleavage.")
                .weaponTraitDescription("Diamond Edge: Razor-sharp cutting edge that bypasses 20% enemy armor.")
                .armorTraitDescription("Diamond Bulwark: Provides supreme armor toughness and knockback absorption.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_emerald").name("Emerald").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.RARE).type(MaterialType.GEM)
                .baseVanillaMaterial(Material.EMERALD_ORE).processedVanillaMaterial(Material.EMERALD).nuggetVanillaMaterial(Material.EMERALD).blockVanillaMaterial(Material.EMERALD_BLOCK)
                .colorHex("#2ECC71").description("Vibrant beryl gemstone prized by villagers.").meltingDurationTicks(110)
                .durabilityBonus(600).miningSpeed(7.5f).attackDamageBonus(3.2).traitName("Merchant's Eye")
                .traitDescription("Deals bonus damage against Illagers and yields extra emerald drops.")
                .weaponTraitDescription("Illager's Bane: Deals +40% bonus damage against Illagers, Evokers, and Vindicators.")
                .armorTraitDescription("Heroic Presence: Reduces damage taken from Illagers and grants Hero of the Village.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_netherite").name("Netherite").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.LEGENDARY).type(MaterialType.ALLOY)
                .baseVanillaMaterial(Material.ANCIENT_DEBRIS).processedVanillaMaterial(Material.NETHERITE_INGOT).nuggetVanillaMaterial(Material.NETHERITE_SCRAP).blockVanillaMaterial(Material.NETHERITE_BLOCK)
                .colorHex("#4A3B32").description("Indestructible ancient Nether alloy.").meltingDurationTicks(200)
                .durabilityBonus(2031).miningSpeed(9.0f).attackDamageBonus(5.0).traitName("Netherborn Core")
                .traitDescription("Complete fire/lava immunity and heavy knockback resistance.")
                .weaponTraitDescription("Netherborn Wrath: Inflicts searing soul fire and ignores 20% enemy defense.")
                .armorTraitDescription("Ancient Bastion: Total fire/lava immunity and +20% knockback resistance.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_quartz").name("Nether Quartz").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.COMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.NETHER_QUARTZ_ORE).processedVanillaMaterial(Material.QUARTZ).nuggetVanillaMaterial(Material.QUARTZ).blockVanillaMaterial(Material.QUARTZ_BLOCK)
                .colorHex("#F4F6F6").description("Jagged thermal silica crystal.").meltingDurationTicks(50)
                .durabilityBonus(280).miningSpeed(7.0f).attackDamageBonus(3.0).traitName("Serrated Shard")
                .traitDescription("Sharp edges inflict painful bleed wounds over time.")
                .weaponTraitDescription("Bleed Gouge: Attacks inflict painful bleeding damage over 4 seconds.")
                .armorTraitDescription("Crystalline Spikes: Reflects 20% melee damage back to attackers as sharp thorns.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_amethyst").name("Amethyst").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.AMETHYST_CLUSTER).processedVanillaMaterial(Material.AMETHYST_SHARD).nuggetVanillaMaterial(Material.AMETHYST_SHARD).blockVanillaMaterial(Material.AMETHYST_BLOCK)
                .colorHex("#AF7AC5").description("Resonant crystalline quartz geode.").meltingDurationTicks(65)
                .durabilityBonus(400).miningSpeed(7.2f).attackDamageBonus(2.5).traitName("Resonant Pulse")
                .traitDescription("Emits kinetic chime waves upon connecting critical hits.")
                .weaponTraitDescription("Resonant Chime: Crits release acoustic shockwaves damaging nearby foes for 2.5 damage.")
                .armorTraitDescription("Harmonic Buffer: Absorbs incoming kinetic shocks and projectile blasts.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_flint").name("Flint").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.COMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.FLINT).processedVanillaMaterial(Material.FLINT).nuggetVanillaMaterial(Material.FLINT).blockVanillaMaterial(Material.GRAVEL)
                .colorHex("#4C4E52").description("Sedimentary cryptocrystalline quartz mineral with razor conchoidal fracture.").meltingDurationTicks(40)
                .durabilityBonus(190).miningSpeed(6.5f).attackDamageBonus(2.0).traitName("Jagged Edge")
                .traitDescription("Abrasive flaking keeps edges razor-sharp during prolonged use.")
                .weaponTraitDescription("Razor Edge: 25% chance on strike to cause deep bleeding wounds.")
                .armorTraitDescription("Abrasive Scale: Melee attackers suffer thorns abrasion when striking.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_obsidian").name("Obsidian").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.MINERAL)
                .baseVanillaMaterial(Material.OBSIDIAN).processedVanillaMaterial(Material.OBSIDIAN).nuggetVanillaMaterial(Material.FLINT).blockVanillaMaterial(Material.OBSIDIAN)
                .colorHex("#2E1C4D").description("Dense volcanic silicate glass cooled instantly from lava.").meltingDurationTicks(160)
                .durabilityBonus(1800).miningSpeed(7.0f).attackDamageBonus(4.2).traitName("Void Cleave")
                .traitDescription("Extremely dense volcanic structure immune to explosive wear.")
                .weaponTraitDescription("Armor Cleave: Strikes bypass 25% of target armor protection.")
                .armorTraitDescription("Blast Hardening: Grants 50% resistance against explosions and blast damage.")
                .build());

        register(TinkerMaterial.builder()
                .id("mvtink_prismarine").name("Prismarine").origin(MineralOrigin.VANILLA).rarity(MaterialRarity.UNCOMMON).type(MaterialType.CRYSTAL)
                .baseVanillaMaterial(Material.PRISMARINE_SHARD).processedVanillaMaterial(Material.PRISMARINE_CRYSTALS).nuggetVanillaMaterial(Material.PRISMARINE_SHARD).blockVanillaMaterial(Material.PRISMARINE)
                .colorHex("#56A69E").description("Aquatic oceanic crystal recovered from ocean depths and elder guardians.").meltingDurationTicks(70)
                .durabilityBonus(350).miningSpeed(7.5f).attackDamageBonus(2.8).traitName("Aquatic Surge")
                .traitDescription("Maintains optimal mining speed underwater without aquatic penalties.")
                .weaponTraitDescription("Oceanic Strike: Deals +30% bonus damage in water or rain with water splash.")
                .armorTraitDescription("Abyssal Grace: Grants Conduit Power resonance and increased swim agility.")
                .build());
    }
}
