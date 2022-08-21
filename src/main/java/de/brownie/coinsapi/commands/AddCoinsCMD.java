package de.brownie.coinsapi.commands;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import lombok.SneakyThrows;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AddCoinsCMD implements CommandExecutor {
    @SneakyThrows
    public boolean onCommand(final CommandSender commandSender, final Command cmd, final String label, final String[] args) {
        if (commandSender instanceof Player) {
            final Player p = (Player) commandSender;
            if (p.hasPermission("coins.admin")) {
                switch (args.length) {
                    case 1:
                        try {
                            String amount = args[0];
                            if (Objects.nonNull(amount) && CoinsAPIPlugin.INSTANCE.isVaultEnabled()) {
                                EconomyResponse r = CoinsAPIPlugin.INSTANCE.getVaultAPI().depositPlayer(p, Integer.parseInt(amount));
                                if (!r.transactionSuccess()) {
                                    ChatUtils.sendMessage(p, String.format("&cError", amount));
                                }
                                ChatUtils.sendMessage(p, "using hook");
                            } else {
                                CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(p.getName(), Integer.parseInt(amount));
                                ChatUtils.sendMessage(p, "using legacy");
                            }
                            ChatUtils.sendMessage(p, String.format("&7You gave &byourself &e%s Coins &7!", amount));
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(p, "&cAmount was not a valid number!");
                        }
                        break;
                    case 2:
                        String name = args[0];
                        String amount = args[1];
                        try {
                            if (Objects.nonNull(amount) && Objects.nonNull(name)) {
                                CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(name, Integer.parseInt(amount));
                                ChatUtils.sendMessage(p, String.format("&7You removed &e%s Coins &7from &b%s&7!", amount, name));
                            }
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(p, "&cAmount was not a valid number!");
                        }
                        break;
                    default:
                        ChatUtils.sendMessage(p, "&c/addcoins <amount>");
                        ChatUtils.sendMessage(p, "&c/addcoins <player> <amount>");
                        break;
                }
            } else {
                ChatUtils.sendMessage(p, "&cYou do not have permissions!");
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                String targetName = args[0];
                String amount = args[1];
                CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(targetName, Integer.parseInt(amount));
                ChatUtils.sendConsoleMessage("You gave &e" + amount + " &7Coins to &b" + targetName);
                Player target = Bukkit.getPlayer(targetName);
                if (Objects.nonNull(target)) {
                    ChatUtils.sendMessage(target, "You have received &e" + amount + " &7Coins!");
                }

            } else {
                ChatUtils.sendConsoleMessage("&c/addcoins <player> <amount>");
            }
        }
        return false;
    }
}