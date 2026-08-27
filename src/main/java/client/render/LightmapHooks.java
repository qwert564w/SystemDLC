package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.FullBright;
import client.module.visual.NoRender;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import net.minecraft.client.render.LightmapTextureManager;

@HookClass(LightmapTextureManager.class)
public class LightmapHooks {
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<FullBright> unsafeAccess2 = new UnsafeAccess<>(FullBright.class);
   private static final long time = ReflectionCache.getLongByClassClass2(LightmapTextureManager.class, boolean.class);

   @Hook(
      method = "method_42597",
      desc = "(F)F",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static float getFloatByLightmapTextureManagerFloat(LightmapTextureManager lightmapTextureManager, float value) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender != null && norender.check3() ? 0.0F : HandleInvoker.getFloatByObjectArray(lightmapTextureManager, value);
   }

   @Hook(
      method = "method_3313",
      desc = "(F)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onLightmapTextureManagerFloat(LightmapTextureManager lightmapTextureManager, float value) {
      FullBright fullbright = (FullBright)unsafeAccess2.getModule2();
      if (fullbright != null && fullbright.check3()) {
         try {
            UnsafeAccess.unsafe.putBoolean(lightmapTextureManager, time, true);
         } catch (Throwable throwable) {
         }
      }
   }
}
