package client.module.combat;

import client.module.Category;
import client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ShiftTap extends Module {
   public long time = 0L;
   public boolean flag = false;

   public ShiftTap() {
      super("ShiftTap", Category.COMBAT);
   }

   private void update11() {
      if (this.flag) {
         this.options().sneakKey.setPressed(false);
         this.flag = false;
      }
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (!this.notInGame()) {
         this.update12();
      }
   }

   private void update12() {
      this.time = System.currentTimeMillis() + 25L;
      if (!this.flag) {
         this.options().sneakKey.setPressed(true);
         this.flag = true;
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void update8() {
      if (this.player() != null && !this.player().isSpectator()) {
         long i = System.currentTimeMillis();
         if (this.flag && i > this.time) {
            this.update11();
         }
      } else {
         this.update11();
      }
   }
}
