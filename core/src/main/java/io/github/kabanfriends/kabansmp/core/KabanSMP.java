package io.github.kabanfriends.kabansmp.core;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.database.Database;
import io.github.kabanfriends.kabansmp.core.platform.Platform;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.text.language.LanguageManager;
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

public class KabanSMP extends JavaPlugin {

    private static KabanSMP instance;

    private Platform platform;

    @Override
    public void onLoad() {
        if (platform == null) {
            throw new IllegalStateException("No platform is specified!");
        }
        if (platform.hasCapability(PlatformCapability.PACKET_EVENTS)) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            PacketEvents.getAPI().load();
        }
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

        if (platform.hasCapability(PlatformCapability.PACKET_EVENTS)) {
            Bukkit.getScheduler().runTask(this, () -> PacketEvents.getAPI().init());
        }
    }

    @Override
    public void onDisable() {
        Modules.close();
        if (platform.hasCapability(PlatformCapability.PACKET_EVENTS)) {
            PacketEvents.getAPI().terminate();
        }
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

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public static KabanSMP getInstance() {
        return instance;
    }
}
