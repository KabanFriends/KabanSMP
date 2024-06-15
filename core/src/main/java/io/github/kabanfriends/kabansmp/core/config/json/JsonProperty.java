package io.github.kabanfriends.kabansmp.core.config.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonProperty {

    private final JsonObject json;
    private final String property;

    public JsonProperty(JsonObject json, String property) {
        this.json = json;
        this.property = property;
    }

    public JsonElement get() {
        return json.get(property);
    }

    public void set(JsonElement value) {
        json.add(property, value);
    }
}
