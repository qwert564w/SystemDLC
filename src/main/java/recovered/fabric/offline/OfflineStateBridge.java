package recovered.fabric.offline;

import client.data.SystemFriend;
import java.util.Set;
import recovered.fabric.diagnostic.SystemDlcLog;

/**
 * Loads the friend list at system initialization. The original build reached this through the
 * obfuscated "eV" class, which the recovery turned into {@link SystemFriend}.
 */
public final class OfflineStateBridge {
   private OfflineStateBridge() {
   }

   public static void initialize() {
      try {
         SystemFriend friends = SystemFriend.getInstance();
         friends.update2();
         Set<String> names = friends.getSet();
         SystemDlcLog.info("offline friends loaded count=" + names.size());
      } catch (Throwable throwable) {
         SystemDlcLog.errorOnce("offline friends load", root(throwable));
      }
   }

   private static Throwable root(Throwable error) {
      return error.getCause() == null ? error : error.getCause();
   }
}
