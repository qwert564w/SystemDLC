package client.enums;

public enum ScriptType {
   LATIN,
   CYRILLIC;

   private static final ScriptType[] scriptTypeArray = getScriptTypeArray();

   private static ScriptType[] getScriptTypeArray() {
      return new ScriptType[]{LATIN, CYRILLIC};
   }

   public static ScriptType getScriptTypeByString(String text) {
      return Enum.valueOf(ScriptType.class, text);
   }
}
