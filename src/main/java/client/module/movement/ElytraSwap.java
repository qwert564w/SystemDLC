package client.module.movement;

import client.api.Icon;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.Setting;
import client.util.InventoryActions;
import client.util.KeyBindings;
import client.util.NotificationManager;
import client.util.SneakState;
import client.util.SphereItems;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ElytraSwap extends Module {
   private HotkeySetting keySvapa;
   private HotkeySetting keyFeyerverka;
   private BooleanSetting otkryvatInventar;
   private boolean flag;
   private boolean flag2;
   private Runnable runnable;
   private int value235;
   private int value236;

   public ElytraSwap() {
      super("ElytraSwap", Category.MOVEMENT);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 71, this::update13);
      hotkeysetting.setName("Клавиша свапа");
      hotkeysetting.setDescription("Кнопка для смены элитры");
      this.keySvapa = hotkeysetting;
      hotkeysetting = new HotkeySetting("", "", 70, this::update15);
      hotkeysetting.setName("Клавиша фейерверка");
      hotkeysetting.setDescription("Кнопка для запуска фейерверка");
      this.keyFeyerverka = hotkeysetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Открывать инвентарь");
      booleansetting.setDescription("Открывать инвентарь при свапе");
      this.otkryvatInventar = booleansetting;
      this.flag = false;
      this.flag2 = false;
      this.runnable = null;
      this.value235 = 0;
      this.value236 = 0;
      this.addSettings(new Setting[]{this.keySvapa, this.keyFeyerverka, this.otkryvatInventar});
   }

   private int getInt() {
      for (int i = 9; i < 36; i++) {
         if (this.isItemStack(this.inventory().getStack(i))) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public void onTick() {
      if (this.flag) {
         SneakState.update4();
      }

      if (this.runnable != null) {
         if (this.value235 > 0) {
            this.value235--;
         } else {
            Runnable runnablex = this.runnable;
            this.runnable = null;
            runnablex.run();
            this.value236 = 2;
         }
      } else {
         if (this.value236 > 0) {
            this.value236--;
            if (this.value236 == 0) {
               KeyBindings.update4();
               this.update12();
               this.flag = false;
            }
         }
      }
   }

   private int getInt2() {
      return this.getIntByItem(Items.ELYTRA);
   }

   private static void onIntInt(int count, int count2) {
      Runnable runnablex = () -> ElytraSwap.onIntInt2(count, count);
      byte b0 = 6;
      SphereItems.onRunnableIntInt(runnablex, count, b0);
   }

   private void update11() {
      this.update12();
      KeyBindings.update4();
      this.runnable = null;
      this.value235 = 0;
      this.value236 = 0;
      this.flag = false;
   }

   private int getIntByItem(Item item2) {
      for (int i = 9; i < 36; i++) {
         if (this.inventory().getStack(i).isOf(item2)) {
            return i;
         }
      }

      return -1;
   }

   private static void onInt(int count) {
      SphereItems.onIntInt4(6, count);
   }

   private void update12() {
      if (this.flag2) {
         this.flag2 = false;
         SneakState.update2();
      }
   }

   private void onBoolean(boolean flag) {
      Item item = flag ? this.getItem() : Items.ELYTRA;
      if (item == null) {
         item = Items.ELYTRA;
      }

      String s = flag ? "нагрудник" : "элитру";
      NotificationManager notificationmanager = NotificationManager.getInstance();
      Icon icon1 = Icon.getIconByItem(item);
      String s1 = "Свапнул";
      Icon icon = icon1;
      notificationmanager.onStringIconString(s1, icon, s);
   }

   private static void onIntInt2(int count, int count2) {
      SphereItems.onIntInt4(count, count2);
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private void update13() {
      if (!this.notInGame() && !this.flag) {
         boolean flagx = this.check3();
         int i = flagx ? this.getInt3() : this.getInt4();
         int j = flagx ? this.getInt() : this.getInt2();
         if (i != -1 || j != -1) {
            if (i != -1) {
               this.setRunnable(() -> ElytraSwap.onInt(j));
            } else {
               this.onInt2(j);
            }

            this.onBoolean(flagx);
         }
      }
   }

   private int getInt3() {
      for (int i = 0; i < 9; i++) {
         if (this.isItemStack(this.inventory().getStack(i))) {
            return i;
         }
      }

      return -1;
   }

   private Runnable getRunnable() {
      return this::setFlag;
   }

   private int getIntByItem2(Item item2) {
      for (int i = 0; i < 9; i++) {
         if (this.inventory().getStack(i).isOf(item2)) {
            return i;
         }
      }

      return -1;
   }

   private boolean isItemStack(ItemStack itemStack) {
      if (!itemStack.isEmpty() && !itemStack.isOf(Items.ELYTRA)) {
         EquippableComponent equippablecomponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
         return equippablecomponent != null && equippablecomponent.slot() == EquipmentSlot.CHEST;
      } else {
         return false;
      }
   }

   private static void onIntInt3(int count, int count2) {
      Runnable runnablex = () -> ElytraSwap.onIntInt(count, count);
      SphereItems.onRunnableIntInt(runnablex, count2, count);
   }

   private void onInt2(int count) {
      int i = this.inventory().selectedSlot;
      int j = SphereItems.getIntByInt(count);
      this.setRunnable(() -> ElytraSwap.onIntInt3(j, j));
   }

   private void setRunnable(Runnable runnable2) {
      this.flag = true;
      this.update14();
      KeyBindings.update();
      this.flag2 = true;
      SneakState.update5();
      if (this.player() != null) {
         this.player().setSprinting(false);
      }

      this.runnable = runnable2;
      this.value235 = 1;
   }

   private Item getItem() {
      int i = this.getInt3();
      if (i != -1) {
         return this.inventory().getStack(i).getItem();
      } else {
         int j = this.getInt();
         return j != -1 ? this.inventory().getStack(j).getItem() : null;
      }
   }

   private void update14() {
      if (this.otkryvatInventar.isFlag3() && this.currentScreen() == null) {
         this.client().setScreen(new InventoryScreen(this.player()));
      }
   }

   private boolean check3() {
      return this.player().getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
   }

   private int getInt4() {
      return this.getIntByItem2(Items.ELYTRA);
   }

   private void update15() {
      if (!this.notInGame() && !this.flag) {
         if (this.check3()) {
            int i = this.getIntByItem2(Items.FIREWORK_ROCKET);
            if (i != -1) {
               int l = this.inventory().selectedSlot;
               this.flag = true;
               Runnable runnablex = this.getRunnable();
               InventoryActions.onIntIntRunnableModule(i, l, runnablex, this);
               NotificationManager notificationmanager = NotificationManager.getInstance();
               Icon icon2 = Icon.getIconByItem(Items.FIREWORK_ROCKET);
               String s1 = "фейерверк";
               String s = "Использовал";
               Icon icon = icon2;
               notificationmanager.onStringIconString(s, icon, s1);
            } else {
               int j = this.getIntByItem(Items.FIREWORK_ROCKET);
               if (j != -1) {
                  int k = SphereItems.getIntByInt(j);
                  this.flag = true;
                  Runnable runnable1 = this.getRunnable();
                  InventoryActions.onRunnableModuleInt(runnable1, this, k);
                  NotificationManager notificationmanager1 = NotificationManager.getInstance();
                  Icon icon3 = Icon.getIconByItem(Items.FIREWORK_ROCKET);
                  String s3 = "фейерверк";
                  String s2 = "Использовал";
                  Icon icon1 = icon3;
                  notificationmanager1.onStringIconString(s2, icon1, s3);
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }

   @Override
   public void update8() {
      if (this.flag) {
         SneakState.update4();
      }
   }

   private void setFlag() {
      this.flag = false;
   }
}
