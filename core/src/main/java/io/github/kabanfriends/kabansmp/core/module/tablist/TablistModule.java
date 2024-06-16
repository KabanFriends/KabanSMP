package io.github.kabanfriends.kabansmp.core.module.tablist;

import io.github.kabanfriends.kabansmp.core.config.TablistConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TablistModule extends Module {

    private static final int TABLIST_SEND_INTERVAL = 1;

    private static Economy econ;
    private static Chat chat;

    @Override
    public void onLoad() {
        new TablistConfig().load();

        var econProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (econProvider != null) {
            econ = econProvider.getProvider();
        }

        var chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        BukkitTask tablistTask = new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) sendTablist(player);
            }
        }.runTaskTimer(KabanSMPPlugin.getInstance(), TABLIST_SEND_INTERVAL * 20, TABLIST_SEND_INTERVAL * 20);
    }

    public static void sendTablist(Player player) {
        int players = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!isVanished(p)) players++;
        }

        List<Component> header = new ArrayList<>();

        if (TablistConfig.SHOW_SERVER_NAME.get()) {
            header.add(TablistConfig.SERVER_NAME.get());
            header.add(Component.empty());
        }
        header.add(Components.translatable("tablist.header.players",
                Component.text(players + "/" + Bukkit.getMaxPlayers()).color(ServerColors.WHITE)
        ).color(ServerColors.GRAY_LIGHT));
        if (TablistConfig.SHOW_MONEY.get()) {
            header.add(Components.translatable("tablist.footer.balance",
                    Component.text(econ.format(econ.getBalance(player))).color(ServerColors.WHITE)
            ).color(ServerColors.GRAY_LIGHT));
        }
        header.add(Components.translatable("tablist.footer.coordinate",
                Component.text((int) Math.floor(player.getX()) + ", " + (int) Math.floor(player.getY()) + ", " + (int) Math.floor(player.getZ())).color(ServerColors.WHITE)
        ).color(ServerColors.GRAY_LIGHT));

        // Send tablist header and footer
        player.sendPlayerListHeader(Components.newlined(header));

        if (TablistConfig.CUSTOM_PLAYER_NAME.get()) {
            // Set tablist player name
            String prefix = chat.getGroupPrefix(player.getWorld(), chat.getPrimaryGroup(player));
            prefix = prefix.replaceAll("#([a-fA-F0-9]{6})", "§#$1");
            Component name = Component.empty();
            if (!prefix.isEmpty()) {
                name = Components.legacy(prefix).appendSpace();
            }

            player.playerListName(name.append(player.displayName()));
        }
    }

    private static boolean isVanished(Player player) {
        if (!player.isVisibleByDefault()) {
            return true;
        }

        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
