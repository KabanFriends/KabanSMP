package io.github.kabanfriends.kabansmp.core.module.discord.command;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordLink;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordUserInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;
import java.util.UUID;

public class CommandSearchUser implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /searchuser <uuid|name|id>");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(KabanSMPPlugin.getInstance(), () -> {
            String query = args[0];
            if (query.matches("^[\\d]{17,}$")) { // Looks like a discord ID, search MC users
                DiscordUserInfo info = DiscordLink.getDiscordUserFromID(query);
                if (info == null) {
                    sender.sendMessage(ChatColor.RED + "Discord user was not found");
                    return;
                }
                sender.sendMessage(ChatColor.YELLOW + "Discord user: " + info.username());

                Set<UUID> set = DiscordLink.getLinkedUUIDs(query);
                for (UUID uuid : set) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                    sender.sendMessage("- " + ChatColor.GREEN + player.getName() + ChatColor.RESET + " (" + player.getUniqueId() + ")");
                }
            } else { // Search discord users
                UUID uuid;
                if (query.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    uuid = UUID.fromString(query);
                } else {
                    uuid = Bukkit.getOfflinePlayer(query).getUniqueId();
                }

                DiscordUserInfo info = DiscordLink.getLinkedDiscordUser(uuid);
                if (info == null) {
                    sender.sendMessage(ChatColor.RED + "This player has no associated Discord users");
                    return;
                }

                sender.sendMessage("Discord user: " + ChatColor.GREEN + info.username() + ChatColor.RESET + " (" + info.userId() + ")");
            }
        });

        return true;
    }
}
