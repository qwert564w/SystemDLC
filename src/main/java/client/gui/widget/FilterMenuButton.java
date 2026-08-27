package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.FilterMenuSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class FilterMenuButton extends ButtonWidget {
   private final FilterMenuSetting filterMenuSetting;
   private final ToggleButton toggleButton;
   private final FilterDropdown filterDropdown;
   private final FilterDropdown filterDropdown2;

   public FilterMenuButton(FilterMenuSetting filterMenuSetting2) {
      this.filterMenuSetting = filterMenuSetting2;
      this.value237 = 301.0F;
      this.toggleButton = new ToggleButton(filterMenuSetting2.getBooleanSetting().isFlag3());
      this.toggleButton.setRunnable(() -> this.onFilterMenuSetting(filterMenuSetting2));
      this.filterDropdown = new FilterDropdown(filterMenuSetting2.getObyazatelnyeChary());
      this.filterDropdown2 = new FilterDropdown(filterMenuSetting2.getIsklyuchennyeChary());
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      this.value238 = this.getFloat6();
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f + this.value238 / 2.0F);
      float f12 = this.value235;
      float f13 = this.value237;
      float f14 = this.value238;
      int l = Theme.background();
      int i1 = Theme.border();
      float f10 = 1.0F;
      int j = i1;
      int i = l;
      float f9 = 14.0F;
      float f8 = f14;
      float f7 = f13;
      float f6 = f12;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f10, f7, value2, matrix4f, f, i, f9, f6, j, f8);
      float f1 = f + 16.0F;
      this.onFloatFloatFloatMatrix4fFloat(value2, value3, value, matrix4f, f1);
      float f2 = f1 + 24.0F + 16.0F;
      float f3 = this.value235 + 16.0F;
      float f4 = this.value237 - 32.0F;
      int k = Theme.border();
      float f11 = 1.0F;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f11, f3, f2, f4, matrix4f, k);
      float f5 = f2 + 1.0F + 16.0F;
      FilterDropdown filterdropdown = this.filterDropdown;
      f5 = this.getFloatByFloatFloatFloatMatrix4fFloatFloatFloatFilterDropdown(f5, value2, value3, matrix4f, value, f3, f4, filterdropdown);
      f5 += 16.0F;
      FilterDropdown filterdropdown1 = this.filterDropdown2;
      this.getFloatByFloatFloatFloatMatrix4fFloatFloatFloatFilterDropdown(f5, value2, value3, matrix4f, value, f3, f4, filterdropdown1);
      this.filterDropdown.onFloatFloatFloatMatrix4f2(value, value3, value2, matrix4f);
      this.filterDropdown2.onFloatFloatFloatMatrix4f2(value, value3, value2, matrix4f);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return !this.filterDropdown.check2() && !this.filterDropdown2.check2()
         ? super.isIntDoubleDouble(count, value, value2)
         : this.isIntDoubleDouble2(count, value, value2);
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      return true;
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      this.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4);
      return true;
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      this.toggleButton.isDoubleDoubleInt(value, value2, count);
      this.filterDropdown.isDoubleDoubleInt(value, value2, count);
      this.filterDropdown2.isDoubleDoubleInt(value, value2, count);
      return true;
   }

   public boolean check2() {
      return this.filterDropdown.check2() || this.filterDropdown2.check2();
   }

   private float[] getFloatArrayByColorSupplier(ColorSupplier colorSupplier) {
      float[] afloat = colorSupplier.get();
      return new float[]{afloat[0], afloat[1] - this.value238 / 2.0F};
   }

   private void onFilterMenuSetting(FilterMenuSetting filterMenuSetting) {
      filterMenuSetting.getBooleanSetting().setBoolean(this.toggleButton.isFlag4());
   }

   @Override
   public void setColorSupplier2(ColorSupplier colorSupplier) {
      this.value238 = this.getFloat6();
      super.setColorSupplier2(() -> this.getFloatArrayByColorSupplier(colorSupplier));
   }

   private float getFloat6() {
      float f = 73.0F;
      this.filterDropdown.setFloat(this.value237 - 32.0F);
      this.filterDropdown2.setFloat(this.value237 - 32.0F);
      f += this.filterDropdown.getFloat5();
      f += 16.0F;
      f += this.filterDropdown2.getFloat5();
      return f + 16.0F;
   }

   private void onFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      float f = this.value235 + 16.0F;
      float f1 = value4 + 4.0F;
      String s1 = this.filterMenuSetting.getDisplayName();
      int i = Theme.foreground();
      float f4 = 16.0F;
      String s = s1;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f1, f, i, f4, value, s, matrix4f);
      this.toggleButton.setBoolean2(this.filterMenuSetting.getBooleanSetting().isFlag3());
      float f2 = this.value235 + this.value237 - 16.0F - 28.0F;
      float f3 = value4 + 4.0F;
      this.toggleButton.onFloatFloat2(f3, f2);
      this.toggleButton.onFloatFloatFloatMatrix4f(value, value3, value2, matrix4f);
   }

   private float getFloatByFloatFloatFloatMatrix4fFloatFloatFloatFilterDropdown(
      float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5, float value6, FilterDropdown filterDropdown
   ) {
      filterDropdown.setFloat(value6);
      filterDropdown.onFloatFloat2(value, value5);
      filterDropdown.onFloatFloatFloatMatrix4f(value2, value4, value3, matrix4f);
      return value + filterDropdown.getFloat5();
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count != 0) {
         return true;
      } else if (this.filterDropdown.check2() && this.filterDropdown.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.filterDropdown2.check2() && this.filterDropdown2.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.toggleButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.filterDropdown.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else {
         return this.filterDropdown2.isIntDoubleDouble(count, value, value2) ? true : true;
      }
   }

   @Override
   protected void update7() {
      this.filterDropdown.update3();
      this.filterDropdown2.update3();
   }
}
