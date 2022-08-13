package de.brownie.coinsapi.api;

import de.brownie.coinsapi.CoinsAPIPlugin;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsAPI {
    @SneakyThrows
    public void addCoins(String playerName, int amount) {
        if (CoinsAPIPlugin.INSTANCE.isVaultEnabled() && Bukkit.getPlayer(playerName) != null) {
            Player target = Bukkit.getPlayer(playerName);
            CoinsAPIPlugin.INSTANCE.getVaultAPI().depositPlayer(target, amount);
        } else {
            CoinsAPIPlugin.INSTANCE.getDatabaseAPI().removeCoins(playerName, amount);
        }
    }

    @SneakyThrows
    public void removeCoins(String playerName, int amount) {
        if (CoinsAPIPlugin.INSTANCE.isVaultEnabled() && Bukkit.getPlayer(playerName) != null) {
            Player target = Bukkit.getPlayer(playerName);
            CoinsAPIPlugin.INSTANCE.getVaultAPI().withdrawPlayer(target, amount);
        } else {
            CoinsAPIPlugin.INSTANCE.getDatabaseAPI().removeCoins(playerName, amount);
        }
    }

    @SneakyThrows
    public void resetCoins(String playerName) {
        if (CoinsAPIPlugin.INSTANCE.isVaultEnabled() && Bukkit.getPlayer(playerName) != null) {
            Player target = Bukkit.getPlayer(playerName);
            CoinsAPIPlugin.INSTANCE.getVaultAPI().withdrawPlayer(target, CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(playerName));
        } else {
            CoinsAPIPlugin.INSTANCE.getDatabaseAPI().resetCoins(playerName);
        }
    }

    @SneakyThrows
    public int getCoins(String playerName) {
        return CoinsAPIPlugin.INSTANCE.getDatabaseAPI().getCoins(playerName);
    }

    public boolean doesPlayerExist(String playerName) {
        if (!CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().isConnected()) {
            return true;
        }
        try {
            PreparedStatement preparedStatement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection().prepareStatement("SELECT * FROM coins WHERE playerName = ?");
            preparedStatement.setString(1, playerName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getString("coins") != null) {
                    return true;
                }
                return false;
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}