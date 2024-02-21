package io.github.kabanfriends.kabansmp.module.test;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.test.command.CommandTest;

public class TestModule implements Module {

    @Override
    public void load() {
        registerCommand("test", new CommandTest());
    }
}
