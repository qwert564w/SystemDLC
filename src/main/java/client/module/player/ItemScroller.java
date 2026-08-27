package client.module.player;

import client.module.Category;
import client.module.Module;
import client.util.UnsafeFields;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class ItemScroller extends Module {
   private UnsafeFields<Slot> unsafeFields;
   private Class<?> classValue;
   private boolean flag = false;
   private int value235 = -1;
   private final Set<Integer> set = new HashSet<>();

   public ItemScroller() {
      super("ItemScroller", Category.PLAYER);
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.interactionManager() != null) {
         boolean flagx = this.options().sneakKey.isPressed() || this.check3();
         boolean flag1 = this.check4();
         if (flagx && flag1) {
            if (this.currentScreen() instanceof HandledScreen handledscreen) {
               int i = this.player().currentScreenHandler.syncId;
               if (i != this.value235) {
                  this.set.clear();
                  this.value235 = i;
               }

               Slot slot = this.getSlotByHandledScreen(handledscreen);
               if (slot != null && slot.hasStack()) {
                  if (this.set.add(slot.id)) {
                     this.interactionManager().clickSlot(this.player().currentScreenHandler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, this.player());
                  }
               }
            } else {
               this.update11();
            }
         } else {
            this.update11();
         }
      } else {
         this.update11();
      }
   }

   private void update11() {
      this.value235 = -1;
      this.set.clear();
   }

   private boolean check3() {
      long i = this.client().getWindow().getHandle();
      return GLFW.glfwGetKey(i, 340) == 1 || GLFW.glfwGetKey(i, 344) == 1;
   }

   @Override
   public void onDisable() {
      this.update12();
   }

   private boolean check4() {
      return GLFW.glfwGetMouseButton(this.client().getWindow().getHandle(), 0) == 1;
   }

   private Slot getSlotByHandledScreen(HandledScreen handledScreen) {
      Class oclass = handledScreen.getClass();
      if (!this.flag || this.classValue != oclass || this.unsafeFields == null) {
         this.classValue = oclass;

         try {
            this.unsafeFields = new UnsafeFields<>(handledScreen, HandledScreen.class, Slot.class);
         } catch (Exception exception) {
            this.unsafeFields = null;
         }

         this.flag = true;
      }

      return this.unsafeFields == null ? null : (Slot)this.unsafeFields.getObjectByObject(handledScreen);
   }

   private void update12() {
      this.update11();
      this.classValue = null;
      this.unsafeFields = null;
      this.flag = false;
   }

   @Override
   public void onEnable() {
      this.update12();
   }
}
