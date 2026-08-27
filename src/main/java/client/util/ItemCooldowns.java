package client.util;

import client.module.Feature;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;

public final class ItemCooldowns {
   private ItemCooldowns() {
   }

   public static double getDoubleByItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && Feature.mc.player != null) {
         ItemCooldownManager itemcooldownmanager = Feature.mc.player.getItemCooldownManager();
         if (itemcooldownmanager != null && itemcooldownmanager.isCoolingDown(itemStack)) {
            float f = Feature.mc.getRenderTickCounter() != null ? Feature.mc.getRenderTickCounter().getTickDelta(false) : 0.0F;
            float f1 = itemcooldownmanager.getCooldownProgress(itemStack, f);
            if (f1 <= 0.0F) {
               return 0.0;
            } else {
               float f2 = itemcooldownmanager.getCooldownProgress(itemStack, 0.0F) - itemcooldownmanager.getCooldownProgress(itemStack, 1.0F);
               return f2 > 0.0F ? f1 / f2 / 20.0 : f1 * 20.0;
            }
         } else {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }
}
