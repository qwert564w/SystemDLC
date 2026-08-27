package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.WorldRenderHooks;
import net.minecraft.client.gl.GlUniform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlUniform.class)
public abstract class GlUniformMixin {
    @Inject(method = "method_1249", at = @At("HEAD"), cancellable = true)
    private void systemdlcSet(float value1, float value2, float value3, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            WorldRenderHooks.onGlUniformFloatFloatFloat((GlUniform)(Object)this, value1, value2, value3);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
