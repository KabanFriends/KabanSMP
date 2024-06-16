package io.github.kabanfriends.kabansmp.core.module.test;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.test.command.CommandTest;

public class TestModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("test", new CommandTest());
    }
}
