package client.module.combat;

import client.data.ChoiceOption;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.InventoryActions;
import client.util.ItemCooldowns;
import client.util.PearlMath;
import client.util.SneakState;
import client.util.SphereItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.world.World;

public class AutoTotem extends Module {
   private ChoiceSetting ruka;
   private BooleanSetting neSvapatOnEde;
   private SliderSetting minimalnoeHp;
   private BooleanSetting svapNazad;
   private BooleanSetting withoutFeyerverkov;
   private BooleanSetting prioritetObychnyh;
   private BooleanSetting neBratVKd;
   private BooleanSetting zolotyeSerdca;
   private BooleanSetting crystals;
   private BooleanSetting trezubec;
   private BooleanSetting tnt;
   private BooleanSetting lyubayaRange;
   private SliderSetting radiusTnt;
   private BooleanSetting padenie;
   private BooleanSetting elitra;
   private SliderSetting hpCElitroy;
   private boolean flag;
   private int value235;
   private int value236;
   private int value237;
   private int value238;
   private long time;

   public AutoTotem() {
      super("AutoTotem", Category.COMBAT);
      ChoiceSetting choicesetting = new ChoiceSetting("", "", new ChoiceOption("Левая"), new ChoiceOption("Правая"), false);
      choicesetting.setName("Рука");
      choicesetting.setDescription("В какую руку брать тотем");
      this.ruka = choicesetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Не свапать при еде");
      booleansetting.setDescription("Не свапать тотем пока игрок ест");
      this.neSvapatOnEde = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 5.0, 0.0, 20.0, 0.5);
      slidersetting.setName("Минимальное HP");
      slidersetting.setDescription("HP при котором возьмется тотем");
      this.minimalnoeHp = slidersetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Свап назад");
      booleansetting1.setDescription("Возвращать предмет обратно после тотема");
      this.svapNazad = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Без фейерверков");
      booleansetting2.setDescription("Не свапать если фейерверк в офхенде");
      this.withoutFeyerverkov = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Приоритет обычных");
      booleansetting3.setDescription("Сначала брать незачарённые тотемы, зачарённые — в последнюю очередь");
      this.prioritetObychnyh = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", true);
      booleansetting4.setName("Не брать в кд");
      booleansetting4.setDescription("Не брать тотем на серверном кулдауне — такой тотем не сработает при смерти");
      this.neBratVKd = booleansetting4;
      BooleanSetting booleansetting5 = new BooleanSetting("", "", true);
      booleansetting5.setName("Золотые сердца");
      booleansetting5.setDescription("Учитывать золотые сердца");
      this.zolotyeSerdca = booleansetting5;
      BooleanSetting booleansetting6 = new BooleanSetting("", "", true);
      booleansetting6.setName("Кристаллы");
      booleansetting6.setDescription("Брать тотем при кристалле рядом");
      this.crystals = booleansetting6;
      BooleanSetting booleansetting7 = new BooleanSetting("", "", true);
      booleansetting7.setName("Трезубец");
      booleansetting7.setDescription("Брать тотем при летящем в игрока трезубце (15 блоков)");
      this.trezubec = booleansetting7;
      BooleanSetting booleansetting8 = new BooleanSetting("", "", true);
      booleansetting8.setName("ТНТ");
      booleansetting8.setDescription("Брать тотем при активном ТНТ рядом");
      this.tnt = booleansetting8;
      BooleanSetting booleansetting9 = new BooleanSetting("", "", false);
      booleansetting9.setName("Любая дистанция");
      booleansetting9.setDescription("Игнорировать радиус поиска ТНТ");
      this.lyubayaRange = booleansetting9;
      SliderSetting slidersetting1 = new SliderSetting("", "", 6.0, 1.0, 16.0, 0.5);
      slidersetting1.setName("Радиус ТНТ");
      slidersetting1.setDescription("Радиус поиска активного ТНТ");
      this.radiusTnt = slidersetting1;
      BooleanSetting booleansetting10 = new BooleanSetting("", "", false);
      booleansetting10.setName("Падение");
      booleansetting10.setDescription("Брать тотем при падении");
      this.padenie = booleansetting10;
      BooleanSetting booleansetting11 = new BooleanSetting("", "", false);
      booleansetting11.setName("Элитра");
      booleansetting11.setDescription("Отдельное HP при полёте на элитре");
      this.elitra = booleansetting11;
      SliderSetting slidersetting2 = new SliderSetting("", "", 10.0, 0.0, 20.0, 0.5);
      slidersetting2.setName("HP c элитрой");
      slidersetting2.setDescription("HP при котором возьмется тотем c элитрой");
      this.hpCElitroy = slidersetting2;
      this.flag = false;
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.value238 = 0;
      this.time = 0L;
      this.hpCElitroy.setVisibleWhen(this.elitra::isFlag3);
      this.lyubayaRange.setVisibleWhen(this.tnt::isFlag3);
      this.radiusTnt.setVisibleWhen(this::getBoolean);
      this.withoutFeyerverkov.setVisibleWhen(this::getBoolean2);
      this.addSettings(
         new Setting[]{
            this.ruka,
            this.neSvapatOnEde,
            this.minimalnoeHp,
            this.svapNazad,
            this.withoutFeyerverkov,
            this.prioritetObychnyh,
            this.neBratVKd,
            this.zolotyeSerdca,
            this.crystals,
            this.trezubec,
            this.tnt,
            this.lyubayaRange,
            this.radiusTnt,
            this.padenie,
            this.elitra,
            this.hpCElitroy
         }
      );
   }

   private boolean check3() {
      ItemStack itemstack = this.player().getMainHandStack();
      ItemStack itemstack1 = this.player().getEquippedStack(EquipmentSlot.OFFHAND);
      return this.isItemStack3(itemstack) || this.isItemStack3(itemstack1);
   }

   @Override
   public void onTick() {
      SneakState.update4();
   }

   private void update11() {
      if (this.value237 != -1) {
         int i = this.value237;
         this.value237 = -1;
         SphereItems.onInt(i);
         this.time = System.currentTimeMillis();
      } else if (this.value235 != -1) {
         if (this.value236 != -1) {
            boolean flagx = false;
            int k = this.value236;
            int j = this.value235;
            this.onBooleanIntInt(flagx, j, k);
         } else {
            this.onIntBoolean(this.value235, false);
         }
      }
   }

   private boolean check4() {
      if (this.check9()) {
         return false;
      } else if (this.neSvapatOnEde.isFlag3() && this.player().isUsingItem()) {
         return false;
      } else {
         return this.check8() ? true : this.check6();
      }
   }

   private Boolean getBoolean() {
      return this.tnt.isFlag3() && !this.lyubayaRange.isFlag3();
   }

   private int getInt() {
      if (this.player() == null) {
         return -1;
      } else {
         for (int i = 0; i < 36; i++) {
            ItemStack itemstack = this.player().getInventory().getStack(i);
            if (SphereItems.isItemStack2(itemstack) && !this.isItemStack2(itemstack)) {
               return i;
            }
         }

         return -1;
      }
   }

   private void setInt(int count) {
      if (!SphereItems.isItemStack5(this.player().getEquippedStack(EquipmentSlot.OFFHAND))) {
         if (this.value235 == -1) {
            this.value235 = count;
         }
      }
   }

   private boolean isItemStack(ItemStack itemStack) {
      return !this.prioritetObychnyh.isFlag3() || itemStack.getEnchantments().isEmpty() || this.value238 <= 0;
   }

   private boolean check5() {
      if (this.check9()) {
         return true;
      } else {
         return this.check8() ? false : !this.check6();
      }
   }

   private void update12() {
      this.value238 = SphereItems.getIntByItemBoolean(Items.TOTEM_OF_UNDYING, true);
   }

   private boolean isItemStack2(ItemStack itemStack) {
      return this.neBratVKd.isFlag3() && ItemCooldowns.getDoubleByItemStack(itemStack) > 0.0;
   }

   private void setBoolean(boolean flag2) {
      if (!flag2) {
         this.value235 = -1;
         this.value236 = -1;
      }

      this.time = System.currentTimeMillis();
      this.flag = false;
   }

   private boolean check6() {
      float f = this.player().getHealth() + (this.zolotyeSerdca.isFlag3() ? this.player().getAbsorptionAmount() : 0.0F);
      float f1 = this.elitra.isFlag3() && SphereItems.check() ? (float)this.hpCElitroy.getValue() : (float)this.minimalnoeHp.getValue();
      return f <= f1;
   }

   @Override
   public void onDisable() {
      this.update13();
   }

   private void update13() {
      this.flag = false;
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.time = 0L;
      InventoryActions.setModule(this);
   }

   private void setInt2(int count) {
      if (!this.check7()) {
         this.setInt(count);
         this.onIntBoolean(count, true);
      } else {
         int i = this.player().getInventory().selectedSlot;
         if (count < 9) {
            if (count != i) {
               if (this.value237 == -1) {
                  this.value237 = i;
               }

               SphereItems.onInt(count);
               this.time = System.currentTimeMillis();
            }
         } else {
            if (this.value235 == -1) {
               this.value235 = count;
               this.value236 = i;
            }

            boolean flagx = true;
            this.onBooleanIntInt(flagx, count, i);
         }
      }
   }

   private int getInt2() {
      int i = SphereItems.getIntByBoolean(this.prioritetObychnyh.isFlag3());
      return i != -1 && !this.isItemStack2(this.player().getInventory().getStack(i)) ? i : this.getInt();
   }

   private void update14() {
      if (System.currentTimeMillis() - this.time >= 400L) {
         if (this.check4() && !this.check3()) {
            int i = this.getInt2();
            if (i != -1) {
               this.setInt2(i);
               return;
            }
         }

         if (this.svapNazad.isFlag3() && this.check5()) {
            this.update11();
         }
      }
   }

   private void onBooleanIntInt(boolean flag2, int count, int count2) {
      if (!this.notInGame()) {
         this.flag = true;
         int j = SphereItems.getIntByInt(count);
         Runnable runnable = this.getRunnableByBoolean(flag2);
         int i = j;
         InventoryActions.onIntRunnableModuleInt(i, runnable, this, count2);
      }
   }

   private Boolean getBoolean2() {
      return !this.check7();
   }

   private boolean isItemStack3(ItemStack itemStack) {
      if (!SphereItems.isItemStack6(itemStack) || this.isItemStack2(itemStack)) {
         return false;
      } else {
         return !SphereItems.isItemStack5(itemStack) ? SphereItems.isItemStack2(itemStack) : this.isItemStack(itemStack);
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entitystatuss2cpacket) {
            if (entitystatuss2cpacket.getStatus() == 35 && entitystatuss2cpacket.getEntity(this.world()) == this.player()) {
               this.flag = false;
            }
         }
      }
   }

   private void onIntBoolean(int count, boolean flag2) {
      if (!this.notInGame()) {
         this.flag = true;
         InventoryActions.onModuleIntRunnable(this, SphereItems.getIntByInt(count), this.getRunnableByBoolean(flag2));
      }
   }

   private boolean check7() {
      return this.ruka.isFlag3();
   }

   private Runnable getRunnableByBoolean(boolean flag) {
      return () -> this.setBoolean(flag);
   }

   private boolean check8() {
      if (!this.crystals.isFlag3() || !PearlMath.isWorldPlayerEntityDouble2(this.world(), this.player(), 6.0)) {
         if (this.tnt.isFlag3()) {
            World world1 = this.world();
            PlayerEntity playerentity1 = this.player();
            double d1 = this.lyubayaRange.isFlag3() ? Double.MAX_VALUE : this.radiusTnt.getValue();
            byte b0 = 80;
            double d0 = d1;
            PlayerEntity playerentity = playerentity1;
            World world = world1;
            if (PearlMath.isIntWorldDoublePlayerEntity(b0, world, d0, playerentity)) {
               return true;
            }
         }

         if ((!this.trezubec.isFlag3() || !PearlMath.isWorldPlayerEntityDouble(this.world(), this.player(), 15.0))
            && (!this.padenie.isFlag3() || this.player().isOnGround() || this.player().isGliding() || !(this.player().fallDistance > 10.0F))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void onEnable() {
      this.update13();
   }

   @Override
   public void update8() {
      SneakState.update4();
      if (this.notInGame() || this.player().isDead() || this.player().getHealth() <= 0.0F) {
         this.update13();
      } else if (!this.flag) {
         this.update12();
         this.update14();
      }
   }

   private boolean check9() {
      if (this.check7()) {
         return false;
      } else {
         return this.padenie.isFlag3() && this.player().fallDistance > 5.0F
            ? false
            : this.withoutFeyerverkov.isFlag3() && this.player().getEquippedStack(EquipmentSlot.OFFHAND).isOf(Items.FIREWORK_ROCKET);
      }
   }
}
