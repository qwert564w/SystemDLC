package recovered.fabric.offline;

public final class JsonPayload {
   private JsonPayload() {
   }

   public static String array(String json, String field) {
      if (json == null) {
         throw new IllegalArgumentException("payload missing");
      } else {
         String source = json.trim();
         if (source.startsWith("[")) {
            return valid(source);
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
               } else if (source.charAt(start) == '"') {
                  return valid(decode(source, start));
               } else {
                  int end = source.lastIndexOf(93);
                  if (source.charAt(start) == '[' && end >= start) {
                     return valid(source.substring(start, end + 1));
                  } else {
                     throw new IllegalArgumentException(field + " invalid");
                  }
               }
            }
         }
      }
   }

   public static String valid(String value) {
      String result = value.trim();
      if (result.length() <= 1000000 && result.startsWith("[") && result.endsWith("]")) {
         return result;
      } else {
         throw new IllegalArgumentException("invalid JSON array");
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
            if (++index >= source.length()) {
               break;
            }

            char escaped = source.charAt(index);
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
                  if (index + 4 >= source.length()) {
                     throw new IllegalArgumentException("invalid unicode escape");
                  }

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
}
