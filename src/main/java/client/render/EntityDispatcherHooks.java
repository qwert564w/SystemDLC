package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.render.PlayerChams;
import client.module.render.ShowInvisible;
import client.module.visual.HitboxChecks;
import client.module.visual.NoRender;
import client.transform.MethodIndex;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.lang.invoke.MethodHandle;
import java.util.Map;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.SkinTextures.Model;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

@HookClass(EntityRenderDispatcher.class)
public class EntityDispatcherHooks {
   private static final long time = ReflectionCache.getLongByClassClass2(EntityRenderDispatcher.class, World.class);
   private static final long time2 = ReflectionCache.getLongByClassClass2(EntityRenderDispatcher.class, Quaternionf.class);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(EntityRenderDispatcher.class, boolean.class, 0);
   private static final long time4 = ReflectionCache.getLongByClassClassInt(EntityRenderDispatcher.class, boolean.class, 1);
   private static final MethodHandle methodHandle = MethodIndex.getMethodHandleByClassInt(EntityRenderDispatcher.class, 16);
   private static final MethodHandle methodHandle2 = MethodIndex.getMethodHandleByClassInt(EntityRenderDispatcher.class, 13);
   private static final MethodHandle methodHandle3 = MethodIndex.getMethodHandleByClassInt(EntityRenderDispatcher.class, 14);
   private static final Quaternionf quaternionf = new Quaternionf();
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<ShowInvisible> unsafeAccess2 = new UnsafeAccess<>(ShowInvisible::getInstance);
   private static final UnsafeAccess<HitboxChecks> unsafeAccess3 = new UnsafeAccess<>(HitboxChecks.class);
   private static final UnsafeAccess<PlayerChams> unsafeAccess4 = new UnsafeAccess<>(PlayerChams::getInstance);

   private static PlayerEntityRenderer getPlayerEntityRendererByContextBoolean(Context context, boolean flag) {
      PlayerEntityRenderer playerentityrenderer = new PlayerEntityRenderer(context, flag);
      PlayerNameTagHooks.onPlayerEntityRendererContextBoolean(playerentityrenderer, context, flag);
      return playerentityrenderer;
   }

   @Hook(
      method = "method_3954",
      desc = "(Lnet/minecraft/class_1297;DDDFLnet/minecraft/class_4587;Lnet/minecraft/class_4597;ILnet/minecraft/class_897;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onEntityRenderDispatcherEntityDoubleDoubleDoubleFloatMatrixStackVertexConsumerProviderIntEntityRenderer(
      EntityRenderDispatcher entityRenderDispatcher,
      Entity entity2,
      double value,
      double value2,
      double value3,
      float value4,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int count,
      EntityRenderer entityRenderer
   ) {
      try {
         NoRender norender = (NoRender)unsafeAccess.getModule2();
         ShowInvisible showinvisible = (ShowInvisible)unsafeAccess2.getModule2();
         HitboxChecks hitboxchecks = (HitboxChecks)unsafeAccess3.getModule2();
         PlayerChams playerchams = (PlayerChams)unsafeAccess4.getModule2();
         EntityRenderState entityrenderstate = entityRenderer.getAndUpdateRenderState(entity2, value4);
         Vec3d vec3d = entityRenderer.getPositionOffset(entityrenderstate);
         double d0 = value + vec3d.getX();
         double d1 = value2 + vec3d.getY();
         double d2 = value3 + vec3d.getZ();
         boolean flag = entity2 instanceof PlayerEntity playerentity && playerchams != null && playerentity.isAlive() && playerentity != Feature.mc.player;
         Immediate immediate = flag ? Feature.mc.getBufferBuilders().getEntityVertexConsumers() : null;
         if (entity2 instanceof EndCrystalEntity) {
            EndCrystalRenderHook.entity = entity2;
         }

         matrixStack.push();

         try {
            matrixStack.translate(d0, d1, d2);
            if (flag) {
               immediate.draw();
               GL11.glDepthRange(0.0, 0.01);

               try {
                  entityRenderer.render(entityrenderstate, matrixStack, immediate, count);
               } finally {
                  immediate.draw();
                  GL11.glDepthRange(0.0, 1.0);
               }
            } else {
               entityRenderer.render(entityrenderstate, matrixStack, vertexConsumerProvider, count);
            }

            if (entityrenderstate.onFire) {
               Quaternionf quaternionfx = (Quaternionf)ReflectionCache.getObjectByObjectLong(entityRenderDispatcher, time2);
               if (quaternionfx != null && methodHandle3 != null) {
                  methodHandle3.invoke(
                     (EntityRenderDispatcher)entityRenderDispatcher,
                     (MatrixStack)matrixStack,
                     (VertexConsumerProvider)vertexConsumerProvider,
                     (EntityRenderState)entityrenderstate,
                     (Quaternionf)MathHelper.rotateAround(MathHelper.Y_AXIS, quaternionfx, quaternionf)
                  );
               }
            }

            if (entity2 instanceof PlayerEntity) {
               matrixStack.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
            }

            boolean flag2 = UnsafeAccess.unsafe.getBoolean(entityRenderDispatcher, time3);
            boolean flag1 = norender != null && norender.check5();
            if (!flag1 && (Boolean)entityRenderDispatcher.gameOptions.getEntityShadows().getValue() && flag2 && !entityrenderstate.invisible) {
               float f = 0.15F;
               double d3 = entityrenderstate.squaredDistanceToCamera;
               float f1 = (float)((1.0 - d3 / 256.0) * 1.0);
               if (f1 > 0.0F && methodHandle != null) {
                  World world = (World)ReflectionCache.getObjectByObjectLong(entityRenderDispatcher, time);
                  methodHandle.invoke(
                     (MatrixStack)matrixStack, (VertexConsumerProvider)vertexConsumerProvider, (EntityRenderState)entityrenderstate, (float)f1, (float)value4, (WorldView)world, (float)f
                  );
               }
            }

            if (!(entity2 instanceof PlayerEntity)) {
               matrixStack.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
            }

            boolean flag3 = UnsafeAccess.unsafe.getBoolean(entityRenderDispatcher, time4);
            boolean flag4 = false;
            if (entity2 instanceof PlayerEntity && entityrenderstate.invisible) {
               flag4 = showinvisible != null && showinvisible.check3();
            }

            if ((flag3 && !entityrenderstate.invisible || flag4) && !Feature.mc.hasReducedDebugInfo()) {
               if (hitboxchecks != null) {
                  hitboxchecks.onEntityMatrixStackFloatVertexConsumerProvider(entity2, matrixStack, value4, vertexConsumerProvider);
               } else if (methodHandle2 != null) {
                  methodHandle2.invoke(
                     (MatrixStack)matrixStack,
                     (VertexConsumer)vertexConsumerProvider.getBuffer(RenderLayer.getLines()),
                     (Entity)entity2,
                     (float)value4,
                     (float)1.0F,
                     (float)1.0F,
                     (float)1.0F
                  );
               }
            }
         } finally {
            matrixStack.pop();
         }
      } catch (Throwable throwable) {
      }
   }

   @Hook(
      method = "method_3950",
      desc = "(Lnet/minecraft/class_1297;Lnet/minecraft/class_4604;DDD)Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isEntityRenderDispatcherEntityFrustumDoubleDoubleDouble(
      EntityRenderDispatcher entityRenderDispatcher, Entity entity2, Frustum frustum, double value, double value2, double value3
   ) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check13() || !(entity2 instanceof ExperienceBottleEntity);
   }

   @Hook(
      target = EntityRenderers.class,
      method = "method_32177",
      desc = "(Lnet/minecraft/class_5617$class_5618;)Ljava/util/Map;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Map getMapByContext(Context context) {
      Builder builder = ImmutableMap.builder();
      builder.put(Model.WIDE, getPlayerEntityRendererByContextBoolean(context, false));
      builder.put(Model.SLIM, getPlayerEntityRendererByContextBoolean(context, true));
      return builder.build();
   }
}
