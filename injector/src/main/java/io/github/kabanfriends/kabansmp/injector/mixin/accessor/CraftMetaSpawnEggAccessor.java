package io.github.kabanfriends.kabansmp.injector.mixin.accessor;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.inventory.CraftMetaSpawnEgg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CraftMetaSpawnEgg.class, remap = false)
public interface CraftMetaSpawnEggAccessor {

    @Accessor("entityTag")
    CompoundTag getEntityTag();

    @Accessor("entityTag")
    void setEntitytag(CompoundTag tag);
}
