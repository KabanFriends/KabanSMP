package io.github.kabanfriends.kabansmp.networking.recipient;

public class ServerManager {

    private static final ServerRecipient[] SERVERS = {
            new ServerRecipient("lobby", 25564),
            new ServerRecipient("survival", 25566),
            new ServerRecipient("creative", 25567)
    };

    private static int currentPort;
    private static ServerRecipient currentServer;

    public static void init(int port) {
        currentPort = port;
    }

    public static ServerRecipient getCurrentServer() {
        if (currentServer != null) {
            return currentServer;
        }

        for (ServerRecipient server : SERVERS) {
            if (server.getPort() == currentPort) {
                currentServer = server;
                break;
            }
        }

        if (currentServer == null) {
            throw new IllegalStateException("Server of the port " + currentPort + " was not found. Did you add the server definition to ServerManager?");
        }

        return currentServer;
    }

    public static ServerRecipient[] getAllServers() {
        return SERVERS;
    }
}
