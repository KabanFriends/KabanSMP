package io.github.kabanfriends.kabansmp.bukkit;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.adapter.Adapter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BukkitAdapter extends Adapter {

    private final BukkitAudiences audiences;

    private int currentTick;

    public BukkitAdapter(BukkitAudiences audiences) {
        this.audiences = audiences;
        // Not the smartest way, but it works!
        Bukkit.getScheduler().runTaskTimer(KabanSMP.getInstance(), () -> currentTick++, 0L, 1L);
    }

    @Override
    public Audience getAudience(CommandSender sender) {
        return audiences.sender(sender);
    }

    @Override
    public int getCurrentTick() {
        return currentTick;
    }

    @Override
    public void close() {
        audiences.close();
    }
}
