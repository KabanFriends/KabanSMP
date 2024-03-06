package io.github.kabanfriends.kabansmp.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import io.github.kabanfriends.kabansmp.velocity.player.ServerSelector;

public class ServerCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            return;
        }

        ServerSelector.openGui(player);
    }
}
