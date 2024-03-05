package io.github.kabanfriends.kabansmp.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public interface SMPCommand {

    boolean run(CommandSender sender, Command cmd, String[] args);

    default List<String> tabComplete(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}
