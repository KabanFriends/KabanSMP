package io.github.kabanfriends.kabansmp.core.module.discord;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.database.Database;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DiscordLink {

    private static final Random RANDOM = new Random();

    private static final int VERIFY_CODE_LENGTH = 6;
    private static final String VERIFY_CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static final Cache<String, MinecraftUserInfo> VERIFY_CODES = CacheBuilder.newBuilder()
            .expireAfterAccess(10L, TimeUnit.MINUTES)
            .build();

    public static String startVerification(MinecraftUserInfo info) {
        // Check for existing key
        for (Map.Entry<String, MinecraftUserInfo> entry : VERIFY_CODES.asMap().entrySet()) {
            if (entry.getValue().equals(info)) {
                return entry.getKey();
            }
        }

        // Create a new key
        String verifyCode = createVerifyCode();
        VERIFY_CODES.put(verifyCode, info);

        KabanSMP.getInstance().getLogger().log(Level.INFO, "Started verification for " + info.displayName() + " (UUID: " + info.uuid() + " Code: " + verifyCode + ")");
        return verifyCode;
    }

    public static @Nullable MinecraftUserInfo checkVerifyCode(String code) {
        MinecraftUserInfo info = VERIFY_CODES.getIfPresent(code);
        if (info != null) {
            VERIFY_CODES.invalidate(code);
        }
        return info;
    }

    public static Set<UUID> getLinkedUUIDs(String discordId) {
        Set<UUID> set = new HashSet<>();
        return Database.connection((connection) -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT uuid FROM discord_link WHERE discord_id = ?")) {
                ps.setString(1, discordId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    set.add(UUID.fromString(rs.getString("uuid")));
                }
                return set;
            }
        });
    }

    public static @Nullable DiscordUserInfo getLinkedDiscordUser(UUID uuid) {
        return Database.connection((connection) -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT discord_id, discord_name FROM discord_link WHERE uuid = ?")) {
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new DiscordUserInfo(rs.getString("discord_id"), rs.getString("discord_name"));
                }
                return null;
            }
        });
    }

    public static @Nullable DiscordUserInfo getDiscordUserFromID(String discordId) {
        return Database.connection((connection) -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT discord_name FROM discord_link WHERE discord_id = ?")) {
                ps.setString(1, discordId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new DiscordUserInfo(discordId, rs.getString("discord_name"));
                }
                return null;
            }
        });
    }

    public static void addDiscordID(UUID uuid, String discordId, String discordName) {
        Database.connection((connection) -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO discord_link (discord_id, discord_name, uuid) VALUES (?, ?, ?)")) {
                ps.setString(1, discordId);
                ps.setString(2, discordName);
                ps.setString(3, uuid.toString());
                ps.executeUpdate();
            }
        });
    }

    private static String createVerifyCode() {
        StringBuilder code;

        do {
            code = new StringBuilder();

            for (int i = 0; i < VERIFY_CODE_LENGTH; i++) {
                code.append(VERIFY_CODE_CHARS.charAt(RANDOM.nextInt(VERIFY_CODE_CHARS.length())));
            }
        } while (VERIFY_CODES.getIfPresent(code.toString()) != null);

        return code.toString();
    }

}
