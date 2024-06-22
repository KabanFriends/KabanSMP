package io.github.kabanfriends.kabansmp.core.module.discord.event;

import io.github.kabanfriends.kabansmp.core.config.DiscordConfig;
import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordLink;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordUserInfo;
import io.github.kabanfriends.kabansmp.core.module.discord.MinecraftUserInfo;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

public class DiscordJoinEventHandler implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        DiscordUserInfo discordId = DiscordLink.getLinkedDiscordUser(event.getUniqueId());
        if (discordId == null) {
            UUID uuid = event.getUniqueId();

            boolean isBedrock = FloodgateApi.getInstance().isFloodgateId(uuid);
            MinecraftUserInfo info = new MinecraftUserInfo(
                    uuid,
                    isBedrock ? FloodgateApi.getInstance().getPlayer(uuid).getUsername() : event.getName(),
                    isBedrock
            );
            String verifyCode = DiscordLink.startVerification(info);

            Component code = Component.text(verifyCode, Style.style(ServerColors.LIME, TextDecoration.BOLD));

            Component reason;
            if (isBedrock) {
                reason = Components.newlined(
                        Components.translatable("discord.verify.kick.description.bedrock").color(ServerColors.AQUA_LIGHT_3),
                        Component.empty(),
                        Components.translatable("discord.verify.kick.code.bedrock", code).color(ServerColors.WHITE)
                );
            } else {
                Component url = Component.text(DiscordConfig.HELP_URL.get(), ServerColors.MUSTARD)
                        .hoverEvent(HoverEvent.showText(Components.translatable("all.chat.clickToOpen")))
                        .clickEvent(ClickEvent.openUrl(DiscordConfig.HELP_URL.get()));

                reason = Components.newlined(
                        Components.translatable("discord.verify.kick.description").color(ServerColors.AQUA_LIGHT_3),
                        Component.empty(),
                        Components.translatable("discord.verify.kick.code", code).color(ServerColors.WHITE),
                        Component.empty(),
                        Components.translatable("discord.verify.kick.help", url).color(ServerColors.GREEN_LIGHT_3)
                );
            }

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, GlobalTranslator.render(reason, LanguageConfig.DEFAULT_LOCALE.get()));
            return;
        }
        event.allow();
    }
}
