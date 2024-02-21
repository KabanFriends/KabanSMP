package io.github.kabanfriends.kabansmp.module.home.command;

import io.github.kabanfriends.kabansmp.command.SMPCommand;
import io.github.kabanfriends.kabansmp.module.home.Home;
import io.github.kabanfriends.kabansmp.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.Format;
import io.github.kabanfriends.kabansmp.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements SMPCommand {


    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        player.sendMessage(Components.formatted(
                Format.HOME_SUCCESS,
                "home.command.sethome.success",
                Component.text("/home").color(ServerColors.MUSTARD)
        ));

        Location location = player.getLocation().clone();
        location.setPitch(0);

        Home home = new Home(player.getUniqueId(), location);

        HomeModule.setHome(home);

        return true;
    }
}
