package io.github.kabanfriends.kabansmp.core.module.hardcore.event;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.util.api.PlayerAPI;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.player.PlayerNames;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class HardcoreDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.getPlayerData(player);

        boolean isHardcore = data.getValue(HardcoreModule.HARDCORE_MODE_DATA);
        if (isHardcore) {
            PlayerNames.updateDisplayName(player);
            HardcoreModule.PLAYERS_TO_RESPAWN.add(player);
        }

        Bukkit.getScheduler().runTaskAsynchronously(KabanSMPPlugin.getInstance(), () -> {
            int deaths = data.getValue(HardcoreModule.DEATH_COUNT_DATA);
            data.setValue(HardcoreModule.DEATH_COUNT_DATA, ++deaths);
            if (isHardcore) {
                data.setValue(HardcoreModule.HARDCORE_MODE_DATA, false);
            }
        });
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (HardcoreModule.PLAYERS_TO_RESPAWN.contains(player)) {
            HardcoreModule.PLAYERS_TO_RESPAWN.remove(player);

            player.sendMessage(Components.formatted(Format.HARDCORE_FAIL, "hardcore.message.died"));
            if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                PlayerAPI.setHardcoreHeart(player, false, true);
            }
        }
    }
}
