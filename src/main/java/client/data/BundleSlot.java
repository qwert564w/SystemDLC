package client.data;

import net.minecraft.screen.slot.Slot;

public record BundleSlot(Slot bundleSlot, int indexInBundle) {
   public int getIndexInBundle() {
      return this.indexInBundle;
   }

   public Slot getBundleSlot() {
      return this.bundleSlot;
   }
}
