package io.github.kabanfriends.kabansmp.core.module.join.event;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.injector.api.ChatMixinAPI;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.player.PlayerNames;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
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

        PlayerNames.updateDisplayName(player);

        boolean isBedrock = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
        event.joinMessage(Components.formatted(
                Format.JOIN_MESSAGE,
                "join.message.joined",
                player.displayName().colorIfAbsent(ServerColors.GREEN_LIGHT),
                isBedrock ? Components.translatable("all.minecraft.bedrock") : Components.translatable("all.minecraft.java")
        ));

        Bukkit.getScheduler().runTaskLaterAsynchronously(KabanSMP.getInstance(), () -> {
            if (!player.isOnline()) {
                return;
            }
            player.sendMessage(Components.formatted(
                    Format.GENERIC_NOTIFY,
                    "join.message.website",
                    Component.text(SharedConfig.WEBSITE_URL.get(), ServerColors.MUSTARD)
                            .clickEvent(ClickEvent.openUrl(SharedConfig.WEBSITE_URL.get()))
                            .hoverEvent(HoverEvent.showText(Components.translatable("all.chat.clickToOpen")))
            ));
        }, 5 * 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        DamageModule.LAST_DAMAGE_TICKS.remove(player);
        HardcoreModule.PLAYERS_TO_RESPAWN.remove(player);

        ChatMixinAPI.resetChatSessionUpdate(player);

        event.quitMessage(Components.formatted(Format.QUIT_MESSAGE, "join.message.left", player.displayName().colorIfAbsent(ServerColors.RED_LIGHT)));
    }
}
