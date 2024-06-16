package io.github.kabanfriends.kabansmp.core.module.spawn;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.spawn.command.CommandSpawn;

public class SpawnModule extends Module {

    @Override
    public void onLoad() {
        registerCommand("spawn", new CommandSpawn());
    }
}
