package io.github.kabanfriends.kabansmp.player;

import io.github.kabanfriends.kabansmp.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerNames {

    private static final Component HARDCORE_MODE_SUFFIX = Component.text("❣", ServerColors.RED);

    public static void updateDisplayName(Player player) {
        // Reserved for nicknames? Maybe...
        setDisplayName(player, player.name());
    }

    public static void setDisplayName(Player player, @Nullable Component name) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data.hardcoreMode) {
            if (name == null) {
                name = player.name();
            }
            name = name.append(HARDCORE_MODE_SUFFIX.hoverEvent(HoverEvent.showText(Components.translatable("hardcore.tooltip.chatTag"))));
        } else if (name == player.name()) {
            name = null;
        }
        player.displayName(name);
    }
}
