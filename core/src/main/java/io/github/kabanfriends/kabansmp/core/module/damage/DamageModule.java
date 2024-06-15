package io.github.kabanfriends.kabansmp.core.module.damage;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.damage.event.DamageEventHandler;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DamageModule implements Module {

    public static final Map<Player, Integer> LAST_DAMAGE_TICKS = new ConcurrentHashMap<>();

    @Override
    public void load() {
        registerEvents(new DamageEventHandler());
    }
}
