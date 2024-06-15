package io.github.kabanfriends.kabansmp.core.codec;

import io.github.kabanfriends.kabansmp.core.config.json.JsonProperty;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class JsonCodec<T> extends Codec<JsonProperty, T> {

    public JsonCodec(BiConsumer<JsonProperty, T> serializer, Function<JsonProperty, T> deserializer) {
        super(serializer, deserializer);
    }
}
