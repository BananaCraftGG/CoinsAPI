package de.brownie.coinsapi.api;

import de.brownie.coinsapi.CoinsAPIPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAPI {

    public void addCoins(String playerName) throws SQLException {
        PreparedStatement statement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection()
                .prepareStatement("REPLACE INTO coins(playerName, coins) VALUES (?, ?)");
        statement.setString(1, playerName);
        statement.setInt(2, getCoins(playerName) + 1);
        statement.executeUpdate();
        statement.close();
    }

    public void addCoins(String playerName, int amount) throws SQLException {
        PreparedStatement statement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection()
                .prepareStatement("REPLACE INTO coins(playerName, coins) VALUES (?, ?)");
        statement.setString(1, playerName);
        statement.setInt(2, getCoins(playerName) + amount);
        statement.executeUpdate();
        statement.close();
    }

    public void removeCoins(String playerName, int amount) throws SQLException {
        PreparedStatement statement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection()
                .prepareStatement("REPLACE INTO coins(playername, coins) VALUES (?, ?)");
        statement.setString(1, playerName);
        statement.setInt(2, getCoins(playerName) - amount);
        statement.executeUpdate();
        statement.close();
    }

    public void resetCoins(String playerName) throws SQLException {
        PreparedStatement statement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection()
                .prepareStatement("REPLACE INTO coins(playerName, coins) VALUES (?, ?)");
        statement.setString(1, playerName);
        statement.setInt(2, 0);
        statement.executeUpdate();
        statement.close();
    }

    public int getCoins(String playerName) throws SQLException {
        PreparedStatement statement = CoinsAPIPlugin.INSTANCE.getCoinsAPIDatabase().getConnection().prepareStatement("SELECT coins FROM coins WHERE playerName = ?");
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            return Integer.parseInt(resultSet.getString("coins"));
        }
        statement.close();
        return 0;
    }

}
