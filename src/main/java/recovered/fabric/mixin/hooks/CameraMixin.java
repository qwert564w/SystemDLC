package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.CameraHooks;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Inject(method = "method_19318", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetCameraDistance(float value, CallbackInfoReturnable<Float> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(CameraHooks.getFloatByCameraFloat2((Camera)(Object)this, value));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_19321", at = @At("HEAD"), cancellable = true)
    private void systemdlcUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CameraHooks.onCameraBlockViewEntityBooleanBooleanFloat((Camera)(Object)this, area, focusedEntity, thirdPerson, inverseView, tickDelta);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
