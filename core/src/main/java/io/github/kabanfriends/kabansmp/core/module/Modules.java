package io.github.kabanfriends.kabansmp.core.module;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.module.base.BaseModule;
import io.github.kabanfriends.kabansmp.core.module.collision.CollisionModule;
import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordModule;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.core.module.join.JoinModule;
import io.github.kabanfriends.kabansmp.core.module.motd.MotdModule;
import io.github.kabanfriends.kabansmp.core.module.pvp.PVPModule;
import io.github.kabanfriends.kabansmp.core.module.serverlinks.ServerLinksModule;
import io.github.kabanfriends.kabansmp.core.module.spawn.SpawnModule;
import io.github.kabanfriends.kabansmp.core.module.tablist.TablistModule;
import io.github.kabanfriends.kabansmp.core.module.test.TestModule;
import io.github.kabanfriends.kabansmp.core.module.worldload.WorldLoadModule;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Modules {

    private static final Map<String, Module> LOADED_MODULES = new HashMap<>();

    public static void load() {
        modules:
        for (String name : SharedConfig.MODULES.get()) {
            Module module = findModule(name);
            if (module != null) {
                KabanSMP.getInstance().getLogger().log(Level.INFO, "Loading module: " + name);
                for (PlatformCapability capability : module.requiredCapabilities()) {
                    if (!KabanSMP.getInstance().getPlatform().hasCapability(capability)) {
                        KabanSMP.getInstance().getLogger().log(Level.SEVERE, "Platform does not have the capability " + capability.name() + " required by module \"" + name + "\".");
                        continue modules;
                    }
                }
                module.onLoad();
                LOADED_MODULES.put(name, module);
            } else {
                KabanSMP.getInstance().getLogger().log(Level.WARNING, "Module not found: " + name);
            }
        }
    }

    public static void close() {
        for (Module module : LOADED_MODULES.values()) {
            module.onClose();
        }
        LOADED_MODULES.clear();
    }

    public static boolean isModuleEnabled(String name) {
        return LOADED_MODULES.containsKey(name);
    }

    public static boolean handleCommand(CommandSender sender, Command cmd, String[] args) {
        for (Module module : LOADED_MODULES.values()) {
            if (module.handleCommand(sender, cmd, args)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> handleTabComplete(CommandSender sender, Command cmd, String[] args) {
        for (Module module : LOADED_MODULES.values()) {
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
            case "join" -> new JoinModule();
            case "damage" -> new DamageModule();
            case "pvp" -> new PVPModule();
            case "home" -> new HomeModule();
            case "motd" -> new MotdModule();
            case "tablist" -> new TablistModule();
            case "hardcore" -> new HardcoreModule();
            case "spawn" -> new SpawnModule();
            case "collision" -> new CollisionModule();
            case "discord" -> new DiscordModule();
            case "serverlinks" -> new ServerLinksModule();
            case "worldload" -> new WorldLoadModule();
            default -> null;
        };
    }
}
