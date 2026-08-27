package client.network;

import client.enums.PacketDirection;
import client.module.Feature;
import net.minecraft.network.packet.Packet;

public class PacketEvent {
   private final Packet<?> packet;
   private final PacketDirection packetDirection;
   private boolean flag = false;

   public PacketEvent(Packet packet2, PacketDirection packetDirection2) {
      this.packet = packet2;
      this.packetDirection = packetDirection2;
   }

   public static void onPacket(Packet packet2) {
      if (Feature.mc.getNetworkHandler() != null) {
         try {
            ConnectionHooks.setFlag2(true);
            packet2.apply(Feature.mc.getNetworkHandler());
         } finally {
            ConnectionHooks.setFlag2(false);
         }
      }
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public PacketDirection getPacketDirection() {
      return this.packetDirection;
   }

   public Packet<?> getPacket() {
      return this.packet;
   }

   public static void onPacket2(Packet packet2) {
      if (Feature.mc.getNetworkHandler() != null) {
         try {
            ConnectionHooks.setFlag(true);
            Feature.mc.getNetworkHandler().sendPacket(packet2);
         } finally {
            ConnectionHooks.setFlag(false);
         }
      }
   }
}
