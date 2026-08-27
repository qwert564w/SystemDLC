package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.ChamsRenderHooks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.entity.equipment.EquipmentRenderer.class)
public abstract class EquipmentRendererMixin {
    @Inject(method = "method_64078", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(
        EquipmentModel.LayerType layerType,
        RegistryKey assetKey,
        net.minecraft.client.model.Model model,
        ItemStack stack,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        Identifier texture,
        CallbackInfo ci
    ) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            ChamsRenderHooks.onEquipmentRendererLayerTypeRegistryKeyModelItemStackMatrixStackVertexConsumerProviderIntIdentifier(
                (net.minecraft.client.render.entity.equipment.EquipmentRenderer)(Object)this,
                layerType, assetKey, model, stack, matrices, vertexConsumers, light, texture
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
