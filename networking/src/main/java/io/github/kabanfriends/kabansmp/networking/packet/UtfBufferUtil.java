package io.github.kabanfriends.kabansmp.networking.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.StandardCharsets;

public class UtfBufferUtil {

    public static String read(PacketBuffer buffer, int length) {
        int maxBytes = ByteBufUtil.utf8MaxBytes(length);
        int byteLength = buffer.readVarInt();
        if (byteLength > maxBytes) {
            throw new IllegalStateException("String length is longer than max allowed (" + byteLength + " > " + maxBytes + ")");
        } else if (byteLength < 0) {
            throw new IllegalStateException("String length is less than zero");
        } else {
            int readable = buffer.source.readableBytes();
            if (byteLength > readable) {
                throw new IllegalStateException("Not enough bytes in buffer; expected " + byteLength + ", but got " + readable);
            } else {
                String string = buffer.source.toString(buffer.source.readerIndex(), byteLength, StandardCharsets.UTF_8);
                buffer.source.readerIndex(buffer.source.readerIndex() + byteLength);
                if (string.length() > length) {
                    throw new IllegalStateException("String length is longer than max allowed (" + string.length() + " > " + length + ")");
                } else {
                    return string;
                }
            }
        }
    }

    public static void write(PacketBuffer buffer, CharSequence string, int length) {
        if (string.length() > length) {
            throw new IllegalStateException("String is too long (max " + length + ", but got " + string.length() + ")");
        } else {
            int maxBytes = ByteBufUtil.utf8MaxBytes(string);
            ByteBuf byteBuf = buffer.source.alloc().buffer(maxBytes);

            try {
                int encoded = ByteBufUtil.writeUtf8(byteBuf, string);
                int max = ByteBufUtil.utf8MaxBytes(length);
                if (encoded > max) {
                    throw new IllegalStateException("String is too long (was " + encoded + " bytes encoded, max " + max + ")");
                }

                buffer.writeVarInt(encoded);
                buffer.source.writeBytes(byteBuf);
            } finally {
                byteBuf.release();
            }

        }
    }
}
