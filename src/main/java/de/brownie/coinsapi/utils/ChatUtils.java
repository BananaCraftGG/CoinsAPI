package de.brownie.coinsapi.utils;

import de.brownie.coinsapi.CoinsAPIPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', CoinsAPIPlugin.INSTANCE.getPrefix() + message));
    }
}
