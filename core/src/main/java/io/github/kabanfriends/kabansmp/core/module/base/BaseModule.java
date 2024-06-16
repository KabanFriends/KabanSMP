package io.github.kabanfriends.kabansmp.core.module.base;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.base.command.CommandReloadLang;

public class BaseModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("reloadlang", new CommandReloadLang());
    }
}
