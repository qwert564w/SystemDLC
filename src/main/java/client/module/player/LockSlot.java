package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.MultilistSetting;
import client.setting.Setting;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class LockSlot extends Module {
   private MultilistSetting sloty;

   public LockSlot() {
      super("LockSlot", Category.PLAYER);
      MultilistSetting multilistsetting = new MultilistSetting("", "", getList(), List.of());
      multilistsetting.setName("Слоты");
      multilistsetting.setDescription("Слоты хотбара, из которых нельзя выкидывать");
      this.sloty = multilistsetting;
      this.addSettings(new Setting[]{this.sloty});
   }

   @Override
   public void onDisable() {
   }

   private static List getList() {
      ArrayList arraylist = new ArrayList(9);

      for (int i = 1; i <= 9; i++) {
         arraylist.add(String.valueOf(i));
      }

      return arraylist;
   }

   public boolean isSlotActionTypeInt(SlotActionType slotActionType, int count) {
      if (slotActionType != SlotActionType.THROW || count < 0) {
         return false;
      } else if (this.player() == null) {
         return false;
      } else {
         ScreenHandler screenhandler = this.player().currentScreenHandler;
         if (screenhandler != null && count < screenhandler.slots.size()) {
            Slot slot = (Slot)screenhandler.slots.get(count);
            return slot != null && slot.inventory == this.inventory() ? this.isInt(slot.getIndex()) : false;
         } else {
            return false;
         }
      }
   }

   private boolean isInt(int count) {
      return count >= 0 && count < 9 ? this.sloty.getList4().contains(String.valueOf(count + 1)) : false;
   }

   public boolean check3() {
      return this.player() != null && this.isInt(this.inventory().selectedSlot);
   }

   @Override
   public void onEnable() {
   }
}
