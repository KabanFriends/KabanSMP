package io.github.kabanfriends.kabansmp.core;

import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.config.ModuleConfig;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.module.Modules;
import io.github.kabanfriends.kabansmp.core.module.autosave.AutosaveModule;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class KabanSMPPlugin extends JavaPlugin {

    private static KabanSMPPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        File pluginDir = new File("plugins/KabanSMP");
        if (!pluginDir.exists()) {
            pluginDir.mkdir();
        }

        // Load configs
        SharedConfig.load();
        LanguageConfig.load();
        ModuleConfig.load();

        // Load modules
        Modules.load();

        // Other stuff
        PlayerDataManager.init();
    }

    @Override
    public void onDisable() {
        AutosaveModule.doAutosave();
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
