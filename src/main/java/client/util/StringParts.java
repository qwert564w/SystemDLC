package client.util;

public final class StringParts {
   private StringParts() {
   }

   public static String[] split(String text) {
      if (text != null && !text.isEmpty()) {
         String[] astring = new String[text.length()];

         for (int i = 0; i < text.length(); i++) {
            astring[i] = String.valueOf(text.charAt(i));
         }

         return astring;
      } else {
         return new String[0];
      }
   }

   public static String join(String[] textArray) {
      if (textArray != null && textArray.length != 0) {
         StringBuilder stringbuilder = new StringBuilder(textArray.length);

         for (String s : textArray) {
            if (s != null) {
               stringbuilder.append(s);
            }
         }

         return stringbuilder.toString();
      } else {
         return "";
      }
   }

   public static boolean isBlank(String[] textArray) {
      if (textArray != null && textArray.length != 0) {
         for (String s : textArray) {
            if (s != null && !s.isBlank()) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }
}
