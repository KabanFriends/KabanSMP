package io.github.kabanfriends.kabansmp.networking.recipient;

public class ServerRecipient extends Recipient {

    private final int port;

    protected ServerRecipient(String name, int port) {
        super(name);
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
