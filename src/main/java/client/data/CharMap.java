package client.data;

import java.util.HashMap;
import java.util.Map;

public class CharMap {
   private static final Map<Character, Character> map = new HashMap<>();

   private CharMap() {
   }

   static {
      onCharChar('А', 'A');
      onCharChar('Б', 'B');
      onCharChar('В', 'V');
      onCharChar('Г', 'G');
      onCharChar('Д', 'D');
      onCharChar('Е', 'E');
      onCharChar('Ж', 'J');
      onCharChar('З', 'S');
      onCharChar('И', 'I');
      onCharChar('К', 'K');
      onCharChar('Л', 'L');
      onCharChar('М', 'M');
      onCharChar('Н', 'N');
      onCharChar('О', 'O');
      onCharChar('П', 'P');
      onCharChar('Р', 'R');
      onCharChar('С', 'C');
      onCharChar('Т', 'T');
      onCharChar('У', 'U');
      onCharChar('Ф', 'F');
      onCharChar('Х', 'H');
      onCharChar('Ш', 'W');
      onCharChar('Ы', 'Y');
      onCharChar('Ё', 'E');
      onCharChar('Й', 'Y');
      onCharChar('Ц', 'C');
      onCharChar('Ч', 'C');
      onCharChar('Щ', 'W');
      onCharChar('Ъ', 'B');
      onCharChar('Ь', 'B');
      onCharChar('Э', 'E');
      onCharChar('Ю', 'I');
      onCharChar('Я', 'R');
      map.put('$', 'x');
      map.put('%', 'X');
   }

   private static void onCharChar(char symbol, char symbol2) {
      map.put(symbol, symbol2);
      map.put(Character.toLowerCase(symbol), Character.toLowerCase(symbol2));
   }

   public static String getStringByString(String text) {
      if (text == null) {
         return null;
      } else {
         StringBuilder stringbuilder = new StringBuilder(text.length());

         for (int i = 0; i < text.length(); i++) {
            char c0 = text.charAt(i);
            Character character = map.get(c0);
            stringbuilder.append(character != null ? character : c0);
         }

         return stringbuilder.toString();
      }
   }
}
