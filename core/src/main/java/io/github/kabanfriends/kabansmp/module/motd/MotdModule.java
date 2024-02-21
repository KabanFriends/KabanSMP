package io.github.kabanfriends.kabansmp.module.motd;

import io.github.kabanfriends.kabansmp.config.MotdConfig;
import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.motd.event.MotdEventHandler;

public class MotdModule implements Module {

    @Override
    public void load() {
        MotdConfig.load();

        registerEvents(new MotdEventHandler());
    }
}
