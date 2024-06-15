package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;

public class SharedConfig extends Config {

    public static final ConfigField<String[]> MODULES = new ConfigField<>("modules", JsonCodecs.STRING_ARRAY, new String[0]);
    public static final ConfigField<String> WEBSITE_URL = new ConfigField<>("websiteUrl", JsonCodecs.STRING, "https://example.com");

    public SharedConfig() {
        super("shared",
                MODULES,
                WEBSITE_URL
        );
    }
}
