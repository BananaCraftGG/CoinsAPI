package de.brownie.coinsapi.config;

import de.brownie.coinsapi.CoinsAPIPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class CoinsAPIConfig {

    private File file;
    private FileConfiguration configuration;
    private HashMap<String, Object> cache;
    private ArrayList<ConfigInput> sortedList;

    public CoinsAPIConfig() {
        file = new File(CoinsAPIPlugin.INSTANCE.getDataFolder(), "mysql.yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        cache = new HashMap<>();
        sortedList = new ArrayList<>();
    }

    public void loadConfig() {
        init();
        if (configuration.get("mysql.host") != null) {
            return;
        }
        configuration.options().copyDefaults(true);

        for (ConfigInput configInput : sortedList) {
            if (configuration.get(configInput.getPath()) == null) {
                configuration.set(configInput.getPath(), configInput.getValue());
            }
        }
        save();
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        new ConfigInput("mysql.host", "localhost");
        new ConfigInput("mysql.db", "coins");
        new ConfigInput("mysql.user", "root");
        new ConfigInput("mysql.password", "password");
        new ConfigInput("mysql.port", 3306);
        new ConfigInput("settings.FirstJoinCoins", 500);
    }

    public Integer getInt(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, configuration.getInt(string));
            }
            return (Integer) cache.get(string);
        } catch (Exception e) {
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (Integer) getConfigInput(string).getValue();
        }
    }

    public Boolean getBoolean(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, configuration.getBoolean(string));
            }
            return (Boolean) cache.get(string);
        } catch (Exception e) {
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (Boolean) getConfigInput(string).getValue();
        }
    }

    public String getString(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, configuration.getString(string));
            }
            return (String) cache.get(string);
        } catch (Exception e) {
            System.out.print(string);
            System.out.print(getConfigInput(string).getValue());
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (String) getConfigInput(string).getValue();
        }
    }

    private ConfigInput getConfigInput(String string) {
        for (ConfigInput configInput : sortedList) {
            if (configInput.getPath().equalsIgnoreCase(string)) {
                return configInput;
            }
        }
        return null;
    }
}
