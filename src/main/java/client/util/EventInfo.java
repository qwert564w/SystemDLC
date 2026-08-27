package client.util;

import client.data.EventStatus;
import java.util.List;
import net.minecraft.util.math.BlockPos;

public record EventInfo(int index, String name, EventStatus status, BlockPos coords, String warp, List<String> extras) {
   public String getWarp() {
      return this.warp;
   }

   public BlockPos getCoords() {
      return this.coords;
   }

   public List<String> getExtras() {
      return this.extras;
   }

   public EventStatus getStatus() {
      return this.status;
   }

   public String getName() {
      return this.name;
   }

   public int getIndex() {
      return this.index;
   }
}
