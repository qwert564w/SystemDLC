package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.render.ShapeShader;
import client.util.MathUtil;
import org.joml.Matrix4f;

public final class PanelPainter {
   private static final float value = 0.78F;
   private static final float value2 = 0.1F;
   private static final float value3 = 0.12F;

   private PanelPainter() {
   }

   public static int getIntByInt(int count) {
      int i = 0xFF000000 | Theme.background() & 16777215;
      int j = 0xFF000000 | Theme.foreground() & 16777215;
      float f = 0.78F;
      int k = AnimatedInt.getIntByIntFloatInt(i, f, count);
      if (MathUtil.getFloatByIntInt(i, k) < 0.1F) {
         float f1 = 0.12F;
         k = AnimatedInt.getIntByIntFloatInt(j, f1, k);
      }

      return k;
   }

   public static void onFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      int k1 = Theme.border();
      int l = Theme.primary();
      int k = k1;
      int i = AnimatedInt.getIntByIntFloatInt(l, value4, k);
      k1 = Theme.foreground();
      int j1 = Theme.background();
      int i1 = k1;
      int j = AnimatedInt.getIntByIntFloatInt(j1, value4, i1);
      float f4 = 8.0F;
      float f3 = 16.0F;
      float f2 = 28.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f4, value3, i, matrix4f, f3, f2, value2, value);
      float f = value3 + 2.0F + value4 * 12.0F;
      float f1 = value + 2.0F;
      float f7 = 6.0F;
      float f6 = 12.0F;
      float f5 = 12.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, f, j, matrix4f, f6, f5, value2, f1);
   }

   public static void onFloatMatrix4fBooleanFloatFloatFloatTweenFloatFloat(
      float value, Matrix4f matrix4f, boolean flag, float value2, float value3, float value4, Tween tween, float value5, float value6
   ) {
      tween.setFloat2(flag ? 1.0F : 0.0F);
      float f = tween.getFloat();
      int k1 = Theme.surface();
      int l1 = Theme.elevated();
      float f1 = 0.85F;
      int l = l1;
      int k = k1;
      int i = AnimatedInt.getIntByIntFloatInt(l, f1, k);
      int i1 = Theme.surface();
      int j = AnimatedInt.getIntByIntFloatInt(i, f, i1);
      float f5 = 3.0F;
      float f4 = 1.0F;
      float f3 = 0.0F;
      int j1 = 436207616;
      float f2 = 0.0F;
      byte b0 = 0;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value5, j1, j, value6, value3, f5, value2, value4, f4, matrix4f, value, b0, value2, f2, value2, value2, f3
      );
   }
}
