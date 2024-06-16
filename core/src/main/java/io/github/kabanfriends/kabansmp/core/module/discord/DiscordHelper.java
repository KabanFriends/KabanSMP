package io.github.kabanfriends.kabansmp.core.module.discord;

import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.text.Components;

import java.util.*;

public class DiscordHelper {

    public static String text(String key, Locale locale, Object... objects) {
        return Components.plain(Components.translate(key, locale, objects));
    }

    public static String text(String key, Object... objects) {
        return text(key, LanguageConfig.DEFAULT_LOCALE.get(), objects);
    }

}
