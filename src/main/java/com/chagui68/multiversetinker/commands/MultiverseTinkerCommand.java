package com.chagui68.multiversetinker.commands;

import com.chagui68.multiversetinker.MultiverseTinker;
import com.chagui68.multiversetinker.api.MineralOrigin;
import com.chagui68.multiversetinker.items.TinkerItemRegistry;
import com.chagui68.multiversetinker.materials.MaterialRegistry;
import com.chagui68.multiversetinker.materials.TinkerMaterial;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class MultiverseTinkerCommand implements CommandExecutor, TabCompleter {

    private final MultiverseTinker plugin;
    private final MaterialRegistry materialRegistry;
    private final TinkerItemRegistry itemRegistry;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public MultiverseTinkerCommand(@Nonnull MultiverseTinker plugin,
                                  @Nonnull MaterialRegistry materialRegistry,
                                  @Nonnull TinkerItemRegistry itemRegistry) {
        this.plugin = plugin;
        this.materialRegistry = materialRegistry;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!sender.hasPermission("multiversetinker.admin")) {
            sender.sendMessage(miniMessage.deserialize("<red>You do not have permission to execute this command.</red>"));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender, label);
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);

        switch (sub) {
            case "reload" -> {
                plugin.reloadConfig();
                plugin.getArchaeologyManager().getLootTable().reload();
                itemRegistry.reload();
                sender.sendMessage(miniMessage.deserialize("<green>MultiverseTinker configuration, items and loot tables reloaded successfully!</green>"));
                return true;
            }

            case "list" -> {
                MineralOrigin filter = null;
                if (args.length >= 2) {
                    try {
                        filter = MineralOrigin.valueOf(args[1].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker Materials (Chagui68) ===</gold>"));
                Collection<TinkerMaterial> list = filter != null
                        ? materialRegistry.getByOrigin(filter)
                        : materialRegistry.getAll();

                for (TinkerMaterial mat : list) {
                    String line = "<gray>• </gray><gradient:" + mat.getColorHex() + ":#ffffff>" + mat.getName() + "</gradient> "
                            + "<dark_gray>(" + mat.getId() + ")</dark_gray> "
                            + "<yellow>[" + mat.getOrigin().name() + "]</yellow> "
                            + "<aqua>Trait: " + mat.getTraitName() + "</aqua>";
                    sender.sendMessage(miniMessage.deserialize(line));
                }
                sender.sendMessage(miniMessage.deserialize("<gray>Total: " + list.size() + " registered materials.</gray>"));
                return true;
            }

            case "give" -> {
                if (args.length < 3) {
                    sender.sendMessage(miniMessage.deserialize("<red>Usage: /" + label + " give <player> <mvtink_id> [amount]</red>"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(miniMessage.deserialize("<red>Player not found: " + args[1] + "</red>"));
                    return true;
                }

                String itemId = args[2].toLowerCase(Locale.ROOT);
                ItemStack item = itemRegistry.getItemById(itemId);

                if (item == null) {
                    sender.sendMessage(miniMessage.deserialize("<red>Item not found: " + args[2] + "</red>"));
                    return true;
                }

                int amount = 1;
                if (args.length >= 4) {
                    try {
                        amount = Math.max(1, Integer.parseInt(args[3]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(miniMessage.deserialize("<red>Invalid amount: " + args[3] + "</red>"));
                        return true;
                    }
                }

                item.setAmount(amount);
                target.getInventory().addItem(item);
                sender.sendMessage(miniMessage.deserialize("<green>Gave " + amount + "x " + itemId + " to " + target.getName() + ".</green>"));
                return true;
            }

            default -> {
                sendHelp(sender, label);
                return true;
            }
        }
    }

    private void sendHelp(CommandSender sender, String label) {
        sender.sendMessage(miniMessage.deserialize("<gold>=== MultiverseTinker v" + plugin.getDescription().getVersion() + " (Chagui68) ===</gold>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " give <player> <mvtink_id> [amount]</yellow> <gray>- Give items, tools, raw ores, ingots, casts, or the prospector brush.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " list [OVERWORLD|NETHER|THE_END]</yellow> <gray>- List all 45 geological materials.</gray>"));
        sender.sendMessage(miniMessage.deserialize("<yellow>/" + label + " reload</yellow> <gray>- Reload configuration and caches.</gray>"));
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        if (!sender.hasPermission("multiversetinker.admin")) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return filter(List.of("give", "list", "reload"), args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return null; // Player names
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            return filter(List.of("OVERWORLD", "NETHER", "THE_END"), args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return filter(new ArrayList<>(itemRegistry.getAllItemIds()), args[2]);
        }

        return Collections.emptyList();
    }

    private List<String> filter(List<String> list, String input) {
        String lower = input.toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s.toLowerCase(Locale.ROOT).startsWith(lower)) {
                result.add(s);
            }
        }
        return result;
    }
}
