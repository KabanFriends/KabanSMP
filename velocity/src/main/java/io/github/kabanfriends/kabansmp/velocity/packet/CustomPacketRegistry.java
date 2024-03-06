package io.github.kabanfriends.kabansmp.velocity.packet;

import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.Protocol;
import dev.simplix.protocolize.api.providers.ProtocolRegistrationProvider;
import io.github.kabanfriends.kabansmp.velocity.packet.impl.SwingArm;

public class CustomPacketRegistry {

    public static void registerPackets(ProtocolRegistrationProvider provider) {
        provider.registerPacket(SwingArm.MAPPINGS, Protocol.PLAY, PacketDirection.SERVERBOUND, SwingArm.class);
    }
}
