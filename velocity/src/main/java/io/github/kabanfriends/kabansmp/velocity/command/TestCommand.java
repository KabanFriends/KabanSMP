package io.github.kabanfriends.kabansmp.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import io.github.kabanfriends.kabansmp.networking.packet.RedisPacketHandler;
import io.github.kabanfriends.kabansmp.networking.packet.impl.TestPacket;
import io.github.kabanfriends.kabansmp.velocity.text.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TestCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 0) {
            invocation.source().sendMessage(Component.text("No arguments specified"));
            return;
        }

        String name = args[0];
        switch (name) {
            case "packet": packetTest(); break;
            case "lang": langTest(invocation); break;
            case "inv": inventoryTest(invocation); break;
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("kabansmp.vtest");
    }

    private static void packetTest() {
        RedisPacketHandler.sendToAllServers(new TestPacket(69));
    }

    private static void langTest(Invocation invocation) {
        invocation.source().sendMessage(Components.translatable("all.teleport.notStanding"));
    }

    private static void inventoryTest(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            return;
        }

        Inventory inventory = new Inventory(InventoryType.GENERIC_9X1);
        inventory.title(ChatElement.of(MiniMessage.miniMessage().deserialize("<rainbow>Hello world!")));

        inventory.item(0, new ItemStack(ItemType.OAK_LOG));
        ProtocolizePlayer pp = Protocolize.playerProvider().player(player.getUniqueId());
        pp.openInventory(inventory);

        player.sendMessage(Component.text("Inventory opened"));
    }
}
