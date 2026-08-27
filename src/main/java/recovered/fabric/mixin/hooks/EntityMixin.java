package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.EntityDamageHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "method_5810", at = @At("HEAD"), cancellable = true)
    private void systemdlcIsPushable(CallbackInfoReturnable<Boolean> cir) {
        if (!EntityDamageHelper.isEntity((Entity)(Object)this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_5697", at = @At("HEAD"), cancellable = true)
    private void systemdlcPushAwayFrom(Entity entity, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            EntityDamageHelper.onEntityEntity((Entity)(Object)this, entity);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_5784", at = @At("HEAD"))
    private void systemdlcMoveHead(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        EntityDamageHelper.onEntityMovementTypeVec3d2((Entity)(Object)this, movementType, movement);
    }

    @Inject(method = "method_5784", at = @At("TAIL"))
    private void systemdlcMoveTail(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        EntityDamageHelper.onEntityMovementTypeVec3d((Entity)(Object)this, movementType, movement);
    }

    @Inject(method = "method_65038", at = @At("HEAD"), cancellable = true)
    private void systemdlcIsControlledByPlayer(CallbackInfoReturnable<Boolean> cir) {
        if (!EntityDamageHelper.isEntity2((Entity)(Object)this)) {
            cir.setReturnValue(false);
        }
    }
}
