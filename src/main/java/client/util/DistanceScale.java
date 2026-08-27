package client.util;

public class DistanceScale {
   public static float getFloatByDoubleFloat(double value, float value2) {
      float f = (float)Math.clamp(value * 0.0025F, 0.02F, 0.1F);
      return f * value2;
   }
}
