package io.github.kabanfriends.kabansmp.module.hardcore.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.github.kabanfriends.kabansmp.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.player.data.PlayerDataManager;
import org.bukkit.entity.Player;

public class HardcorePacketListener {

    public static void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(KabanSMPPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.LOGIN) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        try {
                            Player player = event.getPlayer();
                            PlayerDataManager.loadPlayer(player);
                            PlayerData data = PlayerDataManager.getPlayerData(player);

                            PacketContainer packet = event.getPacket();

                            if (data.hardcoreMode) {
                                packet.getBooleans().write(0, true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                            // Probably a bedrock player; hardcore hearts don't exist in bedrock anyway so just ignore it
                        }
                    }
                }
        );
    }
}
