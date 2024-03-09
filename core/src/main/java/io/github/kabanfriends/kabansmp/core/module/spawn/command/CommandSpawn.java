package io.github.kabanfriends.kabansmp.core.module.spawn.command;

import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.player.Teleports;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        Location loc = player.getWorld().getSpawnLocation().clone();
        loc.add(0.5, 0, 0.5);
        Teleports.teleport(player, loc, true, () -> player.sendMessage(Components.formatted(Format.HOME_SUCCESS, "spawn.command.spawn.success")));
        return true;
    }
}
