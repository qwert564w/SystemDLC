package client.util;

import client.module.Feature;
import net.minecraft.entity.LivingEntity;

public class ShieldState {
   private int value = -1;
   private boolean flag = false;
   private long time = 0L;
   private LivingEntity livingEntity = null;

   public void update() {
      this.value = -1;
      this.flag = false;
      this.time = 0L;
      this.livingEntity = null;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void update2() {
      if (Feature.mc.player != null) {
         if (this.value != -1 && this.value < 9) {
            Feature.mc.player.getInventory().selectedSlot = this.value;
         }

         this.update();
      }
   }

   public void onDouble(double value2) {
      if (this.flag && this.value != -1 && Feature.mc.player != null) {
         int i = CritChecks.getInt();
         int j = Feature.mc.player.getInventory().selectedSlot;
         if (j != i && j != this.value) {
            this.update();
         } else {
            long k = System.currentTimeMillis() - this.time;
            boolean flagx = k >= value2;
            if ((this.livingEntity == null || !this.livingEntity.isAlive() || !CritChecks.isLivingEntity(this.livingEntity)) && k >= 100L) {
               flagx = true;
            }

            if (flagx) {
               this.update2();
            }
         }
      }
   }

   public void setLivingEntity(LivingEntity livingEntity2) {
      if (Feature.mc.player != null) {
         if (this.value == -1) {
            this.value = Feature.mc.player.getInventory().selectedSlot;
         }

         int i = CritChecks.getInt();
         if (i != -1) {
            Feature.mc.player.getInventory().selectedSlot = i;
            this.flag = true;
            this.time = System.currentTimeMillis();
            this.livingEntity = livingEntity2;
         }
      }
   }
}
