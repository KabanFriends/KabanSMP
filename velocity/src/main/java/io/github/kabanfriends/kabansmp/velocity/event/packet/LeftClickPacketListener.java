package io.github.kabanfriends.kabansmp.velocity.event.packet;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import io.github.kabanfriends.kabansmp.velocity.packet.impl.SwingArm;
import io.github.kabanfriends.kabansmp.velocity.player.PlayerUtil;

public class LeftClickPacketListener extends AbstractPacketListener<SwingArm> {

    public LeftClickPacketListener() {
        super(SwingArm.class, Direction.UPSTREAM, 0);
    }

    @Override
    public void packetReceive(PacketReceiveEvent<SwingArm> event) {
        if (PlayerUtil.isPlayerInLobby(event.player().uniqueId())) {
            PlayerUtil.optionallyOpenGui(event.player().uniqueId());
        }
    }

    @Override
    public void packetSend(PacketSendEvent<SwingArm> packetSendEvent) {
    }
}
