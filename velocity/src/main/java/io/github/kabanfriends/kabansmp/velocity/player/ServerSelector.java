package io.github.kabanfriends.kabansmp.velocity.player;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.simplix.protocolize.api.ClickType;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.Sound;
import dev.simplix.protocolize.data.inventory.InventoryType;
import dev.simplix.protocolize.data.packets.SetSlot;
import io.github.kabanfriends.kabansmp.velocity.KabanSMPVelocity;
import io.github.kabanfriends.kabansmp.velocity.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.velocity.networking.Server;
import io.github.kabanfriends.kabansmp.velocity.networking.ServerStatus;
import io.github.kabanfriends.kabansmp.velocity.networking.StatusManager;
import io.github.kabanfriends.kabansmp.velocity.text.Components;
import io.github.kabanfriends.kabansmp.velocity.text.formatting.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.*;

public class ServerSelector {

    private static final ChatElement EMPTY_TEXT = ChatElement.of(Component.empty());

    public static void openGui(Player player) {
        if (PlayerUtil.isPlayerInLobby(player)) {
            ProtocolizePlayer pp = Protocolize.playerProvider().player(player.getUniqueId());
            for (int i = 36; i < 45; i++) {
                pp.sendPacket(new SetSlot((byte)0, (short)i, new ItemStack(ItemType.BLACK_STAINED_GLASS_PANE).displayName(EMPTY_TEXT), (short)0));
            }
        }

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
            ServerStatus status = StatusManager.getServerStatus(server.getId());

            ItemStack item;

            if (status != null) {
                item = new ItemStack(server.getMaterial());

                item.displayName(ChatElement.of(Components.translate(
                        Components.translatable("all.proxy.server.name." + server.getId()).style(Style.style(server.getColor(), TextDecoration.ITALIC.withState(false), TextDecoration.BOLD))
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
            } else {
                item = new ItemStack(ItemType.BARRIER);

                item.displayName(ChatElement.of(Components.translate(
                        Components.translatable("all.proxy.server.name." + server.getId()).style(Style.style(ServerColors.RED_LIGHT_2, TextDecoration.ITALIC.withState(false), TextDecoration.BOLD))
                        , locale)));
                item.lore(List.of(
                        ChatElement.of(Components.translate(
                                Components.translatable("selector.form.unknown").style(Style.style(ServerColors.GRAY_DARK, TextDecoration.ITALIC.withState(false)))
                                , locale)),
                        ChatElement.of(Component.empty()),
                        ChatElement.of(Components.translate(
                                Components.of(
                                        Component.text("▶ ").style(Style.style(ServerColors.RED, TextDecoration.ITALIC.withState(false))),
                                        Components.translatable("selector.form.unavailable").style(Style.style(ServerColors.WHITE, TextDecoration.ITALIC.withState(false)))
                                )
                                , locale))
                ));
            }

            inventory.item(server.getSlot(), item);
        }

        inventory.onClick(click -> {
            ClickType type = click.clickType();
            if (type == ClickType.LEFT_CLICK || type == ClickType.RIGHT_CLICK ||
                    type == ClickType.SHIFT_LEFT_CLICK || type == ClickType.SHIFT_RIGHT_CLICK) {
                for (Server server : Server.values()) {
                    if (click.slot() == server.getSlot()) {
                        ProtocolizePlayer pp = Protocolize.playerProvider().player(player.getUniqueId());
                        ServerStatus status = StatusManager.getServerStatus(server.getId());

                        if (status != null) {
                            pp.playSound(Sound.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.MASTER, 1.0f, 1.0f);
                            pp.closeInventory();
                            acceptPrompt(player, server);
                        } else {
                            pp.playSound(Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1.0f, 1.0f);
                            openJavaGui(player);
                        }
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
                .title(translate("selector.form.title", locale))
                .validResultHandler(response -> {
                    Server server = Server.values()[response.clickedButtonId()];
                    ServerStatus status = StatusManager.getServerStatus(server.getId());

                    if (status != null) {
                        acceptPrompt(player, server);
                    } else {
                        openBedrockGui(player);
                    }
                })
                .invalidResultHandler(() -> {
                    Optional<ServerConnection> optional = player.getCurrentServer();
                    if (optional.isEmpty()) {
                        return;
                    }
                    String serverName = optional.get().getServerInfo().getName();
                    if (serverName.equals("lobby")) {
                        openBedrockGui(player);
                    }
                });

        for (Server server : Server.values()) {
            Optional<RegisteredServer> optional = KabanSMPVelocity.getInstance().getServer().getServer(server.getId());
            if (optional.isEmpty()) {
                continue;
            }
            RegisteredServer rs = optional.get();
            ServerStatus status = StatusManager.getServerStatus(server.getId());

            if (status != null) {
                form.button(translate("all.proxy.server.name." + server.getId(), locale) + " (" + translate("all.players", locale, rs.getPlayersConnected().size()) + ")", FormImage.Type.URL, server.getImageUrl());
            } else {
                form.button(translate("all.proxy.server.name." + server.getId(), locale) + " (" + translate("selector.form.unavailable.short", locale) + ")", FormImage.Type.URL, "https://i.imgur.com/uB1yNl3.png");
            }
        }

        fplayer.sendForm(form.build());
    }

    private static String translate(String key, Locale locale, Object... values) {
        return Components.plain(Components.translate(Components.translatable(key, values), locale));
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
