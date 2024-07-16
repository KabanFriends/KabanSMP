package io.github.kabanfriends.kabansmp.core.module.collision.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CollisionEventHandler implements Listener {

    private static boolean initialized = false;
    private static Team defaultTeam;

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!initialized) {
            initialized = true;

            Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
            defaultTeam = scoreboard.getTeam("default");
            if (defaultTeam == null) {
                defaultTeam = scoreboard.registerNewTeam("default");
                defaultTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        defaultTeam.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        defaultTeam.removePlayer(event.getPlayer());
    }
}
