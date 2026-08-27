package client.data;

import client.enums.ServerFlag;

public class ServerFlagSwitchMap {
   public static final int[] intArray = new int[ServerFlag.values().length];

   static {
      try {
         intArray[ServerFlag.NONE.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror3) {
      }

      try {
         intArray[ServerFlag.ANY.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         intArray[ServerFlag.FT.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[ServerFlag.HW.ordinal()] = 4;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
