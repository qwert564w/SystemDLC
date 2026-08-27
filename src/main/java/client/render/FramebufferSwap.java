package client.render;

import net.minecraft.client.gl.Framebuffer;

public final class FramebufferSwap {
   private static boolean flag;
   private static Framebuffer framebuffer;

   private FramebufferSwap() {
   }

   public static void update() {
      flag = false;
      framebuffer = null;
   }

   public static void setFramebuffer(Framebuffer framebuffer2) {
      flag = true;
      framebuffer = framebuffer2;
   }

   public static Framebuffer getFramebuffer() {
      return framebuffer;
   }

   public static boolean isFlag() {
      return flag;
   }
}
