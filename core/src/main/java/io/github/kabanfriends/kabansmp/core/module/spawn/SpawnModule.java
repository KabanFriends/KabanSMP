package io.github.kabanfriends.kabansmp.core.module.spawn;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.spawn.command.CommandSpawn;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class SpawnModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("spawn", new CommandSpawn());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
