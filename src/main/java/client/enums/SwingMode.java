package client.enums;

public enum SwingMode {
   SMOOTH("Плавная"),
   PUNCH("Панч"),
   SLIDE("Слайд"),
   SWAG("Свэговая"),
   SELF_BACK("В себя");

   private String text;
   private static final SwingMode[] swingModeArray = getSwingModeArray();

   private SwingMode(String text2) {
      this.text = text2;
   }

   public static SwingMode getSwingModeByString(String text2) {
      for (SwingMode swingmode : values()) {
         if (swingmode.text.equals(text2)) {
            return swingmode;
         }
      }

      return SMOOTH;
   }

   public static SwingMode getSwingModeByString2(String text) {
      return Enum.valueOf(SwingMode.class, text);
   }

   private static SwingMode[] getSwingModeArray() {
      return new SwingMode[]{SMOOTH, PUNCH, SLIDE, SWAG, SELF_BACK};
   }

   public String getText() {
      return this.text;
   }
}
