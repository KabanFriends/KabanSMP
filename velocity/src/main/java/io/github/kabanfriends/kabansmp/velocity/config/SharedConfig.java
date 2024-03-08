package io.github.kabanfriends.kabansmp.velocity.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SharedConfig {

    public static int maxPlayers;

    public static void load() {
        KabanSMPVelocity.getInstance().getLogger().info("Loading shared config");

        File sharedConfig = new File("plugins/KabanSMP/shared.json");
        if (sharedConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(sharedConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPVelocity.getInstance().getLogger().error("Invalid json: " + sharedConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                maxPlayers = json.getAsJsonPrimitive("maxPlayers").getAsInt();
            }
        }
    }
}
