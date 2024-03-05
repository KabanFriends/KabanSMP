package io.github.kabanfriends.kabansmp.core.module.base;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.base.command.CommandReloadLang;

public class BaseModule implements Module {

    @Override
    public void load() {
        registerCommand("reloadlang", new CommandReloadLang());
    }
}
