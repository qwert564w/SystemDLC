package client.util;

import client.enums.TrackedItem;
import net.minecraft.screen.slot.Slot;

public record SlotEntry(Slot slot, TrackedItem entry) {
   public TrackedItem getEntry() {
      return this.entry;
   }

   public Slot getSlot() {
      return this.slot;
   }
}
