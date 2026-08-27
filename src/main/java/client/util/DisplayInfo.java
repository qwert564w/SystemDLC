package client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class DisplayInfo {
   private static final int value = 60;

   private DisplayInfo() {
   }

   private static boolean isFlag() {
      try {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient == null) {
            return false;
         } else {
            Window window = minecraftclient.getWindow();
            return window != null && window.getHandle() != 0L;
         }
      } catch (Throwable throwable) {
         return false;
      }
   }

   public static int getInt() {
      if (!isFlag()) {
         return 60;
      } else {
         try {
            long i = GLFW.glfwGetPrimaryMonitor();
            if (i == 0L) {
               return 60;
            } else {
               GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(i);
               return glfwvidmode != null && glfwvidmode.refreshRate() > 0 ? glfwvidmode.refreshRate() : 60;
            }
         } catch (Throwable throwable) {
            return 60;
         }
      }
   }
}
