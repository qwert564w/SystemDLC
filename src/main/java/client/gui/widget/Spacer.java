package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import org.joml.Matrix4f;

public class Spacer implements UiMetrics {
   public static final float value235 = 14.0F;
   public static final float value236 = 4.0F;

   public static void onFloatFloatCategoryTypeFloatMatrix4f(float value, float value2, CategoryType categoryType, float value3, Matrix4f matrix4f) {
      float f2 = 14.0F;
      float f1 = 14.0F;
      float f = 20.0F;
      onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(categoryType, matrix4f, value3, f2, f, value, value2, f1);
   }

   public static void onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(
      CategoryType categoryType, Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, float value6
   ) {
      int i = Theme.elevated();
      float f2 = 4.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f2, value, i, matrix4f, value3, value3, value5, value4);
      float f = value + (value3 - value6) / 2.0F;
      float f1 = value4 + (value3 - value2) / 2.0F;
      int j = Theme.mutedFg();
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value5, j, matrix4f, f1, categoryType, value2, f, value6);
   }
}
