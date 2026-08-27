package client.network;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.util.ModuleDispatcher;
import client.util.UnsafeAccess;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;

@HookClass(ClientPlayNetworkHandler.class)
public class NetworkHandlerHooks {
   public static boolean flag = false;

   @Hook(
      method = "method_11120",
      desc = "(Lnet/minecraft/class_2678;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onClientPlayNetworkHandlerGameJoinS2CPacket(ClientPlayNetworkHandler clientPlayNetworkHandler, GameJoinS2CPacket gameJoinS2CPacket) {
      if (!flag) {
         flag = true;
         String s = null;

         try {
            if (clientPlayNetworkHandler.getConnection() != null && clientPlayNetworkHandler.getConnection().getAddress() != null) {
               s = clientPlayNetworkHandler.getConnection().getAddress().toString();
            }
         } catch (Throwable throwable1) {
         }

         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            try {
               moduledispatcher.onString2(s);
            } catch (Throwable throwable) {
            }
         }
      }
   }

   @Hook(
      method = "method_45729",
      desc = "(Ljava/lang/String;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onClientPlayNetworkHandlerString(ClientPlayNetworkHandler clientPlayNetworkHandler, String text) {
      if (text != null && !text.startsWith(".")) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            try {
               moduledispatcher.onString(text);
            } catch (Throwable throwable) {
            }
         }
      }
   }
}
