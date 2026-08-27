package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.module.CategoryType;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class ContextMenu extends Widget {
   public static final float value239 = 200.0F;
   private static final float value240 = 12.0F;
   private static final float value241 = 20.0F;
   private static final float value242 = 14.0F;
   private static final float value243 = 6.0F;
   private static final float value244 = 11.0F;
   private static final float value245 = 8.0F;
   private static final float value246 = 15.0F;
   private static final float value247 = 14.0F;
   private static final float value248 = 0.001F;
   private final List<RenderElement> list;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private boolean flag4;

   public ContextMenu(List list2) {
      this.list = list2;
      this.value237 = 200.0F;
      this.value238 = this.getFloat();
      this.tween4.setFloat2(1.0F);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         float f3 = (float)value;
         float f2 = (float)value2;
         float f1 = f3;
         if (!this.isFloatFloat(f2, f1)) {
            return false;
         } else {
            for (int i = 0; i < this.list.size(); i++) {
               float f = this.getFloatByInt2(i);
               if (value2 >= f && value2 <= f + 20.0F) {
                  RenderElement renderelement = this.list.get(i);
                  renderelement.onBoolean(!renderelement.check13());
                  return true;
               }
            }

            return true;
         }
      }
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.value238 = this.getFloat();
      float f12 = this.value235;
      float f13 = this.value236;
      float f14 = this.value237;
      float f15 = this.value238;
      int i1 = Theme.background();
      int j1 = Theme.border();
      float f11 = 6.0F;
      float f10 = 2.0F;
      float f9 = 0.0F;
      int l = 436207616;
      float f8 = 1.0F;
      int k = j1;
      int j = i1;
      float f7 = 14.0F;
      float f6 = 14.0F;
      float f5 = 14.0F;
      float f4 = 14.0F;
      float f3 = f15;
      float f2 = f14;
      float f1 = f13;
      float f = f12;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f2, l, j, f, f1, f11, f4, f3, f10, matrix4f, value, k, f6, f8, f5, f7, f9
      );

      for (int i = 0; i < this.list.size(); i++) {
         this.onIntFloatMatrix4f(i, value, matrix4f);
      }
   }

   private void onIntFloatMatrix4f(int count, float value, Matrix4f matrix4f) {
      RenderElement renderelement = this.list.get(count);
      float f = this.getFloatByInt2(count);
      float f1 = this.value235 + 12.0F;
      int i = Theme.elevated();
      float f6 = 4.0F;
      float f5 = 20.0F;
      float f4 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f6, f1, i, matrix4f, f5, f4, value, f);
      CategoryType categorytype = renderelement.getCategoryType();
      onFloatFloatFloatMatrix4fCategoryType(value, f, f1, matrix4f, categorytype);
      float f2 = f1 + 20.0F + 6.0F;
      float f3 = f + 3.0F;
      String s1 = renderelement.getString();
      int j = Theme.foreground();
      float f7 = 14.0F;
      String s = s1;
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, s, f7, f2, j, f3, matrix4f);
      if (renderelement.check13()) {
         this.onFloatFloatMatrix4f(f, value, matrix4f);
      }
   }

   private static void onFloatFloatFloatMatrix4fCategoryType(float value, float value2, float value3, Matrix4f matrix4f, CategoryType categoryType) {
      if (categoryType != null) {
         float f = Math.min(14.0F / categoryType.getWidth(), 14.0F / categoryType.getHeight());
         float f1 = categoryType.getWidth() * f;
         float f2 = categoryType.getHeight() * f;
         float f3 = value3 + (20.0F - f1) / 2.0F;
         float f4 = value2 + (20.0F - f2) / 2.0F;
         int i = Theme.mutedFg();
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f4, categoryType, f2, f3, f1);
      }
   }

   private void onFloatFloatMatrix4f(float value, float value2, Matrix4f matrix4f) {
      float f = this.value235 + this.value237 - 15.0F - 11.0F;
      float f1 = value + 6.0F;
      CategoryType categorytype1 = CategoryType.DROPDOWN_SUCCESS;
      int i = Theme.primary();
      float f3 = 8.0F;
      float f2 = 11.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, i, matrix4f, f1, categorytype, f3, f, f2);
   }

   public void update3() {
      if (!this.flag4) {
         this.flag4 = true;
         this.tween4.setFloat2(0.0F);
      }
   }

   public void onFloatFloatMatrix4fDrawContextFloat(float value, float value2, Matrix4f matrix4f2, DrawContext drawContext, float value3) {
      float f = this.tween4.getFloat();
      if (!(f <= 0.001F)) {
         float f1 = EasingPresets.getFloatByFloat2(f);
         float f2 = (1.0F - f) * -6.0F;
         float f3 = this.value235 + this.value237 / 2.0F;
         float f4 = this.value236 + this.value238 / 2.0F;
         MatrixStack matrixstack = drawContext.getMatrices();
         Matrix4f matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStack(f1, f3, f2, f4, matrixstack);

         try {
            float f5 = value2 * f;
            this.onFloatFloatFloatMatrix4f(f5, value3, value, matrix4f);
         } finally {
            matrixstack.pop();
         }
      }
   }

   private float getFloat() {
      int i = this.list.size();
      return i == 0 ? 24.0F : 12.0F + i * 20.0F + (i - 1) * 12.0F + 12.0F;
   }

   public boolean isFloatFloat(float value, float value2) {
      return value2 >= this.value235 && value2 <= this.value235 + this.value237 && value >= this.value236 && value <= this.value236 + this.value238;
   }

   private float getFloatByInt2(int count) {
      return this.value236 + 12.0F + count * 32.0F;
   }

   public boolean check() {
      return this.flag4 && this.tween4.getValue3() < 0.001F;
   }
}
