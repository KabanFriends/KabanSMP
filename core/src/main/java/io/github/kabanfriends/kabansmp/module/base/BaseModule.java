package io.github.kabanfriends.kabansmp.module.base;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.base.command.CommandReloadLang;

public class BaseModule implements Module {

    @Override
    public void load() {
        registerCommand("reloadlang", new CommandReloadLang());
    }
}
