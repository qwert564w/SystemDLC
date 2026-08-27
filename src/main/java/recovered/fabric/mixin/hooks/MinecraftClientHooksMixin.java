package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.concurrent.ResourceManagerHooks;
import client.render.GameMenuHooks;
import client.util.ModuleDispatcher;
import client.util.UnsafeAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientHooksMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void systemdlcTickHead(CallbackInfo ci) {
        try {
            ResourceManagerHooks.update();
        } catch (Throwable ignored) {
        }
        MinecraftClient self = (MinecraftClient)(Object)this;
        if (self.player != null) {
            ModuleDispatcher dispatcher = UnsafeAccess.getModuleDispatcher();
            if (dispatcher != null) {
                try {
                    dispatcher.update6();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void systemdlcDoAttack(CallbackInfoReturnable<Boolean> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            boolean result = GameMenuHooks.isMinecraftClient((MinecraftClient)(Object)this);
            cir.setReturnValue(result);
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void systemdlcDoItemUse(CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            GameMenuHooks.onMinecraftClient3((MinecraftClient)(Object)this);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "getFramebuffer", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetFramebuffer(CallbackInfoReturnable<Framebuffer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            Framebuffer fb = GameMenuHooks.getFramebufferByMinecraftClient((MinecraftClient)(Object)this);
            if (fb != null) {
                cir.setReturnValue(fb);
            }
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_1588", at = @At("HEAD"), cancellable = true)
    private static void systemdlcCheck2(CallbackInfoReturnable<Boolean> cir) {
        if (!client.render.WorldRenderHooks.check2()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    private void systemdlcOnDisconnected(CallbackInfo ci) {
        GameMenuHooks.onMinecraftClient2((MinecraftClient)(Object)this);
    }
}
