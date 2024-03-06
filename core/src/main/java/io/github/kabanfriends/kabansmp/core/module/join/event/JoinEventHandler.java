package io.github.kabanfriends.kabansmp.core.module.join.event;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.injector.api.ChatMixinAPI;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.player.PlayerNames;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.networking.config.ProxyConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class JoinEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.loadPlayer(player);
        PlayerData data = PlayerDataManager.getPlayerData(player);
        data.hasJoined = true;

        PlayerNames.updateDisplayName(player);

        if (ProxyConfig.useProxy) {
            event.joinMessage(null);
        } else {
            boolean isBedrock = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
            event.joinMessage(Components.formatted(
                    Format.JOIN_MESSAGE,
                    "join.message.joined",
                    player.displayName().colorIfAbsent(ServerColors.GREEN_LIGHT),
                    isBedrock ? Components.translatable("all.minecraft.bedrock") : Components.translatable("all.minecraft.java")
            ));
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(KabanSMPPlugin.getInstance(), () -> {
            if (!player.isOnline()) {
                return;
            }
            player.sendMessage(Components.formatted(
                    Format.GENERIC_NOTIFY,
                    "join.message.website",
                    Component.text(SharedConfig.websiteUrl, ServerColors.MUSTARD)
                            .clickEvent(ClickEvent.openUrl(SharedConfig.websiteUrl))
                            .hoverEvent(HoverEvent.showText(Components.translatable("all.chat.clickToOpen")))
            ));
        }, 5 * 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerDataManager.savePlayer(player);
        PlayerDataManager.unloadPlayer(player);

        DamageModule.LAST_DAMAGE_TICKS.remove(player);
        HardcoreModule.PLAYERS_TO_RESPAWN.remove(player);

        ChatMixinAPI.resetChatSessionUpdate(player);

        if (ProxyConfig.useProxy) {
            event.quitMessage(null);
        } else {
            event.quitMessage(Components.formatted(Format.QUIT_MESSAGE, "join.message.left", player.displayName().colorIfAbsent(ServerColors.RED_LIGHT)));
        }
    }
}
