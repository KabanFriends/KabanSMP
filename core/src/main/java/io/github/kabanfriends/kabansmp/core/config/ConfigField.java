package io.github.kabanfriends.kabansmp.core.config;

import io.github.kabanfriends.kabansmp.core.codec.JsonCodec;

public class ConfigField<T> {

    protected final String id;
    protected final JsonCodec<T> codec;
    private final T defaultValue;

    private T value;

    public ConfigField(String id, JsonCodec<T> codec, T defaultValue) {
        this.id = id;
        this.codec = codec;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T get() {
        return this.value;
    }

    protected void set(T value) {
        this.value = value;
    }
}
