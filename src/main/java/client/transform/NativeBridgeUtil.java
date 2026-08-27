package client.transform;

import b.Boot;

public final class NativeBridgeUtil {
   private static String text;

   private NativeBridgeUtil() {
   }

   public static String getStringByStringString(String text2, String text3) {
      if (text2 != null && !text2.isEmpty()) {
         try {
            int i = Integer.parseInt(text2);
            return Boot.call(i, text3, text);
         } catch (UnsatisfiedLinkError | NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String getText() {
      return text;
   }

   public static void setText(String text2) {
      text = text2;
   }
}
