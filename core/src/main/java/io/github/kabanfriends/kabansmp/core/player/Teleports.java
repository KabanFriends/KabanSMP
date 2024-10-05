package io.github.kabanfriends.kabansmp.core.player;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.module.damage.DamageModule;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.util.AdventureUtil;
import io.github.kabanfriends.kabansmp.core.util.api.EntityAPI;
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
        int currentTick = KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API) ? Bukkit.getCurrentTick() : KabanSMP.getInstance().getAdapter().getCurrentTick();
        return currentTick - DamageModule.LAST_DAMAGE_TICKS.get(player) > ALLOW_TELEPORT_TICKS * 20;
    }

    public static boolean checkAndNotifyTeleport(Player player) {
        if (!canTeleport(player)) {
            AdventureUtil.sendMessage(player, Components.formatted(
                    Format.GENERIC_FAIL,
                    "all.teleport.cooldown",
                    Components.translatable("all.time.seconds", ALLOW_TELEPORT_TICKS).color(ServerColors.MUSTARD)
            ));
            return false;
        }

        if (!player.isOnGround()) {
            AdventureUtil.sendMessage(player, Components.formatted(Format.GENERIC_FAIL, "all.teleport.notStanding"));
            return false;
        }
        return true;
    }

    public static void teleport(Player player, Location location, boolean force, Runnable after) {
        if (!force && !checkAndNotifyTeleport(player)) {
            return;
        }

        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            player.teleportAsync(location).thenAccept(result -> afterTeleport(player, result, after));
        } else {
            player.teleport(location);
            afterTeleport(player, true, after);
        }
    }

    private static void afterTeleport(Player player, boolean result, Runnable after) {
        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            EntityAPI.playMobAnimation(player, EntityAPI.AnimationType.WAKE_UP);
        }
        player.playSound(player, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 2.0f, 1.4f);
        if (result) {
            after.run();
        }
    }
}
