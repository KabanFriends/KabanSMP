package io.github.kabanfriends.kabansmp.mixin;

import io.github.kabanfriends.kabansmp.api.ChatMixinAPI;
import net.minecraft.network.chat.LastSeenMessagesValidator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {

    @Shadow @Final @Mutable
    private LastSeenMessagesValidator lastSeenMessages;

    @Shadow
    public ServerPlayer player;

    @Redirect(method = "handleChatSessionUpdate", at = @At(value = "INVOKE", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    public boolean onPublicKeyCompare(Object a, Object b) {
        // Player is allowed to update chat session with the same public key
        if (ChatMixinAPI.SESSION_AWAITING_PLAYERS.contains(player.getBukkitEntity())) {
            ChatMixinAPI.SESSION_AWAITING_PLAYERS.remove(player.getBukkitEntity());
            lastSeenMessages = new LastSeenMessagesValidator(20); // Reset last seen messages
            return false;
        }
        return Objects.equals(a, b);
    }
}
