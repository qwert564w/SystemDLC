package client.util;

import net.minecraft.entity.player.PlayerEntity;

public class AttackRecord {
   private static final long time = 5000L;
   private static PlayerEntity playerEntity;
   private static long time2;

   public static void setPlayerEntity(PlayerEntity playerEntity2) {
      playerEntity = playerEntity2;
      time2 = System.currentTimeMillis();
   }

   public static PlayerEntity getPlayerEntity() {
      if (playerEntity == null) {
         return null;
      } else if (!playerEntity.isAlive()) {
         playerEntity = null;
         return null;
      } else if (System.currentTimeMillis() - time2 > 5000L) {
         playerEntity = null;
         return null;
      } else {
         return playerEntity;
      }
   }
}
