package io.github.kabanfriends.kabansmp.core.module.home;

import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.module.home.command.CommandSetHome;
import io.github.kabanfriends.kabansmp.core.module.home.command.CommandHome;
import io.github.kabanfriends.kabansmp.core.player.data.DataField;
import io.github.kabanfriends.kabansmp.core.codec.impl.ByteCodecs;
import org.bukkit.Location;

public class HomeModule implements Module {

    public static final DataField<Location> HOME_LOCATION_DATA = new DataField<>("home_location", ByteCodecs.LOCATION, null);

    @Override
    public void load() {
        registerCommand("sethome", new CommandSetHome());
        registerCommand("home", new CommandHome());
    }
}
