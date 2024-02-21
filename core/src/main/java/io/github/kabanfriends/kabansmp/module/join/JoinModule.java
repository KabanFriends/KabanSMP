package io.github.kabanfriends.kabansmp.module.join;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.join.event.JoinEventHandler;

public class JoinModule implements Module {

    @Override
    public void load() {
        registerEvents(new JoinEventHandler());
    }
}
