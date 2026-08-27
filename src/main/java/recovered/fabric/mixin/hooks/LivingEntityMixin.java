package recovered.fabric.mixin.hooks;

import client.util.EntityDamageHelper;
import client.util.TargetAnimation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "method_6007", at = @At("HEAD"))
    private void systemdlcTickMovementHead(CallbackInfo ci) {
        TargetAnimation.setLivingEntity2((LivingEntity)(Object)this);
    }

    @Inject(method = "method_6007", at = @At("TAIL"))
    private void systemdlcTickMovementTail(CallbackInfo ci) {
        TargetAnimation.setLivingEntity((LivingEntity)(Object)this);
    }

    @Inject(method = "method_6115", at = @At("HEAD"), cancellable = true)
    private void systemdlcIsUsingItem(CallbackInfoReturnable<Boolean> cir) {
        if (!TargetAnimation.isLivingEntity((LivingEntity)(Object)this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_6078", at = @At("HEAD"))
    private void systemdlcApplyDamage(DamageSource source, CallbackInfo ci) {
        EntityDamageHelper.onLivingEntityDamageSource((LivingEntity)(Object)this, source);
    }
}
