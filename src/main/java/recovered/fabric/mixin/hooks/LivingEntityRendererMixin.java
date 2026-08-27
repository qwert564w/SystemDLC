package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.ChamsRenderHooks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "method_4054", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(
        LivingEntityRenderState state,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo ci
    ) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            ChamsRenderHooks.onLivingEntityRendererLivingEntityRenderStateMatrixStackVertexConsumerProviderInt(
                (LivingEntityRenderer)(Object)this, state, matrices, vertexConsumers, light
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
