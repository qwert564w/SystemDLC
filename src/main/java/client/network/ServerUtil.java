package client.network;

import client.module.Feature;
import net.minecraft.client.network.ServerInfo;

public class ServerUtil {
   private static String text;
   private static String text2;

   public static String getText2() {
      return text2;
   }

   public static void update() {
      try {
         text = Feature.mc.getGameProfile().getName();
      } catch (Exception exception1) {
      }

      try {
         ServerInfo serverinfo = Feature.mc.getCurrentServerEntry();
         text2 = serverinfo != null ? serverinfo.address : null;
      } catch (Exception exception) {
         text2 = null;
      }
   }

   public static String getText() {
      return text;
   }

   public static void update2() {
      try {
         text = Feature.mc.getGameProfile().getName();
      } catch (Exception exception) {
         text = "unknown";
      }
   }
}
