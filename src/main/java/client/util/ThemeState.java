package client.util;

import client.module.client.ThemeModule;

public final class ThemeState {
   private static boolean flag = true;

   private ThemeState() {
   }

   public static boolean check() {
      ThemeModule thememodule = ThemeModule.getThemeModule();
      return thememodule != null ? thememodule.check5() : flag;
   }

   public static void update() {
      setBoolean(!check());
   }

   public static void setBoolean(boolean flag2) {
      flag = flag2;
      ThemeModule thememodule = ThemeModule.getThemeModule();
      if (thememodule != null) {
         thememodule.onBoolean(flag2);
      }
   }
}
