package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class ServerStatusPacket extends Packet {

    public static final int HEARTBEAT_INTERVAL_SECONDS = 2;

    private final String server;
    private final int maxPlayers;

    public ServerStatusPacket(String server, int maxPlayers) {
        this.server = server;
        this.maxPlayers = maxPlayers;
    }

    public ServerStatusPacket(PacketBuffer buffer) {
        this(buffer.readString(), buffer.readVarInt());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(server);
        buffer.writeVarInt(maxPlayers);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handleServerStatusPacket(this);
    }

    public String getServer() {
        return server;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
