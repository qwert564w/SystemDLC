package client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.ByteBuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class PixelReader {
   private static final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4);

   public static int getIntByIntIntInt(int count, int count2, int count3) {
      try {
         int i = GL11.glGetInteger(36006);
         GlStateManager._glBindFramebuffer(36008, count);
         byteBuffer.clear();
         GL11.glReadPixels(count2, count3, 1, 1, 6408, 5121, byteBuffer);
         GlStateManager._glBindFramebuffer(36160, i);
         return byteBuffer.get(3) & 0xFF;
      } catch (Throwable throwable) {
         return -1;
      }
   }

   public static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      GlStateManager._activeTexture(33984);
      GlStateManager._bindTexture(simpleFramebuffer.getColorAttachment());
      GlStateManager._texParameter(3553, 10241, 9729);
      GlStateManager._texParameter(3553, 10240, 9729);
      GlStateManager._texParameter(3553, 10242, 10496);
      GlStateManager._texParameter(3553, 10243, 10496);
      GlStateManager._bindTexture(0);
   }

   public static void onIntIntIntIntIntIntIntIntIntInt(int count, int count2, int count3, int count4, int count5, int count6, int count7, int count8, int count9, int count10) {
      GlStateManager._glBindFramebuffer(36008, count7);
      GlStateManager._glBindFramebuffer(36009, count);
      GL30.glBlitFramebuffer(count8, count4, count9, count6, count2, count10, count5, count3, 16384, 9728);
   }
}
