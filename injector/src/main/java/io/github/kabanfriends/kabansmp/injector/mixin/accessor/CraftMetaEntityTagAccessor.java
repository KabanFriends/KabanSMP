package io.github.kabanfriends.kabansmp.injector.mixin.accessor;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMetaEntityTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CraftMetaEntityTag.class, remap = false)
public interface CraftMetaEntityTagAccessor {

    @Accessor("entityTag")
    CompoundTag getEntityTag();

    @Accessor("entityTag")
    void setEntitytag(CompoundTag tag);
}
