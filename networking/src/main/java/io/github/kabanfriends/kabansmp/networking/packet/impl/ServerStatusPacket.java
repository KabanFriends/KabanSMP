package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class ServerStatusPacket extends Packet {

    public static final int HEARTBEAT_INTERVAL_SECONDS = 2;

    private final String server;

    public ServerStatusPacket(String server) {
        this.server = server;
    }

    public ServerStatusPacket(PacketBuffer buffer) {
        this(buffer.readString());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(server);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handleServerStatusPacket(this);
    }

    public String getServer() {
        return server;
    }
}
