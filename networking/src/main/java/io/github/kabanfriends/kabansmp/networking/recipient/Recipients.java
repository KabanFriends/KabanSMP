package io.github.kabanfriends.kabansmp.networking.recipient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Recipients {

    protected static final Map<String, Recipient> RECIPIENTS = new HashMap<>();

    public static final ProxyRecipient PROXY = new ProxyRecipient("proxy");

    public static Recipient getByName(String name) {
        return RECIPIENTS.get(name);
    }

    public static Collection<Recipient> getAll() {
        return RECIPIENTS.values();
    }
}
