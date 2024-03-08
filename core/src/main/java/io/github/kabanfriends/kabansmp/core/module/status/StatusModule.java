package io.github.kabanfriends.kabansmp.core.module.status;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.networking.packet.RedisPacketHandler;
import io.github.kabanfriends.kabansmp.networking.packet.impl.ServerStatusPacket;
import io.github.kabanfriends.kabansmp.networking.recipient.Recipients;
import io.github.kabanfriends.kabansmp.networking.recipient.ServerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class StatusModule implements Module {

    @Override
    public void load() {
        new BukkitRunnable() {
            @Override
            public void run() {
                RedisPacketHandler.sendPacket(new ServerStatusPacket(ServerManager.getCurrentServer().getName()), Recipients.PROXY);
            }
        }.runTaskTimerAsynchronously(KabanSMPPlugin.getInstance(), 0L, ServerStatusPacket.HEARTBEAT_INTERVAL_SECONDS);
    }
}
