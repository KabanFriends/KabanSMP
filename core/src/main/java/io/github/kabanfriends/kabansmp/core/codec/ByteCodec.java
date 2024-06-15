package io.github.kabanfriends.kabansmp.core.codec;

import io.netty.buffer.ByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ByteCodec<T> extends Codec<ByteBuf, T> {

    private static final byte NULL_MARKER = 0x00;
    private static final byte NOT_NULL_MARKER = 0x01;

    public ByteCodec(BiConsumer<ByteBuf, T> serializer, Function<ByteBuf, T> deserializer) {
        super(serializer, deserializer);
    }

    @Override
    public T deserialize(ByteBuf buffer) {
        if (buffer.readByte() == NULL_MARKER) {
            return null;
        }
        return super.deserialize(buffer);
    }

    @Override
    public void serialize(ByteBuf buffer, T value) {
        if (value == null) {
            buffer.writeByte(NULL_MARKER);
            return;
        }
        buffer.writeByte(NOT_NULL_MARKER);
        super.serialize(buffer, value);
    }
}
