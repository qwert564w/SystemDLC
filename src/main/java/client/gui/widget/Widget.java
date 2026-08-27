package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.data.Tween;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public abstract class Widget implements UiMetrics, Theme {
   protected float value235;
   protected float value236;
   protected float value237;
   public float value238;
   protected boolean flag;
   protected final Tween tween = EasingPresets.getTween();
   protected boolean flag2 = true;
   private Tween tween2;
   private Tween tween3;
   private boolean flag3;

   protected final void onFloatFloat(float value, float value2) {
      if (this.tween2 == null) {
         this.tween2 = EasingPresets.getTweenByFloatFloat(0.0F, 0.32F);
         this.tween3 = EasingPresets.getTweenByFloatFloat(0.0F, 0.32F);
      }

      this.tween2.setFloat(value);
      this.tween3.setFloat(value2);
      this.value235 = value;
      this.value236 = value2;
      this.flag3 = true;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public boolean isIntIntInt(int count, int count2, int count3) {
      return false;
   }

   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return false;
   }

   public void setFlag2(boolean flag) {
      this.flag2 = flag;
   }

   protected float getFloatByFloatFloat(float value, float value2) {
      this.flag = this.isDoubleDouble(value, value2) && !WidgetState.check();
      this.tween.setFloat2(this.flag ? 1.0F : 0.0F);
      return this.tween.getFloat();
   }

   public void onFloatFloat2(float value, float value2) {
      this.value235 = value2;
      this.value236 = value;
   }

   public void setValue237(float value) {
      this.value237 = value;
   }

   public abstract void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f);

   public boolean isFlag2() {
      return this.flag2;
   }

   protected final void update() {
      if (this.flag3 && this.tween2 != null) {
         this.value235 = this.tween2.getFloat();
         this.value236 = this.tween3.getFloat();
      }
   }

   protected final void onFloatFloat3(float value, float value2) {
      if (this.tween2 == null) {
         this.tween2 = EasingPresets.getTweenByFloatFloat(0.0F, 0.32F);
         this.tween3 = EasingPresets.getTweenByFloatFloat(0.0F, 0.32F);
      }

      if (!this.flag3) {
         this.tween2.setFloat(value);
         this.tween3.setFloat(value2);
         this.value235 = value;
         this.value236 = value2;
         this.flag3 = true;
      } else {
         this.tween2.setFloat2(value);
         this.tween3.setFloat2(value2);
      }
   }

   public void setValue238(float value) {
      this.value238 = value;
   }

   protected boolean isDoubleDouble(double value, double value2) {
      float f3 = this.value238;
      float f2 = this.value237;
      float f1 = this.value236;
      float f = this.value235;
      return isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value);
   }

   public void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
   }

   public boolean isIntIntInt2(int count, int count2, int count3) {
      return false;
   }

   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      return false;
   }

   public static boolean isFloatFloatDoubleFloatFloatDouble(float value, float value2, double value3, float value4, float value5, double value6) {
      return value6 >= value && value6 <= value + value5 && value3 >= value2 && value3 <= value2 + value4;
   }

   public boolean isIntChar(int count, char symbol) {
      return false;
   }

   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      return false;
   }

   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      return false;
   }

   public float getValue235() {
      return this.value235;
   }

   public float getValue236() {
      return this.value236;
   }

   public void update2() {
   }

   public float getValue237() {
      return this.value237;
   }

   public float getValue238() {
      return this.value238;
   }
}
