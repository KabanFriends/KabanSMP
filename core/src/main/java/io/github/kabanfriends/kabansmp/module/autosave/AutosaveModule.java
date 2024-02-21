package io.github.kabanfriends.kabansmp.module.autosave;

import io.github.kabanfriends.kabansmp.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.player.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

public class AutosaveModule implements Module {

    private static final int AUTOSAVE_INTERVAL = 1800;

    @Override
    public void load() {
        BukkitTask task = new BukkitRunnable() {
            public void run() {
                KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Autosave started");
                doAutosave();
                KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Autosave finished");
            }
        }.runTaskTimer(KabanSMPPlugin.getInstance(), AUTOSAVE_INTERVAL * 20, AUTOSAVE_INTERVAL * 20);
    }

    public static void doAutosave() {
        HomeModule.saveHomes();
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerDataManager.savePlayer(player);
        }
    }
}
