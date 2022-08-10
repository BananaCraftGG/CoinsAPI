package de.brownie.coinsapi;

import de.brownie.coinsapi.api.CoinsAPI;
import de.brownie.coinsapi.commands.AddCoinsCMD;
import de.brownie.coinsapi.commands.CoinsCMD;
import de.brownie.coinsapi.config.CoinsAPIConfig;
import de.brownie.coinsapi.database.Database;
import de.brownie.coinsapi.listeners.PlayerJoinListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public class CoinsAPIPlugin extends JavaPlugin {

    public static CoinsAPIPlugin INSTANCE;
    private Database coinsAPIDatabase = new Database();
    private CoinsAPI coinsAPI = new CoinsAPI();
    private CoinsAPIConfig coinsAPIConfig;
    private PluginManager pluginManager = Bukkit.getPluginManager();
    private String prefix = "&8[&eBananaCraft&8] &7";

    @Override
    public void onEnable() {
        INSTANCE = this;
        coinsAPIConfig = new CoinsAPIConfig();
        coinsAPIConfig.loadConfig();
        try {
            coinsAPIDatabase.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        registerCommands();
        registerListeners();
    }
    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }
    private void registerCommands() {
        this.getCommand("addcoins").setExecutor(new AddCoinsCMD());
        this.getCommand("coins").setExecutor(new CoinsCMD());
    }
}
