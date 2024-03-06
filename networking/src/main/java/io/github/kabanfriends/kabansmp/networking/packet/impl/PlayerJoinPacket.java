package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class PlayerJoinPacket extends Packet {

    private final String playerName;
    private final String targetServer;
    private final boolean isBedrock;


    public PlayerJoinPacket(String playerName, String targetServer, boolean isBedrock) {
        this.playerName = playerName;
        this.targetServer = targetServer;
        this.isBedrock = isBedrock;
    }

    public PlayerJoinPacket(PacketBuffer buffer) {
        this(buffer.readString(), buffer.readString(), buffer.readBoolean());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(playerName);
        buffer.writeString(targetServer);
        buffer.writeBoolean(isBedrock);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handlePlayerJoinPacket(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTargetServer() {
        return targetServer;
    }

    public boolean isBedrock() {
        return isBedrock;
    }
}
