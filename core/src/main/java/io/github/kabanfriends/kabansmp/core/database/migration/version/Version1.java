package io.github.kabanfriends.kabansmp.core.database.migration.version;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Version1 extends DatabaseVersion {

    @Override
    public void update(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE player_data (uuid VARCHAR(64), type VARCHAR(16), value BLOB)");
        }
    }
}
