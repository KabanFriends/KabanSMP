package io.github.kabanfriends.kabansmp.core.player.data;

import io.github.kabanfriends.kabansmp.core.codec.ByteCodec;

public record DataField<T>(String id, ByteCodec<T> codec, T defaultValue) {
}
