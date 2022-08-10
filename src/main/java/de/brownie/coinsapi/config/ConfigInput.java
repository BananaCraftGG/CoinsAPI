package de.brownie.coinsapi.config;

import de.brownie.coinsapi.CoinsAPIPlugin;
import lombok.Getter;

@Getter
public class ConfigInput {

    private String path;
    private Object value;

    public ConfigInput(String path, Object value) {
        this.path = path;
        this.value = value;
        CoinsAPIPlugin.INSTANCE.getCoinsAPIConfig().getSortedList().add(this);
    }
}

