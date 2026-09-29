package ru.pulse.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pulse.cosmetic.CosmeticFeatureRenderer;

@Mixin(LivingEntityRenderer.class)
public abstract class CosmeticRendererMixin {
    @Shadow
    protected abstract boolean addFeature(FeatureRenderer<?, ?> feature);

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void systemdlc$cosmeticsInit(EntityRendererFactory.Context ctx, EntityModel model, float shadowRadius, CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntityRenderer) {
            @SuppressWarnings("unchecked")
            FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context =
                (FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>) (Object) this;
            this.addFeature(new CosmeticFeatureRenderer(context));
        }
    }
}
