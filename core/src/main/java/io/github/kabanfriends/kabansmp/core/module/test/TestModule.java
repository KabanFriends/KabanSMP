package io.github.kabanfriends.kabansmp.core.module.test;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.test.command.CommandTest;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class TestModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("test", new CommandTest());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API,
                PlatformCapability.FLOODGATE
        };
    }
}
