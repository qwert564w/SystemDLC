package client.data;

import client.render.TextShader;

public final class TextTrimmer {
   private static final String text = "...";

   private TextTrimmer() {
   }

   public static String getStringByFloatStringFloat(float value, String text, float value2) {
      if (text == null) {
         return "";
      } else if (TextShader.getFloatByStringFloat(text, value2) <= value) {
         return text;
      } else {
         int i = 0;

         while (i < text.length() && TextShader.getFloatByStringFloat(text.substring(i), value2) > value) {
            i++;
         }

         return text.substring(i);
      }
   }

   public static String getStringByFloatStringFloat2(float value, String text, float value2) {
      if (text == null) {
         return "";
      } else if (TextShader.getFloatByStringFloat(text, value2) <= value) {
         return text;
      } else {
         float f = TextShader.getFloatByStringFloat("...", value2);
         int i = text.length();

         while (i > 0 && TextShader.getFloatByStringFloat(text.substring(0, i), value2) + f > value) {
            i--;
         }

         return text.substring(0, i) + "...";
      }
   }

   public static String getStringByFloatFloatIntString(float value, float value2, int count, String text) {
      float f = count * TextShader.getFloatByFloat3(value) + 0.5F;
      if (TextShader.getFloatByFloatFloatString(value2, value, text) <= f) {
         return text;
      } else {
         for (int i = text.length(); i > 0; i--) {
            String s = text.substring(0, i).stripTrailing() + "...";
            if (TextShader.getFloatByFloatFloatString(value2, value, s) <= f) {
               return s;
            }
         }

         return "...";
      }
   }
}
