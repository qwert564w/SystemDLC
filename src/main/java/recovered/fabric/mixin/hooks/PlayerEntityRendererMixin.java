package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.PlayerNameTagHooks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "method_4217", at = @At("HEAD"), cancellable = true)
    protected void systemdlcScale(PlayerEntityRenderState state, MatrixStack matrices, CallbackInfo ci) {
        PlayerNameTagHooks.onPlayerEntityRendererPlayerEntityRenderStateMatrixStack(
            (PlayerEntityRenderer)(Object)this, state, matrices
        );
        ci.cancel();
    }

    @Inject(method = "method_4213", at = @At("HEAD"), cancellable = true)
    protected void systemdlcRenderLabel(
        PlayerEntityRenderState state,
        Text text,
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
            PlayerNameTagHooks.onPlayerEntityRendererPlayerEntityRenderStateTextMatrixStackVertexConsumerProviderInt(
                (PlayerEntityRenderer)(Object)this, state, text, matrices, vertexConsumers, light
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
