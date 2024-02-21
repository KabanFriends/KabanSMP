package io.github.kabanfriends.kabansmp.module.pvp.command;

import io.github.kabanfriends.kabansmp.command.SMPCommand;
import io.github.kabanfriends.kabansmp.module.pvp.PVPModule;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPVP implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (PVPModule.PVP_PLAYERS.contains(player)) {
            PVPModule.PVP_PLAYERS.remove(player);
            player.sendMessage(Components.formatted(Format.PVP_DISABLED, "pvp.command.pvp.disabled"));
        } else {
            PVPModule.PVP_PLAYERS.add(player);
            player.sendMessage(Components.formatted(Format.PVP_ENABLED, "pvp.command.pvp.enabled"));
        }
        return true;
    }
}
