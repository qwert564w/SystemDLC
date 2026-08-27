package client.module.movement;

import client.module.Category;
import client.module.Module;
import client.util.UnsafeFields;
import net.minecraft.entity.LivingEntity;

public class NoJumpDelay extends Module {
   private final UnsafeFields<Integer> unsafeFields = new UnsafeFields<>(this.player(), LivingEntity.class, 91);

   public NoJumpDelay() {
      super("NoJumpDelay", Category.MOVEMENT);
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         this.unsafeFields.onObjectInt(this.player(), 0);
      }
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
   }
}
