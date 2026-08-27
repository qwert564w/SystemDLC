package client.gui.widget;

import client.api.Theme;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.SliderSetting;
import client.util.Interpolation;
import org.joml.Matrix4f;

public final class SliderField extends SettingField {
   public final SliderSetting sliderSetting;
   private final Interpolation interpolation;
   private float value;
   private boolean flag;
   public final SettingRow settingRow;

   SliderField(SettingRow settingRow2, SliderSetting sliderSetting2) {
      super(sliderSetting2);
      this.settingRow = settingRow2;
      this.interpolation = new Interpolation();
      this.sliderSetting = sliderSetting2;
   }

   private String getStringByFloat(float value) {
      int i = this.sliderSetting.getValue6();
      String s = this.sliderSetting.getText() == null ? "" : this.sliderSetting.getText();
      String s1 = i == 0 ? "%.0f%s" : "%." + i + "f%s";
      return String.format(s1, value, s);
   }

   public void onDoubleFloat(double value, float value2) {
      float f = value2 + 141.0F;
      float f1 = (float)Math.clamp((value - f) / 70.0, 0.0, 1.0);
      this.sliderSetting.setDouble2(this.sliderSetting.getValue3() + f1 * (this.sliderSetting.getValue42() - this.sliderSetting.getValue3()));
   }

   @Override
   public boolean isDoubleDoubleIntFloatFloatFloat(double value, double value2, int count, float value3, float value4, float value5) {
      if (count != 0) {
         return false;
      } else {
         float f = value5 + 141.0F;
         if (value < f || value > f + 70.0F) {
            return false;
         } else if (!(value2 < value4) && !(value2 > value4 + 26.0F)) {
            this.settingRow.sliderSetting = this.sliderSetting;
            this.onDoubleFloat(value, value5);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public void onFloatFloatFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, float value6) {
      float f = this.getFloat();
      float f1 = value4 + 141.0F;
      float f2 = value6 + 10.0F;
      int i = Theme.elevated();
      float f7 = 3.0F;
      float f6 = 6.0F;
      float f5 = 70.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, f1, i, matrix4f, f6, f5, value, f2);
      double d0 = Math.clamp((f - this.sliderSetting.getValue3()) / (this.sliderSetting.getValue42() - this.sliderSetting.getValue3()), 0.0, 1.0);
      float f3 = (float)(70.0 * d0);
      if (f3 > 0.0F) {
         int j = Theme.primary();
         float f9 = 3.0F;
         float f8 = 6.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f9, f1, j, matrix4f, f8, f3, value, f2);
      }

      float f19 = f1 + f3 - 5.0F;
      float f20 = f2 + -2.0F;
      int j1 = Theme.background();
      int k1 = Theme.foreground();
      float f15 = 1.15F;
      int l = k1;
      int k = j1;
      float f14 = 5.0F;
      float f13 = 10.0F;
      float f12 = 10.0F;
      float f11 = f20;
      float f10 = f19;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f15, f12, value, matrix4f, f11, k, f14, f10, l, f13);
      String s = this.getStringByFloat(f);
      float f4 = TextShader.getFloatByStringFloat(this.sliderSetting.getString2(), 12.0F);
      f20 = value4 + value5 - 8.0F - f4;
      float f21 = value6 + 7.0F;
      int i1 = Theme.primary();
      float f18 = 12.0F;
      float f17 = f21;
      float f16 = f20;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f17, f16, i1, f18, value, s, matrix4f);
   }

   private float getFloat() {
      float f = (float)this.sliderSetting.getValue();
      float f1 = this.interpolation.getFloat2();
      if (!this.flag) {
         this.value = f;
         this.flag = true;
      } else {
         float f3 = 0.05F;
         float f2 = this.value;
         this.value = Interpolation.getFloatByFloatFloatFloatFloat2(f, f2, f1, f3);
      }

      return this.value;
   }
}
