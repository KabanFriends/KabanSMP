package io.github.kabanfriends.kabansmp.networking.packet.impl;

import io.github.kabanfriends.kabansmp.networking.packet.Packet;
import io.github.kabanfriends.kabansmp.networking.packet.PacketBuffer;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;

public class TestPacket extends Packet {

    private final int value;

    public TestPacket(int value) {
        this.value = value;
    }

    public TestPacket(PacketBuffer buffer) {
        this(buffer.readInt());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(value);
    }

    @Override
    public void handle(PacketListener listener) {
        listener.handleTestPacket(this);
    }

    public int getValue() {
        return value;
    }
}
