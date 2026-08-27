package client.data;

public class AnimatedFloat {
   private float value;
   private float value2;
   private float value3;
   private float value4;
   private boolean flag;

   public AnimatedFloat(float value, float value2, boolean flag) {
      this.getAnimatedFloatByFloatFloatBoolean(value, value2, flag);
   }

   public float getValue2() {
      return this.value2;
   }

   public void setValue2(float value) {
      this.value2 = value;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public float getValue3() {
      return this.value3;
   }

   public float getValue4() {
      return this.value4;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public AnimatedFloat getAnimatedFloatByFloatFloatBoolean(float value5, float value6, boolean flag2) {
      this.value = value5;
      this.value2 = value6;
      this.value3 = value5;
      this.value4 = value6;
      this.flag = flag2;
      return this;
   }

   public void setValue(float value2) {
      this.value = value2;
   }

   public boolean check() {
      return this.value != this.value3 || this.value2 != this.value4;
   }

   public float getValue() {
      return this.value;
   }
}
