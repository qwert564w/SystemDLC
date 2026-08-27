package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.module.CategoryType;
import client.render.SvgShader;
import org.joml.Matrix4f;

public class IconLabel extends LabelWidget {
   private CategoryType categoryType;
   private final float value239;
   private final float value240;
   private Runnable runnable;

   public IconLabel(CategoryType categoryType2, float value, float value2, float value3, float value4) {
      this.categoryType = categoryType2;
      this.value237 = value;
      this.value238 = value2;
      this.value239 = value3;
      this.value240 = value4;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.getFloatByFloatFloat(value3, value2);
      int l = Theme.mutedFg();
      int k = Theme.foreground();
      int j = l;
      int i = AnimatedInt.getIntByIntFloatInt(k, f, j);
      float f1 = this.value235 + (this.value237 - this.value239) / 2.0F;
      float f2 = this.value236 + (this.value238 - this.value240) / 2.0F;
      float f4 = this.value240;
      float f3 = this.value239;
      CategoryType categorytype = this.categoryType;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f2, categorytype, f4, f1, f3);
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

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }
}
