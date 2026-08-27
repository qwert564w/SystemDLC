package client.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemCount {
   public final ItemStack itemStack;
   public final Item item;
   public final int value;
   public int value2;

   public ItemCount(ItemStack itemStack2, int count, int count2) {
      this.itemStack = itemStack2;
      this.item = itemStack2.getItem();
      this.value = count;
      this.value2 = count2;
   }
}
