package io.github.kabanfriends.kabansmp.module.hardcore;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.hardcore.command.CommandHardcore;
import io.github.kabanfriends.kabansmp.module.hardcore.event.HardcoreDeathEventHandler;
import io.github.kabanfriends.kabansmp.module.hardcore.event.HardcorePacketListener;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class HardcoreModule implements Module {

    public static Set<Player> PLAYERS_TO_RESPAWN = new HashSet<>();

    @Override
    public void load() {
        HardcorePacketListener.init();
        registerCommand("hardcore", new CommandHardcore());
        registerEvents(new HardcoreDeathEventHandler());
    }
}
