package com.chagui68.multiversetinker.smeltery;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SmelteryGUI implements InventoryHolder {

    public static final int SLOT_RAW_INPUT = 10;
    public static final int SLOT_BUCKET_INPUT = 12;
    public static final int SLOT_STATUS_INDICATOR = 14;
    public static final int SLOT_OUTPUT = 16;

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Getter
    private final Block smelteryBlock;
    @Getter
    private final Inventory inventory;

    @Getter
    @Setter
    private int currentProgressTicks = 0;
    @Getter
    @Setter
    private int requiredProgressTicks = 100;
    @Getter
    @Setter
    private String currentSmeltingMaterialId = null;

    public SmelteryGUI(@Nonnull Block smelteryBlock) {
        this.smelteryBlock = smelteryBlock;
        Component title = MINI_MESSAGE.deserialize("<gradient:#ff4500:#ffaa00>Tinker Smeltery Crucible</gradient>");
        this.inventory = Bukkit.createInventory(this, 27, title);
        initializeLayout();
    }

    private void initializeLayout() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = filler.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(" "));
            filler.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i != SLOT_RAW_INPUT && i != SLOT_BUCKET_INPUT && i != SLOT_STATUS_INDICATOR && i != SLOT_OUTPUT) {
                inventory.setItem(i, filler);
            }
        }
    }

    public void updateStatus(boolean hasLava, boolean isSmelting, float progressRatio) {
        updateStatus(hasLava ? HeatSource.LAVA : HeatSource.NONE, isSmelting, progressRatio);
    }

    public void updateStatus(@Nonnull HeatSource heatSource, boolean isSmelting, float progressRatio) {
        ItemStack indicator;
        if (heatSource == HeatSource.NONE) {
            indicator = new ItemStack(Material.BARRIER);
            ItemMeta meta = indicator.getItemMeta();
            if (meta != null) {
                meta.displayName(MINI_MESSAGE.deserialize("<red><b>❌ Inactive: No Heat Source</b></red>")
                        .decoration(TextDecoration.ITALIC, false));
                meta.lore(List.of(
                        Component.text("Crucible is cold and unheated!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                        Component.empty(),
                        MINI_MESSAGE.deserialize("<yellow>Place a valid heat source directly beneath</yellow>").decoration(TextDecoration.ITALIC, false),
                        MINI_MESSAGE.deserialize("<yellow>this Smeltery block to ignite the crucible:</yellow>").decoration(TextDecoration.ITALIC, false),
                        Component.empty(),
                        MINI_MESSAGE.deserialize("<gray>• <gold><b>Lava</b></gold>: 100% Speed (10% consume chance per melt)</gray>").decoration(TextDecoration.ITALIC, false),
                        MINI_MESSAGE.deserialize("<gray>• <red><b>Magma Block</b></red>: 70% Speed (Infinite, non-consumable)</gray>").decoration(TextDecoration.ITALIC, false)
                ));
                indicator.setItemMeta(meta);
            }
        } else if (isSmelting) {
            indicator = new ItemStack(Material.FIRE_CHARGE);
            ItemMeta meta = indicator.getItemMeta();
            if (meta != null) {
                meta.displayName(MINI_MESSAGE.deserialize("<gradient:#ff4500:#ffaa00><b>🔥 Smelting in Progress...</b></gradient>")
                        .decoration(TextDecoration.ITALIC, false));

                int percent = (int) (progressRatio * 100);
                List<Component> lore = new ArrayList<>();
                if (heatSource == HeatSource.LAVA) {
                    lore.add(Component.text("Heat Source: Lava (100% Speed)", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                } else {
                    lore.add(Component.text("Heat Source: Magma Block (70% Speed / -30% Speed)", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
                }
                lore.add(Component.empty());
                lore.add(MINI_MESSAGE.deserialize("<gray>Melting Progress: </gray><gold>" + percent + "%</gold>").decoration(TextDecoration.ITALIC, false));
                lore.add(MINI_MESSAGE.deserialize("<dark_gray>[" + "█".repeat(Math.max(1, percent / 10)) + "-".repeat(Math.max(0, 10 - percent / 10)) + "]</dark_gray>").decoration(TextDecoration.ITALIC, false));
                lore.add(Component.empty());
                if (heatSource == HeatSource.LAVA) {
                    lore.add(Component.text("Notice: 10% chance to consume lava source upon melt.", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false));
                } else {
                    lore.add(Component.text("Notice: Thermal output reduced by 30% (Infinite heat).", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false));
                }
                meta.lore(lore);
                indicator.setItemMeta(meta);
            }
        } else {
            indicator = new ItemStack(heatSource == HeatSource.MAGMA_BLOCK ? Material.MAGMA_CREAM : Material.BLAZE_POWDER);
            ItemMeta meta = indicator.getItemMeta();
            if (meta != null) {
                if (heatSource == HeatSource.LAVA) {
                    meta.displayName(MINI_MESSAGE.deserialize("<gold><b>🔥 Crucible Heated (Lava Detected)</b></gold>")
                            .decoration(TextDecoration.ITALIC, false));
                    meta.lore(List.of(
                            Component.text("Heat Source: Lava (Optimal Temperature - 100% Speed)", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                            Component.text("10% chance to consume lava source upon completing a melt.", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false),
                            Component.empty(),
                            Component.text("Insert raw ores on the left and an empty bucket", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                            Component.text("to begin smelting molten metal liquid.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                    ));
                } else {
                    meta.displayName(MINI_MESSAGE.deserialize("<gold><b>🔥 Crucible Heated (Magma Block Detected)</b></gold>")
                            .decoration(TextDecoration.ITALIC, false));
                    meta.lore(List.of(
                            Component.text("Heat Source: Magma Block (Steady Heat - 70% Speed)", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                            Component.text("Permanent thermal stability (Will never consume block).", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                            Component.empty(),
                            Component.text("Insert raw ores on the left and an empty bucket", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                            Component.text("to begin smelting molten metal liquid.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                    ));
                }
                indicator.setItemMeta(meta);
            }
        }

        inventory.setItem(SLOT_STATUS_INDICATOR, indicator);
    }
}
