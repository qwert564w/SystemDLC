package client.util;

public final class SmoothFloat {
   private static final float value = 1.0E-4F;
   private float value2;
   private float value3;

   public SmoothFloat(float value) {
      this.value2 = value;
   }

   public float getValue2() {
      return this.value2;
   }

   public float getFloatByFloatFloatFloat(float value, float value4, float value5) {
      if (value <= 0.0F) {
         this.setFloat(value4);
         return this.value2;
      } else if (value5 <= 0.0F) {
         return this.value2;
      } else if (Math.abs(this.value2 - value4) < 1.0E-4F && Math.abs(this.value3) < 1.0E-4F) {
         this.setFloat(value4);
         return this.value2;
      } else {
         float f = 2.0F / value;
         float f1 = f * value5;
         float f2 = 1.0F / (1.0F + f1 + 0.48F * f1 * f1 + 0.235F * f1 * f1 * f1);
         float f3 = this.value2 - value4;
         float f4 = (this.value3 + f * f3) * value5;
         this.value3 = (this.value3 - f * f4) * f2;
         this.value2 = value4 + (f3 + f4) * f2;
         return this.value2;
      }
   }

   public void setFloat(float value) {
      this.value2 = value;
      this.value3 = 0.0F;
   }
}
