package io.github.kabanfriends.kabansmp.core.module.join;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.join.event.JoinEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class JoinModule extends Module {

    @Override
    public void onLoad() {
        registerEvents(new JoinEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API,
                PlatformCapability.FLOODGATE
        };
    }
}
