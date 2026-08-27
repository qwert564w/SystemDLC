package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class SearchResultRow extends Widget {
   private static final float value239 = 0.82F;
   private static final float value240 = 1.0F;
   private static final float value241 = 3.5F;
   private static final float value242 = 7.0F;
   private static final float value243 = 1.0F;
   private static final float value244 = 5.0F;
   private static final float value245 = 16.0F;
   private static final float value246 = 11.0F;
   private static final float value247 = 4.0F;
   private final CategoryType categoryType;
   private final String text;
   private final String text2;
   private final boolean flag4;
   private final String text3;
   private final Runnable runnable;
   private final Tween tween4 = EasingPresets.getTween();
   private boolean flag5;

   public SearchResultRow(CategoryType categoryType, String text, String text2, Runnable runnable) {
      this(categoryType, text, text2, false, null, runnable);
   }

   public SearchResultRow(CategoryType categoryType2, String text4, String text5, boolean flag, String text6, Runnable runnable2) {
      this.categoryType = categoryType2;
      this.text = text4;
      this.text2 = text5;
      this.flag4 = flag;
      this.text3 = text6;
      this.runnable = runnable2;
      this.value237 = 440.0F;
      this.value238 = 32.0F;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count == 0 && this.isDoubleDouble(value, value2) && this.runnable != null) {
         this.runnable.run();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.flag = this.isDoubleDouble(value3, value2);
      float f = this.flag5 ? 1.0F : (this.flag ? 0.82F : 0.0F);
      this.tween4.setFloat2(f);
      float f1 = this.tween4.getFloat();
      if (f1 > 0.001F) {
         float f33 = this.value235;
         float f34 = this.value236;
         float f35 = this.value237;
         float f36 = this.value238;
         int k1 = Theme.elevated();
         float f17 = value * f1;
         int i = k1;
         float f16 = 8.0F;
         float f15 = f36;
         float f14 = f35;
         float f13 = f34;
         float f12 = f33;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f16, f12, i, matrix4f, f15, f14, f17, f13);
      }

      float f2 = this.value235 + 9.0F;
      float f3 = this.value236 + (this.value238 - 12.0F) / 2.0F;
      CategoryType categorytype2 = this.categoryType;
      int j = Theme.mutedFg();
      float f19 = 12.0F;
      float f18 = 12.0F;
      CategoryType categorytype = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j, matrix4f, f3, categorytype, f19, f2, f18);
      float f4 = this.value236 + (this.value238 - 14.0F) / 2.0F;
      float f5 = f2 + 12.0F + 9.0F;
      float f6 = this.value235 + this.value237 - 9.0F;
      float f7 = 0.0F;
      if (this.text3 != null) {
         float f20 = 11.0F;
         String s = this.text3;
         f7 = TextShader.getFloatByFloatString(f20, s) + 10.0F;
         f6 -= f7 + 9.0F;
      }

      float f24 = this.value238;
      float f23 = this.value237;
      float f22 = this.value236;
      float f21 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f23, f24, f22, f21);
      if (this.text2 != null) {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, this.text, f5, f4, 14.0F, Theme.mutedFg(), value);
         float f25 = 14.0F;
         String s1 = this.text;
         f5 += TextShader.getFloatByFloatString(f25, s1) + 11.0F;
         float f8 = this.value236 + (this.value238 - 7.0F) / 2.0F + 1.0F;
         categorytype2 = CategoryType.CHEVRON_RIGHT;
         int k = Theme.mutedFg();
         float f27 = 7.0F;
         float f26 = 3.5F;
         CategoryType categorytype1 = categorytype2;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, k, matrix4f, f8, categorytype1, f27, f5, f26);
         f5 += 14.5F;
         float f9 = Math.max(0.0F, f6 - f5);
         if (f9 > 0.0F) {
            if (this.flag4) {
               TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, this.text2, f5, f4, 14.0F, Theme.foreground(), value);
            } else {
               TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, this.text2, f5, f4, 14.0F, Theme.foreground(), value);
            }
         }
      } else {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, this.text, f5, f4, 14.0F, Theme.foreground(), value);
      }

      ScissorStack.update();
      if (this.text3 != null) {
         float f31 = this.value235 + this.value237 - 9.0F - f7;
         float f32 = this.value236 + (this.value238 - 16.0F) / 2.0F;
         int j1 = Theme.elevated();
         int l1 = Theme.border();
         float f30 = 1.0F;
         int i1 = l1;
         int l = j1;
         float f29 = 4.0F;
         float f28 = 16.0F;
         ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f30, f7, value, matrix4f, f32, l, f29, f31, i1, f28);
         float f10 = f31 + 5.0F;
         float f11 = f32 + 2.5F;
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, this.text3, f10, f11, 11.0F, Theme.mutedFg(), value);
      }
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   public void setFlag5(boolean flag) {
      this.flag5 = flag;
   }
}
