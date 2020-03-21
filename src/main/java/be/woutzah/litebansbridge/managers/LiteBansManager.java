package be.woutzah.litebansbridge.managers;

import be.woutzah.litebansbridge.LiteBansBridge;
import be.woutzah.litebansbridge.enums.ModerationType;
import litebans.api.Database;

import java.util.UUID;

public class LiteBansManager {

    private LiteBansBridge plugin;

    public LiteBansManager(LiteBansBridge plugin) {
        this.plugin = plugin;
    }

    public boolean getIsPlayerBanned(UUID uuid){
        return Database.get().isPlayerBanned(uuid, null);
    }

    public boolean getIsPlayerMuted(UUID uuid){
        return Database.get().isPlayerMuted(uuid, null);
    }


}
