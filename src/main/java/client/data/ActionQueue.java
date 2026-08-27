package client.data;

import client.module.Feature;
import client.module.Module;
import client.util.InventoryActions;
import client.util.SneakState;
import client.util.SphereItems;
import java.util.ArrayList;
import java.util.List;

public final class ActionQueue {
   private final Module module;
   private final Runnable runnable;
   private final List<ScheduledTask> list = new ArrayList<>(3);
   private boolean flag;
   private boolean flag2;
   private int value;

   public ActionQueue(Module module2, Runnable runnable2) {
      this.module = module2;
      this.runnable = runnable2;
   }

   private void update() {
      try {
         if (InventoryActions.check2()) {
            InventoryActions.update();
         }
      } finally {
         this.update3();
      }
   }

   private void update2() {
      if (this.flag2 && InventoryActions.check2()) {
         Runnable runnablex = this::update4;
         byte b0 = 1;
         SphereItems.onRunnableInt(runnablex, b0);
      } else {
         this.update3();
      }
   }

   public ActionQueue getActionQueue() {
      this.flag2 = true;
      return this;
   }

   private void update3() {
      if (this.flag) {
         InventoryActions.update9();
      }

      InventoryActions.onModule2(this.module);
      InventoryActions.onRunnable(this.runnable);
   }

   private void update4() {
      int j = this.value;
      Runnable runnable1 = this::update;
      byte b0 = 5;
      Runnable runnablex = runnable1;
      int i = j;
      InventoryActions.onIntIntRunnable(i, b0, runnablex);
   }

   private void onScheduledTaskInt(ScheduledTask scheduledTask, int count) {
      if (this.value == InventoryActions.value3) {
         try {
            SneakState.update4();
            scheduledTask.getAction().run();
         } catch (Throwable throwable) {
            this.update2();
            return;
         }

         this.onInt(count + 1);
      }
   }

   private void onRunnable(Runnable runnable) {
      byte b0 = 5;
      int i = this.value;
      InventoryActions.onIntIntRunnable(i, b0, runnable);
   }

   public ActionQueue getActionQueue2() {
      this.flag = true;
      return this;
   }

   public ActionQueue getActionQueueByRunnableInt(Runnable runnable, int count) {
      this.list.add(new ScheduledTask(count, false, runnable));
      return this;
   }

   public ActionQueue getActionQueueByRunnable(Runnable runnable) {
      this.list.add(new ScheduledTask(1, true, runnable));
      return this;
   }

   public void update5() {
      if (Feature.mc.player == null) {
         InventoryActions.onRunnable(this.runnable);
      } else {
         this.value = InventoryActions.value3;
         InventoryActions.onModule(this.module);
         if (this.flag) {
            InventoryActions.update6();
         }

         this.onInt(0);
      }
   }

   private void onInt(int count) {
      if (count >= this.list.size()) {
         this.update2();
      } else {
         ScheduledTask scheduledtask = this.list.get(count);
         Runnable runnablex = () -> this.onScheduledTaskInt(scheduledtask, count);
         if (scheduledtask.isAtTickStart()) {
            SphereItems.onIntRunnable(scheduledtask.getDelay(), runnablex);
         } else {
            int j = scheduledtask.getDelay();
            Runnable runnable1 = () -> this.onRunnable(runnablex);
            int i = j;
            SphereItems.onRunnableInt(runnable1, i);
         }
      }
   }
}
