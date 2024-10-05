package io.github.kabanfriends.kabansmp.core.module.worldload.event;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.WorldLoadConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.logging.Level;

public class WorldLoadEventHandler implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        for (String name : WorldLoadConfig.WORLDS_TO_LOAD.get()) {
            if (event.getWorld().getName().equals(name)) {
                return;
            }
        }
        KabanSMP.getInstance().getLogger().log(Level.INFO, "Ignoring preparation for " + event.getWorld().getName());
        event.getWorld().setKeepSpawnInMemory(false);
    }
}
