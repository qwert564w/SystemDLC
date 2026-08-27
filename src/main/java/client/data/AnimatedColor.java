package client.data;

import client.util.EasingPresets;

public final class AnimatedColor {
   private final AnimatedInt animatedInt;
   private final AnimatedInt animatedInt2;
   private final Tween tween;
   private boolean flag;

   public AnimatedColor(int count, int count2, float value) {
      this.animatedInt = new AnimatedInt(count, 0.22F);
      this.animatedInt2 = new AnimatedInt(count2, 0.22F);
      this.tween = EasingPresets.getTweenByFloatFloat(value, 0.15F);
   }

   public int getInt() {
      return this.animatedInt2.getInt();
   }

   public float getFloat() {
      return this.tween.getValue3();
   }

   public int getInt2() {
      return this.animatedInt.getInt();
   }

   public void onIntIntFloat(int count, int count2, float value) {
      if (!this.flag) {
         this.animatedInt.setInt2(count2);
         this.animatedInt2.setInt2(count);
         this.tween.setFloat(value);
         this.flag = true;
      } else {
         this.animatedInt.setInt(count2);
         this.animatedInt2.setInt(count);
         this.tween.setFloat2(value);
      }

      this.animatedInt.getInt2();
      this.animatedInt2.getInt2();
      this.tween.getFloat();
   }
}
