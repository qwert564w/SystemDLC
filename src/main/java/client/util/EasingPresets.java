package client.util;

import client.data.Tween;
import org.joml.Matrix4f;

public final class EasingPresets {
   public static final float value = 0.15F;
   public static final float value2 = 0.22F;
   public static final float value3 = 0.32F;
   public static final float value4 = 0.28F;
   public static final float value5 = 16.0F;
   public static final float value6 = 0.92F;
   public static final float value7 = 1.7F;

   private EasingPresets() {
   }

   public static Tween getTweenByFloatFloat(float value, float value2) {
      return new Tween(value, value2).getTweenByFunction(Easings::getFloatByFloat6);
   }

   public static float getFloatByFloat(float value) {
      return 16.0F * (1.0F - Math.clamp(value, 0.0F, 1.0F));
   }

   public static float getFloatByFloat2(float value) {
      return 0.92F + 0.07999998F * Math.clamp(value, 0.0F, 1.0F);
   }

   public static Tween getTweenByFloatFloat2(float value, float value2) {
      return new Tween(value, value2).getTweenByFunction(Easings::getFloatByFloat3);
   }

   public static Tween getTweenByFloat(float value) {
      return new Tween(value, 0.22F).getTweenByFunction(Easings::getFloatByFloat3);
   }

   public static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return value3 + (value - value3) * value2;
   }

   public static Tween getTween() {
      return new Tween(0.0F, 0.15F).getTweenByFunction(Easings::getFloatByFloat3);
   }

   public static Matrix4f getMatrix4fByMatrix4fFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3) {
      float f = getFloatByFloat2(value);
      return new Matrix4f(matrix4f).translate(value2, value3, 0.0F).scale(f, f, 1.0F).translate(-value2, -value3, 0.0F);
   }

   public static float getFloatByFloat3(float value) {
      float f = Math.clamp(value, 0.0F, 1.0F);
      return (float)Math.pow(f, 1.7F);
   }
}
