package io.github.kabanfriends.kabansmp.core.module.discord;

import java.util.UUID;

public record MinecraftUserInfo(UUID uuid, String displayName, boolean isBedrock) {
}
