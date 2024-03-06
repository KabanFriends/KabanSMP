package io.github.kabanfriends.kabansmp.velocity.event;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import io.github.kabanfriends.kabansmp.networking.packet.RedisPacketHandler;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerChangeServerPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerJoinPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerQuitPacket;
import io.github.kabanfriends.kabansmp.velocity.player.ServerSelector;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.HashMap;
import java.util.Map;

public class PlayerServerEventHandler {

    private static final Map<Player, String> LAST_SERVERS = new HashMap<>();

    @Subscribe
    public void onProxyDisconnect(DisconnectEvent event) {
        if (LAST_SERVERS.containsKey(event.getPlayer())) {
            LAST_SERVERS.remove(event.getPlayer());
            RedisPacketHandler.sendToAllServers(new PlayerQuitPacket(event.getPlayer().getUsername()));
        }
    }

    @Subscribe
    public void onServerConnect(ServerConnectedEvent event) {
        String target = event.getServer().getServerInfo().getName();

        if (!target.equals("lobby")) {
            if (!LAST_SERVERS.containsKey(event.getPlayer())) {
                LAST_SERVERS.put(event.getPlayer(), target);

                RedisPacketHandler.sendToAllServers(new PlayerJoinPacket(
                        event.getPlayer().getUsername(),
                        target,
                        FloodgateApi.getInstance().isFloodgatePlayer(event.getPlayer().getUniqueId()
                )));
            } else {
                String last = LAST_SERVERS.get(event.getPlayer());
                if (!last.equals(target)) {
                    RedisPacketHandler.sendToAllServers(new PlayerChangeServerPacket(
                            event.getPlayer().getUsername(),
                            target
                    ));
                }

                LAST_SERVERS.put(event.getPlayer(), event.getServer().getServerInfo().getName());
            }
        }
    }

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        ServerSelector.openGui(event.getPlayer());
    }
}
