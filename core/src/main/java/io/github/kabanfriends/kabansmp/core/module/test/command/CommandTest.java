package io.github.kabanfriends.kabansmp.core.module.test.command;

import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.Locale;

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

        return switch (id) {
            default -> true;
            case "form" -> formTest(player, args);
        };
    }

    private static boolean formTest(Player player, String[] args) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            player.sendMessage("Not a bedrock player");
            return true;
        }
        FloodgatePlayer fplayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());

        if (args.length <= 1) {
            player.sendMessage("form types: modal, simple");
            return true;
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

        return true;
    }
}
