package client.util;

import net.minecraft.item.ItemStack;

public record CooldownEntry(ItemStack stack, double remainingSeconds, float ratio, String label) {
   public float getRatio() {
      return this.ratio;
   }

   public String getLabel() {
      return this.label;
   }

   public double getRemainingSeconds() {
      return this.remainingSeconds;
   }

   public ItemStack getStack() {
      return this.stack;
   }
}
