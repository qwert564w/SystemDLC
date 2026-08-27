package client.util;

import client.gui.hud.HotbarHud;
import net.minecraft.item.ItemStack;

public final class ItemDisplayEntry {
   public final int value;
   public ItemStack itemStack = ItemStack.EMPTY;
   public String text = "";
   public float value2;
   public float value3 = 24.0F;
   public float value4;
   public float value5;
   public boolean flag;

   public ItemDisplayEntry(int count) {
      this.value = count;
   }

   public float getFloat() {
      float f2 = this.value5;
      float f1 = this.value3;
      float f = 24.0F;
      return HotbarHud.getFloatByFloatFloatFloat(f2, f, f1);
   }
}
