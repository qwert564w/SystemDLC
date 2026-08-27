package client.util;

public final class ScrollOffset {
   private static float value;
   private static float value2;
   private static boolean flag;

   private ScrollOffset() {
   }

   public static float getValue2() {
      return value2;
   }

   public static boolean isFlag() {
      return flag;
   }

   public static float getValue() {
      return value;
   }

   public static void onFloatFloat(float value3, float value4) {
      value = value3;
      value2 = value4;
      flag = true;
   }

   public static void setFlag() {
      flag = false;
   }
}
