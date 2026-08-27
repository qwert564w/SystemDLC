package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.SliderSetting;
import client.util.UnsafeFields;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

public class FastUse extends Module {
   private UnsafeFields<Integer> unsafeFields;
   private SliderSetting delay;
   private BooleanSetting onlyOpyt;

   public FastUse() {
      super("FastUse", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 0.0, 4.0, 1.0);
      slidersetting.setName("Задержка");
      slidersetting.setDescription("Задержка между использованиями (тики)");
      this.delay = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только опыт");
      booleansetting.setDescription("Работает только c пузырьками опыта");
      this.onlyOpyt = booleansetting;
      this.addSetting(this.delay);
      this.addSetting(this.onlyOpyt);
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.unsafeFields != null) {
         if (!this.onlyOpyt.isFlag3() || this.player().getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE) {
            int i = this.delay.getInt2();
            if (this.unsafeFields.getInt() > i) {
               this.unsafeFields.onInt(i);
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.unsafeFields = null;
   }

   @Override
   public void onEnable() {
      this.unsafeFields = new UnsafeFields<>(this.client(), MinecraftClient.class, 90);
   }
}
