package io.github.kabanfriends.kabansmp.networking.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ProxyConfig {

    public static boolean useProxy = false;
    public static String redisUrl;

    public static void load() {
        // I know specifying path like this in a common module is not the best way but it'll do for now
        File redisConfig = new File("plugins/KabanSMP/proxy.json");
        if (redisConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(redisConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                System.err.println("Invalid proxy json:");
                e.printStackTrace();
            }

            if (json != null) {
                useProxy = json.getAsJsonPrimitive("useProxy").getAsBoolean();
                if (useProxy) {
                    redisUrl = json.getAsJsonPrimitive("redisUrl").getAsString();
                }
            }
        }
    }

}
