package io.github.kabanfriends.kabansmp.core.player.data;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private static final Map<UUID, PlayerData> DATA_MAP = new ConcurrentHashMap<>();

    public static PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public static PlayerData getPlayerData(UUID uuid) {
        if (!DATA_MAP.containsKey(uuid)) {
            PlayerData data = new PlayerData(uuid);
            DATA_MAP.put(uuid, data);
            return data;
        }
        return DATA_MAP.get(uuid);
    }

    public static void unloadPlayerData(UUID uuid) {
        DATA_MAP.remove(uuid);
    }
}
