package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.data.TextTrimmer;
import client.enums.FontWeight;
import client.render.TextShader;
import org.joml.Matrix4f;

public class EmptyRow implements UiMetrics {
   public static float getFloatByIntFloatFloatFloatFloatMatrix4fFontWeightFloatStringTextInputController(
      int count, float value, float value2, float value3, float value4, Matrix4f matrix4f, FontWeight fontWeight, float value5, String text, TextInputController textInputController
   ) {
      if (textInputController.isFlag()) {
         TextInputState textinputstate = textInputController.getTextInputState();
         int k = Theme.foreground();
         boolean flag = textInputController.check();
         int j = k;
         textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(value3, j, matrix4f, value5, flag, value2, value4);
         String s2 = textInputController.getTextInputState().getString2();
         int i = textInputController.getTextInputState().getIntByFloat(value4);
         return Math.min(TextShader.getFloatByStringFloat(s2.substring(i), value), value4);
      } else {
         String s1 = text == null ? "" : text;
         String s = TextTrimmer.getStringByFloatStringFloat2(value4, s1, value);
         onStringFloatFloatIntFloatMatrix4fFontWeightFloat(s, value, value5, count, value3, matrix4f, fontWeight, value2);
         return Math.min(TextShader.getFloatByStringFloat(s, value), value4);
      }
   }

   private static void onStringFloatFloatIntFloatMatrix4fFontWeightFloat(
      String text, float value, float value2, int count, float value3, Matrix4f matrix4f, FontWeight fontWeight, float value4
   ) {
      if (fontWeight == FontWeight.MEDIUM) {
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value3, value4, count, value, value2, text, matrix4f);
      } else {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, text, value4, value3, value, count, value2);
      }
   }
}
