package io.github.kabanfriends.kabansmp.api;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ChatMixinAPI {

    public static final Set<Player> SESSION_AWAITING_PLAYERS = new HashSet<>();

    public static void allowNextChatSessionUpdate(Player player) {
        SESSION_AWAITING_PLAYERS.add(player);
    }

    public static void resetChatSessionUpdate(Player player) {
        SESSION_AWAITING_PLAYERS.remove(player);
    }
}
