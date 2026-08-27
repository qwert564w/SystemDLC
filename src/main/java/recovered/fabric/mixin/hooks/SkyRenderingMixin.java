package recovered.fabric.mixin.hooks;

import client.render.WorldRenderHooks;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.Fog;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRendering.class)
public abstract class SkyRenderingMixin {
    @Inject(method = "method_62302", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderSkyColor(float value, float value2, float value3, CallbackInfo ci) {
        if (!WorldRenderHooks.isSkyRenderingFloatFloatFloat((SkyRendering)(Object)this, value, value2, value3)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_62305", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderSky(MatrixStack matrices, CallbackInfo ci) {
        if (!WorldRenderHooks.isSkyRenderingMatrixStack((SkyRendering)(Object)this, matrices)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_62306", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderSkyFlat(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, float tickDelta, int light, CallbackInfo ci) {
        if (!WorldRenderHooks.isSkyRenderingMatrixStackImmediateFloatInt((SkyRendering)(Object)this, matrices, immediate, tickDelta, light)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_62307", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderStars(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, float tickDelta, int light, float value2, float value3, Fog fog, CallbackInfo ci) {
        if (!WorldRenderHooks.isSkyRenderingMatrixStackImmediateFloatIntFloatFloatFog(
            (SkyRendering)(Object)this, matrices, immediate, tickDelta, light, value2, value3, fog
        )) {
            ci.cancel();
        }
    }
}
