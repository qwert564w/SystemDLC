package recovered.fabric.mixin.hooks;

import client.render.NameTagVisibilityHook;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class BaseEntityRendererMixin {
    @Inject(method = "method_3926", at = @At("HEAD"), cancellable = true)
    protected void systemdlcRenderLabelIfPresent(
        EntityRenderState state,
        Text text,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo ci
    ) {
        if (!NameTagVisibilityHook.isEntityRendererEntityRenderStateTextMatrixStackVertexConsumerProviderInt(
            (EntityRenderer)(Object)this, state, text, matrices, vertexConsumers, light
        )) {
            ci.cancel();
        }
    }
}
