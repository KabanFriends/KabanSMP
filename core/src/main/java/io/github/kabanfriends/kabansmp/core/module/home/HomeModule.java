package io.github.kabanfriends.kabansmp.core.module.home;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.home.command.CommandSetHome;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.module.home.command.CommandHome;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class HomeModule implements Module {

    private static final Map<UUID, Home> READ_CACHE = new HashMap<>();
    private static final Map<UUID, Home> WRITE_CACHE = new HashMap<>();

    public static void saveHomes() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Saving homes...");

        for (Home home : WRITE_CACHE.values()) {
            if (home != null) writeHome(home);
        }
    }

    public static void writeHome(Home home) {
        YamlConfiguration yaml = new YamlConfiguration();

        yaml.set("owner", home.owner().toString());
        yaml.set("location", home.location());

        try {
            yaml.save("plugins/KabanSMP/homes/" + home.owner() + ".yml");
        } catch (IOException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while writing to home yaml:");
            e.printStackTrace();
        }
    }

    public static Home getHome(UUID uuid) {
        if (!READ_CACHE.containsKey(uuid)) {
            File file = new File("plugins/KabanSMP/homes/" + uuid + ".yml");
            if (file.exists() && file.isFile()) {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

                Location loc = yaml.getLocation("location");
                Home home = new Home(uuid, loc);

                READ_CACHE.put(uuid, home);
                return home;
            }
            return null;
        }else {
            return READ_CACHE.get(uuid);
        }
    }

    public static void setHome(Home home) {
        READ_CACHE.put(home.owner(), home);
        WRITE_CACHE.put(home.owner(), home);
    }

    public static void deleteHome(UUID uuid) {
        READ_CACHE.put(uuid, null);
        WRITE_CACHE.remove(uuid);

        File file = new File("plugins/KabanSMP/homes/" + uuid + ".yml");
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    @Override
    public void load() {
        File homeDir = new File("plugins/KabanSMP/homes");
        if (!homeDir.exists()) {
            homeDir.mkdir();
        }

        registerCommand("sethome", new CommandSetHome());
        registerCommand("home", new CommandHome());
    }
}
