package client.util;

import net.minecraft.item.ItemStack;

public record HotbarEntry(int index, ItemStack stack, int count, String bind) {
   public int getCount() {
      return this.count;
   }

   public String getBind() {
      return this.bind;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public int getIndex() {
      return this.index;
   }
}
