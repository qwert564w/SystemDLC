package client.util;

import client.module.Feature;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

public class KeyBindings {
   public static void update() {
      if (Feature.mc.options != null) {
         if (Feature.mc.player != null) {
            Feature.mc.player.setSprinting(false);
         }

         for (KeyBinding keybinding : getKeyBindingArray()) {
            keybinding.setPressed(false);
         }
      }
   }

   public static void update2() {
      if (Feature.mc != null && Feature.mc.options != null) {
         Feature.mc.options.attackKey.setPressed(false);
      }
   }

   public static void update3() {
      if (Feature.mc != null && Feature.mc.options != null) {
         Key key = InputUtil.fromTranslationKey(Feature.mc.options.useKey.getBoundKeyTranslationKey());
         KeyBinding.onKeyPressed(key);
      }
   }

   public static void update4() {
      if (Feature.mc.options != null && Feature.mc.getWindow() != null && Feature.mc.currentScreen == null) {
         long i = Feature.mc.getWindow().getHandle();

         for (KeyBinding keybinding : getKeyBindingArray()) {
            int j = keybinding.getDefaultKey().getCode();
            keybinding.setPressed(InputUtil.isKeyPressed(i, j));
         }
      }
   }

   public static void update5() {
      if (Feature.mc != null && Feature.mc.options != null) {
         Feature.mc.options.useKey.setPressed(false);
      }
   }

   public static void update6() {
      if (Feature.mc != null && Feature.mc.options != null) {
         Key key = InputUtil.fromTranslationKey(Feature.mc.options.attackKey.getBoundKeyTranslationKey());
         KeyBinding.onKeyPressed(key);
      }
   }

   private static KeyBinding[] getKeyBindingArray() {
      return new KeyBinding[]{Feature.mc.options.forwardKey};
   }
}
