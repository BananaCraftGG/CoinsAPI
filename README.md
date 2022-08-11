# CoinsAPI
BananaCrafts Economy plugin

# API Usage Sample
```java
@Getter
public class Main extends JavaPlugin {
    private CoinsAPI coinsAPI;
    
    @Override
    public void onEnable() {
        coinsAPI = CoinsAPIPlugin.INSTANCE.getCoinsAPI();
    }
    
    //Gives player 500 coins on first join.
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        if(!coinsAPI.doesPlayerExist(p.getName())) {
            coinsAPI.addCoins(p.getName(), 500);
        }
    }
}
```
