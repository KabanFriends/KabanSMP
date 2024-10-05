package io.github.kabanfriends.kabansmp.core.module.tablist;

import io.github.kabanfriends.kabansmp.core.config.TablistConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.platform.PlatformCapability;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import io.github.kabanfriends.kabansmp.core.util.AdventureUtil;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TablistModule extends Module {

    private static final int TABLIST_SEND_INTERVAL = 1;

    private static Object /*Economy*/ econ;
    private static Object /*Chat*/ chat;

    @Override
    public void onLoad() {
        new TablistConfig().load();

        if (TablistConfig.SHOW_MONEY.get()) {
            var econProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if (econProvider != null) {
                econ = econProvider.getProvider();
            }
        }

        if (TablistConfig.CUSTOM_PLAYER_NAME.get()) {
            var chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
            if (chatProvider != null) {
                chat = chatProvider.getProvider();
            }
        }

        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) sendTablist(player);
            }
        }.runTaskTimer(KabanSMP.getInstance(), TABLIST_SEND_INTERVAL * 20, TABLIST_SEND_INTERVAL * 20);
    }

    public static void sendTablist(Player player) {
        int players = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!isVanished(p)) players++;
        }

        List<Component> headerLines = new ArrayList<>();

        if (TablistConfig.SHOW_SERVER_NAME.get()) {
            headerLines.add(TablistConfig.SERVER_NAME.get());
            headerLines.add(Component.empty());
        }
        headerLines.add(Components.translatable("tablist.header.players",
                Component.text(players + "/" + Bukkit.getMaxPlayers()).color(ServerColors.WHITE)
        ).color(ServerColors.GRAY_LIGHT));
        if (TablistConfig.SHOW_MONEY.get()) {
            headerLines.add(Components.translatable("tablist.footer.balance",
                    Component.text(((Economy) econ).format(((Economy) econ).getBalance(player))).color(ServerColors.WHITE)
            ).color(ServerColors.GRAY_LIGHT));
        }
        Location location = player.getLocation();
        headerLines.add(Components.translatable("tablist.footer.coordinate",
                Component.text((int) Math.floor(location.getX()) + ", " + (int) Math.floor(location.getY()) + ", " + (int) Math.floor(location.getZ())).color(ServerColors.WHITE)
        ).color(ServerColors.GRAY_LIGHT));

        Component header = Components.newlined(headerLines);
        // Send tablist header and footer
        if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
            player.sendPlayerListHeader(header);
        } else {
            player.setPlayerListHeaderFooter(AdventureUtil.toLegacy(header, player.getLocale()), null);
        }

        if (TablistConfig.CUSTOM_PLAYER_NAME.get()) {
            // Set tablist player name
            String prefix = ((Chat) chat).getGroupPrefix(player.getWorld(), ((Chat) chat).getPrimaryGroup(player));
            prefix = prefix.replaceAll("#([a-fA-F0-9]{6})", "§#$1");
            Component name = Component.empty();
            if (!prefix.isEmpty()) {
                name = Components.legacy(prefix).appendSpace();
            }

            Component fullName = name.append(AdventureUtil.getPlayerDisplayName(player));
            if (KabanSMP.getInstance().getPlatform().hasCapability(PlatformCapability.PAPER_API)) {
                player.playerListName(fullName);
            } else {
                player.setPlayerListName(AdventureUtil.toLegacy(fullName));
            }
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

    @Override
    public PlatformCapability[] requiredCapabilities() {
        return new PlatformCapability[] {
                PlatformCapability.BUKKIT_API
        };
    }
}
