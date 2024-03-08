package io.github.kabanfriends.kabansmp.velocity.event;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import io.github.kabanfriends.kabansmp.velocity.player.PlayerUtil;

public class PlayerChatEventHandler {

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChat(PlayerChatEvent event) {
        if (PlayerUtil.isPlayerInLobby(event.getPlayer())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());
        }
    }
}
