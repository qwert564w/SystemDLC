package client.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public record BlockPlacement(BlockPos pos, Direction side, Vec3d aimPoint) {
   public Direction getSide() {
      return this.side;
   }

   public Vec3d getAimPoint() {
      return this.aimPoint;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
