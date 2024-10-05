package io.github.kabanfriends.kabansmp.core.module.serverlinks.event;

import io.github.kabanfriends.kabansmp.core.module.serverlinks.ServerLinksManager;
import io.github.kabanfriends.kabansmp.core.util.api.ServerAPI;
import org.bukkit.ServerLinks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLinksSendEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

@SuppressWarnings("UnstableApiUsage")
public class ServerLinkEventHandler implements Listener {

    @EventHandler
    public void onPlayerLinksSend(PlayerLinksSendEvent event) {
        ServerLinksManager.processServerLinks(event.getLinks(), event.getPlayer().locale());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ServerLinks links = ServerAPI.createNewServerLinks();
        ServerLinksManager.processServerLinks(links, event.getPlayer().locale());
        event.getPlayer().sendLinks(links);
    }

    @EventHandler
    public void onPlayerLocaleChange(PlayerLocaleChangeEvent event) {
        ServerLinks links = ServerAPI.createNewServerLinks();
        ServerLinksManager.processServerLinks(links, event.locale());
        event.getPlayer().sendLinks(links);
    }
}
