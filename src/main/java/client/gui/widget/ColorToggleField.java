package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedColor;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ColorToggleSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public final class ColorToggleField extends SettingField {
   private static final float value = 8.0F;
   public final ColorToggleSetting colorToggleSetting;
   private final ColorPicker colorPicker;
   public final ToggleButton toggleButton;
   public final Tween tween;
   private AnimatedColor animatedColor;
   public final SettingRow settingRow;

   ColorToggleField(SettingRow settingRow2, ColorToggleSetting colorToggleSetting2) {
      super(colorToggleSetting2);
      this.settingRow = settingRow2;
      this.colorToggleSetting = colorToggleSetting2;
      this.colorPicker = new ColorPicker(colorToggleSetting2.getColorSetting());
      this.toggleButton = new ToggleButton(colorToggleSetting2.check());
      this.toggleButton.setRunnable(() -> this.colorToggleSetting.onBoolean(this.toggleButton.isFlag4()));
      this.tween = EasingPresets.getTweenByFloatFloat2(colorToggleSetting2.check() ? 1.0F : 0.0F, 0.22F);
   }

   @Override
   public boolean isDoubleDoubleIntFloatFloatFloat(double value, double value2, int count, float value3, float value4, float value5) {
      if (count != 0) {
         return false;
      } else if (!(value2 < value4) && !(value2 > value4 + 26.0F)) {
         byte b0 = 0;
         if (this.toggleButton.isIntDoubleDouble(b0, value, value2)) {
            return true;
         } else {
            float f = value5 + value3 - 8.0F - 28.0F;
            float f1 = f - 8.0F - 14.0F;
            if (value >= f1 - 4.0F && value <= f1 + 14.0F + 4.0F) {
               this.colorPicker
                  .setColorSupplier2(
                     () -> new float[]{
                        SettingRow.getFloatBySettingRow6(this.settingRow) + SettingRow.getFloatBySettingRow2(this.settingRow) + 8.0F,
                        SettingRow.getFloatBySettingRow3(this.settingRow)
                     }
                  );
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public float getFloatByFloatFloat(float value, float value2) {
      float f = value2 + value - 8.0F - 28.0F;
      float f1 = f - 8.0F - 14.0F;
      String s = String.format("#%06X", this.colorToggleSetting.getInt() & 16777215);
      float f2 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f3 = Math.max(56.0F, f2 + 8.0F);
      return f1 - 8.0F - f3;
   }

   public float getFloat() {
      return 0.4F + 0.6F * this.tween.getValue3();
   }

   @Override
   public void onFloatFloatFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, float value6) {
      boolean flag = this.colorToggleSetting.check();
      this.tween.setFloat2(flag ? 1.0F : 0.0F);
      float f = this.tween.getFloat();
      float f1 = value * (0.4F + 0.6F * f);
      float f2 = value4 + value5 - 8.0F - 28.0F;
      float f3 = value6 + 5.0F;
      float f4 = f2 - 8.0F - 14.0F;
      String s = String.format("#%06X", this.colorToggleSetting.getInt() & 16777215);
      float f5 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f6 = Math.max(56.0F, f5 + 8.0F);
      float f7 = f4 - 8.0F - f6;
      float f8 = value6 + 4.0F;
      int i = 0xFF000000 | this.colorToggleSetting.getInt() & 16777215;
      int j = PanelPainter.getIntByInt(i);
      if (this.animatedColor == null) {
         this.animatedColor = new AnimatedColor(j, i, f6);
      }

      this.animatedColor.onIntIntFloat(i, j, f6);
      float f19 = this.animatedColor.getFloat();
      int k = this.animatedColor.getInt2();
      float f11 = 4.0F;
      float f10 = 18.0F;
      float f9 = f19;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f7, k, matrix4f, f10, f9, f1, f8);
      float f18 = f7 + (this.animatedColor.getFloat() - f5) / 2.0F;
      f19 = f8 + 3.0F;
      int l = this.animatedColor.getInt();
      float f14 = 12.0F;
      float f13 = f19;
      float f12 = f18;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f13, f12, l, f14, f1, s, matrix4f);
      CategoryType categorytype1 = CategoryType.BRUSH;
      f19 = value6 + 6.5F;
      int i1 = Theme.foreground();
      float f17 = 13.0F;
      float f16 = 14.0F;
      float f15 = f19;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f1, i1, matrix4f, f15, categorytype, f17, f4, f16);
      this.toggleButton.setBoolean2(flag);
      this.toggleButton.onFloatFloat2(f3, f2);
      this.toggleButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
   }
}
