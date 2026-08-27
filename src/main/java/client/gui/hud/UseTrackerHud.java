package client.gui.hud;

import client.api.Theme;
import client.concurrent.CooldownTracker;
import client.data.AnimatedInt;
import client.data.OrderedSet;
import client.enums.CooldownItem;
import client.gui.widget.KeybindEntry;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.client.HudModule;
import client.module.player.Protect;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.util.Interpolation;
import client.util.ItemIcons;
import client.util.TextFormatUtil;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

public class UseTrackerHud extends HudPanel {
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
   private static final String text = "Use Tracker";
   private static final UnsafeAccess<Protect> unsafeAccess2 = new UnsafeAccess<>(Protect.class);
   private final Map<String, ItemStack> map2 = new HashMap<>();
   private final Map<String, Float> map3 = new HashMap<>();
   private final Map<String, Double> map4 = new HashMap<>();
   private final Map<String, Float> map5 = new HashMap<>();
   private final Interpolation interpolation3 = new Interpolation();
   private long time2 = -1L;
   private float value288;
   private final OrderedSet<String> orderedSet = new OrderedSet<>();
   private final Map<String, CooldownItem> map6 = new HashMap<>();
   private final ArrayList<KeybindEntry> list5 = new ArrayList<>();

   @Override
   public String getString() {
      return "УзеТраскер";
   }

   @Override
   protected List getList2() {
      this.map2.put(CooldownItem.ENCHANTED_GOLDEN_APPLE.text, CooldownItem.ENCHANTED_GOLDEN_APPLE.itemStack);
      this.map2.put(CooldownItem.NETHERITE_SCRAP.text, CooldownItem.NETHERITE_SCRAP.itemStack);
      return List.of(new KeybindEntry(CooldownItem.ENCHANTED_GOLDEN_APPLE.text, "142s"), new KeybindEntry(CooldownItem.NETHERITE_SCRAP.text, "6.4s"));
   }

   @Override
   protected float getFloat28() {
      return 24.0F;
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
   protected String getString2() {
      String s = CooldownTracker.getText();
      if (s != null && !s.isEmpty()) {
         Protect protect = (Protect)unsafeAccess2.getModule2();
         return protect != null ? protect.getStringByString2(s) : s;
      } else {
         return "Use Tracker";
      }
   }

   @Override
   protected float getFloatByKeybindEntry3(KeybindEntry keybindEntry) {
      return 22.0F;
   }

   @Override
   public String getString3() {
      return "ut";
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

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getKuldaunyTargets().isFlag3();
   }

   private static String getStringByDouble(double value) {
      return value < 10.0 ? TextFormatUtil.getStringByDouble(value) + "s" : (int)value + "s";
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      Map map = this.map6;
      map.clear();

      for (CooldownItem cooldownitem : CooldownTracker.getCooldownItemArray()) {
         if (CooldownTracker.getLongByCooldownItem(cooldownitem) > 0L) {
            map.put(cooldownitem.text, cooldownitem);
         }
      }

      this.orderedSet.onSetComparator(map.keySet(), Comparator.comparingLong(var1x -> CooldownTracker.getLongByCooldownItem((CooldownItem)map.get(var1x))));

      for (String s : (Iterable<String>)(this.orderedSet.getLinkedHashSetAsIterable())) {
         CooldownItem cooldownitem1 = (CooldownItem)map.get(s);
         double d0 = CooldownTracker.getLongByCooldownItem(cooldownitem1) / 1000.0;
         arraylist.add(new KeybindEntry(s, getStringByDouble(d0)));
         this.map2.put(s, cooldownitem1.itemStack);
         Float f = this.map3.get(s);
         if (f != null && f <= 0.001F) {
            this.map5.remove(s);
         }

         this.map3.put(s, (float)Math.clamp(d0 * 1000.0 / cooldownitem1.time, 0.0, 1.0));
         this.map4.put(s, d0);
      }

      for (String s1 : this.map3.keySet()) {
         if (!map.containsKey(s1)) {
            this.map3.put(s1, 0.0F);
            this.map4.put(s1, 0.0);
         }
      }

      this.addMapArray(new Map[]{this.map2, this.map3, this.map4, this.map5});
      return arraylist;
   }

   @Override
   protected float getFloat30() {
      return Math.max(16.0F, 20.0F);
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

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getKuldaunyTargets().setBoolean(flag);
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.USE_TRACKER;
   }

   @Override
   protected boolean check16() {
      return true;
   }
}
