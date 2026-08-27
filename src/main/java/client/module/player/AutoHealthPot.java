package client.module.player;

import client.enums.HealPotMode;
import client.module.Category;
import client.module.Module;
import client.setting.HotkeySetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.RandomUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class AutoHealthPot extends Module {
   private HotkeySetting knopkaZelyaZdorovya;
   private SliderSetting speedNavodki;
   private int value235;
   private boolean flag;
   private HealPotMode healPotMode;
   private int value236;
   private int value237;
   private float value238;
   private float value239;
   private int value240;
   private ItemStack itemStack;
   private int value241;
   private float value242;
   private float value243;
   private long time;
   private boolean flag2;
   private float value244;
   private float value245;
   private int value246;
   private ItemStack itemStack2;
   private int value247;
   private boolean flag3;

   public AutoHealthPot() {
      super("AutoHealthPot", Category.PLAYER);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 72, this::update16);
      hotkeysetting.setName("Кнопка зелья здоровья");
      hotkeysetting.setDescription("Кнопка для использования лечебного зелья");
      this.knopkaZelyaZdorovya = hotkeysetting;
      SliderSetting slidersetting = new SliderSetting("", "", 15.0, 10.0, 30.0, 0.5);
      slidersetting.setName("Скорость наводки");
      slidersetting.setDescription("Скорость поворота камеры");
      this.speedNavodki = slidersetting;
      this.value235 = 8;
      this.flag = true;
      this.healPotMode = HealPotMode.NONE;
      this.value236 = 0;
      this.value237 = 0;
      this.value238 = 0.0F;
      this.value239 = 0.0F;
      this.value240 = -1;
      this.itemStack = ItemStack.EMPTY;
      this.value241 = -1;
      this.value242 = 0.0F;
      this.value243 = 0.0F;
      this.time = 0L;
      this.flag2 = false;
      this.value244 = 0.0F;
      this.value245 = 0.0F;
      this.value246 = -1;
      this.itemStack2 = ItemStack.EMPTY;
      this.value247 = -1;
      this.flag3 = false;
      this.addSettings(new Setting[]{this.speedNavodki, this.knopkaZelyaZdorovya});
   }

   private boolean check3() {
      float f = this.player().getYaw();
      float f1 = this.player().getPitch();
      double d0 = RandomUtil.getDoubleByDouble(this.value242 - f);
      double d1 = this.value243 - f1;
      return Math.sqrt(d0 * d0 + d1 * d1) < 1.5;
   }

   private void update11() {
      this.flag2 = false;
      this.itemStack2 = ItemStack.EMPTY;
   }

   private void update12() {
      if (this.flag2) {
         float f = this.player().getYaw();
         float f1 = this.player().getPitch();
         double d0 = RandomUtil.getDoubleByDouble(this.value244 - f);
         double d1 = this.value245 - f1;
         if (!(Math.sqrt(d0 * d0 + d1 * d1) >= 1.5)) {
            if (this.value246 >= 0) {
               this.inventory().selectedSlot = this.value246;
            }

            if (this.flag3 && !this.itemStack2.isEmpty()) {
               this.inventory().setStack(this.value247, this.itemStack2);
            }

            this.update11();
         }
      }
   }

   private void setBoolean(boolean flag) {
      this.flag2 = true;
      this.value244 = this.value239;
      this.value245 = this.value238;
      this.value246 = this.value240;
      this.itemStack2 = this.itemStack;
      this.value247 = this.value235;
      this.flag3 = flag;
      this.value242 = this.value239;
      this.value243 = this.value238;
   }

   @Override
   public void onDisable() {
      this.update14();
   }

   private void update13() {
      switch (this.value236) {
         case 1:
            if (!this.check3()) {
               return;
            }

            this.inventory().selectedSlot = this.value241;
            this.setInt(0);
            break;
         case 2:
            this.interactItem(this.mainHand());
            this.healPotMode = HealPotMode.NONE;
            this.value236 = 0;
            this.value237 = 0;
            this.setBoolean(false);
      }
   }

   private void onDouble(double value) {
      float f = this.player().getYaw();
      float f1 = this.player().getPitch();
      double d0 = RandomUtil.getDoubleByDouble(this.value242 - f);
      double d1 = this.value243 - f1;
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      if (!(d2 < 0.05)) {
         double d3 = this.speedNavodki.getValue();
         double d4 = MathHelper.clamp(d3 * 0.04 * value, 0.01, 0.95);
         double d5 = d0 * d4;
         double d6 = d1 * d4;
         double d7 = RandomUtil.getDouble();
         d5 = RandomUtil.getDoubleByDoubleDouble(d7, d5);
         d6 = RandomUtil.getDoubleByDoubleDouble(d7, d6);
         if (d5 == 0.0 && Math.abs(d0) > d7) {
            d5 = Math.signum(d0) * d7;
         }

         if (d6 == 0.0 && Math.abs(d1) > d7) {
            d6 = Math.signum(d1) * d7;
         }

         float f2 = f + (float)d5;
         float f3 = MathHelper.clamp(f1 + (float)d6, -90.0F, 90.0F);
         this.player().setYaw(f2);
         this.player().setPitch(f3);
      }
   }

   private int getInt() {
      int i = -1;
      int j = 0;

      for (int k = 0; k < this.inventory().size(); k++) {
         ItemStack itemstack = this.inventory().getStack(k);
         if (itemstack.getItem() == Items.SPLASH_POTION) {
            int l = this.getIntByItemStack(itemstack);
            if (l > 0) {
               if (l == 2) {
                  return k;
               }

               if (l > j) {
                  j = l;
                  i = k;
               }
            }
         }
      }

      return i;
   }

   private int getIntByItemStack(ItemStack itemStack) {
      String s = itemStack.getName().getString().toLowerCase();
      if (!s.contains("healing") && !s.contains("исцеления") && !s.contains("лечения")) {
         return 0;
      } else {
         return !s.contains("ii") && !s.contains("2") && !s.contains("ии") ? 1 : 2;
      }
   }

   private void setInt(int count) {
      this.value236++;
      this.value237 = count;
   }

   private boolean check4() {
      return this.healPotMode != HealPotMode.NONE;
   }

   private void update14() {
      this.healPotMode = HealPotMode.NONE;
      this.value236 = 0;
      this.value237 = 0;
      this.value238 = 0.0F;
      this.value239 = 0.0F;
      this.value240 = -1;
      this.itemStack = ItemStack.EMPTY;
      this.value241 = -1;
      this.value242 = 0.0F;
      this.value243 = 0.0F;
      this.time = 0L;
      this.flag2 = false;
      this.value244 = 0.0F;
      this.value245 = 0.0F;
      this.value246 = -1;
      this.itemStack2 = ItemStack.EMPTY;
      this.value247 = -1;
      this.flag3 = false;
   }

   @Override
   public void update7() {
      if (!this.notInGame()) {
         if (this.healPotMode != HealPotMode.NONE || this.flag2) {
            long i = System.nanoTime();
            double d0 = this.time > 0L ? (i - this.time) / 1.66666667E7 : 1.0;
            d0 = MathHelper.clamp(d0, 0.05, 3.0);
            this.time = i;
            this.onDouble(d0);
         }
      }
   }

   private void update15() {
      switch (this.value236) {
         case 1:
            this.swapSlots(this.value241, this.value235);
            this.setInt(1);
            break;
         case 2:
            if (!this.check3()) {
               return;
            }

            this.inventory().selectedSlot = this.value235;
            this.setInt(0);
            break;
         case 3:
            this.interactItem(this.mainHand());
            this.healPotMode = HealPotMode.NONE;
            this.value236 = 0;
            this.value237 = 0;
            this.setBoolean(this.flag);
      }
   }

   @Override
   public void onEnable() {
      this.update14();
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (this.healPotMode == HealPotMode.NONE) {
            this.update12();
         } else if (this.value237 > 0) {
            this.value237--;
         } else {
            switch (this.healPotMode) {
               case USE_FROM_HOTBAR:
                  this.update13();
                  break;
               case USE_FROM_INVENTORY:
                  this.update15();
            }
         }
      }
   }

   private void update16() {
      if (!this.check4() && !this.notInGame()) {
         int i = this.getInt();
         if (i != -1) {
            PlayerEntity playerentity = this.player();
            if (this.flag2) {
               this.value238 = this.value245;
               this.value239 = this.value244;
               this.value240 = this.value246;
               this.itemStack = this.itemStack2;
               this.update11();
            } else {
               this.value238 = playerentity.getPitch();
               this.value239 = playerentity.getYaw();
               this.value240 = this.inventory().selectedSlot;
               this.itemStack = ItemStack.EMPTY;
            }

            this.value241 = i;
            this.value236 = 1;
            this.value237 = 0;
            this.value242 = this.value239;
            this.value243 = 85.0F;
            this.time = System.nanoTime();
            if (i < 9) {
               this.healPotMode = HealPotMode.USE_FROM_HOTBAR;
            } else {
               if (this.flag && this.itemStack.isEmpty()) {
                  this.itemStack = this.inventory().getStack(this.value235).copy();
               }

               this.healPotMode = HealPotMode.USE_FROM_INVENTORY;
            }
         }
      }
   }
}
