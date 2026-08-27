package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.CustomTime;
import client.util.UnsafeAccess;
import net.minecraft.client.world.ClientWorld;

@HookClass(ClientWorld.class)
public class WorldTickHook {
   private static final UnsafeAccess<CustomTime> unsafeAccess = new UnsafeAccess<>(CustomTime.class);

   @Hook(
      method = "method_29089",
      desc = "(JJZ)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onClientWorldLongLongBoolean(ClientWorld clientWorld, long time, long time2, boolean flag) {
      CustomTime customtime = (CustomTime)unsafeAccess.getModule2();
      if (customtime != null) {
         HandleInvoker.onObjectArray(clientWorld, time, customtime.getLong(), false);
      } else {
         HandleInvoker.onObjectArray(clientWorld, time, time2, flag);
      }
   }
}
