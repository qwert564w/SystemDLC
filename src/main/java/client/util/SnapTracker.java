package client.util;

public final class SnapTracker {
   public final float value;
   public float value2;
   public float value3 = 8.0F;

   public SnapTracker(float value3) {
      this.value = value3;
      this.value2 = value3;
   }

   public void onFloatFloat(float value4, float value5) {
      float f = Math.abs(value4 - value5);
      if (f < this.value3) {
         this.value2 = this.value - (value4 - value5);
         this.value3 = f;
      }
   }
}
