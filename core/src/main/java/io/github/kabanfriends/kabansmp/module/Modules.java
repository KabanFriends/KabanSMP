package io.github.kabanfriends.kabansmp.module;

import io.github.kabanfriends.kabansmp.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.config.ModuleConfig;
import io.github.kabanfriends.kabansmp.module.autosave.AutosaveModule;
import io.github.kabanfriends.kabansmp.module.base.BaseModule;
import io.github.kabanfriends.kabansmp.module.collision.CollisionModule;
import io.github.kabanfriends.kabansmp.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.module.join.JoinModule;
import io.github.kabanfriends.kabansmp.module.motd.MotdModule;
import io.github.kabanfriends.kabansmp.module.pvp.PVPModule;
import io.github.kabanfriends.kabansmp.module.tablist.TablistModule;
import io.github.kabanfriends.kabansmp.module.test.TestModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Modules {

    private static final Map<String, Module> MODULES = new HashMap<>();

    public static void load() {
        for (String name : ModuleConfig.MODULES) {
            Module module = findModule(name);
            if (module != null) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading module: " + name);
                module.load();
                MODULES.put(name, module);
            } else {
                KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Module not found: " + name);
            }
        }
    }

    public static boolean isModuleLoaded(String name) {
        return MODULES.containsKey(name);
    }

    public static boolean handleCommand(CommandSender sender, Command cmd, String[] args) {
        for (Module module : MODULES.values()) {
            if (module.handleCommand(sender, cmd, args)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> handleTabComplete(CommandSender sender, Command cmd, String[] args) {
        for (Module module : MODULES.values()) {
            List<String> list = module.handleTabComplete(sender, cmd, args);
            if (list != null) {
                return list;
            }
        }
        return Collections.emptyList();
    }

    public static Module findModule(String name) {
        return switch (name) {
            case "test" -> new TestModule();
            case "base" -> new BaseModule();
            case "autosave" -> new AutosaveModule();
            case "join" -> new JoinModule();
            case "damage" -> new DamageModule();
            case "pvp" -> new PVPModule();
            case "home" -> new HomeModule();
            case "motd" -> new MotdModule();
            case "tablist" -> new TablistModule();
            case "hardcore" -> new HardcoreModule();
            case "collision" -> new CollisionModule();
            default -> null;
        };
    }
}
