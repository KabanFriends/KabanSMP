package io.github.kabanfriends.kabansmp.core.module.home.command;

import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.module.home.Home;
import io.github.kabanfriends.kabansmp.core.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.core.player.Teleports;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.core.util.LocationUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHome implements SMPCommand {

    private static final int COOLDOWN_TICKS = 20 * 20;
    private static final Map<Player, Integer> COMMAND_USE_TICKS = new HashMap<>();

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (COMMAND_USE_TICKS.containsKey(player) && Bukkit.getCurrentTick() - COMMAND_USE_TICKS.get(player) <= COOLDOWN_TICKS) {
                int left = COOLDOWN_TICKS - (Bukkit.getCurrentTick() - COMMAND_USE_TICKS.get(player));
                player.sendMessage(Components.formatted(
                        Format.HOME_FAIL,
                        "home.command.home.cooldown",
                        Components.translatable("all.time.seconds", left / 20f).color(ServerColors.MUSTARD)
                ));
                return true;
            }

            Home home = HomeModule.getHome(player.getUniqueId());
            if (home == null) {
                player.sendMessage(Components.formatted(
                        Format.HOME_NOTIFY,
                        "home.command.home.notSet",
                        Component.text("/sethome").color(ServerColors.MUSTARD)
                ));
                return true;
            }

            if (!Teleports.checkAndNotifyTeleport(player)) {
                return true;
            }

            // TODO: Move this to a common logic for warp
            Location homeLocation = home.location().clone().set(
                    Math.floor(home.location().getX()) + 0.5,
                    Math.floor(home.location().getY()),
                    Math.floor(home.location().getZ()) + 0.5
            );

            COMMAND_USE_TICKS.put(player, Bukkit.getCurrentTick());
            Location safeLocation = LocationUtil.getSafeDestination(homeLocation);
            if (safeLocation == null) {
                player.sendMessage(Components.formatted(
                        Format.HOME_FAIL,
                        "home.command.home.notSafe"
                ));
                return true;
            }

            Teleports.teleport(player, safeLocation, false, () -> {
                player.sendMessage(Components.formatted(Format.HOME_SUCCESS, "home.command.home.success"));

                if (!safeLocation.equals(homeLocation)) {
                    player.sendMessage(Components.formatted(
                            Format.HOME_NOTIFY,
                            "home.command.home.moved",
                            Component.text("/sethome").color(ServerColors.MUSTARD)
                    ));
                }
            });
        }
        return true;
    }
}
