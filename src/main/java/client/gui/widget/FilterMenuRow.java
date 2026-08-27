package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.FilterMenuSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class FilterMenuRow extends PanelWidget {
   private final FilterMenuSetting filterMenuSetting;
   private final FilterMenuButton filterMenuButton;
   private final Tween tween4 = EasingPresets.getTween();

   public FilterMenuRow(FilterMenuSetting filterMenuSetting2) {
      super(filterMenuSetting2);
      this.filterMenuSetting = filterMenuSetting2;
      this.filterMenuButton = new FilterMenuButton(filterMenuSetting2);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else if (this.isDoubleDouble3(value, value2)) {
         this.filterMenuButton.setColorSupplier2(this::getFloatArray);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f10 = this.value237;
      float f = this.getFloatByFloatMatrix4fFloat(f10, matrix4f, value);
      float f11 = this.value237;
      float f1 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f, f11);
      float f2 = this.getFloatByFloatFloat2(f1, f);
      boolean flag = value3 >= this.value235 && value3 <= this.value235 + this.value237 && value2 >= f2 && value2 <= f2 + 32.0F;
      this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      float f3 = this.tween4.getFloat();
      int i2 = Theme.background();
      int j2 = Theme.elevated();
      float f12 = 0.85F;
      int i1 = j2;
      int l = i2;
      int i = AnimatedInt.getIntByIntFloatInt(i1, f12, l);
      int j1 = Theme.background();
      int j = AnimatedInt.getIntByIntFloatInt(i, f3, j1);
      float f28 = this.value235;
      float f30 = this.value237;
      int k2 = Theme.border();
      float f23 = 1.0F;
      float f22 = 1.0F;
      float f21 = 0.0F;
      int l1 = 436207616;
      float f20 = 1.0F;
      int k1 = k2;
      float f19 = 8.0F;
      float f18 = 8.0F;
      float f17 = 8.0F;
      float f16 = 8.0F;
      float f15 = 32.0F;
      float f14 = f30;
      float f13 = f28;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f14, l1, j, f13, f2, f23, f16, f15, f22, matrix4f, value, k1, f18, f20, f17, f19, f21
      );
      int k = Theme.foreground();
      String s = this.filterMenuSetting.getText();
      float f4 = TextShader.getFloatByStringFloat(s, 14.0F);
      float f5 = 8.0F;
      float f6 = 12.0F + f5 + f4;
      float f7 = this.value235 + (this.value237 - f6) / 2.0F;
      float f8 = f2 + 9.5F;
      float f9 = f2 + 9.0F;
      float f25 = 13.0F;
      float f24 = 12.0F;
      CategoryType categorytype = CategoryType.SETTINGS;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, k, matrix4f, f8, categorytype, f25, f7, f24);
      float f29 = f7 + 12.0F + f5;
      float f27 = 14.0F;
      float f26 = f29;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f9, f26, k, f27, value, s, matrix4f);
      this.value238 = f2 + 32.0F - this.value236;
   }

   @Override
   public float getFloat5() {
      return this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 32.0F;
   }

   private boolean isDoubleDouble3(double value, double value2) {
      float f = this.getFloat8();
      return value >= this.value235 && value <= this.value235 + this.value237 && value2 >= f && value2 <= f + 32.0F;
   }

   private float[] getFloatArray() {
      return new float[]{this.value235 + this.value237 + 16.0F + 12.0F, this.getFloat8() + 16.0F};
   }

   private float getFloat8() {
      float f1 = this.getFloatByFloat(this.value237);
      float f = this.getFloatByFloat2(this.value237);
      return this.getFloatByFloatFloat2(f1, f);
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_CHECKBOX;
   }
}
