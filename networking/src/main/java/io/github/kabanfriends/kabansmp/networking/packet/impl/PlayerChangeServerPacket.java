package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class PlayerChangeServerPacket extends Packet {

    private final String playerName;
    private final String targetServer;

    public PlayerChangeServerPacket(String playerName, String targetServer) {
        this.playerName = playerName;
        this.targetServer = targetServer;
    }

    public PlayerChangeServerPacket(PacketBuffer buffer) {
        this(buffer.readString(), buffer.readString());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(playerName);
        buffer.writeString(targetServer);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handlePlayerChangeServerPacket(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTargetServer() {
        return targetServer;
    }
}
