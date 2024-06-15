package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;
import net.kyori.adventure.text.Component;

public class MotdConfig extends Config {

    public static final ConfigField<String[]> LINES = new ConfigField<>("lines", JsonCodecs.STRING_ARRAY, new String[0]);
    public static final ConfigField<Component[]> RANDOM_MESSAGES = new ConfigField<>("random", JsonCodecs.COMPONENT_ARRAY, new Component[0]);

    public MotdConfig() {
        super("motd",
                LINES,
                RANDOM_MESSAGES
        );
    }
}
