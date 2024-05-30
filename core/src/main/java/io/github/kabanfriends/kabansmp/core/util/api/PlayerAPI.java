package io.github.kabanfriends.kabansmp.core.util.api;

import io.github.kabanfriends.kabansmp.injector.api.ChatMixinAPI;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.function.Consumer;

public class PlayerAPI {

    public static void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    private static void sendEntity(org.bukkit.entity.Entity entity, Player dummy, Consumer<Packet<ClientGamePacketListener>> packetConsumer) {
        ServerLevel level = ((CraftWorld) entity.getWorld()).getHandle();

        ChunkMap.TrackedEntity entityTracker = level.getChunkSource().chunkMap.entityMap.get(entity.getEntityId());
        if (entityTracker == null) {
            return;
        }

        // Use a dummy player. This seems to only be used
        // for updating the tracker's attributes. Unless the entity they are
        // trying to send isn't themselves it should appear accurate.
        ServerPlayer dummyPlayer = ((CraftPlayer) dummy).getHandle();

        // Populate packet list
        entityTracker.serverEntity.sendPairingData(dummyPlayer, packetConsumer);
    }


    public static int getPermissionLevel(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        return MinecraftServer.getServer().getProfilePermissions(nmsPlayer.gameProfile);
    }

    public static void resendSelf(Player player) {
        // See: CraftPlayer#refreshPlayer()
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        ServerLevel level = ((CraftWorld) player.getWorld()).getHandle();

        Location location = player.getLocation();

        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(nmsPlayer.createCommonSpawnInfo(level), ClientboundRespawnPacket.KEEP_ALL_DATA);
        sendPacket(respawn, player);

        nmsPlayer.onUpdateAbilities();

        ClientboundPlayerPositionPacket position = new ClientboundPlayerPositionPacket(location.getX(), location.getY(), location.getZ(), nmsPlayer.yHeadRot, location.getPitch(), Collections.emptySet(), 0);
        sendPacket(position, player);

        player.sendOpLevel((byte) getPermissionLevel(player));

        PlayerList playerList = level.getServer().getPlayerList();
        playerList.sendLevelInfo(nmsPlayer, level);
        playerList.sendAllPlayerInfo(nmsPlayer);

        // Resend their XP and effects because the respawn packet resets it
        sendPacket(new ClientboundSetExperiencePacket(nmsPlayer.experienceProgress, nmsPlayer.totalExperience, nmsPlayer.experienceLevel), player);
        for (net.minecraft.world.effect.MobEffectInstance mobEffect : nmsPlayer.getActiveEffects()) {
            sendPacket(new net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket(nmsPlayer.getId(), mobEffect, false), player);
        }

        sendEntity(player, player, (packet) -> {
            if (packet instanceof ClientboundAddEntityPacket) { // Don't resend player
                return;
            }
            sendPacket(packet, player);
        });

        nmsPlayer.resetSentInfo();
    }

    public static void setHardcoreHeart(Player player, boolean hardcore) {
        setHardcoreHeart(player, hardcore, false);
    }

    public static void setHardcoreHeart(Player player, boolean hardcore, boolean isRespawning) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        ServerLevel level = ((CraftWorld) player.getWorld()).getHandle();

        ClientboundLoginPacket packet = new ClientboundLoginPacket(
                nmsPlayer.getId(),
                hardcore,
                MinecraftServer.getServer().levelKeys(),
                MinecraftServer.getServer().getPlayerList().getMaxPlayers(),
                player.getWorld().getSendViewDistance(),
                player.getWorld().getSimulationDistance(),
                player.getWorld().getGameRuleValue(GameRule.REDUCED_DEBUG_INFO),
                player.getWorld().getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN),
                player.getWorld().getGameRuleValue(GameRule.DO_LIMITED_CRAFTING),
                nmsPlayer.createCommonSpawnInfo(level),
                nmsPlayer.getServer().enforceSecureProfile()
        );

        ChatMixinAPI.allowNextChatSessionUpdate(player);

        sendPacket(packet, player);

        if (!isRespawning) {
            resendSelf(player);
            level.playerChunkLoader.removePlayer(nmsPlayer);
            level.playerChunkLoader.addPlayer(nmsPlayer);
        }

        PlayerList playerList = level.getServer().getPlayerList();
        playerList.updateEntireScoreboard(level.getScoreboard(), nmsPlayer);
    }
}
