package client.util;

public final class RangeMath {
   private RangeMath() {
   }

   public static float getFloatByFloatFloatFloatFloatFloat(float value, float value2, float value3, float value4, float value5) {
      float f = value2 - value;
      return f <= 0.0F ? 0.0F : Math.clamp((value4 - value5) / f * value3, 0.0F, value3);
   }

   public static float getFloatByFloatFloatFloatFloatFloat2(float value, float value2, float value3, float value4, float value5) {
      return value4 <= 0.0F ? value5 : value5 + (value2 - value3) * Math.clamp(value / value4, 0.0F, 1.0F);
   }
}
