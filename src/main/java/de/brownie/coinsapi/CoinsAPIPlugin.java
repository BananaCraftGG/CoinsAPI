package de.brownie.coinsapi;

import de.brownie.coinsapi.api.CoinsAPI;
import de.brownie.coinsapi.api.DatabaseAPI;
import de.brownie.coinsapi.commands.AddCoinsCMD;
import de.brownie.coinsapi.commands.CoinsCMD;
import de.brownie.coinsapi.commands.RemoveCoinsCMD;
import de.brownie.coinsapi.commands.ResetCoinsCMD;
import de.brownie.coinsapi.config.CoinsAPIConfig;
import de.brownie.coinsapi.listeners.PlayerJoinListener;
import de.brownie.coinsapi.utils.ChatUtils;
import de.brownie.coinsapi.vault.VaultHook;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public class CoinsAPIPlugin extends JavaPlugin {

    public static CoinsAPIPlugin INSTANCE;
    private de.brownie.coinsapi.database.Database coinsAPIDatabase = new de.brownie.coinsapi.database.Database();
    private DatabaseAPI databaseAPI = new DatabaseAPI();

    private CoinsAPI coinsAPI = new CoinsAPI();
    private CoinsAPIConfig coinsAPIConfig;
    private PluginManager pluginManager = Bukkit.getPluginManager();
    private String prefix = "&8[&eBananaCraft&8] &7";
    private boolean vaultEnabled = false;
    private Economy vaultAPI = null;

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
        if(!setupVault()) {
            ChatUtils.sendConsoleMessage("&cFailed to hook Vault");
        }
    }
    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        Bukkit.getServer().getServicesManager().register(Economy.class, new VaultHook(INSTANCE), this, ServicePriority.Highest);
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        vaultAPI = rsp.getProvider();
        vaultEnabled = true;
        return vaultAPI != null;
    }
    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }
    private void registerCommands() {
        this.getCommand("addcoins").setExecutor(new AddCoinsCMD());
        this.getCommand("resetcoins").setExecutor(new ResetCoinsCMD());
        this.getCommand("coins").setExecutor(new CoinsCMD());
        this.getCommand("removecoins").setExecutor(new RemoveCoinsCMD());
    }
}
