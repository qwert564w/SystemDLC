package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.EntityHooks;
import client.util.TargetAnimation;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "method_6007", at = @At("HEAD"))
    private void systemdlcTickMovementHead(CallbackInfo ci) {
        TargetAnimation.setClientPlayerEntity((ClientPlayerEntity)(Object)this);
    }

    @Inject(method = "method_6007", at = @At("TAIL"))
    private void systemdlcTickMovementTail(CallbackInfo ci) {
        TargetAnimation.setClientPlayerEntity2((ClientPlayerEntity)(Object)this);
    }

    @Inject(method = "method_6115", at = @At("HEAD"), cancellable = true)
    private void systemdlcIsUsingItem(CallbackInfoReturnable<Boolean> cir) {
        if (!TargetAnimation.isClientPlayerEntity((ClientPlayerEntity)(Object)this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_5773", at = @At("HEAD"))
    private void systemdlcTickHead(CallbackInfo ci) {
        TargetAnimation.setClientPlayerEntity3((ClientPlayerEntity)(Object)this);
    }

    @Inject(method = "method_5773", at = @At("TAIL"))
    private void systemdlcTickTail(CallbackInfo ci) {
        TargetAnimation.onClientPlayerEntity2((ClientPlayerEntity)(Object)this);
        TargetAnimation.onClientPlayerEntityTail((ClientPlayerEntity)(Object)this);
    }

    @Inject(method = "method_3136", at = @At("HEAD"))
    private void systemdlcSendMovementPacketsHead(CallbackInfo ci) {
        TargetAnimation.onClientPlayerEntity((ClientPlayerEntity)(Object)this);
    }

    @Inject(method = "method_7290", at = @At("HEAD"), cancellable = true)
    private void systemdlcDropSelectedItem(boolean dropAll, CallbackInfoReturnable<Boolean> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(EntityHooks.isClientPlayerEntityBoolean((ClientPlayerEntity)(Object)this, dropAll));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
