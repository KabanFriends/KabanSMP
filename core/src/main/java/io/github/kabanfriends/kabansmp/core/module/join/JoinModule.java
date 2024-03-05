package io.github.kabanfriends.kabansmp.core.module.join;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.join.event.JoinEventHandler;

public class JoinModule implements Module {

    @Override
    public void load() {
        registerEvents(new JoinEventHandler());
    }
}
