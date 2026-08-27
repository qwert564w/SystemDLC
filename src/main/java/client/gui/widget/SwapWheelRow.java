package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.SwapWheelSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class SwapWheelRow extends PanelWidget {
   private final SwapWheelSetting swapWheelSetting;
   private final Tween tween4 = EasingPresets.getTween();

   public SwapWheelRow(SwapWheelSetting swapWheelSetting2) {
      super(swapWheelSetting2);
      this.swapWheelSetting = swapWheelSetting2;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else if (this.isDoubleDouble3(value, value2)) {
         this.swapWheelSetting.getSwapWheelEditor().update5();
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
      int i2 = Theme.elevated();
      int l = Theme.foreground();
      int k = i2;
      int i = AnimatedInt.getIntByIntFloatInt(l, f3, k);
      i2 = Theme.foreground();
      int j1 = Theme.background();
      int i1 = i2;
      int j = AnimatedInt.getIntByIntFloatInt(j1, f3, i1);
      float f27 = this.value235;
      float f29 = this.value237;
      int j2 = Theme.border();
      float f22 = 1.0F;
      float f21 = 1.0F;
      float f20 = 0.0F;
      int l1 = 436207616;
      float f19 = 1.0F;
      int k1 = j2;
      float f18 = 8.0F;
      float f17 = 8.0F;
      float f16 = 8.0F;
      float f15 = 8.0F;
      float f14 = 32.0F;
      float f13 = f29;
      float f12 = f27;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f13, l1, i, f12, f2, f22, f15, f14, f21, matrix4f, value, k1, f17, f19, f16, f18, f20
      );
      String s = "Настроить колесо";
      float f4 = TextShader.getFloatByStringFloat(s, 14.0F);
      float f5 = 8.0F;
      float f6 = 12.0F + f5 + f4;
      float f7 = this.value235 + (this.value237 - f6) / 2.0F;
      float f8 = f2 + 10.0F;
      float f9 = f2 + 9.0F;
      float f24 = 12.0F;
      float f23 = 12.0F;
      CategoryType categorytype = CategoryType.EDIT;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j, matrix4f, f8, categorytype, f24, f7, f23);
      float f28 = f7 + 12.0F + f5;
      float f26 = 14.0F;
      float f25 = f28;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f9, f25, j, f26, value, s, matrix4f);
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

   private float getFloat8() {
      float f1 = this.getFloatByFloat(this.value237);
      float f = this.getFloatByFloat2(this.value237);
      return this.getFloatByFloatFloat2(f1, f);
   }
}
