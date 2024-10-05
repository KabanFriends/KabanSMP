package io.github.kabanfriends.kabansmp.bukkit;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class KabanSMPBukkit extends KabanSMP {

    public void onLoad() {
        setPlatform(new BukkitPlatform());
        super.onLoad();
    }

    @Override
    public void onEnable() {
        KabanSMP.getInstance().setAdapter(new BukkitAdapter(BukkitAudiences.create(this)));
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        KabanSMP.getInstance().getAdapter().close();
    }
}
