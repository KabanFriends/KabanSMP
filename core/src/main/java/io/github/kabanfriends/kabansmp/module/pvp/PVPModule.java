package io.github.kabanfriends.kabansmp.module.pvp;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.pvp.command.CommandPVP;
import io.github.kabanfriends.kabansmp.module.pvp.event.PVPEventHandler;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PVPModule implements Module {

    public static final Set<Player> PVP_PLAYERS = new HashSet<>();

    @Override
    public void load() {
        registerCommand("pvp", new CommandPVP());
        registerEvents(new PVPEventHandler());
    }
}
