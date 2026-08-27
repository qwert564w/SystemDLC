package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@HookClass(EntityRenderer.class)
public class NameTagVisibilityHook {
   @Hook(
      method = "method_3926",
      desc = "(Lnet/minecraft/class_10017;Lnet/minecraft/class_2561;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isEntityRendererEntityRenderStateTextMatrixStackVertexConsumerProviderInt(
      EntityRenderer entityRenderer, EntityRenderState entityRenderState, Text text2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int count
   ) {
      return !FramebufferSwap.isFlag();
   }
}
