package client.data;

import client.enums.TextAlign;

public class TextAlignSwitchMap {
   public static final int[] intArray = new int[TextAlign.values().length];

   static {
      try {
         intArray[TextAlign.LEFT.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[TextAlign.RIGHT.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
