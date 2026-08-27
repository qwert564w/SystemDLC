package recovered.fabric.mixin.hooks;

import client.render.CrosshairTextures;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(DrawContext context, CallbackInfo ci) {
        boolean result = CrosshairTextures.isBossBarHudDrawContext((BossBarHud)(Object)this, context);
        if (!result) {
            ci.cancel();
        }
    }
}
