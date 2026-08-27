package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.CustomFog;
import client.module.visual.NoRender;
import client.util.UnsafeAccess;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Vector4f;

@HookClass(BackgroundRenderer.class)
public class FogHooks {
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<CustomFog> unsafeAccess2 = new UnsafeAccess<>(CustomFog::getInstance);

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return value2 + (value3 - value2) * value;
   }

   private static void onCameraStatusEffectInstance(Camera camera, StatusEffectInstance statusEffectInstance) {
      if (statusEffectInstance != null) {
         if (camera.getFocusedEntity() instanceof LivingEntity livingentity) {
            livingentity.getActiveStatusEffects().put(StatusEffects.DARKNESS, statusEffectInstance);
         }
      }
   }

   private static StatusEffectInstance getStatusEffectInstanceByCamera(Camera camera) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      if (norender != null && norender.check3()) {
         if (camera.getFocusedEntity() instanceof LivingEntity livingentity) {
            RegistryEntry registryentry = StatusEffects.DARKNESS;
            return (StatusEffectInstance)livingentity.getActiveStatusEffects().remove(registryentry);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Hook(
      method = "method_62185",
      desc = "(Lnet/minecraft/class_4184;FLnet/minecraft/class_638;IF)Lorg/joml/Vector4f;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Vector4f getVector4fByCameraFloatClientWorldIntFloat(Camera camera, float value, ClientWorld clientWorld, int count, float value2) {
      StatusEffectInstance statuseffectinstance = getStatusEffectInstanceByCamera(camera);

      Vector4f vector4f;
      try {
         vector4f = (Vector4f)HandleInvoker.getObjectByObjectArray2(camera, value, clientWorld, count, value2);
      } finally {
         onCameraStatusEffectInstance(camera, statuseffectinstance);
      }

      CustomFog customfog = (CustomFog)unsafeAccess2.getModule2();
      if (customfog != null && customfog.check3() && !isCameraCustomFog(camera, customfog)) {
         float f = customfog.getFloat4();
         vector4f.x = getFloatByFloatFloatFloat(f, vector4f.x, customfog.getFloat3());
         vector4f.y = getFloatByFloatFloatFloat(f, vector4f.y, customfog.getFloat5());
         vector4f.z = getFloatByFloatFloatFloat(f, vector4f.z, customfog.getFloat());
      }

      return vector4f;
   }

   private static boolean isCameraCustomFog(Camera camera, CustomFog customFog) {
      if (!customFog.check4()) {
         return false;
      } else {
         CameraSubmersionType camerasubmersiontype = camera.getSubmersionType();
         return camerasubmersiontype == CameraSubmersionType.WATER
            || camerasubmersiontype == CameraSubmersionType.LAVA
            || camerasubmersiontype == CameraSubmersionType.POWDER_SNOW;
      }
   }

   @Hook(
      method = "method_3211",
      desc = "(Lnet/minecraft/class_4184;Lnet/minecraft/class_758$class_4596;Lorg/joml/Vector4f;FZF)Lnet/minecraft/class_9958;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Fog getFogByCameraFogTypeVector4fFloatBooleanFloat(Camera camera, FogType fogType, Vector4f vector4f, float value, boolean flag, float value2) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      if (norender != null && norender.check20()) {
         return Fog.DUMMY;
      } else {
         StatusEffectInstance statuseffectinstance = getStatusEffectInstanceByCamera(camera);

         Fog fog;
         try {
            fog = (Fog)HandleInvoker.getObjectByObjectArray2(camera, fogType, vector4f, value, flag, value2);
         } finally {
            onCameraStatusEffectInstance(camera, statuseffectinstance);
         }

         CustomFog customfog = (CustomFog)unsafeAccess2.getModule2();
         if (customfog == null) {
            return fog;
         } else if (isCameraCustomFog(camera, customfog)) {
            return fog;
         } else {
            float f = fog.start();
            float f1 = fog.end();
            float f2 = fog.red();
            float f3 = fog.green();
            float f4 = fog.blue();
            FogShape fogshape = fog.shape();
            if (customfog.check5()) {
               f1 = customfog.getFloat2();
               f = Math.min(customfog.getFloat6(), f1 - 0.1F);
            }

            if (customfog.check3()) {
               float f5 = customfog.getFloat4();
               f2 = getFloatByFloatFloatFloat(f5, f2, customfog.getFloat3());
               f3 = getFloatByFloatFloatFloat(f5, f3, customfog.getFloat5());
               f4 = getFloatByFloatFloatFloat(f5, f4, customfog.getFloat());
            }

            return new Fog(f, f1, fogshape, f2, f3, f4, fog.alpha());
         }
      }
   }
}
