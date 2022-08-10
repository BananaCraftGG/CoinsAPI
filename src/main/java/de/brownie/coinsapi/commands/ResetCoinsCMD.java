package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ResetCoinsCMD
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("lobby.admin")) {
                if(args.length == 1) {
                    try {
                        CoinsAPIPlugin.INSTANCE.getCoinsAPI().resetCoins(args[0]);
                        ChatUtils.sendMessage(p, "&aSuccessfully reset &b" + args[0] +"'s &acoins.");
                    } catch (SQLException e) {
                        ChatUtils.sendMessage(p, e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
                else {
                    ChatUtils.sendMessage(p, "&c/resetcoins <player>");
                }
            } else {
                ChatUtils.sendMessage(p, ChatColor.RED + "You don't have permissions!");
            }
        }
        return false;
    }

}
