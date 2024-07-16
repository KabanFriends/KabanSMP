package io.github.kabanfriends.kabansmp.core.module.discord;

import com.discordsrv.api.DiscordSRVApi;
import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.DiscordConfig;
import io.github.kabanfriends.kabansmp.core.module.discord.event.DiscordSRVEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.text.language.LanguageManager;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.discord.command.CommandSearchUser;
import io.github.kabanfriends.kabansmp.core.module.discord.event.JDAEventHandler;
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

    private boolean isDiscordSRV = false;

    @Override
    public void onLoad() {
        new DiscordConfig().load();
        KabanSMP.getInstance().getLogger().log(Level.INFO, "[Discord] Starting bot");

        registerCommand("searchuser", new CommandSearchUser());
        registerEvents(new DiscordJoinEventHandler());

        Activity activity = Activity.customStatus(DiscordHelper.text("discord.bot.status"));

        // If DiscordSRV is enabled, use their JDA instance
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("DiscordSRV-Ascension") && DiscordSRVApi.isAvailable()) {
            KabanSMP.getInstance().getLogger().log(Level.INFO, "[Discord] Using DiscordSRV JDA instance");
            DiscordSRVApi.get().eventBus().subscribe(new DiscordSRVEventHandler());
            jda = DiscordSRVApi.get().jda();
            jda.getPresence().setActivity(activity);
            DiscordModule.initializeBot();
            isDiscordSRV = true;
        } else {
            jda = JDABuilder.createLight(DiscordConfig.BOT_TOKEN.get())
                    .addEventListeners(new JDAEventHandler())
                    .setActivity(activity)
                    .build();
        }
    }

    @Override
    public void onClose() {
        KabanSMP.getInstance().getLogger().log(Level.INFO, "Shutting down Discord bot");

        if (!isDiscordSRV) {
            // https://jda.wiki/using-jda/troubleshooting/#illegalstateexception-zip-file-closed
            try {
                jda.shutdown();
                if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
                    jda.shutdownNow();
                    jda.awaitShutdown();
                }
            } catch (Exception ignored) {}
        }
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API
        };
    }

    public static void initializeBot() {
        if (initialized) {
            return;
        }
        initialized = true;

        Guild guild = jda.getGuildById(DiscordConfig.GUILD_ID.get());

        if (guild == null) {
            KabanSMP.getInstance().getLogger().log(Level.WARNING, "[Discord] Guild " + DiscordConfig.GUILD_ID.get() + " was not found");
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
