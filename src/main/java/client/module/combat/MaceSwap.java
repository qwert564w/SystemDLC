package client.module.combat;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.util.InteractEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MaceSwap extends Module {
   private boolean flag = false;
   private int value235 = -1;
   private int value236 = 0;

   public MaceSwap() {
      super("MaceSwap", Category.COMBAT);
   }

   private void onInt(int count) {
      if (!this.notInGame()) {
         this.inventory().selectedSlot = count;
      }
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (this.flag && this.player() != null) {
         this.value236++;
         if (this.value236 >= 2 && this.value235 != -1) {
            this.onInt(this.value235);
            this.flag = false;
            this.value236 = 0;
            this.value235 = -1;
         }
      }
   }

   @Override
   public void onDisable() {
      this.flag = false;
      this.value236 = 0;
      this.value235 = -1;
   }

   private int getInt() {
      if (this.notInGame()) {
         return -1;
      } else {
         for (int i = 0; i < 9; i++) {
            ItemStack itemstack = this.inventory().getStack(i);
            if (itemstack.getItem() == Items.MACE) {
               return i;
            }
         }

         return -1;
      }
   }

   private boolean isItemStack(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty()
         ? itemStack.getItem() == Items.WOODEN_SWORD
            || itemStack.getItem() == Items.STONE_SWORD
            || itemStack.getItem() == Items.IRON_SWORD
            || itemStack.getItem() == Items.GOLDEN_SWORD
            || itemStack.getItem() == Items.DIAMOND_SWORD
            || itemStack.getItem() == Items.NETHERITE_SWORD
         : false;
   }

   private void setInt(int count) {
      if (!this.notInGame()) {
         this.value235 = this.inventory().selectedSlot;
         this.flag = true;
         this.value236 = 0;
         this.onInt(count);
      }
   }

   @Override
   public void onInteractEvent(InteractEvent interactEvent) {
      if (!this.notInGame() && interactEvent.getHand() == this.mainHand() && !this.flag) {
         ItemStack itemstack = this.player().getMainHandStack();
         if (this.isItemStack(itemstack)) {
            int i = this.getInt();
            if (i != -1) {
               this.setInt(i);
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.flag = false;
      this.value236 = 0;
      this.value235 = -1;
   }
}
