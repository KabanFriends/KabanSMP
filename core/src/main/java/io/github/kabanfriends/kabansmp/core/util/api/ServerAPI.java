package io.github.kabanfriends.kabansmp.core.util.api;

import com.google.common.collect.Lists;
import org.bukkit.ServerLinks;
import org.bukkit.craftbukkit.CraftServerLinks;

public class ServerAPI {

    @SuppressWarnings("UnstableApiUsage")
    public static ServerLinks createNewServerLinks() {
        return new CraftServerLinks(new net.minecraft.server.ServerLinks(Lists.newArrayList()));
    }
}
