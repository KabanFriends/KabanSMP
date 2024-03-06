package io.github.kabanfriends.kabansmp.networking.recipient;

public abstract class Recipient {

    private final String name;

    protected Recipient(String name) {
        this.name = name;
        Recipients.RECIPIENTS.put(name, this);
    }

    public String getName() {
        return name;
    }
}
