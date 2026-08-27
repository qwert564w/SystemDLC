package client.data;

import net.minecraft.util.math.Direction.Axis;

public class AxisSwitchMap {
   public static final int[] intArray = new int[Axis.values().length];

   static {
      try {
         intArray[Axis.Y.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         intArray[Axis.X.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[Axis.Z.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
