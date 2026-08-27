package client.util;

import client.enums.ServerMode;
import client.module.Feature;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.world.World;

public class HealthTracker {
   private static final Map<UUID, Float> map = new HashMap<>();
   private static long time = 0L;

   private HealthTracker() {
   }

   public static void update() {
      map.clear();
      time = 0L;
   }

   public static String getStringByFloat(float value) {
      return isFloat(value) ? "?" : Integer.toString(Math.round(value));
   }

   private static float getFloatByPlayerEntity(PlayerEntity playerEntity) {
      try {
         World world = playerEntity.getWorld();
         if (world == null) {
            return -1.0F;
         }

         Scoreboard scoreboard = world.getScoreboard();
         if (scoreboard == null) {
            return -1.0F;
         }

         ScoreboardObjective scoreboardobjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME);
         if (scoreboardobjective == null) {
            return -1.0F;
         }

         ReadableScoreboardScore readablescoreboardscore = scoreboard.getScore(playerEntity, scoreboardobjective);
         if (readablescoreboardscore != null) {
            return readablescoreboardscore.getScore();
         }
      } catch (Exception exception) {
      }

      return -1.0F;
   }

   public static float getFloatByFloatFloat(float value, float value2) {
      return !isFloat(value2) && !(value <= 0.0F) ? Math.clamp(value2 / value, 0.0F, 1.0F) : 1.0F;
   }

   public static String getStringByFloatFloat(float value, float value2) {
      if (isFloat(value)) {
         return "?";
      } else {
         int i = Math.round(value / 2.0F);
         int j = Math.round(value2 / 2.0F);
         return i + "/" + j;
      }
   }

   private static void update2() {
      long i = System.currentTimeMillis();
      if (i - time >= 100L) {
         time = i;
         ClientWorld clientworld = Feature.mc.world;
         if (clientworld != null) {
            Scoreboard scoreboard = clientworld.getScoreboard();
            if (scoreboard != null) {
               ScoreboardObjective scoreboardobjective = null;

               for (ScoreboardObjective scoreboardobjective1 : scoreboard.getObjectives()) {
                  if (scoreboardobjective1 != null) {
                     String s = scoreboardobjective1.getName().toLowerCase();
                     String s1 = scoreboardobjective1.getDisplayName().getString().toLowerCase();
                     if (s.contains("health") || s.contains("hp") || s.contains("heart") || s1.contains("♥") || s1.contains("❤") || s1.contains("health")) {
                        scoreboardobjective = scoreboardobjective1;
                        break;
                     }
                  }
               }

               if (scoreboardobjective == null) {
                  scoreboardobjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST);
               }

               if (scoreboardobjective != null) {
                  for (PlayerEntity playerentity : clientworld.getPlayers()) {
                     ReadableScoreboardScore readablescoreboardscore = scoreboard.getScore(playerentity, scoreboardobjective);
                     if (readablescoreboardscore != null) {
                        map.put(playerentity.getUuid(), (float)readablescoreboardscore.getScore());
                     }
                  }
               }
            }
         }
      }
   }

   public static float getFloatByServerModePlayerEntity(ServerMode serverMode, PlayerEntity playerEntity) {
      float f = playerEntity.getHealth();
      float f1 = playerEntity.getAbsorptionAmount();
      if (serverMode == ServerMode.FUNTIME) {
         float f2 = getFloatByPlayerEntity(playerEntity);
         if (f2 > 0.0F) {
            return f2;
         }
      } else if (serverMode == ServerMode.MINEBLAZE) {
         update2();
         Float f3 = map.get(playerEntity.getUuid());
         if (f3 != null && f3 > 0.0F) {
            return f3 + f1;
         }
      }

      return f + f1;
   }

   public static float getFloatByPlayerEntityServerMode(PlayerEntity playerEntity, ServerMode serverMode) {
      float f = playerEntity.getMaxHealth();
      float f1 = playerEntity.getAbsorptionAmount();
      if (serverMode == ServerMode.OFF) {
         return f + f1;
      } else {
         return serverMode == ServerMode.FUNTIME ? Math.max(f, 20.0F) : Math.max(f, 20.0F) + f1;
      }
   }

   public static boolean isFloat(float value) {
      return value > 100.0F;
   }

   public static boolean isPlayerEntity(PlayerEntity playerEntity) {
      return getFloatByPlayerEntity(playerEntity) > 0.0F;
   }

   public static String getStringByFloat2(float value) {
      return isFloat(value) ? "?" : Math.round(value) + "HP";
   }

   public static ServerMode getServerModeByString(String text) {
      if ("FT".equals(text)) {
         return ServerMode.FUNTIME;
      } else {
         return "MB".equals(text) ? ServerMode.MINEBLAZE : ServerMode.OFF;
      }
   }
}
