package recovered.fabric.identity;

import net.minecraft.client.MinecraftClient;

public final class ProfileIdentity {
   private ProfileIdentity() {
   }

   public static String username() {
      try {
         MinecraftClient mc = MinecraftClient.getInstance();
         if (mc != null && mc.getGameProfile() != null) {
            String name = mc.getGameProfile().getName();
            if (name != null && !name.isEmpty()) {
               return name;
            }
         }
      } catch (Exception ignored) {
      }
      return "Player";
   }

   public static int uid() {
      return 0;
   }

   public static String hwid() {
      return DeviceIdentity.get();
   }

   public static String role() {
      return "Developer$$";
   }

   public static String subscription() {
      return "Lifetime";
   }
}
