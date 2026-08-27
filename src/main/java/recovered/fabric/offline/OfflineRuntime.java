package recovered.fabric.offline;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import recovered.fabric.identity.DeviceIdentity;
import recovered.fabric.identity.ProfileIdentity;

public final class OfflineRuntime {
   private static final Set<Integer> assetActions = Set.of(10, 11, 12, 13, 14, 15, 56);
   private static final Set<Integer> configActions = Set.of(30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46, 47, 48, 92, 93);
   private static final String authorized = authorization();
   private static final String success = "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{}}";
   private static final String stopped = "{\"success\":false,\"message\":\"offline runtime stopped\",\"status\":\"OFFLINE_SHUTDOWN\",\"data\":{}}";
   private static final String assets = ResourceText.read("/offline/actions/assets.json", authorized);
   private static volatile boolean closed;

   private OfflineRuntime() {
   }

   public static String call(int action, String payload, String session) {
      if (closed) {
         return "{\"success\":false,\"message\":\"offline runtime stopped\",\"status\":\"OFFLINE_SHUTDOWN\",\"data\":{}}";
      } else if (assetActions.contains(action)) {
         return assets;
      } else if (configActions.contains(action)) {
         return OfflineConfigStore.call(action, payload);
      } else if (action != 90 && action != 91) {
         return action != 1 && action != 3 ? "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{}}" : authorized;
      } else {
         return OfflineFriendStore.call(action, payload);
      }
   }

   public static String deviceId() {
      return DeviceIdentity.get();
   }

   public static String pcName() {
      try {
         return InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException unknownHostException) {
         return System.getenv().getOrDefault("COMPUTERNAME", "offline");
      }
   }

   public static String ip() {
      return "127.0.0.1";
   }

   public static String wsUrl() {
      return "ws://127.0.0.1/offline";
   }

   public static void shutdown() {
      closed = true;
   }

   private static String authorization() {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{\"session_id\":\"offline\",\"username\":\""
         + ProfileIdentity.username()
         + "\",\"uid\":"
         + ProfileIdentity.uid()
         + ",\"hwid\":\""
         + ProfileIdentity.hwid()
         + "\",\"role\":\""
         + ProfileIdentity.role()
         + "\",\"subscription\":\""
         + ProfileIdentity.subscription()
         + "\"}}";
   }
}
