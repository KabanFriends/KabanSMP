package io.github.kabanfriends.kabansmp.core.module.creative;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.creative.event.CreativeItemEventHandler;

public class CreativeModule implements Module {

    @Override
    public void load() {
        registerEvents(new CreativeItemEventHandler());
    }
}
