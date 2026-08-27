package client.enums;

public enum Language {
   RU("Русский", "RU"),
   EN("English", "EN");

   private final String text;
   private final String text2;
   private static final Language[] languageArray = getLanguageArray();

   private Language(String text3, String text4) {
      this.text = text3;
      this.text2 = text4;
   }

   public String getText2() {
      return this.text2;
   }

   public String getText() {
      return this.text;
   }

   private static Language[] getLanguageArray() {
      return new Language[]{RU, EN};
   }

   public Language getLanguage() {
      Language[] alanguage = values();
      return alanguage[(this.ordinal() + 1) % alanguage.length];
   }

   public static Language getLanguageByString(String text) {
      return Enum.valueOf(Language.class, text);
   }
}
