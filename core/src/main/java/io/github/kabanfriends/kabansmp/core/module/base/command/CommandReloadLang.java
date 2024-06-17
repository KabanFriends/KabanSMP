package io.github.kabanfriends.kabansmp.core.module.base.command;

import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.text.language.LanguageManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandReloadLang implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        LanguageManager.unload();
        LanguageManager.load();
        sender.sendMessage(Components.formatted(Format.GENERIC_SUCCESS, "base.command.reloadlang.success").color(NamedTextColor.GREEN));
        return true;
    }
}
