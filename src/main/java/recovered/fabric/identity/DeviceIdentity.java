package recovered.fabric.identity;

public final class DeviceIdentity {
   private static final String value = "offline";

   private DeviceIdentity() {
   }

   public static String get() {
      return value;
   }
}
