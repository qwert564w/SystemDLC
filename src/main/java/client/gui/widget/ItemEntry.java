package client.gui.widget;

import client.api.ListEntry;
import client.gui.hud.SwapBindsHud;
import client.util.Animation;
import client.util.Easings;
import net.minecraft.item.ItemStack;

public final class ItemEntry implements ListEntry {
   public final int value;
   public ItemStack itemStack;
   public int value2;
   public String text;
   public float value3;
   public final Animation animation = new Animation(6.0F).getAnimationByFunction(Easings::getFloatByFloat3).getAnimation();

   public ItemEntry(int count, ItemStack itemStack, int count2, String text) {
      this.value = count;
      this.onStringItemStackInt(text, itemStack, count2);
   }

   public boolean check() {
      return this.itemStack == null || this.itemStack.isEmpty();
   }

   public void onStringItemStackInt(String text2, ItemStack itemStack2, int count) {
      this.itemStack = itemStack2;
      this.value2 = count;
      this.text = text2;
      ItemStack itemstack = this.itemStack;
      this.value3 = SwapBindsHud.getFloatByItemStackStringInt(itemstack, text2, count);
   }

   @Override
   public float itemHeight() {
      return 32.0F;
   }

   @Override
   public Animation animation() {
      return this.animation;
   }
}
