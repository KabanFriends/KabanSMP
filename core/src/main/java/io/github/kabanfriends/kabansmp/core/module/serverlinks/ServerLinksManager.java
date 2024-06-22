package io.github.kabanfriends.kabansmp.core.module.serverlinks;

import io.github.kabanfriends.kabansmp.core.config.ServerLinksConfig;
import io.github.kabanfriends.kabansmp.core.text.Components;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.ServerLinks;

import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class ServerLinksManager {

    public static void processServerLinks(ServerLinks links, Locale locale) {
        for (ServerLinkData data : ServerLinksConfig.SERVER_LINKS.get()) {
            links.addLink(GlobalTranslator.render(Components.translatable("serverlinks.link." + data.id() + ".name"), locale), data.uri());
        }
    }
}
