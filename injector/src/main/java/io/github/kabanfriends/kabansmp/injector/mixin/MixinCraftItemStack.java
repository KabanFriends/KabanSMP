package io.github.kabanfriends.kabansmp.injector.mixin;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.invoke.VarHandle;

@Mixin(value = CraftItemStack.class, remap = false)
public class MixinCraftItemStack {

    @Redirect(method = "getCraftStack", at = @At(value = "INVOKE", target = "Ljava/lang/invoke/VarHandle;get(Lorg/bukkit/inventory/ItemStack;)Lorg/bukkit/craftbukkit/inventory/CraftItemStack;"))
    private static CraftItemStack redirectDelegateGet(VarHandle instance, ItemStack itemStack) {
        ItemStack delegate = itemStack;
        do {
            delegate = (ItemStack) instance.get(delegate);
        } while (!(delegate instanceof CraftItemStack));
        return (CraftItemStack) delegate;
    }
}
