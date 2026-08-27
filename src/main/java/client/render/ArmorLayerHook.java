package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.util.IconRenderFlag;
import client.util.ReflectionCache;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderState.LayerRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;

@HookClass(LayerRenderState.class)
public class ArmorLayerHook {
   private static final long time = ReflectionCache.getLongByClassClass2(LayerRenderState.class, RenderLayer.class);

   @Hook(
      method = "method_65614",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;II)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onLayerRenderStateMatrixStackVertexConsumerProviderIntInt(
      LayerRenderState layerRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int count, int count2
   ) {
      if (IconRenderFlag.isFlag() && time != 0L) {
         RenderLayer renderlayer = (RenderLayer)ReflectionCache.getObjectByObjectLong(layerRenderState, time);
         if (renderlayer == null) {
            HandleInvoker.onObjectArray(layerRenderState, matrixStack, vertexConsumerProvider, count, count2);
         } else {
            ReflectionCache.onObjectLongObject(layerRenderState, time, RenderLayer.getEntityTranslucent(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));

            try {
               HandleInvoker.onObjectArray(layerRenderState, matrixStack, vertexConsumerProvider, count, count2);
            } finally {
               ReflectionCache.onObjectLongObject(layerRenderState, time, renderlayer);
            }
         }
      } else {
         HandleInvoker.onObjectArray(layerRenderState, matrixStack, vertexConsumerProvider, count, count2);
      }
   }
}
