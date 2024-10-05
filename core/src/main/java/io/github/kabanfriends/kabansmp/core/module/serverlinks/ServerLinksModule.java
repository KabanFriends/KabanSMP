package io.github.kabanfriends.kabansmp.core.module.serverlinks;

import io.github.kabanfriends.kabansmp.core.config.ServerLinksConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.serverlinks.event.ServerLinkEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class ServerLinksModule extends Module {

    @Override
    public void onLoad() {
        new ServerLinksConfig().load();

        registerEvents(new ServerLinkEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API
        };
    }
}
