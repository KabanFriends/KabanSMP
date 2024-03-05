package io.github.kabanfriends.kabansmp.core.module.hardcore.command;

import io.github.kabanfriends.kabansmp.core.command.SMPCommand;
import io.github.kabanfriends.kabansmp.core.player.PlayerNames;
import io.github.kabanfriends.kabansmp.core.util.api.PlayerAPI;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerData;
import io.github.kabanfriends.kabansmp.core.player.data.PlayerDataManager;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.text.formatting.Format;
import io.github.kabanfriends.kabansmp.core.text.formatting.ServerColors;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.apache.commons.lang.LocaleUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public class CommandHardcore implements SMPCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        // Clicking the prompt in the book GUI
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("prompt-true")) {
                player.playSound(player, Sound.BLOCK_TRIPWIRE_CLICK_ON, 1.0f, 1.0f);
                acceptPrompt(player);
                return true;
            } else if (args[0].equalsIgnoreCase("prompt-false")) {
                player.playSound(player, Sound.BLOCK_TRIPWIRE_CLICK_ON, 1.0f, 1.0f);
                return true;
            }
        }

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data.hardcoreMode) {
            player.sendMessage(Components.formatted(Format.HARDCORE_NOTIFY, "hardcore.message.alreadyEnabled"));
            return true;
        }
        if (data.deathCount > 0) {
            player.sendMessage(Components.formatted(Format.HARDCORE_FAIL, "hardcore.message.unavailable"));
            return true;
        }

        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            FloodgatePlayer fplayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
            ModalForm form = ModalForm.builder()
                    .translator(CommandHardcore::translateForm, player.locale().getLanguage())
                    .title("hardcore.form.title")
                    .content("hardcore.form.description")
                    .button1("hardcore.form.button.yes")
                    .button2("hardcore.form.button.no")
                    .validResultHandler(response -> {
                       if (response.clickedFirst()) {
                           acceptPrompt(player);
                       }
                    })
                    .build();

            fplayer.sendForm(form);
            return true;
        }

        Component clickToSelect = GlobalTranslator.translator().translate(Components.translatable("all.chat.clickToSelect"), player.locale());
        Component yesButton = Components.translatable("hardcore.form.button.yes").style(Style.style(ServerColors.GREEN_DARK, TextDecoration.BOLD))
                .hoverEvent(HoverEvent.showText(clickToSelect))
                .clickEvent(ClickEvent.runCommand("/kabansmp:hardcore prompt-true"));
        Component noButton = Components.translatable("hardcore.form.button.no").style(Style.style(ServerColors.RED, TextDecoration.BOLD))
                .hoverEvent(HoverEvent.showText(clickToSelect))
                .clickEvent(ClickEvent.runCommand("/kabansmp:hardcore prompt-false"));

        Component content = Components.newlined(
                Components.of(Component.text("❣", ServerColors.RED), Component.space(), Components.translatable("hardcore.form.title").style(Style.style(TextDecoration.UNDERLINED, TextDecoration.BOLD))),
                Component.empty(),
                Components.translatable("hardcore.form.description"),
                Component.empty(),
                Components.translatable("hardcore.form.instruction"),
                Component.empty(),
                Components.of(Component.text("> ", ServerColors.GRAY_DARK), yesButton),
                Components.of(Component.text("> ", ServerColors.GRAY_DARK), noButton)
        );

        Book book = Book.book(Component.empty(), Component.empty(), content);
        player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 2.0f, 1.4f);
        player.openBook(book);
        return true;
    }

    private static String translateForm(String key, String locale) {
        return Components.legacyWithoutHex(GlobalTranslator.translator().translate(Components.translatable(key), LocaleUtils.toLocale(locale)));
    }

    private static void acceptPrompt(Player player) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        data.hardcoreMode = true;
        PlayerNames.updateDisplayName(player);

        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 2.0f, 1.25f);
        player.sendMessage(Components.formatted(Format.HARDCORE_SUCCESS, "hardcore.message.enabled"));

        if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            PlayerAPI.setHardcoreHeart(player, true);
        }
    }
}
