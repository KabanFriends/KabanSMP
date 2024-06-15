package io.github.kabanfriends.kabansmp.core.codec;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Codec<S, T> {

    private final Function<S, T> deserializer;
    private final BiConsumer<S, T> serializer;

    public Codec(BiConsumer<S, T> serializer, Function<S, T> deserializer) {
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    public T deserialize(S container) {
        return deserializer.apply(container);
    }

    public void serialize(S container, T value) {
        serializer.accept(container, value);
    }
}
