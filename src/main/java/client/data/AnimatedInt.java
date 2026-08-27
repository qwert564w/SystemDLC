package client.data;

import client.util.Easings;

public final class AnimatedInt {
   private final Tween tween;
   private int value;
   private int value2;

   public AnimatedInt(int count, float value3) {
      this.value = count;
      this.value2 = count;
      this.tween = new Tween(1.0F, value3).getTweenByFunction(Easings::getFloatByFloat3);
      this.tween.setFloat(1.0F);
   }

   public int getInt() {
      int k = this.value;
      int l = this.value2;
      float f = this.tween.getValue3();
      int j = l;
      int i = k;
      return getIntByIntFloatInt(j, f, i);
   }

   public void setInt(int count) {
      if (this.value2 != count) {
         this.value = this.getInt();
         this.value2 = count;
         this.tween.setFloat(0.0F);
         this.tween.setFloat2(1.0F);
      }
   }

   public static int getIntByIntFloatInt(int count, float value, int count2) {
      if (value <= 0.0F) {
         return count2;
      } else if (value >= 1.0F) {
         return count;
      } else {
         int i = count2 >> 24 & 0xFF;
         int j = count2 >> 16 & 0xFF;
         int k = count2 >> 8 & 0xFF;
         int l = count2 & 0xFF;
         int i1 = count >> 24 & 0xFF;
         int j1 = count >> 16 & 0xFF;
         int k1 = count >> 8 & 0xFF;
         int l1 = count & 0xFF;
         int i2 = (int)(i + (i1 - i) * value);
         int j2 = (int)(j + (j1 - j) * value);
         int k2 = (int)(k + (k1 - k) * value);
         int l2 = (int)(l + (l1 - l) * value);
         return i2 << 24 | j2 << 16 | k2 << 8 | l2;
      }
   }

   public int getInt2() {
      float f = this.tween.getFloat();
      int j = this.value2;
      int i = this.value;
      return getIntByIntFloatInt(j, f, i);
   }

   public void setInt2(int count) {
      this.value = count;
      this.value2 = count;
      this.tween.setFloat(1.0F);
   }
}
