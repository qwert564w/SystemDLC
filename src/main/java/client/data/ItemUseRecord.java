package client.data;

import client.enums.TrackedItem;

public record ItemUseRecord(TrackedItem entry, long time) {
   public long getTime() {
      return this.time;
   }

   public TrackedItem getEntry() {
      return this.entry;
   }
}
