package io.github.kabanfriends.kabansmp.core.module.creative;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.creative.event.CreativeItemEventHandler;
import io.github.kabanfriends.kabansmp.core.module.creative.event.CreativeWorldEventHandler;
import io.github.kabanfriends.kabansmp.injector.api.MixinConfigAPI;

public class CreativeModule implements Module {

    @Override
    public void load() {
        MixinConfigAPI.filterBlockEntities = true;
        registerEvents(new CreativeItemEventHandler());
        registerEvents(new CreativeWorldEventHandler());
    }
}
