package client.gui.hud;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.OrderedSet;
import client.enums.TrackedItem;
import client.gui.widget.KeybindEntry;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.module.player.ItemTracker;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.util.CooldownEntry;
import client.util.Interpolation;
import client.util.ItemIcons;
import client.util.TextFormatUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.joml.Matrix4f;

public class CooldownsHud extends HudPanel {
   private static final float value277 = 16.0F;
   private static final float value278 = 8.0F;
   private static final float value279 = 20.0F;
   private static final float value280 = 4.0F;
   private static final float value281 = 6.0F;
   private static final float value282 = 11.0F;
   private static final float value283 = 1.5F;
   private static final float value284 = 5.0F;
   private static final float value285 = 0.18F;
   private static final float value286 = 0.65F;
   private static final double value287 = 3.0;
   private final Map<String, ItemStack> map2 = new HashMap<>();
   private final Map<String, Float> map3 = new HashMap<>();
   private final Map<String, Double> map4 = new HashMap<>();
   private final Map<String, Float> map5 = new HashMap<>();
   private final Interpolation interpolation3 = new Interpolation();
   private long time2 = -1L;
   private float value288;
   private final OrderedSet<String> orderedSet = new OrderedSet<>();
   private final Map<Identifier, CooldownEntry> map6 = new HashMap<>();
   private final Set<Identifier> set2 = new HashSet<>();
   private final Map<String, CooldownEntry> map7 = new HashMap<>();
   private final ArrayList<KeybindEntry> list5 = new ArrayList<>();

   @Override
   public String getString() {
      return "Соолдошнз";
   }

   @Override
   protected List getList2() {
      this.map2.put("Ender Pearl", new ItemStack(Items.ENDER_PEARL));
      this.map2.put("Chorus Fruit", new ItemStack(Items.CHORUS_FRUIT));
      return List.of(new KeybindEntry("Ender Pearl", "5s"), new KeybindEntry("Chorus Fruit", "2.3s"));
   }

   @Override
   protected boolean check24() {
      return true;
   }

   @Override
   protected int getIntByKeybindEntry(KeybindEntry keybindEntry) {
      Double d0 = this.map4.get(keybindEntry.getText());
      if (d0 != null && !(d0 > 3.0)) {
         float f = 1.0F - (float)Math.clamp(d0 / 3.0, 0.0, 1.0);
         int k = Theme.mutedFg();
         int j = Theme.danger();
         int i = k;
         return AnimatedInt.getIntByIntFloatInt(j, f, i);
      } else {
         return Theme.mutedFg();
      }
   }

   @Override
   protected float getFloat28() {
      return 24.0F;
   }

   @Override
   protected String getString2() {
      return "Cooldowns";
   }

   @Override
   public String getString3() {
      return "cd";
   }

   @Override
   protected float getFloatByKeybindEntry3(KeybindEntry keybindEntry) {
      return 22.0F;
   }

   private static String getStringByDouble(double value) {
      return value < 10.0 ? TextFormatUtil.getStringByDouble(value) + "s" : (int)value + "s";
   }

   @Override
   protected void onFloatMatrix4fFloatFloatKeybindEntryFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, KeybindEntry keybindEntry, float value4, float value5) {
      float f = value2 - 6.0F;
      float f1 = value5 + 5.0F + 11.0F + 12.0F;
      float f2 = value + -4.0F;
      int k = Theme.elevated();
      float f7 = this.getFloatByFloat(value4);
      int i = k;
      float f6 = 4.0F;
      float f5 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f6, f, i, matrix4f, f5, f1, f7, f2);
      float f3 = Math.round(value2 + value5 + 5.0F);
      float f4 = Math.round(value + 0.5F);
      int j = this.getIntByKeybindEntry(keybindEntry);
      float f10 = this.getFloatByKeybindEntry4(keybindEntry);
      float f9 = 1.5F;
      float f8 = 11.0F;
      ShapeShader.onIntFloatMatrix4fFloatFloatFloatFloatFloat(j, f4, matrix4f, f10, f9, f3, value4, f8);
   }

   private float getFloatByKeybindEntry4(KeybindEntry keybindEntry) {
      String s = keybindEntry.getText();
      Float f = this.map3.get(s);
      float f1 = f == null ? 0.65F : f;
      long i = UiContext.getTime();
      if (this.time2 != i) {
         this.time2 = i;
         this.value288 = this.interpolation3.getFloat2();
      }

      Float f2 = this.map5.get(s);
      float f7;
      if (f2 == null) {
         f7 = f1;
      } else {
         f7 = f2;
         float f6 = 0.18F;
         float f5 = this.value288;
         float f4 = f7;
         f7 = Interpolation.getFloatByFloatFloatFloatFloat2(f1, f4, f5, f6);
      }

      float f3 = f7;
      this.map5.put(s, f3);
      return f3;
   }

   private static String getStringByItemStackIdentifier(ItemStack itemStack, Identifier identifier2) {
      TrackedItem trackeditem = ItemTracker.getTrackedItemByItemStack(itemStack);
      if (trackeditem != null) {
         return trackeditem.text;
      } else {
         Identifier identifier = Registries.ITEM.getId(itemStack.getItem());
         if (identifier == null) {
            identifier = identifier2;
         }

         return TextFormatUtil.getStringByString(identifier.getPath());
      }
   }

   @Override
   protected float getFloat30() {
      return Math.max(16.0F, 20.0F);
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.COOLDOWN;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getKuldauny().setBoolean(flag);
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getKuldauny().isFlag3();
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      if (Feature.mc != null && Feature.mc.player != null) {
         ClientPlayerEntity clientplayerentity = Feature.mc.player;
         ItemCooldownManager itemcooldownmanager = clientplayerentity.getItemCooldownManager();
         PlayerInventory playerinventory = clientplayerentity.getInventory();
         float f = Feature.mc.getRenderTickCounter() != null ? Feature.mc.getRenderTickCounter().getTickDelta(false) : 0.0F;
         Map map = this.map6;
         Set set = this.set2;
         Map map1 = this.map7;
         map.clear();
         set.clear();
         map1.clear();
         DefaultedList defaultedlist = playerinventory.main;
         onSetItemCooldownManagerFloatMapList(set, itemcooldownmanager, f, map, defaultedlist);
         DefaultedList defaultedlist1 = playerinventory.offHand;
         onSetItemCooldownManagerFloatMapList(set, itemcooldownmanager, f, map, defaultedlist1);

         for (CooldownEntry cooldownentry : (Iterable<CooldownEntry>)(map.values())) {
            map1.put(cooldownentry.label(), cooldownentry);
         }

         this.orderedSet.onSetComparator(map1.keySet(), Comparator.comparingDouble(var1x -> ((CooldownEntry)map1.get(var1x)).remainingSeconds()));

         for (String s : (Iterable<String>)(this.orderedSet.getLinkedHashSetAsIterable())) {
            CooldownEntry cooldownentry1 = (CooldownEntry)map1.get(s);
            arraylist.add(new KeybindEntry(s, getStringByDouble(cooldownentry1.remainingSeconds())));
            this.map2.put(s, cooldownentry1.stack());
            Float f1 = this.map3.get(s);
            if (f1 != null && f1 <= 0.001F) {
               this.map5.remove(s);
            }

            this.map3.put(s, cooldownentry1.ratio());
            this.map4.put(s, cooldownentry1.remainingSeconds());
         }

         for (String s1 : this.map3.keySet()) {
            if (!map1.containsKey(s1)) {
               this.map3.put(s1, 0.0F);
               this.map4.put(s1, 0.0);
            }
         }

         this.addMapArray(new Map[]{this.map2, this.map3, this.map4, this.map5});
         return arraylist;
      } else {
         return arraylist;
      }
   }

   private static void onSetItemCooldownManagerFloatMapList(Set set, ItemCooldownManager itemCooldownManager, float value, Map map, List<ItemStack> list) {
      for (ItemStack itemstack : list) {
         if (itemstack != null && !itemstack.isEmpty()) {
            Identifier identifier = itemCooldownManager.getGroup(itemstack);
            if (identifier != null && set.add(identifier)) {
               float f = itemCooldownManager.getCooldownProgress(itemstack, value);
               if (!(f <= 0.0F)) {
                  float f1 = itemCooldownManager.getCooldownProgress(itemstack, 0.0F) - itemCooldownManager.getCooldownProgress(itemstack, 1.0F);
                  double d0 = f1 > 0.0F ? f / f1 / 20.0 : f * 20.0;
                  String s = getStringByItemStackIdentifier(itemstack, identifier);
                  map.put(identifier, new CooldownEntry(itemstack.copy(), d0, Math.clamp(f, 0.0F, 1.0F), s));
               }
            }
         }
      }
   }

   @Override
   protected void onKeybindEntryFloatFloatFloatMatrix4fFloat(KeybindEntry keybindEntry, float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      ItemStack itemstack = this.map2.get(keybindEntry.getText());
      if (itemstack != null && !itemstack.isEmpty()) {
         float f4 = value4 + 8.0F;
         float f1 = 16.0F;
         float f = f4;
         if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f, matrix4f, value3, f1, value, itemstack)) {
            DrawContext drawcontext = this.drawContext;
            if (drawcontext != null) {
               f4 = value4 + 8.0F;
               float f3 = 16.0F;
               float f2 = f4;
               ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f3, itemstack, value3, value, f2, drawcontext);
            }
         }
      }
   }
}
