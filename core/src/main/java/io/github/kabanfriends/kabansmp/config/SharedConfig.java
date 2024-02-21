package io.github.kabanfriends.kabansmp.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.KabanSMPPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

public class SharedConfig {

    public static String websiteUrl;

    public static void load() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading shared config");

        File sharedConfig = new File("plugins/KabanSMP/shared.json");
        if (sharedConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(sharedConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + sharedConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                websiteUrl = json.getAsJsonPrimitive("websiteUrl").getAsString();
            }
        }
    }
}
