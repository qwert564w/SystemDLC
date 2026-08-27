package client.util;

public final class FramebufferRedirect {
   private static boolean flag;

   private FramebufferRedirect() {
   }

   public static void setFlag() {
      flag = false;
   }

   public static boolean isFlag() {
      return flag;
   }

   public static void setFlag2() {
      flag = true;
   }
}
