package client.data;

import client.enums.ThemePalette;
import client.module.client.ThemeModule;
import client.util.Easings;

public final class ThemeConfig {
   private static final float value = 0.35F;
   private static ThemePalette themePalette = ThemePalette.INSTANCE;
   private static Palette palette = ThemePalette.INSTANCE.getPalette();
   private static Palette palette2 = ThemePalette.INSTANCE.getPalette();
   private static final Tween tween = new Tween(1.0F, 0.35F).getTweenByFunction(Easings::getFloatByFloat7);
   private static Palette palette3 = palette2;
   private static float value2 = 1.0F;

   private ThemeConfig() {
   }

   public static void setThemePalette(ThemePalette themePalette2) {
      if (themePalette2 != themePalette) {
         palette = getPalette();
         themePalette = themePalette2;
         palette2 = themePalette2.getPalette();
         tween.setFloat(0.0F);
         tween.setFloat2(1.0F);
      }
   }

   public static void setString(String text) {
      if (text != null) {
         try {
            ThemePalette themepalette = ThemePalette.getThemePaletteByString(text);
            if (themepalette == themePalette) {
               return;
            }

            themePalette = themepalette;
            palette = themepalette.getPalette();
            palette2 = themepalette.getPalette();
            tween.setFloat(1.0F);
         } catch (IllegalArgumentException illegalargumentexception) {
         }
      }
   }

   public static void update() {
      setThemePalette(themePalette == ThemePalette.INSTANCE ? ThemePalette.INSTANCE2 : ThemePalette.INSTANCE);
   }

   public static Palette getPalette() {
      float f = tween.getFloat();
      Palette palettex;
      if (f >= 1.0F) {
         palettex = palette2;
      } else if (f == value2) {
         palettex = palette3;
      } else {
         value2 = f;
         int k5 = palette.getValue();
         int j = palette2.getValue();
         int i = k5;
         k5 = AnimatedInt.getIntByIntFloatInt(j, f, i);
         int l5 = palette.getValue2();
         int l = palette2.getValue2();
         int k = l5;
         l5 = AnimatedInt.getIntByIntFloatInt(l, f, k);
         int i6 = palette.getValue3();
         int j1 = palette2.getValue3();
         int i1 = i6;
         i6 = AnimatedInt.getIntByIntFloatInt(j1, f, i1);
         int j6 = palette.getValue4();
         int l1 = palette2.getValue4();
         int k1 = j6;
         j6 = AnimatedInt.getIntByIntFloatInt(l1, f, k1);
         int k6 = palette.getValue5();
         int j2 = palette2.getValue5();
         int i2 = k6;
         k6 = AnimatedInt.getIntByIntFloatInt(j2, f, i2);
         int l6 = palette.getValue6();
         int l2 = palette2.getValue6();
         int k2 = l6;
         l6 = AnimatedInt.getIntByIntFloatInt(l2, f, k2);
         int i7 = palette.getValue7();
         int j3 = palette2.getValue7();
         int i3 = i7;
         i7 = AnimatedInt.getIntByIntFloatInt(j3, f, i3);
         int j7 = palette.getValue8();
         int l3 = palette2.getValue8();
         int k3 = j7;
         j7 = AnimatedInt.getIntByIntFloatInt(l3, f, k3);
         int k7 = palette.getValue9();
         int j4 = palette2.getValue9();
         int i4 = k7;
         k7 = AnimatedInt.getIntByIntFloatInt(j4, f, i4);
         int l7 = palette.getValue10();
         int l4 = palette2.getValue10();
         int k4 = l7;
         l7 = AnimatedInt.getIntByIntFloatInt(l4, f, k4);
         int i8 = palette.getValue11();
         int j5 = palette2.getValue11();
         int i5 = i8;
         palette3 = new Palette(k5, l5, i6, j6, k6, l6, i7, j7, k7, l7, AnimatedInt.getIntByIntFloatInt(j5, f, i5));
         palettex = palette3;
      }

      ThemeModule thememodule = ThemeModule.getThemeModule();
      return thememodule != null ? thememodule.getPaletteByPalette(palettex) : palettex;
   }

   public static ThemePalette getThemePalette() {
      return themePalette;
   }
}
