package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.Setting;
import client.util.Easings;
import org.joml.Matrix4f;

public abstract class SegmentedRow extends PanelWidget {
   protected static final float value252 = 32.0F;
   protected static final float value253 = 26.0F;
   protected static final float value254 = 8.0F;
   protected static final float value255 = 0.22F;
   protected static final float value256 = 1.5F;
   protected static final float value257 = 14.0F;
   protected static final float value258 = 9.0F;
   protected final Tween tween4 = new Tween(0.0F, 0.22F).getTweenByFunction(Easings::getFloatByFloat7);
   private boolean flag4;
   private boolean flag5;
   private double value259;

   protected SegmentedRow(Setting setting2) {
      super(setting2);
   }

   protected abstract float getFloat8();

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         float f3 = this.getFloatByFloat2(this.value237 - this.getFloat9() - 8.0F);
         float f2 = this.getFloatByFloat(this.value237);
         float f1 = f3;
         float f = this.getFloatByFloatFloat2(f2, f1);
         if (!(value < this.value235) && !(value > this.value235 + this.value237) && !(value2 < f) && !(value2 > f + 32.0F)) {
            this.flag4 = true;
            this.flag5 = false;
            this.value259 = value;
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      String s = this.getString();
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f10 = this.value237 - f - 8.0F;
      float f1 = this.getFloatByFloatMatrix4fFloat(f10, matrix4f, value);
      float f11 = this.value237;
      float f2 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f1, f11);
      float f3 = this.value236 + 1.0F;
      float f28 = this.value235 + this.value237 - f;
      int i = Theme.primary();
      float f13 = 12.0F;
      float f12 = f28;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f12, i, f13, value, s, matrix4f);
      float f4 = this.getFloatByFloatFloat2(f2, f1);
      float f27 = this.value235;
      float f29 = this.value237;
      int j = Theme.elevated();
      float f17 = 8.0F;
      float f16 = 32.0F;
      float f15 = f29;
      float f14 = f27;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f17, f14, j, matrix4f, f16, f15, value, f4);
      if (!this.flag5) {
         this.tween4.setFloat2(this.getFloat8());
      }

      float f5 = this.tween4.getFloat();
      float f6 = this.getFloat10();
      float f7 = this.getInt() == 1
         ? this.getFloatByInt2(0)
         : this.getFloatByInt2(0) + (this.getFloatByInt2(this.getInt() - 1) - this.getFloatByInt2(0)) * f5 / (this.getInt() - 1);
      float f8 = f7 - f6 / 2.0F;
      float f9 = f4 + 3.0F;
      int i1 = Theme.background();
      float f26 = 2.0F;
      float f25 = 1.0F;
      float f24 = 0.0F;
      int l = 436207616;
      float f23 = 0.0F;
      byte b0 = 0;
      int k = i1;
      float f22 = 8.0F;
      float f21 = 8.0F;
      float f20 = 8.0F;
      float f19 = 8.0F;
      float f18 = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f6, l, k, f8, f9, f26, f19, f18, f25, matrix4f, value, b0, f21, f23, f20, f22, f24
      );
      this.onMatrix4fFloatFloat(matrix4f, f4, value);
      this.value238 = f4 + 32.0F - this.value236;
   }

   protected final float getFloat9() {
      return TextShader.getFloatByStringFloat(this.getString(), 12.0F);
   }

   @Override
   public float getFloat5() {
      float f = this.getFloatByFloat2(this.value237 - this.getFloat9() - 8.0F);
      float f1 = this.getFloatByFloat(this.value237);
      return this.getFloatByFloatFloat3(f, f1) + 8.0F + 32.0F;
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag4 && count == 0) {
         if (!this.flag5 && Math.abs(value2 - this.value259) >= 1.5) {
            this.flag5 = true;
         }

         if (!this.flag5) {
            return true;
         } else {
            this.tween4.setFloat2(this.getFloatByDouble(value2));
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag4 && count == 0) {
         boolean flag = this.flag5;
         this.flag4 = false;
         this.flag5 = false;
         int i = flag ? Math.round(this.getFloatByDouble(value)) : this.getIntByDouble(value);
         i = Math.clamp((long)i, 0, this.getInt() - 1);
         this.onInt(i);
         return true;
      } else {
         return false;
      }
   }

   protected float getFloatByDouble(double value) {
      float f = this.value237 / this.getInt();
      float f1 = (float)((value - this.value235) / f) - 0.5F;
      return Math.clamp(f1, 0.0F, this.getInt() - 1.0F);
   }

   protected int getIntByDouble(double value) {
      float f = this.value237 / this.getInt();
      return (int)Math.floor((value - this.value235) / f);
   }

   protected abstract String getString();

   protected abstract void onInt(int count);

   protected abstract void onMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2);

   protected final float getFloatByInt2(int count) {
      return this.value235 + this.value237 / this.getInt() * (count + 0.5F);
   }

   protected abstract float getFloat10();

   protected abstract int getInt();
}
