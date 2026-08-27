package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealer extends Module {
   private SliderSetting delay;
   private BooleanSetting autoZakrytie;
   private long time;

   public ChestStealer() {
      super("ChestStealer", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 100.0, 0.0, 500.0, 10.0);
      slidersetting.setName("Задержка");
      slidersetting.setDescription("Задержка между перемещением предметов (в мс)");
      this.delay = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Авто-закрытие");
      booleansetting.setDescription("Закрывать контейнер после стила");
      this.autoZakrytie = booleansetting;
      this.time = 0L;
      this.addSettings(new Setting[]{this.delay, this.autoZakrytie});
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.interactionManager() != null) {
         if (this.currentScreen() instanceof HandledScreen handledscreen) {
            if (!(handledscreen instanceof InventoryScreen) && !(handledscreen instanceof CreativeInventoryScreen)) {
               ScreenHandler screenhandler = handledscreen.getScreenHandler();
               if (this.isScreenHandler(screenhandler)) {
                  long i = System.currentTimeMillis();
                  if (i - this.time >= this.delay.getValueAsLong()) {
                     Slot slot = this.getSlotByScreenHandler(screenhandler);
                     if (slot == null) {
                        if (this.autoZakrytie.isFlag3() && this.check3()) {
                           handledscreen.close();
                           this.client().setScreen(null);
                        }
                     } else {
                        this.interactionManager().clickSlot(screenhandler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, this.player());
                        this.time = i;
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.time = 0L;
   }

   private Slot getSlotByScreenHandler(ScreenHandler screenHandler) {
      PlayerInventory playerinventory = this.inventory();
      ArrayList arraylist = new ArrayList();

      for (Slot slot : screenHandler.slots) {
         if (slot != null && slot.hasStack() && slot.inventory != playerinventory && slot.canTakeItems(this.player())) {
            arraylist.add(slot);
         }
      }

      return arraylist.isEmpty() ? null : (Slot)arraylist.get(ThreadLocalRandom.current().nextInt(arraylist.size()));
   }

   private boolean isScreenHandler(ScreenHandler screenHandler) {
      PlayerInventory playerinventory = this.player().getInventory();

      for (Slot slot : screenHandler.slots) {
         if (slot != null && slot.inventory != playerinventory) {
            return true;
         }
      }

      return false;
   }

   private boolean check3() {
      Screen screen = this.currentScreen();
      if (!(screen instanceof HandledScreen)) {
         return false;
      } else {
         return screen instanceof InventoryScreen ? false : !(screen instanceof CreativeInventoryScreen);
      }
   }

   @Override
   public void onEnable() {
      this.time = 0L;
   }
}
