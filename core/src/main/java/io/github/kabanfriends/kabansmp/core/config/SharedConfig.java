package io.github.kabanfriends.kabansmp.core.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.injector.api.MixinConfigAPI;

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
                MixinConfigAPI.unloadPack = json.getAsJsonPrimitive("unloadPack").getAsBoolean();
                boolean creativePatches = json.getAsJsonPrimitive("creativePatches").getAsBoolean();
                if (creativePatches) {
                    MixinConfigAPI.filterBlockEntities = true;
                }
                websiteUrl = json.getAsJsonPrimitive("websiteUrl").getAsString();
            }
        }
    }
}
