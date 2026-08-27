package client.render;

import client.module.Feature;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.lwjgl.opengl.GL11;

public final class SnapshotFramebuffer {
   private SimpleFramebuffer simpleFramebuffer;

   public SimpleFramebuffer getSimpleFramebuffer() {
      Framebuffer framebuffer = Feature.mc.getFramebuffer();
      if (framebuffer == null) {
         return null;
      } else {
         int i = framebuffer.textureWidth;
         int j = framebuffer.textureHeight;
         if (i > 0 && j > 0) {
            int k = GL11.glGetInteger(36006);
            if (k == 0) {
               k = framebuffer.fbo;
            }

            if (this.simpleFramebuffer == null) {
               this.simpleFramebuffer = new SimpleFramebuffer(i, j, false);
               PixelReader.onSimpleFramebuffer(this.simpleFramebuffer);
            } else if (this.simpleFramebuffer.textureWidth != i || this.simpleFramebuffer.textureHeight != j) {
               this.simpleFramebuffer.resize(i, j);
               PixelReader.onSimpleFramebuffer(this.simpleFramebuffer);
            }

            byte b3 = 0;
            byte b2 = 0;
            int l = this.simpleFramebuffer.fbo;
            byte b1 = 0;
            byte b0 = 0;
            PixelReader.onIntIntIntIntIntIntIntIntIntInt(l, b2, j, b1, i, j, k, b0, i, b3);
            GlStateManager._glBindFramebuffer(36160, k);
            return this.simpleFramebuffer;
         } else {
            return null;
         }
      }
   }

   public void update() {
      if (this.simpleFramebuffer != null) {
         try {
            this.simpleFramebuffer.delete();
         } catch (Throwable throwable) {
         }

         this.simpleFramebuffer = null;
      }
   }
}
