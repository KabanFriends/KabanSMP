package io.github.kabanfriends.kabansmp.core.player;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerNames {

    private static final Component HARDCORE_MODE_SUFFIX = Component.text("❣", ServerColors.RED);

    public static void updateDisplayName(Player player) {
        // Reserved for nicknames? Maybe...
        setDisplayName(player, player.name());
    }

    public static void setDisplayName(Player player, @Nullable Component name) {
        Bukkit.getScheduler().runTaskAsynchronously(KabanSMP.getInstance(), () -> {
            Component toSet = name;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            if (data.getValue(HardcoreModule.HARDCORE_MODE_DATA)) {
                if (name == null) {
                    toSet = player.name();
                }
                toSet = toSet.append(HARDCORE_MODE_SUFFIX.hoverEvent(HoverEvent.showText(Components.translatable("hardcore.tooltip.chatTag"))));
            } else if (toSet == player.name()) {
                toSet = null;
            }
            player.displayName(toSet);
        });
    }
}
