package client.util;

import net.minecraft.util.math.BlockPos;

public final class NamedBlockPos {
   public final String text;
   public final BlockPos blockPos;

   public NamedBlockPos(String text2, BlockPos blockPos2) {
      this.text = text2;
      this.blockPos = blockPos2;
   }

   @Override
   public boolean equals(Object value) {
      return value instanceof NamedBlockPos namedblockpos && this.text.equals(namedblockpos.text) && this.blockPos.equals(namedblockpos.blockPos);
   }

   @Override
   public int hashCode() {
      return this.text.hashCode() * 31 + this.blockPos.hashCode();
   }
}
