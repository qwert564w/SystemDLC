package recovered.fabric.mixin.hooks;

import client.gui.hud.DebugHudHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Inject(method = "method_1846", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderLeftText(DrawContext context, CallbackInfo ci) {
        if (!DebugHudHelper.isDebugHudDrawContext((DebugHud)(Object)this, context)) {
            ci.cancel();
        }
    }
}
