package io.github.kabanfriends.kabansmp.core.config;
;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

public class TablistConfig {

    public static boolean showServerName = false;
    public static boolean showMoney = false;
    public static boolean customPlayerName = false;

    public static Component serverName = Component.empty();

    public static void load() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading tablist config");

        File tablistConfig = new File("plugins/KabanSMP/tablist.json");
        if (tablistConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(tablistConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + tablistConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                showServerName = json.getAsJsonPrimitive("showServerName").getAsBoolean();
                showMoney = json.getAsJsonPrimitive("showMoney").getAsBoolean();
                customPlayerName = json.getAsJsonPrimitive("customPlayerName").getAsBoolean();

                if (showServerName) {
                    serverName = MiniMessage.miniMessage().deserialize(json.getAsJsonPrimitive("serverName").getAsString());
                }
            }
        }
    }
}
