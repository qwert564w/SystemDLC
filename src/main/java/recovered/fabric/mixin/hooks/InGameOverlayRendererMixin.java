package recovered.fabric.mixin.hooks;

import client.module.visual.NoRender;
import client.util.UnsafeAccess;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {
    private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);

    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void systemdlcRenderFireOverlay(MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        NoRender norender = (NoRender) unsafeAccess.getModule2();
        if (norender != null && norender.check11()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderInWallOverlay", at = @At("HEAD"), cancellable = true)
    private static void systemdlcRenderInWallOverlay(Sprite sprite, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        NoRender norender = (NoRender) unsafeAccess.getModule2();
        if (norender != null) {
            ci.cancel();
        }
    }
}
