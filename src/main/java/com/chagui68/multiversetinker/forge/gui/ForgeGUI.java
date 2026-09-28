package com.chagui68.multiversetinker.forge.gui;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.CastType;
import com.chagui68.multiversetinker.api.ModularToolType;
import com.chagui68.multiversetinker.api.ToolPartType;
import com.chagui68.multiversetinker.items.TinkerItemBuilder;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import com.chagui68.multiversetinker.storage.TinkerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.*;

public class ForgeGUI implements InventoryHolder {

    public static final int SLOT_FORGE_MATERIAL = 10;
    public static final int SLOT_FORGE_CAST = 12;
    public static final int SLOT_FORGE_STRIKE = 20;
    public static final int SLOT_FORGE_OUTPUT = 21;
    public static final int SLOT_CARVE_MOLDS = 18;

    public static final int SLOT_TOOL_TYPE_SELECTOR = 24;
    public static final int SLOT_TOOL_HEAD = 30;
    public static final int SLOT_TOOL_ROD = 32;
    public static final int SLOT_TOOL_BINDING = 34;
    public static final int SLOT_TOOL_ASSEMBLE = 41;
    public static final int SLOT_TOOL_OUTPUT = 43;

    private static final Set<Integer> INTERACTIVE_SLOTS = Set.of(
            SLOT_FORGE_MATERIAL, SLOT_FORGE_CAST, SLOT_FORGE_OUTPUT,
            SLOT_TOOL_HEAD, SLOT_TOOL_ROD, SLOT_TOOL_BINDING, SLOT_TOOL_OUTPUT
    );

    private final MultiverseTinker plugin;
    private final TinkerItemRegistry itemRegistry;
    private final MaterialRegistry materialRegistry;
    private final Inventory inventory;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private ModularToolType selectedToolType = ModularToolType.PICKAXE;

    public ForgeGUI(@Nonnull MultiverseTinker plugin,
                    @Nonnull TinkerItemRegistry itemRegistry,
                    @Nonnull MaterialRegistry materialRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
        this.materialRegistry = materialRegistry;
        this.inventory = Bukkit.createInventory(this, 54, miniMessage.deserialize("<gradient:#ff4500:#ffaa00><b>Multiverse Forge</b></gradient>"));
        setupLayout();
    }

    private void setupLayout() {
        ItemStack grayGlass = createDecor(Material.GRAY_STAINED_GLASS_PANE, " ");
        ItemStack orangeGlass = createDecor(Material.ORANGE_STAINED_GLASS_PANE, " ");
        ItemStack redGlass = createDecor(Material.RED_STAINED_GLASS_PANE, " ");

        // Fill background
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, grayGlass);
        }

        // Center column divider (fire theme)
        int[] dividers = {4, 13, 22, 31, 40, 49};
        for (int d : dividers) {
            inventory.setItem(d, orangeGlass);
        }

        // Status Beacon in Slot 4
        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta beaconMeta = beacon.getItemMeta();
        if (beaconMeta != null) {
            beaconMeta.displayName(miniMessage.deserialize("<gradient:#ffaa00:#ff4500><b>MULTIVERSE FORGE (ACTIVE)</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(miniMessage.deserialize("<green>✔ Thermal Resonance Active (100%)</green>").decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Surrounded by 4 lava pillars & chiseled tuff architecture.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("⚒ Left Section: Forge Tool Parts & Casts", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("⚒ Right Section: Assemble Modular Equipment", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            beaconMeta.lore(lore);
            beacon.setItemMeta(beaconMeta);
        }
        inventory.setItem(4, beacon);

        // Section 1: Tool Part Forging
        inventory.setItem(1, createDecor(Material.IRON_INGOT, "<gold><b>[ 1. Part Forging ]</b></gold>"));
        inventory.setItem(SLOT_CARVE_MOLDS, createCarveMoldsButton());
        inventory.setItem(SLOT_FORGE_STRIKE, createStrikeButton());

        // Clear interactive slots for player items
        inventory.setItem(SLOT_FORGE_MATERIAL, null);
        inventory.setItem(SLOT_FORGE_CAST, null);
        inventory.setItem(SLOT_FORGE_OUTPUT, null);

        // Section 2: Modular Tool Assembly
        inventory.setItem(7, createDecor(Material.DIAMOND_SWORD, "<aqua><b>[ 2. Tool Assembly ]</b></aqua>"));
        updateToolTypeSelectorItem();
        inventory.setItem(SLOT_TOOL_ASSEMBLE, createAssembleButton());

        // Clear assembly input slots
        inventory.setItem(SLOT_TOOL_HEAD, null);
        inventory.setItem(SLOT_TOOL_ROD, null);
        inventory.setItem(SLOT_TOOL_BINDING, null);
        inventory.setItem(SLOT_TOOL_OUTPUT, null);

        // Guide Codex in Slot 49
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        if (bookMeta != null) {
            bookMeta.displayName(miniMessage.deserialize("<gradient:#ffff55:#ffaa00><b>Metallurgical Codex</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("• Head: Dictates Attack Damage, Speed & Primary Trait.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("• Handle/Rod: Dictates Base Durability & Handle Trait.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("• Binding: Adds Auxiliary Durability & Secondary Trait.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Every one of the 90 minerals features distinct properties!", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false));
            bookMeta.lore(lore);
            book.setItemMeta(bookMeta);
        }
        inventory.setItem(49, book);
    }

    private void updateToolTypeSelectorItem() {
        ItemStack selector = new ItemStack(selectedToolType.getBaseMaterial());
        ItemMeta meta = selector.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#00f2fe:#4facfe><b>Selected Tool: " + selectedToolType.getDisplayName() + "</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(selectedToolType.getDescription(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Click to cycle tool type:", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  Pickaxe → Sword → Axe → Shovel → Hoe", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            selector.setItemMeta(meta);
        }
        inventory.setItem(SLOT_TOOL_TYPE_SELECTOR, selector);
    }

    private ItemStack createStrikeButton() {
        ItemStack item = new ItemStack(Material.SMITHING_TABLE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#ffaa00:#ff5500><b>⚒ Strike & Forge Part</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Place a Molten Bucket in Slot 10 and a Cast in Slot 12.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Consumes: ", NamedTextColor.GOLD)
                    .append(Component.text("1 Molten Liquid Bucket", NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Preserves: ", NamedTextColor.GREEN)
                    .append(Component.text("Casting Mold (Reusable)", NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createCarveMoldsButton() {
        ItemStack item = new ItemStack(Material.BRICK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#e67e22:#f39c12><b>[ Quick Mold Carver ]</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Click to carve reusable casting molds:", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Left-Click: Tool Head Cast (1 Clay Brick)", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Right-Click: Tool Rod Cast (1 Clay Brick)", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Shift-Click: Tool Binding Cast (1 Clay Brick)", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createAssembleButton() {
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize("<gradient:#22c55e:#10b981><b>⚒ Assemble Modular Tool</b></gradient>")
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Requires:", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 30: Tool Head (Cabeza)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 32: Tool Handle/Rod (Palo)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("  • Slot 34: Tool Binding (Mango)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("Click to assemble into a customized tool with all 3 traits.", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createDecor(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(miniMessage.deserialize(name).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(meta);
        }
        return item;
    }

    public void handleClick(@Nonnull InventoryClickEvent event, @Nonnull Player player) {
        int rawSlot = event.getRawSlot();

        // Allow interacting freely with player's own inventory
        if (rawSlot >= 54) {
            Bukkit.getScheduler().runTask(plugin, this::updateAssemblyPreview);
            return;
        }

        // Cycle tool type selector
        if (rawSlot == SLOT_TOOL_TYPE_SELECTOR) {
            event.setCancelled(true);
            ModularToolType[] types = ModularToolType.values();
            int nextIdx = (selectedToolType.ordinal() + 1) % types.length;
            this.selectedToolType = types[nextIdx];
            updateToolTypeSelectorItem();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
            updateAssemblyPreview();
            return;
        }

        // Quick Mold Carver
        if (rawSlot == SLOT_CARVE_MOLDS) {
            event.setCancelled(true);
            handleCarveMold(event, player);
            return;
        }

        // Strike anvil to forge part
        if (rawSlot == SLOT_FORGE_STRIKE) {
            event.setCancelled(true);
            handleForgePart(player);
            return;
        }

        // Assemble tool button
        if (rawSlot == SLOT_TOOL_ASSEMBLE) {
            event.setCancelled(true);
            handleAssembleTool(player);
            return;
        }

        // Prevent taking decorative panes
        if (!INTERACTIVE_SLOTS.contains(rawSlot)) {
            event.setCancelled(true);
            return;
        }

        // Schedule preview update after player places/takes items in interactive slots
        Bukkit.getScheduler().runTask(plugin, this::updateAssemblyPreview);
    }

    private void handleCarveMold(InventoryClickEvent event, Player player) {
        CastType typeToCarve;
        if (event.isShiftClick()) {
            typeToCarve = CastType.BINDING;
        } else if (event.isRightClick()) {
            typeToCarve = CastType.ROD;
        } else {
            typeToCarve = CastType.HEAD;
        }

        // Check if player has clay bricks
        if (!player.getInventory().contains(Material.BRICK)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ You need 1 Clay Brick in your inventory to carve this casting mold!</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.7f);
            return;
        }

        player.getInventory().removeItem(new ItemStack(Material.BRICK, 1));
        ItemStack cast = itemRegistry.getCastItem(typeToCarve);
        if (cast != null) {
            player.getInventory().addItem(cast);
            player.playSound(player.getLocation(), Sound.BLOCK_GRAVEL_PLACE, 1.0f, 1.4f);
            player.sendMessage(miniMessage.deserialize("<green>✔ Successfully carved " + typeToCarve.getDisplayName() + "!</green>"));
        }
    }

    private void handleForgePart(Player player) {
        ItemStack matInput = inventory.getItem(SLOT_FORGE_MATERIAL);
        ItemStack castInput = inventory.getItem(SLOT_FORGE_CAST);

        if (matInput == null || castInput == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing components! Place a Molten Liquid Bucket in Slot 10 and a Cast in Slot 12.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        ItemMeta matMeta = matInput.getItemMeta();
        ItemMeta castMeta = castInput.getItemMeta();
        if (matMeta == null || castMeta == null) return;

        PersistentDataContainer matPdc = matMeta.getPersistentDataContainer();
        PersistentDataContainer castPdc = castMeta.getPersistentDataContainer();

        String materialId = matPdc.get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        String castTypeName = castPdc.get(TinkerKeys.CAST_TYPE, PersistentDataType.STRING);

        if (materialId == null || castTypeName == null) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Invalid forge items! Must use a valid Molten Liquid Bucket and Reusable Cast.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        CastType castType;
        try {
            castType = CastType.valueOf(castTypeName);
        } catch (IllegalArgumentException e) {
            return;
        }

        ItemStack forgedItem = switch (castType) {
            case HEAD -> itemRegistry.getToolHeadItem(materialId);
            case ROD -> itemRegistry.getToolRodItem(materialId);
            case BINDING -> itemRegistry.getToolBindingItem(materialId);
            case INGOT -> itemRegistry.getIngotItem(materialId);
            case NUGGET -> {
                ItemStack n = itemRegistry.getNuggetItem(materialId);
                if (n != null) n.setAmount(9);
                yield n;
            }
            case BLOCK -> itemRegistry.getBlockItem(materialId);
        };

        if (forgedItem == null) return;

        // Verify output slot is free or can take the item
        ItemStack currentOutput = inventory.getItem(SLOT_FORGE_OUTPUT);
        if (currentOutput != null && currentOutput.getType() != Material.AIR) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Output slot is occupied! Remove the forged part first.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 1.0f, 0.5f);
            return;
        }

        // Consume 1 molten bucket and return empty bucket
        if (matInput.getAmount() > 1) {
            matInput.setAmount(matInput.getAmount() - 1);
            player.getInventory().addItem(new ItemStack(Material.BUCKET));
        } else {
            inventory.setItem(SLOT_FORGE_MATERIAL, new ItemStack(Material.BUCKET));
        }

        inventory.setItem(SLOT_FORGE_OUTPUT, forgedItem);

        // Sound & particles
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.8f, 1.5f);
        player.spawnParticle(Particle.LAVA, player.getLocation().add(0, 1, 0), 10, 0.3, 0.3, 0.3, 0.0);
        player.sendMessage(miniMessage.deserialize("<green>✔ Forged part successfully!</green>"));
    }

    private void updateAssemblyPreview() {
        ItemStack headItem = inventory.getItem(SLOT_TOOL_HEAD);
        ItemStack rodItem = inventory.getItem(SLOT_TOOL_ROD);
        ItemStack bindingItem = inventory.getItem(SLOT_TOOL_BINDING);

        if (headItem == null || rodItem == null || bindingItem == null) {
            // Incomplete parts -> clear preview slot
            ItemStack currentOut = inventory.getItem(SLOT_TOOL_OUTPUT);
            if (currentOut != null && isModularTool(currentOut)) {
                inventory.setItem(SLOT_TOOL_OUTPUT, null);
            }
            return;
        }

        TinkerMaterial headMat = getMaterialFromPart(headItem, ToolPartType.HEAD);
        TinkerMaterial rodMat = getMaterialFromPart(rodItem, ToolPartType.ROD);
        TinkerMaterial bindingMat = getMaterialFromPart(bindingItem, ToolPartType.BINDING);

        if (headMat == null || rodMat == null || bindingMat == null) {
            return;
        }

        ItemStack previewTool = TinkerItemBuilder.createModularTool(selectedToolType, headMat, rodMat, bindingMat);
        inventory.setItem(SLOT_TOOL_OUTPUT, previewTool);
    }

    private void handleAssembleTool(Player player) {
        updateAssemblyPreview();
        ItemStack assembled = inventory.getItem(SLOT_TOOL_OUTPUT);
        if (assembled == null || !isModularTool(assembled)) {
            player.sendMessage(miniMessage.deserialize("<red>⚠ Missing tool parts! Provide a valid Head, Rod, and Binding in slots 30, 32, and 34.</red>"));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.6f);
            return;
        }

        // Consume parts
        decrementSlot(SLOT_TOOL_HEAD);
        decrementSlot(SLOT_TOOL_ROD);
        decrementSlot(SLOT_TOOL_BINDING);

        inventory.setItem(SLOT_TOOL_OUTPUT, null);
        player.getInventory().addItem(assembled);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.2f, 1.4f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
        player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1.2, 0), 30, 0.4, 0.4, 0.4, 0.1);
        player.sendMessage(miniMessage.deserialize("<green>✔ Successfully forged a new " + selectedToolType.getDisplayName() + "!</green>"));

        updateAssemblyPreview();
    }

    private void decrementSlot(int slot) {
        ItemStack item = inventory.getItem(slot);
        if (item != null) {
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                inventory.setItem(slot, null);
            }
        }
    }

    private TinkerMaterial getMaterialFromPart(ItemStack item, ToolPartType expectedType) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String partType = pdc.get(TinkerKeys.TOOL_PART_TYPE, PersistentDataType.STRING);
        if (partType == null || !partType.equalsIgnoreCase(expectedType.name())) return null;

        String matId = pdc.get(TinkerKeys.MATERIAL_ID, PersistentDataType.STRING);
        if (matId == null) return null;
        return materialRegistry.get(matId);
    }

    private boolean isModularTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        Byte b = meta.getPersistentDataContainer().get(TinkerKeys.IS_MODULAR_TOOL, PersistentDataType.BYTE);
        return b != null && b == (byte) 1;
    }

    public void handleClose(@Nonnull InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        // Return leftover interactive items to player
        int[] returnSlots = {SLOT_FORGE_MATERIAL, SLOT_FORGE_CAST, SLOT_FORGE_OUTPUT, SLOT_TOOL_HEAD, SLOT_TOOL_ROD, SLOT_TOOL_BINDING};
        for (int slot : returnSlots) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                inventory.setItem(slot, null);
                Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                for (ItemStack drop : leftover.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), drop);
                }
            }
        }

        // Clear preview output
        inventory.setItem(SLOT_TOOL_OUTPUT, null);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
