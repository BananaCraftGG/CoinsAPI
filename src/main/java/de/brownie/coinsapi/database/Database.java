package de.brownie.coinsapi.database;

import de.brownie.coinsapi.CoinsAPIPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

@Getter
public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if (connection != null) {
            return connection;
        }
        String host = CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getString("mysql.host");
        String port = CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getString("mysql.port");
        String db = CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getString("mysql.db");
        String user = CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getString("mysql.user");
        String password = CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getString("mysql.password");

        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, db);

        Connection connection = DriverManager.getConnection(url, user, password);

        this.connection = connection;

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', CoinsAPIPlugin.INSTANCE.getPrefix() +  "Connected to Database"));

        return connection;
    }

    public void initializeDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        //Create the coins table
        String sql= "CREATE TABLE IF NOT EXISTS coins(playerName varchar(255) primary key, coins int)";
        statement.execute(sql);
        statement.close();
    }
    public boolean isConnected() {
        return connection != null;
    }
}