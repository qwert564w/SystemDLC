package client.data;

import java.util.HashMap;
import java.util.Map;

public class TextSanitizer {
   private static final Map<Integer, Character> map = new HashMap<>(600);

   static {
      map.put(7424, 'a');
      map.put(665, 'b');
      map.put(7428, 'c');
      map.put(7429, 'd');
      map.put(7431, 'e');
      map.put(42800, 'f');
      map.put(610, 'g');
      map.put(668, 'h');
      map.put(618, 'i');
      map.put(7434, 'j');
      map.put(7435, 'k');
      map.put(671, 'l');
      map.put(7437, 'm');
      map.put(628, 'n');
      map.put(7439, 'o');
      map.put(7448, 'p');
      map.put(491, 'q');
      map.put(640, 'r');
      map.put(42801, 's');
      map.put(7451, 't');
      map.put(7452, 'u');
      map.put(7456, 'v');
      map.put(7457, 'w');
      map.put(655, 'y');
      map.put(7458, 'z');
      int[][] aint = new int[][]{
         {119808, 119834},
         {119860, 119886},
         {119912, 119938},
         {119964, 119990},
         {120016, 120042},
         {120068, 120094},
         {120120, 120146},
         {120172, 120198},
         {120224, 120250},
         {120276, 120302},
         {120328, 120354},
         {120380, 120406},
         {120432, 120458}
      };

      for (int[] aint1 : aint) {
         for (int i = 0; i < 26; i++) {
            map.put(aint1[0] + i, (char)(97 + i));
            map.put(aint1[1] + i, (char)(97 + i));
         }
      }

      int[] aint2 = new int[]{120782, 120792, 120802, 120812, 120822};

      for (int k1 : aint2) {
         for (int j = 0; j < 10; j++) {
            map.put(k1 + j, (char)(48 + j));
         }
      }

      map.put(8462, 'h');
      map.put(8492, 'b');
      map.put(8496, 'e');
      map.put(8497, 'f');
      map.put(8459, 'h');
      map.put(8464, 'i');
      map.put(8466, 'l');
      map.put(8499, 'm');
      map.put(8475, 'r');
      map.put(8495, 'e');
      map.put(8458, 'g');
      map.put(8500, 'o');
      map.put(8493, 'c');
      map.put(8460, 'h');
      map.put(8465, 'i');
      map.put(8476, 'r');
      map.put(8488, 'z');
      map.put(8450, 'c');
      map.put(8461, 'h');
      map.put(8469, 'n');
      map.put(8473, 'p');
      map.put(8474, 'q');
      map.put(8477, 'r');
      map.put(8484, 'z');

      for (int k = 0; k < 26; k++) {
         map.put(65313 + k, (char)(97 + k));
         map.put(65345 + k, (char)(97 + k));
      }

      for (int l = 0; l < 10; l++) {
         map.put(65296 + l, (char)(48 + l));
      }

      for (int i1 = 0; i1 < 26; i1++) {
         map.put(9398 + i1, (char)(97 + i1));
         map.put(9424 + i1, (char)(97 + i1));
      }

      for (int j1 = 0; j1 < 26; j1++) {
         map.put(127280 + j1, (char)(97 + j1));
         map.put(127344 + j1, (char)(97 + j1));
      }
   }

   private static String getStringByString(String text) {
      StringBuilder stringbuilder = new StringBuilder(text.length());
      int i = 0;

      while (i < text.length()) {
         int j = text.codePointAt(i);
         if (Character.isLetterOrDigit(j) || j == 32 || j == 95 || j == 45) {
            stringbuilder.appendCodePoint(j);
         } else if (!stringbuilder.isEmpty() && stringbuilder.charAt(stringbuilder.length() - 1) != ' ') {
            stringbuilder.append(' ');
         }

         i += Character.charCount(j);
      }

      return stringbuilder.toString().strip().replaceAll(" +", " ");
   }

   public static String getStringByStringString(String text, String text2) {
      String s = text.replaceAll("§.", "").replaceAll("[\\p{Cf}\\p{Cc}]", "");
      String s1 = getStringByString2(s);
      int i = getIntByStringString(text2, s1);
      return i <= 0 ? "" : getStringByString(s1.substring(0, i));
   }

   private static int getIntByStringString(String text, String text2) {
      if (text.isEmpty()) {
         return 0;
      } else {
         int i = text2.length();
         int j = text.length();
         int k = i - j;

         for (int l = 0; l <= k; l++) {
            if (text2.regionMatches(true, l, text, 0, j)) {
               return l;
            }
         }

         return -1;
      }
   }

   public static String getStringByString2(String text) {
      if (text != null && !text.isEmpty()) {
         StringBuilder stringbuilder = new StringBuilder(text.length());
         int i = 0;

         while (i < text.length()) {
            int j = text.codePointAt(i);
            Character character = map.get(j);
            if (character != null) {
               stringbuilder.append(character);
            } else {
               stringbuilder.appendCodePoint(j);
            }

            i += Character.charCount(j);
         }

         return stringbuilder.toString();
      } else {
         return text;
      }
   }
}
