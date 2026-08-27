package recovered.fabric.mixin.hooks;

import client.network.ConnectionHooks;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void systemdlcOnChannelRead(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        boolean result = ConnectionHooks.isClientConnectionChannelHandlerContextPacket(
            (ClientConnection)(Object)this, ctx, packet
        );
        if (!result) {
            ci.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void systemdlcOnSend(Packet<?> packet, CallbackInfo ci) {
        boolean result = ConnectionHooks.isClientConnectionPacket(
            (ClientConnection)(Object)this, packet
        );
        if (!result) {
            ci.cancel();
        }
    }
}
