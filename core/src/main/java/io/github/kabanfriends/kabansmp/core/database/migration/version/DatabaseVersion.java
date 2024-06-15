package io.github.kabanfriends.kabansmp.core.database.migration.version;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseVersion {

    public abstract void update(Connection connection) throws SQLException;
}
