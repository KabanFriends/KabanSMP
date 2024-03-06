package io.github.kabanfriends.kabansmp.networking.packet;

import io.github.kabanfriends.kabansmp.networking.config.ProxyConfig;
import io.github.kabanfriends.kabansmp.networking.recipient.Recipient;
import io.github.kabanfriends.kabansmp.networking.recipient.Recipients;
import io.github.kabanfriends.kabansmp.networking.recipient.ServerManager;
import io.github.kabanfriends.kabansmp.networking.recipient.ServerRecipient;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPooled;

import java.nio.charset.StandardCharsets;

public class RedisPacketHandler {

    private static Recipient owner;
    private static JedisPooled main;
    private static Jedis pubSub;

    public static void init(Recipient currentRecipient, PacketListener listener) {
        ConnectionPoolConfig config = new ConnectionPoolConfig();
        config.setMinIdle(1);

        owner = currentRecipient;

        main = new JedisPooled(config, ProxyConfig.redisUrl);
        pubSub = new Jedis(ProxyConfig.redisUrl);

        new Thread(() -> {
            pubSub.subscribe(new Receiver(listener), currentRecipient.getName().getBytes(StandardCharsets.UTF_8));
        }, "Redis packet listener thread").start();
    }

    public static void sendPacket(Packet packet, Recipient destination) {
        int id = PacketRegistry.getId(packet.getClass());

        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(id);
        packet.write(buffer);

        main.publish(destination.getName().getBytes(StandardCharsets.UTF_8), buffer.toBytes());
    }

    public static void sendToAll(Packet packet) {
        for (Recipient recipient : Recipients.getAll()) {
            if (owner == recipient) {
                continue;
            }
            sendPacket(packet, recipient);
        }
    }

    public static void sendToAllServers(Packet packet) {
        for (ServerRecipient recipient : ServerManager.getAllServers()) {
            if (owner == recipient) {
                continue;
            }
            sendPacket(packet, recipient);
        }
    }

    static class Receiver extends BinaryJedisPubSub {

        private final PacketListener listener;

        public Receiver(PacketListener listener) {
            this.listener = listener;
        }

        @Override
        public void onMessage(byte[] channel, byte[] message) {
            PacketBuffer buffer = new PacketBuffer(message);

            try {
                PacketRegistry.createPacket(buffer.readVarInt(), buffer).handle(listener);
            } catch (Exception e) {
                System.err.println("Error handling packet:");
                e.printStackTrace();
            }
        }
    }
}
