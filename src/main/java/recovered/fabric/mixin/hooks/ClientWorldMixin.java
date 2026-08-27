package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.WorldTickHook;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(method = "method_29089", at = @At("HEAD"), cancellable = true)
    private void systemdlcSetTime(long time, long time2, boolean doDaylightCycle, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            WorldTickHook.onClientWorldLongLongBoolean((ClientWorld)(Object)this, time, time2, doDaylightCycle);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
