package io.github.kabanfriends.kabansmp.module.base.command;

import io.github.kabanfriends.kabansmp.command.SMPCommand;
import io.github.kabanfriends.kabansmp.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.Format;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandReloadLang implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        LanguageConfig.unload();
        LanguageConfig.load();
        sender.sendMessage(Components.formatted(Format.GENERIC_SUCCESS, "base.command.reloadlang.success").color(NamedTextColor.GREEN));
        return true;
    }
}
