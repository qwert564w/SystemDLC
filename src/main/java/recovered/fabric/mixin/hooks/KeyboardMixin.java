package recovered.fabric.mixin.hooks;

import client.util.InputHooks;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "method_1457", at = @At("HEAD"), cancellable = true)
    private void systemdlcOnKey(long window, int key, int scancode, CallbackInfo ci) {
        if (!InputHooks.isKeyboardLongIntInt((Keyboard)(Object)this, window, key, scancode)) {
            ci.cancel();
        }
    }
}
