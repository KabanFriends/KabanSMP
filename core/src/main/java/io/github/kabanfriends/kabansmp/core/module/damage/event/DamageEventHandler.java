package io.github.kabanfriends.kabansmp.core.module.damage.event;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DamageEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        int currentTick = KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API) ? Bukkit.getCurrentTick() : KabanSMP.getInstance().getAdapter().getCurrentTick();
        DamageModule.LAST_DAMAGE_TICKS.put(player, currentTick);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DamageModule.LAST_DAMAGE_TICKS.remove(event.getPlayer());
    }
}
