package io.github.kabanfriends.kabansmp.module.damage;

import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.damage.event.DamageEventHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DamageModule implements Module {

    public static final Map<Player, Integer> LAST_DAMAGE_TICKS = new HashMap<>();

    @Override
    public void load() {
        registerEvents(new DamageEventHandler());
    }
}
