package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.render.ShapeShader;
import org.joml.Matrix4f;

public class ToggleButton extends LabelWidget {
   private boolean flag4;
   private Runnable runnable;
   private float value239;
   private float value240;
   private boolean flag5;
   private boolean flag6;
   private boolean flag7;
   private double value241;
   private long time;

   public ToggleButton(boolean flag) {
      this.flag4 = flag;
      this.value239 = flag ? 1.0F : 0.0F;
      this.value240 = this.value239;
      this.time = System.nanoTime();
      this.value237 = 28.0F;
      this.value238 = 16.0F;
   }

   private void setBoolean(boolean flag) {
      if (this.flag4 != flag) {
         this.flag4 = flag;
         if (this.runnable != null) {
            this.runnable.run();
         }
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      long i = System.nanoTime();
      float f = (float)((i - this.time) / 1.0E9);
      this.time = i;
      this.value240 = this.value240 + (this.value239 - this.value240) * (1.0F - (float)Math.exp(-14.0F * f));
      float f1 = this.value240;
      int l1 = Theme.border();
      int i1 = Theme.primary();
      int l = l1;
      int j = AnimatedInt.getIntByIntFloatInt(i1, f1, l);
      l1 = Theme.foreground();
      int k1 = Theme.background();
      int j1 = l1;
      int k = AnimatedInt.getIntByIntFloatInt(k1, f1, j1);
      float f8 = this.value238 / 2.0F;
      float f7 = this.value238;
      float f6 = this.value237;
      float f5 = this.value236;
      float f4 = this.value235;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, f4, j, matrix4f, f7, f6, value, f5);
      float f2 = this.value235 + 2.0F + f1 * (this.value237 - 12.0F - 4.0F);
      float f3 = this.value236 + (this.value238 - 12.0F) / 2.0F;
      float f11 = 6.0F;
      float f10 = 12.0F;
      float f9 = 12.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f2, k, matrix4f, f10, f9, value, f3);
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag5 && count == 0) {
         boolean flag = this.flag6;
         this.flag5 = false;
         this.flag6 = false;
         boolean flag1 = flag ? this.value239 >= 0.5F : !this.flag7;
         this.setBoolean(flag1);
         this.value239 = flag1 ? 1.0F : 0.0F;
         return true;
      } else {
         return false;
      }
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag5 && count == 0) {
         if (!this.flag6 && Math.abs(value2 - this.value241) >= 1.5) {
            this.flag6 = true;
         }

         if (!this.flag6) {
            return true;
         } else {
            this.value239 = (float)Math.clamp((value2 - (this.value235 + 2.0F + 6.0F)) / (this.value237 - 12.0F - 4.0F), 0.0, 1.0);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   protected boolean isDoubleDouble2(double value, double value2) {
      this.flag5 = true;
      this.flag6 = false;
      this.flag7 = this.flag4;
      this.value241 = value2;
      return true;
   }

   public void setBoolean2(boolean flag) {
      if (this.flag4 != flag) {
         this.flag4 = flag;
         this.value239 = flag ? 1.0F : 0.0F;
      }
   }
}
