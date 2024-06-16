package io.github.kabanfriends.kabansmp.core.module.motd;

import io.github.kabanfriends.kabansmp.core.config.MotdConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.motd.event.MotdEventHandler;

public class MotdModule extends Module {

    @Override
    public void onLoad() {
        new MotdConfig().load();
        registerEvents(new MotdEventHandler());
    }
}
