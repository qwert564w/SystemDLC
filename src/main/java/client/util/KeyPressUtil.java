package client.util;

import client.module.Feature;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyPressUtil {
   private static boolean flag = false;

   public static void update() {
      if (flag) {
         flag = false;
         if (Feature.mc != null && Feature.mc.getWindow() != null) {
            GameOptions gameoptions = Feature.mc.options;
            long i = Feature.mc.getWindow().getHandle();
            gameoptions.forwardKey.setPressed(isLongKeyBinding(i, gameoptions.forwardKey));
            gameoptions.backKey.setPressed(isLongKeyBinding(i, gameoptions.backKey));
            gameoptions.leftKey.setPressed(isLongKeyBinding(i, gameoptions.leftKey));
            gameoptions.rightKey.setPressed(isLongKeyBinding(i, gameoptions.rightKey));
            gameoptions.jumpKey.setPressed(isLongKeyBinding(i, gameoptions.jumpKey));
            gameoptions.sprintKey.setPressed(isLongKeyBinding(i, gameoptions.sprintKey));
         }
      }
   }

   public static void update2() {
      if (flag) {
         if (Feature.mc != null && Feature.mc.player != null) {
            GameOptions gameoptions = Feature.mc.options;
            gameoptions.forwardKey.setPressed(false);
            gameoptions.backKey.setPressed(false);
            gameoptions.leftKey.setPressed(false);
            gameoptions.rightKey.setPressed(false);
            gameoptions.jumpKey.setPressed(false);
            gameoptions.sprintKey.setPressed(false);
            Feature.mc.player.setSprinting(false);
         }
      }
   }

   public static void update3() {
      if (!flag) {
         flag = true;
         if (Feature.mc != null && Feature.mc.player != null) {
            GameOptions gameoptions = Feature.mc.options;
            gameoptions.forwardKey.setPressed(false);
            gameoptions.backKey.setPressed(false);
            gameoptions.leftKey.setPressed(false);
            gameoptions.rightKey.setPressed(false);
            gameoptions.jumpKey.setPressed(false);
            gameoptions.sprintKey.setPressed(false);
            Feature.mc.player.setSprinting(false);
         }
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   private static boolean isLongKeyBinding(long time, KeyBinding keyBinding) {
      return InputUtil.isKeyPressed(time, keyBinding.getDefaultKey().getCode());
   }
}
