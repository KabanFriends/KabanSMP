package io.github.kabanfriends.kabansmp.velocity.packet.impl;

import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;

import java.util.List;

import static dev.simplix.protocolize.api.util.ProtocolVersions.*;

public class SwingArm extends AbstractPacket {

    private int side;

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_20_3, MINECRAFT_LATEST, 0x33)
    );

    @Override
    public void read(ByteBuf buf, PacketDirection packetDirection, int version) {
        side = ProtocolUtil.readVarInt(buf);
    }

    @Override
    public void write(ByteBuf buf, PacketDirection packetDirection, int version) {
        ProtocolUtil.writeVarInt(buf, side);
    }
}
