package io.github.kabanfriends.kabansmp.core.module.motd.event;

import io.github.kabanfriends.kabansmp.core.config.MotdConfig;
import io.github.kabanfriends.kabansmp.core.text.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Random;

public class MotdEventHandler implements Listener {

    public static final Random RANDOM = new Random();

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        Component[] lines = new Component[MotdConfig.MOTD_LINES.size()];
        for (int i = 0; i < MotdConfig.MOTD_LINES.size(); i++) {
            String line = MotdConfig.MOTD_LINES.get(i);
            if (line.contains("{random}")) {
                int index = RANDOM.nextInt(MotdConfig.RANDOM_MESSAGES.size());
                line = line.replaceAll("\\{random}", MotdConfig.RANDOM_MESSAGES.get(index));
            }
            lines[i] = MiniMessage.miniMessage().deserialize(line);
        }

        event.motd(Components.newlined(lines));
    }
}
