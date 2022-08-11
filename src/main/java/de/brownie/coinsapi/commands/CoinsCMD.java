package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CoinsCMD
        implements CommandExecutor {
    @SneakyThrows
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            switch (args.length) {
                case 0:
                    if (CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(p.getName())) {
                        ChatUtils.sendMessage(p, "&7Your Coins: &e" + CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(p.getName()));
                    } else {
                        ChatUtils.sendMessage(p, String.format("&b%s &cwas not found!", p.getName()));
                    }
                    break;
                case 1:
                    String playerName = args[0];
                    if (CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName)) {
                        ChatUtils.sendMessage(p, String.format("&b%s &7has &e%s &7%s", playerName, CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(playerName), "Coins"));
                    } else {
                        ChatUtils.sendMessage(p, String.format("&b%s &7does not exist!", playerName));
                    }
                    break;
                default:
                    ChatUtils.sendMessage(p, "&c/coins");
                    ChatUtils.sendMessage(p, "&c/coins <player>");
                    break;
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            switch (args.length) {
                case 1:
                    String playerName = args[0];
                    if (CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName)) {
                        int balance;
                        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
                        if (CoinsAPIPlugin.INSTANCE.isVaultEnabled()) {
                            balance = (int) CoinsAPIPlugin.INSTANCE.getVaultAPI().getBalance(target);
                        } else {
                            balance = CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(playerName);
                        }
                        ChatUtils.sendConsoleMessage(String.format("&b%s &7has &e%s &7%s", playerName, balance, "Coins"));
                    } else {
                        ChatUtils.sendConsoleMessage(String.format("&b%s &7does not exist!", playerName));
                    }
                    break;
                default:
                    ChatUtils.sendConsoleMessage("&c/coins <player> <amount>");
                    break;
            }
        }
        return false;
    }
}