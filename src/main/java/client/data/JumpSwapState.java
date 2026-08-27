package client.data;

import client.enums.SwapJumpState;
import client.module.Feature;
import client.util.SneakState;
import client.util.SphereItems;
import java.util.function.Consumer;
import net.minecraft.screen.slot.Slot;

public final class JumpSwapState {
   private static boolean flag = false;
   private final Consumer<Boolean> consumer;
   private SwapJumpState swapJumpState = SwapJumpState.IDLE;
   private int value = 0;
   private int value2 = -1;
   private int value3 = 0;
   private boolean flag2 = false;
   private boolean flag3 = false;

   public JumpSwapState(Consumer<Boolean> consumer2) {
      this.consumer = consumer2;
   }

   private void update() {
      if (this.flag3) {
         this.flag3 = false;
         SneakState.update2();
      }
   }

   private void update2() {
      switch (this.swapJumpState) {
         case LOCK_IN:
            this.update4();
            this.swapJumpState = SwapJumpState.SWAP_IN;
            this.value = 1;
            break;
         case SWAP_IN:
            this.onIntInt2(this.value2, this.value3);
            this.update();
            Feature.mc.options.useKey.setPressed(true);
            Feature.mc.options.jumpKey.setPressed(true);
            this.swapJumpState = SwapJumpState.PRESS_JUMP_PREP;
            this.value = 2;
            break;
         case PRESS_JUMP_PREP:
            Feature.mc.options.useKey.setPressed(false);
            this.update4();
            this.swapJumpState = SwapJumpState.PRESS_JUMP;
            this.value = 1;
            break;
         case PRESS_JUMP:
            int j = this.value3;
            int i = this.value2;
            this.onIntInt(j, i);
            this.update();
            flag = false;
            this.consumer.accept(false);
            this.swapJumpState = SwapJumpState.RELEASE_JUMP;
            this.value = 2;
            break;
         case RELEASE_JUMP:
            Feature.mc.options.jumpKey.setPressed(false);
            this.swapJumpState = SwapJumpState.IDLE;
      }
   }

   private void onIntInt(int count, int count2) {
      if (this.flag2) {
         Feature.mc.player.getInventory().selectedSlot = count;
      } else {
         SphereItems.onIntInt4(count2, count);
      }
   }

   public void update3() {
      this.update();
      if (this.swapJumpState != SwapJumpState.IDLE) {
         Feature.mc.options.jumpKey.setPressed(false);
         Feature.mc.options.useKey.setPressed(false);
      }

      this.swapJumpState = SwapJumpState.IDLE;
      this.value = 0;
      flag = false;
      this.value2 = -1;
   }

   private void update4() {
      if (!this.flag3) {
         this.flag3 = true;
         SneakState.update5();
      }
   }

   public void update5() {
      if (this.swapJumpState != SwapJumpState.IDLE) {
         if (this.value > 0) {
            this.value--;
         } else {
            try {
               this.update2();
            } catch (Throwable throwable) {
               this.update3();
            }
         }
      }
   }

   public boolean check() {
      return this.swapJumpState == SwapJumpState.IDLE;
   }

   public static Float getFloat() {
      return flag ? 90.0F : null;
   }

   public void setSlot(Slot slot2) {
      if (Feature.mc.player != null && slot2 != null) {
         this.value3 = Feature.mc.player.getInventory().selectedSlot;
         this.flag2 = SphereItems.isInt2(slot2.id);
         this.value2 = slot2.id;
         this.consumer.accept(true);
         flag = true;
         this.swapJumpState = SwapJumpState.LOCK_IN;
         this.value = 0;
      }
   }

   private void onIntInt2(int count, int count2) {
      if (this.flag2) {
         Feature.mc.player.getInventory().selectedSlot = count - 36;
      } else {
         SphereItems.onIntInt4(count, count2);
      }
   }
}
