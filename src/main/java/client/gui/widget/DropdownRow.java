package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.HotkeySetting;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.util.EasingPresets;
import client.util.Easings;
import org.joml.Matrix4f;

public class DropdownRow extends PanelWidget {
   private static final float value252 = 34.0F;
   private static final float value253 = 4.0F;
   private static final float value254 = 26.0F;
   private static final float value255 = 26.0F;
   private static final float value256 = 12.0F;
   private static final float value257 = 8.0F;
   private static final float value258 = 0.85F;
   private static final float value259 = 16.0F;
   private static final float value260 = 10.0F;
   private boolean flag4;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private final Tween tween5 = EasingPresets.getTween();
   private final Tween tween6 = EasingPresets.getTween();
   private final Tween tween7 = new Tween(0.0F, 0.55F).getTweenByFunction(Easings::getFloatByFloat3);
   private int value261 = -1;

   public DropdownRow(Setting setting2) {
      super(setting2);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.flag4) {
         if (count == 0 && this.isDoubleDouble4(value, value2)) {
            this.flag4 = false;
            this.value261 = -1;
            return true;
         } else {
            this.onIntInt(count, 0);
            this.flag4 = false;
            this.value261 = -1;
            return true;
         }
      } else if (count == 0 && this.isDoubleDouble3(value, value2)) {
         this.onIntInt(-1, 0);
         this.flag4 = false;
         this.value261 = -1;
         this.tween7.setFloat(0.0F);
         this.tween7.setFloat2(1.0F);
         return true;
      } else if (count == 0 && this.isDoubleDouble4(value, value2)) {
         this.flag4 = true;
         this.value261 = -1;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (this.value261 == -1) {
         return false;
      } else if (count == this.value261) {
         this.onIntInt(this.value261, 0);
         this.flag4 = false;
         this.value261 = -1;
         return true;
      } else {
         return false;
      }
   }

   private boolean isDoubleDouble3(double value, double value2) {
      float f = this.getFloat8();
      float f1 = this.value235 + this.value237 - 4.0F - 26.0F;
      float f2 = f + 4.0F;
      return value >= f1 && value <= f1 + 26.0F && value2 >= f2 && value2 <= f2 + 26.0F;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f7 = this.value237;
      float f = this.getFloatByFloatMatrix4fFloat(f7, matrix4f, value);
      float f8 = this.value237;
      float f1 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f, f8);
      float f2 = this.getFloatByFloatFloat2(f1, f);
      float f13 = this.value235;
      float f14 = this.value237;
      int i = Theme.elevated();
      float f12 = 8.0F;
      float f11 = 34.0F;
      float f10 = f14;
      float f9 = f13;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f12, f9, i, matrix4f, f11, f10, value, f2);
      float f3 = this.value235 + 4.0F;
      float f4 = f2 + 4.0F;
      float f5 = this.value237 - 12.0F - 26.0F;
      this.onFloatFloatFloatFloatFloatMatrix4fFloat(f3, value2, value, value3, f4, matrix4f, f5);
      float f6 = this.value235 + this.value237 - 4.0F - 26.0F;
      this.onFloatFloatFloatMatrix4fFloatFloat(value3, f4, value, matrix4f, f6, value2);
      this.value238 = f2 + 34.0F - this.value236;
   }

   @Override
   public float getFloat5() {
      return this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 34.0F;
   }

   private String getString() {
      if (this.setting instanceof KeybindSetting keybindsetting) {
         return keybindsetting.getText2();
      } else {
         return this.setting instanceof HotkeySetting hotkeysetting ? hotkeysetting.getText2() : "None";
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (count3 == 256) {
         this.onIntInt(-1, 0);
         this.flag4 = false;
         this.value261 = -1;
         return true;
      } else if (KeybindSetting.isInt(count3)) {
         this.value261 = count3;
         return true;
      } else {
         this.onIntInt(count3, count2);
         this.flag4 = false;
         this.value261 = -1;
         return true;
      }
   }

   private void onIntInt(int count, int count2) {
      if (this.setting instanceof KeybindSetting keybindsetting) {
         keybindsetting.onIntInt(count, count2);
      } else if (this.setting instanceof HotkeySetting hotkeysetting) {
         hotkeysetting.onIntInt(count2, count);
      }
   }

   private void onFloatFloatFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, float value6) {
      String s = this.flag4 ? "..." : this.getString();
      this.tween4.setFloat2(this.flag4 ? 1.0F : 0.0F);
      float f = this.tween4.getFloat();
      boolean flag = !this.flag4 && value4 >= value && value4 <= value + value6 && value2 >= value5 && value2 <= value5 + 26.0F;
      this.tween5.setFloat2(flag ? 1.0F : 0.0F);
      float f1 = this.tween5.getFloat();
      int l2 = Theme.surface();
      int i3 = Theme.elevated();
      float f8 = 0.85F;
      int j1 = i3;
      int i1 = l2;
      int i = AnimatedInt.getIntByIntFloatInt(j1, f8, i1);
      int k1 = Theme.surface();
      int j = AnimatedInt.getIntByIntFloatInt(i, f1, k1);
      int l1 = Theme.primary();
      int k = AnimatedInt.getIntByIntFloatInt(l1, f, j);
      l2 = Theme.foreground();
      int j2 = Theme.background();
      int i2 = l2;
      int l = AnimatedInt.getIntByIntFloatInt(j2, f, i2);
      float f17 = 3.0F;
      float f16 = 1.0F;
      float f15 = 0.0F;
      int k2 = 436207616;
      float f14 = 0.0F;
      byte b0 = 0;
      float f13 = 8.0F;
      float f12 = 8.0F;
      float f11 = 8.0F;
      float f10 = 8.0F;
      float f9 = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value6, k2, k, value, value5, f17, f10, f9, f16, matrix4f, value3, b0, f12, f14, f11, f13, f15
      );
      float f2 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f3 = 22.0F + f2;
      float f4 = value + (value6 - f3) / 2.0F;
      float f5 = value5 + 8.0F;
      float f6 = f4 + 16.0F + 6.0F;
      float f7 = value5 + 7.0F;
      float f19 = 10.0F;
      float f18 = 16.0F;
      CategoryType categorytype = CategoryType.KEYBOARD;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, l, matrix4f, f5, categorytype, f19, f4, f18);
      float f20 = 12.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f7, f6, l, f20, value3, s, matrix4f);
   }

   private void onFloatFloatFloatMatrix4fFloatFloat(float value, float value2, float value3, Matrix4f matrix4f2, float value4, float value5) {
      boolean flag = value >= value4 && value <= value4 + 26.0F && value5 >= value2 && value5 <= value2 + 26.0F;
      this.tween6.setFloat2(flag ? 1.0F : 0.0F);
      float f = this.tween6.getFloat();
      int l1 = Theme.surface();
      int i2 = Theme.elevated();
      float f7 = 0.85F;
      int l = i2;
      int k = l1;
      int i = AnimatedInt.getIntByIntFloatInt(l, f7, k);
      int i1 = Theme.surface();
      int j = AnimatedInt.getIntByIntFloatInt(i, f, i1);
      float f17 = 3.0F;
      float f16 = 1.0F;
      float f15 = 0.0F;
      int j1 = 436207616;
      float f14 = 0.0F;
      byte b0 = 0;
      float f13 = 8.0F;
      float f12 = 8.0F;
      float f11 = 8.0F;
      float f10 = 8.0F;
      float f9 = 26.0F;
      float f8 = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f8, j1, j, value4, value2, f17, f10, f9, f16, matrix4f2, value3, b0, f12, f14, f11, f13, f15
      );
      float f1 = value4 + 7.0F;
      float f2 = value2 + 7.0F;
      float f3 = this.tween7.getFloat();
      Matrix4f matrix4f = matrix4f2;
      if (f3 > 1.0E-4F && f3 < 0.9999F) {
         float f4 = f3 * (float) (Math.PI * 2);
         float f5 = f1 + 6.0F;
         float f6 = f2 + 6.0F;
         matrix4f = new Matrix4f(matrix4f2).translate(f5, f6, 0.0F).rotateZ(f4).translate(-f5, -f6, 0.0F);
      }

      CategoryType categorytype1 = CategoryType.RESET;
      int k1 = Theme.foreground();
      float f19 = 12.0F;
      float f18 = 12.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, k1, matrix4f, f2, categorytype, f19, f1, f18);
   }

   private float getFloat8() {
      float f1 = this.getFloatByFloat(this.value237);
      float f = this.getFloatByFloat2(this.value237);
      return this.getFloatByFloatFloat2(f1, f);
   }

   private boolean isDoubleDouble4(double value, double value2) {
      float f = this.getFloat8();
      float f1 = this.value235 + 4.0F;
      float f2 = f + 4.0F;
      float f3 = this.value237 - 12.0F - 26.0F;
      return value >= f1 && value <= f1 + f3 && value2 >= f2 && value2 <= f2 + 26.0F;
   }
}
