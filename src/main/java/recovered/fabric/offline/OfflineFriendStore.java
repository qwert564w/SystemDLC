package recovered.fabric.offline;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import recovered.fabric.diagnostic.SystemDlcLog;

public final class OfflineFriendStore {
   private static final Path file = Path.of("config", "systemdlc", "friends.json").toAbsolutePath();
   private static final String success = "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{}}";

   private OfflineFriendStore() {
   }

   public static synchronized String call(int action, String payload) {
      try {
         if (action == 90) {
            return load();
         } else {
            save(payload);
            return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{}}";
         }
      } catch (Throwable throwable) {
         SystemDlcLog.error("offline friend storage", throwable);
         return "{\"success\":false,\"message\":\"friend storage failed\",\"status\":\"OFFLINE_ERROR\",\"data\":{}}";
      }
   }

   private static String load() throws Exception {
      String content = Files.isRegularFile(file) ? Files.readString(file, StandardCharsets.UTF_8) : "[]";
      String friends = JsonPayload.valid(content);
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{\"friends\":" + friends + "}}";
   }

   private static void save(String payload) throws Exception {
      String friends = JsonPayload.array(payload, "payload");
      Files.createDirectories(file.getParent());
      Files.writeString(file, friends.toString(), StandardCharsets.UTF_8);
   }
}
