package io.github.kabanfriends.kabansmp.core.module.creative.event;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.domains.PlayerDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class CreativeWorldEventHandler implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!event.getWorld().getName().equals("world")) {
            return;
        }

        // I don't like this, but I don't know how to wait until WorldGuard finishes loading
        Bukkit.getScheduler().runTaskLater(KabanSMPPlugin.getInstance(), () -> {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager manager = container.get(BukkitAdapter.adapt(event.getWorld()));

            DefaultDomain domain = new DefaultDomain();
            domain.getPlayerDomain().addPlayer("*");

            GlobalProtectedRegion global = new GlobalProtectedRegion("__global__");
            global.setOwners(domain);

            manager.addRegion(global);
        }, 0L);
    }
}
