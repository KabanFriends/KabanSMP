package io.github.kabanfriends.kabansmp.injector.mixin;

import io.github.kabanfriends.kabansmp.injector.api.MixinConfigAPI;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerConfigurationPacketListenerImpl.class)
public class MixinServerConfigurationPacketListenerImpl {

    @Inject(method = "startConfiguration", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerConfigurationPacketListenerImpl;addOptionalTasks()V"))
    public void popPackOnConfiguration(CallbackInfo ci) {
        if (MixinConfigAPI.unloadPack) {
            ((ServerConfigurationPacketListenerImpl)(Object)this).send(new ClientboundResourcePackPopPacket(Optional.empty()));
        }
    }
}
