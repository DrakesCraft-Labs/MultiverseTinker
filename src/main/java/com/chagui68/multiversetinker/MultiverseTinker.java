package com.chagui68.multiversetinker;

import com.chagui68.multiversetinker.alloys.AlloyRegistry;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.archaeology.ArchaeologyListener;
import com.chagui68.multiversetinker.archaeology.ArchaeologyLootTable;
import com.chagui68.multiversetinker.archaeology.ArchaeologyManager;
import com.chagui68.multiversetinker.casting.CastingListener;
import com.chagui68.multiversetinker.commands.MultiverseTinkerCommand;
import com.chagui68.multiversetinker.forge.ForgeListener;
import com.chagui68.multiversetinker.forge.ForgeManager;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.smeltery.SmelteryListener;
import com.chagui68.multiversetinker.smeltery.SmelteryManager;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import com.chagui68.multiversetinker.tools.ModularToolListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

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

        getLogger().info("========================================");
        getLogger().info("   MultiverseTinker - Paper 1.21+       ");
        getLogger().info("           Author: Chagui68             ");
        getLogger().info("========================================");

        // Initialize Storage Keys
        TinkerKeys.initialize(this);

        // Register Materials & Items
        this.materialRegistry = new MaterialRegistry();
        this.alloyRegistry = new AlloyRegistry();
        // Composite alloys forged in previous sessions must come back before items and recipes are built.
        this.alloyRegistry.enablePersistence(this, materialRegistry);
        this.alloyRegistry.registerAlloysIntoMaterialRegistry(materialRegistry);
        getLogger().info("Registered " + materialRegistry.getAll().size() + " geological and alloy materials ("
                + alloyRegistry.getDynamicAlloyCount() + " player-forged composites restored).");

        this.itemRegistry = new TinkerItemRegistry(materialRegistry);

        // Archaeology System
        ArchaeologyLootTable lootTable = new ArchaeologyLootTable(materialRegistry);
        this.archaeologyManager = new ArchaeologyManager(this, lootTable, itemRegistry);

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

        // Register Commands
        PluginCommand cmd = getCommand("multiversetinker");
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
