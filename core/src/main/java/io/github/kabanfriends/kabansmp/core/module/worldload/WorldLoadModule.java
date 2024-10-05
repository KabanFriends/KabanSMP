package io.github.kabanfriends.kabansmp.core.module.worldload;

import io.github.kabanfriends.kabansmp.core.config.WorldLoadConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.worldload.event.WorldLoadEventHandler;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class WorldLoadModule extends Module {

    @Override
    public void onLoad() {
        new WorldLoadConfig().load();

        registerEvents(new WorldLoadEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
