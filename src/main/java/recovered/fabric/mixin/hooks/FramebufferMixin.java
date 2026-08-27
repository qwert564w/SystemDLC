package recovered.fabric.mixin.hooks;

import client.render.FramebufferBindHook;
import net.minecraft.client.gl.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Framebuffer.class)
public abstract class FramebufferMixin {
    @Inject(method = "method_1235", at = @At("HEAD"), cancellable = true)
    private void systemdlcBeginWrite(boolean setViewport, CallbackInfo ci) {
        if (!FramebufferBindHook.isFramebufferBoolean((Framebuffer)(Object)this, setViewport)) {
            ci.cancel();
        }
    }
}
