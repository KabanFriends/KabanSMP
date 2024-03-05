package io.github.kabanfriends.kabansmp.core.module.damage.event;

import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        DamageModule.LAST_DAMAGE_TICKS.put(player, Bukkit.getCurrentTick());
    }
}
