package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.util.IconRenderFlag;
import java.util.function.Function;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.SpriteIdentifier;

@HookClass(SpriteIdentifier.class)
public class SpriteRenderLayers {
   @Hook(
      method = "method_24146",
      desc = "(Ljava/util/function/Function;)Lnet/minecraft/class_1921;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static RenderLayer getRenderLayerBySpriteIdentifierFunction(SpriteIdentifier spriteIdentifier, Function function2) {
      return IconRenderFlag.isFlag() ? RenderLayer.getEntityTranslucent(spriteIdentifier.getAtlasId()) : (RenderLayer)HandleInvoker.getObjectByObjectArray2(spriteIdentifier, function2);
   }
}
