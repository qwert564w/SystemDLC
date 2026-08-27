package client.gui.widget;

import client.api.ListEntry;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ResourceItem;
import client.data.Tween;
import client.render.ItemIconCache;
import client.render.TextShader;
import client.setting.ResourceIndexSetting;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import org.joml.Matrix4f;

public class ResourceFinderPanel extends OverlayPanel<String, ItemSlotEntry> {
   private final ResourceIndexSetting resourceIndexSetting;
   private final List<ResourceItem> list = new ArrayList<>();
   private final Map<String, Tween> map = new HashMap<>();
   private boolean flag9;

   public ResourceFinderPanel(ResourceIndexSetting resourceIndexSetting2) {
      this.resourceIndexSetting = resourceIndexSetting2;
   }

   @Override
   protected String getString() {
      return "Убрать всё";
   }

   private static String getStringByString(String text) {
      int i;
      try {
         i = Integer.parseInt(text);
      } catch (NumberFormatException numberformatexception) {
         return null;
      }
      return switch (i) {
         case 1 -> "i";
         case 2 -> "ii";
         case 3 -> "iii";
         case 4 -> "iv";
         case 5 -> "v";
         case 6 -> "vi";
         case 7 -> "vii";
         case 8 -> "viii";
         case 9 -> "ix";
         case 10 -> "x";
         default -> null;
      };
   }

   @Override
   protected void update12() {
      this.flag9 = !this.flag9;
      this.update17();
   }

   private ItemSlotEntry getItemSlotEntryByString(String text2) {
      for (ItemSlotEntry itemslotentry : (Iterable<ItemSlotEntry>)(this.scrollAnimator.getCollection())) {
         if (itemslotentry.resourceItem.text.equals(text2)) {
            return itemslotentry;
         }
      }

      return null;
   }

   @Override
   protected String getString2() {
      return "Найдите запомненные предметы из сундуков и шалкеров.";
   }

   @Override
   protected String getString3() {
      return "Например: починка, ассасин, зелье";
   }

   @Override
   protected void update13() {
      for (String s : (Iterable<String>)(new ArrayList(this.resourceIndexSetting.getSet()))) {
         this.resourceIndexSetting.onStringBoolean(s, false);
         ItemSlotEntry itemslotentry = this.getItemSlotEntryByString(s);
         if (itemslotentry != null) {
            itemslotentry.tween.setFloat2(0.0F);
         }
      }

      if (this.flag9) {
         this.update17();
      }
   }

   @Override
   protected void update15() {
      this.update17();
   }

   @Override
   protected String getString4() {
      return this.flag9 ? "Показать все" : "Только подсв.";
   }

   @Override
   protected boolean isListEntryFloatFloatDoubleFloatDouble(ListEntry listEntry, float value, float value2, double value3, float value4, double value5) {
      ItemSlotEntry itemslotentry = (ItemSlotEntry)listEntry;
      return this.isDoubleFloatFloatItemSlotEntryDoubleFloat(value3, value, value2, itemslotentry, value5, value4);
   }

   protected boolean isDoubleFloatFloatItemSlotEntryDoubleFloat(double value, float value2, float value3, ItemSlotEntry itemSlotEntry, double value4, float value5) {
      float f = value3 + value5 - 8.0F - 28.0F;
      float f1 = value2 + 5.0F;
      float f6 = f - 2.0F;
      float f7 = f1 - 3.0F;
      float f5 = 22.0F;
      float f4 = 32.0F;
      float f3 = f7;
      float f2 = f6;
      if (isFloatFloatDoubleFloatFloatDouble(f2, f3, value4, f5, f4, value)) {
         this.onString(itemSlotEntry.resourceItem.text);
      }

      return true;
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      if (this.list.isEmpty()) {
         String s = this.resourceIndexSetting.getMap().isEmpty() ? "Откройте сундук/шалкер — предметы запомнятся" : "Ничего не найдено";
         float f = TextShader.getFloatByStringFloat(s, 14.0F);
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, value3 + (311.0F - f) / 2.0F, value + 67.0F, 14.0F, Theme.mutedFg(), value2);
      }
   }

   protected void onItemSlotEntryFloatFloatFloatFloatFloatFloatMatrix4f(
      ItemSlotEntry itemSlotEntry, float value, float value2, float value3, float value4, float value5, float value6, Matrix4f matrix4f
   ) {
      ResourceItem resourceitem = itemSlotEntry.resourceItem;
      boolean flag = this.resourceIndexSetting.isString(resourceitem.text);
      itemSlotEntry.tween.setFloat2(flag ? 1.0F : 0.0F);
      float f = itemSlotEntry.tween.getFloat();
      float f1 = value4 * (0.4F + 0.6F * f);
      Tween tween = this.map.computeIfAbsent(resourceitem.text, ResourceFinderPanel::getTweenByString);
      double d2 = value3;
      double d3 = value;
      float f12 = 26.0F;
      double d1 = d3;
      double d0 = d2;
      boolean flag1 = isFloatFloatDoubleFloatFloatDouble(value6, value5, d1, f12, value2, d0);
      float f14 = 6.0F;
      float f13 = 26.0F;
      PanelPainter.onFloatMatrix4fBooleanFloatFloatFloatTweenFloatFloat(value4, matrix4f, flag1, f14, value5, f13, tween, value2, value6);
      float f2 = value6 + 6.0F;
      float f3 = value5 + 5.0F;
      ItemStack itemstack = getItemStackByItemSlotEntry(itemSlotEntry);
      if (itemstack != null && !itemstack.isEmpty()) {
         float f15 = 16.0F;
         ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f15, f1, f2, itemstack, matrix4f, f3);
      }

      int l = Theme.mutedFg();
      int k = Theme.foreground();
      int j = l;
      int i = AnimatedInt.getIntByIntFloatInt(k, f, j);
      float f4 = value6 + value2 - 8.0F - 28.0F;
      float f5 = value5 + 5.0F;
      String s = "×" + resourceitem.getInt();
      float f6 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f7 = f4 - 8.0F - f6;
      float f8 = value5 + 7.0F;
      float f9 = f2 + 16.0F + 6.0F;
      float f10 = value5 + 7.0F;
      float f11 = f7 - 6.0F - f9;
      float f16 = 26.0F;
      ScissorStack.onFloatFloatFloatFloat(f11, f16, value5, f9);
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, resourceitem.text3, f9, f10, 12.0F, i, f1);
      ScissorStack.update();
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, f7, f8, 12.0F, Theme.mutedFg(), f1);
      PanelPainter.onFloatFloatFloatMatrix4fFloat(f5, value4, f4, matrix4f, f);
   }

   @Override
   protected String getString5() {
      return "Поиск ресурсов";
   }

   private static String getStringByResourceItem(ResourceItem resourceItem) {
      return resourceItem.text3;
   }

   private static Tween getTweenByString(String text) {
      return EasingPresets.getTween();
   }

   @Override
   protected ListEntry getListEntryByObject2(Object value) {
      return this.getItemSlotEntryByString2((String)value);
   }

   @Override
   protected void onMatrix4fFloatFloatFloatFloatFloatFloatListEntry(
      Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, float value6, ListEntry listEntry
   ) {
      ItemSlotEntry itemslotentry = (ItemSlotEntry)listEntry;
      this.onItemSlotEntryFloatFloatFloatFloatFloatFloatMatrix4f(itemslotentry, value5, value, value4, value2, value6, value3, matrix4f);
   }

   protected ItemSlotEntry getItemSlotEntryByString2(String text) {
      ResourceItem resourceitem = (ResourceItem)this.resourceIndexSetting.getMap().get(text);
      return new ItemSlotEntry(resourceitem, this.resourceIndexSetting.isString(text));
   }

   @Override
   protected List<ResourceItem> getList() {
      ArrayList arraylist = new ArrayList(this.list.size());

      for (ResourceItem resourceitem : this.list) {
         arraylist.add(resourceitem.text);
      }

      return arraylist;
   }

   private void update17() {
      String s = this.textField.getString() == null ? "" : this.textField.getString().trim().toLowerCase();
      this.list.clear();

      for (ResourceItem resourceitem : this.resourceIndexSetting.getMap().values()) {
         if ((!this.flag9 || this.resourceIndexSetting.isString(resourceitem.text)) && (s.isEmpty() || isStringResourceItem(s, resourceitem))) {
            this.list.add(resourceitem);
         }
      }

      this.list.sort(Comparator.comparing(ResourceFinderPanel::getStringByResourceItem, String.CASE_INSENSITIVE_ORDER));
   }

   @Override
   protected boolean check3() {
      return this.flag9;
   }

   private static String getStringByString2(String text) {
      StringBuilder stringbuilder = null;
      int i = 0;
      int j = text.length();

      while (i < j) {
         char c0 = text.charAt(i);
         boolean flag = i == 0 || !Character.isLetterOrDigit(text.charAt(i - 1));
         if (flag && Character.isDigit(c0)) {
            int k = i;

            while (k < j && Character.isDigit(text.charAt(k))) {
               k++;
            }

            boolean flag1 = k == j || !Character.isLetterOrDigit(text.charAt(k));
            if (flag1) {
               String s = text.substring(i, k);
               String s1 = getStringByString(s);
               if (s1 != null) {
                  if (stringbuilder == null) {
                     stringbuilder = new StringBuilder(text.length()).append(text, 0, i);
                  }

                  stringbuilder.append(s1);
                  i = k;
                  continue;
               }
            }
         }

         if (stringbuilder != null) {
            stringbuilder.append(c0);
         }

         i++;
      }

      return stringbuilder == null ? null : stringbuilder.toString();
   }

   private static boolean isStringResourceItem(String text, ResourceItem resourceItem) {
      String s = resourceItem.text4;
      if (s == null) {
         return false;
      } else if (s.contains(text)) {
         return true;
      } else {
         String s1 = getStringByString2(text);
         return s1 != null && s.contains(s1);
      }
   }

   private static ItemStack getItemStackByItemSlotEntry(ItemSlotEntry itemSlotEntry) {
      String s = itemSlotEntry.resourceItem.text5;
      if (s != null && s.equals(itemSlotEntry.text) && itemSlotEntry.itemStack != null) {
         return itemSlotEntry.itemStack;
      } else {
         itemSlotEntry.text = s;
         itemSlotEntry.itemStack = null;
         if (s != null && !s.isEmpty()) {
            MinecraftClient minecraftclient = MinecraftClient.getInstance();
            if (minecraftclient.world != null) {
               try {
                  NbtCompound nbtcompound = StringNbtReader.parse(s);
                  itemSlotEntry.itemStack = ItemStack.fromNbtOrEmpty(minecraftclient.world.getRegistryManager(), nbtcompound);
               } catch (Throwable throwable) {
               }
            }
         }

         if (itemSlotEntry.itemStack == null || itemSlotEntry.itemStack.isEmpty()) {
            Item item = itemSlotEntry.resourceItem.getItem();
            itemSlotEntry.itemStack = item != null ? new ItemStack(item) : ItemStack.EMPTY;
         }

         return itemSlotEntry.itemStack;
      }
   }

   private void onString(String text) {
      boolean flag = !this.resourceIndexSetting.isString(text);
      this.resourceIndexSetting.onStringBoolean(text, flag);
      ItemSlotEntry itemslotentry = this.getItemSlotEntryByString(text);
      if (itemslotentry != null) {
         itemslotentry.tween.setFloat2(flag ? 1.0F : 0.0F);
      }

      if (this.flag9) {
         this.update17();
      }
   }
}
