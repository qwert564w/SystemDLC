package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedColor;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ColorSetting;
import client.util.EasingPresets;
import client.util.MathUtil;
import org.joml.Matrix4f;

public class ColorPickerRow extends PanelWidget {
   private static final int[] intArray = new int[]{-1, -16777216, -1096636, -429290, -340971, -14498466, -12877066};
   private static final float value252 = 10.0F;
   private static final float value253 = 16.0F;
   private static final float value254 = 16.0F;
   private static final float value255 = 10.0F;
   private static final float value256 = 6.0F;
   private static final float value257 = 14.0F;
   private static final float value258 = 13.0F;
   private static final float value259 = 12.0F;
   private static final float value260 = 4.0F;
   private static final float value261 = 20.0F;
   private static final float value262 = 4.0F;
   private static final float value263 = 2.0F;
   private static final float value264 = 0.78F;
   private static final float value265 = 0.1F;
   private static final float value266 = 0.12F;
   private static final float value267 = 29.0F;
   private static final float value268 = 26.0F;
   private final ColorSetting colorSetting;
   private final ColorPicker colorPicker;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.15F);
   private int value269 = -1;
   private final AnimatedColor animatedColor;
   private final AnimatedInt animatedInt;
   private final TextInputState textInputState = new TextInputState(12.0F).getTextInputStateByBoolean(true).getTextInputStateByBoolean2(true);
   private boolean flag4;
   private float value270;
   private float value271;
   private float value272;
   private int value273 = Integer.MIN_VALUE;
   private String text;
   private float value274;
   private int value275 = -2;

   public ColorPickerRow(ColorSetting colorSetting2) {
      super(colorSetting2);
      this.colorSetting = colorSetting2;
      this.colorPicker = new ColorPicker(colorSetting2);
      int i = 0xFF000000 | colorSetting2.getInt3() & 16777215;
      this.animatedColor = new AnimatedColor(i, i, 0.0F);
      this.animatedInt = new AnimatedInt(i, 0.22F);
      int j = this.getInt();
      if (j >= 0) {
         this.value269 = j;
         this.tween4.setFloat(1.0F);
         this.tween5.setFloat(j);
      }

      this.textInputState.setRunnable(() -> this.setBoolean(true));
   }

   private float getFloat8() {
      return this.getFloat11() + 10.0F + 6.0F;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         if (this.flag4) {
            this.setBoolean(true);
         }

         return false;
      } else {
         float f10 = 20.0F;
         float f9 = this.value272;
         float f8 = this.value271;
         float f7 = this.value270;
         if (isFloatFloatDoubleFloatFloatDouble(f7, f8, value2, f10, f9, value)) {
            if (!this.flag4) {
               this.update7();
            }

            return true;
         } else {
            if (this.flag4) {
               this.setBoolean(true);
            }

            float f = this.value237 - this.getFloat9() - 8.0F;
            float f12 = this.getFloatByFloat(f);
            float f11 = this.getFloatByFloat2(f);
            float f1 = this.getFloatByFloatFloat2(f12, f11);
            float f2 = this.getFloat10();
            float f3 = f1 + 9.5F;
            float f14 = 13.0F;
            float f13 = 14.0F;
            if (isFloatFloatDoubleFloatFloatDouble(f2, f3, value2, f14, f13, value)) {
               this.colorPicker.setColorSupplier2(() -> new float[]{this.value235 + this.value237 + 6.0F, this.value236 + 7.0F - 191.5F});
               return true;
            } else {
               float f17 = 32.0F;
               float f16 = this.value237;
               float f15 = this.value235;
               if (isFloatFloatDoubleFloatFloatDouble(f15, f1, value2, f17, f16, value)) {
                  float f4 = f1 + 3.0F;

                  for (int i = 0; i < intArray.length; i++) {
                     float f5 = this.getFloatByInt2(i);
                     float f6 = f5 + -6.5F;
                     float f19 = 26.0F;
                     float f18 = 29.0F;
                     if (isFloatFloatDoubleFloatFloatDouble(f6, f4, value2, f19, f18, value)) {
                        this.colorSetting.setInt(intArray[i]);
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   private float getFloat9() {
      this.update6();
      return this.value274 + 8.0F;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      int i = 0xFF000000 | this.colorSetting.getInt3() & 16777215;
      int j = 0xFF000000 | Theme.background() & 16777215;
      int k = 0xFF000000 | Theme.foreground() & 16777215;
      float f14 = 0.78F;
      int l = AnimatedInt.getIntByIntFloatInt(j, f14, i);
      if (MathUtil.getFloatByIntInt(j, l) < 0.1F) {
         float f15 = 0.12F;
         l = AnimatedInt.getIntByIntFloatInt(k, f15, l);
      }

      String s = this.getString();
      float f = this.value274;
      float f1 = this.flag4 ? TextShader.getFloatByStringFloat(this.textInputState.getString2(), 12.0F) : f;
      float f2 = this.flag4 ? 2.0F : 0.0F;
      float f3 = Math.max(f1, f) + 8.0F + f2;
      this.animatedColor.onIntIntFloat(i, l, f3);
      int i1 = this.animatedColor.getInt2();
      int j1 = this.animatedColor.getInt();
      float f4 = this.animatedColor.getFloat();
      float f5 = this.value237 - f4 - 8.0F;
      float f6 = this.getFloatByFloatMatrix4fFloat(f5, matrix4f, value);
      float f7 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f6, f5);
      float f8 = this.value235 + this.value237 - f4;
      float f9 = this.value236 + -3.0F;
      float f17 = 4.0F;
      float f16 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f17, f8, i1, matrix4f, f16, f4, value, f9);
      float f10 = f9 + 4.0F;
      float f11 = f8 + 4.0F;
      float f12 = f4 - 8.0F;
      if (this.flag4) {
         boolean flag = true;
         this.textInputState.onFloatIntMatrix4fFloatBooleanFloatFloat(f10, j1, matrix4f, value, flag, f11, f12);
      } else {
         float f18 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f10, f11, j1, f18, value, s, matrix4f);
      }

      this.value270 = f8;
      this.value271 = f9;
      this.value272 = f4;
      float f13 = this.getFloatByFloatFloat2(f7, f6);
      this.onFloatFloatFloatMatrix4fFloat(value, value3, f13, matrix4f, value2);
      this.value238 = f13 + 32.0F - this.value236;
   }

   @Override
   protected float getFloat3() {
      return 12.0F;
   }

   private void update6() {
      int i = this.colorSetting.getInt3();
      if (i != this.value273 || this.text == null) {
         this.value273 = i;
         this.text = String.format("#%02X%02X%02X", i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF);
         this.value274 = TextShader.getFloatByStringFloat(this.text, 12.0F);
         int j = -1;

         for (int k = 0; k < intArray.length; k++) {
            if (MathUtil.isIntInt(intArray[k], i)) {
               j = k;
               break;
            }
         }

         this.value275 = j;
      }
   }

   private float getFloat10() {
      return this.getFloat8() + 1.0F + 10.0F;
   }

   @Override
   public float getFloat5() {
      float f = this.value237 - this.getFloat9() - 8.0F;
      return this.getFloatByFloatFloat3(this.getFloatByFloat2(f), this.getFloatByFloat(f)) + 8.0F + 32.0F;
   }

   private void update7() {
      this.textInputState.setString2(this.getString());
      this.flag4 = true;
   }

   @Override
   protected float getFloat6() {
      return 12.0F;
   }

   private static boolean isChar(char symbol) {
      return symbol >= '0' && symbol <= '9' || symbol >= 'a' && symbol <= 'f' || symbol >= 'A' && symbol <= 'F';
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      if (!this.flag4) {
         return false;
      } else if (!isChar(symbol) && symbol != '#') {
         return true;
      } else {
         this.textInputState.isChar(symbol);
         return true;
      }
   }

   private void onFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      float f37 = this.value235;
      float f39 = this.value237;
      int l = Theme.elevated();
      float f10 = 8.0F;
      float f9 = 32.0F;
      float f8 = f39;
      float f7 = f37;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f10, f7, l, matrix4f, f9, f8, value, value3);
      int i = this.getInt();
      if (i >= 0) {
         if (this.value269 < 0) {
            this.tween5.setFloat(i);
         }

         this.tween5.setFloat2(i);
         this.tween4.setFloat2(1.0F);
         this.value269 = i;
      } else {
         this.tween4.setFloat2(0.0F);
         this.value269 = -1;
      }

      float f = this.tween4.getFloat();
      float f1 = this.tween5.getFloat();
      int j = 0xFF000000 | this.colorSetting.getInt3() & 16777215;
      this.animatedInt.setInt(j);
      int k = this.animatedInt.getInt2();
      if (f > 0.001F) {
         float f2 = this.value235 + 10.0F + f1 * 32.0F;
         float f3 = f2 + -6.5F;
         float f4 = value3 + 3.0F;
         int i3 = Theme.background();
         float f21 = value * f;
         float f20 = 2.0F;
         float f19 = 1.0F;
         float f18 = 0.0F;
         int j1 = 436207616;
         float f17 = 1.5F;
         int i1 = i3;
         float f16 = 8.0F;
         float f15 = 8.0F;
         float f14 = 8.0F;
         float f13 = 8.0F;
         float f12 = 26.0F;
         float f11 = 29.0F;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f11, j1, i1, f3, f4, f20, f13, f12, f19, matrix4f, f21, k, f15, f17, f14, f16, f18
         );
      }

      float f33 = 8.0F;
      float f34 = value3 + 8.0F;

      for (int k2 = 0; k2 < intArray.length; k2++) {
         float f5 = this.getFloatByInt2(k2);
         int l2 = intArray[k2];
         float f27 = 2.0F;
         float f26 = 1.0F;
         float f25 = 0.0F;
         int l1 = 436207616;
         float f24 = 0.0F;
         byte b0 = 0;
         int k1 = l2;
         float f23 = 16.0F;
         float f22 = 16.0F;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f22, l1, k1, f5, f34, f27, f33, f23, f26, matrix4f, value, b0, f33, f24, f33, f33, f25
         );
      }

      float f35 = this.getFloat8();
      float f38 = value3 + 8.0F;
      int i2 = Theme.border();
      float f30 = 16.0F;
      float f29 = 1.0F;
      float f28 = f38;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f30, f35, f28, f29, matrix4f, i2);
      float f36 = this.getFloat10();
      float f6 = value3 + 9.5F;
      CategoryType categorytype1 = CategoryType.BRUSH;
      int j2 = Theme.foreground();
      float f32 = 13.0F;
      float f31 = 14.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j2, matrix4f, f6, categorytype, f32, f36, f31);
   }

   private void setBoolean(boolean flag) {
      if (this.flag4) {
         this.flag4 = false;
         if (flag) {
            Integer integer = MathUtil.getIntegerByString(this.textInputState.getString2());
            if (integer != null) {
               int i = this.colorSetting.getInt3();
               int j = integer & 16777215;
               this.colorSetting.setInt(i & 0xFF000000 | j);
            }
         }
      }
   }

   private float getFloatByInt2(int count) {
      return this.value235 + 10.0F + count * 32.0F;
   }

   private float getFloat11() {
      return this.getFloatByInt2(intArray.length - 1) + 16.0F;
   }

   private String getString() {
      this.update6();
      return this.text;
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_COLOR;
   }

   private int getInt() {
      this.update6();
      return this.value275;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (count3 == 256) {
         this.setBoolean(false);
         return true;
      } else {
         this.textInputState.isIntInt(count2, count3);
         return true;
      }
   }
}
