package io.github.kabanfriends.kabansmp.core.module.hardcore;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.hardcore.event.HardcoreEventHandler;
import io.github.kabanfriends.kabansmp.core.module.hardcore.command.CommandHardcore;
import io.github.kabanfriends.kabansmp.core.module.hardcore.event.HardcorePacketListener;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.player.data.DataField;
import io.github.kabanfriends.kabansmp.core.codec.impl.ByteCodecs;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class HardcoreModule extends Module {

    public static final Set<Player> PLAYERS_TO_RESPAWN = new HashSet<>();

    public static final DataField<Boolean> HARDCORE_MODE_DATA = new DataField<>("hardcore_mode", ByteCodecs.BOOLEAN, false);
    public static final DataField<Integer> DEATH_COUNT_DATA = new DataField<>("death_count", ByteCodecs.INTEGER, 0);

    @Override
    public void onLoad() {
        PacketEvents.getAPI().getEventManager().registerListener(new HardcorePacketListener(), PacketListenerPriority.NORMAL);
        registerCommand("hardcore", new CommandHardcore());
        registerEvents(new HardcoreEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API,
                PlatformCapability.PACKET_EVENTS
        };
    }
}
