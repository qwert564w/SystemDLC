package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.KeyBindings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class CrystalTap extends Module {
   public BooleanSetting avtPlaceKristal;
   public SliderSetting delayStavki;
   public SliderSetting delayAtaki;
   public int value235;
   public int value236;

   public CrystalTap() {
      super("CrystalTap", Category.COMBAT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Авт. ставить кристал");
      booleansetting.setDescription("Автоматически ставить кристалл на обсидиан");
      this.avtPlaceKristal = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 0.0, 5.0, 1.0);
      slidersetting.setName("Задержка ставки");
      slidersetting.setDescription("Задержка между постановкой кристалла (тики)");
      this.delayStavki = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 0.0, 0.0, 5.0, 1.0);
      slidersetting1.setName("Задержка атаки");
      slidersetting1.setDescription("Задержка между атаками кристалла (тики)");
      this.delayAtaki = slidersetting1;
      this.addSettings(new Setting[]{this.avtPlaceKristal, this.delayStavki, this.delayAtaki});
   }

   @Override
   public void onDisable() {
      KeyBindings.update2();
      KeyBindings.update5();
   }

   private void update11() {
      if (this.client().crosshairTarget instanceof BlockHitResult blockhitresult
         && this.world().getBlockState(blockhitresult.getBlockPos()).getBlock() == Blocks.OBSIDIAN) {
         for (Entity entity : this.clientWorld().getEntities()) {
            if (entity instanceof EndCrystalEntity endcrystalentity && endcrystalentity.getBlockPos().down().equals(blockhitresult.getBlockPos())) {
               return;
            }
         }

         if (this.player().getMainHandStack().getItem() != Items.END_CRYSTAL) {
            return;
         }

         if (this.value236 >= this.delayStavki.getValueAsFloat()) {
            KeyBindings.update3();
            this.value236 = 0;
         } else {
            this.value236++;
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void update8() {
      if (this.avtPlaceKristal.isFlag3()) {
         this.update11();
      }

      if (this.client().crosshairTarget instanceof EntityHitResult entityhitresult && entityhitresult.getEntity() instanceof EndCrystalEntity endcrystalentity) {
         if (endcrystalentity.isRemoved() || !endcrystalentity.isAlive()) {
            return;
         }

         if (this.value235 >= this.delayAtaki.getValueAsFloat()) {
            KeyBindings.update6();
            this.value235 = 0;
         } else {
            this.value235++;
         }
      }
   }
}
