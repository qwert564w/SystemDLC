package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.util.FramebufferRedirect;
import net.minecraft.client.gl.Framebuffer;

@HookClass(Framebuffer.class)
public class FramebufferBindHook {
   @Hook(
      method = "method_1235",
      desc = "(Z)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isFramebufferBoolean(Framebuffer framebuffer2, boolean flag) {
      if (FramebufferSwap.isFlag()) {
         Framebuffer framebuffer1 = FramebufferSwap.getFramebuffer();
         if (framebuffer1 == null) {
            framebuffer1 = PlayerOutlineMaskEffect.getSimpleFramebufferAsFramebuffer();
         }

         if (framebuffer1 != null && framebuffer2 != framebuffer1) {
            framebuffer1.beginWrite(flag);
            return false;
         } else {
            return true;
         }
      } else if (FramebufferRedirect.isFlag()) {
         Framebuffer framebuffer = PlayerOutlineEffect.getSimpleFramebufferAsFramebuffer();
         if (framebuffer != null && framebuffer2 != framebuffer) {
            framebuffer.beginWrite(flag);
            return false;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
