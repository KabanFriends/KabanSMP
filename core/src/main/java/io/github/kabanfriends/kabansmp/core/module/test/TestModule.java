package io.github.kabanfriends.kabansmp.core.module.test;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.test.command.CommandTest;

public class TestModule implements Module {

    @Override
    public void load() {
        registerCommand("test", new CommandTest());
    }
}
