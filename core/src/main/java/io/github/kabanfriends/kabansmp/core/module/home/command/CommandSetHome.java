package io.github.kabanfriends.kabansmp.core.module.home.command;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

        Bukkit.getScheduler().runTaskAsynchronously(KabanSMP.getInstance(), () -> {
            Location location = player.getLocation().clone();
            location.setPitch(0);

            PlayerData data = PlayerDataManager.getPlayerData(player);
            data.setValue(HomeModule.HOME_LOCATION_DATA, location);

            player.sendMessage(Components.formatted(
                    Format.HOME_SUCCESS,
                    "home.command.sethome.success",
                    Component.text("/home").color(ServerColors.MUSTARD)
            ));
        });

        return true;
    }
}
