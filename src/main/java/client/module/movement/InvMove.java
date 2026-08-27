package client.module.movement;

import client.enums.PacketDirection;
import client.gui.screen.ClickGuiScreen;
import client.gui.widget.WidgetState;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.SneakState;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class InvMove extends Module {
   private BooleanSetting begatVInventare;
   private BooleanSetting begatVGui;
   private final Queue<ClickSlotC2SPacket> queue;
   private ClickSlotC2SPacket clickSlotC2SPacket;
   private CloseHandledScreenC2SPacket closeHandledScreenC2SPacket;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private int value235;
   private int value236;
   private int value237;
   private int value238;
   private int value239;
   private KeyBinding[] keyBindingArray;

   public InvMove() {
      super("InvMove", Category.MOVEMENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Бегать в инвентаре");
      booleansetting.setDescription("Позволяет двигаться c открытым инвентарём");
      this.begatVInventare = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Бегать в гуи");
      booleansetting.setDescription("Позволяет двигаться c открытым гуи клиента");
      this.begatVGui = booleansetting;
      this.queue = new ConcurrentLinkedQueue<>();
      this.clickSlotC2SPacket = null;
      this.closeHandledScreenC2SPacket = null;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.value235 = 0;
      this.value236 = 0;
      this.value237 = 0;
      this.value238 = 0;
      this.value239 = 0;
      this.addSettings(new Setting[]{this.begatVInventare, this.begatVGui});
   }

   @Override
   public void onTick() {
      if (this.player() == null) {
         this.update16();
      } else {
         if (this.check4()) {
            this.value239 = 4;
         } else if (this.value239 > 0) {
            this.value239--;
         }

         if (this.value238 > 0) {
            this.value238--;
            if (this.value238 == 0 && this.closeHandledScreenC2SPacket == null) {
               this.update12();
            }
         }

         if (this.value235 > 0) {
            this.value235--;
         } else if (!this.queue.isEmpty()) {
            ClickSlotC2SPacket clickslotc2spacket = this.queue.poll();
            if (clickslotc2spacket != null && clickslotc2spacket.getSyncId() == this.getInt()) {
               onPacket(clickslotc2spacket);
            }

            this.value235 = 1;
            if (!this.queue.isEmpty()) {
               this.value236 = Math.max(this.value236, 4);
            }
         }

         if (this.value236 > 0) {
            this.value236--;
            if (this.value236 == 0 && this.queue.isEmpty()) {
               this.update11();
            }
         }

         if (this.value237 > 0) {
            this.value237--;
            if (this.value237 == 0 && this.clickSlotC2SPacket != null) {
               if (this.clickSlotC2SPacket.getSyncId() == this.getInt()) {
                  onPacket(this.clickSlotC2SPacket);
               }

               this.clickSlotC2SPacket = null;
            }
         }

         if (this.flag2) {
            if (this.flag3) {
               this.update13();
            } else {
               SneakState.update4();
            }
         } else if (this.currentScreen() != null
            && !(this.currentScreen() instanceof ChatScreen)
            && !(this.currentScreen() instanceof SignEditScreen)
            && !(this.currentScreen() instanceof AnvilScreen)) {
            if (!WidgetState.isWidgetAsBoolean() && !(this.currentScreen() instanceof ShulkerBoxScreen)) {
               this.update15();
               boolean flag2x = this.check3();
               boolean flagx = this.currentScreen() instanceof ClickGuiScreen;
               if ((flag2x && this.begatVInventare.isFlag3() || flagx && this.begatVGui.isFlag3()) && this.keyBindingArray != null) {
                  long i = this.client().getWindow().getHandle();

                  for (KeyBinding keybinding : this.keyBindingArray) {
                     boolean flag1 = GLFW.glfwGetKey(i, keybinding.getDefaultKey().getCode()) == 1;
                     if (keybinding.isPressed() != flag1) {
                        keybinding.setPressed(flag1);
                     }
                  }
               }
            } else {
               this.update13();
            }
         }
      }
   }

   private void update11() {
      if (this.closeHandledScreenC2SPacket != null) {
         SneakState.update4();
         onPacket(this.closeHandledScreenC2SPacket);
         this.closeHandledScreenC2SPacket = null;
         this.value238 = 1;
      } else {
         this.update12();
      }
   }

   private boolean check3() {
      return this.currentScreen() instanceof InventoryScreen && !(this.currentScreen() instanceof CreativeInventoryScreen);
   }

   private boolean check4() {
      if (this.player() != null && this.clientPlayer() != null) {
         Input input = this.clientPlayer().input;
         if (input == null || input.movementForward == 0.0F && input.movementSideways == 0.0F) {
            if (this.player().isSprinting()) {
               return true;
            } else if (this.client() != null && this.client().getWindow() != null && this.options() != null) {
               long i = this.client().getWindow().getHandle();
               return isLongInt(i, this.options().forwardKey.getDefaultKey().getCode())
                  || isLongInt(i, this.options().backKey.getDefaultKey().getCode())
                  || isLongInt(i, this.options().leftKey.getDefaultKey().getCode())
                  || isLongInt(i, this.options().rightKey.getDefaultKey().getCode())
                  || isLongInt(i, this.options().jumpKey.getDefaultKey().getCode());
            } else {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private void update12() {
      if (this.flag2) {
         this.flag2 = false;
         SneakState.update2();
      }

      this.flag3 = false;
   }

   @Override
   public void onDisable() {
      this.update16();
   }

   private boolean check5() {
      return this.value239 > 0 || this.check4();
   }

   private void update13() {
      if (this.options() != null) {
         this.options().forwardKey.setPressed(false);
         this.options().backKey.setPressed(false);
         this.options().leftKey.setPressed(false);
         this.options().rightKey.setPressed(false);
         this.options().jumpKey.setPressed(false);
         this.options().sprintKey.setPressed(false);
         this.options().sneakKey.setPressed(false);
      }
   }

   private static void onPacket(Packet packet2) {
      try {
         PacketEvent.onPacket2(packet2);
      } catch (Throwable throwable) {
      }
   }

   private int getInt() {
      return this.player() != null && this.player().currentScreenHandler != null ? this.player().currentScreenHandler.syncId : -1;
   }

   private boolean isInt(int count) {
      if (this.player() == null || this.player().currentScreenHandler == null) {
         return false;
      } else if (count >= 0 && count < this.player().currentScreenHandler.slots.size()) {
         ItemStack itemstack = ((Slot)this.player().currentScreenHandler.slots.get(count)).getStack();
         ItemStack itemstack1 = this.player().currentScreenHandler.getCursorStack();
         return isItemStack(itemstack) || isItemStack(itemstack1);
      } else {
         return false;
      }
   }

   private static boolean isItemStack(ItemStack itemStack) {
      return itemStack.getItem() instanceof BlockItem blockitem && blockitem.getBlock() instanceof ShulkerBoxBlock;
   }

   private static boolean isLongInt(long time, int count) {
      return count > 0 && GLFW.glfwGetKey(time, count) == 1;
   }

   private void onPacketEventClickSlotC2SPacket(PacketEvent packetEvent, ClickSlotC2SPacket clickSlotC2SPacket2) {
      SlotActionType slotactiontype = clickSlotC2SPacket2.getActionType();
      if (slotactiontype == SlotActionType.PICKUP && !this.flag && this.isInt(clickSlotC2SPacket2.getSlot())) {
         packetEvent.setFlag(true);
         this.clickSlotC2SPacket = clickSlotC2SPacket2;
         this.value237 = 2;
         this.update14();
         this.value236 = 4;
      } else {
         if (slotactiontype == SlotActionType.PICKUP
            || slotactiontype == SlotActionType.QUICK_MOVE
            || slotactiontype == SlotActionType.SWAP
            || slotactiontype == SlotActionType.THROW
            || slotactiontype == SlotActionType.QUICK_CRAFT) {
            packetEvent.setFlag(true);
            this.queue.add(clickSlotC2SPacket2);
            this.setClickSlotC2SPacket(clickSlotC2SPacket2);
            this.update14();
            this.value236 = 4;
            if (this.value235 == 0) {
               this.value235 = 1;
            }
         }
      }
   }

   private boolean check6() {
      if (this.player() != null && this.clientPlayer() != null) {
         Input input = this.clientPlayer().input;
         return input == null || input.movementForward == 0.0F && input.movementSideways == 0.0F ? this.player().isSprinting() : true;
      } else {
         return false;
      }
   }

   private void update14() {
      if (!this.flag2) {
         this.flag2 = true;
         SneakState.update5();
      }
   }

   private void onPacketEventCloseHandledScreenC2SPacket(PacketEvent packetEvent, CloseHandledScreenC2SPacket closeHandledScreenC2SPacket2) {
      boolean flagx = !this.queue.isEmpty() || this.clickSlotC2SPacket != null;
      if (flagx || this.check6()) {
         packetEvent.setFlag(true);
         this.closeHandledScreenC2SPacket = closeHandledScreenC2SPacket2;
         this.value238 = 0;
         this.update14();
         this.value236 = Math.max(this.value236, flagx ? 4 : 2);
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (!this.notInGame() && packetEvent.getPacketDirection() == PacketDirection.SEND) {
         if (packetEvent.getPacket() instanceof CloseHandledScreenC2SPacket closehandledscreenc2spacket) {
            this.onPacketEventCloseHandledScreenC2SPacket(packetEvent, closehandledscreenc2spacket);
         } else if (!SneakState.isValueAsBoolean()) {
            if (packetEvent.getPacket() instanceof ClickSlotC2SPacket clickslotc2spacket) {
               if (this.check3()) {
                  if (!this.check5()) {
                     this.setClickSlotC2SPacket(clickslotc2spacket);
                  } else {
                     this.onPacketEventClickSlotC2SPacket(packetEvent, clickslotc2spacket);
                  }
               }
            }
         }
      }
   }

   private void setClickSlotC2SPacket(ClickSlotC2SPacket clickSlotC2SPacket) {
      if (clickSlotC2SPacket.getActionType() == SlotActionType.PICKUP) {
         this.flag = !this.flag;
      }
   }

   private void update15() {
      if (this.keyBindingArray == null && this.options() != null) {
         this.keyBindingArray = new KeyBinding[]{
            this.options().forwardKey,
            this.options().backKey,
            this.options().leftKey,
            this.options().rightKey,
            this.options().jumpKey,
            this.options().sprintKey
         };
      }
   }

   @Override
   public void onEnable() {
      this.update16();
   }

   private void update16() {
      int i = this.getInt();

      while (!this.queue.isEmpty()) {
         ClickSlotC2SPacket clickslotc2spacket = this.queue.poll();
         if (clickslotc2spacket != null && clickslotc2spacket.getSyncId() == i) {
            onPacket(clickslotc2spacket);
         }
      }

      if (this.clickSlotC2SPacket != null) {
         if (this.clickSlotC2SPacket.getSyncId() == i) {
            onPacket(this.clickSlotC2SPacket);
         }

         this.clickSlotC2SPacket = null;
      }

      this.flag = false;
      if (this.closeHandledScreenC2SPacket != null) {
         SneakState.update4();
         onPacket(this.closeHandledScreenC2SPacket);
         this.closeHandledScreenC2SPacket = null;
      }

      if (this.flag2) {
         this.flag2 = false;
         SneakState.update2();
      }

      this.flag3 = false;
      this.value237 = 0;
      this.value235 = 0;
      this.value236 = 0;
      this.value238 = 0;
      this.value239 = 0;
   }
}
