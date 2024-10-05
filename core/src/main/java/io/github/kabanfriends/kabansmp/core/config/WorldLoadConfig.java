package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;

public class WorldLoadConfig extends Config {

    public static final ConfigField<String[]> WORLDS_TO_LOAD = new ConfigField<>("worldsToLoad", JsonCodecs.STRING_ARRAY, new String[] {"world"});

    public WorldLoadConfig() {
        super("worldload", WORLDS_TO_LOAD);
    }
}
