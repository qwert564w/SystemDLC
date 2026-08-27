package client.data;

import client.enums.InjectPoint;

public class InjectPointSwitchMap {
   public static final int[] intArray = new int[InjectPoint.values().length];

   static {
      try {
         intArray[InjectPoint.HEAD.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror3) {
      }

      try {
         intArray[InjectPoint.TAIL.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         intArray[InjectPoint.REPLACE.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[InjectPoint.CANCELLABLE.ordinal()] = 4;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
