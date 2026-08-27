package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.data.TextTrimmer;
import client.render.ShapeShader;
import client.render.TextShader;
import org.joml.Matrix4f;

public final class Divider implements UiMetrics {
   private static final float value235 = 12.0F;
   private static final float value236 = 12.0F;
   private static final float value237 = 8.0F;

   private Divider() {
   }

   public static void onMatrix4fFloatTextInputControllerStringFloatFloatInt(
      Matrix4f matrix4f, float value, TextInputController textInputController, String text, float value2, float value3, int count
   ) {
      String s = getStringByString(text);
      String s1 = textInputController != null && textInputController.isFlag() ? textInputController.getString() : Integer.toString(count);
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f1 = value3 + 8.0F;
      float f2 = value + 7.0F;
      int i = Theme.mutedFg();
      float f10 = 12.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f2, f1, i, f10, value2, s, matrix4f);
      float f3 = f1 + f + 4.0F;
      float f4 = value3 + 87.0F - 8.0F;
      float f5 = Math.max(0.0F, f4 - f3);
      float f11 = 12.0F;
      String s2 = TextTrimmer.getStringByFloatStringFloat(f5, s1, f11);
      float f6 = TextShader.getFloatByStringFloat(s2, 12.0F);
      float f7 = f4 - f6;
      float f8 = value + 7.0F;
      int j = Theme.foreground();
      float f12 = 12.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f8, f7, j, f12, value2, s2, matrix4f);
      if (textInputController != null && textInputController.isFlag() && textInputController.check()) {
         float f9 = f7 + f6 + 1.0F;
         int k = Theme.foreground();
         float f14 = 12.0F;
         float f13 = 1.0F;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f14, f9, f8, f13, matrix4f, k);
      }
   }

   private static String getStringByString(String text) {
      if (text != null && !text.isEmpty()) {
         char c0 = text.charAt(0);
         return Character.toString(Character.toUpperCase(c0));
      } else {
         return "";
      }
   }
}
