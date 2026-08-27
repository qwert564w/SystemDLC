package recovered.fabric.offline;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class ResourceText {
   private ResourceText() {
   }

   public static String read(String path, String fallback) {
      try {
         String text;
         try (InputStream input = ResourceText.class.getResourceAsStream(path)) {
            text = input == null ? fallback : new String(input.readAllBytes(), StandardCharsets.UTF_8);
         }

         return text;
      } catch (IOException iOException) {
         return fallback;
      }
   }
}
