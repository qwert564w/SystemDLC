package client.network;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.SystemClient;
import client.enums.InjectPoint;
import client.enums.PacketDirection;
import client.module.Module;
import client.module.client.PanicModule;
import client.util.InventoryActions;
import client.util.PingTracker;
import client.util.PvpStateParser;
import client.util.TpsTracker;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;

@HookClass(ClientConnection.class)
public class ConnectionHooks {
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static ClientConnection clientConnection = null;

   public static boolean isFlag2() {
      return flag2;
   }

   public static void setFlag2(boolean flag) {
      flag2 = flag;
   }

   public static void setFlag(boolean flag2) {
      flag = flag2;
   }

   public static boolean isFlag() {
      return flag;
   }

   @Hook(
      method = "method_10770",
      desc = "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/class_2596;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isClientConnectionChannelHandlerContextPacket(ClientConnection clientConnection2, ChannelHandlerContext channelHandlerContext, Packet packet2) {
      clientConnection = clientConnection2;
      if (!flag2 && !PanicModule.isFlag()) {
         PacketEvent packetevent = new PacketEvent(packet2, PacketDirection.RECEIVE);
         onPacketEvent(packetevent);
         return !packetevent.isFlag();
      } else {
         return true;
      }
   }

   public static ClientConnection getClientConnection() {
      return clientConnection;
   }

   private static void onPacketEvent(PacketEvent packetEvent) {
      if (!PanicModule.isFlag()) {
         try {
            InventoryActions.onPacketEvent(packetEvent);
            PvpStateParser.onPacketEvent(packetEvent);
            TpsTracker.getInstance().addPacketEvent(packetEvent);
            PingTracker.getInstance().onPacketEvent(packetEvent);
            if (SystemClient.getInstance() != null && SystemClient.getInstance().getModuleRegistry() != null) {
               for (Module module : SystemClient.getInstance().getModuleRegistry().getList22()) {
                  try {
                     module.onPacketEvent(packetEvent);
                     if (packetEvent.isFlag()) {
                        break;
                     }
                  } catch (Exception exception) {
                  }
               }
            }
         } catch (Exception exception1) {
         }
      }
   }

   @Hook(
      method = "method_10743",
      desc = "(Lnet/minecraft/class_2596;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isClientConnectionPacket(ClientConnection clientConnection2, Packet packet2) {
      clientConnection = clientConnection2;
      if (!flag && !PanicModule.isFlag()) {
         PacketEvent packetevent = new PacketEvent(packet2, PacketDirection.SEND);
         onPacketEvent(packetevent);
         return !packetevent.isFlag();
      } else {
         return true;
      }
   }
}
