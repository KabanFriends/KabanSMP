package io.github.kabanfriends.kabansmp.bukkit;

import io.github.kabanfriends.kabansmp.core.platform.Platform;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class BukkitPlatform extends Platform {

    public BukkitPlatform() {
        super(
                PlatformCapability.BUKKIT_API
        );
    }
}
