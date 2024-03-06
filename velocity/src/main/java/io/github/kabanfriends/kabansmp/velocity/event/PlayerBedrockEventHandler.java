package io.github.kabanfriends.kabansmp.velocity.event;

import com.velocitypowered.api.proxy.Player;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;
import io.github.kabanfriends.kabansmp.velocity.player.ServerSelector;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.bedrock.SessionJoinEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerBedrockEventHandler implements EventRegistrar {

    @Subscribe
    public void onJoin(SessionJoinEvent event) {
        UUID uuid = new UUID(0, Long.parseLong(event.connection().xuid()));
        Optional<Player> optional = KabanSMPVelocity.getInstance().getServer().getPlayer(uuid);
        if (optional.isEmpty()) {
            KabanSMPVelocity.getInstance().getLogger().warn("onJoin: Player is empty!");
            return;
        }
        Player player = optional.get();
        ServerSelector.openGui(player);
    }
}
