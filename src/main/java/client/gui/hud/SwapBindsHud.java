package client.gui.hud;

import client.api.Theme;
import client.data.ChoiceOption;
import client.data.ScrollAnimator;
import client.enums.TrackedItem;
import client.gui.widget.ItemEntry;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.module.player.AutoSwap;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.HotkeySetting;
import client.setting.Setting;
import client.util.HotbarEntry;
import client.util.ItemIcons;
import client.util.ItemSlot;
import client.util.SphereItems;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.joml.Matrix4f;

public class SwapBindsHud extends RenderElement {
   private static final float value271 = 32.0F;
   private static final float value272 = 5.0F;
   private static final float value273 = 8.0F;
   private static final float value274 = 4.0F;
   private static final float value275 = 6.0F;
   private static final float value276 = 6.0F;
   private static final float value277 = 16.0F;
   private static final float value278 = 12.0F;
   private static final float value279 = 16.0F;
   private static final float value280 = 4.0F;
   private static final float value281 = 6.0F;
   private static final float value282 = 0.05F;
   private static final float value283 = 9.0F;
   private static final float value284 = 0.001F;
   private static final int value285 = 36;
   private static final UnsafeAccess<AutoSwap> unsafeAccess2 = new UnsafeAccess<>(AutoSwap.class);
   private static final int value286 = Integer.MAX_VALUE;
   private final ScrollAnimator<ItemEntry> scrollAnimator = new ScrollAnimator<>(5.0F);
   private final BooleanSetting hideEsliNetVInventare;
   private final ChoiceSetting orientaciya;
   private int value287;
   private List<HotbarEntry> list2;
   private final Map<Integer, HotbarEntry> map;
   private final ArrayList<Object> list3;
   private final Function<Object, ItemEntry> function;

   public SwapBindsHud() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скрывать если нет в инвентаре");
      booleansetting.setDescription("Включите функцию чтобы биндов не было в худе если в инвентаре не нашлось предмета.");
      this.hideEsliNetVInventare = booleansetting;
      ChoiceSetting choicesetting = new ChoiceSetting(
         "", "", new ChoiceOption("Горизонталь", CategoryType.ORIENT_HORIZONTAL), new ChoiceOption("Вертикаль", CategoryType.ORIENT_VERTICAL), false
      );
      choicesetting.setName("Ориентация");
      choicesetting.setDescription("Расположение плиток биндов: горизонтально или вертикально.");
      this.orientaciya = choicesetting;
      this.value287 = Integer.MIN_VALUE;
      this.list2 = List.of();
      this.map = new HashMap<>();
      this.list3 = new ArrayList<>();
      this.function = var1x -> {
         HotbarEntry hotbarentry = this.map.get((Integer)var1x);
         return hotbarentry == null ? null : new ItemEntry(hotbarentry.index(), hotbarentry.stack(), hotbarentry.count(), hotbarentry.bind());
      };
      this.onSettingArray(new Setting[]{this.hideEsliNetVInventare, this.orientaciya});
   }

   @Override
   public String getString() {
      return "ЗшапБиндз";
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.SWAP_HUD;
   }

   private List getList2() {
      ClientPlayerEntity clientplayerentity = Feature.mc != null ? Feature.mc.player : null;
      if (clientplayerentity == null) {
         return this.getList3();
      } else if (clientplayerentity.age == this.value287) {
         return this.list2;
      } else {
         this.value287 = clientplayerentity.age;
         this.list2 = this.getList3();
         return this.list2;
      }
   }

   private List getList3() {
      AutoSwap autoswap = (AutoSwap)unsafeAccess2.getModule2();
      if (autoswap == null) {
         return (List)(this.check4() ? getList4() : new ArrayList());
      } else {
         HotkeySetting[] ahotkeysetting = autoswap.getHotkeySettingArray();
         TrackedItem[] atrackeditem = AutoSwap.getTrackedItemArray();
         if (ahotkeysetting != null && atrackeditem != null && Feature.mc != null && Feature.mc.player != null) {
            ClientPlayerEntity clientplayerentity = Feature.mc.player;
            PlayerInventory playerinventory = clientplayerentity.getInventory();
            boolean flag = this.hideEsliNetVInventare.isFlag3();
            int i = Math.min(atrackeditem.length, ahotkeysetting.length);
            ArrayList arraylist = new ArrayList();

            for (int j = 0; j < i; j++) {
               int k = ahotkeysetting[j].getValue();
               if (k != -1 && k != -1) {
                  TrackedItem trackeditem3 = atrackeditem[j];
                  HotkeySetting hotkeysetting2 = ahotkeysetting[j];
                  ItemStack itemstack = clientplayerentity.getOffHandStack();
                  HotkeySetting hotkeysetting = hotkeysetting2;
                  TrackedItem trackeditem = trackeditem3;
                  HotbarEntry hotbarentry = getHotbarEntryByHotkeySettingTrackedItemPlayerInventoryItemStackInt(
                     hotkeysetting, trackeditem, playerinventory, itemstack, j
                  );
                  if (hotbarentry != null || !flag) {
                     arraylist.add(hotbarentry != null ? hotbarentry : new HotbarEntry(j, ItemStack.EMPTY, 0, ahotkeysetting[j].getText2()));
                  }
               }
            }

            HotkeySetting hotkeysetting1 = autoswap.getKeyZaryadaVetra();
            if (hotkeysetting1 != null) {
               int i1 = hotkeysetting1.getValue();
               if (i1 != -1 && i1 != -1) {
                  TrackedItem trackeditem2 = TrackedItem.WIND_CHARGE;
                  ItemStack itemstack1 = clientplayerentity.getOffHandStack();
                  TrackedItem trackeditem1 = trackeditem2;
                  int l = Integer.MAX_VALUE;
                  HotbarEntry hotbarentry1 = getHotbarEntryByHotkeySettingTrackedItemPlayerInventoryItemStackInt(
                     hotkeysetting1, trackeditem1, playerinventory, itemstack1, l
                  );
                  if (hotbarentry1 != null) {
                     arraylist.add(hotbarentry1);
                  } else if (!flag) {
                     arraylist.add(new HotbarEntry(Integer.MAX_VALUE, ItemStack.EMPTY, 0, hotkeysetting1.getText2()));
                  }
               }
            }

            return (List)(arraylist.isEmpty() && this.check4() ? getList4() : arraylist);
         } else {
            return (List)(this.check4() ? getList4() : new ArrayList());
         }
      }
   }

   @Override
   public float getFloat9() {
      this.update4();
      return this.check24() ? this.scrollAnimator.getFloat() : 32.0F;
   }

   @Override
   public String getString3() {
      return "ss";
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getSvapBindy().isFlag3();
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getSvapBindy().setBoolean(flag);
   }

   private boolean check24() {
      return this.orientaciya.isFlag3();
   }

   private void onFloatFloatItemEntryFloatMatrix4fFloatDrawContext(
      float value, float value2, ItemEntry itemEntry, float value4, Matrix4f matrix4f, float value5, DrawContext drawContext
   ) {
      TextShader.update2();
      float f = this.getFloatByFloat(value2);
      int k = Theme.background();
      float f15 = 1.0F;
      float f14 = 1.0F;
      float f13 = 0.0F;
      int j = 436207616;
      float f12 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f11 = 12.0F;
      float f10 = 12.0F;
      float f9 = 12.0F;
      float f8 = 12.0F;
      float f7 = 32.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value, j, i, value4, value5, f15, f8, f7, f14, matrix4f, f, b0, f10, f12, f9, f11, f13
      );
      float f1 = value4 + 8.0F;
      float f2 = value5 + 8.0F;
      float f3 = value5 + 10.0F;
      float f4 = this.getFloatByFloatItemEntryDrawContextMatrix4fFloatFloatFloat(f3, itemEntry, drawContext, matrix4f, value2, f2, f1);
      float f5 = value4 + itemEntry.value3;
      float f6 = value4 + value;
      if (f6 > f5) {
         f4 += f6 - f5;
      }

      String s = itemEntry.text;
      this.onFloatFloatFloatMatrix4fFloatFloatString(f4, value2, f3, matrix4f, value5, f, s);
   }

   private float getFloatByFloatItemEntryDrawContextMatrix4fFloatFloatFloat(
      float value, ItemEntry itemEntry, DrawContext drawContext, Matrix4f matrix4f, float value3, float value4, float value5
   ) {
      if (itemEntry.check()) {
         float f5 = value5 + 3.5F;
         float f6 = value4 + 3.5F;
         CategoryType categorytype1 = CategoryType.CLOSE;
         int i = Theme.border();
         float f1 = 9.0F;
         CategoryType categorytype = categorytype1;
         SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f6, categorytype, i, matrix4f, value3, f1, f5);
         return value5 + 16.0F + 6.0F;
      } else {
         float f2 = 16.0F;
         ItemStack itemstack = itemEntry.itemStack;
         if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(value5, matrix4f, value3, f2, value4, itemstack)) {
            float f3 = 16.0F;
            ItemStack itemstack1 = itemEntry.itemStack;
            ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f3, itemstack1, value3, value4, value5, drawContext);
         }

         String s = getStringByInt(itemEntry.value2);
         float f = value5 + 16.0F + 4.0F;
         int j = Theme.foreground();
         float f4 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value, f, j, f4, value3, s, matrix4f);
         return f + TextShader.getFloatByStringFloat(s, 12.0F) + 6.0F;
      }
   }

   private void onFloatFloatFloatMatrix4fFloatFloatString(float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5, String text) {
      float f = TextShader.getFloatByStringFloat(text, 12.0F);
      float f1 = 6.0F + f + 6.0F;
      float f2 = value4 + 8.0F;
      int i = Theme.elevated();
      float f4 = 4.0F;
      float f3 = 16.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f4, value, i, matrix4f, f3, f1, value5, f2);
      float f7 = value + 6.0F;
      int j = Theme.foreground();
      float f6 = 12.0F;
      float f5 = f7;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value3, f5, j, f6, value2, text, matrix4f);
   }

   public BooleanSetting getHideEsliNetVInventare() {
      return this.hideEsliNetVInventare;
   }

   private static HotbarEntry getHotbarEntryByHotkeySettingTrackedItemPlayerInventoryItemStackInt(
      HotkeySetting hotkeySetting, TrackedItem trackedItem, PlayerInventory playerInventory, ItemStack itemStack2, int count
   ) {
      ItemSlot itemslot = new ItemSlot();

      for (int i = 0; i < 36; i++) {
         itemslot.onItemStackTrackedItem(playerInventory.getStack(i), trackedItem);
      }

      itemslot.onItemStackTrackedItem(itemStack2, trackedItem);
      SphereItems.onConsumer(var2x -> itemslot.onItemStackTrackedItem(var2x, trackedItem));
      return itemslot.value > 0 && !itemslot.itemStack.isEmpty() ? new HotbarEntry(count, itemslot.itemStack.copy(), itemslot.value, hotkeySetting.getText2()) : null;
   }

   private static List getList4() {
      return List.of(
         new HotbarEntry(0, new ItemStack(Items.TOTEM_OF_UNDYING), 2, "F"),
         new HotbarEntry(1, new ItemStack(Items.ENDER_PEARL), 16, "R"),
         new HotbarEntry(2, new ItemStack(Items.PLAYER_HEAD), 5, "MB5")
      );
   }

   public static float getFloatByItemStackStringInt(ItemStack itemStack, String text, int count) {
      float f = 6.0F + TextShader.getFloatByStringFloat(text, 12.0F) + 6.0F;
      if (itemStack != null && !itemStack.isEmpty()) {
         float f1 = TextShader.getFloatByStringFloat(getStringByInt(count), 12.0F);
         return 28.0F + f1 + 6.0F + f + 8.0F;
      } else {
         return 30.0F + f + 8.0F;
      }
   }

   private static String getStringByInt(int count) {
      return "x" + count;
   }

   private float getFloat28() {
      float f = 0.0F;

      for (ItemEntry itementry : (Iterable<ItemEntry>)(this.scrollAnimator.getCollection())) {
         if (itementry.value3 > f) {
            f = itementry.value3;
         }
      }

      return f;
   }

   private void update4() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         List<HotbarEntry> list = this.getList2();
         this.map.clear();
         this.list3.clear();

         for (HotbarEntry hotbarentry : list) {
            this.map.put(hotbarentry.index(), hotbarentry);
            this.list3.add(hotbarentry.index());
         }

         float f = 0.05F;
         Function functionx = this.function;
         ArrayList arraylist = this.list3;
         this.scrollAnimator.onFloatListFunction(f, arraylist, functionx);

         for (ItemEntry itementry : (Iterable<ItemEntry>)(this.scrollAnimator.getCollection())) {
            HotbarEntry hotbarentry1 = this.map.get(itementry.value);
            if (hotbarentry1 != null) {
               String s = hotbarentry1.bind();
               int i = hotbarentry1.count();
               ItemStack itemstack = hotbarentry1.stack();
               itementry.onStringItemStackInt(s, itemstack, i);
            }
         }

         this.scrollAnimator.onList(this.list3);
         this.scrollAnimator.update();
      }
   }

   @Override
   public float getFloat10() {
      return this.getFloat9();
   }

   @Override
   public float getFloat11() {
      this.update4();
      return this.check24() ? this.getFloat28() : this.scrollAnimator.getFloatByToDoubleFunction2(var0 -> var0.value3);
   }

   @Override
   protected boolean check14() {
      return !this.scrollAnimator.check2();
   }

   @Override
   public float getFloat14() {
      return this.getFloat11();
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected boolean check23() {
      return this.scrollAnimator.check() || !this.getList2().isEmpty();
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      this.update4();
      if (!this.scrollAnimator.check2()) {
         boolean flag = this.check24();
         float f = flag ? this.getFloat28() : 0.0F;
         if (flag) {
            this.scrollAnimator.setFloat(this.getValue261());
         } else {
            this.scrollAnimator.onFloatToDoubleFunction2(this.getValue260(), var0 -> var0.value3);
         }

         for (ItemEntry itementry : (Iterable<ItemEntry>)(this.scrollAnimator.getCollection())) {
            float f1 = itementry.animation.getFloat();
            if (!(f1 <= 0.001F)) {
               float f2 = flag ? this.getValue260() : itementry.animation.getValue7();
               float f3 = flag ? itementry.animation.getValue7() : this.getValue261();
               float f4 = flag ? f : itementry.value3;
               float f5 = value * f1;
               this.onFloatFloatItemEntryFloatMatrix4fFloatDrawContext(f4, f5, itementry, f2, matrix4f, f3, drawContext);
            }
         }
      }
   }

   @Override
   public float getFloat22() {
      return this.getValue260();
   }

   @Override
   public float getFloat23() {
      return this.getFloat9();
   }

   @Override
   public float getFloat24() {
      return this.getFloat11();
   }
}
