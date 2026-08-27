package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.Setting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public abstract class ModuleWidget<M extends ButtonWidget> extends PanelWidget {
   private static final float value252 = 32.0F;
   private static final float value253 = 8.0F;
   private static final float value254 = 12.0F;
   private static final float value255 = 12.0F;
   private static final float value256 = 12.0F;
   private static final float value257 = 0.85F;
   protected final M buttonWidget;
   private final Tween tween4 = EasingPresets.getTween();

   protected ModuleWidget(Setting setting2, ButtonWidget buttonWidget2) {
      super(setting2);
      this.buttonWidget = (M)buttonWidget2;
   }

   @Override
   public void update3() {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.update4();
      }
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isIntDoubleDouble(count, value, value2);
         return true;
      } else if (count != 0) {
         return false;
      } else {
         float f4 = this.value235;
         float f5 = this.getFloat8();
         float f3 = 32.0F;
         float f2 = this.value237;
         float f1 = f5;
         float f = f4;
         if (!isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
            return false;
         } else {
            this.onColorSupplier(
               () -> new float[]{this.getFloatByFloat3(this.buttonWidget.getValue237()), this.getFloat8() + 16.0F - this.buttonWidget.getValue238() / 2.0F}
            );
            return true;
         }
      }
   }

   protected abstract CategoryType getCategoryType2();

   @Override
   public boolean check2() {
      return this.check4() && this.buttonWidget.check();
   }

   protected final float getFloatByFloat3(float value) {
      float f = UiContext.getInstance().getFloat3();
      float f1 = 28.0F;
      float f2 = this.value235 + this.value237 + f1;
      float f3 = this.value235 - f1 - value;
      boolean flag = f2 + value + 8.0F <= f;
      if (flag) {
         return f2;
      } else {
         boolean flag1 = f3 >= 8.0F;
         return flag1 ? f3 : Math.max(8.0F, f - value - 8.0F);
      }
   }

   @Override
   public boolean isDoubleDouble2(double value, double value2) {
      if (this.check4() && this.buttonWidget.check()) {
         float f4 = this.buttonWidget.getValue235();
         float f5 = this.buttonWidget.getValue236();
         float f3 = this.buttonWidget.getValue238();
         float f2 = this.buttonWidget.getValue237();
         float f1 = f5;
         float f = f4;
         return isFloatFloatDoubleFloatFloatDouble(f, f1, value, f3, f2, value2);
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
      double d2 = value3;
      double d3 = value2;
      float f14 = 32.0F;
      float f13 = this.value237;
      float f12 = this.value235;
      double d1 = d3;
      double d0 = d2;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(f12, f2, d1, f14, f13, d0);
      this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      float f3 = this.tween4.getFloat();
      int i2 = Theme.background();
      int j2 = Theme.elevated();
      float f15 = 0.85F;
      int i1 = j2;
      int l = i2;
      int i = AnimatedInt.getIntByIntFloatInt(i1, f15, l);
      int j1 = Theme.background();
      int j = AnimatedInt.getIntByIntFloatInt(i, f3, j1);
      int k = Theme.foreground();
      float f31 = this.value235;
      float f33 = this.value237;
      int k2 = Theme.border();
      float f26 = 1.0F;
      float f25 = 1.0F;
      float f24 = 0.0F;
      int l1 = 436207616;
      float f23 = 1.0F;
      int k1 = k2;
      float f22 = 8.0F;
      float f21 = 8.0F;
      float f20 = 8.0F;
      float f19 = 8.0F;
      float f18 = 32.0F;
      float f17 = f33;
      float f16 = f31;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f17, l1, j, f16, f2, f26, f19, f18, f25, matrix4f, value, k1, f21, f23, f20, f22, f24
      );
      String s = this.getString();
      float f4 = TextShader.getFloatByStringFloat(s, 14.0F);
      float f5 = 8.0F;
      float f6 = 12.0F + f5 + f4;
      float f7 = this.value235 + (this.value237 - f6) / 2.0F;
      float f8 = f2 + 10.0F;
      float f9 = f2 + 9.0F;
      CategoryType categorytype1 = this.getCategoryType2();
      float f28 = 12.0F;
      float f27 = 12.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, k, matrix4f, f8, categorytype, f28, f7, f27);
      float f32 = f7 + 12.0F + f5;
      float f30 = 14.0F;
      float f29 = f32;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f9, f29, k, f30, value, s, matrix4f);
      this.value238 = f2 + 32.0F - this.value236;
   }

   @Override
   public float getFloat5() {
      return this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 32.0F;
   }

   protected boolean check4() {
      return false;
   }

   @Override
   public void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.onFloatFloatFloatMatrix4f2(value, value2, value3, matrix4f);
      }
   }

   protected abstract String getString();

   @Override
   public boolean isIntChar(int count, char symbol) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isIntChar(count, symbol);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isIntIntInt2(count, count2, count3);
         return true;
      } else {
         return false;
      }
   }

   protected final float getFloat8() {
      float f1 = this.getFloatByFloat(this.value237);
      float f = this.getFloatByFloat2(this.value237);
      return this.getFloatByFloatFloat2(f1, f);
   }

   protected abstract void onColorSupplier(ColorSupplier colorSupplier);

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isDoubleDoubleInt(value, value2, count);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.isDoubleDoubleDouble(value, value2, value3);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void update2() {
      if (this.check4() && this.buttonWidget.check()) {
         this.buttonWidget.update4();
      }
   }
}
