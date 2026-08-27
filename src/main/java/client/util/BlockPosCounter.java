package client.util;

import net.minecraft.util.math.BlockPos;

public final class BlockPosCounter {
   public BlockPos blockPos;
   public int value;

   public BlockPosCounter(BlockPos blockPos2, int count) {
      this.blockPos = blockPos2;
      this.value = count;
   }
}
