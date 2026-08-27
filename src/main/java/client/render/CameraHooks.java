package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.CameraChecks;
import client.module.visual.FreeLook;
import client.transform.MethodIndex;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import java.lang.reflect.Method;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.BlockView;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@HookClass(Camera.class)
public class CameraHooks {
   private static final UnsafeAccess<CameraChecks> unsafeAccess = new UnsafeAccess<>(CameraChecks.class);
   private static final UnsafeAccess<FreeLook> unsafeAccess2 = new UnsafeAccess<>(FreeLook.class);
   private static final long time = ReflectionCache.getLongByClassClassInt(Camera.class, boolean.class, 0);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(Camera.class, boolean.class, 1);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(Camera.class, float.class, 0);
   private static final long time4 = ReflectionCache.getLongByClassClassInt(Camera.class, float.class, 1);
   private static final long time5 = ReflectionCache.getLongByClassClassInt(Camera.class, float.class, 2);
   private static final long time6 = ReflectionCache.getLongByClassClassInt(Camera.class, float.class, 3);
   private static final long time7 = ReflectionCache.getLongByClassClassInt(Camera.class, float.class, 4);
   private static final long time8 = ReflectionCache.getLongByClassClass2(Camera.class, BlockView.class);
   private static final long time9 = ReflectionCache.getLongByClassClass2(Camera.class, Entity.class);
   private static final long time10 = ReflectionCache.getLongByClassClass2(Camera.class, Vec3d.class);
   private static final long time11 = ReflectionCache.getLongByClassClass2(Camera.class, Mutable.class);
   private static final long time12 = ReflectionCache.getLongByClassClassInt(Camera.class, Vector3f.class, 0);
   private static final long time13 = ReflectionCache.getLongByClassClassInt(Camera.class, Vector3f.class, 1);
   private static final long time14 = ReflectionCache.getLongByClassClassInt(Camera.class, Vector3f.class, 2);
   private static final long time15 = ReflectionCache.getLongByClassClass2(Camera.class, Quaternionf.class);
   private static final Method method = MethodIndex.getMethodByClassInt(Camera.class, 3);

   private static float getFloatByCameraFloat(Camera camera, float value) {
      try {
         return (Float)method.invoke(camera, value);
      } catch (Exception exception) {
         return value;
      }
   }

   private static void onCameraVec3d(Camera camera, Vec3d vec3d) {
      UnsafeAccess.unsafe.putObject(camera, time10, vec3d);
      Mutable mutable = (Mutable)ReflectionCache.getObjectByObjectLong(camera, time11);
      mutable.set(vec3d.x, vec3d.y, vec3d.z);
   }

   private static void onCameraFloatFloatFloat(Camera camera, float value, float value2, float value3) {
      Quaternionf quaternionf = (Quaternionf)ReflectionCache.getObjectByObjectLong(camera, time15);
      Vector3f vector3f = new Vector3f(value3, value2, -value).rotate(quaternionf);
      Vec3d vec3d = (Vec3d)ReflectionCache.getObjectByObjectLong(camera, time10);
      onCameraVec3d(camera, new Vec3d(vec3d.x + vector3f.x, vec3d.y + vector3f.y, vec3d.z + vector3f.z));
   }

   @Hook(
      method = "method_19318",
      desc = "(F)F",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static float getFloatByCameraFloat2(Camera camera, float value) {
      CameraChecks camerachecks = (CameraChecks)unsafeAccess.getModule2();
      return camerachecks == null ? HandleInvoker.getFloatByObjectArray(camera, value) : camerachecks.getFloat() * value / 4.0F;
   }

   private static void onCameraFloatFloat(Camera camera, float value, float value2) {
      UnsafeAccess.unsafe.putFloat(camera, time3, value2);
      UnsafeAccess.unsafe.putFloat(camera, time4, value);
      Quaternionf quaternionf = (Quaternionf)ReflectionCache.getObjectByObjectLong(camera, time15);
      quaternionf.rotationYXZ((float) Math.PI - value * (float) (Math.PI / 180.0), -value2 * (float) (Math.PI / 180.0), 0.0F);
      Vector3f vector3f = (Vector3f)ReflectionCache.getObjectByObjectLong(camera, time12);
      Vector3f vector3f1 = (Vector3f)ReflectionCache.getObjectByObjectLong(camera, time13);
      Vector3f vector3f2 = (Vector3f)ReflectionCache.getObjectByObjectLong(camera, time14);
      new Vector3f(0.0F, 0.0F, -1.0F).rotate(quaternionf, vector3f);
      new Vector3f(0.0F, 1.0F, 0.0F).rotate(quaternionf, vector3f1);
      new Vector3f(-1.0F, 0.0F, 0.0F).rotate(quaternionf, vector3f2);
   }

   @Hook(
      method = "method_19321",
      desc = "(Lnet/minecraft/class_1922;Lnet/minecraft/class_1297;ZZF)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onCameraBlockViewEntityBooleanBooleanFloat(Camera camera, BlockView blockView, Entity entity2, boolean flag, boolean flag2, float value) {
      FreeLook freelook = (FreeLook)unsafeAccess2.getModule2();
      if (freelook != null && freelook.check3()) {
         UnsafeAccess.unsafe.putBoolean(camera, time, true);
         UnsafeAccess.unsafe.putObject(camera, time8, blockView);
         UnsafeAccess.unsafe.putObject(camera, time9, entity2);
         UnsafeAccess.unsafe.putBoolean(camera, time2, flag);
         UnsafeAccess.unsafe.putFloat(camera, time7, value);
         float f = MathHelper.lerp(value, UnsafeAccess.unsafe.getFloat(camera, time6), UnsafeAccess.unsafe.getFloat(camera, time5));
         Vec3d vec3d;
         if (entity2.hasVehicle()
            && entity2.getVehicle() instanceof MinecartEntity minecartentity
            && minecartentity.getController() instanceof ExperimentalMinecartController experimentalminecartcontroller
            && experimentalminecartcontroller.hasCurrentLerpSteps()) {
            Vec3d vec3d1 = minecartentity.getPassengerRidingPos(entity2)
               .subtract(minecartentity.getPos())
               .subtract(entity2.getVehicleAttachmentPos(minecartentity))
               .add(new Vec3d(0.0, f, 0.0));
            vec3d = experimentalminecartcontroller.getLerpedPosition(value).add(vec3d1);
         } else {
            vec3d = new Vec3d(
               MathHelper.lerp(value, entity2.prevX, entity2.getX()),
               MathHelper.lerp(value, entity2.prevY, entity2.getY()) + f,
               MathHelper.lerp(value, entity2.prevZ, entity2.getZ())
            );
         }

         onCameraFloatFloat(camera, freelook.getValue237(), freelook.getValue238());
         onCameraVec3d(camera, vec3d);
         if (flag) {
            float f1 = entity2 instanceof LivingEntity livingentity1 ? livingentity1.getScale() : 1.0F;
            onCameraFloatFloatFloat(camera, -getFloatByCameraFloat(camera, 4.0F * f1), 0.0F, 0.0F);
         } else if (entity2 instanceof LivingEntity livingentity && livingentity.isSleeping()) {
            Direction direction = livingentity.getSleepingDirection();
            onCameraFloatFloat(camera, direction != null ? direction.getPositiveHorizontalDegrees() - 180.0F : 0.0F, 0.0F);
            onCameraFloatFloatFloat(camera, 0.0F, 0.3F, 0.0F);
         }
      } else {
         HandleInvoker.onObjectArray(camera, blockView, entity2, flag, flag2, value);
      }
   }
}
