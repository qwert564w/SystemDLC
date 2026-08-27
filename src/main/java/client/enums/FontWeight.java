package client.enums;

public enum FontWeight {
   REGULAR,
   MEDIUM;

   private static final FontWeight[] fontWeightArray = getFontWeightArray();

   private static FontWeight[] getFontWeightArray() {
      return new FontWeight[]{REGULAR, MEDIUM};
   }

   public static FontWeight getFontWeightByString(String text) {
      return Enum.valueOf(FontWeight.class, text);
   }
}
