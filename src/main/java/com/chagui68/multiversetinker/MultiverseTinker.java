package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.access.AccessControl;
import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularArmorType;
import com.chagui68.multiversetinker.archaeology.ArchaeologyListener;
import com.chagui68.multiversetinker.archaeology.ArchaeologyLootTable;
import com.chagui68.multiversetinker.archaeology.ArchaeologyManager;
import com.chagui68.multiversetinker.casting.CastingListener;
import com.chagui68.multiversetinker.commands.MultiverseTinkerCommand;
import com.chagui68.multiversetinker.forge.ForgeListener;
import com.chagui68.multiversetinker.forge.ForgeManager;
import com.chagui68.multiversetinker.items.LoreWrap;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.smeltery.SmelteryListener;
import com.chagui68.multiversetinker.smeltery.SmelteryManager;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.EquipmentAnimation;
import com.chagui68.multiversetinker.tools.ModularToolListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class MultiverseTinker extends JavaPlugin {

    @Getter
    private static MultiverseTinker instance;

    @Getter
    private MaterialRegistry materialRegistry;
    @Getter
    private AlloyRegistry alloyRegistry;
    @Getter
    private TinkerItemRegistry itemRegistry;
    @Getter
    private ArchaeologyManager archaeologyManager;
    @Getter
    private SmelteryManager smelteryManager;
    @Getter
    private ForgeManager forgeManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        applyLoreSettings();
        applyAnimationSettings();
        applyEquipmentSettings();
        applyAccessSettings();

        getLogger().info("========================================");
        getLogger().info("   MultiverseTinker - Paper 1.21+       ");
        getLogger().info("           Author: Chagui68             ");
        getLogger().info("========================================");

        // Initialize Storage Keys
        TinkerKeys.initialize(this);

        // Register Materials & Items
        this.materialRegistry = new MaterialRegistry();
        this.alloyRegistry = new AlloyRegistry();
        // Vanilla catalysts (Nether Star, Blue Ice…) must exist before restored prime alloys resolve their parents.
        this.alloyRegistry.registerCatalystMaterials(materialRegistry);
        // Composite alloys forged in previous sessions must come back before items and recipes are built.
        this.alloyRegistry.enablePersistence(this, materialRegistry);
        this.alloyRegistry.registerAlloysIntoMaterialRegistry(materialRegistry);
        getLogger().info("Registered " + materialRegistry.getAll().size() + " geological, catalyst and alloy materials ("
                + alloyRegistry.getDynamicAlloyCount() + " player-forged composites, "
                + alloyRegistry.getPrimeAlloyCount() + " prime alloys restored).");

        this.itemRegistry = new TinkerItemRegistry(materialRegistry);

        // Archaeology System
        ArchaeologyLootTable lootTable = new ArchaeologyLootTable(materialRegistry);
        this.archaeologyManager = new ArchaeologyManager(this, lootTable, itemRegistry);
        // The drop table is weighted by rarity-weights of config.yml, so it is tuned the moment it exists.
        applyLootSettings();

        // Smeltery System
        this.smelteryManager = new SmelteryManager(this, materialRegistry, itemRegistry);

        // Forge Multiblock System
        this.forgeManager = new ForgeManager(this);
        forgeManager.startAuraTask();

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ArchaeologyListener(archaeologyManager), this);
        getServer().getPluginManager().registerEvents(new SmelteryListener(smelteryManager, itemRegistry), this);
        getServer().getPluginManager().registerEvents(new CastingListener(itemRegistry), this);
        getServer().getPluginManager().registerEvents(new ForgeListener(this, forgeManager, itemRegistry, materialRegistry), this);
        ModularToolListener modularToolListener = new ModularToolListener(this, materialRegistry);
        modularToolListener.startAuraTask();
        getServer().getPluginManager().registerEvents(modularToolListener, this);

        // Register Commands — /mvtink is the only command name, with no aliases.
        PluginCommand cmd = getCommand("mvtink");
        if (cmd != null) {
            MultiverseTinkerCommand executor = new MultiverseTinkerCommand(this, materialRegistry, itemRegistry);
            cmd.setExecutor(executor);
            cmd.setTabCompleter(executor);
        }

        // Repeating Smeltery Tick Task (runs every 5 ticks = 4 times a second)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (smelteryManager != null) {
                smelteryManager.tickSmelteries();
            }
        }, 5L, 5L);

        // Asynchronous Session Cleanup Task
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            if (archaeologyManager != null) {
                archaeologyManager.cleanExpiredSessions();
            }
        }, 100L, 100L);

        // Register Survival Crafting Recipes
        registerRecipes();

        getLogger().info("MultiverseTinker successfully enabled. Smeltery, Archaeology, Forge & Modular Tools online.");
    }

    /**
     * Applies the item lore presentation settings of {@code config.yml}.
     *
     * <p>Lore is wrapped by a static helper, so this is called on enable and again by
     * {@code /mvtink reload} — otherwise a changed width would only show on freshly built items.</p>
     */
    public void applyLoreSettings() {
        LoreWrap.configure(
                getConfig().getBoolean(LoreWrap.CONFIG_ENABLED, true),
                getConfig().getInt(LoreWrap.CONFIG_MAX_PIXELS, LoreWrap.DEFAULT_MAX_PIXELS),
                getConfig().getInt(LoreWrap.CONFIG_HEADER_PIXELS, LoreWrap.HEADER_MAX_PIXELS));

        getLogger().info("Item lore wrapping " + (LoreWrap.isEnabled()
                ? "enabled at " + LoreWrap.configuredMaxPixels() + "px (headers "
                        + LoreWrap.configuredHeaderPixels() + "px) per row."
                : "disabled."));
    }

    /**
     * Applies the perk animation settings of {@code config.yml}.
     *
     * <p>Like the lore settings, the animation catalogue is static, so this runs on enable and again
     * from {@code /mvtink reload} to let a server change the intensity without a restart.</p>
     */
    public void applyAnimationSettings() {
        EquipmentAnimation.configure(
                getConfig().getBoolean(EquipmentAnimation.CONFIG_ENABLED, true),
                getConfig().getDouble(EquipmentAnimation.CONFIG_PARTICLE_SCALE, EquipmentAnimation.DEFAULT_PARTICLE_SCALE),
                getConfig().getBoolean(EquipmentAnimation.CONFIG_SOUNDS, true),
                getConfig().getInt(EquipmentAnimation.CONFIG_COOLDOWN_MILLIS, EquipmentAnimation.DEFAULT_COOLDOWN_MILLIS));

        getLogger().info("Perk animations " + (EquipmentAnimation.isEnabled()
                ? "enabled at " + EquipmentAnimation.configuredParticleScale() + "x particles (sounds "
                        + (EquipmentAnimation.isSoundsEnabled() ? "on" : "off") + ", cooldown "
                        + EquipmentAnimation.configuredCooldownMillis() + "ms)."
                : "disabled."));
    }

    /**
     * Applies the modular equipment rules of {@code config.yml}.
     *
     * <p>Forged equipment is always unbreakable for vanilla — its own durability counter is the only
     * wear it can take — while the attack damage printed in its lore can be toggled between the
     * mineral-rolled value and the plain vanilla material value.</p>
     *
     * <p>The armor perk curve is read here too, so a server retunes how much of a hit each slot
     * answers with without a rebuild. The curve is applied to everything the plugin derives from that
     * moment on, both the sentence a freshly forged piece prints and the damage combat subtracts; a
     * piece forged before the change keeps the percentage already baked into its lore until it evolves
     * or is reforged.</p>
     */
    public void applyEquipmentSettings() {
        TinkerItemBuilder.configureEquipment(
                getConfig().getBoolean(TinkerItemBuilder.CONFIG_MODULAR_ATTACK_DAMAGE, true));
        TinkerItemBuilder.configureArmorDefense(
                getConfig().getBoolean(TinkerItemBuilder.CONFIG_MODULAR_ARMOR_DEFENSE, true));
        TinkerItemBuilder.configurePerkScaling(
                getConfig().getDouble(TinkerItemBuilder.CONFIG_PERK_SCALE_PER_POINT,
                        TinkerItemBuilder.DEFAULT_PERK_SCALE_PER_POINT),
                readArmorPerkCaps());

        getLogger().info("Modular equipment: vanilla durability disabled, attack damage "
                + (TinkerItemBuilder.isModularAttackDamage() ? "taken from the forged materials."
                        : "left at the vanilla material value."));
        getLogger().info("Modular armor: protection "
                + (TinkerItemBuilder.isModularArmorDefense()
                        ? "rolled from the forged minerals (Defense, Toughness, knockback)."
                        : "left at the vanilla values of the tier material."));
        getLogger().info("Armor perk scaling: " + percent(TinkerItemBuilder.getPerkScalePerPoint())
                + " of mitigation per point of rolled Defense and Toughness, capped at " + perkCapsSummary() + ".");
    }

    /**
     * The mitigation ceiling of every slot a server has set one for.
     *
     * <p>A slot left out of the file keeps the ceiling the plugin ships with, so removing a key
     * restores it on the next reload instead of freezing whatever value was applied last. A key that
     * names no slot with a share to cap is reported, because it would otherwise do nothing at all.</p>
     */
    @Nonnull
    private Map<ModularArmorType, Double> readArmorPerkCaps() {
        Map<ModularArmorType, Double> caps = new EnumMap<>(ModularArmorType.class);
        for (ModularArmorType type : ModularArmorType.values()) {
            String key = TinkerItemBuilder.CONFIG_PERK_CAP_PREFIX + type.name().toLowerCase(Locale.ROOT);
            if (getConfig().isSet(key)) {
                caps.put(type, getConfig().getDouble(key));
            }
        }

        ConfigurationSection section = getConfig()
                .getConfigurationSection("equipment.armor-perk.caps");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                ModularArmorType type = armorTypeByKey(key);
                if (type == null || !TinkerItemBuilder.printsMitigation(type)) {
                    getLogger().warning("Ignoring equipment.armor-perk.caps." + key + ": only "
                            + printMitigatingSlots() + " answer a hit with a share of it. Leggings answer with"
                            + " mobility, so they have no number to cap.");
                }
            }
        }
        return caps;
    }

    /** The armor slot a config key names, or {@code null} when it names none. */
    @Nullable
    private static ModularArmorType armorTypeByKey(@Nonnull String key) {
        for (ModularArmorType type : ModularArmorType.values()) {
            if (type.name().equalsIgnoreCase(key.trim())) return type;
        }
        return null;
    }

    /** The slots with a perk share worth capping, spelled the way {@code config.yml} spells them. */
    @Nonnull
    private static String printMitigatingSlots() {
        StringJoiner joiner = new StringJoiner(", ");
        for (ModularArmorType type : ModularArmorType.values()) {
            if (TinkerItemBuilder.printsMitigation(type)) {
                joiner.add(type.name().toLowerCase(Locale.ROOT));
            }
        }
        return joiner.toString();
    }

    /** The perk curve as one log line: {@code helmet 65% · chestplate 60% · boots 75%}. */
    @Nonnull
    private static String perkCapsSummary() {
        StringJoiner joiner = new StringJoiner(" · ");
        for (ModularArmorType type : ModularArmorType.values()) {
            if (!TinkerItemBuilder.printsMitigation(type)) continue;
            joiner.add(type.name().toLowerCase(Locale.ROOT) + " " + percent(TinkerItemBuilder.getPerkCap(type)));
        }
        return joiner.toString();
    }

    /** A share the way the lore prints it: {@code 0.38} becomes {@code 38%}. */
    @Nonnull
    private static String percent(double fraction) {
        return String.format(Locale.US, "%.0f%%", fraction * 100);
    }

    /**
     * Applies the access rules of {@code config.yml}.
     *
     * <p>Who may forge, browse or brush is a server decision, so it lives in the config rather than in
     * the permission nodes alone: a server without a permissions plugin can open or close each surface
     * here, and a server with one keeps the fine-grained nodes. Re-read by {@code /mvtink reload}.</p>
     *
     * <p>A rule that hands players too much or that nobody can satisfy is warned about here, at startup
     * and on every reload, rather than being left for a player to discover: an op-only surface on a
     * server without a single operator, or a leftover key still asking for the administrative commands
     * to be public.</p>
     */
    public void applyAccessSettings() {
        List<String> ignored = AccessControl.configure(getConfig());
        List<AccessControl.Advisory> advisories = AccessControl.advisories(getConfig(),
                !Bukkit.getOperators().isEmpty());
        Set<String> reported = advisories.stream()
                .map(AccessControl.Advisory::configKey)
                .collect(Collectors.toSet());

        for (String key : ignored) {
            // A stale key that is also dangerous gets the sharper message below, not both.
            if (reported.contains(key)) continue;
            getLogger().warning("Ignoring " + key + ": the administrative /mvtink subcommands are always"
                    + " behind " + MultiverseTinkerCommand.ADMIN_PERMISSION + " or one of its subcommand nodes"
                    + " (operators by default, or whoever a permissions plugin grants one to). Only the codex"
                    + " can be opened to players.");
        }
        for (AccessControl.Advisory advisory : advisories) {
            getLogger().warning(advisory.severity() + " access rule — " + advisory.message());
        }
        getLogger().info("Access control — " + AccessControl.summary());
        getLogger().info("Administrative nodes — " + AccessControl.adminNodeSummary());
    }

    /**
     * Applies the {@code rarity-weights} section of {@code config.yml} to the archaeology drop table.
     *
     * <p>How often a legendary mineral comes out of the stone is a server decision, so the table reads
     * its weights from the config instead of the built-in ones. Re-read by {@code /mvtink reload}, like
     * every other setting.</p>
     */
    public void applyLootSettings() {
        if (archaeologyManager == null) return;

        ArchaeologyLootTable lootTable = archaeologyManager.getLootTable();
        lootTable.configureWeights(getConfig());
        getLogger().info("Archaeology rarity weights — " + lootTable.weightsSummary()
                + (lootTable.usesDefaultWeights() ? " (shipped)" : " (configured)"));
    }

    private void registerRecipes() {
        // 1. Prospector Brush Recipe
        NamespacedKey brushKey = new NamespacedKey(this, "mvtink_recipe_prospector_brush");
        ShapedRecipe brushRecipe = new ShapedRecipe(brushKey, itemRegistry.getProspectorBrush());
        brushRecipe.shape(" G ", "CBC", " R ");
        brushRecipe.setIngredient('G', Material.GOLD_INGOT);
        brushRecipe.setIngredient('C', Material.COPPER_INGOT);
        brushRecipe.setIngredient('B', Material.BRUSH);
        brushRecipe.setIngredient('R', Material.AMETHYST_SHARD);
        getServer().addRecipe(brushRecipe);

        // 2. Smeltery Crucible Recipe
        NamespacedKey smelteryKey = new NamespacedKey(this, "mvtink_recipe_smeltery");
        ShapedRecipe smelteryRecipe = new ShapedRecipe(smelteryKey, itemRegistry.getSmelteryItem());
        smelteryRecipe.shape("SFS", "MBM", "SSS");
        smelteryRecipe.setIngredient('S', Material.SMOOTH_STONE);
        smelteryRecipe.setIngredient('F', Material.BLAST_FURNACE);
        smelteryRecipe.setIngredient('M', Material.MAGMA_BLOCK);
        smelteryRecipe.setIngredient('B', Material.BUCKET);
        getServer().addRecipe(smelteryRecipe);

        // 3. Casting Mold Recipes (Ingot, Nugget, Block Casts)
        ItemStack ingotCast = itemRegistry.getCastItem(CastType.INGOT);
        if (ingotCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_ingot");
            ShapedRecipe recipe = new ShapedRecipe(key, ingotCast);
            recipe.shape("BBB", "B B", "BBB");
            recipe.setIngredient('B', Material.BRICK);
            getServer().addRecipe(recipe);
        }

        ItemStack nuggetCast = itemRegistry.getCastItem(CastType.NUGGET);
        if (nuggetCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_nugget");
            ShapedRecipe recipe = new ShapedRecipe(key, nuggetCast);
            recipe.shape("B B", " C ", "B B");
            recipe.setIngredient('B', Material.BRICK);
            recipe.setIngredient('C', Material.CLAY_BALL);
            getServer().addRecipe(recipe);
        }

        ItemStack blockCast = itemRegistry.getCastItem(CastType.BLOCK);
        if (blockCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_block");
            ShapedRecipe recipe = new ShapedRecipe(key, blockCast);
            recipe.shape("BBB", "BCB", "BBB");
            recipe.setIngredient('B', Material.BRICK);
            recipe.setIngredient('C', Material.IRON_BLOCK);
            getServer().addRecipe(recipe);
        }

        // Head, Rod, and Binding Cast recipes
        ItemStack headCast = itemRegistry.getCastItem(CastType.HEAD);
        if (headCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_head");
            ShapedRecipe recipe = new ShapedRecipe(key, headCast);
            recipe.shape("BGB", "B B", "BBB");
            recipe.setIngredient('B', Material.BRICK);
            recipe.setIngredient('G', Material.GOLD_INGOT);
            getServer().addRecipe(recipe);
        }

        ItemStack rodCast = itemRegistry.getCastItem(CastType.ROD);
        if (rodCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_rod");
            ShapedRecipe recipe = new ShapedRecipe(key, rodCast);
            recipe.shape("BCB", "B B", "B B");
            recipe.setIngredient('B', Material.BRICK);
            recipe.setIngredient('C', Material.COPPER_INGOT);
            getServer().addRecipe(recipe);
        }

        ItemStack bindingCast = itemRegistry.getCastItem(CastType.BINDING);
        if (bindingCast != null) {
            NamespacedKey key = new NamespacedKey(this, "mvtink_recipe_cast_binding");
            ShapedRecipe recipe = new ShapedRecipe(key, bindingCast);
            recipe.shape("BIB", " C ", "BBB");
            recipe.setIngredient('B', Material.BRICK);
            recipe.setIngredient('I', Material.IRON_INGOT);
            recipe.setIngredient('C', Material.CLAY_BALL);
            getServer().addRecipe(recipe);
        }

        // 4. Register 9 Nuggets <-> 1 Ingot & 9 Ingots <-> 1 Block for each material
        for (TinkerMaterial material : materialRegistry.getAll()) {
            String matId = material.getId();
            ItemStack nugget = itemRegistry.getNuggetItem(matId);
            ItemStack ingot = itemRegistry.getIngotItem(matId);
            ItemStack block = itemRegistry.getBlockItem(matId);

            if (nugget != null && ingot != null) {
                // 9 Nuggets -> 1 Ingot
                NamespacedKey nToIKey = new NamespacedKey(this, matId + "_nuggets_to_ingot");
                ShapedRecipe nToIRecipe = new ShapedRecipe(nToIKey, ingot);
                nToIRecipe.shape("NNN", "NNN", "NNN");
                nToIRecipe.setIngredient('N', new RecipeChoice.ExactChoice(nugget));
                getServer().addRecipe(nToIRecipe);

                // 1 Ingot -> 9 Nuggets
                ItemStack nineNuggets = nugget.clone();
                nineNuggets.setAmount(9);
                NamespacedKey iToNKey = new NamespacedKey(this, matId + "_ingot_to_nuggets");
                ShapelessRecipe iToNRecipe = new ShapelessRecipe(iToNKey, nineNuggets);
                iToNRecipe.addIngredient(new RecipeChoice.ExactChoice(ingot));
                getServer().addRecipe(iToNRecipe);
            }

            if (ingot != null && block != null) {
                // 9 Ingots -> 1 Block
                NamespacedKey iToBKey = new NamespacedKey(this, matId + "_ingots_to_block");
                ShapedRecipe iToBRecipe = new ShapedRecipe(iToBKey, block);
                iToBRecipe.shape("III", "III", "III");
                iToBRecipe.setIngredient('I', new RecipeChoice.ExactChoice(ingot));
                getServer().addRecipe(iToBRecipe);

                // 1 Block -> 9 Ingots
                ItemStack nineIngots = ingot.clone();
                nineIngots.setAmount(9);
                NamespacedKey bToIKey = new NamespacedKey(this, matId + "_block_to_ingots");
                ShapelessRecipe bToIRecipe = new ShapelessRecipe(bToIKey, nineIngots);
                bToIRecipe.addIngredient(new RecipeChoice.ExactChoice(block));
                getServer().addRecipe(bToIRecipe);
            }
        }
    }

    @Override
    public void onDisable() {
        if (alloyRegistry != null) {
            alloyRegistry.flush();
        }
        if (forgeManager != null) {
            forgeManager.stopAuraTask();
        }
        getLogger().info("MultiverseTinker disabled.");
        instance = null;
    }
}
