package io.github.kabanfriends.kabansmp.core.module.base;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.base.command.CommandReloadLang;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class BaseModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("reloadlang", new CommandReloadLang());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
