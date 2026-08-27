package client.util;

import java.util.function.Function;

public class Animation {
   private static final float value = 14.0F;
   private static final float value2 = 0.001F;
   private static final float value3 = 0.05F;
   private static final Function<Float, Float> function = var0 -> {
      float f = 1.0F - var0;
      return 1.0F - f * f * f;
   };
   private final float value4;
   private float value5;
   private boolean flag;
   private float value6;
   private Function<Float, Float> function2 = function;
   private boolean flag2;
   private boolean flag3;
   private float value7;
   private float value8;

   public Animation(float value) {
      this.value4 = value;
   }

   public void setFloat(float value) {
      this.value7 = value;
      this.value8 = value;
      this.flag3 = true;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public float getValue5() {
      return this.value5;
   }

   public void update() {
      this.value5 = 0.0F;
      this.flag = false;
   }

   public void setFloat2(float value) {
      float f = this.getFloatByFloat(value);
      this.value5 = this.flag ? Math.min(1.0F, this.value5 + f * this.value4) : Math.max(0.0F, this.value5 - f * this.value4);
      if (this.flag2 && this.flag3) {
         this.setFloat3(value);
      }
   }

   public float getValue7() {
      return this.value7;
   }

   public float getValue8() {
      return this.value8;
   }

   private void setFloat3(float value) {
      float f = this.value8 - this.value7;
      if (Math.abs(f) < 0.05F) {
         this.value7 = this.value8;
      } else {
         float f1 = Math.min(1.0F, value * 14.0F);
         this.value7 += f * f1;
      }
   }

   public void update2() {
      this.value5 = 1.0F;
      this.flag = true;
   }

   public boolean check() {
      return !this.flag && this.value5 <= 0.001F;
   }

   private float getFloatByFloat(float value) {
      if (this.value6 <= 0.0F) {
         return value;
      } else if (value >= this.value6) {
         float f = value - this.value6;
         this.value6 = 0.0F;
         return f;
      } else {
         this.value6 -= value;
         return 0.0F;
      }
   }

   public void setFloat4(float value) {
      this.value8 = value;
      if (!this.flag3) {
         this.value7 = value;
         this.flag3 = true;
      }
   }

   public void onFloatBoolean(float value, boolean flag2) {
      if (this.flag != flag2) {
         this.flag = flag2;
         this.value6 = Math.max(0.0F, value);
      }
   }

   public void setBoolean(boolean flag2) {
      if (this.flag != flag2) {
         this.flag = flag2;
         this.value6 = 0.0F;
      }
   }

   public Animation getAnimationByFunction(Function<Float, Float> function3) {
      this.function2 = function3;
      return this;
   }

   public Animation getAnimation() {
      this.flag2 = true;
      return this;
   }

   public float getFloat() {
      return this.function2.apply(Math.clamp(this.value5, 0.0F, 1.0F));
   }
}
