package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.SliderSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class SliderRow extends PanelWidget {
   private final SliderSetting sliderSetting;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat(0.0F, 0.22F);
   private boolean flag4;
   private float value252;
   private long time3;
   private float value253 = Float.NaN;
   private String text;
   private float value254 = -1.0F;
   private String text2;

   public SliderRow(SliderSetting sliderSetting2) {
      super(sliderSetting2);
      this.sliderSetting = sliderSetting2;
      this.value252 = (float)sliderSetting2.getValue();
      this.time3 = System.nanoTime();
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count == 0 && this.isDoubleDouble3(value2, value)) {
         this.flag4 = true;
         this.onDouble(value);
         return true;
      } else {
         return false;
      }
   }

   private float getFloat8() {
      float f = this.getFloat9();
      float f1 = this.getFloatByFloat2(this.value237 - f - 8.0F);
      float f2 = this.getFloatByFloat(this.value237);
      return this.getFloatByFloatFloat2(f2, f1);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = (float)this.sliderSetting.getValue();
      long i = System.nanoTime();
      float f1 = (float)((i - this.time3) / 1.0E9);
      this.time3 = i;
      this.value252 = this.value252 + (f - this.value252) * (1.0F - (float)Math.exp(-14.0F * f1));
      float f2 = this.value252;
      String s = this.getStringByFloat(f2);
      float f3 = this.getFloat9();
      float f13 = this.value237 - f3 - 8.0F;
      float f4 = this.getFloatByFloatMatrix4fFloat(f13, matrix4f, value);
      float f14 = this.value237;
      float f5 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f4, f14);
      float f6 = this.value236 + 1.0F;
      float f30 = this.value235 + this.value237 - f3;
      int j = Theme.primary();
      float f16 = 12.0F;
      float f15 = f30;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f6, f15, j, f16, value, s, matrix4f);
      float f7 = this.getFloatByFloatFloat2(f5, f4);
      float f29 = this.value235;
      float f31 = this.value237;
      int k = Theme.elevated();
      float f20 = 3.0F;
      float f19 = 6.0F;
      float f18 = f31;
      float f17 = f29;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f20, f17, k, matrix4f, f19, f18, value, f7);
      double d0 = (f2 - this.sliderSetting.getValue3()) / (this.sliderSetting.getValue42() - this.sliderSetting.getValue3());
      d0 = Math.clamp(d0, 0.0, 1.0);
      float f8 = (float)(this.value237 * d0);
      if (f8 > 0.0F) {
         f29 = this.value235;
         int l = Theme.primary();
         float f23 = 3.0F;
         float f22 = 6.0F;
         float f21 = f29;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f23, f21, l, matrix4f, f22, f8, value, f7);
      }

      this.tween4.setFloat2(this.flag4 ? 1.0F : 0.0F);
      float f9 = this.tween4.getFloat();
      float f25 = 14.0F;
      float f24 = 10.0F;
      float f10 = EasingPresets.getFloatByFloatFloatFloat(f25, f9, f24);
      float f11 = this.value235 + f8 - f10 / 2.0F;
      float f12 = f7 + -2.0F;
      int k1 = Theme.background();
      int l1 = Theme.foreground();
      float f28 = 1.15F;
      int j1 = l1;
      int i1 = k1;
      float f27 = 5.0F;
      float f26 = 10.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f28, f10, value, matrix4f, f12, i1, f27, f11, j1, f26);
      this.value238 = f7 + 10.0F - this.value236;
   }

   @Override
   public float getFloat5() {
      float f = this.getFloat9();
      float f1 = this.getFloatByFloat2(this.value237 - f - 8.0F);
      float f2 = this.getFloatByFloat(this.value237);
      return this.getFloatByFloatFloat3(f1, f2) + 8.0F + 10.0F;
   }

   private boolean isDoubleDouble3(double value, double value2) {
      float f = this.getFloat8();
      return value2 >= this.value235 && value2 <= this.value235 + this.value237 && value >= f - 10.0F && value <= f + 10.0F;
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_SLIDER;
   }

   private void onDouble(double value) {
      float f = (float)Math.clamp((value - this.value235) / this.value237, 0.0, 1.0);
      double d0 = this.sliderSetting.getValue3() + f * (this.sliderSetting.getValue42() - this.sliderSetting.getValue3());
      this.sliderSetting.setDouble2(d0);
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag4) {
         this.onDouble(value2);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag4) {
         this.flag4 = false;
         return true;
      } else {
         return false;
      }
   }

   private String getStringByFloat(float value) {
      if (this.text != null && Float.floatToRawIntBits(value) == Float.floatToRawIntBits(this.value253)) {
         return this.text;
      } else {
         int i = this.sliderSetting.getValue6();
         String s = this.sliderSetting.getText() == null ? "" : this.sliderSetting.getText();
         String s1 = i == 0 ? "%.0f%s" : "%." + i + "f%s";
         this.text = String.format(s1, value, s);
         this.value253 = value;
         return this.text;
      }
   }

   private float getFloat9() {
      String s = this.sliderSetting.getString2();
      if (s == this.text2) {
         return this.value254;
      } else {
         this.value254 = TextShader.getFloatByStringFloat(s, 12.0F);
         this.text2 = s;
         return this.value254;
      }
   }
}
