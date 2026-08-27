package client.gui.hud;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.module.visual.Enhancer;
import client.util.UnsafeAccess;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;

@HookClass(DebugHud.class)
public class DebugHudHelper {
   private static final UnsafeAccess<Enhancer> unsafeAccess = new UnsafeAccess<>(Enhancer.class);

   @Hook(
      method = "method_1846",
      desc = "(Lnet/minecraft/class_332;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isDebugHudDrawContext(DebugHud debugHud, DrawContext drawContext) {
      Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
      return enhancer == null || !enhancer.check9();
   }
}
