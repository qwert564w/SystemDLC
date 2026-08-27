package client.data;

import net.minecraft.item.consume.UseAction;

public class UseActionSwitchMap {
   public static final int[] intArray = new int[UseAction.values().length];

   static {
      try {
         intArray[UseAction.NONE.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror7) {
      }

      try {
         intArray[UseAction.EAT.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror6) {
      }

      try {
         intArray[UseAction.DRINK.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror5) {
      }

      try {
         intArray[UseAction.BLOCK.ordinal()] = 4;
      } catch (NoSuchFieldError nosuchfielderror4) {
      }

      try {
         intArray[UseAction.BOW.ordinal()] = 5;
      } catch (NoSuchFieldError nosuchfielderror3) {
      }

      try {
         intArray[UseAction.SPEAR.ordinal()] = 6;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         intArray[UseAction.BRUSH.ordinal()] = 7;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         intArray[UseAction.BUNDLE.ordinal()] = 8;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
