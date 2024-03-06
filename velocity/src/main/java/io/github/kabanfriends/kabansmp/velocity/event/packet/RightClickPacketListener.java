package io.github.kabanfriends.kabansmp.velocity.event.packet;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import dev.simplix.protocolize.data.packets.UseItem;
import io.github.kabanfriends.kabansmp.velocity.player.PlayerUtil;

public class RightClickPacketListener extends AbstractPacketListener<UseItem> {

    public RightClickPacketListener() {
        super(UseItem.class, Direction.UPSTREAM, 0);
    }

    @Override
    public void packetReceive(PacketReceiveEvent<UseItem> event) {
        if (PlayerUtil.isPlayerInLobby(event.player().uniqueId())) {
            PlayerUtil.optionallyOpenGui(event.player().uniqueId());
        }
    }

    @Override
    public void packetSend(PacketSendEvent<UseItem> packetSendEvent) {
    }
}
