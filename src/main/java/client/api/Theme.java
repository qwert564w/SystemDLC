package client.api;

import client.data.ThemeConfig;

public interface Theme {
   public int value322 = 436207616;

   public static float getFloatByInt(int count) {
      return (count & 0xFF) / 255.0F;
   }

   public static float getFloatByInt2(int count) {
      return (count >> 24 & 0xFF) / 255.0F;
   }

   public static float getFloatByInt3(int count) {
      return (count >> 8 & 0xFF) / 255.0F;
   }

   public static float getFloatByInt4(int count) {
      return (count >> 16 & 0xFF) / 255.0F;
   }

   public static int success() {
      return ThemeConfig.getPalette().getValue8();
   }

   public static int warning() {
      return ThemeConfig.getPalette().getValue9();
   }

   public static int primary() {
      return ThemeConfig.getPalette().getValue6();
   }

   public static int elevated() {
      return ThemeConfig.getPalette().getValue3();
   }

   public static int mutedFg() {
      return ThemeConfig.getPalette().getValue5();
   }

   public static int caution() {
      return ThemeConfig.getPalette().getValue10();
   }

   public static int border() {
      return ThemeConfig.getPalette().getValue4();
   }

   public static int danger() {
      return ThemeConfig.getPalette().getValue11();
   }

   public static int background() {
      return ThemeConfig.getPalette().getValue();
   }

   public static int surface() {
      return ThemeConfig.getPalette().getValue2();
   }

   public static int foreground() {
      return ThemeConfig.getPalette().getValue7();
   }
}
