package client.data;

import client.enums.HookPoint;

public class HookPointSwitchMap {
   public static final int[] intArray = new int[HookPoint.values().length];

   static {
      try {
         intArray[HookPoint.HEAD.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror3) {
      }

      try {
         intArray[HookPoint.TAIL.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         intArray[HookPoint.REPLACE.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[HookPoint.HEAD_CANCELLABLE.ordinal()] = 4;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
