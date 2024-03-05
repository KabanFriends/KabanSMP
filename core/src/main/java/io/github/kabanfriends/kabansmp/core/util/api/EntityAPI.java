package io.github.kabanfriends.kabansmp.core.util.api;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;

public class EntityAPI {

    public static void playMobAnimation(org.bukkit.entity.LivingEntity entity, AnimationType animationType) {
        LivingEntity nmsEntity = ((CraftLivingEntity) entity).getHandle();
        Packet<?> nmsPacket = null;

        org.bukkit.entity.EntityType entityType = entity.getType();
        if (animationType.isAttack) { // Some mobs are special
            if (entityType == org.bukkit.entity.EntityType.IRON_GOLEM
                    || entityType == org.bukkit.entity.EntityType.RAVAGER
                    || entityType == org.bukkit.entity.EntityType.HOGLIN
                    || entityType == org.bukkit.entity.EntityType.ZOGLIN) {
                nmsPacket = new ClientboundEntityEventPacket(nmsEntity, (byte) 4);
            }
        }

        if (nmsPacket == null) {
            nmsPacket = new ClientboundAnimatePacket(nmsEntity, animationType.animationId);
        }

        sendPacketToTracking(nmsEntity, nmsPacket);
    }

    private static void sendPacketToTracking(net.minecraft.world.entity.Entity nmsEntity, Packet<?> nmsPacket) {
        ((ServerChunkCache) nmsEntity.level().getChunkSource()).broadcastAndSend(nmsEntity, nmsPacket);
    }

    public enum AnimationType {
        SWING_ARM_MAIN(0, true),
        SWING_ARM_OFFHAND(3, true),
        HURT_ANIMATION(1, false),
        CRIT_PARTICLES(4, false),
        ENCHANTED_HIT_PARTICLES(5, false),
        WAKE_UP(2, false),
        ;

        private final int animationId;
        private final boolean isAttack;

        AnimationType(int animationId, boolean isAttack) {
            this.animationId = animationId;
            this.isAttack = isAttack;
        }
    }
}
