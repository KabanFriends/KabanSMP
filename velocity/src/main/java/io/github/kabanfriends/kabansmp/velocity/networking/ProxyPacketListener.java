package io.github.kabanfriends.kabansmp.velocity.networking;

import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;
import io.github.kabanfriends.kabansmp.networking.packet.impl.ServerStatusPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.TestPacket;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;

public class ProxyPacketListener implements PacketListener {

    @Override
    public void handleTestPacket(TestPacket packet) {
        KabanSMPVelocity.getInstance().getLogger().info("Received TestPacket! Data = " + packet.getValue());
    }

    @Override
    public void handleServerStatusPacket(ServerStatusPacket packet) {
        ServerStatusManager.updateServerStatus(packet.getServer(), new ServerStatus(System.currentTimeMillis(), packet.getMaxPlayers()));
    }
}
