package client.module.movement;

import client.module.Category;
import client.module.CategoryModule;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import net.minecraft.entity.player.PlayerEntity;

public class LagRange extends CategoryModule {
   private final SliderSetting range;
   private boolean flag2;
   private long time3;

   public LagRange() {
      super("LagRange", Category.MOVEMENT);
      SliderSetting slidersetting = new SliderSetting("", "", 10.0, 1.0, 30.0, 0.5, StringParts.join(new String[]{"м"}), 1);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Дистанция до ближайшего игрока для активации");
      this.range = slidersetting;
      this.flag2 = false;
      this.time3 = 0L;
      this.addSettings(new Setting[]{this.delay, this.range, this.pokazatServernuyuPoziciyu});
   }

   @Override
   protected boolean check3() {
      if (this.notInGame()) {
         return false;
      } else {
         double d0 = this.range.getValue() * this.range.getValue();
         boolean flag = false;

         for (PlayerEntity playerentity : this.clientWorld().getPlayers()) {
            if (playerentity != this.player() && this.player().squaredDistanceTo(playerentity) <= d0) {
               flag = true;
               break;
            }
         }

         long i = System.currentTimeMillis();
         if (flag != this.flag2) {
            if (this.time3 == 0L) {
               this.time3 = i;
            }

            if (i - this.time3 >= 150L) {
               this.flag2 = flag;
               this.time3 = 0L;
               if (!flag) {
                  return false;
               }
            }

            return this.flag2;
         } else {
            this.time3 = 0L;
            return flag;
         }
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.flag2 = false;
      this.time3 = 0L;
   }
}
