package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.impl.JsonCodecs;
import io.github.kabanfriends.kabansmp.core.module.serverlinks.ServerLinkData;

import java.net.URI;

public class ServerLinksConfig extends Config {

    public static final ConfigField<ServerLinkData[]> SERVER_LINKS = new ConfigField<>("links", JsonCodecs.SERVER_LINK_ARRAY,
            new ServerLinkData[] {
                    new ServerLinkData("example", URI.create("https://example.com/"))
            }
    );

    public ServerLinksConfig() {
        super("serverlinks",
                SERVER_LINKS
        );
    }
}
