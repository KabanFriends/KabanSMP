package io.github.kabanfriends.kabansmp.core;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.database.Database;
import io.github.kabanfriends.kabansmp.core.language.LanguageManager;
import io.github.kabanfriends.kabansmp.core.module.Modules;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class KabanSMPPlugin extends JavaPlugin {

    private static KabanSMPPlugin instance;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;

        File pluginDir = new File("plugins/KabanSMP");
        if (!pluginDir.exists()) {
            pluginDir.mkdir();
        }

        new SharedConfig().load();

        Database.start();
        LanguageManager.load();
        Modules.load();

        Bukkit.getScheduler().runTask(this, () -> PacketEvents.getAPI().init());
    }

    @Override
    public void onDisable() {
        Modules.close();
        PacketEvents.getAPI().terminate();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Modules.handleCommand(sender, cmd, args)) {
            return true;
        }
        sender.sendMessage(Components.formatted(Format.GENERIC_FAIL, "all.command.notLoaded"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        return Modules.handleTabComplete(sender, cmd, args);
    }

    public static KabanSMPPlugin getInstance() {
        return instance;
    }
}
