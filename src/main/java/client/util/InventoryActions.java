package client.util;

import client.data.ActionQueue;
import client.enums.PacketDirection;
import client.module.Feature;
import client.module.Module;
import client.network.PacketEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.IntConsumer;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;

public final class InventoryActions {
   private static final Deque<Module> deque = new ArrayDeque<>();
   private static final List<PendingAction> list = new ArrayList<>();
   private static int value = 0;
   private static int value2 = 0;
   public static int value3 = 0;

   private InventoryActions() {
   }

   private static void onIntInt(int count, int count2) {
      SphereItems.onIntInt4(count, count2);
   }

   public static void update() {
      if (Feature.mc.player != null && Feature.mc.player.networkHandler != null && Feature.mc.player.currentScreenHandler != null) {
         TickCounter.setInt(2);
         Feature.mc.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(Feature.mc.player.currentScreenHandler.syncId));
      }
   }

   private static void update2() {
      while (value2 > 0) {
         value2--;
         SneakState.update2();
      }
   }

   private static void onIntInt2(int count, int count2) {
      onIntInt6(count, count2);
      SphereItems.onInt3(count);
   }

   public static void setModule(Module module2) {
      if (module2 != null) {
         deque.removeIf(p0 -> InventoryActions.isModuleModule(module2, p0));
         if (deque.isEmpty()) {
            if (value2 > 0) {
               update2();
            }

            value3++;
         }

         addModule(module2);
      }
   }

   private static void onIntIntInt(int count, int count2, int count3) {
      onIntIntInt2(count3, count, count2);
   }

   public static boolean check() {
      return !deque.isEmpty() || value2 > 0 || !list.isEmpty();
   }

   public static void update3() {
      if (value2 > 0) {
         SneakState.update4();
      }

      update4();
   }

   private static boolean isModuleModule(Module module2, Module module3) {
      return module3 == module2;
   }

   private static void update4() {
      if (check() && !SphereItems.check2()) {
         if (++value >= 20) {
            value = 0;
            deque.clear();
            update2();
            value3++;
            addModule(null);
         }
      } else {
         value = 0;
      }
   }

   private static void onIntInt3(int count, int count2) {
      SphereItems.onInt2(count);
      getIntByInt(count2);
   }

   public static boolean check2() {
      if (Feature.mc.player != null && Feature.mc.currentScreen == null) {
         ScreenHandler screenhandler = Feature.mc.player.currentScreenHandler;
         return screenhandler != null && screenhandler.getCursorStack().isEmpty();
      } else {
         return false;
      }
   }

   public static void onModule(Module module2) {
      deque.push(module2);
   }

   private static void update5() {
      if (value2 != 0) {
         value2--;
         SneakState.update2();
      }
   }

   public static void update6() {
      value2++;
      SneakState.update5();
   }

   private static void onIntInt4(int count, int count2) {
      SphereItems.onIntInt4(count, count2);
   }

   private static void update7() {
      float f1 = Float.NaN;
      float f = Float.NaN;
      onFloatFloat(f1, f);
   }

   private static void onIntInt5(int count, int count2) {
      SphereItems.onIntInt4(count, count2);
   }

   public static void onModule2(Module module2) {
      if (!deque.isEmpty()) {
         if (deque.peek() == module2) {
            deque.pop();
         } else {
            deque.removeIf(p0 -> InventoryActions.isModuleModule2(module2, p0));
         }
      }
   }

   public static void onFloatFloat(float value, float value2) {
      if (Feature.mc.interactionManager != null && Feature.mc.player != null) {
         TickCounter.setInt(2);
         float f = Feature.mc.player.getYaw();
         float f1 = Feature.mc.player.getPitch();
         boolean flag = !Float.isNaN(value2) || !Float.isNaN(value);
         if (flag) {
            Feature.mc.player.setYaw(Float.isNaN(value2) ? f : value2);
            Feature.mc.player.setPitch(Float.isNaN(value) ? f1 : value);
         }

         try {
            Feature.mc.interactionManager.interactItem(Feature.mc.player, Hand.MAIN_HAND);
         } catch (Throwable throwable) {
         } finally {
            if (flag) {
               Feature.mc.player.setYaw(f);
               Feature.mc.player.setPitch(f1);
            }
         }
      }
   }

   private static void onIntModuleRunnable(int count, Module module2, Runnable runnable) {
      try {
         if (Feature.mc.player != null) {
            Feature.mc.player.getInventory().selectedSlot = count;
         }
      } finally {
         onModule2(module2);
         onRunnable(runnable);
      }
   }

   private static boolean isModuleModule2(Module module2, Module module3) {
      return module3 == module2;
   }

   public static void onRunnable(Runnable runnable) {
      if (runnable != null) {
         try {
            runnable.run();
         } catch (Throwable throwable) {
         }
      }
   }

   private static void onInt(int count) {
      SphereItems.onInt4(count);
   }

   private static void onIntIntIntItemInt(int count, int count2, int count3, Item item2, int count4) {
      if (Feature.mc.player != null) {
         boolean flag = Feature.mc.player.getInventory().getStack(count).isEmpty();
         if (count2 >= 0) {
            SphereItems.onIntInt4(count2, count);
            if (!flag) {
               SphereItems.onInt2(count2);
               getIntByInt(count3);
            }
         } else if (count2 == -2) {
            int i = SphereItems.getIntByIntItem(count3, item2);
            if (i >= 0) {
               onIntInt2(count3, i);
               if (!check3()) {
                  SphereItems.onInt2(count4);
                  getIntByInt(count3);
               }
            }
         } else if (!check3()) {
            SphereItems.onInt2(count4);
            getIntByInt(count3);
         }
      }
   }

   private static void onIntIntIntIntIntIntArray(int count, int count2, int count3, int count4, int count5, int[] countArray) {
      if (count >= 0) {
         onIntIntInt2(count, count2, count3);
         SphereItems.onIntInt4(count, count4);
      } else {
         onIntIntInt2(count5, count2, count3);
         countArray[0] = getIntByInt(count2);
      }
   }

   private static void onIntConsumerIntArray(IntConsumer intConsumer, int[] countArray) {
      intConsumer.accept(countArray[0]);
   }

   private static void onIntRunnableInt(int count, Runnable runnable, int count2) {
      int i = count2 - 1;
      onIntIntRunnable(count, i, runnable);
   }

   private static void onBooleanArrayPendingActionArrayRunnable(boolean[] flagArray, PendingAction[] pendingActionArray, Runnable runnable) {
      if (!flagArray[0]) {
         flagArray[0] = true;
         list.remove(pendingActionArray[0]);
         onRunnable(runnable);
      }
   }

   private static void onBooleanArrayPendingActionArrayIntConsumerInt(boolean[] flagArray, PendingAction[] pendingActionArray, IntConsumer intConsumer, int count) {
      if (!flagArray[0]) {
         flagArray[0] = true;
         list.remove(pendingActionArray[0]);
         if (intConsumer != null) {
            try {
               intConsumer.accept(count);
            } catch (Throwable throwable) {
            }
         }
      }
   }

   private static void onIntConsumer(IntConsumer intConsumer) {
      intConsumer.accept(-1);
   }

   public static void onModuleIntIntIntIntConsumer(Module module2, int count, int count2, int count3, IntConsumer intConsumer) {
      IntConsumer intconsumer = getIntConsumerByIntConsumerModule(intConsumer, module2);
      if (Feature.mc.player != null && check3()) {
         int i = SphereItems.getInt();
         int j = count + 36;
         int[] aint = new int[]{i};
         Runnable runnable = () -> InventoryActions.onIntConsumerIntArray(intconsumer, aint);
         ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2();
         Runnable runnable1 = () -> InventoryActions.onIntIntIntIntIntIntArray(j, j, j, j, j, aint);
         byte b0 = 1;
         actionqueue.getActionQueueByRunnableInt(runnable1, b0).update5();
      } else {
         intconsumer.accept(-3);
      }
   }

   public static void onModuleIntRunnableIntItemInt(Module module2, int count, Runnable runnable2, int count2, Item item2, int count3) {
      if (count == -3) {
         onRunnable(getRunnableByModuleRunnable(module2, runnable2));
      } else {
         int i = count3 + 36;
         Runnable runnable = getRunnableByModuleRunnable(module2, runnable2);
         ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2().getActionQueue();
         Runnable runnable1 = () -> InventoryActions.onIntIntIntItemInt(i, i, i, item2, i);
         byte b0 = 1;
         actionqueue.getActionQueueByRunnableInt(runnable1, b0).update5();
      }
   }

   public static void onModuleIntIntRunnableInt(Module module2, int count, int count2, Runnable runnable3, int count3) {
      Runnable runnable = getRunnableByModuleRunnable(module2, runnable3);
      if (Feature.mc.player != null && check3()) {
         int i = count + 36;
         TickCounter.setInt(2);
         ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2().getActionQueue();
         Runnable runnable1 = () -> InventoryActions.onIntIntInt(i, i, i);
         byte b0 = 1;
         actionqueue = actionqueue.getActionQueueByRunnableInt(runnable1, b0).getActionQueueByRunnable(InventoryActions::update7);
         Runnable runnable2 = () -> InventoryActions.onIntInt3(i, i);
         byte b1 = 1;
         actionqueue.getActionQueueByRunnableInt(runnable2, b1).update5();
      } else {
         onRunnable(runnable);
      }
   }

   private static int getIntByInt(int count) {
      if (check3()) {
         return -3;
      } else if (SphereItems.isInt(count)) {
         return -2;
      } else {
         int i = SphereItems.getInt();
         if (i < 0) {
            return -1;
         } else {
            SphereItems.onInt2(i);
            return i;
         }
      }
   }

   private static void addModule(Module module2) {
      if (!list.isEmpty()) {
         ArrayList<PendingAction> arraylist = new ArrayList();

         for (PendingAction pendingaction : list) {
            if (module2 == null || pendingaction.getOwner() == module2) {
               arraylist.add(pendingaction);
            }
         }

         list.removeAll(arraylist);

         for (PendingAction pendingaction1 : arraylist) {
            onRunnable(pendingaction1.getAbort());
         }
      }
   }

   public static void onIntRunnableModuleInt(int count, Runnable runnable2, Module module2, int count2) {
      Runnable runnable = getRunnableByModuleRunnable(module2, runnable2);
      ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2().getActionQueue();
      Runnable runnable1 = () -> InventoryActions.onIntInt4(count, count);
      byte b0 = 1;
      actionqueue.getActionQueueByRunnableInt(runnable1, b0).update5();
   }

   public static void onModuleIntRunnable(Module module2, int count, Runnable runnable2) {
      Runnable runnable = getRunnableByModuleRunnable(module2, runnable2);
      ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2().getActionQueue();
      Runnable runnable1 = () -> InventoryActions.onInt(count);
      byte b0 = 1;
      actionqueue.getActionQueueByRunnableInt(runnable1, b0).update5();
   }

   public static void onRunnableModuleInt(Runnable runnable3, Module module2, int count) {
      Runnable runnable = getRunnableByModuleRunnable(module2, runnable3);
      if (Feature.mc.player == null) {
         onRunnable(runnable);
      } else {
         int i = Feature.mc.player.getInventory().selectedSlot;
         TickCounter.setInt(2);
         ActionQueue actionqueue = getActionQueueByRunnableModule(runnable, module2).getActionQueue2().getActionQueue();
         Runnable runnable1 = () -> InventoryActions.onIntInt(i, i);
         byte b0 = 1;
         actionqueue = actionqueue.getActionQueueByRunnableInt(runnable1, b0).getActionQueueByRunnable(InventoryActions::update7);
         Runnable runnable2 = () -> InventoryActions.onIntInt5(i, i);
         byte b1 = 1;
         actionqueue.getActionQueueByRunnableInt(runnable2, b1).update5();
      }
   }

   public static void onIntIntRunnableModule(int count, int count2, Runnable runnable2, Module module2) {
      Runnable runnable = getRunnableByModuleRunnable(module2, runnable2);
      if (Feature.mc.player == null) {
         onRunnable(runnable);
      } else {
         TickCounter.setInt(2);
         onModule(module2);

         try {
            Feature.mc.player.getInventory().selectedSlot = count;
         } catch (Throwable throwable) {
            onModule2(module2);
            onRunnable(runnable);
            return;
         }

         SphereItems.onIntRunnable(1, InventoryActions::update7);
         SphereItems.onIntRunnable(2, () -> InventoryActions.onIntModuleRunnable(count, module2, runnable));
      }
   }

   private static ActionQueue getActionQueueByRunnableModule(Runnable runnable, Module module2) {
      return new ActionQueue(module2, runnable);
   }

   public static void onIntIntRunnable(int count, int count2, Runnable runnable2) {
      if (count == value3) {
         if (!SneakState.isFlag() && count2 > 0) {
            Runnable runnable = () -> InventoryActions.onIntRunnableInt(count, runnable2, count);
            byte b0 = 1;
            SphereItems.onRunnableInt(runnable, b0);
         } else {
            runnable2.run();
         }
      }
   }

   private static Runnable getRunnableByModuleRunnable(Module module2, Runnable runnable2) {
      boolean[] aboolean = new boolean[]{false};
      PendingAction[] apendingaction = new PendingAction[1];
      Runnable runnable = () -> InventoryActions.onBooleanArrayPendingActionArrayRunnable(aboolean, apendingaction, runnable2);
      apendingaction[0] = new PendingAction(module2, runnable);
      list.add(apendingaction[0]);
      return runnable;
   }

   private static IntConsumer getIntConsumerByIntConsumerModule(IntConsumer intConsumer, Module module2) {
      boolean[] aboolean = new boolean[]{false};
      PendingAction[] apendingaction = new PendingAction[1];
      IntConsumer intconsumer = p0 -> InventoryActions.onBooleanArrayPendingActionArrayIntConsumerInt(aboolean, apendingaction, intConsumer, p0);
      apendingaction[0] = new PendingAction(module2, () -> InventoryActions.onIntConsumer(intconsumer));
      list.add(apendingaction[0]);
      return intconsumer;
   }

   private static void onIntInt6(int count, int count2) {
      byte b0 = -1;
      SphereItems.onIntInt3(b0, count);
      SphereItems.onIntInt3(count2, count);
   }

   private static void onIntIntInt2(int count, int count2, int count3) {
      onIntInt2(count2, count3);
      SphereItems.onInt2(count);
   }

   private static boolean check3() {
      return SphereItems.getItemStack().isEmpty();
   }

   public static void update8() {
      deque.clear();
      if (value2 > 0) {
         update2();
      }

      value3++;
      addModule(null);
   }

   public static void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent != null && packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         Packet packet = packetEvent.getPacket();
         if (packet instanceof PlayerRespawnS2CPacket || packet instanceof PlayerPositionLookS2CPacket) {
            update8();
         }
      }
   }

   public static void update9() {
      Runnable runnable = InventoryActions::update5;
      byte b0 = 1;
      SphereItems.onRunnableInt(runnable, b0);
   }
}
