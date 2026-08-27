package client.enums;

public enum ColorFormat {
   RGB("RGB"),
   HSL("HSL"),
   HEX("HEX");

   public final String text;
   private static final ColorFormat[] colorFormatArray = getColorFormatArray();

   private ColorFormat(String text2) {
      this.text = text2;
   }

   private static ColorFormat[] getColorFormatArray() {
      return new ColorFormat[]{RGB, HSL, HEX};
   }

   public static ColorFormat getColorFormatByString(String text) {
      return Enum.valueOf(ColorFormat.class, text);
   }
}
