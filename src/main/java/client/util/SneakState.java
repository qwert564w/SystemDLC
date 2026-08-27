package client.util;

import client.module.Feature;
import net.minecraft.client.option.GameOptions;

public final class SneakState {
   private static int value = 0;
   private static boolean flag = false;

   private SneakState() {
   }

   public static void update() {
      flag = !check();
   }

   public static boolean isFlag() {
      return flag;
   }

   public static void update2() {
      if (value > 0) {
         value--;
      }

      if (value == 0) {
         KeyPressUtil.update();
      }
   }

   public static void update3() {
      value = 0;
      KeyPressUtil.update();
   }

   private static boolean check() {
      if (Feature.mc.player != null && Feature.mc.options != null) {
         GameOptions gameoptions = Feature.mc.options;
         return !gameoptions.forwardKey.isPressed()
               && !gameoptions.backKey.isPressed()
               && !gameoptions.leftKey.isPressed()
               && !gameoptions.rightKey.isPressed()
            ? gameoptions.jumpKey.isPressed() || Feature.mc.player.isSprinting()
            : true;
      } else {
         return false;
      }
   }

   public static void update4() {
      if (value != 0) {
         KeyPressUtil.update3();
         KeyPressUtil.update2();
      }
   }

   public static boolean isValueAsBoolean() {
      return value > 0;
   }

   public static void update5() {
      value++;
      KeyPressUtil.update3();
      KeyPressUtil.update2();
   }
}
