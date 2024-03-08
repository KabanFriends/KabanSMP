package io.github.kabanfriends.kabansmp.velocity.networking;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github.kabanfriends.kabansmp.networking.packet.RedisPacketHandler;
import io.github.kabanfriends.kabansmp.networking.packet.impl.ProxyStatusPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.ServerStatusPacket;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;
import io.github.kabanfriends.kabansmp.velocity.config.SharedConfig;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StatusManager {

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

    public static void sendProxyStatus() {
        ProxyServer proxy = KabanSMPVelocity.getInstance().getServer();
        int count = 0;
        for (RegisteredServer server : proxy.getAllServers()) {
            if (!server.getServerInfo().getName().equals("lobby")) {
                count += server.getPlayersConnected().size();
            }
        }
        KabanSMPVelocity.getInstance().getLogger().info("Proxy status: " + count + "/" + SharedConfig.maxPlayers + " players");
        RedisPacketHandler.sendToAllServers(new ProxyStatusPacket(count, SharedConfig.maxPlayers));
    }
}
