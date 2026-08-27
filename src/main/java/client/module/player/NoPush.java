package client.module.player;

import client.module.Category;
import client.module.Module;

public class NoPush extends Module {
   public NoPush() {
      super("NoPush", Category.PLAYER);
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
   }
}
