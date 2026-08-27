package client.module.render;

import client.enums.TrackedItem;
import client.module.Category;
import client.module.Module;
import client.render.HudRenderContext;
import client.setting.ColorToggleSetting;
import client.setting.CompactGroupSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.util.MathUtil;
import client.util.StringParts;
import client.util.UnsafeFields;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;

public class ItemColor extends Module {
   private UnsafeFields<Integer> unsafeFields;
   private UnsafeFields<Integer> unsafeFields2;
   private UnsafeFields<Integer> unsafeFields3;
   private UnsafeFields<Integer> unsafeFields4;
   private boolean flag = false;
   private ListSetting podsvetka;
   private final Map<TrackedItem, ColorToggleSetting> map;

   public ItemColor() {
      super("ItemColor", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"C", "т", "а", "т", "и", "к"}), StringParts.join(new String[]{"М", "и", "г", "а", "т", "ь"})),
         List.of(StringParts.join(new String[]{"М", "и", "г", "а", "т", "ь"})),
         false
      );
      listsetting.setName("Подсветка");
      listsetting.setDescription("Способ отрисовки подсветки");
      this.podsvetka = listsetting;
      this.map = new EnumMap<>(TrackedItem.class);
      this.addSetting(this.podsvetka);
      TrackedItem[] atrackeditem = TrackedItem.values();
      Setting[] asetting = new Setting[atrackeditem.length];
      IdentityHashMap identityhashmap = new IdentityHashMap();

      for (int i = 0; i < atrackeditem.length; i++) {
         TrackedItem trackeditem = atrackeditem[i];
         ColorToggleSetting colortogglesetting = new ColorToggleSetting(
            trackeditem.text, "Подсветка для " + trackeditem.text + ".", true, getIntByIntInt(i, atrackeditem.length), true
         );
         this.map.put(trackeditem, colortogglesetting);
         asetting[i] = colortogglesetting;
         identityhashmap.put(colortogglesetting, trackeditem);
      }

      CompactGroupSetting compactgroupsetting1 = new CompactGroupSetting("", "", asetting);
      compactgroupsetting1.setName("Предметы");
      compactgroupsetting1.setDescription("Настройте подсветку для каждого предмета.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting1;
      compactgroupsetting.getCompactGroupSettingByFunction2(p0 -> ItemColor.getItemStackByIdentityHashMapSetting(identityhashmap, p0));
      this.addSetting(compactgroupsetting);
   }

   @Override
   public void onDisable() {
   }

   private static int getIntByIntInt(int count, int count2) {
      float f = count2 <= 1 ? 0.0F : (float)count / count2;
      return MathUtil.getIntByFloat(f);
   }

   private static int getIntByInt(int count) {
      float f = (float)(System.currentTimeMillis() % 900L) / 900.0F;
      float f1 = 0.5F - 0.5F * (float)Math.cos(f * (float) (Math.PI * 2));
      float f2 = 0.35F + 0.65F * f1;
      int i = Math.clamp((long)((int)((count >> 24 & 0xFF) * f2)), 0, 255);
      return count & 16777215 | i << 24;
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

   private static ItemStack getItemStackByIdentityHashMapSetting(IdentityHashMap identityHashMap, Setting setting2) {
      TrackedItem trackeditem = (TrackedItem)identityHashMap.get(setting2);
      return getItemStackByTrackedItem(trackeditem);
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (!this.notInGame()) {
         if (this.currentScreen() instanceof HandledScreen handledscreen) {
            int[] aint = this.getIntArrayByHandledScreen(handledscreen);
            int i = aint[0];
            int j = aint[1];
            DrawContext drawcontext = hudRenderContext.getDrawContext();

            for (Slot slot : handledscreen.getScreenHandler().slots) {
               if (slot != null && slot.isEnabled() && slot.hasStack()) {
                  ColorToggleSetting colortogglesetting = this.getColorToggleSettingByItemStack(slot.getStack());
                  if (colortogglesetting != null && colortogglesetting.check()) {
                     int k = colortogglesetting.getInt();
                     int l = "Мигать".equals(this.podsvetka.getString2()) ? getIntByInt(k) : k;
                     drawcontext.fill(i + slot.x, j + slot.y, i + slot.x + 16, j + slot.y + 16, l);
                  }
               }
            }
         }
      }
   }

   private ColorToggleSetting getColorToggleSettingByItemStack(ItemStack itemStack) {
      String s = itemStack.getName().getString().toLowerCase();

      for (Entry entry : this.map.entrySet()) {
         TrackedItem trackeditem = (TrackedItem)entry.getKey();
         if (trackeditem.text2 != null && (trackeditem.item == null || itemStack.isOf(trackeditem.item)) && s.contains(trackeditem.text2)) {
            return (ColorToggleSetting)entry.getValue();
         }
      }

      for (Entry entry1 : this.map.entrySet()) {
         TrackedItem trackeditem1 = (TrackedItem)entry1.getKey();
         if (trackeditem1 == TrackedItem.SHULKER) {
            if (itemStack.isIn(ItemTags.SHULKER_BOXES)) {
               return (ColorToggleSetting)entry1.getValue();
            }
         } else if (trackeditem1.text2 == null && trackeditem1.item != null && itemStack.isOf(trackeditem1.item)) {
            return (ColorToggleSetting)entry1.getValue();
         }
      }

      return null;
   }

   private int[] getIntArrayByHandledScreen(HandledScreen handledScreen) {
      if (!this.flag) {
         this.unsafeFields = new UnsafeFields<>(null, HandledScreen.class, 23);
         this.unsafeFields2 = new UnsafeFields<>(null, HandledScreen.class, 24);
         this.unsafeFields3 = new UnsafeFields<>(null, HandledScreen.class, 9);
         this.unsafeFields4 = new UnsafeFields<>(null, HandledScreen.class, 10);
         this.flag = true;
      }

      int i = this.unsafeFields.getIntByObject(handledScreen);
      int j = this.unsafeFields2.getIntByObject(handledScreen);
      if (i == 0 && j == 0) {
         int k = this.unsafeFields3.getIntByObject(handledScreen);
         int l = this.unsafeFields4.getIntByObject(handledScreen);
         if (k == 0) {
            k = 176;
         }

         if (l == 0) {
            l = 166;
         }

         return new int[]{(handledScreen.width - k) / 2, (handledScreen.height - l) / 2};
      } else {
         return new int[]{i, j};
      }
   }

   @Override
   public void onEnable() {
   }
}
