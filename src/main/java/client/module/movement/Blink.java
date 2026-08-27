package client.module.movement;

import client.module.Category;
import client.module.CategoryModule;
import client.setting.Setting;

public class Blink extends CategoryModule {
   public Blink() {
      super("Blink", Category.MOVEMENT);
      this.addSettings(new Setting[]{this.pokazatServernuyuPoziciyu});
   }

   @Override
   public void onTick() {
   }

   @Override
   protected boolean check3() {
      return true;
   }
}
