package client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

public final class RotationBuffer {
   private static final Quaternionf quaternionf = new Quaternionf();
   private static final Quaternionf quaternionf2 = new Quaternionf();
   private static boolean flag = false;

   private RotationBuffer() {
   }

   public static void setMinecraftClient(MinecraftClient minecraftClient) {
      flag = false;

      try {
         minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
         minecraftClient.getBufferBuilders().getEffectVertexConsumers().draw();
      } catch (Exception exception) {
      }

      RenderSystem.depthMask(true);
      GL11.glDepthFunc(515);
      RenderSystem.disableBlend();
      RenderSystem.enableCull();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static boolean isFlag() {
      return flag;
   }

   public static void setMinecraftClient2(MinecraftClient minecraftClient) {
      flag = true;
      minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
      minecraftClient.getBufferBuilders().getEffectVertexConsumers().draw();
      minecraftClient.getFramebuffer().beginWrite(false);
      GL11.glDepthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
   }

   public static void onMatrix4fMatrix4f(Matrix4f matrix4f2, Matrix4f matrix4f3) {
      Matrix4f matrix4f = Math.abs(matrix4f2.m33() - 1.0F) < 0.01F ? matrix4f2 : matrix4f3;
      quaternionf2.setFromNormalized(matrix4f).conjugate();
   }

   public static void render(MatrixStack matrixStack) {
      matrixStack.multiply(quaternionf2);
      matrixStack.multiply(quaternionf.rotationY((float) Math.PI));
   }
}
