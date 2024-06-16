package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;

public class DiscordConfig extends Config {

    public static final ConfigField<String> BOT_TOKEN = new ConfigField<>("botToken", JsonCodecs.STRING, "");
    public static final ConfigField<String> GUILD_ID = new ConfigField<>("guildId", JsonCodecs.STRING, "");
    public static final ConfigField<Integer> MAX_LINKS = new ConfigField<>("maxLinks", JsonCodecs.INTEGER, 1);
    public static final ConfigField<String> HELP_URL = new ConfigField<>("helpUrl", JsonCodecs.STRING, "https://example.com");

    public DiscordConfig() {
        super("discord",
                BOT_TOKEN,
                GUILD_ID,
                MAX_LINKS,
                HELP_URL
        );
    }
}
