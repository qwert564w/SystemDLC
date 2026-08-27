package recovered.fabric.offline;

public final class JsonField {
   private JsonField() {
   }

   public static String text(String source, String field) {
      int start = valueStart(source, field);
      if (source.startsWith("null", start)) {
         return null;
      } else if (source.charAt(start) != '"') {
         throw new IllegalArgumentException(field + " is not text");
      } else {
         return decode(source, start);
      }
   }

   public static boolean bool(String source, String field) {
      int start = valueStart(source, field);
      if (source.startsWith("true", start)) {
         return true;
      } else if (source.startsWith("false", start)) {
         return false;
      } else {
         throw new IllegalArgumentException(field + " is not boolean");
      }
   }

   public static String quote(String value) {
      if (value == null) {
         return "null";
      } else {
         StringBuilder result = new StringBuilder(value.length() + 2).append('"');

         for (int index = 0; index < value.length(); index++) {
            append(result, value.charAt(index));
         }

         return result.append('"').toString();
      }
   }

   private static int valueStart(String source, String field) {
      if (source == null) {
         throw new IllegalArgumentException("payload missing");
      } else {
         int key = source.indexOf("\"" + field + "\"");
         int colon = key < 0 ? -1 : source.indexOf(58, key + field.length() + 2);
         if (colon < 0) {
            throw new IllegalArgumentException(field + " missing");
         } else {
            int start = colon + 1;

            while (start < source.length() && Character.isWhitespace(source.charAt(start))) {
               start++;
            }

            if (start >= source.length()) {
               throw new IllegalArgumentException(field + " empty");
            } else {
               return start;
            }
         }
      }
   }

   private static String decode(String source, int start) {
      StringBuilder result = new StringBuilder();

      for (int index = start + 1; index < source.length(); index++) {
         char value = source.charAt(index);
         if (value == '"') {
            return result.toString();
         }

         if (value != '\\') {
            result.append(value);
         } else {
            char escaped = source.charAt(++index);
            switch (escaped) {
               case '"':
               case '/':
               case '\\':
                  result.append(escaped);
                  break;
               case 'b':
                  result.append('\b');
                  break;
               case 'f':
                  result.append('\f');
                  break;
               case 'n':
                  result.append('\n');
                  break;
               case 'r':
                  result.append('\r');
                  break;
               case 't':
                  result.append('\t');
                  break;
               case 'u':
                  result.append((char)Integer.parseInt(source.substring(index + 1, index + 5), 16));
                  index += 4;
                  break;
               default:
                  throw new IllegalArgumentException("invalid JSON escape");
            }
         }
      }

      throw new IllegalArgumentException("unterminated JSON string");
   }

   private static void append(StringBuilder result, char value) {
      switch (value) {
         case '\b':
            result.append("\\b");
            break;
         case '\t':
            result.append("\\t");
            break;
         case '\n':
            result.append("\\n");
            break;
         case '\f':
            result.append("\\f");
            break;
         case '\r':
            result.append("\\r");
            break;
         case '"':
            result.append("\\\"");
            break;
         case '\\':
            result.append("\\\\");
            break;
         default:
            if (value < ' ') {
               result.append(String.format("\\u%04x", Integer.valueOf(value)));
            } else {
               result.append(value);
            }
      }
   }
}
