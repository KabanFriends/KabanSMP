package io.github.kabanfriends.kabansmp.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.InvocableCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.protocolize.api.Protocolize;
import io.github.kabanfriends.kabansmp.networking.config.ProxyConfig;
import io.github.kabanfriends.kabansmp.networking.packet.RedisPacketHandler;
import io.github.kabanfriends.kabansmp.networking.recipient.Recipients;
import io.github.kabanfriends.kabansmp.velocity.command.ServerCommand;
import io.github.kabanfriends.kabansmp.velocity.command.TestCommand;
import io.github.kabanfriends.kabansmp.velocity.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.velocity.event.PlayerBedrockEventHandler;
import io.github.kabanfriends.kabansmp.velocity.event.PlayerServerEventHandler;
import io.github.kabanfriends.kabansmp.velocity.event.packet.LeftClickPacketListener;
import io.github.kabanfriends.kabansmp.velocity.event.packet.RightClickPacketListener;
import io.github.kabanfriends.kabansmp.velocity.networking.ProxyPacketListener;
import io.github.kabanfriends.kabansmp.velocity.packet.CustomPacketRegistry;
import org.geysermc.geyser.api.GeyserApi;
import org.slf4j.Logger;

@Plugin(
        id = "kabansmp",
        name = "KabanSMP-Velocity",
        version = "1.2.0",
        authors = { "KabanFriends" },
        dependencies = {
                @Dependency(id = "protocolize")
        }
)
public class KabanSMPVelocity {

    private static KabanSMPVelocity instance;


    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public KabanSMPVelocity(ProxyServer server, Logger logger) {
        instance = this;
        this.server = server;
        this.logger = logger;

        // Register commands
        registerCommand("vtest", new TestCommand());
        registerCommand("server", new ServerCommand(), "s");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Load config
        LanguageConfig.load();
        ProxyConfig.load();

        // Start redis packet listener
        logger.info("Starting Redis packet listener");
        RedisPacketHandler.init(Recipients.PROXY, new ProxyPacketListener());

        // Register event listeners
        registerListener(new PlayerServerEventHandler());

        // Init Geyser related stuff
        GeyserApi.api().eventBus().register(new PlayerBedrockEventHandler(), KabanSMPVelocity.class);

        // Register Protocolize packet listener
        CustomPacketRegistry.registerPackets(Protocolize.protocolRegistration());
        Protocolize.listenerProvider().registerListener(new RightClickPacketListener());
        Protocolize.listenerProvider().registerListener(new LeftClickPacketListener());
    }

    private void registerCommand(String name, InvocableCommand command, String... aliases) {
        CommandManager manager = server.getCommandManager();
        manager.register(manager.metaBuilder(name).aliases(aliases).build(), command);
    }

    private void registerListener(Object listener) {
        server.getEventManager().register(this, listener);
    }

    public Logger getLogger() {
        return logger;
    }

    public ProxyServer getServer() {
        return server;
    }

    public static KabanSMPVelocity getInstance() {
        return instance;
    }
}
