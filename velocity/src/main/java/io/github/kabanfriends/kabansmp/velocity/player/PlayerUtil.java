package io.github.kabanfriends.kabansmp.velocity.player;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;

import java.util.Optional;
import java.util.UUID;

public class PlayerUtil {

    public static boolean isPlayerInLobby(UUID uuid) {
        Optional<Player> optional = KabanSMPVelocity.getInstance().getServer().getPlayer(uuid);
        if (optional.isEmpty()) {
            return false;
        }
        return isPlayerInLobby(optional.get());
    }

    public static boolean isPlayerInLobby(Player player) {
        Optional<ServerConnection> optional2 = player.getCurrentServer();
        if (optional2.isEmpty()) {
            return false;
        }
        String serverName = optional2.get().getServerInfo().getName();
        if (!serverName.equals("lobby")) {
            return false;
        }
        return true;
    }

    public static void optionallyOpenGui(UUID uuid) {
        Optional<Player> optional = KabanSMPVelocity.getInstance().getServer().getPlayer(uuid);
        if (optional.isEmpty()) {
            return;
        }
        ServerSelector.openGui(optional.get());
    }
}
