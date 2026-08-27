package client.util;

import net.minecraft.item.Item;

public final class SwapState {
   public boolean flag;
   public boolean flag2;
   public int value = -1;
   public int value2 = -1;
   public boolean flag3;
   public Item item;
   public int value3 = -1;
   public int value4 = -3;
   public Item item2;
   public boolean flag4;

   public SwapState() {
   }

   public void update() {
      this.flag = false;
      this.flag2 = false;
      this.value = -1;
      this.value2 = -1;
      this.item = null;
      this.value3 = -1;
      this.value4 = -3;
      this.item2 = null;
      this.flag4 = false;
   }
}
