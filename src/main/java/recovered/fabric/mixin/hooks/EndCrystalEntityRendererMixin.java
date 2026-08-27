package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.EndCrystalRenderHook;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class EndCrystalEntityRendererMixin {
    @Inject(method = "method_3908", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(
        EndCrystalEntityRenderState state,
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
            EndCrystalRenderHook.onEndCrystalEntityRendererEndCrystalEntityRenderStateMatrixStackVertexConsumerProviderInt(
                (EndCrystalEntityRenderer)(Object)this, state, matrices, vertexConsumers, light
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
