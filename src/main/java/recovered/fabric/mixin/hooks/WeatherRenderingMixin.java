package recovered.fabric.mixin.hooks;

import client.render.WorldRenderHooks;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherRendering.class)
public abstract class WeatherRenderingMixin {
    @Inject(method = "method_62316", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderPrecipitation(World world, VertexConsumerProvider vertexConsumers, int ticks, float delta, Vec3d cameraPos, CallbackInfo ci) {
        if (!WorldRenderHooks.isWeatherRenderingWorldVertexConsumerProviderIntFloatVec3d(
            (WeatherRendering)(Object)this, world, vertexConsumers, ticks, delta, cameraPos
        )) {
            ci.cancel();
        }
    }
}
