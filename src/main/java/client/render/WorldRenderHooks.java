package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.concurrent.ResourceManagerHooks;
import client.enums.InjectPoint;
import client.module.client.ThemeModule;
import client.module.render.ChunkAnimator;
import client.module.render.PlayerESP;
import client.module.visual.Bloom;
import client.module.visual.CustomFog;
import client.module.visual.MotionBlur;
import client.module.visual.NoRender;
import client.module.visual.Saturation;
import client.module.visual.SkyShader;
import client.util.ModuleDispatcher;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.WorldBorderRendering;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.joml.Matrix4f;

@HookClass(WorldRenderer.class)
public class WorldRenderHooks {
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<ChunkAnimator> unsafeAccess2 = new UnsafeAccess<>(ChunkAnimator.class);
   private static final UnsafeAccess<PlayerESP> unsafeAccess3 = new UnsafeAccess<>(PlayerESP.class);
   private static final UnsafeAccess<SkyShader> unsafeAccess4 = new UnsafeAccess<>(SkyShader::getInstance);
   private static final UnsafeAccess<CustomFog> unsafeAccess5 = new UnsafeAccess<>(CustomFog::getInstance);
   private static final long time = ReflectionCache.getLongByClassClass2(WorldRenderer.class, ClientWorld.class);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(EntityRenderDispatcher.class, boolean.class, 0);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(EntityRenderDispatcher.class, boolean.class, 1);
   private static final WorldRenderContext worldRenderContext = new WorldRenderContext();
   private static final MatrixStack matrixStack = new MatrixStack();
   private static boolean flag;
   private static boolean flag2;
   private static Entity entity;
   private static AbstractClientPlayerEntity abstractClientPlayerEntity;
   private static EntityRenderDispatcher entityRenderDispatcher;
   private static boolean flag3;
   private static boolean flag4;
   private static Framebuffer framebuffer;
   private static Framebuffer framebuffer2;
   private static Framebuffer framebuffer3;
   private static PlayerESP playerESP;
   private static boolean flag5;

   @Hook(
      method = "method_62207",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4184;Lnet/minecraft/class_9779;Ljava/util/List;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererMatrixStackImmediateCameraRenderTickCounterList(
      WorldRenderer worldRenderer, MatrixStack matrixStack, Immediate immediate, Camera camera, RenderTickCounter renderTickCounter, List list
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         try {
            ClientWorld clientworld = (ClientWorld)ReflectionCache.getObjectByObjectLong(worldRenderer, time);
            WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
               matrixStack, camera, renderTickCounter, clientworld
            );
            moduledispatcher.render2(worldrendercontext);
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_3254",
      desc = "()V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isWorldRenderer(WorldRenderer worldRenderer) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check14();
   }

   private static boolean check() {
      CustomFog customfog = (CustomFog)unsafeAccess5.getModule2();
      return customfog != null && customfog.check5() && customfog.getFloat2() < 32.0F;
   }

   @Hook(
      method = "method_62214",
      desc = "(Lnet/minecraft/class_9958;Lnet/minecraft/class_9779;Lnet/minecraft/class_4184;Lnet/minecraft/class_3695;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;ZLnet/minecraft/class_4604;Lnet/minecraft/class_9925;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererFogRenderTickCounterCameraProfilerMatrix4fMatrix4fHandleHandleHandleHandleBooleanFrustumHandle(
      WorldRenderer worldRenderer,
      Fog fog,
      RenderTickCounter renderTickCounter,
      Camera camera,
      Profiler profiler2,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      Handle handle,
      Handle handle2,
      Handle handle3,
      Handle handle4,
      boolean flag,
      Frustum frustum,
      Handle handle5
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         if (ResourceManagerHooks.isFlag3()) {
            
            matrixStack.push();
            try {
               ClientWorld clientworld = (ClientWorld)ReflectionCache.getObjectByObjectLong(worldRenderer, time);
               OverlayFramebuffers.update5();
               WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
                  matrixStack, camera, renderTickCounter, clientworld
               );
               moduledispatcher.render(worldrendercontext);
               moduledispatcher.render4(worldrendercontext);
            
            } finally {

               matrixStack.pop();

            }
         }
      }
   }

   @Hook(
      method = "method_3251",
      desc = "(Lnet/minecraft/class_1921;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererRenderLayerDoubleDoubleDoubleMatrix4fMatrix4f(
      WorldRenderer worldRenderer, RenderLayer renderLayer, double value, double value2, double value3, Matrix4f matrix4f, Matrix4f matrix4f2
   ) {
      ChunkAnimator chunkanimator = (ChunkAnimator)unsafeAccess2.getModule2();
      if (chunkanimator != null) {
         chunkanimator.setFlag();
      }
   }

   @Hook(
      method = "method_22710",
      desc = "(Lnet/minecraft/class_9922;Lnet/minecraft/class_9779;ZLnet/minecraft/class_4184;Lnet/minecraft/class_757;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererObjectAllocatorRenderTickCounterBooleanCameraGameRendererMatrix4fMatrix4f(
      WorldRenderer worldRenderer, ObjectAllocator objectAllocator, RenderTickCounter renderTickCounter, boolean flag, Camera camera, GameRenderer gameRenderer, Matrix4f matrix4f, Matrix4f matrix4f2
   ) {
      if (MotionBlur.check3()) {
         try {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            MotionBlur.onFramebuffer(MinecraftClient.getInstance().getFramebuffer());
            MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
         } catch (Throwable throwable5) {
         }
      }

      if (Bloom.isFlag2()) {
         try {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            Bloom.setFramebuffer(MinecraftClient.getInstance().getFramebuffer());
            MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
         } catch (Throwable throwable4) {
         }
      }

      if (Saturation.isFlag2()) {
         try {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            Saturation.setFramebuffer(MinecraftClient.getInstance().getFramebuffer());
            MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
         } catch (Throwable throwable3) {
         }
      }

      try {
         MinecraftClient minecraftclient1 = MinecraftClient.getInstance();
         PlayerESP playeresp = (PlayerESP)unsafeAccess3.getModule2();
         MinecraftClient minecraftclient = minecraftclient1;
         PlayerOutlineMaskEffect.onPlayerESPMinecraftClient(playeresp, minecraftclient);
      } catch (Throwable throwable2) {
      }

      try {
         ThemeModule thememodule = ThemeModule.getThemeModule();
         if (thememodule != null && thememodule.check4()) {
            MipmapCapture.getInt();
         }
      } catch (Throwable throwable1) {
      }

      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         try {
            ClientWorld clientworld = (ClientWorld)ReflectionCache.getObjectByObjectLong(worldRenderer, time);
            WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
               matrixStack, camera, renderTickCounter, clientworld
            );
            moduledispatcher.render3(worldrendercontext);
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_62207",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4184;Lnet/minecraft/class_9779;Ljava/util/List;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRendererMatrixStackImmediateCameraRenderTickCounterList2(
      WorldRenderer worldRenderer, MatrixStack matrixStack, Immediate immediate, Camera camera, RenderTickCounter renderTickCounter, List list
   ) {
      if (flag) {
         update();
      }

      PlayerESP playeresp = (PlayerESP)unsafeAccess3.getModule2();
      if (playeresp != null && playeresp.check7()) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         ClientPlayerEntity clientplayerentity = minecraftclient.player;
         if (minecraftclient.world != null && clientplayerentity != null && !minecraftclient.gameRenderer.isRenderingPanorama()) {
            Framebuffer framebufferx = minecraftclient.getFramebuffer();
            boolean flagx = playeresp.check5();
            boolean flag1 = !playeresp.check4();
            if (PlayerOutlineMaskEffect.isFramebufferBooleanBoolean(framebufferx, flag1, flagx)) {
               framebufferx.beginWrite(false);
               abstractClientPlayerEntity = clientplayerentity;
               flag2 = minecraftclient.options.getPerspective() == Perspective.FIRST_PERSON;
               entity = flag2 ? minecraftclient.getCameraEntity() : null;
               entityRenderDispatcher = minecraftclient.getEntityRenderDispatcher();
               framebuffer = framebufferx;
               framebuffer2 = PlayerOutlineMaskEffect.getSimpleFramebufferAsFramebuffer();
               framebuffer3 = PlayerOutlineMaskEffect.getSimpleFramebuffer2AsFramebuffer();
               playerESP = playeresp;
               flag5 = flagx;
               flag3 = UnsafeAccess.unsafe.getBoolean(entityRenderDispatcher, time2);
               flag4 = UnsafeAccess.unsafe.getBoolean(entityRenderDispatcher, time3);
               UnsafeAccess.unsafe.putBoolean(entityRenderDispatcher, time2, false);
               UnsafeAccess.unsafe.putBoolean(entityRenderDispatcher, time3, false);
               flag = framebuffer2 != null;
            }
         }
      }
   }

   @Hook(
      method = "method_62207",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4184;Lnet/minecraft/class_9779;Ljava/util/List;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererMatrixStackImmediateCameraRenderTickCounterList3(
      WorldRenderer worldRenderer, MatrixStack matrixStack, Immediate immediate, Camera camera, RenderTickCounter renderTickCounter, List list
   ) {
      if (flag) {
         update();
      }
   }

   @Hook(
      method = "method_22977",
      desc = "(Lnet/minecraft/class_1297;DDDFLnet/minecraft/class_4587;Lnet/minecraft/class_4597;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererEntityDoubleDoubleDoubleFloatMatrixStackVertexConsumerProvider(
      WorldRenderer worldRenderer, Entity entity2, double value, double value2, double value3, float value4, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider
   ) {
      try {
         onEntityDoubleDoubleDoubleFloatMatrixStack(entity2, value, value2, value3, value4, matrixStack);
      } finally {
         DepthState.render(matrixStack);
      }
   }

   private static void update() {
      flag = false;
      if (entityRenderDispatcher != null) {
         UnsafeAccess.unsafe.putBoolean(entityRenderDispatcher, time2, flag3);
         UnsafeAccess.unsafe.putBoolean(entityRenderDispatcher, time3, flag4);
      }

      if (framebuffer != null) {
         PlayerOutlineMaskEffect.onFramebuffer(framebuffer);
      }

      abstractClientPlayerEntity = null;
      entity = null;
      entityRenderDispatcher = null;
      framebuffer = null;
      framebuffer2 = null;
      framebuffer3 = null;
      playerESP = null;
   }

   @Hook(
      method = "method_22990",
      desc = "()Lnet/minecraft/class_276;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Framebuffer getFramebufferByWorldRenderer(WorldRenderer worldRenderer) {
      if (FramebufferSwap.isFlag()) {
         Framebuffer framebufferx = FramebufferSwap.getFramebuffer();
         if (framebufferx == null) {
            framebufferx = PlayerOutlineMaskEffect.getSimpleFramebufferAsFramebuffer();
         }

         if (framebufferx != null) {
            return framebufferx;
         }
      }

      return (Framebuffer)HandleInvoker.getObjectByObjectArray2(worldRenderer);
   }

   @Hook(
      target = SkyRendering.class,
      method = "method_62306",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;FI)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSkyRenderingMatrixStackImmediateFloatInt(SkyRendering skyRendering, MatrixStack matrixStack, Immediate immediate, float value, int count) {
      return unsafeAccess4.getModule2() == null && !check();
   }

   @Hook(
      target = SkyRendering.class,
      method = "method_62305",
      desc = "(Lnet/minecraft/class_4587;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSkyRenderingMatrixStack(SkyRendering skyRendering, MatrixStack matrixStack) {
      return unsafeAccess4.getModule2() == null && !check();
   }

   @Hook(
      method = "method_62204",
      desc = "(Lnet/minecraft/class_9909;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/class_4063;Lnet/minecraft/class_243;FIF)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isWorldRendererFrameGraphBuilderMatrix4fMatrix4fCloudRenderModeVec3dFloatIntFloat(
      WorldRenderer worldRenderer, FrameGraphBuilder frameGraphBuilder, Matrix4f matrix4f, Matrix4f matrix4f2, CloudRenderMode cloudRenderMode, Vec3d vec3d, float value, int count, float value2
   ) {
      return unsafeAccess4.getModule2() == null && !check();
   }

   @Hook(
      method = "method_62208",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4184;F)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWorldRendererMatrixStackImmediateImmediateCameraFloat(
      WorldRenderer worldRenderer, MatrixStack matrixStack, Immediate immediate, Immediate immediate2, Camera camera, float value
   ) {
      try {
         ChamsRenderHooks.render(matrixStack, camera, value);
      } catch (Throwable throwable) {
      } finally {
         DepthState.render(matrixStack);
      }
   }

   @Hook(
      target = SkyRendering.class,
      method = "method_62307",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;FIFFLnet/minecraft/class_9958;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSkyRenderingMatrixStackImmediateFloatIntFloatFloatFog(
      SkyRendering skyRendering, MatrixStack matrixStack, Immediate immediate, float value, int count, float value2, float value3, Fog fog
   ) {
      return unsafeAccess4.getModule2() == null && !check();
   }

   @Hook(
      target = SkyRendering.class,
      method = "method_62302",
      desc = "(FFF)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSkyRenderingFloatFloatFloat(SkyRendering skyRendering, float value, float value2, float value3) {
      SkyShader skyshader = (SkyShader)unsafeAccess4.getModule2();
      if (skyshader != null) {
         skyshader.update11();
         return false;
      } else {
         return !check();
      }
   }

   private static void onEntityDoubleDoubleDoubleFloatMatrixStack(Entity entity2, double value, double value2, double value3, float value4, MatrixStack matrixStack) {
      if (flag) {
         if (entity2 instanceof AbstractClientPlayerEntity abstractclientplayerentity) {
            if (playerESP != null && framebuffer2 != null) {
               if (abstractclientplayerentity != abstractClientPlayerEntity
                  && abstractclientplayerentity.isAlive()
                  && !abstractclientplayerentity.isRemoved()
                  && !abstractclientplayerentity.isSpectator()) {
                  if (!flag2 || abstractclientplayerentity != entity) {
                     boolean flagx = playerESP.isFriend(abstractclientplayerentity);
                     if (!flagx || flag5) {
                        if (playerESP.isPlayerEntity4(abstractclientplayerentity)) {
                           boolean flag1 = flagx && framebuffer3 != null;
                           Framebuffer framebufferx = flag1 ? framebuffer3 : framebuffer2;
                           double d0 = MathHelper.lerp(value4, abstractclientplayerentity.lastRenderX, abstractclientplayerentity.getX());
                           double d1 = MathHelper.lerp(value4, abstractclientplayerentity.lastRenderY, abstractclientplayerentity.getY());
                           double d2 = MathHelper.lerp(value4, abstractclientplayerentity.lastRenderZ, abstractclientplayerentity.getZ());
                           LayeredBufferSource layeredbuffersource = LayeredBufferSource.getInstance();
                           FramebufferSwap.setFramebuffer(framebufferx);
                           framebufferx.beginWrite(false);

                           try {
                              entityRenderDispatcher.render(
                                 abstractclientplayerentity,
                                 d0 - value,
                                 d1 - value2,
                                 d2 - value3,
                                 value4,
                                 matrixStack,
                                 layeredbuffersource,
                                 entityRenderDispatcher.getLight(abstractclientplayerentity, value4)
                              );
                              layeredbuffersource.update();
                              if (flag1) {
                                 PlayerOutlineMaskEffect.setFlag2();
                              } else {
                                 PlayerOutlineMaskEffect.setFlag();
                              }
                           } catch (Throwable throwable) {
                           } finally {
                              FramebufferSwap.update();
                              framebuffer.beginWrite(false);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Hook(
      target = WeatherRendering.class,
      method = "method_62316",
      desc = "(Lnet/minecraft/class_1937;Lnet/minecraft/class_4597;IFLnet/minecraft/class_243;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isWeatherRenderingWorldVertexConsumerProviderIntFloatVec3d(
      WeatherRendering weatherRendering, World world2, VertexConsumerProvider vertexConsumerProvider, int count, float value, Vec3d vec3d
   ) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check19();
   }

   @Hook(
      target = MinecraftClient.class,
      method = "method_1588",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean check2() {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check5();
   }

   @Hook(
      target = MinecraftClient.class,
      method = "method_27022",
      desc = "(Lnet/minecraft/class_1297;)Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isMinecraftClientEntity(MinecraftClient minecraftClient, Entity entity2) {
      return true;
   }

   @Hook(
      method = "method_3270",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isWorldRenderer2(WorldRenderer worldRenderer) {
      return true;
   }

   @Hook(
      target = GlUniform.class,
      method = "method_1249",
      desc = "(FFF)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onGlUniformFloatFloatFloat(GlUniform glUniform, float value, float value2, float value3) {
      ChunkAnimator chunkanimator = (ChunkAnimator)unsafeAccess2.getModule2();
      if (chunkanimator != null) {
         float[] afloat = chunkanimator.getFloatArrayByFloatFloatFloat(value2, value3, value);
         if (afloat != null) {
            HandleInvoker.onObjectArray(glUniform, afloat[0], afloat[1], afloat[2]);
            return;
         }
      }

      HandleInvoker.onObjectArray(glUniform, value, value2, value3);
   }

   @Hook(
      method = "method_3279",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRenderer(WorldRenderer worldRenderer) {
      ChunkAnimator chunkanimator = (ChunkAnimator)unsafeAccess2.getModule2();
      if (chunkanimator != null) {
         chunkanimator.update11();
      }
   }

   @Hook(
      method = "method_22710",
      desc = "(Lnet/minecraft/class_9922;Lnet/minecraft/class_9779;ZLnet/minecraft/class_4184;Lnet/minecraft/class_757;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRendererObjectAllocatorRenderTickCounterBooleanCameraGameRendererMatrix4fMatrix4f2(
      WorldRenderer worldRenderer, ObjectAllocator objectAllocator, RenderTickCounter renderTickCounter, boolean flag, Camera camera, GameRenderer gameRenderer, Matrix4f matrix4f, Matrix4f matrix4f2
   ) {
      try {
         Saturation.setObjectAllocator(objectAllocator);
         Bloom.setObjectAllocator(objectAllocator);
         MotionBlur.onMatrix4fObjectAllocatorMatrix4fCamera(matrix4f, objectAllocator, matrix4f2, camera);
      } catch (Throwable throwable2) {
      }

      try {
         ChamsRenderHooks.update();
      } catch (Throwable throwable1) {
      }

      PlayerOutlineMaskEffect.update3();
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         try {
            moduledispatcher.update5();
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_3251",
      desc = "(Lnet/minecraft/class_1921;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRendererRenderLayerDoubleDoubleDoubleMatrix4fMatrix4f2(
      WorldRenderer worldRenderer, RenderLayer renderLayer, double value, double value2, double value3, Matrix4f matrix4f, Matrix4f matrix4f2
   ) {
      ChunkAnimator chunkanimator = (ChunkAnimator)unsafeAccess2.getModule2();
      if (chunkanimator != null) {
         chunkanimator.onDoubleDoubleDouble(value3, value, value2);
      }
   }

   @Hook(
      method = "method_62207",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4184;Lnet/minecraft/class_9779;Ljava/util/List;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRendererMatrixStackImmediateCameraRenderTickCounterList4(
      WorldRenderer worldRenderer, MatrixStack matrixStack, Immediate immediate, Camera camera, RenderTickCounter renderTickCounter, List list
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         try {
            ClientWorld clientworld = (ClientWorld)ReflectionCache.getObjectByObjectLong(worldRenderer, time);
            WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
               matrixStack, camera, renderTickCounter, clientworld
            );
            moduledispatcher.render6(worldrendercontext);
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_62214",
      desc = "(Lnet/minecraft/class_9958;Lnet/minecraft/class_9779;Lnet/minecraft/class_4184;Lnet/minecraft/class_3695;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;Lnet/minecraft/class_9925;ZLnet/minecraft/class_4604;Lnet/minecraft/class_9925;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onWorldRendererFogRenderTickCounterCameraProfilerMatrix4fMatrix4fHandleHandleHandleHandleBooleanFrustumHandle2(
      WorldRenderer worldRenderer,
      Fog fog,
      RenderTickCounter renderTickCounter,
      Camera camera,
      Profiler profiler2,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      Handle handle,
      Handle handle2,
      Handle handle3,
      Handle handle4,
      boolean flag,
      Frustum frustum,
      Handle handle5
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null) {
         try {
            RotationBuffer.onMatrix4fMatrix4f(matrix4f, matrix4f2);
         } catch (Throwable throwable1) {
         }

         
         matrixStack.push();
         try {
            ClientWorld clientworld = (ClientWorld)ReflectionCache.getObjectByObjectLong(worldRenderer, time);
            WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
               matrixStack, camera, renderTickCounter, clientworld
            );
            moduledispatcher.render5(worldrendercontext);
         
         } finally {

            matrixStack.pop();

         }
      }
   }

   @Hook(
      target = net.minecraft.client.gl.ShaderLoader.class,
      method = "method_62941",
      desc = "(Lnet/minecraft/class_2960;Ljava/util/Set;)Lnet/minecraft/class_279;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static PostEffectProcessor getPostEffectProcessorByShaderLoaderIdentifierSet(net.minecraft.client.gl.ShaderLoader shaderLoader, Identifier identifier, Set set) {
      PostEffectProcessor posteffectprocessor = (PostEffectProcessor)HandleInvoker.getObjectByObjectArray2(shaderLoader, identifier, set);
      if (posteffectprocessor != null && identifier != null && "minecraft".equals(identifier.getNamespace()) && "saturation".equals(identifier.getPath())) {
         try {
            Saturation.onPostEffectProcessor(posteffectprocessor);
         } catch (Throwable throwable) {
         }
      }

      return posteffectprocessor;
   }

   @Hook(
      target = WorldBorderRendering.class,
      method = "method_62322",
      desc = "(Lnet/minecraft/class_2784;Lnet/minecraft/class_243;DD)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isWorldBorderRenderingWorldBorderVec3dDoubleDouble(WorldBorderRendering worldBorderRendering, WorldBorder worldBorder, Vec3d vec3d, double value, double value2) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check22();
   }
}
