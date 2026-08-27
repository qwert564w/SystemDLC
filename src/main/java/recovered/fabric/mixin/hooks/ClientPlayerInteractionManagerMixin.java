package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.EntityChecks;
import client.util.EntityHooks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "method_2905", at = @At("HEAD"), cancellable = true)
    private void systemdlcAttackEntity(PlayerEntity player, Entity target, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(EntityChecks.getActionResultByClientPlayerInteractionManagerPlayerEntityEntityHand(
                (ClientPlayerInteractionManager)(Object)this, player, target, hand
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_2917", at = @At("HEAD"), cancellable = true)
    private void systemdlcInteractEntityAtLocation(PlayerEntity player, Entity entity, EntityHitResult hitResult, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(EntityChecks.getActionResultByClientPlayerInteractionManagerPlayerEntityEntityEntityHitResultHand(
                (ClientPlayerInteractionManager)(Object)this, player, entity, hitResult, hand
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_2919", at = @At("HEAD"), cancellable = true)
    private void systemdlcInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(EntityChecks.getActionResultByClientPlayerInteractionManagerPlayerEntityHand(
                (ClientPlayerInteractionManager)(Object)this, player, hand
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_2896", at = @At("HEAD"), cancellable = true)
    private void systemdlcInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            cir.setReturnValue(EntityChecks.getActionResultByClientPlayerInteractionManagerClientPlayerEntityHandBlockHitResult(
                (ClientPlayerInteractionManager)(Object)this, player, hand, hitResult
            ));
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_2918", at = @At("HEAD"))
    private void systemdlcAttackBlock(PlayerEntity player, Entity entity, CallbackInfo ci) {
        EntityChecks.onClientPlayerInteractionManagerPlayerEntityEntity((ClientPlayerInteractionManager)(Object)this, player, entity);
    }

    @Inject(method = "method_2906", at = @At("HEAD"), cancellable = true)
    private void systemdlcClickSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            EntityHooks.onClientPlayerInteractionManagerIntIntIntSlotActionTypePlayerEntity(
                (ClientPlayerInteractionManager)(Object)this, syncId, slotId, button, actionType, player
            );
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
