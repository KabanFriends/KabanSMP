package io.github.kabanfriends.kabansmp.core.player.data;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.database.Database;
import io.github.kabanfriends.kabansmp.core.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class PlayerData {

    private final UUID uuid;

    private final LoadingCache<DataField<?>, Optional<?>> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public Optional<?> load(DataField<?> key) {
                    return Database.connection((connection) -> {
                        try (PreparedStatement ps = connection.prepareStatement("SELECT value FROM player_data WHERE uuid = ? AND type = ?")) {
                            ps.setString(1, uuid.toString());
                            ps.setString(2, key.id());
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                try {
                                    return makeOptional(key.codec().deserialize(Unpooled.copiedBuffer(rs.getBytes("value"))));
                                } catch (Exception e) {
                                    KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Failed to deserialize player data: " + key.id() + " (" + uuid + ")");
                                    return makeOptional(key.defaultValue());
                                }
                            } else {
                                return makeOptional(key.defaultValue());
                            }
                        }
                    });
                }
            });

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(DataField<T> field) {
        return (T) cache.getUnchecked(field).orElse(null);
    }

    public <T> void setValue(DataField<T> field, T value) {
        cache.put(field, makeOptional(value));

        Database.connection((connection) -> {
            ByteBuf buffer = Unpooled.buffer();
            field.codec().serialize(buffer, value);

            boolean update;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM player_data WHERE uuid = ? AND type = ?")) {
                ps.setString(1, uuid.toString());
                ps.setString(2, field.id());
                ResultSet rs = ps.executeQuery();
                update = rs.next();
            }

            if (update) {
                try (PreparedStatement ps = connection.prepareStatement("UPDATE player_data SET value = ? WHERE uuid = ? AND type = ?")) {
                    ps.setBytes(1, ByteBufUtil.readAllBytes(buffer));
                    ps.setString(2, uuid.toString());
                    ps.setString(3, field.id());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO player_data (uuid, type, value) VALUES (?, ?, ?)")) {
                    ps.setString(1, uuid.toString());
                    ps.setString(2, field.id());
                    ps.setBytes(3, ByteBufUtil.readAllBytes(buffer));
                    ps.executeUpdate();
                }
            }
        });
    }

    private static <T> Optional<T> makeOptional(T value) {
        return value == null ? Optional.empty() : Optional.of(value);
    }
}
