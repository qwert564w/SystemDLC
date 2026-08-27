package client.util;

import client.enums.PacketDirection;
import client.module.Feature;
import client.network.PacketEvent;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;

public final class PvpStateParser {
   private static final Pattern pattern = Pattern.compile("(\\d+)\\s*сек");
   private static UnsafeFields<Map<UUID, ClientBossBar>> unsafeFields;
   private static boolean flag = false;
   private static long time = 0L;
   private static long time2 = 0L;

   private PvpStateParser() {
   }

   private static boolean check() {
      if (Feature.mc != null && Feature.mc.inGameHud != null) {
         BossBarHud bossbarhud = Feature.mc.inGameHud.getBossBarHud();
         if (bossbarhud == null) {
            return false;
         } else {
            if (unsafeFields == null) {
               try {
                  unsafeFields = new UnsafeFields<>(bossbarhud, BossBarHud.class, Map.class);
               } catch (Throwable throwable) {
                  return false;
               }
            }

            Map map = (Map)unsafeFields.getObjectByObject(bossbarhud);
            if (map != null && !map.isEmpty()) {
               for (ClientBossBar clientbossbar : (Iterable<ClientBossBar>)(map.values())) {
                  Text text = clientbossbar.getName();
                  if (text != null && text.getString().toLowerCase().contains("pvp")) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static void setLong(long time) {
      time2 = System.currentTimeMillis() + time;
   }

   public static void update() {
      time2 = 0L;
      flag = false;
      time = 0L;
   }

   public static void setString(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         String s = text2.toLowerCase();
         if (s.contains("вышли из режим")) {
            time2 = 0L;
         } else if (s.contains("вошли в режим pvp")) {
            setLong(30000L);
         } else if (s.contains("режим pvp") || s.contains("не выходите из игры")) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
               try {
                  setLong(Long.parseLong(matcher.group(1)) * 1000L);
                  return;
               } catch (NumberFormatException numberformatexception) {
               }
            }

            if (getLong() <= 0L) {
               setLong(30000L);
            }
         }
      }
   }

   public static boolean check2() {
      if (getLong() > 0L) {
         return true;
      } else {
         long i = System.currentTimeMillis();
         if (i - time < 100L) {
            return flag;
         } else {
            time = i;
            flag = check();
            return flag;
         }
      }
   }

   public static void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent != null && packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text text = gamemessages2cpacket1.content();
            gamemessages2cpacket1 = gamemessages2cpacket;

            try {
               gamemessages2cpacket1.overlay();
            } catch (Throwable throwable) {
               throw new MatchException(throwable.toString(), throwable);
            }

            if (true) {
               setString(text.getString());
            }
         }
      }
   }

   public static long getLong() {
      long i = time2;
      if (i == 0L) {
         return 0L;
      } else if (Feature.mc.player != null && !Feature.mc.player.isDead()) {
         long j = i - System.currentTimeMillis();
         if (j <= 0L) {
            time2 = 0L;
            return 0L;
         } else {
            return j;
         }
      } else {
         time2 = 0L;
         return 0L;
      }
   }
}
