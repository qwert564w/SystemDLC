package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.GameRendererHooks;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void systemdlcBobView(MatrixStack matrixStack, float tickDelta, CallbackInfo ci) {
        boolean result = GameRendererHooks.isGameRendererMatrixStackFloat((GameRenderer)(Object)this, matrixStack, tickDelta);
        if (!result) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    private void systemdlcRenderHandHead(Camera camera, float tickDelta, Matrix4f projectionMatrix, CallbackInfo ci) {
        GameRendererHooks.onGameRendererCameraFloatMatrix4f2((GameRenderer)(Object)this, camera, tickDelta, projectionMatrix);
    }

    @Inject(method = "renderHand", at = @At("TAIL"))
    private void systemdlcRenderHandTail(Camera camera, float tickDelta, Matrix4f projectionMatrix, CallbackInfo ci) {
        GameRendererHooks.onGameRendererCameraFloatMatrix4f((GameRenderer)(Object)this, camera, tickDelta, projectionMatrix);
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void systemdlcTiltViewWhenHurt(MatrixStack matrixStack, float tickDelta, CallbackInfo ci) {
        boolean result = GameRendererHooks.isGameRendererMatrixStackFloat2((GameRenderer)(Object)this, matrixStack, tickDelta);
        if (!result) {
            ci.cancel();
        }
    }

    @Inject(method = "getFov", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetFov(Camera camera, float tickDelta, boolean useFovSetting, CallbackInfoReturnable<Float> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            float result = GameRendererHooks.getFloatByGameRendererCameraFloatBoolean((GameRenderer)(Object)this, camera, tickDelta, useFovSetting);
            cir.setReturnValue(result);
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "getBasicProjectionMatrix", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetBasicProjectionMatrix(float fov, CallbackInfoReturnable<Matrix4f> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            Matrix4f result = GameRendererHooks.getMatrix4fByGameRendererFloat((GameRenderer)(Object)this, fov);
            if (result != null) {
                cir.setReturnValue(result);
            }
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderBlur", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderBlur(CallbackInfo ci) {
        boolean result = GameRendererHooks.isGameRenderer((GameRenderer)(Object)this);
        if (!result) {
            ci.cancel();
        }
    }
}
