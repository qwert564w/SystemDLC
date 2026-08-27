package client.module.player;

import client.api.Icon;
import client.data.AnimatedFloat;
import client.data.BundleSlot;
import client.data.JumpSwapState;
import client.enums.SwapMode;
import client.enums.SwapProjectile;
import client.enums.TrackedItem;
import client.module.Category;
import client.module.Module;
import client.setting.CompactGroupSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.util.InventoryActions;
import client.util.ItemCooldowns;
import client.util.NotificationManager;
import client.util.SlotEntry;
import client.util.SneakState;
import client.util.SphereItems;
import client.util.StringParts;
import client.util.SwapState;
import client.util.TickCounter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.input.Input;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class AutoSwap extends Module {
   private static final TrackedItem[] trackedItemArray;
   private static final ItemStack itemStack = new ItemStack(Items.PLAYER_HEAD);
   private static final ItemStack itemStack2 = new ItemStack(Items.TOTEM_OF_UNDYING);
   private static final ItemStack itemStack3 = new ItemStack(Items.MACE);
   private ListSetting server;
   private ListSetting mode;
   private HotkeySetting bindSvapa;
   private HotkeySetting bindTotemSfera;
   private HotkeySetting bindSferaSfera;
   private HotkeySetting bindTotemTotem;
   private HotkeySetting keyZaryadaVetra;
   private HotkeySetting keyZaryadaCBulavoy;
   private HotkeySetting keyPerla;
   private HotkeySetting keyHorusa;
   private HotkeySetting keyOpyta;
   private final HotkeySetting[] hotkeySettingArray;
   private final HotkeySetting[] hotkeySettingArray2;
   private final boolean[] booleanArray;
   private final boolean[] booleanArray2;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private boolean flag7;
   private boolean flag8;
   private boolean flag9;
   private final JumpSwapState jumpSwapState;
   private final SwapState swapState;
   private final SwapState swapState2;

   public AutoSwap() {
      super("AutoSwap", Category.PLAYER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"Ф", "Т"}), StringParts.join(new String[]{"Х", "В"})),
         List.of(StringParts.join(new String[]{"Ф", "Т"})),
         false
      );
      listsetting.setName("Сервер");
      listsetting.setDescription("Выбор набора биндов под конкретный сервер");
      this.server = listsetting;
      listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"Т", "о", "т", "е", "м", " ", "н", "а", " ", "с", "ф", "е", "р", "у"}),
            StringParts.join(new String[]{"С", "ф", "е", "р", "а", " ", "н", "а", " ", "с", "ф", "е", "р", "у"}),
            StringParts.join(new String[]{"Т", "а", "л", "и", "к", " ", "н", "а", " ", "т", "а", "л", "и", "к"}),
            StringParts.join(new String[]{"В", "c", "е"})
         ),
         List.of(StringParts.join(new String[]{"Т", "о", "т", "е", "м", " ", "н", "а", " ", "с", "ф", "е", "р", "у"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Режим свапа");
      this.mode = listsetting;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1);
      hotkeysetting.setName("Бинд свапа");
      hotkeysetting.setDescription("Кнопка для свапа в offhand");
      this.bindSvapa = hotkeysetting;
      HotkeySetting hotkeysetting1 = new HotkeySetting("", "", -1);
      hotkeysetting1.setName("Бинд тотем→сфера");
      hotkeysetting1.setDescription("Кнопка для свапа тотема на сферу");
      this.bindTotemSfera = hotkeysetting1;
      HotkeySetting hotkeysetting2 = new HotkeySetting("", "", -1);
      hotkeysetting2.setName("Бинд сфера→сфера");
      hotkeysetting2.setDescription("Кнопка для свапа сферы на сферу");
      this.bindSferaSfera = hotkeysetting2;
      HotkeySetting hotkeysetting3 = new HotkeySetting("", "", -1);
      hotkeysetting3.setName("Бинд тотем→тотем");
      hotkeysetting3.setDescription("Кнопка для свапа тотема на тотем");
      this.bindTotemTotem = hotkeysetting3;
      HotkeySetting hotkeysetting4 = new HotkeySetting("", "", -1, this::update13);
      hotkeysetting4.setName("Клавиша заряда ветра");
      hotkeysetting4.setDescription("Кнопка для броска заряда ветра");
      this.keyZaryadaVetra = hotkeysetting4;
      HotkeySetting hotkeysetting5 = new HotkeySetting("", "", -1, this::update22);
      hotkeysetting5.setName("Клавиша заряда c булавой");
      hotkeysetting5.setDescription("Тот же бросок заряда ветра, после него булава берётся в руку");
      this.keyZaryadaCBulavoy = hotkeysetting5;
      HotkeySetting hotkeysetting6 = new HotkeySetting("", "", 2);
      hotkeysetting6.setName("Клавиша пёрла");
      hotkeysetting6.setDescription("Кнопка для броска эндер-пёрла");
      this.keyPerla = hotkeysetting6;
      HotkeySetting hotkeysetting7 = new HotkeySetting("", "", -1);
      hotkeysetting7.setName("Клавиша хоруса");
      hotkeysetting7.setDescription("Удерживай — свапает хорус в руку и ест, после отпускания возвращает предмет");
      this.keyHorusa = hotkeysetting7;
      HotkeySetting hotkeysetting8 = new HotkeySetting("", "", -1);
      hotkeysetting8.setName("Клавиша опыта");
      hotkeysetting8.setDescription("Удерживай — свапает бутылки опыта в руку и кидает, после отпускания возвращает предмет");
      this.keyOpyta = hotkeysetting8;
      this.booleanArray = new boolean[trackedItemArray.length];
      this.booleanArray2 = new boolean[SwapProjectile.values().length];
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.flag8 = false;
      this.flag9 = false;
      this.jumpSwapState = new JumpSwapState(this::setBoolean);
      this.swapState = new SwapState();
      this.swapState2 = new SwapState();
      this.hotkeySettingArray = new HotkeySetting[trackedItemArray.length];

      for (int i = 0; i < trackedItemArray.length; i++) {
         this.hotkeySettingArray[i] = new HotkeySetting("ФТ: " + trackedItemArray[i].text, "Кнопка для использования: " + trackedItemArray[i].text, -1);
      }

      SwapProjectile[] aswapprojectile = SwapProjectile.values();
      int[] aint = new int[]{90, 88, 67, 86, 66, 78};
      this.hotkeySettingArray2 = new HotkeySetting[aswapprojectile.length];

      for (int j = 0; j < aswapprojectile.length; j++) {
         int k = j < aint.length ? aint[j] : -1;
         this.hotkeySettingArray2[j] = new HotkeySetting("ХВ: " + aswapprojectile[j].text, "Кнопка для использования: " + aswapprojectile[j].text, k);
      }

      Supplier<Boolean> supplier1 = this::getBoolean2;
      Supplier<Boolean> supplier2 = this::getBoolean;
      Supplier<Boolean> supplier = this::getBoolean3;
      this.bindSvapa.setVisibleWhen(() -> AutoSwap.getBooleanBySupplier(supplier));
      this.bindTotemSfera.setVisibleWhen(supplier);
      this.bindSferaSfera.setVisibleWhen(supplier);
      this.bindTotemTotem.setVisibleWhen(supplier);
      EnumSet enumset = EnumSet.of(TrackedItem.SHULKER, TrackedItem.CROSSBOW);
      IdentityHashMap identityhashmap = new IdentityHashMap();

      for (int l = 0; l < trackedItemArray.length; l++) {
         identityhashmap.put(this.hotkeySettingArray[l], trackedItemArray[l]);
      }

      identityhashmap.put(this.keyZaryadaVetra, TrackedItem.WIND_CHARGE);
      identityhashmap.put(this.keyPerla, TrackedItem.ENDER_PEARL);
      identityhashmap.put(this.keyHorusa, TrackedItem.CHORUS_FRUIT);
      identityhashmap.put(this.keyOpyta, TrackedItem.EXPERIENCE_BOTTLE);
      ArrayList<Setting> arraylist1 = new ArrayList();
      arraylist1.add(this.keyZaryadaVetra);
      arraylist1.add(this.keyZaryadaCBulavoy);
      arraylist1.add(this.keyPerla);
      arraylist1.add(this.keyHorusa);
      arraylist1.add(this.keyOpyta);

      for (int i1 = 0; i1 < trackedItemArray.length; i1++) {
         if (enumset.contains(trackedItemArray[i1])) {
            arraylist1.add(this.hotkeySettingArray[i1]);
         }
      }

      CompactGroupSetting compactgroupsetting3 = new CompactGroupSetting("", "", arraylist1.toArray(new Setting[0]));
      compactgroupsetting3.setName("Общие клавиши");
      compactgroupsetting3.setDescription("Клавиши, работающие на обоих серверах.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting3;
      compactgroupsetting.getCompactGroupSettingByFunction2(p0 -> this.getItemStackByIdentityHashMapSetting2(identityhashmap, p0));
      ArrayList<Setting> arraylist = new ArrayList<>();

      for (int j1 = 0; j1 < trackedItemArray.length; j1++) {
         if (!enumset.contains(trackedItemArray[j1])) {
            arraylist.add(this.hotkeySettingArray[j1]);
         }
      }

      compactgroupsetting3 = new CompactGroupSetting("", "", arraylist.toArray(new Setting[0]));
      compactgroupsetting3.setName("Клавиши ФТ");
      compactgroupsetting3.setDescription("Укажите настройки клавиш для каждого предмета.");
      CompactGroupSetting compactgroupsetting1 = compactgroupsetting3;
      compactgroupsetting1.getCompactGroupSettingByFunction2(p0 -> AutoSwap.getItemStackByIdentityHashMapSetting(identityhashmap, p0));
      compactgroupsetting1.setVisibleWhen(supplier1);
      IdentityHashMap identityhashmap1 = new IdentityHashMap();

      for (int k1 = 0; k1 < aswapprojectile.length; k1++) {
         identityhashmap1.put(this.hotkeySettingArray2[k1], aswapprojectile[k1]);
      }

      compactgroupsetting3 = new CompactGroupSetting("", "", this.hotkeySettingArray2);
      compactgroupsetting3.setName("Клавиши ХВ");
      compactgroupsetting3.setDescription("Укажите настройки клавиш для каждого предмета.");
      CompactGroupSetting compactgroupsetting2 = compactgroupsetting3;
      compactgroupsetting2.getCompactGroupSettingByFunction2(p0 -> AutoSwap.getItemStackByIdentityHashMapSetting3(identityhashmap1, p0));
      compactgroupsetting2.setVisibleWhen(supplier2);
      this.addSettings(
         new Setting[]{
            this.server,
            this.mode,
            this.bindSvapa,
            this.bindTotemSfera,
            this.bindSferaSfera,
            this.bindTotemTotem,
            compactgroupsetting,
            compactgroupsetting1,
            compactgroupsetting2
         }
      );
   }

   static {
      ArrayList<TrackedItem> arraylist = new ArrayList<>();

      for (TrackedItem trackeditem : TrackedItem.values()) {
         if (trackeditem.text2 != null || trackeditem.text3 != null) {
            arraylist.add(trackeditem);
         }
      }

      trackedItemArray = arraylist.toArray(new TrackedItem[0]);
   }

   private Boolean getBoolean() {
      return "ХВ".equals(this.server.getString2());
   }

   private static ItemStack getItemStackByIdentityHashMapSetting(IdentityHashMap identityHashMap, Setting setting2) {
      TrackedItem trackeditem = (TrackedItem)identityHashMap.get(setting2);
      return getItemStackByTrackedItem(trackeditem);
   }

   private void update11() {
      long i = this.client().getWindow().getHandle();
      int j = getIntByLong(i);
      SwapProjectile[] aswapprojectile = SwapProjectile.values();

      for (int k = 0; k < aswapprojectile.length; k++) {
         HotkeySetting hotkeysetting = this.hotkeySettingArray2[k];
         int l = hotkeysetting.getValue();
         if (l != -1) {
            boolean flagx = isIntHotkeySetting(j, hotkeysetting);
            if (flagx && !this.booleanArray2[k]) {
               this.isSwapProjectile(aswapprojectile[k]);
            }

            this.booleanArray2[k] = flagx;
         }
      }
   }

   private static boolean isTrackedItemItemStack(TrackedItem trackedItem, ItemStack itemStack) {
      return isTrackedItemItemStack4(trackedItem, itemStack);
   }

   private static boolean isTrackedItemItemStack2(TrackedItem trackedItem, ItemStack itemStack) {
      return isTrackedItemItemStack4(trackedItem, itemStack);
   }

   private void setFlag6() {
      this.flag6 = false;
   }

   private ItemStack getItemStackByIdentityHashMapSetting2(IdentityHashMap identityHashMap, Setting setting2) {
      if (setting2 == this.keyZaryadaCBulavoy) {
         return itemStack3;
      } else {
         TrackedItem trackeditem = (TrackedItem)identityHashMap.get(setting2);
         return getItemStackByTrackedItem(trackeditem);
      }
   }

   private Boolean getBoolean2() {
      return "ФТ".equals(this.server.getString2());
   }

   private void setFlag62() {
      this.flag6 = false;
   }

   private static boolean isTrackedItemItemStack3(TrackedItem trackedItem, ItemStack itemStack) {
      return isTrackedItemItemStack4(trackedItem, itemStack);
   }

   private void setFlag9() {
      this.flag9 = false;
   }

   public boolean isTrackedItem(TrackedItem trackedItem) {
      if (this.interactionManager() != null && this.player() != null && !this.check4()) {
         Slot slot = this.getSlotByTrackedItem(trackedItem);
         if (slot == null) {
            return false;
         } else {
            ItemStack itemstack = slot.getStack();
            if (this.isItemStackTrackedItem(itemstack, trackedItem)) {
               return false;
            } else {
               this.setInt(slot.id);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void onTick() {
      SneakState.update4();
      this.jumpSwapState.update5();
      this.update19();
      TrackedItem trackeditem = TrackedItem.CHORUS_FRUIT;
      Item item = Items.CHORUS_FRUIT;
      HotkeySetting hotkeysetting = this.keyHorusa;
      SwapState swapstate = this.swapState;
      this.onHotkeySettingItemSwapStateTrackedItem(hotkeysetting, item, swapstate, trackeditem);
      TrackedItem trackeditem1 = TrackedItem.EXPERIENCE_BOTTLE;
      Item item1 = Items.EXPERIENCE_BOTTLE;
      HotkeySetting hotkeysetting1 = this.keyOpyta;
      SwapState swapstate1 = this.swapState2;
      this.onHotkeySettingItemSwapStateTrackedItem(hotkeysetting1, item1, swapstate1, trackeditem1);
      if (this.client().currentScreen == null) {
         boolean flagx = "ХВ".equals(this.server.getString2());
         this.onBoolean(flagx);
         this.update17();
         this.update21();
         if (flagx) {
            this.update11();
         }
      }

      this.update14();
   }

   private static boolean isTrackedItem2(TrackedItem trackedItem) {
      return trackedItem == TrackedItem.SHULKER || trackedItem == TrackedItem.CROSSBOW;
   }

   private static ItemStack getItemStackByTrackedItem(TrackedItem trackedItem) {
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

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (JumpSwapState.getFloat() != null) {
         animatedFloat.setValue(90.0F);
         animatedFloat.setFlag(true);
      }
   }

   private boolean check3() {
      TrackedItem trackeditem = TrackedItem.CHORUS_FRUIT;
      Item item = Items.CHORUS_FRUIT;
      HotkeySetting hotkeysetting1 = this.keyHorusa;
      SwapState swapstate = this.swapState;
      if (this.isHotkeySettingSwapStateItemTrackedItem(hotkeysetting1, swapstate, item, trackeditem)) {
         return true;
      } else {
         TrackedItem trackeditem1 = TrackedItem.EXPERIENCE_BOTTLE;
         Item item1 = Items.EXPERIENCE_BOTTLE;
         HotkeySetting hotkeysetting2 = this.keyOpyta;
         SwapState swapstate1 = this.swapState2;
         if (this.isHotkeySettingSwapStateItemTrackedItem(hotkeysetting2, swapstate1, item1, trackeditem1)) {
            return true;
         } else {
            boolean flag1 = this.flag5;
            HotkeySetting hotkeysetting3 = this.keyPerla;
            if (isBooleanHotkeySetting(flag1, hotkeysetting3) && this.isTrackedItem5(TrackedItem.ENDER_PEARL)) {
               return true;
            } else {
               if ("Вcе".equals(this.mode.getString2())) {
                  SwapMode swapmode = SwapMode.TOTEM_TO_SPHERE;
                  boolean flag2x = this.flag2;
                  HotkeySetting hotkeysetting4 = this.bindTotemSfera;
                  if (this.isSwapModeBooleanHotkeySetting(swapmode, flag2x, hotkeysetting4)) {
                     return true;
                  }

                  SwapMode swapmode1 = SwapMode.SPHERE_TO_SPHERE;
                  boolean flag3x = this.flag3;
                  HotkeySetting hotkeysetting5 = this.bindSferaSfera;
                  if (this.isSwapModeBooleanHotkeySetting(swapmode1, flag3x, hotkeysetting5)) {
                     return true;
                  }

                  SwapMode swapmode2 = SwapMode.TOTEM_TO_TOTEM;
                  boolean flag4x = this.flag4;
                  HotkeySetting hotkeysetting6 = this.bindTotemTotem;
                  if (this.isSwapModeBooleanHotkeySetting(swapmode2, flag4x, hotkeysetting6)) {
                     return true;
                  }
               } else {
                  HotkeySetting hotkeysetting8 = this.bindSvapa;
                  boolean flag6x = this.flag;
                  SwapMode swapmode3 = getSwapModeByString(this.mode.getString2());
                  boolean flag5x = flag6x;
                  HotkeySetting hotkeysetting7 = hotkeysetting8;
                  if (this.isSwapModeBooleanHotkeySetting(swapmode3, flag5x, hotkeysetting7)) {
                     return true;
                  }
               }

               long i = this.client().getWindow().getHandle();
               int j = getIntByLong(i);
               boolean flagx = "ХВ".equals(this.server.getString2());

               for (int k = 0; k < trackedItemArray.length; k++) {
                  HotkeySetting hotkeysetting = this.hotkeySettingArray[k];
                  if (hotkeysetting.getValue() != -1
                     && (!flagx || isTrackedItem2(trackedItemArray[k]))
                     && isIntHotkeySetting(j, hotkeysetting)
                     && !this.booleanArray[k]
                     && this.isTrackedItem5(trackedItemArray[k])) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   private void update12() {
      if (this.player() != null && this.client().currentScreen == null) {
         if (this.check5() && !SneakState.isValueAsBoolean()) {
            if (this.check3()) {
               SneakState.update5();
               this.flag8 = true;
            }
         }
      }
   }

   private boolean isItemStackTrackedItem(ItemStack itemStack, TrackedItem trackedItem) {
      if (!isItemStackTrackedItem2(itemStack, trackedItem)) {
         return false;
      } else {
         NotificationManager.getInstance().onTrackedItemDouble(trackedItem, ItemCooldowns.getDoubleByItemStack(itemStack));
         return true;
      }
   }

   private void update13() {
      this.setBoolean(false);
   }

   private void setBoolean(boolean flag) {
      if (this.inGame() && !this.check4()) {
         Slot slot = SphereItems.getSlotByItem2(Items.WIND_CHARGE);
         if (slot != null) {
            TrackedItem trackeditem1 = TrackedItem.WIND_CHARGE;
            ItemStack itemstack = slot.getStack();
            TrackedItem trackeditem = trackeditem1;
            if (!this.isItemStackTrackedItem(itemstack, trackeditem)) {
               NotificationManager.getInstance().onTrackedItem2(TrackedItem.WIND_CHARGE);
               this.jumpSwapState.setSlot(slot);
               this.flag7 = flag;
            }
         }
      }
   }

   private boolean check4() {
      return this.flag6 || this.swapState.flag || this.swapState2.flag || !this.jumpSwapState.check();
   }

   public boolean isTrackedItem3(TrackedItem trackedItem) {
      if (this.interactionManager() != null && this.player() != null && !this.check4()) {
         Slot slot = this.getSlotByTrackedItem(trackedItem);
         if (slot != null) {
            ItemStack itemstack = slot.getStack();
            if (this.isItemStackTrackedItem(itemstack, trackedItem)) {
               return false;
            } else {
               int i = this.getInt();
               if (SphereItems.isInt2(slot.id)) {
                  int i1 = slot.id - 36;
                  Runnable runnable = this.getRunnable();
                  int j = i1;
                  InventoryActions.onIntIntRunnableModule(j, i, runnable, this);
               } else {
                  int l = slot.id;
                  Runnable runnable1 = this.getRunnable();
                  int k = l;
                  InventoryActions.onRunnableModuleInt(runnable1, this, k);
               }

               return true;
            }
         } else {
            return this.isTrackedItem4(trackedItem);
         }
      } else {
         return false;
      }
   }

   private void update14() {
      if (this.flag8) {
         this.flag8 = false;
         SneakState.update2();
      }
   }

   @Override
   public void onDisable() {
      this.update16();
      this.update24();
   }

   private void update15() {
      if (this.flag8) {
         this.flag8 = false;
         Runnable runnable = SneakState::update2;
         byte b0 = 1;
         SphereItems.onRunnableInt(runnable, b0);
      }
   }

   private boolean isTrackedItem4(TrackedItem trackedItem) {
      BundleSlot bundleslot = SphereItems.getBundleSlotByPredicate(p0 -> AutoSwap.isTrackedItemItemStack5(trackedItem, p0));
      if (bundleslot == null) {
         return false;
      } else {
         BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)bundleslot.getBundleSlot()
            .getStack()
            .get(DataComponentTypes.BUNDLE_CONTENTS);
         if (bundlecontentscomponent != null && bundleslot.getIndexInBundle() < bundlecontentscomponent.size()) {
            ItemStack itemstack = bundlecontentscomponent.get(bundleslot.getIndexInBundle());
            if (this.isItemStackTrackedItem(itemstack, trackedItem)) {
               return false;
            } else {
               int i = this.getInt();
               int l = bundleslot.getBundleSlot().id;
               int i1 = bundleslot.getIndexInBundle();
               Runnable runnable = this.getRunnable();
               int k = i1;
               int j = l;
               InventoryActions.onModuleIntIntRunnableInt(this, i, j, runnable, k);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public static Float getFloat() {
      return JumpSwapState.getFloat();
   }

   private static boolean isItemStackItem(ItemStack itemStack, Item item2) {
      return item2 != null && itemStack.getItem() != item2 ? item2 == Items.SHULKER_BOX && Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock : true;
   }

   public static boolean isTrackedItemItemStack4(TrackedItem trackedItem, ItemStack itemStack) {
      if (itemStack == null || itemStack.isEmpty() || trackedItem == null) {
         return false;
      } else if (trackedItem.value != -1) {
         return SphereItems.isItemStackInt2(itemStack, trackedItem.value);
      } else if (trackedItem.value2 != -1) {
         return SphereItems.isItemStackInt(itemStack, trackedItem.value2);
      } else if (trackedItem.text4 == null && trackedItem.text5 == null) {
         return trackedItem.item != null && itemStack.getItem() == trackedItem.item;
      } else if (!isItemStackItem(itemStack, trackedItem.item)) {
         return false;
      } else {
         if (trackedItem.text5 != null) {
            String s = trackedItem.text5;
            if (SphereItems.isStringItemStack(s, itemStack)) {
               return true;
            }
         }

         return trackedItem.text4 != null && SphereItems.isItemStackString(itemStack, trackedItem.text4);
      }
   }

   private void setInt(int count) {
      this.flag6 = true;
      InventoryActions.onModuleIntRunnable(this, count, this::setFlag62);
   }

   private static boolean isItemStack(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == Items.PLAYER_HEAD && SphereItems.isItemStack8(itemStack);
   }

   private void onItemStackString(ItemStack itemStack, String text) {
      NotificationManager notificationmanager = NotificationManager.getInstance();
      Icon icon1 = Icon.getIconByItemStack(itemStack);
      String s = "";
      Icon icon = icon1;
      notificationmanager.onStringIconString(s, icon, text);
   }

   private void onSwapMode(SwapMode swapMode) {
      if (this.interactionManager() != null && this.player() != null && !this.check4()) {
         ItemStack itemstack = this.player().getOffHandStack();
         boolean flagx = isItemStack(itemstack);
         if (swapMode != SwapMode.TOTEM_TO_TOTEM && (swapMode != SwapMode.TOTEM_TO_SPHERE || !flagx)) {
            this.update20();
         } else {
            this.update18();
         }
      }
   }

   private static SwapMode getSwapModeByString(String text) {
      return switch (text) {
         case "Сфера на сферу" -> SwapMode.SPHERE_TO_SPHERE;
         case "Талик на талик" -> SwapMode.TOTEM_TO_TOTEM;
         default -> SwapMode.TOTEM_TO_SPHERE;
      };
   }

   private static boolean isBooleanHotkeySetting(boolean flag, HotkeySetting hotkeySetting) {
      return hotkeySetting.check() && !flag;
   }

   private boolean isTrackedItem5(TrackedItem trackedItem) {
      Slot slot = this.getSlotByTrackedItem(trackedItem);
      if (slot != null) {
         if (!SphereItems.isInt2(slot.id)) {
            ItemStack itemstack1 = slot.getStack();
            if (!isItemStackTrackedItem2(itemstack1, trackedItem)) {
               return true;
            }
         }

         return false;
      } else {
         ItemStack itemstack = this.getItemStackByTrackedItem2(trackedItem);
         return itemstack != null && !isItemStackTrackedItem2(itemstack, trackedItem);
      }
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      return (ActionResult)(!this.flag9 ? ActionResult.PASS : NoInteract.getActionResultByEntityPlayerEntityHand(entity2, playerEntity, hand));
   }

   private boolean isHotkeySettingSwapStateItemTrackedItem(HotkeySetting hotkeySetting, SwapState swapState, Item item2, TrackedItem trackedItem) {
      if (!isSwapStateHotkeySetting(swapState, hotkeySetting)) {
         return false;
      } else if (swapState.flag) {
         return swapState.flag2 || swapState.value3 >= 0;
      } else {
         Slot slot = SphereItems.getSlotByItem2(item2);
         if (slot == null) {
            return this.isTrackedItem5(trackedItem);
         } else {
            if (!SphereItems.isInt2(slot.id)) {
               ItemStack itemstack = slot.getStack();
               if (!isItemStackTrackedItem2(itemstack, trackedItem)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private void onHotkeySettingItemSwapStateTrackedItem(HotkeySetting hotkeySetting, Item item2, SwapState swapState, TrackedItem trackedItem) {
      if (this.player() != null && this.interactionManager() != null) {
         boolean flagx = hotkeySetting.getValue() != -1 && hotkeySetting.check();
         if (!swapState.flag) {
            if (flagx && this.client().currentScreen == null && !this.check4()) {
               this.onItemHotkeySettingSwapStateTrackedItem(item2, hotkeySetting, swapState, trackedItem);
            }
         } else if (!flagx) {
            if (!swapState.flag4) {
               this.setSwapState(swapState);
            }
         } else {
            boolean flag1 = this.player().getMainHandStack().getItem() == swapState.item;
            if (flag1 && !swapState.flag3) {
               this.options().useKey.setPressed(true);
               swapState.flag3 = true;
            } else if (!flag1 && swapState.flag3) {
               this.options().useKey.setPressed(false);
               swapState.flag3 = false;
            }

            if (this.player().isUsingItem()) {
               TickCounter.setInt(2);
            }
         }
      } else {
         if (swapState.flag) {
            this.setSwapState(swapState);
         }
      }
   }

   private void onItemHotkeySettingSwapStateTrackedItem(Item item2, HotkeySetting hotkeySetting, SwapState swapState, TrackedItem trackedItem) {
      Slot slot = SphereItems.getSlotByItem2(item2);
      if (slot == null) {
         this.onTrackedItemItemHotkeySettingSwapState(trackedItem, item2, hotkeySetting, swapState);
      } else {
         ItemStack itemstack = slot.getStack();
         if (!this.isItemStackTrackedItem(itemstack, trackedItem)) {
            this.onSwapStateItem(swapState, item2);
            if (SphereItems.isInt2(slot.id)) {
               this.inventory().selectedSlot = slot.id - 36;
               this.options().useKey.setPressed(true);
               swapState.flag3 = true;
               this.update15();
            } else {
               swapState.flag2 = true;
               swapState.value = slot.id;
               swapState.flag4 = true;
               int k = slot.id;
               int l = swapState.value2;
               Runnable runnable = this.getRunnableBySwapStateHotkeySetting(swapState, hotkeySetting);
               int j = l;
               int i = k;
               InventoryActions.onIntRunnableModuleInt(i, runnable, this, j);
            }
         }
      }
   }

   private void onTrackedItemItemHotkeySettingSwapState(TrackedItem trackedItem, Item item3, HotkeySetting hotkeySetting, SwapState swapState) {
      BundleSlot bundleslot = SphereItems.getBundleSlotByPredicate(p0 -> AutoSwap.isTrackedItemItemStack2(trackedItem, p0));
      if (bundleslot != null) {
         BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)bundleslot.getBundleSlot()
            .getStack()
            .get(DataComponentTypes.BUNDLE_CONTENTS);
         if (bundlecontentscomponent != null && bundleslot.getIndexInBundle() < bundlecontentscomponent.size()) {
            ItemStack itemstack = bundlecontentscomponent.get(bundleslot.getIndexInBundle());
            if (!this.isItemStackTrackedItem(itemstack, trackedItem)) {
               this.onSwapStateItem(swapState, item3);
               swapState.value3 = bundleslot.getBundleSlot().id;
               swapState.item2 = this.inventory().getStack(swapState.value2).getItem();
               swapState.flag4 = true;
               int l = bundleslot.getBundleSlot().id;
               int i1 = bundleslot.getIndexInBundle();
               int j1 = swapState.value2;
               IntConsumer intconsumer = p0 -> this.onSwapStateHotkeySettingInt(swapState, hotkeySetting, p0);
               int k = j1;
               int j = i1;
               int i = l;
               InventoryActions.onModuleIntIntIntIntConsumer(this, k, j, i, intconsumer);
            }
         }
      }
   }

   private static boolean isSwapStateHotkeySetting(SwapState swapState, HotkeySetting hotkeySetting) {
      boolean flagx = hotkeySetting.getValue() != -1 && hotkeySetting.check();
      return swapState.flag != flagx;
   }

   private static boolean isIntHotkeySetting(int count, HotkeySetting hotkeySetting) {
      if (!hotkeySetting.check()) {
         return false;
      } else {
         int i = hotkeySetting.getValue2();
         return i == 0 || i == count;
      }
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      return (ActionResult)(!this.flag9 ? ActionResult.PASS : NoInteract.getActionResultByBlockHitResultHandWorld(blockHitResult, hand, world2));
   }

   private boolean isSwapMode(SwapMode swapMode) {
      if (this.player() == null) {
         return false;
      } else {
         boolean flagx = swapMode == SwapMode.TOTEM_TO_TOTEM || swapMode == SwapMode.TOTEM_TO_SPHERE && isItemStack(this.player().getOffHandStack());
         return flagx ? SphereItems.getSlotEntry() != null : SphereItems.getSlotByPredicate(AutoSwap::isItemStack) != null;
      }
   }

   private ItemStack getItemStackByTrackedItem2(TrackedItem trackedItem) {
      BundleSlot bundleslot = SphereItems.getBundleSlotByPredicate(p0 -> AutoSwap.isTrackedItemItemStack3(trackedItem, p0));
      if (bundleslot == null) {
         return null;
      } else {
         BundleContentsComponent bundlecontentscomponent = (BundleContentsComponent)bundleslot.getBundleSlot()
            .getStack()
            .get(DataComponentTypes.BUNDLE_CONTENTS);
         return bundlecontentscomponent != null && bundleslot.getIndexInBundle() < bundlecontentscomponent.size()
            ? bundlecontentscomponent.get(bundleslot.getIndexInBundle())
            : null;
      }
   }

   private Slot getSlotByTrackedItem(TrackedItem trackedItem) {
      if (trackedItem.value != -1) {
         return SphereItems.getSlotByInt2(trackedItem.value);
      } else {
         if (trackedItem.value2 != -1) {
            Slot slot = SphereItems.getSlotByInt(trackedItem.value2);
            if (slot != null) {
               return slot;
            }

            if (trackedItem.text2 == null && trackedItem.text3 == null) {
               return null;
            }
         }

         Slot slot1 = SphereItems.getSlotByPredicate(p0 -> AutoSwap.isTrackedItemItemStack(trackedItem, p0));
         if (slot1 != null) {
            return slot1;
         } else {
            return trackedItem.item != null ? SphereItems.getSlotByItem(trackedItem.item) : null;
         }
      }
   }

   private void update16() {
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      Arrays.fill(this.booleanArray, false);
      Arrays.fill(this.booleanArray2, false);
   }

   private static boolean isItemStackTrackedItem2(ItemStack itemStack, TrackedItem trackedItem) {
      if (trackedItem != null && trackedItem.value2 != -1) {
         return false;
      } else {
         return SphereItems.isItemStack6(itemStack) && SphereItems.isItemStack9(itemStack) ? false : ItemCooldowns.getDoubleByItemStack(itemStack) > 0.0;
      }
   }

   private int getInt() {
      this.flag6 = true;
      this.flag9 = true;
      return this.inventory().selectedSlot;
   }

   private Runnable getRunnable() {
      return this::update23;
   }

   private void onSwapStateItem(SwapState swapState, Item item2) {
      swapState.value2 = this.inventory().selectedSlot;
      swapState.item = item2;
      swapState.flag = true;
      this.flag9 = true;
   }

   private boolean isSwapModeBooleanHotkeySetting(SwapMode swapMode, boolean flag, HotkeySetting hotkeySetting) {
      return isBooleanHotkeySetting(flag, hotkeySetting) && this.isSwapMode(swapMode);
   }

   private boolean check5() {
      if (this.clientPlayer() == null) {
         return false;
      } else {
         Input input = this.clientPlayer().input;
         if (input == null || input.movementForward == 0.0F && input.movementSideways == 0.0F) {
            return this.options() != null && this.options().jumpKey.isPressed() ? true : this.player().isSprinting();
         } else {
            return true;
         }
      }
   }

   private void setSwapState(SwapState swapState) {
      if (swapState.flag3) {
         this.options().useKey.setPressed(false);
         swapState.flag3 = false;
      }

      if (swapState.value3 >= 0 && swapState.value2 >= 0) {
         int j1 = swapState.value3;
         int k1 = swapState.value2;
         int j2 = swapState.value4;
         Item item1 = swapState.item2;
         Runnable runnable = this::setFlag92;
         Item item = item1;
         int k = j2;
         int j = k1;
         int i = j1;
         InventoryActions.onModuleIntRunnableIntItemInt(this, k, runnable, i, item, j);
      } else if (swapState.flag2 && swapState.value >= 0 && swapState.value2 >= 0) {
         int l1 = swapState.value;
         int i2 = swapState.value2;
         Runnable runnable1 = this::setFlag9;
         int i1 = i2;
         int l = l1;
         InventoryActions.onIntRunnableModuleInt(l, runnable1, this, i1);
      } else {
         if (swapState.value2 >= 0) {
            this.inventory().selectedSlot = swapState.value2;
         }

         this.flag9 = false;
         this.update15();
      }

      swapState.update();
   }

   private Runnable getRunnableBySwapStateHotkeySetting(SwapState swapState, HotkeySetting hotkeySetting) {
      return () -> this.onSwapStateHotkeySetting(swapState, hotkeySetting);
   }

   private void update17() {
      boolean flagx = this.flag5;
      HotkeySetting hotkeysetting = this.keyPerla;
      if (isBooleanHotkeySetting(flagx, hotkeysetting) && this.isTrackedItem3(TrackedItem.ENDER_PEARL)) {
         NotificationManager.getInstance().onTrackedItem2(TrackedItem.ENDER_PEARL);
      }

      this.flag5 = this.keyPerla.check();
   }

   @Override
   public void onEnable() {
      this.update16();
      this.update24();
   }

   private void update18() {
      SlotEntry slotentry = SphereItems.getSlotEntry();
      if (slotentry == null) {
         this.onItemStackString(itemStack2, "Тотем не найден");
      } else {
         ItemStack itemstack = slotentry.getSlot().getStack();
         TrackedItem trackeditem = slotentry.getEntry();
         if (!this.isItemStackTrackedItem(itemstack, trackeditem)) {
            this.setInt(slotentry.getSlot().id);
         }
      }
   }

   @Override
   public void update8() {
      SneakState.update4();
      this.update12();
   }

   private void update19() {
      if (this.flag7 && this.jumpSwapState.check()) {
         this.flag7 = false;
         Slot slot = SphereItems.getSlotByItem2(Items.MACE);
         if (slot == null) {
            this.onItemStackString(itemStack3, "Булава не найдена");
         } else if (SphereItems.isInt2(slot.id)) {
            SphereItems.onInt(slot.id - 36);
         } else {
            this.flag6 = true;
            int k = slot.id;
            int l = this.inventory().selectedSlot;
            Runnable runnable = this::setFlag6;
            int j = l;
            int i = k;
            InventoryActions.onIntRunnableModuleInt(i, runnable, this, j);
         }
      }
   }

   private void update20() {
      Slot slot = SphereItems.getSlotByPredicate(AutoSwap::isItemStack);
      if (slot == null) {
         this.onItemStackString(itemStack, "Сфера не найдена");
      } else {
         this.setInt(slot.id);
      }
   }

   private void update21() {
      if ("Вcе".equals(this.mode.getString2())) {
         boolean flagx = this.flag2;
         HotkeySetting hotkeysetting = this.bindTotemSfera;
         if (isBooleanHotkeySetting(flagx, hotkeysetting)) {
            this.onSwapMode(SwapMode.TOTEM_TO_SPHERE);
         }

         this.flag2 = this.bindTotemSfera.check();
         boolean flag1 = this.flag3;
         HotkeySetting hotkeysetting1 = this.bindSferaSfera;
         if (isBooleanHotkeySetting(flag1, hotkeysetting1)) {
            this.onSwapMode(SwapMode.SPHERE_TO_SPHERE);
         }

         this.flag3 = this.bindSferaSfera.check();
         boolean flag2x = this.flag4;
         HotkeySetting hotkeysetting2 = this.bindTotemTotem;
         if (isBooleanHotkeySetting(flag2x, hotkeysetting2)) {
            this.onSwapMode(SwapMode.TOTEM_TO_TOTEM);
         }

         this.flag4 = this.bindTotemTotem.check();
      } else {
         boolean flag3x = this.flag;
         HotkeySetting hotkeysetting3 = this.bindSvapa;
         if (isBooleanHotkeySetting(flag3x, hotkeysetting3)) {
            this.onSwapMode(getSwapModeByString(this.mode.getString2()));
         }

         this.flag = this.bindSvapa.check();
      }
   }

   private void update22() {
      this.setBoolean(true);
   }

   private void update23() {
      this.flag6 = false;
      this.flag9 = false;
   }

   private void onSwapStateHotkeySettingInt(SwapState swapState, HotkeySetting hotkeySetting, int count) {
      swapState.value4 = count;
      swapState.flag4 = false;
      if (swapState.flag && hotkeySetting.check()) {
         this.options().useKey.setPressed(true);
         swapState.flag3 = true;
      }
   }

   private static ItemStack getItemStackByIdentityHashMapSetting3(IdentityHashMap identityHashMap, Setting setting2) {
      SwapProjectile swapprojectile = (SwapProjectile)identityHashMap.get(setting2);
      if (swapprojectile == null) {
         return ItemStack.EMPTY;
      } else {
         Item item = swapprojectile.item != null ? swapprojectile.item : Items.SHULKER_BOX;
         return new ItemStack(item);
      }
   }

   private void setBoolean(Boolean flag) {
      this.flag9 = flag;
   }

   private Boolean getBoolean3() {
      return "Вcе".equals(this.mode.getString2());
   }

   private static Boolean getBooleanBySupplier(Supplier<Boolean> supplier) {
      return !(Boolean)supplier.get();
   }

   public boolean check6() {
      return "ХВ".equals(this.server.getString2());
   }

   private Slot getSlotBySwapProjectile(SwapProjectile swapProjectile) {
      if (swapProjectile.check()) {
         for (Item item : SwapProjectile.getItemArray()) {
            Slot slot = SphereItems.getSlotByItem(item);
            if (slot != null) {
               return slot;
            }
         }

         return null;
      } else {
         return swapProjectile.item == null ? null : SphereItems.getSlotByItem(swapProjectile.item);
      }
   }

   public HotkeySetting[] getHotkeySettingArray() {
      return this.hotkeySettingArray;
   }

   public HotkeySetting getKeyZaryadaVetra() {
      return this.keyZaryadaVetra;
   }

   public static TrackedItem[] getTrackedItemArray() {
      return trackedItemArray;
   }

   private void onBoolean(boolean flag2) {
      long i = this.client().getWindow().getHandle();
      int j = getIntByLong(i);

      for (int k = 0; k < trackedItemArray.length; k++) {
         HotkeySetting hotkeysetting = this.hotkeySettingArray[k];
         int l = hotkeysetting.getValue();
         if (l != -1) {
            TrackedItem trackeditem = trackedItemArray[k];
            if (!flag2 || isTrackedItem2(trackeditem)) {
               boolean flagx = isIntHotkeySetting(j, hotkeysetting);
               if (flagx && !this.booleanArray[k]) {
                  if (trackeditem.flag) {
                     if (this.isTrackedItem(trackeditem)) {
                        NotificationManager.getInstance().onTrackedItem(trackeditem);
                     }
                  } else if (this.isTrackedItem3(trackeditem)) {
                     NotificationManager.getInstance().onTrackedItem2(trackeditem);
                  }
               }

               this.booleanArray[k] = flagx;
            }
         }
      }
   }

   private static int getIntByLong(long time) {
      byte b0 = 0;
      if (GLFW.glfwGetKey(time, 340) == 1 || GLFW.glfwGetKey(time, 344) == 1) {
         b0 |= 1;
      }

      if (GLFW.glfwGetKey(time, 341) == 1 || GLFW.glfwGetKey(time, 345) == 1) {
         b0 |= 2;
      }

      if (GLFW.glfwGetKey(time, 342) == 1 || GLFW.glfwGetKey(time, 346) == 1) {
         b0 |= 4;
      }

      return b0;
   }

   private void onSwapStateHotkeySetting(SwapState swapState, HotkeySetting hotkeySetting) {
      swapState.flag4 = false;
      if (swapState.flag && hotkeySetting.check()) {
         this.options().useKey.setPressed(true);
         swapState.flag3 = true;
      }
   }

   private void update24() {
      this.flag6 = false;
      this.flag7 = false;
      if (this.flag8) {
         this.flag8 = false;
         SneakState.update2();
      }

      this.jumpSwapState.update3();
      InventoryActions.setModule(this);
      this.flag9 = false;
      this.onSwapState(this.swapState);
      this.onSwapState(this.swapState2);
   }

   private void onSwapState(SwapState swapState) {
      if (swapState.flag3) {
         this.options().useKey.setPressed(false);
         swapState.flag3 = false;
      }

      swapState.update();
   }

   private static boolean isTrackedItemItemStack5(TrackedItem trackedItem, ItemStack itemStack) {
      return isTrackedItemItemStack4(trackedItem, itemStack);
   }

   private boolean isSwapProjectile(SwapProjectile swapProjectile) {
      if (this.interactionManager() != null && this.player() != null && !this.check4()) {
         Slot slot = this.getSlotBySwapProjectile(swapProjectile);
         if (slot == null) {
            return false;
         } else {
            int i = this.getInt();
            Runnable runnable = this.getRunnable();
            if (SphereItems.isInt2(slot.id)) {
               int j = slot.id - 36;
               InventoryActions.onIntIntRunnableModule(j, i, runnable, this);
            } else {
               int k = slot.id;
               InventoryActions.onRunnableModuleInt(runnable, this, k);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void setFlag92() {
      this.flag9 = false;
   }
}
