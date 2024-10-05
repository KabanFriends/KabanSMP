package io.github.kabanfriends.kabansmp.core.module.collision;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.module.collision.event.CollisionEventHandler;
import net.kyori.adventure.text.Component;

public class CollisionModule extends Module {

    @Override
    public void onLoad() {
        registerEvents(new CollisionEventHandler());
    }

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
