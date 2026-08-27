package client.module.visual;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.Setting;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.math.MathHelper;

public class FreeLook extends Module {
   private HotkeySetting key;
   private BooleanSetting autoF5;
   private boolean flag;
   private float value235;
   private float value236;
   private float value237;
   private float value238;
   private boolean flag2;
   private Perspective perspective;

   public FreeLook() {
      super("FreeLook", Category.VISUAL);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 342);
      hotkeysetting.setName("Клавиша");
      hotkeysetting.setDescription("Зажмите для свободного осмотра");
      this.key = hotkeysetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Авто F5");
      booleansetting.setDescription("Автоматически переключать в 3-e лицо во время Фрилука");
      this.autoF5 = booleansetting;
      this.flag = false;
      this.value235 = 0.0F;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.value238 = 0.0F;
      this.flag2 = false;
      this.perspective = Perspective.FIRST_PERSON;
      this.addSettings(new Setting[]{this.key, this.autoF5});
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (this.flag) {
         animatedFloat.setValue2(this.value235);
         animatedFloat.setValue(this.value236);
      }
   }

   public float getValue238() {
      return this.value238;
   }

   private void update11() {
      if (this.flag2 && this.options() != null) {
         this.options().setPerspective(this.perspective);
      }

      this.update12();
   }

   public boolean isFlag() {
      return this.flag;
   }

   private void update12() {
      this.flag = false;
      this.flag2 = false;
      this.value235 = 0.0F;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.value238 = 0.0F;
      this.perspective = Perspective.FIRST_PERSON;
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private void update13() {
      if (this.player() != null && this.options() != null) {
         this.flag = true;
         this.value235 = this.player().getYaw();
         this.value236 = this.player().getPitch();
         this.value237 = this.value235;
         this.value238 = this.value236;
         this.perspective = this.options().getPerspective();
         this.flag2 = false;
         if (this.autoF5.isFlag3() && this.perspective.isFirstPerson()) {
            this.options().setPerspective(Perspective.THIRD_PERSON_BACK);
            this.flag2 = true;
         }
      }
   }

   public float getValue237() {
      return this.value237;
   }

   public boolean check3() {
      return this.isEnabled() && this.flag;
   }

   @Override
   public void update7() {
      if (!this.notInGame()) {
         if (this.client().currentScreen != null) {
            if (this.flag) {
               this.update11();
            }
         } else if (!this.key.check()) {
            if (this.flag) {
               this.update11();
            }
         } else {
            if (!this.flag) {
               this.update13();
            }

            if (this.flag) {
               float f = this.player().getYaw();
               float f1 = this.player().getPitch();
               float f2 = MathHelper.wrapDegrees(f - this.value235);
               float f3 = f1 - this.value236;
               this.value237 = MathHelper.wrapDegrees(this.value237 + f2);
               this.value238 = MathHelper.clamp(this.value238 + f3, -90.0F, 90.0F);
               this.player().setYaw(this.value235);
               this.player().setPitch(this.value236);
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.update12();
   }
}
