package recovered.fabric.mixin.hooks;

import client.render.EntityDispatcherHooks;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Inject(method = "method_3954", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(
        Entity entity,
        double x,
        double y,
        double z,
        float yaw,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        EntityRenderer renderer,
        CallbackInfo ci
    ) {
        EntityDispatcherHooks.onEntityRenderDispatcherEntityDoubleDoubleDoubleFloatMatrixStackVertexConsumerProviderIntEntityRenderer(
            (EntityRenderDispatcher)(Object)this, entity, x, y, z, yaw, matrices, vertexConsumers, light, renderer
        );
        ci.cancel();
    }

    @Inject(method = "method_3950", at = @At("HEAD"), cancellable = true)
    private void systemdlcShouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (!EntityDispatcherHooks.isEntityRenderDispatcherEntityFrustumDoubleDoubleDouble(
            (EntityRenderDispatcher)(Object)this, entity, frustum, x, y, z
        )) {
            cir.setReturnValue(false);
        }
    }
}
