package client.util;

import client.module.Feature;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public final class BlockChecks {
   private BlockChecks() {
   }

   public static boolean isBlockPos(BlockPos blockPos) {
      if (Feature.mc.world == null || Feature.mc.player == null) {
         return false;
      } else {
         return !Feature.mc.world.getBlockState(blockPos).isReplaceable() ? false : !Feature.mc.player.getBoundingBox().intersects(new Box(blockPos));
      }
   }

   public static boolean isBlockPos2(BlockPos blockPos) {
      return Feature.mc.world == null ? false : (Integer)Feature.mc.world.getBlockState(blockPos).get(RespawnAnchorBlock.CHARGES) > 0;
   }
}
