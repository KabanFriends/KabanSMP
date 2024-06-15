package io.github.kabanfriends.kabansmp.core.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.config.json.JsonProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

public abstract class Config {

    private final ConfigField<?>[] fields;
    private final String name;

    public Config(String name, ConfigField<?>... fields) {
        this.name = name;
        this.fields = fields;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void load() {
        File configFile = new File("plugins/KabanSMP/" + name + ".json");

        if (!configFile.exists()) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Config " + configFile.getName() + " could not be found!");
            return;
        }

        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading config: " + configFile.getName());

        try {
            JsonObject json = JsonParser.parseReader(new FileReader(configFile)).getAsJsonObject();
            for (ConfigField field : fields) {
                if (!json.has(field.id)) {
                    continue;
                }
                try {
                    field.set(field.codec.deserialize(new JsonProperty(json, field.id)));
                } catch (Exception e) {
                    KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Failed to parse config entry: " + field.id + " (" + configFile.getName() + ")");
                }
            }
        } catch (FileNotFoundException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Failed to read config " + configFile.getName());
            e.printStackTrace();
        } catch (JsonParseException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Invalid config json: " + configFile.getName());
            e.printStackTrace();
        }
    }
}
