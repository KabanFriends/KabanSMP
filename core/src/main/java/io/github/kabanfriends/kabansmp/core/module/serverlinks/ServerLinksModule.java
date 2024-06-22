package io.github.kabanfriends.kabansmp.core.module.serverlinks;

import io.github.kabanfriends.kabansmp.core.config.ServerLinksConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.serverlinks.events.ServerLinkEventHandler;

public class ServerLinksModule extends Module {

    @Override
    public void onLoad() {
        new ServerLinksConfig().load();

        registerEvents(new ServerLinkEventHandler());
    }
}
