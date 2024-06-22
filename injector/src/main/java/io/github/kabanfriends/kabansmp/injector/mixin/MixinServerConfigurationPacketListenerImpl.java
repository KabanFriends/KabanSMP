package io.github.kabanfriends.kabansmp.injector.mixin;

import com.google.common.collect.Lists;
import net.minecraft.server.ServerLinks;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerConfigurationPacketListenerImpl.class)
public class MixinServerConfigurationPacketListenerImpl {

    @ModifyArg(method = "startConfiguration", at = @At(value = "INVOKE", target = "Lorg/bukkit/craftbukkit/CraftServerLinks;<init>(Lnet/minecraft/server/ServerLinks;)V"))
    public ServerLinks modifyServerLinks(ServerLinks serverLinks) {
        return new ServerLinks(Lists.newArrayList());
    }
}
