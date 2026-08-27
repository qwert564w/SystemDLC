package client.util;

public final class Easings {
   private Easings() {
   }

   public static float getFloatByFloat(float value) {
      if (value < 0.5F) {
         return 16.0F * value * value * value * value * value;
      } else {
         float f = -2.0F * value + 2.0F;
         return 1.0F - f * f * f * f * f * 0.5F;
      }
   }

   public static float getFloatByFloat2(float value) {
      float f = 1.70158F;
      float f1 = f + 1.0F;
      return f1 * value * value * value - f * value * value;
   }

   public static float getFloatByFloat3(float value) {
      float f = 1.0F - value;
      return 1.0F - f * f * f;
   }

   public static float getFloatByFloat4(float value) {
      return value * value * value;
   }

   public static float getFloatByFloat5(float value) {
      float f = 1.70158F;
      float f1 = f + 1.0F;
      float f2 = value - 1.0F;
      return 1.0F + f1 * f2 * f2 * f2 + f * f2 * f2;
   }

   public static float getFloatByFloat6(float value) {
      return value >= 1.0F ? 1.0F : 1.0F - (float)Math.exp(-6.931471805599453 * value);
   }

   public static float getFloatByFloat7(float value) {
      if (value < 0.5F) {
         return 4.0F * value * value * value;
      } else {
         float f = -2.0F * value + 2.0F;
         return 1.0F - f * f * f * 0.5F;
      }
   }

   public static float getFloatByFloat8(float value) {
      return value;
   }

   public static float getFloatByFloat9(float value) {
      float f = 1.0F - value;
      return 1.0F - f * f * f * f * f;
   }
}
