package client.enums;

import client.data.Palette;

public enum ThemePalette {
   INSTANCE(new Palette(-1, -328966, -657931, -1710619, -9211021, -15263977, -16119286, -7934036, -208051, -148876, -219739)),
   INSTANCE2(new Palette(-16119286, -15263977, -14277082, -12566464, -6184543, -328966, -328966, -7934036, -4959479, -4046580, -4645860));

   private final Palette palette;
   private static final ThemePalette[] themePaletteArray = getThemePaletteArray();

   private ThemePalette(Palette palette2) {
      this.palette = palette2;
   }

   public static ThemePalette getThemePaletteByString(String text) {
      return Enum.valueOf(ThemePalette.class, text);
   }

   private static ThemePalette[] getThemePaletteArray() {
      return new ThemePalette[]{INSTANCE, INSTANCE2};
   }

   public Palette getPalette() {
      return this.palette;
   }
}
