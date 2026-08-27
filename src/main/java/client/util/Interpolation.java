package client.util;

public class Interpolation {
   private static final float value = 0.016666668F;
   private static final float value2 = 0.1F;
   private static final float value3 = -1.0F;
   private static final double value4 = -0.6931471805599453;
   private long time = -1L;

   public static float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      return value3 < 0.0F ? value : getFloatByFloatFloatFloatFloat2(value, value3, value2, value4);
   }

   public static float getFloat() {
      return -1.0F;
   }

   public void setTime() {
      this.time = -1L;
   }

   public float getFloat2() {
      long i = System.nanoTime();
      if (this.time < 0L) {
         this.time = i;
         return 0.016666668F;
      } else {
         float f = (float)((i - this.time) / 1.0E9);
         this.time = i;
         return f < 0.0F ? 0.0F : Math.min(f, 0.1F);
      }
   }

   public static float getFloatByFloatFloatFloatFloat2(float value, float value2, float value3, float value4) {
      if (value4 <= 0.0F) {
         return value;
      } else {
         float f = value - value2;
         if (Math.abs(f) < 0.01F) {
            return value;
         } else {
            float f1 = (float)Math.exp(-0.6931471805599453 * value3 / value4);
            return value - f * f1;
         }
      }
   }
}
