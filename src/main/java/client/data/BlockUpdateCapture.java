package client.data;

import net.minecraft.util.math.Direction;

public final class BlockUpdateCapture {
   public long time = -1L;
   public Direction direction;
   public final int value;
   public final int value2;
   public final int value3;

   public BlockUpdateCapture(int count, int count2, int count3) {
      this.value = count;
      this.value2 = count2;
      this.value3 = count3;
   }
}
