package io.github.kabanfriends.kabansmp.velocity.networking;

import dev.simplix.protocolize.data.ItemType;
import io.github.kabanfriends.kabansmp.velocity.text.formatting.ServerColors;
import net.kyori.adventure.text.format.TextColor;

public enum Server {
    SURVIVAL("survival", ItemType.GRASS_BLOCK, ServerColors.GREEN_LIGHT, "", 1),
    CREATIVE("creative", ItemType.BRICKS, ServerColors.GOLD_LIGHT, "", 3),
    ;

    private final String id;
    private final ItemType material;
    private final TextColor color;
    private final String imageUrl;
    private final int slot;

    Server(String id, ItemType material, TextColor color, String imageUrl, int slot) {
        this.id = id;
        this.material = material;
        this.color = color;
        this.imageUrl = imageUrl;
        this.slot = slot;
    }

    public String getId() {
        return id;
    }

    public ItemType getMaterial() {
        return material;
    }

    public TextColor getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getSlot() {
        return slot;
    }
}
