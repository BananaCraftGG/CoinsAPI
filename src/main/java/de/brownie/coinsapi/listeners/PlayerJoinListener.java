package de.brownie.coinsapi.listeners;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.utils.ChatUtils;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        if(!CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(p.getName())) {
            CoinsAPIPlugin.INSTANCE.getCoinsAPI().addCoins(p.getName(), CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getInt("settings.FirstJoinCoins"));
        }
    }
}
