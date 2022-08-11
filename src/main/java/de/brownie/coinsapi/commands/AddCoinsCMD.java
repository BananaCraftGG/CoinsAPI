package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class AddCoinsCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command cmd, final String label, final String[] args) {
        if (commandSender instanceof Player) {
            final Player p = (Player) commandSender;
            if (p.hasPermission("coins.admin")) {
                if (args.length > 0) {
                    if (args.length == 1) {
                        String amount = args[0];
                        try {
                            if (Objects.nonNull(amount)) {
                                try {
                                    CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(p.getName(), Integer.parseInt(amount));
                                    ChatUtils.sendMessage(p, String.format("&7You gave &byourself &e%s Cookies &7!", amount));
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(p, "&cAmount was not a valid number!");
                        }
                    } else if (args.length == 2) {
                        String name = args[0];
                        String amount = args[1];
                        try {
                            if (Objects.nonNull(amount) && Objects.nonNull(name)) {
                                try {
                                    CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(name, Integer.parseInt(amount));
                                    ChatUtils.sendMessage(p, String.format("&7You gave &e%s Cookies &7to &b%s&7!", amount, name));
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(p, "&cAmount was not a valid number!");
                        }
                    } else {
                        ChatUtils.sendMessage(p, "&c/addcoins <amount>");
                        ChatUtils.sendMessage(p, "&c/addcoins <player> <amount>");
                    }
                } else {
                    ChatUtils.sendMessage(p, "&c/addcoins <amount>");
                    ChatUtils.sendMessage(p, "&c/addcoins <player> <amount>");
                }
            } else {
                ChatUtils.sendMessage(p, "&cYou do not have permissions!");
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                String name = args[0];
                String amount = args[1];
                if (Objects.nonNull(amount) && Objects.nonNull(name)) {
                    try {
                        CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(name, Integer.parseInt(amount));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ChatUtils.sendConsoleMessage("You gave &e" + amount + " &7Coins to &b" + name);
                    Player target = Bukkit.getPlayer(name);
                    if(Objects.nonNull(target)) {
                        ChatUtils.sendMessage(target, "You have received &e" + amount + " &7Coins!");
                    }
                }
            }
            else {
                ChatUtils.sendConsoleMessage("&c/addcoins <player> <amount>");
            }
        }
        return false;
    }
}