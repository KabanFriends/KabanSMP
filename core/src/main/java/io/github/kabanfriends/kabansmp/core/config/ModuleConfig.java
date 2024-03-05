package io.github.kabanfriends.kabansmp.core.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class ModuleConfig {

    public static final Set<String> MODULES = new HashSet<>();

    public static void load() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading module config");

        File moduleConfig = new File("plugins/KabanSMP/modules.json");
        if (moduleConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(moduleConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + moduleConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                for (JsonElement jsonElement : json.getAsJsonArray("modules")) {
                    MODULES.add(jsonElement.getAsString());
                }
            }
        }
    }
}
