package io.github.kabanfriends.kabansmp.core.module.pvp.event;

import io.github.kabanfriends.kabansmp.core.module.pvp.PVPModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPEventHandler implements Listener {

    @EventHandler
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager) || !(event.getEntity() instanceof Player victim)) {
            return;
        }

        if (!PVPModule.PVP_PLAYERS.contains(damager) || !PVPModule.PVP_PLAYERS.contains(victim)) {
            event.setCancelled(true);
        }
    }
}
