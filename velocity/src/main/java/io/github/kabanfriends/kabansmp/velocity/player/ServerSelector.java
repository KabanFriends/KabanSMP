package io.github.kabanfriends.kabansmp.velocity.player;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.simplix.protocolize.api.ClickType;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.inventory.InventoryType;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;
import io.github.kabanfriends.kabansmp.velocity.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.velocity.networking.Server;
import io.github.kabanfriends.kabansmp.velocity.text.Components;
import io.github.kabanfriends.kabansmp.velocity.text.formatting.ServerColors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.apache.commons.lang.LocaleUtils;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ServerSelector {

    public static void openGui(Player player) {
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            openBedrockGui(player);
            return;
        }

        openJavaGui(player);
    }

    private static void openJavaGui(Player player) {
        Locale locale = player.getEffectiveLocale();

        Inventory inventory = new Inventory(InventoryType.HOPPER);
        inventory.title(ChatElement.of(Components.translate(Components.translatable("selector.form.title"), locale)));

        for (Server server : Server.values()) {
            Optional<RegisteredServer> optional = KabanSMPVelocity.getInstance().getServer().getServer(server.getId());
            if (optional.isEmpty()) {
                continue;
            }
            RegisteredServer rs = optional.get();

            ItemStack item = new ItemStack(server.getMaterial());
            item.displayName(ChatElement.of(Components.translate(
                    Components.translatable("all.proxy.server.name." + server.getId()).style(Style.style(server.getColor(), TextDecoration.ITALIC.withState(false)))
            , locale)));
            item.lore(List.of(
                    ChatElement.of(Components.translate(
                            Components.translatable("selector.form.players", Component.text(rs.getPlayersConnected().size()).style(Style.style(ServerColors.WHITE, TextDecoration.ITALIC.withState(false)))).style(Style.style(ServerColors.GRAY_LIGHT, TextDecoration.ITALIC.withState(false)))
                    , locale)),
                    ChatElement.of(Component.empty()),
                    ChatElement.of(Components.translate(
                            Components.of(
                                    Component.text("▶ ").style(Style.style(ServerColors.SKY, TextDecoration.ITALIC.withState(false))),
                                    Components.translatable("selector.form.clickToJoin").style(Style.style(ServerColors.WHITE, TextDecoration.ITALIC.withState(false)))
                            )
                    , locale))
            ));

            inventory.item(server.getSlot(), item);
        }

        inventory.onClick(click -> {
            ClickType type = click.clickType();
            if (type == ClickType.LEFT_CLICK || type == ClickType.RIGHT_CLICK ||
                    type == ClickType.SHIFT_LEFT_CLICK || type == ClickType.SHIFT_RIGHT_CLICK) {
                for (Server server : Server.values()) {
                    if (click.slot() == server.getSlot()) {
                        player.playSound(Sound.sound(Key.key("minecraft", "block.tripwire.click.on"), Sound.Source.MASTER, 1.0f, 1.0f));
                        acceptPrompt(player, server);
                        break;
                    }
                }
            }
        });

        ProtocolizePlayer pp = Protocolize.playerProvider().player(player.getUniqueId());
        pp.openInventory(inventory);
    }

    private static void openBedrockGui(Player player) {
        FloodgatePlayer fplayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
        Locale locale = player.getEffectiveLocale();
        if (locale == null) {
            locale = LanguageConfig.defaultLocale;
        }

        SimpleForm.Builder form = SimpleForm.builder()
                .translator(ServerSelector::translateForm, locale.getLanguage())
                .title("selector.form.title")
                .validResultHandler(response -> {
                    Server server = Server.values()[response.clickedButtonId()];
                    acceptPrompt(player, server);
                });

        for (Server server : Server.values()) {
            form.button("all.proxy.server.name." + server.getId(), FormImage.Type.URL, server.getImageUrl());
        }

        fplayer.sendForm(form.build());
    }

    private static String translateForm(String key, String locale) {
        return Components.legacyWithoutHex(GlobalTranslator.translator().translate(Components.translatable(key), LocaleUtils.toLocale(locale)));
    }

    private static void acceptPrompt(Player player, Server target) {
        Optional<RegisteredServer> optional = KabanSMPVelocity.getInstance().getServer().getServer(target.getId());
        if (optional.isEmpty()) {
            return;
        }
        RegisteredServer server = optional.get();

        player.createConnectionRequest(server).fireAndForget();
    }
}
