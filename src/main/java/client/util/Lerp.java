package client.util;

public final class Lerp {
   private Lerp() {
   }

   public static float getFloatByFloat(float value) {
      return value < 0.5F ? 2.0F * value * value : 1.0F - (float)Math.pow(-2.0F * value + 2.0F, 2.0) / 2.0F;
   }

   public static float getFloatByFloat2(float value) {
      return value < 0.0F ? 0.0F : (value > 1.0F ? 1.0F : value);
   }

   public static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return value + (value3 - value) * value2;
   }
}
