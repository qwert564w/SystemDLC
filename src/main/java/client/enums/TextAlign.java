package client.enums;

public enum TextAlign {
   LEFT("Лево"),
   CENTER("Центр"),
   RIGHT("Право");

   public final String text;
   private static final TextAlign[] textAlignArray = getTextAlignArray();

   private TextAlign(String text2) {
      this.text = text2;
   }

   private static TextAlign[] getTextAlignArray() {
      return new TextAlign[]{LEFT, CENTER, RIGHT};
   }

   public static TextAlign getTextAlignByString(String text) {
      return Enum.valueOf(TextAlign.class, text);
   }
}
