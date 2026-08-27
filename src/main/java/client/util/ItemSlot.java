package client.util;

import client.enums.TrackedItem;
import client.module.player.AutoSwap;
import net.minecraft.item.ItemStack;

public final class ItemSlot {
   public ItemStack itemStack = ItemStack.EMPTY;
   public int value;

   public ItemSlot() {
   }

   public void onItemStackTrackedItem(ItemStack itemStack2, TrackedItem trackedItem) {
      if (AutoSwap.isTrackedItemItemStack4(trackedItem, itemStack2)) {
         if (this.itemStack.isEmpty()) {
            this.itemStack = itemStack2;
         }

         this.value = this.value + itemStack2.getCount();
      }
   }
}
