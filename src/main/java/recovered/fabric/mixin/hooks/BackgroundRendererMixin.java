package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.FogHooks;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @Inject(method = "method_62185", at = @At("HEAD"), cancellable = true)
    private static void systemdlcApplyFog(Camera camera, float value, ClientWorld world, int count, float value2, CallbackInfoReturnable<Vector4f> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(FogHooks.getVector4fByCameraFloatClientWorldIntFloat(camera, value, world, count, value2));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_3211", at = @At("HEAD"), cancellable = true)
    private static void systemdlcGetFogColor(Camera camera, FogType fogType, Vector4f color, float viewDistance, boolean thickFog, float tickDelta, CallbackInfoReturnable<Fog> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(FogHooks.getFogByCameraFogTypeVector4fFloatBooleanFloat(camera, fogType, color, viewDistance, thickFog, tickDelta));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
