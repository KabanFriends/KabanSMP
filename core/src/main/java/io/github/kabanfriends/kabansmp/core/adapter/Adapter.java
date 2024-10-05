package io.github.kabanfriends.kabansmp.core.adapter;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

public abstract class Adapter {

    public abstract Audience getAudience(CommandSender sender);

    public abstract int getCurrentTick();

    public abstract void close();
}
