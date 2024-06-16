package io.github.kabanfriends.kabansmp.core.module.join;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.join.event.JoinEventHandler;

public class JoinModule extends Module {

    @Override
    public void onLoad() {
        registerEvents(new JoinEventHandler());
    }
}
