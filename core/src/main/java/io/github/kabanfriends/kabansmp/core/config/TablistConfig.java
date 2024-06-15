package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;
import net.kyori.adventure.text.Component;

public class TablistConfig extends Config {

    public static final ConfigField<Boolean> SHOW_SERVER_NAME = new ConfigField<>("showServerName", JsonCodecs.BOOLEAN, false);
    public static final ConfigField<Boolean> SHOW_MONEY = new ConfigField<>("showMoney", JsonCodecs.BOOLEAN, false);
    public static final ConfigField<Boolean> CUSTOM_PLAYER_NAME = new ConfigField<>("customPlayerName", JsonCodecs.BOOLEAN, false);
    public static final ConfigField<Component> SERVER_NAME = new ConfigField<>("serverName", JsonCodecs.COMPONENT, Component.text("Server Name"));

    public TablistConfig() {
        super("tablist",
                SHOW_SERVER_NAME,
                SHOW_MONEY,
                CUSTOM_PLAYER_NAME,
                SERVER_NAME
        );
    }
}
