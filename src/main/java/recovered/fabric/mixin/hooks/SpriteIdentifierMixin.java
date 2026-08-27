package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.SpriteRenderLayers;
import java.util.function.Function;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpriteIdentifier.class)
public abstract class SpriteIdentifierMixin {
    @Inject(method = "method_24146", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetRenderLayer(Function<Identifier, RenderLayer> layerFactory, CallbackInfoReturnable<RenderLayer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(SpriteRenderLayers.getRenderLayerBySpriteIdentifierFunction((SpriteIdentifier)(Object)this, layerFactory));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
