package io.github.kabanfriends.kabansmp.core.util.api;

import io.github.kabanfriends.kabansmp.injector.api.AccessorMixinAPI;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMetaEntityTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMetaSpawnEgg;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAPI {

    public static String getPaintingItemVariant(ItemMeta meta) {
        if (meta instanceof CraftMetaEntityTag craftMeta) {
            CompoundTag tag = AccessorMixinAPI.getEntityTag(craftMeta);
            if (tag != null && tag.contains("variant")) {
                return tag.getString("variant");
            }
        }
        return null;
    }

    public static void setPaintingItemVariant(ItemMeta meta, String variant) {
        if (meta instanceof CraftMetaEntityTag craftMeta) {
            CompoundTag tag = AccessorMixinAPI.getEntityTag(craftMeta);
            if (tag == null) {
                return;
            }
            tag.putString("variant", variant);
        }
    }

    public static void clearEntityMeta(ItemMeta meta) {
        if (meta instanceof CraftMetaEntityTag craftMeta) {
            if (AccessorMixinAPI.getEntityTag(craftMeta) == null) {
                return;
            }

            AccessorMixinAPI.setEntityTag(craftMeta, new CompoundTag());
        } else if (meta instanceof CraftMetaSpawnEgg craftMeta) {
            if (AccessorMixinAPI.getSpawnEggEntityTag(craftMeta) == null) {
                return;
            }

            AccessorMixinAPI.setSpawnEggEntityTag(craftMeta, new CompoundTag());
        }
    }
}
