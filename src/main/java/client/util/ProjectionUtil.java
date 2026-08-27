package client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public final class ProjectionUtil {
   private static final Matrix4f matrix4f = new Matrix4f();
   private static final Matrix4f matrix4f2 = new Matrix4f();
   private static Vec3d vec3d = Vec3d.ZERO;
   private static boolean flag;

   private ProjectionUtil() {
   }

   public static Vec3d getVec3dByVec3d(Vec3d vec3d) {
      if (!flag) {
         return null;
      } else {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient == null) {
            return null;
         } else {
            float f3 = (float)vec3d.x;
            float f4 = (float)vec3d.y;
            float f2 = (float)vec3d.z;
            float f1 = f4;
            float f = f3;
            return getVec3dByFloatFloatMinecraftClientFloat(f1, f2, minecraftclient, f);
         }
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   public static Vec3d getVec3dByFloatFloatMinecraftClientFloat(float value, float value2, MinecraftClient minecraftClient, float value3) {
      if (flag && minecraftClient != null) {
         Vector4f vector4f = new Vector4f(value3, value, value2, 1.0F);
         matrix4f2.transform(vector4f);
         matrix4f.transform(vector4f);
         if (vector4f.w <= 0.0F) {
            return null;
         } else {
            float f = 1.0F / vector4f.w;
            float f1 = vector4f.x * f;
            float f2 = vector4f.y * f;
            if (!(f1 < -1.05F) && !(f1 > 1.05F) && !(f2 < -1.05F) && !(f2 > 1.05F)) {
               int i = minecraftClient.getWindow().getScaledWidth();
               int j = minecraftClient.getWindow().getScaledHeight();
               float f3 = (f1 + 1.0F) * 0.5F * i;
               float f4 = (1.0F - f2) * 0.5F * j;
               return new Vec3d(f3, f4, vector4f.w);
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public static Vec3d getVec3dByVec3d2(Vec3d vec3d2) {
      if (!flag) {
         return null;
      } else {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient == null) {
            return null;
         } else {
            float f3 = (float)(vec3d2.x - vec3d.x);
            float f4 = (float)(vec3d2.y - vec3d.y);
            float f2 = (float)(vec3d2.z - vec3d.z);
            float f1 = f4;
            float f = f3;
            return getVec3dByFloatFloatMinecraftClientFloat(f1, f2, minecraftclient, f);
         }
      }
   }

   public static void setVec3d(Vec3d vec3d2) {
      matrix4f.set(RenderSystem.getProjectionMatrix());
      matrix4f2.set(RenderSystem.getModelViewMatrix());
      vec3d = vec3d2;
      flag = true;
   }

   public static Vec3d getVec3d() {
      return vec3d;
   }
}
