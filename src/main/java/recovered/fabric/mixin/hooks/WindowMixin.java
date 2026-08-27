package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.WindowUtil;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public abstract class WindowMixin {
    @Inject(method = "method_4479", at = @At("TAIL"))
    private void systemdlcSetWindowAttributes(CallbackInfo ci) {
        WindowUtil.onWindow((Window)(Object)this);
    }

    @Inject(method = "method_15998", at = @At("HEAD"), cancellable = true)
    private void systemdlcAcceptTracyFrameCapturer(TracyFrameCapturer capturer, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            WindowUtil.onWindowTracyFrameCapturer((Window)(Object)this, capturer);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
