package client.api;

import client.setting.HotkeySetting;
import net.minecraft.item.ItemStack;

public interface SwapWheelView {
   public void clearSlot(int count);

   public void setSlot(ItemStack itemStack, int count);

   public ItemStack[] getWheelSlots();

   public HotkeySetting getCellBind(int count);

   public int slotCapacity();
}
