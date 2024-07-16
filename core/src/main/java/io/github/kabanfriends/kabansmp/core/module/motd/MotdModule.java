package io.github.kabanfriends.kabansmp.core.module.motd;

import io.github.kabanfriends.kabansmp.core.config.MotdConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.motd.event.MotdEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class MotdModule extends Module {

    @Override
    public void onLoad() {
        new MotdConfig().load();
        registerEvents(new MotdEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API
        };
    }
}
