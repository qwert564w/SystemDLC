package client.module.render;

import client.module.Category;
import client.module.Module;

public class PlayerChams extends Module {
   private static PlayerChams INSTANCE;

   public PlayerChams() {
      super("PlayerChams", Category.RENDER);
      INSTANCE = this;
   }

   @Override
   public void onDisable() {
   }

   public static PlayerChams getInstance() {
      return INSTANCE;
   }

   public boolean check3() {
      return this.isEnabled();
   }

   @Override
   public void onEnable() {
   }
}
