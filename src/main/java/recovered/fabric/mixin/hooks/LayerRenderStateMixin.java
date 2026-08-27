package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.ArmorLayerHook;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderState.LayerRenderState.class)
public abstract class LayerRenderStateMixin {
    @Inject(method = "method_65614", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            ArmorLayerHook.onLayerRenderStateMatrixStackVertexConsumerProviderIntInt(
                (ItemRenderState.LayerRenderState)(Object)this, matrices, vertexConsumers, light, overlay
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
