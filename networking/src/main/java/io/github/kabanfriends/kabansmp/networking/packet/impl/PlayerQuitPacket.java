package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class PlayerQuitPacket extends Packet {

    private final String playerName;

    public PlayerQuitPacket(String playerName) {
        this.playerName = playerName;
    }

    public PlayerQuitPacket(PacketBuffer buffer) {
        this(buffer.readString());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(playerName);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handlePlayerQuitPacket(this);
    }

    public String getPlayerName() {
        return playerName;
    }
}
