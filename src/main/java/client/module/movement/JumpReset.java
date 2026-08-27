package client.module.movement;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.RandomUtil;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;

public class JumpReset extends Module {
   private final BooleanSetting chance;
   private final SliderSetting procent;
   private int value235;

   public JumpReset() {
      super("JumpReset", Category.MOVEMENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Шанс");
      booleansetting.setDescription("Включить случайный шанс прыжка");
      this.chance = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 80.0, 0.0, 100.0, 0.0);
      slidersetting.setName("Процент");
      slidersetting.setDescription("Вероятность прыжка");
      this.procent = slidersetting;
      this.value235 = 0;
      this.addSettings(new Setting[]{this.chance, this.procent});
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (!this.notInGame()) {
         PlayerEntity playerentity = this.player();
         if (playerentity != null) {
            int i = playerentity.hurtTime;
            boolean flag = i > this.value235;
            this.value235 = i;
            if (flag) {
               if (playerentity.getAttacker() instanceof PlayerEntity) {
                  if (playerentity.isOnGround()) {
                     if (!(this.currentScreen() instanceof HandledScreen)) {
                        if (!playerentity.isTouchingWater() && !playerentity.isInsideWall()) {
                           if (!this.chance.isFlag3() || RandomUtil.isIntIntDouble(0, 100, this.procent.getValue())) {
                              playerentity.jump();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.value235 = 0;
   }

   @Override
   public void onEnable() {
      this.value235 = 0;
   }
}
