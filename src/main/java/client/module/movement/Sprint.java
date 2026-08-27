package client.module.movement;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.network.ClientPlayerEntity;

public class Sprint extends Module {
   public Sprint() {
      super("Sprint", Category.MOVEMENT);
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (this.options().forwardKey.isPressed()) {
         ClientPlayerEntity clientplayerentity = this.clientPlayer();
         this.options().sprintKey.setPressed(true);
         if (clientplayerentity.isTouchingWater()) {
            if (clientplayerentity.isSubmergedInWater()) {
               clientplayerentity.setSwimming(true);
            }
         } else {
            clientplayerentity.setSprinting(true);
         }
      }
   }

   @Override
   public void onDisable() {
      this.options().sprintKey.setPressed(false);
   }

   @Override
   public void onEnable() {
   }
}
