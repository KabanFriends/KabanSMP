package io.github.kabanfriends.kabansmp.core.util;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class AdventureUtil {

    public static void sendMessage(CommandSender sender, Component message) {
        KabanSMP plugin = KabanSMP.getInstance();
        if (plugin.getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            sender.sendMessage(message);
        } else {
            plugin.getAdapter().getAudience(sender).sendMessage(message);
        }
    }

    public static Component getPlayerName(Player player) {
        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            return player.name();
        } else {
            return Component.text(player.getName());
        }
    }

    public static Component getPlayerDisplayName(Player player) {
        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            return player.displayName();
        } else {
            return Component.text(player.getDisplayName());
        }
    }

    public static String toLegacy(Component component) {
        return toLegacy(component, LanguageConfig.DEFAULT_LOCALE.get());
    }

    public static String toLegacy(Component component, String locale) {
        return toLegacy(component, toLocale(locale));
    }

    public static String toLegacy(Component component, Locale locale) {
        return BukkitComponentSerializer.legacy().serialize(GlobalTranslator.render(component, locale));
    }

    private static Locale toLocale(String string) {
        if (string != null) {
            Locale locale = Translator.parseLocale(string);
            if (locale != null) {
                return locale;
            }
        }
        return Locale.US;
    }
}
