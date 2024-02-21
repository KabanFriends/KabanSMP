package io.github.kabanfriends.kabansmp.player.data;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import io.github.kabanfriends.kabansmp.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.util.JsonUtil;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PlayerDataManager {

    private static Map<Player, PlayerData> dataMap = new HashMap<>();

    public static void init() {
        File dataDir = new File("plugins/KabanSMP/data");

        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public static void loadPlayer(Player player) {
        if (dataMap.containsKey(player)) {
            return;
        }

        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading player data for " + player.getName());

        PlayerData data = null;
        File dataFile = new File("plugins/KabanSMP/data/" + player.getUniqueId() + ".json");
        if (dataFile.exists()) {
            try {
                JsonObject json = JsonParser.parseReader(new FileReader(dataFile)).getAsJsonObject();
                data = read(json);
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + dataFile.getName());
                e.printStackTrace();
                data = new PlayerData();
            }
        } else {
            data = new PlayerData();
        }

        dataMap.put(player, data);
    }

    public static void savePlayer(Player player) {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Saving player data for " + player.getName());

        File dataFile = new File("plugins/KabanSMP/data/" + player.getUniqueId() + ".json");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while creating player data file for: " + player.getName());
                e.printStackTrace();
            }
        }

        JsonObject json = new JsonObject();
        write(json, dataMap.get(player));

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (JsonWriter writer = gson.newJsonWriter(new OutputStreamWriter(new FileOutputStream(dataFile, false), StandardCharsets.UTF_8))) {
            writer.setIndent("\t");
            gson.toJson(json, writer);
            writer.flush();
        } catch (IOException e) {
            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while saving player data for: " + player.getName());
            e.printStackTrace();
        }
    }

    public static void unloadPlayer(Player player) {
        dataMap.remove(player);
    }

    public static PlayerData getPlayerData(Player player) {
        return dataMap.get(player);
    }

    private static PlayerData read(JsonObject json) {
        PlayerData data = new PlayerData();
        data.hasJoined = JsonUtil.getOrDefault(json, "hasJoined", false, boolean.class);
        data.hardcoreMode = JsonUtil.getOrDefault(json, "hardcoreMode", false, boolean.class);
        data.deathCount = JsonUtil.getOrDefault(json, "deathCount", 0, int.class);
        data.nickname = JsonUtil.getOrDefault(json, "nickname", "", String.class);
        return data;
    }

    private static void write(JsonObject json, PlayerData data) {
        json.addProperty("hasJoined", data.hasJoined);
        json.addProperty("hardcoreMode", data.hardcoreMode);
        json.addProperty("deathCount", data.deathCount);
        json.addProperty("nickname", data.nickname);
    }
}
