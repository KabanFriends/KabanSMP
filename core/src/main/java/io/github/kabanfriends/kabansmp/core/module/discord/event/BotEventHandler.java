package io.github.kabanfriends.kabansmp.core.module.discord.event;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.config.DiscordConfig;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordHelper;
import io.github.kabanfriends.kabansmp.core.module.discord.DiscordLink;
import io.github.kabanfriends.kabansmp.core.module.discord.MinecraftUserInfo;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.bukkit.Bukkit;

import java.util.Locale;
import java.util.logging.Level;

public class BotEventHandler {

    public static void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "[Discord] " + event.getUser().getName() + " used command " + event.getCommandString());
        SlashCommandInteraction interaction = event.getInteraction();
        Locale locale = event.getUserLocale().toLocale();

        if (event.getCommandString().equals("/verify")) {
            Bukkit.getScheduler().runTaskAsynchronously(KabanSMPPlugin.getInstance(), () -> {
                int accounts = DiscordLink.getLinkedUUIDs(event.getUser().getId()).size();
                if (accounts >= DiscordConfig.MAX_LINKS.get()) {
                    interaction.reply(DiscordHelper.text("discord.message.verify.tooMany", locale, accounts)).setEphemeral(true).queue();
                    return;
                }

                TextInput input = TextInput.create("link-key", DiscordHelper.text("discord.modal.verify.key.name", locale), TextInputStyle.SHORT)
                        .setPlaceholder(DiscordHelper.text("discord.modal.verify.key.placeholder", locale))
                        .setMinLength(6)
                        .setMaxLength(6)
                        .build();

                Modal modal = Modal.create("modal-verify:" + event.getUser().getId(), DiscordHelper.text("discord.modal.verify.title", locale))
                        .addComponents(ActionRow.of(input))
                        .build();

                interaction.replyModal(modal).queue();
            });
        }
    }

    public static void onModalInteraction(ModalInteractionEvent event) {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "[Discord] " + event.getUser().getName() + " used modal " + event.getModalId());
        ModalInteraction interaction = event.getInteraction();
        Locale locale = event.getUserLocale().toLocale();

        if (event.getModalId().equals("modal-verify:" + event.getUser().getId())) {
            int accounts = DiscordLink.getLinkedUUIDs(event.getUser().getId()).size();
            if (accounts >= DiscordConfig.MAX_LINKS.get()) {
                interaction.reply(DiscordHelper.text("discord.message.verify.tooMany", locale, accounts)).setEphemeral(true).queue();
                return;
            }

            ModalMapping input = event.getValue("link-key");
            if (input == null) {
                interaction.reply(DiscordHelper.text("discord.message.verify.error", locale)).setEphemeral(true).queue();
                return;
            }
            MinecraftUserInfo info = DiscordLink.checkVerifyCode(input.getAsString().toUpperCase(Locale.ROOT));
            if (info == null) {
                interaction.reply(DiscordHelper.text("discord.message.verify.incorrect", locale)).setEphemeral(true).queue();
                return;
            }
            interaction.reply(DiscordHelper.text("discord.message.verify.success", locale, info.isBedrock() ? DiscordHelper.text("all.minecraft.bedrock", locale) : DiscordHelper.text("all.minecraft.java", locale), info.displayName())).setEphemeral(true).queue();

            DiscordLink.addDiscordID(info.uuid(), event.getUser().getId(), event.getUser().getName());
        }
    }
}
