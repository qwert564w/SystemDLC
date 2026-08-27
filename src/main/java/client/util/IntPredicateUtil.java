package client.util;

import java.util.function.IntPredicate;

public final class IntPredicateUtil {
   public static final IntPredicate intPredicate = var0 -> var0 == 45 || var0 == 32 || var0 == 44 || var0 == 59 || var0 == 46 || var0 >= 48 && var0 <= 57;

   private IntPredicateUtil() {
   }

   public static int[] getIntArrayByString(String text) {
      if (text == null) {
         return null;
      } else {
         String[] astring = text.trim().split("[\\s,;]+");
         if (astring.length < 3) {
            return null;
         } else {
            int[] aint = new int[3];

            for (int i = 0; i < 3; i++) {
               try {
                  aint[i] = Integer.parseInt(astring[i].trim());
               } catch (NumberFormatException numberformatexception) {
                  return null;
               }
            }

            return aint;
         }
      }
   }

   public static Integer getIntegerByString(String text) {
      if (text == null) {
         return null;
      } else {
         try {
            return Integer.parseInt(text.trim());
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      }
   }
}
