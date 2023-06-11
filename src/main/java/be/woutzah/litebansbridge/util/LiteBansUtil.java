package be.woutzah.litebansbridge.util;

import litebans.api.Database;

import java.util.UUID;

public class LiteBansUtil {

    private LiteBansUtil() {}

    public static boolean getIsPlayerBanned(UUID uuid){
        return Database.get().isPlayerBanned(uuid, null);
    }

    public static boolean getIsPlayerMuted(UUID uuid){
        return Database.get().isPlayerMuted(uuid, null);
    }
}
