package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class ProxyStatusPacket extends Packet {

    private final int playerCount;
    private final int maxPlayerCount;

    public ProxyStatusPacket(int playerCount, int maxPlayerCount) {
        this.playerCount = playerCount;
        this.maxPlayerCount = maxPlayerCount;
    }

    public ProxyStatusPacket(PacketBuffer buffer) {
        this(buffer.readVarInt(), buffer.readVarInt());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeVarInt(playerCount);
        buffer.writeVarInt(maxPlayerCount);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handleProxyStatusPacket(this);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }
}
