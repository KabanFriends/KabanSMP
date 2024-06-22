package io.github.kabanfriends.kabansmp.core.module.discord.event;

import com.discordsrv.api.event.bus.Subscribe;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DiscordSRVEventHandler {

    @Subscribe
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        BotEventHandler.onSlashCommandInteraction(event);
    }

    @Subscribe
    public void onModalInteraction(ModalInteractionEvent event) {
        BotEventHandler.onModalInteraction(event);
    }
}
