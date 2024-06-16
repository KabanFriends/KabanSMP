package io.github.kabanfriends.kabansmp.core.module.discord;

import com.discordsrv.api.DiscordSRVApi;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.config.DiscordConfig;
import io.github.kabanfriends.kabansmp.core.language.LanguageManager;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.discord.command.CommandSearchUser;
import io.github.kabanfriends.kabansmp.core.module.discord.event.DiscordBotEventListener;
import io.github.kabanfriends.kabansmp.core.module.discord.event.DiscordJoinEventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.util.Locale;
import java.util.logging.Level;

public class DiscordModule extends Module {

    private static JDA jda;
    private static boolean initialized;

    @Override
    public void onLoad() {
        new DiscordConfig().load();
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "[Discord] Starting bot");

        registerCommand("searchuser", new CommandSearchUser());
        registerEvents(new DiscordJoinEventHandler());

        Activity activity = Activity.customStatus(DiscordHelper.text("discord.bot.status"));

        // If DiscordSRV is enabled, use their JDA instance
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DiscordSRV-Ascension") && DiscordSRVApi.isAvailable()) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "[Discord] Using DiscordSRV JDA instance");
            jda = DiscordSRVApi.get().jda();
            jda.addEventListener(new DiscordBotEventListener());
            jda.getPresence().setActivity(activity);
            DiscordModule.initializeBot();
        } else {
            jda = JDABuilder.createLight(DiscordConfig.BOT_TOKEN.get())
                    .addEventListeners(new DiscordBotEventListener())
                    .setActivity(activity)
                    .build();
        }
    }

    @Override
    public void onClose() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Shutting down Discord bot");

        // https://jda.wiki/using-jda/troubleshooting/#illegalstateexception-zip-file-closed
        try {
            jda.shutdown();
            if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
                jda.shutdownNow();
                jda.awaitShutdown();
            }
        } catch (Exception ignored) {}
    }

    public static void initializeBot() {
        if (initialized) {
            return;
        }
        initialized = true;

        Guild guild = jda.getGuildById(DiscordConfig.GUILD_ID.get());

        if (guild == null) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "[Discord] Guild " + DiscordConfig.GUILD_ID.get() + " was not found");
            return;
        }

        SlashCommandData command = Commands.slash("verify", DiscordHelper.text("discord.command.verify.description"));
        for (Locale locale : LanguageManager.getTranslator().getLocales()) {
            DiscordLocale dLocale = DiscordLocale.from(DiscordHelper.text("metadata.language.tag", locale));
            if (dLocale == DiscordLocale.UNKNOWN) {
                continue;
            }
            command.setDescriptionLocalization(dLocale, DiscordHelper.text("discord.command.verify.description", locale));
        }

        guild.updateCommands()
                .addCommands(command)
                .queue();
    }
}
