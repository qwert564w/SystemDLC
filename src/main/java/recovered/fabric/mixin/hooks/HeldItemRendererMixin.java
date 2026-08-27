package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.HeldItemHooks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Inject(method = "method_3228", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderFirstPersonItem(
        net.minecraft.client.network.AbstractClientPlayerEntity player,
        float tickDelta,
        float pitch,
        Hand hand,
        float swingProgress,
        ItemStack item,
        float equipProgress,
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
            HeldItemHooks.onHeldItemRendererAbstractClientPlayerEntityFloatFloatHandFloatItemStackFloatMatrixStackVertexConsumerProviderInt(
                (HeldItemRenderer)(Object)this, player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
