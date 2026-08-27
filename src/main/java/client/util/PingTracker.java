package client.util;

import client.module.Feature;
import client.network.PacketEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;

public class PingTracker {
   private static final PingTracker INSTANCE = new PingTracker();
   private final int[] intArray = new int[5];
   private int value;
   private int value2;
   private int value3;

   private void update() {
      this.value = 0;
      this.value2 = 0;
      this.value3 = 0;
   }

   public void onPacketEvent(PacketEvent packetEvent) {
   }

   private void update2() {
      if (Feature.mc != null && Feature.mc.player != null) {
         ClientPlayNetworkHandler clientplaynetworkhandler = Feature.mc.getNetworkHandler();
         if (clientplaynetworkhandler == null) {
            this.update();
         } else {
            PlayerListEntry playerlistentry = clientplaynetworkhandler.getPlayerListEntry(Feature.mc.player.getUuid());
            if (playerlistentry == null) {
               this.update();
            } else {
               int i = playerlistentry.getLatency();
               if (i > 0) {
                  this.intArray[this.value % 5] = i;
                  this.value++;
                  this.value2 = Math.min(this.value2 + 1, 5);
                  int j = 0;

                  for (int k = 0; k < this.value2; k++) {
                     j += this.intArray[k];
                  }

                  this.value3 = j / this.value2;
               }
            }
         }
      } else {
         this.update();
      }
   }

   public static PingTracker getInstance() {
      return INSTANCE;
   }

   public int getInt() {
      this.update2();
      return this.value3;
   }
}
