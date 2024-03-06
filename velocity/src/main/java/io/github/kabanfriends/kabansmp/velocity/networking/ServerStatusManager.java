package io.github.kabanfriends.kabansmp.velocity.networking;

import io.github.kabanfriends.kabansmp.networking.packet.impl.ServerStatusPacket;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ServerStatusManager {

    private static final Map<String, ServerStatus> SERVER_STATUS_MAP = new HashMap<>();

    public static void updateServerStatus(String name, ServerStatus status) {
        SERVER_STATUS_MAP.put(name, status);
    }

    public static @Nullable ServerStatus getServerStatus(String name) {
        if (!SERVER_STATUS_MAP.containsKey(name)) {
            return null;
        }

        ServerStatus status = SERVER_STATUS_MAP.get(name);
        if (System.currentTimeMillis() - status.updateTime() > ServerStatusPacket.HEARTBEAT_INTERVAL_SECONDS * 2 * 1000L) {
            return null;
        }
        return status;
    }
}
