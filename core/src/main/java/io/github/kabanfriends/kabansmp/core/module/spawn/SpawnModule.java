package io.github.kabanfriends.kabansmp.core.module.spawn;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.spawn.command.CommandSpawn;

public class SpawnModule implements Module {

    @Override
    public void load() {
        registerCommand("spawn", new CommandSpawn());
    }
}
