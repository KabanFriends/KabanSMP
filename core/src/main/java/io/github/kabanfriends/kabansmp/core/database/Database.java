package io.github.kabanfriends.kabansmp.core.database;

import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.database.migration.DatabaseMigrator;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Database {

    private static final File DATABASE_FILE = new File("plugins/KabanSMP/server.sqlite");

    private static Connection connection;

    public static void start() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading database");

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "SQLite JDBC driver could not be found");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE.getPath());
        } catch (SQLException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Could not connect to the server database");
            e.printStackTrace();
            return;
        }

        DatabaseMigrator.migrate();
    }

    public static <T> T connection(ReturningCallback<T, Connection, SQLException> callback) {
        if (connection == null) {
            throw new IllegalStateException("Database has not been initialized");
        }

        try {
            return callback.call(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void connection(Callback<Connection, SQLException> callback) {
        connection(result -> {
            callback.call(result);
            return null;
        });
    }

    public static void connectionAsync(Callback<Connection, SQLException> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(KabanSMPPlugin.getInstance(), () -> connection(callback));
    }

    public interface Callback<T, U extends Throwable> {
        void call(T result) throws U;
    }

    public interface ReturningCallback<T, U, V extends Throwable> {
        T call(U result) throws V;
    }
}
