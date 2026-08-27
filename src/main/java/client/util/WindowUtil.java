package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.client.StreamBypass;
import client.render.OverlayFramebuffers;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

@HookClass(Window.class)
public class WindowUtil {
   private static boolean flag;

   public static void setWindow(Window window) {
      long i = window.getHandle();
      if (i != 0L) {
         if (!window.isFullscreen()) {
            if (flag) {
               flag = false;
               GLFW.glfwSetWindowAttrib(i, 131077, 1);
            }
         } else if (StreamBypass.check5()) {
            if (GLFW.glfwGetWindowMonitor(i) != 0L) {
               long j = getLongByLong(i);
               if (j != 0L) {
                  GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(j);
                  if (glfwvidmode != null) {
                     int[] aint = new int[1];
                     int[] aint1 = new int[1];
                     GLFW.glfwGetMonitorPos(j, aint, aint1);
                     flag = true;
                     GLFW.glfwSetWindowAttrib(i, 131077, 0);
                     GLFW.glfwSetWindowMonitor(i, 0L, aint[0], aint1[0], glfwvidmode.width(), glfwvidmode.height(), -1);
                  }
               }
            }
         }
      }
   }

   private static long getLongByLong(long time) {
      long i = GLFW.glfwGetWindowMonitor(time);
      if (i != 0L) {
         return i;
      } else {
         int[] aint = new int[1];
         int[] aint1 = new int[1];
         GLFW.glfwGetWindowPos(time, aint, aint1);
         PointerBuffer pointerbuffer = GLFW.glfwGetMonitors();
         if (pointerbuffer == null) {
            return GLFW.glfwGetPrimaryMonitor();
         } else {
            for (int j = 0; j < pointerbuffer.limit(); j++) {
               long k = pointerbuffer.get(j);
               GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(k);
               if (glfwvidmode != null) {
                  int[] aint2 = new int[1];
                  int[] aint3 = new int[1];
                  GLFW.glfwGetMonitorPos(k, aint2, aint3);
                  if (aint[0] >= aint2[0] && aint[0] < aint2[0] + glfwvidmode.width() && aint1[0] >= aint3[0] && aint1[0] < aint3[0] + glfwvidmode.height()) {
                     return k;
                  }
               }
            }

            return GLFW.glfwGetPrimaryMonitor();
         }
      }
   }

   @Hook(
      method = "method_4479",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onWindow(Window window) {
      setWindow(window);
   }

   @Hook(
      method = "method_15998",
      desc = "(Lnet/minecraft/class_10219;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onWindowTracyFrameCapturer(Window window, TracyFrameCapturer tracyFrameCapturer) {
      if (StreamBypass.check5()) {
         try {
            OverlayFramebuffers.update4();
         } catch (Throwable throwable) {
         }
      }

      HandleInvoker.onObjectArray(window, tracyFrameCapturer);
   }
}
