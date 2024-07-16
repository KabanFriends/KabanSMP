package io.github.kabanfriends.kabansmp.bukkit;

import io.github.kabanfriends.kabansmp.core.KabanSMP;

public class KabanSMPBukkit extends KabanSMP {

    @Override
    public void onLoad() {
        setPlatform(new BukkitPlatform());
        super.onLoad();
    }
}
