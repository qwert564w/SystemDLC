package client.data;

import client.util.Easings;
import java.util.function.Function;

public final class Tween {
   private static final double value = 1.0E9;
   private float value2;
   private float value3;
   private float value4;
   private float value5;
   private Function<Float, Float> function = Easings::getFloatByFloat3;
   private long time;
   private boolean flag;

   public Tween(float value, float value6) {
      this.value2 = value;
      this.value3 = value;
      this.value4 = value;
      this.value5 = value6;
      this.time = System.nanoTime();
   }

   public void setFloat(float value) {
      this.value2 = value;
      this.value3 = value;
      this.value4 = value;
      this.flag = false;
   }

   public float getValue3() {
      return this.value3;
   }

   public float getValue4() {
      return this.value4;
   }

   public void setFloat2(float value) {
      if (this.value4 != value) {
         this.value2 = this.value3;
         this.value4 = value;
         this.time = System.nanoTime();
         this.flag = true;
      }
   }

   public Tween getTweenByFunction(Function<Float, Float> function2) {
      this.function = function2;
      return this;
   }

   public void setValue5(float value) {
      this.value5 = value;
   }

   public float getFloat() {
      if (this.flag && !(this.value5 <= 0.0F)) {
         float f = (float)((System.nanoTime() - this.time) / 1.0E9);
         float f1 = f / this.value5;
         if (f1 <= 0.0F) {
            this.value3 = this.value2;
         } else if (f1 >= 1.0F) {
            this.value3 = this.value4;
            this.flag = false;
         } else {
            float f2 = this.function.apply(f1);
            this.value3 = this.value2 + (this.value4 - this.value2) * f2;
         }

         return this.value3;
      } else {
         this.value3 = this.value4;
         this.flag = false;
         return this.value3;
      }
   }

   public void onFloatFloat(float value, float value5) {
      if (this.value4 != value5) {
         this.value2 = this.value3;
         this.value4 = value5;
         this.time = System.nanoTime() + (long)(value * 1.0E9);
         this.flag = true;
      }
   }
}
