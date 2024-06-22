package io.github.kabanfriends.kabansmp.core.module.discord.event;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.module.discord.*;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Level;

public class JDAEventHandler extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "[Discord] Bot is ready!");
        DiscordModule.initializeBot();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        BotEventHandler.onSlashCommandInteraction(event);
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        BotEventHandler.onModalInteraction(event);
    }
}
