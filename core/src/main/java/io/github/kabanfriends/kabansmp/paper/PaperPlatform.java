package io.github.kabanfriends.kabansmp.paper;

import io.github.kabanfriends.kabansmp.core.platform.Platform;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;

public class PaperPlatform extends Platform {

    public PaperPlatform() {
        super(
                PlatformCapability.BUKKIT_API,
                PlatformCapability.PAPER_API,
                PlatformCapability.FLOODGATE,
                PlatformCapability.PACKET_EVENTS
        );
    }
}
