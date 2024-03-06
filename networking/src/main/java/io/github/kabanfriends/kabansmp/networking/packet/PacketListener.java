package io.github.kabanfriends.kabansmp.networking.packet;

import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerChangeServerPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerJoinPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.PlayerQuitPacket;
import io.github.kabanfriends.kabansmp.networking.packet.impl.TestPacket;

public interface PacketListener {

    default void handleTestPacket(TestPacket packet) {

    }

    default void handlePlayerJoinPacket(PlayerJoinPacket packet) {

    }

    default void handlePlayerQuitPacket(PlayerQuitPacket packet) {

    }

    default void handlePlayerChangeServerPacket(PlayerChangeServerPacket packet) {

    }
}
