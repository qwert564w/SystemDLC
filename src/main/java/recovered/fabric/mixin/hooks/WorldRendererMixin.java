package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.WorldRenderHooks;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Inject(method = "renderEntities", at = @At("HEAD"))
    private void systemdlcRenderEntitiesHead(
        MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate, Camera camera,
        RenderTickCounter renderTickCounter, List<Entity> list, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererMatrixStackImmediateCameraRenderTickCounterList2(
            (WorldRenderer)(Object)this, matrixStack, immediate, camera, renderTickCounter, list
        );        WorldRenderHooks.onWorldRendererMatrixStackImmediateCameraRenderTickCounterList4(
            (WorldRenderer)(Object)this, matrixStack, immediate, camera, renderTickCounter, list
        );
    }

    @Inject(method = "method_22710", at = @At("HEAD"))
    private void systemdlcRenderHead(
        ObjectAllocator allocator, RenderTickCounter rtc, boolean renderBlockOutline,
        Camera camera, GameRenderer gameRenderer, Matrix4f m1, Matrix4f m2, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererObjectAllocatorRenderTickCounterBooleanCameraGameRendererMatrix4fMatrix4f2(
            (WorldRenderer)(Object)this, allocator, rtc, renderBlockOutline, camera, gameRenderer, m1, m2
        );
    }

    @Inject(method = "method_3279", at = @At("HEAD"))
    private void systemdlcOnWorldRenderer(CallbackInfo ci) {
        WorldRenderHooks.onWorldRenderer((WorldRenderer)(Object)this);    }

    @Inject(method = "renderEntities", at = @At("TAIL"))
    private void systemdlcRenderEntitiesTail(
        MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate, Camera camera,
        RenderTickCounter renderTickCounter, List<Entity> list, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererMatrixStackImmediateCameraRenderTickCounterList(
            (WorldRenderer)(Object)this, matrixStack, immediate, camera, renderTickCounter, list
        );
        WorldRenderHooks.onWorldRendererMatrixStackImmediateCameraRenderTickCounterList3(
            (WorldRenderer)(Object)this, matrixStack, immediate, camera, renderTickCounter, list
        );
    }

    @Inject(method = "renderLayer", at = @At("HEAD"))
    private void systemdlcRenderLayerHead(
        RenderLayer renderLayer, double x, double y, double z, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererRenderLayerDoubleDoubleDoubleMatrix4fMatrix4f2(
            (WorldRenderer)(Object)this, renderLayer, x, y, z, matrix4f, matrix4f2
        );
    }

    @Inject(method = "renderLayer", at = @At("TAIL"))
    private void systemdlcRenderLayerTail(
        RenderLayer renderLayer, double x, double y, double z, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererRenderLayerDoubleDoubleDoubleMatrix4fMatrix4f(
            (WorldRenderer)(Object)this, renderLayer, x, y, z, matrix4f, matrix4f2
        );
    }

    @Inject(method = "method_62204", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderClouds(
        net.minecraft.client.render.FrameGraphBuilder frameGraphBuilder,
        Matrix4f m1,
        Matrix4f m2,
        net.minecraft.client.option.CloudRenderMode cloudRenderMode,
        net.minecraft.util.math.Vec3d cameraPos,
        float tickDelta,
        int count,
        float value2,
        CallbackInfo ci
    ) {
        if (!WorldRenderHooks.isWorldRendererFrameGraphBuilderMatrix4fMatrix4fCloudRenderModeVec3dFloatIntFloat(
            (WorldRenderer)(Object)this, frameGraphBuilder, m1, m2, cloudRenderMode, cameraPos, tickDelta, count, value2
        )) {
            ci.cancel();
        }
    }

    @Inject(method = "method_62208", at = @At("TAIL"))
    private void systemdlcRenderLate(
        MatrixStack matrices,
        VertexConsumerProvider.Immediate immediate,
        VertexConsumerProvider.Immediate immediate2,
        Camera camera,
        float tickDelta,
        CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererMatrixStackImmediateImmediateCameraFloat(
            (WorldRenderer)(Object)this, matrices, immediate, immediate2, camera, tickDelta
        );
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("HEAD"), cancellable = true)
    private void systemdlcDrawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (!WorldRenderHooks.isWorldRenderer((WorldRenderer)(Object)this)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderEntity", at = @At("TAIL"))
    private void systemdlcRenderEntityTail(
        Entity entity, double x, double y, double z, float yaw, MatrixStack matrixStack,
        VertexConsumerProvider vcp, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererEntityDoubleDoubleDoubleFloatMatrixStackVertexConsumerProvider(
            (WorldRenderer)(Object)this, entity, x, y, z, yaw, matrixStack, vcp
        );
    }

    @Inject(method = "getEntityOutlinesFramebuffer", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetEntityOutlinesFramebuffer(CallbackInfoReturnable<Framebuffer> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            Framebuffer fb = WorldRenderHooks.getFramebufferByWorldRenderer((WorldRenderer)(Object)this);
            if (fb != null) {
                cir.setReturnValue(fb);
            }
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_62214", at = @At("HEAD"))
    private void systemdlcRenderMainHead(
        net.minecraft.client.render.Fog fog,
        RenderTickCounter rtc,
        Camera camera,
        net.minecraft.util.profiler.Profiler profiler,
        Matrix4f m1,
        Matrix4f m2,
        net.minecraft.client.util.Handle h1,
        net.minecraft.client.util.Handle h2,
        net.minecraft.client.util.Handle h3,
        net.minecraft.client.util.Handle h4,
        boolean flag,
        Frustum frustum,
        net.minecraft.client.util.Handle h5,
        CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererFogRenderTickCounterCameraProfilerMatrix4fMatrix4fHandleHandleHandleHandleBooleanFrustumHandle2(
            (WorldRenderer)(Object)this, fog, rtc, camera, profiler, m1, m2, h1, h2, h3, h4, flag, frustum, h5
        );
    }

    @Inject(method = "method_62214", at = @At("TAIL"))
    private void systemdlcRenderMain(
        net.minecraft.client.render.Fog fog,
        RenderTickCounter rtc,
        Camera camera,
        net.minecraft.util.profiler.Profiler profiler,
        Matrix4f m1,
        Matrix4f m2,
        net.minecraft.client.util.Handle h1,
        net.minecraft.client.util.Handle h2,
        net.minecraft.client.util.Handle h3,
        net.minecraft.client.util.Handle h4,
        boolean flag,
        Frustum frustum,
        net.minecraft.client.util.Handle h5,
        CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererFogRenderTickCounterCameraProfilerMatrix4fMatrix4fHandleHandleHandleHandleBooleanFrustumHandle(
            (WorldRenderer)(Object)this, fog, rtc, camera, profiler, m1, m2, h1, h2, h3, h4, flag, frustum, h5
        );
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void systemdlcRenderTail(
        ObjectAllocator allocator, RenderTickCounter rtc, boolean renderBlockOutline,
        Camera camera, GameRenderer gameRenderer, Matrix4f m1, Matrix4f m2, CallbackInfo ci
    ) {
        WorldRenderHooks.onWorldRendererObjectAllocatorRenderTickCounterBooleanCameraGameRendererMatrix4fMatrix4f(
            (WorldRenderer)(Object)this, allocator, rtc, renderBlockOutline, camera, gameRenderer, m1, m2
        );
    }
}
