package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.LightmapHooks;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Inject(method = "method_42597", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetDarkness(float delta, CallbackInfoReturnable<Float> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(LightmapHooks.getFloatByLightmapTextureManagerFloat((LightmapTextureManager)(Object)this, delta));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_3313", at = @At("HEAD"))
    private void systemdlcUpdate(float delta, CallbackInfo ci) {
        LightmapHooks.onLightmapTextureManagerFloat((LightmapTextureManager)(Object)this, delta);
    }
}
