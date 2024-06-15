package io.github.kabanfriends.kabansmp.core.module.hardcore;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.hardcore.event.HardcoreDeathEventHandler;
import io.github.kabanfriends.kabansmp.core.module.hardcore.command.CommandHardcore;
import io.github.kabanfriends.kabansmp.core.module.hardcore.event.HardcorePacketListener;
import io.github.kabanfriends.kabansmp.core.player.data.DataField;
import io.github.kabanfriends.kabansmp.core.codec.impl.ByteCodecs;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class HardcoreModule implements Module {

    public static final Set<Player> PLAYERS_TO_RESPAWN = new HashSet<>();

    public static final DataField<Boolean> HARDCORE_MODE_DATA = new DataField<>("hardcore_mode", ByteCodecs.BOOLEAN, false);
    public static final DataField<Integer> DEATH_COUNT_DATA = new DataField<>("death_count", ByteCodecs.INTEGER, 0);

    @Override
    public void load() {
        PacketEvents.getAPI().getEventManager().registerListener(new HardcorePacketListener(), PacketListenerPriority.NORMAL);
        registerCommand("hardcore", new CommandHardcore());
        registerEvents(new HardcoreDeathEventHandler());
    }
}
