package client.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public final class ItemTrackEntry {
   public final Vec3d vec3d;
   public final long time;
   public final long time2;
   public final ItemStack itemStack;
   public final String text;

   public ItemTrackEntry(Vec3d vec3d2, long time3, long time4, ItemStack itemStack2, String text2) {
      this.vec3d = vec3d2;
      this.time = time3;
      this.time2 = time4;
      this.itemStack = itemStack2;
      this.text = text2;
   }
}
