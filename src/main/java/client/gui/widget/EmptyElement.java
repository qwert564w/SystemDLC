package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.render.TextShader;
import org.joml.Matrix4f;

public class EmptyElement implements UiMetrics {
   public static float getFloatByFloatMatrix4fFloatFloatStringFloatString(
      float value, Matrix4f matrix4f, float value2, float value3, String text, float value4, String text2
   ) {
      float f2 = 14.0F;
      float f = TextShader.getFloatByFloatFloatString(value, f2, text2);
      int i = Theme.foreground();
      float f3 = 14.0F;
      TextShader.onFloatFloatIntFloatMatrix4fStringFloatFloat(value2, value3, i, value, matrix4f, text2, value4, f3);
      if (text != null && !text.isEmpty()) {
         float f4 = 12.0F;
         float f1 = TextShader.getFloatByFloatFloatString(value, f4, text);
         float f7 = value3 + f + 0.0F;
         int j = Theme.mutedFg();
         float f6 = 12.0F;
         float f5 = f7;
         TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value4, text, value2, f6, matrix4f, value, f5, j);
         return f + 0.0F + f1;
      } else {
         return f;
      }
   }

   public static float getFloatByFloatStringString(float value, String text, String text2) {
      float f2 = 14.0F;
      float f = TextShader.getFloatByFloatFloatString(value, f2, text2);
      if (text != null && !text.isEmpty()) {
         float f3 = 12.0F;
         float f1 = TextShader.getFloatByFloatFloatString(value, f3, text);
         return f + 0.0F + f1;
      } else {
         return f;
      }
   }
}
