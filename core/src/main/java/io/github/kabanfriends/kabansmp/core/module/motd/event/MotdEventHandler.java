package io.github.kabanfriends.kabansmp.core.module.motd.event;

import io.github.kabanfriends.kabansmp.core.config.MotdConfig;
import io.github.kabanfriends.kabansmp.core.text.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Random;

public class MotdEventHandler implements Listener {

    private static final Component MISSING_RANDOM = Component.text("Random text is not configured!");
    private static final Random RANDOM = new Random();

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        Component[] lines = new Component[MotdConfig.LINES.get().length];
        for (int i = 0; i < MotdConfig.LINES.get().length; i++) {
            String line = MotdConfig.LINES.get()[i];
            Component random = MISSING_RANDOM;
            if (MotdConfig.RANDOM_MESSAGES.get().length > 0) {
                random = MotdConfig.RANDOM_MESSAGES.get()[RANDOM.nextInt(MotdConfig.RANDOM_MESSAGES.get().length)];
            }
            lines[i] = MiniMessage.miniMessage().deserialize(line, Placeholder.component("random", random));
        }

        event.motd(Components.newlined(lines));
    }
}
