package client.util;

public final class TextFormatUtil {
   private TextFormatUtil() {
   }

   public static String getStringByDouble(double value) {
      long i = Math.round(value * 10.0);
      return i / 10L + "." + i % 10L;
   }

   public static String getStringByString(String text) {
      if (text != null && !text.isEmpty()) {
         StringBuilder stringbuilder = new StringBuilder(text.length());
         boolean flag = true;

         for (String s : text.split("_")) {
            if (!s.isEmpty()) {
               if (!flag) {
                  stringbuilder.append(' ');
               }

               stringbuilder.append(Character.toUpperCase(s.charAt(0)));
               if (s.length() > 1) {
                  stringbuilder.append(s, 1, s.length());
               }

               flag = false;
            }
         }

         return stringbuilder.toString();
      } else {
         return "";
      }
   }
}
