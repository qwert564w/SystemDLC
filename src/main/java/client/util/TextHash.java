package client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class TextHash {
   private static boolean flag = false;

   private TextHash() {
   }

   public static boolean isStringString(String text, String text2) {
      if (text2 != null && text != null) {
         if (text2.equals(text)) {
            return true;
         } else if (!getStringByString(text2).equals(getStringByString(text))) {
            return false;
         } else {
            flag = true;
            return true;
         }
      } else {
         return text2 == null && text == null;
      }
   }

   public static Object getObjectByStringMap(String text, Map map) {
      Object object = map.get(text);
      if (object != null) {
         return object;
      } else {
         for (Entry entry : (Iterable<Entry>)(map.entrySet())) {
            String s = (String)entry.getKey();
            if (isStringString(text, s)) {
               return entry.getValue();
            }
         }

         return null;
      }
   }

   public static JsonElement getJsonElementByStringJsonObject(String text, JsonObject jsonObject) {
      JsonElement jsonelement = jsonObject.get(text);
      if (jsonelement != null) {
         return jsonelement;
      } else {
         for (Entry entry : jsonObject.entrySet()) {
            String s = (String)entry.getKey();
            if (isStringString(text, s)) {
               return (JsonElement)entry.getValue();
            }
         }

         return null;
      }
   }

   public static String getStringByListString(List<String> list, String text) {
      if (list.contains(text)) {
         return text;
      } else {
         for (String s : list) {
            if (isStringString(text, s)) {
               return s;
            }
         }

         return null;
      }
   }

   public static String getStringByString(String text) {
      if (text == null) {
         return null;
      } else {
         StringBuilder stringbuilder = null;

         for (int i = 0; i < text.length(); i++) {
            char c0 = text.charAt(i);
            int j = "aeocpyxABCEHKMOPTX".indexOf(c0);
            if (j < 0) {
               if (stringbuilder != null) {
                  stringbuilder.append(c0);
               }
            } else {
               if (stringbuilder == null) {
                  stringbuilder = new StringBuilder(text.length()).append(text, 0, i);
               }

               stringbuilder.append("аеосрухАВСЕНКМОРТХ".charAt(j));
            }
         }

         return stringbuilder == null ? text : stringbuilder.toString();
      }
   }

   public static char getCharByChar(char symbol) {
      int i = "aeocpyxABCEHKMOPTX".indexOf(symbol);
      return i < 0 ? symbol : "аеосрухАВСЕНКМОРТХ".charAt(i);
   }

   public static long getLongByStringArray(String[] textArray) {
      long i = 1469598103934665603L;
      if (textArray != null) {
         for (String s : textArray) {
            if (s != null) {
               for (int j = 0; j < s.length(); j++) {
                  i ^= getCharByChar(s.charAt(j));
                  i *= 1099511628211L;
               }
            }
         }
      }

      return i;
   }

   public static long getLongByString(String text) {
      long i = 1469598103934665603L;
      if (text != null) {
         for (int j = 0; j < text.length(); j++) {
            i ^= getCharByChar(text.charAt(j));
            i *= 1099511628211L;
         }
      }

      return i;
   }

   public static void setFlag() {
      flag = true;
   }

   public static boolean check() {
      boolean flagx = flag;
      flag = false;
      return flagx;
   }
}
