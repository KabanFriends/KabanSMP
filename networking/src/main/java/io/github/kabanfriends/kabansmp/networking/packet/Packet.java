package io.github.kabanfriends.kabansmp.networking.packet;

public abstract class Packet {

    public Packet() {}

    public Packet(PacketBuffer buffer) {
        throw new UnsupportedOperationException("Packet deserialization is not implemented for this packet!");
    }

    public abstract void write(PacketBuffer buffer);

    public abstract void handle(PacketListener listener);
}
