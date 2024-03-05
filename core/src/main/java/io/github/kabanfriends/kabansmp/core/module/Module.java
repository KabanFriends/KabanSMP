package io.github.kabanfriends.kabansmp.core.module;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Module {

    Map<String, SMPCommand> commands = new HashMap<>();

    void load();

    default void registerCommand(String name, SMPCommand command) {
        commands.put(name, command);
    }

    default boolean handleCommand(CommandSender sender, Command cmd, String[] args) {
        String name = cmd.getName().toLowerCase();
        for (Map.Entry<String, SMPCommand> entry : commands.entrySet()) {
            if (name.equals(entry.getKey())) {
                return entry.getValue().run(sender, cmd, args);
            }
        }
        return false;
    }

    default List<String> handleTabComplete(CommandSender sender, Command cmd, String[] args) {
        String name = cmd.getName().toLowerCase();
        for (Map.Entry<String, SMPCommand> entry : commands.entrySet()) {
            if (name.equals(entry.getKey())) {
                return entry.getValue().tabComplete(sender, cmd, args);
            }
        }
        return null;
    }

    default void registerEvents(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, KabanSMPPlugin.getInstance());
    }
}
