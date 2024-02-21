package io.github.kabanfriends.kabansmp.module.home.command;

import io.github.kabanfriends.kabansmp.command.SMPCommand;
import io.github.kabanfriends.kabansmp.module.home.Home;
import io.github.kabanfriends.kabansmp.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.player.Teleports;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.Format;
import io.github.kabanfriends.kabansmp.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHome implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Home home = HomeModule.getHome(player.getUniqueId());
            if (home == null) {
                player.sendMessage(Components.formatted(
                        Format.HOME_NOTIFY,
                        "home.command.home.notSet",
                        Component.text("/sethome").color(ServerColors.MUSTARD)
                ));
                return true;
            }

            Teleports.teleport(player, home.location(), false, () -> {
                player.sendMessage(Components.formatted(Format.HOME_SUCCESS, "home.command.home.success"));
            });
        }
        return true;
    }
}
