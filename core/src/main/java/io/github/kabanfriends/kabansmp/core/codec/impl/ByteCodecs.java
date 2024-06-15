package io.github.kabanfriends.kabansmp.core.codec.impl;

import io.github.kabanfriends.kabansmp.core.codec.ByteCodec;
import io.github.kabanfriends.kabansmp.core.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ByteCodecs {

    public static final ByteCodec<String> STRING = new ByteCodec<>(ByteBufUtil::writeString, ByteBufUtil::readString);
    public static final ByteCodec<Integer> INTEGER = new ByteCodec<>(ByteBuf::writeInt, ByteBuf::readInt);
    public static final ByteCodec<Boolean> BOOLEAN = new ByteCodec<>(ByteBuf::writeBoolean, ByteBuf::readBoolean);
    public static final ByteCodec<Location> LOCATION = new ByteCodec<>(
            (buffer, value) -> {
                ByteBufUtil.writeString(buffer, value.getWorld().getName());
                buffer.writeDouble(value.getX());
                buffer.writeDouble(value.getY());
                buffer.writeDouble(value.getZ());
                buffer.writeFloat(value.getYaw());
                buffer.writeFloat(value.getPitch());
            },
            (buffer) -> new Location(
                    Bukkit.getWorld(ByteBufUtil.readString(buffer)),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readFloat(),
                    buffer.readFloat()
            )
    );
}
