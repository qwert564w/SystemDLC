package client.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;

public class InteractEvent {
   private final PlayerEntity playerEntity;
   private final Hand hand;
   private final HitResult hitResult;
   private boolean flag;

   public InteractEvent(PlayerEntity playerEntity2, Hand hand2, HitResult hitResult2) {
      this.playerEntity = playerEntity2;
      this.hand = hand2;
      this.hitResult = hitResult2;
      this.flag = false;
   }

   public HitResult getHitResult() {
      return this.hitResult;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public Hand getHand() {
      return this.hand;
   }

   public PlayerEntity getPlayerEntity() {
      return this.playerEntity;
   }
}
