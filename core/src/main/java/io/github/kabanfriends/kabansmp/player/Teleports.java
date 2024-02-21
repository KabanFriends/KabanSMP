package io.github.kabanfriends.kabansmp.player;

import io.github.kabanfriends.kabansmp.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.text.Components;
import io.github.kabanfriends.kabansmp.text.formatting.Format;
import io.github.kabanfriends.kabansmp.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.util.api.EntityAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class Teleports {

    private static final int ALLOW_TELEPORT_TICKS = 10;

    public static boolean canTeleport(Player player) {
        if (!DamageModule.LAST_DAMAGE_TICKS.containsKey(player)) {
            return true;
        }
        return Bukkit.getCurrentTick() - DamageModule.LAST_DAMAGE_TICKS.get(player) > ALLOW_TELEPORT_TICKS * 20;
    }

    public static void teleport(Player player, Location location, boolean force, Runnable after) {
        if (!force && !canTeleport(player)) {
            player.sendMessage(Components.formatted(
                    Format.GENERIC_FAIL,
                    "all.teleport.cooldown",
                    Components.translatable("all.time.seconds", ALLOW_TELEPORT_TICKS).color(ServerColors.MUSTARD)
            ));
            return;
        }

        if (!force && !player.isOnGround()) {
            player.sendMessage(Components.formatted(Format.GENERIC_FAIL, "all.teleport.notStanding"));
            return;
        }

        player.teleportAsync(location).thenAccept(result -> {
            EntityAPI.playMobAnimation(player, EntityAPI.AnimationType.WAKE_UP);
            player.playSound(player, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 2.0f, 1.4f);
            if (result) {
                after.run();
            }
        });
    }
}
