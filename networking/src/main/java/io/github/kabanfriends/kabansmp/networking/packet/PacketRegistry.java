package io.github.kabanfriends.kabansmp.networking.packet;

import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerChangeServerPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerJoinPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerQuitPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.TestPacket;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PacketRegistry {

    private static final Object2IntMap<Class<? extends Packet>> CLASS_TO_ID = new Object2IntOpenHashMap<>();
    private static final List<Function<PacketBuffer, ? extends Packet>> ID_TO_DESERIALIZER = new ArrayList<>();

    static {
        CLASS_TO_ID.defaultReturnValue(-1);

        register(TestPacket.class, TestPacket::new);
        register(PlayerJoinPacket.class, PlayerJoinPacket::new);
        register(PlayerQuitPacket.class, PlayerQuitPacket::new);
        register(PlayerChangeServerPacket.class, PlayerChangeServerPacket::new);
    }

    private static <T extends Packet> void register(Class<T> clazz, Function<PacketBuffer, T> deserializer) {
        int id = ID_TO_DESERIALIZER.size();

        int prev = CLASS_TO_ID.putIfAbsent(clazz, id);
        if (prev != -1) {
            throw new IllegalArgumentException("Packet " + clazz + " is already registered as " + prev);
        }

        ID_TO_DESERIALIZER.add(deserializer);
    }

    protected static int getId(Class<?> clazz) {
        return CLASS_TO_ID.getInt(clazz);
    }

    protected static Packet createPacket(int id, PacketBuffer buffer) {
        if (id < 0 || id > ID_TO_DESERIALIZER.size()) return null;

        Function<PacketBuffer, ? extends Packet> deserializer = ID_TO_DESERIALIZER.get(id);
        return deserializer != null ? deserializer.apply(buffer) : null;
    }
}
