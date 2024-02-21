package io.github.kabanfriends.kabansmp.module.home;

import org.bukkit.Location;

import java.util.UUID;

public record Home(UUID owner, Location location) {
}
