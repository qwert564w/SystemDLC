package client.util;

import client.data.BundleSlot;
import client.data.TaskCapture;
import client.enums.SphereItemType;
import client.enums.TrackedItem;
import client.module.Feature;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.component.type.BundleContentsComponent.Builder;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

public class SphereItems {
   private static final List<TaskCapture> list = new ArrayList<>();
   private static final List<TaskCapture> list2 = new ArrayList<>();

   private static void update() {
      if (Feature.mc.player != null && Feature.mc.currentScreen == null) {
         Feature.mc.player.setSprinting(false);
      }
   }

   public static int getIntByItem(Item item2) {
      if (Feature.mc.player == null) {
         return -1;
      } else {
         PlayerInventory playerinventory = Feature.mc.player.getInventory();

         for (int i = 0; i < 9; i++) {
            if (playerinventory.getStack(i).getItem() == item2) {
               return i;
            }
         }

         for (int j = 9; j < playerinventory.size(); j++) {
            if (playerinventory.getStack(j).getItem() == item2) {
               return j;
            }
         }

         return -1;
      }
   }

   public static boolean isItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         return lorecomponent != null && !lorecomponent.lines().isEmpty();
      } else {
         return false;
      }
   }

   public static boolean isItemStack2(ItemStack itemStack) {
      if (isItemStack6(itemStack) && isItemStack9(itemStack)) {
         TrackedItem trackeditem = getTrackedItemByItemStack(itemStack);
         return trackeditem != null && trackeditem.flag2;
      } else {
         return false;
      }
   }

   public static void onInt(int count) {
      if (Feature.mc.player != null && count >= 0 && count <= 8) {
         if (Feature.mc.player.getInventory().selectedSlot != count) {
            Feature.mc.player.getInventory().selectedSlot = count;
            update2();
         }
      }
   }

   public static boolean isItemStackInt(ItemStack itemStack, int count) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
         CustomModelDataComponent custommodeldatacomponent = (CustomModelDataComponent)itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
         return custommodeldatacomponent != null && !custommodeldatacomponent.floats().isEmpty()
            ? (int)((Float)custommodeldatacomponent.floats().getFirst()).floatValue() == count
            : false;
      } else {
         return false;
      }
   }

   private static void onIntInt(int count, int count2) {
      if (Feature.mc.interactionManager != null && Feature.mc.player != null) {
         TickCounter.setInt(2);
         int i = Feature.mc.player.currentScreenHandler.syncId;
         Feature.mc.interactionManager.clickSlot(i, count2, count, SlotActionType.SWAP, Feature.mc.player);
      }
   }

   public static Slot getSlotByInt(int count) {
      if (Feature.mc.player == null) {
         return null;
      } else {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;

         for (Slot slot : defaultedlist) {
            if (slot.id >= 9 && slot.id <= 35) {
               ItemStack itemstack = slot.getStack();
               if (isIntItemStack(count, itemstack)) {
                  return slot;
               }
            }
         }

         for (Slot slot1 : defaultedlist) {
            if (slot1.id >= 36 && slot1.id <= 44) {
               ItemStack itemstack1 = slot1.getStack();
               if (isIntItemStack(count, itemstack1)) {
                  return slot1;
               }
            }
         }

         return null;
      }
   }

   public static boolean check() {
      return Feature.mc.player != null && Feature.mc.player.getInventory().getStack(38).isOf(Items.ELYTRA);
   }

   private static boolean isItemStack3(ItemStack itemStack) {
      NbtComponent nbtcomponent = (NbtComponent)itemStack.get(DataComponentTypes.CUSTOM_DATA);
      return nbtcomponent != null && !nbtcomponent.isEmpty() ? isStringString(nbtcomponent.copyNbt().toString(), "sphere-") : false;
   }

   public static boolean isInt(int count) {
      if (!isItemStackItemStack(getItemStackByInt(count), getItemStack())) {
         return false;
      } else {
         onInt2(count);
         return true;
      }
   }

   public static Slot getSlotByItem(Item item2) {
      byte b1 = 35;
      byte b0 = 9;
      Slot slot = getSlotByItemIntInt(item2, b1, b0);
      Slot slot1;
      if (slot != null) {
         slot1 = slot;
      } else {
         byte b3 = 44;
         byte b2 = 36;
         slot1 = getSlotByItemIntInt(item2, b3, b2);
      }

      return slot1;
   }

   public static int getIntByBoolean(boolean flag) {
      int i = getIntByBoolean2(flag);
      if (i == -1 && flag) {
         i = getIntByBoolean2(false);
      }

      return i;
   }

   public static void update2() {
      if (Feature.mc.interactionManager != null && Feature.mc.player != null) {
         SlotSyncAccess.onClientPlayerInteractionManager(Feature.mc.interactionManager);
      }
   }

   public static int getIntByItem2(Item item2) {
      boolean flag = false;
      byte b1 = 8;
      byte b0 = 0;
      return getIntByBooleanItemIntInt(flag, item2, b1, b0);
   }

   public static void update3() {
      removeList(list);
   }

   public static void onInt2(int count) {
      onIntInt2(count, 0);
   }

   private static boolean isItemStack4(ItemStack itemStack) {
      return isStringString(itemStack.getName().getString(), "сфера");
   }

   public static int getIntByItem3(Item item2) {
      int i = getIntByItem2(item2);
      return i == -1 ? -1 : 36 + i;
   }

   private static void onIntInt2(int count, int count2) {
      if (Feature.mc.interactionManager != null && Feature.mc.player != null) {
         TickCounter.setInt(2);
         int i = Feature.mc.player.currentScreenHandler.syncId;
         Feature.mc.interactionManager.clickSlot(i, count, count2, SlotActionType.PICKUP, Feature.mc.player);
      }
   }

   public static boolean isItemStack5(ItemStack itemStack) {
      return isItemStack6(itemStack) && !isItemStack9(itemStack);
   }

   public static boolean isItemStack6(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == Items.TOTEM_OF_UNDYING;
   }

   public static int getIntByItem4(Item item2) {
      boolean flag = true;
      int i = getIntByBooleanItem(flag, item2);
      int j;
      if (i != -1) {
         j = i;
      } else {
         boolean flag1 = false;
         j = getIntByBooleanItem(flag1, item2);
      }

      return j;
   }

   private static boolean isItemStack7(ItemStack itemStack) {
      AttributeModifiersComponent attributemodifierscomponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributemodifierscomponent == null) {
         return false;
      } else {
         for (Entry entry : attributemodifierscomponent.modifiers()) {
            AttributeModifierSlot attributemodifierslot = entry.slot();
            if (attributemodifierslot == AttributeModifierSlot.OFFHAND || attributemodifierslot == AttributeModifierSlot.HAND) {
               return true;
            }
         }

         return false;
      }
   }

   public static void onIntInt3(int count, int count2) {
      if (Feature.mc.player != null && Feature.mc.player.networkHandler != null) {
         TickCounter.setInt(2);
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;
         if (count2 >= 0 && count2 < defaultedlist.size()) {
            BundleItem.setSelectedStackIndex(((Slot)defaultedlist.get(count2)).getStack(), count);
         }

         Feature.mc.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(count2, count));
      }
   }

   public static int getIntByItem5(Item item2) {
      boolean flag = false;
      byte b1 = 35;
      byte b0 = 9;
      return getIntByBooleanItemIntInt(flag, item2, b1, b0);
   }

   public static void update4() {
      list.clear();
      list2.clear();
      SneakState.update3();
   }

   public static void onInt3(int count) {
      onIntInt2(count, 1);
   }

   public static ItemStack getItemStackByInt(int count) {
      if (Feature.mc.player == null) {
         return ItemStack.EMPTY;
      } else {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;
         return count >= 0 && count < defaultedlist.size() ? ((Slot)defaultedlist.get(count)).getStack() : ItemStack.EMPTY;
      }
   }

   public static void onIntInt4(int count, int count2) {
      if (count2 >= 0 && count2 <= 8) {
         update();
         onIntInt(count2, count);
      }
   }

   public static ItemStack getItemStack() {
      return Feature.mc.player == null ? ItemStack.EMPTY : Feature.mc.player.currentScreenHandler.getCursorStack();
   }

   public static int getIntByIntItem(int count, Item item2) {
      if (item2 == null) {
         return -1;
      } else {
         BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)getItemStackByInt(count).get(DataComponentTypes.BUNDLE_CONTENTS);
         if (bundlecontentscomponent == null) {
            return -1;
         } else {
            for (int i = 0; i < bundlecontentscomponent.size(); i++) {
               if (bundlecontentscomponent.get(i).getItem() == item2) {
                  return i;
               }
            }

            return -1;
         }
      }
   }

   private static void onRunnable(Runnable runnable) {
      try {
         runnable.run();
      } catch (Throwable throwable) {
      }
   }

   public static int getIntByItemBoolean(Item item2, boolean flag) {
      if (Feature.mc.player == null) {
         return 0;
      } else {
         int i = 0;

         for (int j = 0; j < 36; j++) {
            ItemStack itemstack = Feature.mc.player.getInventory().getStack(j);
            if (itemstack.isOf(item2) && (!flag || itemstack.getEnchantments().isEmpty())) {
               i++;
            }
         }

         return i;
      }
   }

   public static int getIntByInt(int count) {
      return count < 9 ? count + 36 : count;
   }

   public static int getIntByBooleanItemIntInt(boolean flag, Item item2, int count, int count2) {
      if (item2 != null && Feature.mc.player != null) {
         for (int i = count2; i <= count; i++) {
            ItemStack itemstack = Feature.mc.player.getInventory().getStack(i);
            if (itemstack.isOf(item2) && (!flag || itemstack.getEnchantments().isEmpty())) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static ItemStack getItemStackByTrackedItem(TrackedItem trackedItem) {
      if (trackedItem != null && trackedItem.item != null) {
         ItemStack itemstack = new ItemStack(trackedItem.item);
         if (trackedItem.value >= 0) {
            itemstack.set(
               DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(trackedItem.value), List.of(), Optional.empty())
            );
         }

         return itemstack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public static PlayerInventory getPlayerInventory() {
      return Feature.mc.player != null ? Feature.mc.player.getInventory() : null;
   }

   public static BundleSlot getBundleSlotByPredicate(Predicate<ItemStack> predicate) {
      if (Feature.mc.player != null && predicate != null) {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;
         byte b1 = 35;
         byte b0 = 9;
         BundleSlot bundleslot = getBundleSlotByIntListIntPredicate(b1, defaultedlist, b0, predicate);
         if (bundleslot != null) {
            return bundleslot;
         } else {
            byte b3 = 44;
            byte b2 = 36;
            return getBundleSlotByIntListIntPredicate(b3, defaultedlist, b2, predicate);
         }
      } else {
         return null;
      }
   }

   public static void onConsumer(Consumer<ItemStack> consumer) {
      if (Feature.mc.player != null && consumer != null) {
         for (Slot slot : Feature.mc.player.currentScreenHandler.slots) {
            if (slot.id >= 9 && slot.id <= 44) {
               ItemStack itemstack = slot.getStack();
               if (itemstack != null && !itemstack.isEmpty()) {
                  BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)itemstack.get(DataComponentTypes.BUNDLE_CONTENTS);
                  if (bundlecontentscomponent != null && !bundlecontentscomponent.isEmpty()) {
                     for (int i = 0; i < bundlecontentscomponent.size(); i++) {
                        ItemStack itemstack1 = bundlecontentscomponent.get(i);
                        if (itemstack1 != null && !itemstack1.isEmpty()) {
                           consumer.accept(itemstack1);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static BundleSlot getBundleSlotByIntListIntPredicate(int count, List<Slot> list, int count2, Predicate<ItemStack> predicate) {
      for (Slot slot : list) {
         if (slot.id >= count2 && slot.id <= count) {
            ItemStack itemstack = slot.getStack();
            if (itemstack != null && !itemstack.isEmpty()) {
               BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)itemstack.get(DataComponentTypes.BUNDLE_CONTENTS);
               if (bundlecontentscomponent != null && !bundlecontentscomponent.isEmpty()) {
                  for (int i = 0; i < bundlecontentscomponent.size(); i++) {
                     ItemStack itemstack1 = bundlecontentscomponent.get(i);
                     if (itemstack1 != null && !itemstack1.isEmpty() && predicate.test(itemstack1)) {
                        return new BundleSlot(slot, i);
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   public static void onRunnableIntInt(Runnable runnable2, int count, int count2) {
      SneakState.update5();
      Runnable runnable = () -> SphereItems.onIntIntRunnable(count, count, runnable2);
      byte b0 = 1;
      onRunnableInt(runnable, b0);
   }

   public static void onInt4(int count) {
      update();
      byte b0 = 40;
      onIntInt(b0, count);
   }

   public static int getInt() {
      if (Feature.mc.player == null) {
         return -1;
      } else {
         for (Slot slot : Feature.mc.player.currentScreenHandler.slots) {
            if (slot.id >= 9 && slot.id <= 35 && slot.getStack().isEmpty()) {
               return slot.id;
            }
         }

         return -1;
      }
   }

   private static void onIntIntRunnable(int count, int count2, Runnable runnable) {
      try {
         SneakState.update4();
         onIntInt4(count, count2);
      } finally {
         SneakState.update2();
         if (runnable != null) {
            onRunnable(runnable);
         }
      }
   }

   public static boolean isItemStackItemStack(ItemStack itemStack, ItemStack itemStack2) {
      if (itemStack != null && itemStack2 != null && !itemStack2.isEmpty()) {
         BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)itemStack.get(DataComponentTypes.BUNDLE_CONTENTS);
         return bundlecontentscomponent == null ? false : new Builder(bundlecontentscomponent).add(itemStack2.copy()) == itemStack2.getCount();
      } else {
         return false;
      }
   }

   public static boolean isItemStackInt2(ItemStack itemStack, int count) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == Items.SPLASH_POTION) {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         return potioncontentscomponent != null
            && potioncontentscomponent.customColor().isPresent()
            && (Integer)potioncontentscomponent.customColor().get() == count;
      } else {
         return false;
      }
   }

   public static Slot getSlotByInt2(int count) {
      if (Feature.mc.player == null) {
         return null;
      } else {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;

         for (Slot slot : defaultedlist) {
            if (slot.id >= 9 && slot.id <= 35 && isItemStackInt2(slot.getStack(), count)) {
               return slot;
            }
         }

         for (Slot slot1 : defaultedlist) {
            if (slot1.id >= 36 && slot1.id <= 44 && isItemStackInt2(slot1.getStack(), count)) {
               return slot1;
            }
         }

         return null;
      }
   }

   public static boolean isStringString(String text2, String text3) {
      int i = text2.length() - text3.length();

      for (int j = 0; j <= i; j++) {
         if (text2.regionMatches(true, j, text3, 0, text3.length())) {
            return true;
         }
      }

      return false;
   }

   public static boolean isStringItemStack(String text2, ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && text2 != null) {
         LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (lorecomponent == null) {
            return false;
         } else {
            for (Text text : lorecomponent.lines()) {
               if (text != null) {
                  String s = text.getString();
                  if (s != null && isStringString(s, text2)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean isIntItemStack(int count, ItemStack itemStack) {
      return isItemStackInt(itemStack, count);
   }

   public static TrackedItem getTrackedItemByItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
         CustomModelDataComponent custommodeldatacomponent = (CustomModelDataComponent)itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
         return custommodeldatacomponent != null && !custommodeldatacomponent.floats().isEmpty()
            ? TrackedItem.getTrackedItemByInt((int)((Float)custommodeldatacomponent.floats().getFirst()).floatValue())
            : null;
      } else {
         return null;
      }
   }

   public static boolean isItemStackString(ItemStack itemStack, String text2) {
      return itemStack != null && !itemStack.isEmpty() ? isStringString(itemStack.getName().getString(), text2) : false;
   }

   public static Slot getSlotByItemIntInt(Item item2, int count, int count2) {
      if (item2 != null && Feature.mc.player != null) {
         for (Slot slot : Feature.mc.player.currentScreenHandler.slots) {
            if (slot.id >= count2 && slot.id <= count && slot.getStack().getItem() == item2) {
               return slot;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static boolean check2() {
      return !list.isEmpty() || !list2.isEmpty();
   }

   private static void removeList(List<TaskCapture> list) {
      if (!list.isEmpty()) {
         int i = list.size();

         for (int j = 0; j < i; j++) {
            TaskCapture taskcapture = list.get(j);
            taskcapture.value--;
            if (taskcapture.value <= 0) {
               try {
                  taskcapture.runnable.run();
               } catch (Throwable throwable) {
               }

               list.remove(j);
               j--;
               i--;
            }
         }
      }
   }

   public static void update5() {
      removeList(list2);
   }

   public static Slot getSlotByString(String text2) {
      return getSlotByStringString(text2, null);
   }

   public static Slot getSlotByStringString(String text2, String text3) {
      if (Feature.mc.player == null) {
         return null;
      } else if (text2 == null && text3 == null) {
         return null;
      } else {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;
         String s = text2 == null ? null : text2.toLowerCase();
         String s1 = text3 == null ? null : text3.toLowerCase();

         for (Slot slot : defaultedlist) {
            if (slot.id >= 9 && slot.id <= 35) {
               ItemStack itemstack = slot.getStack();
               if (isItemStackStringString(itemstack, s1, s)) {
                  return slot;
               }
            }
         }

         for (Slot slot1 : defaultedlist) {
            if (slot1.id >= 36 && slot1.id <= 44) {
               ItemStack itemstack1 = slot1.getStack();
               if (isItemStackStringString(itemstack1, s1, s)) {
                  return slot1;
               }
            }
         }

         return null;
      }
   }

   private static boolean isItemStackStringString(ItemStack itemStack, String text2, String text3) {
      return text3 != null && isItemStackString(itemStack, text3) ? true : text2 != null && isStringItemStack(text2, itemStack);
   }

   public static int getIntByBoolean2(boolean flag) {
      if (Feature.mc.player == null) {
         return -1;
      } else {
         for (int i = 0; i < 36; i++) {
            ItemStack itemstack = Feature.mc.player.getInventory().getStack(i);
            if (isItemStack5(itemstack) && (!flag || itemstack.getEnchantments().isEmpty())) {
               return i;
            }
         }

         return -1;
      }
   }

   public static void onIntRunnable(int count, Runnable runnable) {
      if (runnable != null) {
         if (count <= 0) {
            runnable.run();
         } else {
            list2.add(new TaskCapture(count, runnable));
         }
      }
   }

   public static void onRunnableInt(Runnable runnable, int count) {
      if (runnable != null) {
         if (count <= 0) {
            runnable.run();
         } else {
            list.add(new TaskCapture(count, runnable));
         }
      }
   }

   public static boolean isItemStack8(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() ? isItemStack3(itemStack) || isItemStack7(itemStack) || isItemStack4(itemStack) : false;
   }

   public static SlotEntry getSlotEntry() {
      if (Feature.mc.player == null) {
         return null;
      } else {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;

         for (SphereItemType sphereitemtype : SphereItemType.values()) {
            byte b1 = 35;
            byte b0 = 9;
            SlotEntry slotentry = getSlotEntryByListIntSphereItemTypeInt(defaultedlist, b0, sphereitemtype, b1);
            if (slotentry != null) {
               return slotentry;
            }

            byte b3 = 44;
            byte b2 = 36;
            slotentry = getSlotEntryByListIntSphereItemTypeInt(defaultedlist, b2, sphereitemtype, b3);
            if (slotentry != null) {
               return slotentry;
            }
         }

         return null;
      }
   }

   private static SlotEntry getSlotEntryByListIntSphereItemTypeInt(List<Slot> list, int count, SphereItemType sphereItemType, int count2) {
      for (Slot slot : list) {
         if (slot.id >= count && slot.id <= count2) {
            ItemStack itemstack = slot.getStack();
            if (isItemStack6(itemstack)) {
               TrackedItem trackeditem = getTrackedItemByItemStack(itemstack);
               if (getSphereItemTypeByItemStackTrackedItem(itemstack, trackeditem) == sphereItemType) {
                  return new SlotEntry(slot, trackeditem != null ? trackeditem : TrackedItem.TOTEM_OF_UNDYING);
               }
            }
         }
      }

      return null;
   }

   private static SphereItemType getSphereItemTypeByItemStackTrackedItem(ItemStack itemStack, TrackedItem trackedItem) {
      if (!isItemStack9(itemStack)) {
         return SphereItemType.PLAIN;
      } else if (trackedItem == null) {
         return SphereItemType.UNKNOWN;
      } else {
         return trackedItem.flag2 ? SphereItemType.SACRIFICIAL : SphereItemType.VALUABLE;
      }
   }

   public static int getIntByClass(Class value) {
      if (value != null && Feature.mc.player != null) {
         for (int i = 0; i < 9; i++) {
            if (value.isInstance(Feature.mc.player.getInventory().getStack(i).getItem())) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static int getIntByBooleanItem(boolean flag, Item item2) {
      byte b1 = 35;
      byte b0 = 0;
      return getIntByBooleanItemIntInt(flag, item2, b1, b0);
   }

   public static int getIntByItem6(Item item2) {
      boolean flag = false;
      return getIntByBooleanItem(flag, item2);
   }

   public static boolean isInt2(int count) {
      return count >= 36 && count <= 44;
   }

   public static Slot getSlotByItem2(Item item2) {
      byte b1 = 44;
      byte b0 = 36;
      Slot slot = getSlotByItemIntInt(item2, b1, b0);
      Slot slot1;
      if (slot != null) {
         slot1 = slot;
      } else {
         byte b3 = 35;
         byte b2 = 9;
         slot1 = getSlotByItemIntInt(item2, b3, b2);
      }

      return slot1;
   }

   public static Slot getSlotByPredicate(Predicate<ItemStack> predicate) {
      if (Feature.mc.player != null && predicate != null) {
         DefaultedList<Slot> defaultedlist = Feature.mc.player.currentScreenHandler.slots;

         for (Slot slot : defaultedlist) {
            if (slot.id >= 9 && slot.id <= 35 && predicate.test(slot.getStack())) {
               return slot;
            }
         }

         for (Slot slot1 : defaultedlist) {
            if (slot1.id >= 36 && slot1.id <= 44 && predicate.test(slot1.getStack())) {
               return slot1;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static boolean isItemStack9(ItemStack itemStack) {
      CustomModelDataComponent custommodeldatacomponent = (CustomModelDataComponent)itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
      return custommodeldatacomponent == null
         ? false
         : !custommodeldatacomponent.floats().isEmpty()
            || !custommodeldatacomponent.strings().isEmpty()
            || !custommodeldatacomponent.flags().isEmpty()
            || !custommodeldatacomponent.colors().isEmpty();
   }
}
