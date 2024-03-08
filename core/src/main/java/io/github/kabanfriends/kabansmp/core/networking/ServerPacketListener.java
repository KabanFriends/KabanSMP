package io.github.kabanfriends.kabansmp.core.networking;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.module.tablist.TablistModule;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.networking.packet.PacketListener;
import io.github.kabanfriends.kabansmp.networking.packet.impl.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ServerPacketListener implements PacketListener {

    @Override
    public void handleTestPacket(TestPacket packet) {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Received TestPacket! Data = " + packet.getValue());
    }

    @Override
    public void handlePlayerJoinPacket(PlayerJoinPacket packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Components.formatted(
                    Format.JOIN_MESSAGE,
                    "join.proxy.message.joined",
                    Component.text(packet.getPlayerName()).colorIfAbsent(ServerColors.GREEN_LIGHT),
                    Components.translatable("all.proxy.server.name." + packet.getTargetServer()).colorIfAbsent(ServerColors.MUSTARD),
                    packet.isBedrock() ? Components.translatable("all.minecraft.bedrock") : Components.translatable("all.minecraft.java")
            ));
        }
    }

    @Override
    public void handlePlayerQuitPacket(PlayerQuitPacket packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Components.formatted(
                    Format.QUIT_MESSAGE,
                    "join.proxy.message.left",
                    Component.text(packet.getPlayerName()).colorIfAbsent(ServerColors.RED_LIGHT)
            ));
        }
    }

    @Override
    public void handlePlayerChangeServerPacket(PlayerChangeServerPacket packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Components.formatted(
                    Format.CHANGE_MESSAGE,
                    "join.proxy.message.changed",
                    Component.text(packet.getPlayerName()).colorIfAbsent(ServerColors.SKY_LIGHT),
                    Components.translatable("all.proxy.server.name." + packet.getTargetServer()).colorIfAbsent(ServerColors.MUSTARD)
            ));
        }
    }

    @Override
    public void handleProxyStatusPacket(ProxyStatusPacket packet) {
        TablistModule.proxyPlayers = packet.getPlayerCount();
        TablistModule.proxyMaxPlayers = packet.getMaxPlayerCount();
    }
}
