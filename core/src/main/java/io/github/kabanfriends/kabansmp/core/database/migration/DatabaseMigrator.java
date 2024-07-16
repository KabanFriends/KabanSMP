package io.github.kabanfriends.kabansmp.core.database.migration;

import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.database.Database;
import io.github.kabanfriends.kabansmp.core.database.migration.version.DatabaseVersion;
import io.github.kabanfriends.kabansmp.core.database.migration.version.Version1;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DatabaseMigrator {

    private static final int DATABASE_VERSION = 1;

    public static void migrate() {
        Database.connection((connection) -> {
            /*
            Get current database version
            */
            int currentVersion = 0;

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet tableResult = meta.getTables(null, null, "migrator_data", null);

            if (tableResult.next()) { // migrator_data table exists, get current version
                try (PreparedStatement ps = connection.prepareStatement("SELECT version FROM migrator_data")) {
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        currentVersion = rs.getInt("version");
                    }
                }
            } else { // Create a new table and store version
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS migrator_data (version SMALLINT)");
                    try (PreparedStatement insert = connection.prepareStatement("INSERT INTO migrator_data (version) VALUES (?)")) {
                        insert.setInt(1, DATABASE_VERSION);
                        insert.executeUpdate();
                    }
                }
            }

            if (currentVersion >= DATABASE_VERSION) {
                KabanSMP.getInstance().getLogger().log(Level.INFO, "Database version is latest!");
                return;
            }

            /*
            Run migrators until the current version
            */
            Map<Integer, DatabaseVersion> versions = new HashMap<>();
            versions.put(1, new Version1());

            KabanSMP.getInstance().getLogger().log(Level.INFO, "Database version (current: " + currentVersion + ", target: " + DATABASE_VERSION + ")");

            for (; currentVersion++ < DATABASE_VERSION;) {
                DatabaseVersion version = versions.get(currentVersion);
                if (version == null) {
                    continue;
                }
                KabanSMP.getInstance().getLogger().log(Level.INFO, "Updating to database version " + currentVersion);
                try {
                    version.update(connection);
                } catch (SQLException e) {
                    KabanSMP.getInstance().getLogger().log(Level.SEVERE, "Failed to apply database version " + currentVersion);
                    break;
                }
            }
        });
    }
}
