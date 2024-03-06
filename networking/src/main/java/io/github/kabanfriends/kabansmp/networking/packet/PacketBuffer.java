package io.github.kabanfriends.kabansmp.networking.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketBuffer {

    protected final ByteBuf source;

    public PacketBuffer() {
        this.source = Unpooled.buffer();
    }

    public PacketBuffer(ByteBuf source) {
        this.source = source;
    }

    public PacketBuffer(byte[] data) {
        this(Unpooled.copiedBuffer(data));
    }

    public byte[] toBytes() {
        if (source.hasArray()) {
            return source.array();
        }

        byte[] bytes = new byte[source.readableBytes()];
        source.getBytes(source.readerIndex(), bytes);

        return bytes;
    }

    public void writeVarInt(int input) {
        while ((input & -128) != 0) {
            source.writeByte((byte) (input & 127 | 128));
            input >>>= 7;
        }

        source.writeByte((byte) input);
    }

    public int readVarInt() {
        int intValue = 0;
        int byteLength = 0;

        while (true) {
            byte byteValue = source.readByte();

            intValue |= (byteValue & 127) << byteLength++ * 7;
            if (byteLength > 5) {
                throw new RuntimeException("Tried to read a VarInt that is too large: " + byteLength);
            }

            if ((byteValue & 128) != 128) {
                break;
            }
        }

        return intValue;
    }

    public void writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            source.writeByte((byte) ((int) (value & 127L) | 128));
            value >>>= 7;
        }

        source.writeByte((byte) value);
    }

    public long readVarLong() {
        long longValue = 0L;
        int byteLength = 0;

        while (true) {
            byte byteValue = source.readByte();
            longValue |= (long) (byteValue & 127) << byteLength++ * 7;
            if (byteLength > 10) {
                throw new RuntimeException("Tried to read a VarLong that is too large: " + byteLength);
            }

            if ((byteValue & 128) != 128) {
                break;
            }
        }

        return longValue;
    }

    public void writeInt(int value) {
        source.writeInt(value);
    }

    public int readInt() {
        return source.readInt();
    }

    public void writeLong(long value) {
        source.writeLong(value);
    }

    public long readLong() {
        return source.readLong();
    }

    public void writeShort(short value) {
        source.writeShort(value);
    }

    public short readShort() {
        return source.readShort();
    }

    public void writeDouble(double value) {
        source.writeDouble(value);
    }

    public double readDouble() {
        return source.readDouble();
    }

    public void writeBoolean(boolean value) {
        source.writeBoolean(value);
    }

    public boolean readBoolean() {
        return source.readBoolean();
    }

    public void writeChar(char value) {
        source.writeChar(value);
    }

    public char readChar() {
        return source.readChar();
    }

    public void writeString(String string) {
        UtfBufferUtil.write(this, string, 32767);
    }

    public String readString() {
        return UtfBufferUtil.read(this, 32767);
    }

    public void writeUUID(UUID uuid) {
        this.writeVarLong(uuid.getMostSignificantBits());
        this.writeVarLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() {
        return new UUID(this.readVarLong(), this.readVarLong());
    }
}
