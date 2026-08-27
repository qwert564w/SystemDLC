package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.CritChecks;
import client.util.KeyBindings;
import net.minecraft.util.hit.EntityHitResult;

public class TapMouse extends Module {
   private SliderSetting cooldown;
   private BooleanSetting pauzaVEkrane;
   private int value235;

   public TapMouse() {
      super("TapMouse", Category.COMBAT);
      SliderSetting slidersetting = new SliderSetting("", "", 15.0, 1.0, 100.0, 1.0);
      slidersetting.setName("Кулдаун");
      slidersetting.setDescription("Тиков между ударами");
      this.cooldown = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Пауза в экране");
      booleansetting.setDescription("Останавливать удары когда открыт экран");
      this.pauzaVEkrane = booleansetting;
      this.addSettings(new Setting[]{this.cooldown, this.pauzaVEkrane});
   }

   @Override
   public void onDisable() {
   }

   private void update11() {
      if (this.currentScreen() == null) {
         KeyBindings.update6();
      } else {
         if (this.client().crosshairTarget instanceof EntityHitResult entityhitresult) {
            CritChecks.onEntity(entityhitresult.getEntity());
         }
      }
   }

   @Override
   public void onEnable() {
      this.value235 = 0;
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (!this.pauzaVEkrane.isFlag3() || this.currentScreen() == null) {
            if (!this.interactionManager().isBreakingBlock()) {
               if (this.value235 > 0) {
                  this.value235--;
               } else {
                  this.update11();
                  this.value235 = this.cooldown.getInt2();
               }
            }
         }
      }
   }
}
