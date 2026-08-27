package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.ChatSpamGuard;
import client.util.PvpStateParser;
import client.util.StringParts;
import client.util.UnsafeFields;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

public class SafeLeave extends Module {
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static final String[] stringArray = new String[]{"pvp", "бой", "combat", "арена", "arena", "дуэль", "duel"};
   private static final Set<String> set = Set.of("hub", "an", "lobby", "leave", "spawn", "quit");
   private static final Pattern pattern = Pattern.compile("\\d+$");
   private final SliderSetting taymautChata;
   private long time;
   private String text;
   private long time2;
   private UnsafeFields<Map<UUID, ClientBossBar>> unsafeFields;

   public SafeLeave() {
      super("SafeLeave", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 10.0, 1.0, 60.0, 1.0, StringParts.join(new String[]{" ", "c", "е", "к"}), 0);
      slidersetting.setName("Таймаут чата");
      slidersetting.setDescription("Сколько держать статус после сообщения в чате");
      this.taymautChata = slidersetting;
      this.time = 0L;
      this.text = null;
      this.time2 = 0L;
      this.addSettings(new Setting[]{this.taymautChata});
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         boolean flagx = this.check3();
         boolean flag1 = this.check4();
         long i = System.currentTimeMillis() - this.time;
         boolean flag2x = this.time > 0L && i < (long)(this.taymautChata.getValue() * 1000.0);
         boolean flag3 = PvpStateParser.getLong() > 0L;
         boolean flag4 = flag;
         flag = flagx || flag1 || flag2x || flag3;
         if (flag4 && !flag) {
            flag2 = false;
            this.text = null;
         }

         boolean flag5 = this.currentScreen() instanceof GameMenuScreen;
         if (!flag5) {
            flag2 = false;
         }
      }
   }

   private boolean check3() {
      if (this.notInGame()) {
         return false;
      } else {
         Scoreboard scoreboard = this.world().getScoreboard();
         if (scoreboard == null) {
            return false;
         } else {
            ScoreboardObjective scoreboardobjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
            if (scoreboardobjective == null) {
               return false;
            } else if (this.isString(scoreboardobjective.getDisplayName().getString())) {
               return true;
            } else {
               for (ScoreboardEntry scoreboardentry : scoreboard.getScoreboardEntries(scoreboardobjective)) {
                  if (!scoreboardentry.hidden()) {
                     Text textx = scoreboardentry.display();
                     if (textx != null && this.isString(textx.getString())) {
                        return true;
                     }

                     Text text1 = scoreboardentry.name();
                     if (text1 != null && this.isString(text1.getString())) {
                        return true;
                     }

                     String s = scoreboardentry.owner();
                     if (this.isString(s)) {
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   private static String getString() {
      long i = PvpStateParser.getLong();
      return i <= 0L ? "" : " (осталось " + Math.max(1L, (i + 999L) / 1000L) + "с)";
   }

   public static void setFlag2(boolean flag) {
      flag2 = flag;
   }

   @Override
   public void onDisable() {
      flag = false;
      flag2 = false;
      this.time = 0L;
      this.text = null;
      this.time2 = 0L;
   }

   public static boolean isFlag2() {
      return flag2;
   }

   @Override
   public void update3() {
      PvpStateParser.update();
      flag = false;
      flag2 = false;
      this.time = 0L;
      this.text = null;
   }

   private boolean isString(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         String s = text2.toLowerCase();

         for (String s1 : stringArray) {
            if (s.contains(s1)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void onString2(String text2) {
      if (this.player() != null) {
         ChatSpamGuard.setFlag();
         this.player().sendMessage(Text.literal(text2), false);
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text textx = gamemessages2cpacket1.content();
            gamemessages2cpacket1 = gamemessages2cpacket;

            try {
               gamemessages2cpacket1.overlay();
            } catch (Throwable throwable) {
               throw new MatchException(throwable.toString(), throwable);
            }

            if (true) {
               String s2 = textx.getString();
               if (this.isString(s2)) {
                  this.time = System.currentTimeMillis();
               }
            }
         }
      } else if (flag) {
         if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
            if (packetEvent.getPacket() instanceof CommandExecutionC2SPacket commandexecutionc2spacket) {
               CommandExecutionC2SPacket commandexecutionc2spacket1 = commandexecutionc2spacket;

               String s = commandexecutionc2spacket1.command();
               String s1 = s.toLowerCase().split(" ", 2)[0];
               s = pattern.matcher(s1).replaceAll("");
               if (!set.contains(s1) && !set.contains(s)) {
                  return;
               }

               long i = System.currentTimeMillis();
               if (s1.equals(this.text) && i - this.time2 < 5000L) {
                  this.text = null;
                  return;
               }

               packetEvent.setFlag(true);
               this.text = s1;
               this.time2 = i;
               this.onString2("§c[SafeLeave] §fBы в PvP" + getString() + "! Введите /" + s1 + " ещё раз для подтверждения.");
            }
         }
      }
   }

   private boolean check4() {
      if (this.client() != null && this.client().inGameHud != null) {
         BossBarHud bossbarhud = this.client().inGameHud.getBossBarHud();
         if (bossbarhud == null) {
            return false;
         } else {
            if (this.unsafeFields == null) {
               this.unsafeFields = new UnsafeFields<>(bossbarhud, BossBarHud.class, Map.class);
            }

            Map map = (Map)this.unsafeFields.getObjectByObject(bossbarhud);
            if (map != null && !map.isEmpty()) {
               for (ClientBossBar clientbossbar : (Iterable<ClientBossBar>)(map.values())) {
                  Text textx = clientbossbar.getName();
                  if (textx != null && this.isString(textx.getString())) {
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

   @Override
   public void update4() {
      PvpStateParser.update();
      flag = false;
      flag2 = false;
      this.time = 0L;
      this.text = null;
   }

   @Override
   public void onEnable() {
      flag = false;
      flag2 = false;
      this.time = 0L;
      this.text = null;
      this.time2 = 0L;
   }
}
