package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedColor;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ColorSetting;
import org.joml.Matrix4f;

public final class ColorField extends SettingField {
   public final ColorSetting colorSetting;
   private final ColorPicker colorPicker;
   private AnimatedColor animatedColor;
   public final SettingRow settingRow;

   ColorField(SettingRow settingRow2, ColorSetting colorSetting2) {
      super(colorSetting2);
      this.settingRow = settingRow2;
      this.colorSetting = colorSetting2;
      this.colorPicker = new ColorPicker(colorSetting2);
   }

   @Override
   public boolean isDoubleDoubleIntFloatFloatFloat(double value, double value2, int count, float value3, float value4, float value5) {
      if (count != 0) {
         return false;
      } else if (!(value2 < value4) && !(value2 > value4 + 26.0F)) {
         float f = value5 + value3 - 8.0F - 14.0F;
         if (value >= f - 4.0F && value <= f + 14.0F + 4.0F) {
            this.colorPicker
               .setColorSupplier2(
                  () -> new float[]{
                     SettingRow.getFloatBySettingRow5(this.settingRow) + SettingRow.getFloatBySettingRow(this.settingRow) + 8.0F,
                     SettingRow.getFloatBySettingRow4(this.settingRow)
                  }
               );
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, float value6) {
      String s = String.format("#%06X", this.colorSetting.getInt3() & 16777215);
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f1 = Math.max(56.0F, f + 8.0F);
      float f2 = value4 + value5 - 8.0F - 14.0F;
      float f3 = f2 - 8.0F - f1;
      float f4 = value6 + 4.0F;
      int i = 0xFF000000 | this.colorSetting.getInt3() & 16777215;
      int j = PanelPainter.getIntByInt(i);
      if (this.animatedColor == null) {
         this.animatedColor = new AnimatedColor(j, i, f1);
      }

      this.animatedColor.onIntIntFloat(i, j, f1);
      float f15 = this.animatedColor.getFloat();
      int k = this.animatedColor.getInt2();
      float f7 = 4.0F;
      float f6 = 18.0F;
      float f5 = f15;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, f3, k, matrix4f, f6, f5, value, f4);
      float f14 = f3 + (this.animatedColor.getFloat() - f) / 2.0F;
      f15 = f4 + 3.0F;
      int l = this.animatedColor.getInt();
      float f10 = 12.0F;
      float f9 = f15;
      float f8 = f14;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f9, f8, l, f10, value, s, matrix4f);
      CategoryType categorytype1 = CategoryType.BRUSH;
      f15 = value6 + 6.5F;
      int i1 = Theme.foreground();
      float f13 = 13.0F;
      float f12 = 14.0F;
      float f11 = f15;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i1, matrix4f, f11, categorytype, f13, f2, f12);
   }

   public float getFloatByFloatFloat(float value, float value2) {
      String s = String.format("#%06X", this.colorSetting.getInt3() & 16777215);
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f1 = Math.max(56.0F, f + 8.0F);
      float f2 = value + value2 - 8.0F - 14.0F;
      return f2 - 8.0F - f1;
   }
}
