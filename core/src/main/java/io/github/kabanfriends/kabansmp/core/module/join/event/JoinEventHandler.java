package io.github.kabanfriends.kabansmp.core.module.join.event;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.util.AdventureUtil;
import io.github.kabanfriends.kabansmp.core.config.SharedConfig;
import io.github.kabanfriends.kabansmp.core.player.PlayerNames;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
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

        Component message;
        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.FLOODGATE)) {
            boolean isBedrock = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
            message = Components.formatted(
                    Format.JOIN_MESSAGE,
                    "join.message.joined.edition",
                    AdventureUtil.getPlayerDisplayName(player).colorIfAbsent(ServerColors.GREEN_LIGHT),
                    isBedrock ? Components.translatable("all.minecraft.bedrock") : Components.translatable("all.minecraft.java")
            );
        } else {
            message = Components.formatted(
                    Format.JOIN_MESSAGE,
                    "join.message.joined",
                    AdventureUtil.getPlayerDisplayName(player).colorIfAbsent(ServerColors.GREEN_LIGHT)
            );
        }

        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            event.joinMessage(message);
        } else {
            event.setJoinMessage(null);
            Bukkit.getConsoleSender().sendMessage(PlainTextComponentSerializer.plainText().serialize(GlobalTranslator.render(message, LanguageConfig.DEFAULT_LOCALE.get())));
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                AdventureUtil.sendMessage(player1, message);
            }
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(KabanSMP.getInstance(), () -> {
            if (!player.isOnline()) {
                return;
            }
            AdventureUtil.sendMessage(player, Components.formatted(
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

        Component message = Components.formatted(Format.QUIT_MESSAGE, "join.message.left", AdventureUtil.getPlayerDisplayName(player).colorIfAbsent(ServerColors.RED_LIGHT));

        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            event.quitMessage(message);
        } else {
            event.setQuitMessage(null);
            Bukkit.getConsoleSender().sendMessage(PlainTextComponentSerializer.plainText().serialize(GlobalTranslator.render(message, LanguageConfig.DEFAULT_LOCALE.get())));
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                AdventureUtil.sendMessage(player1, message);
            }
        }
    }
}
