package client.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public final class DroppedItemEntry {
   public final ItemStack itemStack;
   public Vec3d vec3d;

   public DroppedItemEntry(ItemStack itemStack2, Vec3d vec3d2) {
      this.itemStack = itemStack2;
      this.vec3d = vec3d2;
   }
}
