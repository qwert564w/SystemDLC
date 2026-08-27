package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.gui.widget.UiContext;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

@HookClass(ChatScreen.class)
public class InputHooks {
   @Hook(
      method = "method_25402",
      desc = "(DDI)Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isChatScreenDoubleDoubleInt(ChatScreen chatScreen, double value, double value2, int count) {
      return count != 0 ? true : !CoordsParser.isDoubleDouble(value, value2);
   }

   @Hook(
      method = "method_25401",
      desc = "(DDDD)Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isChatScreenDoubleDoubleDoubleDouble(ChatScreen chatScreen, double value, double value2, double value3, double value4) {
      return !UiContext.getInstance().isDoubleDoubleDouble(value2, value4, value);
   }

   @Hook(
      target = Keyboard.class,
      method = "method_1457",
      desc = "(JII)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isKeyboardLongIntInt(Keyboard keyboard, long time, int count, int count2) {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      if (minecraftclient != null && minecraftclient.currentScreen instanceof ChatScreen) {
         if (Character.charCount(count) == 1) {
            return !UiContext.getInstance().isCharInt((char)count, count2);
         } else {
            boolean flag = UiContext.getInstance().isCharInt(Character.highSurrogate(count), count2);
            boolean flag1 = UiContext.getInstance().isCharInt(Character.lowSurrogate(count), count2);
            return !flag && !flag1;
         }
      } else {
         return true;
      }
   }

   @Hook(
      method = "method_25404",
      desc = "(III)Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isChatScreenIntIntInt(ChatScreen chatScreen, int count, int count2, int count3) {
      return !UiContext.getInstance().isIntIntInt2(count2, count, count3);
   }
}
