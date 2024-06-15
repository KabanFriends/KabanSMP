package io.github.kabanfriends.kabansmp.core.codec.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.kabanfriends.kabansmp.core.codec.JsonCodec;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.LocaleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Array;
import java.util.Locale;
import java.util.function.Function;

public class JsonCodecs {

    public static final JsonCodec<String> STRING = new JsonCodec<>((property, value) -> property.set(new JsonPrimitive(value)), (property) -> property.get().getAsString());
    public static final JsonCodec<String[]> STRING_ARRAY = new ArrayCodec<>(String.class, JsonPrimitive::new, JsonElement::getAsString);
    public static final JsonCodec<Integer> INTEGER = new JsonCodec<>((property, value) -> property.set(new JsonPrimitive(value)), (property) -> property.get().getAsInt());
    public static final JsonCodec<Boolean> BOOLEAN = new JsonCodec<>((property, value) -> property.set(new JsonPrimitive(value)), (property) -> property.get().getAsBoolean());
    public static final JsonCodec<Location> LOCATION = new JsonCodec<>(
            (property, value) -> {
                JsonObject json = new JsonObject();
                json.addProperty("world", value.getWorld().getName());
                json.addProperty("x", value.getX());
                json.addProperty("y", value.getY());
                json.addProperty("z", value.getZ());
                json.addProperty("yaw", value.getY());
                json.addProperty("pitch", value.getPitch());
                property.set(json);
            },
            (property) -> {
                JsonObject json = property.get().getAsJsonObject();
                return new Location(
                        Bukkit.getWorld(json.get("world").getAsString()),
                        json.get("x").getAsDouble(),
                        json.get("y").getAsDouble(),
                        json.get("z").getAsDouble(),
                        json.get("yaw").getAsFloat(),
                        json.get("pitch").getAsFloat()
                );
            }
    );
    public static final JsonCodec<Component> COMPONENT = new JsonCodec<>(
            (property, value) -> property.set(new JsonPrimitive(MiniMessage.miniMessage().serialize(value))),
            (property) -> MiniMessage.miniMessage().deserialize(property.get().getAsString())
    );
    public static final JsonCodec<Component[]> COMPONENT_ARRAY = new ArrayCodec<>(Component.class, (value) -> new JsonPrimitive(MiniMessage.miniMessage().serialize(value)), (property) -> MiniMessage.miniMessage().deserialize(property.getAsString()));
    public static final JsonCodec<Locale> LOCALE = new JsonCodec<>(
            (property, value) -> property.set(new JsonPrimitive(value.getLanguage())),
            (property) -> LocaleUtils.toLocale(property.get().getAsString())
    );

    public static class ArrayCodec<T> extends JsonCodec<T[]> {

        public ArrayCodec(Class<T> clazz, Function<T, JsonElement> serializer, Function<JsonElement, T> deserializer) {
            super(
                    (property, array) -> {
                        JsonArray jsonArray = new JsonArray();
                        for (T value : array) {
                            jsonArray.add(serializer.apply(value));
                        }
                        property.set(jsonArray);
                    },
                    (property) -> {
                        JsonArray jsonArray = property.get().getAsJsonArray();
                        T[] array = (T[]) Array.newInstance(clazz, jsonArray.size());
                        for (int i = 0; i < jsonArray.size(); i++) {
                            array[i] = deserializer.apply(jsonArray.get(i));
                        }
                        return array;
                    }
            );
        }
    }
}
