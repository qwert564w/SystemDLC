package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.NameTagRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    @Inject(method = "method_22942", at = @At("HEAD"), cancellable = true)
    public void systemdlcDraw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(NameTagRenderer.getIntByTextRendererOrderedTextFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntInt(
                (TextRenderer)(Object)this, text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_30882", at = @At("HEAD"), cancellable = true)
    public void systemdlcDraw2(Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, boolean outline, CallbackInfoReturnable<Integer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(NameTagRenderer.getIntByTextRendererTextFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntIntBoolean(
                (TextRenderer)(Object)this, text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light, outline
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_37296", at = @At("HEAD"), cancellable = true)
    public void systemdlcDrawWithOutline(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            NameTagRenderer.onTextRendererOrderedTextFloatFloatIntIntMatrix4fVertexConsumerProviderInt(
                (TextRenderer)(Object)this, text, x, y, color, outlineColor, matrix, vertexConsumers, light
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_27521", at = @At("HEAD"), cancellable = true)
    public void systemdlcDraw3(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(NameTagRenderer.getIntByTextRendererStringFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntInt(
                (TextRenderer)(Object)this, text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
