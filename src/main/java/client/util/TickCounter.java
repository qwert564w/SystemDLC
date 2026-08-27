package client.util;

public final class TickCounter {
   private static int value = 0;

   private TickCounter() {
   }

   public static void update() {
      if (value > 0) {
         value--;
      }
   }

   public static boolean isValueAsBoolean() {
      return value > 0;
   }

   public static void setInt(int count) {
      value = Math.max(value, count);
   }
}
