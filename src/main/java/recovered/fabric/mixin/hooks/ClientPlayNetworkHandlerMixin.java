package recovered.fabric.mixin.hooks;

import client.network.NetworkHandlerHooks;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onGameJoin", at = @At("HEAD"))
    private void systemdlcOnGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        NetworkHandlerHooks.onClientPlayNetworkHandlerGameJoinS2CPacket(
            (ClientPlayNetworkHandler)(Object)this, packet
        );
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    private void systemdlcOnSendChatMessage(String message, CallbackInfo ci) {
        NetworkHandlerHooks.onClientPlayNetworkHandlerString(
            (ClientPlayNetworkHandler)(Object)this, message
        );
    }
}
