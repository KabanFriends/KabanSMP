package io.github.kabanfriends.kabansmp.injector.api;

import io.github.kabanfriends.kabansmp.injector.mixin.accessor.CraftMetaEntityTagAccessor;
import io.github.kabanfriends.kabansmp.injector.mixin.accessor.CraftMetaSpawnEggAccessor;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMetaEntityTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMetaSpawnEgg;

public class AccessorMixinAPI {

    public static CompoundTag getEntityTag(CraftMetaEntityTag meta) {
        return ((CraftMetaEntityTagAccessor) meta).getEntityTag();
    }

    public static void setEntityTag(CraftMetaEntityTag meta, CompoundTag tag) {
        ((CraftMetaEntityTagAccessor) meta).setEntitytag(tag);
    }

    public static CompoundTag getSpawnEggEntityTag(CraftMetaSpawnEgg meta) {
        return ((CraftMetaSpawnEggAccessor) meta).getEntityTag();
    }

    public static void setSpawnEggEntityTag(CraftMetaSpawnEgg meta, CompoundTag tag) {
        ((CraftMetaSpawnEggAccessor) meta).setEntitytag(tag);
    }
}
