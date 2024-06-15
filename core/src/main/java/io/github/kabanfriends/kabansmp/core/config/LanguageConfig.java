package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;

import java.util.Locale;

public class LanguageConfig extends Config {

    public static final ConfigField<Locale> DEFAULT_LOCALE = new ConfigField<>("defaultLocale", JsonCodecs.LOCALE, Locale.US);

    public LanguageConfig() {
        super("lang", DEFAULT_LOCALE);
    }
}
