package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class ResetCoinsCMD
        implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission("lobby.admin")) {
                if (args.length == 1) {
                    try {
                        CoinsAPIPlugin.INSTANCE.getCoinsAPI().resetCoins(args[0]);
                        ChatUtils.sendMessage(p, "&aSuccessfully reset &b" + args[0] + "'s &acoins.");
                    } catch (SQLException e) {
                        ChatUtils.sendMessage(p, e.getMessage());
                        throw new RuntimeException(e);
                    }
                } else {
                    ChatUtils.sendMessage(p, "&c/resetcoins <player>");
                }
            } else {
                ChatUtils.sendMessage(p, ChatColor.RED + "You don't have permissions!");
            }

        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                String name = args[0];
                if (Objects.nonNull(name)) {
                    try {
                        CoinsAPIPlugin.INSTANCE.getCoinsAPI().resetCoins(name);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ChatUtils.sendConsoleMessage("You have reset &b" + name + "'s &7Coins");
                }
            } else {
                ChatUtils.sendConsoleMessage("&c/resetcoins <player>");
            }
        }
        return false;
    }
}