package client.util;

public final class NameParts {
   private static final String[] stringArray = new String[]{"a", "aa", "b", "bb", "c", "cc"};

   private NameParts() {
   }

   public static String[] getStringArray() {
      return (String[])stringArray.clone();
   }

   public static String[] getStringArrayByStringArray(String... text) {
      String[] astring = new String[stringArray.length + text.length];
      System.arraycopy(stringArray, 0, astring, 0, stringArray.length);
      System.arraycopy(text, 0, astring, stringArray.length, text.length);
      return astring;
   }
}
