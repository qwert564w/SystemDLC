package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.InputSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class TextInputRow extends PanelWidget {
   private static final float value252 = 14.0F;
   private static TextInputRow INSTANCE;
   private final InputSetting inputSetting;
   private final TextInputState textInputState = new TextInputState(14.0F);
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private boolean flag4;
   private float value253;

   public TextInputRow(InputSetting inputSetting2) {
      super(inputSetting2);
      this.inputSetting = inputSetting2;
      this.textInputState.setString2(this.inputSetting.getText() != null ? this.inputSetting.getText() : "");
      this.textInputState.setConsumer(this.inputSetting::setString);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         boolean flag = value >= this.value235 && value <= this.value235 + this.value237 && value2 >= this.value253 && value2 <= this.value253 + 32.0F;
         this.setBoolean(flag);
         return flag;
      }
   }

   @Override
   protected float getFloat3() {
      return 12.0F;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f6 = this.value237;
      float f = this.getFloatByFloatMatrix4fFloat(f6, matrix4f, value);
      float f7 = this.value237;
      float f1 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f, f7);
      this.value253 = this.getFloatByFloatFloat2(f1, f);
      float f2 = this.tween4.getFloat();
      int k1 = Theme.border();
      int k = Theme.primary();
      int j = k1;
      int i = AnimatedInt.getIntByIntFloatInt(k, f2, j);
      float f22 = this.value235;
      float f23 = this.value253;
      float f24 = this.value237;
      int i2 = Theme.background();
      float f19 = 1.0F;
      float f18 = 1.0F;
      float f17 = 0.0F;
      int i1 = 436207616;
      float f16 = 1.0F;
      int l = i2;
      float f15 = 8.0F;
      float f14 = 8.0F;
      float f13 = 8.0F;
      float f12 = 8.0F;
      float f11 = 32.0F;
      float f10 = f24;
      float f9 = f23;
      float f8 = f22;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f10, i1, l, f8, f9, f19, f12, f11, f18, matrix4f, value, i, f14, f16, f13, f15, f17
      );
      float f3 = this.value235 + 12.0F;
      float f4 = this.value253 + 9.0F;
      float f5 = this.value237 - 24.0F;
      float f21 = 32.0F;
      float f20 = this.value253;
      ScissorStack.onFloatFloatFloatFloat(f5, f21, f20, f3);
      if (this.textInputState.check2() && !this.flag4) {
         String s = this.inputSetting.getText3();
         if (s == null || s.isEmpty()) {
            s = "Введите текст";
         }

         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, f3, f4, 14.0F, Theme.mutedFg(), value);
      } else {
         TextInputState textinputstate = this.textInputState;
         int l1 = Theme.foreground();
         boolean flag = this.flag4;
         int j1 = l1;
         textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(f4, j1, matrix4f, value, flag, f3, f5);
      }

      ScissorStack.update();
      this.value238 = this.value253 + 32.0F - this.value236;
   }

   @Override
   public float getFloat5() {
      return this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 32.0F;
   }

   @Override
   protected float getFloat6() {
      return 12.0F;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (count3 != 256 && count3 != 257 && count3 != 335) {
         this.textInputState.isIntInt(count2, count3);
         return true;
      } else {
         this.setBoolean(false);
         return true;
      }
   }

   private void setBoolean(boolean flag) {
      if (this.flag4 != flag) {
         this.flag4 = flag;
         this.tween4.setFloat2(flag ? 1.0F : 0.0F);
         if (flag) {
            if (INSTANCE != null && INSTANCE != this) {
               INSTANCE.setBoolean(false);
            }

            INSTANCE = this;
         } else if (INSTANCE == this) {
            INSTANCE = null;
         }
      }
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return !this.flag4 ? false : this.textInputState.isChar(symbol);
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.EDIT;
   }
}
