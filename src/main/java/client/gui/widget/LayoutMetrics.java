package client.gui.widget;

import client.api.Theme;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import org.joml.Matrix4f;

public final class LayoutMetrics {
   public static final float value = 36.0F;
   public static final float value2 = 12.0F;
   public static final float value3 = 8.0F;
   public static final float value4 = 5.0F;
   public static final float value5 = 5.0F;
   public static final float value6 = 8.0F;
   public static final float value7 = 1.0F;
   public static final float value8 = 6.0F;
   public static final float value9 = 14.0F;
   public static final float value10 = 12.0F;

   public static void onStringFloatFloatFloatFloatMatrix4fFloat(String text, float value, float value2, float value3, float value4, Matrix4f matrix4f, float value5) {
      if (text != null && !text.isEmpty() && !(value3 <= 0.0F)) {
         float f = value5 + value + 6.0F + 5.0F - 1.0F;
         float f1 = value4 + value2 / 2.0F - 18.0F;
         onFloatFloatStringMatrix4fFloat(f1, f, text, matrix4f, value3);
      }
   }

   public static void onFloatMatrix4fStringFloatFloat(float value, Matrix4f matrix4f, String text, float value2, float value3) {
      if (text != null && !text.isEmpty() && !(value <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = 36.0F;
         int i = Theme.primary();
         float f7 = 14.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value2, i, matrix4f, f1, f, value, value3);
         float f2 = TextShader.getFloatByStringFloat(text, 12.0F);
         float f3 = value2 + (f - f2) / 2.0F;
         float f4 = value3 + (f1 - 12.0F) / 2.0F;
         int j = Theme.elevated();
         float f8 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, j, f8, value, text, matrix4f);
         float f5 = value2 + f - 1.0F;
         float f6 = value3 + (f1 - 8.0F) / 2.0F;
         float f10 = 8.0F;
         float f9 = 5.0F;
         CategoryType categorytype = CategoryType.TOOLTIP_ARROW_RIGHT;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f6, categorytype, f10, f5, f9);
      }
   }

   public static void onFloatFloatMatrix4fFloatFloatString(float value, float value2, Matrix4f matrix4f, float value3, float value4, String text) {
      if (text != null && !text.isEmpty() && !(value2 <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = value - 6.0F - 5.0F + 1.0F - f;
         float f2 = value4 + value3 / 2.0F - 18.0F;
         onFloatMatrix4fStringFloatFloat(value2, matrix4f, text, f1, f2);
      }
   }

   public static void onFloatFloatStringMatrix4fFloat(float value, float value2, String text, Matrix4f matrix4f, float value3) {
      if (text != null && !text.isEmpty() && !(value3 <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = 36.0F;
         int i = Theme.primary();
         float f7 = 14.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value2, i, matrix4f, f1, f, value3, value);
         float f2 = TextShader.getFloatByStringFloat(text, 12.0F);
         float f3 = value2 + (f - f2) / 2.0F;
         float f4 = value + (f1 - 12.0F) / 2.0F;
         int j = Theme.elevated();
         float f8 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, j, f8, value3, text, matrix4f);
         float f5 = value2 - 5.0F + 1.0F;
         float f6 = value + (f1 - 8.0F) / 2.0F;
         float f10 = 8.0F;
         float f9 = 5.0F;
         CategoryType categorytype = CategoryType.TOOLTIP_ARROW_LEFT;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f6, categorytype, f10, f5, f9);
      }
   }

   public static void onFloatFloatFloatStringMatrix4f(float value, float value2, float value3, String text, Matrix4f matrix4f) {
      if (text != null && !text.isEmpty() && !(value <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = 36.0F;
         int i = Theme.primary();
         float f7 = 14.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value3, i, matrix4f, f1, f, value, value2);
         float f2 = TextShader.getFloatByStringFloat(text, 12.0F);
         float f3 = value3 + (f - f2) / 2.0F;
         float f4 = value2 + (f1 - 12.0F) / 2.0F;
         int j = Theme.elevated();
         float f8 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, j, f8, value, text, matrix4f);
         float f5 = value3 + (f - 8.0F) / 2.0F;
         float f6 = value2 - 5.0F + 1.0F;
         float f10 = 5.0F;
         float f9 = 8.0F;
         CategoryType categorytype = CategoryType.TOOLTIP_ARROW_UP;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f6, categorytype, f10, f5, f9);
      }
   }

   public static float getFloatByString(String text) {
      return TextShader.getFloatByStringFloat(text, 12.0F) + 24.0F;
   }

   public static void onFloatFloatFloatStringFloatMatrix4f(float value, float value2, float value3, String text, float value4, Matrix4f matrix4f) {
      if (text != null && !text.isEmpty() && !(value3 <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = value4 + value / 2.0F - f / 2.0F;
         float f2 = value2 - 6.0F - 5.0F + 1.0F - 36.0F;
         onFloatFloatFloatMatrix4fString(value3, f1, f2, matrix4f, text);
      }
   }

   public static void onStringFloatMatrix4fFloatFloatFloatFloat(String text, float value, Matrix4f matrix4f, float value2, float value3, float value4, float value5) {
      if (text != null && !text.isEmpty() && !(value4 <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = value + value3 / 2.0F - f / 2.0F;
         float f2 = value2 + value5 + 6.0F + 5.0F - 1.0F;
         onFloatFloatFloatStringMatrix4f(value4, f2, f1, text, matrix4f);
      }
   }

   public static void onFloatFloatFloatMatrix4fString(float value, float value2, float value3, Matrix4f matrix4f, String text) {
      if (text != null && !text.isEmpty() && !(value <= 0.0F)) {
         float f = getFloatByString(text);
         float f1 = 36.0F;
         int i = Theme.primary();
         float f7 = 14.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value2, i, matrix4f, f1, f, value, value3);
         float f2 = TextShader.getFloatByStringFloat(text, 12.0F);
         float f3 = value2 + (f - f2) / 2.0F;
         float f4 = value3 + (f1 - 12.0F) / 2.0F;
         int j = Theme.elevated();
         float f8 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, j, f8, value, text, matrix4f);
         float f5 = value2 + (f - 8.0F) / 2.0F;
         float f6 = value3 + f1 - 1.0F;
         float f10 = 5.0F;
         float f9 = 8.0F;
         CategoryType categorytype = CategoryType.TOOLTIP_ARROW;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f6, categorytype, f10, f5, f9);
      }
   }
}
