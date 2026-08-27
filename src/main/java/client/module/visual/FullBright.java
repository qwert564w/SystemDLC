package client.module.visual;

import client.data.ChoiceOption;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.UnsafeFields;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class FullBright extends Module {
   private ChoiceSetting mode;
   private BooleanSetting dinamichnyy;
   private SliderSetting minGammy;
   private SliderSetting gamma;
   private Double value235;
   private double value236;
   private double value237;
   private long time;
   private boolean flag;
   private final Mutable mutable;

   public FullBright() {
      super("FullBright", Category.VISUAL);
      ChoiceSetting choicesetting = new ChoiceSetting("", "", new ChoiceOption("Эффект"), new ChoiceOption("Гамма"), true);
      choicesetting.setName("Режим");
      choicesetting.setDescription("Способ подсветки");
      this.mode = choicesetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Динамичный");
      booleansetting.setDescription("Подстраивать гамму под текущий свет: макс. в темноте, минимум на свету");
      this.dinamichnyy = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 15.0, 0.1);
      slidersetting.setName("Минимум гаммы");
      slidersetting.setDescription("Гамма при полном освещении (потолок снизу)");
      this.minGammy = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 15.0, 1.0, 20.0, 0.5);
      slidersetting1.setName("Гамма");
      slidersetting1.setDescription("Уровень гаммы (потолок в динамическом режиме)");
      this.gamma = slidersetting1;
      this.value235 = null;
      this.value236 = 0.5;
      this.value237 = 15.0;
      this.time = 0L;
      this.flag = false;
      this.mutable = new Mutable();
      this.dinamichnyy.setVisibleWhen(this.mode::isFlag3);
      this.gamma.setVisibleWhen(this.mode::isFlag3);
      this.gamma.setVisibleWhen(this::getBoolean);
      this.minGammy.setVisibleWhen(this::getBoolean2);
      this.addSettings(new Setting[]{this.mode, this.dinamichnyy, this.minGammy, this.gamma});
   }

   @Override
   public void onTick() {
      if (this.inGame()) {
         if (!this.mode.isFlag3()) {
            this.player().addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
         } else {
            this.player().removeStatusEffect(StatusEffects.NIGHT_VISION);
         }
      }
   }

   private void update11() {
      if (this.value235 != null && this.options() != null) {
         onSimpleOptionDouble(this.options().getGamma(), this.value235);
         this.value235 = null;
      }
   }

   private Boolean getBoolean() {
      return !this.dinamichnyy.isFlag3();
   }

   @Override
   public void onDisable() {
      if (this.inGame()) {
         this.player().removeStatusEffect(StatusEffects.NIGHT_VISION);
      }

      this.update11();
      this.value236 = 0.5;
      this.value237 = 15.0;
      this.time = 0L;
      this.flag = false;
   }

   private void update12() {
      if (this.options() != null) {
         SimpleOption simpleoption = this.options().getGamma();
         if (this.value235 == null) {
            this.value235 = (Double)simpleoption.getValue();
         }

         double d0 = this.dinamichnyy.isFlag3() ? this.getDouble() : this.gamma.getValue();
         if (Math.abs((Double)simpleoption.getValue() - d0) > 1.0E-4) {
            onSimpleOptionDouble(simpleoption, d0);
         }
      }
   }

   private static void onSimpleOptionDouble(SimpleOption simpleOption, double value) {
      new UnsafeFields(simpleOption, SimpleOption.class, Object.class, 1).onObject(value);
   }

   private Boolean getBoolean2() {
      return this.mode.isFlag3() && this.dinamichnyy.isFlag3();
   }

   public boolean check3() {
      return this.isEnabled() && this.mode.isFlag3() && this.dinamichnyy.isFlag3();
   }

   private double getDouble() {
      double d0 = this.gamma.getValue();
      double d1 = Math.min(this.minGammy.getValue(), d0);
      if (!this.inGame()) {
         return this.value236;
      } else {
         PlayerEntity playerentity = this.player();
         Mutable mutablex = this.mutable
            .set((int)Math.floor(playerentity.getX()), (int)Math.floor(playerentity.getEyeY()), (int)Math.floor(playerentity.getZ()));
         World world = this.world();
         int i = world.getLightLevel(LightType.BLOCK, mutablex);
         int j = world.getLightLevel(LightType.SKY, mutablex);
         double d2 = Math.max(i, j);
         long k = System.currentTimeMillis();
         double d3 = this.time == 0L ? 0.0 : k - this.time;
         this.time = k;
         if (!this.flag) {
            this.value237 = d2;
            double d8 = 1.0 - this.value237 / 15.0;
            this.value236 = d1 + (d0 - d1) * (d8 * d8);
            this.flag = true;
            return this.value236;
         } else {
            double d4 = 1.0 - Math.pow(0.5, d3 / 120.0);
            this.value237 = this.value237 + (d2 - this.value237) * d4;
            double d5 = 1.0 - this.value237 / 15.0;
            double d6 = d1 + (d0 - d1) * (d5 * d5);
            double d7 = 1.0 - Math.pow(0.5, d3 / 180.0);
            this.value236 = this.value236 + (d6 - this.value236) * d7;
            return this.value236;
         }
      }
   }

   @Override
   public void update7() {
      if (this.mode.isFlag3()) {
         this.update12();
      }
   }

   @Override
   public void onEnable() {
      this.value236 = 0.5;
      this.value237 = 15.0;
      this.time = 0L;
      this.flag = false;
      if (this.mode.isFlag3()) {
         this.update12();
      }
   }
}
