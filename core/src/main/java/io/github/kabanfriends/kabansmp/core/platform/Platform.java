package io.github.kabanfriends.kabansmp.core.platform;

import java.util.Collections;
import java.util.Set;

public abstract class Platform {

    private final Set<PlatformCapability> capabilities;

    public Platform(PlatformCapability... capabilities) {
        this.capabilities = Set.of(capabilities);
    }

    public Set<PlatformCapability> getCapabilities() {
        return Collections.unmodifiableSet(capabilities);
    }

    public boolean hasCapability(PlatformCapability capability) {
        return capabilities.contains(capability);
    }
}
