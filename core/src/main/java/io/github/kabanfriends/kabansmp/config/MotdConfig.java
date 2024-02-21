package io.github.kabanfriends.kabansmp.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.KabanSMPPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MotdConfig {

    public static final List<String> MOTD_LINES = new ArrayList<>();
    public static final List<String> RANDOM_MESSAGES = new ArrayList<>();


    public static void load() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading MOTD config");

        File motdConfig = new File("plugins/KabanSMP/motd.json");
        if (motdConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(motdConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + motdConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                for (JsonElement element : json.getAsJsonArray("lines")) {
                    MOTD_LINES.add(element.getAsString());
                }
                for (JsonElement element : json.getAsJsonArray("random")) {
                    RANDOM_MESSAGES.add(element.getAsString());
                }
            }
        }
    }
}
