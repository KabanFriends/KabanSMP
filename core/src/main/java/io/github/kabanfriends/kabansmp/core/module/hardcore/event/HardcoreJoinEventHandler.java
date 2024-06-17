package io.github.kabanfriends.kabansmp.core.module.hardcore.event;

import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class HardcoreJoinEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            PlayerData data = PlayerDataManager.getPlayerData(event.getUniqueId());

            // This is a bit ugly, but we have to make a sync query to get this value later
            // when the join game packet is sent, so we cache the value here to reduce lag.
            data.getValue(HardcoreModule.HARDCORE_MODE_DATA);
        }
    }
}
