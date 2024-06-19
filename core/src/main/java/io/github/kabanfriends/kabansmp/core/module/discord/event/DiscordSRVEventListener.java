package io.github.kabanfriends.kabansmp.core.module.discord.event;

import com.discordsrv.api.event.bus.Subscribe;
import io.github.kabanfriends.kabansmp.core.module.discord.BotEventHandler;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DiscordSRVEventListener {

    @Subscribe
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        BotEventHandler.onSlashCommandInteraction(event);
    }

    @Subscribe
    public void onModalInteraction(ModalInteractionEvent event) {
        BotEventHandler.onModalInteraction(event);
    }
}
