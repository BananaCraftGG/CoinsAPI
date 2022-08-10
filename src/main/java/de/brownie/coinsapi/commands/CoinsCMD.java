package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CoinsCMD
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if(args.length == 0) {
                try {
                    ChatUtils.sendMessage(p, "&7Your Coins: &e" + CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(p.getName()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(args.length == 1) {
                try {
                    String playerName = args[0];
                    if(CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName)) {
                        ChatUtils.sendMessage(p, String.format("&b%s &7has &e%s &7%s", playerName, CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(playerName), "Coins"));
                    } else {
                        ChatUtils.sendMessage(p, String.format("&b%s &7was not found!", playerName));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                ChatUtils.sendMessage(p, "&c/coins");
                ChatUtils.sendMessage(p, "&c/coins <player>");
            }
        }
        return false;
    }

}
