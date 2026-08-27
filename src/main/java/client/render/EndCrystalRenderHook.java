package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.CrystalChecks;
import client.util.UnsafeAccess;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

@HookClass(EndCrystalEntityRenderer.class)
public class EndCrystalRenderHook {
   public static Entity entity = null;
   private static final UnsafeAccess<CrystalChecks> unsafeAccess = new UnsafeAccess<>(CrystalChecks.class);

   @Hook(
      method = "method_3908",
      desc = "(Lnet/minecraft/class_10014;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onEndCrystalEntityRendererEndCrystalEntityRenderStateMatrixStackVertexConsumerProviderInt(
      EndCrystalEntityRenderer endCrystalEntityRenderer, EndCrystalEntityRenderState endCrystalEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int count
   ) {
      CrystalChecks crystalchecks = (CrystalChecks)unsafeAccess.getModule2();
      boolean flag = crystalchecks != null && entity != null && crystalchecks.isEntity(entity);
      if (flag && crystalchecks.getMenyatSize().isFlag3()) {
         float f = (float)crystalchecks.getSize().getValue() / 2.0F;
         float f1 = (float)crystalchecks.getSdvigX().getValue();
         float f2 = (float)crystalchecks.getSdvigY().getValue();
         matrixStack.push();
         matrixStack.translate(f1, f2, 0.0F);
         matrixStack.scale(f, f, f);
         HandleInvoker.onObjectArray(endCrystalEntityRenderer, endCrystalEntityRenderState, matrixStack, vertexConsumerProvider, count);
         matrixStack.pop();
      } else {
         HandleInvoker.onObjectArray(endCrystalEntityRenderer, endCrystalEntityRenderState, matrixStack, vertexConsumerProvider, count);
      }
   }
}
