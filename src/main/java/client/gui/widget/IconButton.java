package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.SvgShader;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class IconButton extends LabelWidget {
   private CategoryType categoryType;
   private CategoryType categoryType2;
   private boolean flag4;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private Runnable runnable;
   private String text;

   public IconButton(CategoryType categoryType2, float value) {
      this.categoryType = categoryType2;
      this.value237 = value;
      this.value238 = value;
   }

   public void setCategoryType2(CategoryType categoryType) {
      this.categoryType2 = categoryType;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.getFloatByFloatFloat(value3, value2);
      this.tween4.setFloat2(this.flag4 ? 1.0F : 0.0F);
      float f1 = this.categoryType2 != null ? this.tween4.getFloat() : 0.0F;
      int l = Theme.foreground();
      int i1 = Theme.primary();
      float f2 = Math.max(f, f1);
      int k = i1;
      int j = l;
      int i = AnimatedInt.getIntByIntFloatInt(k, f2, j);
      if (f1 < 0.999F) {
         float f7 = value * (1.0F - f1);
         float f6 = this.value238;
         float f5 = this.value237;
         float f4 = this.value236;
         float f3 = this.value235;
         CategoryType categorytype = this.categoryType;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f7, i, matrix4f, f4, categorytype, f6, f3, f5);
      }

      if (f1 > 0.001F) {
         float f12 = value * f1;
         float f11 = this.value238;
         float f10 = this.value237;
         float f9 = this.value236;
         float f8 = this.value235;
         CategoryType categorytype1 = this.categoryType2;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f12, i, matrix4f, f9, categorytype1, f11, f8, f10);
      }

      if (this.flag && this.text != null) {
         float f15 = this.value237;
         float f14 = this.value236;
         float f13 = this.value235;
         String s = this.text;
         HeaderPainter.onFloatFloatStringFloat(f13, f14, s, f15);
      }
   }

   public void setFlag4(boolean flag) {
      this.flag4 = flag;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public void setCategoryType(CategoryType categoryType2) {
      this.categoryType = categoryType2;
   }

   @Override
   protected boolean isDoubleDouble2(double value, double value2) {
      if (this.runnable != null) {
         this.runnable.run();
      }

      return true;
   }
}
