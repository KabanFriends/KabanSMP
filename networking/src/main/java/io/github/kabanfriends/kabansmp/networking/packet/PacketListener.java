package io.github.kabanfriends.kabansmp.networking.packet;

import io.github.kabanfriends.kabansmp.networking.packet.impl.*;

public interface PacketListener {

    default void handleTestPacket(TestPacket packet) {
    }

    default void handlePlayerJoinPacket(PlayerJoinPacket packet) {
    }

    default void handlePlayerQuitPacket(PlayerQuitPacket packet) {
    }

    default void handlePlayerChangeServerPacket(PlayerChangeServerPacket packet) {
    }

    default void handleServerStatusPacket(ServerStatusPacket packet) {
    }
}
