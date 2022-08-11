package de.brownie.coinsapi.vault;

import de.brownie.coinsapi.CoinsAPIPlugin;
import de.brownie.coinsapi.api.DatabaseAPI;
import de.brownie.coinsapi.utils.ChatUtils;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class VaultHook extends AbstractEconomy {
    private final Logger log;
    private final String name = "CoinsAPI";
    private Plugin plugin = null;
    private DatabaseAPI databaseAPI = null;

    public VaultHook(Plugin plugin) {
        Plugin economy;
        this.plugin = plugin;
        this.log = plugin.getLogger();
        if (this.databaseAPI == null && (economy = plugin.getServer().getPluginManager().getPlugin("CoinsAPI")) != null && economy.isEnabled()) {
            this.databaseAPI = CoinsAPIPlugin.INSTANCE.getDatabaseAPI();
            this.log.info(String.format("[Economy] %s hooked.", "CoinsAPI"));
        }
    }


    @Override
    public boolean isEnabled() {
        return databaseAPI != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        try {
            return databaseAPI.getCoins(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getBalance(String playerName, String world) {
        try {
            return databaseAPI.getCoins(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean has(String playerName, double amount) {
        try {
            if(databaseAPI.getCoins(playerName) > amount) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        try {
            if(databaseAPI.getCoins(playerName) > amount) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        try {
            if (!CoinsAPIPlugin.INSTANCE.getCoinsAPI().doesPlayerExist(playerName)) {
                return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player does not exist");
            }
            double balance = CoinsAPIPlugin.INSTANCE.getCoinsAPI().getCoins(playerName);
            if (balance < amount) {
                return new EconomyResponse(0.0, balance, EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
            }
            this.databaseAPI.removeCoins(playerName, (int) amount);
            int finalBalance = databaseAPI.getCoins(playerName);
            return new EconomyResponse(amount, finalBalance, EconomyResponse.ResponseType.SUCCESS, null);
        } catch(SQLException e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, e.getMessage());
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
            try {
                CoinsAPIPlugin.INSTANCE.getDatabaseAPI().addCoins(playerName, (int) amount);
                int finalBalance = databaseAPI.getCoins(playerName);
                return new EconomyResponse(amount, finalBalance, EconomyResponse.ResponseType.SUCCESS, null);
            } catch (SQLException e) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, e.getMessage());
            }
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        try {
            CoinsAPIPlugin.INSTANCE.getDatabaseAPI().addCoins(playerName, (int) amount);
            int finalBalance = databaseAPI.getCoins(playerName);
            return new EconomyResponse(amount, finalBalance, EconomyResponse.ResponseType.SUCCESS, null);
        } catch (SQLException e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, e.getMessage());
        }
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }
}
