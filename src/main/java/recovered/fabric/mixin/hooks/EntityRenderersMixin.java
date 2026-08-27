package recovered.fabric.mixin.hooks;

import client.render.EntityDispatcherHooks;
import java.util.Map;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderers.class)
public abstract class EntityRenderersMixin {
    @Inject(method = "method_32177", at = @At("HEAD"), cancellable = true)
    private static void systemdlcCreatePlayerRenderers(EntityRendererFactory.Context context, CallbackInfoReturnable<Map<net.minecraft.client.util.SkinTextures.Model, PlayerEntityRenderer>> cir) {
        cir.setReturnValue(EntityDispatcherHooks.getMapByContext(context));
    }
}
