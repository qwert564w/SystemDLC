package client.gui.screen;

import client.util.UnsafeAccess;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

public final class ScreenHelper {
   public static final long time = getLong();

   private ScreenHelper() {
   }

   private static long getLong() {
      try {
         for (Field field : GameMenuScreen.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && field.getType() == ButtonWidget.class) {
               return UnsafeAccess.unsafe.objectFieldOffset(field);
            }
         }
      } catch (Exception exception) {
      }

      return -1L;
   }
}
