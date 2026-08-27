package client.render;

import client.module.Feature;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.lwjgl.opengl.GL30;

public final class MipmapCapture {
   private static final int value = 4;
   private static SimpleFramebuffer simpleFramebuffer;
   private static int value2 = 0;
   private static int value3 = 0;
   private static boolean flag = true;
   private static boolean flag2 = true;
   private static long time = 0L;

   private MipmapCapture() {
   }

   public static void update() {
      if (flag2 && simpleFramebuffer != null) {
         try {
            GlStateManager._activeTexture(33984);
            GlStateManager._bindTexture(simpleFramebuffer.getColorAttachment());
            GlStateManager._texParameter(3553, 33085, 4);
            GL30.glGenerateMipmap(3553);
            GlStateManager._texParameter(3553, 10241, 9987);
            GlStateManager._bindTexture(0);
            flag2 = false;
         } catch (Throwable throwable) {
         }
      }
   }

   public static int getInt() {
      try {
         Framebuffer framebuffer = Feature.mc.getFramebuffer();
         if (framebuffer == null) {
            return 0;
         } else {
            int i = framebuffer.textureWidth;
            int j = framebuffer.textureHeight;
            if (i > 0 && j > 0) {
               boolean flagx = false;
               if (simpleFramebuffer == null) {
                  simpleFramebuffer = new SimpleFramebuffer(i, j, false);
                  onSimpleFramebuffer(simpleFramebuffer);
                  value2 = i;
                  value3 = j;
                  flagx = true;
               } else if (value2 != i || value3 != j) {
                  simpleFramebuffer.resize(i, j);
                  onSimpleFramebuffer(simpleFramebuffer);
                  value2 = i;
                  value3 = j;
                  flagx = true;
               }

               if (flag || flagx) {
                  simpleFramebuffer.beginWrite(true);
                  framebuffer.draw(i, j);
                  framebuffer.beginWrite(true);
                  flag = false;
                  flag2 = true;
               }

               return simpleFramebuffer.getColorAttachment();
            } else {
               return 0;
            }
         }
      } catch (Throwable throwable) {
         return 0;
      }
   }

   private static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      PixelReader.onSimpleFramebuffer(simpleFramebuffer);
   }

   public static long getTime() {
      return time;
   }

   public static void update2() {
      flag = true;
      time++;
   }
}
