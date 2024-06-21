package io.github.kabanfriends.kabansmp.core.module.test.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.module.hardcore.HardcoreModule;
import io.github.kabanfriends.kabansmp.core.module.home.HomeModule;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.UUID;

public class CommandTest implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("No args specified");
            return true;
        }
        String id = args[0].toLowerCase(Locale.ROOT);

        switch (id) {
            case "form" -> formTest(player, args);
            case "migrate" -> migrateOldData(player, args);
        }

        return true;
    }

    private static void formTest(Player player, String[] args) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            player.sendMessage("Not a bedrock player");
            return;
        }
        FloodgatePlayer fplayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());

        if (args.length <= 1) {
            player.sendMessage("form types: modal, simple");
            return;
        }

        if (args[1].equalsIgnoreCase("modal")) {
            ModalForm form = ModalForm.builder()
                    .title("Form Test")
                    .content("Hello world! §aColor code")
                    .button1("OK")
                    .button2("No")
                    .build();

            fplayer.sendForm(form);
        } else if (args[1].equalsIgnoreCase("simple")) {
            SimpleForm form = SimpleForm.builder()
                    .title("Form Test")
                    .content("Content ABC\nNewline??\n§gCOLOR CODE")
                    .button("press this button", FormImage.Type.PATH, "textures/i/glyph_world_template.png")
                    .build();

            fplayer.sendForm(form);
        }

        return;
    }

    private static String totallyRandomText = null;

    private static void migrateOldData(Player player, String[] args) {
        if (totallyRandomText == null) {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!\"#$%&'()=~|;:-<>?_";
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(chars.charAt((int) (Math.random() * chars.length())));
            }
            totallyRandomText = builder.toString();

            player.sendMessage(ChatColor.RED + "Running this command WILL destroy ALL existing player data stored in the database.");
            player.sendMessage(ChatColor.RED + "Are you sure you really want to execute this command?");
            player.sendMessage(ChatColor.RED + "If you are absolutely sure, type: " + ChatColor.GOLD + "/test migrate " + totallyRandomText);
            return;
        }

        String code = args[1];
        if (!code.equals(totallyRandomText)) {
            player.sendMessage(ChatColor.RED + "Wrong code!");
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "Migrating player data...");

        File dataDir = new File("plugins/KabanSMP/data");
        File homeDir = new File("plugins/KabanSMP/homes");

        for (File file : dataDir.listFiles()) {
            if (file.getName().endsWith(".json")) {
                String uuidStr = FilenameUtils.removeExtension(file.getName());
                UUID uuid = UUID.fromString(uuidStr);
                PlayerData data = PlayerDataManager.getPlayerData(uuid);

                try {
                    JsonObject json = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
                    data.setValue(HardcoreModule.HARDCORE_MODE_DATA, json.get("hardcoreMode").getAsBoolean());
                    data.setValue(HardcoreModule.DEATH_COUNT_DATA, json.get("deathCount").getAsInt());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (Bukkit.getPlayer(uuid) == null) {
                    PlayerDataManager.unloadPlayerData(uuid);
                }
            }
        }

        player.sendMessage(ChatColor.GREEN + "Player data migrated!");
        player.sendMessage(ChatColor.YELLOW + "Migrating player homes...");

        for (File file : homeDir.listFiles()) {
            if (file.getName().endsWith(".yml")) {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                String uuidStr = yaml.getString("owner");
                UUID uuid = UUID.fromString(uuidStr);
                PlayerData data = PlayerDataManager.getPlayerData(uuid);

                data.setValue(HomeModule.HOME_LOCATION_DATA, yaml.getLocation("location"));

                if (Bukkit.getPlayer(uuid) == null) {
                    PlayerDataManager.unloadPlayerData(uuid);
                }
            }
        }

        player.sendMessage(ChatColor.GREEN + "Player homes migrated!");
    }
}
