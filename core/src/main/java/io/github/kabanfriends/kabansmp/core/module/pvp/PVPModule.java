package io.github.kabanfriends.kabansmp.core.module.pvp;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.pvp.command.CommandPVP;
import io.github.kabanfriends.kabansmp.core.module.pvp.event.PVPEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PVPModule extends Module {

    public static final Set<Player> PVP_PLAYERS = new HashSet<>();

    @Override
    public void onLoad() {
        registerCommand("pvp", new CommandPVP());
        registerEvents(new PVPEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
